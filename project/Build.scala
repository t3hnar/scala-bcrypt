import sbt._
import Keys._
import sbtrelease.ReleasePlugin._

object Build extends Build {
  lazy val basicSettings = Seq(
    name := "Scala BCrypt",
    organization := "com.github.t3hnar",
    description := "Scala wrapper for jBcrypt + pom.xml inside",
    scalaVersion := "2.11.8",
    crossScalaVersions   := Seq("2.12.0-RC2", "2.11.8", "2.10.6"),
    licenses := Seq(("Apache License, Version 2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))),
    homepage := Some(new URL("https://github.com/t3hnar/scala-bcrypt")),
    startYear := Some(2012),
    scalacOptions := Seq("-encoding", "UTF-8", "-unchecked", "-deprecation", "-feature"),
    resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
    libraryDependencies ++= Seq(jbcrypt, specs2))

  val jbcrypt = "de.svenkubiak" % "jBCrypt" % "0.4.1"
  val specs2  = "org.specs2" %% "specs2-core" % "3.8.5.1" % "test"

  lazy val root = Project(
    "scala-bcrypt",
    file("."),
    settings = basicSettings ++ Defaults.defaultSettings ++ releaseSettings  ++ Publish.settings)
}
