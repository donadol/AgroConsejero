package entidadesTransversales;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Agricultor implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private static ObjectInputStream in;
	private static ObjectOutputStream out;
	
	private String nombre;
	private String password;
	private int port;
	private String host;
	private String selfPort;
	private Socket socket;
	private List<Topico> topicos;
	private Cultivo cultivo;
	/**
	 * @param nombre
	 * @param password
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws NumberFormatException 
	 */
	public Agricultor(String nombre, String password, int port, String host, Cultivo cult) throws NumberFormatException, UnknownHostException, IOException {
		super();
		this.nombre = nombre;
		this.password = password;
		this.port = port;
		this.host = host;
		this.selfPort = null;
		topicos = new ArrayList<Topico>();
		this.cultivo = cult;
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
	public int getPort() {
		return port;
	}
	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
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
	public Cultivo getCultivo() {
		return cultivo;
	}
	/**
	 * @param cultivos the cultivos to set
	 */
	public void setCultivos(Cultivo cultivo) {
		this.cultivo = cultivo;
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
			socket = new Socket(host, port );
			System.out.println("Conexi�n establecida");
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
