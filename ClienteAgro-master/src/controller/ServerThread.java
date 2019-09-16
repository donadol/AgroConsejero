/**
 * 
 */
package controller;

import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author acer
 *
 */
public class ServerThread implements Runnable {
	
	private Socket socket;
	
	/**
	 * @param socket
	 */
	public ServerThread(Socket socket) {
		super();
		this.socket = socket;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			System.out.println("ThreadServer: Ha llegado una nueva conexion desde el puerto " + socket.getPort() );
			//while (true) {
				int port_salida = 5317;
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				
				System.out.println("Enviando nuevo puerto");
				
				out.writeObject(port_salida);
				
				System.out.println("Puerto enviado");
				
				DatagramSocket ds = new DatagramSocket();
				String str = "Prueba Datagram";
				
				InetAddress ip = InetAddress.getByName("localhost");
				DatagramPacket dp = new DatagramPacket(str.getBytes(), str.length(), ip, port_salida);  
			    ds.send(dp);  
			    ds.close(); 
			//}
		} catch (Exception e) {
			e.printStackTrace();
			return; 
		}
	}
	
}
