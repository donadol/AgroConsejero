/**
 * 
 */
package code;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import data.Topico;

/**
 * @author acer
 *
 */
public class ServerListener {

	public static void main(String[] args) {
		
	      try{
	   	   int serverPort = 7896;   // the server port
	   	   ServerSocket listenSocket = new ServerSocket(serverPort);
	   	   while(true) {
	   	    Socket clientSocket = listenSocket.accept();
	   	   ConnectionThread c = new ConnectionThread(clientSocket);
	   	   }
	   	} catch(IOException e) {
	   	      System.out.println("Listen socket:"+e.getMessage());
	   	}
	}
}
