
val ammonite = "com.lihaoyi" %% "ammonite-ops" % "1.6.0"

lazy val root = (project in file("."))
  .settings(
    name := "Configuration Generator",
    scalaVersion := "2.12.7",
    libraryDependencies += ammonite ,
    mainClass := Some("Gen")
  )

