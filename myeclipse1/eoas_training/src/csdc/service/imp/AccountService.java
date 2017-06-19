package csdc.service.imp;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.UserQuery;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.sun.org.apache.bcel.internal.generic.NEW;

import csdc.bean.Account;
import csdc.bean.AccountRole;
import csdc.bean.Right;
import csdc.bean.Role;
import csdc.bean.Role_Right;
import csdc.dao.BaseDao;
import csdc.dao.IBaseDao;
import csdc.service.IAccountService;


public class AccountService extends BaseService implements IAccountService  {
	final static Logger logger = LoggerFactory.getLogger(AccountService.class);
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	IdentityService identityService = processEngine.getIdentityService();
	

	/**
	 * 获取当前通过认证的账号名称
	 */
	public String securityUsername() {
		// 获得当前通过认证的用户上下文信息
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		// 获得当前通过认证用户的账号名
		String username;
		if (principal instanceof UserDetails) {// 如果上下文信息为UserDails实例，则调用该接口的getUsername方法获取账号名
			username = ((UserDetails)principal).getUsername();
		} else {// 如果上下文信息不是UserDails实例，则它就是账号名
			username = principal.toString();
		}
		return username;
	}
	
/*	public Account findByEmail(String email) {
		return this.accountDao.findByEmail(email);
	}
	
	
	 * 通过邮箱查找帐号
	 * @param email
	 * @return
	 
	public boolean checkByEmailAndPassword(String email, String password) {
		try {
			accountDao.checkByEmailAndPassword(email,  password);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		if(accountDao.checkByEmailAndPassword(email,  password)!= null) {
			return true;
		} else {
			return false;
		}
	}*/

	
	/**
	 * 检查新注册用户是否可用 
	 * @param email
	 * @return true可用  false不可用
	 */
	public boolean checkAccount(String email) {
		Map map = new HashMap();
		map.put("email", email);
		Account account = new Account();
		account = (Account)this.baseDao.load(Account.class.getName()+".find", map);
		return (account == null) ? true : false;
	}
	
