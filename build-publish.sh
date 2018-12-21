#!/usr/bin/env bash

./gradlew clean build
cd docker
docker build -t gcr.io/os-prod-218112/osaccounts-api:latest . && docker push gcr.io/os-prod-218112/osaccounts-api:latest
cd ../../devops/
#./bin/os_helm.py -c os-apps -n os-dev -a delete -r accounts && ./bin/os_helm.py -c os-apps -n os-dev -a install -r accounts
./bin/os_helm.py -c os-apps -n os-dev -a upgrade -r accounts