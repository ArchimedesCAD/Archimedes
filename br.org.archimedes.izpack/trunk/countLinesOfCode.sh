#!/bin/sh
TMP_DIR=/tmp
JAVA_LIST=javas
CODE_LINES=code
EMPTY_LINE_REGEXP="^\w*$"

if [ $1 != "" ]; then
    TMP_DIR=$1  
fi

totalcount=0

for i in `find $TMP_DIR -name "*.java" -print | grep -v ".svn"`
do
newcount=`cat $i | grep -v $EMPTY_LINE_REGEXP | wc -l`
totalcount=$(($totalcount+$newcount))
done
echo "Lines of code: $totalcount"

testcount=0
for i in `find $TMP_DIR -name "*.java" -print | grep -v ".svn" | grep "tests"`
do
newcount=`cat $i | grep -v $EMPTY_LINE_REGEXP | wc -l`
testcount=$(($testcount+$newcount))
done
echo "Lines of tests: $testcount"

xmlcount=0
for i in `find $TMP_DIR -name "*.xml" -print | grep -v ".svn"`
do
newcount=`cat $i | grep -v $EMPTY_LINE_REGEXP | wc -l`
xmlcount=$(($xmlcount+$newcount))
done
echo "Lines of xml: $xmlcount"