package entidadesTransversales;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Agricultor implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String usuario;
	private String password;
	private List<Topico> topicos;
	private int puerto;
	private String host;


	public Agricultor() {
		// TODO Auto-generated constructor stub
		topicos= new ArrayList <Topico>();
	}

	public Agricultor(String usuario, String password, String tamano) {
		super();
		this.usuario  = usuario;
		this.password = password;
		topicos= new ArrayList <Topico>();
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Topico> getTopicos() {
		return topicos;
	}

	public void setTopicos(List<Topico> topicos) {
		this.topicos = topicos;
	}
	public void addTopico(Topico top) {
		this.topicos.add(top);
	}

	public int getPuerto() {
		return puerto;
	}

	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	
}
