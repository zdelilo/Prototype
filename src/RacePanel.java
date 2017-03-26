import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

	/**
	 * @author CodeFather 
	 * Race Panel - used to create panels that hold races and candidates
	 */
	public class RacePanel{
        	
       	 public JPanel race;
       	 public String race_title;
       	 public JRadioButton button;
       	 public String[] candidate_names;
       	 
       	 /**
       	  * Constructor for Race Panel
       	  * @param panel - JPanel 
       	  * @param race_title - Title for race
       	  * @param candidates - String array | holds names of candidates
       	  **/
       	 RacePanel(JPanel panel, String race_title, String[] candidates){
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
       		 String result = "";
       		 for(int i = 0; i < race.getComponents().length; i++){
       			 result += ((JButton)race.getComponent(i)).getText() + " ";
       		 }
       		 return result;
       	 }

      } 