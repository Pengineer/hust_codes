package csdc.tool.execution.importer;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.AgencyFunding;
import csdc.bean.FundingBatch;
import csdc.bean.FundingList;
import csdc.bean.GeneralFunding;
import csdc.bean.GeneralGranted;
import csdc.bean.Officer;
import csdc.bean.Teacher;
import csdc.dao.SystemOptionDao;
import csdc.tool.execution.finder.AgencyFundingFinder;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 
 * @author maowh
 * 导入《2014年第二批高等学校哲学社会科学繁荣计划专项资金划拨情况明细总表v2.xls_修正导入.xls》中的“ 13择优资助二期”和“14择优资助一期”
 *若库中没有申请和立项数据，则需先导入申请和立项数据
 */
public class SpecialIPTEMidInspectionFundingImporter extends Importer{
	
	private ExcelReader reader;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private GeneralProjectFinder generalProjectFinder;
	
	@Autowired
	private AgencyFundingFinder agencyFundingFinder;

	@Autowired
	private Tool tool;
	
	@Autowired
	private SystemOptionDao systemOptionDao;
	
	@Autowired
	private UnivPersonFinder univPersonFinder;
	

	
	@Override
	public void work() throws Exception {
//	    validate();
//		importData();
		fixData();//修正拨款数据（原本将一期拨款录为立项拨款，二期拨款录为中检拨款，现在修正）
	}

	private void importData() throws Exception {
		
//		reader.readSheet(5);
		reader.readSheet(6);
		
		Officer 宋义栋 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '宋义栋'");
		
		FundingList fundingList = new FundingList();
		FundingBatch projectFundingBatch = (FundingBatch) dao.queryUnique("select fb from FundingBatch fb where fb.name = '2014年第二批高等学校哲学社会科学繁荣计划专项资金拨款'");		
		
		fundingList.setCount(50);
		fundingList.setCreateDate(tool.getDate("2014-12-25"));
		fundingList.setFee(150.0);
		fundingList.setStatus(1);
		fundingList.setFundingBatch(projectFundingBatch);
//		fundingList.setSubSubType("mid");
		fundingList.setSubSubType("granted");
		
		fundingList.setSubType("general");
		fundingList.setType("project");
		fundingList.setRate(30.0);
		fundingList.setYear(2014);
//		fundingList.setName("2013年度“全国高校优秀中青年思想政治理论课教师择优资助计划”二期拨款一览表");
		fundingList.setName("2014年度“全国高校优秀中青年思想政治理论课教师择优资助计划”一期拨款一览表");
		
	
		dao.addOrModify(fundingList);
		
		while (next(reader)) {			
			if (A.isEmpty() || A == null) {
				break;
			}
			
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			
			GeneralGranted granted = generalProjectFinder.findGranted(D);
			Agency university = universityFinder.getUnivByName(B);
			Teacher applicant = univPersonFinder.findTeacher(A.replaceAll("\\s+", ""), university);
			
			if (G == null || Integer.valueOf(G.trim()) == 0 || "".equals(G.trim())) {
				continue;
			}			
			GeneralFunding funding = new GeneralFunding();
			funding.setFundingList(fundingList);
			funding.setDate(tool.getDate(2014, 12, 25));
			funding.setFee(tool.getFee(F));
			
			funding.setAgency(university);
			funding.setAgencyName(university.getName());
			funding.setGranted(granted);
			
			funding.setPerson(applicant.getPerson());
			funding.setPayee(applicant.getPerson().getName());
			funding.setDepartment(applicant.getDepartment());
			funding.setInstitute(applicant.getInstitute());
			granted.addFunding(funding); 	
			
			funding.setStatus(1);
//			funding.setType(2);//中检拨款
			funding.setType(1);//立项拨款
			granted.addFunding(funding);
			dao.addOrModify(granted);
			
			AgencyFunding af  = agencyFundingFinder.getAgencyFunding(B, "2014年第二批高等学校哲学社会科学繁荣计划专项资金拨款");
			if(af == null) {
				af = new AgencyFunding();
				AgencyFundingFinder.agencyFundingMap.put(university.getName() + "2014年第二批高等学校哲学社会科学繁荣计划专项资金拨款", af);
				af.setAgency(university);
				af.setAgencyName(university.getName());
				af.setFundingBatch(projectFundingBatch);
				dao.add(af);
			}
			funding.setAgencyFunding(af);
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
			
			GeneralGranted granted = generalProjectFinder.findGranted(D);
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
	
	private void fixData() throws Exception {
		reader.readSheet(5);
		while (next(reader)) {
			if (A.isEmpty() || A == null) {
				break;
			}
			
			GeneralGranted granted = generalProjectFinder.findGranted(D);
			Set<GeneralFunding> gfs = (Set<GeneralFunding>) granted.getFunding();
			int size = gfs.size();
			if (size != 1) {
				continue;
			}
			for (GeneralFunding gf : gfs) {
				int type = gf.getType();
				String date = gf.getDate().toString();
				System.out.println(type + "--" + date + granted.getName());
				gf.setType(5);
//				gf.setType(4);
				dao.addOrModify(gf);
			}
		}

		
	}
	
	public SpecialIPTEMidInspectionFundingImporter(){
	}
	
	public SpecialIPTEMidInspectionFundingImporter(String file1) {
		reader = new ExcelReader(file1);		
	}
	

}



