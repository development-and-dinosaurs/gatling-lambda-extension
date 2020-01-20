package io.toolebox.gatlinglambdaextension

import io.gatling.core.action.builder.ActionBuilder
import io.toolebox.gatlinglambdaextension.request.LambdaInvokeBuilder

object Predef {

  def lambda(function: String): LambdaInvokeBuilder =
    LambdaInvokeBuilder(function)

  implicit def invokeBuilderToActionBuilder(
      builder: LambdaInvokeBuilder
  ): ActionBuilder = builder.build

}
