import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TypeOneReducer extends Reducer<Text, Text, Text, Text> {

	public static Double porcentajeSimilitud;
	public static Double criterioDistancia; //(porcentajeSimilitud / 100) <- PORCENTAJE
	public static Double criterioPorcentaje;

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

//		List<RegistroPersona> personas = new ArrayList<RegistroPersona>();
//
//	    for (RegistroPersona value : values) {
//	    	personas.add(new RegistroPersona(value));
//	    }
//
//		if (personas.size() == 2) {
//			Double porcentajeLevensthein = personas.get(0).getLevenshteinPercentage(personas.get(1), porcentajeSimilitud);
//			//if (distanciaLevensthein < criterio) {
//				Text pair = new Text(personas.get(0) + "\t\t" + personas.get(1) + "\t\t");
//				Text dist = new Text(key + ": " + porcentajeLevensthein.toString());
//				context.write(pair, dist);
//			//}
//		}

		for (Text value: values)
			context.write(key, value);

	}

}