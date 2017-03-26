import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JTextField;

public abstract class User {

	String username;
	String password;
	String inputUsername;
	String inputPassword;
	JTextField usernameFLD;
	JTextField passwordFLD;
	JLabel tryAgain;
	
	
	static HashMap<String,User> users = new HashMap<String,User>();
	
	
	User(String username, String password){
		this.username = username;
		this.password = password;
	}

	public abstract void UserGUI();

	public static void main(String[]args){
		
	}


}
