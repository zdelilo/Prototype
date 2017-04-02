import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CurrentElection extends JFrame implements ActionListener{

	JLabel lblElectionName;
	JLabel lblCommissionerID;
	JLabel lblCandidates;
	JButton done;
	HSOInterface election;
	
	CurrentElection(HSOInterface HSO){
		
		election = HSO;
		
		JPanel panelMain = new JPanel();
		GroupLayout layout = new GroupLayout(panelMain);
		JPanel panelElection = new JPanel();
		JPanel panelCommissioner = new JPanel();
		JPanel panelCandidates = new JPanel();
		
		lblElectionName = new JLabel("Election Name:  " + election.getElection());
		
		/**Set Defaults for main panel | allows enter key | top bar icon**/
        this.getRootPane().setDefaultButton(done);
        this.setIconImage(MyImages.codeFather.getImage());
        this.setTitle("Current Election");
        this.setSize(450, 200);
        this.getContentPane().add(panelMain);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panelMain.setBackground(MyColors.deepBlue);
        
        panelElection.add(lblElectionName);
        
        layout.setHorizontalGroup(layout.createSequentialGroup()
        		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        				.addComponent(panelElection))
        				);
        layout.setVerticalGroup(layout.createParallelGroup()
        		.addGroup(layout.createSequentialGroup()
        				.addComponent(panelElection))
        		);
        
        
        /**Centers GUI onto Screen**/
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((d.getWidth() - this.getWidth()) / 2);
        int y = (int) ((d.getHeight() - this.getHeight()) / 2);
        setLocation(x, y);
        this.setVisible(true); 
	}
	
	
	
	
	
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
