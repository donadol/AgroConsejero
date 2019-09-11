package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TrayIcon.MessageType;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.awt.event.ActionEvent;
import java.security.MessageDigest;
import java.security.MessageDigest.*;
import java.security.NoSuchAlgorithmException;
import javax.swing.JPasswordField;

public class LogInInterface extends JFrame {

	private JPanel contentPane;
	private JTextField Tusuario;
	private JPasswordField Tpass;
	private FarmerInterface menuInicio;
	private JFrame signUp;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogInInterface frame = new LogInInterface();
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
	public LogInInterface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Tusuario = new JTextField();
		Tusuario.setBounds(183, 85, 96, 20);
		contentPane.add(Tusuario);
		Tusuario.setColumns(10);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(93, 88, 48, 14);
		contentPane.add(lblUsuario);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setBounds(93, 139, 80, 14);
		contentPane.add(lblContrasea);
		
		JButton btnIngresar = new JButton("Iniciar sesion");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					String usuario = Tusuario.getText();
					byte[] hash = MessageDigest.getInstance("SHA-256").digest( new String (Tpass.getPassword()).getBytes() );
					String string_hash = new String(hash);
					
					if( VerificarUsuario( usuario, string_hash ) ) {
						
						menuInicio = new FarmerInterface();
						setVisible( false );
						menuInicio.setVisible( true );
					}else {
						JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "Error de inicio de sesion", JOptionPane.ERROR_MESSAGE );
					}
					
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnIngresar.setBounds(103, 208, 105, 34);
		contentPane.add(btnIngresar);
		
		JLabel lblAgroconsejero = new JLabel("AgroConsejero");
		lblAgroconsejero.setBounds(167, 11, 123, 34);
		contentPane.add(lblAgroconsejero);
		
		Tpass = new JPasswordField();
		Tpass.setBounds(183, 136, 96, 20);
		contentPane.add(Tpass);
		
		JButton btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				signUp = new SignUpInterface();
				setVisible( false );
				signUp.setVisible( true );
				
			}
		});
		btnRegistrarse.setBounds(234, 208, 105, 34);
		contentPane.add(btnRegistrarse);
	}
	
	boolean VerificarUsuario( String usuario, String hash ) {
		//TODO
		return true;
	}
}
