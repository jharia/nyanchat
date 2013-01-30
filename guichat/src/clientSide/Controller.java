package clientSide;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class Controller {

	private NyanchatGUI GUI;
	
	public Controller(NyanchatGUI GUI){
		this.GUI = GUI; //GUI gets passed into it at time that it's created

	}
	
	/**
	 * This is the comprehensive method that handles messages from the Server and updates the GUI.
	 * @param message to be handled by Controller
	 * @modifies different GUI objects that it's sub-processes modify 
	 */
	
	public void handleServerMessage(String message){
		String[] arrMsg = message.split(" ");
		if(arrMsg[0].equals("ONLINEUSERS")){
			//Updating according to online users
			onlineUsersUpdate(arrMsg);
		}
		else if(arrMsg[0].equals("CSTART")){
			//Start a conversation between 2 users
			conversationStartUpdate(arrMsg);
		}
		else if(arrMsg[0].equals("CCHANGE")){
			//conversation users change (added/removed) OR new conversation with more than 2 users
			conversationUsersChangeUpdate(message);
		}
		else if(arrMsg[0].equals("CHATSENDER")){
			//Chat message
			chatMessageUpdate(message);
		}
		else if(arrMsg[0].equals("TYPING")){
			typingMessage("TYPING",arrMsg);
		}
		else if(arrMsg[0].equals("TYPED")){
			typingMessage("TYPED",arrMsg);
		}
		else if(arrMsg[0].equals("CLEARED")){
			typingMessage("CLEARED",arrMsg);
		}

		else{
			//shouldn't get here
		}
	}
	
	/**
	 * This method updates the online users table based on the info from the Server.
	 * It also makes sure that the users username itself is not included in the list of online users.
	 * @param arrMessage: the array string split on " ".
	 * @modifies tableOnlineUsers table in the GUI
	 */
	private void onlineUsersUpdate(String[] arrMessage){
		String[][] onlineUsers = new String[arrMessage.length-2][1];
		int count = 0;
		for(int i=0;i<arrMessage.length-1;i++){
			if (!arrMessage[i+1].equals(GUI.username)) {
				onlineUsers[count][0]=arrMessage[i+1];
				count++;
			}
		}		
		String[] column = {"Online Users"};
		GUI.getOnlineUsersTable().setDataVector(onlineUsers, column);
	}
	
	/**
	 *This method extracts the chat data from the Server message and updates the chat history window.
	 * @param message: message from Server
	 * @modifies chatHistory Text Pane in GUI
	 */
	private void chatMessageUpdate(String message){
		String[] arrMessage = message.split(" ");
		String strCID = arrMessage[3];
		int CID = Integer.parseInt(strCID);
		if(GUI.cidToTabs.containsKey(CID)){		
			int dataIndex = message.indexOf("DATA");
			String data = message.substring(dataIndex+5);
			String newData = "\n"+data;
			Document doc = GUI.cidToTabs.get(CID).getChatHistory().getDocument();
			try {
				doc.insertString(doc.getLength(), newData, null);
				
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param arrMessage: Split array message
	 * @modifies JLabel lblConversationWith in ConversationTab in GUI that has list of users in conversation
	 * @modifies JTabbedPane tabbedRightPane if that user doesn't exist in the conversation - ie new ConversationTab created
	 * and added to pane.
	 * 
	 */
	private void conversationUsersChangeUpdate(String Message){
		String[] arrMessage = Message.split(" ");
		int CID = Integer.parseInt(arrMessage[2]);
		String users ="";
		
		for(int i=3;i<arrMessage.length;i++){
			if(!arrMessage[i].equals(GUI.getUsername())){
				users+= arrMessage[i] + "  ";
			}
		}
		
		//if conversation already exists for Client
		if(GUI.cidToTabs.containsKey(CID)){
		    ConversationTab conTab = GUI.cidToTabs.get(CID);
		    conTab.setTitle(users);
			JLabel lblConversationWith = conTab.getConversationWith();
			String labelText= lblConversationWith.getText();
			String finalText = "<html>  ";
			
			//adding users that are not already in the label
			for(int i=3; i<arrMessage.length; i++ ){
				if((!arrMessage[i].equals(GUI.getUsername()))&&(!arrMessage[i].equals(""))){
					Pattern add = Pattern.compile("<font color=\"(red|green|black)\">"+arrMessage[i]+"</font>  ");
					Matcher addMatcher = add.matcher(labelText);
					if(!addMatcher.find()){
						finalText+="<font color=\"black\">"+arrMessage[i]+"</font>  ";
					}
				}
			}
			
			//removing users in the label that are not in the list of users
			Pattern remove = Pattern.compile("<font color=\"(red|black|green)\">\\w+</font>  ");
			Matcher removeMatcher = remove.matcher(labelText);
			while(removeMatcher.find()){
				String section = labelText.substring(removeMatcher.start(), removeMatcher.end());
				Scanner sc = new Scanner(section);
				sc.useDelimiter("(</font>  |<font color=\"(red|black|green)\">)");
				String user = sc.next();
				for(int i=3; i<arrMessage.length;i++){
					if(user.equals(arrMessage[i])){
						finalText+=section;
						break;
					}
				}
				
			}
			//setting lblConversationWith 
			finalText += "</html>";
			if((arrMessage.length==4)&&(arrMessage[3].equals(GUI.getUsername()))){
				finalText = "<html><font color=\"black\">You are the only user in this conversation</font></html>";
			}
			lblConversationWith.setText(finalText);
			
		}
		else{
			//if conversation doesn't exist, addTab.
			GUI.addTab(CID, users);
		}
		
	}
	
	
	/**
	 * This creates a new conversation between 2 users
	 * @param arrMessage
	 * @modifies JTabbedPane tabbedRightPane by adding new ConversationTab.
	 */
	private void conversationStartUpdate(String[] arrMessage) {
		int conversationID = Integer.parseInt(arrMessage[2]);
		String user = arrMessage[4];
		GUI.addTab(conversationID, user);
	}
	
	/**
	 * We use HTML syntax to change the colors of typing users in the label.
	 * @param type TYPING, TYPED or CLEARED (color is set according to this)
	 * @param arrMsg The arrMessage so that we know which user in which conversation this applies to
	 * @modifies lblGetConversation with in the ConversationTab with relevant CID 
	 */
	
	private void typingMessage(String type, String[] arrMsg){
		String color = "black";
		if(type.equals("TYPING")){
			color = "red";
		}
		else if(type.equals("TYPED")){
			color = "green";
		}
		else if(type.equals("CLEARED")){
			color = "black";
		}
		int CID = Integer.parseInt(arrMsg[3]);
		String user = arrMsg[1];
		if((!user.equals(GUI.getUsername()))&&(GUI.cidToTabs.containsKey(CID))){
			JLabel lblConversationWith = GUI.cidToTabs.get(CID).getConversationWith();
			String labelText= lblConversationWith.getText();
			String[] splitMessage = labelText.split("<font color=\"(red|green|black)\">"+user+"</font>  ");
			if(splitMessage.length==2){
				String before = splitMessage[0];
				String during = "<font color=\""+color+"\">"+user+"</font>  ";
				String after = splitMessage[1];
				String total = before + during + after;
				lblConversationWith.setText(total);
			}
		
			
		}
		}
	}
	

