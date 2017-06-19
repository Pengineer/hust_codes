package csdc.tool.security;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import csdc.model.Account;
import csdc.service.imp.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDeitailsService implements UserDetailsService {

	@Autowired
	private AccountService accountService;// 账号管理接口
	@SuppressWarnings("unused")
	private List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

	/**
	 * 根据账号名生成权限对象
	 */
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		// 根据账号名查找账号对象
		Account account = accountService.getAccountByName(username);
		GrantedAuthority[] grantedAuthArray;// 账号权限
		List<String> userRight = null;
		if (account == null) {// 如果账号不存在，则抛异常
			throw new UsernameNotFoundException("User name is not found.");
		} else {// 如果账号存在，则查找其拥有的权限，并打包成security需要的形式
			userRight = accountService.getRightByAccountName(username);
			grantedAuthArray = new GrantedAuthority[userRight.size() + 1];
			Iterator iterator = userRight.iterator();
			int i = 0;
			while (iterator.hasNext()) {// 遍历用户权限，生成security需要的权限对象GrantedAuthority
				grantedAuthArray[i] = new GrantedAuthorityImpl(((String) iterator.next()).toUpperCase());
				i++;
			}
			grantedAuthArray[i] = new GrantedAuthorityImpl("ROLE_USER");
			return new org.springframework.security.core.userdetails.User(
					username, account.getPassword(), true, true, true, true,
					grantedAuthArray);
		}
	}

}
