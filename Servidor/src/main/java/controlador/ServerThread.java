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
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.jspace.ActualField;
import org.jspace.FormalField;

import java.util.Set;

import entidadesTransversales.*;
import vista.ServerInterface;
import modelo.Servidor;

public class ServerThread extends Thread{
	
	private int puerto = 4200;
	private Coordinador coordinador;
	private Servidor servidor;
	private String operacion;
	
	private static ServerInterface gui;
	private static ArrayList< Zona > zonas;
	private HashMap< String, Integer >servidores;
	
	public static int id=0;

	public  ServerThread ( String operacion ) {
		this.operacion = operacion;
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
	
	public void run() {           		  
		if( operacion.equals("atender"))
			atender();
		else if (operacion.equals("escuchar")) {
			coordinador = new Coordinador("elegir", servidores);
			escuchar();
		}
			
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
								datosServidores = servidores.entrySet();
							}
							if( Coordinador.getIp().equals("localhost") ) {
								agricultor.setHost( s.getInetAddress().getHostAddress() );
								agricultor.setPuerto( s.getPort() );
								
								for( Entry<String, Integer> server : datosServidores )
								{	
									SendTCPMessage( server.getKey(), server.getValue().intValue() ,(Object ) agricultor );
								}
								gui.EnviarMensajeExito();
							}
							break;
						case SUSCRIBIR: //Suscribir un usuario a todos los servidores activos
							synchronized( this ) {
								servidor.RegistrarUsuario(agricultor);
								datosServidores = servidores.entrySet();
							}
							if( Coordinador.getIp().equals("localhost") ) {								
								for( Entry<String, Integer> server : datosServidores )
								{	
									SendTCPMessage( server.getKey(), server.getValue().intValue() ,(Object ) agricultor );
								}
							}
							s.close();	
							gui.EnviarMensajeExito();
							break;
						
						case ERROR: //Notificar que el usuario no se pudo Registrar
							gui.EnviarMensajeError();
							s.close();
							break;
							
						default:
							break;
						
					}
					
				}else if ( dato instanceof String) {
					//Comunicacion Servidor -> Servidor
					
				}else if ( dato instanceof Zona ) {
					//Comunicacion Coordinador -> Servidor
					synchronized ( this ) {						
						zonas.add( (Zona) dato );
					}
				}
				
				
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}

/*
Datollego = (Dato) in.readObject();
if (Datollego.getTipo()==1) //PUBLICADOOOOR
{
	Topico topic=Files.buscarPoslista(tops, Datollego.getTopico());			
	if(topic!=null)
	{
		topic.getData_history().add(Datollego);
	}
	else
	{
		Topico topiconuevo=new Topico(Datollego.getTopico());
		topiconuevo.addData(Datollego);
		tops.add(topiconuevo);
	}
	//System.out.println("4");					
	//outdata.writeUTF("--PUBLICADO CORRECTAMENTE--");
	outdata.write(1);					
	//System.out.println("3");
	//System.out.println("-------------------------------------------------\n"+tops.toString());
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
}
if (Datollego.getTipo()==2)//DATOS AGRICULTOR
{
	boolean esta=false;
	//System.out.println("->"+Datollego.getAgricultor().toString());

	if(Datollego.getAgricultor().getId()==-1)
	{	
		Datollego.getAgricultor().setId(id);
		outdata.writeInt(id);
		(id)++;
	}
	agrs.add(Datollego.getAgricultor());
	//System.out.println("-->>"+Datollego.getAgricultor().toString());
	for (String suscrib : Datollego.getAgricultor().getTopicos())
	{
		for (Topico topic : tops) {
			esta=false;
			if(topic.getNombre().equals(suscrib))
			{
				for (Agricultor agric : topic.getAgricultor()) {
					if(agric.getId()==Datollego.getAgricultor().getId())
					{
						esta=true;
						break;
					}
				}
				if(esta==false)
				{
					topic.getAgricultor().add(Datollego.getAgricultor());									
				}
			}
		}						
	}
	//System.out.println(tops.toString());

}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
if (Datollego.getTipo()==99)//Actualizar informacion interfasserver
{
	//System.out.println("ENTREEEE");
	Dato datooolista=new Dato();
	datooolista.setTopicos2(tops);
	//System.out.println("TENGO: "+datooolista.gettops().size());	
	out.writeObject((Object)datooolista);

}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
if (Datollego.getTipo()==100)//Actualizar CONSULTA interfasserver
{
	//System.out.println("ENTREEEE100");
	Dato datooolista=new Dato();
	Topico topictemp = null;
	for (Topico topic : tops) {
		if(topic.getNombre().equals(Datollego.getTopico()))
		{
			topictemp=new Topico(topic.getNombre());
			for (Agricultor agri : topic.getAgricultor()) {
				topictemp.getAgricultor().add(agri);							
			}
		}
	}
	datooolista.getTopicos2().add(topictemp);
	//System.out.println("TENGO100: "+datooolista.gettops().size());	

	out.writeObject((Object)datooolista);
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
if (Datollego.getTipo()==98)//Actualizar informacion interfas user
{
	//System.out.println("\n*****************************\n"+Datollego.toString()+"\n***********************");

	Agricultor agricultor=new Agricultor();
	Dato datooolista=new Dato();

	//System.out.println("ENTRE 98");
	agricultor=Datollego.getAgricultor();
	//System.out.println("\n*****************************\n"+agricultor.toString()+"\n***********************");
	for (String topi : agricultor.getTopicos()) {
		for (Topico topico : tops) {
			if(topico.getNombre().equals(topi))
			{
				//datooolista.gettops().add(topico);
				Topico topicotemp=new Topico(topi);
				//System.out.println("--------------TOPICAO: "+topi+"\n-----datooo---------------------------------------");
				for (Dato datooo:topico.getData_history())
				{
					topicotemp.addData(datooo);
				}
				datooolista.getTopicos2().add(topicotemp);

			}
		}
	}
	try {
		out.flush();
	} catch (Exception e) {
		//System.out.println("NO flush");
	}
	out.writeObject((Object)datooolista);

	//out.flush();
}*/
