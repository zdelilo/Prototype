import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * @author CodeFather 
 * Abstract Parent User Class
 * Each user has a | Username | Password | UserGUI |
 */
public abstract class User {

	String username;
	String password;
	
	static HashMap<String,User> users = new HashMap<String,User>();
	
	
	User(String username, String password){
		this.username = username;
		this.password = password;
	}

	public abstract void UserGUI();

	public static void main(String[]args){
		
	}


}
