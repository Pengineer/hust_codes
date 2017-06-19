package csdc.tool.execution.importer;

import java.io.Reader;

import mondrian.olap.fun.vba.Excel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import oracle.net.aso.e;
import oracle.net.aso.p;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import csdc.tool.FileTool;
import csdc.tool.WordTool;

import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.execution.Execution;
import csdc.tool.reader.JdbcTemplateReader;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.AwardGranted;
import csdc.bean.AwardApplication;
import csdc.bean.Department;
import csdc.bean.Electronic;
import csdc.bean.Institute;
import csdc.bean.OtherProduct;
import csdc.bean.Person;
import csdc.bean.Product;
import csdc.bean.Paper;
import csdc.bean.Book;
import csdc.bean.Consultation;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.tool.execution.finder.DepartmentFinder;
import csdc.tool.execution.finder.InstituteFinder;
import csdc.tool.execution.finder.PersonFinder;
import csdc.tool.execution.finder.TeacherFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.dao.SystemOptionDao;

import csdc.tool.execution.Execution;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;


/**
 * 第六届成果奖申请入库
 * 《第六届成果奖申请名单（专家建议名单初选）.xlsx》
 * @author maowh
 *
 */
public class AwardApplicationSixthImporter extends Importer{
	
	private ExcelReader excelReader;
	
	@Autowired
	private Tool tool;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private DepartmentFinder departmentFinder;
	
	@Autowired
	private InstituteFinder instituteFinder;
	
	@Autowired
	private TeacherFinder teacherFinder;
	
	@Autowired
	private UnivPersonFinder univPersonFinder;
	
	@Autowired
	private SystemOptionDao systemOptionDao;
	
	@Autowired
	private BeanFieldUtils beanFieldUtils;
	
	
				
	@Override
	public void work() throws Exception{
		//validate();
		importData();		
	}

