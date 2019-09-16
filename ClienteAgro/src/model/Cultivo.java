package model;

import java.io.Serializable;

public class Cultivo implements Serializable{
	private String tipo_producto;
	private int tamanho;
	private Zona zona;
	/**
	 * @param tipo_producto
	 * @param tamanho
	 * @param zona
	 */
	public Cultivo(String tipo_producto, int tamanho, Zona zona) {
		super();
		this.tipo_producto = tipo_producto;
		this.tamanho = tamanho;
		this.zona = zona;
	}
	/**
	 * @return the tipo_producto
	 */
	public String getTipo_producto() {
		return tipo_producto;
	}
	/**
	 * @param tipo_producto the tipo_producto to set
	 */
	public void setTipo_producto(String tipo_producto) {
		this.tipo_producto = tipo_producto;
	}
	/**
	 * @return the tamanho
	 */
	public int getTamanho() {
		return tamanho;
	}
	/**
	 * @param tamanho the tamanho to set
	 */
	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}
	/**
	 * @return the zona
	 */
	public Zona getZona() {
		return zona;
	}
	/**
	 * @param zona the zona to set
	 */
	public void setZona(Zona zona) {
		this.zona = zona;
	}
}
