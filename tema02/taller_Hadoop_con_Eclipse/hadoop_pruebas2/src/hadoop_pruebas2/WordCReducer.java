package hadoop_pruebas2;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;


public class WordCReducer extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {

	public void reduce(Text _key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
		// process values
      int sum = 0;
        /*iterates through all the values available with a key and add them together and give the
        final result as the key and sum of its values*/
      while (values.hasNext())
      {
           sum += values.next().get();
      }
      output.collect(_key, new IntWritable(sum));
	}

}
