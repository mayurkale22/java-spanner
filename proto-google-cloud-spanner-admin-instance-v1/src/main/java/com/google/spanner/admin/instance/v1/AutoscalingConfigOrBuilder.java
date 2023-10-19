/*
 * Copyright 2023 Google LLC
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
// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/spanner/admin/instance/v1/spanner_instance_admin.proto

package com.google.spanner.admin.instance.v1;

public interface AutoscalingConfigOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.spanner.admin.instance.v1.AutoscalingConfig)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * Required. Autoscaling limits for an instance.
   * </pre>
   *
   * <code>
   * .google.spanner.admin.instance.v1.AutoscalingConfig.AutoscalingLimits autoscaling_limits = 1 [(.google.api.field_behavior) = REQUIRED];
   * </code>
   *
   * @return Whether the autoscalingLimits field is set.
   */
  boolean hasAutoscalingLimits();
  /**
   *
   *
   * <pre>
   * Required. Autoscaling limits for an instance.
   * </pre>
   *
   * <code>
   * .google.spanner.admin.instance.v1.AutoscalingConfig.AutoscalingLimits autoscaling_limits = 1 [(.google.api.field_behavior) = REQUIRED];
   * </code>
   *
   * @return The autoscalingLimits.
   */
  com.google.spanner.admin.instance.v1.AutoscalingConfig.AutoscalingLimits getAutoscalingLimits();
  /**
   *
   *
   * <pre>
   * Required. Autoscaling limits for an instance.
   * </pre>
   *
   * <code>
   * .google.spanner.admin.instance.v1.AutoscalingConfig.AutoscalingLimits autoscaling_limits = 1 [(.google.api.field_behavior) = REQUIRED];
   * </code>
   */
  com.google.spanner.admin.instance.v1.AutoscalingConfig.AutoscalingLimitsOrBuilder
      getAutoscalingLimitsOrBuilder();

  /**
   *
   *
   * <pre>
   * Required. The autoscaling targets for an instance.
   * </pre>
   *
   * <code>
   * .google.spanner.admin.instance.v1.AutoscalingConfig.AutoscalingTargets autoscaling_targets = 2 [(.google.api.field_behavior) = REQUIRED];
   * </code>
   *
   * @return Whether the autoscalingTargets field is set.
   */
  boolean hasAutoscalingTargets();
  /**
   *
   *
   * <pre>
   * Required. The autoscaling targets for an instance.
   * </pre>
   *
   * <code>
   * .google.spanner.admin.instance.v1.AutoscalingConfig.AutoscalingTargets autoscaling_targets = 2 [(.google.api.field_behavior) = REQUIRED];
   * </code>
   *
   * @return The autoscalingTargets.
   */
  com.google.spanner.admin.instance.v1.AutoscalingConfig.AutoscalingTargets getAutoscalingTargets();
  /**
   *
   *
   * <pre>
   * Required. The autoscaling targets for an instance.
   * </pre>
   *
   * <code>
   * .google.spanner.admin.instance.v1.AutoscalingConfig.AutoscalingTargets autoscaling_targets = 2 [(.google.api.field_behavior) = REQUIRED];
   * </code>
   */
  com.google.spanner.admin.instance.v1.AutoscalingConfig.AutoscalingTargetsOrBuilder
      getAutoscalingTargetsOrBuilder();
}