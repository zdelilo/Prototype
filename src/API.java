import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Scanner;

public class API {
	@SuppressWarnings("resource")
	/**
	 * API Constructor 
	 * fills MyVoteServer with users
	 * 	| Students | Election Commissioners | HSO | OIT |
	 */
	API(){
		try{
		Scanner apiReader = new Scanner(new File("API.txt"));
		while(apiReader.hasNext()){
			
			String[] user = apiReader.nextLine().split(" ");
			
			if(user.length < 3)
				MyVoteServer.users.put(user[0], new Student(user[0], user[1]));
			if(user[0].equals("EC"))
				MyVoteServer.users.put(user[1], new ElectionCommissioner(user[1], user[2]));
			if(user[0].equals("HSO"))
				MyVoteServer.users.put(user[1], new HSO(user[1], user[2]));
			if(user[0].equals("OIT"))
				MyVoteServer.users.put(user[1], new OIT(user[1], user[2]));
		}
		
		}catch(IOException e){
			System.out.println("API not present...");
		}
	}
	
	/**
	 * Possible serializeAPI method
	 * TODO Serialize does not work for Abstract Data Types
	 * 		Create serizlize method to work for user HashMap
	 */
	public static void serializeAPI(){
		
		try{
			
			ObjectOutputStream apiOut = new ObjectOutputStream(new FileOutputStream("MyVoteAPI.ser"));
			apiOut.writeObject(MyVoteServer.users);
			apiOut.close();
			System.out.println("Serialization Complete!");
			
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Possible deserializeAPI method
	 * TODO Derialize does not work for Abstract Data Types
	 * 		Create derizlize method to work for user HashMap
	 */
	@SuppressWarnings("unchecked")
	public static void deserializeAPI(){
		
		try{
			
			ObjectInputStream apiIn = new ObjectInputStream(new FileInputStream("MyVoteAPI.ser"));
			MyVoteServer.users = (HashMap<String,User>) apiIn.readObject();
			System.out.println("Deserialization Complete!");
			apiIn.close();
			
			
		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("API Not Found...");
			e.printStackTrace();
		}
		
	}
	
}
