package csdc.service;

import csdc.bean.Passport;
import csdc.bean.Person;
import csdc.bean.ProjectMember;

/**
 * @description 客户端激活service
 * @author wangming
 */
public interface IMobileActivateService extends IBaseService {
	/**
	 * @description 校验人员信息
	 * @param projectNumber
	 * @param idcardNumber
	 * @return
	 */
	public int checkPersonInfo(String projectNumber, String idcardNumber);
	public boolean checkPassportName(String passportName);
	public ProjectMember getProjectMemberInfo(String projectNumber, String idcardNumber);
	public Passport activateTeacherAccount(ProjectMember projectMember, String passportName, String password);
	public int validateSecondActivate(String passportId, String activateVerifyCode);
	public void secondActivate(String passportId, String accountId);
}
