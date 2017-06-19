package csdc.tool.execution.importer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import csdc.bean.Expert;
import csdc.bean.ProjectApplication;
import csdc.dao.HibernateBaseDao;
import csdc.tool.StringTool;
import csdc.tool.execution.finder.GeneralFinder;
import csdc.tool.execution.finder.InstpFinder;
import csdc.tool.reader.JxlExcelReader;

/**
 * 2014申请数据入库(包括一般项目和基地项目)
 * @author suwb
 *
 */
@Transactional
public class Application2014Importer  extends Importer{
	
	private JxlExcelReader reader;
	
	private int year = 2014;//项目年度
	public Document document;//已经读取了xml的document文件
	private String filename;
	private HibernateBaseDao dao;
	private Tools tool;
	private GeneralFinder generalFinder;
	private Map<String, Expert> expertMap;
	private InstpFinder instpFinder;
	private Map<String, String> titleMap;
	
	//一般项目的构造函数
	public Application2014Importer(Document doc, HibernateBaseDao dao, Tools tool, GeneralFinder generalFinder, Map<String, Expert> expertMap, String file){
		this.document = doc;
		this.dao = dao;
		this.tool = tool;
		this.generalFinder = generalFinder;
		this.expertMap = expertMap;
		this.reader = new JxlExcelReader(file);
	}
	
	//基地项目的构造函数
	public Application2014Importer(String fileName, HibernateBaseDao dao, Tools tool, InstpFinder instpFinder, Map<String, Expert> expertMap, String file){
		this.filename = fileName;
		this.dao = dao;
		this.tool = tool;
		this.instpFinder = instpFinder;
		this.expertMap = expertMap;
		this.reader = new JxlExcelReader(file);
	}

	/**
	 * reader重置
	 * @throws Exception
	 */
	private void resetReader() throws Exception {
		reader.readSheet(0);//读取excel的sheet表,下标从0开始
	}
	
	@Override
	public void work() throws Exception {
		importTitle();
	}
	
	private void importTitle() throws Exception {
		resetReader();
		
		//职称标准
		titleMap = new HashMap<String, String>();
		
		while (next(reader)) {
			titleMap.put(A, E);
		}
	}

