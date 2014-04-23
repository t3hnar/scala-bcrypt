import sbt._
import Keys._
import sbtrelease.ReleasePlugin._

object Build extends Build {
  lazy val basicSettings = Seq(
    name := "Scala BCrypt",
    organization := "com.github.t3hnar",
    description := "Scala wrapper for jBcrypt + pom.xml inside",
    scalaVersion := "2.10.4",
    crossScalaVersions := Seq("2.10.4", "2.11.0"),
    licenses := Seq(("Apache License, Version 2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))),
    homepage := Some(new URL("https://github.com/t3hnar/scala-bcrypt")),
    startYear := Some(2012),
    scalacOptions := Seq("-encoding", "UTF-8", "-unchecked", "-deprecation", "-feature"),
    libraryDependencies ++= Seq(jbcrypt, junit, specs2))

  val jbcrypt = "org.mindrot" % "jbcrypt" % "0.3m"
  val junit   = "junit" % "junit" % "4.11" % "test"
  val specs2  = "org.specs2" %% "specs2" % "2.3.4" % "test"


  lazy val root = Project(
    "scala-bcrypt",
    file("."),
    settings = basicSettings ++ Defaults.defaultSettings ++ releaseSettings  ++ Publish.settings)
}