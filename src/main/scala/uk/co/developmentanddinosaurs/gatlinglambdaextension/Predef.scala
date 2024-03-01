package uk.co.developmentanddinosaurs.gatlinglambdaextension

import scala.language.implicitConversions
import io.gatling.core.action.builder.ActionBuilder
import io.gatling.core.config.GatlingConfiguration
import io.gatling.core.session.Expression
import uk.co.developmentanddinosaurs.gatlinglambdaextension.protocol.{
  LambdaProtocol,
  LambdaProtocolBuilder
}
import uk.co.developmentanddinosaurs.gatlinglambdaextension.request.LambdaInvokeBuilder

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