package csdc.tool.execution.importer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.tool.execution.importer.tool.Tool;

import csdc.bean.Agency;
import csdc.bean.AgencyFunding;
import csdc.bean.BankAccount;
import csdc.bean.SystemOption;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.reader.ExcelReader;

/**
 * 导入：《2014年第二批高等学校哲学社会科学繁荣计划专项资金拨款账号（非部属高校）_修正导入.xls》
 * @author liangjian
 *
 */
public class AgencyFundingAccount2014Importer extends Importer{

	ExcelReader excelReader;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private IHibernateBaseDao dao;
	
	@Autowired
	private Tool tool;
	
	List<BankAccount> bankAccounts = new ArrayList<BankAccount>();
	
	@Override
	protected void work() throws Throwable {
	//	validate();
	//	importData();    
		//最后一步单独执行()
		createRelate();
	}
	
	/*
	 * 将Excel中的数据导入到BankAccount表
	 */
	public void importData() throws Exception{
		excelReader.readSheet(0);
		while (next(excelReader)) {
			if (A.isEmpty()) {
				break;
			}
			if (H.isEmpty()) {
				continue;
			}
			System.out.println(excelReader.getCurrentRowIndex() + "/" + excelReader.getRowNumber());
			
			SystemOption province = null;
			SystemOption city = null;
			if (E != null && E.length() != 0) {
				province = (SystemOption) dao.queryUnique("select so from SystemOption so where so.standard = 'GBT2260-2007' and so.systemOption.id='4028d88a2d2b1df0012d2b210f190001' and so.name like ?", E + "%");
			}

			if ("马鞍".equals(F)) {
				F = "马鞍山市";
			} else if ("德宏傣族景颇族自治州".equals(F)) {
				F = "德宏傣族景颇族自治州";
			} else if ("襄阳".equals(F)) {
				F = "襄樊市";
			} else if ("甘孜".equals(F)) {
				F = "甘孜藏族自治州";
			} else if ("文山".equals(F)) {
				F = "文山壮族苗族自治州";
			} else if ("西双版纳州景洪".equals(F)) {
				F = "景洪市";
			} else if ("蒙自".equals(F)) {
				F = "蒙自县";
			} else {
				F = F + "市";
			}
			if (F != null && F.length() != 0){
				city = (SystemOption) dao.queryUnique("select so from SystemOption so where so.standard = 'GBT2260-2007' and so.name = ?", F);
			}
			
			if((city == null && F != null && F.length() != 0) || (province == null && E != null && E.length() != 0)){
				System.out.println(E + F);
				throw new RuntimeException();
			}
			
			//查找高校账号组中的账号信息，如没有，就新增一个
			Agency agency = universityFinder.getAgencyByName(H);
			boolean has = false;   //判断高校账号中是否有该条记录
			bankAccounts = (List<BankAccount>) dao.query("select ba from BankAccount ba where ba.ids = ?", agency.getBankIds());
			if(bankAccounts.size() == 0 ){  //该高校没有账号信息，直接新增
				String uuid = UUID.randomUUID().toString().replaceAll("-", "");
				agency.setBankIds(uuid);
				BankAccount bankAccount = new BankAccount();
				bankAccount.setIds(uuid.toString());
				bankAccount.setSn(1);
				bankAccount.setIsDefault(1);
				bankAccount.setAccountName(B);
				bankAccount.setAccountNumber(C); 
				bankAccount.setBankName(D);    
				bankAccount.setCity(city);
				bankAccount.setProvince(province);
				bankAccount.setCreateDate(tool.getDate(2014, 12, 25));
				bankAccount.setCreateMode(2);
				dao.add(bankAccount);
			} else {  //高校有账号信息，需判重
				for (BankAccount bankAccount : bankAccounts) {
					if(bankAccount.getAccountName().equals(B) && bankAccount.getAccountNumber().equals(C) && bankAccount.getBankName().equals(D)) {
						has = true; 
						if(bankAccount.getCity() == null){
							bankAccount.setCity(city);
						}
						if (bankAccount.getProvince() == null){
							bankAccount.setProvince(province);
						}						
						bankAccount.setUpdateDate(tool.getDate(2014, 12, 25));
						bankAccount.setCreateMode(2);
						dao.addOrModify(bankAccount);
						break;
					}
				}
				if (!has) {
					BankAccount bankAccount = new BankAccount();
					bankAccount.setIds(agency.getBankIds());
					bankAccount.setSn(bankAccounts.size() + 1);
					bankAccount.setIsDefault(0);
					bankAccount.setAccountName(B);
					bankAccount.setAccountNumber(C); 
					bankAccount.setBankName(D);    
					bankAccount.setCity(city);
					bankAccount.setProvince(province);
					bankAccount.setCreateDate(tool.getDate(2014, 12, 25));            
					bankAccount.setCreateMode(2);                     
					dao.add(bankAccount);
				}
			}
			dao.addOrModify(agency);		
		}
		System.out.println("SUCCESS!!!");
	}
	
	/*
	 * 建立AgencyFunding和BankAccount之间的关联关系
	 */
	public void createRelate() {
		int index = 0;
		Map<String,String> paraMap = new HashMap<String, String>();
		List<AgencyFunding> agencyFundings = dao.query("select af from AgencyFunding af where af.fBankAccountName is not null or af.fBankAccount is not null or af.fBank is not null");
		for (AgencyFunding agencyFunding : agencyFundings ) {
			System.out.println((++index) + "/" + agencyFundings.size());
			paraMap.put("bankName", agencyFunding.getfBank());
			paraMap.put("accountName", agencyFunding.getfBankAccountName());
			paraMap.put("accountNumber", agencyFunding.getfBankAccount());
			System.out.println(agencyFunding.getId());
			BankAccount bankAccount = (BankAccount) dao.queryUnique("select ba from BankAccount ba where ba.bankName = :bankName and ba.accountName = :accountName and ba.accountNumber = :accountNumber", paraMap);//正式库用这个，由于上面的判重用的等于符号而不是equals，导致判重失败，全部新增了一个
		//	List<BankAccount> bankAccounts = (List<BankAccount>) dao.query("select ba from BankAccount ba where ba.bankName = :bankName and ba.accountName = :accountName and ba.accountNumber = :accountNumber", paraMap);
		//	BankAccount bankAccount = bankAccounts.iterator().next();
			agencyFunding.setBankAccount(bankAccount);
			dao.modify(agencyFunding);
		}
	}
	
	private void validate() throws Exception {
		excelReader.readSheet(0);
		
		HashSet<String> exMsg = new HashSet<String>();
		while (next(excelReader)) {
			if (H.isEmpty()) {
				continue;
			}
			Agency university = universityFinder.getAgencyByName(H.replaceAll("\\s+", ""));
			if (university == null) {
				exMsg.add("不存在的高校: " + H);
			}
		}

		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}

	}
	
	public AgencyFundingAccount2014Importer(){
		
	}
	
	public AgencyFundingAccount2014Importer(String filePath){
		excelReader = new ExcelReader(filePath);
	}

}
