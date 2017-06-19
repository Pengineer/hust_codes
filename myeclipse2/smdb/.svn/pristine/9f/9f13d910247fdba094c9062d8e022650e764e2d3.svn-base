package csdc.service;

import java.util.List;

import csdc.bean.FundList;
import csdc.bean.ProjectFunding;

public interface IProjectFundService extends IBaseService  {
	/**
	 * 根据项目年度、项目子类、项目类型和清单类型取得项目拨款的id集合
	 * @param projectYear项目年度
	 * @param projectSubtype项目子类
	 * @param projectType项目类型
	 * @param fundType清单类型
	 * @return 项目拨款的id集合
	 */
	public List getProjectFundsByYearAndSubType(int projectYear,String projectSubtype,String projectType,String fundType);
	
	/**
	 * 添加或修改清单的时候根据清单及其包含的项目拨款id集合对清单实体和项目拨款进行更新
	 * @param fundList清单实体
	 * @param pfList项目拨款的id集合
	 * @param i：0表示添加；1表示修改
	 * @return 拨款清单实体对象
	 */
	public FundList modifyByFundList(FundList fundList, List pfList, int i);
	
	/**
	 * 根据清单取得其包含的项目拨款对象
	 * @param fundList清单实体
	 * @return 项目拨款对象集合
	 */
	public List<ProjectFunding> getProjectFundsByFundList(FundList fundList);
	
	/**
	 * 修改某条拨款记录的金额
	 * @param fee金额
	 * @param fundId拨款id
	 * @param fundType清单类型
	 * @return 拨款清单实体对象
	 */
	public FundList modifyFee(double fee, String fundId, String fundType);
	
	/**
	 * 删除清单
	 * @param fundList清单实体对象
	 * @param entityIds清单实体id集合
	 * @return 拨款清单实体对象
	 */
	public FundList fundListDelete(FundList fundList, List<String> entityIds);
	
	/**
	 * 拨款时对清单更新
	 * @param fundList清单实体对象
	 * @param attn拨款经办人
	 * @return 拨款清单实体对象
	 */
	public FundList modifyForAudit(FundList fundList, String attn);
	
}