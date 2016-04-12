package org.eclipse.om2m.thermos.ipe.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.om2m.commons.exceptions.BadRequestException;

public class ThermosModel {

	private static Map<String,Connected> CONNECTED = new HashMap<String, Connected>();
	private static List<ConnectedObserver> OBSERVERS = new ArrayList<ConnectedObserver>();
	
	public ThermosModel() {
		// TODO Auto-generated constructor stub
	}

	public static void setConnectedState(final String ConnectedId, ConnectedState state) {
		checkConnectedIdValue(ConnectedId);
		CONNECTED.get(ConnectedId).setState(state);
		notifyObservers(ConnectedId, state);
	}
	
	public static ConnectedState getConnectedState(String ConnectedId) {
		checkConnectedIdValue(ConnectedId);
		return CONNECTED.get(ConnectedId).getState();
	}

	public static void checkConnectedIdValue(String ConnectedId){
		if(ConnectedId == null || !CONNECTED.containsKey(ConnectedId)){
			throw new BadRequestException("Identifiant inconnu");
		}
	}
	
	public static void addObserver(ConnectedObserver obs){
		if(!OBSERVERS.contains(obs)){
			OBSERVERS.add(obs);
		}
	}
	
	public static void deleteObserver(ConnectedObserver obs){
		if(OBSERVERS.contains(obs)){
			OBSERVERS.remove(obs);
		}
	}
	
	private static void notifyObservers(final String connectedId, final ConnectedState state){
		new Thread(){
			@Override
			public void run() {
				for(ConnectedObserver obs: OBSERVERS){
					obs.onConnectedStateChange(connectedId, state);
				}
			}
		}.start();
	}
	
	public static interface ConnectedObserver{
		void onConnectedStateChange(String ConnectedId, ConnectedState state);
	}

	public static void setModel(Map<String, Connected> connected) {
		CONNECTED = connected;
	}
}
