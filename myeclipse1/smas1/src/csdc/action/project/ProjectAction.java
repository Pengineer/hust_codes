package csdc.action.project;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.action.project.general.GeneralAction;
import csdc.bean.ProjectApplication;
import csdc.bean.ProjectEndinspection;
import csdc.bean.ProjectFee;
import csdc.bean.ProjectGranted;
import csdc.bean.ProjectMidinspection;
import csdc.bean.ProjectVariation;
import csdc.bean.SystemOption;
import csdc.bean.University;
import csdc.dao.HibernateBaseDao;
import csdc.service.IProjectService;
import csdc.tool.DoubleTool;
import csdc.tool.execution.importer.Tools;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;
import csdc.tool.tableKit.imp.ProjectKit;
import csdc.tool.widget.NumberHandle;

/**
 * 
 * @author fengcl
 *
 */
public abstract class ProjectAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	protected String projectid;//项目立项id
	private static final String PAGE_BUFFER_ID = "app.id";//缓存id
	
	protected static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	protected Double applyFee,otherFee,approveFee,bookFee,dataFee,travelFee,conferenceFee,internationalFee,deviceFee,consultationFee,laborFee,printFee,indirectFee,otherFeeD,totalFee,surplusFee,fundedFee,toFundFee;
	protected String bookNote,dataNote,travelNote,conferenceNote,internationalNote,deviceNote,consultationNote,laborNote,printNote,indirectNote,otherNote,feeNote;
	protected ProjectFee projectFeeApply,projectFeeGranted,projectFeeMid,projectFeeEnd;//项目申请经费对象，项目立项经费对象，项目年检经费对象，项目中检经费对象
	
	@Autowired
	protected IProjectService projectService;//项目管理接口
	@Autowired
	protected ProjectKit ek;	//项目工具包对象
	@Autowired
	protected HibernateBaseDao dao;	
	@Autowired
	protected Tools tool;
	
	@Override
	public String pageName() {
		return null;
	}
	@Override
	public String[] column() {
		return null;
	}
	@Override
	public String dateFormat() {
		return DATE_FORMAT;
	}
	@Override
	public Object[] simpleSearchCondition() {
		return null;
	}
	@Override
	public Object[] advSearchCondition() {
		return null;
	}
	@Override
	public String pageBufferId() {
		return ProjectAction.PAGE_BUFFER_ID;
	}
	public abstract String listHQL();
	public abstract String listHQLG();	
	public abstract String listHQLC();
	public abstract String projectType();
	public abstract String className();

	/**
	 * 进入查看
	 */
	public String toView() {
		return SUCCESS;
	}
	
	/**
	 * 查看校验
	 */
	public void validateView() {
		if (entityId == null || entityId.isEmpty()) {// 项目ID不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VIEW_NULL);
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VIEW_NULL);
		}
	}
		
	/**
	 * 查看详情
	 * @throws DocumentException 
	 */
	public String view() throws DocumentException {
		//申请相关信息
		List<String> appFileSizeList = new ArrayList<String>();//申请书大小
		ProjectApplication project = (ProjectApplication) baseService.query(ProjectApplication.class, entityId);// 查询项目
		if (project == null) {// 日志不存在，返回错误信息
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_NULL);
			return INPUT;
		} else {// 项目存在，存入jsonMap，并更新日志页面对象
			University universityName = (University) baseService.query(University.class, project.getUniversityCode());
			jsonMap.put("universityName", universityName.getName());//高校名称
			project.setDiscipline(projectService.getDiscipline(project.getDiscipline()));//组装学科代码+学科名称
			ProjectFee projectFeeApply = null;
			if (project.getProjectFee() != null) {
				projectFeeApply = (ProjectFee) baseService.query(ProjectFee.class, project.getProjectFee().getId());				
			}
			if (project.getFile() != null) {
				String[] attachPath = project.getFile().split("; ");
				InputStream is = null;
				for (String path : attachPath) {
					is = ServletActionContext.getServletContext().getResourceAsStream(path);
					if (null != is) {
						try {
							appFileSizeList.add(baseService.accquireFileSize(is.available()));
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {// 附件不存在
						appFileSizeList.add(null);
					}
					jsonMap.put("appFileSizeList", appFileSizeList);
				}
			}
			jsonMap.put("projectFeeApply", projectFeeApply);//经费概算
			jsonMap.put("project", project);//项目申请
			StringBuffer hql4Expert = new StringBuffer();
			Map map = new HashMap();
			hql4Expert.append("select e.id, e.name, u.name, e.specialityTitle, u.founderCode, e.discipline, pr.priority, pr.isManual from Expert e, University u, ProjectApplicationReview pr where pr.year = :defaultYear and pr.project.id = :projectId and pr.reviewer.id = e.id and e.universityCode = u.code order by pr.priority asc");
			map.put("projectId", entityId);
			map.put("defaultYear", session.get("defaultYear"));
			pageList = baseService.query(hql4Expert.toString(), map);
			jsonMap.put("pageList", pageList);
			if (pageList != null && !pageList.isEmpty()) {
				Map<String, String> expertInfo = new HashMap<String, String>();
				for (int i = 0; i < pageList.size(); i++) {
					Object[] o;
					String mapid;// 专家ID
					String value = "";// 专家学科信息信息
					o = (Object[]) pageList.get(i);
					mapid = o[0].toString();
					// 获得专家学科
					if (o[5] != null) {
						value = projectService.getDiscipline(o[5].toString());
					}
					expertInfo.put(mapid, value);
				}
				jsonMap.put("expertInfo", expertInfo);
			}
			List applicationReviews = new ArrayList();//所有人评审信息
			applicationReviews = this.projectService.getAllAppReviewList(entityId);
			if (null !=applicationReviews && applicationReviews.size() != 0) {
				jsonMap.put("applicationReviews", applicationReviews);//所有人评审信息
			} else {
				jsonMap.put("applicationReviews", null);//所有人评审信息
			}
			//立项相关信息
			String grantedId = this.projectService.getGrantedIdByAppId(entityId);
			ProjectGranted granted = (ProjectGranted)this.projectService.getGrantedFetchDetailByGrantedId(grantedId);
			jsonMap.put("granted", granted);//项目立项
			if(granted != null) {
				if (granted.getProjectFee() != null) {
					projectFeeGranted = (ProjectFee) dao.query(ProjectFee.class, granted.getProjectFee().getId());
				}
				jsonMap.put("projectFeeGranted", projectFeeGranted);	//经费预算
				if(project.getFinalAuditResult() == 2 && project.getIsGranted() == 1) {//已立项
					//结项是否通过
					int endPassAlready = this.projectService.getPassEndinspectionByGrantedId(grantedId).size()>0 ? 1 : 0;  //1：通过；0：未通过
					//是否有待审结项
					int endPending = this.projectService.getPendingEndinspectionByGrantedId(grantedId).size()>0 ? 1 : 0;
					//是否有待审变更
					int varPending = this.projectService.getPendingVariationByGrantedId(grantedId).size()>0 ? 1 : 0;
					//结项信息
					List<ProjectEndinspection> endList= this.projectService.getAllEndinspectionByGrantedId(grantedId);
					ProjectEndinspection endinspection = this.projectService.getCurrentEndinspectionByGrantedId(grantedId);
					List<String> endiFileSizeList = new ArrayList<String>();//结项申请书大小
					List endinspectionReviews = new ArrayList();//所有人评审信息
					//显示结项申请书大小
					for (ProjectEndinspection pendi : endList) {
						if (pendi.getFile() != null) {
							String[] attachPath = pendi.getFile().split("; ");
							InputStream is = null;
							for (String path : attachPath) {
								is = ServletActionContext.getServletContext().getResourceAsStream(path);
								if (null != is) {
									try {
										endiFileSizeList.add(baseService.accquireFileSize(is.available()));
									} catch (IOException e) {
										e.printStackTrace();
									}
								} else {// 附件不存在
									endiFileSizeList.add(null);
								}
							}
						}					
					}
					for(int i=0; i<endList.size(); i++){//循环查询每次结项的评审信息
						//所有人评审信息
						List endinspectionReviewsDetail = this.projectService.getAllEndReviewList(endList.get(i).getId());
						endinspectionReviews.add(i, endinspectionReviewsDetail);	
					}
					List<String> endIds = new ArrayList<String>();
					for(ProjectEndinspection ge : endList) {
						endIds.add(ge.getId());//获取当前所有结项ids
					}
					List<Map> projectEndFees = projectService.getProjectFeeEndByEndId(grantedId, endIds);//结项经费
					jsonMap.put("endList", endList);//结项信息
					jsonMap.put("endiFileSizeList", endiFileSizeList);
					jsonMap.put("endinspectionReviews", endinspectionReviews);//所有人评审信息
					jsonMap.put("projectEndFees", projectEndFees);//结项经费
					jsonMap.put("endPassAlready",endPassAlready);//结项是否通过
					jsonMap.put("endPending",endPending);//是否有待审结项
					//变更信息
					List<ProjectVariation> varList = this.projectService.getAllVariationByGrantedId(grantedId);
					ProjectVariation variation = this.projectService.getCurrentVariationByGrantedId(grantedId);
					List<String> postponementPlanFileSizeList = new ArrayList<String>();//延期计划书大小 
					List<String> varFileSizeList = new ArrayList<String>();//变更申请书大小  
					//显示变更申请书的大小
					for (ProjectVariation var : varList) {
						if(var.getChangeMember() == 1) {
							String xmlNewMembers = variation.getNewMembers();
							Document doc = DocumentHelper.parseText(xmlNewMembers);
							Iterator<Element> memberNodes = doc.selectNodes("//member").iterator();
							String members = "";
							while (memberNodes.hasNext()) {
								Element memberElement = memberNodes.next();
								String memberName = memberElement.elementText("memberName");
								String agencyName = memberElement.elementText("agencyName");
								String divisionName = memberElement.elementText("divisionName");
								members += memberName + "(" + agencyName + divisionName + ")" + "; ";
							}
							var.setNewMembers(members.substring(0, members.length()-2));
						}
						if (var.getFile() != null) {
							String[] attachPath = var.getFile().split("; ");
							InputStream is = null;
							for (String path : attachPath) {
								is = ServletActionContext.getServletContext().getResourceAsStream(path);
								if (null != is) {
									try {
										varFileSizeList.add(baseService.accquireFileSize(is.available()));
									} catch (IOException e) {
										e.printStackTrace();
									}
								} else {// 附件不存在
									varFileSizeList.add(null);
								}
							}
						}
						if (var.getPostponementPlanFile() != null) {
							String[] attachPath = var.getPostponementPlanFile().split("; ");
							InputStream is = null;
							for (String path : attachPath) {
								is = ServletActionContext.getServletContext().getResourceAsStream(path);
								if (null != is) {
									try {
										postponementPlanFileSizeList.add(baseService.accquireFileSize(is.available()));
									} catch (IOException e) {
										e.printStackTrace();
									}
								} else {// 附件不存在
									postponementPlanFileSizeList.add(null);
								}
							}
						}
						
					}
					jsonMap.put("varPending",varPending);//是否有待审变更
					jsonMap.put("varList", varList);//变更信息
					jsonMap.put("postponementPlanFileSizeList", postponementPlanFileSizeList);
					jsonMap.put("varFileSizeList", varFileSizeList);
					int midPassAlready = this.projectService.getPassMidinspectionByGrantedId(grantedId).size()>0 ? 1 : 0;//中检是否通过 1：通过；0：未通过
					int midPending = this.projectService.getPendingMidinspectionByGrantedId(grantedId).size()>0 ? 1 : 0;////是否有待审中检 1：有；0：没有
					int grantedYear = (granted.getApproveDate() != null) ? (granted.getApproveDate().getYear() + 1900) : project.getYear();
					int midForbid = this.projectService.getMidForbidByGrantedDate(grantedYear);//是否可以中检，1：不可以 0：可以
					int endAllow = this.projectService.getEndAllowByGrantedDate(grantedYear);//是否可以结项，1：可以，0：不可以
					//中检信息
					List<String> midFileSizeList = new ArrayList<String>();//中检申请书大小
					List<ProjectMidinspection> midList = this.projectService.getAllMidinspectionByGrantedId(grantedId);
					List<String> midIds = new ArrayList<String>();
					for(ProjectMidinspection gm : midList) {
						midIds.add(gm.getId());//获取当前所有中检ids
						if (gm.getFile() != null) {
							String[] attachPath = gm.getFile().split("; ");
							InputStream is = null;
							for (String path : attachPath) {
								is = ServletActionContext.getServletContext().getResourceAsStream(path);
								if (null != is) {
									try {
										midFileSizeList.add(baseService.accquireFileSize(is.available()));
									} catch (IOException e) {
										e.printStackTrace();
									}
								} else {// 附件不存在
									midFileSizeList.add(null);
								}
							}
						}
					}
					List<Map> projectMidFees = projectService.getProjectFeeMidByMidId(grantedId, midIds);//中检经费
					jsonMap.put("midFileSizeList", midFileSizeList);
					jsonMap.put("midList", midList);//中检信息
					jsonMap.put("midPassAlready",midPassAlready);//中检是否通过
					jsonMap.put("midPending",midPending);//是否有待审中检
					jsonMap.put("midForbid",midForbid);//是否可以中检，1：不可以 0：可以
					jsonMap.put("endAllow",endAllow);//是否可以结项，1：可以，0：不可以
				}
			}
			return SUCCESS;
		}
	}
	
	public String toAddApplyFee() throws UnsupportedEncodingException {
		if (!request.getParameter("applyFee").isEmpty() && !request.getParameter("applyFee").equals(null)) {
			applyFee = Double.parseDouble(request.getParameter("applyFee").trim());
		}
		if (!request.getParameter("otherFee").isEmpty() && !request.getParameter("otherFee").equals(null)) {
			otherFee = Double.parseDouble(request.getParameter("otherFee").trim());
		}
		if (!request.getParameter("bookFee").isEmpty() && !request.getParameter("bookFee").equals(null)) {
			bookFee = Double.parseDouble(request.getParameter("bookFee").trim());
		}
		if (!request.getParameter("bookNote").isEmpty() && !request.getParameter("bookNote").equals(null)) {
			bookNote = new String(request.getParameter("bookNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("dataFee").isEmpty() && !request.getParameter("dataFee").equals(null)) {
			dataFee = Double.parseDouble(request.getParameter("dataFee").trim());
		}
		if (!request.getParameter("dataNote").isEmpty() && !request.getParameter("dataNote").equals(null)) {
			dataNote = new String(request.getParameter("dataNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("travelFee").isEmpty() && !request.getParameter("travelFee").equals(null)) {
			travelFee = Double.parseDouble(request.getParameter("travelFee").trim());
		}
		if (!request.getParameter("travelNote").isEmpty() && !request.getParameter("travelNote").equals(null)) {
			travelNote = new String(request.getParameter("travelNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("conferenceFee").isEmpty() && !request.getParameter("conferenceFee").equals(null)) {
			conferenceFee = Double.parseDouble(request.getParameter("conferenceFee").trim());
		}
		if (!request.getParameter("conferenceNote").isEmpty() && !request.getParameter("conferenceNote").equals(null)) {
			conferenceNote = new String(request.getParameter("conferenceNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("internationalFee").isEmpty() && !request.getParameter("internationalFee").equals(null)) {
			internationalFee = Double.parseDouble(request.getParameter("internationalFee").trim());
		}
		if (!request.getParameter("internationalNote").isEmpty() && !request.getParameter("internationalNote").equals(null)) {
			internationalNote = new String(request.getParameter("internationalNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("deviceFee").isEmpty() && !request.getParameter("deviceFee").equals(null)) {
			deviceFee = Double.parseDouble(request.getParameter("deviceFee").trim());
		}
		if (!request.getParameter("deviceNote").isEmpty() && !request.getParameter("deviceNote").equals(null)) {
			deviceNote = new String(request.getParameter("deviceNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("consultationFee").isEmpty() && !request.getParameter("consultationFee").equals(null)) {
			consultationFee = Double.parseDouble(request.getParameter("consultationFee").trim());
		}
		if (!request.getParameter("consultationNote").isEmpty() && !request.getParameter("consultationNote").equals(null)) {
			consultationNote = new String(request.getParameter("consultationNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("laborFee").isEmpty() && !request.getParameter("laborFee").equals(null)) {
			laborFee = Double.parseDouble(request.getParameter("laborFee").trim());
		}
		if (!request.getParameter("laborNote").isEmpty() && !request.getParameter("laborNote").equals(null)) {
			laborNote = new String(request.getParameter("laborNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("printFee").isEmpty() && !request.getParameter("printFee").equals(null)) {
			printFee = Double.parseDouble(request.getParameter("printFee").trim());
		}
		if (!request.getParameter("printNote").isEmpty() && !request.getParameter("printNote").equals(null)) {
			printNote = new String(request.getParameter("printNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("indirectFee").isEmpty() && !request.getParameter("indirectFee").equals(null)) {
			indirectFee = Double.parseDouble(request.getParameter("indirectFee").trim());
		}
		if (!request.getParameter("indirectNote").isEmpty() && !request.getParameter("indirectNote").equals(null)) {
			indirectNote = new String(request.getParameter("indirectNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("otherFeeD").isEmpty() && !request.getParameter("otherFeeD").equals(null)) {
			otherFeeD = Double.parseDouble(request.getParameter("otherFeeD").trim());
		}
		if (!request.getParameter("otherNote").isEmpty() && !request.getParameter("otherNote").equals(null)) {
			otherNote = new String(request.getParameter("otherNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("totalFee").isEmpty() && !request.getParameter("totalFee").equals(null)) {
			totalFee = (Double.parseDouble(request.getParameter("totalFee").trim()));
		}
		if (!request.getParameter("feeNote").isEmpty() && !request.getParameter("feeNote").equals(null)) {
			feeNote = new String(request.getParameter("feeNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		return SUCCESS;
	}
	
	public String toAddGrantedFee() throws UnsupportedEncodingException {
		if (!request.getParameter("projectid").isEmpty() && !request.getParameter("projectid").equals(null)) {
			projectid = request.getParameter("projectid").trim();
			ProjectGranted projectGranted = (ProjectGranted) dao.query(ProjectGranted.class, projectid);
			if (projectGranted.getApproveFee() != null) {
				approveFee = projectGranted.getApproveFee();
			}
		}
		if (!request.getParameter("bookFee").isEmpty() && !request.getParameter("bookFee").equals(null)) {
			bookFee = Double.parseDouble(request.getParameter("bookFee").trim());
		}
		if (!request.getParameter("bookNote").isEmpty() && !request.getParameter("bookNote").equals(null)) {
			bookNote = new String(request.getParameter("bookNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("dataFee").isEmpty() && !request.getParameter("dataFee").equals(null)) {
			dataFee = Double.parseDouble(request.getParameter("dataFee").trim());
		}
		if (!request.getParameter("dataNote").isEmpty() && !request.getParameter("dataNote").equals(null)) {
			dataNote = new String(request.getParameter("dataNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("travelFee").isEmpty() && !request.getParameter("travelFee").equals(null)) {
			travelFee = Double.parseDouble(request.getParameter("travelFee").trim());
		}
		if (!request.getParameter("travelNote").isEmpty() && !request.getParameter("travelNote").equals(null)) {
			travelNote = new String(request.getParameter("travelNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("conferenceFee").isEmpty() && !request.getParameter("conferenceFee").equals(null)) {
			conferenceFee = Double.parseDouble(request.getParameter("conferenceFee").trim());
		}
		if (!request.getParameter("conferenceNote").isEmpty() && !request.getParameter("conferenceNote").equals(null)) {
			conferenceNote = new String(request.getParameter("conferenceNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("internationalFee").isEmpty() && !request.getParameter("internationalFee").equals(null)) {
			internationalFee = Double.parseDouble(request.getParameter("internationalFee").trim());
		}
		if (!request.getParameter("internationalNote").isEmpty() && !request.getParameter("internationalNote").equals(null)) {
			internationalNote = new String(request.getParameter("internationalNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("deviceFee").isEmpty() && !request.getParameter("deviceFee").equals(null)) {
			deviceFee = Double.parseDouble(request.getParameter("deviceFee").trim());
		}
		if (!request.getParameter("deviceNote").isEmpty() && !request.getParameter("deviceNote").equals(null)) {
			deviceNote = new String(request.getParameter("deviceNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("consultationFee").isEmpty() && !request.getParameter("consultationFee").equals(null)) {
			consultationFee = Double.parseDouble(request.getParameter("consultationFee").trim());
		}
		if (!request.getParameter("consultationNote").isEmpty() && !request.getParameter("consultationNote").equals(null)) {
			consultationNote = new String(request.getParameter("consultationNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("laborFee").isEmpty() && !request.getParameter("laborFee").equals(null)) {
			laborFee = Double.parseDouble(request.getParameter("laborFee").trim());
		}
		if (!request.getParameter("laborNote").isEmpty() && !request.getParameter("laborNote").equals(null)) {
			laborNote = new String(request.getParameter("laborNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("printFee").isEmpty() && !request.getParameter("printFee").equals(null)) {
			printFee = Double.parseDouble(request.getParameter("printFee").trim());
		}
		if (!request.getParameter("printNote").isEmpty() && !request.getParameter("printNote").equals(null)) {
			printNote = new String(request.getParameter("printNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("indirectFee").isEmpty() && !request.getParameter("indirectFee").equals(null)) {
			indirectFee = Double.parseDouble(request.getParameter("indirectFee").trim());
		}
		if (!request.getParameter("indirectNote").isEmpty() && !request.getParameter("indirectNote").equals(null)) {
			indirectNote = new String(request.getParameter("indirectNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("otherFeeD").isEmpty() && !request.getParameter("otherFeeD").equals(null)) {
			otherFeeD = Double.parseDouble(request.getParameter("otherFeeD").trim());
		}
		if (!request.getParameter("otherNote").isEmpty() && !request.getParameter("otherNote").equals(null)) {
			otherNote = new String(request.getParameter("otherNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("totalFee").isEmpty() && !request.getParameter("totalFee").equals(null)) {
			totalFee = (Double.parseDouble(request.getParameter("totalFee").trim()));
		}
		if (!request.getParameter("feeNote").isEmpty() && !request.getParameter("feeNote").equals(null)) {
			feeNote = new String(request.getParameter("feeNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		return SUCCESS;
	}
	
	public String toAddMidFee() throws UnsupportedEncodingException {
		if (!request.getParameter("projectid").isEmpty() && !request.getParameter("projectid").equals(null)) {
			projectid = request.getParameter("projectid").trim();
			ProjectGranted projectGranted = (ProjectGranted) dao.query(ProjectGranted.class, projectid);
			fundedFee = this.projectService.getFundedFeeByGrantedId(projectid);
			toFundFee = DoubleTool.sub(projectGranted.getApproveFee(),fundedFee);
			if (projectGranted.getProjectFee() != null) {
				projectFeeGranted = (ProjectFee) dao.query(ProjectFee.class , projectGranted.getProjectFee().getId());
			}
		}
		if (!request.getParameter("feeNote").isEmpty() && !request.getParameter("feeNote").equals(null)) {
			feeNote = new String(request.getParameter("feeNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("bookFee").isEmpty() && !request.getParameter("bookFee").equals(null)) {
			bookFee = Double.parseDouble(request.getParameter("bookFee").trim());
		}
		if (!request.getParameter("bookNote").isEmpty() && !request.getParameter("bookNote").equals(null)) {
			bookNote = new String(request.getParameter("bookNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("dataFee").isEmpty() && !request.getParameter("dataFee").equals(null)) {
			dataFee = Double.parseDouble(request.getParameter("dataFee").trim());
		}
		if (!request.getParameter("dataNote").isEmpty() && !request.getParameter("dataNote").equals(null)) {
			dataNote = new String(request.getParameter("dataNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("travelFee").isEmpty() && !request.getParameter("travelFee").equals(null)) {
			travelFee = Double.parseDouble(request.getParameter("travelFee").trim());
		}
		if (!request.getParameter("travelNote").isEmpty() && !request.getParameter("travelNote").equals(null)) {
			travelNote = new String(request.getParameter("travelNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("conferenceFee").isEmpty() && !request.getParameter("conferenceFee").equals(null)) {
			conferenceFee = Double.parseDouble(request.getParameter("conferenceFee").trim());
		}
		if (!request.getParameter("conferenceNote").isEmpty() && !request.getParameter("conferenceNote").equals(null)) {
			conferenceNote = new String(request.getParameter("conferenceNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("internationalFee").isEmpty() && !request.getParameter("internationalFee").equals(null)) {
			internationalFee = Double.parseDouble(request.getParameter("internationalFee").trim());
		}
		if (!request.getParameter("internationalNote").isEmpty() && !request.getParameter("internationalNote").equals(null)) {
			internationalNote = new String(request.getParameter("internationalNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("deviceFee").isEmpty() && !request.getParameter("deviceFee").equals(null)) {
			deviceFee = Double.parseDouble(request.getParameter("deviceFee").trim());
		}
		if (!request.getParameter("deviceNote").isEmpty() && !request.getParameter("deviceNote").equals(null)) {
			deviceNote = new String(request.getParameter("deviceNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("consultationFee").isEmpty() && !request.getParameter("consultationFee").equals(null)) {
			consultationFee = Double.parseDouble(request.getParameter("consultationFee").trim());
		}
		if (!request.getParameter("consultationNote").isEmpty() && !request.getParameter("consultationNote").equals(null)) {
			consultationNote = new String(request.getParameter("consultationNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("laborFee").isEmpty() && !request.getParameter("laborFee").equals(null)) {
			laborFee = Double.parseDouble(request.getParameter("laborFee").trim());
		}
		if (!request.getParameter("laborNote").isEmpty() && !request.getParameter("laborNote").equals(null)) {
			laborNote = new String(request.getParameter("laborNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("printFee").isEmpty() && !request.getParameter("printFee").equals(null)) {
			printFee = Double.parseDouble(request.getParameter("printFee").trim());
		}
		if (!request.getParameter("printNote").isEmpty() && !request.getParameter("printNote").equals(null)) {
			printNote = new String(request.getParameter("printNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("indirectFee").isEmpty() && !request.getParameter("indirectFee").equals(null)) {
			indirectFee = Double.parseDouble(request.getParameter("indirectFee").trim());
		}
		if (!request.getParameter("indirectNote").isEmpty() && !request.getParameter("indirectNote").equals(null)) {
			indirectNote = new String(request.getParameter("indirectNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("otherFeeD").isEmpty() && !request.getParameter("otherFeeD").equals(null)) {
			otherFeeD = Double.parseDouble(request.getParameter("otherFeeD").trim());
		}
		if (!request.getParameter("otherNote").isEmpty() && !request.getParameter("otherNote").equals(null)) {
			otherNote = new String(request.getParameter("otherNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("totalFee").isEmpty() && !request.getParameter("totalFee").equals(null)) {
			totalFee = (Double.parseDouble(request.getParameter("totalFee").trim()));
		}
		if (!request.getParameter("surplusFee").isEmpty() && !request.getParameter("surplusFee").equals(null)) {
			surplusFee = (Double.parseDouble(request.getParameter("surplusFee").trim()));
		}
		return SUCCESS;
	}
	
	
	public String toAddEndFee() throws UnsupportedEncodingException {
		if (!request.getParameter("projectid").isEmpty() && !request.getParameter("projectid").equals(null)) {
			projectid = request.getParameter("projectid").trim();
			ProjectGranted projectGranted = (ProjectGranted) dao.query(ProjectGranted.class, projectid);
			fundedFee = this.projectService.getFundedFeeByGrantedId(projectid);
			toFundFee = DoubleTool.sub(projectGranted.getApproveFee(),fundedFee);
			if (projectGranted.getProjectFee() != null) {
				projectFeeGranted = (ProjectFee) dao.query(ProjectFee.class , projectGranted.getProjectFee().getId());
			}
		}
		if (!request.getParameter("feeNote").isEmpty() && !request.getParameter("feeNote").equals(null)) {
			feeNote = new String(request.getParameter("feeNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("bookFee").isEmpty() && !request.getParameter("bookFee").equals(null)) {
			bookFee = Double.parseDouble(request.getParameter("bookFee").trim());
		}
		if (!request.getParameter("bookNote").isEmpty() && !request.getParameter("bookNote").equals(null)) {
			bookNote = new String(request.getParameter("bookNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("dataFee").isEmpty() && !request.getParameter("dataFee").equals(null)) {
			dataFee = Double.parseDouble(request.getParameter("dataFee").trim());
		}
		if (!request.getParameter("dataNote").isEmpty() && !request.getParameter("dataNote").equals(null)) {
			dataNote = new String(request.getParameter("dataNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("travelFee").isEmpty() && !request.getParameter("travelFee").equals(null)) {
			travelFee = Double.parseDouble(request.getParameter("travelFee").trim());
		}
		if (!request.getParameter("travelNote").isEmpty() && !request.getParameter("travelNote").equals(null)) {
			travelNote = new String(request.getParameter("travelNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("conferenceFee").isEmpty() && !request.getParameter("conferenceFee").equals(null)) {
			conferenceFee = Double.parseDouble(request.getParameter("conferenceFee").trim());
		}
		if (!request.getParameter("conferenceNote").isEmpty() && !request.getParameter("conferenceNote").equals(null)) {
			conferenceNote = new String(request.getParameter("conferenceNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("internationalFee").isEmpty() && !request.getParameter("internationalFee").equals(null)) {
			internationalFee = Double.parseDouble(request.getParameter("internationalFee").trim());
		}
		if (!request.getParameter("internationalNote").isEmpty() && !request.getParameter("internationalNote").equals(null)) {
			internationalNote = new String(request.getParameter("internationalNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("deviceFee").isEmpty() && !request.getParameter("deviceFee").equals(null)) {
			deviceFee = Double.parseDouble(request.getParameter("deviceFee").trim());
		}
		if (!request.getParameter("deviceNote").isEmpty() && !request.getParameter("deviceNote").equals(null)) {
			deviceNote = new String(request.getParameter("deviceNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("consultationFee").isEmpty() && !request.getParameter("consultationFee").equals(null)) {
			consultationFee = Double.parseDouble(request.getParameter("consultationFee").trim());
		}
		if (!request.getParameter("consultationNote").isEmpty() && !request.getParameter("consultationNote").equals(null)) {
			consultationNote = new String(request.getParameter("consultationNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("laborFee").isEmpty() && !request.getParameter("laborFee").equals(null)) {
			laborFee = Double.parseDouble(request.getParameter("laborFee").trim());
		}
		if (!request.getParameter("laborNote").isEmpty() && !request.getParameter("laborNote").equals(null)) {
			laborNote = new String(request.getParameter("laborNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("printFee").isEmpty() && !request.getParameter("printFee").equals(null)) {
			printFee = Double.parseDouble(request.getParameter("printFee").trim());
		}
		if (!request.getParameter("printNote").isEmpty() && !request.getParameter("printNote").equals(null)) {
			printNote = new String(request.getParameter("printNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("indirectFee").isEmpty() && !request.getParameter("indirectFee").equals(null)) {
			indirectFee = Double.parseDouble(request.getParameter("indirectFee").trim());
		}
		if (!request.getParameter("indirectNote").isEmpty() && !request.getParameter("indirectNote").equals(null)) {
			indirectNote = new String(request.getParameter("indirectNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("otherFeeD").isEmpty() && !request.getParameter("otherFeeD").equals(null)) {
			otherFeeD = Double.parseDouble(request.getParameter("otherFeeD").trim());
		}
		if (!request.getParameter("otherNote").isEmpty() && !request.getParameter("otherNote").equals(null)) {
			otherNote = new String(request.getParameter("otherNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("totalFee").isEmpty() && !request.getParameter("totalFee").equals(null)) {
			totalFee = (Double.parseDouble(request.getParameter("totalFee").trim()));
		}
		if (!request.getParameter("surplusFee").isEmpty() && !request.getParameter("surplusFee").equals(null)) {
			surplusFee = (Double.parseDouble(request.getParameter("surplusFee").trim()));
		}
		return SUCCESS;
	}
	
	public String toAddVarFee() throws UnsupportedEncodingException {
		if (!request.getParameter("projectid").isEmpty() && !request.getParameter("projectid").equals(null)) {
			projectid = request.getParameter("projectid").trim();
			ProjectGranted projectGranted = (ProjectGranted) dao.query(ProjectGranted.class, projectid);
//			fundedFee = this.projectService.getFundedFeeByGrantedId(projectid);
//			toFundFee = projectGranted.getApproveFee()-fundedFee;
			if (projectGranted.getProjectFee() != null) {
				projectFeeGranted = (ProjectFee) dao.query(ProjectFee.class , projectGranted.getProjectFee().getId());
			}
		}
		if (!request.getParameter("feeNote").isEmpty() && !request.getParameter("feeNote").equals(null)) {
			feeNote = new String(request.getParameter("feeNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("bookFee").isEmpty() && !request.getParameter("bookFee").equals(null)) {
			bookFee = Double.parseDouble(request.getParameter("bookFee").trim());
		}
		if (!request.getParameter("bookNote").isEmpty() && !request.getParameter("bookNote").equals(null)) {
			bookNote = new String(request.getParameter("bookNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("dataFee").isEmpty() && !request.getParameter("dataFee").equals(null)) {
			dataFee = Double.parseDouble(request.getParameter("dataFee").trim());
		}
		if (!request.getParameter("dataNote").isEmpty() && !request.getParameter("dataNote").equals(null)) {
			dataNote = new String(request.getParameter("dataNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("travelFee").isEmpty() && !request.getParameter("travelFee").equals(null)) {
			travelFee = Double.parseDouble(request.getParameter("travelFee").trim());
		}
		if (!request.getParameter("travelNote").isEmpty() && !request.getParameter("travelNote").equals(null)) {
			travelNote = new String(request.getParameter("travelNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("conferenceFee").isEmpty() && !request.getParameter("conferenceFee").equals(null)) {
			conferenceFee = Double.parseDouble(request.getParameter("conferenceFee").trim());
		}
		if (!request.getParameter("conferenceNote").isEmpty() && !request.getParameter("conferenceNote").equals(null)) {
			conferenceNote = new String(request.getParameter("conferenceNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("internationalFee").isEmpty() && !request.getParameter("internationalFee").equals(null)) {
			internationalFee = Double.parseDouble(request.getParameter("internationalFee").trim());
		}
		if (!request.getParameter("internationalNote").isEmpty() && !request.getParameter("internationalNote").equals(null)) {
			internationalNote = new String(request.getParameter("internationalNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("deviceFee").isEmpty() && !request.getParameter("deviceFee").equals(null)) {
			deviceFee = Double.parseDouble(request.getParameter("deviceFee").trim());
		}
		if (!request.getParameter("deviceNote").isEmpty() && !request.getParameter("deviceNote").equals(null)) {
			deviceNote = new String(request.getParameter("deviceNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("consultationFee").isEmpty() && !request.getParameter("consultationFee").equals(null)) {
			consultationFee = Double.parseDouble(request.getParameter("consultationFee").trim());
		}
		if (!request.getParameter("consultationNote").isEmpty() && !request.getParameter("consultationNote").equals(null)) {
			consultationNote = new String(request.getParameter("consultationNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("laborFee").isEmpty() && !request.getParameter("laborFee").equals(null)) {
			laborFee = Double.parseDouble(request.getParameter("laborFee").trim());
		}
		if (!request.getParameter("laborNote").isEmpty() && !request.getParameter("laborNote").equals(null)) {
			laborNote = new String(request.getParameter("laborNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("printFee").isEmpty() && !request.getParameter("printFee").equals(null)) {
			printFee = Double.parseDouble(request.getParameter("printFee").trim());
		}
		if (!request.getParameter("printNote").isEmpty() && !request.getParameter("printNote").equals(null)) {
			printNote = new String(request.getParameter("printNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("indirectFee").isEmpty() && !request.getParameter("indirectFee").equals(null)) {
			indirectFee = Double.parseDouble(request.getParameter("indirectFee").trim());
		}
		if (!request.getParameter("indirectNote").isEmpty() && !request.getParameter("indirectNote").equals(null)) {
			indirectNote = new String(request.getParameter("indirectNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("otherFeeD").isEmpty() && !request.getParameter("otherFeeD").equals(null)) {
			otherFeeD = Double.parseDouble(request.getParameter("otherFeeD").trim());
		}
		if (!request.getParameter("otherNote").isEmpty() && !request.getParameter("otherNote").equals(null)) {
			otherNote = new String(request.getParameter("otherNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("totalFee").isEmpty() && !request.getParameter("totalFee").equals(null)) {
			totalFee = (Double.parseDouble(request.getParameter("totalFee").trim()));
		}
		return SUCCESS;
	}
	
	public ProjectFee doWithAddResultFee(ProjectFee projectFee){
		if ((totalFee != null) && (fundedFee != null)) {
			projectFee.setBookFee(bookFee);
			projectFee.setBookNote(bookNote);
			projectFee.setConferenceFee(conferenceFee);
			projectFee.setConferenceNote(conferenceNote);
			projectFee.setConsultationFee(consultationFee);
			projectFee.setConsultationNote(consultationNote);
			projectFee.setDataFee(dataFee);
			projectFee.setDataNote(dataNote);
			projectFee.setDeviceFee(deviceFee);
			projectFee.setDeviceNote(deviceNote);
			projectFee.setIndirectFee(indirectFee);
			projectFee.setIndirectNote(indirectNote);
			projectFee.setInternationalFee(internationalFee);
			projectFee.setInternationalNote(internationalNote);
			projectFee.setLaborFee(laborFee);
			projectFee.setLaborNote(laborNote);
			projectFee.setOtherFee(otherFeeD);
			projectFee.setOtherNote(otherNote);
			projectFee.setPrintFee(printFee);
			projectFee.setPrintNote(printNote);
			projectFee.setTravelFee(travelFee);
			projectFee.setTravelNote(travelNote);
			projectFee.setFeeNote(feeNote);
			projectFee.setTotalFee(totalFee);
			projectFee.setFundedFee(fundedFee);
			projectFee.setType(projectType());
			return projectFee;
		} else {
			return null;
		}
	}
	/**
	 * 获得默认项目批准号
	 * @param entityId 项目申请id
	 * @return 默认项目批准号
	 */
	protected String getDefaultProjectNumber(String entityId) {
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		String aprrovalNumber = "";//项目批准号
		HashSet<String> specialSet = new HashSet<String>(Arrays.asList(GlobalInfo.SPECIAL_AREAS));
		HashSet<String> xbSet = new HashSet<String>(Arrays.asList(GlobalInfo.XB_AREAS));
		Map map = new HashMap();
		map.put("defaultYear", session.get("defaultYear"));
		ProjectApplication p = (ProjectApplication) baseService.query(ProjectApplication.class, entityId);
		if(p == null){
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_NULL);
			return INPUT;
		}
		//判断项目所属地区
		String hql4Area = "select u.provinceName from University u where u.code =:universityCode";
		map.put("universityCode", p.getUniversityCode().trim());
		List areaList = baseService.query(hql4Area, map);
		if(areaList == null || areaList.isEmpty()){
			jsonMap.put(GlobalInfo.ERROR_INFO, "该项目省份信息缺失");
			return INPUT;
		}
		String provinceName = areaList.get(0).toString();
		//============生成项目批准号============
		//1、项目年度
		aprrovalNumber = (p.getYear()+"").substring(2);//年度(截取后两位)
		//2、项目类别
		if(p.getType().equals("general")) {
			if(!p.getProjectType().equals("专项任务")) {
				if(!specialSet.contains(provinceName)){//一般项目
					aprrovalNumber += "Y";	
				}else if(xbSet.contains(provinceName)){//西部项目
					aprrovalNumber += "X";
				}else if(provinceName.equals("新疆维吾尔自治区")){//新疆项目
					aprrovalNumber += "XJ";
				}else if(provinceName.equals("西藏自治区")){//西藏项目
					aprrovalNumber += "XZ";
				}
				//3、项目类型
				if(p.getProjectType() != null && p.getProjectType().trim().equals("规划基金项目")){
					aprrovalNumber += "JA";
				}else if(p.getProjectType() != null && p.getProjectType().trim().equals("青年基金项目")){
					aprrovalNumber += "JC";
				}else if(p.getProjectType() != null && p.getProjectType().trim().equals("自筹经费项目")){
					aprrovalNumber += "JE";
				}
			} else {
				aprrovalNumber += "JD";
			}
		} else if(p.getType().equals("instp")) {
			aprrovalNumber += "JJD";
		} else {
			jsonMap.put(GlobalInfo.ERROR_INFO, "项目类别不存在");
			return INPUT;
		}
		//3、学科代码(用学科门类)
		String hql4disCode = "select so.code from SystemOption so where so.name =:disciplineType";
		map.put("disciplineType", p.getDisciplineType().trim());
		List codeList = baseService.query(hql4disCode, map);
		if(codeList == null || codeList.isEmpty()){
			jsonMap.put(GlobalInfo.ERROR_INFO, "该项目学科门类信息不完整！");
			return INPUT;
		}
		String disciplineCode = codeList.get(0).toString();
		aprrovalNumber += disciplineCode;
		//4、多次确定立项判断：获取当前年度已经立项项目批准号的最大值，已生成新的批准号（如11YJA760086,则下次的本类别批准号从11YJA760087开始）
		int max = 0;
		map.put("projectNumber", aprrovalNumber+"%");
		String hql = "select gra.number from ProjectGranted gra where gra.type = '" + p.getType() + "' and gra.number like :projectNumber order by gra.number desc";
		List cersF = dao.query(hql,map);
		if(cersF.size() > 0){
			String ExistGraNum = (String) cersF.get(0);
			String graNum = ExistGraNum.substring(aprrovalNumber.length());
			max = Integer.parseInt(graNum) + 1;
		}else{
			max = 1;
		}
		String projectNumber = NumberHandle.numFormat(max, 3);//这里设置项目编号为3位数
		aprrovalNumber += projectNumber;//最终批准号
		return aprrovalNumber;
	}
	
	public String getPageBufferId() {
		return ProjectAction.PAGE_BUFFER_ID;
	}
	public String getProjectid() {
		return projectid;
	}
	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	public Double getApplyFee() {
		return applyFee;
	}
	public void setApplyFee(Double applyFee) {
		this.applyFee = applyFee;
	}
	public Double getOtherFee() {
		return otherFee;
	}
	public void setOtherFee(Double otherFee) {
		this.otherFee = otherFee;
	}
	public Double getApproveFee() {
		return approveFee;
	}
	public void setApproveFee(Double approveFee) {
		this.approveFee = approveFee;
	}
	public Double getBookFee() {
		return bookFee;
	}
	public void setBookFee(Double bookFee) {
		this.bookFee = bookFee;
	}
	public Double getDataFee() {
		return dataFee;
	}
	public void setDataFee(Double dataFee) {
		this.dataFee = dataFee;
	}
	public Double getTravelFee() {
		return travelFee;
	}
	public void setTravelFee(Double travelFee) {
		this.travelFee = travelFee;
	}
	public Double getConferenceFee() {
		return conferenceFee;
	}
	public void setConferenceFee(Double conferenceFee) {
		this.conferenceFee = conferenceFee;
	}
	public Double getInternationalFee() {
		return internationalFee;
	}
	public void setInternationalFee(Double internationalFee) {
		this.internationalFee = internationalFee;
	}
	public Double getDeviceFee() {
		return deviceFee;
	}
	public void setDeviceFee(Double deviceFee) {
		this.deviceFee = deviceFee;
	}
	public Double getConsultationFee() {
		return consultationFee;
	}
	public void setConsultationFee(Double consultationFee) {
		this.consultationFee = consultationFee;
	}
	public Double getLaborFee() {
		return laborFee;
	}
	public void setLaborFee(Double laborFee) {
		this.laborFee = laborFee;
	}
	public Double getPrintFee() {
		return printFee;
	}
	public void setPrintFee(Double printFee) {
		this.printFee = printFee;
	}
	public Double getIndirectFee() {
		return indirectFee;
	}
	public void setIndirectFee(Double indirectFee) {
		this.indirectFee = indirectFee;
	}
	public Double getOtherFeeD() {
		return otherFeeD;
	}
	public void setOtherFeeD(Double otherFeeD) {
		this.otherFeeD = otherFeeD;
	}
	public Double getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}
	public Double getSurplusFee() {
		return surplusFee;
	}
	public void setSurplusFee(Double surplusFee) {
		this.surplusFee = surplusFee;
	}
	public Double getFundedFee() {
		return fundedFee;
	}
	public void setFundedFee(Double fundedFee) {
		this.fundedFee = fundedFee;
	}
	public Double getToFundFee() {
		return toFundFee;
	}
	public void setToFundFee(Double toFundFee) {
		this.toFundFee = toFundFee;
	}
	public String getBookNote() {
		return bookNote;
	}
	public void setBookNote(String bookNote) {
		this.bookNote = bookNote;
	}
	public String getDataNote() {
		return dataNote;
	}
	public void setDataNote(String dataNote) {
		this.dataNote = dataNote;
	}
	public String getTravelNote() {
		return travelNote;
	}
	public void setTravelNote(String travelNote) {
		this.travelNote = travelNote;
	}
	public String getConferenceNote() {
		return conferenceNote;
	}
	public void setConferenceNote(String conferenceNote) {
		this.conferenceNote = conferenceNote;
	}
	public String getInternationalNote() {
		return internationalNote;
	}
	public void setInternationalNote(String internationalNote) {
		this.internationalNote = internationalNote;
	}
	public String getDeviceNote() {
		return deviceNote;
	}
	public void setDeviceNote(String deviceNote) {
		this.deviceNote = deviceNote;
	}
	public String getConsultationNote() {
		return consultationNote;
	}
	public void setConsultationNote(String consultationNote) {
		this.consultationNote = consultationNote;
	}
	public String getLaborNote() {
		return laborNote;
	}
	public void setLaborNote(String laborNote) {
		this.laborNote = laborNote;
	}
	public String getPrintNote() {
		return printNote;
	}
	public void setPrintNote(String printNote) {
		this.printNote = printNote;
	}
	public String getIndirectNote() {
		return indirectNote;
	}
	public void setIndirectNote(String indirectNote) {
		this.indirectNote = indirectNote;
	}
	public String getOtherNote() {
		return otherNote;
	}
	public void setOtherNote(String otherNote) {
		this.otherNote = otherNote;
	}
	public String getFeeNote() {
		return feeNote;
	}
	public void setFeeNote(String feeNote) {
		this.feeNote = feeNote;
	}
	public ProjectFee getProjectFeeApply() {
		return projectFeeApply;
	}
	public void setProjectFeeApply(ProjectFee projectFeeApply) {
		this.projectFeeApply = projectFeeApply;
	}
	public ProjectFee getProjectFeeGranted() {
		return projectFeeGranted;
	}
	public void setProjectFeeGranted(ProjectFee projectFeeGranted) {
		this.projectFeeGranted = projectFeeGranted;
	}
	public ProjectFee getProjectFeeMid() {
		return projectFeeMid;
	}
	public void setProjectFeeMid(ProjectFee projectFeeMid) {
		this.projectFeeMid = projectFeeMid;
	}
	public ProjectFee getProjectFeeEnd() {
		return projectFeeEnd;
	}
	public void setProjectFeeEnd(ProjectFee projectFeeEnd) {
		this.projectFeeEnd = projectFeeEnd;
	}	
	
}
