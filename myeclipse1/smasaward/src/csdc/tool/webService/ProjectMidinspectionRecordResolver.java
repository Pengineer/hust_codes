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
import csdc.bean.ProjectMidinspection;
import csdc.bean.ProjectVariation;
import csdc.bean.SmdbProjectApplication;
import csdc.bean.SmdbProjectEndinspection;
import csdc.bean.SmdbProjectGranted;
import csdc.bean.SmdbProjectMidinspection;
import csdc.bean.SmdbProjectVariation;
import csdc.dao.IHibernateBaseDao;
import csdc.service.IProjectService;

/**
 * 项目结项记录解析入库
 */
public class ProjectMidinspectionRecordResolver extends BaseRecordResolver implements RecordResolver  {	
	private Element itemRoot;//已经读取了xml的元素
	private SimpleDateFormat sdf; 
	public ProjectMidinspectionRecordResolver() {};
	public ProjectMidinspectionRecordResolver(Element item, SimpleDateFormat sdf, IHibernateBaseDao dao, List badItemIDs) {
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
		
		String smdbMidinspectionID = getArgumentByTag(itemRoot, "id");
		String smdbGriantedID = getArgumentByTag(itemRoot, "grantedId");
		if (!smdbMidinspectionID.equals("null") &&!smdbGriantedID.equals("null") && !smdbMidinspectionID.equals("") && !smdbGriantedID.equals("")) {
			String smasMidinspectionID = getProjectMidinspectionInfos(smdbMidinspectionID);
			//判断中间表是否存在此变更记录，只处理不存在情况， 直接入库和中间表。smas添加的变更记录与smdb端的必然不同
			Map resultMap = getProjectGrantedInfos(smdbGriantedID);
			// 没有入库 且 有对应立项
			if ((smasMidinspectionID == null || smasMidinspectionID.equals("")) && (resultMap != null)) {
				
				String file= getArgumentByTag(itemRoot, "file");
				String applicantSubmitDate= getArgumentByTag(itemRoot, "applicantSubmitDate");
				String progress= getArgumentByTag(itemRoot, "progress");
				String productIntroduction= getArgumentByTag(itemRoot, "productIntroduction");
				String finalAuditResult= getArgumentByTag(itemRoot, "finalAuditResult");
				String finalAuditorName= getArgumentByTag(itemRoot, "finalAuditorName");
				String finalAuditDate= getArgumentByTag(itemRoot, "finalAuditDate");
				String finalAuditOpinion= getArgumentByTag(itemRoot, "finalAuditOpinion");
				String finalAuditOpinionFeedback= getArgumentByTag(itemRoot, "finalAuditOpinionFeedback");
				String type= getArgumentByTag(itemRoot, "type");
				//新建smas对象
				ProjectMidinspection projectMidinspection = new ProjectMidinspection();
				//smas端对应的立项对象
				if (resultMap != null) {
					projectMidinspection.setGranted((ProjectGranted)resultMap.get("projectGranted"));
				}
				projectMidinspection.setImportedDate(new Date());
				projectMidinspection.setIsImported(0);//导入方式
				if (!file.equals("null")) {
					projectMidinspection.setFile(file);
				}
				if (!applicantSubmitDate.equals("null")) {
					projectMidinspection.setApplicantSubmitDate(_date(applicantSubmitDate));
				}
				if (!progress.equals("null")) {
					projectMidinspection.setProgress(progress);
				}
				if (!productIntroduction.equals("null")) {
					projectMidinspection.setProductIntroduction(productIntroduction);
				}
				if (!finalAuditResult.equals("null")) {
					projectMidinspection.setFinalAuditResult(Integer.parseInt(finalAuditResult));
				}
				
				if (!finalAuditorName.equals("null")) {
					projectMidinspection.setFinalAuditorName(finalAuditorName);
				}if (!finalAuditDate.equals("null")) {
					projectMidinspection.setFinalAuditDate(_date(finalAuditDate));
				}
				if (!finalAuditOpinion.equals("null")) {
					projectMidinspection.setFinalAuditOpinion(finalAuditOpinionFeedback);
				}
				if (!finalAuditOpinionFeedback.equals("null")) {
					projectMidinspection.setFinalAuditOpinionFeedback(finalAuditOpinionFeedback);
				}
				if (!type.equals("null")) {
					projectMidinspection.setType(type);
				}
			
				String feeItem = getArgumentByTag(itemRoot, "projectFeeItem");
				ProjectFee feeObject = null;
				if (!feeItem.equals("null") && !feeItem.equals("")) {
					Element feeElement = (Element) itemRoot.selectNodes("//projectFeeItem").get(0);
					feeObject = getProjectFeeObject(feeElement, type);
					dao.add(feeObject);
				}
				if (feeObject != null) {
					projectMidinspection.setProjectFee(feeObject);
				}
				
				String smasprojectMidinspectionID = (String) dao.add(projectMidinspection);
				
				if (!smasprojectMidinspectionID.equals("") && smasprojectMidinspectionID != null) {
					//产生中间表记录
					SmdbProjectMidinspection smdbPM = new SmdbProjectMidinspection();
					smdbPM.setImportedDate(new Date());
					smdbPM.setImportPerson("zhangnan");
					smdbPM.setIsAdded(1);
					smdbPM.setSmasMidinspectionID(smasprojectMidinspectionID);
					smdbPM.setSmdbMidinspectionID(smdbMidinspectionID);
					//关联中间表对应立项对象
					smdbPM.setSmdbGranted((SmdbProjectGranted)resultMap.get("smdbProjectGranted"));
					dao.add(smdbPM);
				}
			} else if(resultMap == null) { // 无对应的立项数据，不规范数据
				badItemsList.add(smdbMidinspectionID);
			}//其他项目已经入库
		} else {//数据不规范
			badItemsList.add(smdbMidinspectionID);
		}
	}

	/**
	 * 根据项目结项中间表，通过smdb的结项ID，获取smas端的项目结项id
	 * @param smdbAppId
	 */
	private String getProjectMidinspectionInfos(String smdbMidinspectionID) {
		Map resultMap = null;
		Map argsMap = new HashMap<String, String>();
		argsMap.put("smdbMidinspectionID", smdbMidinspectionID);
		String hql = "select smdbpm.smasMidinspectionID from SmdbProjectMidinspection smdbpm where smdbpm.smdbMidinspectionID = :smdbMidinspectionID ";
		List infoList = dao.query(hql, argsMap);
		if (infoList != null && infoList.size() != 0) {
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
