## Transformation
The purpose of this project is to read a raw json tweet from Google PubSub and sink into BigQuery for later analysis.

### Pre-requisites:
* Java 1.8.x
* Scala Scala 2.13

### Required Env variables
```
GOOGLE_APPLICATION_CREDENTIALS
```

### Running Beam Pipeline
```
java -jar build/libs/transformation-1.0-all.jar \
  --project=gov-cdmx-twitter-sentiment \
  --runner=DataflowRunner \
  --streaming=true \
  --region=us-east1 \
  --tempLocation=gs://gov-cdmx-twitter-sentiment/temp/ \
  --stagingLocation=gs://gov-cdmx-twitter-sentiment/jars/ \
  --filesToStage=build/libs/transformation-1.0-all.jar \
  --maxNumWorkers=2 \
  --numWorkers=1
```