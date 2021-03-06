package csdc.service.webService.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Account;
import csdc.bean.InterfaceConfig;
import csdc.bean.SyncStatus;
import csdc.tool.bean.AccountType;

@SuppressWarnings("unchecked")
@Transactional
public class SinossWebService extends BaseWebService implements ISinossWebService{

	
	public String requestProjectApplicationResult(String projectType, int length, Account account, String requestAccountBelong) {
		Long begin = System.currentTimeMillis();
		Map sMap = new HashMap();
		Map map = new HashMap();
		String peer = getPeerByAccount(account);
		map.put("peer", peer);
		String belongId = projectService.getBelongIdByAccount(account);
		AccountType type = account.getType();
		int isPrincipal = account.getIsPrincipal();
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		sMap.put("peer", peer);
		List synList = dao.query("from SyncStatus s where s.status = 0 and s.peer = :peer and s.interfaceName != 'requestProjectApplicationResult'", sMap);
		if(!synList.isEmpty()) {
			return responseContent("error", "您上一次访问的服务接口，还有未应答项目");
		}
		List<String> projectIdList = dao.query("select s.projectId from SyncStatus s where s.status = 0 and s.peer = :peer and s.interfaceName = 'requestProjectApplicationResult'", sMap);
		Boolean flag = true;//标志是否已将本次同步数据加入同步状态表，每条数据仅在同步状态表中存在一条
		if(!projectIdList.isEmpty()) {
			flag = false;
		}
		Date requestDate = new Date();
		List<SyncStatus> syncStatusList = new ArrayList<SyncStatus>();
		StringBuffer hql = new StringBuffer();
		hql.append("select app.id, spa.sinossId, app.finalAuditResult, pg.number, pg.approveFee ");
		if(account.getType().within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
			hql.append("from SinossProjectApplication spa left join spa.projectApplication app left join spa.projectGranted pg left join app.university uni " +
					"left join ProjectMember mem where mem.application.id=app.id ");
		}else{//管理人员
			hql.append("from SinossProjectApplication spa left join spa.projectApplication app left join spa.projectGranted pg left join app.university uni " +
					"where 1=1 ");
		}		
		if(type.equals(AccountType.ADMINISTRATOR)){}//系统管理员
		else if(type.equals(AccountType.MINISTRY) ){//部级
			if(isPrincipal == 1){//主账号 
			} else {//子账号
				String ministryId = projectService.getAgencyIdByOfficerId(belongId);
				if (ministryId != null){
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if(type.equals(AccountType.PROVINCE) ){//省级
			if(isPrincipal == 1){//主账号
				hql.append(" and uni.type=4 and uni.subjection.id=:belongId");
				map.put("belongId", belongId);
			}else{//子账号
				String provinceId = projectService.getAgencyIdByOfficerId(belongId);
				if(provinceId != null){
					hql.append(" and uni.type=4 and uni.subjection.id=:provinceId");
					map.put("provinceId", provinceId);
				}else{
					hql.append(" and 1=0");
				}
			}
		} else if(type.equals(AccountType.MINISTRY_UNIVERSITY) || type.equals(AccountType.LOCAL_UNIVERSITY)){//部属高校与地方高校
			if (isPrincipal == 1){//主账号
				hql.append(" and uni.id=:belongId");
				map.put("belongId", belongId);
			} else {//子账号
				String universityId = projectService.getAgencyIdByOfficerId(belongId);
				if (universityId != null){
					hql.append(" and uni.id=:universityId");
					map.put("universityId", universityId);
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if(type.equals(AccountType.DEPARTMENT)){//院系
			if(isPrincipal == 1){//主账号
				hql.append(" and app.department.id=:belongId");
				map.put("belongId", belongId);
			} else{//子账号
				String departmentId = projectService.getDepartmentIdByOfficerId(belongId);
				if(departmentId != null){
					hql.append(" and app.department.id=:departmentId");
					map.put("departmentId", departmentId);
				} else{
					hql.append(" and 1=0");
				}
			}
		} else if(type.equals(AccountType.INSTITUTE)){//研究机构
			if(isPrincipal == 1){//主账号
				hql.append(" and app.institute.id=:belongId");
				map.put("belongId", belongId);
			} else{//子账号
				String instituteId = projectService.getInstituteIdByOfficerId(belongId);
				if(instituteId != null){
					hql.append(" and app.institute.id=:instituteId");
					map.put("instituteId", instituteId);
				} else{
					hql.append(" and 1=0");
				}
			}
		} else if(type.within(AccountType.EXPERT, AccountType.STUDENT)){//外部专家与内部专家或学生
			hql.append(" and mem.member.id=:belongId and mem.groupNumber = 1" );
			map.put("belongId", belongId);
		} else{
			hql.append(" and 1=0");
		}
		JSONObject mapObj = new JSONObject();
		InterfaceConfig interfaceConfig = (InterfaceConfig) dao.query("from InterfaceConfig ic where ic.serviceName = 'SinossWebService' and ic.methodName = 'requestProjectApplicationResult'").get(0);
		String options = interfaceConfig.getOptions();
		Map opMap = JSONObject.fromObject(options);
		if ((Integer) opMap.get("startYear") != -1) {
			hql.append(" and app.year>=:startYear ");
			map.put("startYear", (Integer) opMap.get("startYear"));
		}
		if ((Integer) opMap.get("endYear") != -1) {
			hql.append(" and app.year<=:endYear ");
			map.put("endYear", (Integer) opMap.get("endYear"));
		}
		if(opMap.get("applyStartDate") != null) {
			hql.append(" and app.applicantSubmitDate is not null and to_char(app.applicantSubmitDate,'yyyy-MM-dd')>=:applyStartDate");
			map.put("applyStartDate", opMap.get("applyStartDate"));
		}
		if(opMap.get("applyEndDate") != null) {
			hql.append(" and app.applicantSubmitDate is not null and to_char(app.applicantSubmitDate,'yyyy-MM-dd')<=:applyEndDate");
			map.put("applyEndDate", opMap.get("applyEndDate"));
		}
		if(opMap.get("auditStartDate") != null) {
			hql.append(" and app.finalAuditDate is not null and to_char(app.finalAuditDate,'yyyy-MM-dd')>=:auditStartDate");
			map.put("auditStartDate", opMap.get("auditStartDate"));
		}
		if(opMap.get("auditEndDate") != null) {
			hql.append(" and app.finalAuditDate is not null and to_char(app.finalAuditDate,'yyyy-MM-dd')<=:auditEndDate");
			map.put("auditEndDate", opMap.get("auditEndDate"));
		}
		hql.append(" and app.finalAuditResult !=0");
		hql.append(" and app.finalAuditResultPublish = 1");
		//注意这里的项目类型名称type
		if(!projectType.equals("general") && !projectType.equals("instp")) {
			return responseContent("error", "接口调用参数有误");
		} else if(projectType.equals("general")) {
			hql.append(" and app.type = 'general'");
		} else if(projectType.equals("instp")) {
			hql.append(" and app.type = 'instp'");
		}
		if(flag) {
			hql.append(" and app.id not in (select s.projectId from SyncStatus s where s.peer = :peer and s.interfaceName = 'requestProjectApplicationResult')");
		} else {
			hql.append(" and app.id in (select s.projectId from SyncStatus s where s.status = 0 and s.peer = :peer and s.interfaceName = 'requestProjectApplicationResult')");
		}
		List<Object[]> list = dao.query(hql.toString(), map, 0, length);
		if(list.isEmpty()) {
			return responseContent("notice", "已同步完成");
		}
		Document document = DocumentHelper.createDocument();
		Element content = document.addElement("content");
		for (Object[] o : list) {
			String[] str = new String[5];
			for (int i = 0; i < o.length; i++) {
				if (o[i] instanceof String){
					str[i] = (String) o[i];
				} else if (o[i] instanceof Integer) {
					int temp = (Integer) o[i];
					str[i] = String.valueOf(temp);
				} else if (o[i] instanceof Double) {
					str[i] = String.valueOf(o[i]);
				} else if (o[i] == null) {
					str[i] = "null";//当对象为空时
				}
			}	
			Element result = content.addElement("item");
			Element projectId = result.addElement("projectId");//管理数据库项目id
			projectId.setText(str[0]);
			Element sinossProjectId =  result.addElement("sinossProjectId");//管理平台项目id
			sinossProjectId.setText(str[1]);
			Element applicationResult =  result.addElement("applicationResult");//申请审核结果
			applicationResult.setText(str[2]);
			if(str[2].equals("2")) {
				Element projectNumber =  result.addElement("projectNumber");//项目批准号
				projectNumber.setText(str[3]);	
				Element projectFee =  result.addElement("projectFee");//项目批准经费
				projectFee.setText(str[4]);					
			}
			if(flag) {
				SyncStatus syncStatus = new SyncStatus();
				syncStatus.setPeer(peer);
				syncStatus.setInterfaceName("requestProjectApplicationResult");
				syncStatus.setProjectId(str[0]);
				syncStatus.setRequestDate(requestDate);
				syncStatus.setRequestAccount(account);
				syncStatus.setRequestAccountBelong(requestAccountBelong);
				syncStatus.setStatus(0);
				syncStatusList.add(syncStatus);
			}
		}
		if(flag) {
			dao.add(syncStatusList);			
		}
		Long end = System.currentTimeMillis();
		System.out.println("此次同步" + list.size() + "条数据总耗时 :" + (end - begin) + "ms");
		return responseContent("data", document);
	}
	
	public String requestProjectMidinspectionRequired(String projectType, int length, Account account, String requestAccountBelong) {
		Long begin = System.currentTimeMillis();
		Map sMap = new HashMap();
		Map map = new HashMap();
		String peer = getPeerByAccount(account);
		map.put("peer", peer);
		String belongId = projectService.getBelongIdByAccount(account);
		AccountType type = account.getType();
		int isPrincipal = account.getIsPrincipal();
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		sMap.put("peer", peer);
		List synList = dao.query("from SyncStatus s where s.status = 0 and s.peer = :peer and s.interfaceName != 'requestProjectMidinspectionRequired'", sMap);
		if(!synList.isEmpty()) {
			return responseContent("error", "您上一次访问的服务接口，还有未应答项目");
		}
		List<String> projectIdList = dao.query("select s.projectId from SyncStatus s where s.status = 0 and s.peer = :peer and s.interfaceName = 'requestProjectMidinspectionRequired'", sMap);
		Boolean flag = true;//标志是否已将本次同步数据加入同步状态表，每条数据仅在同步状态表中存在一条
		if(!projectIdList.isEmpty()) {
			flag = false;
		}
		Date requestDate = new Date();
		List<SyncStatus> syncStatusList = new ArrayList<SyncStatus>();
		StringBuffer hql = new StringBuffer();
		hql.append("select gra.applicationId, gra.name, gra.number, uni.name, uni.code, gra.applicantName, gra.productType ");
		if(account.getType().within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
			hql.append("from ProjectGranted gra, ProjectMember mem, ProjectApplication app join gra.university uni " +
					"where mem.application.id=app.id and gra.applicationId = app.id ");
		}else{//管理人员
			hql.append("from ProjectGranted gra, ProjectApplication app join gra.university uni " +
					"where gra.applicationId = app.id ");
		}
		if (type.equals(AccountType.ADMINISTRATOR)) {
		}// 系统管理员
		else if (type.equals(AccountType.MINISTRY)) {// 部级
			if (isPrincipal == 1) {// 主账号
			} else {// 子账号
				String ministryId = projectService.getAgencyIdByOfficerId(belongId);
				if (ministryId != null) {
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if (type.equals(AccountType.PROVINCE)) {// 省级
			if (isPrincipal == 1) {// 主账号
				hql.append(" and uni.type=4 and uni.subjection.id=:belongId");
				map.put("belongId", belongId);
			} else {// 子账号
				String provinceId = projectService.getAgencyIdByOfficerId(belongId);
				if (provinceId != null) {
					hql.append(" and uni.type=4 and uni.subjection.id=:provinceId");
					map.put("provinceId", provinceId);
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if (type.equals(AccountType.MINISTRY_UNIVERSITY) || type.equals(AccountType.LOCAL_UNIVERSITY)) {// 部属高校与地方高校
			if (isPrincipal == 1) {// 主账号
				hql.append(" and uni.id=:belongId");
				map.put("belongId", belongId);
			} else {// 子账号
				String universityId = projectService.getAgencyIdByOfficerId(belongId);
				if (universityId != null) {
					hql.append(" and uni.id=:universityId");
					map.put("universityId", universityId);
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if (type.equals(AccountType.DEPARTMENT)) {// 院系
			if (isPrincipal == 1) {// 主账号
				hql.append(" and gra.department.id=:belongId");
				map.put("belongId", belongId);
			} else {// 子账号
				String departmentId = projectService.getDepartmentIdByOfficerId(belongId);
				if (departmentId != null) {
					hql.append(" and gra.department.id=:departmentId");
					map.put("departmentId", departmentId);
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if (type.equals(AccountType.INSTITUTE)) {// 研究机构
			if (isPrincipal == 1) {// 主账号
				hql.append(" and gra.institute.id=:belongId");
				map.put("belongId", belongId);
			} else {// 子账号
				String instituteId = projectService.getInstituteIdByOfficerId(belongId);
				if (instituteId != null) {
					hql.append(" and gra.institute.id=:instituteId");
					map.put("instituteId", instituteId);
				} else {
					hql.append(" and 1=0");
				}
			}

		} else if (type.within(AccountType.EXPERT, AccountType.STUDENT)) {// 外部专家与内部专家或学生
			hql.append(" and mem.member.id=:belongId and mem.groupNumber = gra.memberGroupNumber");
			map.put("belongId", belongId);
		} else {
			hql.append(" and 1=0");
		}		
		JSONObject mapObj = new JSONObject();
		InterfaceConfig interfaceConfig = (InterfaceConfig) dao.query("from InterfaceConfig ic where ic.serviceName = 'SinossWebService' and ic.methodName = 'requestProjectMidinspectionRequired'").get(0);
		String options = interfaceConfig.getOptions();
		Map opMap = JSONObject.fromObject(options);	
		if ((Integer) opMap.get("startYear") != -1) {
			hql.append(" and app.year>=:startYear ");
			map.put("startYear", (Integer) opMap.get("startYear"));
		}
		if ((Integer) opMap.get("endYear") != -1) {
			hql.append(" and app.year<=:endYear ");
			map.put("endYear", (Integer) opMap.get("endYear"));
		}
		int numberTemp = Integer.parseInt(year.substring(year.length()- 2, year.length()));//项目批准号条件拼接
		hql.append(" and (gra.number like '" + (numberTemp - 3) + "%' or gra.number like '" + (numberTemp - 2) + "%')");
		hql.append(" and not exists(from ProjectGranted gra1 where gra.id = gra1.id and gra1.subtype.id = '20120618173132686vcroipsnrftirrd')");
		hql.append(" and gra.status = 1");
		if(!projectType.equals("general") && !projectType.equals("instp")) {
			return responseContent("error", "接口调用参数有误");
		} else if(projectType.equals("general")) {
			hql.append(" and gra.projectType = 'general'");
		} else if(projectType.equals("instp")) {
			hql.append(" and gra.projectType = 'instp'");
		}
		hql.append(" and not exists(from ProjectMidinspection mid where mid.grantedId = gra.id and mid.finalAuditResult = 2)");
		if(flag) {
			hql.append(" and gra.applicationId not in (select s.projectId from SyncStatus s where s.peer = :peer and s.interfaceName = 'requestProjectMidinspectionRequired')");
		} else {
			hql.append(" and gra.applicationId in (select s.projectId from SyncStatus s where s.status = 0 and s.peer = :peer and s.interfaceName = 'requestProjectMidinspectionRequired')");
		}
		List<Object[]> list = dao.query(hql.toString(), map, 0, length);
		if(list.isEmpty()) {
			return responseContent("notice", "已同步完成");
		}
		Document document = DocumentHelper.createDocument();
		Element content = document.addElement("content");
		for (Object[] o : list) {
			String[] str = new String[7];
			for (int i = 0; i < o.length; i++) {
				if (o[i] instanceof String){
					str[i] = (String) o[i];
				} else if (o[i] instanceof Integer) {
					int temp = (Integer) o[i];
					str[i] = String.valueOf(temp);
				} else if (o[i] == null) {
					str[i] = "null";//当对象为空时
				}
			}	
			Element result = content.addElement("item");
			Element projectId = result.addElement("projectId");//管理数据库项目id
			projectId.setText(str[0]);
			Element projectName = result.addElement("projectName");//项目名称
			projectName.setText(str[1]);
			Element number =  result.addElement("number");//项目批准号
			number.setText(str[2]);			
			Element universityName =  result.addElement("universityName");//高校名称
			universityName.setText(str[3]);
			Element universityCode =  result.addElement("universityCode");//高校名称
			universityCode.setText(str[4]);
			Element applicantName = result.addElement("applicantName");//申请人姓名
			applicantName.setText(str[5]);
			Element email =  result.addElement("productType");//最终成果形式
			email.setText(str[6]);
			if(flag) {
				SyncStatus syncStatus = new SyncStatus();
				syncStatus.setPeer(peer);
				syncStatus.setInterfaceName("requestProjectMidinspectionRequired");
				syncStatus.setProjectId(str[0]);
				syncStatus.setRequestDate(requestDate);
				syncStatus.setRequestAccount(account);
				syncStatus.setRequestAccountBelong(requestAccountBelong);
				syncStatus.setStatus(0);
				syncStatusList.add(syncStatus);				
			}
		}
		if(flag) {
			dao.add(syncStatusList);			
		}
		Long end = System.currentTimeMillis();
		System.out.println("此次同步" + list.size() + "条数据总耗时 :" + (end - begin) + "ms");
		return responseContent("data", document);
	}
	
	public String requestProjectMidinspectionDeferResult(String projectType, int length, Account account, String requestAccountBelong) {
		Long begin = System.currentTimeMillis();
		Map sMap = new HashMap();
		Map map = new HashMap();
		String peer = getPeerByAccount(account);
		map.put("peer", peer);
		String belongId = projectService.getBelongIdByAccount(account);
		AccountType type = account.getType();
		int isPrincipal = account.getIsPrincipal();
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		sMap.put("peer", peer);
		List synList = dao.query("from SyncStatus s where s.status = 0 and s.peer = :peer and s.interfaceName != 'requestProjectMidinspectionDeferResult'", sMap);
		if(!synList.isEmpty()) {
			return responseContent("error", "您上一次访问的服务接口，还有未应答项目");
		}
		List<String> midinspectionIdList = dao.query("select s.variationId from SyncStatus s where s.status = 0 and s.peer = :peer and s.interfaceName = 'requestProjectMidinspectionDeferResult'", sMap);
		Boolean flag = true;//标志是否已将本次同步数据加入同步状态表，每条数据仅在同步状态表中存在一条
		if(!midinspectionIdList.isEmpty()) {
			flag = false;
		}
		Date requestDate = new Date();
		List<SyncStatus> syncStatusList = new ArrayList<SyncStatus>();
		StringBuffer hql = new StringBuffer();
		hql.append("select app.id, var.id, spm.sinossProjectId, spm.sinossId, var.finalAuditResult, var.finalAuditResultDetail ");
		if(account.getType().within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
			hql.append("from SinossProjectMidinspection spm,ProjectMember mem left join spm.projectVariation var left join spm.projectApplication app left join spm.projectGranted gra left join gra.university uni " +
					"where mem.application.id=app.id ");
		}else{//管理人员
			hql.append("from SinossProjectMidinspection spm left join spm.projectVariation var left join spm.projectApplication app left join spm.projectGranted gra left join gra.university uni " +
					"where 1=1 ");
		}
		if (type.equals(AccountType.ADMINISTRATOR)) {
		}// 系统管理员
		else if (type.equals(AccountType.MINISTRY)) {// 部级
			if (isPrincipal == 1) {// 主账号
			} else {// 子账号
				String ministryId = projectService.getAgencyIdByOfficerId(belongId);
				if (ministryId != null) {
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if (type.equals(AccountType.PROVINCE)) {// 省级
			if (isPrincipal == 1) {// 主账号
				hql.append(" and uni.type=4 and uni.subjection.id=:belongId");
				map.put("belongId", belongId);
			} else {// 子账号
				String provinceId = projectService.getAgencyIdByOfficerId(belongId);
				if (provinceId != null) {
					hql.append(" and uni.type=4 and uni.subjection.id=:provinceId");
					map.put("provinceId", provinceId);
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if (type.equals(AccountType.MINISTRY_UNIVERSITY) || type.equals(AccountType.LOCAL_UNIVERSITY)) {// 部属高校与地方高校
			if (isPrincipal == 1) {// 主账号
				hql.append(" and uni.id=:belongId");
				map.put("belongId", belongId);
			} else {// 子账号
				String universityId = projectService.getAgencyIdByOfficerId(belongId);
				if (universityId != null) {
					hql.append(" and uni.id=:universityId");
					map.put("universityId", universityId);
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if (type.equals(AccountType.DEPARTMENT)) {// 院系
			if (isPrincipal == 1) {// 主账号
				hql.append(" and gra.department.id=:belongId");
				map.put("belongId", belongId);
			} else {// 子账号
				String departmentId = projectService.getDepartmentIdByOfficerId(belongId);
				if (departmentId != null) {
					hql.append(" and gra.department.id=:departmentId");
					map.put("departmentId", departmentId);
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if (type.equals(AccountType.INSTITUTE)) {// 研究机构
			if (isPrincipal == 1) {// 主账号
				hql.append(" and gra.institute.id=:belongId");
				map.put("belongId", belongId);
			} else {// 子账号
				String instituteId = projectService.getInstituteIdByOfficerId(belongId);
				if (instituteId != null) {
					hql.append(" and gra.institute.id=:instituteId");
					map.put("instituteId", instituteId);
				} else {
					hql.append(" and 1=0");
				}
			}

		} else if (type.within(AccountType.EXPERT, AccountType.STUDENT)) {// 外部专家与内部专家或学生
			hql.append(" and mem.member.id=:belongId and mem.groupNumber = gra.memberGroupNumber");
			map.put("belongId", belongId);
		} else {
			hql.append(" and 1=0");
		}		
		JSONObject mapObj = new JSONObject();
		//中检延期接口配置与中检接口配置公用
		InterfaceConfig interfaceConfig = (InterfaceConfig) dao.query("from InterfaceConfig ic where ic.serviceName = 'SinossWebService' and ic.methodName = 'requestProjectMidinspectionResult'").get(0);
		String options = interfaceConfig.getOptions();
		Map opMap = JSONObject.fromObject(options);	
		if ((Integer) opMap.get("startYear") != -1) {
			hql.append(" and app.year>=:startYear ");
			map.put("startYear", (Integer) opMap.get("startYear"));
		}
		if ((Integer) opMap.get("endYear") != -1) {
			hql.append(" and app.year<=:endYear ");
			map.put("endYear", (Integer) opMap.get("endYear"));
		}
		if(opMap.get("applyStartDate") != null) {
			hql.append(" and var.applicantSubmitDate is not null and to_char(var.applicantSubmitDate,'yyyy-MM-dd')>=:applyStartDate");
			map.put("applyStartDate", opMap.get("applyStartDate"));
		}
		if(opMap.get("applyEndDate") != null) {
			hql.append(" and var.applicantSubmitDate is not null and to_char(var.applicantSubmitDate,'yyyy-MM-dd')<=:applyEndDate");
			map.put("applyEndDate", opMap.get("applyEndDate"));
		}
		if(opMap.get("auditStartDate") != null) {
			hql.append(" and var.finalAuditDate is not null and to_char(var.finalAuditDate,'yyyy-MM-dd')>=:auditStartDate");
			map.put("auditStartDate", opMap.get("auditStartDate"));
		}
		if(opMap.get("auditEndDate") != null) {
			hql.append(" and var.finalAuditDate is not null and to_char(var.finalAuditDate,'yyyy-MM-dd')<=:auditEndDate");
			map.put("auditEndDate", opMap.get("auditEndDate"));
		}	
		hql.append(" and var.finalAuditResult !=0");
		hql.append(" and var.finalAuditResultPublish = 1");
		if(!projectType.equals("general") && !projectType.equals("instp")) {
			return responseContent("error", "接口调用参数有误");
		} else if(projectType.equals("general")) {
			hql.append(" and var.projectType = 'general'");
		} else if(projectType.equals("instp")) {
			hql.append(" and var.projectType = 'instp'");
		}
		if(flag) {
			hql.append(" and var.id not in (select s.variationId from SyncStatus s where s.peer = :peer and s.interfaceName = 'requestProjectMidinspectionDeferResult')");
		} else {
			hql.append(" and var.id in (select s.variationId from SyncStatus s where s.status = 0 and s.peer = :peer and s.interfaceName = 'requestProjectMidinspectionDeferResult')");
		}
		List<Object[]> list = dao.query(hql.toString(), map, 0, length);
		if(list.isEmpty()) {
			return responseContent("notice", "已同步完成");
		}	
		Document document = DocumentHelper.createDocument();
		Element content = document.addElement("content");
		for (Object[] o : list) {
			String[] str = new String[6];
			for (int i = 0; i < o.length; i++) {
				if (o[i] instanceof String){
					str[i] = (String) o[i];
				} else if (o[i] instanceof Integer) {
					int temp = (Integer) o[i];
					str[i] = String.valueOf(temp);
				} else if (o[i] == null) {
					str[i] = "null";//当对象为空时
				}
			}
			Element result = content.addElement("item");
			Element projectId = result.addElement("projectId");//管理数据库项目id
			projectId.setText(str[0]);
			Element midinspectionId =  result.addElement("variationId");//管理数据库变更id
			midinspectionId.setText(str[1]);
			Element sinossProjectId =  result.addElement("sinossProjectId");//管理平台项目id
			sinossProjectId.setText(str[2]);			
			Element sinossMidinspectionId =  result.addElement("sinossMidinspectionId");//管理平台中检id
			sinossMidinspectionId.setText(str[3]);	
			Element midinspectionResult =  result.addElement("midinspectionResult");//中检延期审核结果
			midinspectionResult.setText(str[4]);
			if(flag) {
				SyncStatus syncStatus = new SyncStatus();
				syncStatus.setPeer(peer);
				syncStatus.setInterfaceName("requestProjectMidinspectionDeferResult");
				syncStatus.setVariationId(str[1]);
				syncStatus.setRequestDate(requestDate);
				syncStatus.setRequestAccount(account);
				syncStatus.setRequestAccountBelong(requestAccountBelong);
				syncStatus.setStatus(0);
				syncStatusList.add(syncStatus);
			}
		}
		if(flag) {
			dao.add(syncStatusList);
		}
		Long end = System.currentTimeMillis();
		System.out.println("此次同步" + list.size() + "条数据总耗时 :" + (end - begin) + "ms");
		return responseContent("data", document);
	} 
	
	public String requestProjectMidinspectionResult(String projectType, int length, Account account, String requestAccountBelong) {
		Long begin = System.currentTimeMillis();
		Map sMap = new HashMap();
		Map map = new HashMap();
		String peer = getPeerByAccount(account);
		map.put("peer", peer);
		String belongId = projectService.getBelongIdByAccount(account);
		AccountType type = account.getType();
		int isPrincipal = account.getIsPrincipal();
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		sMap.put("peer", peer);
		List synList = dao.query("from SyncStatus s where s.status = 0 and s.peer = :peer and s.interfaceName != 'requestProjectMidinspectionResult'", sMap);
		if(!synList.isEmpty()) {
			return responseContent("error", "您上一次访问的服务接口，还有未应答项目");
		}
		List<String> midinspectionIdList = dao.query("select s.midinspectionId from SyncStatus s where s.status = 0 and s.peer = :peer and s.interfaceName = 'requestProjectMidinspectionResult'", sMap);
		Boolean flag = true;//标志是否已将本次同步数据加入同步状态表，每条数据仅在同步状态表中存在一条
		if(!midinspectionIdList.isEmpty()) {
			flag = false;
		}
		Date requestDate = new Date();
		List<SyncStatus> syncStatusList = new ArrayList<SyncStatus>();
		StringBuffer hql = new StringBuffer();
		hql.append("select app.id, mid.id, spm.sinossProjectId, spm.sinossId, mid.finalAuditResult ");
		if(account.getType().within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
			hql.append("from SinossProjectMidinspection spm,ProjectMember mem left join spm.projectMidinspection mid left join spm.projectApplication app left join spm.projectGranted gra left join gra.university uni " +
					"where mem.application.id=app.id ");
		}else{//管理人员
			hql.append("from SinossProjectMidinspection spm left join spm.projectMidinspection mid left join spm.projectApplication app left join spm.projectGranted gra left join gra.university uni " +
					"where 1=1 ");
		}
		if (type.equals(AccountType.ADMINISTRATOR)) {
		}// 系统管理员
		else if (type.equals(AccountType.MINISTRY)) {// 部级
			if (isPrincipal == 1) {// 主账号
			} else {// 子账号
				String ministryId = projectService.getAgencyIdByOfficerId(belongId);
				if (ministryId != null) {
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if (type.equals(AccountType.PROVINCE)) {// 省级
			if (isPrincipal == 1) {// 主账号
				hql.append(" and uni.type=4 and uni.subjection.id=:belongId");
				map.put("belongId", belongId);
			} else {// 子账号
				String provinceId = projectService.getAgencyIdByOfficerId(belongId);
				if (provinceId != null) {
					hql.append(" and uni.type=4 and uni.subjection.id=:provinceId");
					map.put("provinceId", provinceId);
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if (type.equals(AccountType.MINISTRY_UNIVERSITY) || type.equals(AccountType.LOCAL_UNIVERSITY)) {// 部属高校与地方高校
			if (isPrincipal == 1) {// 主账号
				hql.append(" and uni.id=:belongId");
				map.put("belongId", belongId);
			} else {// 子账号
				String universityId = projectService.getAgencyIdByOfficerId(belongId);
				if (universityId != null) {
					hql.append(" and uni.id=:universityId");
					map.put("universityId", universityId);
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if (type.equals(AccountType.DEPARTMENT)) {// 院系
			if (isPrincipal == 1) {// 主账号
				hql.append(" and gra.department.id=:belongId");
				map.put("belongId", belongId);
			} else {// 子账号
				String departmentId = projectService.getDepartmentIdByOfficerId(belongId);
				if (departmentId != null) {
					hql.append(" and gra.department.id=:departmentId");
					map.put("departmentId", departmentId);
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if (type.equals(AccountType.INSTITUTE)) {// 研究机构
			if (isPrincipal == 1) {// 主账号
				hql.append(" and gra.institute.id=:belongId");
				map.put("belongId", belongId);
			} else {// 子账号
				String instituteId = projectService.getInstituteIdByOfficerId(belongId);
				if (instituteId != null) {
					hql.append(" and gra.institute.id=:instituteId");
					map.put("instituteId", instituteId);
				} else {
					hql.append(" and 1=0");
				}
			}

		} else if (type.within(AccountType.EXPERT, AccountType.STUDENT)) {// 外部专家与内部专家或学生
			hql.append(" and mem.member.id=:belongId and mem.groupNumber = gra.memberGroupNumber");
			map.put("belongId", belongId);
		} else {
			hql.append(" and 1=0");
		}		
		JSONObject mapObj = new JSONObject();
		InterfaceConfig interfaceConfig = (InterfaceConfig) dao.query("from InterfaceConfig ic where ic.serviceName = 'SinossWebService' and ic.methodName = 'requestProjectMidinspectionResult'").get(0);
		String options = interfaceConfig.getOptions();
		Map opMap = JSONObject.fromObject(options);	
		if ((Integer) opMap.get("startYear") != -1) {
			hql.append(" and app.year>=:startYear ");
			map.put("startYear", (Integer) opMap.get("startYear"));
		}
		if ((Integer) opMap.get("endYear") != -1) {
			hql.append(" and app.year<=:endYear ");
			map.put("endYear", (Integer) opMap.get("endYear"));
		}
		if(opMap.get("applyStartDate") != null) {
			hql.append(" and mid.applicantSubmitDate is not null and to_char(mid.applicantSubmitDate,'yyyy-MM-dd')>=:applyStartDate");
			map.put("applyStartDate", opMap.get("applyStartDate"));
		}
		if(opMap.get("applyEndDate") != null) {
			hql.append(" and mid.applicantSubmitDate is not null and to_char(mid.applicantSubmitDate,'yyyy-MM-dd')<=:applyEndDate");
			map.put("applyEndDate", opMap.get("applyEndDate"));
		}
		if(opMap.get("auditStartDate") != null) {
			hql.append(" and mid.finalAuditDate is not null and to_char(mid.finalAuditDate,'yyyy-MM-dd')>=:auditStartDate");
			map.put("auditStartDate", opMap.get("auditStartDate"));
		}
		if(opMap.get("auditEndDate") != null) {
			hql.append(" and mid.finalAuditDate is not null and to_char(mid.finalAuditDate,'yyyy-MM-dd')<=:auditEndDate");
			map.put("auditEndDate", opMap.get("auditEndDate"));
		}	
		hql.append(" and mid.finalAuditResult !=0");
		hql.append(" and mid.finalAuditResultPublish = 1");
		if(!projectType.equals("general") && !projectType.equals("instp")) {
			return responseContent("error", "接口调用参数有误");
		} else if(projectType.equals("general")) {
			hql.append(" and mid.projectType = 'general'");
		} else if(projectType.equals("instp")) {
			hql.append(" and mid.projectType = 'instp'");
		}
		if(flag) {
			hql.append(" and mid.id not in (select s.midinspectionId from SyncStatus s where s.peer = :peer and s.interfaceName = 'requestProjectMidinspectionResult')");
		} else {
			hql.append(" and mid.id in (select s.midinspectionId from SyncStatus s where s.status = 0 and s.peer = :peer and s.interfaceName = 'requestProjectMidinspectionResult')");
		}
		List<Object[]> list = dao.query(hql.toString(), map, 0, length);
		if(list.isEmpty()) {
			return responseContent("notice", "已同步完成");
		}	
		Document document = DocumentHelper.createDocument();
		Element content = document.addElement("content");
		for (Object[] o : list) {
			String[] str = new String[5];
			for (int i = 0; i < o.length; i++) {
				if (o[i] instanceof String){
					str[i] = (String) o[i];
				} else if (o[i] instanceof Integer) {
					int temp = (Integer) o[i];
					str[i] = String.valueOf(temp);
				} else if (o[i] == null) {
					str[i] = "null";//当对象为空时
				}
			}	
			Element result = content.addElement("item");
			Element projectId = result.addElement("projectId");//管理数据库项目id
			projectId.setText(str[0]);
			Element midinspectionId =  result.addElement("midinspectionId");//管理数据库中检id
			midinspectionId.setText(str[1]);
			Element sinossProjectId =  result.addElement("sinossProjectId");//管理平台项目id
			sinossProjectId.setText(str[2]);			
			Element sinossMidinspectionId =  result.addElement("sinossMidinspectionId");//管理平台中检id
			sinossMidinspectionId.setText(str[3]);	
			Element midinspectionResult =  result.addElement("midinspectionResult");//变更审核结果
			midinspectionResult.setText(str[4]);
			if(flag) {
				SyncStatus syncStatus = new SyncStatus();
				syncStatus.setPeer(peer);
				syncStatus.setInterfaceName("requestProjectMidinspectionResult");
				syncStatus.setMidinspectionId(str[1]);
				syncStatus.setRequestDate(requestDate);
				syncStatus.setRequestAccount(account);
				syncStatus.setRequestAccountBelong(requestAccountBelong);
				syncStatus.setStatus(0);
				syncStatusList.add(syncStatus);
			}
		}
		if(flag) {
			dao.add(syncStatusList);
		}
		Long end = System.currentTimeMillis();
		System.out.println("此次同步" + list.size() + "条数据总耗时 :" + (end - begin) + "ms");
		return responseContent("data", document);
	}
	
	public String requestProjectVariationResult(String projectType, int length, Account account, String requestAccountBelong) {
		Long begin = System.currentTimeMillis();
		Map sMap = new HashMap();
		Map map = new HashMap();
		String peer = getPeerByAccount(account);
		map.put("peer", peer);
		String belongId = projectService.getBelongIdByAccount(account);
		AccountType type = account.getType();
		int isPrincipal = account.getIsPrincipal();
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		sMap.put("peer", peer);
		List synList = dao.query("from SyncStatus s where s.status = 0 and s.peer = :peer and s.interfaceName != 'requestProjectVariationResult'", sMap);
		if(!synList.isEmpty()) {
			return responseContent("error", "您上一次访问的服务接口，还有未应答项目");
		}
		List<String> variationIdList = dao.query("select s.variationId from SyncStatus s where s.status = 0 and s.peer = :peer and s.interfaceName = 'requestProjectVariationResult'", sMap);
		Boolean flag = true;//标志是否已将本次同步数据加入同步状态表，每条数据仅在同步状态表中存在一条
		if(!variationIdList.isEmpty()) {
			flag = false;
		}
		Date requestDate = new Date();
		List<SyncStatus> syncStatusList = new ArrayList<SyncStatus>();
		StringBuffer hql = new StringBuffer();		
		hql.append("select app.id, var.id, spv.sinossProjectId, spv.sinossId, var.finalAuditResult, var.finalAuditResultDetail ");
		if(account.getType().within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
			hql.append("from SinossProjectVariation spv,ProjectMember mem left join spv.projectVariation var left join spv.projectApplication app left join spv.projectGranted gra left join gra.university uni " +
					"where mem.application.id=app.id ");
		}else{//管理人员
			hql.append("from SinossProjectVariation spv left join spv.projectVariation var left join spv.projectApplication app left join spv.projectGranted gra left join gra.university uni " +
					"where 1=1 ");
		}
		if (type.equals(AccountType.ADMINISTRATOR)) {
		}// 系统管理员
		else if (type.equals(AccountType.MINISTRY)) {// 部级
			if (isPrincipal == 1) {// 主账号
			} else {// 子账号
				String ministryId = projectService.getAgencyIdByOfficerId(belongId);
				if (ministryId != null) {
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if (type.equals(AccountType.PROVINCE)) {// 省级
			if (isPrincipal == 1) {// 主账号
				hql.append(" and uni.type=4 and uni.subjection.id=:belongId");
				map.put("belongId", belongId);
			} else {// 子账号
				String provinceId = projectService.getAgencyIdByOfficerId(belongId);
				if (provinceId != null) {
					hql.append(" and uni.type=4 and uni.subjection.id=:provinceId");
					map.put("provinceId", provinceId);
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if (type.equals(AccountType.MINISTRY_UNIVERSITY) || type.equals(AccountType.LOCAL_UNIVERSITY)) {// 部属高校与地方高校
			if (isPrincipal == 1) {// 主账号
				hql.append(" and uni.id=:belongId");
				map.put("belongId", belongId);
			} else {// 子账号
				String universityId = projectService.getAgencyIdByOfficerId(belongId);
				if (universityId != null) {
					hql.append(" and uni.id=:universityId");
					map.put("universityId", universityId);
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if (type.equals(AccountType.DEPARTMENT)) {// 院系
			if (isPrincipal == 1) {// 主账号
				hql.append(" and gra.department.id=:belongId");
				map.put("belongId", belongId);
			} else {// 子账号
				String departmentId = projectService.getDepartmentIdByOfficerId(belongId);
				if (departmentId != null) {
					hql.append(" and gra.department.id=:departmentId");
					map.put("departmentId", departmentId);
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if (type.equals(AccountType.INSTITUTE)) {// 研究机构
			if (isPrincipal == 1) {// 主账号
				hql.append(" and gra.institute.id=:belongId");
				map.put("belongId", belongId);
			} else {// 子账号
				String instituteId = projectService.getInstituteIdByOfficerId(belongId);
				if (instituteId != null) {
					hql.append(" and gra.institute.id=:instituteId");
					map.put("instituteId", instituteId);
				} else {
					hql.append(" and 1=0");
				}
			}

		} else if (type.within(AccountType.EXPERT, AccountType.STUDENT)) {// 外部专家与内部专家或学生
			hql.append(" and mem.member.id=:belongId and mem.groupNumber = gra.memberGroupNumber");
			map.put("belongId", belongId);
		} else {
			hql.append(" and 1=0");
		}		
		JSONObject mapObj = new JSONObject();
		InterfaceConfig interfaceConfig = (InterfaceConfig) dao.query("from InterfaceConfig ic where ic.serviceName = 'SinossWebService' and ic.methodName = 'requestProjectVariationResult'").get(0);
		String options = interfaceConfig.getOptions();
		Map opMap = JSONObject.fromObject(options);	
		if ((Integer) opMap.get("startYear") != -1) {
			hql.append(" and app.year>=:startYear ");
			map.put("startYear", (Integer) opMap.get("startYear"));
		}
		if ((Integer) opMap.get("endYear") != -1) {
			hql.append(" and app.year<=:endYear ");
			map.put("endYear", (Integer) opMap.get("endYear"));
		}
		if(opMap.get("applyStartDate") != null) {
			hql.append(" and var.applicantSubmitDate is not null and to_char(var.applicantSubmitDate,'yyyy-MM-dd')>=:applyStartDate");
			map.put("applyStartDate", opMap.get("applyStartDate"));
		}
		if(opMap.get("applyEndDate") != null) {
			hql.append(" and var.applicantSubmitDate is not null and to_char(var.applicantSubmitDate,'yyyy-MM-dd')<=:applyEndDate");
			map.put("applyEndDate", opMap.get("applyEndDate"));
		}
		if(opMap.get("auditStartDate") != null) {
			hql.append(" and var.finalAuditDate is not null and to_char(var.finalAuditDate,'yyyy-MM-dd')>=:auditStartDate");
			map.put("auditStartDate", opMap.get("auditStartDate"));
		}
		if(opMap.get("auditEndDate") != null) {
			hql.append(" and var.finalAuditDate is not null and to_char(var.finalAuditDate,'yyyy-MM-dd')<=:auditEndDate");
			map.put("auditEndDate", opMap.get("auditEndDate"));
		}
		hql.append(" and var.finalAuditResult !=0");
		hql.append(" and var.finalAuditResultPublish = 1");
		//根据项目类型从数据库获取变更数据
		if(!projectType.equals("general") && !projectType.equals("instp")) {
			return responseContent("error", "接口调用参数有误");
		} else if(projectType.equals("general")) {
			hql.append(" and var.projectType = 'general'");
		} else if(projectType.equals("instp")) {
			hql.append(" and var.projectType = 'instp'");
		}
		if(flag) {
			hql.append(" and var.id not in (select s.variationId from SyncStatus s where s.peer = :peer and s.interfaceName = 'requestProjectVariationResult')");
		} else {
			hql.append(" and var.id in (select s.variationId from SyncStatus s where s.status = 0 and s.peer = :peer and s.interfaceName = 'requestProjectVariationResult')");
		}
		List<Object[]> list = dao.query(hql.toString(), map, 0, length);
		if(list.isEmpty()) {
			return responseContent("notice", "已同步完成");
		}		
		//将list中的查询结果数据组装成xml数据的document对象
		Document document = DocumentHelper.createDocument();
		Element content = document.addElement("content");
		for (Object[] o : list) {
			String[] str = new String[6];
			for (int i = 0; i < o.length; i++) {
				if (o[i] instanceof String){
					str[i] = (String) o[i];
				} else if (o[i] instanceof Integer) {
					int temp = (Integer) o[i];
					str[i] = String.valueOf(temp);
				} else if (o[i] == null) {
					str[i] = "null";//当对象为空时
				}
			}			
			Element result = content.addElement("item");
			Element projectId = result.addElement("projectId");//管理数据库项目id
			projectId.setText(str[0]);
			Element variationId =  result.addElement("variationId");//管理数据库变更id
			variationId.setText(str[1]);
			Element sinossProjectId =  result.addElement("sinossProjectId");//管理平台项目id
			sinossProjectId.setText(str[2]);			
			Element sinossVariationId =  result.addElement("sinossVariationId");//管理平台变更id
			sinossVariationId.setText(str[3]);			
			Element variationResult =  result.addElement("variationResult");//变更审核结果
			variationResult.setText(str[4]);
			if(str[5] != null) {
				StringBuffer item = new StringBuffer();
				for(int i=0;i<str[5].length();i++) {
					char s = str[5].charAt(i);
					if(s == 49) {//unicode中代表1
						switch (i) {
						case 0:
							item.append("变更项目成员（含负责人）;");
							break;
						case 1:
							item.append("变更项目管理机构;");
							break;
						case 2:
							item.append("变更成果形式;");
							break;
						case 3:
							item.append("变更项目名称;");
							break;
						case 4:
							item.append("研究内容有重大调整;");
							break;
						case 5:
							item.append("延期;");
							break;
						case 6:
							item.append("自行终止;");
							break;		
						case 7:
							item.append("申请撤项;");
							break;	
						case 8:
							item.append("其他;");
							break;					
						default:
							break;
						}						
					}					
				}
				if(!item.toString().isEmpty() && !item.toString().equals("")) {
					str[5] = item.toString().substring(0, item.length()-1);					
					Element variationItem =  result.addElement("variationItem");//变更同意事项
					variationItem.setText(str[5]);						
				}			
			}
			if(flag) {
				SyncStatus syncStatus = new SyncStatus();
				syncStatus.setPeer(peer);
				syncStatus.setInterfaceName("requestProjectVariationResult");
				syncStatus.setVariationId(str[1]);
				syncStatus.setRequestDate(requestDate);
				syncStatus.setRequestAccount(account);
				syncStatus.setRequestAccountBelong(requestAccountBelong);
				syncStatus.setStatus(0);
				syncStatusList.add(syncStatus);
			}
		}
		if(flag) {
			dao.add(syncStatusList);
		}
		Long end = System.currentTimeMillis();
		System.out.println("此次同步" + list.size() + "条数据总耗时 :" + (end - begin) + "ms");
		return responseContent("data", document);
	}

	public String requestProjectEndinspectionResult(String projectType, int length, Account account, String requestAccountBelong) {
		Long begin = System.currentTimeMillis();
		Map sMap = new HashMap();
		Map map = new HashMap();
		String peer = getPeerByAccount(account);
		map.put("peer", peer);
		String belongId = projectService.getBelongIdByAccount(account);
		AccountType type = account.getType();
		int isPrincipal = account.getIsPrincipal();
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		sMap.put("peer", peer);
		List synList = dao.query("from SyncStatus s where s.status = 0 and s.peer = :peer and s.interfaceName != 'requestProjectEndinspectionResult'", sMap);
		if(!synList.isEmpty()) {
			return responseContent("error", "您上一次访问的服务接口，还有未应答项目");
		}
		List<String> endinspectionIdList = dao.query("select s.endinspectionId from SyncStatus s where s.status = 0 and s.peer = :peer and s.interfaceName = 'requestProjectEndinspectionResult'", sMap);
		Boolean flag = true;//标志是否已将本次同步数据加入同步状态表，每条数据仅在同步状态表中存在一条
		if(!endinspectionIdList.isEmpty()) {
			flag = false;
		}
		Date requestDate = new Date();
		List<SyncStatus> syncStatusList = new ArrayList<SyncStatus>();
		StringBuffer hql = new StringBuffer();
		hql.append("select app.id, end.id, spe.sinossProjectId, spe.sinossId, end.finalAuditResultEnd ");
		if(account.getType().within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
			hql.append("from SinossProjectEndinspection spe,ProjectMember mem left join spe.projectEndinspection end left join spe.projectApplication app left join spe.projectGranted gra left join gra.university uni " +
					"where mem.application.id=app.id ");
		}else{//管理人员
			hql.append("from SinossProjectEndinspection spe left join spe.projectEndinspection end left join spe.projectApplication app left join spe.projectGranted gra left join gra.university uni " +
					"where 1=1 ");
		}
		//查询范围控限制语句
		if (type.equals(AccountType.ADMINISTRATOR)) {
		}// 系统管理员
		else if (type.equals(AccountType.MINISTRY)) {// 部级
			if (isPrincipal == 1) {// 主账号
			} else {// 子账号
				String ministryId = projectService.getAgencyIdByOfficerId(belongId);
				if (ministryId != null) {
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if (type.equals(AccountType.PROVINCE)) {// 省级
			if (isPrincipal == 1) {// 主账号
				hql.append(" and uni.type=4 and uni.subjection.id=:belongId");
				map.put("belongId", belongId);
			} else {// 子账号
				String provinceId = projectService.getAgencyIdByOfficerId(belongId);
				if (provinceId != null) {
					hql.append(" and uni.type=4 and uni.subjection.id=:provinceId");
					map.put("provinceId", provinceId);
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if (type.equals(AccountType.MINISTRY_UNIVERSITY) || type.equals(AccountType.LOCAL_UNIVERSITY)) {// 部属高校与地方高校
			if (isPrincipal == 1) {// 主账号
				hql.append(" and uni.id=:belongId");
				map.put("belongId", belongId);
			} else {// 子账号
				String universityId = projectService.getAgencyIdByOfficerId(belongId);
				if (universityId != null) {
					hql.append(" and uni.id=:universityId");
					map.put("universityId", universityId);
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if (type.equals(AccountType.DEPARTMENT)) {// 院系
			if (isPrincipal == 1) {// 主账号
				hql.append(" and gra.department.id=:belongId");
				map.put("belongId", belongId);
			} else {// 子账号
				String departmentId = projectService.getDepartmentIdByOfficerId(belongId);
				if (departmentId != null) {
					hql.append(" and gra.department.id=:departmentId");
					map.put("departmentId", departmentId);
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if (type.equals(AccountType.INSTITUTE)) {// 研究机构
			if (isPrincipal == 1) {// 主账号
				hql.append(" and gra.institute.id=:belongId");
				map.put("belongId", belongId);
			} else {// 子账号
				String instituteId = projectService.getInstituteIdByOfficerId(belongId);
				if (instituteId != null) {
					hql.append(" and gra.institute.id=:instituteId");
					map.put("instituteId", instituteId);
				} else {
					hql.append(" and 1=0");
				}
			}

		} else if (type.within(AccountType.EXPERT, AccountType.STUDENT)) {// 外部专家与内部专家或学生
			hql.append(" and mem.member.id=:belongId and mem.groupNumber = gra.memberGroupNumber");
			map.put("belongId", belongId);
		} else {
			hql.append(" and 1=0");
		}		
		JSONObject mapObj = new JSONObject();
		InterfaceConfig interfaceConfig = (InterfaceConfig) dao.query("from InterfaceConfig ic where ic.serviceName = 'SinossWebService' and ic.methodName = 'requestProjectEndinspectionResult'").get(0);
		String options = interfaceConfig.getOptions();
		Map opMap = JSONObject.fromObject(options);	
		if ((Integer) opMap.get("startYear") != -1) {
			hql.append(" and app.year>=:startYear ");
			map.put("startYear", (Integer) opMap.get("startYear"));
		}
		if ((Integer) opMap.get("endYear") != -1) {
			hql.append(" and app.year<=:endYear ");
			map.put("endYear", (Integer) opMap.get("endYear"));
		}
		if(opMap.get("applyStartDate") != null) {
			hql.append(" and end.applicantSubmitDate is not null and to_char(end.applicantSubmitDate,'yyyy-MM-dd')>=:applyStartDate");
			map.put("applyStartDate", opMap.get("applyStartDate"));
		}
		if(opMap.get("applyEndDate") != null) {
			hql.append(" and end.applicantSubmitDate is not null and to_char(end.applicantSubmitDate,'yyyy-MM-dd')<=:applyEndDate");
			map.put("applyEndDate", opMap.get("applyEndDate"));
		}
		if(opMap.get("auditStartDate") != null) {
			hql.append(" and end.finalAuditDate is not null and to_char(end.finalAuditDate,'yyyy-MM-dd')>=:auditStartDate");
			map.put("auditStartDate", opMap.get("auditStartDate"));
		}
		if(opMap.get("auditEndDate") != null) {
			hql.append(" and end.finalAuditDate is not null and to_char(end.finalAuditDate,'yyyy-MM-dd')<=:auditEndDate");
			map.put("auditEndDate", opMap.get("auditEndDate"));
		}
		hql.append(" and end.finalAuditResultEnd !=0");
		hql.append(" and end.finalAuditResultPublish = 1");
		if(!projectType.equals("general") && !projectType.equals("instp")) {
			return responseContent("error", "接口调用参数有误");
		} else if(projectType.equals("general")) {
			hql.append(" and end.projectType = 'general'");
		} else if(projectType.equals("instp")) {
			hql.append(" and end.projectType = 'instp'");
		}
		if(flag) {
			hql.append(" and end.id not in (select s.endinspectionId from SyncStatus s where s.peer = :peer and s.interfaceName = 'requestProjectEndinspectionResult')");
		} else {
			hql.append(" and end.id in (select s.endinspectionId from SyncStatus s where s.status = 0 and s.peer = :peer and s.interfaceName = 'requestProjectEndinspectionResult')");
		}
		List<Object[]> list = dao.query(hql.toString(), map, 0, length);
		if(list.isEmpty()) {
			return responseContent("notice", "已同步完成");
		}				
		Document document = DocumentHelper.createDocument();
		Element content = document.addElement("content");
		for (Object[] o : list) {
			String[] str = new String[5];
			for (int i = 0; i < o.length; i++) {
				if (o[i] instanceof String){
					str[i] = (String) o[i];
				} else if (o[i] instanceof Integer) {
					int temp = (Integer) o[i];
					str[i] = String.valueOf(temp);
				} else if (o[i] == null) {
					str[i] = "null";//当对象为空时
				}
			}	
			Element result = content.addElement("item");
			Element projectId = result.addElement("projectId");//管理数据库项目id
			projectId.setText(str[0]);
			Element endinspectionId =  result.addElement("endinspectionId");//管理数据库结项id
			endinspectionId.setText(str[1]);
			Element sinossProjectId =  result.addElement("sinossProjectId");//管理平台项目id
			sinossProjectId.setText(str[2]);
			Element sinossEndinspectionId =  result.addElement("sinossEndinspectionId");//管理平台结项id
			sinossEndinspectionId.setText(str[3]);
			Element endinspectionResult =  result.addElement("endinspectionResult");//结项审核结果
			endinspectionResult.setText(str[4]);
			if(flag) {
				SyncStatus syncStatus = new SyncStatus();
				syncStatus.setPeer(peer);
				syncStatus.setInterfaceName("requestProjectEndinspectionResult");
				syncStatus.setEndinspectionId(str[1]);
				syncStatus.setRequestDate(requestDate);
				syncStatus.setRequestAccount(account);
				syncStatus.setRequestAccountBelong(requestAccountBelong);
				syncStatus.setStatus(0);
				syncStatusList.add(syncStatus);				
			}
		}
		if(flag) {
			dao.add(syncStatusList);
		}
		Long end = System.currentTimeMillis();
		System.out.println("此次同步" + list.size() + "条数据总耗时 :" + (end - begin) + "ms");
		return responseContent("data", document);
	}

}
