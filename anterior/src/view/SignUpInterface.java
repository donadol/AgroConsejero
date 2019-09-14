package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class SignUpInterface extends JFrame {

	private JPanel contentPane;
	private JTextField Tusuario;
	private JPasswordField Tpass1;
	private JPasswordField Tpass2;
	private JFrame logIn; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUpInterface frame = new SignUpInterface();
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
	public SignUpInterface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombreDeUsuario = new JLabel("Nombre de usuario");
		lblNombreDeUsuario.setBounds(123, 45, 113, 14);
		contentPane.add(lblNombreDeUsuario);
		
		JLabel lblContrase = new JLabel("Contrase\u00F1a ( minimo 8 caracteres )");
		lblContrase.setBounds(123, 101, 230, 14);
		contentPane.add(lblContrase);
		
		JLabel lblNewLabel = new JLabel("Vuelva a ingresar la contrase\u00F1a");
		lblNewLabel.setBounds(123, 162, 230, 14);
		contentPane.add(lblNewLabel);
		
		Tusuario = new JTextField();
		Tusuario.setBounds(123, 70, 192, 20);
		contentPane.add(Tusuario);
		Tusuario.setColumns(10);
		
		JButton btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String usuario = Tusuario.getText();
				String pass1 = new String ( Tpass1.getPassword() );
				String pass2 = new String ( Tpass2.getPassword() );
				
				if( pass1.compareTo(pass2) != 0 )
				{
					JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden", "Error ", JOptionPane.ERROR_MESSAGE );
				}else if( pass1.length() < 8 ){
					
					JOptionPane.showMessageDialog(null, "La contraseña debe tener minimo 8 caracteres" , "Contraseña muy corta", JOptionPane.WARNING_MESSAGE);
				}else {
					
					try {
						String hash = MessageDigest.getInstance("SHA-256").digest( pass1.getBytes() ).toString();

						//Verificar que el usuario fue creado de forma exitosa
						if( CrearUsuario( usuario, hash ) ) {	
								logIn = new LogInInterface();
								setVisible( false );
								logIn.setVisible( true );
						}
						
						
					} catch (NoSuchAlgorithmException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnRegistrarse.setBounds(178, 231, 89, 23);
		contentPane.add(btnRegistrarse);
		
		JLabel lblCrearUnaCuenta = new JLabel("Crear una cuenta");
		lblCrearUnaCuenta.setBounds(178, 11, 100, 23);
		contentPane.add(lblCrearUnaCuenta);
		
		Tpass1 = new JPasswordField();
		Tpass1.setBounds(123, 126, 192, 20);
		contentPane.add(Tpass1);
		
		Tpass2 = new JPasswordField();
		Tpass2.setBounds(123, 187, 192, 20);
		contentPane.add(Tpass2);
	}
	
	boolean CrearUsuario( String usuario, String hash ) {
		//TODO
		return true;
	}
}
