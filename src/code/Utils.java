/**
 * 
 */
package code;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import data.Topico;

/**
 * @author acer
 *
 */
public class Utils {
	public static ArrayList<Topico> read_file (String File_Name) throws IOException {
		ArrayList<Topico> topicos = new ArrayList<Topico>();
		FileReader f = new FileReader(File_Name);
		BufferedReader b = new BufferedReader(f);
		String cadena;
		Topico topic;
		
		while((cadena = b.readLine())!=null) {
			StringTokenizer tokens = new StringTokenizer(cadena, "*");
			int a = 0;
			String token;
			LocalTime time = LocalTime.parse("00:00");
			String tema = null;
			String ubicacion = null;
			String descripcion = null;
			
			while (tokens.hasMoreTokens()) {
				a++;
				token = tokens.nextToken();
				if(a == 1) {time = LocalTime.parse(token);}
				if(a == 2) {tema = token;}
				if(a == 3) {ubicacion = token;}
				if(a == 4) {descripcion = token;}
			}
			
			topic = new Topico(time, ubicacion, descripcion, tema);
			topicos.add(topic);
		}
		
		b.close();
		return topicos;
	}
	
	public static ArrayList<String> obtain_topics(ArrayList<Topico> topicos) {
		ArrayList<String> topics = new ArrayList<String>();
		
		for (Topico t: topicos) {
			topics.add(t.getTema());
		}
		
		Set<String> hs = new HashSet<String>();
		hs.addAll(topics);
		topics.clear();
		topics.addAll(hs);
		
		for (String s: topics) {
			System.out.println(s);
		}
		
		return topics;
	}
	
	public static ArrayList<String> obtain_location(ArrayList<Topico> topicos) {
		ArrayList<String> ubicaciones = new ArrayList<String>();
		
		for (Topico t: topicos) {
			ubicaciones.add(t.getUbicacion());
		}
		
		Set<String> hs = new HashSet<String>();
		hs.addAll(ubicaciones);
		ubicaciones.clear();
		ubicaciones.addAll(hs);
		
		for (String s: ubicaciones) {
			System.out.println(s);
		}
		
		return ubicaciones;
	}
	
	public static void main(String[] args) throws IOException {
		ArrayList<Topico> topicos = new ArrayList<Topico>();
		
		topicos = read_file("topicos.txt");
		
		/*for (Topico t: topicos) {
			System.out.println("Hora: " + String.valueOf(t.getHora_publicacion()));
			System.out.println("Ubicacion: " + t.getUbicacion());
			System.out.println("Tema: " + t.getTema());
			System.out.println("Descripcion: " + t.getDescripcion());
		}*/
	}
}
