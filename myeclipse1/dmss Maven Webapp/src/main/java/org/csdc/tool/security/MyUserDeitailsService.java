package org.csdc.tool.security;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.csdc.model.Account;
import org.csdc.service.imp.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 实现UserDetailsService接口
 * @author 龚凡
 */
public class MyUserDeitailsService implements UserDetailsService {

	@Autowired
    private AccountService accountService;// 账号管理接口
//    private List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

    /**
     * 根据账号名生成权限对象
     */
    @SuppressWarnings({})
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
    	// 根据账号名查找账号对象
    	Account account = accountService.getAccountByAccountName(username);
    	GrantedAuthority[] grantedAuthArray;// 账号权限
		List<String> userRight = null;
		if(account != null) {
			userRight = accountService.getRightByAccountName(username);
			grantedAuthArray = new GrantedAuthority[userRight.size() + 1];
			Iterator iterator = userRight.iterator();
			int i = 0;
			while (iterator.hasNext()) {// 遍历用户权限，生成security需要的权限对象GrantedAuthority
				grantedAuthArray[i] = new GrantedAuthorityImpl(((String) iterator.next()).toUpperCase());
				i++;
			}
			grantedAuthArray[i] = new GrantedAuthorityImpl("ROLE_USER");			
		} else {
			grantedAuthArray = new GrantedAuthority[1];
			grantedAuthArray[0] = new GrantedAuthorityImpl("NULL");
		}
    		/*---------临时修改密码校验方法-----------*/
//    		String temp = MD5.getMD5("supersmdb");
//    		return new org.springframework.security.core.userdetails.User(username, temp, true, true, true, true, this.AUTHORITIES);
    		/*---------临时修改密码校验方法结束-----------*/		
    	return new org.springframework.security.core.userdetails.User(username, account.getPassword(), true, true, true, true, grantedAuthArray);    	
    }
}
