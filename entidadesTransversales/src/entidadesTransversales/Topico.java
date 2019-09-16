package entidadesTransversales;

import java.util.List;

public class Topico{
	
	private String nombre;
	private List<String> subtopicos;
	
	public Topico() {
	}
	public Topico(String nombre, List<String> subtopicos) {
		super();
		this.nombre = nombre;
		this.subtopicos = subtopicos;
	}
	public String getNombre() {
		return nombre;
	}
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
