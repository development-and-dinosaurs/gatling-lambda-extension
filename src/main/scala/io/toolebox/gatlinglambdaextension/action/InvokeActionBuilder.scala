package io.toolebox.gatlinglambdaextension.action

import io.gatling.core.action.Action
import io.gatling.core.action.builder.ActionBuilder
import io.gatling.core.structure.ScenarioContext

case class InvokeActionBuilder() extends ActionBuilder {
  override def build(ctx: ScenarioContext, next: Action): Action = {
    import ctx._
    new InvokeAction(coreComponents, next)
  }
}
