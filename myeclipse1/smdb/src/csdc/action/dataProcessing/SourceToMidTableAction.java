package csdc.action.dataProcessing;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.DocumentException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import csdc.bean.Task;
import csdc.service.processing.SourceToMidTableService;
import csdc.service.taskConfig.ITaskService;
import csdc.service.taskConfig.TaskConfigService;
/**
 * 
 * @author maowh
 *
 * 从数据源到中间表，通用同步代码
 * 全文参数介绍：
 * sourceName：数据源（SINOSS）
 * typeName：数据类型（application、midInspection、endInspection、variation）
 * tableName：主表名（T_SINOSS_PROJECT_APPLICATION）
 * subXmlFieldName：子表在xml中节点值（members、checkLogs）
 * subTableName：子表名（"T_SINOSS_MEMBERS"）
 * subFKName：子表到主表外键名称（C_PROJECT_APPLICATION_ID）
 * taskName：任务名称
 * toExcludeElementList : 在插入xml数据时，待排除的xml中节点名称
 * xmlFieldToPathName : 在xml文件中，一个xml节点的路径值，例如：result这个节点，路径值为：records/result
 */
public class SourceToMidTableAction extends ActionSupport implements ITaskService {
	private static final long serialVersionUID = 1L;
	@Autowired
	private SourceToMidTableService sourceToMidTableService;
	@Autowired 
	private TaskConfigService taskConfigService;//任务配置service
	
	private String sourceName;	//数据源（SINOSS）
	private String typeName;	//数据类型（申请、中检、变更、结项）（application、midInspection、endInspection、variation）
	private String tableName;	//主表名
	private String subTableName;	//子表名（"T_SINOSS_MEMBERS"）
	private String parameters;	//参数
	
	private String subXmlFieldName;//	子表在xml中节点值
	
	private Map jsonMap = new HashMap();// json对象容器

	/**
	 * 跳转到配置页面，配置：数据源，数据类型，主表等数据
	 * 
	 * @return
	 */
	public String toConfigView() {
		return SUCCESS;
	}
	
	public String toPopSelect() {
		return SUCCESS;
	}
	
	/**
	 * 获取xml节点树
	 * @return
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	public String gainXmlElementTree() throws FileNotFoundException, DocumentException{
		Set<String> resultElementTreeSet = new HashSet<String>();
		Map<String, Map> resultElementTreeMap = sourceToMidTableService.gainElementTree(sourceName, typeName, resultElementTreeSet);
		Map<String, String> xmlFieldNameToPathMap = sourceToMidTableService.gainXmlFieldPath(resultElementTreeMap);
		Set<String> trueElementTreeSet = new HashSet<String>();
		Map<String, Map> trueElementTreeMap = sourceToMidTableService.gainTrueElementTreeMap(sourceName, typeName, resultElementTreeMap, trueElementTreeSet, xmlFieldNameToPathMap);
		List<Map> resultMapList = sourceToMidTableService.gainResultMapList(trueElementTreeMap);
		List<String> toExcludeElementList = sourceToMidTableService.gainToExcludeElementList(resultElementTreeSet, trueElementTreeSet);
		jsonMap.put("trueElementTreeMap", resultMapList);
		jsonMap.put("xmlFieldNameToPathMap", xmlFieldNameToPathMap);
		jsonMap.put("toExcludeElementList", toExcludeElementList);
		return SUCCESS;
	}
	
	/**
	 * 获取xml文件与数据库字段的对应关系
	 * @throws DocumentException
	 * @throws FileNotFoundException
	 * parameters格式：jsonObjcet对象{"sourceName":"","typeName":"","toExcludeElementList":["","","",""],"xmlFieldToPathName":"","tableName":""}
	 * sourceName:数据源（SINOSS）
	 * typeName:数据类型（application、midInspection、endInspection、variation）
	 * xmlFieldToPathName:xmlField的路径（records/result）
	 * toExcludeElementList:[records,members,thirdChildrens,checkLogs];
	 * tableName:表名（T_SINOSS_PROJECT_APPLICATION）
	 * @throws JSONException 
	 * @throws SQLException 
	 */
	public String gainXmlFieldMap() throws FileNotFoundException, DocumentException, JSONException, SQLException{
		//TODO 加校验，判断参数是否合法
		JSONObject parametersJsonObjcet = new JSONObject(parameters);
		String sourceNameString = parametersJsonObjcet.getString("sourceName");
		String typeNameString = parametersJsonObjcet.getString("typeName");
		JSONArray toExcludeElementListArray = parametersJsonObjcet.getJSONArray("toExcludeElementList");
		List<String> toExcludeElementList = new ArrayList<String>();
		for (int i = 0; i < toExcludeElementListArray.length(); i++) {
			toExcludeElementList.add(toExcludeElementListArray.getString(i));
		}
		String xmlFieldToPathNameString = parametersJsonObjcet.getString("xmlFieldToPathName");//xmlFieldToPathNameString
		String tableNameString = parametersJsonObjcet.getString("tableName");//tableNameString
		Map<String, String> resultMap = sourceToMidTableService.gainXMLFieldFromXMLName(sourceNameString, typeNameString, xmlFieldToPathNameString, toExcludeElementList, tableNameString);
		jsonMap.put(tableNameString, resultMap);
		return SUCCESS;
	}
	
