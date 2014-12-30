package sample

import skinny.splash._

class MyDispatcher extends SprayDispatcher {

  override def routes = Seq(
    getRoute("")(req => new MyController(req)),
    postRoute("foo")(req => new MyController(req))
  )

}
