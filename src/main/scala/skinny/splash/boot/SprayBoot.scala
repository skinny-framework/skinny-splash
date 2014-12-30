package skinny.splash.boot

import akka.actor.{ ActorSystem, Props }
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import skinny.splash.dispatcher.SprayDispatcher
import spray.can.Http

import scala.concurrent.duration._

trait SprayBoot[Dispatcher <: SprayDispatcher] {

  def dispatcherClass: Class[Dispatcher]

  def actorSystemName: String = "skinny-spray-application"

  def interface: String = "0.0.0.0"

  def port: Int = 8080

  def timeoutSeconds: Int = 3

  def run(): Unit = {
    implicit val system: ActorSystem = ActorSystem(actorSystemName)
    implicit val timeout = Timeout(timeoutSeconds.seconds)

    val httpBind = Http.Bind(
      listener = system.actorOf(Props(dispatcherClass), dispatcherClass.getCanonicalName),
      interface = interface,
      port = port
    )
    IO(Http) ? httpBind
  }

}
