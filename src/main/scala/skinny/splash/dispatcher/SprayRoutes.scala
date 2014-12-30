package skinny.splash.dispatcher

import spray.routing.{ HttpService, Route }

trait SprayRoutes { self: HttpService =>

  def routes: Seq[Route]

  def composedRoutes: Route = {
    routes.size match {
      case 0 =>
        throw new IllegalStateException("No route found.")
      case 1 =>
        routes.head
      case _ =>
        routes.tail.foldLeft(routes.head) {
          case (all, route) =>
            all ~ route
        }
    }
  }

}
