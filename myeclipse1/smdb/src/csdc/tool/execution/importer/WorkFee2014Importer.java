package csdc.tool.execution.importer;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.AgencyFunding;
import csdc.bean.FundingBatch;
import csdc.bean.FundingList;
import csdc.bean.WorkFunding;
import csdc.tool.execution.finder.AgencyFundingFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 导入：《2014年第二批高等学校哲学社会科学繁荣计划专项资金划拨情况明细总表v2_修正导入.xls》中的“工作经费”
 * @author pengliang
 *
 */

public class WorkFee2014Importer extends Importer {

	ExcelReader reader;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private AgencyFundingFinder agencyFundingFinder;
	
	@Autowired
	private Tool tool;
	
	private String batchName = "2014年第二批高等学校哲学社会科学繁荣计划专项资金划拨情况";
	
	@Override
	protected void work() throws Throwable {
	//	validate();
		importData();
	}
	
	/*
	 * 导入拨款清单数据（批次的创建只出现在同一批的的一个java文件中）
	 */
	public void importData() throws Exception {
		FundingBatch projectFundingBatch = (FundingBatch) dao.queryUnique("select fb from FundingBatch fb where fb.name = ?", batchName);		
		
		FundingList fundingList = new FundingList();
		fundingList.setCreateDate(tool.getDate(2014, 12, 25));
		fundingList.setName("工作经费");
		fundingList.setCount(13);
		fundingList.setStatus(1);
		fundingList.setFee(1197.12706);
		fundingList.setType("work");
	//	fundingList.setSubType("work");  同一个FundingList的子类类型有多个时，不填写
		fundingList.setYear(2014);
		fundingList.setFundingBatch(projectFundingBatch);
		dao.add(fundingList);
		
		reader.readSheet(16);
		reader.setCurrentRowIndex(1);
		while(next(reader)){
			if (A.isEmpty() || A == null) {
				break;
			}
			if(C == null || Double.parseDouble(C.trim())==0 || "".equals(C.trim())){
				continue;
			}
			System.out.println(A);
			Agency agency = universityFinder.getUnivByName(A.trim());
			//（AgencyFunding表用来存放各高校在本批次的总拨款数）判定该高校在本批次中是否有导入过，若没有就添加
			AgencyFunding agencyFunding = agencyFundingFinder.getAgencyFunding(agency.getName(), batchName);
			if(agencyFunding == null){
				agencyFunding = new AgencyFunding();
				agencyFundingFinder.agencyFundingMap.put(agency.getName() + batchName, agencyFunding);//agencyFundingMap是一个静态的Map，用来存放本批次的高校拨款情况
				agencyFunding.setAgency(agency);
				agencyFunding.setAgencyName(agency.getName());
				agencyFunding.setFundingBatch(projectFundingBatch);
				dao.add(agencyFunding);
			}
			WorkFunding workFunding = new WorkFunding();
			workFunding.setDate(tool.getDate(2014, 12, 25));
			System.out.println(C);
			System.out.println(Double.parseDouble(C.trim()));
			workFunding.setFee(Double.parseDouble(C.trim()));
			workFunding.setName(B.trim());  //名称在库里面已更改（以后的导入可参考）
			workFunding.setType(E);
			workFunding.setAgency(agency);
			workFunding.setAgencyName(agency.getName());
			workFunding.setPayee(agency.getName());
			workFunding.setFundingList(fundingList);
			workFunding.setAgencyFunding(agencyFunding);
			dao.add(workFunding);
		}
	}
	/**
	 * 校验学校是否存在 
	 */
	public void validate() throws Exception{
		HashSet<String> exMsg = new HashSet<String>();
		reader.readSheet(16);
		reader.setCurrentRowIndex(1);
		while(next(reader)){
			Agency universityFindByName = universityFinder.getUnivByName(A.trim());
			if (universityFindByName == null) { 
				exMsg.add("不存在的高校名称: " + A.trim());	
			}
		}
		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}

	public WorkFee2014Importer() {
	}
	
	public WorkFee2014Importer(String fileName) {
		reader = new ExcelReader(fileName);
	}

}
