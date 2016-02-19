package hadoop_pruebas2;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;

import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.JobClient;

public class WordC  {
	
	public static void main(String[] args) throws Exception {
				
		//Configuration conf1 = new Configuration();
		//JobConf conf = new JobConf(conf1, WordC.class);
		JobConf job = new JobConf(WordC.class);
		job.setJobName("WordCount");
		
		//Job job = Job.getInstance(conf, "JobName");
		job.setJarByClass(hadoop_pruebas2.WordC.class);
		
		job.setMapperClass(WordCMapper.class);
		job.setReducerClass(WordCReducer.class);

		// TODO: specify output types
		   
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

        Path inp = new Path(args[0]);
        Path out = new Path(args[1]);
        //the hdfs input and output directory to be fetched from the command line

		// TODO: specify input and output DIRECTORIES (not files)
		FileInputFormat.setInputPaths(job, inp);
		FileOutputFormat.setOutputPath(job, out);
        
		JobClient.runJob(job);
		
		//if (!job.waitForCompletion(true))
		//	return;
	}

}
