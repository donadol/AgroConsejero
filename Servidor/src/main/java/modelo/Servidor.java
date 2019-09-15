package modelo;

import java.net.Socket;
import java.util.ArrayList;

import entidadesTransversales.*;

public class Servidor {
	
	private ArrayList < Agricultor > agricultores;
	public enum EstadoUsuario { SUSCRIBIR , INICIAR_SESION, ERROR }  
	
	public Servidor() {
		agricultores = new ArrayList<Agricultor>();
	}
	
	public EstadoUsuario ValidarUsuario( Agricultor agricultor ) {
		
		for( Agricultor usuario : agricultores ) {
			if( usuario.getUsuario().equals( agricultor.getUsuario() ) ) {
				
				if( usuario.getPassword().equals( agricultor.getPassword() ) )
					return EstadoUsuario.INICIAR_SESION;
				else
					return EstadoUsuario.ERROR;
				
			}
		}
		return EstadoUsuario.SUSCRIBIR;
	}
	
	public void RegistrarUsuario( Agricultor agricultor ) {
		
		agricultores.add( agricultor );
		
	}
	
	public void setPuerto(Agricultor agricultor, Socket puerto) {
		
		for( Agricultor usuario: agricultores) {
			
			if(  usuario.getUsuario().equals( agricultor.getUsuario()) ){
				usuario.setPuerto( puerto.getPort() );
				usuario.setHost( puerto.getInetAddress().getHostAddress());
				break;
			}
		}
	}
	
	public ArrayList< Agricultor >filtrar( Informacion noticia, Zona zona){
		
		ArrayList< Agricultor >destinatarios = new ArrayList<Agricultor>();
		for( Agricultor agricultor: agricultores ) {
			
			//if( agricultor.getCultivos().getZona() == zona) {
				
				for (Topico topi : agricultor.getTopicos()) {
						if(noticia.getTopicos().contains( topi )) {
							destinatarios.add( agricultor );
							break;
						}
				}
			//}
		}
		return destinatarios;
	}
	
}
