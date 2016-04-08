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
package org.eclipse.om2m.ipe.sample.util;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.ShortName;
import org.eclipse.om2m.commons.obix.Bool;
import org.eclipse.om2m.commons.obix.Contract;
import org.eclipse.om2m.commons.obix.Obj;
import org.eclipse.om2m.commons.obix.Op;
import org.eclipse.om2m.commons.obix.Str;
import org.eclipse.om2m.commons.obix.Enum;
import org.eclipse.om2m.commons.obix.Uri;
import org.eclipse.om2m.commons.obix.io.ObixEncoder;
import org.eclipse.om2m.ipe.sample.constants.Operations;
import org.eclipse.om2m.ipe.sample.constants.ThermosConstants;
import org.eclipse.om2m.ipe.sample.model.Connected;
import org.eclipse.om2m.ipe.sample.model.ConnectedState;

public class ObixUtil {

	public static String getDescriptorRep(String cseId, String appId, String stateCont) {
		String prefix = cseId+"/"+ Constants.CSE_NAME + "/" + appId;
		// oBIX
		Obj obj = new Obj();
		obj.add(new Str("type",Connected.TYPE));
		obj.add(new Str("location",Connected.LOCATION));
		obj.add(new Str("appId",appId));
		// OP GetState from SCL DataBase
		Op opState = new Op();
		opState.setName("getState");
		opState.setHref(new Uri(prefix  +"/"+stateCont+"/"+ ShortName.LATEST));
		opState.setIs(new Contract("retrieve"));
		opState.setIn(new Contract("obix:Nil"));
		opState.setOut(new Contract("obix:Nil"));
		obj.add(opState);
		// OP GetState from SCL IPU
		Op opStateDirect = new Op();
		opStateDirect.setName("getState(Direct)");
		opStateDirect.setHref(new Uri(prefix + "?op="+ Operations.GET_STATE_DIRECT+"&connectedid=" + appId));
		opStateDirect.setIs(new Contract("execute"));
		opStateDirect.setIn(new Contract("obix:Nil"));
		opStateDirect.setOut(new Contract("obix:Nil"));
		obj.add(opStateDirect);
		
		Op opOFF = new Op();
		opOFF.setName("switchOFF");
		opOFF.setHref(new Uri(prefix + "?op="+ Operations.SET_OFF +"&connectedid=" + appId));
		opOFF.setIs(new Contract("execute"));
		opOFF.setIn(new Contract("obix:Nil"));
		opOFF.setOut(new Contract("obix:Nil"));
		obj.add(opOFF);
		
		Op opLow = new Op();
		opLow.setName("switchLOW");
		opLow.setHref(new Uri(prefix  + "?op=" + Operations.SET_LOW + "&connectedid=" + appId));
		opLow.setIs(new Contract("execute"));
		opLow.setIn(new Contract("obix:Nil"));
		opLow.setOut(new Contract("obix:Nil"));
		obj.add(opLow);
		
		Op opStrong = new Op();
		opStrong.setName("switchSTRONG");
		opStrong.setHref(new Uri(prefix + "?op="+ Operations.SET_STRONG +"&connectedid=" + appId));
		opStrong.setIs(new Contract("execute"));
		opStrong.setIn(new Contract("obix:Nil"));
		opStrong.setOut(new Contract("obix:Nil"));
		obj.add(opStrong);
		
		Op opClose = new Op();
		opStrong.setName("closeWindow");
		opStrong.setHref(new Uri(prefix + "?op="+ Operations.CLOSE +"&connectedid=" + appId));
		opStrong.setIs(new Contract("execute"));
		opStrong.setIn(new Contract("obix:Nil"));
		opStrong.setOut(new Contract("obix:Nil"));
		obj.add(opClose);
		
		Op opOpen = new Op();
		opStrong.setName("openWindow");
		opStrong.setHref(new Uri(prefix + "?op="+ Operations.OPEN +"&connectedid=" + appId));
		opStrong.setIs(new Contract("execute"));
		opStrong.setIn(new Contract("obix:Nil"));
		opStrong.setOut(new Contract("obix:Nil"));
		obj.add(opOpen);
		
		Op opInside = new Op();
		opStrong.setName("setInside");
		opStrong.setHref(new Uri(prefix + "?op="+ Operations.SET_INSIDE +"&connectedid=" + appId));
		opStrong.setIs(new Contract("execute"));
		opStrong.setIn(new Contract("obix:Nil"));
		opStrong.setOut(new Contract("obix:Nil"));
		obj.add(opInside);

		Op opOutside = new Op();
		opStrong.setName("setOutside");
		opStrong.setHref(new Uri(prefix + "?op="+ Operations.SET_OUTSIDE +"&connectedid=" + appId));
		opStrong.setIs(new Contract("execute"));
		opStrong.setIn(new Contract("obix:Nil"));
		opStrong.setOut(new Contract("obix:Nil"));
		obj.add(opOutside);
		
		return ObixEncoder.toString(obj);
	}
	
	public static String getStateRep(String connectedId, ConnectedState value) {
		// oBIX
		Obj obj = new Obj();
		obj.add(new Str("type",Connected.TYPE));
		obj.add(new Str("location",Connected.LOCATION));
		obj.add(new Str("connectedId",connectedId));
		obj.add(new Enum("state",value.toString()));
		return ObixEncoder.toString(obj);
	}

	public static String createConnectedAllDescriptor(){
		String prefix = ThermosConstants.CSE_ID +"/"+ Constants.CSE_NAME + "/" + "CONNECTED_ALL";
		Obj descriptor = new Obj();
		return ObixEncoder.toString(descriptor);

	}

}
