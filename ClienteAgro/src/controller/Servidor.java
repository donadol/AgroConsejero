package controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import entidadesTransversales.*;

public class Servidor {
	public static void main (String args[]) throws IOException, ClassNotFoundException {
		
		DataOutputStream outData;
		
		try {
			ServerSocket ss = new ServerSocket(4980);
			System.out.println("Esperando conexiones: ");
			Socket socket = ss.accept();
			System.out.println("Conexión de : " + socket);
			
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			List<Agricultor> agrs = (List<Agricultor>) in.readObject();
			System.out.println("Received [" + agrs.size() + "] messages from: " + socket);
			
			for (Agricultor a: agrs) {
				System.out.println("//----------------------------------------------------//");
				System.out.println("Nombre: " + a.getNombre());
				System.out.println("Password: " + a.getPassword());
				System.out.println("Port: " + a.getPort());
				System.out.println("Host: " + a.getHost());
				System.out.println("Topicos: ");
				for (Topico t: a.getTopicos()) {
					System.out.println("\tTopico: " + t.getNombre());
				}
				System.out.println("Cultivos: ");
				System.out.println("(-------------------------------------------------------------");
				System.out.println("\tCultivo: " + a.getCultivo().getTipo_producto());
				System.out.println("\tTamaño: " + a.getCultivo().getTamanho());
				System.out.println("\tZona: " + a.getCultivo().getZona());
			}
			
			socket.close();
			ss.close();
			
			try {
				ServerSocket serverSocket = new ServerSocket(4900);
				System.out.println("Esperando conexiones: ");
				
				while (true) {
					
					Socket socket2 = serverSocket.accept();
					System.out.println("Conexión de : " + socket2);
					ServerThread hilo = new ServerThread(socket2);
					Thread t = new Thread(hilo);
					t.start();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
				System.out.println("Conexión de : " + socket);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
