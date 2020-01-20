// A Java program for a Server 
import com.fasterxml.jackson.core.JsonParser;
import servermanagement.controller.RequestController;
import servermanagement.entities.Request;
import servermanagement.service.RequestService;

import java.net.*;
import java.io.*; 

public class Server 
{ 
	//initialize socket and input stream 
	private Socket		 socket = null; 
	private ServerSocket server = null; 
	private DataInputStream in	 = null; 

	// constructor with port 
	public Server(int port) 
	{ 
		// starts server and waits for a connection 
		try
		{ 
			server = new ServerSocket(port); 
			System.out.println("Server started"); 

			System.out.println("Waiting for a client ..."); 

			socket = server.accept(); 
			System.out.println("Client accepted"); 

			// takes input from the client socket 
			in = new DataInputStream( 
				new BufferedInputStream(socket.getInputStream())); 

			final String  line = "";

			// reads message from client until "Over" is sent 
			while (!line.equals("Over")) 
			{
				RequestController controller = new RequestController();
				Runnable runnable = new Runnable() {
					@Override
					public void run() {

						if(line.startsWith("sleep?") ) {
							String timeOut = "10";
							String connid = "1";

							System.out.println( controller.sleep(timeOut, connid));
						} else if(line.startsWith("serverstatus") ) {
							System.out.println(controller.serverstatus() );
						} else if(line.startsWith("kill")){
							Request r = new Request();
							r.setConnId("1");
							System.out.println(controller.kill(r));
						}
					}
				};

				/*try
				{
					line = in.readUTF();
					System.out.println(line);

				}
				catch(IOException i)
				{
					System.out.println(i);
				}*/

				Thread t = new Thread(runnable);
				t.start();

			} 
			System.out.println("Closing connection"); 

			// close connection 
			socket.close(); 
			in.close(); 
		} 
		catch(IOException i) 
		{ 
			System.out.println(i); 
		} 
	} 

	public static void main(String args[]) 
	{ 
		Server server = new Server(5000); 
	} 
} 
