package io.toolebox.gatlinglambdaextension.action

import java.net.URI

import io.gatling.core.action.Action
import io.gatling.core.action.builder.ActionBuilder
import io.gatling.core.structure.ScenarioContext
import software.amazon.awssdk.auth.credentials.{
  AwsBasicCredentials,
  StaticCredentialsProvider
}
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.lambda.LambdaClient

case class InvokeActionBuilder() extends ActionBuilder {
  override def build(ctx: ScenarioContext, next: Action): Action = {
    import ctx._
    def client =
      LambdaClient
        .builder()
        .region(Region.of("eu-west-1"))
        .credentialsProvider(
          StaticCredentialsProvider
            .create(AwsBasicCredentials.create("test", "test"))
        )
        .endpointOverride(URI.create("http://localhost:4574"))
        .build()

    new InvokeAction(client, coreComponents, next)
  }
}
