package skinny.splash.controller.feature

import skinny.splash.{ SprayRequest, SprayResponse }
import spray.http.{ HttpHeader, StatusCodes, StatusCode }

trait SprayResponseFeature {

  def respondAs(body: String, status: StatusCode = StatusCodes.OK, headers: Seq[HttpHeader] = Nil)(
    implicit currentRequest: SprayRequest): SprayResponse = {
    SprayResponse(status = status, headers = headers, body = body)
  }

}
