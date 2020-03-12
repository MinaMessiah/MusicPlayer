import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class ClientCM {

	private InetAddress ip;
	private int port;
	private DatagramSocket socket;
	byte buf[] = null; 

	ClientCM(String ipAddr, int cmPort) throws UnknownHostException, SocketException {
		this.port = cmPort;
		this.socket = new DatagramSocket();
		this.ip = InetAddress.getByName(ipAddr);
	}


	public String send(String msg) throws IOException {
		buf = msg.getBytes();
		DatagramPacket packet = new DatagramPacket(buf, buf.length, this.ip, this.port);
		socket.send(packet);
		packet = new DatagramPacket(buf, buf.length);
		socket.receive(packet);
		String received = new String(packet.getData(), 0, packet.getLength());
		return received;
	}
	
	
	public void closeConncection() {
		this.socket.close();
	}




	public static void main(String[] args) throws IOException {
		ClientCM cm = new ClientCM("127.0.0.1", 8082);
		System.out.println(cm.send("{\"remoteMethod\":\"testFun\",\"className\":\"Playlist\",\"param\": { \"s\": \"Hello lolo\" }}"));
		cm.closeConncection();
	}

}