	/**
	 * 根据表名，获取该表在数据库中间表中的字段名
	 * @return
	 * @throws SQLException
	 */
	public String gainDBFieldByTableName() throws SQLException{
		List<String> dBFieldList = sourceToMidTableService.gainDBFieldByTableName(tableName);
		jsonMap.put("dBFieldList", dBFieldList);
		return SUCCESS;
	}
	
	/**
	 * 保存设置好的xml字段信息
	 * parameters格式：jsonObjcet对象{"parameters":[{"sourceName":"","typeName":"","tableName":"","xmlFieldMap":{key:value,key1:value1}},{"sourceName":"","typeName":"","tableName":"","xmlFieldMap":{key:value,key1:value1}}]}
	 * {"sourceName":"","typeName":"","tableName":"","xmlFieldMap":{key:value,key1:value1}} 一个或多个
	 */
	public String saveXmlFieldMap() {
		try {
			JSONObject parametersJsonObjcet = new JSONObject(parameters);
			JSONArray jsonArray = parametersJsonObjcet.getJSONArray("parameters");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String sourceName = jsonObject.getString("sourceName");
				String typeName = jsonObject.getString("typeName");
				String tableName = jsonObject.getString("tableName");
				JSONObject xmlFieldMapObject = jsonObject.getJSONObject("xmlFieldMap");
				Map<String, String> xmlFieldMap = sourceToMidTableService.jsonObjectToMap(xmlFieldMapObject);
				sourceToMidTableService.saveFieldConfigInfo(sourceName, typeName, tableName, xmlFieldMap);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}	
		jsonMap.put("results", "成功");
		return SUCCESS;
	}
	
	/**
	 * 导入数据源中数据到中间表
	 * parameters格式：jsonObjcet对象{"sourceName":"","typeName":"","toExcludeElementList":["","","",""],"parameters":[{"xmlFieldToPathName":"","tableName":"","subFKName":"","isMainTable":""}]}
	 * 其中，parameters这个key下面的元素构成为jsonArray对象，每一个值为一个json对象，里面包含（"xmlFieldToPathName"、"tableName"、"subFKName"、"isMainTable"）四个key
	 * 备注：主表中，没有subFKName这个key，子表中包含subFKName这个key。
	 * @return
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 * @throws SQLException
	 * @throws ParseException
	 * @throws JSONException 
	 */
	public String importXmlInfoToDB() throws FileNotFoundException, DocumentException, SQLException, ParseException, JSONException{
		//TODO 加校验，判断参数是否合法
		JSONObject parametersJsonObjcet = new JSONObject(parameters);
		String sourceNameString = parametersJsonObjcet.getString("sourceName");
		String typeNameString = parametersJsonObjcet.getString("typeName");
		JSONArray toExcludeElementListArray = parametersJsonObjcet.getJSONArray("toExcludeElementList");
		List<String> toExcludeElementList = new ArrayList<String>();
		for (int i = 0; i < toExcludeElementListArray.length(); i++) {
			toExcludeElementList.add(toExcludeElementListArray.getString(i));
		}
		JSONArray parametersArray = parametersJsonObjcet.getJSONArray("parameters");
		for (int i = 0; i < parametersArray.length(); i++) {
			JSONObject tempParametersObject = new JSONObject(parametersArray.getString(i));
			String xmlFieldToPathNameString = tempParametersObject.getString("xmlFieldToPathName");//xmlFieldToPathNameString
			String tableNameString = tempParametersObject.getString("tableName");//tableNameString
			String subFKNameString = "";
			boolean isMainTable = Integer.parseInt(tempParametersObject.getString("isMainTable")) == 0 ? false : true;//isMainTable
			if (!isMainTable) {
				subFKNameString = tempParametersObject.getString("subFKName");//subFKName
			}
			sourceToMidTableService.importXmlInfoToDB(sourceNameString, typeNameString, xmlFieldToPathNameString, tableNameString, subFKNameString, isMainTable, toExcludeElementList);
		}
		jsonMap.put("results", "成功");
		return SUCCESS;
	}
	
