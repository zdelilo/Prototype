import java.awt.Color;     
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

/**
 * @author CodeFather 
 * Creates ballet given input from Ballot Prompt 
 */
public class Ballot extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
		JButton confirm;     
        /**Layout and ButtonGroup Components**/
        JPanel panelMain = new JPanel();
        GroupLayout layout = new GroupLayout(panelMain);	 
       
        ArrayList<ButtonGroup> selectedCandidates = new ArrayList<ButtonGroup>();
        ArrayList<String> raceGroup = new ArrayList<String>();

       
	    /**
	     * Constructor - Ballot GUI
	     */
	    Ballot(){
	    		
	    	 confirm = new JButton("Confirm");
	    	 confirm.setActionCommand("confirm");
	    	 confirm.addActionListener(this);
	    	
	    	/**Default values for Main Panel | Color | Size | Icon | Title | **/
	        panelMain.setBackground(MyColors.deepBlue);
	        this.setSize(400,200);
	        this.setIconImage(MyImages.codeFather.getImage());
	        this.setTitle("MyVote");
	        this.getContentPane().add(panelMain);
	        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	        this.getRootPane().setDefaultButton(confirm);
	       
	        /**Centers GUI to center of screen**/
	        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
	        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
	        setLocation(x, y);
	        setVisible(true);
	        
	    }
	    
	    /**
	     * Adds a race panel to Ballot GUI
	     * @param p - Race Panel
	     */
	    public void addBallot(RacePanel p){
	    	 ButtonGroup btnRadioGroup = new ButtonGroup();
	    	 JPanel race = p.race;
	    	 race.setBackground(MyColors.seaGreen);
	    	 race.setBorder(new LineBorder(Color.white,3));
	    	 race.add(new JLabel(p.race_title));
	    	 raceGroup.add(p.race_title);
	      	 String[] candidates = p.candidate_names;
	      	 int num_candidates  = candidates.length;
	      	
	    	   for(int i = 0; i < num_candidates;i++){
	    		   JRadioButton cand = new JRadioButton(candidates[i]);
	    		   cand.setActionCommand(candidates[i]);
	    		   cand.setBackground(Color.white);
	    		   
	    		  
	    		   btnRadioGroup.add(cand);
	    		   p.addButton(cand);
	    		   p.button.setText(candidates[i]);
	    		   p.button.setVisible(true);
	    		   
	    		   /**Addds components to main layout**/
	    		   layout.setHorizontalGroup(layout.createSequentialGroup()
		        			  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		        					  .addComponent(p.race)
		        					  ));
		   	 	    layout.setVerticalGroup(
		   	 	            layout.createParallelGroup()
		   	 	                .addGroup(layout.createSequentialGroup())
		   	 	                    .addComponent(p.race)
		   	 	                    );      
	    	   }
	    	   selectedCandidates.add(btnRadioGroup);
	    }
	    
	    /**
	     * Adds Finish Button to Ballot GUI
	     * TODO - Export ballot to server
	     */
	    public void finishBallot(){
	    	/**Addds components to main layout**/
 		   layout.setHorizontalGroup(layout.createSequentialGroup()
	        			  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	        					  .addComponent(confirm)
	        					  ));
   	 	    layout.setVerticalGroup(
   	 	            layout.createParallelGroup()
   	 	                .addGroup(layout.createSequentialGroup())
   	 	                    .addComponent(confirm)
   	 	                    ); 
	    }
	    
	    public void actionPerformed(ActionEvent e){
	    	if(e.getActionCommand().equals("confirm"))
	    	
	    	for(int i = 0; i < raceGroup.size();i++)	
	    		System.out.println(
	    				raceGroup.get(i) + " Winner!\n" +
	    				selectedCandidates.get(i).getSelection().getActionCommand()
	    				);
	    }

	    public static void main(String args[]){
	    	
	    }	
	
}
