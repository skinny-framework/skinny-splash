package skinny.splash.controller.feature

import skinny.splash.SprayResponse
import spray.http.{ HttpHeader, StatusCodes, StatusCode }

trait SprayResponseFeature {

  def respondAs(body: String, status: StatusCode = StatusCodes.Created, headers: Seq[HttpHeader] = Nil): SprayResponse = {
    SprayResponse(status = status, headers = headers, body = body)
  }

}
