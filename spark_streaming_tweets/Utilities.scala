import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object Utilities {
    /** Makes sure only ERROR messages get logged to avoid log spam. */
//  def setupErrLogging() = {
//    import org.apache.log4j.{Level, Logger}
//    val rootLogger = Logger.getRootLogger()
//    rootLogger.setLevel(Level.ERROR)
//  }
//
  /** Configures Twitter service credentials using twiter.txt in the main workspace directory */
  def setupTwitterCredentials() = {
    System.setProperty("twitter4j.oauth.consumerKey", "HNLvDTd1DsKrxC2cmr0y1HFK1")
    System.setProperty("twitter4j.oauth.consumerSecret", "wvKlVLrdbvuTn0dgMqrm0HSZDzUUikS4gTP76jo2OTVgWvpaqN")
    System.setProperty("twitter4j.oauth.accessToken", "1665041906-RdbcxqBs3omlcZ9LE5NolRvVW3JCKapjhvdFKLP")
    System.setProperty("twitter4j.oauth.accessTokenSecret", "sjQdgQC85zT0C31kwbR843mu1J5Ow5Evy4Exf8YhZsFDW")
  }

  def setupStandaloneSparkStreamingContext(name : String, sec : Int) = {
    val sparkConf = new SparkConf()
      .setMaster("local[*]") // --> The master URL to connect to, such as "local" to run locally with one thread, "local[4]" to run locally with 4 cores, or "spark://master:7077" to run on a Spark standalone cluster.
      .setAppName(name)
      .set("spark.streaming.stopGracefullyOnShutdown", "true")

    new StreamingContext(sparkConf, Seconds(sec))
  }

}
