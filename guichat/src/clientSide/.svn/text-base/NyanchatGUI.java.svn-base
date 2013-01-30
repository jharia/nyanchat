package clientSide;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author jesika
 * NyanchatGUI is the main GUI window that is created, that creates conversation tabs, maintains a list of online users and such. 
 */
public class NyanchatGUI {

	private JFrame frmNyanchatUsername; // JFrame that hosts everything 
	private JLabel lblOnlineUsers; //label for online users 
	public JTable tableOnlineUsers; //Table of online users
	private ChatTableModel onlineUsers; //TableModel that contains online user data
	public HashMap<Integer, ConversationTab> cidToTabs; //HashMap of all conversation IDs to ConversationTab
	public ChatClient chatClient; //Client to which this GUI interfaces
	private JTabbedPane tabbedRightPane; //Pane of conversations
	public String username; //Username of person using GUI.
	private JButton btnLogout; //Logout Button
	private JButton btnNewButton; //New Conversation button

	/**
	 * NyanchatGUI constructor. 
	 */
	public NyanchatGUI(String username, ChatClient chatClient) {
		initialize(username,chatClient);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(final String username, final ChatClient chatClient) {
		
		this.chatClient = chatClient;
		this.username = username;
		cidToTabs = new HashMap<Integer, ConversationTab>();
		/*
		 * Initialize basic frame
		 */
		setFrmNyanchatUsername(new JFrame());
		getFrmNyanchatUsername().setVisible(true);
		getFrmNyanchatUsername().setTitle("NyanChat :: "+username);
		getFrmNyanchatUsername().getContentPane().setPreferredSize(new Dimension(800, 600));
		getFrmNyanchatUsername().setBounds(100, 100, 800, 600);
		getFrmNyanchatUsername().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrmNyanchatUsername().setResizable(false);

		/*
		 * Online users table
		 */
		String col[] = {"Online Users"}; 
		String data[] [] = {{}};
		onlineUsers = new ChatTableModel(data,col);
		JPanel leftPanel = new JPanel();
		leftPanel.setSize(100,600);
		leftPanel.setBackground(new Color(204, 255, 255));
		tableOnlineUsers = new JTable(onlineUsers);
		tableOnlineUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableOnlineUsers.setFillsViewportHeight(true);
		tableOnlineUsers.setSurrendersFocusOnKeystroke(true);
		tableOnlineUsers.setForeground(new Color(153, 51, 204));
		tableOnlineUsers.setFont(new Font("Nanum Brush Script", Font.BOLD, 21));
		tableOnlineUsers.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(153, 153, 255), null, null, null));
		tableOnlineUsers.setSelectionBackground(new Color(204, 51, 204));
		tableOnlineUsers.setCellSelectionEnabled(true);

		/*
		 * Make right tabbed pane.
		 */
		tabbedRightPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedRightPane.setOpaque(true);
		tabbedRightPane.setFont(new Font("Nanum Brush Script", Font.PLAIN, 20));
		tabbedRightPane.setForeground(new Color(255, 255, 255));
		tabbedRightPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		tabbedRightPane.setBackground(new Color(204, 153, 255));

		/*
		 * Make welcome panel. 
		 */
		JPanel panel_welcome = new JPanel(); 
		panel_welcome.setBackground(new Color(204, 255, 255));
		panel_welcome.setAutoscrolls(true);
		tabbedRightPane.addTab("Welcome", null, panel_welcome, null);
		panel_welcome.setLayout(new BoxLayout(panel_welcome, BoxLayout.Y_AXIS));
		panel_welcome.add(new Box.Filler(new Dimension(0,50), new Dimension(0,50), new Dimension(0,50))); 
		JLabel lblWelcomeToNyanchat = new JLabel(username + ", Welcome to NyanChat! ");
		lblWelcomeToNyanchat.setAlignmentX(0.5f);
		lblWelcomeToNyanchat.setAlignmentY(0.0f);
		lblWelcomeToNyanchat.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeToNyanchat.setForeground(new Color(153, 51, 255));
		lblWelcomeToNyanchat.setFont(new Font("Nanum Brush Script", Font.BOLD, 30));
		panel_welcome.add(lblWelcomeToNyanchat);
		panel_welcome.add(new Box.Filler(new Dimension(0,10), new Dimension(0,10), new Dimension(0,10))); 
		JLabel lblGetStartedIn = new JLabel("Get started in two steps:");
		lblGetStartedIn.setAlignmentX(0.5f);
		lblGetStartedIn.setAlignmentY(0.0f);
		lblGetStartedIn.setHorizontalAlignment(SwingConstants.CENTER);
		lblGetStartedIn.setForeground(new Color(51, 153, 255));
		lblGetStartedIn.setFont(new Font("Nanum Brush Script", Font.PLAIN, 25));
		panel_welcome.add(lblGetStartedIn);
		panel_welcome.add(new Box.Filler(new Dimension(0,10), new Dimension(0,10), new Dimension(0,10))); 
		JLabel lblSelectAn = new JLabel("1) Select an online user");
		//JLabel lblSelectAn = new JLabel("<html><font color=#FF0000>My</font> <font color=#00FF00>label</font></html>");
		lblSelectAn.setAlignmentY(0.0f);
		lblSelectAn.setAlignmentX(0.5f);
		lblSelectAn.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectAn.setForeground(new Color(51, 153, 255));
		lblSelectAn.setFont(new Font("Nanum Brush Script", Font.PLAIN, 25));
		panel_welcome.add(lblSelectAn);
		panel_welcome.add(new Box.Filler(new Dimension(0,10), new Dimension(0,10), new Dimension(0,10))); 
		JLabel lblClicknew = new JLabel("2) Click 'New Convo'");
		lblClicknew.setAlignmentY(0.0f);
		lblClicknew.setAlignmentX(0.5f);
		lblClicknew.setHorizontalAlignment(SwingConstants.LEFT);
		lblClicknew.setForeground(new Color(51, 153, 255));
		lblClicknew.setFont(new Font("Nanum Brush Script", Font.PLAIN, 25));
		panel_welcome.add(lblClicknew);
		// Set Welcome tab as first tab of the tabbedRightPane
		tabbedRightPane.setTabComponentAt(0, new ButtonTabComponent(tabbedRightPane));
		
