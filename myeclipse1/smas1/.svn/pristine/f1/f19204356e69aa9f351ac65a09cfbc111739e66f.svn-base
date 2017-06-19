package csdc.tool.execution.importer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Expert;
import csdc.bean.ProjectInfos;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.execution.finder.ExpertFinder;
import csdc.tool.info.ImporterInfo;
import csdc.tool.reader.PoiExcelReader;

/**
 * 经讨论确认后，有5条数据是新增数据，还有1条数据是错误数据。故，补录新增5条专家数据
 * @author pengliang
 */
public class Project2015ExpertSupplementImporter extends Importer{
	
	private PoiExcelReader reader;
	
	@Autowired
	private BeanFieldUtils beanFieldUtils;
	
	@Autowired
	private Tools tool;
	
	@Autowired
	private ExpertFinder expertFinder;

	private List<String> invalidMoeProjectInfoList;
	private List<String> invalidNationalProjectInfolList;
	
	/**
	 * reader重置
	 * @throws Exception
	 */
	private void resetReader() throws Exception {
		reader.readSheet(0);
	}
	
	@Override
	public void work() throws Exception {
		importData();
		if (invalidMoeProjectInfoList != null && invalidMoeProjectInfoList.size() > 0) {
			for (String moeProjectInfo : invalidMoeProjectInfoList) {
				System.out.println("InvalidMoeProjectInfo____" + moeProjectInfo);
			}
		}
		if (invalidNationalProjectInfolList != null && invalidNationalProjectInfolList.size() > 0) {
			for (String nationalProjectInfo : invalidNationalProjectInfolList) {
				System.out.println("InvalidNationalProjectInfo____" + nationalProjectInfo);
			}
		}
	}
	
