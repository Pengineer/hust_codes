package csdc.tool.execution.importer;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.FundingList;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralGranted;
import csdc.bean.ProjectFunding;
import csdc.tool.execution.finder.AcademicFinder;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.reader.ExcelReader;
/**
 * 修正ProjectFunding与FundingList的关联关系
 * 附件：《2014年第二批高等学校哲学社会科学繁荣计划专项资金划拨情况明细总表v2_修正导入.xls》中的“马三化结项”
 * @author pengliang
 */
public class SpecialEndinspectionMarxFundingFix2014Importer extends Importer {
	
	private ExcelReader reader;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private GeneralProjectFinder generalProjectFinder;
	
	@Autowired
	private UnivPersonFinder univPersonFinder;
	
	@Autowired
	private AcademicFinder academicFinder;
	
	@Override
	protected void work() throws Throwable {
		freeMemory();
		System.out.println("start......");
		validate(); 
		importData();
		freeMemory();
	}
	
	private void importData() throws Exception {
		System.out.println("importData()");
		FundingList fundingList = (FundingList) dao.queryUnique("select fl from FundingList fl where fl.name='2014年度教育部人文社会科学研究专项任务项目（马克思主义中国化时代化大众化）结项拨款一览表' and fl.fundingBatch.name='2014年第二批高等学校哲学社会科学繁荣计划专项资金拨款'");
				
		reader.readSheet(14);
		reader.setCurrentRowIndex(1);
		while (next(reader)) {			
			if (A.isEmpty() || A == null) {
				break;
			}
			
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			
			GeneralGranted granted = generalProjectFinder.findGrantedByName(B.trim(), D.trim());
			if(granted == null){
				throw new RuntimeException();
			}
			ProjectFunding projectFunding = (ProjectFunding) dao.queryUnique("select pf from ProjectFunding pf where pf.date like '%25-12月-14%' and pf.fundingList is null and pf.grantedId=?", granted.getId());			
			projectFunding.setFundingList(fundingList);		
			dao.addOrModify(projectFunding); 
		}
	}
	
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
	
	public SpecialEndinspectionMarxFundingFix2014Importer() {
	}
	
	public SpecialEndinspectionMarxFundingFix2014Importer(String fileName) {
		reader = new ExcelReader(fileName);
	}
	
	public void freeMemory(){
		generalProjectFinder.reset();
		universityFinder.reset();
		univPersonFinder.reset();
		academicFinder.reset();
	}
}
