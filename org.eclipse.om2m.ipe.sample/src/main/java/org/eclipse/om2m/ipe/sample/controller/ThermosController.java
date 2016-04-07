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
	
	public static void toggleRadiatorState(String connectedId, ConnectedState state){
		if(ThermosModel.isSystemActivate() && !ThermosModel.isWindowOpen()){
			setConnectedState(connectedId,state);
		}
	}
	
	public static void toggleWindowState(String connectedId, ConnectedState state){
			setConnectedState(connectedId,state);
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
			int locTempInterne = ThermosModel.getTemperatureInterne();
			int locTempExterne = ThermosModel.getTemperatureExterne();
			int locTempConsigne = ThermosModel.getTempconsigne();
			double locCoefUserTempExterne = locTempExterne +(ThermosModel.getCoefUser()*(locTempConsigne-locTempExterne));
			double locDegreTolere = ThermosModel.getIntervalleTolerance();
			
			if(locTempInterne>locTempExterne){
				toggleRadiatorState(Radiator.TYPE+"_"+1,ConnectedState.Off);
			}else if(locTempInterne>locTempExterne && locCoefUserTempExterne>locTempInterne){
				toggleRadiatorState(Radiator.TYPE+"_"+1,ConnectedState.Strong);
			}else if(locTempInterne>locCoefUserTempExterne && (locTempConsigne+locDegreTolere)>locTempInterne){
				toggleRadiatorState(Radiator.TYPE+"_"+1,ConnectedState.Low);
			}else if(locTempInterne>(locTempConsigne+locDegreTolere) && locTempConsigne>locTempInterne){
				toggleRadiatorState(Radiator.TYPE+"_"+1,ConnectedState.Off);
			}
		}
	}	
}
