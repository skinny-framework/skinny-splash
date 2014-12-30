package skinny.splash.boot

import akka.actor._
import skinny.splash.dispatcher.SprayDispatcherActor
import spray.servlet.WebBoot

/**
 * Spray app integration with Servlet.
 */
trait SprayServletBoot extends WebBoot {

  /**
   * Props of SprayDispatcherActor.
   */
  def dispatcherProps: Props

  def actorSystemName: String = "skinny-spray-application"

  lazy implicit val system: ActorSystem = {
    val system = ActorSystem(actorSystemName)
    system.registerOnTermination {
      system.shutdown
    }
    system
  }

  def toProps(clazz: Class[_ <: SprayDispatcherActor]): Props = akka.actor.Props(clazz)

  def actorOf(props: Props): ActorRef = system.actorOf(props)

  override def serviceActor: ActorRef = actorOf(dispatcherProps)

}
