import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

	/**
	 * @author CodeFather 
	 * Race Panel - used to create panels that hold races and candidates
	 */
	public class RacePanel implements java.io.Serializable{
        	
       	 public transient  JPanel race;
       	 public String race_title;
       	 public transient JRadioButton button;
       	 public List<String> candidate_names;
       	 
       	 /**
       	  * Constructor for Race Panel
       	  * @param panel - JPanel 
       	  * @param race_title - Title for race
       	  * @param candidates - String array | holds names of candidates
       	  **/
       	 RacePanel(JPanel panel, String race_title, List<String>  candidates){
       		 this.race = panel;
       		 this.race_title = race_title;
       		 this.candidate_names = candidates;
       	 }
       	 
       	 /**
       	  * Takes in a JRadioButton and adds to Race Panel
       	  * @param button - Radio button to represent choice for candidate
       	  **/
       	 public void addButton(JRadioButton button){
       		 race.add(button);
       		 this.button = button;
       	 }
       	 
       	 /**
       	  * ToString method for class**/
       	 public String toString(){
       		 String result = race_title + "\n";
       		 
       		 for(String names: candidate_names)
       			 result += names + " ";
       		 return result;
       	 }

      } 