	/**
	 * 一般项目解析代码-由xml解析入库
	 * @throws Exception 
	 */
	public Integer generalRun() throws Exception{
		if(titleMap == null){
			importTitle();
		}
			
		//本年度专家库中专家申请项目的专家统计
		HashSet<String> existingExperts = new HashSet<String>();
		
		NodeList records =document.getElementsByTagName("result");
		Element result=(Element) records.item(0);
		XPathFactory xFactory = XPathFactory.newInstance();
	    XPath xpath = xFactory.newXPath();		
		
	    Node projectNode = (Node) result.getElementsByTagName("projectName").item(0);
	    Node applyNode = (Node) result.getElementsByTagName("applyer").item(0);
		String projectName = (projectNode == null ? "" : projectNode.getFirstChild().getNodeValue());
		//负责人人名去除空格时要判断是否为英文名,不能直接把空格全部清掉.待完善！
		String director = (applyNode == null ? "" : StringTool.replaceBlank(applyNode.getFirstChild().getNodeValue()));
		
		//获取项目,如果没有则新建，否则更新
//			General ge = generalFinder.findGeneral(projectName, director, year, true);
		ProjectApplication ge = new ProjectApplication();
		ge.setType("general");
		
		XPathExpression exprCheckstatus = xpath.compile("//result[1]/checkStatus/text()");
		NodeList nodeCheckstatus = (NodeList) exprCheckstatus.evaluate(document, XPathConstants.NODESET);
		if(nodeCheckstatus.item(0) != null){
			String auditStatue = nodeCheckstatus.item(0).getNodeValue();
			if(auditStatue.equals("2") || auditStatue.equals("4")){
				ge.setAuditStatus(_auditStatue(auditStatue));					
				ge.setProjectName(projectName);
				ge.setDirector(director);						
				Node universityNode = (Node) result.getElementsByTagName("unitCode").item(0);
				String universityCode = (universityNode == null ? "" : universityNode.getFirstChild().getNodeValue());
				ge.setUniversityCode(universityCode);
				Node unitNode = (Node) result.getElementsByTagName("unitName").item(0);
				String universityName = (unitNode == null ? "" : unitNode.getFirstChild().getNodeValue());
				ge.setUniversityName(universityName);
				if(null != result.getElementsByTagName("projectType").item(0)){
					ge.setProjectType(_projectType(result.getElementsByTagName("projectType").item(0).getFirstChild().getNodeValue()));
				}
				ge.setYear(year);
				if(null != result.getElementsByTagName("applyDate").item(0)){
					ge.setApplyDate(_date(result.getElementsByTagName("applyDate").item(0).getFirstChild().getNodeValue()));
				}
				if(null != result.getElementsByTagName("planFinishDate").item(0)){
					ge.setPlanEndDate(_date(result.getElementsByTagName("planFinishDate").item(0).getFirstChild().getNodeValue()));
				}
				String discipline = "";
				String subject = "";
				if(null != result.getElementsByTagName("researchDirection").item(0)){
					discipline = tool.transformDisc(result.getElementsByTagName("researchDirection").item(0).getFirstChild().getNodeValue());
				}
				if(null != result.getElementsByTagName("subject").item(0)){
					subject = result.getElementsByTagName("subject").item(0).getFirstChild().getNodeValue();
					ge.setDisciplineType(_disciplineType(subject));
				}
				if(subject.equals("75047-99") || subject.equals("75011-44") || subject.equals("zjxxk")){
					ge.setDiscipline(discipline);
				}else {
					if(discipline.contains(_discipline(subject))){
						ge.setDiscipline(discipline);
					}else {
						if(discipline.compareTo(_discipline(subject)) > 0){
							ge.setDiscipline(_discipline(subject) + "; " + discipline);
						}else ge.setDiscipline(discipline + "; " + _discipline(subject));
					}
				}
				if(null != result.getElementsByTagName("researchType").item(0)){
					ge.setResearchType(result.getElementsByTagName("researchType").item(0).getFirstChild().getNodeValue());
				}
				if(null != result.getElementsByTagName("lastProductMode").item(0)){
					ge.setFinalResultType(result.getElementsByTagName("lastProductMode").item(0).getFirstChild().getNodeValue());
				}
				if(null != result.getElementsByTagName("applyTotalFee").item(0)){
					ge.setApplyFee(result.getElementsByTagName("applyTotalFee").item(0).getFirstChild().getNodeValue());
				}
				if(null != result.getElementsByTagName("otherFeeSource").item(0)){
					ge.setOtherFee(result.getElementsByTagName("otherFeeSource").item(0).getFirstChild().getNodeValue());
				}
				if(null != result.getElementsByTagName("applyDocName").item(0)){
					ge.setFile(result.getElementsByTagName("applyDocName").item(0).getFirstChild().getNodeValue());
				}
				if(null != result.getElementsByTagName("gender").item(0)){
					ge.setGender(result.getElementsByTagName("gender").item(0).getFirstChild().getNodeValue());
				}
				if(null != result.getElementsByTagName("birthday").item(0)){
					ge.setBirthday(_date(result.getElementsByTagName("birthday").item(0).getFirstChild().getNodeValue()));
				}
				if(null != result.getElementsByTagName("IDCardNo").item(0)){
					ge.setIdcard(result.getElementsByTagName("IDCardNo").item(0).getFirstChild().getNodeValue());
				}
				if(null != result.getElementsByTagName("title").item(0)){
					ge.setTitle(titleMap.get(result.getElementsByTagName("title").item(0).getFirstChild().getNodeValue()));
				}
				if(null != result.getElementsByTagName("division").item(0)){
					String division = result.getElementsByTagName("division").item(0).getFirstChild().getNodeValue().replace(universityName, "");
					ge.setDepartment(StringTool.replaceBlank(division));
				}
				if(null != result.getElementsByTagName("duty").item(0)){
					ge.setJob(result.getElementsByTagName("duty").item(0).getFirstChild().getNodeValue());
				}
				if(null != result.getElementsByTagName("eduLevel").item(0)){
					ge.setEducation(_eduLevel(result.getElementsByTagName("eduLevel").item(0).getFirstChild().getNodeValue()));
				}
				if(null != result.getElementsByTagName("eduDegree").item(0)){
					ge.setDegree(_eduDegree(result.getElementsByTagName("eduDegree").item(0).getFirstChild().getNodeValue()));
				}
				if(null != result.getElementsByTagName("language").item(0)){
					ge.setForeign(_language(result.getElementsByTagName("language").item(0).getFirstChild().getNodeValue()));
				}
				if(null != result.getElementsByTagName("email").item(0)){
					ge.setEmail(result.getElementsByTagName("email").item(0).getFirstChild().getNodeValue());
				}
				if(null != result.getElementsByTagName("address").item(0)){
					ge.setAddress(result.getElementsByTagName("address").item(0).getFirstChild().getNodeValue());
				}
				if(null != result.getElementsByTagName("postalCode").item(0)){
					ge.setPostcode(result.getElementsByTagName("postalCode").item(0).getFirstChild().getNodeValue());
				}
				if(null != result.getElementsByTagName("telOffice").item(0)){
					ge.setPhone(result.getElementsByTagName("telOffice").item(0).getFirstChild().getNodeValue());
				}
				if(null != result.getElementsByTagName("tel").item(0)){
					ge.setMobile(result.getElementsByTagName("tel").item(0).getFirstChild().getNodeValue());
				}
				NodeList members = result.getElementsByTagName("ProjectApplyMember");
				if(null !=members.item(0)){
					StringBuffer memberList = new StringBuffer();
					for(int k = 0;k<members.getLength();k++){
						Element member = (Element) members.item(k);
						if(null != member.getElementsByTagName("memberName").item(0)){
							memberList.append(StringTool.replaceBlank(member.getElementsByTagName("memberName").item(0).getFirstChild().getNodeValue()));
						}
						if(null != member.getElementsByTagName("memberUnit").item(0)){
							memberList.append("(" + StringTool.replaceBlank(member.getElementsByTagName("memberUnit").item(0).getFirstChild().getNodeValue()) + "),");
						}else {
							if(null != member.getElementsByTagName("memberName").item(0)){
								memberList.append("(),");//存在只有成员名没有成员所在学校的数据									
							}
						}
					}
					ge.setMembers(memberList.toString().substring(0, memberList.length()-1));
				}
				ge.setIsReviewable(1);
				
				//为申请人添加2014申请年份
				Expert applicant = expertMap.get(universityCode + director.replaceAll("\\s+", ""));
				if (applicant != null) {
					existingExperts.add(applicant.getName()+applicant.getId());
					//合并过往申请年份
					List<String> ay = new ArrayList<String>(new TreeSet<String>(Arrays.asList((applicant.getGeneralApplyYears() + " " + year).split("\\D+"))));
					String newAy = "";
					for (String string : ay) {
						if (string.length() > 0) {
							newAy += (newAy.isEmpty() ? "" : "; ") + string;
						}
					}
					applicant.setGeneralApplyYears(newAy);
					dao.modify(applicant);
				}
				dao.modify(ge);
			}
		}			
		return existingExperts.size();
	}
	
