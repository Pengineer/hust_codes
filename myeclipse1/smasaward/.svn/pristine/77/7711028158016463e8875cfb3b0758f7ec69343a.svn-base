package csdc.action.pop.select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import csdc.action.BaseAction;

/**
 * 学科代码树
 * @author fengcl
 *
 */
public class DisciplineTreeAction extends BaseAction{
	private static final long serialVersionUID = -917187157377264903L;
	
	private List nodesInfo;				//树节点列表
	private String id;					//已选择的学科代码id
	
	private String selectedDisciplines;	//已选择的学科代码+名称
	private String codes;				//已选择的学科代码
	
	public String toDisciplineTree(){
		session.put("selectedDisciplines", selectedDisciplines);
		return SUCCESS;
	}
	
	/**
	 * 创建学科树
	 * @return
	 */
	public String createDisciplineTree() {
		Date start = new Date();

		//已选择的学科代码
		HashSet<String> selectDisps = null; 
		selectedDisciplines = (String) session.get("selectedDisciplines");
		if (null != selectedDisciplines && !selectedDisciplines.isEmpty()) {
			String[] strs = selectedDisciplines.split("; ");
			selectDisps = new HashSet<String>(Arrays.asList(strs));
		}
		//学科树的各级节点
		nodesInfo = new ArrayList();	//根节点
		String hql = "select so.id, so.code, so.name, min(children.id) from SystemOption so left join so.sysoption children where " +
				(id == null || id.isEmpty() ? " so.parent.id = (select so1.id from SystemOption so1 where so1.parent.id is null and so1.standard = '[GBT13745-2009]') " : " so.parent.id = ? ") +
				"and so.standard = '[GBT13745-2009]' group by so.id, so.name, so.code order by so.code ";
		List<Object[]> disall = (id == null || id.isEmpty()) ? baseService.query(hql) : baseService.query(hql, id);
		
		for (Object[] systemOption : disall) {
			Map node = new HashMap();
			node.put("id", systemOption[0]);
			String code = (String) systemOption[1];
			String name = (code == null) ? systemOption[2].toString() : (code + "/" + systemOption[2]);
			node.put("name", name);
			if (systemOption[3] != null) {
				node.put("isParent", true);//是否父节点
			}
			if (null != selectDisps && selectDisps.contains(code)) {
				node.put("checked", true);
			}
			nodesInfo.add(node);
		}
		
		System.out.println("结束生成treeNodes...");
		System.out.println("生成treeNodes耗时："+(new Date().getTime() - start.getTime())+"ms");
		return SUCCESS;
	}

	/**
	 * 根据学科代码code获取idCodeName
	 * @return
	 */
	public String getDispIdCodeNameByCode(){
		String[] code = codes.split("; ");
		StringBuffer sb = new StringBuffer();
		if(code != null && code.length > 0){
			Map paraMap = new HashMap();
			paraMap.put("code", code);
			String hql = "select so.id, so.code, so.name from SystemOption so where so.standard = '[GBT13745-2009]' and so.code in (:code) order by so.code ";
			List<Object[]> disp = baseService.query(hql, paraMap);
			for (Object[] obj : disp) {
				String idCodeName = obj[0] + "%" + obj[1] + "/" + obj[2];
				sb.append(idCodeName).append("; ");
			}
		}
		if (sb.length() > 2) {
			jsonMap.put("idCodeName", sb.substring(0, sb.length() - 2));
		}else {
			jsonMap.put("idCodeName", "");
		}
		return SUCCESS;
	}
	
	public List getNodesInfo() {
		return nodesInfo;
	}
	public void setNodesInfo(List nodesInfo) {
		this.nodesInfo = nodesInfo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	@Override
	public String pageName() {
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
	public Object[] simpleSearchCondition() {
		return null;
	}
	@Override
	public Object[] advSearchCondition() {
		return null;
	}
	@Override
	public String pageBufferId() {
		return null;
	}

	public String getSelectedDisciplines() {
		return selectedDisciplines;
	}
	public void setSelectedDisciplines(String selectedDisciplines) {
		this.selectedDisciplines = selectedDisciplines;
	}
	public String getCodes() {
		return codes;
	}
	public void setCodes(String codes) {
		this.codes = codes;
	}

}
