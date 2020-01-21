package io.toolebox.gatlinglambdaextension.protocol

import io.gatling.core.CoreComponents
import io.gatling.core.protocol.ProtocolComponents
import io.gatling.core.session.Session

case class LambdaComponents(
    coreComponents: CoreComponents,
    lambdaProtocol: LambdaProtocol
) extends ProtocolComponents {
  override def onStart: Session => Session = ProtocolComponents.NoopOnStart

  override def onExit: Session => Unit = ProtocolComponents.NoopOnExit
}
