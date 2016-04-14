package org.eclipse.om2m.ipe.sample.controller;

import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.ipe.sample.RequestSender;
import org.eclipse.om2m.ipe.sample.constants.ThermosConstants;
import org.eclipse.om2m.ipe.sample.model.*;
import org.eclipse.om2m.ipe.sample.util.ObixUtil;

public class ThermosController {

	public static CseService CSE;
	protected static String AE_ID;
	
	/*
	 * Création de la requete et set du changement d'état au Model
	 * @param connectedId Id de la ressource
	 * @param state etat de la ressource
	 */
	public static void setConnectedState(String connectedId, ConnectedState state){
		// Set the value in the "real world" model
		ThermosModel.setConnectedState(connectedId, state);
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
	
	public static void toggleRadiatorState(String connectedId, ConnectedState state){
		if(ThermosModel.isSystemActivate() && !ThermosModel.isWindowOpen()){
			setConnectedState(connectedId,state);
		}
	}
	
	public static void toggleWindowState(String connectedId, ConnectedState state){
			setConnectedState(connectedId,state);
			if(state.equals(ConnectedState.Closed)) setConnectedState(ThermosConstants.RADIATOR_1,ConnectedState.Off);
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
	
	public static void regulTemperature(){
		if(ThermosModel.isSystemActivate() && !ThermosModel.isWindowOpen()){
			// TI
			int locTempInterne = ThermosModel.getTemperatureInterne();
			// TE
			int locTempExterne = ThermosModel.getTemperatureExterne();
			// TC
			int locTempConsigne = ThermosModel.getTempconsigne();
			// TE + Coef(TC - TE)
			double locCoefUserTempExterne = locTempExterne +(ThermosModel.getCoefUser()*(locTempConsigne-locTempExterne));
			double locDegreTolere = ThermosModel.getIntervalleTolerance();
			
			// TI > TE
			if(locTempInterne>locTempExterne){
				toggleRadiatorState(ThermosConstants.RADIATOR_1,ConnectedState.Off);
				// TE + Coef(TC - TE) > TI > TE 
			}else if(locTempInterne>locTempExterne && locCoefUserTempExterne>locTempInterne){
				toggleRadiatorState(ThermosConstants.RADIATOR_1,ConnectedState.Strong);
				// TC - Delta > TI > TE + Coef(TC - TE)
			}else if(locTempInterne>locCoefUserTempExterne && (locTempConsigne-locDegreTolere)>locTempInterne){
				toggleRadiatorState(ThermosConstants.RADIATOR_1,ConnectedState.Low);
				// TC > TI > TC - Delta
			}else if(locTempInterne>(locTempConsigne-locDegreTolere) && locTempConsigne>locTempInterne){
				toggleRadiatorState(ThermosConstants.RADIATOR_1,ConnectedState.Off);
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
