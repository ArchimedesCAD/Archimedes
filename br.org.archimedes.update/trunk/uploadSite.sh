#!/bin/sh
scp -r \
index.html \
site.xml \
features \
web \
plugins \
$1@shell.sf.net:/home/groups/a/ar/arquimedes/htdocs/update/
