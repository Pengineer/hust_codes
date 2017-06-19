package csdc.service;

import java.util.List;
import java.util.Map;

import csdc.bean.Account;

/**
 * 辅助信息平台的Service
 * @author yangfq
 *
 */
public interface IAuxiliaryService extends IBaseService {
	
	/**
	 * 根据entityId和人员类型查找对应的账号
	 * @param entityId
	 * @param type 1:管理人员；2：外部专家；3：教师；4：学生
	 * @return Account
	 */
	public Account getAccountByBelongId(String entityId, int type);
	
	/**
	 * 根据人员类型和entityId得到PersonId
	 * @param entityId
	 * @param type 1:管理人员；2：外部专家；3：教师；4：学生
	 */
	public String getPersonId(String entityId, int type);
	
	/**
	 * 根据前台的personType转换成对应的人员类型
	 * @param personType
	 */
	public String getPersonType(int personType);
	
	/**
	 * 统计研究人员参与项目的立项、中检、结项的情况
	 * @param personId 研究人员的ID
	 * @param director 0：参与的全部项目；1：作为负责人的项目
	 * @return Map 申报、立项、中检、结项的情况
	 */
	public Map projectStatistic(String personId, int director);
	
	/**
	 * 查询科研人员的奖励情况
	 * @param personId 研究人员的ID
	 */
	public List getAward(String personId);
	
	/**
	 * 查询科研人员的成果情况
	 * @param personId 研究人员的ID
	 */
	
	public Map getProduct(String personId);
	
	/**
	 * 组装查询的数据类型
	 * @param query_data 查询的数据类型
	 */
	public String getFormatedData(List<Integer> query_data);
	
	/**
	 * 计算比例（率）
	 * @param dividend	被除数
	 * @param divider	除数
	 * @return	百分数（保留两位小数）
	 */
	public Object calculate(Object dividend, Object divider);
	
	/**
	 * 处理list的数据
	 * @param listData 处理之前的LIST
	 * @param List laData 处理之后的LIST
	 */
	public void listDealWith(List listData,List laData);

}
