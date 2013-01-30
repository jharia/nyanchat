package serverSide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Receives messages from a client and sends them to the server. Sends messages to the client.
 * @author wgrathwohl
 *
 */
public class ClientHandler extends Thread {
    public String username = "";
    private ChatServer server;
    private Socket socket;
    private BufferedReader in;
    public PrintWriter out;
    public ArrayList<ConversationHandler> conversations;
    public boolean signedIn = false;
    
    /**
     * Starts the thread. Will read input from user until the connection is closed or lost. 
     */
    public void run() {
        try {
            handleConnections();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace(); // but don't terminate serve()
            
        } 
    }
    /**
     * Takes messages from the client and sends them to the server to be processed. 
     * @throws IOException
     */
    public void handleConnections() throws IOException {
        try {
            for (String line = in.readLine(); line != null; line = in.readLine()) {
                String[] tokens = line.split(" ");
                if (tokens[0].equals("SIGNIN")) {
                    server.signIn(tokens, this);
                }
                else {
                    server.handleInput(tokens, this);
                }
            }
        } 
        finally {
            in.close();
            out.close();
            server.removeClient(this);
            
        }
    }
    /**
     * Sends a message to the client.
     * @param message : The message to be sent. 
     * @throws IOException
     */
    public void sendToClient(String message) throws IOException {
        out.println(message);
        out.flush();
    }
    /**
     * Builds a Clienthandler 
     * @param socket : The socket to connect to. 
     * @param server : The server that client will send messages to. 
     */
    public ClientHandler(Socket socket, ChatServer server) {
        this.server = server;
        this.socket = socket;
        this.conversations = new ArrayList<ConversationHandler>();
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
            sendToClient("Welcome to NyanChat");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
