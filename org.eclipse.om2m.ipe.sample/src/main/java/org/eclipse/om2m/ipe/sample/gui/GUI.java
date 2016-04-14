package org.eclipse.om2m.ipe.sample.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.ipe.sample.constants.ThermosConstants;
import org.eclipse.om2m.ipe.sample.controller.ThermosController;
import org.eclipse.om2m.ipe.sample.model.ConnectedState;
import org.eclipse.om2m.ipe.sample.model.ThermosModel;
import org.osgi.framework.FrameworkUtil;

public class GUI extends JFrame {
    
    static Log LOGGER = LogFactory.getLog(GUI.class);
    private static final long serialVersionUID = 1L;
    static ThermosModel.ConnectedObserver connectedObserver;

	static ImageIcon houseRadiatorOff = new ImageIcon(FrameworkUtil.getBundle(GUI.class).getResource("images/Piece_Chauff_Eteint.png"));
	static ImageIcon houseRadiatorLow = new ImageIcon(FrameworkUtil.getBundle(GUI.class).getResource("images/Piece_Chauff_Faible.png"));
	static ImageIcon houseRadiatorStrong = new ImageIcon(FrameworkUtil.getBundle(GUI.class).getResource("images/Piece_Chauff_Fort.png"));
	//static ImageIcon houseWindowOpen = new ImageIcon(FrameworkUtil.getBundle(GUI.class).getResource("images/Piece_Fen_Ouverte.png"));
    
	private JPanel contentPanel = new JPanel();
	private JPanel statePanel = new JPanel();
	private JPanel userPanel = new JPanel();
	private JPanel paramPanel = new JPanel();
	private JPanel housePanel = new JPanel();
		                
    static JLabel LABEL_RADIATOR = new JLabel("Radiator");
    static JLabel LABEL_WINDOW = new JLabel("Window");
    static JLabel LABEL_SETPOINT = new JLabel("Setpoint");
    static JLabel LABEL_THERMOMETER_EXT = new JLabel("Outside Thermometer");
    static JLabel LABEL_THERMOMETER_INT = new JLabel("Inside Thermometer");
    static JLabel LABEL_STATE = new JLabel("Objects State");
    static JLabel LABEL_USER = new JLabel("User Parameters");
    static JLabel LABEL_USERPROFILE = new JLabel("User Profile");
    static JLabel LABEL_SYSTEM = new JLabel("System State");
    
    static JLabel houseState = new JLabel(houseRadiatorOff);
    
    static JButton incSetPointButton = new JButton("+");
    static JButton decSetPointButton = new JButton("-");
    static JButton incTempIntButton = new JButton("+");
    static JButton decTempIntButton = new JButton("-");
    static JButton incTempExtButton = new JButton("+");
    static JButton decTempExtButton = new JButton("-");
    
    static JTextField setPointField = new JTextField();
    static JTextField tempIntField = new JTextField();
    static JTextField tempExtField = new JTextField();
    
    static int setPoint = 27;
    static int tempInt = 27;
    static int tempExt = 27;
    
    static String[] profiles = {"Eco", "Confort"};
    static JComboBox<String> userProfileBox = new JComboBox<String>(profiles);
    static String[] systemStates = {"Activated", "Disabled"};
    static JComboBox<String> systemStateBox = new JComboBox<String>(systemStates);
    static String[] windowStates = {"Open", "Closed"};
    static JComboBox<String> windowStateBox = new JComboBox<String>(windowStates);
    static String[] radiatorStates = {"Off", "Low", "Strong"};
    static JComboBox<String> radiatorStateBox = new JComboBox<String>(radiatorStates);
    
    static String WINDOW = "WINDOW";
    static String RADIATOR = "RADIATOR";
    static String THERMOMETER_EXT = "THERMOMETER_EXT";
    static String THERMOMETER_INT = "THERMOMETER_INT";
		

