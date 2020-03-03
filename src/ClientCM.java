import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
  
        Gson gson = new Gson();
        
        JsonObject json = new JsonObject();


        String jsonRequest = new String(Files.readAllBytes(Paths.get("./getSongChunk.json")));

        
        
        buf = jsonRequest.getBytes(); 
        DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ip, 1234); 
        ds.send(DpSend); 
        
    
		
        // Convert the String input into the byte array. 
        //buf = inp.getBytes(); 
  
        // Step 2: Create the datagramPacket for sending the data. 
        //DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ip, 1234); 
  
        // Step 3: invoke the send call to actually send the data. 
        //ds.send(DpSend); 

    } 
	
}
