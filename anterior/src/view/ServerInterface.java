package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import model.Agricultor;
import model.Informacion;
import model.Topico;
import view.ServerInterface;

public class ServerInterface extends JFrame{

	private JPanel contentPane;
	private JTable Historial;
	private int soc=7896;
	private JTable suscritos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerInterface frame = new ServerInterface();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ServerInterface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 654, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 77, 452, 209);
		contentPane.add(scrollPane);
		
		Historial = new JTable();
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		Historial.setModel(new DefaultTableModel
				(
				new Object[][] {},
				new String[] {"Tiempo","Topico", "Noticia"}
				));
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		scrollPane.setViewportView(Historial);
		
		JLabel lblServidor = new JLabel("SERVIDOR");
		lblServidor.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblServidor.setBounds(156, 22, 113, 37);
		contentPane.add(lblServidor);
		
		JButton btnActaulizar = new JButton("ACTUALIZAR");
		btnActaulizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Informacion data1 = new Informacion();
				
				data1.setTipo(99);
				
				Socket s = null;
	
					try {
						s = new Socket("localhost", soc);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ObjectOutputStream out = null;
					try {
						out = new ObjectOutputStream(s.getOutputStream());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
					ObjectInputStream in = null;
					try {
						in = new ObjectInputStream(s.getInputStream());
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					try {

						out.writeObject(data1);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

						try {
							//////System.out.println("tengo"+in.available());
							data1=(Informacion)in.readObject();
							//////System.out.println("tengo"+in.available());
							//////System.out.println("---------------------------------------------------------------------\n"+data1.toString()+"\n---------------------------------------------------------------------\n");
							//////System.out.println("tengo"+in.available());
							//////System.out.println("aaaaaaaaaaaaaaa");
							
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					

				
				
				
				Vector<List<String>> columnaip = new Vector<List<String>>();
				
				
				
				
				
				for (Topico topico:data1.getTopicos2()) {
				    //////System.out.println("----"+topico.toString());
					for (Informacion datoo : topico.getData_history()) {
						Vector<String> fila=new Vector<String>();
						fila.add(datoo.getHora());
						fila.add(topico.getNombre());
						fila.add(datoo.getDescripcion());
						columnaip.add(fila);						
					}
				}
				
				Vector<String> name = new Vector<String>();
				name.add("Tiempo");name.add("Topico");name.add("Data");
				TableModel agrs=new DefaultTableModel( columnaip,name);

				Historial.setModel(agrs);
				
				
			}
		});
		btnActaulizar.setBounds(285, 22, 123, 37);
		contentPane.add(btnActaulizar);
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int colum=Historial.getSelectedColumn();
				int filas=Historial.getSelectedRow();
				if(colum!=-1 && filas !=-1)
				{
					String tema=(String) Historial.getValueAt(filas, colum);
					////System.out.println("CONSULTA"+tema);
					
					
					Informacion data1 = new Informacion();
					
					data1.setTipo(100);
					data1.setTopico(tema);
					Socket s = null;
					try {
						s = new Socket("localhost", soc);
						ObjectOutputStream out = null;
						out = new ObjectOutputStream(s.getOutputStream());
						ObjectInputStream in = null;				
						in = new ObjectInputStream(s.getInputStream());					
						out.writeObject(data1);					
						data1=(Informacion)in.readObject();
						//////System.out.println("tengo"+in.available());
						////System.out.println("---------------------------------------------------------------------\n"+data1.toString()+"\n---------------------------------------------------------------------\n");
						//////System.out.println("tengo"+in.available());
						////System.out.println("aaaaaaaaaaaaaaa");
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
								

					
					/////////////////////////////
					
					Vector<List<String>> columnaip = new Vector<List<String>>();
					
					
						for (Agricultor agricultor : data1.getTopicos2().get(0).getAgricultor()) {
							Vector<String> fila=new Vector<String>();
							fila.add(Integer.toString(agricultor.getId()));
							columnaip.add(fila);						
						}
					
					
					Vector<String> name = new Vector<String>();
					name.add(data1.getTopicos2().get(0).getNombre());
					TableModel agrs=new DefaultTableModel( columnaip,name);

					suscritos.setModel(agrs);
				}
			}
		});
		btnConsultar.setBounds(502, 77, 89, 23);
		contentPane.add(btnConsultar);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(498, 111, 97, 175);
		contentPane.add(scrollPane_1);
		
		suscritos = new JTable();
		scrollPane_1.setViewportView(suscritos);
	}
}
