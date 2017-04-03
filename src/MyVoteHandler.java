import java.io.IOException;  
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class MyVoteHandler extends Thread {
	ObjectInputStream brIn;
	ObjectOutputStream pwOut;
	static MyVoteServer server;
	Socket sock;
	
	MyVoteHandler(Socket socket, MyVoteServer serv) {
		try{
		server = serv;
		sock = socket;
		brIn = new ObjectInputStream(sock.getInputStream());
		pwOut = new ObjectOutputStream(sock.getOutputStream());

		}catch(Exception e){
			System.out.println("Error initiating MyVoteHandler");
		}
	}
	
	public void run(){
		String myVoteStream = new String();
				
		try {
			while(!brIn.equals(null)){
			myVoteStream = (String) brIn.readObject();
			String[] myVoteCommands = myVoteStream.split(" ");
			System.out.println(myVoteCommands[0]);
			switch(myVoteCommands[0]){
			
			case "<login>":
				if(validateLogin(myVoteCommands[1],myVoteCommands[2])){
					//if(server.users.get(myVoteCommands[1]) instanceof HSO||server.users.get(myVoteCommands[1]) instanceof ElectionCommissioner || !server.voted(myVoteCommands[1]))
					{
						pwOut.writeObject("<validated>");
						pwOut.writeObject(server.getUser(myVoteCommands[1]));
					}
					//else
					//pwOut.writeObject("<hasVoted>");
				}
				else	
				pwOut.writeObject("<invalid>");
			
			break;
			
			case "<vote>":
				String race = (String)brIn.readObject();
				int selected = (int)brIn.readObject();
				String votedUser = (String)brIn.readObject();
				server.addVote(race, selected, votedUser);
			break;			
			
			case "<addRace>":
				 RacePanel races =  (RacePanel) brIn.readObject();
				 server.addRace(races);
			break;
			
			case "<shutdown>":
				die();
			break;
			
			case "<getVotes>":
				pwOut.writeObject(server.getVotes());
			case "<certify>":
				pwOut.writeObject(server.getVotedUsers());
			break;
			case "<addElection>":
			Election e = (Election)brIn.readObject();
			server.addElection(e);
			break;
			case "<getElections>":
				Election[] elections = server.getElections();
				pwOut.writeObject(elections);
			break;
			case"<selectedElection>":
				String selectedE = (String)brIn.readObject();
				server.selectedElection(selectedE);
			default:
				pwOut.writeObject("<noQuery>");
			}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	public static boolean validateLogin(String inputUsername, String inputPassword) throws IOException{
		return server.validateLogin(inputUsername,inputPassword);
			
	}
	public void die(){
		try{
			sock.close();
			server.die();
		}catch(IOException bacon){
			System.out.print("exiting...");	
		}
		System.exit(0);
	}

}
