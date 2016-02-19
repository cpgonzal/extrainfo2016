import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.net.URI;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


public class TypeOneMapper extends Mapper<LongWritable, Text, Text, Text> {

	public static Double porcentajeSimilitud;
	public static ArrayList<String> registros1;
    //public static LogFactory logi;
    //= LogFactory.getLog(TypeOneMapper.class);
	public static ArrayList<String> registros = new ArrayList<String>();
	
	public static Log LOG = LogFactory.getLog(TypeOneMapper.class);
	
	
    public static Path[] localFiles;
    private File file;
   
    @Override        
    protected void setup(Context context) throws IOException, InterruptedException {
    	Configuration conf = context.getConfiguration();
        URI[] cacheFiles = context.getCacheFiles();
        System.out.println("Aaaaaaaaaas: "+cacheFiles.length);
        if (cacheFiles != null && cacheFiles.length > 0)
        {          
          try
          {
              FileSystem fs = FileSystem.get(context.getConfiguration());
              Path path = new Path(cacheFiles[0].toString());
              System.out.println("Entrsrr: "+cacheFiles.length);
              //registros = (ArrayList<String>) FileUtils.readLines(new File(path.toString()));
              //String strf = FileUtils.readFileToString(new File(path.toString()));
              //int j=registros.size();
              //System.out.println(strf);
              BufferedReader reader = new BufferedReader(new InputStreamReader(fs.open(path)));
             
              String line = null;   
              //line = reader.readLine();
              //registros1.add(line);
              while ((line = reader.readLine()) != null)
            	  registros.add(line);
              reader.close();
              
              int j=registros.size();
              System.out.println("num: "+j);
              
             } catch (IOException e) {
              e.printStackTrace();
             }
          }
    }
    
    
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String personaLeftString = value.toString();
    	RegistroPersona personaLeft = new RegistroPersona();
    	RegistroPersona personaRight = new RegistroPersona();
    	 
    	//registros1 = new ArrayList<String>();
    	//myList.add("newString");
    	

    	if (personaLeft.matches(personaLeftString)) {
		    Text outputKey;
		    Text distance;
		    
		    int j;
		    j=registros.size() ;
		    System.out.println("***********************"+registros.size());
		    LOG.info("Mapeeeeeeeeeeeeee key ");
		    
		    for (long i = personaLeft.getId().get() + 1; i < registros.size(); i++) {
		    	//outputKey = new Text(personaLeft.getId().toString());
		    	//outputKey = new Text(Long.toString(i));
	    		//distance = new Text(personaLeft.getLevenshteinPercentage(personaRight, porcentajeSimilitud).toString());
	    		//context.write(outputKey, distance);
		    	if (personaRight.matches(registros.get((int) i))) {
		    		outputKey = new Text(personaLeft.getId().toString() + "|" + personaRight.getId().toString());
		    		distance = new Text(personaLeft.getLevenshteinPercentage(personaRight, porcentajeSimilitud).toString());
		    		context.write(outputKey, distance);
		    	}
		    }
    	}

	}

}