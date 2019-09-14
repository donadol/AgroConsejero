/**
 * 
 */
package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import model.Topico;

/**
 * @author acer
 *
 */
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
	public static void leerInformacion(){
		BufferedReader br = null;
		FileReader fr = null;

		try {
			fr = new FileReader("topicos.txt");
			br = new BufferedReader(fr);
			

			String linea;
			while((linea=br.readLine())!=null) {
				 StringTokenizer token = new StringTokenizer(linea, "*");
				 while(token.hasMoreTokens()) {
					 System.out.println(token.nextToken());
				 }
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
	}
	public static void main(String[] args) {
		leerInformacion();
	}
}
