/**
 * 
 */
package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import model.Agricultor;
import model.Dato;
import model.Topico;
import utils.Files;

/**
 * @author acer
 *
 */
public class ConnectionThread extends Thread{
	DataInputStream indata;
	ObjectInputStream in;
	ObjectOutputStream out;
	DataOutputStream outdata;
	Socket clientSocket;
	public static List<Agricultor> agrs= new ArrayList <Agricultor>();
	public static List<Topico> tops= new ArrayList <Topico>();
	public static int id=0;

	public  ConnectionThread (Socket aClientSocket) 
	{
		try 
		{
			clientSocket = aClientSocket;
			in = new ObjectInputStream(clientSocket.getInputStream());
			out =new ObjectOutputStream(clientSocket.getOutputStream());
			outdata=new DataOutputStream(clientSocket.getOutputStream());
			this.start();
		} 
		catch(IOException e)
		{
			//System.out.println("Connection:"+e.getMessage());
		}
	}


	public void run() 
	{           		  
		Dato Datollego;
		try 
		{		
			//System.out.println("1");
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			while(true)
			{
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
				}
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	} // end run

}
