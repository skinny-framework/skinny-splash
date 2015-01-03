// -----------------------
// common settings
// -----------------------

lazy val releaseVersion = "0.1.2"
lazy val skinnyVersion = "1.3.9"
lazy val skinnyOrg = "org.skinny-framework"

lazy val skinnyDeps = Seq(
  skinnyOrg %% "skinny-common"    % skinnyVersion % "compile",
  skinnyOrg %% "skinny-json"      % skinnyVersion % "compile",
  skinnyOrg %% "skinny-validator" % skinnyVersion % "compile"
)
lazy val sprayDeps = Seq(
  "com.typesafe.akka" %% "akka-actor"    % "2.3.8" % "compile",
  "io.spray"          %% "spray-can"     % "1.3.2" % "compile",
  "io.spray"          %% "spray-routing" % "1.3.2" % "compile",
  "io.spray"          %% "spray-json"    % "1.3.1" % "compile"
)
lazy val logbackDeps = Seq(
  "ch.qos.logback" % "logback-classic" % "1.1.2"
)
lazy val logbackTestDeps = Seq(
  "ch.qos.logback" % "logback-classic" % "1.1.2" % "test"
)
lazy val sprayTestDeps = Seq(
  "io.spray"          %% "spray-testkit" % "1.3.2" % "test",
  "org.scalatest"     %% "scalatest"     % "2.2.3" % "test"
)
lazy val sprayServletDeps = Seq(
  "io.spray"          %% "spray-servlet" % "1.3.2" % "compile"
)

lazy val baseSettings = Seq(
  scalaVersion := "2.11.4",
  transitiveClassifiers in Global := Seq(Artifact.SourceClassifier),
  parallelExecution in Test := false,
  logBuffered in Test := false,
  scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")
)

lazy val librarySettings = Seq(
  organization := skinnyOrg,
  version := releaseVersion,
  crossScalaVersions := Seq("2.11.4"),
  publishTo <<= version { (v: String) => 
    val nexus = "https://oss.sonatype.org/"
    if (v.trim.endsWith("SNAPSHOT")) Some("snapshots" at nexus + "content/repositories/snapshots")
    else Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },
  publishMavenStyle := true,
  pomIncludeRepository := { x => false },
  pomExtra := <url>https://github.com/skinny-framework/skinny-spray/</url>
  <licenses>
    <license>
      <name>MIT License</name>
      <url>http://www.opensource.org/licenses/mit-license.php</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:skinny-framework/skinny-spray.git</url>
    <connection>scm:git:git@github.com:skinny-framework/skinny-spray.git</connection>
  </scm>
  <developers>
    <developer>
      <id>seratch</id>
      <name>Kazuhiro Sera</name>
      <url>http://git.io/sera</url>
    </developer>
  </developers>
)

// -----------------------
// library
// -----------------------

lazy val skinnySplash = (project in file(".")).settings(baseSettings: _*).settings(librarySettings: _*).settings(
  name := "skinny-spray",
  libraryDependencies ++= sprayDeps ++ skinnyDeps ++ sprayTestDeps ++ logbackTestDeps
).settings(scalariformSettings: _*)
 .settings(sonatypeSettings: _*)

lazy val skinnySplashServlet = (project in file("servlet")).settings(baseSettings: _*).settings(librarySettings: _*).settings(
  name := "skinny-spray-servlet",
  libraryDependencies ++= sprayDeps ++ skinnyDeps ++ sprayServletDeps ++ sprayTestDeps ++ logbackTestDeps
).dependsOn(skinnySplash)
 .settings(scalariformSettings: _*)
 .settings(sonatypeSettings: _*)

// -----------------------
// samples
// -----------------------

lazy val simpleSample = (project in file("samples/simple")).settings(baseSettings: _*).settings(
  libraryDependencies ++= logbackDeps ++ sprayTestDeps
).dependsOn(skinnySplash)
 .settings(scalariformSettings: _*)

import org.scalatra.sbt._
lazy val servletSample = (project in file("samples/servlet"))
  .settings(baseSettings: _*)
  .settings(ScalatraPlugin.scalatraWithJRebel: _*)
  .settings(
    libraryDependencies ++= logbackDeps ++ sprayTestDeps ++ Seq(
      skinnyOrg            %% "skinny-framework"  % skinnyVersion,
      "javax.servlet"      %  "javax.servlet-api" % "3.1.0",
      "org.eclipse.jetty"  %  "jetty-webapp"      % "9.2.6.v20141205" % "container",
      "org.eclipse.jetty"  %  "jetty-plus"        % "9.2.6.v20141205" % "container"
    )
  ).dependsOn(skinnySplash, skinnySplashServlet)
   .settings(scalariformSettings: _*)

