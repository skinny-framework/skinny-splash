package simple

import skinny.splash._
import skinny.validator._

class SimpleExampleController extends SprayController {

  def echoForm(req: SprayRequest) = validation(req,
    paramKey("str") is required & maxLength(10)
  )

  def echo(implicit req: SprayRequest) = {
    if (echoForm(req).validate()) {
      respondAs(toJSONString(Map("str" -> params.get("str"))))
    } else {
      respondAs(status = 400, body = toJSONString(keyAndErrorMessages))
    }
  }

}
