#!/bin/bash

# Enables job control
set -m

# Enables error propagation
set -e

# Run the server and send it to the background
/entrypoint.sh couchbase-server &

# Check if couchbase server is up
check_db() {
  curl --silent http://127.0.0.1:8091/pools > /dev/null
  echo $?
}

# Variable used in echo
i=1
# Echo with
log() {
  echo "[$i] [$(date +"%T")] $@"
  i=`expr $i + 1`
}

# Wait until it's ready
until [[ $(check_db) = 0 ]]; do
  >&2 log "Waiting for Couchbase Server to be available ..."
  sleep 1
done

PROPERTY_FILE=couchbaseSetup.properties

function prop {
   PROP_KEY=$1
   PROP_VALUE=`cat $PROPERTY_FILE | grep "$PROP_KEY" | cut -d'=' -f2`
   echo $PROP_VALUE
}

echo "# Reading property from $PROPERTY_FILE"

# Setup index and memory quota
log "$(date +"%T") Init cluster ........."
couchbase-cli cluster-init -c 127.0.0.1 --cluster-username $(prop "clusterUsername") --cluster-password $(prop "clusterPassword") \
  --cluster-name $(prop "clusterName") --cluster-ramsize $(prop "clusterRamsize") --cluster-index-ramsize $(prop "clusterIndexRamsize") --services data,index,query,fts \
  --index-storage-setting default

# Create the buckets
log "$(date +"%T") Create buckets ........."
couchbase-cli bucket-create -c 127.0.0.1 --username $(prop "clusterUsername") --password $(prop "clusterPassword") --bucket-type couchbase --bucket-ramsize 250 --bucket $(prop "bucketName")

# Create user
log "$(date +"%T") Create users ........."
couchbase-cli user-manage -c 127.0.0.1:8091 -u $(prop "clusterUsername") -p $(prop "clusterPassword") --set --rbac-username $(prop "bucketName") --rbac-password $(prop "bucketPassword") \
 --rbac-name "sysadmin" --roles admin --auth-domain local

# Need to wait until query service is ready to process N1QL queries
log "$(date +"%T") Waiting ........."
sleep 20
fg 1



