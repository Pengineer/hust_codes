package csdc.tool.webService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.springframework.transaction.annotation.Transactional;

import csdc.bean.ProjectApplication;
import csdc.bean.ProjectFee;
import csdc.bean.ProjectGranted;
import csdc.bean.ProjectVariation;
import csdc.bean.SmdbProjectApplication;
import csdc.bean.SmdbProjectGranted;
import csdc.bean.SmdbProjectVariation;
import csdc.dao.IHibernateBaseDao;
import csdc.service.IProjectService;

/**
 * 项目申请记录解析入库
 * 2015-1-12
 */
public class ProjectVariationRecordResolver extends BaseRecordResolver implements RecordResolver  {	
	private Element itemRoot;
	private SimpleDateFormat sdf; 
	public ProjectVariationRecordResolver() {};
	public ProjectVariationRecordResolver(Element item, SimpleDateFormat sdf, IHibernateBaseDao dao, List badItemIDs) {
		this.itemRoot = item;
		this.dao = dao;
		this.sdf = sdf;
		this.badItemsList = badItemIDs;
	}
	//方式二
	public void parse(Element item, SimpleDateFormat sdf,
			IHibernateBaseDao dao, String projectType, List<String> badIDList)
			throws ParseException {
		this.itemRoot = item;
		this.dao = dao;
		this.sdf = sdf;
		this.badItemsList = badIDList;
		//解析
		parse();
	}
	/**
	 * 项目变更
	 * @return
	 */
	@Transactional
	public void parse() throws ParseException {
		
		String smdbVariationID = getArgumentByTag(itemRoot, "id");
		String smdbGriantedID = getArgumentByTag(itemRoot, "grantedID");
		if (!smdbVariationID.equals("null") &&!smdbGriantedID.equals("null") && !smdbVariationID.equals("") && !smdbGriantedID.equals("")) {
			String smasVariationID = getProjectVariationInfos(smdbVariationID);
			//由于项目变更是一对多的关系，只能通过项目变更中间表中判断是否已经入库
			Map resultMap = getProjectGrantedInfos(smdbGriantedID);
			//判断没有入库中间表，且有对应立项信息
			if ((smasVariationID == null || smasVariationID.equals("")) && (resultMap != null)) {
				
				String file = getArgumentByTag(itemRoot, "file");
				String applicantSubmitDate= getArgumentByTag(itemRoot, "applicantSubmitDate");
				String variationReason = getArgumentByTag(itemRoot, "variationReason");
				String changeMember = getArgumentByTag(itemRoot, "changeMember");
				String oldMembers = getArgumentByTag(itemRoot, "oldMembers");
				String newMembers = getArgumentByTag(itemRoot, "newMembers");
				String changeAgency = getArgumentByTag(itemRoot, "changeAgency");
				String oldAgencyName = getArgumentByTag(itemRoot, "oldAgencyName");
				String newAgencyName = getArgumentByTag(itemRoot, "newAgencyName");
				String changeProductType = getArgumentByTag(itemRoot, "changeProductType");
				String oldProductType = getArgumentByTag(itemRoot, "oldProductType");
				String oldProductTypeOther = getArgumentByTag(itemRoot, "oldProductTypeOther");
				String newProductType = getArgumentByTag(itemRoot, "newProductType");
				String newProductTypeOther = getArgumentByTag(itemRoot, "newProductTypeOther");
				String changeName = getArgumentByTag(itemRoot, "changeName");
				String newName = getArgumentByTag(itemRoot, "newName");
				String oldName = getArgumentByTag(itemRoot, "oldName");
				String changeContent = getArgumentByTag(itemRoot, "changeContent");
				String postponement = getArgumentByTag(itemRoot, "postponement");
				String oldOnceDate = getArgumentByTag(itemRoot, "oldOnceDate");
				String newOnceDate = getArgumentByTag(itemRoot, "newOnceDate");
				String stop = getArgumentByTag(itemRoot, "stop");
				String withdraw = getArgumentByTag(itemRoot, "withdraw");
				String other = getArgumentByTag(itemRoot, "other");
				String otherInfo = getArgumentByTag(itemRoot, "otherInfo");
				
				String postponementPlanFile = getArgumentByTag(itemRoot, "postponementPlanFile");
				String finalAuditResult = getArgumentByTag(itemRoot, "finalAuditResult");
				String finalAuditResultDetail = getArgumentByTag(itemRoot, "finalAuditResultDetail");
				String finalAuditDate = getArgumentByTag(itemRoot, "finalAuditDate");
				String finalAuditOpinion = getArgumentByTag(itemRoot, "finalAuditOpinion");
				String finalAuditOpinionFeedback = getArgumentByTag(itemRoot, "finalAuditOpinionFeedback");
				String universityAuditResultDetail = getArgumentByTag(itemRoot, "universityAuditResultDetail");//
				String provinceAuditResultDetail  = getArgumentByTag(itemRoot, "provinceAuditResultDetail ");//
				String type = getArgumentByTag(itemRoot, "type");
				
				ProjectVariation projectVariation = new ProjectVariation();
				//smas端对应的立项对象
				if (resultMap != null) {//不存在抛异常
					projectVariation.setGranted((ProjectGranted)resultMap.get("projectGranted"));
				}
				projectVariation.setIsImported(0);//0 标识smdb端同步
				projectVariation.setImportedDate(new Date());
				
				if (!file.equals("null")) {
					projectVariation.setFile(file);
				}
				if (!applicantSubmitDate.equals("null")) {
					projectVariation.setApplicantSubmitDate(_date(applicantSubmitDate));
				}
				if (!variationReason.equals("null")) {
					projectVariation.setVariationReason(variationReason);
				}
				if (!changeMember.equals("null")) {
					projectVariation.setChangeMember(Integer.parseInt(changeMember));
				}
				if (!oldMembers.equals("null")) {
					projectVariation.setOldMembers(oldMembers);
				}
				if (!newMembers.equals("null")) {
					projectVariation.setNewMembers(newMembers);
				}
				if (!changeAgency.equals("null")) {
					projectVariation.setChangeAgency(Integer.parseInt(changeAgency));
				}
				if (!oldAgencyName.equals("null") && !oldAgencyName.equals("; ")) {
					projectVariation.setOldAgencyName(oldAgencyName);
				}
				if (!newAgencyName.equals("null") && !newAgencyName.equals("; ")) {
					projectVariation.setNewAgencyName(newAgencyName);
				}
				if (!changeProductType.equals("null")) {
					projectVariation.setChangeProductType(Integer.parseInt(changeProductType));
				}
				if (!oldProductType.equals("null")) {
					projectVariation.setOldProductType(oldProductTypeOther);
				}
				if (!oldProductTypeOther.equals("null")) {
					projectVariation.setOldProductTypeOther(oldProductTypeOther);
				}
				if (!newProductType.equals("null")) {
					projectVariation.setNewProductType(newProductTypeOther);
				}
				if (!newProductTypeOther.equals("null")) {
					projectVariation.setNewProductTypeOther(newProductTypeOther);
				}
				if (!changeName.equals("null")) {
					projectVariation.setChangeName(Integer.parseInt(changeName));
				}
				if (!newName.equals("null")) {
					projectVariation.setNewName(newName);
				}
				if (!oldName.equals("null")) {
					projectVariation.setOldName(oldName);
				}
				if (!changeContent.equals("null")) {
					projectVariation.setChangeContent(Integer.parseInt(changeContent));
				}
				if (!postponement.equals("null")) {
					projectVariation.setPostponement(Integer.parseInt(postponement));
				}
				if (!oldOnceDate.equals("null")) {
					projectVariation.setOldOnceDate(_date(oldOnceDate));
				}
				if (!newOnceDate.equals("null")) {
					projectVariation.setNewOnceDate(_date(newOnceDate));
				}
				if (!stop.equals("null")) {
					projectVariation.setStop(Integer.parseInt(stop));
				}
				if (!withdraw.equals("null")) {
					projectVariation.setWithdraw(Integer.parseInt(withdraw));
				}
				if (!other.equals("null")) {
					projectVariation.setOther(Integer.parseInt(other));
				}
				if (!otherInfo.equals("null")) {
					projectVariation.setOtherInfo(otherInfo);
				}
				if (!postponementPlanFile.equals("null")) {
					projectVariation.setPostponementPlanFile(postponementPlanFile);
				}
				if (!finalAuditResult.equals("null")) {
					projectVariation.setFinalAuditResult(Integer.parseInt(finalAuditResult));
				}
				if (!finalAuditResultDetail.equals("null")) {
					projectVariation.setFinalAuditResultDetail(finalAuditResultDetail);
				}
				if (!finalAuditDate.equals("null")) {
					projectVariation.setFinalAuditDate(_date(finalAuditDate));
				}
				if (!finalAuditOpinion.equals("null")) {
					projectVariation.setFinalAuditOpinion(finalAuditOpinion);
				}
				if (!finalAuditOpinionFeedback.equals("null")) {
					projectVariation.setFinalAuditOpinionFeedback(finalAuditOpinionFeedback);
				}
				
				if (!universityAuditResultDetail.equals("null")) {
					projectVariation.setUniversityAuditResultDetail(universityAuditResultDetail);
				}
				if (!provinceAuditResultDetail.equals("null")) {
					projectVariation.setProvinceAuditResultDetail(provinceAuditResultDetail);
				}
				if (!type.equals("null")) {
					projectVariation.setType(type);
				}
				String changeFee = getArgumentByTag(itemRoot, "changeFee");
				if (!changeFee.equals("null")) {
					projectVariation.setChangeFee(Integer.parseInt(changeFee));
				}
				String oldFeeItem = getArgumentByTag(itemRoot, "oldFeeItem");
				String newFeeItem = getArgumentByTag(itemRoot, "newFeeItem");
				
				ProjectFee oldFee = null, newFee = null;
				if (!oldFeeItem.equals("null") && !oldFeeItem.equals("")) {
					Element oldFeeElement = (Element) itemRoot.selectNodes("//oldFeeItem").get(0);
					oldFee = getProjectFeeObject(oldFeeElement, type);
					dao.add(oldFee);
				}
				if (!newFeeItem.equals("null") && !newFeeItem.equals("")) {
					Element newFeeElement = (Element) itemRoot.selectNodes("//newFeeItem").get(0);
					newFee = getProjectFeeObject(newFeeElement, type);
					dao.add(newFee);
				}
				if (oldFee != null) {
					projectVariation.setOldProjectFee(oldFee);
				}
				if (newFee != null) {
					projectVariation.setNewProjectFee(newFee);
				}
				String smasProjectVariationID = (String) dao.add(projectVariation);

				if (!smasProjectVariationID.equals("") && smasProjectVariationID != null && resultMap != null ) {//不存在跑异常
					//产生中间表记录
					SmdbProjectVariation smdbPV = new SmdbProjectVariation();
					smdbPV.setImportedDate(new Date());
					smdbPV.setImportPerson("zhangnan");
					smdbPV.setIsAdded(1);
					smdbPV.setSmasVariationID(smasProjectVariationID);
					smdbPV.setSmdbVariationID(smdbVariationID);
					//关联中间表对应立项对象
					smdbPV.setSmdbGranted((SmdbProjectGranted)resultMap.get("smdbProjectGranted"));
					dao.add(smdbPV);
				}
			} else if ((smasVariationID != null && !smasVariationID.equals("")) && (resultMap != null)) {
				//数据已经同步在库中
				//只做新增数据的修改（新增字段修复代码，只执行一次）
				// 修复 程序一
//				String universityAuditResultDetail = getArgumentByTag(itemRoot, "universityAuditResultDetail");//新增同步字段
//				String provinceAuditResultDetail  = getArgumentByTag(itemRoot, "provinceAuditResultDetail ");//
//				if (!universityAuditResultDetail.equals("null") || !provinceAuditResultDetail.equals("null")) {
//					ProjectVariation pv = (ProjectVariation) dao.query(ProjectVariation.class, smasVariationID);
//					if (!universityAuditResultDetail.equals("null")) {
//						pv.setUniversityAuditResultDetail(universityAuditResultDetail);
//					}
//					if (!provinceAuditResultDetail.equals("null")) {
//						pv.setProvinceAuditResultDetail(provinceAuditResultDetail);
//					}
//					dao.addOrModify(pv);
//				}
				//修复程序二 (只执行一次）
				//<项目变更详情中，人员（机构出现null）>的问题
				
				String oldMembers = getArgumentByTag(itemRoot, "oldMembers");//新增同步字段
				String newMembers  = getArgumentByTag(itemRoot, "newMembers ");//
				
				if (!oldMembers.equals("null") || !newMembers.equals("null")) {
					ProjectVariation pv = (ProjectVariation) dao.query(ProjectVariation.class, smasVariationID);
					if (!oldMembers.equals("null")) {
						pv.setOldMembers(oldMembers);
					}
					if (!newMembers.equals("null")) {
						pv.setNewMembers(newMembers);
					}
					dao.addOrModify(pv);
				}
			} else if(resultMap == null) { // 无对应的立项数据，不规范数据
				badItemsList.add(smdbVariationID);
			}//其他项目已经入库
		} else {//数据不合法，或者没有对应的立项，则不符合规范。
			badItemsList.add(smdbVariationID);
		}
	}

