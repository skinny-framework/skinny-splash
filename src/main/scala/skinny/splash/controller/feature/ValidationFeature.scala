package skinny.splash.controller.feature

import java.util.Locale

import skinny.I18n
import skinny.splash.SprayRequest
import skinny.splash.controller.KeyAndErrorMessages
import skinny.util.StringUtil._
import skinny.validator._

trait ValidationFeature { self: RequestScopeFeature with LocaleFeature =>

  private[this] val ERROR_MESSAGES = "validation-error-messages"

  private[this] val KEY_AND_ERROR_MESSAGES = "validation-key-and-error-messages"

  def validation(currentRequest: SprayRequest, validations: NewValidation*): MapValidator = {
    validationWithPrefix(currentRequest, null, validations: _*)
  }

  def validationWithPrefix(currentRequest: SprayRequest, prefix: String, validations: NewValidation*): MapValidator = {
    implicit val _req: SprayRequest = currentRequest
    val params = currentRequest.params
    val locale = currentLocale.orNull[Locale]
    val validator = new MapValidator(params, Validations(params, validations))

    validator
      .success { _ => }
      .failure { (inputs, errors) =>
        val skinnyValidationMessages = Messages.loadFromConfig(locale = Option(locale))
        val i18n = I18n(locale)
        def withPrefix(key: String): String = if (prefix != null) s"${prefix}.${key}" else key

        // errorMessages
        val errorMessages: Seq[String] = validations.map(_.paramDef.key).flatMap { key =>
          errors.get(key).map { error =>
            skinnyValidationMessages.get(
              key = error.name,
              params = i18n.get(withPrefix(toCamelCase(key))).getOrElse(key) :: error.messageParams.map {
                case I18nKeyParam(key) => i18n.getOrKey(key)
                case value => value
              }.toList
            ).getOrElse(error.name)
          }
        }
        set(ERROR_MESSAGES -> errorMessages)

        // keyAndErrorMessages
        val keyAndErrorMessages: KeyAndErrorMessages = KeyAndErrorMessages(validations.map(_.paramDef.key).map { key =>
          key -> errors.get(key).map { error =>
            skinnyValidationMessages.get(
              key = error.name,
              params = i18n.get(withPrefix(toCamelCase(key))).getOrElse(key) :: error.messageParams.map {
                case I18nKeyParam(key) => i18n.getOrKey(key)
                case value => value
              }.toList
            ).getOrElse(error.name)
          }
        }.toMap)
        set(KEY_AND_ERROR_MESSAGES, keyAndErrorMessages)
      }.apply()

    validator
  }

  def errorMessages(implicit currentRequest: SprayRequest): Seq[String] = {
    getFromRequestScope(ERROR_MESSAGES).getOrElse {
      throw new IllegalStateException("No validation errors found.")
    }
  }

  def keyAndErrorMessages(implicit currentRequest: SprayRequest): KeyAndErrorMessages = {
    getFromRequestScope(KEY_AND_ERROR_MESSAGES).getOrElse {
      throw new IllegalStateException("No validation errors found.")
    }
  }

}
