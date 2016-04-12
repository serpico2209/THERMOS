package org.eclipse.om2m.thermos.ipe.model;

public class Thermometer extends Connected{
	
	public final static String LOCATION = "Home";
    public final static String TYPE = "Thermometer";
    private int temperature;
    
    public Thermometer(String ConnectedId, ConnectedState state){
    	super(ConnectedId);
    	this.connectedState = state;
    	this.temperature = 0;
    }

	public int getTemperature() {
		return temperature;
	}
	
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	@Override
	public String getType() {
		return TYPE;
	}
}
