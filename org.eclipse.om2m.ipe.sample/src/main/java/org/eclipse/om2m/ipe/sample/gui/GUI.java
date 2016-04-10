package org.eclipse.om2m.ipe.sample.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.ipe.sample.constants.ThermosConstants;
import org.eclipse.om2m.ipe.sample.controller.ThermosController;
import org.eclipse.om2m.ipe.sample.model.ConnectedState;
import org.eclipse.om2m.ipe.sample.model.ThermosModel;
import org.osgi.framework.FrameworkUtil;

public class GUI extends JFrame {
	
	private JPanel contentPanel1;
    static JPanel jPanelOuvetFermet;
    static JPanel panelTemperatureConsigne;
    static JPanel panel_Radiator;
    static JPanel panel_Thermometer_Ext;
    static JPanel panel_Thermometer_Int;
    static JPanel panel_Window;
    
    static Log LOGGER = LogFactory.getLog(GUI.class);
    private static final long serialVersionUID = 1L;
    static ThermosModel.ConnectedObserver connectedObserver;

	
	/*Les températures lors de l'exucution du programme*/
	private int temp_externe;
	private int temp_interne;
	private int temp_consigne; 
	
	                
    static JLabel LABEL_RADIATOR;
    static JLabel LABEL_THERMOMETER_EXT;
    static JLabel LABEL_WINDOW;
    static JLabel LABEL_THERMOMETER_Int;
    static JLabel jLabelFermer;
    static JLabel jLabelRadiator;
    static JLabel jLabelTempConsigne;
    static JLabel jLabelTermo;
    static JLabel jLabelWindow;
    
    static JButton jButtonAugmenterTC;
    static JButton jButtonAugmenterTE;
    static JButton jButtonAugmenterTi;
    static JButton jButtonDiminuerTC;
    static JButton jButtonDiminuerTE;
    static JButton jButtonDiminuerTi;
    
    static JComboBox<String> jComboBoxRadiator;
    static JComboBox<String> jComboBoxWindow;
    
    static JTextField jTextTempCons;
    static JTextField jTextThermoEx;
    static JTextField jTextThermoIn;
    
    
    static String WINDOW = "WINDOW";
    static String RADIATOR = "RADIATOR";
    static String THERMOMETER_EXT = "THERMOMETER_EXT";
    static String THERMOMETER_INT = "THERMOMETER_INT";
    
	/*Les différentes images liées aux états des ressources*/
	static ImageIcon Radiator_Low = new ImageIcon(FrameworkUtil.getBundle(GUI.class).getResource("images/RadiateurOuvert"));
	static ImageIcon Radiator_Close = new ImageIcon(FrameworkUtil.getBundle(GUI.class).getResource("images/RadiateurFermer"));
	static ImageIcon Radiator_Strong = new ImageIcon(FrameworkUtil.getBundle(GUI.class).getResource("images/RadiateurFort"));
	static ImageIcon Thermometer = new ImageIcon(FrameworkUtil.getBundle(GUI.class).getResource("images/thermometer"));
	static ImageIcon Window_Open = new ImageIcon(FrameworkUtil.getBundle(GUI.class).getResource("images/fenetreOuvert"));
	static ImageIcon Window_Close = new ImageIcon(FrameworkUtil.getBundle(GUI.class).getResource("images/fenetreFermer"));
	static ImageIcon State_On = new ImageIcon(FrameworkUtil.getBundle(GUI.class).getResource("images/Btn_ON"));
	static ImageIcon State_Off = new ImageIcon(FrameworkUtil.getBundle(GUI.class).getResource("images/Btn_OFF"));	
	
			
		
