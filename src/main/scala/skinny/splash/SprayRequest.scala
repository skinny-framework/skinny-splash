package skinny.splash

import akka.actor.ActorRef
import spray.http.Uri.Path
import spray.http.{ HttpEntity, HttpRequest }
import spray.routing.RequestContext

import scala.collection.concurrent

/**
 * Spray Request Wrapper.
 */
case class SprayRequest(
    context: RequestContext,
    queryMultiParams: Map[String, Seq[String]],
    formMultiParams: Map[String, Seq[String]]) {

  lazy val requestScope: concurrent.Map[String, Any] = new concurrent.TrieMap[String, Any]()

  lazy val request: HttpRequest = context.request

  lazy val responder: ActorRef = context.responder

  lazy val unmatchedPath: Path = context.unmatchedPath

  lazy val entity: HttpEntity = request.entity

  lazy val queryParams: Map[String, String] = queryMultiParams.flatMap {
    case (key, values) => values.headOption.map { firstValue => (key, firstValue) }
  }

  lazy val formParams: Map[String, String] = formMultiParams.flatMap {
    case (key, values) => values.headOption.map { firstValue => (key, firstValue) }
  }

  lazy val params: Map[String, String] = queryParams ++ formParams

  lazy val multiParams: Map[String, Seq[String]] = queryMultiParams ++ formMultiParams

}
