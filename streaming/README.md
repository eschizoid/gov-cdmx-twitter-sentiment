## Streaming
The purpose of this project is to stream tweets using Twitter Streaming API and publish them to Google PubSub

### Pre-requisites:
* Python 3

### Required Env variables
```
CONSUMER_KEY
CONSUMER_SECRET
ACCESS_TOKEN
ACCESS_SECRET
```

### Running Beam Pipeline
```
java -jar build/libs/transformation-1.0-SNAPSHOT-all.jar \
  --project=gov-cdmx-twitter-sentiment \
  --runner=DataflowRunner \
  --streaming=true \
  --region=us-east1 \
  --tempLocation=gs://gov-cdmx-twitter-sentiment/temp/ \
  --stagingLocation=gs://gov-cdmx-twitter-sentiment/jars/ \
  --maxNumWorkers=2 \
  --numWorkers=1
```