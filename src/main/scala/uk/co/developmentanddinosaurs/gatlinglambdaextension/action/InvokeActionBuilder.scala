package uk.co.developmentanddinosaurs.gatlinglambdaextension.action

import io.gatling.core.action.Action
import io.gatling.core.action.builder.ActionBuilder
import io.gatling.core.structure.ScenarioContext
import io.gatling.core.util.NameGen
import uk.co.developmentanddinosaurs.gatlinglambdaextension.protocol.LambdaProtocol
import uk.co.developmentanddinosaurs.gatlinglambdaextension.request.LambdaAttributes

case class InvokeActionBuilder(attr: LambdaAttributes)
    extends ActionBuilder
    with NameGen {
  override def build(ctx: ScenarioContext, next: Action): Action = {
    val protocol = getProtocol(ctx)
    val client = protocol.lambdaClient
    new InvokeAction(client, ctx.coreComponents, next, genName(""), attr)
  }

  private def getProtocol(ctx: ScenarioContext) = {
    ctx.protocolComponentsRegistry
      .components(LambdaProtocol.lambdaProtocolKey)
      .lambdaProtocol
  }

}
