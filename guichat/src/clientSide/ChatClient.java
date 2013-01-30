package clientSide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.swing.JDialog;


/**
 * 
 * @author Anvisha
 * ChatClient is the class that handles communication with the server.
 * Socket sock: socket at which ChatClient communicates with server
 * BufferedReader in: input stream reader from Server
 * PrintWriter out: output stream to Server
 * BlockingQueue<String> inputQueue: this is where commands from Client to Server are stored before they are sent
 * BlockingQueue<String> outputQueue: this is where commands from the Server are stored before the GUI is updated
 * LoginAttempt loginScreen: Login window UI where username is entered
 * NyanchatGUI GUI: the whole GUI. This is only instantiated after the username is validated by the server (in the constructor)
 *
 */
public class ChatClient{
	
	Socket sock;
	BufferedReader in;
	PrintWriter out;
	BlockingQueue<String> inputQueue;
	BlockingQueue<String> outputQueue;
	LoginAttempt loginScreen;
	NyanchatGUI GUI;
	Controller c;	

	/**
	 * This method is used for testing.
	 * @param sock Socket
	 * @param inputQueue inputQueue set for testing 
	 */
	
	public ChatClient(Socket sock, BlockingQueue<String> inputQueue){
		this.inputQueue = inputQueue;
		initializeChatClient(sock);
	}
	
	/**
	 * This is the normal constructor.
	 * @param sock
	 */
	
	public ChatClient(Socket sock){
		inputQueue = new ArrayBlockingQueue<String>(20);
		loginScreen = new LoginAttempt(this);
		loginScreen.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		loginScreen.setResizable(false); 
		loginScreen.setVisible(true);
		initializeChatClient(sock);
		
	}
	
	/**
	 * Method that is called as an extension of the constructor.
	 * @param sock
	 */
	
	private void initializeChatClient(Socket sock){
		this.sock = sock;
		outputQueue = new ArrayBlockingQueue<String>(20);
		
		//this is the login process that initiates the connection and makes the GUI
		try {
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream(),true);
			String fromServerInitial;	
			String toServerInitial= new String();					
			while(!(fromServerInitial=in.readLine()).equals("SIGNEDIN")){
				//loop repeats until we get a valid response from server
				if(fromServerInitial.equals("SIGNEDIN USERNAME IN USE")){
					//if we get the username in use error from server
					loginScreen.getLabel().setText("Username in use. Please try other username");
				}
				toServerInitial = inputQueue.take();//waits until inputQueue has a value to take again
				out.println(toServerInitial);
				out.flush();
			}
			
			if(loginScreen!=null){ //loginScreen would not exist in testing mode
				loginScreen.dispose();
			}
			GUI = new NyanchatGUI(toServerInitial.split(" ")[2],this); //This creates the GUI
			GUI.getFrmNyanchatUsername().setVisible(true); //sets visibility to true
			c = new Controller(GUI); //creates the Controller
			
			//thread that reads from input stream (Server)
			Thread threadReader = new Thread(){ 
				public void run(){
					readFromServer();
				}
			};
			//thread that writes to output stream
			Thread threadWriter = new Thread(){
				public void run(){
					writeToServer();
				}
			};
			threadReader.start();
			threadWriter.start();
			
		} catch (IOException e) {
			serverDisconnectMessage();
		}
		catch(InterruptedException e){
			serverDisconnectMessage();
		}
		
	}
	
	/**
	 * this is the method that continuously reads from the server.
	 * it pops up a Server Disconnect message is the server is disconnected.
	 */
	
	public void readFromServer(){
		try{
			for(String fromServer = in.readLine(); fromServer!=null; fromServer = in.readLine()){
				c.handleServerMessage(fromServer);
			}
		}
		catch(Exception e){
			serverDisconnectMessage();

		}
		finally{
			try {
				in.close();
				out.close();
				serverDisconnectMessage();

			} catch (IOException e) {
				

			}
		}
	}
	
	/**
	 * this is the method that continuously writes to the server. 
	 */
	
	public void writeToServer(){
		String toServer;
		try {
			while(true){
				toServer = inputQueue.take(); //takes a message from inputQueue when it is non-null
				out.println(toServer);
				out.flush();
			}
		} catch (InterruptedException e) {
		}
		finally{
			out.close();
		}
	}
	
	/**
	 * @param message this is the Client to Server message
	 * @modifies inputQueue -the queue of Client to Server messages
	 * Synchronized to ensure thread safety
	 */
	
	public void addInputMessage(String message){
		synchronized(inputQueue){
			inputQueue.add(message);
		}
	}
	
	/**
	 * @param message from Server to be added to outputQueue 
	 * Synchronized to ensure thread safety.
	 */
	public void addServerMessage(String message){
		synchronized(outputQueue){
			if(message!=null){
			outputQueue.add(message);
			}
		}
	}
	
	/**
	 * Displays the dialog box for Server Disconnect
	 */
	public void serverDisconnectMessage(){
		ServerDisconnect serverDisconnect = new ServerDisconnect();
		serverDisconnect.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		serverDisconnect.setResizable(false); 
		serverDisconnect.setVisible(true);
	}

}
