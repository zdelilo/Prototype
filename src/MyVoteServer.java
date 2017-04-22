import java.io.FileInputStream; 
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class MyVoteServer extends Thread  {

	
	static HashMap<String, Election> elections;
	static Election election;
	static HashMap<String, User> users;
	String selectedE;

   
	ServerSocket ss;
	
	/**
	 * MyVoteServer Constructor
	 * Deserializes API file to initialize the users hashmap
	 */
	MyVoteServer(boolean runDeserialize){
		 users = new HashMap<String,User>();
		 elections = new  HashMap<String, Election>();
		 
		 if(runDeserialize)
		 API.deserializeAPI();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 * >>>Starts the server
	 */
	public void run(){
		try{
			
			ss = new ServerSocket(50000);
			System.out.println("MyVote Server Started");
			
			while(true){
				Socket myVote = ss.accept();
				MyVoteHandler mv = new MyVoteHandler(myVote, this);
				System.out.println("Started MyVoteHandler");
				mv.start();
			}
		}
		catch(SocketException shock){	
			System.out.println("Already running");
		}
		catch(IOException e){
			System.out.println("Server Already Running");
		}
	}
	/**
	 * @param inputUsername
	 * @param inputPassword
	 * @return validates user | true | false |
	 **/
	public boolean validateLogin(String inputUsername, String inputPassword){	
		return (users.containsKey(inputUsername) && inputPassword.equals(users.get(inputUsername).password));
	}
	
	public boolean voted(String username){
		return election.votedUsers.containsKey(username);
	}
	
	/**
	 * @param username
	 * @return user given username
	 **/
	public User getUser(String username){
		return users.get(username.trim());
	}
	
	/**@return an array of the elections*/
	public Election[] getElections(){
		return elections.values().toArray(new Election[0]);
	}
	
	/**
	 * Takes in the name of an election assigns the attribute election
	 * to the selected election
	 * @param selected - Name of the election to select
	 * @return the election selected
	 */
	public String selectedElection(String selected){
		selectedE = selected;
		election = elections.get(selectedE);
		return selectedE;
	}
	/**
	 * Takes in an eleciton and adds it to the server
	 * 		|backs up the election after it is added
	 * @param e - the election to be added to the server
	 */
	public void addElection( Election e){
		elections.put(e.election_title, e);
		backup();
	}
	
	/** @return boolean | true election is up | false no elections up | */
	public boolean electionUp(){
		return !elections.isEmpty();
	}
	
	/**
	 * Takes in the statitic (ie gender, college, etc...) 
	 * 		| increments the statistic for the election
	 * @param data - statistic to be incremented
	 */
	public void updateSummaryStatistics(String data, boolean add){

		if(election.summaryStatistcs != null && election.summaryStatistcs.containsKey(data)){
			int value = election.summaryStatistcs.get(data.trim()).intValue();
			if(add == true)
			election.summaryStatistcs.put(data.trim(), value+1);
			else	
			election.summaryStatistcs.put(data.trim(), value-1);
		}
		else election.summaryStatistcs.put(data, 1);
		backup();
	
	}
	
	/**@return hashmap containing the summary statisitcs */
	public HashMap<String, Integer> getStatistics()
	{
		return election.summaryStatistcs;
	}
	
	/**
	 * @param race
	 * @param candidates
	 * adds races to the HashMap which keeps track of votes
	 * votes is a hashmap with key [race = {list of candidates}]
	 * candidates has | candidate name | vote tallies |
	 **/
	public void addRace(RacePanel race) {
		System.out.println("selectedE " + election);
		election.votes.put(race.race_title, race.candidates);
		System.out.println(elections);
		backup();
	}
	
	/**
	 * @param race
	 * @param selected
	 * takes in a vote and updates the vote tally for a candidate
	 * candidates vote tallies are accessed using the race and 
	 * index of candidate within the candidate list
	 * NOTE >>> Backs up server with each vote (testing purposes)
	 **/
	public void addVote(String race, int selected, String votedUser){
		System.out.println("vote server");
		election.votes.get(race.trim()).get(selected).incramentTally();
		election.votedUsers.put(votedUser, users.get(votedUser));
		System.out.println(election.votedUsers);
		System.out.println(election.votes);
		backup();
	}
	
	/** @return users in api**/
	public User[] getVotedUsers(){
		return  election.votedUsers.values().toArray(new User[0]);
		
	}
	
	public HashMap<String, List<Candidate>> getVotes(){
		return election.votes;
	}
	public HashMap<String, List<Candidate>> getRecount(){
		return election.votes;
		
	}
	
	/**
	 * Takes in information rewuired to disqualify a user and removes the user information
	 * and decrements the tally of the voted candidate
	 * 					|User - user's summary statistidcs must be removed from the summaryStastics
	 * @param userName 		- User whose statistics need to be removed
	 * @param raceName 		- name of the race used to access the candidate
	 * @param candidateName - name of the candidate who's tally must be decremented
	 * @return
	 */
	public String removeVote(String userName,String raceName,String candidateName)
	{
		List<Candidate>candidates = election.votes.get(raceName);
		System.out.println(candidates);
		if(Candidate.contains(candidates, candidateName))
		{	
			Candidate.getCandidate(candidates, candidateName).decrementTally();	
			removeUserStatistics(userName);
			return "Disqualified: " + candidateName;
		}
		
		else return "Not Disqualified. Please check the values.";
	}
	
	public void removeUserStatistics(String userName)
	{
		User removeUser = users.get(userName);

		for(String data: removeUser.dataSet())
			updateSummaryStatistics(data, false);
	}
	
	/**
	 * Serializes/saves the server's "ballot" >>> ballot panels
	 * and current votes **/
	public static void backup(){
		try
		{	
			ObjectOutputStream apiOut = new ObjectOutputStream(new FileOutputStream("BackupAPI.ser"));
			apiOut.writeObject(elections);
			apiOut.close();
			System.out.println("Backup Complete!");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * deserializes/restores the servers "ballot" >>> Votes holds 
	 * races and Candidates necessary to re-build a ballot**/
	@SuppressWarnings("unchecked")
	public static void restore(){
		
		try{
			
			ObjectInputStream apiIn = new ObjectInputStream(new FileInputStream("BackupAPI.ser"));
			elections = (HashMap<String, Election>) apiIn.readObject();
			System.out.println("Restoration Complete!");
			System.out.println(elections);
			apiIn.close();
			
		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Backup Not Found...");
			e.printStackTrace();
		}
	}
	
	/**Kills the server on exit**/
	public void die(){
		try{
			ss.close();
		}catch(IOException e){
			System.out.print("exiting...");
		}
		System.exit(0);
	}
	
	public static void resetElection(){
		new MyVoteServer(true).start();
		backup();
	}

	public static void main(String[]args){
		resetElection();
		restore();
	}
	
	
}
