/**
 * 
 */
package code;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
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
		
		try {
			ServerSocket welcomeSocket = new ServerSocket(4900);
			
			while (true) {
				Socket connectionSocket = welcomeSocket.accept();
				Topico topico;
				
				//Informacion entrante del cliente
				BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				
				//Lineas de texto salientes para el cliente
				DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			
				//Objeto saliente para el cliente (En este caso topicos)
				ObjectOutputStream objectOutput = new ObjectOutputStream(connectionSocket.getOutputStream());
				
				//Objeto entrante de un cliente
				ObjectInputStream objectInput = new ObjectInputStream(connectionSocket.getInputStream());
				System.out.println("Nueva conexión entrante: " + InetAddress.getLocalHost().getHostAddress());
				
				//Escritura del objeto a enviar al cliente
				objectOutput.writeObject(topicos);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
