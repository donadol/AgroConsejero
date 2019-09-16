package modelo;

import java.net.URI;
import java.net.URISyntaxException;

import org.jspace.SequentialSpace;
import org.jspace.Space;
import org.jspace.SpaceRepository;

public class Main {

	public static void main(String[] args) {
		try {
			SpaceRepository repository = new SpaceRepository();
			Space info = new SequentialSpace();
			repository.add("info", info);
			
			URI myUri = new URI("tcp://localhost:31415/?keep");
			String gateUri = "tcp://" + myUri.getHost() + ":" + myUri.getPort() +  "?keep" ;
			System.out.println("Opening repository gate at " + gateUri + "...");
			repository.addGate(gateUri);
			
			Thread publisherThread = new Thread(new PublisherThread(info, null), "Publisher thread");//falta la lista de topicos
			publisherThread.start();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
