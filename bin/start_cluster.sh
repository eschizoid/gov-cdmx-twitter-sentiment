#!/usr/bin/env bash

set -a
source .env
set +a

eksctl create cluster \
    --name=cdmx-government-twitter-sentiment-analysis \
    --nodes=5 \
    --version=1.15 \
    --region=us-east-1 \
    --node-type t3.xlarge \
    --zones=us-east-1a,us-east-1b,us-east-1d
