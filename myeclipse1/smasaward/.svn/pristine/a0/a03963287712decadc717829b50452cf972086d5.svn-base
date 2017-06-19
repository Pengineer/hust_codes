package csdc.tool.execution.importer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.sun.org.apache.xpath.internal.operations.And;

import csdc.bean.ProjectApplication;
import csdc.bean.SmdbProjectApplication;
import csdc.dao.IHibernateBaseDao;
import csdc.service.webService.client.SmdbClient;
import csdc.tool.bean.MemberInfo;
import csdc.tool.bean.SynchronizationInfo;
import csdc.tool.webService.smdb.client.SOAPEnvTool;


/**
 *在smas端同步smdb的系统初评查重结果
 *是由于smas端的查重条件每次都会有变化，所以需要先同步smas其他数据
 *初评结果信息可以不同步，因为smdb中已经有初评结果信息
 *部级初评审核结果信息是最终结果，只需要从smas同步最终的部级审核信息至smdb即可
 */
public class SynchronizationFixSmasProgram extends SynchronizationProjectData {

	private SmdbClient smdbClient;
	private String year; // 同步部级审核信息年份
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//转成规律日期格式的方法

	protected List<String> badItemsList; //无法解析的id集合,最终打印出来，核查数据
	private Map<String, String> argsMap;//数据同步参数
	
	public SynchronizationFixSmasProgram() {
		
	}
	/**
	 * 项目申请数据同步
	 * @param smdbService
	 * @param dao
	 * @param projectType
	 * @param year
	 * @param fetchSize
	 */
	public SynchronizationFixSmasProgram(SmdbClient smdbClient, IHibernateBaseDao dao, String year) {
		this.smdbClient = smdbClient;
		this.year = year;
		super.dao = dao;

		if (argsMap == null) {
			argsMap = new HashMap<String, String>();
		} else {
			argsMap.clear();
		}
		
		if (badItemsList == null) {
			badItemsList = new LinkedList<String>();
		}
		//执行
		init();
	}
	
	private void init() {

		 
	}
	

	@Override
	protected void work() throws Throwable {
		
		
		
//		fixProjectApplicationCFileInfo();
		fixProjectApplicationMemeber2015Info();
	}

	/**
	 * 按照新的项目成员变更方案，将项目申请表的项目成员信息按照
	 * csdc.tool.bean.MemberInfo中设计的字段进行同步并保存在C_MEMBERS字段中
	 * 修复方案是数据库中所有年份的数据2010 - 2015 六年数据
	 * 数据是不一样的，目前只有2015年的项目数据在中间表中有记录，其他年份需要根据条件获取
	 * 步骤一：先修复2015年的数据,利用中间表的数据
	 * 步骤二：在分批修复其他年份的数据
	 * 
	 * 
	 */
	protected void fixProjectApplicationMemeber2015Info() throws Throwable{
		Long begin = System.currentTimeMillis();
		String type = "general";
		Map<String, Object> argumentMap = new HashMap<String, Object>();
		argumentMap.put("year", 2015);
		argumentMap.put("type", "general");
		String hqlfirstAudit = "select smdbApp from SmdbProjectApplication smdbApp, ProjectApplication p where p.type = :type and p.year = :year " +
				" and p.id = smdbApp.smasApplID";

		List<SmdbProjectApplication> resultList = dao.query(hqlfirstAudit, argumentMap);
		if (!resultList.isEmpty()) {
			SimpleDateFormat sdf=new SimpleDateFormat("E[yyyy-MM-dd HH:mm:ss]");
			System.out.println("smas中  " + year +" 年 " + type +"类型项目共有：" + resultList.size() + "个项目需要处理");
			for (int i = 0; i < resultList.size(); i++) {
				System.out.println(sdf.format(new Date()) + ":开始获取第 " + (i+1) + " 个");
				SmdbProjectApplication middleProject = (SmdbProjectApplication) resultList.get(i);
				String smasAppID = middleProject.getSmasApplID();
				String smdbAppID = middleProject.getSmdbApplID();
				if (smdbAppID == null ||smdbAppID.equals("")) {
					badItemsList.add(smasAppID + "(smdbAppID:" + smdbAppID+ ")");
					continue;
				}
				argsMap.put("projectApplicationID",smdbAppID);
				//更新字段
				String backData = smdbClient.invokeDirectory("fwzx", "csdc702", "SmasService", "fixSmasProgram", argsMap);
				if (null == backData) {
					throw new RuntimeException("执行异常");//事务回滚
				} else if(backData.equals("busy")) {
					//3分钟后再请求
					Thread.sleep(smdbClient.WaitUnitTime);
					i--;//
				} else {
					Map backMap = SOAPEnvTool.parseNormalResponse(backData);
					
					if (backMap.get("type") != null && backMap.get("type").equals("data")) {
						Element contentElement = (Element) backMap.get("content");
						//批量入库,返回Element元素
						persistProjectCMembersDomXml(contentElement, smasAppID, smdbAppID);
						
					} else if (backMap.get("type") != null && backMap.get("type").equals("notice")){
						if (backMap.get("content").equals("success")) {
							System.out.println("数据同步工作完毕！");
						}
					} else if (backMap.get("type") != null && backMap.get("type").equals("error")) {
						System.out.println("\n######\n Stop! information : error" + backMap.get("content") + "\n#######");
						//此处转译为RuntimeException，使Spring能够回滚
						throw new RuntimeException((String) backMap.get("content"));//事物回滚
					}
				}
			}
			Long end = System.currentTimeMillis();
			System.out.println("成功入库本批数据[用时：" + (end - begin) + " ms]。");
			printRecordedListContent(badItemsList);
		}
		
		
	}
	
