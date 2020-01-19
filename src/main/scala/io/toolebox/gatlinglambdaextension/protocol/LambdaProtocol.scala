package io.toolebox.gatlinglambdaextension.protocol

import io.gatling.core.protocol.{Protocol, ProtocolKey}

object LambdaProtocol {
  val lambdaProtocolKey: ProtocolKey[LambdaProtocol, LambdaComponents] =
    new LambdaProtocolKey
}

case class LambdaProtocol(
    awsAccessKeyId: String,
    awsSecretAccessKey: String,
    endpoint: String,
    region: String
) extends Protocol {}
