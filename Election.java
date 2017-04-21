import java.util.HashMap;
import java.util.List;

public class Election  implements java.io.Serializable{
	String election_title;
	String commissioner;
	HashMap<String, User> votedUsers;
	HashMap<String, List<Candidate>> votes;
	static HashMap<String, Integer> summaryStatistcs = new HashMap<String, Integer>();
	
	public Election(String title, String comID){
		election_title = title;
		votedUsers = new  HashMap<String, User>();
		votes = new HashMap<String, List<Candidate>>();
		commissioner = comID;
	}
	public String toString(){
		return election_title;
	}
}
