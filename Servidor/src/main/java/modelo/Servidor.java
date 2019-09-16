package modelo;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.jspace.Space;
import entidadesTransversales.*;

public class Servidor {
	
	private ArrayList < Agricultor > agricultores;
	Space info;
	private List <Zona>zonas;
	public enum EstadoUsuario { SUSCRIBIR , INICIAR_SESION, ERROR }  
	
	public Servidor(Space info) {
		agricultores = new ArrayList<Agricultor>();
		this.info = info;
		zonas = new ArrayList<Zona>();
	}
	
	public ArrayList<Agricultor> getAgricultores() {
		return agricultores;
	}

	public void setAgricultores(ArrayList<Agricultor> agricultores) {
		this.agricultores = agricultores;
	}

	public Space getInfo() {
		return info;
	}

	public void setInfo(Space info) {
		this.info = info;
	}

	public List<Zona> getZonas() {
		return zonas;
	}

	public void setZonas(List<Zona> zonas) {
		this.zonas = zonas;
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
			
			if( agricultor.getCultivo().getZona() == zona) {
				
				for (Topico topi : agricultor.getTopicos()) {
						if(noticia.getTopico().equals( topi )) {
							destinatarios.add( agricultor );
							break;
						}
				}
			}
		}
		return destinatarios;
	}
	
}