package modelo;

import org.jspace.SequentialSpace;
import org.jspace.Space;

import controlador.ServerThread;
import vista.ServerInterface;

public class Main {

	public static void main(String[] args) {
		
		Space info = new SequentialSpace();
		Servidor server = new Servidor(info);		
		PublisherThread publisher = new PublisherThread(info, null); //falta la lista de topicos
		
		ServerInterface frame = new ServerInterface();
		Thread frameThread = new Thread(frame, "Frame thread");
		
		ServerThread.setGui( frame );
		ServerThread.setEstado("");
		
		frameThread.start();

		ServerThread hiloEscucha = new ServerThread("escuchar", server);
		ServerThread hiloAtender = new ServerThread("atender", server); 
	}

}
