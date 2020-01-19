package io.toolebox.gatlinglambdaextension.action

import io.gatling.commons.stats.OK
import io.gatling.commons.util.Clock
import io.gatling.core.CoreComponents
import io.gatling.core.Predef.Session
import io.gatling.core.action.{Action, ExitableAction}
import io.gatling.core.stats.StatsEngine
import io.gatling.core.util.NameGen

class InvokeAction(coreComponents: CoreComponents, val next: Action)
    extends ExitableAction
    with NameGen {

  override def execute(session: Session): Unit = {
    val start = clock.nowMillis
    // Do something
    val end = clock.nowMillis
    statsEngine.logResponse(session, name, start, end, OK, None, None)
    next ! session.markAsSucceeded
  }

  override def name: String = genName("invoke")

  override def statsEngine: StatsEngine = coreComponents.statsEngine

  override def clock: Clock = coreComponents.clock

}
