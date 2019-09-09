/**
 * 
 */
package code;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import data.Topico;

/**
 * @author acer
 *
 */
public class Server {

	public static void print_topics (ArrayList<Topico> topicos) {
		Collections.sort(topicos, (t1, t2) -> t1.getHora_publicacion().compareTo(t2.getHora_publicacion()));
		for (Topico t: topicos) {
			while (true) {
				if (t.getHora_publicacion().equals(LocalTime.now()) == true || (t.getHora_publicacion().isBefore(LocalTime.now()))) {
					System.out.println("*******************************************************");
					System.out.println(t.getHora_publicacion());
					System.out.println(t.getTema());
					System.out.println(t.getUbicacion());
					System.out.println(t.getDescripcion());
					break;
				}
			}
		}
	}
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ArrayList<Topico> topicos = new ArrayList<Topico>();
		String File_Name;
		String s="s";
		
		do {
			Scanner in = new Scanner(System.in);
			System.out.println("Introduzca el nombre del archivo: ");
			File_Name = in.nextLine();
			topicos.addAll(Utils.read_file(File_Name));
			Scanner car = new Scanner(System.in);
			s = car.nextLine();
		} while(s.equals("s"));
		
		print_topics(topicos);
		
	}

}
