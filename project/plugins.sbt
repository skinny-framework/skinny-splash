addSbtPlugin("com.typesafe.sbt" % "sbt-scalariform"      % "1.3.0")
addSbtPlugin("org.xerial.sbt"   % "sbt-sonatype"         % "0.2.1")
addSbtPlugin("com.jsuereth"     % "sbt-pgp"              % "1.0.0")
addSbtPlugin("com.timushev.sbt" % "sbt-updates"          % "0.1.7")
addSbtPlugin("com.github.mpeltonen" % "sbt-idea"         % "1.6.0")
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.4")
addSbtPlugin("org.scoverage"    % "sbt-scoverage"        % "1.0.1")
addSbtPlugin("org.scoverage"    % "sbt-coveralls"        % "1.0.0.BETA1")

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

