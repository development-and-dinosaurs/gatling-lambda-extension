package uk.co.developmentanddinosaurs.gatlinglambdaextension.protocol

import io.gatling.core.protocol.{Protocol, ProtocolKey}
import software.amazon.awssdk.services.lambda.LambdaClient

object LambdaProtocol {
  val lambdaProtocolKey: ProtocolKey[LambdaProtocol, LambdaComponents] =
    new LambdaProtocolKey
}

case class LambdaProtocol(lambdaClient: LambdaClient) extends Protocol {}
