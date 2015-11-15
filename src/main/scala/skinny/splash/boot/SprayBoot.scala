package skinny.splash.boot

import akka.actor.{ ActorRef, ActorSystem, Props }
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import skinny.splash.dispatcher.SprayDispatcherActor
import spray.can.Http

import scala.concurrent.duration._

/**
 * Standalone Spray Boot
 */
trait SprayBoot {

  /**
   * Props of SprayDispatcherActor.
   */
  def dispatcherActorProps: Props

  def actorSystemName: String = "skinny-spray-application"

  lazy implicit val system: ActorSystem = {
    val system = ActorSystem(actorSystemName)
    system.registerOnTermination {
      system.terminate()
    }
    system
  }

  def toProps(clazz: Class[_ <: SprayDispatcherActor]): Props = akka.actor.Props(clazz)

  def actorOf(props: Props): ActorRef = system.actorOf(props)

  def interface: String = "0.0.0.0"

  def port: Int = 8080

  def timeoutSeconds: Int = 3

  def run(): Unit = {
    implicit val timeout = Timeout(timeoutSeconds.seconds)

    val httpBind = Http.Bind(
      listener = actorOf(dispatcherActorProps),
      interface = interface,
      port = port
    )
    IO(Http) ? httpBind
  }

}
