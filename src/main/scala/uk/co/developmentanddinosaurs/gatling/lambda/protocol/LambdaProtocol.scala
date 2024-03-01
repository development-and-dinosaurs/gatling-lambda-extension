package uk.co.developmentanddinosaurs.gatling.lambda.protocol

import io.gatling.core.config.GatlingConfiguration
import io.gatling.core.protocol.{Protocol, ProtocolKey}
import software.amazon.awssdk.services.lambda.LambdaClient

object LambdaProtocol {

  val lambdaProtocolKey: ProtocolKey[LambdaProtocol, LambdaComponents] =
    LambdaProtocolKey()

  def apply(configuration: GatlingConfiguration): LambdaProtocol =
    new LambdaProtocol(lambdaClient = LambdaClient.create())
}

final case class LambdaProtocol(lambdaClient: LambdaClient) extends Protocol {
  type Components = LambdaComponents
}
