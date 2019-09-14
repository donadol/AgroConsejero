/**
 * 
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Topico implements Serializable{
	
	private String nombre;
	private List<String> subtopicos;
	
	public Topico(String nombre) {
		super();
		this.nombre = nombre;
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


	public List<String> getSubtopicos() {
		return subtopicos;
	}

	public void setSubtopicos(List<String> subtopicos) {
		this.subtopicos = subtopicos;
	}
	
	
}
