/* 
 * Représentation graphique du comportement de l'algorithme de régulation de 
 * la température interne (TI) de la pièce.
 * 
 * Le GUI appelle des méthodes du contrôleur (ThermosController.java) pour mettre à jour
 * les températures internes, externes et cible des objects connectés. En fonction des ces dernières 
 * il active ou désactive le chauffage.
 * 
 * * Hypothèse : Mode 'Confort' et Fenêtre 'Closed'
 * 
 * Auteur : Mehdi Boureghda (IDL 2015/2016)
 * 
 */


package org.eclipse.om2m.ipe.sample.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


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
	    
    public GUI() {

        initUI();
        
        connectedObserver = new ThermosModel.ConnectedObserver() {			
			@Override
			public void onConnectedStateChange(String connectedId, ConnectedState state) {
				setLabelConnected(connectedId, state);
			}
		};

        ThermosModel.addObserver(connectedObserver);
        
    }

    public static void setLabelConnected(String connectedId, ConnectedState newState) {
        
    }
    
    private void initUI() {

        final Surface surface = new Surface();
        add(surface);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Timer timer = surface.getTimer();
                timer.stop();
            }
        });

        setTitle("Temperature graph simulator");
        setSize(600, 827);
        
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);  
    }
    
    public static void init() {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                GUI ex = new GUI();
                ex.setVisible(true);
                ex.getContentPane().setBackground(Color.BLACK);
                final ConnectedState stateSystem;
                stateSystem = ConnectedState.Activated;
            	ThermosController.toggleProfilUser(stateSystem);
                
            }
        });
    }
    
    public static void stop() {
    	ThermosModel.deleteObserver(connectedObserver);
        new GUI().setVisible(false);
        new GUI().dispose();
    }
    
    
    
}