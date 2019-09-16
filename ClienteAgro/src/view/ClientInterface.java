package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Agricultor;
import utils.Utils;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class ClientInterface extends JFrame {

	private JPanel contentPane;
	private JTextField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Agricultor a = null;
				try {
					ClientInterface frame = new ClientInterface(a);
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
	public ClientInterface(Agricultor agricultor) {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogIn = new JLabel("Log In");
		lblLogIn.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogIn.setFont(new Font("Microsoft Himalaya", Font.BOLD, 30));
		lblLogIn.setBounds(180, 11, 74, 31);
		contentPane.add(lblLogIn);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(95, 78, 46, 14);
		contentPane.add(lblNombre);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setBounds(95, 113, 63, 14);
		contentPane.add(lblContrasea);
		
		txtPassword = new JTextField();
		txtPassword.setBounds(168, 110, 86, 20);
		contentPane.add(txtPassword);
		txtPassword.setColumns(10);
		
		JLabel lblName = new JLabel("New label");
		lblName.setBounds(168, 78, 46, 14);
		contentPane.add(lblName);
		lblName.setText(agricultor.getNombre());
		
		JLabel lblError = new JLabel("");
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setBounds(10, 195, 414, 14);
		contentPane.add(lblError);
		
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Utils.getHash(txtPassword.getText(), "MD5").equals(agricultor.getPassword())) {
					ClientContent f = null;
					try {
						f = new ClientContent(agricultor);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					f.setVisible(true);
					ClientInterface fr = new ClientInterface(agricultor);
					fr.setVisible(false);
				} else {
					lblError.setText("Contraseña Incorrecta");
				}
			}
		});
		btnIngresar.setBounds(168, 161, 89, 23);
		contentPane.add(btnIngresar);
	}
}
