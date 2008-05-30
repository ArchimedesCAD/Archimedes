#!/bin/sh
# Creates missing branches, tags and trunk in local project tree and adds all
# to version control.
PROJECT_LIST_FILE=projects.txt
RCP_ARCHIMEDES_DIR=$1
TMP_FILE=files
TMP_DIR=tmp

add_dir_if_absent() {
    dir=$1
    if [ ! -d $dir ]; then
        echo "\tCreating '$dir' in $i"
        mkdir -p $dir
        svn add $dir
    fi
}

cd $1
ls -1 | grep 'br.org.archimedes' > $PROJECT_LIST_FILE

for i in `cat $PROJECT_LIST_FILE`
do
    cd "$i"
    ls -1 | grep -v "branches\|tags\|trunk\|$TMP_FILE" > $TMP_FILE
    if [ ${#TMP_FILE} != 0 ]; then
        echo "$i has files other than trunk, tags or branches:"
        echo `cat $TMP_FILE`
        
        add_dir_if_absent trunk
        add_dir_if_absent branches
        add_dir_if_absent tags

        for j in `cat $TMP_FILE`
        do
            echo "\t\tMoving $i/$j to 'trunk'"
            svn mv $j trunk || (mv $j trunk && svn add trunk/$j)
        done

        rm -f $TMP_FILE
    fi
    cd ..
 done