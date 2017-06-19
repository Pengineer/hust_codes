package csdc.service;

import java.util.List;

import csdc.bean.FundList;
import csdc.tool.bean.LoginInfo;

public interface IFundListService extends IBaseService  {
	/**
	 * 添加清单实体
	 * @param listName清单名称
	 * @param note清单备注
	 * @param attn清单经办人
	 * @param projectType项目类型
	 * @param fundType清单类型
	 * @param rate清单比率
	 * @param projectYear项目年度
	 * @return 清单实体
	 */
	public String add(String listName,String note,String attn,String projectType,String fundType,double rate,int projectYear);
	
	/**
	 * 修改清单实体
	 * @param listName清单名称
	 * @param note清单备注
	 * @param rate清单比率
	 * @param id清单id
	 * @return 清单实体
	 */
	public FundList modify(String listName,String note,double rate,String id);

	/**
	 * 根据清单id取得清单实体
	 * @param id清单id
	 * @return 清单实体
	 */
	public FundList getFundList(String id);

	/**
	 * 根据清单id取得按学校查看的列表数据
	 * @param id清单id
	 * @return 处理后的列表信息
	 */
	public List getUnitFundList(String id);

	/**
	 * 删除拨款清单
	 * @param id清单id
	 */
	public void deleteFundList(String id);

	/**
	 * 按学校添加拨款通知邮件
	 * @param id清单id,
	 * @param loginer登陆者信息,
	 */
	public void addEmailToUnit(String id, LoginInfo loginer);


}