	/**
	 * 基地项目解析入库-由xml解析入库
	 * @return
	 * @throws Exception 
	 */
	public Integer instpRun() throws Exception{
		if(titleMap == null){
			importTitle();
		}
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc=builder.parse(filename);//读取xml文件
		//本年度专家库中专家申请项目的专家统计
		HashSet<String> existingExperts = new HashSet<String>();
		Map<String, String> univMap = new HashMap<String, String>();
		List<String> universityList = dao.query("select u.name from University u");
		for (String university : universityList) {
			univMap.put(university, university);
		}
		
		NodeList records =doc.getElementsByTagName("result");
		for(int i=0; i<records.getLength(); i++){			
			Element result=(Element) records.item(i);
			XPathFactory xFactory = XPathFactory.newInstance();
		    XPath xpath = xFactory.newXPath();		

		    Node projectNode = (Node) result.getElementsByTagName("projectName").item(0);
		    Node applyNode = (Node) result.getElementsByTagName("applyer").item(0);
			String projectName = (projectNode == null ? "" : projectNode.getFirstChild().getNodeValue());
			String director = (applyNode == null ? "" : StringTool.replaceBlank(applyNode.getFirstChild().getNodeValue()));
			
			//获取项目,如果没有则新建，否则更新
//			Instp ins = instpFinder.findInstp(projectName, director, year, true);
			ProjectApplication ins = new ProjectApplication();
			ins.setType("instp");
			
			XPathExpression exprCheckstatus = xpath.compile("//result[" +(i+1)+ "]/checkStatus/text()");
			NodeList nodeCheckstatus = (NodeList) exprCheckstatus.evaluate(doc, XPathConstants.NODESET);
			if(nodeCheckstatus.item(0) != null){
				String auditStatue = nodeCheckstatus.item(0).getNodeValue();
				if(auditStatue.equals("2") || auditStatue.equals("4")){
					ins.setAuditStatus(_auditStatue(auditStatue));
					ins.setProjectName(projectName);
					ins.setDirector(director);
					if(null != result.getElementsByTagName("projectJdName").item(0)){
						ins.setInstituteName(result.getElementsByTagName("projectJdName").item(0).getFirstChild().getNodeValue());
					}
					Node universityNode = (Node) result.getElementsByTagName("unitCode").item(0);
					String universityCode = (universityNode == null ? "" : universityNode.getFirstChild().getNodeValue());
					ins.setUniversityCode(universityCode);
					if(null != result.getElementsByTagName("unitName").item(0)){
						ins.setUniversityName(result.getElementsByTagName("unitName").item(0).getFirstChild().getNodeValue());
					}
					if(null != result.getElementsByTagName("projectType").item(0)){
						ins.setProjectType("基地项目");
					}
					ins.setYear(year);
					String directorUniversity = null;
					String directorDivision = null;
					if(null != result.getElementsByTagName("division").item(0)){
						String division = result.getElementsByTagName("division").item(0).getFirstChild().getNodeValue();
						for (int len = division.length(); len >= 1; len--) {
							for (int  k= 0; k + len <= division.length(); k++) {
								String subName = division.substring(k, k + len);
								directorUniversity = univMap.get(subName);
								if (directorUniversity != null) {
									ins.setDirectorUniversity(directorUniversity);
								}
							}
						}
						directorUniversity = ins.getDirectorUniversity();
						if(directorUniversity == null){//没有匹配上就用项目所在高校作为负责人所属高校-此时负责人所在部门直接用原始数据
							ins.setDirectorUniversity(result.getElementsByTagName("unitName").item(0).getFirstChild().getNodeValue());
							ins.setDirectorDivisionName(division);
						}else {
							directorDivision = division.replace(directorUniversity, "");
							ins.setDirectorDivisionName(StringTool.replaceBlank(directorDivision));							
						}
					}	
					if(null != result.getElementsByTagName("applyDate").item(0)){
						ins.setApplyDate(_date(result.getElementsByTagName("applyDate").item(0).getFirstChild().getNodeValue()));
					}
					if(null != result.getElementsByTagName("planFinishDate").item(0)){
						ins.setPlanEndDate(_date(result.getElementsByTagName("planFinishDate").item(0).getFirstChild().getNodeValue()));
					}
					//学科代码合并并且按大小排序
					String subject1 = "";
					String subject2 = "";
					String subject3 = "";
					if(null != result.getElementsByTagName("subject1_2").item(0)){
						subject3 = _discipline(result.getElementsByTagName("subject1_2").item(0).getFirstChild().getNodeValue());
					}
					if(null != result.getElementsByTagName("subject1_1").item(0)) {
						subject2 = _discipline(result.getElementsByTagName("subject1_1").item(0).getFirstChild().getNodeValue());
					}
					if(null != result.getElementsByTagName("subject").item(0)){
						subject1 = _discipline(result.getElementsByTagName("subject").item(0).getFirstChild().getNodeValue());
					}
					if((subject1.equals("75047-99") || subject1.equals("75011-44")) && subject2.length()>0){
						ins.setDiscipline(subject3.length()>0 ? subject3 : subject2);
					}else{
						if(subject3.length() > 0){
							if(subject3.contains(subject2)){
								if(subject2.contains(subject1)){
									ins.setDiscipline(subject3);
								}
								else {
									if(subject1.compareTo(subject3) > 0){
										ins.setDiscipline(subject3 + "; " + subject1);
									}else ins.setDiscipline(subject1 + "; " + subject3);
								}
							}else {
								if(subject2.contains(subject1)){
									if(subject2.compareTo(subject3) > 0){
										ins.setDiscipline(subject3 + "; " + subject2);
									}else ins.setDiscipline(subject2 + "; " + subject3);
								}
							}
						}else if(subject3.length() == 0 && subject2.length() > 0){
							if(subject2.contains(subject1)){
								ins.setDiscipline(subject2);														
							}else {
								if(subject2.compareTo(subject1) > 0){
									ins.setDiscipline(subject1 + "; " + subject2);
								}
								else ins.setDiscipline(subject2 + "; " + subject1);
							}
						}else ins.setDiscipline(subject1);						
					}
					if(null != result.getElementsByTagName("subject").item(0)){
						String discipline = result.getElementsByTagName("subject").item(0).getFirstChild().getNodeValue();
						ins.setDisciplineType(_disciplineType(discipline));
					}
					if(null != result.getElementsByTagName("researchDirection").item(0)){
						ins.setDisciplineDirection(result.getElementsByTagName("researchDirection").item(0).getFirstChild().getNodeValue());
					}
					if(null != result.getElementsByTagName("lastProductMode").item(0)){
						ins.setFinalResultType(result.getElementsByTagName("lastProductMode").item(0).getFirstChild().getNodeValue());
					}
					if(null != result.getElementsByTagName("applyDocName").item(0)){
						ins.setFile(result.getElementsByTagName("applyDocName").item(0).getFirstChild().getNodeValue());
					}
					if(null != result.getElementsByTagName("birthday").item(0)){
						ins.setBirthday(_date(result.getElementsByTagName("birthday").item(0).getFirstChild().getNodeValue()));
					}
					if(null != result.getElementsByTagName("title").item(0)){
						ins.setTitle(titleMap.get(result.getElementsByTagName("title").item(0).getFirstChild().getNodeValue()));
					}
					if(null != result.getElementsByTagName("duty").item(0)){
						ins.setJob(result.getElementsByTagName("duty").item(0).getFirstChild().getNodeValue());
					}
					NodeList members = result.getElementsByTagName("ProjectApplyMember");
					if(null != members.item(0)){
						StringBuffer memberList = new StringBuffer();
						for(int k = 0;k<members.getLength();k++){
							Element member = (Element) members.item(k);
							if(null != member.getElementsByTagName("memberName").item(0)){
								memberList.append(StringTool.replaceBlank(member.getElementsByTagName("memberName").item(0).getFirstChild().getNodeValue()));
							}
							if(null != member.getElementsByTagName("memberUnit").item(0)){
								memberList.append("(" + StringTool.replaceBlank(member.getElementsByTagName("memberUnit").item(0).getFirstChild().getNodeValue()) + "),");
							}else {
								if(null != member.getElementsByTagName("memberName").item(0)){
									memberList.append("(),");//存在只有成员名没有成员所在学校的数据									
								}
							}
						}
						ins.setMembers(memberList.toString().substring(0, memberList.length()-1));
					}
					ins.setIsReviewable(1);
					
					//为申请人添加当前年度申请年份
					String[] keys = instpFinder.getKeys(ins.getDirectorUniversity(), ins.getDirector(), true);
					for (String key : keys) {
						Expert applicant = expertMap.get(key);
						
						if (applicant != null) {
							existingExperts.add(applicant.getName()+applicant.getId());
							//合并过往申请年份
							List<String> ay = new ArrayList<String>(new TreeSet<String>(Arrays.asList((applicant.getInstpApplyYears() + " " + year).split("\\D+"))));
							String newAy = "";
							for (String string : ay) {
								if (string.length() > 0) {
									newAy += (newAy.isEmpty() ? "" : "; ") + string;
								}
							}
							applicant.setInstpApplyYears(newAy);
							dao.modify(applicant);
						}
					}					
					dao.modify(ins);
					System.out.println("第" + (i+1) + "条记录结束");					
				}
			}			
		}
		System.out.println("解析入库结束！！");
		return existingExperts.size();
	}
	
