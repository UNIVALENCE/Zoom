
import sbt.{Project, RootProject}


import sbt.url



scalaVersion in ThisBuild := "2.11.8"


lazy val commonSettings = Seq(
  organization := "io.univalence",
  version := "0.3-SNAPSHOT",
  {import scalariform.formatter.preferences._

    scalariformPreferences := scalariformPreferences.value
  .setPreference(RewriteArrowSymbols, true)
  .setPreference(IndentSpaces, 2)
  .setPreference(SpaceBeforeColon, false)
  .setPreference(CompactStringConcatenation, false)
  .setPreference(PreserveSpaceBeforeArguments, false)
  .setPreference(AlignParameters, true)
  .setPreference(AlignArguments, false)
  .setPreference(DoubleIndentConstructorArguments, false)
  .setPreference(FormatXml, true)
  .setPreference(IndentPackageBlocks, true)
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 50)
  .setPreference(IndentLocalDefs, false)
  .setPreference(DanglingCloseParenthesis, Force)
  .setPreference(SpaceInsideParentheses, false)
  .setPreference(SpaceInsideBrackets, false)
  .setPreference(SpacesWithinPatternBinders, true)
  .setPreference(MultilineScaladocCommentsStartOnFirstLine, true)
  .setPreference(IndentWithTabs, false)
  .setPreference(CompactControlReadability, false)
  .setPreference(PlaceScaladocAsterisksBeneathSecondAsterisk, true)
  .setPreference(SpacesAroundMultiImports, true)}

)


val libVersion = new {
  val circe = "0.8.0"
  val scalaTest = "3.0.1"
  val kafka = "0.11.0.0"
  val slf4j = "1.6.4"
}

parallelExecution := false



lazy val callsitemacro = (project in file("modele-macros")).settings(commonSettings).settings(
  libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  libraryDependencies += "org.eclipse.jgit" % "org.eclipse.jgit" % "5.0.1.201806211838-r"
)


lazy val core = (project in file("zoom-core")).settings(commonSettings).settings(
  libraryDependencies ++= Seq(
    "circe-core",
    "circe-generic",
    "circe-parser",
    "circe-generic-extras",
    "circe-optics"
  ).map(x => "io.circe" %% x % libVersion.circe),

    libraryDependencies ++= Seq(
      //Kafka
      "org.apache.kafka" % "kafka-clients" % libVersion.kafka,
      "org.apache.kafka" %% "kafka" % libVersion.kafka,

      //EmbeddedKafka
      "net.manub" %% "scalatest-embedded-kafka" % "0.15.1",

      //Test
      "org.scalatest" %% "scalatest" % libVersion.scalaTest % Test,

      "org.slf4j" % "slf4j-api" % libVersion.slf4j,
      "org.slf4j" % "slf4j-log4j12" % libVersion.slf4j,
      "org.json4s" %% "json4s-native" % "3.5.3",
      "org.scala-lang.modules" %% "scala-xml" % "1.0.2",
      "com.sksamuel.avro4s" %% "avro4s-core" % "1.6.4",
      "joda-time" % "joda-time" % "2.9.9",
      "org.joda" % "joda-convert" % "1.9.2",
      "com.typesafe" % "config" % "1.3.2",
      "org.scala-lang" % "scala-reflect" % scalaVersion.value
    )).dependsOn(callsitemacro)


lazy val root = (project in file(".")).settings(commonSettings).settings(
  name := "zoom"
).aggregate(callsitemacro, core)



//TODO : Desactivate test in ||


licenses += "The Apache License, Version 2.0" ->
  url("http://www.apache.org/licenses/LICENSE-2.0.txt")

description := "Zoom is an event bus"

developers := List(
  Developer(
    id="jwinandy",
    name="Jonathan Winandy",
    email="jonathan@univalence.io",
    url=url("https://github.com/ahoy-jon")
  ),
  Developer(
    id="phong",
    name="Philippe Hong",
    email="philippe@univalence.io",
    url=url("https://github.com/hwki77")
  )
)

scmInfo := Some(ScmInfo(
  url("https://github.com/UNIVALENCE/Zoom"),
  "scm:git:https://github.com/UNIVALENCE/Zoom.git",
  Some(s"scm:git:git@github.com:UNIVALENCE/Zoom.git")
))


publishMavenStyle := true
publishTo := Some(sonatypeDefaultResolver.value)

useGpg := true




