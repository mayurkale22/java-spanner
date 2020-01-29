/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.cloud.spanner;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import io.grpc.Context;
import io.opencensus.common.Scope;
import io.opencensus.stats.Measure;
import io.opencensus.stats.MeasureMap;
import io.opencensus.stats.StatsRecorder;
import io.opencensus.tags.Tag;
import io.opencensus.tags.TagContext;
import io.opencensus.tags.TagContextBuilder;
import io.opencensus.tags.TagKey;
import io.opencensus.tags.TagValue;
import io.opencensus.tags.Tagger;
import io.opencensus.tags.unsafe.ContextUtils;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.annotation.Nullable;

class StatsTestUtils {
  private StatsTestUtils() {}

  public static class MetricsRecord {
    public final ImmutableMap<TagKey, TagValue> tags;
    public final ImmutableMap<Measure, Number> metrics;

    private MetricsRecord(
        ImmutableMap<TagKey, TagValue> tags, ImmutableMap<Measure, Number> metrics) {
      this.tags = tags;
      this.metrics = metrics;
    }
  }

  /**
   * A {@link Tagger} implementation that saves metrics records to be accessible from {@link
   * #pollRecord()}, until {@link #rolloverRecords} is called.
   */
  public static final class FakeStatsRecorder extends StatsRecorder {

    private BlockingQueue<MetricsRecord> records;

    public FakeStatsRecorder() {
      rolloverRecords();
    }

    @Override
    public MeasureMap newMeasureMap() {
      return new FakeStatsRecord(this);
    }

    public MetricsRecord pollRecord() {
      return getCurrentRecordSink().poll();
    }

    /**
     * Disconnect this tagger with the contexts it has created so far. The records from those
     * contexts will not show up in {@link #pollRecord}. Useful for isolating the records between
     * test cases.
     */
    // This needs to be synchronized with getCurrentRecordSink() which may run concurrently.
    public synchronized void rolloverRecords() {
      records = new LinkedBlockingQueue<>();
    }

    private synchronized BlockingQueue<MetricsRecord> getCurrentRecordSink() {
      return records;
    }
  }

  public static final class FakeTagger extends Tagger {

    @Override
    public FakeTagContext empty() {
      return FakeTagContext.EMPTY;
    }

    @Override
    public TagContext getCurrentTagContext() {
      return ContextUtils.getValue(Context.current());
    }

    @Override
    public TagContextBuilder emptyBuilder() {
      return new FakeTagContextBuilder(ImmutableMap.<TagKey, TagValue>of());
    }

    @Override
    public FakeTagContextBuilder toBuilder(TagContext tags) {
      return new FakeTagContextBuilder(getTags(tags));
    }

    @Override
    public TagContextBuilder currentBuilder() {
      throw new UnsupportedOperationException();
    }

    @Override
    public Scope withTagContext(TagContext tags) {
      throw new UnsupportedOperationException();
    }
  }

  public static final class FakeStatsRecord extends MeasureMap {
    private final BlockingQueue<MetricsRecord> recordSink;
    public final Map<Measure, Number> metrics = Maps.newHashMap();

    private FakeStatsRecord(FakeStatsRecorder statsRecorder) {
      this.recordSink = statsRecorder.getCurrentRecordSink();
    }

    @Override
    public MeasureMap put(Measure.MeasureDouble measure, double value) {
      metrics.put(measure, value);
      return this;
    }

    @Override
    public MeasureMap put(Measure.MeasureLong measure, long value) {
      metrics.put(measure, value);
      return this;
    }

    @Override
    public void record(TagContext tags) {
      recordSink.add(new MetricsRecord(getTags(tags), ImmutableMap.copyOf(metrics)));
    }

    @Override
    public void record() {
      throw new UnsupportedOperationException();
    }
  }

  public static final class FakeTagContext extends TagContext {
    private static final FakeTagContext EMPTY =
        new FakeTagContext(ImmutableMap.<TagKey, TagValue>of());

    private final ImmutableMap<TagKey, TagValue> tags;

    private FakeTagContext(ImmutableMap<TagKey, TagValue> tags) {
      this.tags = tags;
    }

    public ImmutableMap<TagKey, TagValue> getTags() {
      return tags;
    }

    @Override
    public String toString() {
      return "[tags=" + tags + "]";
    }

    @Override
    protected Iterator<Tag> getIterator() {
      return Iterators.transform(
          tags.entrySet().iterator(),
          new Function<Entry<TagKey, TagValue>, Tag>() {
            @Override
            public Tag apply(@Nullable Entry<TagKey, TagValue> entry) {
              return Tag.create(entry.getKey(), entry.getValue());
            }
          });
    }
  }

  public static class FakeTagContextBuilder extends TagContextBuilder {

    private final Map<TagKey, TagValue> tagsBuilder = Maps.newHashMap();

    private FakeTagContextBuilder(Map<TagKey, TagValue> tags) {
      tagsBuilder.putAll(tags);
    }

    @SuppressWarnings("deprecation")
    @Override
    public TagContextBuilder put(TagKey key, TagValue value) {
      tagsBuilder.put(key, value);
      return this;
    }

    @Override
    public TagContextBuilder remove(TagKey key) {
      tagsBuilder.remove(key);
      return this;
    }

    @Override
    public TagContext build() {
      return new FakeTagContext(ImmutableMap.copyOf(tagsBuilder));
    }

    @Override
    public Scope buildScoped() {
      throw new UnsupportedOperationException();
    }
  }

  // This method handles the default TagContext, which isn't an instance of FakeTagContext.
  private static ImmutableMap<TagKey, TagValue> getTags(TagContext tags) {
    return tags instanceof FakeTagContext
        ? ((FakeTagContext) tags).getTags()
        : ImmutableMap.<TagKey, TagValue>of();
  }
}
