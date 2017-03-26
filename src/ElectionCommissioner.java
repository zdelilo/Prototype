
/**
 * @author CodeFather
 * Child User class for Election Commissioner
 * User GUI | BallotPrompt |
 */
public class ElectionCommissioner extends User{


	ElectionCommissioner(String username, String password) {
		super(username, password);

	}

	public void UserGUI() {
		new BallotPrompt();
	}

}
