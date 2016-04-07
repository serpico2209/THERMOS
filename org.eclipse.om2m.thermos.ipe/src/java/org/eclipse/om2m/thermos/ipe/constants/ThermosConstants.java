package org.eclipse.om2m.thermos.ipe.constants;

import org.eclipse.om2m.commons.constants.Constants;

public class ThermosConstants {
	
	private ThermosConstants(){}
	
	public static final String POA = "Thermos";
	public static final String DATA = "DATA";
	public static final String DESC = "DESCRIPTOR";
	public static final String AE_NAME = "AE_IPE_THERMOS";
	public static final String RADIATOR = "RADIATOR";
	public static final String WINDOW = "WINDOW";
	public static final String THERMOMETER = "THERMOMETER";
	public static final String QUERY_STRING_OP = "op";
	public static final String QUERY_STRING_RADIATOR_ID = "radiatorid";
	public static final String QUERY_STRING_WINDOW_ID = "windowid";
	public static final String QUERY_STRING_THERMOMETER_ID = "thermometerid";
	public static final boolean GUI = Boolean.valueOf(System.getProperty("org.eclipse.om2m.ipe.thermos.gui", "true"));
	
	public static String CSE_ID = "/" + Constants.CSE_ID;
	public static String CSE_PREFIX = CSE_ID + "/" + Constants.CSE_NAME;
}
