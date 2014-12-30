package sample

import org.scalatest._
import spray.http.StatusCodes
import spray.routing.HttpService
import spray.testkit.ScalatestRouteTest

class MyDispatcherSpec
    extends FlatSpec
    with Matchers
    with HttpService
    with MyDispatcher
    with ScalatestRouteTest {

  def actorRefFactory = system

  it should "returns 'hello world' message" in {
    Get("/?name=Alice") ~> composedRoutes ~> check {
      status should equal(StatusCodes.OK)
      responseAs[String] should equal("""{"message":"Hello, Alice"}""")
    }
  }

  it should "reject requests without name param" in {
    Get("/") ~> composedRoutes ~> check {
      status should equal(StatusCodes.BadRequest)
      responseAs[String] should equal("""{"name":["name is required"]}""")
    }
  }

}
