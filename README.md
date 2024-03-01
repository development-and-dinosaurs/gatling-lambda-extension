# Gatling Lambda Extension
![Maven metadata URL](https://img.shields.io/maven-metadata/v?label=latest%20release&logo=gatling&metadataUrl=https%3A%2F%2Frepo1.maven.org%2Fmaven2%2Fio%2Ftoolebox%2Fgatling-lambda-extension%2Fmaven-metadata.xml&style=for-the-badge)
[![Release Date](https://img.shields.io/github/release-date/development-and-dinosaurs/gatling-lambda-extension?logo=gatling&style=for-the-badge)](https://github.com/development-and-dinosaurs/gatling-lambda-extension/releases)  
[![Build Status](https://img.shields.io/github/actions/workflow/status/development-and-dinosaurs/gatling-lambda-extension/publish.yml?branch=master&style=for-the-badge&logo=github)](https://github.com/development-and-dinosaurs/gatling-lambda-extension/actions?query=workflow%3APublish)
[![MIT License](https://img.shields.io/github/license/development-and-dinosaurs/gatling-lambda-extension?style=for-the-badge&logo=pagekit)](https://github.com/development-and-dinosaurs/gatling-lambda-extension/blob/master/LICENSE)

## What is the Gatling Lambda Extension?
This library is a [Gatling Extension](https://gatling.io/docs/gatling/reference/current/extensions/) that adds support for invoking lambda functions directly using Gatling. 

## How does it work?
The extension extends Gatling by providing actions, protocols, and requests that allow invocations of lambda functions directly. It's not too much different from the way Gatling is set up to handle HTTP requests.

## How do I use it?
This part is pretty simple - you just need to create a lambda protocol using a lambda client, and then make lambda requests. 

A really simple example simulation using this is something like this:

<details>
<summary><strong>Scala API</strong></summary>

```scala
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
```
</details>

<details>
<summary><strong>Java API</strong></summary>

```java
import static io.gatling.javaapi.core.CoreDsl.*;
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
```
</details>

This is a simple example of invoking a lambda function once to see how long it takes to complete - which is good for checking the performance of asynchronous event-driven lambda functions. You can inject any number of users using the Gatling framework to test other things - such as the concurrency of the lambda functions and how well it scales. 

The `LambdaClient` is also under your control, so you can set it up with credentials or HTTP clients as you desire. 

## This is so cool, how do I contribute?
I know right? You should check out the [contribution guide](CONTRIBUTING.md).
