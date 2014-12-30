package skinny.splash

import java.util.Locale
import spray.http.{ Rendering, HttpHeader, StatusCode, StatusCodes }

case class SprayResponse(
    status: StatusCode = StatusCodes.OK,
    headers: Seq[HttpHeader] = Nil,
    body: String = null) {

  private def buildResponseHeader(name: String, value: String): HttpHeader = {
    val _name = name
    val _value = value
    new HttpHeader {
      override def name = _name
      override def value = _value
      override def lowercaseName: String = value.toLowerCase(Locale.ENGLISH)
      override def render[R <: Rendering](r: R): r.type = r ~~ name ~~ ": " ~~ value
    }
  }

  def defaultHeaders: Seq[HttpHeader] = Seq(
    buildResponseHeader("X-XSS-Protection", "1; mode=block"),
    buildResponseHeader("X-Frame-Options", "sameorigin"),
    buildResponseHeader("X-Content-Type-Options", "nosniff")
  )

  def allHeaders = defaultHeaders ++ headers

}
