package utils;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

import entidadesTransversales.*;
import modelo.Servidor;


public class FileUtils {
	
	private static Topico getTopicoBySubtopico(String nombre, List<Topico>topicos) {
		for(int i=0; i<topicos.size();++i) {
			for(int j=0; j<topicos.get(i).getSubtopicos().size();++j) {
				if(topicos.get(i).getSubtopicos().get(j).equals(nombre)) {
					return topicos.get(i);
				}
			}
		}
		return null;
	}
	public static List<Informacion> leerInformacion(List<Topico>topicos){
		List<Informacion> info = new ArrayList<Informacion>();
		Informacion aux;
		List<Topico> topicos_aux = null;
		String contenido = "";
		try {
			contenido = new String(Files.readAllBytes(Paths.get("informacion.json")));
			JSONObject json = new JSONObject(contenido);
			JSONArray infoJson = json.getJSONArray("informacion");
			for(int i = 0; i<infoJson.length(); ++i) {
				JSONObject jsonObj = infoJson.getJSONObject(i);
				aux = new Informacion(Zona.valueOf(jsonObj.getString("zona")), jsonObj.getString("titulo"), jsonObj.getString("descripcion"), Integer.parseInt(jsonObj.getString("tiempo")));
				System.out.println(aux);
				JSONArray tagsInfo = jsonObj.getJSONArray("tags");
				topicos_aux = new ArrayList<Topico>();
				for(int j = 0; j<tagsInfo.length(); ++j) {
					//topicos_aux.add(getTopicoBySubtopico(tagsInfo.getString(j), topicos));
				}
				topicos_aux.add( new Topico("papa") );
				aux.setTopicos(topicos_aux);
				info.add( aux ); // <- EDITAR
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return info;
	}
	
	public static List< List <String> > leerConfiguracionServidor( ) {
		List< List< String > > servidores = new ArrayList< List <String> >();
		try {
			File archivo = new File("C:\\Users\\Leonardo\\Documents\\GitHub\\AgroConsejero2\\Servidor\\server.config");
			BufferedReader br = new BufferedReader( new FileReader( archivo ) );
			String linea;
			while( (linea = br.readLine()) != null ) {
				//El formato del archivo es HOST*PUERTO*PRIORIDAD
				String [] datos = linea.split("\\*");
				servidores.add( new ArrayList<String>( Arrays.asList( datos ) ));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return servidores;
	}
	
	public static void main(String[] args) {
		leerInformacion(null);
	}
}
