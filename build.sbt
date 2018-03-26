name := "jetty-ldap"

organization := "de.otto"

licenses := Seq("Apache License, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))

homepage := Some(url("https://dev.otto.de/"))

scalaVersion := "2.11.12"

conflictManager := sbt.ConflictManager.latestRevision
sbtPlugin := true

libraryDependencies ++= Seq(
	"org.eclipse.jetty" % "jetty-servlet" % "9.4.8.v20171121",
	"org.eclipse.jetty" % "jetty-jaas" % "9.4.8.v20171121"
)

libraryDependencies ++= Seq(
	"org.eclipse.jetty" % "jetty-client" % "9.4.8.v20171121" % Test,
	"org.eclipse.jetty" % "jetty-server" % "9.4.8.v20171121" % Test,
	"org.scalatest" %% "scalatest" % "3.0.3" % Test
)

releasePublishArtifactsAction := PgpKeys.publishSigned.value
useGpg := true

publishMavenStyle := true

publishTo := {
	val nexus = "https://oss.sonatype.org/"
	if (version.value.trim.endsWith("SNAPSHOT")) {
		Some("snapshots" at nexus + "content/repositories/snapshots")
	} else {
		Some("releases" at nexus + "service/local/staging/deploy/maven2")
	}
}

// From: https://github.com/xerial/sbt-sonatype#using-with-sbt-release-plugin
import sbtrelease.ReleasePlugin.autoImport.ReleaseTransformations._

// From: https://github.com/xerial/sbt-sonatype#using-with-sbt-release-plugin
releaseCrossBuild := true
releaseProcess := Seq[ReleaseStep](
	checkSnapshotDependencies,
	inquireVersions,
	runClean,
	releaseStepCommandAndRemaining("^ test"),
	setReleaseVersion,
	commitReleaseVersion,
	tagRelease,
	releaseStepCommandAndRemaining("^ publishSigned"),
	setNextVersion,
	commitNextVersion,
	releaseStepCommand("sonatypeReleaseAll"),
	pushChanges
)

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
	<developers>
		<developer>
			<id>fr3dch3n</id>
			<name>Frederik Mars</name>
			<email>frederik.mars@otto.de</email>
			<organization>Otto (GmbH &amp; Co KG)</organization>
			<organizationUrl>https://www.otto.de</organizationUrl>
		</developer>
	</developers>
		<scm>
			<connection>scm:git:git@github.com:otto-de/jetty-ldap.git</connection>
			<developerConnection>scm:git:git@github.com:otto-de/jetty-ldap.git</developerConnection>
			<url>git@github.com:otto-de/jetty-ldap.git</url>
		</scm>)
