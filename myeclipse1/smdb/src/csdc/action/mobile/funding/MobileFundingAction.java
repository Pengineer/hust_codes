package csdc.action.mobile.funding;

import java.util.HashMap;

import csdc.bean.FundingList;
import csdc.tool.bean.AccountType;


/**
 * mobile经费概况模块
 * @author wangming
 *
 */
public class MobileFundingAction extends MobileFundingBaseAction{
	
	private static final long serialVersionUID = 1L;
	private static final String PAGE_NAME = "mobileFundingPage";
	
	private String fundingListId;//拨款清单id
	private String agencyId;//拨款单位id(可以传空，传空显示所有单位合并起来的拨款概况)

	/**
	 * 拨款概况列表
	 * @return
	 */
	public String simpleSearch(){
		FundingList fundingList = dao.query(FundingList.class, fundingListId);

		StringBuffer hql = new StringBuffer();
		HashMap map = new HashMap();
		map.put("fundingListId", fundingListId);
		if(loginer.getCurrentType().within(AccountType.MINISTRY_UNIVERSITY, AccountType.LOCAL_UNIVERSITY)){//高校管理人员
			String agencyId = loginer.getAccount().getAgency().getId();
			map.put("agencyId", agencyId);
			if (fundingList.getType().contains("work")) {
				hql.append("select fd.id, fd.name, fd.payee, ag.name, fd.fee, fd.note from WorkFunding fd left join fd.fundingList fl left join fd.agency ag where ag.id =:agencyId and fl.id =:fundingListId");
			}
			if (fundingList.getType().contains("project")) {
				hql.append("select fd.id, pg.name, fd.payee, ag.name, fd.fee, fd.note from ProjectFunding fd left join fd.fundingList fl left join fd.agency ag , ProjectGranted pg where pg.id= fd.grantedId and ag.id =:agencyId and ag.id =:agencyId and fl.id =:fundingListId");
			}
		} else {
			if (agencyId!=null&&!agencyId.equals("")) {
				map.put("agencyId", agencyId);
				if (fundingList.getType().contains("work")) {
					hql.append("select fd.id, fd.name, fd.payee, ag.name, fd.fee, fd.note from WorkFunding fd left join fd.fundingList fl left join fd.agency ag where ag.id =:agencyId and fl.id =:fundingListId");
				}
				if (fundingList.getType().contains("project")) {
					hql.append("select fd.id, pg.name, fd.payee, ag.name, fd.fee, fd.note from ProjectFunding fd left join fd.fundingList fl left join fd.agency ag, ProjectGranted pg where pg.id= fd.grantedId and ag.id =:agencyId and fl.id =:fundingListId");
				}
			} else {
				if (fundingList.getType().contains("work")) {
					hql.append("select fd.id, fd.name, fd.payee, ag.name, fd.fee, fd.note from WorkFunding fd left join fd.fundingList fl left join fd.agency ag where fl.id =:fundingListId");
				}
				if (fundingList.getType().contains("project")) {
					hql.append("select fd.id, pg.name, fd.payee, ag.name, fd.fee, fd.note from ProjectFunding fd left join fd.fundingList fl left join fd.agency ag, ProjectGranted pg where pg.id= fd.grantedId and fl.id =:fundingListId");
				}
			}
		}
		keyword = (keyword == null)? "" : keyword.toLowerCase();
		if (!keyword.equals("")) {
			map.put("keyword", "%"+keyword+"%");
			if (fundingList.getType().contains("work")) {
				hql.append(" and (fd.name like :keyword or fd.payee like :keyword or ag.name like :keyword or fd.note like :keyword or to_char(fd.fee) like :keyword) ");			
			}else if (fundingList.getType().contains("project")) {
				hql.append(" and (pg.name like :keyword or fd.payee like :keyword or ag.name like :keyword or fd.note like :keyword or to_char(fd.fee) like :keyword) ");			
			}
		}		
		search(hql, map);
		return SUCCESS;
	}

	@Override
	public String pageName() {
		return PAGE_NAME;
	}

	public String getFundingListId() {
		return fundingListId;
	}

	public void setFundingListId(String fundingListId) {
		this.fundingListId = fundingListId;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}
	
}
