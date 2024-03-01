package uk.co.developmentanddinosaurs.gatling.lambda.protocol

import com.softwaremill.quicklens.ModifyPimp
import io.gatling.core.config.GatlingConfiguration
import software.amazon.awssdk.services.lambda.LambdaClient

import scala.language.implicitConversions

object LambdaProtocolBuilder {

  implicit def toLambdaProtocol(
      builder: LambdaProtocolBuilder
  ): LambdaProtocol = builder.build

  def apply(configuration: GatlingConfiguration): LambdaProtocolBuilder =
    LambdaProtocolBuilder(LambdaProtocol(configuration))
}

final case class LambdaProtocolBuilder(protocol: LambdaProtocol) {
  def client(client: LambdaClient): LambdaProtocolBuilder =
    this.modify(_.protocol.lambdaClient).setTo(client)

  def build: LambdaProtocol = protocol
}
