package csdc.service.webService.client;

import java.util.Map;

import csdc.service.IBaseService;

//业务逻辑接口
public interface IClientWebService extends IBaseService {


	/**
	 * 往年数据同步2014,2013 ··· 2008等
	 * @param serviceName
	 * @param methodName
	 * @throws Exception
	 */
	public void fixProjectDataSynchronization(String serviceName, String methodName, String year) throws Exception;
	
	/**
	 * 新数据同步，2015年（含2015）以后的数据调用
	 * @param serviceName
	 * @param methodName
	 * @param projectType
	 * @param year
	 * @param fetchSize
	 */
	public void projectDataSynchronizationOnline(String serviceName, String methodName, String projectType, String year, int fetchSize) ;
	
	//测试调用
	public void fixContentDataMethod(String serviceName, String methodName);
	
	public Map currentTaskStatusUpdating(int runOrderStatus);
	

}
