package sample

import skinny.splash._
import skinny.validator._

class MyController(req: SprayRequest) extends SprayController(req) {

  def form = validation(params,
    paramKey("name") is required
  )

  override def apply = {
    if (form.validate()) {
      val body = toJSONString(Map("message" -> s"Hello, ${params.getOrElse("name", "Anonymous")}"))
      respondAs(body)
    } else {
      respondAs(status = 400, body = toJSONString(keyAndErrorMessages))
    }
  }

}