	/**
	 * 修复smas申报表中的C_file字段
	 * 按照smdb项目申报表C_File字段，更新smas对应的字段
	 * @throws Throwable
	 */
	protected void fixProjectApplicationCFileInfo() throws Throwable {

		Long begin = System.currentTimeMillis();
		String type = "general";
		Map<String, Object> argumentMap = new HashMap<String, Object>();
		argumentMap.put("year", 2015);
		argumentMap.put("type", "general");
		
		String hqlfirstAudit = "select smdbApp from SmdbProjectApplication smdbApp, ProjectApplication p where p.type = :type and p.year = :year " +
				" and p.id = smdbApp.smasApplID";
	
		List<SmdbProjectApplication> resultList = dao.query(hqlfirstAudit, argumentMap);
		if (!resultList.isEmpty()) {
			SimpleDateFormat sdf=new SimpleDateFormat("E[yyyy-MM-dd HH:mm:ss]");
			System.out.println("smas中  " + year +" 年 " + type +"类型项目共有：" + resultList.size() + "个项目需要同步");
			for (int i = 0; i < resultList.size(); i++) {
				System.out.println(sdf.format(new Date()) + ":开始获取第 " + (i+1) + " 个");
				SmdbProjectApplication middleProject = (SmdbProjectApplication) resultList.get(i);
				String smasAppID = middleProject.getSmasApplID();
				String smdbAppID = middleProject.getSmdbApplID();
				if (smdbAppID == null ||smdbAppID.equals("")) {
					badItemsList.add(smasAppID + "(" + smdbAppID+ ")");
					continue;
				}
				argsMap.put("projectApplicationID",smdbAppID);
				//更新字段
				String backData = smdbClient.invokeDirectory("fwzx", "csdc702", "SmasService", "fixSmasProgram", argsMap);
				if (null == backData) {
					throw new RuntimeException("执行异常");//事务回滚
				} else if(backData.equals("busy")) {
					//3分钟后再请求
					Thread.sleep(smdbClient.WaitUnitTime);
					i--;//
				} else {
					Map backMap = SOAPEnvTool.parseNormalResponse(backData);
					
					if (backMap.get("type") != null && backMap.get("type").equals("data")) {
						Element contentElement = (Element) backMap.get("content");
						//批量入库
						persistProjectCFileDomXml(contentElement, smasAppID, smdbAppID);
						
					} else if (backMap.get("type") != null && backMap.get("type").equals("notice")){
						if (backMap.get("content").equals("success")) {
							System.out.println("数据同步工作完毕！");
						}
					} else if (backMap.get("type") != null && backMap.get("type").equals("error")) {
						System.out.println("\n######\n Stop! information : error" + backMap.get("content") + "\n#######");
						//此处转译为RuntimeException，使Spring能够回滚
						throw new RuntimeException((String) backMap.get("content"));//事物回滚
					}
				}
			}
			Long end = System.currentTimeMillis();
			System.out.println("成功入库本批数据[用时：" + (end - begin) + " ms]。");
			printRecordedListContent(badItemsList);
		}
		
	}

