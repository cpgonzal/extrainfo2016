package hadoop_pruebas2;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class WordCMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
	private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
    
    public void map(LongWritable ikey, Text ivalue, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
        String line = ivalue.toString();
        StringTokenizer tokenizer = new StringTokenizer(line);
     
      //iterating through all the words available in that line and forming the key value pair
        while (tokenizer.hasMoreTokens())
        {
           word.set(tokenizer.nextToken());
           //sending to output collector which inturn passes the same to reducer
             output.collect(word, one);
        }
	}


	

}
