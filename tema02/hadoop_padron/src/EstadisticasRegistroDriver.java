import java.io.File;
import java.util.ArrayList;
import java.net.URI;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.Path;

public class EstadisticasRegistroDriver {

	//public static long splitSize = 5242880; //5Mb
	public static long splitSize = 2097152; //2Mb
	//public static long splitSize = 10485760; //10Mb

	public static void main(String[] args) {
		try {

			if (args.length != 2) {
				System.err.println("Usage: EstadisticasRegistro <input path> <output path>");
				System.exit(-1);
			}
			
			//Configuration conf = getConf();
			//Job job = Job.getInstance(conf);
			Job job = Job.getInstance();
			
			Configuration conf = job.getConfiguration();             
            conf.set("mapred.textoutputformat.separator", ";"); //Hadoop v2+ (YARN)

			job.setJarByClass(EstadisticasRegistroDriver.class);
			job.setJobName("EstadisticasRegistro");

			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);

			Path outPath = new Path(args[1]);
			Path inPath = new Path(args[0]);

		    FileInputFormat.addInputPath(job, inPath);
		    FileInputFormat.setMaxInputSplitSize(job, splitSize);
			FileOutputFormat.setOutputPath(job, outPath);

			System.out.println(outPath);
			System.out.println(inPath);

			job.setMapperClass(TypeOneMapper.class);
			job.setReducerClass(TypeOneReducer.class);

			job.addCacheFile(new URI(args[0]));
			
			//File file = new File(args[0]);
			//TypeOneMapper.registros = new ArrayList<String>();			
			//TypeOneMapper.registros =  (ArrayList<String>) FileUtils.readLines(file);
			
			System.out.println("************************************************");
			//System.out.println(TypeOneMapper.registros.size());
			
			TypeOneMapper.porcentajeSimilitud = 90.0;
			TypeOneReducer.criterioDistancia = 20.0;

	        System.exit(job.waitForCompletion(true) ? 0 : 1);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}