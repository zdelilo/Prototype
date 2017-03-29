import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Candidate implements java.io.Serializable{
	String name;
	private int tally;
	
	Candidate(String name) {
		this.name = name;
		tally = 0;
	}
	
	/**
	 * Increments current tally for candidate
	 */
	public void incramentTally(){
		tally++;
	}
	
	public static List<Candidate> createCandidates( List<String> a) {
		ArrayList<Candidate> c = new ArrayList<Candidate>();
		for(String name: a)
			c.add(new Candidate(name));

		return c;
	}

	public String toString(){
		String result = name + " " +"Current Tally: " + tally + "\n";
		return result;
	}
}
