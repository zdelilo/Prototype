import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.*;
import javax.swing.*;


public class HSOInterface extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	ArrayList<String> contents = new ArrayList<String>();
	DefaultListModel model = new DefaultListModel();
	JList list = new JList(model);

	JPanel panelMain = new JPanel();
	GroupLayout layout = new GroupLayout(panelMain);
	JPanel pnlRadios = new JPanel();
	JPanel pnlList = new JPanel();
	JLabel lWelcome = new JLabel("Welcome HSO, ");// + HSO.users;
	ElectionPrompt election;
	JLabel lCurrentElections; 
	ArrayList<String> commissioner = new ArrayList<String>(); 
	JButton finish; 
	
	Election[] elections;
	/**Server Stuff**/
	ObjectInputStream brIn;
	ObjectOutputStream pwOut;	
	Socket sock;
	
	
	HSOInterface(){
		startServer();
		lCurrentElections = new JLabel("Or choose a current election");

		pnlList.add(lCurrentElections);
		pnlList.add(list);
		pnlList.setAutoscrolls(true);
		
		finish = new JButton("Finish");
    	finish.setActionCommand("finish");
    	finish.addActionListener(this);

		pnlList.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		pnlList.setLayout(new BoxLayout(pnlList, BoxLayout.Y_AXIS));
		JRadioButton radNewElection = new JRadioButton("Create New Election");
		JRadioButton radRecount = new JRadioButton("Recount Results");
		JRadioButton radDisqualify = new JRadioButton("Disqualify Voter");
		JRadioButton radCertify = new JRadioButton("Certify Election");
		JRadioButton radDisplay = new JRadioButton("Display Election");


		radNewElection.setActionCommand("NewElection");
		radNewElection.addActionListener(this);

		radRecount.setActionCommand("Recount");
		radRecount.addActionListener(this);

		radDisqualify.setActionCommand("Disqualify");
		radDisqualify.addActionListener(this);

		radCertify.setActionCommand("Certify");
		radCertify.addActionListener(this);
		
		radDisplay.setActionCommand("Display");
		radDisplay.addActionListener(this);

		pnlRadios.add(radNewElection);
		pnlRadios.add(radDisqualify);
		pnlRadios.add(radRecount);
		pnlRadios.add(radCertify);
		pnlRadios.add(radDisplay);
		
		pnlRadios.setBorder(BorderFactory.createLineBorder(Color.WHITE));

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setVisibleRowCount(4);
		list.setVisible(true);
		pnlRadios.setLayout(new BoxLayout(pnlRadios, BoxLayout.Y_AXIS));

		panelMain.setBackground(MyColors.deepBlue);
		this.setSize(400,600);
		this.setIconImage(MyImages.codeFather.getImage());
		this.setTitle("HSO Interface");
		this.getContentPane().add(panelMain);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		panelMain.setLayout(layout);
		
		
		getList();
		initializeList();

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(lWelcome))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(pnlRadios)
						.addComponent(pnlList))

				);

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lWelcome).addGap(50))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(pnlRadios)).addGap(50)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(pnlList)

						));

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
		setLocation(x, y);
		setVisible(true);
	
	}

	public void addList(String name){

		/**Retrieves Election Name and adds to current Election List**/
		contents.add(name);
		model.addElement(contents.get(contents.size()-1));

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(lWelcome))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(pnlRadios)
						.addComponent(pnlList))
				.addComponent(finish)
				);

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lWelcome).addGap(50))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(pnlRadios)).addGap(50)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(pnlList))
				.addComponent(finish)
				);

		panelMain.setBackground(MyColors.deepBlue);
		this.setSize(400,600);
		this.setIconImage(MyImages.codeFather.getImage());
		this.setTitle("MyVote");
		this.getContentPane().add(panelMain);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


		/**Centers GUI to center of screen**/
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
		setLocation(x, y);
		setVisible(true);

	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("NewElection")){

			/**Have election take in current election**/
			new ElectionPrompt(this);

			setVisible(false);
		}
		if(list.getSelectedValue() != null){
			String selectedE = list.getSelectedValue().toString();
			initializeElection(selectedE);
			if(e.getActionCommand().equals("Recount")){
				new CurrentElection(this);
			}
			else if(e.getActionCommand().equals("Certify")){
				
				new CertifyResultsGUI();
			}
			
			else if(e.getActionCommand().equals("Display")){
				new DisplayGUI();
			}
			
			else if(e.getActionCommand().equals("Disqualify")){

			}
		}
	
		if(e.getActionCommand().equals("finish"))
			shutdown();
	}

	
	 public void getList(){
		 try {
			pwOut.writeObject("<getElections>");
			elections = (Election[])brIn.readObject();
		} catch (IOException | ClassNotFoundException e) {
			
			e.printStackTrace();
		}
	 }
	
	 public void initializeList(){
		 for(Election e: elections)
			 this.addList(e.election_title);
	 }
	 
	 public void initializeElection(String selectedE){
		 try {
				pwOut.writeObject("<selectedElection>");
				pwOut.writeObject(selectedE);
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
	 }
	public void addCommissioner(String id){
		commissioner.add(id);
	}

	
	public String getElection(){
		return list.getSelectedValue().toString();	
	}
	
	public String getCommissioner(){
		return commissioner.get(list.getSelectedIndex());	
	}
	
	
	public void shutdown(){
		try {
			pwOut.writeObject("<shutdown>");
			this.setVisible(false);
		} catch (IOException e) {
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
	
	
	 
	public static void main(String args[]){
		new HSOInterface();
	}
}
