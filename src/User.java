import java.awt.Color; 
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

public class User extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	String username;
	String password;
	String inputUsername;
	String inputPassword;
	JTextField usernameFLD;
	JTextField passwordFLD;
	JLabel tryAgain;
	User(String username, String password){
		this.username = username;
		this.password = password;
		loginGUI();
	}
	public void loginGUI(){
		JPanel mainPanel = new JPanel(new GridBagLayout());		
	    JPanel tablePanel = new JPanel(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
		JPanel userPanel = new JPanel();
		JPanel passPanel = new JPanel();
		JPanel myVote = new JPanel();
		
		/**Login GUI Components**/
		 usernameFLD = new JTextField("Jane Doe",20);
		 passwordFLD = new JTextField("********",20);
		JLabel userLabel = new JLabel("Username: ");
		JLabel passLabel = new JLabel("Password: ");
		JLabel myVoteLabel = new JLabel("MyVote Login: ");
		tryAgain = new JLabel();
		JButton login = new JButton("Login");
		
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
	 Returns true if User's username and password matches entered fields
	**/
	public boolean login() {
		return (username.equals(inputUsername) && password.equals(inputPassword));
	}
	
	/**
	 If login | retrieves inuted username and password and checks with login
	 checks what instance of user tried to login | calls correct user interface
	 **/
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("login")){
			inputUsername = usernameFLD.getText();
			inputPassword = passwordFLD.getText();
			
			if(login()){
				
			if(this instanceof ElectionCommissioner)
				new BallotPrompt();
			
			if(this instanceof Student){
				System.out.println("Student interface");
			}
			
			if(this instanceof HSO){
				System.out.println("HSO interface");
			}
			if(this instanceof HSO){
				System.out.println("OIT interface");
			}
				
			}
			else
				tryAgain.setText("Incorrect username of password. Please Try Again");
		}
	}
	
	public static void main(String[]args){
		@SuppressWarnings("unused")
		ElectionCommissioner ec = new ElectionCommissioner("rnkambara", "helloworld");
	
	}


}
