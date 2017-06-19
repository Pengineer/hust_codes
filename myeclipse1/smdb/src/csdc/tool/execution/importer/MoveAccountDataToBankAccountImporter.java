package csdc.tool.execution.importer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import csdc.bean.Agency;
import csdc.bean.BankAccount;
import csdc.bean.Person;

/**
 * 将person表和agency表中与银行账户有关的数据迁移到新建的BankAccount表
 * @author liangjian
 * 备注：（人员表里面关于银行账号的信息都是空的，本程序此次无需执行）
 */
@Component
public class MoveAccountDataToBankAccountImporter extends Importer {
	private List<Person> person = new ArrayList<Person>(); 
	
	private List<Agency> agencys = new ArrayList<Agency>(); 
	
	@Override
	protected void work() throws Throwable {
	//	movePersonAccountInfo();  //没有可迁移数据
	
		moveAgencyAccountInfo();
  	
	}
	
	public void moveAgencyAccountInfo(){ 
		initAgency();
		System.out.println(agencys.size());
		for(Agency agency : agencys){			
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			agency.setBankIds(uuid);
			if((agency.getFbankAccount() == null || "".equals(agency.getFbankAccount())) && (agency.getFbankBranch() == null || "".equals(agency.getFbankBranch()))){
				continue;  //如果银行账号和银行支行都没有数据就跳过
			}
			System.out.println(agency.getId());		
			if(agency.getFbankAccount().contains(";") && agency.getFbankBranch().contains(";")){   //多个账号一一对应
				String[] fbankAccounts = agency.getFbankAccount().split(";");
				String[] fbankBranchs = agency.getFbankBranch().split(";");
				for(int i = 0; i < fbankAccounts.length; i++){
					BankAccount bankAccount = new BankAccount();
					bankAccount.setIds(uuid);
					bankAccount.setSn(i+1);
					if(i == 0){
						bankAccount.setIsDefault(1);
					} else {
						bankAccount.setIsDefault(0);
					}
					bankAccount.setAccountName(agency.getFbankAccountName()); //开户名称
					bankAccount.setAccountNumber(fbankAccounts[i].replaceAll("\\s+", "")); //银行账号
					bankAccount.setBankName(fbankBranchs[i].replaceAll("\\s+", ""));       //开户银行+银行支行(开户银行里面没数据)
					bankAccount.setBankCupNumber(agency.getFcupNumber());     //银联行号 (此次迁移该字段无数据)
					bankAccount.setCity(agency.getCity());
					bankAccount.setProvince(agency.getProvince());
					bankAccount.setCreateDate(agency.getImportedDate());
					bankAccount.setCreateMode(2);  //本次数据转移【模式】都填2
					dao.add(bankAccount);
				}
			} else if(agency.getFbankAccount().contains(";") && !(agency.getFbankBranch().contains(";"))) { //多个账号对应一个分行
				String[] fbankAccounts = agency.getFbankAccount().split(";");
				for(int i = 0; i < fbankAccounts.length; i++){   //有几个账号就对应几条记录，但使用相同的分行
					BankAccount bankAccount = new BankAccount();
					bankAccount.setIds(uuid.toString());
					bankAccount.setSn(i+1);
					if(i == 0){
						bankAccount.setIsDefault(1);
					} else {
						bankAccount.setIsDefault(0);
					}
					bankAccount.setAccountName(agency.getFbankAccountName());
					bankAccount.setAccountNumber(fbankAccounts[i].replaceAll("\\s+", ""));
					bankAccount.setBankName(agency.getFbankBranch().replaceAll("\\s+", ""));
					bankAccount.setBankCupNumber(agency.getFcupNumber());   
					bankAccount.setCity(agency.getCity());
					bankAccount.setProvince(agency.getProvince());
					bankAccount.setCreateDate(agency.getImportedDate());
					bankAccount.setCreateMode(2);
					dao.add(bankAccount);
				}
			} else if(!(agency.getFbankAccount().contains(";")) && agency.getFbankBranch().contains(";")) {   //多个分行对应一个账号
				String[] fbankBranchs = agency.getFbankBranch().split(";");
				for(int i = 0; i < fbankBranchs.length; i++){
					BankAccount bankAccount = new BankAccount();
					bankAccount.setIds(uuid.toString());
					bankAccount.setSn(i+1);
					if(i == 0){
						bankAccount.setIsDefault(1);
					} else {
						bankAccount.setIsDefault(0);
					}
					bankAccount.setAccountName(agency.getFbankAccountName());
					bankAccount.setAccountNumber(agency.getFbankAccount().replaceAll("\\s+", "")); 
					bankAccount.setBankName(fbankBranchs[i].replaceAll("\\s+", ""));
					bankAccount.setBankCupNumber(agency.getFcupNumber());  
					bankAccount.setCity(agency.getCity());
					bankAccount.setProvince(agency.getProvince());
					bankAccount.setCreateDate(agency.getImportedDate());
					bankAccount.setCreateMode(2);
					dao.add(bankAccount);
				}
			} else { //只有一个账号和一个支行
				BankAccount bankAccount = new BankAccount();
				bankAccount.setIds(uuid.toString());
				bankAccount.setSn(1);
				bankAccount.setIsDefault(1);
				bankAccount.setAccountName(agency.getFbankAccountName());
				bankAccount.setAccountNumber(agency.getFbankAccount().replaceAll("\\s+", "")); 
				bankAccount.setBankName(agency.getFbankBranch().replaceAll("\\s+", ""));
				bankAccount.setBankCupNumber(agency.getFcupNumber());     
				bankAccount.setCity(agency.getCity());
				bankAccount.setProvince(agency.getProvince());
				bankAccount.setCreateDate(agency.getImportedDate());
				bankAccount.setCreateMode(2);
				dao.add(bankAccount);
			}
			dao.modify(agency);
		}
	}
	
	public void movePersonAccountInfo(){
	}
	
	/*
	 * 获取有银行账号信息的person数据
	 */
	public void initAgency(){
		agencys = dao.query("select a from Agency a where a.fbankAccount is not null or a.fcupNumber is not null or a.fbank is not null or a.fbankBranch is not null or a.fbankAccountName is not null");
	}
	
	/*
	 * 获取有银行账号信息的person数据
	 */
	public void initPerson(){
		person = dao.query("select p from Person p where p.bankName is not null or p.bankAccount is not null or p.cupNumber is not null or p.bankBranch is not null or p.bankAccountName is not null");
	}

}
