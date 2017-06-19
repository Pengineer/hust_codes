package csdc.service;

import java.util.List;
import java.util.Map;

import csdc.bean.Business;

/**
 * 业务管理接口
 * @author 肖雅
 * @version 2011.12.29
 */
public interface IBusinessService extends IBaseService {
	
	/**
	 * 判断业务类型是否存在
	 * @param businessType 业务名称
	 * @param businessId 业务Id
	 * @return true存在，false不存在
	 */
	public boolean checkBusinessType(String businessType, String businessId, int startYear, int endYear);
	
	/**
	 * 判断业务对象起止年份是否相等
	 * @param businessType 业务子类id
	 * @param businessId 业务Id
	 * @return true相等，false不相等
	 */
	public boolean isBusinessYearEqual(String businessSubType, int startYear, int endYear);
	
	/**
	 * 判断业务年份是否交叉
	 * @param businessType 业务名称
	 * @param businessId 业务Id
	 * @return true不交叉，false交叉
	 */
	public boolean isBusinessYearCrossed(String businessType, String businessId, int startYear, int endYear);
	
	/**
	 * 修改业务
	 * @param oldRight原始业务对象
	 * @param newRight更新业务对象
	 * @return 业务ID
	 */
	public String modifyBusiness(Business oldBusiness, Business newBusiness);
	
	/**
	 * 查询当年年份形成年份列表，起始年份为2000
	 * @return 年份列表
	 */
	public Map<Integer, Integer> getYearMap();
	
	/**
	 * 根据账号ID获得登录首页中，用于显示的业务管理列表
	 * @param accountId
	 * @return 当前账号涉及业务，包括业务名称，状态，起始时间，业务对象起止时间；
	 */
	@SuppressWarnings("rawtypes")
	public List getBusinessByAccount(String accountId);	

}
