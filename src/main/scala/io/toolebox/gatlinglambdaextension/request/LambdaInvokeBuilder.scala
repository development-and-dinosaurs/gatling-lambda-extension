package io.toolebox.gatlinglambdaextension.request

import io.toolebox.gatlinglambdaextension.action.InvokeActionBuilder

case class LambdaInvokeBuilder(
    function: String,
    payload: Option[String] = None
) {
  def payload(payload: String): LambdaInvokeBuilder =
    copy(payload = Some(payload))

  def build: InvokeActionBuilder =
    InvokeActionBuilder(LambdaAttributes(function, payload))
}