	/**
	 * 修复smas申报表中的学科代码更新过滤程序
	 * smas的形式和smdb不一样
	 * @throws Throwable
	 */
	protected void fixProjectApplicationDiscipline() throws Throwable {
		
		Long begin = System.currentTimeMillis();
		String type = "general";
		Map<String, Object> argumentMap = new HashMap<String, Object>();
		argumentMap.put("year", 2015);
		argumentMap.put("type", "general");
		
		String hqlfirstAudit = "select smdbApp from SmdbProjectApplication smdbApp, ProjectApplication p where p.type = :type and p.year = :year " +
				" and p.id = smdbApp.smasApplID";
	
		List<SmdbProjectApplication> resultList = dao.query(hqlfirstAudit, argumentMap);
		if (!resultList.isEmpty()) {
			SimpleDateFormat sdf=new SimpleDateFormat("E[yyyy-MM-dd HH:mm:ss]");
			System.out.println("smas中  " + year +" 年 " + type +"类型项目共有：" + resultList.size() + "个项目需要同步");
			for (int i = 0; i < resultList.size(); i++) {
				System.out.println(sdf.format(new Date()) + ":开始获取第 " + (i+1) + " 个");
				SmdbProjectApplication middleProject = (SmdbProjectApplication) resultList.get(i);
				String smasAppID = middleProject.getSmasApplID();
				String smdbAppID = middleProject.getSmdbApplID();
				if (smdbAppID == null ||smdbAppID.equals("")) {
					badItemsList.add(smasAppID + "(" + smdbAppID+ ")");
					continue;
				}
				argsMap.put("projectApplicationID",smdbAppID);
				String backData = smdbClient.invokeDirectory("fwzx", "csdc702", "SmasService", "fixSmasProgram", argsMap);
				if (null == backData) {
					throw new RuntimeException("执行异常");//事务回滚
				} else if(backData.equals("busy")) {
					//3分钟后再请求
					Thread.sleep(smdbClient.WaitUnitTime);
					i--;//
				} else {
					Map backMap = SOAPEnvTool.parseNormalResponse(backData);
					
					if (backMap.get("type") != null && backMap.get("type").equals("data")) {
						String contentDomXml = (String) backMap.get("content");
						//批量入库
						persistProjectDisciplineDomXml(contentDomXml,  smasAppID, smdbAppID);
						
					} else if (backMap.get("type") != null && backMap.get("type").equals("notice")){
						if (backMap.get("content").equals("success")) {
							System.out.println("数据同步工作完毕！");
						}
					} else if (backMap.get("type") != null && backMap.get("type").equals("error")) {
						System.out.println("\n######\n Stop! information : error" + backMap.get("content") + "\n#######");
						//此处转译为RuntimeException，使Spring能够回滚
						throw new RuntimeException((String) backMap.get("content"));//事物回滚
					}
				}
			}
			Long end = System.currentTimeMillis();
			System.out.println("成功入库本批数据[用时：" + (end - begin) + " ms]。");
			printRecordedListContent(badItemsList);
		}
		
	}

