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

import static com.google.cloud.spanner.v1.stub.metrics.RpcMeasureConstants.SPANNER_SESSION;
import static com.google.cloud.spanner.v1.stub.metrics.RpcMeasureConstants.NUM_SESSIONS_BEING_CREATED;
import static com.google.cloud.spanner.v1.stub.metrics.RpcMeasureConstants.NUM_SESSIONS_BEING_PREPARED;
import static com.google.cloud.spanner.v1.stub.metrics.RpcMeasureConstants.NUM_SESSIONS_IN_USE;
import static com.google.cloud.spanner.v1.stub.metrics.RpcMeasureConstants.MAX_SESSIONS_IN_USE;

import io.opencensus.stats.Aggregation;
import io.opencensus.stats.Aggregation.Count;
import io.opencensus.stats.View;
import java.util.Arrays;

public class RpcViewConstants {
  // Aggregations
  private static final Aggregation COUNT = Count.create();

  // Views
  public static final View SPANNER_SESSION_POOL_VIEW = View.create(
    View.Name.create("cloud.google.com/java/spanner/session_pool"),
    "Number of pool of the sessions",
    SPANNER_SESSION,
    COUNT,
    Arrays.asList(NUM_SESSIONS_BEING_CREATED, NUM_SESSIONS_BEING_PREPARED, NUM_SESSIONS_IN_USE, MAX_SESSIONS_IN_USE)
  );

}
