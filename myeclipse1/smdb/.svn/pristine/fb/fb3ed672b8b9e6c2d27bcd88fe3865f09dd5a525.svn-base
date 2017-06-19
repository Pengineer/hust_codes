package csdc.tool.execution.importer;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.AgencyFunding;
import csdc.bean.FundingBatch;
import csdc.bean.FundingList;
import csdc.bean.GeneralEndinspection;
import csdc.bean.GeneralFunding;
import csdc.bean.GeneralGranted;
import csdc.bean.GeneralMidinspection;
import csdc.bean.Officer;
import csdc.bean.Teacher;
import csdc.tool.execution.finder.AgencyFundingFinder;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 
 * @author maowh
 * 导入《2014年第二批高等学校哲学社会科学繁荣计划专项资金划拨情况明细总表v2.xls_修正导入.xls》中的“一般项目中检明细”
 */
public class GeneralMidInspectionFunding2014Importer extends Importer{
	
	private ExcelReader reader;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private GeneralProjectFinder generalProjectFinder;
	
	@Autowired
	private UnivPersonFinder univPersonFinder;
	
	@Autowired
	private AgencyFundingFinder agencyFundingFinder;

	@Autowired
	private Tool tool;
	

	
	@Override
	public void work() throws Exception {
//	    validate();
		importData();
	}
	
	private void importData() throws Exception {
		
		reader.readSheet(1);
		
		FundingList fundingList = new FundingList();
		FundingBatch projectFundingBatch = (FundingBatch) dao.queryUnique("select fb from FundingBatch fb where fb.name = '2014年第二批高等学校哲学社会科学繁荣计划专项资金拨款'");		
		
		fundingList.setCount(4116);
		fundingList.setCreateDate(tool.getDate("2014-12-25"));
		fundingList.setFee(9195.77);
		fundingList.setFundingBatch(projectFundingBatch);
		fundingList.setSubSubType("mid");
		fundingList.setSubType("general");
		fundingList.setType("project");
		fundingList.setRate(30.0);
		fundingList.setYear(2014);
		fundingList.setStatus(1);
		fundingList.setName("2014年教育部人文社会科学研究一般项目中检拨款一览表");
	
		dao.addOrModify(fundingList);
		
		GeneralMidinspection generalMidinspection = null;
		
		while (next(reader)) {			
			if (A.isEmpty() || A == null) {
				break;
			}
			
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			Agency university = universityFinder.getUnivByName(A);
			Teacher applicant = univPersonFinder.findTeacher(D.replaceAll("\\s+", ""), university);
			
			GeneralGranted granted = generalProjectFinder.findGranted(C);
			Set<GeneralMidinspection> midinspections = granted.getMidinspection();
			for (GeneralMidinspection mid : midinspections) {
				if(mid.getFinalAuditResult() == 2) {
					generalMidinspection = mid;
				}
			}
			
			if (G == null || Integer.valueOf(G.trim()) == 0 || "".equals(G.trim())) {
				continue;
			}
			GeneralFunding funding = new GeneralFunding();
			funding.setFundingList(fundingList);
			funding.setDate(tool.getDate(2014, 12, 25));
			funding.setFee(tool.getFee(G));
			funding.setStatus(1);
			funding.setType(2);
			funding.setAgency(university);
			funding.setAgencyName(university.getName());
			funding.setGranted(granted);
			
			funding.setPerson(applicant.getPerson());
			funding.setPayee(applicant.getPerson().getName());
			funding.setDepartment(applicant.getDepartment());
			funding.setInstitute(applicant.getInstitute());
			granted.addFunding(funding); 
			
			AgencyFunding af  = agencyFundingFinder.getAgencyFunding(A, "2014年第二批高等学校哲学社会科学繁荣计划专项资金划拨情况");
			if(af == null) {
				af = new AgencyFunding();
				AgencyFundingFinder.agencyFundingMap.put(university.getName() + "2014年第二批高等学校哲学社会科学繁荣计划专项资金划拨情况", af);
				af.setAgency(university);
				af.setAgencyName(university.getName());
				af.setFundingBatch(projectFundingBatch);
				dao.addOrModify(af);
			}
			funding.setAgencyFunding(af);
			dao.add(funding);	
		}
		
	}
			
				
	
	private void validate() throws Exception {
		reader.readSheet(1);
		
		HashSet exMsg = new HashSet();
		
		while (next(reader)) {
			if (A.isEmpty() || A == null) {
				break;
			}
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			Agency university = universityFinder.getUnivByName(A);
			if (university == null) {
				exMsg.add("不存在的高校: " + A);
			}
			
//			GeneralGranted granted = generalProjectFinder.findGrantedByName(C,B);
			GeneralGranted granted = generalProjectFinder.findGranted(C);
			if (granted == null) {
				exMsg.add("用项目名称加批准号不存在的立项: " + B + C);
			} else {				
				Double approveFee = granted.getApproveFee();
				Double in = Double.valueOf(F);
				if(!(approveFee.equals(in) )) {
					exMsg.add("批准经费与库中的不一致: " + B);
				}
				Set<GeneralMidinspection> midinspections = granted.getMidinspection();
			
				if( midinspections == null) {
					exMsg.add("该项目不存在结项: " + B);
				} else if(midinspections.size() != 1){
					int i = 0;//结项同意的数目
					for (GeneralMidinspection midinspection : midinspections) {
						if (midinspection.getFinalAuditResult() == 2) {
							i++;
						}
					}
					if (i != 1) {
						exMsg.add("该项目存在多个中检同意: " + B + "数量为：" + midinspections.size());
					}									
				}
			}
		}	
		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
	
	public GeneralMidInspectionFunding2014Importer(){
	}
	
	public GeneralMidInspectionFunding2014Importer(String file1) {
		reader = new ExcelReader(file1);		
	}
	

}