	/**
	 * 根据乱七八糟的日期字符串分析出正确日期
	 * @param rawDate
	 * @return
	 */
	public Date _date(String rawDate){
		if (rawDate == null) {
			return null;
		}
		rawDate = StringTool.toDBC(rawDate).trim();
		if (rawDate.isEmpty()) {
			return null;
		}
		if (rawDate.matches("(19|20)\\d{4}") || rawDate.matches("20\\d{4}")) {
			Integer year = Integer.parseInt(rawDate) / 100;
			Integer month = Integer.parseInt(rawDate) % 100;
			if (month >= 1 && month <= 12) {
				return new Date(year - 1900, month - 1, 1);
			}
		}
		if (rawDate.matches("-?\\d{5,6}")) {
			//Excel中的日期在常规格式下的值(1900-1-1过去的天数 + 2)
			return new Date(Long.parseLong(rawDate) * 86400000L - 2209161600000L);
		} else if (rawDate.matches("-?\\d{7,}")) {
			//1970-1-1 00:00:00过去的毫秒数
			return new Date(Long.parseLong(rawDate));
		}
		String tmp[] = rawDate.replaceAll("\\D+", " ").trim().split("\\s+");
		if (tmp.length == 0 || tmp[0].isEmpty()) {
			return null;
		}
		Integer mid;
		Integer year = Integer.parseInt(tmp[0]);
		Integer month = tmp.length > 1 ? Integer.parseInt(tmp[1]) : 1;
		Integer date = tmp.length > 2 ? Integer.parseInt(tmp[2]) : 1;
		if (month > 31) {
			mid = month; month = year; year = mid;
		} else if (date > 31) {
			mid = date; date = year; year = mid;
		}
		if (month > 12) {
			mid = date;	date = month; month = mid;
		}
		if (year < 10) {
			year += 2000;
		} else if (year < 100) {
			year += 1900;
		}
		return new Date(year - 1900, month - 1, date);
	}
	
