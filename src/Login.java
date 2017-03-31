import java.awt.Color; 
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

/**
 * @author CodeFather 
 * Login GUI for all users
 * Validates user login | Brings up UserGUI given validLogin |
 */
public class Login extends JFrame implements ActionListener{

	private static final long serialVersionUID = -4896195070277297392L;
	String inputUsername;
	String inputPassword;
	User user;
	JTextField usernameFLD;
	JTextField passwordFLD;
	JLabel tryAgain;
	
	ObjectInputStream brIn;
	ObjectOutputStream pwOut;	
	Socket sock;
	
	 Login(){
		 /**Initialzies Server**/
		 try {
				sock = new Socket("127.0.0.1",50000);
				pwOut = new ObjectOutputStream(sock.getOutputStream());
				brIn = new ObjectInputStream(sock.getInputStream());    	
			} catch (IOException e) {
				
				e.printStackTrace();
			}

		JPanel mainPanel = new JPanel(new GridBagLayout());		
	    JPanel tablePanel = new JPanel(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
		JPanel userPanel = new JPanel();
		JPanel passPanel = new JPanel();
		JPanel myVote = new JPanel();
		
		/**Login GUI Components**/
		 usernameFLD = new JTextField("Rnkambara",20);
		 passwordFLD = new JTextField("helloworld",20);
		 
		JLabel userLabel = new JLabel("Username: ");
		JLabel passLabel = new JLabel("Password: ");
		
		JLabel myVoteLabel = new JLabel("MyVote Login: ");
		JButton login = new JButton("Login");
		login.setIcon(MyImages.smileIcon);
		login.setIconTextGap(10);
		tryAgain = new JLabel();
		tryAgain.setText("Incorrect username of password. Please Try Again...");
		tryAgain.setIcon(MyImages.frownIcon);
		tryAgain.setVisible(false);
		
		/**Adding componets to Panels**/
		mainPanel.setBackground(MyColors.deepBlue);
		
			/*UserName Panel*/
		userPanel.add(userLabel);
		userPanel.add(usernameFLD);
		userPanel.setBackground(MyColors.beechyBlue);
		userPanel.setBorder(new LineBorder(Color.black,2));
		
			/*Password Panel*/
		passPanel.add(passLabel);
		passPanel.add(passwordFLD);
		passPanel.setBackground(MyColors.seaGreen);
		passPanel.setBorder(new LineBorder(Color.black,2));
		
			/*MyVote Panel*/
		myVote.add(myVoteLabel);
		myVote.setBackground(MyColors.paleTurquoise);
		
			/*Table Panel*/
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0; 
		c.gridy = 0;
		tablePanel.add(myVote,c);
		c.gridx = 0; 
		c.gridy = 1;
		c.insets = new Insets(8,10,8, 10);
		tablePanel.add(userPanel,c);
		c.gridx = 0;
		c.gridy = 2;
		tablePanel.add(passPanel,c);
		c.gridx = 0;
		c.gridy = 3;
		login.setActionCommand("login");
		login.addActionListener(this);
		tablePanel.add(login,c);
		c.gridx = 0;
		c.gridy = 4;
		tablePanel.add(tryAgain,c);
		tablePanel.setBorder(new LineBorder(Color.white,3));
		tablePanel.setBackground(Color.white);
		
			/*Main Panel*/
		mainPanel.add(tablePanel);

		/**Sets Defaults for main Panel**/
		this.getContentPane().add(mainPanel);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(430,270);
        this.setVisible(true);  
        this.setIconImage(MyImages.codeFather.getImage());
        this.getRootPane().setDefaultButton(login);
        
	}
		
	/**
	* Action Preformed Method - Checks what buttons have been pressed
	* @param e - Action event (actions enacted )
	* |Login - Grabs inputted username and password and checks if valid login
	* 		|Valid - The user's GUI is brought up
	* 		|Invalid - User is prompted to try again
	* **/
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("login")){
			inputUsername = usernameFLD.getText();
			inputPassword = passwordFLD.getText();
				/**Initialize socket to access server**/
			try {

				/**Writes <login> key to server**/
				pwOut.writeObject("<login> " + inputUsername + " " + inputPassword);
				
				
				/***Reads from server to see if username has been validated (or not)**/
				String readObject = (String) brIn.readObject();
				
					/**Validates user, then user object is read from class**/
				if(readObject.equals("<validated>")){
					user = (User) brIn.readObject();
					user.UserGUI();
					this.setVisible(false);
					
				}	/**Invalid - Error message to window**/
				else if(readObject.equals("<invalid>"))
					tryAgain.setVisible(true);
				
			} catch (IOException | ClassNotFoundException e1) {
				
				e1.printStackTrace();
			}
		}
	}
	public static void main(String[]args){
		new Login();
	}
}
