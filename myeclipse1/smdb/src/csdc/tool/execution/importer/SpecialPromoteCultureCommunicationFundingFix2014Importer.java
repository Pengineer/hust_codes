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
 * 附件：《2014年第二批高等学校哲学社会科学繁荣计划专项资金划拨情况明细总表v2_修正导入.xls》中的“推进中外人文交流合作研究专项课题立项拨款一览表”
 * @author pengliang
 */
public class SpecialPromoteCultureCommunicationFundingFix2014Importer extends Importer {

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
		System.out.println("start.......");
		validate();
		importData();
		freeMemory();
	}
	
	private void importData() throws Exception {
		System.out.println("importData()");
		FundingList fundingList = (FundingList) dao.queryUnique("select fl from FundingList fl where fl.name='推进中外人文交流合作研究专项课题立项拨款一览表' and fl.fundingBatch.name='2014年第二批高等学校哲学社会科学繁荣计划专项资金拨款'");
		
		reader.readSheet(11);
		reader.setCurrentRowIndex(1);
		while (next(reader)) {			
			if (C.isEmpty() || C == null) {
				break;
			}
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			
			GeneralGranted granted = generalProjectFinder.findGrantedByName(B.trim(), C.trim());
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
		reader.readSheet(11);
		
		HashSet<String> exMsg = new HashSet<String>();
		reader.setCurrentRowIndex(1);
		while (next(reader)) {
			if (C.isEmpty() || C == null) {
				break;
			}
			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			Agency university = universityFinder.getUnivByName(D.trim());
			if (university == null) {
				exMsg.add("不存在的高校: " + D);
			}
			GeneralApplication application = generalProjectFinder.findApplication(C.trim(), E.trim(), 2014);
			if (application == null){
				exMsg.add("不存在的申请项目：" + A + C);
			}
		}
		if (exMsg.size() > 0) {
			System.out.println(exMsg.size());
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
	
	public SpecialPromoteCultureCommunicationFundingFix2014Importer(){}
	
	public SpecialPromoteCultureCommunicationFundingFix2014Importer(String fileName){
		reader = new ExcelReader(fileName);
	}
	
	public void freeMemory(){
		generalProjectFinder.reset();
		universityFinder.reset();
		univPersonFinder.reset();
	}
}
