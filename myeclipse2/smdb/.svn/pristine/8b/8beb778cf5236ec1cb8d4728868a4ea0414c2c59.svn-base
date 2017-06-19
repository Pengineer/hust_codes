package csdc.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import csdc.bean.InterfaceConfig;
import csdc.service.IInterfaceService;


@SuppressWarnings("unchecked")
public class InterfaceService extends BaseService implements IInterfaceService{

	public Map getOptionsByName(String serviceName, String methodName) {		
		Map map = new HashMap();
		map.put("serviceName",serviceName);
		map.put("methodName", methodName);
		List<InterfaceConfig> list = dao.query("from InterfaceConfig ic where ic.serviceName = :serviceName and ic.methodName = :methodName" , map);
		if(list.isEmpty()) {
			return null;	
		} else {
			Map options = new HashMap();
			JSONObject mapObj = new JSONObject();
			options = mapObj.fromObject(list.get(0).getOptions());
			return options;
		}
	}

	public int getIsPublished(String serviceName, String methodName) {
		Map map = new HashMap();
		map.put("serviceName",serviceName);
		map.put("methodName", methodName);
		List<InterfaceConfig> list = dao.query("from InterfaceConfig ic where ic.serviceName = :serviceName and ic.methodName = :methodName" , map);
		if(!list.isEmpty()) {
			return list.get(0).getIsPublished();
		} else {
			return 0;
		}	
	}
	
	public InterfaceConfig getInterfaceConfig(String serviceName, String methodName) {
		InterfaceConfig interfaceConfig;
		Map map = new HashMap();
		map.put("serviceName",serviceName);
		map.put("methodName", methodName);
		List<InterfaceConfig> list = dao.query("from InterfaceConfig ic where ic.serviceName = :serviceName and ic.methodName = :methodName" , map);
		if(!list.isEmpty()) {
			interfaceConfig = list.get(0);
		} else {
			interfaceConfig = new InterfaceConfig();
		}
		return interfaceConfig;
	}
}
