#!/bin/sh
# Script that cleans old installers, generate all the new ones 
# and upload the packs to the internet
DEPLOY_DIR=/Users/night/Desktop/deploy
VERSION=${1}
USER=${2}

create_deployed_zip() {
    cp -r ${DEPLOY_DIR}/${1}/ ./
    zip target/Archimedes.${1}-${VERSION}.zip  -r Archimedes
    rm -Rf Archimedes
}

rm -rf target
mkdir target
izpack archimedes-full-installer.xml -b ./ \
    -o target/archimedes-full-installer-${VERSION}.jar
izpack archimedes-full-installer.xml -b ./ \
	-o target/archimedes-online-installer-${VERSION}.jar -k web
izpack archimedes-light-installer.xml -b ./ \
	-o target/archimedes-light-installer-${VERSION}.jar
create_deployed_zip "linux.gtk.x86"
create_deployed_zip "macosx.carbon.x86"
create_deployed_zip "win32.win32.x86"
scp target/archimedes-online-installer-${VERSION}.pack*.jar \
	${USER}@shell.sf.net:/home/groups/a/ar/arquimedes/htdocs/update/
rsync -avP -e ssh target/archimedes-full-installer-${VERSION}.jar \
    target/archimedes-light-installer-${VERSION}.jar \
    target/archimedes-online-installer-${VERSION}.jar \
    target/Archimedes.linux.gtk.x86-${VERSION}.zip \
    target/Archimedes.macosx.carbon.x86-${VERSION}.zip \
    target/Archimedes.win32.win32.x86-${VERSION}.zip \
    ${USER}@frs.sourceforge.net:uploads/