	// ==============================================================
	// 函数名：getAccountRight
	// 函数描述：获取用户权限的集合
	// 返回值：用户权限的集合
	// ==============================================================
	@SuppressWarnings("unchecked")
	public List<String> getAccountRight(String id) {
		if (id == null || id.isEmpty()) {
			return null;
		}else {
			/*Account account = (Account) accountDao.select(accountId);*/
			Account account = new Account();
			account = (Account)baseDao.load(Account.class, id);
			List<String> re= null;// 权限集合
			if (account.getEmail()== "admin") {
				re = null;
			}else {
				Map map = new HashMap();
				map.put("accountId", id);
				re = baseDao.list(Account.class.getName()+".listRightCodeByAccountId", map);
				return re;
			}
			return null;
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    /**
     * 注册或者添加帐号时， 保存用户信息，并且同步用户信息到activiti的identity.User和identify.Group
     * @param user              用户对象{@link User}
     * @param roleIds           用户拥有的角色ID集合
     * @param synToActiviti     是否同步数据到Activiti
     * @see Role
     */
    public void saveUser(Account account, List<Long> roleIds, boolean synToActiviti) {
        // 保存系统用户
        /*accountManager.saveEntity(account);*/
    	Account account2 = (Account) baseDao.load(Account.class, account.getId());
    	if(account2 == null) {
    		baseDao.add(account);
    	} else {
			baseDao.modify(account);
		}
        String userId = ObjectUtils.toString(account.getId());  
        if (synToActiviti) {
            UserQuery userQuery = identityService.createUserQuery();
            List<org.activiti.engine.identity.User> activitiUsers = userQuery.userId(userId).list();
 
            if (activitiUsers.size() == 1) {
                updateActivitiData(account, roleIds, activitiUsers.get(0));
            } else if (activitiUsers.size() > 1) {
                String errorMsg = "发现重复用户：id=" + userId;
                logger.error(errorMsg);
                throw new RuntimeException(errorMsg);
            } else {
                newActivitiUser(account, roleIds);
            }
        }
    }
 
    /**
     * 添加工作流用户以及角色
     * @param user      用户对象{@link User}
     * @param roleIds   用户拥有的角色ID集合
     */
    private void newActivitiUser(Account account, List<Long> roleIds) {
        String userId = account.getId().toString();
        // 添加用户
        saveActivitiUser(account);
        // 添加membership
        addMembershipToIdentify(roleIds, userId);
    }
 
    /**
     * 添加一个用户到Activiti {@link org.activiti.engine.identity.User}
     * @param user  用户对象, {@link User}
     */
    private void saveActivitiUser(Account account) {
        String accountId = account.getId().toString();
        org.activiti.engine.identity.User activitiUser = identityService.newUser(accountId);
        cloneAndSaveActivitiUser(account, activitiUser);
        logger.debug("add activiti user: {}", ToStringBuilder.reflectionToString(activitiUser));
    }
 
    /**
     * 添加Activiti Identify的用户于组关系
     * @param roleIds   角色ID集合
     * @param userId    用户ID
     */
    private void addMembershipToIdentify(List<Long> roleIds, String userId) {
        for (Long roleId : roleIds) {
            /*Role role = roleManager.getEntity(roleId);*/
            
        	Role role = (Role) baseDao.load(Role.class, (roleId + "").toString());
            logger.debug("add role to activit: {}", role);
            identityService.createMembership(userId, role.getId());
        }
    }
 
    /**
     * 更新工作流用户以及角色
     * @param user          用户对象{@link User}
     * @param roleIds       用户拥有的角色ID集合
     * @param activitiUser  Activiti引擎的用户对象，{@link org.activiti.engine.identity.User}
     */
    private void updateActivitiData(Account account, List<Long> roleIds, org.activiti.engine.identity.User activitiUser) {
 
        String userId = account.getId().toString();
 
        // 更新用户主体信息
        cloneAndSaveActivitiUser(account, activitiUser);
 
        // 删除用户的membership
        List<Group> activitiGroups = identityService.createGroupQuery().groupMember(userId).list();
        for (Group group : activitiGroups) {
            logger.debug("delete group from activit: {}", ToStringBuilder.reflectionToString(group));
            identityService.deleteMembership(userId, group.getId());
        }
 
        // 添加membership
        addMembershipToIdentify(roleIds, userId);
    }
 
    /**
     * 使用系统用户对象属性设置到Activiti User对象中
     * @param user          系统用户对象
     * @param activitiUser  Activiti User
     */
    private void cloneAndSaveActivitiUser(Account account, org.activiti.engine.identity.User activitiUser) {
        activitiUser.setFirstName(account.getName());
        activitiUser.setLastName(StringUtils.EMPTY);
        activitiUser.setPassword(StringUtils.EMPTY);
        activitiUser.setEmail(account.getEmail());
        identityService.saveUser(activitiUser);
    }

    
    
    
    @Override
    public void delete(String userId, boolean synToActiviti, boolean synToChecking) throws Exception {
        // 查询需要删除的用户对象
        /*User user = accountManager.getEntity(userId);*/
        Account account = (Account) baseDao.load(Account.class, userId);   
        if (account == null) {
        	throw new Exception("删除用户时，找不到ID为" + userId + "的用户"); 	
            /*throw new ServiceException("删除用户时，找不到ID为" + userId + "的用户");*/
        }
        /**
         * 同步删除Activiti User Group
         */
        if (synToActiviti) {
            // 同步删除Activiti User
            /*List<role> roleList = user.getRoleList();*/
        	
        	Map map = new HashMap();
        	map.put("accountId", userId);
        	account = (Account) baseDao.load(Account.class, userId);
        	AccountRole accountRole = (AccountRole) baseDao.list(AccountRole.class, map);
        	map.put("roleId", accountRole.getRole().getId());
        	List<Role> roleList = baseDao.list(Role.class, map);	
            for (Role role : roleList) {
                identityService.deleteMembership(userId.toString(), role.getName());
            }
            // 同步删除Activiti User
            identityService.deleteUser(userId.toString());
        }
 
        // 删除本系统用户
        /*accountManager.deleteUser(userId);*/
        baseDao.delete(Account.class, userId);
        // 删除考勤机用户
/*        if (synToChecking) {
            checkingAccountManager.deleteEntity(userId);
        }*/
    }
	
	
    /**
      * 同步所有角色数据到{@link Group}
      */
     public void synRoleToActiviti() {
         List<Role> allRole = baseDao.list(Role.class, null);
         List<Group> allGroup = identityService.createGroupQuery().list();
         int count = 0;
         for (Role role : allRole) {
        	 for(Group group : allGroup) {
        		 if(role.getId() == group.getId()) {
        			 count++;
        		 }
        	 }
        	 if(count > 0) {
        		 
        	 } else {
                 String groupId = role.getId().toString();
                 Group group = identityService.newGroup(groupId);
                 group.setName(role.getName());
                 group.setType(role.getDescription());
                 try {
					identityService.saveGroup(group);
				} catch (Exception e) {
					e.printStackTrace();
				}
        	 }

         }
     }

}