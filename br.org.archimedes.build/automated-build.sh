#!/bin/bash

if [ "$1" == "--travis" ]; then
	TRAVIS=true
else
	TRAVIS=false
fi

WHERE=$(pwd)
WHERE="${WHERE//\//\\/}"

BUILD_DIR=/tmp/pluginbuilder/br.org.archimedes.build

if [ $TRAVIS ]; then
	ECLIPSE=eclipse-SDK-3.7.2-linux-gtk.tar.gz
else
	ECLIPSE=eclipse-SDK-3.7.2-linux-gtk-x86_64.tar.gz
fi

cd br.org.archimedes.build

echo "Starting space: "
df -h

echo
echo "Downloading..."
wget -N -nv "http://ftp.halifax.rwth-aachen.de/eclipse//eclipse/downloads/drops/R-3.7.2-201202080800/$ECLIPSE"
wget -N -nv "http://ftp.halifax.rwth-aachen.de/eclipse//eclipse/downloads/drops/R-3.7.2-201202080800/eclipse-3.7.2-delta-pack.zip"

echo
echo "Unpacking..."
tar xf $ECLIPSE
unzip -oq eclipse-3.7.2-delta-pack.zip

if [ $TRAVIS ]; then
	rm -f $ECLIPSE eclipse-3.7.2-delta-pack.zip
fi

# Installing plugins on Eclipse: http://help.eclipse.org/indigo/topic/org.eclipse.platform.doc.isv/guide/p2_director.html
echo
echo "Installing plugins..."
cd eclipse
./eclipse  -nosplash \
   -application org.eclipse.equinox.p2.director \
   -installIU org.eclipse.swtbot.eclipse.gef.finder,org.eclipse.swtbot.forms.feature.group,org.eclipse.swtbot.eclipse.feature.group,org.eclipse.swtbot.eclipse.gef.feature.group,org.eclipse.swtbot.feature.group,org.eclipse.swtbot.ide.feature.group,org.eclipse.swtbot.eclipse.test.junit3.feature.group,org.eclipse.swtbot.eclipse.test.junit4.feature.group \
   -profileProperties org.eclipse.update.install.features=true \
   -profile SDKProfile \
   -repository http://download.eclipse.org/technology/swtbot/helios/dev-build/update-site,http://download.eclipse.org/tools/gef/updates/releases/
cd ..

echo
echo "Building..."
echo "buildHome=`pwd`" > build_local.properties
echo "buildDirectory=$BUILD_DIR" >> build_local.properties
echo "eclipseDir=`pwd`/eclipse" >> build_local.properties
echo "os=linux" >> build_local.properties
echo "ws=gtk" >> build_local.properties
#echo "arch=x86_64" >> build_local.properties
echo "arch=x86" >> build_local.properties

sed "s/test.eclipse.zip\=/test.eclipse.zip\=$WHERE\/br.org.archimedes.build\/$ECLIPSE/" build-files/automatedTests/run-tests-template.properties > build-files/automatedTests/run-tests.properties

sed "s/PROJECT_ROOT/$WHERE/" maps/all-template.map > maps/all.map

ant > output

RET=$?

if [ ! "$RET" = "0" ]; then
	sleep 5
	echo
	echo "ANT log..."
	cat output
	echo
	echo "End Output"
	cat $BUILD_DIR/workspace/.metadata/.log
	echo
	echo "Disk space:"
	df -h
	exit $RET
fi

