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
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import data.Topico;

/**
 * @author acer
 *
 */
public class Files {
	public static Topico buscarPoslista(List<Topico> topicos, String topico)
	{
		for (Topico topico2 : topicos) {
			if (topico2.getNombre().equals(topico)) {
				return topico2;
			}
		}		
		return null;
	}
}
