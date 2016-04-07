package org.eclipse.om2m.thermos.ipe.model;

public abstract class Connected {

	public final static String LOCATION = "Home";
	public final static String TYPE = "CONNECTED";
	protected String connectedId;
	protected ConnectedState connectedState = null;
	
	public Connected(String ConnectedId) {
	   	this.connectedId = ConnectedId;
	}
	
	public String getId() {
		return connectedId;
	}

	public void setId(String ConnectedId) {
		this.connectedId = ConnectedId;
	}

	public ConnectedState getState() {
		return connectedState;
	}
	
	public void setState(ConnectedState connectedState) {
		this.connectedState = connectedState;
	}
	
	public abstract String getType();
}
