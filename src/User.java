import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * @author CodeFather 
 * Abstract Parent User Class
 * Each user has a | Username | Password | UserGUI |
 */
public abstract class User implements java.io.Serializable {

	String username;
	String password;	
	
	User(String username, String password){
		this.username = username;
		this.password = password;
	}

	public abstract void UserGUI();

	public static void main(String[]args){
		
	}


}
