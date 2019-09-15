package entidadesTransversales;

import java.util.List;

public class Topico{
	
	private String nombre;
	private List<String> subtopicos;
	
	public Topico(String nombre) {
		super();
		this.nombre = nombre;
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
