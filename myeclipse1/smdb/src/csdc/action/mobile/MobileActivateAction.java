package csdc.action.mobile;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Passport;
import csdc.bean.ProjectMember;
import csdc.service.IMobileActivateService;

/**
 * mobile账号激活相关模块
 * @author wangming
 *
 */
@SuppressWarnings("unchecked")
public class MobileActivateAction extends MobileAction{
	
	private static final long serialVersionUID = 1L;
	private static final String PAGENAME = "mobileActivatePage";
	private IMobileActivateService mobileActivateService;
	private String projectNumber;
	private String idcardNumber;
	private String passportName;
	private String passportId;
	private String accountId;
	private String activateVerifyCode;
	private String password;
	
	
//	在客户端登录页，会有注册激活按钮，点击跳转到一个“注册激活”页面，项目负责人输入项目批准号、身份证号、账号名、密码，点击“激活”后，
//	数据库中，与该项目批准号以及身份证号相关的人员的邮箱会收到第一次激活链接，点击 链接后，跳转到验证界面，当用户再次输入用户名和密码，
//	与前者保持一致后，就发送第二次激活链接到该邮箱，点击此链接，激活成功。若第二次激活未成功，则覆盖第一次输入的所有信息。如果用户输入
//	项目批准号，身份证号，用户名，密码等信息后，在后台未找到对应的邮箱信息或者该用户已经不再使用该邮箱等特殊情况后，应该有一个人工审核的通道。
//	具体操作拟为：
//	1.用户把项目批准函、身份证等验证信息拍照，同时包含用户希望收到激活邮件的邮箱地址，发送到我们的指定邮箱。
//	2.我们进行审核验证，如果通过的话，则给该用户的激活邮箱发送一份激活邮件，该激活邮件需要用户设置他的用户名和密码，然后实现账号激活。
	public String checkPersonInfo(){
		int checkPersonInfoResult = mobileActivateService.checkPersonInfo(projectNumber, idcardNumber);
		if (checkPersonInfoResult==0) {
			jsonMap.put("result", 0);
		} else if (checkPersonInfoResult ==1) {
			jsonMap.put("errorInfo", "该账号已激活！");
			jsonMap.put("result", 1);
		} else if (checkPersonInfoResult ==2) {
			jsonMap.put("errorInfo", "项目批准号不存在！");
			jsonMap.put("result", 2);
		} else if (checkPersonInfoResult ==3) {
			jsonMap.put("errorInfo", "项目批准号和身份证信息验证不通过！");
			jsonMap.put("result", 3);
		} else if (checkPersonInfoResult ==4) {
			jsonMap.put("errorInfo", "该人员缺少邮箱信息！");
			jsonMap.put("result", 4);
		}
		return SUCCESS;
	}
	
	public String checkPassportName(){
		if (!mobileActivateService.checkPassportName(passportName)) {
			jsonMap.put("errorInfo", "该账号名已存在!");
			jsonMap.put("result", 1);
		}else {
			jsonMap.put("result", 0);
		}
		return SUCCESS;
	}
	@Transactional
	public String firstActivate(){
		//校验项目批准号和身份证号是否对应
		int checkPersonInfoResult = mobileActivateService.checkPersonInfo(projectNumber, idcardNumber);
		if (checkPersonInfoResult!=0) {
			if (checkPersonInfoResult ==1) {
				jsonMap.put("errorInfo", "该账号已激活！");
				jsonMap.put("result", 1);
			} else if (checkPersonInfoResult ==2) {
				jsonMap.put("errorInfo", "项目批准号不存在！");
				jsonMap.put("result", 2);
			} else if (checkPersonInfoResult ==3) {
				jsonMap.put("errorInfo", "项目批准号和身份证信息验证不通过！");
				jsonMap.put("result", 3);
			} else if (checkPersonInfoResult ==4) {
				jsonMap.put("errorInfo", "该人员缺少邮箱信息！");
				jsonMap.put("result", 4);
			}
			return INPUT;
		}
		//校验用户名是否唯一存在
		if (!mobileActivateService.checkPassportName(passportName)) {
			jsonMap.put("errorInfo", "该账号名已存在!");
			jsonMap.put("result", 5);
			return INPUT;
		}
		ProjectMember projectMember = mobileActivateService.getProjectMemberInfo(projectNumber, idcardNumber);
		//创建账号
		Passport passport = mobileActivateService.activateTeacherAccount(projectMember, passportName, password);
		//发送邮件
		jsonMap.put("result", 0);
		return SUCCESS;
	}
	
	public String secondActivate(){
		int validateStatus = mobileActivateService.validateSecondActivate(passportId, activateVerifyCode);
		if (validateStatus !=0) {
			jsonMap.put("result",validateStatus);
			switch (validateStatus) {
			case 1:
				jsonMap.put("errorInfo", "通行证不存在！");
				break;
			case 2:
				jsonMap.put("errorInfo", "通行证已激活！");
				break;
			case 3:
				jsonMap.put("errorInfo", "激活验证码已失效！");
				break;
			case 4:
				jsonMap.put("errorInfo", "激活验证码错误！");
				break;
			default:
				jsonMap.put("errorInfo", "激活失败");
				break;
			}
			return INPUT;
		};
		mobileActivateService.secondActivate(passportId, accountId);
		return SUCCESS;
	}

	@Override
	public String pageName() {
		return PAGENAME;
	}

	public IMobileActivateService getMobileActivateService() {
		return mobileActivateService;
	}

	public void setMobileActivateService(
			IMobileActivateService mobileActivateService) {
		this.mobileActivateService = mobileActivateService;
	}

	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	public String getIdcardNumber() {
		return idcardNumber;
	}

	public void setIdcardNumber(String idcardNumber) {
		this.idcardNumber = idcardNumber;
	}

	public String getPassportName() {
		return passportName;
	}

	public void setPassportName(String passportName) {
		this.passportName = passportName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassportId() {
		return passportId;
	}

	public void setPassportId(String passportId) {
		this.passportId = passportId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getActivateVerifyCode() {
		return activateVerifyCode;
	}

	public void setActivateVerifyCode(String activateVerifyCode) {
		this.activateVerifyCode = activateVerifyCode;
	}
	
}
