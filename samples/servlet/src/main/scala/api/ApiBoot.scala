package api

import skinny.splash.boot.SprayServletBoot

class ApiBoot extends SprayServletBoot {

  override def dispatcherActorProps = toProps(classOf[ApiDispatcherActor])

}
