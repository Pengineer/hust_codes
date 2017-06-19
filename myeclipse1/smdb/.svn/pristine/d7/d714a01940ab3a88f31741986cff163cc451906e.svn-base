package csdc.tool.execution.importer;

import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.AgencyFunding;
import csdc.bean.FundingBatch;
import csdc.bean.FundingList;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralEndinspection;
import csdc.bean.GeneralFunding;
import csdc.bean.GeneralGranted;
import csdc.bean.GeneralMember;
import csdc.bean.Officer;
import csdc.bean.Person;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.dao.SystemOptionDao;
import csdc.tool.execution.finder.AcademicFinder;
import csdc.tool.execution.finder.AgencyFundingFinder;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;
/**
 * 导入：《2014年第二批高等学校哲学社会科学繁荣计划专项资金划拨情况明细总表v2_修正导入.xls》中的“马三化结项”
 * @author pengliang
 * 备注：所有的项目都需要添加申请，立项和结项数据。
 */
public class SpecialEndinspectionMarxFunding2014Importer extends Importer {
	
	private ExcelReader reader;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private GeneralProjectFinder generalProjectFinder;
	
	@Autowired
	private UnivPersonFinder univPersonFinder;
	
	@Autowired
	private AcademicFinder academicFinder;
	
	@Autowired
	private AgencyFundingFinder agencyFundingFinder;

	@Autowired
	private Tool tool;
	
	@Autowired
	private SystemOptionDao systemOptionDao;
	
	private String batchName = "2014年第二批高等学校哲学社会科学繁荣计划专项资金划拨情况";
	
	@Override
	protected void work() throws Throwable {
		//0，只执行validate(); 
		//1，只执行addApplication();
		//2，只执行addApplication();
		//3，只执行validate();
		//4，只执行importData();
		freeMemory();
		System.out.println("start......");
	//	validate(); 
	//	addApplication();
	//	addGranted();
		importData();
	}
	
	/*
	 * 导入拨款清单数据（批次的创建只出现在同一批的的一个java文件中）
	 */
	private void importData() throws Exception {
		Officer 杨瑞 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '杨瑞'");
		FundingList fundingList = new FundingList();
		FundingBatch projectFundingBatch = (FundingBatch) dao.queryUnique("select fb from FundingBatch fb where fb.name = ?", batchName);		
		
		fundingList.setCount(11);
		fundingList.setCreateDate(tool.getDate(2014, 12, 25));
		fundingList.setFee(24.0);
		fundingList.setFundingBatch(projectFundingBatch);
		fundingList.setSubSubType("end");
		fundingList.setSubType("special");
		fundingList.setType("project");
		fundingList.setStatus(1);
		fundingList.setYear(2014);
		fundingList.setName("2014年度教育部人文社会科学研究专项任务项目（马克思主义中国化时代化大众化）结项拨款一览表");
		dao.addOrModify(fundingList);
		
		reader.readSheet(14);
		reader.setCurrentRowIndex(1);
		while (next(reader)) {			
			if (A.isEmpty() || A == null) {
				break;
			}
			
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			
			GeneralGranted granted = generalProjectFinder.findGranted(B.trim());
			GeneralEndinspection endinspection = new GeneralEndinspection();
			endinspection.setFinalAuditDate(tool.getDate(2014, 12, 25));
			endinspection.setFinalAuditor(杨瑞);
			endinspection.setFinalAuditResultEnd(2);
			endinspection.setFinalAuditorName("杨瑞");
			endinspection.setFinalAuditStatus(3);
			endinspection.setImportedDate(new Date());
			endinspection.setGranted(granted);
			endinspection.setStatus(7);
			
			granted.setStatus(2);
			dao.addOrModify(granted);
			dao.add(endinspection);	
			if(I == null || Double.parseDouble(I.trim())==0 || "".equals(I.trim())){
				continue;
			}
			
			Agency university = universityFinder.getUnivByName(E.trim());
			//（AgencyFunding表用来存放各高校在本批次的总拨款数）判定该高校在本批次中是否有导入过，若没有就添加
			AgencyFunding agencyFunding = agencyFundingFinder.getAgencyFunding(university.getName(), batchName);
			if(agencyFunding == null){
				agencyFunding = new AgencyFunding();
				agencyFundingFinder.agencyFundingMap.put(university.getName() + batchName, agencyFunding);  //agencyFundingMap是一个静态的Map，用来存放本批次的高校拨款情况
				agencyFunding.setAgency(university);
				agencyFunding.setAgencyName(university.getName());
				agencyFunding.setFundingBatch(projectFundingBatch);
				dao.add(agencyFunding);
			}
			
			GeneralFunding funding = new GeneralFunding();
			funding.setFundingList(fundingList);
			funding.setDate(tool.getDate(2014, 12, 25));
			funding.setFee(tool.getFee(I));
			funding.setAgency(university);
			funding.setAgencyName(university.getName());
			funding.setPayee(university.getName());
			funding.setStatus(1);
			funding.setType(3);
			funding.setAgencyFunding(agencyFunding);   //建立项目拨款和对应的高校总拨款的关联关系
			granted.addFunding(funding);
			dao.addOrModify(granted);
			dao.add(funding); 
		}
	}
	
