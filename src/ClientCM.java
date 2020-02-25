import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

public class ClientCM {

	public static void main(String args[]) throws IOException 
    { 
		
        // Step 1: Create the socket object for carrying the data. 
        DatagramSocket ds = new DatagramSocket(); 
  
        InetAddress ip = InetAddress.getLocalHost(); 
        byte buf[] = null; 
  
        //Gson gson = new Gson();
		//JsonReader reader = new JsonReader(new FileReader("getSongChunk.json"));
		
		String inp = "this is a message from the client to the server";
		
        // Convert the String input into the byte array. 
        buf = inp.getBytes(); 
  
        // Step 2: Create the datagramPacket for sending the data. 
        DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ip, 1234); 
  
        // Step 3: invoke the send call to actually send the data. 
        ds.send(DpSend); 

    } 
	
}
