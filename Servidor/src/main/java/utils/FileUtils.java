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
	public static List< List <String> > leerConfiguracionServidor( ) {
		List< List< String > > servidores = new ArrayList< List <String> >();
		try {
			File archivo = new File("C:\\Users\\sistemas\\Documents\\Servidor\\server.config");
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
}
