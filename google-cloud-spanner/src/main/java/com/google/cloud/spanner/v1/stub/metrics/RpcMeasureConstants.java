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
package com.google.cloud.spanner.v1.stub.metrics;

import io.opencensus.stats.Measure;
import io.opencensus.stats.Measure.MeasureLong;
import io.opencensus.tags.TagKey;

public class RpcMeasureConstants {

  public static final TagKey NUM_SESSIONS_BEING_PREPARED =
      TagKey.create("numSessionsBeingPrepared");

  public static final TagKey NUM_SESSIONS_BEING_CREATED = TagKey.create("numSessionsBeingCreated");

  public static final TagKey NUM_SESSIONS_IN_USE = TagKey.create("numSessionsInUse");

  public static final TagKey MAX_SESSIONS_IN_USE = TagKey.create("maxSessionsInUse");

  /** Unit to represent counts. */
  private static final String COUNT = "1";

  public static final MeasureLong SPANNER_SESSION =
      Measure.MeasureLong.create(
          "cloud.google.com/java/spanner/session_pool", "Number of pool of the sessions", COUNT);
}