	/**
	 * 学科门类转换
	 */
	public String _disciplineType(String subject){
		if(subject.contains("170")){
			return "地球科学";
		}else if(subject.startsWith("190")){
			return "心理学";
		}else if(subject.startsWith("630")){
			return "管理学";
		}else if(subject.startsWith("710")){
			return "马克思主义";
		}else if(subject.startsWith("720")){
			if(subject.startsWith("72040")){
				return "逻辑学";
			}else return "哲学";
		}else if(subject.startsWith("730")){
			return "宗教学";
		}else if(subject.startsWith("740")){
			return "语言学";
		}else if (subject.startsWith("75011-44")){
			return "中国文学";
		}else if(subject.startsWith("75047-99")){
			return "外国文学";	
		}else if(subject.startsWith("760")){
			return "艺术学";
		}else if(subject.startsWith("770")){
			return "历史学";
		}else if(subject.startsWith("780")){
			return "考古学";
		}else if(subject.startsWith("790")){
			return "经济学";
		}else if(subject.startsWith("810")){
			return "政治学";
		}else if(subject.startsWith("820")){
			return "法学";
		}else if(subject.startsWith("840")){
			return "社会学";
		}else if(subject.startsWith("850")){
			return "民族学与文化学";
		}else if(subject.startsWith("860")){
			return "新闻学与传播学";
		}else if(subject.startsWith("870")){
			return "图书馆、情报与文献学";
		}else if(subject.startsWith("880")){
			return "教育学";
		}else if(subject.startsWith("890")){
			return "体育学";
		}else if(subject.startsWith("910")){
			return "统计学";
		}else if(subject.startsWith("GAT")){
			return "港澳台问题研究";
		}else if(subject.startsWith("GJW")){
			return "国际问题研究";
		}else if(subject.startsWith("zjxxk")){
			return "综合研究/交叉学科";
		}else return "未知";
	}
	
