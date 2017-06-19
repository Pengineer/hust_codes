package csdc.service.webService.server;

import java.util.Map;

import org.dom4j.DocumentException;

import csdc.bean.Account;

public interface IBaseWebService {


	public String requestHandShake(String content, String usermark);
	public Map parseMethod(String content) throws DocumentException;
	public String responseContent(String type ,String info );
	public String getPeerByAccount(Account account);
}
