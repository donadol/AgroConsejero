package controlador;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map.Entry;

import org.jspace.ActualField;
import org.jspace.FormalField;

import java.util.Set;

import entidadesTransversales.*;
import vista.ServerInterface;
import modelo.Servidor;

public class ServerThread extends Thread{
	
	private int puerto = 4200;
	private static String estado;
	private static Servidor servidor;
	private String operacion;
	private boolean esCentral;
	private Entry< String, Integer > duplicado;
	private static ServerInterface gui;
	private static ArrayList< Zona > zonas;	
	public static int id=0;

	public  ServerThread ( String operacion, Servidor servidor ) {
		this.operacion = operacion;
		this.servidor = servidor;
		this.start();
	}
	
	public static void SendTCPMessage( String host, int puerto , Object obj ) {
		
		try {
			
			Socket socket = new Socket(host, puerto);
			ObjectOutputStream out = new ObjectOutputStream( socket.getOutputStream() );
			out.writeObject( obj );
			socket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void SendUDPMessage( String host, int src_port, int dst_port, Object obj) {
		try {
			
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream( buffer );
		out.writeObject( obj );
		out.flush();
		byte [] data = buffer.toByteArray();
		
		DatagramSocket socket = new DatagramSocket( src_port );
		DatagramPacket packet = new DatagramPacket( data, 0, data.length, InetAddress.getByName(host), dst_port);
			
		socket.send( packet );
		
		socket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() 
	{           		  
		if( operacion.equals("atender"))
			atender();
		else if (operacion.equals("escuchar")) 
			escuchar();
		else if (operacion.equals("ping"))
			ping();
			
	} // end run
	
	public void atender() {
		
		
		while( true ) {			
			//To do: COLOCAR CODIGO DE VERIFICAR QUE UNA NOTICIA LLEGO AL SISTEMA
			Object[] tupla;
			int i=-1;
			try {
				do {
					i++;
					if(i==servidor.getZonas().size())
						i=0; 
					tupla = servidor.getInfo().getp(new FormalField(Informacion.class), new ActualField(servidor.getZonas().get(i)));
				}while(tupla==null);
				ArrayList< Agricultor > destinatarios = new ArrayList< Agricultor >();
				Informacion noticia = (Informacion)tupla[0];
				synchronized( this ) {
					for( Zona zone: zonas) {
						destinatarios.addAll( servidor.filtrar( noticia, zone ) ); 
					}
					for( Agricultor destinatario : destinatarios) {
						SendUDPMessage( destinatario.getHost(), puerto , destinatario.getPuerto(), (Object)noticia );
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
	
	
public void escuchar() {
		try 
		{	
			Object dato;
			ServerSocket ss = new ServerSocket ( puerto );
			Set< Entry < String, Integer> > datosServidores;
			
			if( !esCentral ) {
				SendTCPMessage( duplicado.getKey(), duplicado.getValue(), "solicitud");
				ServerThread s_ping = new ServerThread("ping", servidor);
			}
			
			while(true)
			{		
				Socket s;
				s = ss.accept();
				ObjectInputStream in = new ObjectInputStream( s.getInputStream() );
				ObjectOutputStream out = new ObjectOutputStream( s.getOutputStream() );
				
				dato = in.readObject();
				
				if( dato instanceof Agricultor) {
					//Comunicacion Cliente -> Servidor
					Agricultor agricultor = (Agricultor) dato;
					
					switch( servidor.ValidarUsuario(agricultor) ) {
						
						case INICIAR_SESION:
							synchronized ( this ) {
								servidor.setPuerto( agricultor, s );
							}
							synchronized ( estado ) {								
								if( esCentral ) {
									agricultor.setHost( s.getInetAddress().getHostAddress() );
									agricultor.setPuerto( s.getPort() );
									
									SendTCPMessage( duplicado.getKey(), duplicado.getValue().intValue() ,(Object ) agricultor );
									gui.EnviarMensajeExito();
								}
							}
							break;
							
						case SUSCRIBIR: //Suscribir un usuario a todos los servidores activos
								synchronized( this ) {
									servidor.RegistrarUsuario(agricultor);
								}
								synchronized (estado) {									
									if( esCentral )
										SendTCPMessage( duplicado.getKey(), duplicado.getValue().intValue() ,(Object ) agricultor );
								}
							
							gui.EnviarMensajeExito();
							break;
						
						case ERROR: //Notificar que el usuario no se pudo Registrar
							gui.EnviarMensajeError();
							break;
							
						default:
							break;
						
					}
				}else if ( dato instanceof String) {
					//Comunicacion Servidor -> Servidor
					String mensaje = (String) dato;
					if( mensaje.equals("ping"))
						out.writeChars( "ACK" );
					else if (mensaje.equals("ACK"))
					{
						synchronized ( estado )
						{
							estado = mensaje;
						}
					}
					else if( mensaje.equals("solicitud")) {
						int tam = zonas.size()/2;
						while (tam > 0) {
							Zona zone = zonas.remove( tam );
							SendTCPMessage( duplicado.getKey() , duplicado.getValue() , zone );
							tam--;
						}
					}
					}else if ( dato instanceof Zona ) {
					//Comunicacion Coordinador -> Servidor
					synchronized ( this ) {						
						zonas.add( (Zona) dato );
					}
				}
				
				s.close();
				
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	
	public void ping() {
		
		while( true ) {			
			try {
				synchronized( estado ) {
					estado = "no_response";
				}
				SendTCPMessage( duplicado.getKey(), duplicado.getValue(), (Object) "ping");
				sleep( 300 );
				synchronized( estado ) {
					esCentral = estado.equals("no_response");
					if( esCentral ) {
						zonas = new ArrayList( Arrays.asList( Zona.Norte, Zona.Oriente, Zona.Sur, Zona.Occidente) ) ;
					}
				}
				sleep( 2000 );
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}