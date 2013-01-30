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
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Component;
import javax.swing.SwingConstants;

/**
 * 
 * @author jesika
 * Standard error dialog shown when something goes wrong. 
 *
 */
public class ErrorDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblWelcomeToNyanchat;
	JLabel lblNewLabel; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ErrorDialog dialog = new ErrorDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setResizable(false); 
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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
	 */
	public ErrorDialog() {
		setMinimumSize(new Dimension(100, 75));
		setTitle("NyanChat: Login");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setSize(new Dimension(100, 75));
		contentPanel.setBackground(new Color(204, 255, 255));
		contentPanel.setForeground(new Color(0, 0, 0));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		{
			lblWelcomeToNyanchat = new JLabel("Your request could not completed at this time.");
			lblWelcomeToNyanchat.setHorizontalAlignment(SwingConstants.CENTER);
			lblWelcomeToNyanchat.setFont(new Font("Nanum Brush Script", Font.BOLD, 27));
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		contentPanel.setLayout(new BorderLayout(0, 0));
		contentPanel.add(lblWelcomeToNyanchat, BorderLayout.NORTH);
		contentPanel.add(lblNewLabel);
		final JButton cancelButton = new JButton("Ok");
		contentPanel.add(cancelButton, BorderLayout.SOUTH);
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
				System.out.println("Cancel button");
				dispose(); 
			}
		});
		cancelButton.setActionCommand("Cancel");
	}

}
