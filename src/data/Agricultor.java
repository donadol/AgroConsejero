/**
 * 
 */
package data;

import java.net.InetAddress;
import java.util.ArrayList;

/**
 * @author acer
 *
 */
public class Agricultor {
	private InetAddress localIp;
	private String ubicacion;
	private String nombre;
	private ArrayList<String> cultivos = new ArrayList<String>();
	/**
	 * @param localIp
	 * @param ubicacion
	 * @param nombre
	 */
	public Agricultor(InetAddress localIp, String ubicacion, String nombre) {
		super();
		this.localIp = localIp;
		this.ubicacion = ubicacion;
		this.nombre = nombre;
	}
	public InetAddress getLocalIp() {
		return localIp;
	}
	public void setLocalIp(InetAddress localIp) {
		this.localIp = localIp;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public ArrayList<String> getCultivos() {
		return cultivos;
	}
	public void setCultivos(ArrayList<String> cultivos) {
		this.cultivos = cultivos;
	}
}
