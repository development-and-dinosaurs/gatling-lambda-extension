package io.toolebox.gatlinglambdaextension.action

import java.net.URI

import io.gatling.core.action.Action
import io.gatling.core.action.builder.ActionBuilder
import io.gatling.core.structure.ScenarioContext
import io.toolebox.gatlinglambdaextension.protocol.LambdaProtocol
import software.amazon.awssdk.auth.credentials.{
  AwsBasicCredentials,
  StaticCredentialsProvider
}
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.lambda.LambdaClient

case class InvokeActionBuilder() extends ActionBuilder {
  override def build(ctx: ScenarioContext, next: Action): Action = {
    val protocol = ctx.protocolComponentsRegistry
      .components(LambdaProtocol.lambdaProtocolKey)
      .lambdaProtocol
    import protocol._
    def client =
      LambdaClient
        .builder()
        .region(Region.of(region))
        .credentialsProvider(
          StaticCredentialsProvider
            .create(
              AwsBasicCredentials.create(awsAccessKeyId, awsSecretAccessKey)
            )
        )
        .endpointOverride(URI.create(endpoint))
        .build()

    new InvokeAction(client, ctx.coreComponents, next)
  }
}
