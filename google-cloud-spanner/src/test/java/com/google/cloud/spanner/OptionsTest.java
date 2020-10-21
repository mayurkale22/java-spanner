/*
 * Copyright 2017 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.spanner;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** Unit tests for {@link Options}. */
@RunWith(JUnit4.class)
public class OptionsTest {

  @Test
  public void negativeLimitsNotAllowed() {
    try {
      Options.limit(-1);
      fail("Expected exception");
    } catch (IllegalArgumentException ex) {
      assertNotNull(ex.getMessage());
    }
  }

  @Test
  public void zeroLimitNotAllowed() {
    try {
      Options.limit(0);
      fail("Expected exception");
    } catch (IllegalArgumentException ex) {
      assertNotNull(ex.getMessage());
    }
  }

  @Test
  public void negativePrefetchChunksNotAllowed() {
    try {
      Options.prefetchChunks(-1);
      fail("Expected exception");
    } catch (IllegalArgumentException ex) {
      assertNotNull(ex.getMessage());
    }
  }

  @Test
  public void zeroPrefetchChunksNotAllowed() {
    try {
      Options.prefetchChunks(0);
      fail("Expected exception");
    } catch (IllegalArgumentException ex) {
      assertNotNull(ex.getMessage());
    }
  }

  @Test
  public void allOptionsPresent() {
    Options options = Options.fromReadOptions(Options.limit(10), Options.prefetchChunks(1));
    assertThat(options.hasLimit()).isTrue();
    assertThat(options.limit()).isEqualTo(10);
    assertThat(options.hasPrefetchChunks()).isTrue();
    assertThat(options.prefetchChunks()).isEqualTo(1);
  }

  @Test
  public void allOptionsAbsent() {
    Options options = Options.fromReadOptions();
    assertThat(options.hasLimit()).isFalse();
    assertThat(options.hasPrefetchChunks()).isFalse();
    assertThat(options.hasFilter()).isFalse();
    assertThat(options.hasPageToken()).isFalse();
    assertThat(options.hasTag()).isFalse();
    assertThat(options.toString()).isEqualTo("");
    assertThat(options.equals(options)).isTrue();
    assertThat(options.equals(null)).isFalse();
    assertThat(options.equals(this)).isFalse();

    assertThat(options.hashCode()).isEqualTo(31);
  }

  @Test
  public void listOptTest() {
    int pageSize = 3;
    String pageToken = "ptok";
    String filter = "env";
    Options opts =
        Options.fromListOptions(
            Options.pageSize(pageSize), Options.pageToken(pageToken), Options.filter(filter));

    assertThat(opts.toString())
        .isEqualTo(
            "pageSize: "
                + Integer.toString(pageSize)
                + " pageToken: "
                + pageToken
                + " filter: "
                + filter
                + " ");

    assertThat(opts.hasPageSize()).isTrue();
    assertThat(opts.hasPageToken()).isTrue();
    assertThat(opts.hasFilter()).isTrue();

    assertThat(opts.pageSize()).isEqualTo(pageSize);
    assertThat(opts.pageToken()).isEqualTo(pageToken);
    assertThat(opts.filter()).isEqualTo(filter);
    assertThat(opts.hashCode()).isEqualTo(108027089);
  }

  @Test
  public void listEquality() {
    Options o1;
    Options o2;
    Options o3;

    o1 = Options.fromListOptions();
    o2 = Options.fromListOptions();
    assertThat(o1.equals(o2)).isTrue();

    o2 = Options.fromListOptions(Options.pageSize(1));
    assertThat(o1.equals(o2)).isFalse();
    assertThat(o2.equals(o1)).isFalse();

    o3 = Options.fromListOptions(Options.pageSize(1));
    assertThat(o2.equals(o3)).isTrue();

    o3 = Options.fromListOptions(Options.pageSize(2));
    assertThat(o2.equals(o3)).isFalse();

    o2 = Options.fromListOptions(Options.pageToken("t1"));
    assertThat(o1.equals(o2)).isFalse();

    o3 = Options.fromListOptions(Options.pageToken("t1"));
    assertThat(o2.equals(o3)).isTrue();

    o3 = Options.fromListOptions(Options.pageToken("t2"));
    assertThat(o2.equals(o3)).isFalse();

    o2 = Options.fromListOptions(Options.filter("f1"));
    assertThat(o1.equals(o2)).isFalse();

    o3 = Options.fromListOptions(Options.filter("f1"));
    assertThat(o2.equals(o3)).isTrue();

    o3 = Options.fromListOptions(Options.filter("f2"));
    assertThat(o2.equals(o3)).isFalse();
  }

