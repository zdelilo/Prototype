import java.awt.Color;      
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
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
		JButton finish;
        /**Layout and ButtonGroup Components**/
        JPanel panelMain = new JPanel();
        GroupLayout layout = new GroupLayout(panelMain);
        User user;
        
        /**Collections**/
        ArrayList<ButtonGroup> selectedCandidates = new ArrayList<ButtonGroup>();
        ArrayList<RacePanel> panels = new ArrayList<RacePanel>();
        
        /**Server Stuff**/
    	ObjectInputStream brIn;
    	ObjectOutputStream pwOut;	
    	Socket sock;
       
	    /************* 
	     * Constructor - Ballot GUI
	     * @param user - user currently using ballot
	     * ***********/
	    Ballot(User user){
	    	
	   	 /**Initialzies Server**/  	
	    	 startServer();
	    	 this.user = user;
	    	 confirm = new JButton("Confirm");
	    	 confirm.setActionCommand("confirm");
	    	 confirm.addActionListener(this);
	    	 
	    	 finish = new JButton("Finish");
	         finish.setActionCommand("finish");
	         finish.addActionListener(this);
	    	
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
	     * retrieves all ballot information from RacePanel
	     * 		| race_title | candidates |
	     */
	    public void addBallot(RacePanel p, boolean save) {
	    	
	    	/**Panels - used to compartmentalize neccessary ballot componets
	    	 * race and candidates**/
	    	panels.add(p);
	    	/**Saves race and candidates to server**/
	    	addRace(p);
	    	
	    	JPanel race = new JPanel();
	    	race.setBackground(MyColors.seaGreen);
	    	race.setBorder(new LineBorder(Color.white,3));
		    race.add(new JLabel(p.race_title));
		    
		    ButtonGroup btnRadioGroup = new ButtonGroup();
	    	int num_candidates  = p.candidates.size();

	    	/**Loop goes through and creates ballot panels with all candidates**/
	      	for(int i = 0; i < num_candidates;i++){
	      		
	    		   JRadioButton cand = new JRadioButton(p.candidates.get(i).getName());
	    		   cand.setText(p.candidates.get(i).getName());
	    		   cand.setVisible(true);
	    		   cand.setActionCommand(p.candidates.get(i).getName());
	    		   cand.setBackground(Color.white);
	    		   btnRadioGroup.add(cand);
	    		   race.add(cand);
	    	
	    		   /**Addds components to main layout**/
	    		   layout.setHorizontalGroup(layout.createSequentialGroup()
		        			  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		        					  .addComponent(race)
		        					  ));
		   	 	    layout.setVerticalGroup(
		   	 	            layout.createParallelGroup()
		   	 	                .addGroup(layout.createSequentialGroup())
		   	 	                    .addComponent(race)
		   	 	                    );      
	    	   }
	    	   selectedCandidates.add(btnRadioGroup);
	    }
	    
	    
	    /**Adds Finish and confirm Button to Ballot GUI**/
	    public void finishBallot(boolean save){
	    	/**Addds components to main layout**/
 		   layout.setHorizontalGroup(layout.createSequentialGroup()
	        			  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	        					  .addComponent(confirm)
	        					  .addComponent(finish)
	        					  ));
   	 	    layout.setVerticalGroup(
   	 	            layout.createParallelGroup()
   	 	                .addGroup(layout.createSequentialGroup())
   	 	                    .addComponent(confirm)
   	 	                    .addComponent(finish)
   	 	                    ); 
	    }
	    
	    
	    public void actionPerformed(ActionEvent e){
	    	/**Confirms completed submission
	    	 * exits server and system**/
	    	if(e.getActionCommand().equals("finish"))
				shutdown();
	    	
	    	/**Sends vote to server**/
	    	if(e.getActionCommand().equals("confirm"))
	    		
	    	for(int i = 0; i < panels.size();i++){
	    	
	    		String race = panels.get(i).race_title;
	    		String selectedC = selectedCandidates.get(i).getSelection().getActionCommand();
	    		int selectedIndex = panels.get(i).names.indexOf(selectedC);
	   	 	    addVotes(race, selectedIndex); 	
	    		System.out.println(race + " " + selectedC);
	    	}
	    }
	   
	    public void startServer(){
			 /**Initialzies Server**/
			 try {
					sock = new Socket("127.0.0.1",50000);
					pwOut = new ObjectOutputStream(sock.getOutputStream());
					brIn = new ObjectInputStream(sock.getInputStream());    	
				
			 	} catch (IOException e) {
					e.printStackTrace();
				}
		 }
	    
	    public void shutdown(){
			   try {
					pwOut.writeObject("<shutdown>");
					System.exit(0);
				
			   } catch (IOException e1) {				
					e1.printStackTrace();
				}
		   }
	    
	    /**<<<SERVER CONNECTOIN  <vote> >>>
     	  * saves user's candidate vote to server
     	  * **/  
	   public void addVotes(String race, int selectedIndex){
		   try {
	 	    	pwOut.writeObject("<vote>");
	 	    	pwOut.writeObject(race);
				pwOut.writeObject(selectedIndex);
				pwOut.writeObject(user.username);
				confirm.setVisible(false);
			} catch (IOException j) {
				j.printStackTrace();
			}
	   }
	  
    /**<<<SERVER CONNECTOIN  <saveballot> >>>
 	  * saves completed ballot components to server
 	  * **/        
	    public void saveBallot(){
	    	
  	 	    try {
	 	    	pwOut.writeObject("<saveballot>");
				pwOut.writeObject(panels);
			
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
	    /**<<<SERVER CONNECTOIN ADDRACE>>>
     	  * sends <addRace> command to server
     	  * ultimate goald | update the candidates voting array |
     	  * **/
	    public void addRace(RacePanel p){
	    	 
	    	try {
		 	    	pwOut.writeObject("<addRace>");
		 	    	pwOut.writeObject(p);
		 	    	
				} catch (IOException j) {
					
					j.printStackTrace();
				}
	    }
	    public static void main(String args[]){
	    	
	    }	
	
}
