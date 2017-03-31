import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

public class DisplayGUI extends JFrame implements ActionListener{
	
	List<RacePanel> winners;
	
	ObjectInputStream brIn;
	ObjectOutputStream pwOut;	
	Socket sock;
	
	 DisplayGUI(){
		
		 startServer();
		
		 winners = new  ArrayList<RacePanel>(); 
		 getWinners();

		 
		 JPanel mainPanel = new JPanel(new GridBagLayout());	
		 mainPanel.setBackground(Color.white);
		 JPanel winnerPanel = new JPanel(new GridBagLayout());
		 JPanel raceP = new JPanel();
		 JPanel winnerP = new JPanel();
		 raceP.setBackground(MyColors.paleTurquoise);
		 GridBagConstraints c = new GridBagConstraints();
		 
		 c.fill = GridBagConstraints.HORIZONTAL;
		 c.insets = new Insets(9,10,9, 10);
		 
		 /**Adds winners of race go GUI display**/
		 
		 JButton trophy = new JButton();
		 trophy.setIcon(MyImages.trophyIcon);
		 trophy.setText(" WINNERS OF THE ELECTION RACES!!  ");
		 trophy.setBackground(Color.white);
		 trophy.setBorder(new LineBorder(MyColors.gold,3));
		 trophy.setIconTextGap(4);
		 c.gridy = 1;
		 winnerPanel.add(trophy,c);
		 
		 int i;
		 for(i = 0; i< winners.size(); i++)
		 {
			 c.gridy = i+2;
			 raceP.setBorder(new LineBorder(MyColors.gold,4));
			 raceP.add(new JLabel(winners.get(i).race_title));
			 raceP.add(new JLabel(winners.get(i).getWinner().toString()));
			 winnerPanel.add(raceP,c);
			 raceP = new JPanel();
			 raceP.setBackground(MyColors.paleTurquoise);
		 }
		 
		 
    	 JButton finish = new JButton("Finish");
         finish.setActionCommand("finish");
         finish.addActionListener(this);
         finish.setBorder(new LineBorder(MyColors.kaki,4));
         finish.setBackground(Color.white);
		
		 c.gridy = i+2;
         winnerPanel.add(finish,c);
		 winnerPanel.setBackground(MyColors.beechyBlue);
		 winnerPanel.setBorder(new LineBorder(MyColors.gold,4));
		 c.gridy = 1;
		 mainPanel.add(winnerPanel,c);
		 
		 /**Sets Defaults for main Panel**/ 
		 this.getContentPane().add(mainPanel);
		 this.getRootPane().setDefaultButton(finish);
		 this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  
         this.setIconImage(MyImages.codeFather.getImage());
         this.setTitle("Winners!");
         this.setSize(430, 270);
         
         /**Centers GUI onto Screen**/
         Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
         int x = (int) ((d.getWidth() - this.getWidth()) / 2);
         int y = (int) ((d.getHeight() - this.getHeight()) / 2);
         setLocation(x, y);
         this.setVisible(true);  
	        
	}
	 
	 /**
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * Retrieves array race Panels from Server
	 */
	public void getWinners() {
		 try {
			pwOut.writeObject("<getWinners>");
			 winners = (ArrayList<RacePanel>)brIn.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
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
	 

		public void actionPerformed(ActionEvent e) {
			/**Confirms completed submission
	    	 * exits server and system**/
	    	if(e.getActionCommand().equals("finish"))
				try {
					pwOut.writeObject("<shutdown>");
					System.exit(0);
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
	    	
			
		}
	 public static void main(String[]args){
		 MyVoteServer server = new MyVoteServer();
		 server.start();
		 server.restore();
		 
		 new DisplayGUI();
	 }

	
}
