import _root_.controller._
import skinny._

class Bootstrap extends SkinnyLifeCycle {

  override def dbSettingsEnabled = false

  override def initSkinnyApp(ctx: ServletContext): Unit = {
    Controllers.mount(ctx)
  }
}
