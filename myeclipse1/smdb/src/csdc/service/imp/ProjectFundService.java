package csdc.service.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csdc.bean.Agency;
import csdc.bean.FundingList;
import csdc.bean.ProjectFunding;
import csdc.bean.ProjectGranted;
import csdc.service.IProjectFundService;
import csdc.tool.DoubleTool;

public class ProjectFundService extends BaseService implements IProjectFundService{
	
	/**
	 * 根据项目年度、项目子类、项目类型和清单类型取得项目拨款的id集合
	 * @param projectYear项目年度
	 * @param projectSubtype项目子类
	 * @param projectType项目类型
	 * @param fundType清单类型
	 * @return 项目拨款的id集合
	 */
	public List getProjectFundsByYearAndSubType(int projectYear,String projectSubtype,String projectType,String fundType) {
		Map parMap = new HashMap();
		parMap.put("projectYear", projectYear);
		StringBuffer hql = new StringBuffer();
		if (projectType.equals("general")) {
			if (fundType.equals("granted")) {
				hql.append("select pf.id from ProjectFunding pf, GeneralGranted gra left outer join gra.application app "+ 
				"left outer join gra.subtype so where 1=1 and app.year =:projectYear and pf.status = 0 and pf.projectFundingList is null and pf.type = 1 " +
				"and app.finalAuditStatus=3 and app.finalAuditResult=2 and pf.grantedId=gra.id ");
			}
			else if (fundType.equals("mid")) {
				hql.append("select pf.id from ProjectFunding pf, GeneralMidinspection midi left join midi.granted gra left outer join gra.application app "+ 
						"left outer join gra.subtype so where 1=1 and app.year =:projectYear and pf.status = 0 and pf.projectFundingList is null and pf.type = 2 " +
						"and midi.finalAuditStatus=3 and midi.finalAuditResult=2 and pf.grantedId=gra.id ");
			}else if (fundType.equals("end")) {
				hql.append("select pf.id from ProjectFunding pf, GeneralEndinspection endi left join endi.granted gra left outer join gra.application app "+ 
						"left outer join gra.subtype so where 1=1 and app.year =:projectYear and pf.status = 0 and pf.projectFundingList is null and pf.type = 3 " +
						"and endi.finalAuditStatus=3 and endi.finalAuditResultEnd=2 and pf.grantedId=gra.id ");
			}
		}else if (projectType.equals("entrust")) {
			if (fundType.equals("granted")) {
				hql.append("select pf.id from ProjectFunding pf, EntrustGranted gra left outer join gra.application app "+ 
						"left outer join gra.subtype so where 1=1 and app.year =:projectYear and pf.status = 0 and pf.projectFundingList is null and pf.type = 1 " +
						"and app.finalAuditStatus=3 and app.finalAuditResult=2 and pf.grantedId=gra.id ");
			}else if (fundType.equals("end")) {
				hql.append("select pf.id from ProjectFunding pf, EntrustEndinspection endi left join endi.granted gra left outer join gra.application app "+ 
						"left outer join gra.subtype so where 1=1 and app.year =:projectYear and pf.status = 0 and pf.projectFundingList is null and pf.type = 3 " +
						"and endi.finalAuditStatus=3 and endi.finalAuditResultEnd=2 and pf.grantedId=gra.id ");
			}
		}else if (projectType.equals("instp")) {
			if (fundType.equals("granted")) {
				hql.append("select pf.id from ProjectFunding pf, InstpGranted gra left outer join gra.application app "+ 
				"left outer join gra.subtype so where 1=1 and app.year =:projectYear and pf.status = 0 and pf.projectFundingList is null and pf.type = 1 " +
				"and app.finalAuditStatus=3 and app.finalAuditResult=2 and pf.grantedId=gra.id ");
			}
			else if (fundType.equals("mid")) {
				hql.append("select pf.id from ProjectFunding pf, InstpMidinspection midi left join midi.granted gra left outer join gra.application app "+ 
						"left outer join gra.subtype so where 1=1 and app.year =:projectYear and pf.status = 0 and pf.projectFundingList is null and pf.type = 2 " +
						"and midi.finalAuditStatus=3 and midi.finalAuditResult=2 and pf.grantedId=gra.id ");
			}else if (fundType.equals("end")) {
				hql.append("select pf.id from ProjectFunding pf, InstpEndinspection endi left join endi.granted gra left outer join gra.application app "+ 
						"left outer join gra.subtype so where 1=1 and app.year =:projectYear and pf.status = 0 and pf.projectFundingList is null and pf.type = 3 " +
						"and endi.finalAuditStatus=3 and endi.finalAuditResultEnd=2 and pf.grantedId=gra.id ");
			}
		}else if (projectType.equals("key")) {
			if (fundType.equals("granted")) {
				hql.append("select pf.id from ProjectFunding pf, KeyGranted gra left outer join gra.application app "+ 
				"left outer join gra.subtype so where 1=1 and app.year =:projectYear and pf.status = 0 and pf.projectFundingList is null and pf.type = 1 " +
				"and app.finalAuditStatus=3 and app.finalAuditResult=2 and pf.grantedId=gra.id ");
			}
			else if (fundType.equals("mid")) {
				hql.append("select pf.id from ProjectFunding pf, KeyMidinspection midi left join midi.granted gra left outer join gra.application app "+ 
						"left outer join gra.subtype so where 1=1 and app.year =:projectYear and pf.status = 0 and pf.projectFundingList is null and pf.type = 2 " +
						"and midi.finalAuditStatus=3 and midi.finalAuditResult=2 and pf.grantedId=gra.id ");
			}else if (fundType.equals("end")) {
				hql.append("select pf.id from ProjectFunding pf, KeyEndinspection endi left join endi.granted gra left outer join gra.application app "+ 
						"left outer join gra.subtype so where 1=1 and app.year =:projectYear and pf.status = 0 and pf.projectFundingList is null and pf.type = 3 " +
						"and endi.finalAuditStatus=3 and endi.finalAuditResultEnd=2 and pf.grantedId=gra.id ");
			}
		}else if (projectType.equals("post")) {
			if (fundType.equals("granted")) {
				hql.append("select pf.id from ProjectFunding pf, PostGranted gra left outer join gra.application app "+ 
				"left outer join gra.subtype so where 1=1 and app.year =:projectYear and pf.status = 0 and pf.projectFundingList is null and pf.type = 1 " +
				"and app.finalAuditStatus=3 and app.finalAuditResult=2 and pf.grantedId=gra.id ");
			}else if (fundType.equals("end")) {
				hql.append("select pf.id from ProjectFunding pf, PostEndinspection endi left join endi.granted gra left outer join gra.application app "+ 
						"left outer join gra.subtype so where 1=1 and app.year =:projectYear and pf.status = 0 and pf.projectFundingList is null and pf.type = 3 " +
						"and endi.finalAuditStatus=3 and endi.finalAuditResultEnd=2 and pf.grantedId=gra.id ");
			}
		}
		if (!projectSubtype.equals("-1")) {
			parMap.put("projectSubtype", projectSubtype);
			hql.append(" and so.id =:projectSubtype ");
		}
		List pfList = dao.query(hql.toString(), parMap);
		return pfList;
	}
	