	private void importData() throws Exception {
		resetReader();
		
		expertFinder.initData();
		int updateCnt = 0;
		
		//高校名称 -> 高校代码
		Map<String, String> univNameCodeMap = (Map<String, String>) ActionContext.getContext().getApplication().get("univNameCodeMap");
		Map<ImporterInfo, String> conditions = new HashMap<ImporterInfo, String>();
		next(reader);
		while (next(reader)) {
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());	
			
			//填充匹配条件
			conditions.put(ImporterInfo.BIRTHDAY, C);
			conditions.put(ImporterInfo.IDCARD_TYPE, D);
			conditions.put(ImporterInfo.IDCARD_NUMBER, E);
			conditions.put(ImporterInfo.MOBILE_PHONE, L);
			conditions.put(ImporterInfo.EMAIL, M);
			conditions.put(ImporterInfo.HOME_PHONE, N);
			conditions.put(ImporterInfo.OFFICE_PHONE, O);
			conditions.put(ImporterInfo.OFFICE_FAX, Q);
			conditions.put(ImporterInfo.EXPERT_ID, AH);
			if(F.contains("/")){
				F = F.substring(F.indexOf("/") + 1);
			}
			// XXX 每次外部专家与内部专家的确定需要和王老师确定(需要修改findExpert里面注释不确定的地方)
			Expert otherExpert = expertFinder.findExpert(univNameCodeMap.get(F), A, conditions, true);
			
			if (null == otherExpert) {
				continue;
			}
			
			otherExpert.setGender(B);
			otherExpert.setBirthday(Tools.getDate(conditions.get(ImporterInfo.BIRTHDAY)));
			otherExpert.setIdCardType(D);
			otherExpert.setIdCardNumber(conditions.get(ImporterInfo.IDCARD_NUMBER).toUpperCase());
			otherExpert.setUsedName(G);
			otherExpert.setDepartment(H);
			otherExpert.setJob(I);
			otherExpert.setSpecialityTitle(J);
			otherExpert.setPositionLevel(K);
			beanFieldUtils.setField(otherExpert, "mobilePhone", L, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
			beanFieldUtils.setField(otherExpert, "email", M, BuiltinMergeStrategies.PREPEND);
			beanFieldUtils.setField(otherExpert, "homePhone", N, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
			beanFieldUtils.setField(otherExpert, "officePhone", O, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
			otherExpert.setOfficePostcode(P);
			beanFieldUtils.setField(otherExpert, "officeFax", Q, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
			otherExpert.setOfficeAddress(R);
			beanFieldUtils.setField(otherExpert, "discipline", tool.transformDisc(S), BuiltinMergeStrategies.APPEND);
			beanFieldUtils.setField(otherExpert, "researchField", T, BuiltinMergeStrategies.PREPEND);
			otherExpert.setLastEducation(U);
			otherExpert.setDegree(V);
			otherExpert.setIsDoctorTutor(W);
			otherExpert.setLanguage(X);
			otherExpert.setComputerLevel(Y);
			otherExpert.setEthnicLanguage(Z);
			otherExpert.setPostDoctor(AA);
			otherExpert.setPartTime(AB);
			otherExpert.setAbroad(AC);
			otherExpert.setAward(AD);
			otherExpert.setIntroduction(AG);
			otherExpert.setImportedDate(new Date());
			
			//TODO修复2015年专家信息中承担教育部项目情况、承担国家社科项目情况的问题数据
			/**
			 * 问题描述：
			 * 修复原则：
			 * 1.取出excel中，承担教育部项目情况的信息moeInfo1，取出数据库中，承担教育部项目情况的信息moeInfo2
			 * 2.判断moeInfo1中是否存在newProjectInfos节点
			 * 3.判断moeInfo2中是否存在数据，
			 * 如果存在：
			 * （1）且moeInfo2有newProjectInfos节点，则合并moeInfo1中的newProjectInfos数据；
			 * （2）如果没有newProjectInfos节点，且moeInfo1中存在newProjectInfos，则添加moeInfo1中的newProjectInfos数据；
			 * （3）如果moeInfo1、moeInfo2中均不存在newProjectInfos节点，则直接写入moeInfo2数据即可。或者不修改该字段
			 * 
			 * 如果不存在：
			 * （1）写入moeInfo1数据即可。
			 */
			
			otherExpert.setMoeProject(fixProjectInfo(AE, otherExpert.getMoeProject()));  //20150114之后的专家数据入库不需要fix，直接set
			otherExpert.setNationalProject(fixProjectInfo(AF, otherExpert.getNationalProject()));		
			updateCnt++;
		}
		System.out.println("更新入库总数量：" + updateCnt);
		
		//打印输出结果
		expertFinder.printResult();
		
		//更新专家的专兼职
		expertFinder.updateType();
	}
	
	/**
	 * 修复承担教育部项目情况
	 * @param excelMoeInfo excel中数据
	 * @param dataMoeInfo 数据库中数据
	 * @return
	 * @throws DocumentException
	 * 备注：仅用于2015年1月13修正入库数据，之后的专家入库不需要此方法
	 */
	public String fixProjectInfo(String excelMoeInfo, String dataMoeInfo) throws DocumentException{
		if (excelMoeInfo == null || excelMoeInfo.length() == 0) {
			return dataMoeInfo;
		}
		if (dataMoeInfo == null || dataMoeInfo.length() == 0) {
			return excelMoeInfo;
		}
		if (!excelMoeInfo.contains("newProjectInfos")) {
			return dataMoeInfo;
		}
		Set<ProjectInfos> newProjectInfos = new HashSet<ProjectInfos>();
		List<String> projectNumAndName = new ArrayList<String> ();
		Document document = DocumentHelper.parseText(excelMoeInfo);//根据excel中数据生成“承担教育部项目情况”xml节点树
		Element rootElement = document.getRootElement();
		Element newInfoElement = rootElement.element("newProjectInfos");
		if (newInfoElement != null) {
			List<Element> newProjects = newInfoElement.elements();
			for (Element newProjectElement : newProjects) {
				String projectNumAndNmae = newProjectElement.elementText("projectNum") + newProjectElement.elementText("projectName");
				if (projectNumAndName.contains(projectNumAndNmae)) {
					continue;
				}
				projectNumAndName.add(projectNumAndNmae);
				ProjectInfos projectInfos = new ProjectInfos();
				projectInfos.setProjectNum(newProjectElement.elementText("projectNum"));
				projectInfos.setProjectName(newProjectElement.elementText("projectName"));
				projectInfos.setProjectType(newProjectElement.elementText("projectType"));
				newProjectInfos.add(projectInfos);
			}
		}else {
			return dataMoeInfo;
		}
		document = DocumentHelper.parseText(dataMoeInfo);//根据数据库中数据生成“承担教育部项目情况”xml节点树
		rootElement = document.getRootElement();
		newInfoElement = rootElement.element("newProjectInfos");
		if (newInfoElement != null) {
			List<Element> newProjects = newInfoElement.elements();
			for (Element newProjectElement : newProjects) {
				String projectNumAndNmae = newProjectElement.elementText("projectNum") + newProjectElement.elementText("projectName");
				if (projectNumAndName.contains(projectNumAndNmae)) {
					System.out.println(excelMoeInfo);
					System.out.println(dataMoeInfo);
					continue;
				}
				projectNumAndName.add(projectNumAndNmae);
				ProjectInfos projectInfos = new ProjectInfos();
				projectInfos.setProjectNum(newProjectElement.elementText("projectNum"));
				projectInfos.setProjectName(newProjectElement.elementText("projectName"));
				projectInfos.setProjectType(newProjectElement.elementText("projectType"));
				newProjectInfos.add(projectInfos);
			}
		}
		Element oldInfoElement = rootElement.element("oldProjectInfos");
		rootElement.clearContent();
		if (oldInfoElement != null) {
			rootElement.add(oldInfoElement);			
		}
		Element newProjectInfoElement = rootElement.addElement("newProjectInfos");
		for (ProjectInfos projectInfos : newProjectInfos) {
			Element projectInfoElement = newProjectInfoElement.addElement("projectInfo");
			Element projectNumElement = projectInfoElement.addElement("projectNum");
			projectNumElement.setText(projectInfos.getProjectNum());
			Element projectNameElement = projectInfoElement.addElement("projectName");
			projectNameElement.setText(projectInfos.getProjectName());
			Element projectTypeElement = projectInfoElement.addElement("projectType");
			projectTypeElement.setText(projectInfos.getProjectType());
		}
		return rootElement.asXML();
	}
	
	public Project2015ExpertSupplementImporter(){
	}
	
	public Project2015ExpertSupplementImporter(String file) {
		reader = new PoiExcelReader(file);
	}
	
	public static void main(String[] args) throws DocumentException {
		Project2015ExpertSupplementImporter expertImporter = new Project2015ExpertSupplementImporter();
		String excelMoeInfo = "<moeProjectData><oldProjectInfos>&lt;moeProjectData&gt;&lt;newProjectInfos&gt;&lt;projectInfo&gt;&lt;projectNum&gt;10JJD720005&lt;/projectNum&gt;&lt;projectName&gt;语境论与当代心理学哲学研究&lt;/projectName&gt;&lt;projectType&gt;教育部人文社会科学重点研究基地重大项目&lt;/projectType&gt;&lt;/projectInfo&gt;&lt;projectInfo&gt;&lt;projectNum&gt;07JA720015&lt;/projectNum&gt;&lt;projectName&gt;当代心智哲学视域中的意向性问题研究&lt;/projectName&gt;&lt;projectType&gt;教育部人文社会科学规划基金项目&lt;/projectType&gt;&lt;/projectInfo&gt;&lt;projectInfo&gt;&lt;projectNum&gt;NCET-11-1035&lt;/projectNum&gt;&lt;projectName&gt;心理学解释问题研究&lt;/projectName&gt;&lt;projectType&gt;教育部新世纪优秀人才支持计划&lt;/projectType&gt;&lt;/projectInfo&gt;&lt;/newProjectInfos&gt;&lt;oldProjectInfos&gt;主持教育部重点研究基地重大项目：语境论与当代心理学哲学研究（项目批准号：10JJD7200105）；主持教育部人文社会科学规划基金项目：当代心智哲学视阈中的意向性问题研究（项目批准号：07JA720015）&lt;/oldProjectInfos&gt;&lt;/moeProjectData&gt;</oldProjectInfos><newProjectInfos><projectInfo><projectNum>07JA720015</projectNum><projectName>当代心智哲学视域中的意向性问题研究</projectName><projectType>青年项目</projectType></projectInfo><projectInfo><projectNum>10JJD720005</projectNum><projectName>语境论与当代心理学哲学研究</projectName><projectType>基地项目</projectType></projectInfo></newProjectInfos></moeProjectData>";
		String dataMoeInfo = "<moeProjectData><newProjectInfos><projectInfo><projectNum>10JJD720005</projectNum><projectName>语境论与当代心理学哲学研究</projectName><projectType>教育部人文社会科学重点研究基地重大项目</projectType></projectInfo><projectInfo><projectNum>07JA720015</projectNum><projectName>当代心智哲学视域中的意向性问题研究</projectName><projectType>教育部人文社会科学规划基金项目</projectType></projectInfo><projectInfo><projectNum>NCET-11-1035</projectNum><projectName>心理学解释问题研究</projectName><projectType>教育部新世纪优秀人才支持计划</projectType></projectInfo></newProjectInfos><oldProjectInfos>主持教育部重点研究基地重大项目：语境论与当代心理学哲学研究（项目批准号：10JJD7200105）；主持教育部人文社会科学规划基金项目：当代心智哲学视阈中的意向性问题研究（项目批准号：07JA720015）</oldProjectInfos></moeProjectData>";
		String finalString = expertImporter.fixProjectInfo(excelMoeInfo, dataMoeInfo);
		System.out.println(finalString);
	}
}
