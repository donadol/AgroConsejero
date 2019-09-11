/**
 * 
 */
package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.Dato;

/**
 * @author acer
 *
 */
public class PublisherInterface extends JFrame{
	static int serverPort;
	static Socket s;	
	//ObjectInputStream in;
	static ObjectOutputStream out;
	static DataInputStream indata;
	static DataOutputStream outdata;
	static Scanner scaner;

	private JPanel contentPane;
	private JTextField topicotext;
	private JTextField archivo1;
	private JButton btnSimular;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PublisherInterface frame = new PublisherInterface();
					frame.setVisible(true);
					serverPort=7896;
					Socket s=null;
					scaner=new Scanner (System.in);
					////////////////////////////////////////////////////////////////////////////
					s= new Socket("localhost", serverPort);
					//System.out.println("4.1");
					//in = new ObjectInputStream(s.getInputStream());
					//System.out.println("4.2");
					indata=new DataInputStream(s.getInputStream());
					//System.out.println("4.3");
					out =new ObjectOutputStream(s.getOutputStream()); 
					//System.out.println("4.4");
					outdata=new DataOutputStream(s.getOutputStream());
					////////////////////////////////////////////////////////////////////////////////
					//System.out.println("5");
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PublisherInterface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 343, 290);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		topicotext = new JTextField();
		topicotext.setBounds(40, 91, 86, 20);
		contentPane.add(topicotext);
		topicotext.setColumns(10);
		
		JLabel lblTopico = new JLabel("Topico");
		lblTopico.setBounds(40, 66, 46, 14);
		contentPane.add(lblTopico);
		
		JLabel lblNoticia = new JLabel("Noticia");
		lblNoticia.setBounds(158, 66, 46, 14);
		contentPane.add(lblNoticia);
		
		JLabel lblPublicador = new JLabel("PUBLICADOR");
		lblPublicador.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPublicador.setBounds(109, 26, 118, 25);
		contentPane.add(lblPublicador);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(158, 91, 129, 47);
		contentPane.add(scrollPane);
		
		JTextArea Noticiatxt = new JTextArea();
		scrollPane.setViewportView(Noticiatxt);
		
		JButton btnPublicar = new JButton("Publicar");
		btnPublicar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					archivo1.setEnabled(false);
					btnSimular.setEnabled(false);
					
					Dato publicacion = new Dato();
					publicacion.setTopico(topicotext.getText().toUpperCase());
					publicacion.setDescripcion(Noticiatxt.getText());
					
					Calendar calendario = new GregorianCalendar();
					int hora, minutos, segundos;
					hora =calendario.get(Calendar.HOUR_OF_DAY);
					minutos = calendario.get(Calendar.MINUTE);
					segundos = calendario.get(Calendar.SECOND);
					String tiempo=hora+":"+minutos+":"+segundos;
					publicacion.setHora(tiempo);
					
					publicacion.setTipo(1);
					//System.out.println("5.1");
					out.writeObject((Object)publicacion);

					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					while(indata.available()>0) {
						//String respuesta=indata.readUTF();
						int respuesta=indata.read();
						//System.out.println("5.4");
						if(respuesta==1)
						{
							//System.out.println("RESPUESTA: PUBLICADO CORRECTAMENTE");				
						}
					}
				
					
				//////////////////////////////////////////////////////////////////////////////////////
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
				
				
			}
		});
		btnPublicar.setBounds(37, 115, 89, 23);
		contentPane.add(btnPublicar);
		
		btnSimular = new JButton("Simular");
		btnSimular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnPublicar.setEnabled(false);
				topicotext.setEnabled(false);
				Noticiatxt.setEnabled(false);
				

			        String cadena;
			        FileReader f;
					String archivo;
			        try {
						
						archivo=archivo1.getText();
						f = new FileReader(archivo);
						BufferedReader b = new BufferedReader(f);
						while((cadena = b.readLine())!=null) {
							StringTokenizer tokens = new StringTokenizer(cadena, "*");
							////System.out.println(cadena);
							int a = 0;
							String token;
							int time = 0;
							String topico = null;
							String noticia = null;
							while(tokens.hasMoreTokens()){
								a++;
								token = tokens.nextToken();
								////System.out.println(token);
								if (a == 1) {time = Integer.parseInt(token);};
								if (a == 2) {topico = token;};
								if (a == 3) {noticia = token;};
							}
							//System.out.println("********************");
							//System.out.println(time);
							//System.out.println(topico);
							//System.out.println(noticia);
							TimeUnit.SECONDS.sleep(time);
							//System.out.println("Ya pare, voy a enviar el Dato");
							
							Dato publicacion = new Dato(1,topico.toUpperCase(),noticia);
							
							Calendar calendario = new GregorianCalendar();
							int hora, minutos, segundos;
							hora =calendario.get(Calendar.HOUR_OF_DAY);
							minutos = calendario.get(Calendar.MINUTE);
							segundos = calendario.get(Calendar.SECOND);
							String tiempo=hora+":"+minutos+":"+segundos;
							publicacion.setHora(tiempo);
							
							out.writeObject((Object)publicacion);
						}
						b.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
			}
		});
		btnSimular.setBounds(198, 187, 89, 23);
		contentPane.add(btnSimular);
		
		archivo1 = new JTextField();
		archivo1.setBounds(37, 188, 148, 20);
		contentPane.add(archivo1);
		archivo1.setColumns(10);
		

		
		JLabel lblArchivo = new JLabel("Archivo");
		lblArchivo.setBounds(40, 166, 46, 14);
		contentPane.add(lblArchivo);
	}

}