	/**
	 * 添加或修改清单的时候根据清单及其包含的项目拨款id集合对清单实体和项目拨款进行更新
	 * @param fundList清单实体
	 * @param pfList项目拨款的id集合
	 * @param i：0表示添加；1表示修改
	 * @return 拨款清单实体对象
	 */
	public FundingList modifyByFundList(FundingList fundList, List pfList, int flag) {
		for (Object pfId : pfList) {
			ProjectFunding projectFunding = dao.query(ProjectFunding.class, (String)pfId);
			ProjectGranted projectGranted = dao.query(ProjectGranted.class, projectFunding.getGrantedId());
			Agency agency = dao.query(Agency.class, projectGranted.getUniversity().getId());
			projectFunding.setAgency(agency);
			projectFunding.setAgencyName(agency.getName());
			projectFunding.setFundingList(fundList);
			if (projectGranted.getApproveFee()==null) {
				projectFunding.setFee(0.0);
			} else {
				projectFunding.setFee(DoubleTool.mul(projectGranted.getApproveFee(), DoubleTool.div(fundList.getRate(),100,4)));
			}
			dao.modify(projectFunding);
			fundList.setFee(DoubleTool.sum(fundList.getFee(), projectFunding.getFee()));
			if (flag == 0) {
				fundList.setCount((int) DoubleTool.sum(fundList.getCount(), 1));
			}
		}
		dao.modify(fundList);
		return fundList;
	}
	
