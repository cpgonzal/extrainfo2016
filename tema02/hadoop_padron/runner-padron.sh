#hadoop fs -copyFromLocal input/ input
#rm -rf output
hadoop fs -rm -r -f output2

hadoop jar build/jar/EstadisticasRegistro.jar input/padron.txt output2
echo 'Job Input'
echo '----------'
echo ''
#ls -la input
hadoop fs -ls input/*
echo ''
echo 'Job Output'
echo '----------'
#cat output/part-r-00000
hadoop fs -cat output2/part-r-00000