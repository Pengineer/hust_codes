package csdc.tool.execution.importer;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.AgencyFunding;
import csdc.bean.FundingBatch;
import csdc.bean.FundingList;
import csdc.bean.GeneralMidinspection;
import csdc.bean.KeyFunding;
import csdc.bean.KeyGranted;
import csdc.bean.KeyMidinspection;
import csdc.bean.Officer;
import csdc.bean.PostEndinspection;
import csdc.bean.PostFunding;
import csdc.bean.Teacher;
import csdc.dao.SystemOptionDao;
import csdc.tool.execution.finder.AgencyFundingFinder;
import csdc.tool.execution.finder.KeyProjectFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 
 * @author maowh
 *导入《2014年第二批高等学校哲学社会科学繁荣计划专项资金划拨情况明细总表v2.xls_修正导入.xls》中的“ 12重大攻关中检”
 *若库中没有申请和立项数据，则需先导入申请和立项数据
 */
public class KeyMidInspectionFunding2014Importer extends Importer{
	
	private ExcelReader reader;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private KeyProjectFinder keyProjectFinder;
	
	@Autowired
	private UnivPersonFinder univPersonFinder;
	
	@Autowired
	private AgencyFundingFinder agencyFundingFinder;

	@Autowired
	private Tool tool;
	
	@Autowired
	private SystemOptionDao systemOptionDao;
	

	
	@Override
	public void work() throws Exception {
//	    validate();
		importData();
	}
	
	private void importData() throws Exception {
		
		reader.readSheet(3);
		
		Officer 田晨亮 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '田晨亮'");
		
		FundingList fundingList = new FundingList();
		FundingBatch projectFundingBatch = (FundingBatch) dao.queryUnique("select fb from FundingBatch fb where fb.name = '2014年第二批高等学校哲学社会科学繁荣计划专项资金拨款'");		
		
		fundingList.setCount(48);
		fundingList.setCreateDate(tool.getDate("2014-12-25"));
		fundingList.setFee(1041.0);
		fundingList.setStatus(1);
		fundingList.setFundingBatch(projectFundingBatch);
		fundingList.setSubSubType("mid");
		fundingList.setSubType("key");
		fundingList.setType("project");
		fundingList.setRate(30.0);
		fundingList.setYear(2014);
		fundingList.setName("2014年教育部哲学社会科学研究重大课题攻关项目中检拨款一览表");
	
		dao.addOrModify(fundingList);
		
		while (next(reader)) {			
			if (A.isEmpty() || A == null) {
				break;
			}
			
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			
			Agency university = universityFinder.getUnivByName(C);
			Teacher applicant = univPersonFinder.findTeacher(D.replaceAll("\\s+", ""), university);
			
			KeyGranted granted = keyProjectFinder.findGrantedByName(B);
			KeyMidinspection keyMidinspection = new KeyMidinspection();
			keyMidinspection.setFinalAuditDate(tool.getDate("2014-12-25"));
			keyMidinspection.setFinalAuditor(田晨亮);
			keyMidinspection.setFinalAuditorAgency(田晨亮.getAgency());
			keyMidinspection.setFinalAuditorDept(田晨亮.getDepartment());
			keyMidinspection.setFinalAuditorInst(田晨亮.getInstitute());
			keyMidinspection.setStatus(5);
			keyMidinspection.setFinalAuditResult(2);
			keyMidinspection.setFinalAuditorName("田晨亮");
			keyMidinspection.setFinalAuditStatus(3);
			keyMidinspection.setImportedDate(new Date());
			keyMidinspection.setGranted(granted);
			keyMidinspection.setIsImported(1);
			keyMidinspection.setGranted(granted);
			keyMidinspection.setApplicantSubmitStatus(3);
			
			granted.addMidinspection(keyMidinspection);
			dao.addOrModify(granted);
			
			if (G == null || Integer.valueOf(G.trim()) == 0 || "".equals(G.trim())) {
				continue;
			}
			KeyFunding funding = new KeyFunding();
			funding.setFundingList(fundingList);
			funding.setDate(tool.getDate(2014, 12, 25));
			funding.setFee(tool.getFee(F));
			funding.setStatus(1);
			funding.setType(2);//中检拨款
			funding.setAgency(university);
			funding.setAgencyName(university.getName());
			funding.setGranted(granted);
			
			funding.setPerson(applicant.getPerson());
			funding.setPayee(applicant.getPerson().getName());
			funding.setDepartment(applicant.getDepartment());
			funding.setInstitute(applicant.getInstitute());
			
			AgencyFunding af  = agencyFundingFinder.getAgencyFunding(C, "2014年第二批高等学校哲学社会科学繁荣计划专项资金拨款");
			if(af == null) {
				af = new AgencyFunding();
				AgencyFundingFinder.agencyFundingMap.put(university.getName() + "2014年第二批高等学校哲学社会科学繁荣计划专项资金拨款", af);
				af.setAgency(university);
				af.setAgencyName(university.getName());
				af.setFundingBatch(projectFundingBatch);
				dao.add(af);
			}
			funding.setAgencyFunding(af);
			granted.addFunding(funding); 
			dao.addOrModify(granted);
			dao.add(funding); 
			dao.add(keyMidinspection);
		}
		
	}
			
				
	
	private void validate() throws Exception {
		reader.readSheet(3);
//		Officer 张海泽 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '张海泽'");
		
		HashSet exMsg = new HashSet();
		
		while (next(reader)) {
			if (A.isEmpty() || A == null) {
				break;
			}
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			Agency university = universityFinder.getUnivByName(C);
			if (university == null) {
				exMsg.add("不存在的高校: " + C);
			}
			
			KeyGranted granted = keyProjectFinder.findGrantedByName(B);
//			GeneralGranted granted = generalProjectFinder.findGranted(C);
			if (granted == null) {								
				exMsg.add("用项目名称加批准号不存在的立项: " + B);
			} else {				
				Double approveFee = granted.getApproveFee();
				if (approveFee != null) {
					Double in = Double.valueOf(E);
					if(!(approveFee.equals(in) )) {
						exMsg.add("批准经费与库中的不一致: " + D);
					}
				} else {
					granted.setApproveFee(Double.valueOf(E));
					dao.addOrModify(granted);
				}
			}
		}	
		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
	
	public KeyMidInspectionFunding2014Importer(){
	}
	
	public KeyMidInspectionFunding2014Importer(String file1) {
		reader = new ExcelReader(file1);		
	}
	

}




