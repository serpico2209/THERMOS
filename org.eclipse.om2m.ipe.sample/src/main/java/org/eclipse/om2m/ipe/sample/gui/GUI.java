package org.eclipse.om2m.ipe.sample.gui;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

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
	static ImageIcon houseWindowOpen = new ImageIcon(FrameworkUtil.getBundle(GUI.class).getResource("images/Piece_Fen_ouverte.png"));
    
	private JPanel contentPanel = new JPanel();
		                
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
    static int compteur = 0;
    private static boolean isManual =true;
    
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
						HouseModification(connectedId, state);
					}
				};

		        ThermosModel.addObserver(connectedObserver);
		        
		        /*
		         * Positionnement des diff�rents composants du syst�me dans un groupLayout :
		         * Radiator, Window , et thermom�tre
		         */
		        		   
		        this.setTitle("Thermos");
		        this.setSize(1000, 800);
		        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        this.setLocationRelativeTo(null);
		        
		        //contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
		        contentPanel.setLayout(new GridBagLayout());
		        GridBagConstraints gbc = new GridBagConstraints();
		        gbc.fill = GridBagConstraints.HORIZONTAL;
		        gbc.gridwidth = 1;
		        gbc.gridheight = 1;
		        gbc.weightx = 4;
		        gbc.weighty = 1;
		        gbc.gridx = 0;
		        gbc.gridy = 0;
		        contentPanel.add(LABEL_STATE, gbc);
		        LABEL_STATE.setHorizontalAlignment(SwingConstants.CENTER);
		        gbc.gridy = 1;
		        contentPanel.add(LABEL_THERMOMETER_EXT, gbc);
		        LABEL_THERMOMETER_EXT.setHorizontalAlignment(SwingConstants.LEFT);
		        gbc.gridy = 2;
		        gbc.weightx = 2;
		        contentPanel.add(tempExtField, gbc);
		        gbc.gridx = 1;
		        gbc.weightx = 1;
		        contentPanel.add(incTempExtButton, gbc);
		        incTempExtButton.addActionListener(new TempExtModification());
		        gbc.gridx = 2;
		        contentPanel.add(decTempExtButton, gbc);
		        decTempExtButton.addActionListener(new TempExtModification());
		        gbc.gridx = 0;
		        gbc.gridy = 3;
		        gbc.weightx = 4;
		        contentPanel.add(LABEL_THERMOMETER_INT, gbc);
		        gbc.gridy = 4;
		        gbc.weightx = 2;
		        contentPanel.add(tempIntField, gbc);
		        gbc.gridx = 1;
		        gbc.weightx = 1;
		        contentPanel.add(incTempIntButton, gbc);
		        incTempIntButton.addActionListener(new TempIntModification());
		        gbc.gridx = 2;
		        contentPanel.add(decTempIntButton, gbc);
		        decTempIntButton.addActionListener(new TempIntModification());
		        gbc.gridx = 0;
		        gbc.gridy = 5;
		        gbc.weightx = 4;
		        contentPanel.add(LABEL_WINDOW, gbc);
		        LABEL_WINDOW.setHorizontalAlignment(SwingConstants.LEFT);
		        gbc.gridy = 6;
		        contentPanel.add(windowStateBox, gbc);
		        windowStateBox.setSelectedIndex(1);
		        windowStateBox.addActionListener(new WindowStateModification());
		        gbc.gridy = 7;
		        contentPanel.add(LABEL_RADIATOR, gbc);
		        LABEL_RADIATOR.setHorizontalAlignment(SwingConstants.LEFT);
		        gbc.gridy = 8;
		        contentPanel.add(radiatorStateBox, gbc);
		        radiatorStateBox.addActionListener(new RadiatorStateModification());

		        gbc.gridy = 9;
		        contentPanel.add(LABEL_USER, gbc);
		        LABEL_USER.setHorizontalAlignment(SwingConstants.CENTER);
		        gbc.gridy = 10;
		        contentPanel.add(LABEL_SYSTEM, gbc);
		        LABEL_SYSTEM.setHorizontalAlignment(SwingConstants.LEFT);
		        gbc.gridy = 11;
		        contentPanel.add(systemStateBox, gbc);
		        gbc.gridy = 12;
		        contentPanel.add(LABEL_USERPROFILE, gbc);
		        LABEL_USERPROFILE.setHorizontalAlignment(SwingConstants.LEFT);
		        gbc.gridy = 13;
		        contentPanel.add(userProfileBox, gbc);
		        userProfileBox.addActionListener(new UserProfileModification());
		        gbc.gridy = 14;
		        contentPanel.add(LABEL_SETPOINT, gbc);
		        LABEL_SETPOINT.setHorizontalAlignment(SwingConstants.LEFT);
		        gbc.gridy = 15;
		        gbc.weightx = 2;
		        contentPanel.add(setPointField, gbc);
		        gbc.gridx = 1;
		        gbc.weightx = 1;
		        contentPanel.add(incSetPointButton, gbc);
		        incSetPointButton.addActionListener(new SetPointModification());
		        gbc.gridx = 2;
		        contentPanel.add(decSetPointButton, gbc);
		        decSetPointButton.addActionListener(new SetPointModification());
		        systemStateBox.addActionListener(new SetModificationSystem());
		        
		        gbc.gridx = 5;
		        gbc.gridy = 0;
		        gbc.gridwidth = GridBagConstraints.REMAINDER;
		        gbc.gridheight = GridBagConstraints.REMAINDER;
		        contentPanel.add(houseState, gbc);
		        
		        this.setContentPane(contentPanel);
		        this.setVisible(true);
		        
		        setPointField.setText(String.valueOf(setPoint));
		        tempExtField.setText(String.valueOf(tempExt));
		        tempIntField.setText(String.valueOf(tempInt));
		    }                       

		class SetModificationSystem implements ActionListener {
			ConnectedState state;
			public void actionPerformed(ActionEvent e){
				if(systemStateBox.getSelectedItem().toString().equals("Activated")){
					state = ConnectedState.Activated;
				}
				else if(systemStateBox.getSelectedItem().toString().equals("Disabled")){
					state = ConnectedState.Disabled;
				}
				new Thread(){
                    public void run() {
                    	ThermosController.toggleConnectedSystem(state);
                    }
                }.start();
			}
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
			         }else {
			        	// tempIntField.
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
		    
		    class WindowStateModification implements ActionListener {
				ConnectedState state;
				public void actionPerformed(ActionEvent e){
					if(windowStateBox.getSelectedItem().toString().equals("Open")){
						state = ConnectedState.Open;
						houseState.setIcon(houseWindowOpen);
					}
					else if( windowStateBox.getSelectedItem().toString().equals("Closed")){
						state = ConnectedState.Closed;
					}
					new Thread(){
	                    public void run() {
	                    	ThermosController.toggleWindowState(ThermosConstants.WINDOW_1, state);
	                    }
	                }.start();
	                //HouseModification();
				}
			}
			
			class RadiatorStateModification implements ActionListener {
				ConnectedState state;
				public void actionPerformed(ActionEvent e){
					if(radiatorStateBox.getSelectedItem().toString().equals("Off")){
						state = ConnectedState.Off;
			    		houseState.setIcon(houseRadiatorOff);
					}
					else if (radiatorStateBox.getSelectedItem().toString().equals("Low")) {
						state = ConnectedState.Low;
			    		houseState.setIcon(houseRadiatorLow);
					}
					else if(radiatorStateBox.getSelectedItem().toString().equals("Strong"))  {
						state = ConnectedState.Strong;
			    		houseState.setIcon(houseRadiatorStrong);
					}
					new Thread(){
	                    public void run() {
	                    	ThermosController.toggleRadiatorState(ThermosConstants.RADIATOR_1, state,isManual);
	                    }
	                }.start();
	                isManual=true;
	                //HouseModification();
				}
			}
		    
		    public static void HouseModification (String connectedId, ConnectedState newState){
		    	LOGGER.info("*********************************************************************");
		    	LOGGER.info("House Modification  " + (compteur++) +" " + newState.toString());
		    	isManual=false;
			    	if(newState.equals(ConnectedState.Off)) {
			    		radiatorStateBox.setSelectedIndex(0);
			    	} 
			    	else if(newState.equals(ConnectedState.Low)) {
			    		radiatorStateBox.setSelectedIndex(1);
			    	} 
			    	else if(newState.equals(ConnectedState.Strong)) {
			    		radiatorStateBox.setSelectedIndex(2);
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