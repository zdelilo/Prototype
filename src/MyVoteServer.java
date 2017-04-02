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
	
	/**
	 * Hashmap holds all users 
	 * key - student's username
	 * value - User | EC | HSO | OIT | Student |
	 * **/
	static HashMap<String, User> users;
	static HashMap<String, List<Candidate>> votes;
	static HashMap<String, Integer> summaryStatistcs;
   
	ServerSocket ss;
	
	/**
	 * MyVoteServer Constructor
	 * Deserializes API file to initialize the users hashmap
	 */
	MyVoteServer(){
		 users = new HashMap<String,User>();
		 votes = new HashMap<String, List<Candidate>>();
		 summaryStatistcs = new HashMap<String, Integer>();
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
	/**
	 * @param username
	 * @return user given username
	 **/
	public User getUser(String username){
		return users.get(username.trim());
	}
	/**
	 * @param p
	 * saves an array which holds all of the panels contained on the race
	 * panels contain | race_title | candidates |
	 **/
	
	/**
	 * @param race
	 * @param candidates
	 * adds races to the HashMap which keeps track of votes
	 * votes is a hashmap with key [race = {list of candidates}]
	 * candidates has | candidate name | vote tallies |
	 **/
	public void addRace(RacePanel race) {
		votes.put(race.race_title, race.candidates);
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
		System.out.println(votes);
		votes.get(race.trim()).get(selected).incramentTally();
		users.get(votedUser).voted = true;
		
		backup();
	}
	
	
	
	/** @return users in api**/
	public User[] getUsers(){
		return  users.values().toArray(new User[0]);
	}
	
	public HashMap<String, List<Candidate>> getVotes(){
		return votes;
	}
	
	/**
	 * Serializes/saves the server's "ballot" >>> ballot panels
	 * and current votes
	 **/
	public static void backup(){
		
		try{	
			ObjectOutputStream apiOut = new ObjectOutputStream(new FileOutputStream("BackupAPI.ser"));
			apiOut.writeObject(votes);
			apiOut.close();
			System.out.println("Backup Complete!");

		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * deserializes/restores the servers "ballot" >>> Votes holds 
	 * races and Candidates necessary to re-build a ballot
	 **/
	@SuppressWarnings("unchecked")
	public static void restore(){
		
		try{
			
			ObjectInputStream apiIn = new ObjectInputStream(new FileInputStream("BackupAPI.ser"));
			votes = (HashMap<String, List<Candidate>>) apiIn.readObject();
			System.out.println("Restoration Complete!");
			System.out.println(votes);
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

	public static void main(String[]args){
		new MyVoteServer().start();
		restore();

	}
	
	
}
