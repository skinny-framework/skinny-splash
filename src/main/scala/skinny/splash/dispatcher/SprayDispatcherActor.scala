package skinny.splash.dispatcher

import spray.routing.HttpServiceActor

abstract class SprayDispatcherActor extends HttpServiceActor with SprayDispatcher {

  override def receive: Receive = runRoute(composedRoutes)

}
