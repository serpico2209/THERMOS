/*******************************************************************************
 * Copyright (c) 2013-2016 LAAS-CNRS (www.laas.fr)
 * 7 Colonel Roche 31077 Toulouse - France
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial Contributors:
 *     Thierry Monteil : Project manager, technical co-manager
 *     Mahdi Ben Alaya : Technical co-manager
 *     Samir Medjiah : Technical co-manager
 *     Khalil Drira : Strategy expert
 *     Guillaume Garzone : Developer
 *     François Aïssaoui : Developer
 *
 * New contributors :
 *******************************************************************************/
package org.eclipse.om2m.ipe.sample.controller;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.ipe.sample.RequestSender;
import org.eclipse.om2m.ipe.sample.constants.ThermosConstants;
import org.eclipse.om2m.ipe.sample.gui.GUI;
import org.eclipse.om2m.ipe.sample.model.*;
import org.eclipse.om2m.ipe.sample.util.ObixUtil;


public class LifeCycleManager {

	private static Log LOGGER = LogFactory.getLog(LifeCycleManager.class); 

	/**
	 * Handle the start of the plugin with the resource representation and the GUI
	 */
	public static void start(){
		Map<String, Connected> connected = new HashMap<String, Connected>();
		
		String radiatorId = Radiator.TYPE+"_"+1;
		connected.put(radiatorId, new Radiator(radiatorId));
		createConnectedResources(radiatorId, ConnectedState.Off, ThermosConstants.POA);
		
		String windowId = Window.TYPE+"_"+1;
		connected.put(windowId, new Window(windowId));
		createConnectedResources(windowId, ConnectedState.Closed, ThermosConstants.POA);
		
		String thermometerIntId = Thermometer.TYPE+"_Int";
		connected.put(thermometerIntId, new Thermometer(thermometerIntId, ConnectedState.Inside));
		createConnectedResources(thermometerIntId, ConnectedState.Inside, ThermosConstants.POA);
		
		String thermometerExtId = Thermometer.TYPE+"_Ext";
		connected.put(thermometerExtId, new Thermometer(thermometerExtId, ConnectedState.Outside));
		createConnectedResources(thermometerExtId, ConnectedState.Outside, ThermosConstants.POA);
		
		ThermosModel.setModel(connected);
		ThermosModel.setStatesystem(ConnectedState.Activated);
		ThermosModel.setProfiluser(ConnectedState.Eco);

		createConnectedAll(ThermosConstants.POA);			

		// Start the GUI
		if(ThermosConstants.GUI){
			GUI.init();
		}
	}

	/**
	 * Stop the GUI if it is present
	 */
	public static void stop(){
		if(ThermosConstants.GUI){
			GUI.stop();
		}
	}

	/**
	 * Creates all required resources.
	 * @param appId - Application ID
	 * @param initValue - initial lamp value
	 * @param poa - lamp Point of Access
	 */
	private static void createConnectedResources(String appId, ConnectedState initValue, String poa) {
		// Create the Application resource
		Container container = new Container();
		container.getLabels().add("connected");
		container.setMaxNrOfInstances(BigInteger.valueOf(0));

		AE ae = new AE();
		ae.setRequestReachability(true);
		ae.getPointOfAccess().add(poa);
		ae.setAppID(appId);

		ResponsePrimitive response = RequestSender.createAE(ae, appId);
		// Create Application sub-resources only if application not yet created
		if(response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			container = new Container();
			container.setMaxNrOfInstances(BigInteger.valueOf(10));
			// Create DESCRIPTOR container sub-resource
			LOGGER.info(RequestSender.createContainer(response.getLocation(), ThermosConstants.DESC, container));
			// Create STATE container sub-resource
			LOGGER.info(RequestSender.createContainer(response.getLocation(), ThermosConstants.DATA, container));

			String content;
			// Create DESCRIPTION contentInstance on the DESCRIPTOR container resource
			content = ObixUtil.getDescriptorRep(ThermosConstants.CSE_ID, appId, ThermosConstants.DATA);
			ContentInstance contentInstance = new ContentInstance();
			contentInstance.setContent(content);
			contentInstance.setContentInfo(MimeMediaType.OBIX);
			RequestSender.createContentInstance(
					ThermosConstants.CSE_PREFIX + "/" + appId + "/" + ThermosConstants.DESC, null, contentInstance);

			// Create initial contentInstance on the STATE container resource
			content = ObixUtil.getStateRep(appId, initValue);
			contentInstance.setContent(content);
			RequestSender.createContentInstance(
					ThermosConstants.CSE_PREFIX + "/" + appId + "/" + ThermosConstants.DATA, null, contentInstance);
		}
	}


	private static void createConnectedAll(String poa) {
		// Creation of the LAMP_ALL container
		AE ae = new AE();
		ae.setRequestReachability(true);
		ae.getPointOfAccess().add(poa);
		ae.setAppID("CONNECTED_ALL");
		ResponsePrimitive response = RequestSender.createAE(ae, "CONNECTED_ALL");

		// Create descriptor container if not yet created
		if(response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)){
			// Creation of the DESCRIPTOR container
			Container cnt = new Container();
			cnt.setMaxNrOfInstances(BigInteger.valueOf(10));
			RequestSender.createContainer(ThermosConstants.CSE_PREFIX + "/" + "CONNECTED_ALL", ThermosConstants.DESC, cnt);

			// Create the description
			ContentInstance cin = new ContentInstance();
			cin.setContent(ObixUtil.createConnectedAllDescriptor());
			cin.setContentInfo(MimeMediaType.OBIX);
			RequestSender.createContentInstance(ThermosConstants.CSE_PREFIX + "/" + "CONNECTED_ALL" + "/" + ThermosConstants.DESC, null, cin);
		}
	}

}
