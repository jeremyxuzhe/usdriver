package warwick.marsh.ultracam.usdriver;

import java.lang.Void;
import javax.swing.*;
import javax.swing.text.*;

import java.awt.*;              //for layout managers and more
import java.awt.event.*;        //for action events


public class FilterChooser  {
    
    // Colours and Fonts
    public static final Color DEFAULT_COLOUR    = new Color(220, 220, 255);
    public static final Color SEPARATOR_BACK    = new Color(100, 100, 100);
    public static final Color SEPARATOR_FORE    = new Color(150, 150, 200);
    public static final Color LOG_COLOUR        = new Color(240, 230, 255);
    public static final Color ERROR_COLOUR      = new Color(255, 0,   0  );
    public static final Color WARNING_COLOUR    = new Color(255, 100, 0  );
    public static final Color GO_COLOUR         = new Color(0,   255, 0  );
    public static final Color STOP_COLOUR       = new Color(255, 0,   0  );
    public static final Font DEFAULT_FONT = new Font("Dialog", Font.BOLD, 12);

    private static String[] myNames=null;
    private static String[] myIDS=null;

    private static final int nPos = 6;
    private static String[] chosenFilts= new String[nPos];
    private JComboBox[] combos = new JComboBox[nPos];
    private JLabel[]    labels = new JLabel[nPos]; 

    // Use this a fair bit, so just make one
    private static GridBagLayout gbLayout = new GridBagLayout();

    // Use GBLayout for main panel
    private JPanel mainPanel = new JPanel(gbLayout);

    // Method for adding components to GridBagLayout for the window panel
    private static void addComponent (Container cont, Component comp, int gridx, int gridy, 
				      int gridwidth, int gridheight, int fill, int anchor){

	GridBagConstraints gbc = new GridBagConstraints ();
	gbc.gridx      = gridx;
	gbc.gridy      = gridy;
	gbc.gridwidth  = gridwidth;
	gbc.gridheight = gridheight;
	gbc.fill       = fill;
	gbc.anchor     = anchor;
	gbLayout.setConstraints(comp, gbc);
	cont.add (comp);
    }

    public FilterChooser(String[] names, String[] IDS, String[] activeFilts) throws Exception{
	myNames = names;
	myIDS   = IDS;
	if(activeFilts.length != nPos)
	    throw new Exception("too many filters - can only choose up to " + Integer.toString(nPos) + " filters");

	initComponents(activeFilts);
    }

    private void initComponents(String[] activeFilts) {
	mainPanel.setPreferredSize(new Dimension(180,190));
	for(int i=0; i< nPos; i++){
	    labels[i] = new JLabel();
	    labels[i].setText(Integer.toString(i+1));
	}
	for(int i=0; i<nPos; i++){
	    combos[i] = new JComboBox(myNames);
	    combos[i].setSelectedItem(activeFilts[i]);
	}

	JLabel h1 = new JLabel("Pos");
	JLabel h2 = new JLabel("Filter");
	int SEPARATOR_WIDTH=1;
	// now add components
	// headers
	addComponent(mainPanel, h1, 0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
	addComponent(mainPanel, Box.createHorizontalStrut(15), 1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
	addComponent(mainPanel, h2, 2, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);

	// vertical space
	addComponent(mainPanel, Box.createVerticalStrut(10), 0, 1, 3, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);

	// separator between headers and boxes
	
	JSeparator hsep = new JSeparator();
	hsep.setBackground(SEPARATOR_BACK);
	hsep.setForeground(SEPARATOR_FORE);
	addComponent( mainPanel, hsep, 0, 2,  3, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER);
	Dimension hdim = mainPanel.getPreferredSize();
	hsep.setPreferredSize(new Dimension(hdim.width, SEPARATOR_WIDTH));

	// vertical space
	addComponent(mainPanel, Box.createVerticalStrut(10), 0, 3, 3, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);

	// labels and drop down boxes
	int ypos=4;
	for(int i=0; i<nPos; i++){
	    addComponent(mainPanel, labels[i], 0, ypos, 1, 1,GridBagConstraints.NONE, GridBagConstraints.WEST);
	    addComponent(mainPanel, Box.createHorizontalStrut(15), 1, ypos, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
	    addComponent(mainPanel, combos[i], 2, ypos++, 1, 1,GridBagConstraints.NONE, GridBagConstraints.WEST);
	}
    }
    

    public String[] getFilts(){
	for(int i=0; i<nPos; i++){
	    chosenFilts[i] = combos[i].getSelectedItem()+"";
	}
	return chosenFilts;
    }

    public JComponent getComponent(){
	return mainPanel;
    }
    

}