package csdc.action.oa;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import net.sf.json.JSONArray;

import com.opensymphony.xwork2.ActionSupport;

import csdc.bean.Department;
import csdc.bean.SystemOption;
import csdc.service.IBaseService;

public class SystemOptionAction extends ActionSupport {
	
	private IBaseService baseService;
	private Map jsonMap = new HashedMap();
	
	public String toView() {
		return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public String view() {
		ArrayList<Map> systemoptionList = new ArrayList<Map>();
		systemoptionList =  (ArrayList<Map>) baseService.list(SystemOption.class.getName() + ".listSystemOptionMap", null);
		JSONArray jsonArray = JSONArray.fromObject(systemoptionList);
		jsonMap.put("tree", jsonArray);
		return SUCCESS;
	}

	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}
}