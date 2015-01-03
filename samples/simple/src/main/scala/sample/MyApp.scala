package sample

import skinny.splash._
import skinny.splash.dispatcher.SprayDispatcherActor

object MyApp extends SprayApplication {

  class MyDispatcherActor extends SprayDispatcherActor with MyDispatcher

  override def dispatcherActorProps = toProps(classOf[MyDispatcherActor])

}
