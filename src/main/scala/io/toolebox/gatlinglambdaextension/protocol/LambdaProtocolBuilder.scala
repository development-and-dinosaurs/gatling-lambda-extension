package io.toolebox.gatlinglambdaextension.protocol

import software.amazon.awssdk.services.lambda.LambdaClient

case class LambdaProtocolBuilder(
    lambdaClient: LambdaClient = LambdaClient.create()
) {
  def build: LambdaProtocol = LambdaProtocol(lambdaClient)
}
