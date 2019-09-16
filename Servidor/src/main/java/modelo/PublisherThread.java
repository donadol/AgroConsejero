package modelo;

import java.io.IOException;
import java.util.List;

import org.jspace.FormalField;
import org.jspace.Space;

import entidadesTransversales.*;
import utils.FileUtils;

public class PublisherThread extends Thread{
	private Space info;
	private List<Informacion> noticias;
	private List<Topico>topicos;
	
	public PublisherThread(Space info, List<Topico>topicos){
		this.info = info;
		this.topicos = topicos;
		noticias = FileUtils.leerInformacion(topicos);
		this.start();
	}
	
	public void run() {
		
		while ( true )
		{
			try {
				sleep( 10000 );
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Entro a publisher thread ");
			for(int i=0; i<noticias.size(); ++i) {
				try {
					Thread.sleep(noticias.get(i).getTiempo()*1000);
					info.put(noticias.get(i), noticias.get(i).getZona());
					
					System.out.println( "p" +  info.size() );
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
