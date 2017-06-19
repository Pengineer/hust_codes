package csdc.tool.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import csdc.bean.Passport;
import csdc.service.IAccountService;
import csdc.tool.MD5;

/**
 * 实现UserDetailsService接口
 * @author 龚凡
 */
public class MyUserDeitailsService implements UserDetailsService {

    private IAccountService accountService;// 账号管理接口
    private List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

    /**
     * 根据账号名生成权限对象
     */
    @SuppressWarnings({})
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
    	// 根据账号名查找账号对象
    	Passport passport = accountService.getPassportByName(username);
    	
    	if (passport == null) {// 如果通行证不存在，则抛异常
    		throw new UsernameNotFoundException("User name is not found.");
    	} else {// 如果账号存在，则查找其拥有的权限，并打包成security需要的形式
    		AUTHORITIES.add(new GrantedAuthorityImpl("NULL"));//此处不赋权限，设置NULL（不存在的权限名），选账号之后再重新生成权限。
    		/*---------临时修改密码校验方法-----------*/
//    		String temp = MD5.getMD5("supersmdb");
//    		return new org.springframework.security.core.userdetails.User(username, temp, true, true, true, true, this.AUTHORITIES);
    		/*---------临时修改密码校验方法结束-----------*/
    		return new org.springframework.security.core.userdetails.User(username, passport.getPassword(), true, true, true, true, this.AUTHORITIES);
    	}
    }

    public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}
}
