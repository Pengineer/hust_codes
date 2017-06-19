package csdc.tool.webService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import csdc.bean.ProjectApplication;
import csdc.bean.ProjectEndinspection;
import csdc.bean.ProjectFee;
import csdc.bean.ProjectGranted;
import csdc.bean.ProjectVariation;
import csdc.bean.SmdbProjectApplication;
import csdc.bean.SmdbProjectEndinspection;
import csdc.bean.SmdbProjectGranted;
import csdc.bean.SmdbProjectVariation;
import csdc.dao.IHibernateBaseDao;
import csdc.service.IProjectService;

/**
 * 项目结项记录解析入库
 */
public class ProjectEndinspectionRecordResolver extends BaseRecordResolver implements RecordResolver  {	
	private Element itemRoot;//已经读取了xml的元素
	private SimpleDateFormat sdf; 
	public ProjectEndinspectionRecordResolver() {};
	public ProjectEndinspectionRecordResolver(Element item, SimpleDateFormat sdf, IHibernateBaseDao dao, List badItemIDs) {
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
	 * 项目结项数据解析入库
	 * @return
	 */
	public void parse() throws ParseException {
		
		String smdbEndinspectionID = getArgumentByTag(itemRoot, "id");
		String smdbGriantedID = getArgumentByTag(itemRoot, "grantedId");
		if (!smdbEndinspectionID.equals("null") &&!smdbGriantedID.equals("null") && !smdbEndinspectionID.equals("") && !smdbGriantedID.equals("")) {
			String smasEndinspectionID = getProjectEndinspectionInfos(smdbEndinspectionID);
			//判断中间表是否存在此变更记录，只处理不存在情况， 直接入库和中间表。smas添加的变更记录与smdb端的必然不同
			Map resultMap = getProjectGrantedInfos(smdbGriantedID);
			// 没有入库 且 存在对应的立项数据
			if ((smasEndinspectionID == null || smasEndinspectionID.equals("")) && (resultMap != null)) {
				
				String certificate = getArgumentByTag(itemRoot, "certificate");
				String file= getArgumentByTag(itemRoot, "file");
				String applicantSubmitDate= getArgumentByTag(itemRoot, "applicantSubmitDate");
				String isApplyNoevaluation= getArgumentByTag(itemRoot, "isApplyNoevaluation");
				String isApplyExcellent= getArgumentByTag(itemRoot, "isApplyExcellent");
				String memberName= getArgumentByTag(itemRoot, "memberName");
				String ministryAuditorName= getArgumentByTag(itemRoot, "ministryAuditorName");
				String ministryAuditDate= getArgumentByTag(itemRoot, "ministryAuditDate");
				String ministryAuditOpinion= getArgumentByTag(itemRoot, "ministryAuditOpinion");
				String ministryResultEnd= getArgumentByTag(itemRoot, "ministryResultEnd");
				String ministryResultNoevaluation= getArgumentByTag(itemRoot, "ministryResultNoevaluation");
				String ministryResultExcellent= getArgumentByTag(itemRoot, "ministryResultExcellent");
				String finalAuditorName= getArgumentByTag(itemRoot, "finalAuditorName");
				String finalAuditDate= getArgumentByTag(itemRoot, "finalAuditDate");
				String finalAuditResultEnd= getArgumentByTag(itemRoot, "finalAuditResultEnd");
				String finalAuditResultNoevaluation= getArgumentByTag(itemRoot, "finalAuditResultNoevaluation");
				String finalAuditResultExcellent= getArgumentByTag(itemRoot, "finalAuditResultExcellent");
				String finalAuditOpinion= getArgumentByTag(itemRoot, "finalAuditOpinion");
				String finalAuditOpinionFeedback= getArgumentByTag(itemRoot, "finalAuditOpinionFeedback");
				
				String reviewerName= getArgumentByTag(itemRoot, "reviewerName");
				String reviewDate= getArgumentByTag(itemRoot, "reviewDate");
				String reviewTotalScore= getArgumentByTag(itemRoot, "reviewTotalScore");
				String reviewAverageScore= getArgumentByTag(itemRoot, "reviewAverageScore");
				String reviewWay= getArgumentByTag(itemRoot, "reviewWay");
				String reviewResult= getArgumentByTag(itemRoot, "reviewResult");
				String reviewOpinion= getArgumentByTag(itemRoot, "reviewOpinion");
				String reviewGrade= getArgumentByTag(itemRoot, "reviewGrade");
				String reviewOpinionQualitative= getArgumentByTag(itemRoot, "reviewOpinionQualitative");
				String type= getArgumentByTag(itemRoot, "type");
				
				String importedProductInfo= getArgumentByTag(itemRoot, "importedProductInfo");
				String importedProductTypeOther= getArgumentByTag(itemRoot, "importedProductTypeOther");
				//新建smas对象
				ProjectEndinspection projectEndinspection = new ProjectEndinspection();
				//smas端对应的立项对象
				if (resultMap != null) {
					projectEndinspection.setGranted((ProjectGranted)resultMap.get("projectGranted"));
				}
				projectEndinspection.setImportedDate(new Date());
				projectEndinspection.setIsImported(0);//导入方式
				
				if (!certificate.equals("null")) {
					projectEndinspection.setCertificate(certificate);
				}
				if (!file.equals("null")) {
					projectEndinspection.setFile(file);
				}
				if (!applicantSubmitDate.equals("null")) {
					projectEndinspection.setApplicantSubmitDate(_date(applicantSubmitDate));
				}
				if (!isApplyNoevaluation.equals("null")) {
					projectEndinspection.setIsApplyNoevaluation(Integer.parseInt(isApplyNoevaluation));
				}
				if (!isApplyExcellent.equals("null")) {
					projectEndinspection.setIsApplyExcellent(Integer.parseInt(isApplyExcellent));
				}
				if (!memberName.equals("null")) {
					projectEndinspection.setMemberName(memberName);
				}
				if (!ministryAuditorName.equals("null")) {
					projectEndinspection.setMinistryAuditorName(ministryAuditorName);
				}
				if (!ministryAuditDate.equals("null")) {
					projectEndinspection.setMinistryAuditDate(_date(ministryAuditDate));
				}
				if (!ministryAuditOpinion.equals("null")) {
					projectEndinspection.setMinistryAuditOpinion(ministryAuditOpinion);
				}
				
				if (!ministryResultEnd.equals("null")) {
					projectEndinspection.setMinistryResultEnd(Integer.parseInt(ministryResultEnd));
				}
				if (!ministryResultNoevaluation.equals("null")) {
					projectEndinspection.setMinistryResultNoevaluation(Integer.parseInt(ministryResultNoevaluation));
				}
				if (!ministryResultExcellent.equals("null")) {
					projectEndinspection.setMinistryResultExcellent(Integer.parseInt(ministryResultExcellent));
				}
				if (!finalAuditorName.equals("null")) {
					projectEndinspection.setFinalAuditorName(finalAuditorName);
				}
				if (!finalAuditDate.equals("null")) {
					projectEndinspection.setFinalAuditDate(_date(finalAuditDate));
				}
				if (!finalAuditResultEnd.equals("null")) {
					projectEndinspection.setFinalAuditResultEnd(Integer.parseInt(finalAuditResultEnd));
				}
				if (!finalAuditResultNoevaluation.equals("null")) {
					projectEndinspection.setFinalAuditResultNoevaluation(Integer.parseInt(finalAuditResultNoevaluation));
				}
				if (!finalAuditResultExcellent.equals("null")) {
					projectEndinspection.setFinalAuditResultExcellent(Integer.parseInt(finalAuditResultExcellent));
				}
				if (!finalAuditOpinion.equals("null")) {
					projectEndinspection.setFinalAuditOpinion(finalAuditOpinion);
				}
				if (!finalAuditOpinionFeedback.equals("null")) {
					projectEndinspection.setFinalAuditOpinionFeedback(finalAuditOpinionFeedback);
				}
				
				if (!reviewerName.equals("null")) {
					projectEndinspection.setReviewerName(reviewerName);
				}
				if (!reviewDate.equals("null")) {
					projectEndinspection.setReviewDate(_date(reviewDate));
				}
				if (!reviewTotalScore.equals("null")) {
					projectEndinspection.setReviewTotalScore(Double.parseDouble(reviewTotalScore));
				}
				if (!reviewAverageScore.equals("null")) {
					projectEndinspection.setReviewAverageScore(Double.parseDouble(reviewAverageScore));
				}
				if (!reviewWay.equals("null")) {
					projectEndinspection.setReviewWay(Integer.parseInt(reviewWay));
				}
				if (!reviewResult.equals("null")) {
					projectEndinspection.setReviewResult(Integer.parseInt(reviewResult));
				}
				if (!reviewOpinion.equals("null")) {
					projectEndinspection.setReviewOpinion(reviewOpinion);
				}
				if (!reviewGrade.equals("null")) {
					projectEndinspection.setReviewGrade(reviewGrade);
				}
				if (!reviewOpinionQualitative.equals("null")) {
					projectEndinspection.setReviewOpinionQualitative(reviewOpinionQualitative);
				}
				if (!type.equals("null")) {
					projectEndinspection.setType(type);
				}
				
				if (!importedProductInfo.equals("null")) {
					projectEndinspection.setImportedProductInfo(importedProductInfo);
				}
				if (!importedProductTypeOther.equals("null")) {
					projectEndinspection.setImportedProductTypeOther(importedProductTypeOther);
				}
				
				String feeItem = getArgumentByTag(itemRoot, "projectFeeItem");
				ProjectFee feeObject = null;
				if (!feeItem.equals("null") && !feeItem.equals("")) {
					Element feeElement = (Element) itemRoot.selectNodes("//projectFeeItem").get(0);
					feeObject = getProjectFeeObject(feeElement, type);
					dao.add(feeObject);
				}
				if (feeObject != null) {
					projectEndinspection.setProjectFee(feeObject);
				}
				String smasProjectEndinspectID = (String) dao.add(projectEndinspection);
				
				if (!smasProjectEndinspectID.equals("") && smasProjectEndinspectID != null) {
					//产生中间表记录
					SmdbProjectEndinspection smdbPE = new SmdbProjectEndinspection();
					smdbPE.setImportedDate(new Date());
					smdbPE.setImportPerson("zhangnan");
					smdbPE.setIsAdded(1);
					smdbPE.setSmasEndinspectionID(smasProjectEndinspectID);
					smdbPE.setSmdbEndinspectionID(smdbEndinspectionID);
					//关联中间表对应立项对象
					smdbPE.setSmdbGranted((SmdbProjectGranted)resultMap.get("smdbProjectGranted"));
					dao.add(smdbPE);
				}
			} else if (smasEndinspectionID != null && !smasEndinspectionID.equals("") && resultMap != null ) {
				//TODO 修复结项 最终审核日期（只执行一次）
//				String finalAuditDate= getArgumentByTag(itemRoot, "finalAuditDate");
//				if (!finalAuditDate.equals("null")) {
//					ProjectEndinspection projectEndinspection = (ProjectEndinspection) dao.query(ProjectEndinspection.class, smasEndinspectionID);
//					projectEndinspection.setFinalAuditDate(_date(finalAuditDate));
//					dao.addOrModify(projectEndinspection);
//				}
			}else if(resultMap == null) { // 无对应的立项数据，不规范数据
				badItemsList.add(smdbEndinspectionID);
			}//其他项目已经入库
		} else {//数据不合法,或没有对应的立项信息
			badItemsList.add(smdbEndinspectionID);
		}
	}

	/**
	 * 根据项目结项中间表，通过smdb的结项ID，获取smas端的项目结项id。如果不存在返回""
	 * @param smdbAppId
	 */
	private String getProjectEndinspectionInfos(String smdbEndinspectionID) {
		Map resultMap = null;
		Map argsMap = new HashMap<String, String>();
		argsMap.put("smdbEndinspectionID", smdbEndinspectionID);
		String hql = "select smdbpe.smasEndinspectionID from SmdbProjectEndinspection smdbpe where smdbpe.smdbEndinspectionID = :smdbEndinspectionID ";
		List infoList = dao.query(hql, argsMap);
		if (infoList != null && infoList.size() != 0) {
			return  (String) infoList.get(0);
		}
		return "";
	}
	/**
	 * 返回smdbProjectGrantedID 与之对应的smas端立项对象，和中间表对应的立项对象
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
