package api

import skinny.splash.boot.SprayServletBoot

class ApiBoot extends SprayServletBoot {

  override def dispatcherProps = toProps(classOf[ApiDispatcherActor])

}