	/*
	 * 1，校验高校是否存在
	 * 2，校验对应的申请数据是否存在（本次所有的申请数据都不存在，因此不用校验立项数据是否存在）
	 */
	private void validate() throws Exception {
		reader.readSheet(14);
		
		HashSet<String> exMsg = new HashSet<String>();
		reader.setCurrentRowIndex(1);
		while (next(reader)) {
			if (A.isEmpty() || A == null) {
				break;
			}
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			Agency university = universityFinder.getUnivByName(E.trim());
			if (university == null) {
				exMsg.add("不存在的高校: " + E);
			}
			int year =0;
			if(B.contains("10JD")){
				year = 2010;
			} else if (B.contains("11JD")) {
				year = 2011;
			}  else if (B.contains("12JD")) {
				year = 2012;
			}
			GeneralApplication application = generalProjectFinder.findApplication(D.trim(), F.trim(), year);
			if (application == null){
				exMsg.add("不存在的申请项目：" + B);
			}
		}
		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
	
	/*
	 * 添加申请数据
	 */
	public void addApplication() throws Exception{
		SystemOption 专项 = systemOptionDao.query("projectType", "014");		
		reader.readSheet(14);
		reader.setCurrentRowIndex(1);
		while(next(reader)){
			if (A.isEmpty() || A == null) {
				break;
			}
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			GeneralApplication application = new GeneralApplication(); 
			Agency university = universityFinder.getUnivByName(E.trim());
			Teacher applicant = univPersonFinder.findTeacher(F.replaceAll("\\s+", ""), university);
			Academic academic = academicFinder.findAcademic(applicant.getPerson());
			if (academic == null) {
				academic = new Academic();
				academic.setPerson(applicant.getPerson());	
				applicant.getPerson().setAcademic(academic);
			}
			int year =0;
			if(B.contains("10JD")){
				year = 2010;
			} else if (B.contains("11JD")) {
				year = 2011;
			}  else if (B.contains("12JD")) {
				year = 2012;
			}
			application.setYear(year);
			application.setName(D.trim());
			application.setUniversity(university);
			application.setAgencyName(university.getName());
			application.setApplicantId(applicant.getPerson().getId());
			application.setApplicantName(F.replaceAll("\\s+", ""));
			application.setSubtype(专项);
			application.setImportedDate(tool.getDate(2014, 12, 25));
			
			addMember(application, applicant);
			
			dao.addOrModify(application);
			dao.addOrModify(academic);
		}
	}
	
	/*
	 * 未申请数据添加成员信息（只有负责人的信息，负责人均是teacher）
	 */
	private void addMember(GeneralApplication application, Teacher director) {
		GeneralMember member = new GeneralMember();  		
		Person person = director.getPerson();
		System.out.println("项目申请人：" + person.getName());
		member.setUniversity(application.getUniversity());
		member.setAgencyName(E.trim());	

		member.setMemberType(1);
		member.setIsDirector(1);
		member.setMemberName(person.getName());
		member.setMemberSn(1);	
		application.addMember(member);
	
		member.setMember(person);	
		member.setGroupNumber(1);		
	}
	
	/*
	 * 添加立项数据
	 */
	public void addGranted() throws Exception{
		Officer 杨瑞 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '杨瑞'");
		SystemOption 专项 = systemOptionDao.query("projectType", "014");
		
		reader.readSheet(14);
		reader.setCurrentRowIndex(1);
		while(next(reader)){
			if (A.isEmpty() || A == null) {
				break;
			}
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			int year =0;
			GeneralGranted granted = new GeneralGranted();
			GeneralFunding funding = new GeneralFunding();
			if(B.contains("10JD")){
				granted.setApproveDate(tool.getDate(2010, 7, 16));
				funding.setDate(tool.getDate(2010, 12, 24));
				year = 2010;
			} else if (B.contains("11JD")) {
				granted.setApproveDate(tool.getDate(2011, 9, 2));
				funding.setDate(tool.getDate(2011, 11, 7));
				year = 2011;
			}  else if (B.contains("12JD")) {
				granted.setApproveDate(tool.getDate(2012, 2, 24));
				funding.setDate(tool.getDate(2012, 10, 25));
				year = 2012;
			}
			GeneralApplication application = generalProjectFinder.findApplication(D.trim(), F.trim(), year);
			
			Agency university = universityFinder.getUnivByName(E.trim());
			granted.setStatus(1);
			granted.setIsImported(1);
			granted.setImportedDate(new Date());
			application.addGranted(granted);
			granted.setUniversity(university);
			granted.setAgencyName(application.getAgencyName());
			
			granted.setApplicantId(application.getApplicantId());
			granted.setApplicantName(application.getApplicantName());
			granted.setMemberGroupNumber(1);
			
			granted.setSubtype(专项);
			granted.setName(D.trim());
			granted.setNumber(B.trim());
			granted.setApproveFee(Double.parseDouble(G.trim()));
					
			funding.setFee(tool.getFee(H));
			funding.setStatus(1);
			funding.setType(4);
			granted.addFunding(funding);
			
			granted.getApplication().setFinalAuditResult(2);
			granted.getApplication().setFinalAuditStatus(3);
			granted.getApplication().setFinalAuditor(杨瑞); 
			granted.getApplication().setFinalAuditorName(杨瑞.getPerson().getName());
			granted.getApplication().setFinalAuditorAgency(杨瑞.getAgency());
			granted.getApplication().setFinalAuditorInst(杨瑞.getInstitute());
			if(B.contains("10JD")){
				granted.getApplication().setFinalAuditDate(tool.getDate(2010, 7, 16));
			} else if (B.contains("11JD")) {
				granted.getApplication().setFinalAuditDate(tool.getDate(2011, 9, 2));
			}  else if (B.contains("12JD")) {
				granted.getApplication().setFinalAuditDate(tool.getDate(2012, 2, 24));
			}
		}
	}
	
	public SpecialEndinspectionMarxFunding2014Importer() {
	}
	
	public SpecialEndinspectionMarxFunding2014Importer(String fileName) {
		reader = new ExcelReader(fileName);
	}
	
	public void freeMemory(){
		generalProjectFinder.reset();
		universityFinder.reset();
		univPersonFinder.reset();
		academicFinder.reset();
	}
}
