package uk.co.developmentanddinosaurs.gatlinglambdaextension.protocol

import software.amazon.awssdk.services.lambda.LambdaClient

case class LambdaProtocolBuilder(
    lambdaClient: LambdaClient = LambdaClient.create()
) {
  def client(client: LambdaClient): LambdaProtocolBuilder =
    copy(lambdaClient = client)
  def build: LambdaProtocol = LambdaProtocol(lambdaClient)
}
