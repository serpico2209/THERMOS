package org.eclipse.om2m.thermos.ipe;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.exceptions.BadRequestException;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.interworking.service.InterworkingService;
import org.eclipse.om2m.thermos.ipe.constants.Operations;
import org.eclipse.om2m.thermos.ipe.constants.ThermosConstants;
import org.eclipse.om2m.thermos.ipe.controller.Controller;
import org.eclipse.om2m.thermos.ipe.model.ConnectedState;

public class ThermosRouter implements InterworkingService{

	private static Log LOGGER = LogFactory.getLog(ThermosRouter.class);

	@Override
	public ResponsePrimitive doExecute(RequestPrimitive request) {
		ResponsePrimitive response = new ResponsePrimitive(request);
		if(request.getQueryStrings().containsKey("op")){
			String operation = request.getQueryStrings().get("op").get(0);
			Operations op = Operations.getOperationFromString(operation);
			String connectedid= null;
			if(request.getQueryStrings().containsKey("connectedid")){
				connectedid = request.getQueryStrings().get("connectedid").get(0);
			}
			LOGGER.info("Received request in Thermos IPE: op=" + operation + " ; connectedid=" + connectedid);
			switch(op){
			case SET_OFF:
				Controller.setConnectedState(connectedid, ConnectedState.Off);
				response.setResponseStatusCode(ResponseStatusCode.OK);
				break;
			case SET_LOW:
				Controller.setConnectedState(connectedid, ConnectedState.Low);
				response.setResponseStatusCode(ResponseStatusCode.OK);
				break;
			case SET_STRONG:
				Controller.setConnectedState(connectedid, ConnectedState.Strong);
				response.setResponseStatusCode(ResponseStatusCode.OK);
				break;
			case OPEN:
				Controller.setConnectedState(connectedid, ConnectedState.Open);
				response.setResponseStatusCode(ResponseStatusCode.OK);
				break;
			case CLOSE:
				Controller.setConnectedState(connectedid, ConnectedState.Closed);
				response.setResponseStatusCode(ResponseStatusCode.OK);
				break;
			case SET_INSIDE:
				Controller.setConnectedState(connectedid, ConnectedState.Inside);
            	response.setResponseStatusCode(ResponseStatusCode.OK);
            	break;
			case SET_OUTSIDE:
				Controller.setConnectedState(connectedid, ConnectedState.Outside);
            	response.setResponseStatusCode(ResponseStatusCode.OK);
            	break;
			case GET_STATE:
				// Shall not get there...
				throw new BadRequestException();
			case GET_STATE_DIRECT:
				String content = Controller.getFormatedConnectedState(connectedid);
				response.setContent(content);
				request.setReturnContentType(MimeMediaType.OBIX);
				response.setResponseStatusCode(ResponseStatusCode.OK);
				break;
			default:
				throw new BadRequestException();
			}
		}
		if(response.getResponseStatusCode() == null){
			response.setResponseStatusCode(ResponseStatusCode.BAD_REQUEST);
		}
		return response;
	}

	@Override
	public String getAPOCPath() {
		return ThermosConstants.POA;
	}
	
}
