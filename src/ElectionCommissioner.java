
/**
 * @author CodeFather 
 * Child User class for Election Commissioner 
 * User GUI | BallotPrompt |
 */
public class ElectionCommissioner extends User  implements java.io.Serializable{


	ElectionCommissioner(String username, String password, String gender, 
			 String rank,     String major,    String college) {
		super(username, password, gender, rank, major, college);

	}

	public void UserGUI(User user) {
		new BallotPrompt();
	}

}
