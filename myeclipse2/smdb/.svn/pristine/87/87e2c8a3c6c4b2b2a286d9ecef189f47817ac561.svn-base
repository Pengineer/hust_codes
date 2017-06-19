package csdc.tool.execution;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Account;
import csdc.bean.AccountRole;
import csdc.bean.Agency;
import csdc.bean.Role;
import csdc.dao.HibernateBaseDao;
import csdc.service.IBaseService;
import csdc.tool.MD5;
import csdc.tool.PinyinCommon;;

@SuppressWarnings("unchecked")
public class CreatAccount extends Execution{
	
	@Autowired
	protected HibernateBaseDao dao;

	@Override
	public void work() throws Throwable {
		//以下添加校级账号
		addUniversityAccount();
		addProvinceAccount();
		//deleteUniversityAccount();
	}
	
	/*
	 * 添加校级账号，同时分配角色
	 */
	private void addUniversityAccount(){
		List<Agency> universities = dao.query("select ag from Agency ag where (ag.type=3 or ag.type=4) order by ag.code desc ");
		for(Agency university : universities){
			if(university.getCode().equals("99999") || university.getCode().equals("10487")){
				System.out.println(university.getCode());
			} else {
				Account newAccount = new Account();
				String name = university.getCode();
				if(!name.equals("test1")){
					String password = "123456";
					Date startDate = new Date();
					
					GregorianCalendar calendar = new GregorianCalendar();
					calendar.setTime(startDate);
					calendar.add(GregorianCalendar.YEAR, 1);
					
					int type = university.getType() + 1;
					
					newAccount.setName(name);
					newAccount.setPassword(MD5.getMD5(password));
					newAccount.setStartDate(startDate);
					newAccount.setExpireDate(calendar.getTime());
					newAccount.setType(type);
					newAccount.setIsPrincipal(1);
					newAccount.setMaxSession(5);
					newAccount.setStatus(1);
					newAccount.setBelongId(university.getId());
					newAccount.setPasswordWarning(1);
					
					String accountId = (String)dao.add(newAccount);//添加账号
					
					AccountRole basicAccountRole = new AccountRole();
					AccountRole universityAccountRole = new AccountRole();
					
					Role basicRole = (Role) dao.query(Role.class, "f31c4a822fd8c4a2012ffbebe8e509a0");
					Role ministryuniversityRole = (Role) dao.query(Role.class, "4028d8942d5a0133012d5a0a407e0005");
					Role localUniversityRole = (Role) dao.query(Role.class, "4028d8942d5a0133012d5a0ab2720007");
					
					basicAccountRole.setRole(basicRole);
					basicAccountRole.setAccount(newAccount);
					dao.add(basicAccountRole);//分配基本角色
					
					if(type == 4){
						universityAccountRole.setRole(ministryuniversityRole);
					}
					if(type == 5){
						universityAccountRole.setRole(localUniversityRole);
					}
					universityAccountRole.setAccount(newAccount);
					dao.add(universityAccountRole);//部署/地方高校主管理员角色
					
					dao.modify(university);
				}
			}
		}
	}
	
	private void addProvinceAccount(){
		List<Agency> provinces = dao.query("select ag from Agency ag where ag.type=2 ");
		for(Agency province : provinces){
			Account newAccount = new Account();
			String provName = province.getName();
			if(!provName.startsWith("[") && !provName.startsWith("1")){
				String name = "";
				if(provName.startsWith("陕西")){
					name = "shaanxi";
				} else if (provName.startsWith("新疆维吾尔自治区教育厅")) {
					name = "xinjiang_jyt";
				} else if (provName.startsWith("新疆维吾尔自治区生产建设兵团教育局")) {
					name = "xinjiang_bt";
				} else {
					name = PinyinCommon.getPinYin(provName.substring(0, 2));
				}
				System.out.println(name);
				String password = "123456";
				Date startDate = new Date();
				
				GregorianCalendar calendar = new GregorianCalendar();
				calendar.setTime(startDate);
				calendar.add(GregorianCalendar.YEAR, 1);
				
				int type = province.getType() + 1;
				
				newAccount.setName(name);
				newAccount.setPassword(MD5.getMD5(password));
				newAccount.setStartDate(startDate);
				newAccount.setExpireDate(calendar.getTime());
				newAccount.setType(type);
				newAccount.setIsPrincipal(1);
				newAccount.setMaxSession(5);
				newAccount.setStatus(1);
				newAccount.setBelongId(province.getId());
				newAccount.setPasswordWarning(1);
				
				String accountId = (String)dao.add(newAccount);//添加账号
				
				AccountRole basicAccountRole = new AccountRole();
				AccountRole provinceAccountRole = new AccountRole();
				
				Role basicRole = (Role) dao.query(Role.class, "f31c4a822fd8c4a2012ffbebe8e509a0");
				Role provinceRole = (Role) dao.query(Role.class, "4028d8942d5a0133012d5a09b3900003");
				
				basicAccountRole.setRole(basicRole);
				basicAccountRole.setAccount(newAccount);
				dao.add(basicAccountRole);//分配基本角色
				
				provinceAccountRole.setRole(provinceRole);
				provinceAccountRole.setAccount(newAccount);
				dao.add(provinceAccountRole);//省级主管理员角色
				
				dao.modify(province);
			}
		}
	}
	
	/*
	 * 删除校级账号
	 */
	@SuppressWarnings("unused")
	private void deleteUniversityAccount(){
		List<Account> accounts = dao.query("select ac from Account ac where (ac.type = 'MINISTRY_UNIVERSITY' or ac.type= 'LOCAL_UNIVERSITY') ");
		for(Account account:accounts){
			if(account.getName().startsWith("univ_")){
				dao.delete(account);
			}
		}
	}



}
