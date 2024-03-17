// give the user a nice default project!
val sharedSettings = Seq(
  scalaVersion := DependencyVersions.scalaVersion,
  organization := "org.aurora",
  scalacOptions ++=  Seq("-Yretain-trees") //necessary in zio-json if any case classes have default parameters
)


lazy val root = project.in(file(".")).
  aggregate(dataimport.js, dataimport.jvm).
  settings(sharedSettings,
    publish := {},
    publishLocal := {}

  )

lazy val dataimport = crossProject(JSPlatform, JVMPlatform).crossType(CrossType.Full).in(file("."))
  .settings(
    name := "dataimportcsv3s",
    version := "0.1-SNAPSHOT",
    sharedSettings,
    libraryDependencies ++= Dependencies.zioJson.value,
    libraryDependencies ++= Dependencies.scalatest.value

  ).
  jvmSettings(
    // Add JVM-specific settings here
    //this maximizes the number of inlines for the csv3s macros for decoding case classes > 32 fields
    scalacOptions ++= Seq("-Xmax-inlines", "50"),
    libraryDependencies ++= Seq(
      Dependencies.zioHttp, 
      Dependencies.zioTest, 
      Dependencies.zioTestSBT, 
      Dependencies.zioTestMagnolia,
      
    ),
    libraryDependencies ++= Dependencies.scalaLogging.value,
    libraryDependencies += Dependencies.betterfiles,
    libraryDependencies += Dependencies.csv3s
      


  ).
  jsSettings(
    // Add JS-specific settings here
    libraryDependencies ++= Dependencies.sttp.value,
    scalaJSUseMainModuleInitializer := true,
  )