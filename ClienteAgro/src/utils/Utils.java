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

import entidadesTransversales.*;

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
		FileReader f = new FileReader(File_Name);
		BufferedReader b = new BufferedReader(f);
		String cadena, host = null;
		int port = 0;
		Agricultor agricultor;
		Topico topico;
		Cultivo cultivo = null;
		
		StringTokenizer port_host = new StringTokenizer(b.readLine(), "*");
		
		host = port_host.nextToken();
		port = Integer.parseInt(port_host.nextToken());
		
		while((cadena = b.readLine())!=null) {
			tops = new ArrayList<Topico>();
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
								topico = new Topico(token_guion, null);
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
											zona = Zona.valueOf(point_Token);
										}
									}
									d = 0;
									cultivo = new Cultivo(tipo, tamanho, zona);
								}
							}
						}
						c = 0;
					}
				}
			}
			
			agricultor = new Agricultor(nombre, password, port, host, cultivo);
			agricultor.setTopicos(tops);
			agrs.add(agricultor);
		}
		
		b.close();
		return agrs;
	}
}
