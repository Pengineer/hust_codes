package csdc.service;

import java.util.List;
import java.util.Map;

import csdc.bean.InBox;
import csdc.bean.Message;

/**
 * 站内信的Service
 * @author yangfq
 */
public interface IInBoxService extends IBaseService {
	

	/**
	 * 根据账号类型查找用户名
	 * @param C_ACCOUNT_TYPE_S
	 * @return name
	 */
	public Map QueryNameForType(String recName);

}
