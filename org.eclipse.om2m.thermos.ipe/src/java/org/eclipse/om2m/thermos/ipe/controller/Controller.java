package org.eclipse.om2m.thermos.ipe.controller;

import java.awt.image.SampleModel;

import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.thermos.ipe.RequestSender;
import org.eclipse.om2m.thermos.ipe.constants.ThermosConstants;
import org.eclipse.om2m.thermos.ipe.model.ConnectedState;
import org.eclipse.om2m.thermos.ipe.model.ThermosModel;
import org.eclipse.om2m.thermos.ipe.util.ObixUtil;

public class Controller {

	public static CseService CSE;
	protected static String AE_ID;
	
	public static void setConnectedState(String connectedId, boolean value){
		// Set the value in the "real world" model
		ThermosModel.setConnectedState(connectedId, value);
		// Send the information to the CSE
		String targetID = ThermosConstants.CSE_PREFIX + "/" + connectedId + "/" + ThermosConstants.DATA;
		ContentInstance cin = new ContentInstance();
		cin.setContent(ObixUtil.getStateRep(connectedId, value));
		cin.setContentInfo(MimeMediaType.OBIX + ":" + MimeMediaType.ENCOD_PLAIN);
		RequestSender.createContentInstance(targetID, null, cin);
	}
	
	public static String getFormatedConnectedState(String connectedId){
		return ObixUtil.getStateRep(connectedId, getConnectedState(connectedId));
	}
	
	public static ConnectedState getConnectedState(String connectedId){
		return ThermosModel.getConnectedState(connectedId);
	}
	

	public static void setCse(CseService cse){
		CSE = cse;
	}
}