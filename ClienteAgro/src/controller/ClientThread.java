/**
 * 
 */
package controller;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

import entidadesTransversales.*;

/**
 * @author sistemas
 *
 */
public class ClientThread implements Runnable{

	Agricultor agricultor;
	
	public ClientThread(Agricultor a) {
		super();
		this.agricultor = a;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			System.out.println("Cliente: conectando al host: " + agricultor.getHost() + ":" + agricultor.getPort());
			Socket socket = new Socket(agricultor.getHost(), agricultor.getPort());
			System.out.println("Conexión establecida");
			System.out.println("Host: " + agricultor.getHost() + "Port: " + agricultor.getPort());
			
			ObjectOutputStream out;
			out = new ObjectOutputStream(socket.getOutputStream());
			
			System.out.println(agricultor.getNombre());
			System.out.println(agricultor.getCultivo().getTipo_producto());
			System.out.println(agricultor.getCultivo().getTamanho());
			System.out.println(agricultor.getCultivo().getZona().toString());
			
			out.writeObject(agricultor);
			
			socket.close();
			/*ObjectInputStream in;
			in = new ObjectInputStream(socket.getInputStream());
			
			int new_port = (Integer) in.readObject();
			
			agricultor.setSelfPort(String.valueOf(new_port));
			socket.close();*/
			
			DatagramSocket ds = new DatagramSocket(agricultor.getPort());
			while (true) {
				byte[] b = new byte[1024];
				DatagramPacket dp = new DatagramPacket(b, 1024);
				ds.receive(dp);
				ByteArrayInputStream br = new ByteArrayInputStream();
				//ObjectInputStream in = new ObjectInputStream(new );
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
