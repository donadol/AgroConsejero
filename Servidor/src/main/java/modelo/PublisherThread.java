package modelo;

import java.io.IOException;
import java.util.List;

import org.jspace.Space;

import utils.FileUtils;

public class PublisherThread implements Runnable{
	private Space info;
	private List<Informacion> noticias;
	private List<Topico>topicos;
	public PublisherThread(Space info, List<Topico>topicos) throws IOException {
		this.info = info;
		this.topicos = topicos;
		noticias = FileUtils.leerInformacion(topicos);
	}
	public void run() {
		// TODO Auto-generated method stub
	
	}
}
