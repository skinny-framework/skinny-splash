package api

import skinny.splash.SprayRequest
import skinny.splash.controller.SprayController

trait ApiController extends SprayController {

  def index(implicit req: SprayRequest) = respondAs("ok")

}
