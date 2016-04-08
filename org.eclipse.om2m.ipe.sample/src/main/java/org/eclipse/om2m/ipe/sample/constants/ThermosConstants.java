package org.eclipse.om2m.ipe.sample.constants;

import org.eclipse.om2m.commons.constants.Constants;

public class ThermosConstants {
	
	private ThermosConstants(){}
	
	public static final String POA = "Thermos";
	public static final String DATA = "DATA";
	public static final String DESC = "DESCRIPTOR";
	public static final String AE_NAME = "AE_IPE_THERMOS";
	public static final String RADIATOR_1 = "Radiator_1";
	public static final String THERMOMETER_INT = "Thermometer_Int";
	public static final String THERMOMETER_EXT = "Thermometer_Ext";
	public static final String WINDOW_1 = "Window_1";
	public static final String QUERY_STRING_OP = "op";
	public static final String QUERY_STRING_CONNECTED_ID = "connectedid";
	public static final boolean GUI = Boolean.valueOf(System.getProperty("org.eclipse.om2m.ipe.thermos.gui", "true"));
	
	public static String CSE_ID = "/" + Constants.CSE_ID;
	public static String CSE_PREFIX = CSE_ID + "/" + Constants.CSE_NAME;
}
