package skinny.splash.controller.feature

import java.util.Locale

import skinny.splash.SprayRequest

trait LocaleFeature {

  def currentLocale(implicit currentRequest: SprayRequest): Option[Locale] = None

}
