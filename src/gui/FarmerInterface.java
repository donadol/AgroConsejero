/**
 * 
 */
package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import data.Agricultor;
import data.Dato;
import data.Topico;

/**
 * @author acer
 *
 */
public class FarmerInterface extends JFrame{
	static int serverPort;
	static Socket s;	
	static ObjectInputStream in;
	static ObjectOutputStream out;
	static DataInputStream indata;
	static DataOutputStream outdata;

	private JPanel contentPane;
	private JTextField Ubicacion;
	private JTextField Tipo;
	private JTextField Topico;
	private JTextField Tamano;
	private JTable tablemsj;
	private JTable topicos;
	
	private static Agricultor agricultor;
	private JLabel IDLABEL;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		agricultor = new Agricultor();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					FarmerInterface frame = new FarmerInterface();
					frame.setVisible(true);
				
					//System.out.println("1");
					serverPort=7896;
					Socket s=null;
					//System.out.println("2");
					//System.out.println("3");
					
					//System.out.println("4");

					/////////////////////////////////////////////////////////////////////////////
					s= new Socket("localhost", serverPort);
					//System.out.println("4.1");
					//System.out.println("4.2");
					indata=new DataInputStream(s.getInputStream());
					//System.out.println("4.3");
					out =new ObjectOutputStream(s.getOutputStream()); 
					//System.out.println("4.4");
					outdata=new DataOutputStream(s.getOutputStream());
					in = new ObjectInputStream(s.getInputStream());
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
	public FarmerInterface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 629, 534);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsuario = new JLabel("Agricultor");
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 27));
		lblUsuario.setBounds(237, 28, 178, 34);
		contentPane.add(lblUsuario);
		
		Ubicacion = new JTextField();
		Ubicacion.setBounds(131, 118, 86, 20);
		contentPane.add(Ubicacion);
		Ubicacion.setColumns(10);
		
		Tipo = new JTextField();
		Tipo.setBounds(10, 118, 86, 20);
		contentPane.add(Tipo);
		Tipo.setColumns(10);
		
		Topico = new JTextField();
		Topico.setBounds(369, 118, 86, 20);
		contentPane.add(Topico);
		Topico.setColumns(10);
		
		Tamano = new JTextField();
		Tamano.setBounds(244, 118, 86, 20);
		contentPane.add(Tamano);
		Tamano.setColumns(10);
		
		JButton mas = new JButton("+");
		mas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				boolean esta=false;
				for (String topiesta : agricultor.getTopicos()) {
					if(topiesta.equals(Topico.getText().toUpperCase()))
					{
						esta=true;
					}
				}
				if (esta==false) {					
					agricultor.addTopico(Topico.getText().toUpperCase());	
					//System.out.println("MAAAAAAAAAAAAAAAAAAAAAAAASSS");
					//System.out.println(".................................\n"+agricultor.toString()+"\n.................................\n");
					Vector<List<String>> columnaip = new Vector<List<String>>();
					
					for (String topico:agricultor.getTopicos()) {
						Vector<String> fila=new Vector<String>();
						fila.add(topico);
						columnaip.add(fila);						
					}
					Vector<String> name = new Vector<String>();
					name.add("Topico");
					TableModel juliantia=new DefaultTableModel( columnaip,name);
					topicos.setModel(juliantia);			
				}
				
				
			}
		});
		mas.setBounds(369, 149, 89, 23);
		contentPane.add(mas);
		
		JButton Actualizar = new JButton("Actualizar");
		Actualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Dato actualizar = new Dato();
				Agricultor agricultortemp=new Agricultor();
				try {
					actualizar.setTipo(98);
					
					agricultortemp.setId(agricultor.getId());
					agricultortemp.setProducto(agricultor.getProducto());
					agricultortemp.setTamano(agricultor.getTamano());
					agricultortemp.setUbicacion(agricultor.getUbicacion());
					for (String topi : agricultor.getTopicos()) {
						agricultortemp.addTopico(topi);
					}
					
					actualizar.setAgricultor(agricultortemp);
					//System.out.println("ACTUALIZAAAAAAAAAAAR");
					//System.out.println("---------------------------------------------------------------------\n"+actualizar.toString()+"\n---------------------------------------------------------------------\n");
					out.writeObject(actualizar);
					//out.flush();
					//System.out.println("5.2");
					//System.out.println("5.3");
				} catch (IOException e) {
					e.printStackTrace();
				}
				ObjectInputStream in2 = null;
				Dato msj1 = new Dato();
				try {
					msj1=(Dato)in.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				Vector<List<String>> columnaip = new Vector<List<String>>();

				Vector<String> name = new Vector<String>();
				
				
				int mayornumDatos=-1;
				for (Topico topico:msj1.getTopicos2()) {
					name.add(topico.getNombre());
					if (topico.getData_history().size()>mayornumDatos) 
					{
						mayornumDatos=topico.getData_history().size();						
					}
				}
				//System.out.println("1111"+mayornumDatos);
				for (int i = 0; i < mayornumDatos; i++) {
					//System.out.println("2");
					Vector<String> fila=new Vector<String>();
					//System.out.println("3");
					for (Topico topico:msj1.getTopicos2()) {
						//System.out.println("4");
						try {
							fila.add("("+topico.getData_history().get(i).getHora()+") "+topico.getData_history().get(i).getDescripcion());																					
						}
						catch (Exception e) {
							fila.add(" ");
						}
						////System.out.println("FILAAAA"+fila);
					}
					columnaip.add(fila);	
					////System.out.println("Columna+"+columnaip);

				}
				TableModel juliantia=new DefaultTableModel( columnaip,name);

				tablemsj.setModel(juliantia);
				
				
				
			}
		});
		Actualizar.setBounds(10, 218, 144, 34);
		contentPane.add(Actualizar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 263, 577, 215);
		contentPane.add(scrollPane);
		
		tablemsj = new JTable();
		scrollPane.setViewportView(tablemsj);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(369, 183, 89, 64);
		contentPane.add(scrollPane_1);
		
		topicos = new JTable();
		scrollPane_1.setViewportView(topicos);
		
		JButton btnEnviar = new JButton("Enviar");
		
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				Agricultor agritemp= new Agricultor();
				
				agritemp.setId(agricultor.getId());
				
				agricultor.setUbicacion(Ubicacion.getText());
				agritemp.setUbicacion(Ubicacion.getText());

				agricultor.setTamano(Tamano.getText());
				agritemp.setTamano(Tamano.getText());

				agricultor.setProducto(Tipo.getText());		
				agritemp.setProducto(Tipo.getText());		

				for (String topi : agricultor.getTopicos()) {
					
					agritemp.addTopico(topi);
					
				}
				Dato registro = new Dato();
				try {
					registro.setTipo(2);
					registro.setAgricultor(agritemp);
					//System.out.println("5.1");
					//System.out.println("ENVIAAAAAAAAAAAR");
					//System.out.println(".................................\n"+agricultor.toString()+"\n.................................\n");
					//System.out.println("3333333"+registro.toString());
					out.writeObject(registro);
					if(agricultor.getId()==-1)
					{
						agricultor.setId(indata.readInt());						
					}
					String id=Integer.toString(agricultor.getId());
					IDLABEL.setText(id);
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Tipo.setEnabled(false);
				Tamano.setEnabled(false);
				Ubicacion.setEnabled(false);
			}
			
		});
		btnEnviar.setBounds(498, 117, 89, 133);
		contentPane.add(btnEnviar);
		
		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setBounds(10, 93, 46, 14);
		contentPane.add(lblTipo);
		
		JLabel lblUbicacion = new JLabel("Ubicacion");
		lblUbicacion.setBounds(131, 93, 70, 14);
		contentPane.add(lblUbicacion);
		
		JLabel lblTamao = new JLabel("Tama\u00F1o");
		lblTamao.setBounds(244, 93, 46, 14);
		contentPane.add(lblTamao);
		
		JLabel lblTopico = new JLabel("Topico");
		lblTopico.setBounds(369, 93, 46, 14);
		contentPane.add(lblTopico);
		
		IDLABEL = new JLabel("--");
		IDLABEL.setFont(new Font("Tahoma", Font.PLAIN, 57));
		IDLABEL.setBounds(10, 24, 167, 58);
		contentPane.add(IDLABEL);
	}
}
