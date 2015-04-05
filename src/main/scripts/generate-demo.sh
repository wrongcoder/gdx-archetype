#!/bin/sh
set -e
cd ~/Projects
if [ -d demo -a -f demo/.gitignore ]; then
	(cd demo && find . -name .git -prune -o -name '*.iml' -prune -o -type f -print0 | xargs -0 rm)
fi
mvn archetype:generate -B -DarchetypeGroupId=com.vdxp.gdx -DarchetypeArtifactId=gdx-archetype -DgroupId=com.example.demo -DartifactId=demo -Dversion='1.0.0.BUILD-SNAPSHOT' -DprojectTitle='Demo Project'
cd demo
git init
git add -A .
git commit --allow-empty -m 'Generated demo project'
mvn -P desktop,html clean
