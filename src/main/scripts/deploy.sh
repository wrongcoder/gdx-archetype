#!/bin/bash

SCRIPT=$(python -c 'import os, sys; print os.path.realpath(sys.argv[1])' "$0")
SCRIPT_DIR=$(dirname "$SCRIPT")
POM_DIR="$SCRIPT_DIR/../../.."
[ -d "$SCRIPT_DIR" ] || { echo "Cannot locate this script (looks like it's in $SCRIPT_DIR)"; exit 1; }
[ -f "$POM_DIR/pom.xml" ] || { echo "Was this script moved? Redefine POM_DIR"; exit 1; }
cd "$POM_DIR"

mvn clean deploy -DaltDeploymentRepository=local::default::file:../maven
