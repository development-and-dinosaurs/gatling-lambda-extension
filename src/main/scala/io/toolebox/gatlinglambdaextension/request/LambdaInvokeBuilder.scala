package io.toolebox.gatlinglambdaextension.request

import io.gatling.core.session.Expression
import io.toolebox.gatlinglambdaextension.action.InvokeActionBuilder

case class LambdaInvokeBuilder(
    function: Expression[String],
    payload: Option[Expression[String]] = None
) {
  def payload(payload: Expression[String]): LambdaInvokeBuilder =
    copy(payload = Some(payload))

  def build: InvokeActionBuilder =
    InvokeActionBuilder(LambdaAttributes(function, payload))
}
