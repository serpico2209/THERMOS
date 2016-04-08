package org.eclipse.om2m.ipe.sample;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.exceptions.BadRequestException;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.interworking.service.InterworkingService;
import org.eclipse.om2m.ipe.sample.constants.Operations;
import org.eclipse.om2m.ipe.sample.constants.ThermosConstants;
import org.eclipse.om2m.ipe.sample.controller.ThermosController;
import org.eclipse.om2m.ipe.sample.model.ConnectedState;

public class ThermosRouter implements InterworkingService{ 
	private static Log LOGGER = LogFactory.getLog(ThermosRouter.class);

	@Override
	public ResponsePrimitive doExecute(RequestPrimitive request) {
		ResponsePrimitive response = new ResponsePrimitive(request);
		if(request.getQueryStrings().containsKey("op")){
			String operation = request.getQueryStrings().get("op").get(0);
			Operations op = Operations.getOperationFromString(operation);
			String connectedId= null;
			if(request.getQueryStrings().containsKey("connectedid")){
				connectedId = request.getQueryStrings().get("connectedid").get(0);
			}
			LOGGER.info("Received request in Sample IPE: op=" + operation + " ; connectedid=" + connectedId);
			switch(op){
			case SET_STRONG:
				ThermosController.setConnectedState(connectedId, ConnectedState.Strong);
				response.setResponseStatusCode(ResponseStatusCode.OK);
				break;
			case SET_OFF:
				ThermosController.setConnectedState(connectedId, ConnectedState.Off);
				response.setResponseStatusCode(ResponseStatusCode.OK);
				break;
			case SET_LOW:
				ThermosController.setConnectedState(connectedId, ConnectedState.Low);
				response.setResponseStatusCode(ResponseStatusCode.OK);
				break;
			case OPEN:
				ThermosController.setConnectedState(connectedId, ConnectedState.Open);
				response.setResponseStatusCode(ResponseStatusCode.OK);
				break;
			case CLOSE:
				ThermosController.setConnectedState(connectedId, ConnectedState.Closed);
				response.setResponseStatusCode(ResponseStatusCode.OK);
				break;
			case SET_INSIDE:
				ThermosController.setConnectedState(connectedId, ConnectedState.Inside);
            	response.setResponseStatusCode(ResponseStatusCode.OK);
            	break;
			case SET_OUTSIDE:
				ThermosController.setConnectedState(connectedId, ConnectedState.Outside);
            	response.setResponseStatusCode(ResponseStatusCode.OK);
            	break;
			case ACTIVATE:
				ThermosController.toggleProfilUser(ConnectedState.Activated);
            	response.setResponseStatusCode(ResponseStatusCode.OK);
            	break;
			case DISABLE:
				ThermosController.toggleProfilUser(ConnectedState.Disabled);
            	response.setResponseStatusCode(ResponseStatusCode.OK);
            	break;
			case GET_STATE:
				// Shall not get there...
				throw new BadRequestException();
			case GET_STATE_DIRECT:
				String content = ThermosController.getFormatedLampState(connectedId);
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
