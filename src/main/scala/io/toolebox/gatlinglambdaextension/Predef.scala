package io.toolebox.gatlinglambdaextension

import io.gatling.core.action.builder.ActionBuilder
import io.gatling.core.config.GatlingConfiguration
import io.gatling.core.session.Expression
import io.toolebox.gatlinglambdaextension.protocol.{
  LambdaProtocol,
  LambdaProtocolBuilder
}
import io.toolebox.gatlinglambdaextension.request.LambdaInvokeBuilder

object Predef {

  def lambda(function: Expression[String]): LambdaInvokeBuilder =
    LambdaInvokeBuilder(function)
  def lambda(
      implicit configuration: GatlingConfiguration
  ): LambdaProtocolBuilder = LambdaProtocolBuilder()

  implicit def invokeBuilderToActionBuilder(
      builder: LambdaInvokeBuilder
  ): ActionBuilder = builder.build

  implicit def protocolBuilderToProtocol(
      builder: LambdaProtocolBuilder
  ): LambdaProtocol = builder.build
}
