/**
 * 
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author acer
 *
 */
public class Informacion implements Serializable{
	private int tipo;
	private String descripcion;
	private int tiempo;
	private List<Topico> topicos;
	
	public Informacion(int tipo, ArrayList<Topico> topicos, String descripcion) {
		super();
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.topicos= new ArrayList <Topico>(topicos);
	}
	
	public Informacion() {
		super();
		topicos= new ArrayList <Topico>();
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
