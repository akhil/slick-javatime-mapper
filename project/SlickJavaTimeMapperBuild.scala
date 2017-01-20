import sbt._
import sbt.Keys._

object SlickJavaTimeMapperBuild extends Build {

  lazy val root = Project(
    id = "slick-javatime-mapper",
    base = file("."),
    settings = Defaults.coreDefaultSettings ++ Seq(
      name := "slick-javatime-mapper",
      organization := "com.github.akhil",
      version := "1.0.0",
      crossScalaVersions ++= Seq("2.10.6", "2.11.8"),
      scalaVersion := "2.11.8",
      scalacOptions ++= Seq("-deprecation", "-language:_"),
      libraryDependencies ++= Seq(
        "com.h2database" % "h2" % "[1.4,)" % Test,
        "org.scalatest" %% "scalatest" % "3.0.1" % Test,
        "com.typesafe.slick" %% "slick" % "3.1.1" % Provided
      ),
      initialCommands += """
        import com.github.akhil.slick.JavaTimeSupport._
        import java.time._
        import java.sql._
      """
    ) ++ publishingSettings
  )

  val publishingSettings = Seq(
    publishMavenStyle := true,
    publishTo <<= version { (v: String) => _publishTo(v) },
    publishArtifact in Test := false,
    pomExtra := _pomExtra
  )

  def _publishTo(v: String) = {
    val nexus = "https://oss.sonatype.org/"
    if (v.trim.endsWith("SNAPSHOT")) Some("snapshots" at nexus + "content/repositories/snapshots")
    else Some("releases" at nexus + "service/local/staging/deploy/maven2")
  }

  val _pomExtra =
    <url>http://github.com/akhil/slick-javatime-mapper</url>
    <licenses>
      <license>
        <name>Two-clause BSD-style license</name>
        <url>http://github.com/akhil/slick-javatime-mapper/blob/master/LICENSE.txt</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:akhil/slick-javatime-mapper.git</url>
      <connection>scm:git:git@github.com:akhil/slick-javatime-mapper.git</connection>
    </scm>
    <developers>
      <developer>
        <id>akhil</id>
        <name>Akhil Kodali</name>
        <url>http://akhil.github.com</url>
      </developer>
    </developers>

}
