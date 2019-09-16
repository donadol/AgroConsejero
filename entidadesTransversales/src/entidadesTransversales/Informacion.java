package entidadesTransversales;

import java.io.Serializable;

public class Informacion implements Serializable{
	private static final long serialVersionUID = 1L;
	private Zona zona;
	private String titulo;
	private String descripcion;
	private int tiempo;
	private Topico topico;
	
	public Informacion() {
		super();
	}
	
	public Informacion(Zona zona, String titulo, String descripcion, int tiempo, Topico topico) {
		super();
		this.zona = zona;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.tiempo = tiempo;
		this.topico = topico;
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
	public Topico getTopico() {
		return topico;
	}
	public void setTopico(Topico topico) {
		this.topico = topico;
	}
	public Zona getZona() {
		return zona;
	}
	public void setZona(Zona zona) {
		this.zona = zona;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "Informacion [zona=" + zona + ", titulo=" + titulo + ", descripcion=" + descripcion + ", tiempo="
				+ tiempo + ", topico=" + topico + "]";
	}
	
}
