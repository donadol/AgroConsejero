
package controlador;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map.Entry;

import modelo.Servidor;

public class Coordinador extends Thread {

	private String operacion;
	private static String ip;
	private int prioridad;
	private HashMap< String , Integer >servidores;
	
	public Coordinador( String operacion ) {
		this.operacion = operacion;
		this.start();
		
	}
	
	public Coordinador( String operacion , HashMap< String, Integer> servidores) {
		this.servidores = servidores;
		this.operacion = operacion;
		this.start();
		
	}
	
	public void run() {
		
		while( true ) {
			
			if( operacion.equals( "elegir" ) ) {
				
				for( Entry < String, Integer> entry : servidores.entrySet()) {
					if( entry.getKey().equals( InetAddress.getLocalHost().getHostAddress() ))
						break;
					else
						ServerThread.SendTCPMessage( entry.getKey() , entry.getValue() , "elegir");
				}
			}
			
		}
		
	}

	public static String getIp() {
		return ip;
	}

	public static void setIp(String ip_addr) {
		ip = ip_addr;
	}	
}
