package modelo;

import java.io.IOException;
import java.net.UnknownHostException;

import org.jspace.RemoteSpace;

import controlador.ServerThread;
import vista.ServerInterface;

public class Main {

	public static void main(String[] args) {
		RemoteSpace info;
		try {
			info = new RemoteSpace("tcp://127.0.0.1:31415/info?keep");
			Servidor server = new Servidor(info); 
			
			ServerInterface frame = new ServerInterface(); 
			Thread frameThread = new Thread(frame, "Frame thread");
			
			ServerThread.setGui( frame );
			ServerThread.setEstado("");
			
			ServerThread hiloEscucha = new ServerThread("escuchar", server);
			ServerThread hiloAtender = new ServerThread("atender", server); 
			
			frameThread.start();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}