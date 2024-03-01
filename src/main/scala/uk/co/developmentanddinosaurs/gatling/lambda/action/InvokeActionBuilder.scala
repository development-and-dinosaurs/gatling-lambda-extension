package uk.co.developmentanddinosaurs.gatling.lambda.action

import io.gatling.core.action.Action
import io.gatling.core.action.builder.ActionBuilder
import io.gatling.core.session.Expression
import io.gatling.core.structure.ScenarioContext
import io.gatling.core.util.NameGen
import uk.co.developmentanddinosaurs.gatling.lambda.protocol.LambdaProtocol
import uk.co.developmentanddinosaurs.gatling.lambda.request.LambdaAttributes

case class InvokeActionBuilder(
    functionName: Expression[String],
    requestName: Option[Expression[String]],
    payload: Option[Expression[String]]
) extends ActionBuilder
    with NameGen {

  override def build(ctx: ScenarioContext, next: Action): Action = {
    val protocol = getProtocol(ctx)
    val client = protocol.lambdaClient
    new InvokeAction(
      client,
      ctx.coreComponents,
      next,
      genName(""),
      LambdaAttributes(functionName, requestName, payload)
    )
  }

  def payload(payload: Expression[String]): InvokeActionBuilder =
    copy(payload = Some(payload))

  def requestName(requestName: Expression[String]): InvokeActionBuilder =
    copy(requestName = Some(requestName))

  private def getProtocol(ctx: ScenarioContext) = {
    ctx.protocolComponentsRegistry
      .components(LambdaProtocol.lambdaProtocolKey)
      .lambdaProtocol
  }

}
