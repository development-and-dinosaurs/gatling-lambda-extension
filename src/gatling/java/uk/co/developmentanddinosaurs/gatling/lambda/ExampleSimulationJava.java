package uk.co.developmentanddinosaurs.gatling.lambda;

import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static uk.co.developmentanddinosaurs.gatling.lambda.javaapi.LambdaDsl.lambda;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import software.amazon.awssdk.services.lambda.LambdaClient;
import uk.co.developmentanddinosaurs.gatling.lambda.javaapi.protocol.LambdaProtocolBuilder;

public class ExampleSimulationJava extends Simulation {

  LambdaProtocolBuilder protocol = lambda.client(LambdaClient.create());
  ScenarioBuilder scenario =
      scenario("Example Scenario")
          .exec(lambda("my-lambda-function-name").payload("{\"myKey\": \"myValue\"}"));

  {
    setUp(scenario.injectOpen(atOnceUsers(1))).protocols(protocol);
  }
}
