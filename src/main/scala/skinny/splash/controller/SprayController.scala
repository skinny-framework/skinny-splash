package skinny.splash.controller

import skinny.splash.controller.feature._
import skinny.util.JSONStringOps

/**
 * Spray Controller
 */
trait SprayController
  extends SprayRequestFeature
  with SprayResponseFeature
  with RequestScopeFeature
  with LocaleFeature
  with ValidationFeature
  with JSONStringOps

