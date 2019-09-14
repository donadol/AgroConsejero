package model;

import java.util.List;

import org.jspace.*;

import utils.Files;

public class FuenteInformacion implements Runnable{
	private Space info;
	private List<Informacion> noticias;
	private List<Topico>topicos;
	public FuenteInformacion(Space info, List<Topico>topicos) {
		this.info = info;
		this.topicos = topicos;
		noticias = Files.leerInformacion(topicos);
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
