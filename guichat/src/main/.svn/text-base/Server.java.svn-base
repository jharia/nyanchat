package main;

import java.io.IOException;

import serverSide.ChatServer;

/**
 * Chat server runner.
 */
public class Server {

    /**
     * Start a chat server.
     */
    public static void main(String[] args) {
        try {
            ChatServer server = new ChatServer();
            server.serve();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
