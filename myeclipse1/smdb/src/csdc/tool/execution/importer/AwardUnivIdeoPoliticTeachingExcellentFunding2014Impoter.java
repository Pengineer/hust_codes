package csdc.tool.execution.importer;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.AgencyFunding;
import csdc.bean.FundingBatch;
import csdc.bean.FundingList;
import csdc.bean.Teacher;
import csdc.bean.WorkFunding;
import csdc.tool.execution.finder.AgencyFundingFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 导入：《2014年第二批高等学校哲学社会科学繁荣计划专项资金划拨情况明细总表v2_修正导入.xls》中的“思政课教学能手奖励”
 * @author pengliang
 *
 */

public class AwardUnivIdeoPoliticTeachingExcellentFunding2014Impoter extends Importer{

	ExcelReader reader;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private UnivPersonFinder univPersonFinder;
	
	@Autowired
	private AgencyFundingFinder agencyFundingFinder;
	
	@Autowired
	private Tool tool;
	
	private String batchName = "2014年第二批高等学校哲学社会科学繁荣计划专项资金划拨情况";
	
	@Override
	protected void work() throws Throwable {
		validate();
		importData();
	}
	
	/*
	 * 导入拨款清单数据（批次的创建只出现在同一批的的一个java文件中）
	 */
	public void importData() throws Throwable {
		FundingBatch projectFundingBatch = (FundingBatch) dao.queryUnique("select fb from FundingBatch fb where fb.name = ?", batchName);		
		
		FundingList fundingList = new FundingList();
		fundingList.setCreateDate(tool.getDate(2014, 12, 25));
		fundingList.setName("2014年“全国高校思想政治理论课教学能手”奖励拨款一览表");
		fundingList.setCount(49);
		fundingList.setStatus(1);
		fundingList.setFee(24.5);
		fundingList.setType("work");
		fundingList.setSubType("award");
		fundingList.setYear(2014);
		fundingList.setFundingBatch(projectFundingBatch);
		dao.add(fundingList);
		
		reader.readSheet(8);
		reader.setCurrentRowIndex(1);
		while(next(reader)){
			if (B.isEmpty() || B == null) {
				break;
			}
			if(C == null || Double.parseDouble(C.trim())==0 || "".equals(C.trim())){
				continue;
			}
			System.out.println(B + "::" + A);
			String personName = tool.getName(A.replaceAll("\\s+", ""));
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
			
			Teacher teacher = univPersonFinder.findTeacher(personName, university);
			WorkFunding workFunding = new WorkFunding();
			workFunding.setDate(tool.getDate(2014, 12, 25));
			workFunding.setFee(Double.parseDouble(C));
			workFunding.setName("2014年“全国高校思想政治理论课教学能手”奖励金额");
			workFunding.setType("award");
			workFunding.setPerson(teacher.getPerson());
			workFunding.setPayee(personName);
			workFunding.setAgency(university);
			workFunding.setAgencyName(university.getName());
			workFunding.setDepartment(teacher.getDepartment());
			workFunding.setInstitute(teacher.getInstitute());
			workFunding.setFundingList(fundingList);
			workFunding.setAgencyFunding(agencyFunding);
			dao.add(workFunding);
		}
		
		freeMemory();
	}
	
	/**
	 * 校验学校是否存在 
	 */
	public void validate() throws Exception{
		HashSet<String> exMsg = new HashSet<String>();
		reader.readSheet(8);
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

	public AwardUnivIdeoPoliticTeachingExcellentFunding2014Impoter() {
	}
	
	public AwardUnivIdeoPoliticTeachingExcellentFunding2014Impoter(String fileName) {
		reader = new ExcelReader(fileName);
	}
	
	public void freeMemory(){
		univPersonFinder.reset();
	}

}
