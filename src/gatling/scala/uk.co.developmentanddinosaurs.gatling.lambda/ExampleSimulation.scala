package uk.co.developmentanddinosaurs.gatling.lambda

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import software.amazon.awssdk.services.lambda.LambdaClient
import uk.co.developmentanddinosaurs.gatling.lambda.Predef._

class ExampleSimulation extends Simulation {

  setUp(
    scenario("My Lambda")
      .exec(
        lambda("my-lambda-function-name")
          .payload("{\"myKey\": \"myValue\"}")
      )
      .inject(
        atOnceUsers(1)
      )
  ).protocols(
    lambda.client(LambdaClient.create())
  )
}
