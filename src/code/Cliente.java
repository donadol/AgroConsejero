/**
 * 
 */
package code;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import data.Agricultor;
import data.Topico;

/**
 * @author acer
 *
 */
public class Cliente {

	public static Agricultor agregar_Agricultor() throws UnknownHostException {
		Agricultor agri;
		String ubicacion;
		String nombre;
		InetAddress localIp = InetAddress.getLocalHost();
		
		Scanner u = new Scanner(System.in);
		System.out.println("Introduzca su ubicacion: ");
		ubicacion = u.nextLine();
		
		Scanner n = new Scanner(System.in);
		System.out.println("Introduzca su nombre: ");
		nombre = n.nextLine();
		
		agri = new Agricultor(localIp, ubicacion, nombre);
		
		return agri;
	}
	
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
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Agricultor agri;
		agri = agregar_Agricultor();
		Socket clientSocket = new Socket("localhost", 4900);
		ArrayList<Topico> topicos = new ArrayList<Topico>();
		ArrayList<Topico> topics = new ArrayList<Topico>();
		ObjectInputStream objectInput = new ObjectInputStream(clientSocket.getInputStream());
		Object object = objectInput.readObject();
		topicos = (ArrayList<Topico>) object;
		
		for (Topico t: topicos) {
			if (t.getUbicacion().equals(agri.getUbicacion()) == true) {
				topics.add(t);
			}
		}

		print_topics(topics);
		
		clientSocket.close();
		
	}
}
