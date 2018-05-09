import org.apache.spark._
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.twitter.TwitterUtils

/** Listens to a stream of Tweets and keeps track of the most popular
  *  hashtags over a 5 minute window.
  */
object PopularHashtags {

  /** Our main function where the action happens */
  def main(args: Array[String]) {

    Utilities.setupTwitterCredentials()

    val ssc = Utilities.setupStandaloneSparkStreamingContext("PopularHashtags", 5)

    // Get rid of log spam (should be called after the context is set up)
//    Utilities.setupErrLogging()

    // Create a DStream from Twitter using our streaming context
    val tweets = TwitterUtils.createStream(ssc, None)

    // Now extract the text of each status update into DStreams using map()
    val statuses = tweets.map(status => status.getText())

    // Blow out each word into a new DStream
    val tweetwords = statuses.flatMap(tweetText => tweetText.split(" "))

    // Now eliminate anything that's not a hashtag
    val hashtags = tweetwords.filter(word => word.startsWith("#"))

    // Map each hashtag to a key/value pair of (hashtag, 1) so we can count them up by adding up the values
    val hashtagKeyValues = hashtags.map(hashtag => (hashtag, 1))

    // Now count them up over a 20 minute window sliding every 10 seconds
    val hashtagCounts = hashtagKeyValues.reduceByKeyAndWindow((x : Int, y : Int) => (x + y), Seconds(300), Seconds(5))

    // Sort the results by the count values
    val sortedResults = hashtagCounts.transform(rdd => rdd.sortBy(x => x._2, false))

    // Print the top 10
    sortedResults.foreachRDD(rdd =>
      rdd.take(10).foreach(println)
    )


    // ssc.checkpoint() -> needed for stateful streaming, for example: reduceByKeyAndWindow(func, invFunc, windowLength, slideInterval, [numTasks])
    ssc.start()
    ssc.awaitTermination()

    // ssc.awaitTerminationOrTimeout(5000) -> making sparkstreaming stop remains a problem
  }
}
