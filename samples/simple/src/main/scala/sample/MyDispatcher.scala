package sample

import skinny.splash._

trait MyDispatcher extends SprayDispatcher {

  val controller = new MyController

  override def routes = Seq(
    getRoute("")(implicit req => controller.index),
    postRoute("foo")(implicit req => controller.index)
  )

}
