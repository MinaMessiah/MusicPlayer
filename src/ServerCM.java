import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ServerCM extends Thread {

	private DatagramSocket socket;
	byte[] received = new byte[65507]; 


	ServerCM() throws SocketException {
		// Create a socket to listen at port 8080 by default 
		this.socket = new DatagramSocket(8080);
	}

	ServerCM(int port) throws SocketException {
		// Create a socket to listen at given port 
		this.socket = new DatagramSocket(port);
	}


	public void run(){
		DatagramPacket packet = new DatagramPacket(this.received, this.received.length);
		try { socket.receive(packet); } 
		catch (IOException e) { e.printStackTrace(); }


		InetAddress address = packet.getAddress();
		int port = packet.getPort();
		String received = new String(packet.getData(), 0, packet.getLength());
		// TODO Call dispatcher on received 
		//=======================================================================================		
		Dispatcher dispatcher = new Dispatcher();
		// Instance of the services that te dispatcher can handle
		Playlist playlist = new Playlist();

		dispatcher.registerObject(playlist, "testFun");  

		String ret = dispatcher.dispatch(received);
		//		=======================================================================================	

		byte[] bret = ret.getBytes();

		//		///////////////
		System.out.println("Message: " + received);
		packet = new DatagramPacket(bret, bret.length, address, port);

		try { socket.send(packet); } 
		catch (IOException e) { e.printStackTrace(); }

		socket.close();
	}


	public static void main() throws SocketException {
		ServerCM server = new ServerCM(8081);
		server.run();
	}
}

