import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;

/**
 * Created by shayangzang on 4/16/18.
 */
public class TwitterSample {

  public static void main(String[] args) throws TwitterException, IOException {

    ConfigurationBuilder conf = new ConfigurationBuilder();
    conf.setDebugEnabled(true)
        .setOAuthConsumerKey("HNLvDTd1DsKrxC2cmr0y1HFK1")
        .setOAuthConsumerSecret("wvKlVLrdbvuTn0dgMqrm0HSZDzUUikS4gTP76jo2OTVgWvpaqN")
        .setOAuthAccessToken("1665041906-RdbcxqBs3omlcZ9LE5NolRvVW3JCKapjhvdFKLP")
        .setOAuthAccessTokenSecret("sjQdgQC85zT0C31kwbR843mu1J5Ow5Evy4Exf8YhZsFDW");

//    TwitterFactory tf = new TwitterFactory(conf.build());
//    Twitter twitter = tf.getInstance();


    StatusListener listener = new StatusListener() {
      public void onStatus(Status status) {
        System.out.println(status.getUser().getName() + " : " + status.getText());
      }
      public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
      public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
      public void onException(Exception ex) {
        ex.printStackTrace();
      }
      public void onScrubGeo(long var1, long var2) {
        ;
      }
      public void onStallWarning(StallWarning warning) {
        ;
      }
    };
    TwitterStream twitterStream = new TwitterStreamFactory(conf.build()).getInstance();
    twitterStream.addListener(listener);
    // sample() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.

    twitterStream.sample();
  }
}
