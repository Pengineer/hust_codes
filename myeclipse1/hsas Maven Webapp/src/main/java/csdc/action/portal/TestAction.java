package csdc.action.portal;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;

import sun.tools.tree.ThisExpression;

import com.opensymphony.xwork2.ActionSupport;

@Namespace("/test")
@ParentPackage("main")
public class TestAction extends ActionSupport{

	protected Map jsonMap = new HashMap();// json对象容器
	
	@Action(value = "test", results = { 
			@Result(name = "input", type = "json", params={"root", "jsonMap"}),
			@Result(name = "success", type = "json", params={"root", "jsonMap"}) })
	public String test() {
		System.out.println(this);
		jsonMap.put("test", "测试成功");
		return SUCCESS;
	}

	/** 
	* @Description: 进入文件列表页面
	* @return
	*/
	@Action(value = "testPage", results = { @Result(name = "success", location = "/jsp/document/data/list.jsp") })
	public String testPage() {
		return SUCCESS;
	}
	
	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}
	
	
}