			/**
	 		* Creates the frame.
     		*/
			public GUI() {				
		        contentPanel1 = new JPanel(); 
		        
		        /*Window */
		        panel_Window = new JPanel(); 
		        LABEL_WINDOW = new JLabel();
		        jLabelWindow = new JLabel(); 
		        jComboBoxWindow = new JComboBox<>(); 
		        
		        /*Radiator */
		        panel_Radiator = new JPanel();
		        LABEL_RADIATOR = new JLabel();
		        jComboBoxRadiator = new JComboBox<>();  
		        jLabelRadiator = new JLabel(); 
		        
		        /*Thermometre extérieur */
		        panel_Thermometer_Ext = new JPanel();
		        LABEL_THERMOMETER_EXT = new JLabel(); 
		        jTextThermoEx = new JTextField(); 
		        jButtonAugmenterTE = new JButton();
		        jButtonDiminuerTE = new JButton();  
		        
		        /*Thermometre intérieur */
		        panel_Thermometer_Int = new JPanel();
		        LABEL_THERMOMETER_Int = new JLabel();
		        jButtonAugmenterTi = new JButton();
		        jButtonDiminuerTi = new JButton();
		        jTextThermoIn = new JTextField();
		        
		        /*Temperature de consigne */
		        panelTemperatureConsigne = new JPanel();
		        jLabelTermo = new JLabel();
		        jLabelTempConsigne = new JLabel();
		        jTextTempCons = new JTextField();
		        jButtonAugmenterTC = new JButton();
		        jButtonDiminuerTC = new JButton();
		        jPanelOuvetFermet = new JPanel();
		        
		        /*Icon gérant a fermeture du service*/
		        jLabelFermer = new JLabel();
		       // jLabelOuvert = new JLabel();

		        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		        
		        
		        /*Override des observeurs */
		        connectedObserver = new ThermosModel.ConnectedObserver() {			
					@Override
					public void onConnectedStateChange(String connectedId, ConnectedState state) {
						setLabelConnected(connectedId, state);
					}
				};

		        ThermosModel.addObserver(connectedObserver);

		        
		        /*
		         * Possitionnement des différents composants du système dans un groupLayout :
		         * Raiator, Window , et thermometre
		         */
		        
		        /*WINDOW */
		        LABEL_WINDOW.setFont(new java.awt.Font("Tahoma", 1, 11));
		        LABEL_WINDOW.setText("Window");
		        jLabelWindow.setIcon(Window_Open);
		        jComboBoxWindow.setModel(new DefaultComboBoxModel<>(new String[] {"Fermer", "Ouvert" }));
		        jComboBoxWindow.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		                jComboBoxWindowActionPerformed(evt);
		            }
		        });
		        GroupLayout panel_WindowLayout = new GroupLayout(panel_Window);
		        panel_Window.setLayout(panel_WindowLayout);
		        panel_WindowLayout.setHorizontalGroup(
		            panel_WindowLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
		            .addGroup(panel_WindowLayout.createSequentialGroup()
		                .addGap(22, 22, 22)
		                .addComponent(LABEL_WINDOW)
		                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		            .addGroup(panel_WindowLayout.createSequentialGroup()
		                .addContainerGap()
		                .addComponent(jComboBoxWindow, GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
		                .addComponent(jLabelWindow)
		                .addGap(23, 23, 23))
		        );
		        panel_WindowLayout.setVerticalGroup(
		            panel_WindowLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
		            .addGroup(panel_WindowLayout.createSequentialGroup()
		                .addContainerGap()
		                .addComponent(LABEL_WINDOW)
		                .addGroup(panel_WindowLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
		                    .addGroup(panel_WindowLayout.createSequentialGroup()
		                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
		                        .addComponent(jLabelWindow, GroupLayout.PREFERRED_SIZE, 141,GroupLayout.PREFERRED_SIZE))
		                    .addGroup(panel_WindowLayout.createSequentialGroup()
		                        .addGap(23, 23, 23)
		                        .addComponent(jComboBoxWindow,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		                .addContainerGap(41, Short.MAX_VALUE))
		        );
		       
		        
		        /*RADIATOR */	 
		        LABEL_RADIATOR.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		        LABEL_RADIATOR.setText("Radiateur");

		        jComboBoxRadiator.setModel(new DefaultComboBoxModel<>(new String[] { "Eteint", "Fort", "Faible" }));
		        jComboBoxRadiator.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		                jComboBoxRadiatorActionPerformed(evt);
		            }
		        });
		        jLabelRadiator.setIcon(Radiator_Low); // NOI18N
		        GroupLayout panel_RadiatorLayout = new GroupLayout(panel_Radiator);
		        panel_Radiator.setLayout(panel_RadiatorLayout);
		        panel_RadiatorLayout.setHorizontalGroup(
		            panel_RadiatorLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
		            .addGroup(GroupLayout.Alignment.TRAILING, panel_RadiatorLayout.createSequentialGroup()
		                .addContainerGap()
		                .addComponent(jLabelRadiator, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                .addGap(54, 54, 54)
		                .addGroup(panel_RadiatorLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
		                    .addComponent(jComboBoxRadiator, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
		                    .addComponent(LABEL_RADIATOR))
		                .addContainerGap())
		        );
		        panel_RadiatorLayout.setVerticalGroup(
		            panel_RadiatorLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
		            .addGroup(panel_RadiatorLayout.createSequentialGroup()
		                .addGap(4, 4, 4)
		                .addComponent(LABEL_RADIATOR)
		                .addGap(18, 18, 18)
		                .addGroup(panel_RadiatorLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
		                    .addComponent(jComboBoxRadiator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		                    .addComponent(jLabelRadiator))
		                .addContainerGap(28, Short.MAX_VALUE))
		        );
		        

		        /*THERMOMETRE EXTERIEUR */	
		        LABEL_THERMOMETER_EXT.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		        LABEL_THERMOMETER_EXT.setText("Temperature Externe");

		        jTextThermoEx.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		                jTextThermoExActionPerformed(evt);
		            }
		        });
		        jButtonAugmenterTE.setLabel("+");		        
		        jButtonAugmenterTE.addMouseListener(new java.awt.event.MouseAdapter() {
		            public void mouseClicked(java.awt.event.MouseEvent evt) {
		                jButtonAugmenterTEMouseClicked(evt);
		            }
		        });	        
		        jButtonAugmenterTE.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		            	modificationTempExterne(evt);
		            }
		        });
		        jButtonDiminuerTE.setLabel("-");		        
		        jButtonDiminuerTE.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		            	modificationTempExterne(evt);
		            }
		        });
		        GroupLayout panel_Thermometer_ExtLayout = new GroupLayout(panel_Thermometer_Ext);
		        panel_Thermometer_Ext.setLayout(panel_Thermometer_ExtLayout);
		        panel_Thermometer_ExtLayout.setHorizontalGroup(
		            panel_Thermometer_ExtLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
		            .addGroup(panel_Thermometer_ExtLayout.createSequentialGroup()
		                .addGroup(panel_Thermometer_ExtLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
		                    .addGroup(panel_Thermometer_ExtLayout.createSequentialGroup()
		                        .addGap(40, 40, 40)
		                        .addComponent(jTextThermoEx,GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
		                        .addGap(36, 36, 36)
		                        .addGroup(panel_Thermometer_ExtLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
		                            .addComponent(jButtonDiminuerTE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                            .addComponent(jButtonAugmenterTE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
		                    .addGroup(panel_Thermometer_ExtLayout.createSequentialGroup()
		                        .addContainerGap()
		                        .addComponent(LABEL_THERMOMETER_EXT)))
		                .addContainerGap(42, Short.MAX_VALUE))
		        );
		        panel_Thermometer_ExtLayout.setVerticalGroup(
		            panel_Thermometer_ExtLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
		            .addGroup(panel_Thermometer_ExtLayout.createSequentialGroup()
		                .addComponent(LABEL_THERMOMETER_EXT)
		                .addGroup(panel_Thermometer_ExtLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
		                    .addGroup(panel_Thermometer_ExtLayout.createSequentialGroup()
		                        .addGap(39, 39, 39)
		                        .addComponent(jTextThermoEx, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		                    .addGroup(panel_Thermometer_ExtLayout.createSequentialGroup()
		                        .addGap(23, 23, 23)
		                        .addComponent(jButtonAugmenterTE)
		                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
		                        .addComponent(jButtonDiminuerTE)))
		                .addGap(0, 46, Short.MAX_VALUE))
		        );
		        jButtonAugmenterTE.getAccessibleContext().setAccessibleName("setLabel");
		        jButtonDiminuerTE.getAccessibleContext().setAccessibleName("setLabel");

		        
		        
		        /*THERMOMETRE INTERIEUR */	
		        LABEL_THERMOMETER_Int.setFont(new java.awt.Font("Tahoma", 1, 11)); 
		        LABEL_THERMOMETER_Int.setText("Temperature Interne");

		        jButtonAugmenterTi.setLabel("+");
		        jButtonAugmenterTi.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		            	modificationTempInterne(evt);
		            }
		        });

		        jButtonDiminuerTi.setLabel("-");
		        jButtonDiminuerTi.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		            	modificationTempInterne(evt);
		            }
		        });
		        GroupLayout panel_Thermometer_IntLayout = new GroupLayout(panel_Thermometer_Int);
		        panel_Thermometer_Int.setLayout(panel_Thermometer_IntLayout);
		        panel_Thermometer_IntLayout.setHorizontalGroup(
		            panel_Thermometer_IntLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
		            .addGroup(panel_Thermometer_IntLayout.createSequentialGroup()
		                .addComponent(LABEL_THERMOMETER_Int)
		                .addGap(0, 0, Short.MAX_VALUE))
		            .addGroup(GroupLayout.Alignment.TRAILING, panel_Thermometer_IntLayout.createSequentialGroup()
		                .addGap(24, 24, 24)
		                .addComponent(jTextThermoIn, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
		                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
		                .addGroup(panel_Thermometer_IntLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
		                    .addComponent(jButtonAugmenterTi, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                    .addComponent(jButtonDiminuerTi, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		                .addGap(77, 77, 77))
		        );
		        panel_Thermometer_IntLayout.setVerticalGroup(
		            panel_Thermometer_IntLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
		            .addGroup(panel_Thermometer_IntLayout.createSequentialGroup()
		                .addComponent(LABEL_THERMOMETER_Int)
		                .addGroup(panel_Thermometer_IntLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
		                    .addGroup(panel_Thermometer_IntLayout.createSequentialGroup()
		                        .addGap(30, 30, 30)
		                        .addComponent(jTextThermoIn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
		                    .addGroup(panel_Thermometer_IntLayout.createSequentialGroup()
		                        .addGap(13, 13, 13)
		                        .addComponent(jButtonAugmenterTi)
		                        .addGap(11, 11, 11)
		                        .addComponent(jButtonDiminuerTi)))
		                .addContainerGap(72, Short.MAX_VALUE))
		        );
		        jButtonAugmenterTi.getAccessibleContext().setAccessibleName("setLabel");
		        jButtonDiminuerTi.getAccessibleContext().setAccessibleName("setLabel");
		        jLabelTermo.setIcon(Thermometer); // NOI18N

		        
		        /*TEMPERATURE DE CONSIGNE */	
		        jLabelTempConsigne.setFont(new java.awt.Font("Tahoma", 1, 11)); 
		        jLabelTempConsigne.setText("Temperature Consigne");
		        jTextTempCons.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		                jTextTempConsActionPerformed(evt);
		            }
		        });
		        jButtonAugmenterTC.setText("+");
		        jButtonAugmenterTC.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		                modificationTempConsigne(evt);
		            }
		        });
		        jButtonDiminuerTC.setText("-");
		        jButtonDiminuerTC.setMaximumSize(new java.awt.Dimension(41, 23));
		        jButtonDiminuerTC.setMinimumSize(new java.awt.Dimension(41, 23));
		        jButtonDiminuerTC.setPreferredSize(new java.awt.Dimension(41, 23));
		        jButtonDiminuerTC.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		            	modificationTempConsigne(evt);
		            }
		        });
		        GroupLayout panelTemperatureConsigneLayout = new GroupLayout(panelTemperatureConsigne);
		        panelTemperatureConsigne.setLayout(panelTemperatureConsigneLayout);
		        panelTemperatureConsigneLayout.setHorizontalGroup(
		            panelTemperatureConsigneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
		            .addGroup(panelTemperatureConsigneLayout.createSequentialGroup()
		                .addGap(41, 41, 41)
		                .addComponent(jLabelTermo)
		                .addGroup(panelTemperatureConsigneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
		                    .addGroup(panelTemperatureConsigneLayout.createSequentialGroup()
		                        .addGap(65, 65, 65)
		                        .addComponent(jLabelTempConsigne))
		                    .addGroup(panelTemperatureConsigneLayout.createSequentialGroup()
		                        .addGap(35, 35, 35)
		                        .addComponent(jTextTempCons, GroupLayout.PREFERRED_SIZE, 50,GroupLayout.PREFERRED_SIZE)
		                        .addGap(27, 27, 27)
		                        .addGroup(panelTemperatureConsigneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
		                            .addComponent(jButtonAugmenterTC,GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                            .addComponent(jButtonDiminuerTC, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
		                .addContainerGap(86, Short.MAX_VALUE))
		        );
		        panelTemperatureConsigneLayout.setVerticalGroup(
		            panelTemperatureConsigneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
		            .addGroup(GroupLayout.Alignment.TRAILING, panelTemperatureConsigneLayout.createSequentialGroup()
		                .addGap(0, 22, Short.MAX_VALUE)
		                .addComponent(jLabelTermo, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE))
		            .addGroup(panelTemperatureConsigneLayout.createSequentialGroup()
		                .addGap(60, 60, 60)
		                .addComponent(jLabelTempConsigne)
		                .addGroup(panelTemperatureConsigneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
		                    .addGroup(panelTemperatureConsigneLayout.createSequentialGroup()
		                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                        .addComponent(jButtonAugmenterTC)
		                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
		                        .addComponent(jButtonDiminuerTC, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
		                        .addGap(81, 81, 81))
		                    .addGroup(panelTemperatureConsigneLayout.createSequentialGroup()
		                        .addGap(42, 42, 42)
		                        .addComponent(jTextTempCons, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
		        );
		        jLabelTempConsigne.getAccessibleContext().setAccessibleName("setLabel");
		        jButtonAugmenterTC.getAccessibleContext().setAccessibleName("setLabel");
		        jButtonDiminuerTC.getAccessibleContext().setAccessibleName("setLabel");

		        
		        /*ACTIVATION/DESACTIVATION SYSTEME */	
		        jLabelFermer.setIcon(State_Off); 
		       // jLabelOuvert.setIcon(new ImageIcon("C:\\Users\\abchabassem\\Desktop\\Btn_ON.png")); 
		        GroupLayout jPanelOuvetFermetLayout = new GroupLayout(jPanelOuvetFermet);
		        jPanelOuvetFermet.setLayout(jPanelOuvetFermetLayout);
		        jPanelOuvetFermetLayout.setHorizontalGroup(
		            jPanelOuvetFermetLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
		            .addGroup(jPanelOuvetFermetLayout.createSequentialGroup()
		                .addGap(45, 45, 45)
		                .addComponent(jLabelFermer)
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
		                //.addComponent(jLabelOuvert)
		                .addGap(52, 52, 52))
		        );
		        jPanelOuvetFermetLayout.setVerticalGroup(
		            jPanelOuvetFermetLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
		            .addGroup(jPanelOuvetFermetLayout.createSequentialGroup()
		                .addGap(26, 26, 26)
		                .addGroup(jPanelOuvetFermetLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
		                   // .addComponent(jLabelOuvert)
		                    .addComponent(jLabelFermer))
		                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		        );

		        /*GUI Contenant toutes les ressources */
		        GroupLayout contentPanel1Layout = new GroupLayout(contentPanel1);
		        contentPanel1.setLayout(contentPanel1Layout);
		        contentPanel1Layout.setHorizontalGroup(
		            contentPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		            .addGroup(contentPanel1Layout.createSequentialGroup()
		                .addComponent(panel_Window, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		                .addGap(190, 190, 190)
		                .addComponent(panel_Radiator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		            .addGroup(GroupLayout.Alignment.TRAILING, contentPanel1Layout.createSequentialGroup()
		                .addGap(0, 0, Short.MAX_VALUE)
		                .addComponent(jPanelOuvetFermet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		                .addGap(237, 237, 237))
		            .addGroup(contentPanel1Layout.createSequentialGroup()
		                .addGroup(contentPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		                    .addComponent(panel_Thermometer_Ext, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		                    .addGroup(GroupLayout.Alignment.TRAILING, contentPanel1Layout.createSequentialGroup()
		                        .addGap(18, 18, 18)
		                        .addComponent(panel_Thermometer_Int, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                .addComponent(panelTemperatureConsigne, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		                .addGap(39, 39, 39))
		        );
		        contentPanel1Layout.setVerticalGroup(
		            contentPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		            .addGroup(contentPanel1Layout.createSequentialGroup()
		                .addGroup(contentPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		                    .addComponent(panel_Window, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		                    .addComponent(panel_Radiator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		                .addGap(18, 18, 18)
		                .addGroup(contentPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addGroup(contentPanel1Layout.createSequentialGroup()
		                        .addComponent(panel_Thermometer_Ext, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		                        .addGap(18, 18, 18)
		                        .addComponent(panel_Thermometer_Int, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		                    .addComponent(panelTemperatureConsigne, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
		                .addComponent(jPanelOuvetFermet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		        );
		        panel_Thermometer_Int.getAccessibleContext().setAccessibleName("");
		        GroupLayout layout = new GroupLayout(getContentPane());
		        getContentPane().setLayout(layout);
		        layout.setHorizontalGroup(
		            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		            .addGroup(layout.createSequentialGroup()
		                .addComponent(contentPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		                .addGap(0, 0, Short.MAX_VALUE))
		        );
		        layout.setVerticalGroup(
		            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		            .addGroup(layout.createSequentialGroup()
		                .addComponent(contentPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		                .addGap(0, 0, Short.MAX_VALUE))
		        );

		        contentPanel1.getAccessibleContext().setAccessibleName("Thermo Interne");
		        pack();
		    }                       

			/*
			 * Les méthodes qui suivront sont liés aux évenements et changements pouvant intervenir lorsque 
			 * le service de régulation change d'état
			 */
		    
		    /* Evenement : Modification de la temperature interne*/
		    private void modificationTempInterne(java.awt.event.ActionEvent evt) {                                                   
		        // TODO add your handling code here:
		         if(evt.getSource() == jButtonAugmenterTi) {
		        	 jTextThermoIn.setText(String.valueOf(++temp_interne));
		         }else if(evt.getSource() == jButtonDiminuerTi){
			        	jTextThermoIn.setText(String.valueOf(--temp_interne));
		         }
		         new Thread(){
	                    public void run() {
	       	        	 ThermosController.toggleThermometer(ThermosConstants.THERMOMETER_INT, temp_interne);
	                    }
	                }.start();
		    }    

		    /* Evenement : Modification de la temperature de consigne*/
		    private void modificationTempConsigne(java.awt.event.ActionEvent evt) {                                                   
		      
		          if(evt.getSource() == jButtonAugmenterTC) {                    
		        	  jTextTempCons.setText(String.valueOf(++temp_consigne));
		          }else if(evt.getSource() == jButtonDiminuerTC){
			           jTextTempCons.setText(String.valueOf(--temp_consigne));

		          }
		          new Thread(){
	                    public void run() {
	  		        	  ThermosController.toggleTempConsigne(temp_consigne); 
	                    }
	                }.start();
		    }                                                                                   
		    
		    /* Evenement : Changement d'état du radiateur*/
		   private void jComboBoxRadiatorActionPerformed(java.awt.event.ActionEvent evt) {                                                  
			   if(jComboBoxRadiator.getSelectedItem().toString().equals("Faible"))
				   jLabelRadiator.setIcon(Radiator_Low);
			   else if(jComboBoxRadiator.getSelectedItem().toString().equals("Eteint"))
				   jLabelRadiator.setIcon(Radiator_Close);
			   else
				   jLabelRadiator.setIcon(Radiator_Strong); 
		    }                                                 

		    
		    /* Evenement : Changement d'état de la fenêtre*/
		    private void jComboBoxWindowActionPerformed(java.awt.event.ActionEvent evt) {                                                
		    	final ConnectedState changementEtat;
		    	if(jComboBoxWindow.getSelectedItem().toString().equals("Fermer")){
		    		changementEtat = ConnectedState.Closed;
		        	jLabelWindow.setIcon(Window_Close);
		        	jComboBoxRadiator.setSelectedItem("Faible");
		        }
		        else {
		        	changementEtat = ConnectedState.Open;
		        	jLabelWindow.setIcon(Window_Open);
		        }
		        new Thread(){
                    public void run() {
    		        	ThermosController.toggleWindowState(ThermosConstants.WINDOW_1,changementEtat);
                    }
                }.start();
		    }                                               

		    /* Evenement : modification de la température externe*/
		    private void modificationTempExterne(java.awt.event.ActionEvent evt) {      
		        if(evt.getSource() == jButtonAugmenterTE) {  
		        	jTextThermoEx.setText(String.valueOf(++temp_externe));
		        } else if(evt.getSource() == jButtonDiminuerTE){
	            	 jTextThermoEx.setText(String.valueOf(--temp_externe));
		        }
		        new Thread(){
                    public void run() {
    		        	ThermosController.toggleThermometer(ThermosConstants.THERMOMETER_EXT, temp_externe);
                    }
                }.start();
		    }  
		                                          

		    /**
		     * Sets the ConnectedIcon depending on the newState
		     * @param connectedId - The Connected AppId
		     * @param newState - The new ConnectedState
		     */
		    public static void setLabelConnected(String connectedId, ConnectedState newState) {
		        JLabel label = new JLabel("");
		        if (connectedId.contains("Radiator")) {
		            if(newState == ConnectedState.Off) {	            	
		            	LABEL_RADIATOR.setIcon(Radiator_Close);
		            } else if(newState == ConnectedState.Low) {
		            	LABEL_RADIATOR.setIcon(Radiator_Low);
		            } else if(newState == ConnectedState.Strong){
		            	LABEL_RADIATOR.setIcon(Radiator_Strong);
		            }
		        }	
		    }
		        
		        private void jTextThermoExActionPerformed(java.awt.event.ActionEvent evt) {                                              
			        
			    }                                             

			    private void jButtonAugmenterTEMouseClicked(java.awt.event.MouseEvent evt) {                                                

			    }
			                                                  
			    private void jTextTempConsActionPerformed(java.awt.event.ActionEvent evt) {                                              
			        // TODO add your handling code here:
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