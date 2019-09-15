package entidadesTransversales;

public class Cultivo {
	private Zona zona;
	private int tamano;
	private String tipoProducto;
	
	public Cultivo(Zona zona, int tamano, String tipoProducto) {
		super();
		this.zona = zona;
		this.tamano = tamano;
		this.tipoProducto = tipoProducto;
	}
	public Zona getZona() {
		return zona;
	}
	public void setZona(Zona zona) {
		this.zona = zona;
	}
	public int getTamano() {
		return tamano;
	}
	public void setTamano(int tamano) {
		this.tamano = tamano;
	}
	public String getTipoProducto() {
		return tipoProducto;
	}
	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
}
