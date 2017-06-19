package csdc.service.imp;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.FundList;
import csdc.bean.Mail;
import csdc.bean.ProjectFunding;
import csdc.service.IFundListService;
import csdc.tool.ApplicationContainer;
import csdc.tool.DoubleTool;
import csdc.tool.HSSFExport;
import csdc.tool.StringTool;
import csdc.tool.bean.LoginInfo;
import csdc.service.imp.MailService;

public class FundListService extends BaseService implements IFundListService{
	@Autowired
	protected MailService mailService;
	
	/**
	 * 添加清单实体
	 * @param listName清单名称
	 * @param note清单备注
	 * @param attn清单经办人
	 * @param projectType项目类型
	 * @param fundType清单类型
	 * @param rate清单比率
	 * @param projectYear项目年度
	 * @return 清单实体
	 */
	
	public String add(String listName,String note,String attn,String projectType,String fundType,double rate,int projectYear) {
		FundList fundList = new FundList();
		fundList.setCreateDate(new Date());
		fundList.setName(listName);
		fundList.setNote(note);
		fundList.setAttn(attn);
		fundList.setProjectType(projectType);
		fundList.setFundType(fundType);
		fundList.setStatus(0);
		fundList.setRate(rate);
		fundList.setTotal(0.0);
		fundList.setProjectNumber(0);
		fundList.setYear(projectYear);
		dao.add(fundList);
		return fundList.getId();
	}
	
	/**
	 * 修改清单实体
	 * @param listName清单名称
	 * @param note清单备注
	 * @param rate清单比率
	 * @param id清单id
	 * @return 清单实体
	 */
	public FundList modify(String listName,String note,double rate,String id) {
		FundList fundList = dao.query(FundList.class, id);
		fundList.setName(listName);
		fundList.setRate(rate);
		fundList.setNote(note);
		fundList.setTotal(0.0);
		dao.modify(fundList);
		return fundList;
	}
	
	/**
	 * 根据清单id取得清单实体
	 * @param id清单id
	 * @return 清单实体
	 */
	public FundList getFundList(String id) {
		FundList fundList = dao.query(FundList.class, id);
		return fundList;
	}
	
