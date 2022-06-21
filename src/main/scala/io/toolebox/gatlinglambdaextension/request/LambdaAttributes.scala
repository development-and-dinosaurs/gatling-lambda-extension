package io.toolebox.gatlinglambdaextension.request

import io.gatling.core.session.Expression

case class LambdaAttributes(
    functionName: Expression[String],
    requestName: Option[Expression[String]],
    payload: Option[Expression[String]]
)
