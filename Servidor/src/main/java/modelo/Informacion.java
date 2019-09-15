package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Informacion implements Serializable{
	private static final long serialVersionUID = 1L;
	private int tipo;
	private String titulo;
	private String descripcion;
	private int tiempo;
	private List<Topico> topicos;
	
	public Informacion() {
		super();
		topicos= new ArrayList <Topico>();
	}
	
	public Informacion(int tipo, String titulo, String descripcion, int tiempo) {
		super();
		this.titulo=titulo;
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.tiempo = tiempo;
		topicos = new ArrayList<Topico>();
	}
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public int getTiempo() {
		return tiempo;
	}
	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}
	public List<Topico> getTopicos() {
		return topicos;
	}
	public void setTopicos(List<Topico> topicos) {
		this.topicos = topicos;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
