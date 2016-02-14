package skinny.splash.dispatcher

import skinny.splash.{ SprayAction, SprayResponse, SprayRequest }
import skinny.splash.json.SprayJsonFormats
import spray.http._
import spray.routing._

/**
 * A full-stack dispatcher base implementation for Spray HTTP server.
 */
trait SprayDispatcher
    extends HttpService
    with SprayRoutes
    with SprayJsonFormats {

  // TODO path params

  def defaultMediaType: MediaType = MediaTypes.`application/json`

  def respondWithDefaultMediaType: Directive0 = respondWithMediaType(defaultMediaType)

  // TODO: multipart form data

  def route(pathValue: String)(action: SprayAction): Route = {
    path(pathValue) { implicit ctx =>
      parameterMultiMap { multiParams =>
        entity(as[FormData]) { formData =>
          respondWithDefaultMediaType {
            implicit val req = createSprayRequest(ctx, multiParams, formData)
            val resp: SprayResponse = action.apply(req)
            complete(resp.status, resp.allHeaders, resp.body)
          }
        }
      }.apply(ctx)
    }
  }

  def getRoute(pathValue: String)(action: SprayAction): Route = {
    get(route(pathValue)(action))
  }

  def postRoute(pathValue: String)(action: SprayAction): Route = {
    post(route(pathValue)(action))
  }

  def putRoute(pathValue: String)(action: SprayAction): Route = {
    put(route(pathValue)(action))
  }

  def patchRoute(pathValue: String)(action: SprayAction): Route = {
    patch(route(pathValue)(action))
  }

  def deleteRoute(pathValue: String)(action: SprayAction): Route = {
    delete(route(pathValue)(action))
  }

  def headRoute(pathValue: String)(action: SprayAction): Route = {
    head(route(pathValue)(action))
  }

  def optionsRoute(pathValue: String)(action: SprayAction): Route = {
    options(route(pathValue)(action))
  }

  protected def createSprayRequest(
    ctx: RequestContext,
    multiParams: Map[String, List[String]],
    formData: FormData
  ): SprayRequest = {
    SprayRequest(
      context = ctx,
      queryMultiParams = multiParams.map { case (k, vs) => (k, vs.toSeq) },
      formMultiParams = formData.fields.groupBy(_._1).map { case (k, vs) => (k, vs.map(_._2)) }
    )
  }

}
