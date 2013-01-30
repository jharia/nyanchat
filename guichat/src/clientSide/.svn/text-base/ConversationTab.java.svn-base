package clientSide;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * 
 * @author jesika
 * ConversationTab is a Component that represents a tab in a conversation. 
 * JLabel lblConversationWith: label showing people in the conversation with HTML formatting (different colors show different typing status)
 * ChatClient chatClient: referenced chatClient, used to send GUI commands to Server.
 * String username: username of client
 * JTabbedPane tabbedRightPane: pane on which ConversationTabs are contained
 * int CID: Conversation ID of each tab
 * String users: users with whom conversation is initially started
 * HashMap<Integer,ConversationTab> cidToTabs: HashMap of CIDs to ConversationTabs
 * JTextPane chatHistoryArea: Chat history text area
 * JPanel panel_1: Panel on which all these components are contained
 * JButton btnAddUser: add user to conversation
 * JScrollPane scrollPane_1: scroll for text area
 * JTextArea enterChatMessage: place where chat message is entered
 * TypingState currentTypingState: typing state of user.
 * 
 */

public class ConversationTab extends Component {
	/**
	 * ConversationTab is a Component that represents a Tab of a Conversation.
	 * refer to fields below for descriptions
	 */
	private static final long serialVersionUID = 1L;
	private static JLabel lblConversationWith ;  
	private final ChatClient chatClient; 
	private final String username; 
	private final JTabbedPane tabbedRightPane;
	private final int CID; 
	private String users; 
	private HashMap<Integer,ConversationTab> cidToTabs;  
	private final JTextPane chatHistoryArea;
	private final JPanel panel_1; 
	private final JButton btnAddUser; 
	private final JScrollPane scrollPane_1; 
	private final JTextArea enterChatMessage; 
	private TypingState currentTypingState;  
	private int indexOfTab;
	
/**
 * Create the new tab. 
 * @param ChatClient chatClient that the user is connected to 
 * @param String username of the connected client 
 * @param JTabbedPane tabbedRightPane which is the pane in which to open a conversation tab 
 * @param int CID conversation id assigned by the server to the conversation in this tab 
 * @param String users of all the users in the conversation 
 * @oaram HashMap<Integer,ConversationTab> cidToTabs mapping the conversation id to the conversation tab 
 * @param JTable tableOnlineUsers of all the online users present 
 * 
 * @return 
 */

    // enum representing typing state
    public enum TypingState {
        TYPING, TYPED, CLEARED
    }
	
	public ConversationTab(final ChatClient chatClient,final String username,JTabbedPane tabbedRightPane, final int CID,String users, final HashMap<Integer,ConversationTab> cidToTabs, final JTable tableOnlineUsers){
		this.chatClient = chatClient;
		this.username = username;
		this.tabbedRightPane = tabbedRightPane;
		this.CID = CID;
		this.users=users;
		this.cidToTabs = cidToTabs; 
		
		// Create new conversation pane 
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(204, 255, 255));
		panel_1.setAutoscrolls(true);
		
		// Add JTable selection
		tabbedRightPane.addTab(""+this.users, panel_1);
		indexOfTab = tabbedRightPane.indexOfComponent(panel_1);
		
		
		currentTypingState = TypingState.CLEARED; 
		