	/**
	 * 创建一个任务，并让该任务成为一个立即执行的事务，并跳转到事务状态页。
	 * @return
	 */
	public String executeImportXmlInfoToDB(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");
		String currentDate = sdf.format(new Date());
		String taskName = sourceName + "_" + typeName + "_" + "importXmlInfoToDB_" + currentDate;
		taskConfigService.createTask("sourceToMidTableAction", "importXmlInfoToDB", parameters, taskName);
		Task task = taskConfigService.gainTaskByTaskName(taskName);
		List<String> taskIds = new ArrayList<String>();
		taskIds.add(task.getId());
		taskConfigService.createManualTaskConfig(taskName, taskIds);
		return SUCCESS;
	}
	
	/**
	 * 获取数据源和数据类型信息，返回值是map类型
	 * @return
	 */
	public String gainSourceNameAndTypeName(){
		Map<String, List<String>> sourceNameAndTypeNamelMap = sourceToMidTableService.gainSourceNameAndTypeName();
		jsonMap.put("sourceNameAndTypeNamelMap", sourceNameAndTypeNamelMap);
		return SUCCESS;
	}
	
	/**
	 * 根据数据源名称，获取与该数据源相关的中间表表名
	 * @return
	 * @throws SQLException
	 */
	public String gainTableNameBySourceName() throws SQLException{
		List<String> tableNameList = sourceToMidTableService.getTableName(sourceName);
		jsonMap.put("tableNameList", tableNameList);
		return SUCCESS;
	}
	
	/**
	 * 任务配置模块，执行任务
	 * parameters格式：jsonObjcet对象{"sourceName":"","typeName":"","toExcludeElementList":["","","",""],"parameters":[{"xmlFieldToPathName":"","tableName":"","subFKName":"","isMainTable":""}]}
	 * 其中，parameters这个key下面的元素构成为jsonArray对象，每一个值为一个json对象，里面包含（"xmlFieldToPathName"、"tableName"、"subFKName"、"isMainTable"）四个key
	 */
	@Override
	public void executeTask(Task task) {
		if (task.getMethodName().equals("importXmlInfoToDB")) {
			String parameters = task.getParameters();
			try {
				this.parameters = parameters;
				importXmlInfoToDB();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	public SourceToMidTableService getSourceToMidTableService() {
		return sourceToMidTableService;
	}

	public void setSourceToMidTableService(SourceToMidTableService sourceToMidTableService) {
		this.sourceToMidTableService = sourceToMidTableService;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getSubTableName() {
		return subTableName;
	}

	public void setSubTableName(String subTableName) {
		this.subTableName = subTableName;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getSubXmlFieldName() {
		return subXmlFieldName;
	}

	public void setSubXmlFieldName(String subXmlFieldName) {
		this.subXmlFieldName = subXmlFieldName;
	}

	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}

	public TaskConfigService getTaskConfigService() {
		return taskConfigService;
	}

	public void setTaskConfigService(TaskConfigService taskConfigService) {
		this.taskConfigService = taskConfigService;
	}
	
}
