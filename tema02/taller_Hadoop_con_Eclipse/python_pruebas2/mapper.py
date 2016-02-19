#!/usr/bin/env python

'''
#Ejecutar en local:
echo "foo foo quux labs foo bar quux" | python mapper.py
echo "foo foo quux labs foo bar quux" | python mapper.py | sort -k1,1 | python reducer.py

#Ejecutar en Hadoop cluster:
hadoop fs -mkdir inputs/input3
hadoop fs -put input3/* inputs/input3/

hadoop jar $HADOOP_HOME/share/hadoop/tools/lib/hadoop-streaming-2.6.0.jar \
-file mapper.py    -mapper mapper.py \
-file reducer.py   -reducer reducer.py \
-input inputs/input3/* -output output

hadoop fs -cat output/*
'''

import sys

# input comes from STDIN (standard input)
for line in sys.stdin:
    # remove leading and trailing whitespace
    line = line.strip()
    # split the line into words
    words = line.split()
    # increase counters
    for word in words:
        # write the results to STDOUT (standard output);
        # what we output here will be the input for the
        # Reduce step, i.e. the input for reducer.py
        #
        # tab-delimited; the trivial word count is 1
        print '%s\t%s' % (word, 1)