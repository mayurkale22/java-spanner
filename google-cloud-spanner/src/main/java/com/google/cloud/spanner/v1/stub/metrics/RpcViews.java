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

import com.google.common.collect.ImmutableSet;
import io.opencensus.stats.Stats;
import io.opencensus.stats.View;
import io.opencensus.stats.ViewManager;

public class RpcViews {

  static final ImmutableSet<View> SPANNER_CLIENT_VIEWS_SET =
      ImmutableSet.of(RpcViewConstants.SPANNER_SESSION_POOL_VIEW);

  /**
   * Registers all Spanner specific views.
   *
   * <p>It is recommended to call this method before doing any RPC call to avoid missing stats.
   */
  public static void registerSpannerClientViews() {
    ViewManager viewManager = Stats.getViewManager();
    for (View view : SPANNER_CLIENT_VIEWS_SET) {
      viewManager.registerView(view);
    }
  }
}
