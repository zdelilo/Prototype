import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * @author CodeFather 
 * Abstract Parent User Class
 * Each user has a | Username | Password | UserGUI |
 */
public abstract class User implements java.io.Serializable{

	String username;
	String password;	
	String gender;
	String rank;
	String major;
	String college;
	boolean voted;
	
	User(String username, String password, String gender, 
		 String rank,     String major,    String college){
		
		voted = false;
		this.username = username;
		this.password = password;
		this.gender = gender;
		this.rank = rank;
		this.major = major;
		this.college = college;
	}

	/**Each GUI takes in a user**/
	public abstract void UserGUI(User user);

	public String toString(){
		String result = "[ " + username    + "  " +
						   gender   + "  " + 
						   rank     + "  " +
						   major    + "  " +
						   college  + " ]  ";
		return result;
	}
	public static void main(String[]args){
		
	}


}
