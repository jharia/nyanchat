package serverSide;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

/**
 * Class that acts as the server for the chat program.  
 * @author Will Grathwohl
 *
 */
public class ChatServer {
    private HashMap<String, ClientHandler> clientHandlers;
    private HashMap<Integer, ConversationHandler> conversationHandlers;
    private ServerSocket serverSocket;
    private final static int PORT = 4444;
    private Random idGenerator = new Random(19580427);
    
    
//////////////// CONSTRUCTOR //////////////////////////////////////////
    /**
     * Class that acts as the server for the chat program.
     * @throws IOException
     */
    public ChatServer() throws IOException {
        clientHandlers = new HashMap<String, ClientHandler>();
        conversationHandlers = new HashMap<Integer, ConversationHandler>();
        serverSocket = new ServerSocket(PORT);
        
    }

//////////////// SERVER ///////////////////////////////////////////////
    /**
     * Runs the server.
     * @throws IOException
     */
    public void serve() throws IOException {
        while (true) {
            new ClientHandler(serverSocket.accept(), this).start();
        }
    }

    
    
    
//////////// ADD/REMOVE CONVERSATIONS /////////////////////////////////////////// 
    
    
    /**
     * Creates a new conversation between the two inputed users.
     * Send message to each client informing them of the change
     * 
     * @param username1 : String username of first user
     * @param username2 : String username of second user
     * @return the integer ID assigned to this new conversation
     * @modifies this.conversationHandlers
     */
    public void newConversation(String username1, String username2) {
        ClientHandler client1 = clientHandlers.get(username1);
        ClientHandler client2 = clientHandlers.get(username2);
        int conversationID;
        synchronized(conversationHandlers) {
            do {
                conversationID = idGenerator.nextInt();
            } while(conversationHandlers.containsKey(conversationID));
            ConversationHandler conversation = new ConversationHandler(client1, client2, conversationID, this);
            conversationHandlers.put(conversationID, conversation);
        }
        try {
        	client1.sendToClient(conversationStartedMessage(conversationID, username2));
        	client2.sendToClient(conversationStartedMessage(conversationID, username1));
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }
    

    /**
     * Removes the conversation defined by conversationID. 
     * @param conversationID : The id of the conversation that is to be deleted.
     * @modifies conversationHandlers
     */
    public void removeConversation(int conversationID) {
    	synchronized(conversationHandlers) {
    		conversationHandlers.remove(conversationID);
    	}
        
    }
    
    
    
///////////////////// REMOVE USERS //////////////////////////////////////////
    /**
     * Removes the client from the server
     * @param client : ClientHandler to be removed.
     * @modifies clientHandlers, conversationHandlers
     */
    public synchronized void removeClient(ClientHandler client) {
    	synchronized(client) {
    	    if (client.signedIn){ 
    	        clientHandlers.remove(client.username);
    	        for (ConversationHandler ch : client.conversations) {
    	            synchronized(ch) {
    	                ch.removeUser(client);
    	                String message = conversationUsersChangeMessage(ch.conversationID, ch.printClients());
    	                ch.sendMessage(message);
    	            }
    	        }
    	        synchronized(clientHandlers) {
    	        	sendToAll(onlineUsersMessage());
    	        }
    	    }
    	}
    }

    
    /**
     * Reads a username and adds a user to the server
     * @param tokens : The split up sign in request message.
     * @param client : The ClientHandler to be signed in. 
     * @modifies clientHandlers
     */
    public void signIn(String[] tokens, ClientHandler client) {
        String username = tokens[2];
        synchronized(clientHandlers) {
	        if (clientHandlers.containsKey(username)) {
	            client.out.println(signedInErrorMessage());
	            client.out.flush();
	        }
	        else {
	            clientHandlers.put(username, client);
	            client.username = username;
	            client.signedIn = true;
	            try {
                    client.sendToClient(signedInMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
	            sendToAll(onlineUsersMessage());    
	        }
        }
    }

    
    
//////////////////// HANDLE INPUT ///////////////////////////////////////////
    /**
     * Handles the input from the clients and responds. 
     * @param input : Split input message to be parsed.
     * @modifies conversationHandlers
     */
    public void handleInput(String input[], ClientHandler sender) {;

        // New Conversation Requested
        if (input[0].equals("CREQ")) {
            String username1 = input[2];
            String username2 = input[4];
            if (username1.equals(sender.username) && !(username1.equals(username2))) {
	            newConversation(username1, username2);
            }
        }
        // New user to be invited
        else if (input[0].equals("ADDUSER")) {
            String username = input[1];
            int conversationID = Integer.parseInt(input[3]);    
            if (clientHandlers.containsKey(username) && conversationHandlers.containsKey(conversationID)) {
            	ConversationHandler con = conversationHandlers.get(conversationID);
                ClientHandler cli = clientHandlers.get(username);
                //Makes sure the user is not already in the conversation
                boolean go = true;
                String[] users = con.printClients().split(" ");
                for (String un : users) {
                	if (un.equals(username)) {
                		go = false;
                	}
                }
                if (go) {
                	con.addUser(cli);
                	con.sendMessage(conversationUsersChangeMessage(conversationID, con.printClients()));
                }
            }
            else {
            	if (conversationHandlers.containsKey(conversationID)) {
            		ConversationHandler con = conversationHandlers.get(conversationID);
            		con.sendMessage(conversationUsersChangeErrorMessage());
            	}
            }
            
        }       
        else if (input[0].equals("REMOVEUSER")) {
            String username = input[1];
            if (username.equals(sender.username)){ 
	            int conversationID = Integer.parseInt(input[3]);
	            if (conversationHandlers.containsKey(conversationID)) {
		            ConversationHandler con = conversationHandlers.get(conversationID);
		            ClientHandler cli = clientHandlers.get(username);
		            con.removeUser(cli);
		            con.sendMessage(conversationUsersChangeMessage(conversationID, con.printClients()));
	            }
            }
        }
        // Normal chat text or typing state information
        else if (input[0].equals("CHATSENDER")||input[0].equals("TYPING")
                ||input[0].equals("TYPED")||input[0].equals("CLEARED")) {
            String username = input[1];
            if (username.equals(sender.username)) {
	            int conversationID = Integer.parseInt(input[3]);
	            if (conversationHandlers.containsKey(conversationID)) {
		            ConversationHandler con = conversationHandlers.get(conversationID);
		            String message = "";
		            for (String part : input) {
		                message += part + " ";
		            }
		            con.sendMessage(message);
	            }
            }
        }
    }
    /**
     * Sends a message to all signed in clients
     * @param message : message to be sent out.
     */
    public void sendToAll(String message) {
        synchronized(clientHandlers) {
            Iterator<String> it = clientHandlers.keySet().iterator();
    	    while(it.hasNext()) {
    	        String user = it.next();
    	        if (clientHandlers.containsKey(user)) {
	    	        try {
	    	            clientHandlers.get(user).sendToClient(message);
	    	        } catch (IOException e) {
	    	            e.printStackTrace();
	    	        }
    	        }
    	    }
        }
    }   
//////////////// MESSAGE GENERATORS ///////////////////////////////////////// 
    /**
     * Generates an online users message.
     * @return message containing online users.
     */
    public synchronized String onlineUsersMessage() {
        synchronized(clientHandlers) {
            String message = "ONLINEUSERS ";
            Iterator<String> it = clientHandlers.keySet().iterator();
            while (it.hasNext()) {
                String username = it.next();
                message = message + username + " ";
            }
            return message;
        }
    }
    /**
     * Generates a conversation started message
     * @param conversationID : Id of conversation that was started
     * @param username : The other user in the conversation.
     * @return a conversation started message
     */
    public String conversationStartedMessage(int conversationID, String username) {
        String message = "CSTART CID " + conversationID + " USER " + username;       
        return message;
    }
    /**
     * Generates a conversation started error message. 
     * @return a message indicating that there was an error in starting a conversation. 
     */
    public String conversationStartedErrorMessage() {
        return "CSTART ERROR";
    }
    
    /**
     * Message to confirm that the user has signed in correctly.
     * @return confirmation that user has signed in correctly
     */
    public String signedInMessage() {
        return "SIGNEDIN";
    }
    /**
     * Message to indicate that the username entered is already in use.
     * @return indication that the username is currently in use
     */
    public String signedInErrorMessage() {
        return "SIGNEDIN USERNAME IN USE";
    }
    /**
     * 
     * @param conversationID : ID of conversation
     * @param username : User to be added/removed 
     * @param userAdded : true if user is to be added, false if user is to be removed
     * @return
     */
    public String conversationUsersChangeMessage(int conversationID, String currentClients) {
        String message = "CCHANGE CID " + conversationID + " " + currentClients;
        return message;
    }
    /**
     * Message to indicate that there was an error removing or adding a user to the conversation. 
     * @return
     */
    public String conversationUsersChangeErrorMessage() {
        return "CCHANGE ERROR";
    }
    /**
     * Message that indicates there was an error sending message to the users in a conversation. 
     * @return
     */
    public String chatErrorMessage() {
        return "CHAT ERROR";
    }
}
