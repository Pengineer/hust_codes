package csdc.tool.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import csdc.bean.Account;
import csdc.service.IAccountService;


/**
 * 实现UserDetailsService接口
 * @author yuanxj
 */
public class MyAccountDeitailsService implements UserDetailsService {

	private IAccountService accountService;// 账号管理接口
	private List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

    /**
     * 根据账号名生成权限对象
     */
    @SuppressWarnings({"deprecation"})
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException, DataAccessException {
    	// 根据账号名查找账号对象
		Map map = new HashMap();
		map.put("email", email);
    	Account account = (Account)accountService.load(Account.class.getName()+".find", map);// 获取认证的账号对象
    	List<GrantedAuthority> grantedAuthArray;// 账号权限
    	if (account == null) {// 如果用户不存在，则抛异常
    		throw new UsernameNotFoundException("Account name is not found.");
    	} else {// 如果账号存在，则查找其拥有的权限，并打包成security需要的形式
    		List<String> accountRight = null;
			accountRight =  accountService.getAccountRight(account.getId());
    		grantedAuthArray = new ArrayList<GrantedAuthority>(accountRight.size());
    		Iterator iterator = accountRight.iterator();
			int i = 0;
			while (iterator.hasNext()) {// 遍历用户权限，生成security需要的权限对象GrantedAuthority
				grantedAuthArray.add(new GrantedAuthorityImpl(((String) iterator.next()).toUpperCase()));
				i++;
			}
    		return new org.springframework.security.core.userdetails.User(email, account.getPassword(), true, true, true, true, grantedAuthArray);
    	}
    }

    public IAccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}
}