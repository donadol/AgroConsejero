package modelo;

import java.util.List;

import org.jspace.Space;

import entidadesTransversales.Informacion;
import utils.FileUtils;

public class PublisherThread implements Runnable{
	private Space info;
	private List<Informacion> noticias;
	public PublisherThread(Space info){
		this.info = info;
		noticias = FileUtils.leerInformacion();
	}
	public void run() {
		for(int i=0; i<noticias.size(); ++i) {
			try {
				Thread.sleep(noticias.get(i).getTiempo()*1000);
				info.put(noticias.get(i), noticias.get(i).getZona());
				System.out.println(noticias.get(i));
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
	}
}
