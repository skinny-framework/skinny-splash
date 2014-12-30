package skinny.splash.controller.feature

import akka.actor.ActorRef
import skinny.splash.SprayRequest
import spray.http.HttpRequest
import spray.http.Uri.Path
import spray.routing.RequestContext

/**
 * SprayRequest Core APIs.
 */
trait SprayRequestFeature {

  def currentRequest: SprayRequest

  // --------------------
  // RequestContext helper methods
  // --------------------

  def context: RequestContext = currentRequest.context

  def params: Map[String, String] = currentRequest.params

  def multiParams: Map[String, Seq[String]] = currentRequest.multiParams

  def request: HttpRequest = currentRequest.request

  def responder: ActorRef = currentRequest.responder

  def unmatchedPath: Path = currentRequest.unmatchedPath

}

