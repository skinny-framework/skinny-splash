import _root_.controller._
import skinny._

class ScalatraBootstrap extends SkinnyLifeCycle {

  override def dbSettingsEnabled = false

  override def initSkinnyApp(ctx: ServletContext): Unit = {
    Controllers.mount(ctx)
  }
}
