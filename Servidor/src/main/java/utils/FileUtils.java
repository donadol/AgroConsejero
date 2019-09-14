package utils;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import modelo.Informacion;
import modelo.Topico;

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
				aux = new Informacion(Integer.parseInt(jsonObj.getString("tipo")), jsonObj.getString("titulo"), jsonObj.getString("descripcion"), Integer.parseInt(jsonObj.getString("tiempo")));
				System.out.println(aux);
				JSONArray tagsInfo = jsonObj.getJSONArray("tags");
				topicos_aux = new ArrayList<Topico>();
				for(int j = 0; j<tagsInfo.length(); ++j) {
					topicos_aux.add(getTopicoBySubtopico(tagsInfo.getString(j), topicos));
				}
				aux.setTopicos(topicos_aux);
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
