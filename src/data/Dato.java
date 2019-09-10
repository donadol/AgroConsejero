/**
 * 
 */
package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author acer
 *
 */
public class Dato implements Serializable{
	private int tipo;
	private String topico;
	private String descripcion;
	private String hora;
	private Agricultor agricultor;
	private List<Topico> topicos2;
	
	public Dato(int tipo, String topico, String descripcion) {
		super();
		this.tipo = tipo;
		this.topico = topico;
		descripcion = descripcion;
		topicos2= new ArrayList <Topico>();
	}
	
	public Dato() {
		super();
		topicos2= new ArrayList <Topico>();
	}
	
	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}
	
	public Agricultor getAgricultor() {
		return agricultor;
	}
	public void setAgricultor(Agricultor agricultor) {
		this.agricultor = agricultor;
	}
	public List<Topico> getTopicos2() {
		return topicos2;
	}
	public void setTopicos2(List<Topico> topicos2) {
		this.topicos2 = topicos2;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public String getTopico() {
		return topico;
	}
	public void setTopico(String topico) {
		this.topico = topico;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		descripcion = descripcion;
	}
}
