package org.eclipse.om2m.ipe.sample.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.om2m.commons.exceptions.BadRequestException;
import org.eclipse.om2m.ipe.sample.constants.ThermosConstants;


public class ThermosModel {

	//Paramètres liés au statut du système de régulation de température
	private static int tempConsigne = 27;
	private static boolean stateSystem = true;
	private static ConnectedState profilUser= ConnectedState.Eco;
	
	private static Map<String,Connected> CONNECTED = new HashMap<String, Connected>();
	private static List<ConnectedObserver> OBSERVERS = new ArrayList<ConnectedObserver>();
	
	public ThermosModel() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param ConnectedId 
	 * @param newTemp Nouvelle temperature à setter
	 * 
	 * get l'object par son ID, verifie que l'objet en question est bien un thermometre
	 * puis set une nouvelle temperature
	 */
	public static void modifyTemperature(final String ConnectedId,int newTemp){
		checkConnectedIdValue(ConnectedId);
		if(CONNECTED.get(ConnectedId) instanceof Thermometer){
			((Thermometer)CONNECTED.get(ConnectedId)).setTemperature(newTemp);
		}
	}
	
	public static int getTemperatureInterne(){
		return ((Thermometer)CONNECTED.get(ThermosConstants.THERMOMETER_INT)).getTemperature();
	}
	
	public static int getTemperatureExterne(){
		return ((Thermometer)CONNECTED.get(ThermosConstants.THERMOMETER_EXT)).getTemperature();
	}

	/*
	 * Change l'état de l'objet connecté 
	 * @param connectedId Id de la ressource
	 * @param state nouvelle état voulu
	 */
	public static void setConnectedState(final String connectedId, ConnectedState state) {
		checkConnectedIdValue(connectedId);
		CONNECTED.get(connectedId).setState(state);

		//Condition � changer :
		if(connectedId.contains("Radiator")) notifyObservers(connectedId, state);									
	}
	
	public static double getCoefUser(){
		if(profilUser.equals(ConnectedState.Eco))
			return (1/3);
		else 
			return (2/3);
	}
	
	public static double getIntervalleTolerance(){
		if(profilUser.equals(ConnectedState.Eco))
			return 1;
		else 
			return 0.5;
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

	/**
	 * @return the tempconsigne
	 */
	public static int getTempconsigne() {
		return tempConsigne;
	}

	/**
	 * @return the statesystem
	 */
	public static boolean isSystemActivate() {
		return stateSystem;
	}

	/**
	 * @return the profiluser
	 */
	public static ConnectedState getProfiluser() {
		return profilUser;
	}

	/**
	 * @param tempconsigne the tempconsigne to set
	 */
	public static void setTempconsigne(int tempconsigne) {
		tempConsigne = tempconsigne;
	}

	/**
	 * @param statesystem the statesystem to set
	 */
	public static void setStatesystem(boolean statesystem) {
		stateSystem = statesystem;
	}

	/**
	 * @param profiluser the profiluser to set
	 */
	public static void setProfiluser(ConnectedState profiluser) {
		profilUser = profiluser;
	}
	
	/**
	 * @return indique si la fen�tre est ouverte
	 */
	public static boolean isWindowOpen(){
		if((getConnectedState(ThermosConstants.WINDOW_1).equals(ConnectedState.Open))) 
			return true;
		else
			return false;
	}
}
