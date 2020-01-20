package io.toolebox.gatlinglambdaextension.action

import java.net.URI

import io.gatling.core.action.Action
import io.gatling.core.action.builder.ActionBuilder
import io.gatling.core.structure.ScenarioContext
import io.gatling.core.util.NameGen
import io.toolebox.gatlinglambdaextension.protocol.LambdaProtocol
import io.toolebox.gatlinglambdaextension.request.LambdaAttributes
import software.amazon.awssdk.auth.credentials.{
  AwsBasicCredentials,
  StaticCredentialsProvider
}
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.lambda.{
  LambdaClient,
  LambdaClientBuilder
}

case class InvokeActionBuilder(attr: LambdaAttributes)
    extends ActionBuilder
    with NameGen {
  override def build(ctx: ScenarioContext, next: Action): Action = {
    val protocol = getProtocol(ctx)
    val client = LambdaClient.builder()
    maybeSetCredentials(protocol, client)
    maybeSetRegion(protocol, client)
    maybeSetEndpoint(protocol, client)

    new InvokeAction(
      client.build(),
      ctx.coreComponents,
      next,
      genName(attr.functionName.toString()),
      attr
    )
  }

  private def getProtocol(ctx: ScenarioContext) = {
    ctx.protocolComponentsRegistry
      .components(LambdaProtocol.lambdaProtocolKey)
      .lambdaProtocol
  }

  private def maybeSetCredentials(
      protocol: LambdaProtocol,
      client: LambdaClientBuilder
  ) {
    val accessKey = protocol.awsAccessKeyId
    val secretKey = protocol.awsSecretAccessKey
    if (accessKey.isEmpty && secretKey.isEmpty) {
      // implicit return
    } else if (accessKey.isDefined && secretKey.isDefined) {
      client.credentialsProvider(
        StaticCredentialsProvider
          .create(AwsBasicCredentials.create(accessKey.get, secretKey.get))
      )
    } else {
      throw new RuntimeException(
        "Both awsAccessKeyId and awsSecretAccessKey must be defined, or neither."
      )
    }
  }

  private def maybeSetRegion(
      protocol: LambdaProtocol,
      client: LambdaClientBuilder
  ) {
    val region = protocol.region
    if (region.isDefined) {
      client.region(Region.of(region.get))
    }
  }

  private def maybeSetEndpoint(
      protocol: LambdaProtocol,
      client: LambdaClientBuilder
  ) {
    val endpoint = protocol.endpoint
    if (endpoint.isDefined) {
      client.endpointOverride(URI.create(endpoint.get))
    }
  }
}
