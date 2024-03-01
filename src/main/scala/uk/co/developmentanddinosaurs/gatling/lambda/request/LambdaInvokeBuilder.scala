package uk.co.developmentanddinosaurs.gatling.lambda.request

import io.gatling.core.session.Expression
import uk.co.developmentanddinosaurs.gatling.lambda.action.InvokeActionBuilder

case class LambdaInvokeBuilder(
    function: Expression[String],
    requestName: Option[Expression[String]] = None,
    payload: Option[Expression[String]] = None
) {
  def payload(payload: Expression[String]): LambdaInvokeBuilder =
    copy(payload = Some(payload))

  def requestName(requestName: Expression[String]): LambdaInvokeBuilder =
    copy(requestName = Some(requestName))

  def build: InvokeActionBuilder =
    InvokeActionBuilder(LambdaAttributes(function, requestName, payload))
}