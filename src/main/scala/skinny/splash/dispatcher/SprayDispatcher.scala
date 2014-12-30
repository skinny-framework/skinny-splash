package skinny.splash.dispatcher

import skinny.splash.{ SprayResponse, SprayRequest }
import skinny.splash.controller.SprayController
import skinny.splash.json.SprayJsonFormats
import spray.http._
import spray.routing._

/**
 * A full-stack dispatcher base implementation for Spray HTTP server.
 */
trait SprayDispatcher
    extends HttpServiceActor
    with SprayRoutes
    with SprayJsonFormats {

  // TODO path params

  override def actorRefFactory = context

  override def receive: Receive = runRoute(composedRoutes)

  type ControllerFactory = (SprayRequest) => SprayController

  def defaultMediaType: MediaType = MediaTypes.`application/json`

  def respondWithDefaultMediaType: Directive0 = respondWithMediaType(defaultMediaType)

  // TODO: multipart form data

  def route(pathValue: String)(controllerFactory: ControllerFactory): Route = {
    path(pathValue) { ctx =>
      parameterMultiMap { multiParams =>
        entity(as[FormData]) { formData =>
          respondWithDefaultMediaType {
            val req: SprayRequest = buildRequestInRoute(ctx, multiParams, formData)
            val controller: SprayController = controllerFactory.apply(req)
            val resp: SprayResponse = controller.apply()
            complete(resp.status, resp.allHeaders, resp.body)
          }
        }
      }.apply(ctx)
    }
  }

  protected def buildRequestInRoute(
                              ctx: RequestContext,
                              multiParams: Map[String, List[String]],
                              formData: FormData) = {
    SprayRequest(
      context = ctx,
      queryMultiParams = multiParams.map { case (k, vs) => (k, vs.toSeq) },
      formMultiParams = formData.fields.groupBy(_._1).map { case (k, vs) => (k, vs.map(_._2)) }
    )
  }

  def getRoute(pathValue: String)(controllerFactory: ControllerFactory): Route = {
    get(route(pathValue)(controllerFactory))
  }

  def postRoute(pathValue: String)(controllerFactory: ControllerFactory): Route = {
    post(route(pathValue)(controllerFactory))
  }

  def putRoute(pathValue: String)(controllerFactory: ControllerFactory): Route = {
    put(route(pathValue)(controllerFactory))
  }

  def patchRoute(pathValue: String)(controllerFactory: ControllerFactory): Route = {
    patch(route(pathValue)(controllerFactory))
  }

  def deleteRoute(pathValue: String)(controllerFactory: ControllerFactory): Route = {
    delete(route(pathValue)(controllerFactory))
  }

  def headRoute(pathValue: String)(controllerFactory: ControllerFactory): Route = {
    head(route(pathValue)(controllerFactory))
  }

  def optionsRoute(pathValue: String)(controllerFactory: ControllerFactory): Route = {
    options(route(pathValue)(controllerFactory))
  }

}
