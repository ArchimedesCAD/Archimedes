#!/bin/sh
# Script that cleans old installers, generate all the new ones 
# and upload the packs to the internet
rm -rf target
mkdir target
izpack archimedes-full-installer.xml -b ./ \
	-o target/archimedes-full-installer-${1}.jar
izpack archimedes-full-installer.xml -b ./ \
	-o target/archimedes-online-installer-${1}.jar -k web
izpack archimedes-light-installer.xml -b ./ \
	-o target/archimedes-light-installer-${1}.jar
scp target/archimedes-online-installer-${1}.pack*.jar \
	${2}@shell.sf.net:/home/groups/a/ar/arquimedes/htdocs/update/