	/**
	 * 判断是否已经入库中间表，若存在返回smas端的项目变更id
	 * @param smdbAppId
	 */
	private String getProjectVariationInfos(String smdbVariationID) {
		Map resultMap = null;
		Map argsMap = new HashMap<String, String>();
		argsMap.put("smdbVariationID", smdbVariationID);
		String hql = "select smdbpv.smasVariationID from SmdbProjectVariation smdbpv where smdbpv.smdbVariationID = :smdbVariationID ";
		List infoList = dao.query(hql, argsMap);
		if (infoList != null && infoList.size() == 1) {
			return  (String) infoList.get(0);
		}
		return "";
	}
	/**
	 * 返回对应的smas端立项对象，和中间表对应的立项对象
	 * @param smdbProjectGrantedID
	 */
	private Map getProjectGrantedInfos(String smdbGrantedID) {
		Map resultMap = null;
		Map argsMap = new HashMap<String, String>();
		argsMap.put("smdbGrantedID", smdbGrantedID);
		String hql = "select pg, smdbpg from SmdbProjectGranted smdbpg, ProjectGranted pg where smdbpg.smdbGrantedID = :smdbGrantedID and smdbpg.smasGrantedID = pg.id";
		List infoList = dao.query(hql, argsMap);
		if (infoList != null && infoList.size() != 0) {
			Object[] infoObjects = (Object[]) infoList.get(0);
			ProjectGranted pg = (ProjectGranted) infoObjects[0];
			SmdbProjectGranted smdbPG = (SmdbProjectGranted) infoObjects[1];
			resultMap = new HashMap();
			resultMap.put("projectGranted", pg);
			resultMap.put("smdbProjectGranted", smdbPG);
		}
		return resultMap;
	}
	
}
