import java.io.IOException;
import java.net.InetAddress;

public class main {

	public static void main(String[] args) throws IOException {
		ServerCM server = new ServerCM(8082);
		server.run(); 
//	        System.out.println((InetAddress.getLocalHost().getHostAddress()).trim());
	        
	}	
}
