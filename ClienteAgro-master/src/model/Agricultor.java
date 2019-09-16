package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Agricultor implements Serializable{
	private static ObjectInputStream in;
	private static ObjectOutputStream out;
	
	private String nombre;
	private String password;
	private String port;
	private String host;
	private String selfPort;
	private Socket socket;
	private List<Topico> topicos;
	private List<Cultivo> cultivos;
	/**
	 * @param nombre
	 * @param password
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws NumberFormatException 
	 */
	public Agricultor(String nombre, String password, String port, String host) throws NumberFormatException, UnknownHostException, IOException {
		super();
		this.nombre = nombre;
		this.password = password;
		this.port = port;
		this.host = host;
		this.selfPort = null;
		topicos = new ArrayList<Topico>();
		cultivos = new ArrayList<Cultivo>();
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}
	/**
	 * @param port the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}
	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}
	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}
	/**
	 * @return the socket
	 */
	public Socket getSocket() {
		return socket;
	}
	/**
	 * @param socket the socket to set
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	/**
	 * @return the topicos
	 */
	public List<Topico> getTopicos() {
		return topicos;
	}
	/**
	 * @param topicos the topicos to set
	 */
	public void setTopicos(List<Topico> topicos) {
		this.topicos = topicos;
	}
	/**
	 * @return the cultivos
	 */
	public List<Cultivo> getCultivos() {
		return cultivos;
	}
	/**
	 * @param cultivos the cultivos to set
	 */
	public void setCultivos(List<Cultivo> cultivos) {
		this.cultivos = cultivos;
	}
	
	/**
	 * @return the selfPort
	 */
	public String getSelfPort() {
		return selfPort;
	}
	/**
	 * @param selfPort the selfPort to set
	 */
	public void setSelfPort(String selfPort) {
		this.selfPort = selfPort;
	}
	
	public void send_information() throws IOException {
		try {
			socket = new Socket(host, Integer.parseInt(port));
			System.out.println("Conexión establecida");
			while (true) {
				
				/*out = new ObjectOutputStream(socket.getOutputStream());
				
				System.out.println("Enviando topicos...");
				out.writeObject(this.topicos);
				System.out.println("Topicos enviados");*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
	