## skinny-splash

skinny-splash provides handy APIs for [Spray](http://spray.io/) applications by integrating following [Skinny Framework](http://skinny-framework.org/) components.

- skinny-validator
- skinny-json
- skinny-orm

### Motivation

When you need to build low latency JSON APIs, consider using this library.

- the most developer-friendly framework to build low latency JSON APIs
- integration with Skinny Framework's existing handy components

### How to use

#### build.sbt

```scala
libraryDependencies ++= Seq(
  "com.typesafe.akka"    %% "akka-actor"    % "2.3.8",
  "io.spray"             %% "spray-can"     % "1.3.2",
  "io.spray"             %% "spray-routing" % "1.3.2",
  "io.spray"             %% "spray-json"    % "1.3.1"
  "org.skinny-framework" %% "skinny-splash" % "0.1"
)
```

#### Example

```scala
import skinny.splash._
import skinny.validator._

// one controller for one endpoint API
class MyController(req: SprayRequest) extends SprayController(req) {
  def form = validation(params,
    paramKey("name") is required
  )
  override def apply = {
    if (form.validate()) {
      val body = toJSONString(Map("message" -> s"Hello, ${params.getOrElse("name", "Anonymous")}"))
      respondAs(body)
    } else {
      respondAs(status = 400, body = toJSONString(keyAndErrorMessages))
    }
  }
}

// the dispatcher of this Spray app
class MyDispatcher extends SprayDispatcher {
  override def routes = Seq(
    getRoute("")(req => new MyController(req)),
    postRoute("post")(req => new MyController(req))
  )
}

// standalone app (can boot with "sbt run")
object MyApp extends SprayApplication[MyDispatcher] {
  override def dispatcherClass = classOf[MyDispatcher]
}
```

### Servlet Integration

```scala
libraryDependencies += "org.skinny-framework" %% "skinny-splash-servlet" % "0.1"
```

