package csdc.service;

import java.util.Map;

import csdc.bean.InterfaceConfig;

public interface IInterfaceService extends IBaseService{
	
	public Map getOptionsByName(String serviceName,String methodName);
	public int getIsPublished(String serviceName, String methodName);
	public InterfaceConfig getInterfaceConfig(String serviceName, String methodName);
}
