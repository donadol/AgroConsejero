/**
 * 
 */
package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Agricultor implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String Ubicacion;
	private String Producto;
	private String tamano;
	private List<String> topicos;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Agricultor(String ubicacion, String producto, String tamaño) {
		super();
		this.id=-1;
		Ubicacion = ubicacion;
		Producto = producto;
		this.tamano = tamaño;
		topicos= new ArrayList <String>();
	}
	
	public Agricultor() {
		// TODO Auto-generated constructor stub
		this.id=-1;
		topicos= new ArrayList <String>();
	}

	@Override
	public String toString() {
		return "Agricultor [id=" + id + ", Ubicacion=" + Ubicacion + ", Producto=" + Producto + ", tamano=" + tamano
				+ ", topicos=" + topicos + "]";
	}

	public String getUbicacion() {
		return Ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		Ubicacion = ubicacion;
	}
	public String getProducto() {
		return Producto;
	}
	public void setProducto(String producto) {
		Producto = producto;
	}

	public String getTamano() {
		return tamano;
	}

	public void setTamano(String tamano) {
		this.tamano = tamano;
	}

	public List<String> getTopicos() {
		return topicos;
	}

	public void setTopicos(List<String> topicos) {
		this.topicos = topicos;
	}
	public void addTopico(String top) {
		this.topicos.add(top);
	}
}
