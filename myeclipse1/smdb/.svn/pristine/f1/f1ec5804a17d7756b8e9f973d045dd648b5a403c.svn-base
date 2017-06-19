package csdc.tool.execution.importer;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.InstpApplication;
import csdc.bean.InstpFunding;
import csdc.bean.InstpGranted;
import csdc.bean.Institute;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 
 * @author maowh
 *
 */
public class InstpProjectGranted2013Importer extends Importer{
	
	private ExcelReader reader1;
	
	public final static int YEAR = 2013;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private InstpProjectFinder instpProjectFinder;

	@Autowired
	private Tool tool;
	

	
	@Override
	public void work() throws Exception {
	    //validate();
		importData();
	}
	
	private void importData() throws Exception {
		
		reader1.readSheet(0);
		
		while (next(reader1)) {
			System.out.println(reader1.getCurrentRowIndex() + "/" + reader1.getRowNumber());
			
			String[] directorNames = D.split("[\\s　]+");
			
			InstpApplication application = instpProjectFinder.findApplication(C, directorNames[0]);
			application.setFinalAuditResult(2);
			InstpGranted granted = null;
			Agency university = universityFinder.getUnivByName(A);
			Institute institute = application.getInstitute();
			
			if (application.getInstpGranted().isEmpty()) {
				granted = new InstpGranted();
				application.addGranted(granted);
			} else {
				granted = application.getInstpGranted().iterator().next();
			}
			granted.setStatus(1);
			granted.setIsImported(1);
			
			granted.setUniversity(university);
			granted.setAgencyName(application.getAgencyName());
			
			granted.setInstitute(institute);
			granted.setDivisionName(institute.getName());
			
			granted.setApplicantId(application.getApplicantId());
			granted.setApplicantName(application.getApplicantName());
			granted.setMemberGroupNumber(1);
			
			granted.setName(C);
			granted.setNumber(F);
			
			granted.setProductType(application.getProductType());
			granted.setProductTypeOther(application.getProductTypeOther());
			
			granted.setPlanEndDate(application.getPlanEndDate());
			
			//01 部级
			//02省部共建			
			String instituteTypeCode = institute.getType().getCode();
			
			if (instituteTypeCode.equals("01")) {
					granted.setApproveFeeMinistry(Double.valueOf(G));
					granted.setApproveFeeUniversity(10.0);				
			} else if (instituteTypeCode.equals("02")) {
				granted.setApproveFeeMinistry(Double.valueOf(G));
				granted.setApproveFeeUniversity(5.0);
			} else {
				System.out.println(instituteTypeCode);
				throw new RuntimeException();
			}
			
			double approveFee = 0.0;
			if (granted.getApproveFeeMinistry() != null) {
				approveFee += granted.getApproveFeeMinistry();
			}
			if (granted.getApproveFeeSubjection() != null) {
				approveFee += granted.getApproveFeeSubjection();
			}
			if (granted.getApproveFeeUniversity() != null) {
				approveFee += granted.getApproveFeeUniversity();
			}
			granted.setApproveFee(approveFee);
			
			//存在教育部拨款信息且金额不为0时，导入拨款
			if (granted.getApproveFeeMinistry() != null && granted.getApproveFeeMinistry() > 0.01) {
				InstpFunding funding = new InstpFunding();
				funding.setFee(granted.getApproveFeeMinistry());
				funding.setDate(tool.getDate(2013, 7, 26));
				funding.setAttn("刘杰");
				granted.addFunding(funding);
			}
			
			granted.getApplication().setFinalAuditResult(2);
		}
		
	}
			
				
	
	private void validate() throws Exception {
		reader1.readSheet(0);
		
		HashSet exMsg = new HashSet();
		while (next(reader1)) {
			Agency university = universityFinder.getUnivByName(A);
			if (university == null) {
				exMsg.add("不存在的高校: " + A);
			}
			String[] directorNames = D.split("[\\s　]+");
			for (String string : directorNames) {
				string.charAt(0);
			}
			InstpApplication application = instpProjectFinder.findApplication(C, directorNames[0]);
			if (application == null) {
				exMsg.add("不存在的申请: " + C);
			}
		}

		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
	
	public InstpProjectGranted2013Importer(){
	}
	
	public InstpProjectGranted2013Importer(String file1) {
		reader1 = new ExcelReader(file1);		
	}
	

}
