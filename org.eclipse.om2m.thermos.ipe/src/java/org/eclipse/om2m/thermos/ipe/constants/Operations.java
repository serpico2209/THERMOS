package org.eclipse.om2m.thermos.ipe.constants;

import org.eclipse.om2m.commons.exceptions.BadRequestException;

public enum Operations {
	
	GET_STATE("getState"),
	GET_STATE_DIRECT("getStateDirect"),
	SET_STRONG("setStrong"),
	SET_LOW("setLow"),
	SET_OFF("setOff"),
	OPEN("setOpen"),
	CLOSE("setClosed"),
	SET_INSIDE("setInside"),
	SET_OUTSIDE("setOutside"),
	ACTIVATE("setActivated"),
	DISABLE("setDisabled");
	
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
	
	public static Operations getOperationFromString(String operation){
		for(Operations op : values()){
			if(op.getValue().equals(operation)){
				return op;
			}
		}
		throw new BadRequestException("Unknown Operation");
	}
}
