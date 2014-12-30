package skinny.splash

trait SprayApplication[Dispatcher <: SprayDispatcher]
    extends SprayBoot[Dispatcher]
    with App {

  this.run()

}
