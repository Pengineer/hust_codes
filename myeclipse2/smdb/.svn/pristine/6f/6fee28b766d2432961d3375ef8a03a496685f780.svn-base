package csdc.service.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Account;
import csdc.bean.Log;
import csdc.bean.Message;
import csdc.bean.News;
import csdc.bean.Notice;
import csdc.bean.Passport;
import csdc.bean.Role;
import csdc.service.IPassportService;

public class PassportService extends BaseService implements IPassportService {
	public void deletePassport(List<String> passportIds){
		if (passportIds != null && !passportIds.isEmpty()){
			Passport passport;
			for(String ppid: passportIds){
				passport = (Passport) dao.query(Passport.class, ppid);
				String hql="select a.id from Account a where a.passport.id =:ppid";
				Map paraMap = new HashMap();
				paraMap.put("ppid", passport.getId());
				List<String> accountIds = dao.query(hql, paraMap);
				if (accountIds != null && !accountIds.isEmpty()) {// 如果账号ID非空，则需要删除账号
					Account account;// 账号对象
					Passport passport1;// 账号所属通行证
					for (String id : accountIds) {// 遍历账号所有账号，分别删除
						account = (Account) dao.query(Account.class, id);// 查询账号
						passport1 = (Passport) dao.query(Passport.class, account.getPassport().getId());
						String hql1="select a.id from Account a where a.passport.id =:pid";
						Map paraMap1 = new HashMap();
						paraMap1.put("pid", account.getPassport().getId());
						List list = dao.query(hql1, paraMap1);
						// 删除账号时
						// 删除账号创建的角色
						// 删除账号角色对应关系;删除该账号创建的角色; 该账号创建邮件的账号信息置空(邮件表增加账号所属机构或人员字段)
						// 该账号创建新闻的账号信息置空(新闻表增加账号所属机构或人员字段); 该账号创建通知的账号信息置空(通知表增加账号所属机构或人员字段)
						// 该账号留言的账号信息置空; 
						// 该账号日志的accountId和passportId置空(是删除的通行证，所以日志的passportId也要置空)
						List<String> roleIds = dao.query("select r.id from Role r where r.account.id = ?", id);//角色
						for (String roleId : roleIds) {
							dao.delete(Role.class, roleId);
						} 
						List mesId = dao.query("select m.id from Message m where m.account.id = ?", id);
						if (mesId.size() > 0) {
							Message message = (Message) dao.query("select m from Message m where m.account.id = ?", id);//留言簿
							if (null != message) {
								message.setAccount(null);
								dao.modify(message);
							}
						}
						List newId = dao.query("select n.id from News n where n.account.id = ?", id);
						if (newId.size() > 0) {
							News news = (News) dao.query("select n from News n where n.account.id = ?", id).get(0);//新闻
							if (null != news) {
								news.setAccount(null);
								dao.modify(news);
							}
						}
						List noticeId = dao.query("select no.id from Notice no where no.account.id = ?", id);
						if (noticeId.size() > 0) {
							Notice notice  = (Notice) dao.query("select no from Notice no where no.account.id = ?", id).get(0);//通知
							
							if (null != notice) {
								notice.setAccount(null);
								dao.modify(notice);
							}
						}
						paraMap1.put("accountId", id);
						List<String> logIds = dao.query("select l.id from Log l where l.account.id = :accountId or l.passport.id = :pid ", paraMap1);
						if (logIds.size() > 0) {
							for (String logId : logIds) {
								Log log = dao.query(Log.class, logId);
								log.setAccount(null);
								log.setPassport(null);
							}
						}
						dao.delete(account);// 删除账号
					}
				}
			dao.delete(passport);
			}
		}
	}
	
	
}
