addSbtPlugin("com.typesafe.sbt" % "sbt-scalariform"      % "1.3.0")
addSbtPlugin("org.xerial.sbt"   % "sbt-sonatype"         % "0.2.1")
addSbtPlugin("com.jsuereth"     % "sbt-pgp"              % "1.0.0")
addSbtPlugin("com.timushev.sbt" % "sbt-updates"          % "0.1.7")
addSbtPlugin("com.github.mpeltonen" % "sbt-idea"         % "1.6.0")
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.4")
addSbtPlugin("org.scoverage"    % "sbt-scoverage"        % "1.0.1")
addSbtPlugin("org.scoverage"    % "sbt-coveralls"        % "1.0.0.BETA1")

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

// for samples/servlet
addSbtPlugin("org.scalatra.sbt" % "scalatra-sbt" % "0.3.5" excludeAll(
  ExclusionRule(organization = "org.mortbay.jetty"),
  ExclusionRule(organization = "org.eclipse.jetty"),
  ExclusionRule(organization = "org.apache.tomcat.embed"),
  ExclusionRule(organization = "com.earldouglas")
))
addSbtPlugin("com.earldouglas" % "xsbt-web-plugin" % "0.9.1" excludeAll(
  ExclusionRule(organization = "org.mortbay.jetty"),
  ExclusionRule(organization = "org.eclipse.jetty"),
  ExclusionRule(organization = "org.apache.tomcat.embed")
))

