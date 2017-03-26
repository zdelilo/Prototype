import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

public class Login extends JFrame implements ActionListener{
	String inputUsername;
	String inputPassword;
	User user;
	JTextField usernameFLD;
	JTextField passwordFLD;
	JLabel tryAgain;

	HashMap<String,User> users = new HashMap<String,User>();
	
	 Login(){
		 
		 	users.put("Rnkambara", new ElectionCommissioner("Rnkambara","helloworld"));
			users.put("Jjkambara", new Student("Jjkambara","hellokitty"));
			users.put("Dapatrick", new HSO ("Dapatrick","jessica"));
		 
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

	public boolean validLogin(){
		
		return (users.containsKey(inputUsername) && inputPassword.equals(users.get(inputUsername).password));
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("login")){
			inputUsername = usernameFLD.getText();
			inputPassword = passwordFLD.getText();
			user = users.get(inputUsername);
			
			if(validLogin())
				user.UserGUI();
			
			else
				tryAgain.setText("Incorrect username of password. Please Try Again");
		}
	}
	public static void main(String[]args){
		new Login();
	}
}