	/*
	 * 导入数据
	 */
	private void importData() throws Exception {
		
		 excelReader.readSheet(0);
		
		//成果类型
		SystemOption 论文 = systemOptionDao.query("productType", "01");
		SystemOption 著作 = systemOptionDao.query("productType", "02");
		SystemOption 研究咨询报告 = systemOptionDao.query("productType", "03");
		SystemOption 电子出版物 = systemOptionDao.query("productType", "04");
		SystemOption 其他 = systemOptionDao.query("productType", "06");
		
		//奖励类型
		SystemOption moeSocial = systemOptionDao.query("awardType", "01");
		SystemOption 成果普及奖 = systemOptionDao.query("awardType", "011");
		SystemOption 论文奖 = systemOptionDao.query("awardType", "012");
		SystemOption 研究报告奖 = systemOptionDao.query("awardType", "013");
		SystemOption 著作奖 = systemOptionDao.query("awardType", "014");
		

		Map<String, Academic> acMap = new HashMap<String, Academic>();		
		
		while (next(excelReader)) {				
			
			if (A != null) {
				System.out.println(excelReader.getCurrentRowIndex() + "/" + excelReader.getRowNumber());
				
				Agency university = null;
				Department department = null;
				Institute institute = null;
				Academic academic = null;
				Teacher teacher = null;
				Person person = null;
				
				university = universityFinder.getUnivByName(S);
				if (W.contains("院系")){
					department = departmentFinder.getDepartment(university, V, true);//需要校验?
				} else if (W.contains("研究基地")) {
					institute = instituteFinder.getInstitute(university, V, true);//需要校验?
				}				
				
				teacher = univPersonFinder.findTeacher(H, university, department, institute);//校验是否都可以找得到?
				
				beanFieldUtils.setField(teacher.getPerson(), "officePhone", K, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
				beanFieldUtils.setField(teacher.getPerson(), "homePhone", L, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
				beanFieldUtils.setField(teacher.getPerson(), "mobilePhone", M, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
				beanFieldUtils.setField(teacher.getPerson(), "email", N, BuiltinMergeStrategies.APPEND);
				beanFieldUtils.setField(teacher.getPerson(), "officeAddress", Q, BuiltinMergeStrategies.APPEND);
				beanFieldUtils.setField(teacher.getPerson(), "officePostcode", R, BuiltinMergeStrategies.APPEND);
				beanFieldUtils.setField(teacher.getPerson(), "gender", T, BuiltinMergeStrategies.REPLACE);
				beanFieldUtils.setField(teacher.getPerson(), "birthday", tool.getDate(U), BuiltinMergeStrategies.PRECISE_DATE);
				beanFieldUtils.setField(teacher, "position", AA, BuiltinMergeStrategies.REPLACE);
				if ("身份证".equals(O)) {
					beanFieldUtils.setField(teacher.getPerson(), "idcardNumber", P, BuiltinMergeStrategies.REPLACE);
					teacher.getPerson().setIdcardType("身份证");
				} else {
					if (teacher.getPerson().getIdcardType() == null) {//若无证件信息，则将本次证件信息填入
						teacher.getPerson().setIdcardType(O);
						teacher.getPerson().setIdcardNumber(P);						
					}
				}				
												
//				academic = teacher.getPerson().getAcademic();
				academic = acMap.get(teacher.getPerson().getId());
				if (academic == null) {
					academic = teacher.getPerson().getAcademic();
					if (academic == null){
						academic = new Academic();
						academic.setPerson(teacher.getPerson());
						dao.add(academic);							
					}
					acMap.put(teacher.getPerson().getId(), academic);	
				}
				
				beanFieldUtils.setField(academic, "specialityTitle", J, BuiltinMergeStrategies.REPLACE);
				beanFieldUtils.setField(academic, "language", X, BuiltinMergeStrategies.APPEND);
				beanFieldUtils.setField(academic, "lastDegree", Y, BuiltinMergeStrategies.REPLACE);
				beanFieldUtils.setField(academic, "discipline", AN, BuiltinMergeStrategies.APPEND);
				academic.setTutorType(Z.contains("1") ? "博士生导师" : null);
				if (AC != null && !AC.isEmpty()) {
					academic.setPostdoctor(Integer.valueOf(AC));										
				}
				
//				beanFieldUtils.setField(academic, "postdoctor", AC, BuiltinMergeStrategies.REPLACE);
				
				AwardApplication awardApplication = new AwardApplication();
//				AwardGranted award = new AwardGranted();
				String productName = null;
				String[] productNames = A.split("[《》]");
				if (productNames.length == 2 && productNames[0].isEmpty()) {
					productName = A.replaceAll("[《》]", "").replaceAll("<", "《").replaceAll(">", "》");
				} else {
					productName = A;
				}
				awardApplication.setProductName(productName);
				awardApplication.setCreateMode(2);
				awardApplication.setApplicant(teacher.getPerson());
				awardApplication.setAgencyName(S);
				awardApplication.setDivisionName(V);
				awardApplication.setUniversity(university);
				awardApplication.setDepartment(department);
				awardApplication.setInstitute(institute);
				awardApplication.setApplicantName(H);
				awardApplication.setStatus(8);
				awardApplication.setApplicantSubmitStatus(3);
				awardApplication.setType(moeSocial);
				awardApplication.setCreateDate(new Date());
				if ("论文奖".equals(C)) {
					awardApplication.setSubType(论文奖);					
				} else if ("研究报告奖".equals(C)) {
					awardApplication.setSubType(研究报告奖);
				} else if ("成果普及奖".equals(C) || "普及奖".equals(C)) {
					awardApplication.setSubType(成果普及奖);
				} else if ("著作奖".equals(C)) {
					awardApplication.setSubType(著作奖);
				}
				awardApplication.setSession(6);
				awardApplication.setFile(AM);
				awardApplication.setNote(AI);
				awardApplication.setGroup(AK);//分组情况
				awardApplication.setShelfNumber(AG);//二次上架号
				awardApplication.setFinalAuditStatus(3);
				
			    if (AJ != null && AJ.contains("合格")) {
					awardApplication.setFinalAuditResult(2);
				} else if (AJ != null && AJ.contains("淘汰")) {
					awardApplication.setFinalAuditResult(1);
				}
								
				if ("电子出版物".equals(B)) {
					
					Electronic electronic = new Electronic();
					electronic.setChineseName(productName);
					electronic.setDisciplineType(D);//学科门类
					electronic.setDiscipline(E);//学科代码
					electronic.setPublishUnit(F);//出版单位
					electronic.setSubmitStatus(3);
					electronic.setUniversity(university);
					electronic.setAgencyName(S);
					if (W.contains("院系")){
						electronic.setDepartment(department);						
					} else if (W.contains("研究基地")) {
						electronic.setInstitute(institute);
					}
					electronic.setDivisionName(V);					
					electronic.setPublishDate(tool.getDate(G));
					if (Integer.valueOf(AB) == 1) {//以团队名义申请，在product表中填入团队相应的信息
						electronic.setOrgPerson(teacher.getPerson());
						electronic.setOrgName(H);
						electronic.setOrgMember(I);
						electronic.setOrgEmail(N);
						electronic.setOrgMobilePhone(M);
						electronic.setOrgOfficeAddress(Q);
						electronic.setOrgOfficePhone(K);
						electronic.setOrgOfficePostcode(R);
						awardApplication.setApplicationType(2);
					} else {
						electronic.setAuthor(teacher.getPerson());
						electronic.setAuthorName(H);
						electronic.setOtherAuthorName(I);
						awardApplication.setApplicationType(1);
					}					
					
					dao.add(electronic);
					
				    awardApplication.setAgencyName(S);
				    awardApplication.setApplicant(teacher.getPerson());
				    awardApplication.setApplicantSubmitStatus(3);
					awardApplication.setProduct(electronic);
					dao.add(awardApplication);
//					award.setApplication(awardApplication);
//					award.setSession(6);
//					award.setYear(2013);
//					dao.add(award);
	
				} else if ("著作".equals(B)) {

					Book book = new Book();
					book.setChineseName(productName);//成果名称
					book.setDisciplineType(D);//成果学科门类
					book.setDiscipline(E);//成果学科代码
					//book.setWordNumber(Double.valueOf(AD));//成果字数(表中无数据)
					book.setPublishUnit(F);//出版、发表或使用单位
					book.setSubmitStatus(3);
					book.setUniversity(university);
					book.setAgencyName(S);
					if (W.contains("院系")){
						book.setDepartment(department);						
					} else if (W.contains("研究基地")) {
						book.setInstitute(institute);
					}
					book.setDivisionName(V);
					book.setPublishDate(tool.getDate(G));//出版、发表或使用时间
					book.setLanguage(AH);
					if (Integer.valueOf(AB) == 1) {//以团队名义申请，在product表中填入团队相应的信息
						book.setOrgPerson(teacher.getPerson());
						book.setOrgName(H);
						book.setOrgMember(I);
						book.setOrgEmail(N);
						book.setOrgMobilePhone(M);
						book.setOrgOfficeAddress(Q);
						book.setOrgOfficePhone(K);
						book.setOrgOfficePostcode(R);
						awardApplication.setApplicationType(2);
					} else {
						book.setAuthor(teacher.getPerson());
						book.setAuthorName(H);
						book.setOtherAuthorName(I);
						awardApplication.setApplicationType(1);
					}
						dao.add(book);
						awardApplication.setProduct(book);	
						dao.add(awardApplication);
						
//						award.setApplication(awardApplication);
//						award.setSession(6);
//						award.setYear(2013);
//						dao.add(award);
						
				} else if (B.contains("论文")) {
					Paper paper = new Paper();
					if (AO != null && !AO.isEmpty()) {
						paper.setNumber(AO);//期号
					}
					paper.setChineseName(productName);
					paper.setDisciplineType(D);
					paper.setDiscipline(E);
					paper.setPublication(F);
					paper.setPublicationDate(tool.getDate(G));
					paper.setLanguage(AH);
					paper.setSubmitStatus(3);
					paper.setUniversity(university);
					paper.setAgencyName(S);
					if (W.contains("院系")){
						paper.setDepartment(department);						
					} else if (W.contains("研究基地")) {
						paper.setInstitute(institute);
					}
					paper.setDivisionName(V);
					
					if (Integer.valueOf(AB) == 1) {//以团队名义申请，在product表中填入团队相应的信息
						paper.setOrgPerson(teacher.getPerson());
						paper.setOrgName(H);
						paper.setOrgMember(I);
						paper.setOrgEmail(N);
						paper.setOrgMobilePhone(M);
						paper.setOrgOfficeAddress(Q);
						paper.setOrgOfficePhone(K);
						paper.setOrgOfficePostcode(R);
						awardApplication.setApplicationType(2);
					} else {
						paper.setAuthor(teacher.getPerson());
						paper.setAuthorName(H);
						paper.setOtherAuthorName(I);
						awardApplication.setApplicationType(1);
					}
						dao.add(paper);
						awardApplication.setProduct(paper);
						dao.add(awardApplication);
						
//						award.setApplication(awardApplication);
//						award.setSession(6);
//						award.setYear(2013);
//						dao.add(award);
						
				} else if (B.contains("研究报告") || B.contains("咨询报告")) {
				
					Consultation consultation = new Consultation();
					consultation.setChineseName(productName);
					consultation.setDisciplineType(D);
					consultation.setDiscipline(E);
					consultation.setPublicationDate(tool.getDate(G));
					consultation.setLanguage(AH);
					consultation.setUseUnit(F);
					consultation.setSubmitStatus(3);
					consultation.setUniversity(university);
					consultation.setAgencyName(S);
					if (W.contains("院系")){
						consultation.setDepartment(department);						
					} else if (W.contains("研究基地")) {
						consultation.setInstitute(institute);
					}
					consultation.setDivisionName(V);
					
					if (Integer.valueOf(AB) == 1) {//以团队名义申请，在product表中填入团队相应的信息
						consultation.setOrgPerson(teacher.getPerson());
						consultation.setOrgName(H);
						consultation.setOrgMember(I);
						consultation.setOrgEmail(N);
						consultation.setOrgMobilePhone(M);
						consultation.setOrgOfficeAddress(Q);
						consultation.setOrgOfficePhone(K);
						consultation.setOrgOfficePostcode(R);
						awardApplication.setApplicationType(2);
					} else {
						consultation.setAuthor(teacher.getPerson());
						consultation.setAuthorName(H);
						consultation.setOtherAuthorName(I);
						awardApplication.setApplicationType(1);
					}
						dao.add(consultation);
						awardApplication.setProduct(consultation);	
						dao.add(awardApplication);
						
//						award.setApplication(awardApplication);
//						award.setSession(6);
//						award.setYear(2013);
//						dao.add(award);
						
				} else if (B.contains("其他")) {					
					
					OtherProduct otherProduct = new OtherProduct();
					otherProduct.setChineseName(productName);
					otherProduct.setDisciplineType(D);
					otherProduct.setDiscipline(E);
					otherProduct.setPressDate(tool.getDate(G));
					otherProduct.setLanguage(AH);
					otherProduct.setPublishUnit(F);
					otherProduct.setSubmitStatus(3);
					otherProduct.setUniversity(university);
					otherProduct.setAgencyName(S);
					if (W.contains("院系")){
						otherProduct.setDepartment(department);						
					} else if (W.contains("研究基地")) {
						otherProduct.setInstitute(institute);
					}
					otherProduct.setDivisionName(V);
					
					if (Integer.valueOf(AB) == 1) {//以团队名义申请，在product表中填入团队相应的信息
						otherProduct.setOrgPerson(teacher.getPerson());
						otherProduct.setOrgName(H);
						otherProduct.setOrgMember(I);
						otherProduct.setOrgEmail(N);
						otherProduct.setOrgMobilePhone(M);
						otherProduct.setOrgOfficeAddress(Q);
						otherProduct.setOrgOfficePhone(K);
						otherProduct.setOrgOfficePostcode(R);
						awardApplication.setApplicationType(2);
					} else {
						otherProduct.setAuthor(teacher.getPerson());
						otherProduct.setAuthorName(H);
						otherProduct.setOtherAuthorName(I);
						awardApplication.setApplicationType(1);
					}
						dao.add(otherProduct);
						awardApplication.setProduct(otherProduct);	
						dao.add(awardApplication);
						
//						award.setApplication(awardApplication);
//						award.setSession(6);
//						award.setYear(2013);
//						dao.add(award);
				}
			}									
		}																					
	}

	private void validate() throws Exception {
		
		excelReader.readSheet(0);
		List 问题数据 = new ArrayList();
		while (next(excelReader)) {
			if(A == null) {
				break;
			}
			//System.out.println(excelReader.getCurrentRowIndex() + "/" + excelReader.getRowNumber());
			Agency university = universityFinder.getUnivByName(S);
			if (university == null) {
			    问题数据.add("高校： " + S);
				System.out.println("不存在的高校: " + S);
			} else {
				if (W.contains("院系")) {
					Department department = departmentFinder.getDepartment(university, V, false);
					if (department == null) {
						问题数据.add("院系："+ S + " - " + V);
						System.out.println("找不到该院系：" + S + " - " + V);
					}
				} else if (W.contains("研究基地")) {
					Institute institute = instituteFinder.getInstitute(university, V, false);
					if (institute == null) {
						问题数据.add("基地：" + S + " - " + V);
						System.out.println("找不到该研究基地：" + S + " - " + V);
					 }
				}
				
			}
		}
		for (int j = 0; j < 问题数据.size(); j++) {
			System.out.println(问题数据.get(j));
		}
	}
	
	public AwardApplicationSixthImporter(){};
	
	public AwardApplicationSixthImporter(String file){
		excelReader = new ExcelReader(file);
	}
}

