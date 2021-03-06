scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")
addMavenResolverPlugin
addSbtPlugin("org.scalariform"  % "sbt-scalariform"      % "1.6.0")
addSbtPlugin("org.xerial.sbt"   % "sbt-sonatype"         % "1.0")
addSbtPlugin("com.jsuereth"     % "sbt-pgp"              % "1.0.0")
addSbtPlugin("com.timushev.sbt" % "sbt-updates"          % "0.1.10")
addSbtPlugin("com.github.mpeltonen" % "sbt-idea"         % "1.6.0")
addSbtPlugin("org.scoverage"    % "sbt-scoverage"        % "1.3.5")
addSbtPlugin("org.scoverage"    % "sbt-coveralls"        % "1.0.0")
// for simpleSample project
addSbtPlugin("com.eed3si9n"     % "sbt-assembly"         % "0.13.0")
// for samples/servlet
addSbtPlugin("org.skinny-framework" % "sbt-servlet-plugin" % "2.0.4")