	/**
	 * 根据清单取得其包含的项目拨款对象
	 * @param fundList清单实体
	 * @return 项目拨款对象集合
	 */
	
	public List getProjectFundsByFundList(FundingList fundList) {
		String hql = null;
		List<ProjectFunding> pfList = null;
		Map parMap = new HashMap();
		parMap.put("id", fundList.getId());
		if (fundList.getStatus() == 0) {
			if (fundList.getSubSubType().equals("granted")) {
				hql = "select pf.id from ProjectFunding pf where pf.projectFundingList.id = :id and pf.type = 1 ";
				pfList = dao.query(hql, parMap);
			}else if (fundList.getSubSubType().equals("mid")) {
				hql = "select pf.id from ProjectFunding pf where pf.projectFundingList.id = :id and pf.type = 2 ";
				pfList = dao.query(hql, parMap);
			}else if (fundList.getSubSubType().equals("end")) {
				hql = "select pf.id from ProjectFunding pf where pf.projectFundingList.id = :id and pf.type = 3 ";
				pfList = dao.query(hql, parMap);
			}
		}
		return pfList;
	}
	
	/**
	 * 修改某条拨款记录的金额
	 * @param fee金额
	 * @param fundId拨款id
	 * @param fundType清单类型
	 * @return 拨款清单实体对象
	 */
	public FundingList modifyFee(double fee,String fundId,String fundType) {
		ProjectFunding projectFunding = dao.query(ProjectFunding.class, fundId);
		FundingList fundList = null;
		Double difference = 0.0;
		fundList = dao.query(FundingList.class, projectFunding.getFundingList().getId());
		difference = DoubleTool.sub(fee, projectFunding.getFee());//改变的差额
		projectFunding.setFee(fee);
		dao.modify(projectFunding);
		fundList.setFee(DoubleTool.sum(fundList.getFee(), difference));
		dao.modify(fundList);
		return fundList;
	}
	
	/**
	 * 删除清单
	 * @param fundList清单实体对象
	 * @param entityIds清单实体id集合
	 * @return 拨款清单实体对象
	 */
	public FundingList fundListDelete(FundingList fundList,List<String> entityIds) {
		for(int i=0;i<entityIds.size();i++){
			ProjectFunding projectFunding = dao.query(ProjectFunding.class, entityIds.get(i));
			fundList.setFee(DoubleTool.sub(fundList.getFee(), projectFunding.getFee()));//减去删除的项目的金额
			fundList.setCount(fundList.getCount() - 1);//项目总数减1
			projectFunding.setFee((double) 0);
			projectFunding.setFundingList(null);
			dao.modify(projectFunding);
		}
		dao.modify(fundList);
		return fundList;
	}
	
	/**
	 * 拨款时对清单更新
	 * @param fundList清单实体对象
	 * @param attn拨款经办人
	 * @return 拨款清单实体对象
	 */
	public FundingList modifyForAudit(FundingList fundList,String attn) {
		String hql = null;
		Map parMap = new HashMap();
		parMap.put("id", fundList.getId());
		if (fundList.getStatus() == 0) {
			hql = "from ProjectFunding pf where pf.projectFundingList.id = :id ";
			List<ProjectFunding> projectFundings = dao.query(hql, parMap);
			for (ProjectFunding projectFunding : projectFundings) {
				projectFunding.setStatus(1);
				projectFunding.setDate(new Date());
				projectFunding.setAttn(attn);
				dao.modify(projectFunding);
			}
			fundList.setStatus(1);
			dao.modify(fundList);
		}
		return fundList;
	}
}