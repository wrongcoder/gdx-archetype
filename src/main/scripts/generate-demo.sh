#!/bin/bash
set -e

SCRIPT=$(python -c 'import os, sys; print os.path.realpath(sys.argv[1])' "$0")
SCRIPT_DIR=$(dirname "$SCRIPT")
POM_DIR="$SCRIPT_DIR/../../.."
[ -d "$SCRIPT_DIR" ] || { echo "Cannot locate this script (looks like it's in $SCRIPT_DIR)"; exit 1; }
[ -f "$POM_DIR/pom.xml" ] || { echo "Was this script moved? Redefine POM_DIR"; exit 1; }

cd "$POM_DIR/.."

if [ -d demo -a -f demo/.gitignore ]; then
	find demo -name .git -prune -o -name '*.iml' -prune -o -type f -print0 | xargs -0 rm
fi

mvn archetype:generate -B -DarchetypeGroupId=com.vdxp.gdx -DarchetypeArtifactId=gdx-archetype -DgroupId=com.example.demo -DartifactId=demo -Dversion='1.0.0.BUILD-SNAPSHOT' -DprojectTitle='Demo Project'

cd demo
git init
git add -A .
git commit --allow-empty -m 'Generated demo project'
mvn -P desktop,html clean
