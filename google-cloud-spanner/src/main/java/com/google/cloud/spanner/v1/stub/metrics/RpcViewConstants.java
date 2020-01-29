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

import static com.google.cloud.spanner.v1.stub.metrics.RpcMeasureConstants.SESSION_TYPE;
import static com.google.cloud.spanner.v1.stub.metrics.RpcMeasureConstants.SPANNER_ACTIVE_SESSIONS;
import static com.google.cloud.spanner.v1.stub.metrics.RpcMeasureConstants.SPANNER_MAX_SESSIONS;

import com.google.common.collect.ImmutableList;
import io.opencensus.stats.Aggregation;
import io.opencensus.stats.Aggregation.LastValue;
import io.opencensus.stats.View;

public class RpcViewConstants {
  // Aggregations
  private static final Aggregation LAST_VALUE = LastValue.create();

  // Views
  public static final View SPANNER_ACTIVE_SESSIONS_VIEW =
      View.create(
          View.Name.create("cloud.google.com/java/spanner/active_sessions"),
          "Number of active sessions",
          SPANNER_ACTIVE_SESSIONS,
          LAST_VALUE,
          ImmutableList.of((SESSION_TYPE)));

  public static final View SPANNER_MAX_SESSIONS_VIEW =
      View.create(
          View.Name.create("cloud.google.com/java/spanner/max_sessions"),
          "Number of max sessions",
          SPANNER_MAX_SESSIONS,
          LAST_VALUE,
          ImmutableList.of((SESSION_TYPE)));
}
