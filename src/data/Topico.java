/**
 * 
 */
package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Topico implements Serializable{
	
	private String nombre;
	private List<Agricultor> agricultor = new ArrayList<Agricultor>();
	private List<Dato> data_history = new ArrayList<Dato>();
	
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


	/**
	 * @return the agricultor
	 */
	public List<Agricultor> getAgricultor() {
		return agricultor;
	}


	/**
	 * @param agricultor the agricultor to set
	 */
	public void setAgricultor(List<Agricultor> agricultor) {
		this.agricultor = agricultor;
	}


	/**
	 * @return the data_history
	 */
	public List<Dato> getData_history() {
		return data_history;
	}


	/**
	 * @param data_history the data_history to set
	 */
	public void setData_history(List<Dato> data_history) {
		this.data_history = data_history;
	}
	
	public void addData(Dato d) {
		data_history.add(d);

	}
}
