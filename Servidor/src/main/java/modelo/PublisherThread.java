package modelo;

import java.io.IOException;
import java.util.List;

import org.jspace.Space;

import entidadesTransversales.*;
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
		for(int i=0; i<noticias.size(); ++i) {
			try {
				Thread.sleep(noticias.get(i).getTiempo()*1000);
				info.put(noticias.get(i));
				System.out.println(noticias.get(i));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
	}
}
