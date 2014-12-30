package sample

import skinny.splash._

object MyApp extends SprayApplication[MyDispatcher] {

  override def dispatcherClass = classOf[MyDispatcher]

}
