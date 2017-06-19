package csdc.action.funding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import csdc.action.BaseAction;
import csdc.bean.ProjectFunding;
import csdc.bean.WorkFunding;
import csdc.service.IFundingService;
import csdc.tool.bean.AccountType;

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class FundingBaseAction extends BaseAction{
	private static final long serialVersionUID = -6811628952522365091L;
	

	public String fundingListId;
	public String fundingId;
	public Double fee;
	
	public IFundingService fundingService;
	
	public abstract String getFundingType();

	public Object[] simpleSearchCondition() {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("fundingListId", fundingListId);
		if(loginer.getCurrentType().within(AccountType.MINISTRY_UNIVERSITY, AccountType.LOCAL_UNIVERSITY)){
			//高校管理人员
			String agencyId = loginer.getAccount().getAgency().getId();
			map.put("agencyId", agencyId);
			if (getFundingType().equals("workFunding")) {
				hql.append("select fd.id, fd.name, fd.payee, ag.name, fd.fee, fd.note, fd.type, 'workFundingFlag' from WorkFunding fd left join fd.fundingList fl left join fd.agency ag where ag.id =:agencyId and fl.id =:fundingListId");
			}
			if (getFundingType().equals("projectFunding")) {
				hql.append("select fd.id, pg.name, fd.payee, ag.name, fd.fee, fd.note, fd.type, fd.projectType from ProjectFunding fd left join fd.fundingList fl left join fd.agency ag , ProjectGranted pg where pg.id= fd.grantedId and ag.id =:agencyId and ag.id =:agencyId and fl.id =:fundingListId");
			}
		} else if(loginer.getCurrentType().within(AccountType.ADMINISTRATOR, AccountType.MINISTRY)){
			//系统管理人员、部级管理员
			if (getFundingType().equals("workFunding")) {
				hql.append("select fd.id, fd.name, fd.payee, ag.name, fd.fee, fd.note, fd.type, 'workFundingFlag' from WorkFunding fd left join fd.fundingList fl left join fd.agency ag where fl.id =:fundingListId");
			}
			if (getFundingType().equals("projectFunding")) {
				hql.append("select fd.id, pg.name, fd.payee, ag.name, fd.fee, fd.note, fd.type, fd.projectType from ProjectFunding fd left join fd.fundingList fl left join fd.agency ag, ProjectGranted pg where pg.id= fd.grantedId and fl.id =:fundingListId");
			}
		}
		keyword = (keyword == null)? "" : keyword.toLowerCase();
		if (!keyword.equals("")) {
			map.put("keyword", "%"+keyword+"%");
			if (getFundingType().equals("workFunding")) {
				if (searchType == 0) {
					hql.append(" and fd.name like :keyword");
				} else if (searchType == 1) {
					hql.append(" and fd.payee like :keyword ");
				} else if (searchType == 2) {
					hql.append(" and ag.name like :keyword");
				} else if (searchType == 3) {
					hql.append(" and fd.note like :keyword");
				} else {
					hql.append(" and (fd.name like :keyword or fd.payee like :keyword or ag.name like :keyword or fd.note like :keyword) ");			
				}
			}
			if (getFundingType().equals("projectFunding")) {
				if (searchType == 0) {
					hql.append(" and pg.name like :keyword");
				} else if (searchType == 1) {
					hql.append(" and fd.payee like :keyword ");
				} else if (searchType == 2) {
					hql.append(" and ag.name like :keyword");
				} else if (searchType == 3) {
					hql.append(" and fd.note like :keyword");
				} else {
					hql.append(" and (pg.name like :keyword or fd.payee like :keyword or ag.name like :keyword or fd.note like :keyword) ");			
				}
			}
		}
		return new Object[]{
			hql.toString(),
			map,
			1,
			null
		};
	}
	
	public Object[] advSearchCondition() {
		return null;
	}
	
	public void pageListDealWith() {
		laData = new ArrayList();// 处理之后的列表数据
		Object[] o;// 缓存查询结果的一行
		String[] item;// 缓存查询结果一行中的每一字段
		SimpleDateFormat dateformat = new SimpleDateFormat(dateFormat());// 时间格式化对象
		String datestr;// 格式化之后的时间字符串
		
		// 遍历初始查询列表数据，按照指定格式，格式化其中的时间字段，其它字段转化为字符串
		for (Object p : pageList) {
			o = (Object[]) p;
			item = new String[o.length];
			for (int i = 0; i < o.length-2; i++) {
				if (o[i] == null) {// 如果字段值为空，则以""替换
					item[i] = "";
				} else {// 如果字段值非空，则做进一步判断
					if (o[i] instanceof Date) {// 如果字段为时间对象，则按照子类定义的时间格式格式化
						datestr = dateformat.format((Date) o[i]);
						item[i] = datestr;
					} else {// 如果字段非时间对象，则直接转化为字符串
						item[i] = o[i].toString();
					}
				}
			}
			if (o[o.length-1].equals("workFundingFlag")) {
				item[o.length-1] = fundingService.convertFundingListSubType4work(o[o.length-2].toString());
				item[o.length-2] = "";
				jsonMap.put("fundingType", 0);//工作经费
			}else {
				item[o.length-2] = fundingService.convertProjectType(o[o.length-1].toString());
				item[o.length-1] = fundingService.convertFundingListSubSubType4project(o[o.length-2].toString());
				jsonMap.put("fundingType", 1);//项目经费
			}
			laData.add(item);// 将处理好的数据存入laData
		}
	}
	
	public String delete() {
		fundingService.deleteFunding(entityIds, getFundingType());
		return SUCCESS;
	}
	
	public String toModifyFee() {
		if (getFundingType().equals("workFunding")) {
			WorkFunding workFunding = dao.query(WorkFunding.class, fundingId);
			fee = workFunding.getFee();
		} else if (getFundingType().equals("projectFunding")) {
			ProjectFunding projectFunding = dao.query(ProjectFunding.class, fundingId);
			fee = projectFunding.getFee();
		}
		return SUCCESS;
	}
	
	public String modifyFee() {
		if (getFundingType().equals("workFunding")) {
			WorkFunding workFunding = dao.query(WorkFunding.class, fundingId);
			workFunding.setFee(fee);
			dao.modify(workFunding);
		} else if (getFundingType().equals("projectFunding")) {
			ProjectFunding projectFunding = dao.query(ProjectFunding.class, fundingId);
			projectFunding.setFee(fee);
			dao.modify(projectFunding);
		}
		return SUCCESS;
	}
	
	public String getFundingListId() {
		return fundingListId;
	}
	public void setFundingListId(String fundingListId) {
		this.fundingListId = fundingListId;
	}
	public String getFundingId() {
		return fundingId;
	}
	public void setFundingId(String fundingId) {
		this.fundingId = fundingId;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public IFundingService getFundingService() {
		return fundingService;
	}
	public void setFundingService(IFundingService fundingService) {
		this.fundingService = fundingService;
	}
	
}
