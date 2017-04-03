import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

public class ElectionInterface extends JFrame implements ActionListener{
	
	DefaultListModel<String> model = new DefaultListModel<String>();
	JList<String> list = new JList<String>(model);
	Election[] elections;
	
	/**Layout and ButtonGroup Components**/
    JPanel panelMain = new JPanel();
    JPanel pnlList = new JPanel();
    GroupLayout layout = new GroupLayout(panelMain);
    JButton confirm; 
    User user;
    
    /**Server Stuff**/
	ObjectInputStream brIn;
	ObjectOutputStream pwOut;	
	Socket sock;
    
	ElectionInterface(User user){
		startServer();
		this.user = user;
		confirm = new JButton("Confirm");
    	confirm.setActionCommand("confirm");
    	confirm.addActionListener(this);
    	 
    	list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
 		list.setVisibleRowCount(4);
 		list.setVisible(true);
 		 
 		pnlList.add(list);
 		pnlList.add(confirm);
		pnlList.setAutoscrolls(true);
		pnlList.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		pnlList.setLayout(new BoxLayout(pnlList, BoxLayout.Y_AXIS));
		
		getElections();
		for(Election e: elections){
			this.addElection(e.election_title);
		}
		addConfirm();
		
		System.out.println("enter e interface");
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

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("confirm") && !list.isSelectionEmpty()){
			String selectedE = list.getSelectedValue().toString();
			try {
				pwOut.writeObject("<selectedElection>");
				pwOut.writeObject(selectedE);
				user.UserGUI(user);
				this.setVisible(false);
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			this.setVisible(false);
		}
	}
	
	public void addElection(String eName) {
		model.addElement(eName);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(pnlList))
				);

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(pnlList))
				);
	}
	
	public void addConfirm(){
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(confirm))
				);

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(confirm))
				);
	}
	
	public void getElections(){
		try {
			pwOut.writeObject("<getElections>");
			elections = (Election[]) brIn.readObject();
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
   
   public void shutdown(){
		   try {
				pwOut.writeObject("<shutdown>");
				System.exit(0);
			
		   } catch (IOException e1) {				
				e1.printStackTrace();
			}
	   }
}

	
