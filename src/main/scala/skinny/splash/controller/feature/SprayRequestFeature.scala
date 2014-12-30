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

  // --------------------
  // RequestContext helper methods
  // --------------------

  def context(implicit currentRequest: SprayRequest): RequestContext = currentRequest.context

  def params(implicit currentRequest: SprayRequest): Map[String, String] = currentRequest.params

  def multiParams(implicit currentRequest: SprayRequest): Map[String, Seq[String]] = currentRequest.multiParams

  def request(implicit currentRequest: SprayRequest): HttpRequest = currentRequest.request

  def responder(implicit currentRequest: SprayRequest): ActorRef = currentRequest.responder

  def unmatchedPath(implicit currentRequest: SprayRequest): Path = currentRequest.unmatchedPath

}

