package uk.co.developmentanddinosaurs.gatling.lambda.protocol

import io.gatling.core.protocol.ProtocolComponents
import io.gatling.core.session.Session

case class LambdaComponents(lambdaProtocol: LambdaProtocol)
    extends ProtocolComponents {
  override def onStart: Session => Session = Session.Identity

  override def onExit: Session => Unit = ProtocolComponents.NoopOnExit
}
