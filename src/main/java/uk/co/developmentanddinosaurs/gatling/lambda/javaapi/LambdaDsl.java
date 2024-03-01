package uk.co.developmentanddinosaurs.gatling.lambda.javaapi;

import io.gatling.core.Predef;
import io.gatling.javaapi.core.internal.Expressions;
import scala.Option;
import uk.co.developmentanddinosaurs.gatling.lambda.javaapi.action.InvokeActionBuilder;
import uk.co.developmentanddinosaurs.gatling.lambda.javaapi.protocol.LambdaProtocolBuilder;

public class LambdaDsl {

    public static final LambdaProtocolBuilder lambda =
            new LambdaProtocolBuilder(uk.co.developmentanddinosaurs.gatling.lambda.protocol.LambdaProtocolBuilder.apply(Predef.configuration()));

    private LambdaDsl(){}

    public static InvokeActionBuilder lambda(String functionName) {
        return new InvokeActionBuilder(uk.co.developmentanddinosaurs.gatling.lambda.action.InvokeActionBuilder.apply(Expressions.toStringExpression(functionName), Option.empty(), Option.empty()));
    }
}
