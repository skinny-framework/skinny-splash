package simple

import org.scalatest._
import spray.http.{ HttpEntity, MediaTypes, HttpResponse, StatusCodes }
import spray.routing.HttpService
import spray.testkit.ScalatestRouteTest

class SimpleExampleSpec
    extends FlatSpec
    with Matchers
    with HttpService
    with SimpleExampleDispatcher
    with ScalatestRouteTest {

  def actorRefFactory = system

  def assertDefaultHeaders(response: HttpResponse) = {
    response.headers.find(_.name == "X-XSS-Protection").map(_.value) should equal(Some("1; mode=block"))
    response.headers.find(_.name == "X-Frame-Options").map(_.value) should equal(Some("sameorigin"))
    response.headers.find(_.name == "X-Content-Type-Options").map(_.value) should equal(Some("nosniff"))
  }

  it should "returns 'ok'" in {
    Get("/ok") ~> composedRoutes ~> check {
      status should equal(StatusCodes.OK)
      assertDefaultHeaders(response)
      responseAs[String] should equal("ok")
    }
  }

  it should "accept echo request" in {
    Post("/echo", HttpEntity(MediaTypes.`application/x-www-form-urlencoded`, "str=foo")) ~> composedRoutes ~> check {
      status should equal(StatusCodes.OK)
      assertDefaultHeaders(response)
      responseAs[String] should equal("""{"str":"foo"}""")
    }
  }

  it should "reject echo request without str param" in {
    Post("/echo") ~> composedRoutes ~> check {
      status should equal(StatusCodes.BadRequest)
      assertDefaultHeaders(response)
      responseAs[String] should equal("""{"str":["str is required"]}""")
    }
  }

  it should "reject too long str param" in {
    Post("/echo", HttpEntity(MediaTypes.`application/x-www-form-urlencoded`, "str=1234567890123")) ~> composedRoutes ~> check {
      status should equal(StatusCodes.BadRequest)
      assertDefaultHeaders(response)
      responseAs[String] should equal("""{"str":["str length must be less than or equal to 10"]}""")
    }
  }

}
