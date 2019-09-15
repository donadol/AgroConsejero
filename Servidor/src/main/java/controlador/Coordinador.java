package controller;

public class Coordinador extends Thread {

	private String operacion;
	private static String ip;
	private int prioridad;
	
	
	public Coordinador( String operacion ) {
		this.operacion = operacion;
		this.start();
		
	}
	
	public void run() {
		
		
	}

	public static String getIp() {
		return ip;
	}

	public static void setIp(String ip_addr) {
		ip = ip_addr;
	}

	
}
