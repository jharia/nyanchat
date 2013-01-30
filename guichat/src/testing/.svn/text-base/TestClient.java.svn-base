package testing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class TestClient extends Thread {
	public String username;
	BufferedReader in;
	PrintWriter out;
	Socket sock;
	String CID = "";
	public ArrayList<Integer> cids = new ArrayList<Integer>();



	public TestClient(String username) {
		this.username = username;
		try {
			sock = new Socket("localhost", 4444);
	        in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
	        out = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Override 
    public void run() {
        try {
            handleConnections();
            sock.close();
        } catch (IOException e) {
            e.printStackTrace(); // but don't terminate serve()
            
        } 
    }
    
    public void handleConnections() throws IOException {
    	  try {
              for (String line = in.readLine(); line != null; line = in.readLine()) {
            	  String words[] = line.split(" ");
            	  if (words[0].equals("CSTART")) {
            		  this.CID = words[2];
            	  }
            	  System.out.println(username + "'s output: " +line);
              }
    	  } finally {
    		  in.close();
    		  System.out.println("Flushing");
    		  out.flush();
    		  out.close();
    	  }
    }
    
    public void signIn() {
    	out.println("SIGNIN USER "+username);
    	out.flush();
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
    public void sendToServer(String message) {
    	out.println(message);
    	out.flush();
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


//    public static void main(String[] args) {
//    	try {
//			client1();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }
}
