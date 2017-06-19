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
 * 导入：《2014年第二批高等学校哲学社会科学繁荣计划专项资金划拨情况明细总表v2_修正导入.xls》中的“经费重拨高校名单”
 * @author liangjian
 * 
 */
public class WorkFeeSupplementFunding2014Importer extends Importer {
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
		freeMemory();
		validate();
		importData();
		freeMemory();
	}
	
	/*
	 * 导入拨款清单数据（批次的创建只出现在同一批的的一个java文件中）
	 */
	public void importData() throws Throwable {
		FundingBatch projectFundingBatch = (FundingBatch) dao.queryUnique("select fb from FundingBatch fb where fb.name = ?", batchName);		
		
		FundingList fundingList = new FundingList();
		fundingList.setCreateDate(tool.getDate(2014, 12, 25));
		fundingList.setName("2014年9月第一批高等学校哲学社会科学繁荣计划专项资金划拨经费（未到账重拨）拨款一览表");
		fundingList.setCount(12);
		fundingList.setStatus(1);
		fundingList.setFee(412.5000);
		fundingList.setType("work");
		fundingList.setSubType("other");
		fundingList.setYear(2014);
		fundingList.setFundingBatch(projectFundingBatch);
		dao.add(fundingList);
		
		reader.readSheet(10);
		reader.setCurrentRowIndex(1);
		while(next(reader)){
			if (B.isEmpty() || B == null) {
				break;
			}
			if(H == null || Integer.parseInt(H.trim())==0 || "".equals(H.trim())){
				continue;
			}
			System.out.println(B);

			Agency university = universityFinder.getUnivByName(B.trim());
			//（AgencyFunding表用来存放各高校在本批次的总拨款数）判定该高校在本批次中是否有导入过，若没有就添加
			AgencyFunding agencyFunding = agencyFundingFinder.getAgencyFunding(university.getName(), batchName);
			if(agencyFunding == null){
				agencyFunding = new AgencyFunding();
				agencyFundingFinder.agencyFundingMap.put(university.getName() + batchName, agencyFunding);//agencyFundingMap是一个静态的Map，用来存放本批次的高校拨款情况
				agencyFunding.setAgency(university);
				agencyFunding.setAgencyName(university.getName());
				agencyFunding.setFundingBatch(projectFundingBatch);
				dao.add(agencyFunding);
			}
			WorkFunding workFunding = new WorkFunding();
			workFunding.setDate(tool.getDate(2014, 12, 25));
			workFunding.setFee(Double.parseDouble(H.trim())/10000);
			workFunding.setName("2014年9月第一批高等学校哲学社会科学繁荣计划专项资金划拨经费（未到账重拨）");
			workFunding.setType("other");
			workFunding.setAgency(university);
			workFunding.setAgencyName(university.getName());
			workFunding.setPayee(university.getName());
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
		reader.readSheet(10);
		reader.setCurrentRowIndex(1);
		while(next(reader)){
			if (B.isEmpty() || B == null) {
				break;
			}
			Agency universityFindByName = universityFinder.getUnivByName(B.trim());
			if (universityFindByName == null) { 
				exMsg.add("不存在的高校名称: " + B.trim());				
			}
		}
		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
	
	public void freeMemory(){
		universityFinder.reset();
	}

	public WorkFeeSupplementFunding2014Importer() {
	}
	
	public WorkFeeSupplementFunding2014Importer(String fileName) {
		reader = new ExcelReader(fileName);
	}

}
