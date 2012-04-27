#!/bin/bash

WHERE=$(pwd)
WHERE="${WHERE//\//\\/}"

cd br.org.archimedes.build

echo "Downloading..."
wget "http://ftp.halifax.rwth-aachen.de/eclipse//eclipse/downloads/drops/R-3.7.2-201202080800/eclipse-SDK-3.7.2-linux-gtk.tar.gz"
wget "http://ftp.halifax.rwth-aachen.de/eclipse//eclipse/downloads/drops/R-3.7.2-201202080800/eclipse-3.7.2-delta-pack.zip"
wget "ftp://ftp.halifax.rwth-aachen.de/eclipse//technology/swtbot/helios/dev-build/org.eclipse.swtbot-2.0.5.20111003_1754-3676ac8-dev-e36.zip"
wget "ftp://ftp.halifax.rwth-aachen.de/eclipse//technology/swtbot/helios/dev-build/org.eclipse.swtbot.eclipse-2.0.5.20111003_1754-3676ac8-dev-e36.zip"
wget "ftp://ftp.halifax.rwth-aachen.de/eclipse//technology/swtbot/helios/dev-build/org.eclipse.swtbot.ide-2.0.5.20111003_1754-3676ac8-dev-e36.zip"
wget "ftp://ftp.halifax.rwth-aachen.de/eclipse//technology/swtbot/helios/dev-build/org.eclipse.swtbot.eclipse.gef-2.0.5.20111003_1754-3676ac8-dev-e36.zip"
wget "ftp://ftp.halifax.rwth-aachen.de/eclipse//technology/swtbot/helios/dev-build/org.eclipse.swtbot.eclipse.test.junit3-2.0.5.20111003_1754-3676ac8-dev-e36.zip"
wget "http://ftp.halifax.rwth-aachen.de/eclipse//technology/swtbot/helios/dev-build/org.eclipse.swtbot.eclipse.test.junit4-2.0.5.20111003_1754-3676ac8-dev-e36.zip"

echo "Unpacking..."
tar xf eclipse-SDK-3.7.2-linux-gtk.tar.gz
#tar xf eclipse-SDK-3.7.2-linux-gtk-x86_64.tar.gz
unzip -oq eclipse-3.7.2-delta-pack.zip
unzip -oq org.eclipse.swtbot-2.0.5.20111003_1754-3676ac8-dev-e36.zip
unzip -oq org.eclipse.swtbot.eclipse-2.0.5.20111003_1754-3676ac8-dev-e36.zip
unzip -oq org.eclipse.swtbot.eclipse.gef-2.0.5.20111003_1754-3676ac8-dev-e36.zip
unzip -oq org.eclipse.swtbot.eclipse.test.junit3-2.0.5.20111003_1754-3676ac8-dev-e36.zip
unzip -qo org.eclipse.swtbot.ide-2.0.5.20111003_1754-3676ac8-dev-e36.zip

./eclipse  -nosplash \
   -application org.eclipse.equinox.p2.director \
   -installIU org.eclipse.swtbot.eclipse.gef.finder \
   -profileProperties org.eclipse.update.install.features=true \
   -profile SDKProfile \
   -repository http://download.eclipse.org/technology/swtbot/helios/dev-build/update-site,http://download.eclipse.org/tools/gef/updates/releases/

echo "Building..."
echo "buildHome=`pwd`" > build_local.properties
echo "buildDirectory=/tmp/pluginbuilder/br.org.archimedes.build" >> build_local.properties
echo "eclipseDir=`pwd`/eclipse" >> build_local.properties
echo "os=linux" >> build_local.properties
echo "ws=gtk" >> build_local.properties
echo "arch=x86_64" >> build_local.properties
#echo "arch=x86" >> build_local.properties

sed "s/test.eclipse.zip\=/test.eclipse.zip\=$WHERE\/br.org.archimedes.build\/eclipse-SDK-3.7.2-linux-gtk.tar.gz/" build-files/automatedTests/run-tests-template.properties > build-files/automatedTests/run-tests.properties

sed "s/PROJECT_ROOT/$WHERE/" maps/all-template.map > maps/all.map

ant
