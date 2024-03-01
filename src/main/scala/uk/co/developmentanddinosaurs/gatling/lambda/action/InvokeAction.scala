package uk.co.developmentanddinosaurs.gatling.lambda.action

import io.gatling.commons.stats.{KO, OK}
import io.gatling.commons.util.Clock
import io.gatling.core.CoreComponents
import io.gatling.core.Predef.Session
import io.gatling.core.action.{Action, ExitableAction}
import io.gatling.core.stats.StatsEngine
import software.amazon.awssdk.core.SdkBytes
import software.amazon.awssdk.services.lambda.LambdaClient
import software.amazon.awssdk.services.lambda.model.{
  InvokeRequest,
  InvokeResponse
}
import uk.co.developmentanddinosaurs.gatling.lambda.request.LambdaAttributes

class InvokeAction(
    lambdaClient: LambdaClient,
    coreComponents: CoreComponents,
    val next: Action,
    val id: String,
    val attr: LambdaAttributes
) extends ExitableAction {

  override def name: String = "InvokeAction"

  override def execute(session: Session): Unit = {
    var requestName: String = ""
    if (attr.requestName.isDefined) {
      attr.requestName.map(
        name => name(session).map(name => requestName = name + id)
      )
    } else {
      requestName = "invoke" + id
    }
    val request = InvokeRequest.builder()
    attr.functionName(session).map { function =>
      request.functionName(function)
    }
    attr.payload.map(
      payload =>
        payload(session)
          .map(payload => request.payload(SdkBytes.fromUtf8String(payload)))
    )

    if (attr.payload.isDefined) {
      attr.payload
        .get(session)
        .map(payload => request.payload(SdkBytes.fromUtf8String(payload)))
    }
    var maybeResponse: Option[InvokeResponse] = None
    var maybeThrowable: Option[Throwable] = None
    val start = clock.nowMillis
    try {
      maybeResponse = Some(lambdaClient.invoke(request.build()))
    } catch {
      case t: Throwable => maybeThrowable = Some(t)
    }

    val end = clock.nowMillis
    if (maybeResponse.isDefined) {
      val response = maybeResponse.get
      if (isSuccessful(response)) {
        logSuccess(requestName, session, start, end)
      } else {
        logFailure(
          requestName,
          session,
          start,
          end,
          s"Status ${response.statusCode()}"
        )
      }
    } else {
      logFailure(
        requestName,
        session,
        start,
        end,
        maybeThrowable.get.getMessage
      )
    }

  }

  private def isSuccessful(response: InvokeResponse) = {
    val successCodes = 200 to 299
    successCodes contains response.statusCode()
  }

  override def statsEngine: StatsEngine = coreComponents.statsEngine

  override def clock: Clock = coreComponents.clock

  private def logSuccess(
      requestName: String,
      session: Session,
      start: Long,
      end: Long
  ): Unit = {
    statsEngine.logResponse(
      session.scenario,
      session.groups,
      requestName,
      start,
      end,
      OK,
      None,
      None
    )
    next ! session.markAsSucceeded
  }

  private def logFailure(
      requestName: String,
      session: Session,
      start: Long,
      end: Long,
      message: String
  ): Unit = {
    statsEngine.logResponse(
      session.scenario,
      session.groups,
      requestName,
      start,
      end,
      KO,
      None,
      Some(message)
    )
    next ! session.markAsFailed
  }

}
