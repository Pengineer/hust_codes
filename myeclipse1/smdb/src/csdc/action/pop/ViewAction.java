package csdc.action.pop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.action.BaseAction;
import csdc.bean.Passport;
import csdc.service.IAccountService;
import csdc.service.IViewService;
import csdc.tool.info.GlobalInfo;

/**
 * 跨模块查看功能，主要用于弹出层
 * 注意这里用于显示简略信息，凡是信息请通过request带回页面，
 * 不要添加成员变量。
 * @author 龚凡 2010-09-30
 */
@SuppressWarnings("unchecked")
public class ViewAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private IViewService viewService;// 接口
	public HttpServletRequest request;// request对象
	private String entityId;// 查看的实体ID
	private int type;// 机构类型
	private String projectTypeId;//项目类别id
	@Autowired
	protected IAccountService accountService;
	private List<String[]> userList;// 通行证的关联账号信息

	/**
	 * 弹出层查看账号
	 */
	public String viewAccount() {
		Map map = viewService.viewAccount(entityId);
		request.setAttribute("map", map);
		return SUCCESS;
	}
	
	/**
	 * 弹出层查看通行证
	 */
	public String viewPassport() {
		Map map = viewService.viewPassport(entityId);
		Passport passport = dao.query(Passport.class, entityId);
		userList = accountService.userInfo(passport.getName());
		request.setAttribute("map", map);
		return SUCCESS;
	}
	
	/**
	 * 弹出层查看人员
	 */
	public String viewPerson() {
		String[] entityIds = entityId.split("; ");
		List<Map> infoList = new ArrayList<Map>();
		Map map = new HashMap();
		if(entityIds.length == 1){
			map = viewService.viewPerson(type, entityId);
			infoList.add(map);
		} else {
			for(String id : entityIds){
				map = viewService.viewPerson(type, id);
				infoList.add(map);
			}
		}
		request.setAttribute("infoList", infoList);
		return SUCCESS;
	}

	/**
	 * 弹出层查看机构
	 */
	public String viewAgency() {
		Map map = viewService.viewAgency(type, entityId);
		request.setAttribute("map", map);
		if(type == 1){
			return "viewAgency";
		}else if(type == 2){
			return "viewDepartment";
		}else if(type == 3){
			return "viewInstitute";
		}else{
			return INPUT;
		}
	}

	/**
	 * 弹出层查看项目
	 */
	public String viewProject() {
		Map map=viewService.viewProject(projectTypeId, entityId);
		request.setAttribute("map", map);
		return SUCCESS;
	}

	/**
	 * 弹出层查看项目
	 */
	public String viewProjectFunding() {
		Map map=viewService.viewProjectFunding(projectTypeId, entityId);
		request.setAttribute("map", map);
		return SUCCESS;
	}
	
	/**
	 * 弹出层查看日志
	 */
	public String viewLog() {
		Map map = viewService.viewLog(entityId);
		request.setAttribute("map", map);
		return SUCCESS;
	}
	
	/**
	 * 弹出层备忘提醒
	 */
	public String viewMemo() {
		Map map = new HashMap();
		if(entityId.equals("first")){
			map = viewService.viewMemo(0);
			session.put("memoMap", map);
			jsonMap.put("memoCount", map.get("map1"));
			return SUCCESS;
		}else if(entityId.equals("2")){
			map = (Map) session.get("memoMap");
			if(map == null || map.isEmpty()){
				return INPUT;
			}
			request.setAttribute("map", map);
			return "toPop";
		}
		return null;
	}
	
	/**
	 * 弹出层查看团队信息
	 */
	public String viewOrganization() {
		Map map = viewService.viewOrganization(entityId);
		request.setAttribute("map", map);
		return SUCCESS;
	}

	/**
	 * 弹出层查看校验
	 */
	public void validate() {
		if (entityId == null || entityId.trim().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_VIEW_NULL);
		}
	}

	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	public void setViewService(IViewService viewService) {
		this.viewService = viewService;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public String getProjectTypeId() {
		return projectTypeId;
	}

	public void setProjectTypeId(String projectTypeId) {
		this.projectTypeId = projectTypeId;
	}

	public IViewService getViewService() {
		return viewService;
	}

	@Override
	public Object[] advSearchCondition() {
		return null;
	}

	@Override
	public String[] column() {
		return null;
	}

	@Override
	public String dateFormat() {
		return null;
	}

	@Override
	public String pageBufferId() {
		return null;
	}

	@Override
	public String pageName() {
		return null;
	}

	@Override
	public Object[] simpleSearchCondition() {
		return null;
	}

	public List<String[]> getUserList() {
		return userList;
	}

	public void setUserList(List<String[]> userList) {
		this.userList = userList;
	}

}
