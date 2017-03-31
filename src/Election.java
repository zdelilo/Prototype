import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Election extends JFrame implements ActionListener{
	
	
	JLabel lblElectionName;
	JLabel lblCommissionerID;
	JTextField txtElectionName;
	JTextField txtCommissionerID;
	JButton confirm;
	HSOInterface election;
	
	/**HSO Interface is taken through election ballot
	 * all additions are made to original HSO interface**/
	Election(HSOInterface HSO){
		
		/**Assign election to HSO interface**/
		election = HSO;
		JPanel panelMain = new JPanel();
		GroupLayout layout = new GroupLayout(panelMain);
		JPanel panelSub = new JPanel();
		JPanel panelSub1 = new JPanel();
		
		lblElectionName = new JLabel("Election Name:  ");
		lblCommissionerID = new JLabel("Election Commissioner ID:  ");
		
		txtElectionName = new JTextField(20);
		txtCommissionerID = new JTextField(20);
		
		confirm = new JButton("Confirm Election");
		confirm.setActionCommand("confirm");
		confirm.addActionListener(this);
		
		/**Set Defaults for main panel | allows enter key | top bar icon**/
        this.getRootPane().setDefaultButton(confirm);
        this.setIconImage(MyImages.codeFather.getImage());
        this.setTitle("Create Ballot");
        this.setSize(450, 200);
        this.getContentPane().add(panelMain);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panelMain.setBackground(MyColors.deepBlue);
        
        panelSub.add(lblElectionName);
        panelSub.add(txtElectionName);
        
        panelSub1.add(lblCommissionerID);
        panelSub1.add(txtCommissionerID);
        
        layout.setHorizontalGroup(layout.createSequentialGroup()
        		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        				.addComponent(panelSub)
        				.addComponent(panelSub1)
        				.addComponent(confirm))
        		);
        
        layout.setVerticalGroup(layout.createParallelGroup()
        		.addGroup(layout.createSequentialGroup()
        				.addComponent(panelSub)
        				.addComponent(panelSub1)
        				.addComponent(confirm))
        		);
        
        /**Centers GUI onto Screen**/
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((d.getWidth() - this.getWidth()) / 2);
        int y = (int) ((d.getHeight() - this.getHeight()) / 2);
        setLocation(x, y);
        this.setVisible(true); 
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("confirm")){
			String electionName = txtElectionName.getText();
			String commissionerID = txtCommissionerID.getText();
			election.addList(electionName);
			this.setVisible(false);
		}
	}

}
