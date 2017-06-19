package csdc.action.system.interfaces;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.bean.Account;
import csdc.bean.InterfaceConfig;
import csdc.bean.Right;
import csdc.dao.HibernateBaseDao;
import csdc.service.IInterfaceService;
import csdc.service.IProjectService;
import csdc.tool.HSSFExport;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.LoginInfo;
import csdc.tool.info.GlobalInfo;

public class SinossServerAction extends ActionSupport{
	private static final long serialVersionUID = -2753118291122678473L;

	protected IInterfaceService interfaceService;
	private IProjectService projectService;
	@Autowired
	protected HibernateBaseDao dao;
	
	private int startYear,endYear;
	private Date applyStartDate,applyEndDate,auditStartDate,auditEndDate;
	private int isPublished;
	private String export_interface;
	private String export_projectType;
	private String fileFileName;
	protected Map jsonMap = new HashMap();// json对象容器
	
	public String toApplicationResultConfig() {
		Map opMap = interfaceService.getOptionsByName("SinossWebService", "requestProjectApplicationResult");
		isPublished = interfaceService.getIsPublished("SinossWebService", "requestProjectApplicationResult");
		if(opMap != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				startYear = (Integer) opMap.get("startYear");
				endYear = (Integer) opMap.get("endYear");
				if(opMap.get("applyStartDate") != null) {
					applyStartDate = sdf.parse((String) opMap.get("applyStartDate"));					
				}
				if(opMap.get("applyEndDate") != null) {
					applyEndDate = sdf.parse((String) opMap.get("applyEndDate"));					
				}
				if(opMap.get("auditStartDate") != null) {
					auditStartDate = sdf.parse((String) opMap.get("auditStartDate"));					
				}
				if(opMap.get("auditEndDate") != null) {
					auditEndDate = sdf.parse((String) opMap.get("auditEndDate"));						
				}			
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}

	public String applicationResultConfig() {
		JSONObject mapObj = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  		
		InterfaceConfig interfaceConfig = interfaceService.getInterfaceConfig("SinossWebService", "requestProjectApplicationResult");
		interfaceConfig.setIsPublished(isPublished);
		interfaceConfig.setServiceName("SinossWebService");
		interfaceConfig.setMethodName("requestProjectApplicationResult");
		Right right = (Right) dao.query("from Right r where r.code = 'ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_APP_RESULT_CONFIG'").get(0);
		interfaceConfig.setRight(right);
		mapObj.put("startYear", startYear);
		mapObj.put("endYear", endYear);
		if(applyStartDate != null) {
			mapObj.put("applyStartDate", sdf.format(applyStartDate));
		} else {
			mapObj.put("applyStartDate", applyStartDate);
		}
		if(applyEndDate != null) {
			mapObj.put("applyEndDate", sdf.format(applyEndDate));
		} else {
			mapObj.put("applyEndDate", applyEndDate);
		}
		if(auditStartDate != null) {
			mapObj.put("auditStartDate", sdf.format(auditStartDate));
		} else {
			mapObj.put("auditStartDate", auditStartDate);
		}
		if(auditEndDate != null) {
			mapObj.put("auditEndDate", sdf.format(auditEndDate));
		} else {
			mapObj.put("auditEndDate", auditEndDate);
		}
		String options = mapObj.toString();
		interfaceConfig.setOptions(options);
		dao.addOrModify(interfaceConfig);		
		return SUCCESS;
	}
	
	public String toMidinspectionResultConfig() {
		Map opMap = interfaceService.getOptionsByName("SinossWebService", "requestProjectMidinspectionResult");
		isPublished = interfaceService.getIsPublished("SinossWebService", "requestProjectMidinspectionResult");
		if(opMap != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				startYear = (Integer) opMap.get("startYear");
				endYear = (Integer) opMap.get("endYear");
				if(opMap.get("applyStartDate") != null) {
					applyStartDate = sdf.parse((String) opMap.get("applyStartDate"));					
				}
				if(opMap.get("applyEndDate") != null) {
					applyEndDate = sdf.parse((String) opMap.get("applyEndDate"));					
				}
				if(opMap.get("auditStartDate") != null) {
					auditStartDate = sdf.parse((String) opMap.get("auditStartDate"));					
				}
				if(opMap.get("auditEndDate") != null) {
					auditEndDate = sdf.parse((String) opMap.get("auditEndDate"));						
				}			
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}		
		return SUCCESS;
	}

	public String midinspectionResultConfig() {
		JSONObject mapObj = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  		
		InterfaceConfig interfaceConfig = interfaceService.getInterfaceConfig("SinossWebService", "requestProjectMidinspectionResult");
		interfaceConfig.setIsPublished(isPublished);
		interfaceConfig.setServiceName("SinossWebService");
		interfaceConfig.setMethodName("requestProjectMidinspectionResult");
		Right right = (Right) dao.query("from Right r where r.code = 'ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_MID_RESULT_CONFIG'").get(0);
		interfaceConfig.setRight(right);
		mapObj.put("startYear", startYear);
		mapObj.put("endYear", endYear);
		if(applyStartDate != null) {
			mapObj.put("applyStartDate", sdf.format(applyStartDate));
		} else {
			mapObj.put("applyStartDate", applyStartDate);
		}
		if(applyEndDate != null) {
			mapObj.put("applyEndDate", sdf.format(applyEndDate));
		} else {
			mapObj.put("applyEndDate", applyEndDate);
		}
		if(auditStartDate != null) {
			mapObj.put("auditStartDate", sdf.format(auditStartDate));
		} else {
			mapObj.put("auditStartDate", auditStartDate);
		}
		if(auditEndDate != null) {
			mapObj.put("auditEndDate", sdf.format(auditEndDate));
		} else {
			mapObj.put("auditEndDate", auditEndDate);
		}
		String options = mapObj.toString();
		interfaceConfig.setOptions(options);
		dao.addOrModify(interfaceConfig);			
		return SUCCESS;
	}
	
	public String toVariationResultConfig() {
		Map opMap = interfaceService.getOptionsByName("SinossWebService", "requestProjectVariationResult");
		isPublished = interfaceService.getIsPublished("SinossWebService", "requestProjectVariationResult");
		if(opMap != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				startYear = (Integer) opMap.get("startYear");
				endYear = (Integer) opMap.get("endYear");
				if(opMap.get("applyStartDate") != null) {
					applyStartDate = sdf.parse((String) opMap.get("applyStartDate"));					
				}
				if(opMap.get("applyEndDate") != null) {
					applyEndDate = sdf.parse((String) opMap.get("applyEndDate"));					
				}
				if(opMap.get("auditStartDate") != null) {
					auditStartDate = sdf.parse((String) opMap.get("auditStartDate"));					
				}
				if(opMap.get("auditEndDate") != null) {
					auditEndDate = sdf.parse((String) opMap.get("auditEndDate"));						
				}			
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}		
		return SUCCESS;
	}

	public String variationResultConfig() {
		JSONObject mapObj = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  		
		InterfaceConfig interfaceConfig = interfaceService.getInterfaceConfig("SinossWebService", "requestProjectVariationResult");
		interfaceConfig.setIsPublished(isPublished);
		interfaceConfig.setServiceName("SinossWebService");
		interfaceConfig.setMethodName("requestProjectVariationResult");
		Right right = (Right) dao.query("from Right r where r.code = 'ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_VAR_RESULT_CONFIG'").get(0);
		interfaceConfig.setRight(right);
		mapObj.put("startYear", startYear);
		mapObj.put("endYear", endYear);
		if(applyStartDate != null) {
			mapObj.put("applyStartDate", sdf.format(applyStartDate));
		} else {
			mapObj.put("applyStartDate", applyStartDate);
		}
		if(applyEndDate != null) {
			mapObj.put("applyEndDate", sdf.format(applyEndDate));
		} else {
			mapObj.put("applyEndDate", applyEndDate);
		}
		if(auditStartDate != null) {
			mapObj.put("auditStartDate", sdf.format(auditStartDate));
		} else {
			mapObj.put("auditStartDate", auditStartDate);
		}
		if(auditEndDate != null) {
			mapObj.put("auditEndDate", sdf.format(auditEndDate));
		} else {
			mapObj.put("auditEndDate", auditEndDate);
		}
		String options = mapObj.toString();
		interfaceConfig.setOptions(options);
		dao.addOrModify(interfaceConfig);
		return SUCCESS;
	}
	
	public String toEndinspectionResultConfig() {
		Map opMap = interfaceService.getOptionsByName("SinossWebService", "requestProjectEndinspectionResult");
		isPublished = interfaceService.getIsPublished("SinossWebService", "requestProjectEndinspectionResult");
		if(opMap != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				startYear = (Integer) opMap.get("startYear");
				endYear = (Integer) opMap.get("endYear");
				if(opMap.get("applyStartDate") != null) {
					applyStartDate = sdf.parse((String) opMap.get("applyStartDate"));					
				}
				if(opMap.get("applyEndDate") != null) {
					applyEndDate = sdf.parse((String) opMap.get("applyEndDate"));					
				}
				if(opMap.get("auditStartDate") != null) {
					auditStartDate = sdf.parse((String) opMap.get("auditStartDate"));					
				}
				if(opMap.get("auditEndDate") != null) {
					auditEndDate = sdf.parse((String) opMap.get("auditEndDate"));						
				}			
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		return SUCCESS;
	}

	public String endinspectionResultConfig() {
		JSONObject mapObj = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  		
		InterfaceConfig interfaceConfig = interfaceService.getInterfaceConfig("SinossWebService", "requestProjectEndinspectionResult");
		interfaceConfig.setIsPublished(isPublished);
		interfaceConfig.setServiceName("SinossWebService");
		interfaceConfig.setMethodName("requestProjectEndinspectionResult");
		Right right = (Right) dao.query("from Right r where r.code = 'ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_END_RESULT_CONFIG'").get(0);
		interfaceConfig.setRight(right);
		mapObj.put("startYear", startYear);
		mapObj.put("endYear", endYear);
		if(applyStartDate != null) {
			mapObj.put("applyStartDate", sdf.format(applyStartDate));
		} else {
			mapObj.put("applyStartDate", applyStartDate);
		}
		if(applyEndDate != null) {
			mapObj.put("applyEndDate", sdf.format(applyEndDate));
		} else {
			mapObj.put("applyEndDate", applyEndDate);
		}
		if(auditStartDate != null) {
			mapObj.put("auditStartDate", sdf.format(auditStartDate));
		} else {
			mapObj.put("auditStartDate", auditStartDate);
		}
		if(auditEndDate != null) {
			mapObj.put("auditEndDate", sdf.format(auditEndDate));
		} else {
			mapObj.put("auditEndDate", auditEndDate);
		}
		String options = mapObj.toString();
		interfaceConfig.setOptions(options);
		dao.addOrModify(interfaceConfig);		
		return SUCCESS;
	}
	public String toMidinspectionRequiredConfig() {
		Map opMap = interfaceService.getOptionsByName("SinossWebService", "requestProjectMidinspectionRequired");
		isPublished = interfaceService.getIsPublished("SinossWebService", "requestProjectMidinspectionRequired");
		if(opMap != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			startYear = (Integer) opMap.get("startYear");
			endYear = (Integer) opMap.get("endYear");				
		}		
		return SUCCESS;
	}

	public String midinspectionRequiredConfig() {
		JSONObject mapObj = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  		
		InterfaceConfig interfaceConfig = interfaceService.getInterfaceConfig("SinossWebService", "requestProjectMidinspectionRequired");
		interfaceConfig.setIsPublished(isPublished);
		interfaceConfig.setServiceName("SinossWebService");
		interfaceConfig.setMethodName("requestProjectMidinspectionRequired");
		Right right = (Right) dao.query("from Right r where r.code = 'ROLE_INTERFACE_WEBSERVICE_SINOSS_SERVER_MID_REQUIRED_CONFIG'").get(0);
		interfaceConfig.setRight(right);
		mapObj.put("startYear", startYear);
		mapObj.put("endYear", endYear);
		String options = mapObj.toString();
		interfaceConfig.setOptions(options);
		dao.addOrModify(interfaceConfig);			
		return SUCCESS;
	}
	
	public String toExport() {
		return SUCCESS;
	}
	
	public String export() {
		String header = "";//表头
		String[] title = null;
		List dataList = new ArrayList();
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		Account account = loginer.getAccount();
		String belongId = projectService.getBelongIdByAccount(account);
		AccountType type = account.getType();
		int isPrincipal = account.getIsPrincipal();
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		if(export_interface.equals("ApplicationResult")) {
			
		} else if(export_interface.equals("MidinspectionResult")) {
			
		} else if(export_interface.equals("VariationResult")) {
			
		} else if(export_interface.equals("EndinspectionResult")) {
			
		} else if(export_interface.equals("MidinspectionRequired")) {
			header = "教育部人文社会科学需中检项目导出数据一览表";
			title = new String[]{
					"序号",
					"项目名称",
					"项目批准号",
					"依托高校",
					"依托高校代码",
					"项目负责人",
					"最终成果形式"
				};
			hql.append("select gra.name, gra.number, uni.name, uni.code, gra.applicantName, gra.productType ");
			if(account.getType().within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
				hql.append("from ProjectGranted gra, ProjectMember mem, ProjectApplication app join gra.university uni " +
						"where mem.application.id=app.id and gra.applicationId = app.id ");
			}else{//管理人员
				hql.append("from ProjectGranted gra, ProjectApplication app join gra.university uni " +
						"where gra.applicationId = app.id ");
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
				int numberTemp = Integer.parseInt(year.substring(year.length()- 2, year.length()));//项目批准号条件拼接
				hql.append(" and (gra.number like '" + (numberTemp - 3) + "%' or gra.number like '" + (numberTemp - 2) + "%')");
				hql.append(" and gra.status = 1");
				hql.append(" and not exists(from ProjectMidinspection mid where mid.grantedId = gra.id and mid.finalAuditResult = 2)");
			}
		}
		if (startYear != -1) {
			hql.append(" and app.year>=:startYear");
			map.put("startYear", startYear);
		}
		if (endYear != -1) {
			hql.append(" and app.year<=:endYear");
			map.put("endYear", endYear);
		}
		if (export_projectType.trim().equals("") || export_projectType == null) {
			hql.append(" and 1 = 0");
		} else if (export_projectType.contains("all")) {
			hql.append(" and (gra.projectType = 'general' or gra.projectType = 'instp')");
		} else {
			export_projectType = export_projectType.replaceAll(",\\s+", ",");
			String[] projectTypes = export_projectType.split(",");
			map.put("projectTypes", projectTypes);
			hql.append(" and gra.projectType in (:projectTypes)");
		}
		List<Object[]> list = (List) dao.query(hql.toString(), map);
		int index = 1;
		if(export_interface.equals("ApplicationResult")) {
			
		} else if(export_interface.equals("MidinspectionResult")) {
			
		} else if(export_interface.equals("VariationResult")) {
			
		} else if(export_interface.equals("EndinspectionResult")) {
			
		} else if(export_interface.equals("MidinspectionRequired")) {
			for(Object[] o : list) {
				Object[] data = new Object[o.length + 1];
				data[0] = index++;
				data[1] = o[0];//项目名称
				data[2] = o[1];//项目批准号
				data[3] = o[2];//依托高校
				data[4] = o[3];//依托高校代码
				data[5] = o[4];//项目负责人
				data[6] = o[5];//最终成果形式
				dataList.add(data);		
			}			
		}
		session.put("dataList", dataList);
		session.put("header", header);
		session.put("title", title);
		return SUCCESS;
	}
	/**
	 * 导出excel
	 * @return 输入流
	 */
	public InputStream getDownloadFile() throws Exception{
		Map session = ActionContext.getContext().getSession();
		String header = session.get("header") + "";
		//导出的Excel文件名
		fileFileName = header + ".xls";
		fileFileName = new String(fileFileName.getBytes(), "ISO8859-1");
		//获取Excel数据
		if(null == session.get("dataList")){
			return null;
		}
		List dataList = (List) session.get("dataList");	//Excel正文数据源
		String[] title = (String[]) session.get("title");	//Excel第二行标题
		return HSSFExport.commonExportExcel(dataList, header, title);
	}
	
	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	public Date getApplyStartDate() {
		return applyStartDate;
	}

	public void setApplyStartDate(Date applyStartDate) {
		this.applyStartDate = applyStartDate;
	}

	public Date getApplyEndDate() {
		return applyEndDate;
	}

	public void setApplyEndDate(Date applyEndDate) {
		this.applyEndDate = applyEndDate;
	}

	public Date getAuditStartDate() {
		return auditStartDate;
	}

	public void setAuditStartDate(Date auditStartDate) {
		this.auditStartDate = auditStartDate;
	}

	public Date getAuditEndDate() {
		return auditEndDate;
	}

	public void setAuditEndDate(Date auditEndDate) {
		this.auditEndDate = auditEndDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(int isPublished) {
		this.isPublished = isPublished;
	}

	public IInterfaceService getInterfaceService() {
		return interfaceService;
	}

	public void setInterfaceService(IInterfaceService interfaceService) {
		this.interfaceService = interfaceService;
	}

	public IProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}
	
	public Map getJsonMap() {
		return jsonMap;
	}

	public String getExport_interface() {
		return export_interface;
	}

	public void setExport_interface(String export_interface) {
		this.export_interface = export_interface;
	}

	public String getExport_projectType() {
		return export_projectType;
	}

	public void setExport_projectType(String export_projectType) {
		this.export_projectType = export_projectType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	
	
}