	/**
	 * 修复smas2015一般项目初审字段信息
	 * @throws Throwable
	 */
	protected void fixProjectApplicationFirstAuditInfo() throws Throwable {
		
		Long begin = System.currentTimeMillis();
		String type = "general";
		argsMap.put("year", year);
		argsMap.put("type", type);
		
		String hqlfirstAudit = "select smdbApp.smasApplID, smdbApp.smdbApplID from SmdbProjectApplication smdbApp, ProjectApplication p where p.type = :type and p.year = :year " +
				" and p.id = smdbApp.smasApplID";
	
		List<Object[]> resultList = dao.query(hqlfirstAudit, argsMap);
		if (!resultList.isEmpty()) {
			SimpleDateFormat sdf=new SimpleDateFormat("E[yyyy-MM-dd HH:mm:ss]");
			System.out.println("smas中  " + year +" 年 " + type +"类型项目共有：" + resultList.size() + "个需要同步（部级）初评结果信息");
			for (int i = 0; i < resultList.size(); i++) {
				System.out.println(sdf.format(new Date()) + ":开始获取第 " + (i+1) + " 个");
				String[] ids = (String[]) resultList.get(i);
				String smasAppID = ids[0];
				String smdbAppID = ids[1];
				if (smdbAppID == null ||smdbAppID.equals("")) {
					badItemsList.add(smasAppID + "(" + smdbAppID+ ")");
					continue;
				}
				argsMap.put("projectApplicationID",smdbAppID);//500~ 1000
				String backData = smdbClient.invokeDirectory("fwzx", "csdc702", "SmasService", "requestApplicationFirstAudit", argsMap);
				if (null == backData) {
					throw new RuntimeException("执行异常");//事务回滚
				} else if(backData.equals("busy")) {
					//3分钟后再请求
					Thread.sleep(smdbClient.WaitUnitTime);
					i--;//
				} else {
					Map backMap = SOAPEnvTool.parseNormalResponse(backData);
					
					if (backMap.get("type") != null && backMap.get("type").equals("data")) {
						String contentDomXml = (String) backMap.get("content");
						//批量入库
						persistMinistrateFirstAuditDomXml(contentDomXml, ids);
						
					} else if (backMap.get("type") != null && backMap.get("type").equals("notice")){
						if (backMap.get("content").equals("success")) {
							System.out.println("数据同步工作完毕！");
						}
					} else if (backMap.get("type") != null && backMap.get("type").equals("error")) {
						System.out.println("\n######\n Stop! information : error" + backMap.get("content") + "\n#######");
						//此处转译为RuntimeException，使Spring能够回滚
						throw new RuntimeException((String) backMap.get("content"));//事物回滚
					}
				}
			}
			Long end = System.currentTimeMillis();
			System.out.println("成功入库本批数据[用时：" + (end - begin) + " ms]。");
			//输出打印无法解析的信息
			printRecordedListContent(badItemsList);
		}

	}
	/**
	 * 处理在线通过的数据信息，逐条持久化数据库并记录中间表
	 * @param contentXmlString
	 * @throws Exception 
	 */
	private void persistMinistrateFirstAuditDomXml(String contentDomXml,String[] ids) throws Exception {
		String smasAppId = ids[0];
		String smdbAppId = ids[1];	
		//数据处理
		Document recordsDom = DocumentHelper.parseText(contentDomXml);
		Element rootElement = recordsDom.getRootElement();
		List<Element> elemtList = rootElement.element("records").elements("item");
		//(一次请求对应唯一的结果)
		for (int j = 0; j < elemtList.size(); j++) {
			Element item = elemtList.get(j);
			if (item != null) {
				//执行新数据同步
				try {
					addItemObject(item, ids);
				} catch (Exception e) {
					badItemsList.add(smasAppId + "(" + smdbAppId+ ")");
				}
				
				clearDao(false);
			}
		}
	}
	