		public GUI() {				
		        
		        /*Override des observers */
		        connectedObserver = new ThermosModel.ConnectedObserver() {			
					@Override
					public void onConnectedStateChange(String connectedId, ConnectedState state) {
						//setLabelConnected(connectedId, state);
					}
				};

		        ThermosModel.addObserver(connectedObserver);
		        
		        /*
		         * Positionnement des différents composants du système dans un groupLayout :
		         * Radiator, Window , et thermomètre
		         */
		        		   
		        this.setTitle("Thermos");
		        this.setSize(1000, 800);
		        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        this.setLocationRelativeTo(null);
		        
		        statePanel.setLayout(new BoxLayout(statePanel, BoxLayout.PAGE_AXIS));
		        statePanel.add(LABEL_STATE);
		        statePanel.add(LABEL_THERMOMETER_EXT);
		        JPanel tempExtPanel = new JPanel();
		        tempExtPanel.setLayout(new BoxLayout(tempExtPanel, BoxLayout.LINE_AXIS));
		        tempExtPanel.add(tempExtField);
		        tempExtPanel.add(incTempExtButton);
		        incTempExtButton.addActionListener(new TempExtModification());
		        tempExtPanel.add(decTempExtButton);
		        decTempExtButton.addActionListener(new TempExtModification());
		        statePanel.add(tempExtPanel);
		        statePanel.add(LABEL_THERMOMETER_INT);
		        JPanel tempIntPanel = new JPanel();
		        tempIntPanel.setLayout(new BoxLayout(tempIntPanel, BoxLayout.LINE_AXIS));
		        tempIntPanel.add(tempIntField);
		        tempIntPanel.add(incTempIntButton);
		        incTempIntButton.addActionListener(new TempIntModification());
		        tempIntPanel.add(decTempIntButton);
		        decTempIntButton.addActionListener(new TempIntModification());
		        statePanel.add(tempIntPanel);
		        statePanel.add(LABEL_WINDOW);
		        statePanel.add(windowStateBox);
		        statePanel.add(LABEL_RADIATOR);
		        statePanel.add(radiatorStateBox);
		        statePanel.setAlignmentX(LEFT_ALIGNMENT);

		        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.PAGE_AXIS));
		        userPanel.add(LABEL_USER);
		        userPanel.add(LABEL_SYSTEM);
		        userPanel.add(systemStateBox);
		        
		        userPanel.add(LABEL_USERPROFILE);
		        userPanel.add(userProfileBox);
		        userPanel.add(LABEL_SETPOINT);
		        JPanel setPointPanel = new JPanel();
		        setPointPanel.add(setPointField);
		        setPointPanel.add(incSetPointButton);
		        incSetPointButton.addActionListener(new SetPointModification());
		        setPointPanel.add(decSetPointButton);
		        decSetPointButton.addActionListener(new SetPointModification());
		        userPanel.add(setPointPanel);
		        userPanel.setAlignmentX(LEFT_ALIGNMENT);
		        
		        
		   
		        paramPanel.setLayout(new BoxLayout(paramPanel, BoxLayout.PAGE_AXIS));
		        paramPanel.add(statePanel);
		        paramPanel.add(userPanel);
		        
		        housePanel.add(houseState);
		        housePanel.setAlignmentX(RIGHT_ALIGNMENT);

		        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.LINE_AXIS));
		        contentPanel.add(paramPanel);
				contentPanel.add(housePanel);
		        contentPanel.setBackground(Color.WHITE);
		        
		        this.setContentPane(contentPanel);
		        this.setVisible(true);
		        
		        setPointField.setText(String.valueOf(setPoint));
		        tempExtField.setText(String.valueOf(tempExt));
		        tempIntField.setText(String.valueOf(tempInt));
		    }                       

			
			class UserProfileModification implements ActionListener {
				ConnectedState state;
				public void actionPerformed(ActionEvent e){
					if(userProfileBox.getSelectedItem().toString().equals("Eco")){
						state = ConnectedState.Eco;
					}
					else {
						state = ConnectedState.Confort;
					}
					new Thread(){
	                    public void run() {
	                    	ThermosController.toggleProfilUser(state);
	                    }
	                }.start();
				}
			}
			
		    class TempIntModification implements ActionListener {
		    	public void actionPerformed(ActionEvent e){
		    		if(e.getSource() == incTempIntButton) {
			        	 tempIntField.setText(String.valueOf(++tempInt));
			         }else if(e.getSource() == decTempIntButton){
			        	 tempIntField.setText(String.valueOf(--tempInt));
			         }
			         new Thread(){
		                    public void run() {
		       	        	 ThermosController.toggleThermometer(ThermosConstants.THERMOMETER_INT, tempInt);
		                    }
		                }.start();
		    	}
		    }
		    
		    class TempExtModification implements ActionListener {
		    	public void actionPerformed(ActionEvent e){
		    		if(e.getSource() == incTempExtButton) {  
			        	tempExtField.setText(String.valueOf(++tempExt));
			        } else if(e.getSource() == decTempExtButton){
		            	tempExtField.setText(String.valueOf(--tempExt));
			        }
			        new Thread(){
	                    public void run() {
	    		        	ThermosController.toggleThermometer(ThermosConstants.THERMOMETER_EXT, tempExt);
	                    }
	                }.start();
		    	}
		    }
		    class SetPointModification implements ActionListener{
		    	public void actionPerformed(ActionEvent e) {
		    		if(e.getSource() == incSetPointButton) {                    
			        	  setPointField.setText(String.valueOf(++setPoint));
			          }else if(e.getSource() == decSetPointButton){
			        	  setPointField.setText(String.valueOf(--setPoint));

			          }
			          new Thread(){
		                    public void run() {
		  		        	  ThermosController.toggleTempConsigne(setPoint); 
		                    }
		                }.start();
		    	}
		    }                                                                   
		    
		    public static void HouseModification (){
		    	if (windowStateBox.getSelectedItem().toString().equals("Open")){
		    		//houseState.setIcon(houseWindowOpen);
		    	} else
		    		if(radiatorStateBox.getSelectedItem().toString().equals("Off")) {
		    			houseState.setIcon(houseRadiatorOff);
		    		} 
		    		else if (radiatorStateBox.getSelectedItem().toString().equals("Low")) {
		    			houseState.setIcon(houseRadiatorLow);
		    		} 
		    		else if (radiatorStateBox.getSelectedItem().toString().equals("Strong")) {
		    			houseState.setIcon(houseRadiatorStrong);
		    		}
		    }
		  
			/**
			* Initiate The GUI.
			*/
		    public static void init() {
		        EventQueue.invokeLater(new Runnable() {
		            public void run() {
		                try {
		                    new GUI().setVisible(true);
		                } catch (Exception e) {
		                    LOGGER.error("GUI init Error", e);
		                }
		            }
		        });
		    }

		    /**
		     * Stop the GUI.
		     */
		    public static void stop() {
		    	ThermosModel.deleteObserver(connectedObserver);
		        new GUI().setVisible(false);
		        new GUI().dispose();
		    }

			
		}