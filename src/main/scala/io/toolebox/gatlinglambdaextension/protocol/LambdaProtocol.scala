package io.toolebox.gatlinglambdaextension.protocol

import java.time.Duration

import io.gatling.core.protocol.{Protocol, ProtocolKey}

object LambdaProtocol {
  val lambdaProtocolKey: ProtocolKey[LambdaProtocol, LambdaComponents] =
    new LambdaProtocolKey
}

case class LambdaProtocol(
    awsAccessKeyId: Option[String],
    awsSecretAccessKey: Option[String],
    awsSessionToken: Option[String],
    timeout: Option[Duration],
    endpoint: Option[String],
    region: Option[String]
) extends Protocol {}