	/**
	 * 处理在线通过的数据信息，逐条持久化数据库并记录中间表
	 * @param contentXmlString
	 * @throws Exception 
	 */
	private void persistProjectCFileDomXml(Element contentElement, String smasAppId, String smdbAppId) throws Exception {
		//数据处理
		List<Element> elemtList = contentElement.elements("item");
		//(一次请求对应唯一的结果)
		for (int j = 0; j < elemtList.size(); j++) {
			Element item = elemtList.get(j);
			if (item != null) {
				//执行新数据同步
				try {
					String smdbPojAppId = getArgumentByTag(item, "id");
					if (smdbPojAppId.equals(smdbAppId)) {//和smas中的入库信息一致
						//不存在,入库
						String CFile = getArgumentByTag(item, "CFile");
						
						ProjectApplication projectApplication = (ProjectApplication) dao.query(ProjectApplication.class, smasAppId);
						if (!CFile.equals("null") ) {
							projectApplication.setFile(CFile);
							dao.addOrModify(projectApplication);
						}
						
					} else {
						//入库信息不一致
						throw new RuntimeException("入库信息不一致");
					}
				} catch (Exception e) {
					badItemsList.add(smasAppId + "(" + smdbAppId+ ")");
				}
				
				clearDao(false);
			}
		}
	}
	/**
	 * 处理在线通过的数据信息，逐条持久化数据库并记录中间表
	 * @param contentXmlString
	 * @throws Exception 
	 */
	private void persistProjectCMembersDomXml(Element contentElement, String smasAppId, String smdbAppId) throws Exception {
		//数据处理
		List<Element> elemtList = contentElement.elements("item");
		//(一次请求对应唯一的结果)
		for (int j = 0; j < elemtList.size(); j++) {
			Element item = elemtList.get(j);
			if (item != null) {
				//执行新数据同步
				try {
					String smdbPojAppId = getArgumentByTag(item, "id");
					List<Element> memberList = item.elements("member");
					
					if (smdbPojAppId.equals(smdbAppId)) {//和smas中的入库信息一致
						if (memberList!= null && !memberList.isEmpty()) {
							Map jsonMap = new HashMap<String, Object>();//项目成员信息Map
							for (Element memberElement : memberList) {
								String memberSn = getArgumentByTag(memberElement, "memberSn");
								String memberType = getArgumentByTag(memberElement, "memberType");
								String memberName = getArgumentByTag(memberElement, "memberName");
								String gender = getArgumentByTag(memberElement, "gender");
								String birthday = getArgumentByTag(memberElement, "birthday");
								String idcardType = getArgumentByTag(memberElement, "idcardType");
								String idcardNumber = getArgumentByTag(memberElement, "idcardNumber");
								String foreign = getArgumentByTag(memberElement, "foreign");
								String education = getArgumentByTag(memberElement, "education");
								String degree = getArgumentByTag(memberElement, "degree");
								String title = getArgumentByTag(memberElement, "title");
								String agencyName = getArgumentByTag(memberElement, "agencyName");
								String divisionName = getArgumentByTag(memberElement, "divisionName");
								String job = getArgumentByTag(memberElement, "job");
								String email = getArgumentByTag(memberElement, "email");
								String mobile = getArgumentByTag(memberElement, "mobile");
								String phone = getArgumentByTag(memberElement, "phone");
								String postcode = getArgumentByTag(memberElement, "postcode");
								String address = getArgumentByTag(memberElement, "address");
								String isDirector = getArgumentByTag(memberElement, "isDirector");
								MemberInfo memberInfo = new MemberInfo();
								if (memberSn != null && !memberSn.equals("null")) {
									memberInfo.setMemberSn(Integer.parseInt(memberSn));//成员序号
								}
								if (memberType != null && !memberType.equals("null")) {
									memberInfo.setMemberType(Integer.parseInt(memberType));
								}
								if (memberName != null && !memberName.equals("null")) {
									memberInfo.setMemberName(memberName);
								}
								if (gender != null && !gender.equals("null")) {
									memberInfo.setGender(gender);
								}
								if (birthday != null && !birthday.equals("null")) {
									memberInfo.setBirthday(_date(birthday));
								}
								if (idcardType != null && !idcardType.equals("null")) {
									memberInfo.setIdcardType(idcardType);
								}
								if (idcardNumber != null && !idcardNumber.equals("null")) {
									memberInfo.setIdcardNumber(idcardNumber);
								}
								if (foreign != null && !foreign.equals("null")) {
									memberInfo.setForeign(foreign);
								}
								if (education != null && !education.equals("null")) {
									memberInfo.setEducation(education);
								}
								if (degree != null && !degree.equals("null")) {
									memberInfo.setDegree(degree);
								}
								if (title != null && !title.equals("null")) {
									memberInfo.setTitle(title);
								}
								if (agencyName != null && !agencyName.equals("null")) {
									memberInfo.setAgencyName(agencyName);
								}
								if (divisionName != null && !divisionName.equals("null")) {
									memberInfo.setDivisionName(divisionName);
								}
								if (job != null && !job.equals("null")) {
									memberInfo.setJob(job);
								}
								if (email != null && !email.equals("null")) {
									memberInfo.setEmail(email);
								}
								if (mobile != null && !mobile.equals("null")) {
									memberInfo.setMobile(mobile);
								}
								if (phone != null && !phone.equals("null")) {
									memberInfo.setPhone(phone);
								}
								if (postcode != null && !postcode.equals("null")) {
									memberInfo.setPostcode(postcode);
								}
								if (address != null && !address.equals("null")) {
									memberInfo.setAddress(address);
								}
								if (isDirector != null && !isDirector.equals("null")) {
									memberInfo.setIsDirector(Integer.parseInt(isDirector));
								}
								jsonMap.put(memberSn, memberInfo);
							}
							if (jsonMap.size() > 0) {
								//更新member字段
								ProjectApplication projectApplication = (ProjectApplication) dao.query(ProjectApplication.class, smasAppId);
								projectApplication.setMembers(jsonMap.toString());
								dao.addOrModify(projectApplication);
							}
						}
					} else {
						//入库信息不一致
						throw new RuntimeException("入库信息不一致");
					}
				} catch (Exception e) {
					badItemsList.add(smasAppId + "(" + smdbAppId+ ")");
				}
				
				clearDao(false);
			}
		}
	}
	public static void main(String[] args) {
		Map jsonMap = new HashMap<String, Object>();//项目成员信息Map
		MemberInfo memberInfo1 = new MemberInfo();
		memberInfo1.setAddress("洪山区");
		memberInfo1.setJob("工作");
		MemberInfo memberInfo2 = new MemberInfo();
		memberInfo2.setAddress("洪山区2");
		memberInfo2.setJob("工作2");
		jsonMap.put("1", memberInfo1);
		jsonMap.put("2", memberInfo2.toString());
		System.out.println(jsonMap.toString());
	}
	/**
	 * 处理在线通过的数据信息，逐条持久化数据库并记录中间表
	 * @param contentXmlString
	 * @throws Exception 
	 */
	private void persistProjectDisciplineDomXml(String contentDomXml, String smasAppId, String smdbAppId) throws Exception {
		//数据处理
		Document recordsDom = DocumentHelper.parseText(contentDomXml);
		Element rootElement = recordsDom.getRootElement();
		List<Element> elemtList = rootElement.element("records").elements("item");
		//(一次请求对应唯一的结果)
		for (int j = 0; j < elemtList.size(); j++) {
			Element item = elemtList.get(j);
			if (item != null) {
				//执行新数据同步
				try {
					String smdbPojAppId = getArgumentByTag(item, "id");
					if (smdbPojAppId.equals(smdbAppId)) {//和smas中的入库信息一致
						//不存在,入库
						String endDiscipline = getArgumentByTag(item, "discipline");
						
						ProjectApplication projectApplication = (ProjectApplication) dao.query(ProjectApplication.class, smasAppId);
						if (!endDiscipline.equals("null") ) {
							projectApplication.setDiscipline(endDiscipline);
							dao.addOrModify(projectApplication);
						}
						
					} else {
						//入库信息不一致
						throw new RuntimeException("入库信息不一致");
					}
				} catch (Exception e) {
					badItemsList.add(smasAppId + "(" + smdbAppId+ ")");
				}
				
				clearDao(false);
			}
		}
	}
	
