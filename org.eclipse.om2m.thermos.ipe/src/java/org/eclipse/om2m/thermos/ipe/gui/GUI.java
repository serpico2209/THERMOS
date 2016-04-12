package org.eclipse.om2m.thermos.ipe.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.FrameworkUtil;

import org.eclipse.om2m.thermos.ipe.model.*;

public class GUI extends JFrame {
    static Log LOGGER = LogFactory.getLog(GUI.class);
    private static final long serialVersionUID = 1L;
    private JPanel contentPanel;
    static ImageIcon iconWindowOPEN = new ImageIcon(FrameworkUtil.getBundle(GUI.class).getResource("images/window.jpg"));
    static ImageIcon iconWindowCLOSED = new ImageIcon(FrameworkUtil.getBundle(GUI.class).getResource("images/window.jpg"));
    static ImageIcon iconThermometer = new ImageIcon(FrameworkUtil.getBundle(GUI.class).getResource("images/thermometer.jpg"));
    static ImageIcon iconRadiatorOFF = new ImageIcon(FrameworkUtil.getBundle(GUI.class).getResource("images/radiator.jpg"));
    static ImageIcon iconRadiatorLOW = new ImageIcon(FrameworkUtil.getBundle(GUI.class).getResource("images/radiator.jpg"));
    static ImageIcon iconRadiatorSTRONG = new ImageIcon(FrameworkUtil.getBundle(GUI.class).getResource("images/radiator.jpg"));
    static GUI frame;
    static JLabel LABEL_WINDOW = new JLabel("");
    static JLabel LABEL_RADIATOR = new JLabel("");
    static JLabel LABEL_THERMOMETER_EXT = new JLabel("");
    static JLabel LABEL_THERMOMETER_INT = new JLabel("");
    static String WINDOW = "WINDOW";
    static String RADIATOR = "RADIATOR";
    static String THERMOMETER_EXT = "THERMOMETER_EXT";
    static String THERMOMETER_INT = "THERMOMETER_INT";
    static ThermosModel.ConnectedObserver connectedObserver;
    
    //Initialisation
    public static void init() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame = new GUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    LOGGER.error("GUI init Error", e);
                }
            }
        });
    }

    //Arrêt
    public static void stop() {
    	ThermosModel.deleteObserver(connectedObserver);
        frame.setVisible(false);
        frame.dispose();
    }

    //Interface graphique
    public GUI() {
    	//Paramètres de base
        setLocationByPlatform(true);
        setVisible(false);
        setResizable(false);
        setTitle("Thermos Simulated IPE");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-500)/2, (screenSize.height-570)/2, 497, 570);

        contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPanel);
        contentPanel.setLayout(null);

        // Radiator
        JPanel panel_Radiator = new JPanel();
        //panel_Radiator.setBounds(10, 5, 319, 260);
        contentPanel.add(panel_Radiator);
        panel_Radiator.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panel_Radiator.setLayout(null);
        LABEL_RADIATOR.setIcon(iconRadiatorOFF);
        LABEL_RADIATOR.setHorizontalTextPosition(SwingConstants.CENTER);
        LABEL_RADIATOR.setHorizontalAlignment(SwingConstants.CENTER);
        LABEL_RADIATOR.setBounds(10, 9, 149, 240);
        panel_Radiator.add(LABEL_RADIATOR);

        // Window
        JPanel panel_Window = new JPanel();
        //panel_Window.setBounds(10, 271, 319, 260);
        contentPanel.add(panel_Window);
        panel_Window.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panel_Window.setLayout(null);

        LABEL_WINDOW.setIcon(iconWindowOPEN);
        LABEL_WINDOW.setHorizontalTextPosition(SwingConstants.CENTER);
        LABEL_WINDOW.setHorizontalAlignment(SwingConstants.CENTER);
        LABEL_WINDOW.setBounds(10, 9, 154, 240);
        panel_Window.add(LABEL_WINDOW);

        //Thermometers
        JPanel panel_Thermometer_Ext = new JPanel();
        //panel_Window.setBounds(10, 271, 319, 260);
        contentPanel.add(panel_Thermometer_Ext);
        panel_Thermometer_Ext.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panel_Thermometer_Ext.setLayout(null);

        LABEL_THERMOMETER_EXT.setIcon(iconThermometer);
        LABEL_THERMOMETER_EXT.setHorizontalTextPosition(SwingConstants.CENTER);
        LABEL_THERMOMETER_EXT.setHorizontalAlignment(SwingConstants.CENTER);
        LABEL_THERMOMETER_EXT.setBounds(10, 9, 154, 240);
        panel_Window.add(LABEL_THERMOMETER_EXT);
        
        JPanel panel_Thermometer_Int = new JPanel();
        //panel_Window.setBounds(10, 271, 319, 260);
        contentPanel.add(panel_Thermometer_Int);
        panel_Thermometer_Int.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panel_Thermometer_Int.setLayout(null);

        LABEL_THERMOMETER_INT.setIcon(iconThermometer);
        LABEL_THERMOMETER_INT.setHorizontalTextPosition(SwingConstants.CENTER);
        LABEL_THERMOMETER_INT.setHorizontalAlignment(SwingConstants.CENTER);
        LABEL_THERMOMETER_INT.setBounds(10, 9, 154, 240);
        panel_Window.add(LABEL_THERMOMETER_INT);
        
        // Observers
        connectedObserver = new ThermosModel.ConnectedObserver() {
			
			@Override
			public void onConnectedStateChange(String connectedId, ConnectedState state) {
				setLabelConnected(connectedId, state);
			}
		};

        ThermosModel.addObserver(connectedObserver);

    }


    // Changement de l'icône et du label en fonction de l'état de l'objet
    public static void setLabelConnected(String connectedId, ConnectedState newState) {
        JLabel label = new JLabel("");
        if ("LABEL_RADIATOR".endsWith(connectedId)) {
            label = LABEL_RADIATOR;
            if(newState == ConnectedState.Off) {
                label.setIcon(iconRadiatorOFF);
            } else if(newState == ConnectedState.Low) {
                label.setIcon(iconRadiatorLOW);
            } else if(newState == ConnectedState.Strong){
            	label.setIcon(iconRadiatorSTRONG);
            }
        }
        else if ("LABEL_WINDOW".endsWith(connectedId)) {
        	label = LABEL_WINDOW;
        	if(newState == ConnectedState.Open)
        		label.setIcon(iconWindowOPEN);
        	else if(newState == ConnectedState.Closed)
        		label.setIcon(iconWindowCLOSED);
        }
        else if ("LABEL_THERMOMETER_EXT".endsWith(connectedId)) {
        	label = LABEL_THERMOMETER_EXT;
        	label.setIcon(iconThermometer);
        }
        else if ("LABEL_THERMOMETER_INT".endsWith(connectedId)) {
        	label = LABEL_THERMOMETER_INT;
        	label.setIcon(iconThermometer);
        }
        
    }
}
