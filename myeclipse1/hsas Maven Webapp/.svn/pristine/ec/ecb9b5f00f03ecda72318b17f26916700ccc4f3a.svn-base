package csdc.action.system;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;

import csdc.model.Right;
import csdc.service.IRightService;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.RightInfo;


@Component
@Scope(value="prototype")

public class RightAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3572634834691702290L;
	private static final String HQL = "select r.id, r.name, r.description, r.code, r.nodeValue from Right r  ";
	private static final String[] COLUMN = {
			"r.name",
			"r.description, r.name",
			"r.code, r.name",
			"r.nodeValue, r.name"
	};// 用于拼接的排序列
	private static final String PAGE_NAME = "rightPage";// 列表页面名称
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "r.id";// 上下条查看时用于查找缓存的字段
	private static final String TMP_ENTITY_ID = "rightId";// 用于session缓存实体的ID名称
	
	private String keyword1, keyword2, keyword3, keyword4;// 高级检索关键字
	private String fileFileName;//导出文件名
	
	private Right right;// 权限对象
	
	@Autowired
	private IRightService rightService;// 权限管理接口
	
	

	
	@Override
	public String toAdd() {
		
		return SUCCESS;
	}

	@Override
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
		if (rightService.checkRightNode(right.getNodeValue())) {// 权限节点值存在，返回错误提示
			this.addFieldError(GlobalInfo.ERROR_INFO, RightInfo.ERROR_NODEVALUE_EXIST);
			return INPUT;
		}
		entityId = rightService.addRight(right);// 添加权限
		jsonMap.put("tag", 1);
		return SUCCESS;
	}

	
	
	
	
	@Override
	@Transactional
	public String delete() {
		// TODO Auto-generated method stub
		rightService.deleteRight(entityIds);// 删除权限
		return SUCCESS;
	}

	@Override
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

	@Override
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
		if (!oldRight.getNodeValue().equals(right.getNodeValue()) && rightService.checkRightNode(right.getNodeValue())) {// 权限节点存在，返回错误提示
			this.addFieldError(GlobalInfo.ERROR_INFO, RightInfo.ERROR_NODEVALUE_EXIST);
			return INPUT;
		}
		entityId = rightService.modifyRight(oldRight, right);// 修改权限
		ActionContext.getContext().getSession().remove("entityId");// 删除备用权限ID
		jsonMap.clear();
		jsonMap.put("tag", "1");
		return SUCCESS;
	}

	
	@Override
	public String toView() {
		
		return SUCCESS;
	}

	@Override
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
	
	@Override
	public String toList() {
		
		return defaultToList();
		
	}

	@Override
	public String list() {
		
		return defaultList();
	}

	@Override
	public Object[] simpleSearchCondition() {
		keyword = (keyword == null) ? "" : keyword.toLowerCase().trim();// 预处理关键字
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		if (keyword != null && !keyword.isEmpty()) {
			hql.append(" where ");
			if (searchType == 1) {
				hql.append("LOWER(r.name) like :keyword");
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 2) {
				hql.append("LOWER(r.description) like :keyword");
				map.put("keyword", "%" + keyword + "%");
			} else {
				hql.append("(LOWER(r.name) like :keyword or LOWER(r.description) like :keyword)");
				map.put("keyword", "%" + keyword + "%");
			}
		}
		// 调用初级检索功能
		return new Object[]{
			hql.toString(),
			map,
			0,
			null,
			null
		};
	}

	@Override
	public String simpleSearch() {
		
		return defaultSimpleSearch();
	}

	@Override
	public Object[] advSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String advSearch() {
		
		return defaultAdvSearch();
	}

	@Override
	public String changePageSize() {
		
		return defaultChangePageSize();
	}

	@Override
	public String toPage() {
		
		return defaultToPage();
	}

	@Override
	public String toEntity() {
		
		return defaultToEntity();
	}

	@Override
	public String sort() {
		
		return defaultSort();
	}
	
	

	@Override
	public String pageName() {
		return this.PAGE_NAME;
	}

	@Override
	public String pageBufferId() {
		return this.PAGE_BUFFER_ID;
	}

	@Override
	public String[] sortColumn() {
		return this.COLUMN;
	}

	@Override
	public String dateFormat() {
		return this.DATE_FORMAT;
	}
	
	public String HQL() {
		return HQL;
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

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public Right getRight() {
		return right;
	}

	public void setRight(Right right) {
		this.right = right;
	}

}