	/**
	 * 返回的格式要求：
	 * 对应的smdb的项目申报id + 部级评审信息
	 * @param item
	 * @param ids 本次请求的id信息
	 */
	private void addItemObject(Element item, String[] ids) {
		String smasAppId = ids[0];
		String smdbAppId = ids[1];	
		
		String smdbPojAppId = getArgumentByTag(item, "id");
		if (smdbPojAppId.equals(smdbAppId)) {//和smas中的入库信息一致
			//不存在,入库
			String firstAuditResult = getArgumentByTag(item, "firstAuditResult");
			String firstAuditDate = getArgumentByTag(item, "firstAuditDate");
			
			ProjectApplication projectApplication = (ProjectApplication) dao.query(ProjectApplication.class, smasAppId);
			if (!firstAuditResult.equals("null")) {
				projectApplication.setFirstAuditResult(firstAuditResult);
			}
		
			if (!firstAuditDate.equals("null")) {
				projectApplication.setFirstAuditDate(_date(firstAuditDate));
			}
			dao.addOrModify(projectApplication);
		} else {
			//入库信息不一致
			throw new RuntimeException("入库信息不一致");
		}
	}

	@Override
	public SynchronizationInfo updateCurrentSynchronizationInfo(Integer controlOrder) {
	 
		return null;
	}
	
}
