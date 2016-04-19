package org.eclipse.om2m.ipe.sample.model;

public class Window extends Connected{

	public final static String LOCATION = "Home";
    public final static String TYPE = "Window";
    
    public Window(String WindowId){
    	super(WindowId);
    	this.connectedState = ConnectedState.Open;
    }
    
    @Override
	public String getType() {
		return TYPE;
	}
}
