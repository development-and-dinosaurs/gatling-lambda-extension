package uk.co.developmentanddinosaurs.gatling.lambda.javaapi.action;

import io.gatling.javaapi.core.ActionBuilder;
import io.gatling.javaapi.core.internal.Expressions;

public class InvokeActionBuilder implements ActionBuilder {

  private final uk.co.developmentanddinosaurs.gatling.lambda.action.InvokeActionBuilder wrapped;

  public InvokeActionBuilder(
      uk.co.developmentanddinosaurs.gatling.lambda.action.InvokeActionBuilder wrapped) {
    this.wrapped = wrapped;
  }

  public InvokeActionBuilder payload(String payload) {
    return new InvokeActionBuilder(wrapped.payload(Expressions.toStringExpression(payload)));
  }

  public InvokeActionBuilder requestName(String requestName) {
    return new InvokeActionBuilder(
        wrapped.requestName(Expressions.toStringExpression(requestName)));
  }

  @Override
  public io.gatling.core.action.builder.ActionBuilder asScala() {
    return wrapped;
  }
}