	/**
	 * 学科转换(还有890和170分别代表体育科学和地球科学：这两个我们这边没有的学科代码)
	 * @param subject
	 * @return
	 */
	public String _discipline(String subject){
		if(subject.startsWith("GAT")){
			return "980";
		}else if(subject.startsWith("GJW")){
			return "990";
		}else if(subject.startsWith("SXZZJY")){
			return "970";
		}else return subject;
	}
	/**
	 * 一般项目子类转换
	 * @return
	 */
	public String _projectType(String projectType){
		projectType = projectType.trim();
		if(projectType.equals("402881f13f7e4869013f7e4aaa48000c")){
			return "规划基金项目";
		}else if(projectType.equals("402881f13f7e4869013f7e4baf3d000e")){
			return "青年基金项目";
		}else if(projectType.equals("402881f13f7e4869013f7e4b471d000d")){
			return "自筹经费项目";
		}else if(projectType.equals("402881f13f7f38d6013f7f3a67a30008")){
			return "部属基地项目";
		}else if(projectType.equals("402881f13f7f38d6013f7f3aa8360009")){
			return "部省共建基地项目";
		}
		else return "未知";
	}
	
	/**
	 * 学历转换
	 * @return
	 */
	public String _eduLevel(String edulevel){
		edulevel = edulevel.trim();
		if(edulevel.equals("0")){
			return "博士研究生";
		}else if(edulevel.equals("1")){
			return "硕士研究生";
		}else if(edulevel.equals("2")){
			return "本科生";
		}else if(edulevel.equals("3")){
			return "大专生";
		}else if(edulevel.equals("4")){
			return "中专生";
		}else if(edulevel.equals("5")){
			return "其他";
		}else return "未知";
	}
	
