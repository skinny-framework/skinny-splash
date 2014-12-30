package api

import skinny.splash.SprayDispatcher

trait ApiDispatcher extends SprayDispatcher {

  val controller = new ApiController {}

  override def routes = Seq(
    getRoute("ok")(implicit req => controller.index)
  )

}
