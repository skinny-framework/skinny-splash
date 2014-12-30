package skinny.splash.controller

import skinny.splash.{ SprayResponse, SprayRequest }
import skinny.splash.controller.feature._
import skinny.util.JSONStringOps

/**
 * Spray Controller
 */
abstract class SprayController(override val currentRequest: SprayRequest)
    extends SprayRequestFeature
    with SprayResponseFeature
    with RequestScopeFeature
    with LocaleFeature
    with ValidationFeature
    with JSONStringOps {

  /**
   * Action execution. Concrete controller should implement this method.
   * @return response body as String value
   */
  def apply(): SprayResponse

}

