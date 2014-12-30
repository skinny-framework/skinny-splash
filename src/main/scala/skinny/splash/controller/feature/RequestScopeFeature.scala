package skinny.splash.controller.feature

import skinny.splash.controller.SprayController

import scala.collection.concurrent

trait RequestScopeFeature { self: SprayController =>

  def requestScope: concurrent.Map[String, Any] = currentRequest.requestScope

  def getFromRequestScope[A](key: String): Option[A] = {
    requestScope.get(key).flatMap { v =>
      try Option(v.asInstanceOf[A])
      catch {
        case e: ClassCastException =>
          None
      }
    }
  }

  def set(keyAndValue: (String, Any)): Unit = keyAndValue match {
    case (key, value) => currentRequest.requestScope.put(key, value)
  }

}
