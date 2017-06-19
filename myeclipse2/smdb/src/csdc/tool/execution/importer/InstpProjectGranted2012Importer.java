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
 * 导入《2012年基地项目立项一览表_修正导入.xls》、《2012年基地项目立项一览表（省厅资助项目）_修正导入.xls》
 *
 * @author xuhan
 */
public class InstpProjectGranted2012Importer extends Importer {
	
	/**
	 * 《2012年基地项目立项一览表_修正导入.xls》
	 */
	private ExcelReader reader1;
	
	/**
	 * 《2012年基地项目立项一览表（省厅资助项目）_修正导入.xls》
	 */
	private ExcelReader reader2;
	
	public final static int YEAR = 2012;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private InstpProjectFinder instpProjectFinder;

	@Autowired
	private Tool tool;
	

	
	@Override
	public void work() throws Exception {
		validate();
		importData();
	}

	private void importData() throws Exception {
		Set<String> 省厅资助项目编号 = new HashSet<String>();
		
		reader2.readSheet(0);
		while (next(reader2)) {
			System.out.println(reader2.getCurrentRowIndex() + "/" + reader2.getRowNumber());
			省厅资助项目编号.add(G);
		}
		
		
		
		reader1.readSheet(0);
		while (next(reader1)) {
			System.out.println(reader1.getCurrentRowIndex() + "/" + reader1.getRowNumber());
			
			String[] directorNames = D.split("[\\s　]+");
			for (String string : directorNames) {
				string.charAt(0);
			}

			InstpApplication application = instpProjectFinder.findApplication(C, directorNames[0]);
			InstpGranted granted = null;
			Agency university = universityFinder.getUnivByName(A);
			Institute institute = application.getInstitute();
			
			if (application.getGranted().isEmpty()) {
				granted = new InstpGranted();
				application.addGranted(granted);
			} else {
				granted = application.getGranted().iterator().next();
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
			//05校级
			String instituteTypeCode = institute.getType().getCode();
			
			if (instituteTypeCode.equals("01")) {
				if (省厅资助项目编号.contains(F)) {
					granted.setApproveFeeSubjection(10.0);
					granted.setApproveFeeUniversity(10.0);
				} else {
					granted.setApproveFeeMinistry(10.0);
					granted.setApproveFeeUniversity(10.0);
				}
			} else if (instituteTypeCode.equals("02")) {
				granted.setApproveFeeMinistry(5.0);
				granted.setApproveFeeUniversity(5.0);
			} else if (instituteTypeCode.equals("04")){
				granted.setApproveFeeMinistry(10.0);
				granted.setApproveFeeUniversity(10.0);
			} else {
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
			
			if (granted.getApproveFeeMinistry() != null && granted.getApproveFeeMinistry() > 0.01) {
				InstpFunding funding = new InstpFunding();
				funding.setFee(granted.getApproveFeeMinistry());
				funding.setDate(tool.getDate(2012, 4, 1));
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
				exMsg.add("不存在的申报: " + C);
			}
		}

		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
	

	public InstpProjectGranted2012Importer(){
	}
	
	public InstpProjectGranted2012Importer(String file1, String file2) {
		reader1 = new ExcelReader(file1);
		reader2 = new ExcelReader(file2);
	}


}
