package csdc.action.oa;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.swing.ListModel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.ibatis.annotations.Delete;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.bean.Department;
import csdc.bean.Person;
import csdc.dao.BaseDao;
import csdc.service.IBaseService;
import csdc.service.IDepartmentService;
import csdc.service.imp.DepartmentService;

public class DepartmentAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private IBaseService baseService;
	private IDepartmentService departmentService;
	private String id;// 部门id
	private String name;// 部门名称
	private String description;// 部门描述
	private String parentId;// 父节点id
	private Department department;
	private String nodesString;
	private static final String TMP_NODE_VALUE = "nodeValue";// 创建部门树时，缓存的已选项
	Map map = new HashMap();
	Map jsonMap = new HashMap();

	public String toView() {
		return SUCCESS;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String view() {
		ArrayList<Map> depList = new ArrayList<Map>();
		depList = (ArrayList<Map>) baseService.list(Department.class.getName() + ".listDepartmentMap", null);
		JSONArray jsonArray = JSONArray.fromObject(depList);
		jsonMap.put("tree", jsonArray);
		// 必须返回空
		return SUCCESS;
	}
	
	/** 列表 */
/*	public String list() throws Exception {
		List<Department> departmentList = null;
		if (parentId == null) {//查询所有顶级部门列表
			departmentList = DepartmentService.selectTopList();
			departmentList = baseService.list(Department.class,null);
		}else {//查询子部门列表
			map.put("parentId", parentId);
			departmentList = baseService.list(Department.class, map);
			Department parent = (Department) baseService.load(Department.class, parentId);
			ActionContext.getContext().put("parent", parent);
		}		
		ActionContext.getContext().put("departmentList", departmentList);
		return SUCCESS;
	}*/

	/** 添加页面 */
	public String addUI() throws Exception {
		//TODO:准备数据：departmentList，显示为树状结构
		List<Department> departmentList = baseService.list(Department.class, null);
//		List<Department> topList = departmentService.selectTopList();
//		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);
		return "saveUI";
	}

	/** 添加 */
	public String add() throws Exception {
		Department department = new Department();
		/*department.setParentId(parentId);*/
		department.setName(name);
		
		try {
			baseService.add(department);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "tolist";
	}

	/** 修改页面 */
	public String modifyUI() throws Exception {
		// 准备回显的信息
		Department department = (Department) baseService.load(Department.class, id);
		ActionContext.getContext().getValueStack().push(department);
		return "saveUI";
	}

/*	public String modify() {
		if(null != nodesString && !nodesString.isEmpty()) {
			Department departmentZero = (Department) baseService.load(Department.class, "100000002264");//查询第零级部门
			Map map = new HashMap();
			map.put("pId", departmentZero.getId());
			List<Department> departmentOneList = baseService.list(Department.class, map);//查询第一级部门
		}
		
		
		return SUCCESS;
	}*/
	
	/** 修改 */
	public String modify() throws Exception {
		if (null != nodesString && !nodesString.isEmpty()) {
			//查询所有的部门记录，先删除最底层的部门，依次往上层删除
			List<Department> depList = null;
			depList = baseService.list(Department.class, null);
			for(int i = 0; i< depList.size(); i++) {
				if(null == depList.get(i).getDepartment()) {
					baseService.delete(Department.class, depList.get(i).getId());
				}
			}

			
			JSONArray array = JSONArray.fromObject(nodesString);
			Department department = null;
			for (int i = 0; i < array.size(); i++) {
				System.out.println(array.size());;
				JSONObject object = (JSONObject) array.get(i);
				setter(object, department);	
			}
		}
		return SUCCESS;
	}

	/*			for(int i = 0; i< depList.size(); i++) {
	Department department = (Department)baseService.load(Department.class, depList.get(i).getId());
	
	if(null == department.getDepartment()) {
		baseService.delete(Department.class, department.getId());
	}
}*/
/*			for(int i = 0; i< depList.size(); i++) {
	Department department = (Department)baseService.load(Department.class, depList.get(i).getId());
	Map map = new HashMap();
	if(null != department.getDepartment()) {
		
	}
	map.put("pId", department.getId());
	List<Department> children = baseService.list(Department.class, map);
	for(int j = 0; j < children.size(); j++) {
		baseService.delete(Department.class, children.get(j).getId());
	}
}*/

/*			for(int i = 0; i< depList.size(); i++) {
	Department department = (Department)baseService.load(Department.class, depList.get(i).getId());
	Map map = new HashMap();
	map.put("pId", department.getId());
	//查询以department主键作为父节点的部门，如果不存在则department处于最底层部门，可以删除
	List<Department> children = baseService.list(Department.class, map);
	if(children.size() == 0) {
		if()
		baseService.delete(Department.class, depList.get(i).getId());
	}
	
}*/
	
	public void setter(JSONObject object, Department pDepartment) {
		Department department = new Department();
		department.setName(object.getString("name"));
		department.setDepartment(pDepartment);
		// 判断节点是否为父节点
		if (object.containsKey("children") && !object.getJSONArray("children").isEmpty()) {
			baseService.add(department);
			pDepartment = department;// 存放父节点信息
			JSONArray array = JSONArray.fromObject(object.get("children"));
			for (int i = 0; i < array.size(); i++) {
				JSONObject object1 = (JSONObject) array.get(i);
				setter(object1, department);
			}
		} else {
			baseService.add(department);
			if (object.containsKey("value")&& object.getString("value") != "null") {
				// so.setDescription(object.getString("value"));//若从zTree传到后台的数据带有value，则将该改value存入导航栏条目的description属性中
				String[] ids = object.getString("value").split(";");
				if (ids.length > 1) {
					for (int i = 0; i < ids.length; i++) {//查询出所有以ids[i]为外键的人员
						Map map = new HashMap();
						map.put("departmentId", ids[i]);
						List<Person> personList =  baseService.list(Person.class, map);
						if (personList != null) {
							for(int j = 0; j < personList.size(); j++) {
								personList.get(j).setDepartment(null);
								baseService.modify(personList.get(j));
							}
						} 
					}
				}else {}
			}
		}
	}
	
	/** 删除 */
	public String delete() throws Exception {
		System.out.println(id);
		map.put("id", id);
		Department department = (Department) baseService.load(Department.class, id);
		List<Department> departments = baseService.list(Department.class, map);

		baseService.delete(Department.class, id);
		return SUCCESS;
	}


	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public IDepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(IDepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}

	public String getNodesString() {
		return nodesString;
	}

	public void setNodesString(String nodesString) {
		this.nodesString = nodesString;
	}

}
