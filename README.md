## skinny-splash

`skinny-splash` provides handy APIs for [Spray](http://spray.io/) applications by integrating following [Skinny Framework](http://skinny-framework.org/) components.

- skinny-validator
- skinny-json
- skinny-orm

### Motivation

When you need to build low latency JSON APIs, consider using this library.

- The most developer-friendly framework to build low latency JSON APIs
- Integration with Skinny Framework's existing handy components

### Standalone Example

`samples/simple` shows you how to build a simple Spray application. Let's try it out as follows.

```
git clone https://github.com/skinny-framework/skinny-splash.git
cd skinny-splash
sbt simpleSample/assembly
java -jar samples/simple/target/scala-2.11/simpleSample-assembly-0.1-SNAPSHOT.jar
# curl 'localhost:8080/name=World'
```

##### build.sbt

```scala
scalaVersion := "2.11.7"
libraryDependencies ++= Seq(
  "com.typesafe.akka"    %% "akka-actor"    % "2.4.0",
  "io.spray"             %% "spray-can"     % "1.3.3",
  "io.spray"             %% "spray-routing" % "1.3.3",
  "io.spray"             %% "spray-json"    % "1.3.1",
  "org.skinny-framework" %% "skinny-splash" % "1.1.+"
)
```

##### project/build.properties

```properties
sbt.version=0.13.9
```

##### src/main/scala/app.scala

```scala
import skinny.splash._
import skinny.validator._

class MyController extends SprayController {
  def form(req: SprayRequest) = validation(req,
    paramKey("name") is required
  )
  def index(implicit req: SprayRequest): SprayResponse = {
    if (form(req).validate()) {
      val body = toJSONString(Map("message" -> s"Hello, ${params.getOrElse("name", "Anonymous")}"))
      respondAs(body)
    } else {
      respondAs(status = 400, body = toJSONString(keyAndErrorMessages))
    }
  }
}

trait MyDispatcher extends SprayDispatcher {
  val myController = new MyController
  def routes = Seq(
    getRoute("")(implicit req => myController.index),
    postRoute("post")(implicit req => myController.index)
  )
}

class MyDispatcherActor
  extends SprayDispatcherActor
  with MyDispatcher

object MyApp extends SprayApplication {
  def dispatcherActorProps = toProps(classOf[MyDispatcherActor])
}
```

##### src/main/resources/messages.conf

```
error {
  required="{0} is required"
  notNull="{0} is required"
  notEmpty="{0} is required"
  length="{0} length must be {1}"
  minLength="{0} length must be greater than or equal to {1}"
  maxLength="{0} length must be less than or equal to {1}"
  minMaxLength="{0} length must be greater than or equal to {1} or be less than or equal to {2}"
  numeric="{0} must be numeric"
  intValue="{0} is less or greater than expected range"
  longValue="{0} is less or greater than expected range"
  intMinValue="{0} must be greater than or equal to {1}"
  intMaxValue="{0} must be less than or equal to {1}"
  intMinMaxValue="{0} must be greater than or equal to {1} or be less than or equal to {2}"
  longMinValue="{0} must be greater than or equal to {1}"
  longMaxValue="{0} must be less than or equal to {1}"
  longMinMaxValue="{0} must be greater than or equal to {1} or be less than or equal to {2}"
  dateTimeFormat="{0} must be a datetime value"
  dateFormat="{0} must be a date value"
  timeFormat="{0} must be a time value"
  same="{0} must be the same with {1}"
  email="{0} must be an e-mail"
  past="{0} must be in the past"
}

year="Year"
month="Month"
day="Day"
hour="Hour"
minute="Minute"
second="Second"

top="Top"
backToList="Back to list"
submit="Submit"
cancel="Cancel"
detail="Detail"
new="New"
edit="Edit"
delete="Delete"
```

#### How to run

- `sbt run`
- `curl -v localhost:8080`
- `curl -v localhost:8080?name=seratch`

### Servlet Integration Example


#### build.sbt

Additionally, `skinny-splash-servlet` is also required.

```scala
libraryDependencies += "org.skinny-framework" %% "skinny-splash-servlet" % "1.0.+"
```

#### Example Code

##### Scala Code

```scala
package api

import skinny.splash._
import skinny.splash.boot.SprayServletBoot

trait ApiController extends SprayController {
  def index(implicit req: SprayRequest) = respondAs("ok")
}

trait ApiDispatcher extends SprayDispatcher {
  val controller = new ApiController {}
  override def routes = Seq(
    getRoute("ok")(implicit req => controller.index)
  )
}

class ApiDispatcherActor
  extends SprayDispatcherActor
  with ApiDispatcher

class ApiBoot extends SprayServletBoot {
  override def dispatcherActorProps = toProps(classOf[ApiDispatcherActor])
}
```

##### src/main/resources/application.conf

```
spray.servlet {
  boot-class="api.ApiBoot"
  root-path="/api"
}
```

##### src/main/webapp/WEB-INF/web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://java.sun.com/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
         version="3.0">

    <listener>
        <listener-class>org.scalatra.servlet.ScalatraListener</listener-class>
    </listener>
    <listener>
        <listener-class>spray.servlet.Initializer</listener-class>
    </listener>

    <servlet>
        <servlet-name>sprayServlet</servlet-name>
        <servlet-class>spray.servlet.Servlet30ConnectorServlet</servlet-class>
        <async-supported>true</async-supported>
    </servlet>
    <servlet-mapping>
        <servlet-name>sprayServlet</servlet-name>
        <url-pattern>/api/*</url-pattern>
    </servlet-mapping>
</web-app>
```
