#!/bin/bash

WHERE=$(pwd)
WHERE="${WHERE//\//\\/}"

cd br.org.archimedes.build

wget "http://ftp.halifax.rwth-aachen.de/eclipse//eclipse/downloads/drops/R-3.7.2-201202080800/eclipse-SDK-3.7.2-linux-gtk.tar.gz"
wget "http://ftp.halifax.rwth-aachen.de/eclipse//eclipse/downloads/drops/R-3.7.2-201202080800/eclipse-3.7.2-delta-pack.zip"
tar xf eclipse-SDK-3.7.2-linux-gtk.tar.gz
unzip -o eclipse-3.7.2-delta-pack.zip

echo "buildHome=`pwd`" > build_local.properties
echo "buildDirectory=/tmp/pluginbuilder/br.org.archimedes.build" >> build_local.properties
echo "eclipseDir=`pwd`/eclipse" >> build_local.properties
echo "os=linux" >> build_local.properties
echo "ws=gtk" >> build_local.properties
echo "arch=x86" >> build_local.properties

sed "s/test.eclipse.zip\=/test.eclipse.zip\=$WHERE\/br.org.archimedes.build\/eclipse-SDK-3.7.2-linux-gtk.tar.gz/" build-files/automatedTests/run-tests-template.properties > build-files/automatedTests/run-tests.properties

sed "s/PROJECT_ROOT/$WHERE/" maps/all-template.map > maps/all.map

ant
