package csdc.tool.execution.importer;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.AgencyFunding;
import csdc.bean.FundingBatch;
import csdc.bean.FundingList;
import csdc.bean.GeneralFunding;
import csdc.bean.GeneralGranted;
import csdc.bean.GeneralMidinspection;
import csdc.bean.KeyFunding;
import csdc.bean.KeyGranted;
import csdc.bean.KeyMidinspection;
import csdc.bean.Officer;
import csdc.bean.PostEndinspection;
import csdc.bean.PostFunding;
import csdc.bean.DevrptFunding;
import csdc.bean.DevrptGranted;
import csdc.bean.Teacher;
import csdc.dao.SystemOptionDao;
import csdc.tool.execution.finder.AgencyFundingFinder;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.KeyProjectFinder;
import csdc.tool.execution.finder.DevrptProjectFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 
 * @author maowh
 */
public class ReportProjectFundingImporter extends Importer{
	
	private ExcelReader reader;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private DevrptProjectFinder reportProjectFinder;
	
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
		reader.readSheet(4);
		
		Officer 王楠 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '王楠'");
		
		FundingList fundingList = new FundingList();
		FundingBatch projectFundingBatch = (FundingBatch) dao.queryUnique("select fb from FundingBatch fb where fb.name = '2014年第二批高等学校哲学社会科学繁荣计划专项资金拨款'");		
		
		fundingList.setCount(124);
		fundingList.setCreateDate(tool.getDate("2014-12-25"));
		fundingList.setFee(1345.0);
		fundingList.setStatus(1);
		fundingList.setFundingBatch(projectFundingBatch);
		fundingList.setGrandType("granted");
		
		fundingList.setSubType("report");
		fundingList.setType("project");
		fundingList.setRate(100.0);
		fundingList.setYear(2014);
//		fundingList.setName("2013年度“全国高校优秀中青年思想政治理论课教师择优资助计划”二期拨款一览表");
		fundingList.setName("2014年教育部发展报告项目拨款明细");
		
	
		dao.addOrModify(fundingList);
		
		while (next(reader)) {			
			if (A.isEmpty() || A == null) {
				break;
			}
			
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			
			DevrptGranted granted = reportProjectFinder.findGranted(E);
			Agency university = universityFinder.getUnivByName(G);
			Teacher applicant = univPersonFinder.findTeacher(C.replaceAll("\\s+", ""), university);
				
			
			DevrptFunding funding = new DevrptFunding();
			funding.setFundingList(fundingList);
			funding.setDate(tool.getDate(2014, 12, 25));
			funding.setFee(tool.getFee(H));
			funding.setStatus(1);
			funding.setType(1);//立项拨款
			funding.setAgency(university);
			funding.setAgencyName(university.getName());
			funding.setGranted(granted);
			
			funding.setPerson(applicant.getPerson());
			funding.setPayee(applicant.getPerson().getName());
			funding.setDepartment(applicant.getDepartment());
			funding.setInstitute(applicant.getInstitute());
			granted.addFunding(funding); 
			
			AgencyFunding af  = agencyFundingFinder.getAgencyFunding(G, "2014年第二批高等学校哲学社会科学繁荣计划专项资金拨款");
			if(af == null) {
				af = new AgencyFunding();
				AgencyFundingFinder.agencyFundingMap.put(university.getName() + "2014年第二批高等学校哲学社会科学繁荣计划专项资金拨款", af);
				af.setAgency(university);
				af.setAgencyName(university.getName());
				af.setFundingBatch(projectFundingBatch);
				dao.add(af);
			}
			funding.setAgencyFunding(af);
			dao.addOrModify(granted);
			dao.add(funding); 
		}
		
	}
			
				
	
	private void validate() throws Exception {
		reader.readSheet(6);
//		Officer 张海泽 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '张海泽'");
		
		HashSet exMsg = new HashSet();
		
		while (next(reader)) {
			if (A.isEmpty() || A == null) {
				break;
			}
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			
			DevrptGranted granted = reportProjectFinder.findGranted(D);
//			GeneralGranted granted = generalProjectFinder.findGranted(C);
			if (granted == null) {								
				exMsg.add("用项目名称加批准号不存在的立项: " + D);
			} 
		}	
		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
	
	public ReportProjectFundingImporter(){
	}
	
	public ReportProjectFundingImporter(String file1) {
		reader = new ExcelReader(file1);		
	}
	

}




