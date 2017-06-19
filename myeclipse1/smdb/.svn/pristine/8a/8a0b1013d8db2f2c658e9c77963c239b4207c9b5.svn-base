package csdc.tool.execution.importer;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.AgencyFunding;
import csdc.tool.StringTool;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.execution.finder.AgencyFundingFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 导入：《2014年第二批高等学校哲学社会科学繁荣计划专项资金拨款账号（非部属高校）_修正导入.xls》
 * @author maowh
 * 备注：已将账号信息导入AgencyFunding表，但未导入Agency表（可能要新建账号表）
 */
public class AgencyFunding2014Importer extends Importer {

	private ExcelReader excelReader;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private BeanFieldUtils beanFieldUtils;
	
	@Autowired
	private AgencyFundingFinder agencyFundingFinder;
	
	public AgencyFunding2014Importer() {}
	
	public AgencyFunding2014Importer(String filePath) {
		excelReader = new ExcelReader(filePath);
	}
	

	@Override
	public void work() throws Exception {
//		validate();
		importData();
	}

	private void validate() throws Exception {
		excelReader.readSheet(0);
		
		HashSet exMsg = new HashSet();
		while (next(excelReader)) {
			if (H.isEmpty()) {
				continue;
			}
			Agency university = universityFinder.getAgencyByName(H.trim());
			if (university == null) {
				exMsg.add("不存在的高校: " + H);
			}
			AgencyFunding agencyFunding = agencyFundingFinder.getAgencyFundingByAgencyName(H.trim());	
			if (agencyFunding == null) {
				//TODO
				exMsg.add("AgencyFunding不存在的高校: " + H);			
			}
		}

		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}

	}

	/**
	 * 导入数据并进行查重
	 * @throws Exception 
	 */
	/**
	 * @throws Exception
	 */
	private void importData() throws Exception {
		excelReader.readSheet(0);		
		while (next(excelReader)) {
			if (A.isEmpty()) {
				break;
			}
			if (H.isEmpty()) {
				continue;
			}
			System.out.println(excelReader.getCurrentRowIndex() + "/" + excelReader.getRowNumber());
			//导入Agency
//			Agency agency = universityFinder.getUnivByName(StringTool.fix(H.trim()));
//			if (agency == null) {
//				//TODO
//				 System.out.println("agencyError___" + B.trim());
//			}else {
//				beanFieldUtils.setField(agency, "fbankAccount", C, BuiltinMergeStrategies.PREPEND);
//				beanFieldUtils.setField(agency, "fbankAccountName", B, BuiltinMergeStrategies.PREPEND);
//				beanFieldUtils.setField(agency, "fbank", D, BuiltinMergeStrategies.PREPEND);
//				dao.addOrModify(agency);
//			}
			
			//导入AgencyFunding
			AgencyFunding agencyFunding = agencyFundingFinder.getAgencyFundingByAgencyName(StringTool.fix(H.trim()));	
			if (agencyFunding == null ) {
				//TODO
				System.out.println("agencyFundingError___" + B.trim());				
			}else {
//				beanFieldUtils.setField(agencyFunding, "fBankAccount", C, BuiltinMergeStrategies.PREPEND);
//				beanFieldUtils.setField(agencyFunding, "fBankAccountName", B, BuiltinMergeStrategies.PREPEND);
//				beanFieldUtils.setField(agencyFunding, "fBank", D, BuiltinMergeStrategies.PREPEND);
				agencyFunding.setfBank(D);
				agencyFunding.setfBankAccount(C);
				agencyFunding.setfBankAccountName(B);
				dao.addOrModify(agencyFunding);
			}
		}
		System.out.println("ok");
	}
}
