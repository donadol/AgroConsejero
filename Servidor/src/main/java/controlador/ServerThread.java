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
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.jspace.ActualField;
import org.jspace.FormalField;

import java.util.Set;

import entidadesTransversales.*;
import vista.ServerInterface;
import modelo.Servidor;
import utils.FileUtils;

public class ServerThread extends Thread{
	
	private int puerto;
	private static String estado;
	private Servidor servidor;
	private String operacion;
	private boolean esCentral;
	private Entry< String, Integer > duplicado;
	private static ServerInterface gui;
	public static int id=0;

	public static ServerInterface getGui() {
		return gui;
	}

	public static void setGui(ServerInterface gui) {
		ServerThread.gui = gui;
		//gui.setVisible( true );
	
	}
	public static void setEstado(String estado) {
		ServerThread.estado = estado;
	}
	
	public  ServerThread ( String operacion, Servidor servidor ) {
		this.operacion = operacion;
		this.servidor = servidor;
		
		
		String host;
		int puerto, prioridad;
		List< List< String > >datos = FileUtils.leerConfiguracionServidor();
		for( List < String > lista : datos ) {
			host = lista.get(0);
			puerto = Integer.parseInt( lista.get(1));
			prioridad = Integer.parseInt( lista.get(2) );
			System.out.println( host + ":" + puerto + ":" + prioridad);
			
			try {
				if( host.equals( InetAddress.getLocalHost().getHostAddress()) ) {
					this.puerto = puerto;
					if( prioridad == 1) {
						esCentral = true;
						servidor.setZonas( new ArrayList( Arrays.asList( Zona.Norte, Zona.Oriente, Zona.Sur, Zona.Occidente) ) ) ;
					}
				}else {
					this.duplicado = new SimpleEntry<String, Integer>( host, puerto);
				}
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
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
			//e.printStackTrace();
			System.out.println("No se alcanzo el servidor auxiliar");
			
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
			Object[] tupla = null;
			int i=-1;
			
			try {
					System.out.println( "El sistema tiene " + servidor.getAgricultores().size() );
										
						do {
							if( servidor.getInfo().size() > 0) {
																
								i++;
								if(i==servidor.getZonas().size())
									i=0;
								
								tupla = servidor.getInfo().getp(new FormalField(Informacion.class), new ActualField(servidor.getZonas().get(i)));
								//System.out.println( servidor.getInfo().size() );
								
							}	
						}while(tupla==null);

				System.out.println("Salio del while");
				
				ArrayList< Agricultor > destinatarios = new ArrayList< Agricultor >();
				Informacion noticia = (Informacion)tupla[0];
				synchronized( this ) {
					System.out.println("El servidor tiene " + servidor.getAgricultores().size() );
					for( Zona zone: servidor.getZonas() ) {
						destinatarios.addAll( servidor.filtrar( noticia, zone ) ); 
					}
					for( Agricultor destinatario : destinatarios) {
						SendUDPMessage( destinatario.getHost(), puerto , destinatario.getPort(), (Object)noticia );
						gui.ActualizarLog("Envio", destinatario.getNombre(), destinatario.getCultivo().getZona().toString() , noticia.getTitulo() );
						
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
	
	
public void escuchar() {
		Socket s = null; 
		ServerSocket ss = null;
		
		try 
		{	
			ss = new ServerSocket ( puerto );
			Object dato;
			Set< Entry < String, Integer> > datosServidores;
			
			if( !esCentral ) {
				SendTCPMessage( duplicado.getKey(), duplicado.getValue(), "solicitud");
				ServerThread s_ping = new ServerThread("ping", servidor);
			}
			
			while(true)
			{		
				s = ss.accept();
				ObjectInputStream in = new ObjectInputStream( s.getInputStream() );
				ObjectOutputStream out = new ObjectOutputStream( s.getOutputStream() );
				
				dato = in.readObject();
				
				if( dato instanceof Agricultor) {
					//Comunicacion Cliente -> Servidor
					Agricultor agricultor = (Agricultor) dato;
					System.out.println( "CULTIVO: " + agricultor.getCultivo() );
					switch( servidor.ValidarUsuario(agricultor) ) {
						
						case INICIAR_SESION:
							gui.ActualizarLog("Iniciar Sesion", agricultor.getNombre(), agricultor.getCultivo().getZona().toString() , "");
							synchronized ( this ) {
								servidor.setPuerto( agricultor, s );
							}
							synchronized ( estado ) {								
								if( esCentral ) {
									agricultor.setHost( s.getInetAddress().getHostAddress() );
									agricultor.setPort( s.getPort() );
									
									SendTCPMessage( duplicado.getKey(), duplicado.getValue().intValue() ,(Object ) agricultor );
									//gui.EnviarMensajeExito();
								}
							}
							break;
							
						case SUSCRIBIR: //Suscribir un usuario a todos los servidores activos
								System.out.println( "gui" +  gui );
								//System.out.println( "cultivo" + agricultor.getCultivo() );
								gui.ActualizarLog("Suscribir", agricultor.getNombre(), agricultor.getCultivo().getZona().toString() , "");
								synchronized( this ) {
									servidor.RegistrarUsuario(agricultor);
									
								}
								synchronized (estado) {									
									
									if( esCentral ) {
										agricultor.setHost( s.getInetAddress().getHostAddress() );
										agricultor.setPort( s.getPort() );
										
										SendTCPMessage( duplicado.getKey(), duplicado.getValue().intValue() ,(Object ) agricultor );
										
									}
									
								}
								
								gui.EnviarMensajeExito( s );
							break;
						
						case ERROR: //Notificar que el usuario no se pudo Registrar
							gui.ActualizarLog("ERROR", agricultor.getNombre(), agricultor.getCultivo().getZona().toString() , "");
							gui.EnviarMensajeError();
							break;
							
						default:
							break;
						
					}
				}else if ( dato instanceof String) {
					//Comunicacion Servidor -> Servidor
					String mensaje = (String) dato;
					if( mensaje.equals("ping")) {
						out.writeChars( "ACK" );
					}
					else if (mensaje.equals("ACK"))
					{
						synchronized ( estado )
						{
							estado = mensaje;
						}
					}
					else if( mensaje.equals("solicitud")) {
						synchronized( this ) {							
							int tam = servidor.getZonas().size()/2;
							while (tam > 0) {
								Zona zone = servidor.getZonas().remove( tam );
								SendTCPMessage( duplicado.getKey() , duplicado.getValue() , zone );
								tam--;
							}
						}
					}
					}else if ( dato instanceof Zona ) {
					//Comunicacion Coordinador -> Servidor
					synchronized ( this ) {						
						servidor.getZonas().add( (Zona) dato );
					}
				}
				if( s != null)
					s.close();
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		finally {
			try {
				
				if( ss != null)
					ss.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
						servidor.setZonas( new ArrayList( Arrays.asList( Zona.Norte, Zona.Oriente, Zona.Sur, Zona.Occidente) ) ) ;
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