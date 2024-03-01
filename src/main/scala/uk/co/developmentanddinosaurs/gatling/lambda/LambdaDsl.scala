package uk.co.developmentanddinosaurs.gatling.lambda

import io.gatling.core.config.GatlingConfiguration
import io.gatling.core.session.Expression
import uk.co.developmentanddinosaurs.gatling.lambda.action.InvokeActionBuilder
import uk.co.developmentanddinosaurs.gatling.lambda.protocol.{
  LambdaProtocol,
  LambdaProtocolBuilder
}

import scala.language.implicitConversions

class LambdaDsl {

  def lambda(function: Expression[String]): InvokeActionBuilder =
    InvokeActionBuilder(function, Option.empty, Option.empty)

  def lambda(implicit
      configuration: GatlingConfiguration
  ): LambdaProtocolBuilder = LambdaProtocolBuilder(configuration)

  implicit def protocolBuilderToProtocol(
      builder: LambdaProtocolBuilder
  ): LambdaProtocol = builder.build
}
