package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONObject;

import modelo.Informacion;
import modelo.Topico;

public class FileUtils {
	
	private Topico getTopicoBySubtopic(String nombre, List<Topico>topicos) {
		
		
		return null;
	}
	public static List<Informacion> leerInformacion(List<Topico>topicos) throws IOException{
		List<Informacion> info = new ArrayList<Informacion>();
		Informacion aux;
		List<Topico> topicos_aux;
		String contenido = new String(Files.readAllBytes(Paths.get("informacion.json")));
		JSONObject json = new JSONObject(contenido);
		JSONArray infoJson = json.getJSONArray("informacion");
		for(int i = 0; i<infoJson.length(); ++i) {
			JSONObject jsonObj = infoJson.getJSONObject(i);
			aux = new Informacion(Integer.parseInt(jsonObj.getString("tipo")), jsonObj.getString("titulo"), jsonObj.getString("descripcion"), Integer.parseInt(jsonObj.getString("tiempo")));
			JSONArray tagsInfo = jsonObj.getJSONArray("tags");
			
		}
		
		
		return info;
	}
}
