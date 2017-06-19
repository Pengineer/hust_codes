// ========================================================================
// 文件名: RoleRightAction.java
//
// 文件说明:
//     本文件主要实现权限管理的功能，包括权限的添加、权限列表、权限的查看及修改。
// 主要用到service层的接口有IRoleRightService。各个action与页面的对应关系查看
// struts-right.xml文件。
//
// 历史记录:
// 2009-11-28  龚凡           创建文件，完成基本功能.
// 2010-03-09  龚凡          整理代码与角色分离
// ========================================================================

package csdc.action.oa;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.security.crypto.codec.Utf8;

import sun.rmi.transport.proxy.HttpReceiveSocket;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.bean.Account;
import csdc.bean.Resume;
import csdc.bean.Right;
import csdc.service.IBaseService;
import csdc.service.imp.BaseService;


/**
 * 权限管理
 * @author 龚凡
 * @version 1.0 2010.03.31
 *
 */
public class RightAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private IBaseService baseService;
	private Map jsonMap = new HashMap();
	private Right right;
	private String rightId;
	
	public String toList() {
		return SUCCESS;
	}
	/**
	 * 权限列表
	 * @return 跳转成功
	 */
	@SuppressWarnings("unchecked")
	public String list() {
	    ArrayList<Right> rightList = new ArrayList <Right> ();    
	    List<Object[]> rList = new ArrayList<Object[]>();
		Map session = ActionContext.getContext().getSession();
		rightList =  (ArrayList<Right>) baseService.list(Right.class, null);
		String[] item;
		for(Right r : rightList){
			item = new String[5];
			item[0] = r.getName();
			item[1] = r.getCode();
			item[2] = r.getNodeValue();
			item[3] = r.getDescription();
			item[4] = r.getId();
			rList.add(item);
		}
		jsonMap.put("aaData", rList);
		return SUCCESS;
	}

	/**
	 * 进入添加权限页面
	 * @return 跳转成功
	 */
	public String toAdd() {
		return SUCCESS;
	}

	/**
	 * 添加权限
	 * @return 跳转成功
	 */
	public String add() {
		right.setName(right.getName());
		right.setDescription(right.getDescription());
		right.setCode(right.getCode());
		right.setNodeValue(right.getNodeValue());
		try {
			baseService.add(right);
		} catch (Exception e) {
			e.printStackTrace();
		}
		rightId = right.getId();
		return SUCCESS;
	}

	/** 
	 * 删除权限
	 * @return 跳转成功
	 */
	public String delete() {
		baseService.delete(Right.class, rightId);
		Right right = (Right) baseService.load(Right.class, rightId);
		if(null != right) {
			jsonMap.put("result", 0);
		} else {
			jsonMap.put("result", 1);
		}
		return SUCCESS;
	}


	/**
	 * 查看权限
	 * @return 跳转成功
	 */
	@SuppressWarnings("unchecked")
	public String view() {
		right = (Right)baseService.load(Right.class, rightId);
		return SUCCESS;

	}

	/**
	 * 进入修改权限
	 * @return 跳转成功
	 */
	@SuppressWarnings("unchecked")
	public String toModify() {
		right = (Right)baseService.load(Right.class, rightId);
		Map map = ActionContext.getContext().getApplication();
		map.put("right", right);
		return SUCCESS;
	}

	/**
	 * 修改权限
	 * @return 跳转成功
	 */
	@SuppressWarnings("unchecked")
	public String modify() {
		Map map = ActionContext.getContext().getApplication();
		/*Right r = this.rightService.select(((Right) map.get("right")).getId());*/
		Right r = (Right) baseService.load(Right.class, ((Right) map.get("right")).getId());
		r.setName(right.getName());
		r.setDescription(right.getDescription());
		r.setCode(right.getCode());
		r.setNodeValue(right.getNodeValue());
		baseService.modify(r);
		return SUCCESS;
	}

	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}
	
	public Right getRight() {
		return right;
	}

	public void setRight(Right right) {
		this.right = right;
	}

	public String getRightId() {
		return rightId;
	}

	public void setRightId(String rightId) {
		this.rightId = rightId;
	}
	public IBaseService getBaseService() {
		return baseService;
	}
	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}
}