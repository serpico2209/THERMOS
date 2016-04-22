package org.eclipse.om2m.ipe.sample.gui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.eclipse.om2m.ipe.sample.constants.ThermosConstants;
import org.eclipse.om2m.ipe.sample.controller.ThermosController;
import org.eclipse.om2m.ipe.sample.model.*;

import java.awt.BasicStroke;

class Surface extends JPanel implements ActionListener {

    private final int DELAY = 200;
    
    /* Coordonnées X et Y des températures externes (TEx et TEy), 
     * internes (TIx et TIy) et cible (TCx et TCy)
     * TCylimit représente la borne inférieure de la température cible
     */
    
    private int[] TEx = new int[10000];
    private int[] TEy = new int[10000];
    private int[] TIx = new int[10000];
    private int[] TIy = new int[10000];
    private int[] TCx = new int[10000];
    private int[] TCy = new int[10000];
    private int[] TCylimit = new int[10000];
    static String THERMOMETER_EXT = "THERMOMETER_EXT";
    static String THERMOMETER_INT = "THERMOMETER_INT";
    
    /* Initialisation des compteurs de nombres de points 
     * pour chaque courbe
     */
    
    private int ni = 1;
    private int ne = 1;
    private int nc = 1;
    
    private Timer timer;

    public Surface() {
	
    	this.setBackground(Color.white);
    	this.setAutoscrolls(true);
        initTimer();       
    }

    private void initTimer() {

        timer = new Timer(DELAY, this);
        timer.start();           
    }
    
    public Timer getTimer() {
        
        return timer;
    }
    
    /* Translation des courbes de température */
    
    private void shift(int n) {
    	for (int i = 0; i <= (n-1); i++) {
    		TEy[i]=TEy[i+1];
    		TIy[i]=TIy[i+1];
    		TCy[i]=TCy[i+1];
    		TCylimit[i]=TCylimit[i+1];
    	}
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(Color.blue);
        
        if (ni>(540/5)) {
        	shift(540/5);
        	nc = 108;
        	ne = 108;
        	ni = 108;
        }

        int w = getWidth();      
        int h = 800;
        int offset = 60;
        int margeConfort = 1; // Intervalle de tolérance pour la température cible (1°C)
        
        /* Initialisation des témpératures TE (4°C), TI(6°C) et TC (22°C) */
        
        if ((ni==1) || (nc==1) || (ne==1)) {
        TEx[0] = offset;
    	TEy[0] = (int) (h/2-(h/80)*4);
    	TIx[0] = offset;
    	TIy[0] = (int) (h/2-(h/80)*6);
    	TCx[0] = offset;
    	TCy[0] = (int) (h/2-(h/80)*22);
    	TCylimit[0] = (int) (h/2-(h/80)*(22-margeConfort));
    	
        }
    	

        	Random r = new Random();
            float[] dash1 = { 2f, 0f, 2f };
                 
            /* Tracé du quadrillage et de l'échelle des ordonnés */
            
            for (int j = 40; j <=h; j = j + 40) {
            	BasicStroke bs1 = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f,dash1, 2f);
            	g2d.setPaint(Color.gray);
                g2d.setStroke(bs1);
                g2d.drawLine(offset, j, w, j);
                g2d.drawString(Math.round(((double)((h/2)-j)*80*5/h))/5.0 + "°C", 5, j-2);
                g2d.setPaint(new Color(128, 109, 190));
                g2d.drawString("Ext (°C)", w/2-70, h-20);
                g2d.setPaint(new Color(231, 62, 1));
                g2d.drawString("Int (°C)", w/2, h-20);
                g2d.setPaint(new Color(131, 166, 151));
                g2d.drawString("Cible (°C)", w/2+70, h-20);                
        	}
            
            /* Tracé de l'axe principal des abscisse */
            
            BasicStroke bs1 = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f);
        	g2d.setPaint(Color.gray);
            g2d.setStroke(bs1);
            g2d.drawLine(offset, h/2, w, h/2);
            g2d.drawLine(offset, 0, offset, h);
            
            /* Affichage de la courbe des températures externes TE : 
             * utilisation d'un aléa compris entre -3°C et 3°C pour incrémenter 
             * la courbe */
            
            g2d.setPaint(new Color(128, 109, 190));
            BasicStroke bs3 = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1.0f);
            g2d.setStroke(bs3); 
            TEx[ne] = TEx[ne-1] + 5;
            int alea_temp = r.nextInt( 3 + 3 + 1) - 3;
            System.out.print("alea_temp=");
            System.out.println(alea_temp);
            TEy[ne] = (int) (TEy[ne-1] - (h*alea_temp)/80);
            g2d.drawPolyline(TEx, TEy, ne+1);
            ThermosController.toggleThermometer(ThermosConstants.THERMOMETER_EXT, (int) ((((h/2)-TEy[ne])*80)/h));
            ne = ne +1;
        	
            /* Affichage de la courbe des températures internes TI :
             * converge vers la température cible (si chauffage allumé) ou sinon
             * vers la température externe.
             * Chauffage 'strong' : pente plus forte que chauffage 'low' */
            
        	ConnectedState State;
        	State = ThermosModel.getConnectedState(ThermosConstants.RADIATOR_1);
        	g2d.drawString("Chauffage : ", w-151, h-20); 
            g2d.drawString(State.toString(), w-70, h-20); 
            TIx[ni] = TIx[ni-1] + 5;
            if (State.equals(ConnectedState.Low)) {
            	TIy[ni] = (int) (TIy[ni-1]-0.5); 
            } else if (State.equals(ConnectedState.Strong))
            {
            	TIy[ni] = (int) (TIy[ni-1]-2);
            } else            	
            {
            	if ((TIy[ni-1]-TEy[ne-2])< 0) {
            		
            		TIy[ni] = Math.min((int) (TIy[ni-1] + Math.abs(((TIy[ni-1]-TEy[ne-2])/50))), 800);
            	}
            	else {
            		TIy[ni] = Math.max((int) (TIy[ni-1] - Math.abs(((TIy[ni-1]-TEy[ne-2])/50))), 0);
            	}
            }            
            BasicStroke bs2 = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
            g2d.setPaint(new Color(231, 62, 1));
            g2d.setStroke(bs2);
            g2d.drawPolyline(TIx,TIy, ni+1);
            ThermosController.toggleThermometer(ThermosConstants.THERMOMETER_INT,(int) ((((h/2)-TIy[ni])*80)/h));
            ni = ni +1;
            
            /* Tracé de la température cible */
            
            g2d.setPaint(new Color(131, 166, 151));          
            BasicStroke bs4 = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1.0f);
            g2d.setStroke(bs4);
            TCx[nc] = TCx[nc-1] + 5;
            TCy[nc] = (int) (h/2-(h/80)*22);
            TCylimit[nc] = (int) (h/2-(h/80)*(22 - margeConfort)); 
            g2d.drawPolyline(TCx, TCy, nc+1);
            ThermosController.toggleTempConsigne((int) ((((h/2)-TCy[nc])*80)/h));
            //nc = nc +1;
            float[] dash2 = { 4f, 0f, 2f };
            BasicStroke bs5 = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f,dash2, 2f);
            g2d.setStroke(bs5);
            g2d.drawPolyline(TCx, TCylimit, nc+1);
            nc = nc +1;
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
