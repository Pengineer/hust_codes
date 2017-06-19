package csdc.tool.execution.importer;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.FundingList;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralGranted;
import csdc.bean.ProjectFunding;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.reader.ExcelReader;
/**
 * 修正ProjectFunding与FundingList的关联关系
 * 附件：《2014年第二批高等学校哲学社会科学繁荣计划专项资金划拨情况明细总表v2_修正导入.xls》中的“工程结项”
 * @author pengliang
 */
public class SpecialEndinspectionEngineerResearchFundingFix2014Importer extends Importer {

	private ExcelReader reader;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private GeneralProjectFinder generalProjectFinder;
	
	@Autowired
	private UnivPersonFinder univPersonFinder;
	
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
	
		FundingList fundingList = (FundingList) dao.queryUnique("select fl from FundingList fl where fl.name='工程科技人才培养研究结项拨款一览表' and fl.fundingBatch.name='2014年第二批高等学校哲学社会科学繁荣计划专项资金拨款'");
		
		reader.readSheet(12);
		reader.setCurrentRowIndex(1);
		while (next(reader)) {			
			if (B.isEmpty() || B == null) {
				break;
			}
			
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			
			GeneralGranted granted = generalProjectFinder.findGrantedByName(D.trim(), C.trim());
			if(granted == null){
				throw new RuntimeException();
			}
			ProjectFunding projectFunding = (ProjectFunding) dao.queryUnique("select pf from ProjectFunding pf where pf.date like '%25-12月-14%' and pf.fundingList is null and pf.grantedId=?", granted.getId());			
			projectFunding.setFundingList(fundingList);		
			dao.addOrModify(projectFunding); 
		}	
	}
	
	private void validate() throws Exception {
		System.out.println("validate()");
		reader.readSheet(12);
		
		HashSet<String> exMsg = new HashSet<String>();
		reader.setCurrentRowIndex(1);
		while (next(reader)) {
			if (B.isEmpty() || B == null) {
				break;
			}
			Agency university = universityFinder.getUnivByName(B.trim());
			if (university == null) {
				exMsg.add("不存在的高校: " + B);
			}
			int year =0;
			if(D.contains("10JD")){
				year = 2010;
			} else if (D.contains("11JD")) {
				year = 2011;
			}
			GeneralApplication application = generalProjectFinder.findApplication(C.trim(), E.trim(), year);
			if (application == null){
				exMsg.add("不存在的申请项目：" + C);
			}
		}
		if (exMsg.size() > 0) {
			System.out.println(exMsg.size());
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
	
	public SpecialEndinspectionEngineerResearchFundingFix2014Importer() {
	}
	
	public SpecialEndinspectionEngineerResearchFundingFix2014Importer(String fileName) {
		reader = new ExcelReader(fileName);
	}

	public void freeMemory(){
		univPersonFinder.reset();
		universityFinder.reset();
		generalProjectFinder.reset();
	}
}