	/**
	 * 根据清单id取得按学校查看的列表数据
	 * @param id清单id
	 * @return 处理后的列表信息
	 */
	public List getUnitFundList(String id) {
		Map parMap = new HashMap();
		parMap.put("entityId", id);
		StringBuffer hql = new StringBuffer();
		hql.append("select a.name, pf.fbankAccountName, pf.fbankAccount, pf.fbank, ap.name, ac.name, a.type, a.email, a.id, gra.name, gra.number, gra.applicantName, uni.name, so.name, app.year, gra.approveFee, ");
		FundList fundList = this.getFundList(id);
		if (fundList.getProjectType().equals("general")) {//一般项目
			if (fundList.getFundType().equals("granted")) {//立项拨款清单
				hql.append(" pf.fee from Agency a, GeneralGranted gra, ProjectFunding pf left outer join a.province ap left outer join a.city ac left outer join gra.application app "+ 
					 	   "left outer join gra.university uni left outer join gra.subtype so where uni.id = a.id and app.finalAuditStatus=3 and app.finalAuditResult=2 and pf.grantedId=gra.id and pf.fundList.id = :entityId and pf.type = 1 ");
			}else if (fundList.getFundType().equals("mid")) {//中检拨款清单
				hql.append(" pf.fee from Agency a, GeneralGranted gra, ProjectFunding pf left outer join a.province ap left outer join a.city ac left outer join gra.application app "+ 
						   "left outer join gra.university uni left outer join gra.subtype so where uni.id = a.id and app.finalAuditStatus=3 and app.finalAuditResult=2 and pf.grantedId=gra.id and pf.fundList.id = :entityId and pf.type = 2 ");
			}else if (fundList.getFundType().equals("end")) {//结项拨款清单
				hql.append(" pf.fee from Agency a, GeneralGranted gra, ProjectFunding pf left outer join a.province ap left outer join a.city ac left outer join gra.application app "+ 
						   "left outer join gra.university uni left outer join gra.subtype so where uni.id = a.id and app.finalAuditStatus=3 and app.finalAuditResult=2 and pf.grantedId=gra.id and pf.fundList.id = :entityId and pf.type = 3 ");
			}
		}else if (fundList.getProjectType().equals("instp")) {//基地项目
			if (fundList.getFundType().equals("granted")) {//立项拨款清单
				hql.append(" pf.fee from Agency a, InstpGranted gra, ProjectFunding pf left outer join a.province ap left outer join a.city ac left outer join gra.application app "+ 
					 	   "left outer join gra.university uni left outer join gra.subtype so where uni.id = a.id and app.finalAuditStatus=3 and app.finalAuditResult=2 and pf.grantedId=gra.id and pf.fundList.id = :entityId and pf.type = 1 ");
			}else if (fundList.getFundType().equals("mid")) {//中检拨款清单
				hql.append(" pf.fee from Agency a, InstpGranted gra, ProjectFunding pf left outer join a.province ap left outer join a.city ac left outer join gra.application app "+ 
						   "left outer join gra.university uni left outer join gra.subtype so where uni.id = a.id and app.finalAuditStatus=3 and app.finalAuditResult=2 and pf.grantedId=gra.id and pf.fundList.id = :entityId and pf.type = 2 ");
			}else if (fundList.getFundType().equals("end")) {//结项拨款清单
				hql.append(" pf.fee from Agency a, InstpGranted gra, ProjectFunding pf left outer join a.province ap left outer join a.city ac left outer join gra.application app "+ 
						   "left outer join gra.university uni left outer join gra.subtype so where uni.id = a.id and app.finalAuditStatus=3 and app.finalAuditResult=2 and pf.grantedId=gra.id and pf.fundList.id = :entityId and pf.type = 3 ");
			}
			
		}else if (fundList.getProjectType().equals("post")) {//后期资助项目
			if (fundList.getFundType().equals("granted")) {//立项拨款清单
				hql.append(" pf.fee from Agency a, PostGranted gra, ProjectFunding pf left outer join a.province ap left outer join a.city ac left outer join gra.application app "+ 
					 	   "left outer join gra.university uni left outer join gra.subtype so where uni.id = a.id and app.finalAuditStatus=3 and app.finalAuditResult=2 and pf.grantedId=gra.id and pf.fundList.id = :entityId and pf.type = 1 ");
			}else if (fundList.getFundType().equals("mid")) {//中检拨款清单
				hql.append(" pf.fee from Agency a, PostGranted gra, ProjectFunding pf left outer join a.province ap left outer join a.city ac left outer join gra.application app "+ 
						   "left outer join gra.university uni left outer join gra.subtype so where uni.id = a.id and app.finalAuditStatus=3 and app.finalAuditResult=2 and pf.grantedId=gra.id and pf.fundList.id = :entityId and pf.type = 2 ");
			}else if (fundList.getFundType().equals("end")) {//结项拨款清单
				hql.append(" pf.fee from Agency a, PostGranted gra, ProjectFunding pf left outer join a.province ap left outer join a.city ac left outer join gra.application app "+ 
						   "left outer join gra.university uni left outer join gra.subtype so where uni.id = a.id and app.finalAuditStatus=3 and app.finalAuditResult=2 and pf.grantedId=gra.id and pf.fundList.id = :entityId and pf.type = 3 ");
			}
			
		}else if (fundList.getProjectType().equals("entrust")) {//委托应急课题
			if (fundList.getFundType().equals("granted")) {//立项拨款清单
				hql.append(" pf.fee from Agency a, EntrustGranted gra, ProjectFunding pf left outer join a.province ap left outer join a.city ac left outer join gra.application app "+ 
					 	   "left outer join gra.university uni left outer join gra.subtype so where uni.id = a.id and app.finalAuditStatus=3 and app.finalAuditResult=2 and pf.grantedId=gra.id and pf.fundList.id = :entityId and pf.type = 1 ");
			}else if (fundList.getFundType().equals("mid")) {//中检拨款清单
				hql.append(" pf.fee from Agency a, EntrustGranted gra, ProjectFunding pf left outer join a.province ap left outer join a.city ac left outer join gra.application app "+ 
						   "left outer join gra.university uni left outer join gra.subtype so where uni.id = a.id and app.finalAuditStatus=3 and app.finalAuditResult=2 and pf.grantedId=gra.id and pf.fundList.id = :entityId and pf.type = 2 ");
			}else if (fundList.getFundType().equals("end")) {//结项拨款清单
				hql.append(" pf.fee from Agency a, EntrustGranted gra, ProjectFunding pf left outer join a.province ap left outer join a.city ac left outer join gra.application app "+ 
						   "left outer join gra.university uni left outer join gra.subtype so where uni.id = a.id and app.finalAuditStatus=3 and app.finalAuditResult=2 and pf.grantedId=gra.id and pf.fundList.id = :entityId and pf.type = 3 ");
			}
			
		}else if (fundList.getProjectType().equals("key")) {//重大攻关项目
			if (fundList.getFundType().equals("granted")) {//立项拨款清单
				hql.append(" pf.fee from Agency a, KeyGranted gra, ProjectFunding pf left outer join a.province ap left outer join a.city ac left outer join gra.application app "+ 
					 	   "left outer join gra.university uni left outer join gra.subtype so where uni.id = a.id and app.finalAuditStatus=3 and app.finalAuditResult=2 and pf.grantedId=gra.id and pf.fundList.id = :entityId and pf.type = 1 ");
			}else if (fundList.getFundType().equals("mid")) {//中检拨款清单
				hql.append(" pf.fee from Agency a, KeyGranted gra, ProjectFunding pf left outer join a.province ap left outer join a.city ac left outer join gra.application app "+ 
						   "left outer join gra.university uni left outer join gra.subtype so where uni.id = a.id and app.finalAuditStatus=3 and app.finalAuditResult=2 and pf.grantedId=gra.id and pf.fundList.id = :entityId and pf.type = 2 ");
			}else if (fundList.getFundType().equals("end")) {//结项拨款清单
				hql.append(" pf.fee from Agency a, KeyGranted gra, ProjectFunding pf left outer join a.province ap left outer join a.city ac left outer join gra.application app "+ 
						   "left outer join gra.university uni left outer join gra.subtype so where uni.id = a.id and app.finalAuditStatus=3 and app.finalAuditResult=2 and pf.grantedId=gra.id and pf.fundList.id = :entityId and pf.type = 3 ");
			}
		}
		List<Object[]> listAll = dao.query(hql.toString(), parMap);
		Map<String, List<Object[]>> univName2Excel = new HashMap<String, List<Object[]>>();
		for (Object[] objs : listAll) {
			String univName = (String) objs[0];
			List<Object[]> univProjects = univName2Excel.get(univName);
			if (univProjects == null) {
				univProjects = new ArrayList<Object[]>();
				univName2Excel.put(univName, univProjects);//存放学校对应的项目信息
			}
			Object[] projects = new Object[9];
			for (int i = 0; i < 8; i++) {//放入项目信息
				projects[i] = objs[i + 9];
			}
			projects[8] = objs[7];//放入学校的EMAIL地址
			univProjects.add(projects);
		}
		
		Map session = ActionContext.getContext().getSession();
		session.put("univName2Excel", univName2Excel);
		if (session.containsKey("searchForUnitFundListFlag")) {
			System.out.println(session.get("searchForUnitFundListFlag"));
			String flag = session.get("searchForUnitFundListFlag").toString();
			if (flag.equals("1")) {//如果是按学校查看
				String type = session.get("searchType4unit").toString();
				int searchType4unit = Integer.parseInt(type);//检索类型
				String keyword4unit = (String) session.get("keyword4unit");
				keyword4unit = (keyword4unit == null)? "" : keyword4unit.toLowerCase();//检索关键字
				if(!keyword4unit.equals("")){
					parMap.put("keyword4unit", "%" + keyword4unit + "%");
					if (searchType4unit == 1) {//按学校名称检索
						hql.append(" and LOWER(a.name) like :keyword4unit");
					}else if (searchType4unit == 2) {//按省检索
						hql.append(" and LOWER(ap.name) like :keyword4unit");
					}else if (searchType4unit == 3) {//按是否部署高校检索
						if (keyword4unit.equals("是")) {
							hql.append(" and a.type = 3");
						}else if (keyword4unit.equals("否")) {
							hql.append(" and a.type != 3");
						}
					}else {//不指定检索类型
						if (keyword4unit.equals("是")) {
							hql.append(" and a.type = 3");
						}else if (keyword4unit.equals("否")) {
							hql.append(" and a.type != 3");
						}else {
							hql.append(" and (LOWER(a.name) like :keyword4unit or LOWER(ap.name) like :keyword4unit)");
						}
					}
				}
			}
		}
		
		
		List<Object[]> unitFundListOriginal = new ArrayList<Object[]>();
		List<Object[]> list = dao.query(hql.toString(), parMap);
		Map<String, Double> univName2fund = new HashMap<String, Double>();
//		Map<String, List<Object[]>> univName2Excel = new HashMap<String, List<Object[]>>();
		for (Object[] objs : list) {
			String univName = (String) objs[0];
			Double curFund = (Double) objs[16];
			Double existFund = univName2fund.get(univName);
			univName2fund.put(univName, (existFund != null) ? DoubleTool.sum(curFund, existFund) : curFund);//存放学校对应的拨款总额
			
//			List<Object[]> univProjects = univName2Excel.get(univName);
//			if (univProjects == null) {
//				univProjects = new ArrayList<Object[]>();
//				univName2Excel.put(univName, univProjects);
//			}
//			Object[] projects = new Object[8];
//			for (int i = 0; i < 8; i++) {
//				projects[i] = objs[i + 10];
//			}
//			univProjects.add(projects);
		}
		for (Object[] objs : list) {
			if (univName2fund.containsKey(objs[0])) {
				objs[16] = univName2fund.get(objs[0]);
				unitFundListOriginal.add(objs);
				univName2fund.remove(objs[0]);
			}
		}
		List<Object[]> unitFundList = new ArrayList<Object[]>();
		int index = 0;
		for (Object[] objs : unitFundListOriginal) {
			Object[] objects = new Object[11];
			index += 1;
			objects[0] = index;//序号
			objects[1] = objs[0];//学校名称
			objects[2] = objs[1];//开名名称
			objects[3] = objs[2];//银行账号
			objects[4] = objs[3];//开户银行
			objects[5] = objs[4];//所在省
			objects[6] = objs[5];//所在市
			objects[7] = objs[6];//是否部署
			objects[8] = objs[7];//邮箱地址
			objects[9] = objs[8];//机构id
			objects[10] = objs[16];//拨款金额
			unitFundList.add(objects);
		}
		return unitFundList;
	}
	
