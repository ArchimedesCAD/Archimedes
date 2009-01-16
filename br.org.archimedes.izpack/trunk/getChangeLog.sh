#!/bin/sh
VCS=svn
LOG_COMMAND=log
DATE_ONE=${1}
DATE_NOW=${2}
REPOSITORY_ADDRESS=http://svn.archimedes.org.br/public/

if [ "${1}" = "" ]; then
    echo "Usage: ${0} '<previous limit date>' '<current limit date>'\n"
    echo "This date should be in the format yyyy-MM-dd. Like 2008-12-26."
    echo "Log will be generated from the specified dates."
    echo "If the second limit is not set, the date of the execution will be used."
    exit
fi

if [ "${DATE_NOW}" = "" ]; then
    DATE_NOW=`date "+%Y-%m-%d"`
fi

${VCS} ${LOG_COMMAND} ${REPOSITORY_ADDRESS} -r {${DATE_ONE}}:{${DATE_NOW}}