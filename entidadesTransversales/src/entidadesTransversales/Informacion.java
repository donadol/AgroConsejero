package entidadesTransversales;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Informacion implements Serializable{
	private static final long serialVersionUID = 1L;
	private Zona zona;
	private String titulo;
	private String descripcion;
	private int tiempo;
	private List<Topico> topicos;
	
	public Informacion() {
		super();
		topicos= new ArrayList <Topico>();
	}
	
	public Informacion(Zona zona, String titulo, String descripcion, int tiempo) {
		super();
		this.titulo=titulo;
		this.zona = zona;
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
		String data= "Informacion [zona=" + zona + ", titulo=" + titulo + ", descripcion=" + descripcion + ", tiempo="
				+ tiempo + "]";
		for(int i =0; i<this.topicos.size(); ++i) {
			data.concat(this.topicos.get(i).toString()+" ");
		}
		return data;
	}
	
}
