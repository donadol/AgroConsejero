package model;

import java.io.Serializable;

public class Topico implements Serializable{
	private String tema;
	/**
	 * @param tema
	 */
	public Topico(String tema) {
		super();
		this.tema = tema;
	}
	/**
	 * @return the tema
	 */
	public String getTema() {
		return tema;
	}
	/**
	 * @param tema the tema to set
	 */
	public void setTema(String tema) {
		this.tema = tema;
	}
}
