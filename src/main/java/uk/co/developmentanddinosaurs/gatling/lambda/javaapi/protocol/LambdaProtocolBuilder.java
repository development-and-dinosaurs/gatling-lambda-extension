package uk.co.developmentanddinosaurs.gatling.lambda.javaapi.protocol;

import io.gatling.core.protocol.Protocol;
import io.gatling.javaapi.core.ProtocolBuilder;
import software.amazon.awssdk.services.lambda.LambdaClient;

public class LambdaProtocolBuilder implements ProtocolBuilder {

  private final uk.co.developmentanddinosaurs.gatling.lambda.protocol.LambdaProtocolBuilder wrapped;

  public LambdaProtocolBuilder(
      uk.co.developmentanddinosaurs.gatling.lambda.protocol.LambdaProtocolBuilder wrapped) {
    this.wrapped = wrapped;
  }

  public LambdaProtocolBuilder client(LambdaClient client) {
    return new LambdaProtocolBuilder(wrapped.client(client));
  }

  @Override
  public Protocol protocol() {
    return wrapped.protocol();
  }
}