		/*
		 * Layout
		 */
		
		GroupLayout groupLayout = new GroupLayout(getFrmNyanchatUsername().getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(leftPanel, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(tabbedRightPane, GroupLayout.PREFERRED_SIZE, 613, GroupLayout.PREFERRED_SIZE)
						.addGap(42))
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(leftPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
								.addComponent(tabbedRightPane, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 578, GroupLayout.PREFERRED_SIZE))
								.addContainerGap())
				);
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

		/*
		 * Logout button. 
		 */

		btnLogout = new JButton("Logout");
		btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnLogout.setForeground(new Color(102, 153, 255));
		btnLogout.setFont(new Font("Nanum Brush Script", Font.PLAIN, 25));

		// When logout button is clicked. 
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try { 
					System.exit(0); //exits the entire system, including ending Client
				} catch (Exception ex) {
					ex.printStackTrace(); 
					try {
						ErrorDialog dialog = new ErrorDialog();
						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialog.setResizable(false); 
						dialog.setVisible(true);
					} catch (Exception exp) {
						exp.printStackTrace();
					}
				}

			}
		});

		leftPanel.add(new Box.Filler(new Dimension(0,10), new Dimension(0,10), new Dimension(0,10))); 
		leftPanel.add(btnLogout);
		leftPanel.add(new Box.Filler(new Dimension(0,10), new Dimension(0,10), new Dimension(0,10))); 

		/*
		 * New conversation.
		 */
		btnNewButton = new JButton("New convo");
		btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnNewButton.setForeground(new Color(102, 153, 255));
		btnNewButton.setFont(new Font("Nanum Brush Script", Font.PLAIN, 25));

		// When new conversation button is clicked. 
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int row = tableOnlineUsers.getSelectedRow(); //Get selected user
					int[] column = tableOnlineUsers.getSelectedColumns();
					String rcpt = tableOnlineUsers.getValueAt(row,column[0]).toString(); 
					String messages= "CREQ SENDER "+username+" RCPT "+rcpt; //send message to Server
					chatClient.addInputMessage(messages); 

				} 
				catch (Exception exc){
					ErrorDialog dialog = new ErrorDialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setResizable(false); 
					dialog.setVisible(true);
				}
			}});
		leftPanel.add(btnNewButton);
		leftPanel.add(new Box.Filler(new Dimension(0,10), new Dimension(0,10), new Dimension(0,10))); 

		lblOnlineUsers = new JLabel("Online Users", SwingConstants.CENTER);
		lblOnlineUsers.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblOnlineUsers.setVerticalAlignment(SwingConstants.TOP);
		lblOnlineUsers.setBounds(new Rectangle(0, 0, 0, 20));
		lblOnlineUsers.setSize(100,10); 
		lblOnlineUsers.setFont(new Font("Nanum Brush Script", Font.BOLD, 30));
		lblOnlineUsers.setForeground(new Color(153, 51, 255));
		lblOnlineUsers.setAutoscrolls(true);
		lblOnlineUsers.setBackground(new Color(204, 255, 255));
		leftPanel.add(lblOnlineUsers);
		leftPanel.add(new Box.Filler(new Dimension(0,10), new Dimension(0,10), new Dimension(0,10))); 
		leftPanel.add(tableOnlineUsers);
		getFrmNyanchatUsername().getContentPane().setLayout(groupLayout); 

	}

	/**
	 * 
	 * @param CID Conversation ID
	 * @param users List of users in the conversation when it's initiated
	 */
	protected void addTab( final int CID, String users) {
		ConversationTab newConvo = new ConversationTab(chatClient,username,tabbedRightPane,CID,users,cidToTabs,tableOnlineUsers);
		cidToTabs.put(CID,newConvo); 
	}

	/**
	 * Method returns the JFrame. 
	 * @return frmNyanchatUsername
	 */
	public JFrame getFrmNyanchatUsername() {
		return frmNyanchatUsername;
	}
	
	/**
	 * Method returns the online users table. 
	 * @return onlineUsers
	 */
	public DefaultTableModel getOnlineUsersTable() {
		return onlineUsers; 
	}
	
	/**
	 * Method sets the JFrame setFrmNyanchatUsername 
	 * @param frmNyanchatUsername
	 */

	public void setFrmNyanchatUsername(JFrame frmNyanchatUsername) {
		this.frmNyanchatUsername = frmNyanchatUsername;
	}
	
	/**
	 * Method gets username. 
	 * @return username
	 */
	public String getUsername() {
		return username;
	}
}
