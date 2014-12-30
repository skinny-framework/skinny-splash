package skinny.splash.servlet

import akka.actor._
import skinny.splash.dispatcher.SprayDispatcher
import spray.servlet.WebBoot

/**
 * Spray app integration with Servlet.
 */
trait SprayServletBoot extends WebBoot {

  def dispatcher: SprayDispatcher

  def actorSystemName: String = "skinny-spray-application"

  override def system: ActorSystem = ActorSystem(actorSystemName)

  override def serviceActor: ActorRef = system.actorOf(Props(dispatcher))

}

object SprayServletBoot {

  def apply(app: SprayDispatcher): SprayServletBoot = {
    new SprayServletBoot {
      override def dispatcher = app
    }
  }

}