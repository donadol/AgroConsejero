package vista;

import java.awt.Font;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ServerInterface extends JFrame implements Runnable{

	private JPanel contentPane;
	private JTable Historial;
	private int puerto =7896;

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
		scrollPane.setBounds(28, 77, 578, 215);
		contentPane.add(scrollPane);
		
		Historial = new JTable();
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		Historial.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Tiempo", "Operacion", "Cliente", "Zona", "Noticia"
			}
		));
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		scrollPane.setViewportView(Historial);
		
		JLabel lblServidor = new JLabel("SERVIDOR");
		lblServidor.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblServidor.setBounds(156, 22, 113, 37);
		contentPane.add(lblServidor);
		this.setVisible( true );
	}
	
	public void ActualizarLog( String Operacion, String Cliente, String Zona, String Noticia ) {
		DefaultTableModel table = (DefaultTableModel) Historial.getModel();
		Date fecha = new Date (System.currentTimeMillis());
		String formato_fecha = new SimpleDateFormat("hh:mm:ss").format( fecha );
		table.addRow( new Object[]{ formato_fecha, Operacion, Cliente, Zona, Noticia} );
	}
	
	public void EnviarMensajeExito( Socket clientSocket ) {
		
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream( clientSocket.getOutputStream() );
			out.write( Integer.valueOf( clientSocket.getPort() ) );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//TO DO
	}
	
	public void EnviarMensajeError() {
		//TO DO
	}

	public void run() {
		this.setVisible(true);
	}
}