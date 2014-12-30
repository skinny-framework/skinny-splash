package skinny.splash.controller.feature

import skinny.splash.SprayRequest
import skinny.splash.controller.SprayController

import scala.collection.concurrent

trait RequestScopeFeature { self: SprayController =>

  def requestScope(implicit currentRequest: SprayRequest): concurrent.Map[String, Any] = currentRequest.requestScope

  def getFromRequestScope[A](key: String)(implicit currentRequest: SprayRequest): Option[A] = {
    requestScope.get(key).flatMap { v =>
      try Option(v.asInstanceOf[A])
      catch {
        case e: ClassCastException =>
          None
      }
    }
  }

  def set(keyAndValue: (String, Any))(implicit currentRequest: SprayRequest): Unit = keyAndValue match {
    case (key, value) => currentRequest.requestScope.put(key, value)
  }

}
