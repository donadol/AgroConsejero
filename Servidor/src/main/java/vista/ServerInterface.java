package vista;

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

import controlador.ServerThread;
import modelo.Agricultor;
import modelo.Topico;

public class ServerInterface extends JFrame{

	private JPanel contentPane;
	private JTable Historial;
	private int puerto =7896;
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
					
					ServerThread hiloEscucha = new ServerThread("escuchar");
					//ServerThread hiloAtender = new ServerThread("atender"); descomentar cuando se ingrese el codigo de noticia

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
				
				
		}});
		
		btnActaulizar.setBounds(285, 22, 123, 37);
		contentPane.add(btnActaulizar);
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
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
	
	public void EnviarMensajeExito() {
		//TO DO
	}
	
	public void EnviarMensajeError() {
		//TO DO
	}
}