	/**
	 * 学位转换
	 * @return
	 */
	public String _eduDegree(String eduDegree){
		eduDegree = eduDegree.trim();
		if(eduDegree.equals("1")){
			return "名誉博士";
		}else if(eduDegree.equals("2")){
			return "博士";
		}else if(eduDegree.equals("3")){
			return "硕士";
		}else if(eduDegree.equals("4")){
			return "学士";
		}else if(eduDegree.equals("999")){
			return "其他";
		}else return "未知";
	}
	
	/**
	 * 审核状态转换
	 * @return
	 */
	public String _auditStatue(String auditStatue){
		auditStatue = auditStatue.trim();
		if(auditStatue.equals("0")){
			return "未提交";
		}else if (auditStatue.equals("1")){
			return "退回修改";
		}else if (auditStatue.equals("2")){
			return "学校审核通过";
		}else if (auditStatue.equals("3")){
			return "学校审核不通过";
		}else if (auditStatue.equals("4")){
			return "主管部门审核通过";
		}else if (auditStatue.equals("5")){
			return "主管部门审核不通过";
		}else if (auditStatue.equals("6")){
			return "社科司审核通过";
		}else if (auditStatue.equals("7")){
			return "社科司审核不通过";
		}else if (auditStatue.equals("8")){
			return "已修改";
		}else if (auditStatue.equals("9")){
			return "已提交";
		}else return "未知";
	}
	
	/**
	 * 语言格式转换
	 * @param language
	 * @return
	 */
	public String _language(String language){
		language = language.trim();
		if(language.length() > 4){
			language = language.replace(" ", "、");
			language = language.replace(",", "、");
			language = language.replace("/", "、");
			language = language.replace("\\\\", "、");
			language = language.replace("，", "、");
			language = language.replace("；", "、");
			return language;
		}else{
			return StringTool.replaceBlank(language);			
		} 
	}
	
}
