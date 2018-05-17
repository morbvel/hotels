
name := "Hoteles"
version := "0.1"
scalaVersion := "2.11.8"

val sparkVersion = "2.3.0"

libraryDependencies ++= Seq(
  "junit" % "junit" % "4.12" % Test,
  "org.scalatest" %% "scalatest" % "2.2.2" % Test,
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "com.databricks" %% "spark-csv" % "1.5.0"
)

lazy val compileScalastyle = taskKey[Unit]("compileScalastyle")

compileScalastyle := scalastyle.in(Compile).toTask("").value

(compile in Compile) := ((compile in Compile) dependsOn compileScalastyle).value

// disable using the Scala version in output paths and artifacts
crossPaths := false

//option to avoid warnings
updateOptions := updateOptions.value.withLatestSnapshots(false)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

artifact in (Compile, assembly) := {
  val art = (artifact in (Compile, assembly)).value
  art.copy(`classifier` = Some("assembly"))
}

addArtifact(artifact in (Compile, assembly), assembly)

sources in (Compile,doc) := Seq.empty
publishArtifact in (Compile, packageDoc) := false
