import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class RegistroPersona implements WritableComparable<RegistroPersona>, Comparable<RegistroPersona> {

	private LongWritable id;
	private BooleanWritable varon;
	private Text nombre;
	private Text apellido1;
	private Text apellido2;
	private DoubleWritable frecNombre;
	private DoubleWritable frecApellido1;
	private DoubleWritable frecApellido2;
	private Text nif;
	private Text fechaNacimiento;

	//private static final String regexp = "(.*)(;(.*))*$";
	private static final String regexp = "(.*);(.*);(.*);(.*);(.*);(.*);(.*);(.*);(.*);(.*);(.*);(.*);(.*);(.*);(.*)";

	public RegistroPersona() {
		id = new LongWritable();
		varon = new BooleanWritable();
		nombre = new Text();
		apellido1 = new Text();
		apellido2 = new Text();
		frecNombre = new DoubleWritable();
		frecApellido1 = new DoubleWritable();
		frecApellido2 = new DoubleWritable();
		nif = new Text();
		fechaNacimiento = new Text();
	}

	public RegistroPersona(RegistroPersona registroPersona) {
		id = new LongWritable(registroPersona.id.get());
		varon = new BooleanWritable(registroPersona.varon.get());
		nombre = new Text(registroPersona.nombre);
		apellido1 = new Text(registroPersona.apellido1);
		apellido2 = new Text(registroPersona.apellido2);
		frecNombre = new DoubleWritable(registroPersona.frecNombre.get());
		frecApellido1 = new DoubleWritable(registroPersona.frecApellido1.get());
		frecApellido2 = new DoubleWritable(registroPersona.frecApellido2.get());
		nif = new Text(registroPersona.nif);
		fechaNacimiento = new Text(registroPersona.fechaNacimiento);
	}

	public void readFields(DataInput in) throws IOException {
		id.readFields(in);
		varon.readFields(in);
		nombre.readFields(in);
		apellido1.readFields(in);
		apellido2.readFields(in);
		frecNombre.readFields(in);
		frecApellido1.readFields(in);
		frecApellido2.readFields(in);
		nif.readFields(in);
		fechaNacimiento.readFields(in);
	}

	public void write(DataOutput out) throws IOException {
		id.write(out);
		varon.write(out);
		nombre.write(out);
		apellido1.write(out);
		apellido2.write(out);
		frecNombre.write(out);
		frecApellido1.write(out);
		frecApellido2.write(out);
		nif.write(out);
		fechaNacimiento.write(out);
	}

	public boolean matches(String personaString) {
		// ID   ;   CProv    ; FecNac
		//	1	;  2  ; 3; 4 ;   5    ;  6   ;     7      ;    8    ;      9     ;   10    ;      11    ; 12;         13         ;    14     ; 15
		//362487;35016;35;016;19691019;NOMBRE;FRECU NOMBRE;APELLIDO1;FREC APELLI1;APELLIDO2;FREC APELLI2;DNI;00000000000000000000;00000000000;1
		Pattern pattern = Pattern.compile(regexp);
	    Matcher matcher = pattern.matcher(personaString);
	    if (matcher.find() && matcher.groupCount() == 15) {
	    	this.setId(matcher.group(1));
	    	this.setNombre(matcher.group(6));
	    	this.setApellido1(matcher.group(8));
	    	this.setApellido2(matcher.group(10));
	    	this.setFrecNombre(new DoubleWritable(Double.parseDouble(matcher.group(7))));
	    	this.setFrecApellido1(new DoubleWritable(Double.parseDouble(matcher.group(9))));
	    	this.setFrecApellido2(new DoubleWritable(Double.parseDouble(matcher.group(11))));
	    	this.setFechaNacimiento(matcher.group(5));
	    	this.setNif(matcher.group(12));
	    	this.setVaron(matcher.group(15).equals("1"));
	    	return true;
	    }
	    return false;
	}

	public Double getLevenshteinDistance(RegistroPersona next) {
		Double sumDistanciaTotal = 0.0;
		sumDistanciaTotal += StringUtils.getLevenshteinDistance(this.nombre.toString(), next.nombre.toString());
		sumDistanciaTotal += StringUtils.getLevenshteinDistance(this.apellido1.toString(), next.apellido1.toString());
		sumDistanciaTotal += StringUtils.getLevenshteinDistance(this.apellido2.toString(), next.apellido2.toString());
		sumDistanciaTotal += StringUtils.getLevenshteinDistance(this.nif.toString(), next.nif.toString());
		sumDistanciaTotal += StringUtils.getLevenshteinDistance(this.fechaNacimiento.toString(), next.fechaNacimiento.toString());
		return sumDistanciaTotal;
	}

	public Double getLevenshteinPercentage(RegistroPersona next, Double porcentaje) {
		Double maxOperationsTotal = 0.0;
		maxOperationsTotal += Math.max(this.nombre.toString().length(), next.nombre.toString().length());
		maxOperationsTotal += Math.max(this.apellido1.toString().length(), next.apellido1.toString().length());
		maxOperationsTotal += Math.max(this.apellido2.toString().length(), next.apellido2.toString().length());
		maxOperationsTotal += Math.max(this.nif.toString().length(), next.nif.toString().length());
		maxOperationsTotal += Math.max(this.fechaNacimiento.toString().length(), next.fechaNacimiento.toString().length());
		Double distanciaTotal = getLevenshteinDistance(next);
		return (1.0 - (distanciaTotal / maxOperationsTotal));
	}

	@Override
	public int compareTo(RegistroPersona o) {
		return o.id.get() < this.id.get() ? 1 : (o.id.get() > this.id.get() ? -1 : 0);
	}
	
	public String toString() {
		return id + " " + nif + " " + nombre + " " + apellido1 + " " + apellido2;
	}
	
	public BooleanWritable isVaron() {
		return varon;
	}

	public void setVaron(boolean varon) {
		this.varon.set(varon);
	}

	public Text getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre.set(nombre);
	}

	public Text getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1.set(apellido1);
	}

	public Text getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2.set(apellido2);
	}

	public Text getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif.set(nif);
	}
	
	public LongWritable getId() {
		return id;
	}

	public void setId(String id) {
		this.id = new LongWritable(Long.parseLong(id));
	}

	public DoubleWritable getFrecNombre() {
		return frecNombre;
	}

	public void setFrecNombre(DoubleWritable frecNombre) {
		this.frecNombre = frecNombre;
	}

	public DoubleWritable getFrecApellido1() {
		return frecApellido1;
	}

	public void setFrecApellido1(DoubleWritable frecApellido1) {
		this.frecApellido1 = frecApellido1;
	}

	public DoubleWritable getFrecApellido2() {
		return frecApellido2;
	}

	public void setFrecApellido2(DoubleWritable frecApellido2) {
		this.frecApellido2 = frecApellido2;
	}

	public Text getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento.set(fechaNacimiento);
	}

}