	/**
	 * 删除拨款清单
	 * @param id清单id
	 */
	public void deleteFundList(String id){
		FundList fundList = dao.query(FundList.class, id);
		String hql = null;
		Map parMap = new HashMap();
		parMap.put("id", id);
		if (fundList.getStatus() == 0) {
			hql = "from ProjectFunding pf where pf.fundList.id = :id ";
			List<ProjectFunding> projectFundings = dao.query(hql, parMap);
			for (ProjectFunding projectFunding : projectFundings) {
				projectFunding.setFee((double) 0);
				projectFunding.setFundList(null);
				dao.modify(projectFunding);
			}
			dao.delete(fundList);
		}
	}
	
	/**
	 * 按学校添加拨款通知邮件
	 * @param id清单id,
	 * @param loginer登陆者信息,
	 */
	public void addEmailToUnit(String id,LoginInfo loginer) {
		Map session = ActionContext.getContext().getSession();
		Map parMap = new HashMap();
		parMap.put("entityId", id);
		FundList fundList = this.getFundList(id);
		
		if (session.containsKey("univName2Excel")) {
			Map<String, List<Object[]>> univName2Excel = (Map<String, List<Object[]>>) session.get("univName2Excel");
			for (Entry<String, List<Object[]>> entry : univName2Excel.entrySet()) {
				String univName = entry.getKey();
				if (univName.contains("[")) {//去除可能存在的中括号
					univName = univName.replace("[", "").trim();
				}if (univName.contains("]")) {
					univName = univName.replace("]", "").trim();
				}
//				univName = univName.replaceAll("[", "").trim();
//				univName = univName.replaceAll("]", "").trim();
				List<Object[]> univProjects = entry.getValue();
				List<Object[]> univProjects4xls = new ArrayList<Object[]>();//用于存放学校所对应的项目
				int index = 0;
				String sendTo = null;
				for (Object[] objs : univProjects) {
					index += 1;
					sendTo = objs[8].toString();
					Object[] univProject = new Object[9];
					univProject[0] = index;
					for (int i = 0; i < 8; i++) {
						univProject[i+1] = objs[i];
					}
					univProjects4xls.add(univProject);
				}
				
				Mail mail = new Mail();
				mail.setSendTo(sendTo);
//				mail.setSendTo("xn1893@qq.com");
				StringBuffer subject = new StringBuffer();
				subject.append(fundList.getYear() + "年度");
				if (fundList.getProjectType().equals("general")) {
					subject.append("一般项目");
				}else if (fundList.getProjectType().equals("instp")) {
					subject.append("基地项目");
				}else if (fundList.getProjectType().equals("post")) {
					subject.append("后期资助项目");
				}else if (fundList.getProjectType().equals("entrust")) {
					subject.append("委托应急课题");
				}else if (fundList.getProjectType().equals("key")) {
					subject.append("重大攻关项目");
				}
				if (fundList.getFundType().equals("granted")) {
					subject.append("立项拨款");
				}else if (fundList.getFundType().equals("mid")) {
					subject.append("中检拨款");
				}else if (fundList.getFundType().equals("end")) {
					subject.append("结项拨款");
				}
				mail.setSubject("[SMDB] " + "关于下发" + univName + subject.toString() + "的通知");
				mail.setReplyTo("serv@csdc.info");// 认证地址
				mail.setBody("123");
				mail.setIsHtml(1);
				mail.setCreateDate(new Date());
				mail.setFinishDate(null);
				mail.setSendTimes(0);
				mail.setStatus(0);
				// 设置邮件发送账号及发送者属性
				String accountBelong = "";// 账号所属名称
				if (loginer != null) {// 当前处于登录状态，则从登录对象中获取账号及账号所属者信息
					mail.setAccount(loginer.getAccount());// 设置发送账号
					
					// 从loginer中获取发送者名称信息
					if (loginer.getCurrentBelongUnitName() != null) {// 所属机构信息存在，则读取机构名称
						accountBelong = loginer.getCurrentBelongUnitName();
					}
					
					if (loginer.getCurrentPersonName() != null) {// 所属人员信息存在，则读取人员名称
						accountBelong = loginer.getCurrentPersonName();
					}
					mail.setAccountBelong(accountBelong);// 设置发送者名称
				} 
				
				
				dao.add(mail);
				String header = univName + subject.toString() +"明细";
				String[] title = {"序号","项目名称", "批准号", "负责人", "学校名称", "项目子类", "项目年度", "批准经费(万元)","拨款金额(万元)"};
				String realPath = ApplicationContainer.sc.getRealPath("");
				String filepath = (String)ApplicationContainer.sc.getAttribute("MailFileUploadPath");
				realPath = realPath.replace('\\', '/');
				HSSFExport.createExcel(univProjects4xls, header, title, realPath + filepath + "/" +univName+ ".xls" );
				
				File xlsFile = new File(realPath + filepath + "/" + univName+ ".xls");
				String xlsPath = mailService.renameFile(mail.getId(), xlsFile);
				
				List<String> attachments = new ArrayList<String>();
				List<String> attachmentNames = new ArrayList<String>();
				attachments.add(xlsPath);
				attachmentNames.add(univName +  subject.toString() + "清单.xls");
				mail.setAttachment(StringTool.joinString(attachments.toArray(new String[0]), "; "));
				mail.setAttachmentName(StringTool.joinString(attachmentNames.toArray(new String[0]), "; "));
				dao.modify(mail);
			}
		}
	}
	
}