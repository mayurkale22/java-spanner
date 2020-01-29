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
import io.opencensus.tags.TagValue;

public class RpcMeasureConstants {

  public static final TagKey SESSION_TYPE = TagKey.create("SessionType");
  public static final TagValue SESSION_VALUE = TagValue.create("value");

  /** Unit to represent counts. */
  private static final String COUNT = "1";

  public static final MeasureLong SPANNER_ACTIVE_SESSIONS =
      Measure.MeasureLong.create(
          "cloud.google.com/java/spanner/active_sessions", "Number of active sessions", COUNT);

  public static final MeasureLong SPANNER_MAX_SESSIONS =
      Measure.MeasureLong.create(
          "cloud.google.com/java/spanner/max_sessions", "Number of max sessions", COUNT);
}
