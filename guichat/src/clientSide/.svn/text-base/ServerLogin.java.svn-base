package clientSide;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.SwingConstants;

/**
 * 
 * @author jesika
 * ServerLogin class initializes the dialog that allows users to login to a server and specifiy an IP address.
 */
public class ServerLogin extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JLabel lblWelcomeToNyanchat;
	private JLabel lblUsername;
	JLabel lblNewLabel;
	private JButton okButton;
	private JPanel panel;
	
    /** Returns an ImageIcon, or null if the path was invalid. 
     * @throws MalformedURLException */
    protected static ImageIcon createImageIcon(String path,
                                               String description) {
        java.net.URL imgURL;
		try {
			imgURL = new URL(path);
            return new ImageIcon(imgURL, description);

		} catch (MalformedURLException e) {
			System.err.println("Couldn't find file: " + path);
            return null;
		}
    }
	/**
	 * Create the dialog to login. User enters username, and either chooses to
	 * hit Enter or press Login button to login. To cancel login, user presses
	 * Cancel button.
	 * Have not added in password functionality yet (until we get Database, no use)
	 */
	public ServerLogin() {
	//	ImageIcon imageIcon = createImageIcon("Nyan.gif", "NyanCat logo");
		setTitle("NyanChat: Login");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setSize(new Dimension(100, 75));
		contentPanel.setBackground(new Color(204, 255, 255));
		contentPanel.setForeground(new Color(0, 0, 0));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		final JPanel buttonPane = new JPanel();

		{
			lblWelcomeToNyanchat = new JLabel("Enter server IP to connect.");
			lblWelcomeToNyanchat.setHorizontalAlignment(SwingConstants.CENTER);
			lblWelcomeToNyanchat.setFont(new Font("Nanum Brush Script",
					Font.BOLD, 30));
			lblWelcomeToNyanchat.setForeground(new Color(153, 51, 255));
		}
		try {
			ImageIcon icon = createImageIcon("http://www.nyancatgif.com/images/nyan-cat-gif/nyan-cat-white-background.gif",
			        "a pretty but meaningless splat");
			Image img = icon.getImage(); 
			Image scaledImg = img.getScaledInstance(100, 75, java.awt.Image.SCALE_SMOOTH); 
			ImageIcon newIcon = new ImageIcon(scaledImg);
			lblNewLabel = new JLabel(newIcon, JLabel.CENTER);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		contentPanel.setLayout(new BorderLayout(0, 0));
		contentPanel.add(lblWelcomeToNyanchat, BorderLayout.NORTH);
		contentPanel.add(lblNewLabel);
		{
			panel = new JPanel();
			panel.setBorder(null);
			panel.setBackground(new Color(204, 255, 255));
			contentPanel.add(panel, BorderLayout.SOUTH);
			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
			{
				lblUsername = new JLabel("IP Address");
				panel.add(lblUsername);
				lblUsername.setFont(new Font("Nanum Brush Script", Font.BOLD, 35));
				lblUsername.setForeground(new Color(153, 153, 255));
			}
			
		// Testing
		textField = new JTextField("localhost");
		panel.add(textField);
		textField.setToolTipText("Enter the server's IP Address here");
	
		}
		{
			buttonPane.setBackground(new Color(153, 255, 255));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				final JButton cancelButton = new JButton("Cancel");
				cancelButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						cancelButton.setForeground(new Color(128,0,128)); 
					}
					@Override
					public void mouseExited(MouseEvent e) {
						cancelButton.setForeground(new Color(102, 51, 255));
					}
				});
				cancelButton.setFont(new Font("Nanum Brush Script", Font.BOLD,
						25));
				cancelButton.setForeground(new Color(102, 51, 255));
				cancelButton.setBackground(new Color(153, 153, 255));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose(); 
					}
				});
				buttonPane.setLayout(new GridLayout(0, 2, 0, 0));
				{
					okButton = new JButton("Login");
					okButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
					buttonPane.add(okButton);
					okButton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseEntered(MouseEvent arg0) {
							okButton.setForeground(new Color(128,0,128)); 
						}
						@Override
						public void mouseExited(MouseEvent arg0) {
							okButton.setForeground(new Color(102, 51, 255));
						}
					});
					okButton.setForeground(new Color(102, 51, 255));
					okButton.setFont(new Font("Nanum Pen Script", Font.BOLD, 25));
					okButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
                    final String serverIP = textField.getText(); 
                    setVisible(false); 
                   // try {
                    	Thread t1 = new Thread(){
                    		public void run(){
        						try {
									new ChatClient(new Socket(serverIP,4444));
								} catch (UnknownHostException e) {
									setVisible(true);
									lblWelcomeToNyanchat.setText("Please re-enter IP/Port");

								} catch (IOException e) {
									setVisible(true);
									lblWelcomeToNyanchat.setText("Please re-enter IP/Port");

								}
                    		}
                    	};
                    	t1.start();
                    	
					}});
					okButton.setBackground(new Color(153, 153, 255));
					okButton.setActionCommand("Connect");
					getRootPane().setDefaultButton(okButton);
				}
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
