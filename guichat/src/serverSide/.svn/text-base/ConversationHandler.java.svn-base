package serverSide;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Class that handles conversations. Contains Set<CLientHandler> that contains every
 * user in the conversation. Takes String messages from the server and sends them to
 * the clients in the conversation.
 * @author wgrathwohl
 */
public class ConversationHandler {
	private ChatServer server;
    public int conversationID;
    private ArrayList<ClientHandler> clientHandlers;
    
    public ConversationHandler(ClientHandler user1, ClientHandler user2, int conversationID, ChatServer server) {
        clientHandlers = new ArrayList<ClientHandler>();
        addUser(user1);
        addUser(user2);
        this.conversationID = conversationID;
        this.server = server;
    }
    /**
     * Adds a user to the conversation.
     * @param user : ClientHandler that will be added to the conversation handler. 
     * @modifies user, conversations
     */
    public synchronized void addUser(ClientHandler user) {
        clientHandlers.add(user);
        user.conversations.add(this);
    }
    /**
     * Removes a user from the conversation.
     * @param user : ClientHandler to be removed from the conversation. 
     * @modifies user, clientHandlers, server
     */
    public synchronized void removeUser(ClientHandler user) {
        clientHandlers.remove(user);
        if (clientHandlers.size() == 0) {
        	server.removeConversation(conversationID);
        }
    }
    //TODO: CHECK SYNCHRONIZATION ON THIS SHIT!!!!
    /**
     * Sends message to each user in the conversation
     * @param message : The message obtained from the server to be sent to the users.
     */
    public void sendMessage(String message) {
        for (ClientHandler user : clientHandlers) {
            try {
                user.sendToClient(message);
            } catch (IOException e) {
                /*
                 * TODO : CATCH THIS AND SEND ERROR MESSAGE TO CLIENT AND MAYBE SERVER
                 */
                e.printStackTrace();
            }
        }
    }
    /**
     * Generates a string containing the usernames of the users in the 
     * conversation separated by spaces
     * @return String containing the users in the conversation
     */
    public String printClients() {
    	synchronized(clientHandlers) {
	    	String clients = "";
	    	for (ClientHandler cl : clientHandlers) {
	    		if (cl != null) {
	    			clients += cl.username + " ";
	    		}
	    	}
	    	return clients;
    	}
    }
    

}
