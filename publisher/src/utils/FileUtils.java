package utils;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import entidadesTransversales.*;


public class FileUtils {
	public static List<Informacion> leerInformacion(List<Topico>topicos){
		List<Informacion> info = new ArrayList<Informacion>();
		Informacion aux;
		Topico topico;
		List<String> subtopicos;
		String contenido = "";
		try {
			contenido = new String(Files.readAllBytes(Paths.get("informacion.json")));
			JSONObject json = new JSONObject(contenido);
			JSONArray infoJson = json.getJSONArray("informacion");
			for(int i = 0; i<infoJson.length(); ++i) {
				JSONObject jsonObj = infoJson.getJSONObject(i);
				JSONArray tagsInfo = jsonObj.getJSONArray("tags");
				subtopicos = new ArrayList<String>();
				for(int j = 0; j<tagsInfo.length(); ++j) {
					subtopicos.add(tagsInfo.getString(j));
				}
				topico = new Topico(jsonObj.getString("topico"), subtopicos);
				aux = new Informacion(Zona.valueOf(jsonObj.getString("zona")), jsonObj.getString("titulo"), jsonObj.getString("descripcion"), Integer.parseInt(jsonObj.getString("tiempo")), topico);
				System.out.println(aux);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return info;
	}
	public static void main(String[] args) {
		leerInformacion(null);
	}
}
