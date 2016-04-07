package org.eclipse.om2m.thermos.ipe.model;


public class Radiator extends Connected {
	
	public final static String LOCATION = "Home";
	public final static String TYPE = "Radiator";

	public Radiator(String ConnectedId) {
		super(ConnectedId);
		this.connectedState = ConnectedState.Off;
	}
	
	@Override
	public String getType() {
		return TYPE;
	}
}
