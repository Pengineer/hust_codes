package csdc.tool.execution.importer;

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
import csdc.bean.Teacher;
import csdc.tool.execution.finder.AgencyFundingFinder;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 导入：《2014年第二批高等学校哲学社会科学繁荣计划专项资金划拨情况明细总表v2.xls_修正导入.xls》中的“一般项目结项明细”
 * @author maowh
 *
 */
public class GeneralEndInspectionFunding2014Importer extends Importer{
	
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
		
		reader.readSheet(0);
		GeneralEndinspection endinspection = null;
		
		FundingList fundingList = new FundingList();
		FundingBatch projectFundingBatch = new FundingBatch();
		
		projectFundingBatch.setDate(tool.getDate("2014-12-25"));
		projectFundingBatch.setName("一般项目结项明细");		
		
		fundingList.setCount(1562);
		fundingList.setCreateDate(tool.getDate("2014-12-25"));
		fundingList.setFee(1028.34);
		fundingList.setFundingBatch(projectFundingBatch);
		fundingList.setSubSubType("end");
		fundingList.setSubType("general");
		fundingList.setType("project");
		fundingList.setStatus(1);		
		fundingList.setRate(10.0);
		fundingList.setYear(2014);
		fundingList.setName("2014年第二批高等学校哲学社会科学繁荣计划专项资金划拨情况");
		
		dao.addOrModify(projectFundingBatch);
		dao.addOrModify(fundingList);
		
		while (next(reader)) {			
			if (A.isEmpty() || A == null) {
				break;
			}
			
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			
			Agency university = universityFinder.getUnivByName(A);
			
			
			GeneralGranted granted = generalProjectFinder.findGrantedByName(C,B);
			Teacher applicant = univPersonFinder.findTeacher(D.replaceAll("\\s+", ""), university);

			
			Set<GeneralEndinspection> endinspections = granted.getEndinspection();
			for (GeneralEndinspection end : endinspections) {
				if(end.getFinalAuditResultEnd() == 2) {
					endinspection = end;
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
			funding.setType(3);
			funding.setAgency(university);
			funding.setAgencyName(university.getName());
			funding.setGranted(granted);
			
			funding.setPerson(applicant.getPerson());
			funding.setPayee(applicant.getPerson().getName());
			funding.setDepartment(applicant.getDepartment());
			funding.setInstitute(applicant.getInstitute());
			granted.addFunding(funding);
			
			AgencyFunding af  = agencyFundingFinder.getAgencyFunding(A, "2014年第二批高等学校哲学社会科学繁荣计划专项资金拨款");
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
			dao.addOrModify(granted);
		}
		
	}
			
				
	
	private void validate() throws Exception {
		reader.readSheet(0);
		
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
			
			GeneralGranted granted = generalProjectFinder.findGrantedByName(C,B);
			if (granted == null) {
				exMsg.add("不存在的立项: " + B);
			} else {				
				Double approveFee = granted.getApproveFee();
				Double in = Double.valueOf(F);
				if(!(approveFee.equals(in) )) {
					exMsg.add("批准经费与库中的不一致: " + B);
				}
				Set<GeneralEndinspection> endinspections = granted.getEndinspection();
			
				if( endinspections == null) {
					exMsg.add("该项目不存在结项: " + B);
				} else if(endinspections.size() != 1){
					int i = 0;//结项同意的数目
					for (GeneralEndinspection endinspection : endinspections) {
						if (endinspection.getFinalAuditResultEnd() == 2) {
							i++;
						}
					}
					if (i != 1) {
						exMsg.add("该项目存在多个结项: " + B + "数量为：" + endinspections.size());
					}									
				}
			}
		}	
		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
	
	public GeneralEndInspectionFunding2014Importer(){
	}
	
	public GeneralEndInspectionFunding2014Importer(String file1) {
		reader = new ExcelReader(file1);
	}
	

}
