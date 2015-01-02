name := """nonplay"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

lazy val skinnyVersion = "1.3.8"

resolvers += Resolver.url("Edulify Repository", url("http://edulify.github.io/modules/releases/"))(Resolver.ivyStylePatterns)

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalikejdbc"        %% "scalikejdbc-play-dbplugin-adapter" % "2.3.4",
  "org.skinny-framework"   %% "skinny-orm"                        % skinnyVersion,
  "org.skinny-framework"   %% "skinny-validator"                  % skinnyVersion,
  "mysql"                  %  "mysql-connector-java"              % "5.1.32",
  "com.edulify"            %% "play-hikaricp"                     % "1.4.1"
)

lazy val task = (project in file("task")).settings(
  scalaSource in Compile := baseDirectory.value,
  libraryDependencies ++= Seq(
    "org.skinny-framework" %% "skinny-task" % skinnyVersion,
    "org.skinny-framework" %% "skinny-orm"  % skinnyVersion,
    "mysql"                %  "mysql-connector-java" % "5.1.32"
  ),
  mainClass := Some("TaskRunnner")
)