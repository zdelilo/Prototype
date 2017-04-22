import java.util.HashMap;
import java.util.List;

public class Election  implements java.io.Serializable{
	String election_title;
	
	 HashMap<String, User> votedUsers;
	 HashMap<String, List<Candidate>> votes;
	 HashMap<String, Integer> summaryStatistcs ;
	
	public Election(String title)
	{
		election_title = title;
		votedUsers = new  HashMap<String, User>();
		votes = new HashMap<String, List<Candidate>>();
		summaryStatistcs= new HashMap<String, Integer>();
	}
	
	public String toString()
	{
		return election_title;
	}
}
