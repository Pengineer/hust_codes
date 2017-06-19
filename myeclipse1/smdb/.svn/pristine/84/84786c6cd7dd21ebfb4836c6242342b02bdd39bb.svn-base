package csdc.tool.execution.importer;

import java.util.HashSet;

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
 * 导入：《2014年第二批高等学校哲学社会科学繁荣计划专项资金划拨情况明细总表v2_修正导入.xls》中的“智库专刊奖励”
 * @author pengliang
 *
 */
public class AwardBySpecialIssueFunding2014Importer extends Importer {

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
		FundingBatch projectFundingBatch = (FundingBatch) dao.queryUnique("select fb from FundingBatch fb where fb.name = ?",batchName);		
		
		FundingList fundingList = new FundingList();
		fundingList.setCreateDate(tool.getDate(2014, 12, 25));
		fundingList.setName("2014年《教育部简报（大学智库专刊）》奖励拨款一览表");
		fundingList.setCount(39);
		fundingList.setStatus(1);
		fundingList.setFee(32.0);
		fundingList.setType("work");
		fundingList.setSubType("award");
		fundingList.setYear(2014);
		fundingList.setFundingBatch(projectFundingBatch);
		dao.add(fundingList);
		
		reader.readSheet(15);
		reader.setCurrentRowIndex(1);
		while(next(reader)){
			if (B.isEmpty() || B == null) {
				break;
			}
			if(E == null || Double.parseDouble(E.trim())==0 || "".equals(E.trim())){
				continue;
			}
			System.out.println(C);
			String personName = tool.getName(C.replaceAll("\\s+", ""));
			if(B.trim().equals("教育部")) {
				Agency agency = (Agency) dao.queryUnique("select o from Agency o where o.name='教育部'");
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
				workFunding.setFee(Double.parseDouble(E.trim()));
				workFunding.setName(F);
				workFunding.setType("award");
				workFunding.setAgency(agency);
				workFunding.setAgencyName(agency.getName());
				workFunding.setPayee(C);
				workFunding.setNote(D.trim());
				workFunding.setFundingList(fundingList);
				workFunding.setAgencyFunding(agencyFunding);
				dao.add(workFunding);
			} else {
				Agency university = universityFinder.getUniversityWithLongestName(B.trim());
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
				workFunding.setFee(Double.parseDouble(E.trim()));
				workFunding.setName(F);
				workFunding.setType("award");
				workFunding.setPerson(teacher.getPerson());
				workFunding.setPayee(personName);
				workFunding.setAgency(university);
				workFunding.setAgencyName(university.getName());
				workFunding.setDepartment(teacher.getDepartment());
				workFunding.setInstitute(teacher.getInstitute());
				workFunding.setNote(D.trim());
				workFunding.setFundingList(fundingList);
				workFunding.setAgencyFunding(agencyFunding);
				dao.add(workFunding);
			}
		}
		freeMemory();
	}
	
	/**
	 * 校验学校是否存在 
	 */
	public void validate() throws Exception{
		HashSet<String> exMsg = new HashSet<String>();
		reader.readSheet(15);
		reader.setCurrentRowIndex(1);
		while(next(reader)){
			if (B.isEmpty() || B == null) {
				break;
			}
			Agency universityFindByName = universityFinder.getUniversityWithLongestName(B.trim());
			if (universityFindByName == null) { 
				if(B.trim().equals("教育部")){
					continue;
				}
				exMsg.add("不存在的高校: " + B.trim());				
			}
		}
		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}

	public AwardBySpecialIssueFunding2014Importer() {
	}
	
	public AwardBySpecialIssueFunding2014Importer(String fileName) {
		reader = new ExcelReader(fileName);
	}

	public void freeMemory(){
		univPersonFinder.reset();
	}

}
