/**
 * 
 */
package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import model.Informacion;
import model.Topico;

public class Files {
	public static Topico buscarPoslista(List<Topico> topicos, String topico)
	{
		for (Topico topico2 : topicos) {
			if (topico2.getNombre().equals(topico)) {
				return topico2;
			}
		}		
		return null;
	}
	private Topico getTopicoBySubtopic(String nombre, List<Topico>topicos) {
		
		
		return null;
	}
	public static List<Informacion> leerInformacion(List<Topico>topicos){
		List<Informacion> info = new ArrayList<Informacion>();
		StringTokenizer token, token2;
		Informacion aux;
		List<Topico> topicos_aux;
		BufferedReader br = null;
		FileReader fr = null;

		try {
			fr = new FileReader("topicos.txt");
			br = new BufferedReader(fr);
			

			String linea, linea2;
			while((linea=br.readLine())!=null) {
				 token = new StringTokenizer(linea, "*");
				 aux = new Informacion();
				 aux.setTipo(Integer.parseInt(token.nextToken()));
				 aux.setTiempo(Integer.parseInt(token.nextToken()));
				 linea2 = token.nextToken();
				 token2 = new StringTokenizer(linea2, " ");
				 topicos = new ArrayList<Topico>();
				 while(token2.hasMoreTokens()) {
					 topicos_aux.add(token2.nextToken());
				 }
				 aux.setTopicos(topicos);
				 aux.setDescripcion(token.nextToken());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (fr != null) {
					fr.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return info;
	}
}
