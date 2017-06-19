package csdc.tool.execution.importer;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.GeneralApplication;
import csdc.bean.InstpApplication;
import csdc.bean.InstpFunding;
import csdc.bean.InstpGranted;
import csdc.bean.Institute;
import csdc.bean.Officer;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 导入《2014年基地项目立项及拨款一览表_修正导入.xls》
 *
 * @author pengliang
 */
public class InstpProjectGranted2014Importer extends Importer {
	
	private ExcelReader reader;
	
	public final static int YEAR = 2014;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private InstpProjectFinder instpProjectFinder;
	
	/*
	 * 本年度一般申请项目总数
	 */
	private List<InstpApplication> instpApplications;
	
	/*
	 * 本年度不立项的一般申请项目
	 */
	private List<InstpApplication> ungrantedInstpApplications;

	@Autowired
	private Tool tool;
	
	private void resetReader() throws Exception {
		reader.readSheet(0);
	}
	
	@Override
	public void work() throws Exception {
		resetReader();
		validate();
		ungrantedInstpApplication();
		importData();
	}

	private void importData() throws Exception {		
		Officer 白晓 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '白晓'");
		
		System.out.println("开始导入。。。。");
		
		reader.setCurrentRowIndex(0);
		while (next(reader)) {
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			
			String[] directorNames = D.split("；");
			for (String string : directorNames) {
				string.charAt(0);
			}

			InstpApplication application = instpProjectFinder.findApplication(C, directorNames[0].replaceAll("\\s+", ""));
			InstpGranted granted = null;
			Agency university = universityFinder.getUnivByName(A);
			Institute institute = application.getInstitute();
			
			if (application.getInstpGranted().isEmpty()) {
				granted = new InstpGranted();
				granted.setApproveDate(tool.getDate(2014, 7, 25));
				granted.setImportedDate(new Date());
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
			granted.setNumber(G);
			
			granted.setProductType(application.getProductType());
			granted.setProductTypeOther(application.getProductTypeOther());
			
			granted.setPlanEndDate(application.getPlanEndDate());
			
			//批准经费
			granted.setApproveFeeMinistry(Double.parseDouble(J));
			granted.setApproveFee(Double.parseDouble(I));
			
			//存在教育部拨款信息且金额不为0时，导入拨款
			if (granted.getApproveFeeMinistry() != null && granted.getApproveFeeMinistry() > 0.01) {
				InstpFunding funding = new InstpFunding();
				funding.setFee(granted.getApproveFeeMinistry());
				funding.setDate(tool.getDate(2014, 7, 28));
				funding.setStatus(1);
//				funding.setType(1); 从15年开始，增加的拨款类型字段
				granted.addFunding(funding);
			}
			
			granted.getApplication().setFinalAuditResult(2);
			granted.getApplication().setFinalAuditStatus(3);
			granted.getApplication().setFinalAuditor(白晓);
			granted.getApplication().setFinalAuditorName(白晓.getPerson().getName());
			granted.getApplication().setFinalAuditorAgency(白晓.getAgency());
			granted.getApplication().setFinalAuditorInst(白晓.getInstitute());
			granted.getApplication().setFinalAuditDate(tool.getDate(2014, 7, 25));
		}
		
		//不立项项目(如果采用自动化入库代码，就不执行此部分)
		for(InstpApplication ungrantedInstpApplication : ungrantedInstpApplications){
			ungrantedInstpApplication.setFinalAuditResult(1);
			ungrantedInstpApplication.setFinalAuditStatus(3);
			ungrantedInstpApplication.setFinalAuditor(白晓);
			ungrantedInstpApplication.setFinalAuditorName(白晓.getPerson().getName());
			ungrantedInstpApplication.setFinalAuditorAgency(白晓.getAgency());
			ungrantedInstpApplication.setFinalAuditorInst(白晓.getInstitute());
			ungrantedInstpApplication.setFinalAuditDate(tool.getDate(2014, 7, 25));
		}	
		
	}

	
	private void validate() throws Exception {		
		HashSet exMsg = new HashSet();
		while (next(reader)) {
			Agency university = universityFinder.getUnivByName(A);
			if (university == null) {
				exMsg.add("不存在的高校: " + A);
			}
			String[] directorNames = D.split("；");
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
	
	/**
	 * 获取本年度不立项的一般项目：没有出现在Excel中的就设置为不立项
	 * @throws Exception 
	 */
	public void ungrantedInstpApplication() throws Exception{
		instpApplications = dao.query("select pa from ProjectApplication pa where pa.type = 'instp' AND pa.year = ?",YEAR);
		System.out.println("本年度申请的一般项目共有：" + instpApplications.size() + "条");
		reader.setCurrentRowIndex(0);
		while(next(reader)){
			String[] directorNames = D.split("；");
			for (String string : directorNames) {
				string.charAt(0);
			}
			InstpApplication application = instpProjectFinder.findApplication(C, directorNames[0].replaceAll("\\s+", ""));
			instpApplications.remove(application);
		}
		ungrantedInstpApplications = instpApplications;
		System.out.println("本年度不立项的一般项目共有：" + ungrantedInstpApplications.size() + "条");
	}
	

	public InstpProjectGranted2014Importer(){
	}
	
	public InstpProjectGranted2014Importer(String file) {
		reader = new ExcelReader(file);
		
	}


}
