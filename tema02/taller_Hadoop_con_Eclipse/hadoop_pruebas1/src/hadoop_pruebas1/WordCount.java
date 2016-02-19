
//Ejecutar en terminal:
//hadoop fs -rm -r inputs/input1
//hadoop fs -rm -r output
//hadoop fs -mkdir inputs/input1
//hadoop fs -put input/sample.txt inputs/input1/
//hadoop jar build-ant/jar/WordCount.jar hadoop_pruebas1.WordCount inputs/input1 output
//hadoop fs -cat output/*

//Ejecutar desde eclipse (input y output en local)

package hadoop_pruebas1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
//import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
//import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.io.IntWritable;

public class WordCount extends Configured  implements Tool{

	public int run(String[] args) throws Exception {
		//Configuration conf = new Configuration();
		
		JobConf conf = new JobConf(getConf(), WordCount.class);
		conf.setJobName("WordCount");
		
		// TODO: specify output types
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(IntWritable.class);
		
		//Job job = Job.getInstance(conf, "JobName");
		conf.setJarByClass(WordCount.class);
		// TODO: specify a mapper
		conf.setMapperClass(WordCountMapper.class);
		// TODO: specify a reducer
		conf.setReducerClass(WordCountReducer.class);

		// TODO: specify input and output DIRECTORIES (not files)
		//FileInputFormat.setInputPaths(job, new Path("src"));
		//FileOutputFormat.setOutputPath(job, new Path("out"));
		
        Path inp = new Path(args[0]);
        Path out = new Path(args[1]);
        //the hdfs input and output directory to be fetched from the command line
        FileInputFormat.addInputPath(conf, inp);
        FileOutputFormat.setOutputPath(conf, out);

        JobClient.runJob(conf);
        return 0;
		//if (!job.waitForCompletion(true))
		//	return;
	}
	
    public static void main(String[] args) throws Exception
    {
          // this main function will call run method defined above.
      int res = ToolRunner.run(new Configuration(), new WordCount(),args);
          System.exit(res);
    }

}
