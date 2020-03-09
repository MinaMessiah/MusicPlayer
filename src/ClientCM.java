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

	public void send(String jsonRequest) throws IOException 
    { 
		
        // Step 1: Create the socket object for carrying the data. 
        DatagramSocket ds = new DatagramSocket(); 
  
        InetAddress ip = InetAddress.getLocalHost(); 
        byte buf[] = null; 

        // Convert the String input into the byte array. 
        buf = jsonRequest.getBytes(); 
        DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ip, 1234); 
        
        // Step 3: invoke the send call to actually send the data. 
        ds.send(DpSend); 
 

    } 
	
}
