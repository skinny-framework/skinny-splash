package sample

import skinny.splash._
import skinny.validator._

class MyController extends SprayController {

  def form(req: SprayRequest) = validation(req,
    paramKey("name") is required
  )

  def index(implicit req: SprayRequest) = {
    if (form(req).validate()) {
      val body = toJSONString(Map("message" -> s"Hello, ${params.getOrElse("name", "Anonymous")}"))
      respondAs(body)
    } else {
      respondAs(status = 400, body = toJSONString(keyAndErrorMessages))
    }
  }

}