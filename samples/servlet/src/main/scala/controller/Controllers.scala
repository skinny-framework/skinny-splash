package controller

import skinny.ServletContext
import skinny.routing.Routes

object Controllers {

  def mount(ctx: ServletContext): Unit = {
    root.mount(ctx)
  }

  object root extends RootController with Routes {
    val indexUrl = get("/")(index).as('index)
  }
}
