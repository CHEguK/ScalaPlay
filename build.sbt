name := """ScalaPlay"""

version := "1.0-SNAPSHOT"

lazy val server = (project in file("server")).settings(
    name := "Play-Videos-Server",
    organization := "My_organization",
    scalaVersion := "2.13.5",
    libraryDependencies ++= Seq(
        guice,
        "com.typesafe.play" %% "play-json" % "2.8.1",
        "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
    )
).enablePlugins(PlayScala)

