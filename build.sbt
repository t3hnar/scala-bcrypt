name := "scala-bcrypt"

organization := "com.github.t3hnar"

description := "Scala wrapper for jBcrypt + pom.xml inside"

scalaVersion := "2.13.3" // crossScalaVersions.value.last

crossScalaVersions := Seq("2.11.12", "2.12.18", "2.13.12", "3.3.1")

releaseCrossBuild := true

licenses := Seq(("Apache License, Version 2.0", url("http://www.apache.org/licenses/LICENSE-2.0")))

homepage := Some(new URL("https://github.com/t3hnar/scala-bcrypt"))

startYear := Some(2012)

scalacOptions := Seq(
  "-encoding", "UTF-8",
  "-feature",
  "-unchecked",
  "-deprecation",
  "-Xlint",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen")

libraryDependencies ++= Seq(
  "de.svenkubiak" % "jBCrypt" % "0.4.3",
  "org.scalatest" %% "scalatest" % "3.2.17" % Test/*,
  "org.scalatest" %% "scalatest" % "3.2.0" % Test,
  "org.scalatest" %% "scalatest-wordspec" % "3.2.0" % Test*/)

pomExtra in Global := {
  <scm>
    <url>git@github.com:t3hnar/scala-bcrypt.git</url>
    <connection>scm:git:git@github.com:t3hnar/scala-bcrypt.git</connection>
    <developerConnection>scm:git:git@github.com:t3hnar/scala-bcrypt.git</developerConnection>
  </scm>
    <developers>
      <developer>
        <id>t3hnar</id>
        <name>Yaroslav Klymko</name>
        <email>t3hnar@gmail.com</email>
      </developer>
    </developers>
}

releasePublishArtifactsAction := PgpKeys.publishSigned.value

publishTo := Some(
  if (isSnapshot.value)
    Opts.resolver.sonatypeSnapshots
  else
    Opts.resolver.sonatypeStaging
)
