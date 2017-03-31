import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

	HSOInterface(){

		pnlList.add(list);
		pnlList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pnlList.setLayout(new BoxLayout(pnlList, BoxLayout.Y_AXIS));
		JRadioButton radNewElection = new JRadioButton("Create New Election");
		JRadioButton radRecount = new JRadioButton("Recount Results");
		JRadioButton radDisqualify = new JRadioButton("Disqualify Voter");
		JRadioButton radCertify = new JRadioButton("Certify Election");

		radNewElection.setActionCommand("NewElection");
		radNewElection.addActionListener(this);
		pnlRadios.add(radNewElection);
		pnlRadios.add(radDisqualify);
		pnlRadios.add(radRecount);
		pnlRadios.add(radCertify);
		pnlRadios.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setVisibleRowCount(4);
		list.setVisible(true);
		pnlRadios.setLayout(new BoxLayout(pnlRadios, BoxLayout.Y_AXIS));

		panelMain.setBackground(MyColors.deepBlue);
		this.setSize(400,200);
		this.setIconImage(MyImages.codeFather.getImage());
		this.setTitle("HSO Interface");
		this.getContentPane().add(panelMain);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		panelMain.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(lWelcome))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(pnlRadios)
						.addComponent(pnlList))
				);

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lWelcome))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(pnlRadios))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(pnlList))
				);


		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
		setLocation(x, y);
		setVisible(true);

	}

	public void addList(String name){
		contents.add(name);
		for(int i = 0; i < contents.size(); i++){
			model.add(i, contents.get(i));
		}
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(lWelcome))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
					.addComponent(pnlRadios)
						.addComponent(pnlList))
				);

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lWelcome))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(pnlRadios))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(pnlList))
				);
		
		panelMain.setBackground(MyColors.deepBlue);
		this.setSize(400,200);
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
			new Election();
			setVisible(false);
		}
	}

	public static void main(String args[]){
		new HSOInterface();
	}
}
