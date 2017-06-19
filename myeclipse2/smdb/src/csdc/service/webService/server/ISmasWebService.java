package csdc.service.webService.server;

import java.io.IOException;

import csdc.bean.Account;


public interface ISmasWebService extends IBaseWebService{

	/**
	 * SMAS同步SMDB重点人信息
	 * @param account
	 * @param requestAccountBelong
	 * @return
	 * @throws IOException 
	 */
	public String addKeyPerson(Account account,String requestAccountBelong) throws IOException;
}