  @Test
  public void readOptTest() {
    int limit = 3;
    String tag = "read";
    Options opts = Options.fromReadOptions(Options.limit(limit), Options.tag(tag));

    assertThat(opts.toString())
        .isEqualTo("limit: " + Integer.toString(limit) + " " + "tag: " + tag + " ");
    assertThat(opts.tag()).isEqualTo(tag);
    assertThat(opts.hashCode()).isEqualTo(3526226);
  }

  @Test
  public void readEquality() {
    Options o1;
    Options o2;
    Options o3;

    o1 = Options.fromReadOptions();
    o2 = Options.fromReadOptions();
    assertThat(o1.equals(o2)).isTrue();

    o2 = Options.fromReadOptions(Options.limit(1));
    assertThat(o1.equals(o2)).isFalse();
    assertThat(o2.equals(o1)).isFalse();

    o3 = Options.fromReadOptions(Options.limit(1));
    assertThat(o2.equals(o3)).isTrue();

    o3 = Options.fromReadOptions(Options.limit(2));
    assertThat(o2.equals(o3)).isFalse();
  }

  @Test
  public void queryOptTest() {
    int chunks = 3;
    String tag = "query";
    Options opts = Options.fromQueryOptions(Options.prefetchChunks(chunks), Options.tag(tag));
    assertThat(opts.toString())
        .isEqualTo("prefetchChunks: " + Integer.toString(chunks) + " " + "tag: " + tag + " ");
    assertThat(opts.prefetchChunks()).isEqualTo(chunks);
    assertThat(opts.tag()).isEqualTo(tag);
    assertThat(opts.hashCode()).isEqualTo(107974020);
  }

  @Test
  public void queryEquality() {
    Options o1;
    Options o2;
    Options o3;

    o1 = Options.fromQueryOptions();
    o2 = Options.fromQueryOptions();
    assertThat(o1.equals(o2)).isTrue();

    o2 = Options.fromReadOptions(Options.prefetchChunks(1));
    assertThat(o1.equals(o2)).isFalse();
    assertThat(o2.equals(o1)).isFalse();

    o3 = Options.fromReadOptions(Options.prefetchChunks(1));
    assertThat(o2.equals(o3)).isTrue();

    o3 = Options.fromReadOptions(Options.prefetchChunks(2));
    assertThat(o2.equals(o3)).isFalse();
  }

  @Test
  public void updateOptTest() {
    String tag = "tag-1";
    Options opts = Options.fromUpdateOptions(Options.tag(tag));

    assertThat(opts.toString()).isEqualTo("tag: " + tag + " ");
    assertThat(opts.hasTag()).isTrue();
    assertThat(opts.tag()).isEqualTo(tag);
    assertThat(opts.hashCode()).isEqualTo(110119551);
  }

  @Test
  public void updateEquality() {
    Options o1;
    Options o2;
    Options o3;

    o1 = Options.fromUpdateOptions();
    o2 = Options.fromUpdateOptions();
    assertThat(o1.equals(o2)).isTrue();

    o2 = Options.fromUpdateOptions(Options.tag("tag-1"));
    assertThat(o1.equals(o2)).isFalse();
    assertThat(o2.equals(o1)).isFalse();

    o3 = Options.fromUpdateOptions(Options.tag("tag-1"));
    assertThat(o2.equals(o3)).isTrue();

    o3 = Options.fromUpdateOptions(Options.tag("tag-2"));
    assertThat(o2.equals(o3)).isFalse();
  }

  @Test
  public void transactionOptTest() {
    String tag = "tag-1";
    Options opts = Options.fromTransactionOptions(Options.tag(tag));

    assertThat(opts.toString()).isEqualTo("tag: " + tag + " ");
    assertThat(opts.hasTag()).isTrue();
    assertThat(opts.tag()).isEqualTo(tag);
    assertThat(opts.hashCode()).isEqualTo(110119551);
  }

  @Test
  public void transactionEquality() {
    Options o1;
    Options o2;
    Options o3;

    o1 = Options.fromTransactionOptions();
    o2 = Options.fromTransactionOptions();
    assertThat(o1.equals(o2)).isTrue();

    o2 = Options.fromTransactionOptions(Options.tag("tag-1"));
    assertThat(o1.equals(o2)).isFalse();
    assertThat(o2.equals(o1)).isFalse();

    o3 = Options.fromTransactionOptions(Options.tag("tag-1"));
    assertThat(o2.equals(o3)).isTrue();

    o3 = Options.fromTransactionOptions(Options.tag("tag-2"));
    assertThat(o2.equals(o3)).isFalse();
  }
}
