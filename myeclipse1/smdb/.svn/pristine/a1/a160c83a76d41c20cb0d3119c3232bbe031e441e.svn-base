package csdc.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


import csdc.bean.Account;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.InBox;
import csdc.bean.InBoxAccount;
import csdc.bean.Institute;
import csdc.bean.Officer;
import csdc.bean.Passport;
import csdc.bean.Person;
import csdc.service.IInBoxService;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.LoginInfo;

public class InBoxService extends BaseService implements IInBoxService{
	
    /**
	 * 根据收件人确定收件人帐号类型和主子类型
	 * @param C_ACCOUNT_TYPE_S
	 * @return 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public  Map QueryNameForType(String recName) {
		AccountType type = null;
		Integer isPrincipal;
		Map map = new HashMap();
		if (recName.equals("ministry")) {// 部级管理机构
			type = AccountType.MINISTRY;
			isPrincipal = 1;
			map.put("type", type);
			map.put("isPrincipal", isPrincipal);
		} else if (recName.equals("ministryOfficer")) {// 部级管理人员
			type = AccountType.MINISTRY;
			isPrincipal = 0;
			map.put("type", type);
			map.put("isPrincipal", isPrincipal);
		}
		
		if (recName.contains("province")) {// 省级管理机构
			type = AccountType.PROVINCE;
			isPrincipal = 1;
			map.put("type", type);
			map.put("isPrincipal", isPrincipal);
		} else if (recName.contains("provinceOfficer")) {// 省级管理人员
			type = AccountType.PROVINCE;
			isPrincipal = 0;
			map.put("type", type);
			map.put("isPrincipal", isPrincipal);
		}
		
		if (recName.contains("ministryUniversity")) {// 部属高校管理机构
			type = AccountType.MINISTRY_UNIVERSITY;
			isPrincipal = 1;
			map.put("type", type);
			map.put("isPrincipal", isPrincipal);
		} else if (recName.contains("ministryUniversityOfficer")) {// 部属高校管理人员
			type = AccountType.MINISTRY_UNIVERSITY;
			isPrincipal = 0;
			map.put("type", type);
			map.put("isPrincipal", isPrincipal);
		}
		
		if (recName.contains("localUniversity")) {// 地方高校管理机构
			type = AccountType.LOCAL_UNIVERSITY;
			isPrincipal = 1;
			map.put("type", type);
			map.put("isPrincipal", isPrincipal);
		} else if (recName.contains("localUniversityOfficer")) {// 地方高校管理人员
			type = AccountType.LOCAL_UNIVERSITY;
			isPrincipal = 0;
			map.put("type", type);
			map.put("isPrincipal", isPrincipal);
		}
		
		if (recName.contains("department")) {// 院系管理机构
			type = AccountType.DEPARTMENT;
			isPrincipal = 1;
			map.put("type", type);
			map.put("isPrincipal", isPrincipal);
		} else if (recName.contains("departmentOfficer")) {// 院系管理人员
			type = AccountType.DEPARTMENT;
			isPrincipal = 0;
			map.put("type", type);
			map.put("isPrincipal", isPrincipal);
		}
		
		if (recName.contains("institute")) {// 社科研究基地
			type = AccountType.INSTITUTE;
			isPrincipal = 1;
			map.put("type", type);
			map.put("isPrincipal", isPrincipal);
		} else if (recName.contains("instituteOfficer")) {// 基地管理人员
			type = AccountType.INSTITUTE;
			isPrincipal = 0;
			map.put("type", type);
			map.put("isPrincipal", isPrincipal);
		}
		
		if (recName.contains("expert")) {// 外部专家
			type = AccountType.EXPERT;
			isPrincipal = 1;
			map.put("type", type);
			map.put("isPrincipal", isPrincipal);
		}
		
		if (recName.contains("teacher")) {// 教师
			type = AccountType.TEACHER;
			isPrincipal = 1;
			map.put("type", type);
			map.put("isPrincipal", isPrincipal);
		}
		
		if (recName.contains("student")) {// 学生
			type = AccountType.STUDENT;
			isPrincipal = 1;
			map.put("type", type);
			map.put("isPrincipal", isPrincipal);
		}	
		return map;
	}


}