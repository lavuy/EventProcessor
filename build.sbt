name := "EventProcessor"

version := "1.0"

scalaVersion := "2.12.6"
libraryDependencies += "org.scalatest" % "scalatest_2.12" % "3.0.5" % "test"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.5"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.20"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.5.12",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.12" % Test
)
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.1.1",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.1.1" % Test
)






