/**
 * 
 */
package data;

import java.time.LocalTime;

/**
 * @author acer
 *
 */
public class Topico {
	private LocalTime hora_publicacion;
	private String ubicacion;
	private String descripcion;
	private String tema;
	/**
	 * @param hora_publicacion
	 * @param ubicacion
	 * @param descripcion
	 * @param tema
	 */
	public Topico(LocalTime hora_publicacion, String ubicacion, String descripcion, String tema) {
		super();
		this.hora_publicacion = hora_publicacion;
		this.ubicacion = ubicacion;
		this.descripcion = descripcion;
		this.tema = tema;
	}
	public LocalTime getHora_publicacion() {
		return hora_publicacion;
	}
	public void setHora_publicacion(LocalTime hora_publicacion) {
		this.hora_publicacion = hora_publicacion;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getTema() {
		return tema;
	}
	public void setTema(String tema) {
		this.tema = tema;
	}
}
