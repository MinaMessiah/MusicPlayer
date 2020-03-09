import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerCM {

	public String receive() throws IOException 
    { 
        // Step 1: Create a socket to listen at port 1234 
        DatagramSocket ds = new DatagramSocket(1234); 
        byte[] received = new byte[65535]; 
  
        DatagramPacket DpReceive = null; 

        // Step 2: create a DatgramPacket to receive the data. 
        DpReceive = new DatagramPacket(received, received.length); 
  
        // Step 3: receive the data in byte buffer. 
        ds.receive(DpReceive); 
        
        String receivedMsg = data(received).toString();

        // Clear the buffer after every message. 
        received = new byte[65535]; 
            
        return receivedMsg;
    } 
  
    // A utility method to convert the byte array 
    // data into a string representation. 
    public static StringBuilder data(byte[] a) 
    { 
        if (a == null) 
            return null; 
        StringBuilder ret = new StringBuilder(); 
        int i = 0; 
        while (a[i] != 0) 
        { 
            ret.append((char) a[i]); 
            i++; 
        } 
        return ret; 
    } 
	
}
