package skinny

package object splash {

  type SprayBoot[Dispatcher <: SprayDispatcher] = skinny.splash.boot.SprayBoot[Dispatcher]

  type SprayController = skinny.splash.controller.SprayController

  type SprayDispatcher = skinny.splash.dispatcher.SprayDispatcher
  type SprayRoutes = skinny.splash.dispatcher.SprayRoutes

}
