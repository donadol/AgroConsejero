package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.ClientThread;
import entidadesTransversales.*;

import javax.swing.JLabel;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ClientContent extends JFrame {
	static int serverPort = 4900;
	static String serverIP = "192.168.1.111";
	static Socket s;	
	static ObjectInputStream in;
	static ObjectOutputStream out;
	static DataInputStream indata;
	static DataOutputStream outdata;
	private JPanel contentPane;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws ClassNotFoundException 
	 */
	public ClientContent(Agricultor a) throws UnknownHostException, IOException, ClassNotFoundException {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 654, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 77, 578, 215);
		contentPane.add(scrollPane);
		
		JLabel lblNewLabel = new JLabel("Bienvenido " + a.getNombre());
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 22, 618, 37);
		lblNewLabel.setFont(new Font("Microsoft Himalaya", Font.BOLD, 30));
		contentPane.add(lblNewLabel);
		
		table_1 = new JTable();
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Tiempo", "Titulo", "Tópico", "Zona", "Descripción"
			}
		));
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		scrollPane.setViewportView(table_1);
		
		ClientThread hilo = new ClientThread(a, this);
		Thread t = new Thread(hilo);
		t.start();
	}
	
	public void actualizarLog (Informacion noticia) {
		DefaultTableModel table = (DefaultTableModel) table_1.getModel();
		Date fecha = new Date (System.currentTimeMillis());
		String formato_fecha = new SimpleDateFormat("hh:mm:ss").format( fecha );
		table.addRow( new Object[]{ formato_fecha, noticia.getTitulo(), noticia.getTopico().getNombre(), noticia.getZona().toString(), noticia.getDescripcion()} );
	}
}
