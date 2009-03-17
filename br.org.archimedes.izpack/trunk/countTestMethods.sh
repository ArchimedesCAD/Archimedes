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

testmethodscount=0
for i in `find $TMP_DIR -name "*.java" -print | grep -v ".svn" | grep "tests"`
do
newcount=`cat $i | grep "@Test" | wc -l`
testmethodcount=$(($testmethodcount+$newcount))
done
echo "Number of test methods: $testmethodcount"

codelinespertest=$(($totalcount/$testmethodcount))
echo "Lines of code per test method: $codelinespertest"