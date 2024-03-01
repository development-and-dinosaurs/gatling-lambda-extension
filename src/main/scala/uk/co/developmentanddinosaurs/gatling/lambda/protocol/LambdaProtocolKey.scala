package uk.co.developmentanddinosaurs.gatling.lambda.protocol

import io.gatling.core.CoreComponents
import io.gatling.core.config.GatlingConfiguration
import io.gatling.core.protocol.{Protocol, ProtocolKey}

case class LambdaProtocolKey()
    extends ProtocolKey[LambdaProtocol, LambdaComponents] {
  override def protocolClass: Class[Protocol] =
    classOf[LambdaProtocol].asInstanceOf[Class[Protocol]]

  override def defaultProtocolValue(
      configuration: GatlingConfiguration
  ): LambdaProtocol = throw new IllegalStateException(
    "Can't provide a default value for LambdaProtocol"
  )

  override def newComponents(
      coreComponents: CoreComponents
  ): LambdaProtocol => LambdaComponents =
    lambdaProtocol => LambdaComponents(lambdaProtocol)
}
