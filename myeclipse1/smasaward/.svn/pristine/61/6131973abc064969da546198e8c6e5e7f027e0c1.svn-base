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
import csdc.bean.SmdbProjectApplication;
import csdc.bean.SmdbProjectGranted;
import csdc.dao.IHibernateBaseDao;
import csdc.service.IProjectService;

/**
 * 项目申请记录解析入库
 * 2015-1-12
 */
public class ProjectGrantedRecordResolver extends BaseRecordResolver implements RecordResolver {	
	private Element itemRoot;//已经读取了xml的元素
	private SimpleDateFormat sdf; 
	public ProjectGrantedRecordResolver (){}
	public ProjectGrantedRecordResolver(Element item, SimpleDateFormat sdf, IHibernateBaseDao dao, List badItemIDs) {
		this.itemRoot = item;
		this.dao = dao;
		this.sdf = sdf;
		this.badItemsList = badItemIDs;
	}
	@Transactional
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
	 * 项目立项数据：在中间表中建立smdb端的立项与申请之间的关联关系，然后数据全部入库smas 项目立项表
	 * @return
	 */
	
	public void parse() throws ParseException {
		
		String smdbGrantedID = getArgumentByTag(itemRoot, "id");
		String smdbAppID = getArgumentByTag(itemRoot, "applicationId");
		String projectName = getArgumentByTag(itemRoot, "name");//项目名称
		if (!smdbGrantedID.equals("null") &&!smdbAppID.equals("null") && !smdbGrantedID.equals("") && !smdbAppID.equals("")) {
			Map resultMap = getProjectGrantedInfos(smdbAppID, projectName);
			//判断smas中是否存在此立项项目信息，如果存在，直接建立中间表；如果不存在，则入库后，建立中间表
			if (resultMap != null && !resultMap.isEmpty()) {
				//smas立项中存在此项目,中间表必然有对应的记录
				String smasPgId = (String) resultMap.get("smasPgId");
				SmdbProjectApplication smdbPaObject = (SmdbProjectApplication) resultMap.get("smdbPaObject");
				
				if(!checkIsAlreadyInSmdbProjectGranted(smdbGrantedID, smasPgId)) {
					//如中检立项表不存在，则建立
					SmdbProjectGranted smdbPg = new SmdbProjectGranted();
					smdbPg.setImportedDate(new Date());
					smdbPg.setImportPerson("zhangnan");
					smdbPg.setIsAdded(1);
					smdbPg.setSmasGrantedID(smasPgId);
					smdbPg.setSmdbGrantedID(smdbGrantedID);
					smdbPg.setSmdbApplication(smdbPaObject);
					dao.add(smdbPg);
				}
			} else {//smas端没有立项数据，必然直接建立
				//新建smas立项，在建smdb中间表立项
				String type = getArgumentByTag(itemRoot, "projectType");
				String number= getArgumentByTag(itemRoot, "number");
				String status = getArgumentByTag(itemRoot, "status");
				String agencyName = getArgumentByTag(itemRoot, "agencyName");
				String applicantName = getArgumentByTag(itemRoot, "applicantName");
				String applicantSubmitDate = getArgumentByTag(itemRoot, "applicantSubmitDate");
				String approveDate = getArgumentByTag(itemRoot, "approveDate");//---approveDate
				String approveFee = getArgumentByTag(itemRoot, "approveFee");
				String auditType = getArgumentByTag(itemRoot, "auditType");//
				String divisionName = getArgumentByTag(itemRoot, "divisionName");
				String endStopWithdrawDate = getArgumentByTag(itemRoot, "endStopWithdrawDate");
				String endStopWithdrawOpinion = getArgumentByTag(itemRoot, "endStopWithdrawOpinion");
				String endStopWithdrawPerson = getArgumentByTag(itemRoot, "endStopWithdrawPerson");
				String file = getArgumentByTag(itemRoot, "file");
				String finalAuditDate = getArgumentByTag(itemRoot, "finalAuditDate");
				String finalAuditOpinion = getArgumentByTag(itemRoot, "finalAuditOpinion");
				String finalAuditOpinionFeedback = getArgumentByTag(itemRoot, "finalAuditOpinionFeedback");
				String finalAuditResult = getArgumentByTag(itemRoot, "finalAuditResult");
				//添加最终审核人姓名
				String finalAuditorName = getArgumentByTag(itemRoot, "finalAuditorName");//--
//				String importAuditDate = getArgumentByTag(itemRoot, "importAuditDate");//
				String endStopWithdrawOpinionFeedback = getArgumentByTag(itemRoot, "endStopWithdrawOpinionFeedback");
				String planEndDate = getArgumentByTag(itemRoot, "planEndDate");
				String productType = getArgumentByTag(itemRoot, "productType");//--
				String productTypeOther = getArgumentByTag(itemRoot, "productTypeOther");//--
				String subtypeName = getArgumentByTag(itemRoot, "subtypeName");
				String UniversityName = getArgumentByTag(itemRoot, "UniversityName");
				String UniversityCode = getArgumentByTag(itemRoot, "UniversityCode");
				String researchTypeName = getArgumentByTag(itemRoot, "researchTypeName");
				//新建立项项目
				ProjectGranted projectGranted = new ProjectGranted();
				projectGranted.setName(projectName);//项目名称
				if (!type.equals("null")) {
					projectGranted.setType(type);
				}
				if (!number.equals("null")) {
					projectGranted.setNumber(number);
				}
				if (!status.equals("null")) {
					projectGranted.setStatus(Integer.parseInt(status));
				}
				if (!agencyName.equals("null")) {
					projectGranted.setAgencyName(agencyName);
				}
				if (!applicantName.equals("null")) {
					projectGranted.setApplicantName(applicantName);
				}
				if (!applicantSubmitDate.equals("null")) {
					projectGranted.setApplicantSubmitDate(_date(applicantSubmitDate));
				}
				if (!approveDate.equals("null")) {
					projectGranted.setApproveDate(_date(approveDate));
				}
				if (!approveFee.equals("null")) {
					projectGranted.setApproveFee(Double.parseDouble(approveFee));
				}
				if (!divisionName.equals("null")) {
					projectGranted.setDivisionName(divisionName);
				}
				if (!endStopWithdrawDate.equals("null")) {
					projectGranted.setEndStopWithdrawDate(_date(endStopWithdrawDate));
				}
				if (!endStopWithdrawOpinion.equals("null")) {
					projectGranted.setEndStopWithdrawOpinion(endStopWithdrawOpinion);
				}
				if (!endStopWithdrawPerson.equals("null")) {
					projectGranted.setEndStopWithdrawPerson(endStopWithdrawPerson);
				}
				if (!file.equals("null")) {
					projectGranted.setFile(file);
				}
				if (!finalAuditDate.equals("null")) {
					projectGranted.setFinalAuditDate(_date(finalAuditDate));
				}
				if (!finalAuditOpinion.equals("null")) {
					projectGranted.setFinalAuditOpinion(finalAuditOpinion);
				}
				if (!finalAuditOpinionFeedback.equals("null")) {
					projectGranted.setFinalAuditOpinionFeedback(finalAuditOpinionFeedback);
				}
				if (!finalAuditorName.equals("null")) {
					projectGranted.setFinalAuditorName(finalAuditorName);
				}
				if (!finalAuditResult.equals("null")) {
					projectGranted.setFinalAuditResult(Integer.parseInt(finalAuditResult));
				}
				if (!endStopWithdrawOpinionFeedback.equals("null")) {
					projectGranted.setEndStopWithdrawOpinionFeedback(endStopWithdrawOpinionFeedback);
				}
				if (!planEndDate.equals("null")) {
					projectGranted.setPlanEndDate(_date(planEndDate));
				}
				if (!productType.equals("null")) {
					projectGranted.setProductType(productType);
				}
				if (!productTypeOther.equals("null")) {
					projectGranted.setProductTypeOther(productTypeOther);
				}
				if (!subtypeName.equals("null")) {
					projectGranted.setSubtype(subtypeName);
				}
				if (!UniversityName.equals("null")) {
					projectGranted.setUniversityName(UniversityName);
				}
				if (!UniversityCode.equals("null")) {
					projectGranted.setUniversityCode(UniversityCode);
				}
				if (!researchTypeName.equals("null")) {
					projectGranted.setResearchType(researchTypeName);
				}
				String projectFeeContent = getArgumentByTag(itemRoot, "projectFeeItem");
				ProjectFee pojFee = null;
				if (!projectFeeContent.equals("null") && !projectFeeContent.equals("")) {
					Element feeElementRoot = (Element) itemRoot.selectNodes("//projectFeeItem").get(0);
					pojFee = getProjectFeeObject(feeElementRoot, type);
				}
				if (pojFee != null) {
					projectGranted.setProjectFee(pojFee);
				}
				//获取对应的smas项目申请对象，如果没有对应的项目申报，则数据不规范
				ProjectApplication smasPa = getSmasProjectApplicationObject(smdbAppID);
				if (null != smasPa) {
					smasPa.setIsGranted(1);//修改对应立项的标志（逻辑处理上的需要）
					
					projectGranted.setApplication(smasPa);
					projectGranted.setIsImported(0);
					projectGranted.setImportedDate(new Date());
					//保存
					String smasProjectGrantedId = (String) dao.add(projectGranted);
					dao.addOrModify(smasPa);//更新操作
					//添加中间表
					SmdbProjectGranted smdbPg = new SmdbProjectGranted();
					smdbPg.setImportedDate(new Date());
					smdbPg.setImportPerson("zhangnan");
					smdbPg.setIsAdded(1);
					smdbPg.setSmasGrantedID(smasProjectGrantedId);
					smdbPg.setSmdbGrantedID(smdbGrantedID);
					SmdbProjectApplication smdbPa = getSmdbProjectApplicationObject(smdbAppID);
					smdbPg.setSmdbApplication(smdbPa);
					dao.add(smdbPg);
				} else {
					badItemsList.add(smdbGrantedID);
				}
			}
			
		} else {//数据不合法
			badItemsList.add(smdbGrantedID);
		}
	}

