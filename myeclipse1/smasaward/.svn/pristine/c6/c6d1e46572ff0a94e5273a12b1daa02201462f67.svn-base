package csdc.tool.webService.smdb.client;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;


public class ClientHandlerResolver implements HandlerResolver {
	@SuppressWarnings("unchecked")  
	public List<Handler> getHandlerChain(PortInfo portInfo) {
		List<Handler> hchain = new ArrayList<Handler>();
		hchain.add(new AuthClientHandler());
		hchain.add(new ClientVSignHandler());
		return hchain;
	}
}
