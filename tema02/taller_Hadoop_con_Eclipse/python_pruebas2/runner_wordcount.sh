# chmod +x runner_wordcount.sh
echo '#Ejecutar en local:'
echo "foo foo quux labs foo bar quux" | python mapper.py
echo "foo foo quux labs foo bar quux" | python mapper.py | sort -k1,1 | python reducer.py


echo '#Ejecutar en Hadoop cluster:'
#hadoop fs -mkdir inputs/input3
#hadoop fs -put input3/* inputs/input3/
hadoop fs -rm -r -f output

hadoop jar $HADOOP_HOME/share/hadoop/tools/lib/hadoop-streaming-2.6.0.jar \
-file mapper.py    -mapper mapper.py \
-file reducer.py   -reducer reducer.py \
-input inputs/input2/* -output output

hadoop fs -cat output/*