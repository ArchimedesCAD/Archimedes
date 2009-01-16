#!/bin/sh
# Script that cleans old installers, generate all the new ones 
# and upload the packs to the internet
VERSION=${1}
USER=${2}
DEPLOY_DIR=/Users/night/Desktop/deploy
SHELL_DESTINATION=${USER}@web.sourceforge.net:/home/groups/a/ar/arquimedes/htdocs/update/
RSYNC_DESTINATION=${USER}@frs.sourceforge.net:uploads/

create_deployed_zip() {
    cp -r ${DEPLOY_DIR}/${1}/ ./
    zip target/Archimedes.${1}-${VERSION}.zip -r Archimedes
    rm -Rf Archimedes
}

create_source_zip() {
    cd ..
    zip br.org.archimedes.izpack/target/Archimedes.sources-${VERSION}.zip -q -r br.org.archimedes* -i \
        \*.{xml,arc,java,properties,project,classpath,txt,sh,ini,product,bmp,png,gif,ico,icns,mappings,exsd,MF,html,ttf,jar,so,dll,dylib,jnilib}
    cd br.org.archimedes.izpack
}

if [ "${1}" = "" ]; then
    echo "Usage: ${0} <version> <user>"
    exit
fi

rm -rf target
mkdir target
create_source_zip
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
	${SHELL_DESTINATION}
rsync -avP -e ssh target/archimedes-full-installer-${VERSION}.jar \
    target/archimedes-light-installer-${VERSION}.jar \
    target/archimedes-online-installer-${VERSION}.jar \
    target/Archimedes.linux.gtk.x86-${VERSION}.zip \
    target/Archimedes.macosx.carbon.x86-${VERSION}.zip \
    target/Archimedes.win32.win32.x86-${VERSION}.zip \
    target/Archimedes.sources-${VERSION}.zip \
    ${RSYNC_DESTINATION}
