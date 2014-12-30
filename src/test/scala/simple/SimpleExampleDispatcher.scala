package simple

import skinny.splash._

trait SimpleExampleDispatcher extends SprayDispatcher {

  val controller = new SimpleExampleController

  override def routes = Seq(
    getRoute("ok")(req => SprayResponse(body = "ok")),
    postRoute("echo")(implicit req => controller.echo)
  )

}
