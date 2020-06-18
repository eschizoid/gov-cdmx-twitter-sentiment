"""
This script uses the Twitter Streaming API to pull in tweets and publish them to a PubSub topic.
"""
import datetime
import os

import simplejson as json
from google.cloud import pubsub_v1
from tweepy import OAuthHandler
from tweepy import Stream
from tweepy.streaming import StreamListener


class StdOutListener(StreamListener):
    """
    A listener handles tweets that are received from the stream.
    This listener dumps the tweets into a PubSub topic
    """
    count = 0
    tweets = []
    batch_size = 50
    total_tweets = 10000000

    def on_data(self, data):
        self.tweets.append(data)
        if len(self.tweets) >= self.batch_size:
            write_to_pubsub(self.tweets)
            self.tweets = []
        self.count += 1
        if self.count > self.total_tweets:
            return False
        if (self.count % 50) == 0:
            print("count is: %s at %s" % (self.count, datetime.datetime.now()))
        return True

    def on_error(self, status):
        print(status)


def write_to_pubsub(tw):
    """
    Publish to the given pubsub topic.
    """
    messages = []
    [messages.append({"data": json.loads(line, encoding="utf8")}) for line in tw]
    body = json.dumps({"messages": messages}, ensure_ascii=False, encoding="utf8")
    publisher = pubsub_v1.PublisherClient()
    topic_path = publisher.topic_path("gov-cdmx-twitter-sentiment", "twitter")
    future = publisher.publish(topic_path, data=body.encode("utf8"))
    future.result()


if __name__ == "__main__":
    listener = StdOutListener()
    auth = OAuthHandler(os.getenv("CONSUMER_KEY"), os.getenv("CONSUMER_SECRET"))
    auth.set_access_token(os.getenv("ACCESS_TOKEN"), os.getenv("ACCESS_SECRET"))
    stream = Stream(auth, listener)
    stream.filter(languages=["es"],
                  track=["CapitalCulturalDeAmérica", "CapitalCulturalDeAmerica",
                         "NuestraCasa",
                         "CiudadSustentable",
                         "NocheDePrimavera",
                         "MercadosPúblicos", "MercadosPublicos",
                         "MexicoCity",
                         "CDMX",
                         "AlcaldíasCDMX", "AlcaldiasCDMX",
                         "Fotocívicas", "Fotocivicas",
                         "SustituciónTaxi", "SustitucionTaxi"
                         ]
                  )
