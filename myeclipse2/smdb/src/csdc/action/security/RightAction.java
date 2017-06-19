package csdc.action.security;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.bean.Right;
import csdc.service.IRightService;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.RightInfo;

/**
 * 权限表字段说明详见csdc.bean.Right.java中字段注释。
 * 权限管理模块，实现的功能包括：添加、删除、修改、查看。
 * @author 龚凡
 * @version 2011.04.11
 */
@SuppressWarnings("unchecked")
public class RightAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private static final String HQL = "select r.id, r.name, r.description, r.code, r.nodevalue from Right r where 1=1 ";
	private static final String[] COLUMN = {
			"r.name",
			"r.description, r.name",
			"r.code, r.name",
			"r.nodevalue, r.name"
	};// 用于拼接的排序列
	private static final String PAGE_NAME = "rightPage";// 列表页面名称
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "r.id";// 上下条查看时用于查找缓存的字段
	private static final String TMP_ENTITY_ID = "rightId";// 用于session缓存实体的ID名称
	
	private IRightService rightService;// 权限管理接口
	private Right right;// 权限对象
	private String keyword1, keyword2, keyword3, keyword4;// 高级检索关键字
	
	public String pageName(){
		return PAGE_NAME;
	}
	public String[] column(){
		return COLUMN;
	}
	public String HQL() {
		return HQL;
	}
	public String dateFormat() {
		return RightAction.DATE_FORMAT;
	}
	public String pageBufferId() {
		return RightAction.PAGE_BUFFER_ID;
	}

	/**
	 * 进入添加
	 */
	public String toAdd() {
		return SUCCESS;
	}

	/**
	 * 添加权限
	 */
	@Transactional
	public String add() {
		if (rightService.checkRightName(right.getName())) {// 权限名称存在，返回错误提示
			this.addFieldError(GlobalInfo.ERROR_INFO, RightInfo.ERROR_NAME_EXIST);
			return INPUT;
		}
		if (rightService.checkRightCode(right.getCode())) {// 权限代码存在，返回错误提示
			this.addFieldError(GlobalInfo.ERROR_INFO, RightInfo.ERROR_CODE_EXIST);
			return INPUT;
		}
		if (rightService.checkRightNode(right.getNodevalue())) {// 权限节点值存在，返回错误提示
			this.addFieldError(GlobalInfo.ERROR_INFO, RightInfo.ERROR_NODEVALUE_EXIST);
			return INPUT;
		}
		entityId = rightService.addRight(right);// 添加权限
		return SUCCESS;
	}

	/**
	 * 添加校验
	 */
	public void validateAdd() {
		this.updateValidate();
	}

	/**
	 * 进入修改
	 */
	public String toModify() {
		right = (Right) dao.query(Right.class, entityId);// 获取权限
		if (right == null) {// 权限不存在，返回错误提示
			this.addFieldError(GlobalInfo.ERROR_INFO, RightInfo.ERROR_RIGHT_NULL);
			return INPUT;
		} else {// 权限存在，备用权限ID
			ActionContext.getContext().getSession().put(TMP_ENTITY_ID, entityId);
			return SUCCESS;
		}
	}

	/**
	 * 进入修改校验
	 */
	public void validateToModify() {
		if (entityId == null || entityId.isEmpty()) {// 权限ID不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, RightInfo.ERROR_MODIFY_NULL);
		}
	}

	/**
	 * 修改权限
	 */
	@Transactional
	public String modify() {
		entityId = (String) ActionContext.getContext().getSession().get(TMP_ENTITY_ID);// 获取备用权限ID
		Right oldRight = (Right) dao.query(Right.class, entityId);// 获取原来权限
		// 如果权限名称发生变化，校验权限名称是否存在
		if (!oldRight.getName().equals(right.getName()) && rightService.checkRightName(right.getName())) {// 权限名称存在，返回错误提示
			this.addFieldError(GlobalInfo.ERROR_INFO, RightInfo.ERROR_NAME_EXIST);
			return INPUT;
		}
		// 如果权限代码发生变化，校验权限代码是否存在
		if (!oldRight.getCode().equals(right.getCode()) && rightService.checkRightCode(right.getCode())) {// 权限代码存在，返回错误提示
			this.addFieldError(GlobalInfo.ERROR_INFO, RightInfo.ERROR_CODE_EXIST);
			return INPUT;
		}
		// 如果权限节点发生变化，校验权限节点是否存在
		if (!oldRight.getNodevalue().equals(right.getNodevalue()) && rightService.checkRightNode(right.getNodevalue())) {// 权限节点存在，返回错误提示
			this.addFieldError(GlobalInfo.ERROR_INFO, RightInfo.ERROR_NODEVALUE_EXIST);
			return INPUT;
		}
		entityId = rightService.modifyRight(oldRight, right);// 修改权限
		ActionContext.getContext().getSession().remove("entityId");// 删除备用权限ID
		return SUCCESS;
	}

	/**
	 * 修改校验
	 */
	public void validateModify() {
		this.updateValidate();
	}

	/**
	 * 输入校验
	 */
	public void updateValidate() {
		if (right.getName() == null || right.getName().isEmpty()) {// 权限名称不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, RightInfo.ERROR_NAME_NULL);
		} else if (right.getName().length() > 200) {// 权限名称不得超过200字符
			this.addFieldError(GlobalInfo.ERROR_INFO, RightInfo.ERROR_NAME_ILLEGAL);
		}
		if (right.getDescription() == null || right.getDescription().isEmpty()) {// 权限描述不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, RightInfo.ERROR_DESC_NULL);
		} else if (right.getDescription().length() > 800) {// 权限描述不得超过800字符
			this.addFieldError(GlobalInfo.ERROR_INFO, RightInfo.ERROR_DESC_ILLEGAL);
		}
		if (right.getCode() == null || right.getCode().isEmpty()) {// 权限代码不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, RightInfo.ERROR_CODE_NULL);
		} else if (right.getCode().length() > 200) {// 节点名称不得超过200字符
			this.addFieldError(GlobalInfo.ERROR_INFO, RightInfo.ERROR_CODE_ILLEGAL);
		}
		if (right.getNodevalue() == null || right.getNodevalue().isEmpty()) {// 权限节点值不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, RightInfo.ERROR_NODEVALUE_NULL);
		} else if (!Pattern.matches("(\\d\\d)+", right.getNodevalue())) {// 节点值为偶数位数字
			this.addFieldError(GlobalInfo.ERROR_INFO, RightInfo.ERROR_NODEVALUE_ILLEGAL);
		}
	}

	/**
	 * 删除权限
	 */
	@Transactional
	public String delete() {
		rightService.deleteRight(entityIds);// 删除权限
		return SUCCESS;
	}

	/**
	 * 删除校验
	 */
	public String validateDelete() {
		if (entityIds == null || entityIds.isEmpty()) {// 权限ID不得为空
			jsonMap.put(GlobalInfo.ERROR_INFO, RightInfo.ERROR_DELETE_NULL);
			return INPUT;
		} else {
			return null;
		}
	}

	/**
	 * 进入查看
	 */
	public String toView() {
		return SUCCESS;
	}

	/**
	 * 进入查看校验
	 */
	public void validateToView() {
		if (entityId == null || entityId.isEmpty()) {// 权限ID不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, RightInfo.ERROR_VIEW_NULL);
		}
	}

	/**
	 * 查看详情
	 */
	public String view() {
		right = (Right) rightService.viewRight(entityId);// 获取权限
		if (right == null) {// 权限不存在，返回错误提示
			jsonMap.put(GlobalInfo.ERROR_INFO, RightInfo.ERROR_RIGHT_NULL);
			return INPUT;
		} else {// 权限存在，存入jsonMap
			jsonMap.put("right", right);
			return SUCCESS;
		}
	}

	/**
	 * 查看校验
	 */
	public String validateView() {
		if (entityId == null || entityId.isEmpty()) {// 权限ID不得为空
			jsonMap.put(GlobalInfo.ERROR_INFO, RightInfo.ERROR_VIEW_NULL);
			return INPUT;
		} else {
			return null;
		}
	}

	/**
	 * 处理初级检索条件，拼装查询语句。
	 */
	public Object[] simpleSearchCondition() {
		if (keyword == null) {// 预处理关键字
			keyword = "";
		} else {
			keyword = keyword.toLowerCase();
		}
		
		// 拼接检索条件
		StringBuffer hql = new StringBuffer(HQL());
		Map map = new HashMap();
		hql.append(" and ");
		if (searchType == 1) {// 按权限名称检索
			hql.append(" LOWER(r.name) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 2) {// 按权限描述检索
			hql.append(" LOWER(r.description) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 3) {// 按节点名称检索
			hql.append(" LOWER(r.code) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 4) {// 按节点值检索
			hql.append(" LOWER(r.nodevalue) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else {// 按上述字段检索
			hql.append(" (LOWER(r.name) like :keyword or LOWER(r.description) like :keyword or LOWER(r.code) like :keyword or LOWER(r.nodevalue) like :keyword) ");
			map.put("keyword", "%" + keyword + "%");
		}
		return new Object[]{
			hql.toString(),
			map,
			3,
			null
		};
	}

	/**
	 * 处理高级检索条件，拼装查询语句。
	 */
	public Object[] advSearchCondition(){
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL());
		
		// 拼接检索条件，当检索关键字非空时，才添加检索条件，忽略大小写
		if (keyword1 != null && !keyword1.isEmpty()) {// 按权限名称检索
			keyword1 = keyword1.toLowerCase();
			hql.append(" and LOWER(r.name) like :keyword1 ");
			map.put("keyword1", "%" + keyword1 + "%");
		}
		if (keyword2 != null && !keyword2.isEmpty()) {// 按权限描述检索
			keyword2 = keyword2.toLowerCase();
			hql.append(" and LOWER(r.description) like :keyword2 ");
			map.put("keyword2", "%" + keyword2 + "%");
		}
		if (keyword3 != null && !keyword3.isEmpty()) {// 按权限代码检索
			keyword3 = keyword3.toLowerCase();
			hql.append(" and LOWER(r.code) like :keyword3 ");
			map.put("keyword3", "%" + keyword3 + "%");
		}
		if (keyword4 != null && !keyword4.isEmpty()) {// 按权限节点检索
			keyword4 = keyword4.toLowerCase();
			hql.append(" and LOWER(r.nodevalue) like :keyword4 ");
			map.put("keyword4", "%" + keyword4 + "%");
		}
		return new Object[] {
			hql.toString(),
			map,
			0,
			null
		};
	}

	/**
	 * 对saveAdvSearchQuery方法进行子类重写
	 * @author yangfq
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveAdvSearchQuery(Map searchQuery) {
		if(null != keyword1 && !keyword1.isEmpty()){
			searchQuery.put("keyword1", keyword1);
		}
		if(null != keyword2 && !keyword2.isEmpty()){
			searchQuery.put("keyword2", keyword2);
		}
		if(null != keyword3 && !keyword3.isEmpty()){
			searchQuery.put("keyword3", keyword3);
		}
		if (keyword4 != null && !keyword4.isEmpty()){
			searchQuery.put("keyword4", keyword4);
		}
	}
	
	
	
	public void setRightService(IRightService rightService) {
		this.rightService = rightService;
	}
	public Right getRight() {
		return right;
	}
	public void setRight(Right right) {
		this.right = right;
	}
	public String getKeyword1() {
		return keyword1;
	}
	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
	}
	public String getKeyword2() {
		return keyword2;
	}
	public void setKeyword2(String keyword2) {
		this.keyword2 = keyword2;
	}
	public String getKeyword3() {
		return keyword3;
	}
	public void setKeyword3(String keyword3) {
		this.keyword3 = keyword3;
	}
	public String getKeyword4() {
		return keyword4;
	}
	public void setKeyword4(String keyword4) {
		this.keyword4 = keyword4;
	}

}
