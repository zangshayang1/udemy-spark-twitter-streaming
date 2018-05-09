val common = Seq(
 name := """Spark_Streaming_Tweets""",
 version := "1.0-SNAPSHOT",
 scalaVersion := "2.11.7"
)

// lazy val p1 = (project in file("p1")).settings(common)
// lazy val p2 = (project in file("p2")).settings(common)
// lazy val root = (project in file(".")).settings(common).aggregate(p1, p2)

lazy val root = (project in file(".")).settings(common)

// double % means building Spark with scala with version defined above, the last col means the actual spark version you want to use.
// % means you will specify the scala version you want to use to build Spark.
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.5.2",
  "org.apache.spark" %% "spark-streaming" % "1.5.2",
  "org.apache.spark" % "spark-streaming-twitter_2.11" % "1.5.2",
  "org.apache.spark" %% "spark-sql" % "1.5.2",
  "com.google.code.gson" % "gson" % "2.7" // a java (de)serialize lib

  // twitter API for java
  //  "org.twitter4j" % "twitter4j-core" % "3.0.3",
  //  "org.twitter4j" % "twitter4j-stream" % "3.0.3"

  // twitter API for scala -> twitter4s
)
