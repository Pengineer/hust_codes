package csdc.service.imp;

import java.util.HashMap;
import java.util.Map;

import csdc.service.IMobileService;
import csdc.tool.bean.LoginInfo;
import csdc.tool.bean.Mobile;

/**
 * mobile基本接口实现类
 * @author suwb
 *
 */
public class MobileService extends BaseService implements IMobileService {
	
	public Map getSimpleSearchHql(LoginInfo loginer, String keyword, Integer type){
		Map jsonMap = new HashMap();
		return jsonMap;
	};
	
	public Map getAdvSearchHql(LoginInfo loginer, Mobile mobile, Integer type){
		Map jsonMap = new HashMap();
		return jsonMap;
	};

}
