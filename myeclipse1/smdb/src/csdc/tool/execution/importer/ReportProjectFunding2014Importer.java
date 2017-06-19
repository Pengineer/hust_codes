package csdc.tool.execution.importer;

import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.FundingBatch;
import csdc.bean.FundingList;
import csdc.bean.Officer;
import csdc.bean.PostApplication;
import csdc.bean.PostEndinspection;
import csdc.bean.PostFunding;
import csdc.bean.PostGranted;
import csdc.bean.SystemOption;
import csdc.dao.SystemOptionDao;
import csdc.tool.execution.finder.PostProjectFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 
 * @author maowh
 * 导入《2014年第二批高等学校哲学社会科学繁荣计划专项资金划拨情况明细总表v2.xls_修正导入.xls》中的“ 发展报告项目”
 *若库中没有申请和立项数据，则需先导入申请和立项数据
 */
public class ReportProjectFunding2014Importer extends Importer{
	
	private ExcelReader reader;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private PostProjectFinder postProjectFinder;

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
		
		reader.readSheet(2);
		
		Officer 张海泽 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '张海泽'");
		
		FundingList fundingList = new FundingList();
		FundingBatch projectFundingBatch = (FundingBatch) dao.queryUnique("select fb from FundingBatch fb where fb.name = '2014年第二批高等学校哲学社会科学繁荣计划专项资金拨款'");		
		
		fundingList.setCount(50);
		fundingList.setCreateDate(tool.getDate("2014-12-23"));
		fundingList.setFee(95.8);
		fundingList.setFundingBatch(projectFundingBatch);
		fundingList.setSubSubType("end");
		fundingList.setSubType("post");
		fundingList.setRate(20.0);
		fundingList.setYear(2014);
		fundingList.setName("2014年教育部哲学社会科学发展报告项目拨款明细");
	
		dao.addOrModify(fundingList);
		
		while (next(reader)) {			
			if (A.isEmpty() || A == null) {
				break;
			}
			
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			
			PostGranted granted = postProjectFinder.findGranted(A);
			PostEndinspection endinspection = new PostEndinspection();
			endinspection.setFinalAuditDate(tool.getDate("2014-12-23"));
			endinspection.setFinalAuditor(张海泽);
			endinspection.setFinalAuditResultEnd(2);
			endinspection.setFinalAuditorName("张海泽");
			endinspection.setFinalAuditStatus(3);
			endinspection.setImportedDate(new Date());
			endinspection.setGranted(granted);
			endinspection.setStatus(7);
			
			granted.setStatus(5);
			dao.addOrModify(granted);
			
			if (G == null || Integer.valueOf(G.trim()) == 0 || "".equals(G.trim())) {
				continue;
			}
			PostFunding funding = new PostFunding();
			funding.setFundingList(fundingList);
			funding.setDate(tool.getDate(2014, 12, 23));
			funding.setFee(tool.getFee(G));
			funding.setStatus(1);
			funding.setType(3);
			granted.addFunding(funding);
			dao.addOrModify(granted);
			dao.add(funding); 
			dao.add(endinspection);			
		}
		
	}
			
				
	
	private void validate() throws Exception {
		reader.readSheet(2);
		Officer 张海泽 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '张海泽'");
		
		HashSet exMsg = new HashSet();
		
		while (next(reader)) {
			if (A.isEmpty() || A == null) {
				break;
			}
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			Agency university = universityFinder.getUnivByName(B);
			if (university == null) {
				exMsg.add("不存在的高校: " + B);
			}
			
			PostGranted granted = postProjectFinder.findGranted(A);
//			GeneralGranted granted = generalProjectFinder.findGranted(C);
			if (granted == null) {
				SystemOption 重大 = systemOptionDao.query("projectType", "032");
				PostApplication pa = postProjectFinder.findApplication(D, C);
				PostGranted pg = new PostGranted();
				
				pg.setNumber(A);
				pg.setName(D);			
				pg.setSubtype(重大);
				pg.setStatus(1);
				pg.setIsImported(1);
				pg.setApplicantId(pa.getApplicantId());
				pg.setApplicantName(pa.getApplicantName());				
				pg.setUniversity(pa.getUniversity());
				pg.setAgencyName(pa.getAgencyName());
				pg.setDepartment(pa.getDepartment());
				pg.setInstitute(pa.getInstitute());
				pg.setDivisionName(pa.getDivisionName());			
				pg.setApproveDate(pa.getFinalAuditDate());
				pa.setFinalAuditResult(2);

				//批准经费
				pg.setApproveFee(tool.getFee(F));
				pg.setApplication(pa);

				dao.add(pg);
				dao.addOrModify(pa);
				
//				exMsg.add("用项目名称加批准号不存在的立项: " + A + D);
			} else {				
				Double approveFee = granted.getApproveFee();
				if (approveFee != null) {
					Double in = Double.valueOf(F);
					if(!(approveFee.equals(in) )) {
						exMsg.add("批准经费与库中的不一致: " + D);
					}
				} else {
					granted.setApproveFee(Double.valueOf(F));
					dao.addOrModify(granted);
				}

//				Set<GeneralMidinspection> midinspections = granted.getMidinspection();
//			
//				if( midinspections == null) {
//					exMsg.add("该项目不存在结项: " + B);
//				} else if(midinspections.size() != 1){
//					int i = 0;//结项同意的数目
//					for (GeneralMidinspection midinspection : midinspections) {
//						if (midinspection.getFinalAuditResult() == 2) {
//							i++;
//						}
//					}
//					if (i != 1) {
//						exMsg.add("该项目存在多个中检同意: " + B + "数量为：" + midinspections.size());
//					}									
//				}
			}
		}	
		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
	
	public ReportProjectFunding2014Importer(){
	}
	
	public ReportProjectFunding2014Importer(String file1) {
		reader = new ExcelReader(file1);		
	}
	

}



