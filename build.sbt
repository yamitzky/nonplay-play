name := """nonplay"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

lazy val skinnyVersion = "1.3.8"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalikejdbc"        %% "scalikejdbc-play-dbplugin-adapter" % "2.3.4",
  "org.skinny-framework"   %% "skinny-orm"                        % skinnyVersion,
  "mysql"                  %  "mysql-connector-java"              % "5.1.32"
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