package skinny

package object splash {

  type SprayBoot = skinny.splash.boot.SprayBoot

  type SprayController = skinny.splash.controller.SprayController

  type SprayDispatcher = skinny.splash.dispatcher.SprayDispatcher
  type SprayDispatcherActor = skinny.splash.dispatcher.SprayDispatcherActor
  type SprayRoutes = skinny.splash.dispatcher.SprayRoutes

  type SprayAction = (SprayRequest) => SprayResponse

}
