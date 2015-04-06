#!/bin/sh
mvn clean deploy -DaltDeploymentRepository=local::default::file:../gdx-archetype-mvn
