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
package org.eclipse.om2m.ipe.sample.constants;

import org.eclipse.om2m.commons.exceptions.BadRequestException;
<<<<<<< HEAD

=======
/**
 * Represent a operation 
 *
 */
>>>>>>> master
public enum Operations {
	
	GET_STATE("getState"),
	GET_STATE_DIRECT("getStateDirect"),
<<<<<<< HEAD
	SET_STRONG("setStrong"),
	SET_LOW("setLow"),
	SET_OFF("setOff"),
	OPEN("setOpen"),
	CLOSE("setClosed"),
	SET_INSIDE("setInside"),
	SET_OUTSIDE("setOutside"),
	ACTIVATE("setActivated"),
	DISABLE("setDisabled");
	
=======
	SET_ON("setOn"),
	SET_OFF("setOff"),
	TOGGLE("toggle"),
	ALL_ON("allOn"),
	ALL_OFF("allOff"),
	ALL_TOGGLE("allToggle");
>>>>>>> master
	private final String value;
	
	private Operations(final String value){
		this.value = value;
	}
	
	public String toString() {
		return value;
	}
	
	public String getValue(){
		return value;
	}
	
<<<<<<< HEAD
=======
	/**
	 * Return the operation from the string
	 * @param operation
	 * @return
	 */
>>>>>>> master
	public static Operations getOperationFromString(String operation){
		for(Operations op : values()){
			if(op.getValue().equals(operation)){
				return op;
			}
		}
<<<<<<< HEAD
		throw new BadRequestException("Unknown Operation");
	}
}
=======
		throw new BadRequestException("Unknow Operation");
	}
}
>>>>>>> master
