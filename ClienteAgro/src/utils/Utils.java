/**
 * 
 */
package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import model.Agricultor;
import model.Cultivo;
import model.Topico;
import model.Zona;

/**
 * @author acer
 *
 */
public class Utils {
	
	public static String getHash(String txt, String hashType) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance(hashType);
            byte[] array = md.digest(txt.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
	
	public static ArrayList<Agricultor> read_file (String File_Name) throws IOException {
		ArrayList<Agricultor> agrs = new ArrayList<Agricultor>();
		ArrayList<Topico> tops;
		ArrayList<Cultivo> cults;
		FileReader f = new FileReader(File_Name);
		BufferedReader b = new BufferedReader(f);
		String cadena, host = null, port = null;
		Agricultor agricultor;
		Topico topico;
		Cultivo cultivo;
		
		StringTokenizer port_host = new StringTokenizer(b.readLine(), "*");
		
		host = port_host.nextToken();
		port = port_host.nextToken();
		
		while((cadena = b.readLine())!=null) {
			tops = new ArrayList<Topico>();
			cults = new ArrayList<Cultivo>();
			StringTokenizer tokens = new StringTokenizer(cadena, "*");
			int a = 0, c = 0, d = 0;
			String token;
			String nombre = null;
			String password = null;
			
			while (tokens.hasMoreTokens()) {
				a++;
				token = tokens.nextToken();
				if(a == 1) {nombre = token;}
				if(a == 2) {password = getHash(token, "MD5");}
				if(a == 3) {
					StringTokenizer tokens2 = new StringTokenizer(token, "/");
					String token2;
					while (tokens2.hasMoreTokens()) {
						token2 = tokens2.nextToken();
						StringTokenizer guion = new StringTokenizer(token2, "-");
						String token_guion;
						while (guion.hasMoreTokens()) {
							c++;
							token_guion = guion.nextToken();
							if (c == 1) {
								topico = new Topico(token_guion);
								tops.add(topico);
							}
							if (c == 2) {
								StringTokenizer colon = new StringTokenizer(token_guion, ",");
								String colon_Token;
								String tipo = null;
								int tamanho = 0;
								Zona zona = null;
								while (colon.hasMoreTokens()) {
									colon_Token = colon.nextToken();
									StringTokenizer point = new StringTokenizer(colon_Token, ".");
									String point_Token;
									while (point.hasMoreTokens()) {
										d++;
										point_Token = point.nextToken();
										
										if (d == 1) {tipo = point_Token;}
										if (d == 2) {tamanho = Integer.parseInt(point_Token);}
										if (d == 3) {
											if (point_Token.equals("Norte")) {
												zona = Zona.Norte;
											}
											if (point_Token.equals("Sur")) {
												zona = Zona.Sur;
											}
											if (point_Token.equals("Este")) {
												zona = Zona.Este;
											}
											if (point_Token.equals("Oeste")) {
												zona = Zona.Oeste;
											}
										}
									}
									d = 0;
									cultivo = new Cultivo(tipo, tamanho, zona);
									cults.add(cultivo);
								}
							}
						}
						c = 0;
					}
				}
			}
			
			agricultor = new Agricultor(nombre, password, port, host);
			agricultor.setTopicos(tops);
			agricultor.setCultivos(cults);
			System.out.println("Topicos: ");
			for (Topico t: agricultor.getTopicos()) {
				System.out.println("\tTopico: " + t.getTema());
			}
			agrs.add(agricultor);
		}
		
		b.close();
		return agrs;
	}
	
	public static void main(String[] args) throws IOException {
		ArrayList<Agricultor> agrs = new ArrayList<Agricultor>();
		
		agrs = read_file("clientes.txt");
		
		for (Agricultor a: agrs) {
			System.out.println("//----------------------------------------------------//");
			System.out.println("Nombre: " + a.getNombre());
			System.out.println("Password: " + a.getPassword());
			System.out.println("Port: " + a.getPort());
			System.out.println("Host: " + a.getHost());
			System.out.println("Topicos: ");
			for (Topico t: a.getTopicos()) {
				System.out.println("\tTopico: " + t.getTema());
			}
			System.out.println("Cultivos: ");
			for (Cultivo c: a.getCultivos()) {
				System.out.println("(-------------------------------------------------------------");
				System.out.println("\tCultivo: " + c.getTipo_producto());
				System.out.println("\tTamaño: " + c.getTamanho());
				System.out.println("\tZona: " + c.getZona());
			}
		}
	}
}
