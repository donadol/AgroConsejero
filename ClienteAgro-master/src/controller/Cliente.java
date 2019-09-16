/**
 * 
 */
package controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import model.Agricultor;
import utils.Utils;
import view.ClientInterface;

/**
 * @author acer
 *
 */
public class Cliente {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		List<Agricultor> agrs = new ArrayList<Agricultor>();
		
		agrs = Utils.read_file("clientes.txt");
		
		Socket socket = new Socket("localhost", 4980); //ip de coordinador
		
		ObjectOutputStream out;
		
		System.out.println("Conexión establecida");
		
		out = new ObjectOutputStream(socket.getOutputStream());
		
		System.out.println("Enviando agricultores...");
		out.writeObject(agrs);
		System.out.println("Agricultores enviados");
		socket.close();
		
		for (Agricultor a: agrs) {
			ClientInterface frame = new ClientInterface(a);
			frame.setVisible(true);
			//a.send_information();
		}
	}

}