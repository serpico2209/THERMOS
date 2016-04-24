package org.eclipse.om2m.ipe.sample.controller;

import org.eclipse.om2m.core.service.CseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.ipe.sample.RequestSender;
import org.eclipse.om2m.ipe.sample.constants.ThermosConstants;
import org.eclipse.om2m.ipe.sample.gui.GUI;
import org.eclipse.om2m.ipe.sample.model.*;
import org.eclipse.om2m.ipe.sample.util.ObixUtil;

public class ThermosController {

	public static CseService CSE;
	private static String AE_ID;
    static Log LOGGER = LogFactory.getLog(ThermosController.class);

	
	/*
	 * Création de la requete et set du changement d'état au Model
	 * @param connectedId Id de la ressource
	 * @param state etat de la ressource
	 */
	public static void setConnectedState(String connectedId, ConnectedState state,boolean isManual){
		// Set the value in the "real world" model
		ThermosModel.setConnectedState(connectedId,state,isManual);
		// Send the information to the CSE
		String targetID = ThermosConstants.CSE_PREFIX + "/" + connectedId + "/" + ThermosConstants.DATA;
		ContentInstance cin = new ContentInstance();
		cin.setContent(ObixUtil.getStateRep(connectedId, state));
		cin.setContentInfo(MimeMediaType.OBIX + ":" + MimeMediaType.ENCOD_PLAIN);
		RequestSender.createContentInstance(targetID, null, cin);
	}
	
	public static ConnectedState getConnectedState(String connectedId){
		return ThermosModel.getConnectedState(connectedId);
	}
	
	public static void toggleRadiatorState(String connectedId, ConnectedState state,boolean isManual){
		if(ThermosModel.isSystemActivate() && !ThermosModel.isWindowOpen() ){
			setConnectedState(connectedId,state,isManual);
		}
	}
	
	public static void toggleWindowState(String connectedId, ConnectedState state){
			setConnectedState(connectedId,state,false);
			if(state.equals(ConnectedState.Open)){
				setConnectedState(ThermosConstants.RADIATOR_1,ConnectedState.Off,true);
			}else if(state.equals(ConnectedState.Closed)){
				regulTemperature();
			}
	}
	
	public static void toggleThermometer(String connectedId, int newTemp){
		ThermosModel.modifyTemperature(connectedId, newTemp);
		regulTemperature();
	}
	
	public static void toggleTempConsigne(int newTemp){
		ThermosModel.setTempconsigne(newTemp);
		regulTemperature();
	}
	
	public static void toggleProfilUser(ConnectedState state){
		ThermosModel.setProfiluser(state);
		regulTemperature();
	}
	
	public static void toggleConnectedSystem(ConnectedState state){
		ThermosModel.setStatesystem(state);
		if(ThermosModel.isSystemActivate()){
			regulTemperature();
		}
	}
	
	public static void regulTemperature(){
			if(ThermosModel.isSystemActivate() && !ThermosModel.isWindowOpen()){
			// TI
			int locTempInterne = ThermosModel.getTemperatureInterne();
			// TE
			int locTempExterne = ThermosModel.getTemperatureExterne();
			// TC
			int locTempConsigne = ThermosModel.getTempconsigne();
			// TE + Coef(TC - TE)	
			double locCoefUserTempExterne =(double) locTempExterne +(ThermosModel.getCoefUser()* ((double) (locTempConsigne-locTempExterne)));
			double locDegreTolere = ThermosModel.getIntervalleTolerance();
		
			// TI > TE
			if(locTempInterne>locTempConsigne){
				toggleRadiatorState(ThermosConstants.RADIATOR_1,ConnectedState.Off,false);
				// TE + Coef(TC - TE) > TI > TE 
			}else if(locTempInterne>locTempExterne && locCoefUserTempExterne>locTempInterne){
				toggleRadiatorState(ThermosConstants.RADIATOR_1,ConnectedState.Strong,false);
				// TC - Delta > TI > TE + Coef(TC - TE)
			}else if(locTempInterne>locCoefUserTempExterne && (locTempConsigne-locDegreTolere)>locTempInterne){
				toggleRadiatorState(ThermosConstants.RADIATOR_1,ConnectedState.Low,false);
				// TC > TI > TC - Delta
			}else if(locTempInterne>(locTempConsigne-locDegreTolere) && locTempConsigne>locTempInterne){
				toggleRadiatorState(ThermosConstants.RADIATOR_1,ConnectedState.Off,false);
			}else {
				LOGGER.info(" Hypothèse non cité dans le pdf");
				toggleRadiatorState(ThermosConstants.RADIATOR_1,ConnectedState.Off,false);
			}
		}
	}
	
	public static String getFormatedLampState(String lampId){
		return ObixUtil.getStateRep(lampId, getConnectedState(lampId));
	}
	
	public static void setCse(CseService cse){
		CSE = cse;
	}
}