	/**
	 * 根据smdb项目申报id和项目名称，核查smas立项表中否存在此立项信息
	 * @param smdbAppId
	 */
	private Map getProjectGrantedInfos(String smdbApplID, String projectName) {
		Map resultMap = null;
		Map argsMap = new HashMap<String, String>();
		argsMap.put("smdbApplID", smdbApplID);
		argsMap.put("projectName", projectName);
		String hql = "select pg.id, smdbpa from SmdbProjectApplication smdbpa, ProjectGranted pg left join pg.application pa where smdbpa.smdbApplID = :smdbApplID and smdbpa.smasApplID = pa.id and pg.name = :projectName";
		List infoList = dao.query(hql, argsMap);
		if (infoList != null && infoList.size() != 0) {
			Object[] infoObjects = (Object[]) infoList.get(0);
			String smasProjectGeantedID = (String) infoObjects[0];
			SmdbProjectApplication smdbPa = (SmdbProjectApplication) infoObjects[1];
			resultMap = new HashMap();
			resultMap.put("smasPgId", smasProjectGeantedID);
			resultMap.put("smdbPaObject", smdbPa);
		}
		return resultMap;
	}
	
	/**
	 * 检查smdbProjectGranted中间表是否存在项目申报记录
	 * @param smasID
	 * @param smdbID
	 * @return
	 */
	private boolean checkIsAlreadyInSmdbProjectGranted(String smdbGrantedID, String smasGrantedID) {
		Map argMap = new HashMap();
		argMap.put("smdbGrantedID", smdbGrantedID);
		argMap.put("smasGrantedID", smasGrantedID);
		String hql = "from SmdbProjectGranted smdbPg where smdbPg.smdbGrantedID = :smdbGrantedID and smdbPg.smasGrantedID = :smasGrantedID";
		if(dao.count(hql, argMap) != 0) {
			return true;
		}
		return false;
	}
	private ProjectApplication getSmasProjectApplicationObject(String smdbAppID) {
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("smdbApplID", smdbAppID);
		String hql = "select pa from ProjectApplication pa, SmdbProjectApplication smdbPa where smdbPa.smdbApplID = :smdbApplID and smdbPa.smasApplID = pa.id";
		return  (ProjectApplication) dao.queryUnique(hql, argMap);
	}
	private SmdbProjectApplication getSmdbProjectApplicationObject(String smdbAppID) {
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("smdbApplID", smdbAppID);
		String hql = "select smdbPa from SmdbProjectApplication smdbPa where smdbPa.smdbApplID = :smdbApplID";
		return  (SmdbProjectApplication) dao.queryUnique(hql, argMap);
	}
	
}
