/**
 * 
 */
package controller;

import java.io.BufferedInputStream;
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
			ObjectInputStream in;
			in = new ObjectInputStream(socket.getInputStream());
			
			int respuesta = (int) in.readObject();
			
			socket.close();
			System.out.println( respuesta );
			agricultor.setSelfPort(String.valueOf(respuesta));
			DatagramSocket ds = new DatagramSocket(Integer.parseInt(agricultor.getSelfPort()));
			while (true) {
				
				byte[] b = new byte[1024];
				DatagramPacket dp = new DatagramPacket(b, 1024);
				ds.receive(dp);
				ByteArrayInputStream br = new ByteArrayInputStream( b );
				ObjectInputStream input = new ObjectInputStream(new BufferedInputStream( br ));
				Informacion noticia = (Informacion) input.readObject();
				
				System.out.println(noticia);
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