		// Tab removed -> user has left the conversation 
		int count = tabbedRightPane.getTabCount(); 
		ButtonTabComponent buttonTab = new ButtonTabComponent(tabbedRightPane); 
		tabbedRightPane.setTabComponentAt(count-1, buttonTab); 
		buttonTab.button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chatClient.addInputMessage("REMOVEUSER "+username+" CID "+CID);
				cidToTabs.remove(CID); 
			}
		});

		// Adding users to conversation 
		btnAddUser = new JButton("Add user");
		btnAddUser.setHorizontalAlignment(SwingConstants.RIGHT);
		btnAddUser.setForeground(new Color(102, 153, 255));
		btnAddUser.setFont(new Font("Nanum Brush Script", Font.PLAIN, 25));
		btnAddUser.setBackground(Color.WHITE);
		btnAddUser.setAlignmentX(0.5f);
		btnAddUser.setActionCommand("Add user");
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try { 
				int row_user = tableOnlineUsers.getSelectedRow();
				int[] column = tableOnlineUsers.getSelectedColumns();
				String addedUser = tableOnlineUsers.getValueAt(row_user,column[0]).toString();
				chatClient.addInputMessage("ADDUSER " + addedUser + " CID " + CID); 
			} catch (Exception e_1) {
				try {
					ErrorDialog dialog = new ErrorDialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setResizable(false); 
					dialog.setVisible(true);
				} catch (Exception exc) {
					exc.printStackTrace();
				}
			}
			}
		});

		scrollPane_1 = new JScrollPane();
		
		// Set chatHistory area details 
		chatHistoryArea = new JTextPane();
		chatHistoryArea.setEditable(false);
		scrollPane_1.setViewportView(chatHistoryArea);
		chatHistoryArea.setMargin(new Insets(5, 5, 5, 5));
		chatHistoryArea.setForeground(new Color(102, 153, 255));
		chatHistoryArea.setText("");

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		// Set chat message area details
		enterChatMessage = new JTextArea();
		scrollPane.setViewportView(enterChatMessage);
		enterChatMessage.setText("");
		enterChatMessage.setMargin(new Insets(5, 5, 5, 5));
		enterChatMessage.setForeground(new Color(204, 51, 255));
		enterChatMessage.setBounds( 0, 0, 200, 200 );
		
		enterChatMessage.addFocusListener(new FocusListener() {

			/**
			 * Upon focusGained event in the enterChatMessage area, must activate state change mechanism setTypingState(). 
			 * @param FocusEvent arg0 focus event that detects the text area gaining focus
			 * @return 
			 */
			@Override
			public void focusGained(FocusEvent arg0) {
				setTypingState(); 
			}

			@Override
			public void focusLost(FocusEvent arg0) {
			}
		}); 

		enterChatMessage.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// Upon pressing enter, users send chat message 
				if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
					try { 
					  Calendar calendar = new GregorianCalendar();
					  String am_pm;
					  int hour = calendar.get(Calendar.HOUR);
					  int minute = calendar.get(Calendar.MINUTE);
					  String strMinute = ""+minute;
					  if(minute<10){
						  strMinute = "0"+minute;
					  }
					  if(calendar.get(Calendar.AM_PM) == 0)
					  am_pm = "AM";
					  else
					  am_pm = "PM";
					  String time = hour + ":" + strMinute + " " + am_pm + " " + username + " : " + enterChatMessage.getText();
					  chatClient.addInputMessage("CHATSENDER " + username + " CID " + CID + " DATA " + time + enterChatMessage.getText()); 
					  enterChatMessage.setText(""); 
					} 
					catch (Exception ex_1) {
						try {
							ErrorDialog dialog = new ErrorDialog();
							dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
							dialog.setResizable(false); 
							dialog.setVisible(true);
						} catch (Exception excpt) {
							excpt.printStackTrace();
						}
					}

				}
			}
		});

		// Add status from Controller here 
		String[] listUsers = users.split(" ");
		String htmlUsers = "<html>  ";
		for(String user:listUsers){
			htmlUsers+="<font color=\"black\">"+user+"</font>  ";
		}
		htmlUsers+= "</html>";
		lblConversationWith = new JLabel(htmlUsers, SwingConstants.CENTER);
		lblConversationWith.setVerticalAlignment(SwingConstants.TOP);
		lblConversationWith.setForeground(new Color(153, 51, 255));
		lblConversationWith.setFont(new Font("Nanum Brush Script", Font.BOLD, 30));
		lblConversationWith.setBounds(new Rectangle(0, 0, 0, 20));
		lblConversationWith.setBackground(new Color(204, 255, 255));
		lblConversationWith.setAutoscrolls(true);
		lblConversationWith.setAlignmentX(0.5f); 
		
		// Layout panel 
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
				gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
						.addGap(16)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
								.addComponent(scrollPane_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
								.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
								.addGroup(Alignment.LEADING, gl_panel_1.createSequentialGroup()
										.addComponent(lblConversationWith)
										.addPreferredGap(ComponentPlacement.RELATED, 155, Short.MAX_VALUE)
										.addComponent(btnAddUser)))
										.addGap(48))
				);
		gl_panel_1.setVerticalGroup(
				gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
						.addGap(8)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblConversationWith)
								.addComponent(btnAddUser))
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 331, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
								.addGap(15))
				);
		panel_1.setLayout(gl_panel_1);
		
	} 
	/**
	 * Gets lblConversationWith
	 * @return lblConversationWith
	 */
	public JLabel getConversationWith() {
		return lblConversationWith; 
	}
	/**
	 * Gets chatHistoryArea
	 * @return chatHistoryArea
	 */
	public JTextPane getChatHistory(){
		return chatHistoryArea;
	}
	/**
	 * Gets tabbedRightPane
	 * @return tabbedRightPane
	 */
	public JTabbedPane getTabbedRightPane() {
	    return tabbedRightPane;
	}
	/**
	 * Gets panel_1
	 * @return panel_1
	 */
	public JPanel getPanel1() {
	    return panel_1;
	}
	/**
	 * Creates another thread to set typing state of the user entering a message (500 ms frequency).
	 */
	   public void setTypingState() {
	        new Thread(new Runnable() {
	            public void run() {
	                String previousTextInTypeBox = enterChatMessage.getText();
	                while (enterChatMessage.isFocusOwner()) {
	                    String newTextInTypeBox = enterChatMessage.getText();
	                    switch (currentTypingState) {
	                    case TYPING:
	                        if (newTextInTypeBox.isEmpty()) {
	                            currentTypingState = TypingState.CLEARED;
	                            chatClient.inputQueue.add("CLEARED "+username+" CID "+CID); 
	                            
	                        } else if (newTextInTypeBox
	                                .equals(previousTextInTypeBox)) {
	                            currentTypingState = TypingState.TYPED;
	                            chatClient.inputQueue.add("TYPED "+username+" CID "+CID);
	                           
	                        }
	                        break;
	                    case TYPED:
	                        if (newTextInTypeBox.isEmpty()) {
	                            currentTypingState = TypingState.CLEARED;
	                            chatClient.inputQueue.add("CLEARED "+username+" CID "+CID);
	                            
	                        } else if (!newTextInTypeBox
	                                .equals(previousTextInTypeBox)) {
	                            currentTypingState = TypingState.TYPING;
	                            chatClient.inputQueue.add("TYPING "+username+" CID "+CID);
	                            
	                        }
	                        break;
	                    case CLEARED:
	                        if (!newTextInTypeBox.isEmpty()) {
	                            currentTypingState = TypingState.TYPING;
	                            chatClient.inputQueue.add("TYPING "+username+" CID "+CID);
	                           
	                        }
	                        // Since there is no way to get from CLEARED to TYPED without going through TYPING
	                        break;
	                    }
	                    previousTextInTypeBox = newTextInTypeBox;
	                    try {
	                        Thread.sleep(500);
	                    } catch (InterruptedException e) {
	                    }
	                }

	                if (currentTypingState == TypingState.TYPING) {
	                    currentTypingState = TypingState.TYPED;
	                    chatClient.inputQueue.add("TYPED "+username+" CID "+CID);
                        
	                }
	            }
	        }).start();
	    }
	   
	   /**
	    * Gets the hashmap from conversation id to tabs. 
	    * @return cidToTabs
	    */
	   public HashMap<Integer,ConversationTab> getCIDToTabs(){
		   return cidToTabs;
	   }
	   /**
	    * Changes the title of the current tab
	    * @param title : The string to change the title to
	    */
	   
		public void setTitle(String title) {
	        tabbedRightPane.setTitleAt(indexOfTab, title);
	    }
}
