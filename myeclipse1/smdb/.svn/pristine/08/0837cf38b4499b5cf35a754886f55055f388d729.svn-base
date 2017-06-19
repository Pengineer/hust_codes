package csdc.service.processing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.FieldConfig;
import csdc.bean.SystemOption;
import csdc.dao.HibernateBaseDao;
import csdc.dao.JdbcDao;
import csdc.service.imp.BaseService;
import csdc.tool.ApplicationContainer;
/**
 * 
 * @author maowh
 *
 */
public class SourceToMidTableService extends BaseService {

	@Autowired
	private JdbcDao jdbcDao;
	@Autowired
	private HibernateBaseDao dao;

	/**
	 * 根据中间表表名，读取数据库中存储的数据
	 * 
	 * @param tableName
	 * @return Map<String, String>
	 */
	public Map<String, String> getXmlFieldFromSMDB(String tableName) {
		List<FieldConfig> result = dao.query("select fc from FieldConfig fc where fc.tableName = '" + tableName + "'");
		if (result != null && result.size() > 0) {
			Map<String, String> xmlFieldMap = new HashMap<String, String>();
			for (FieldConfig fieldConfig : result) {
				xmlFieldMap.put(fieldConfig.getXmlField(), fieldConfig.getField());
			}
			return xmlFieldMap;
		}
		return null;
	}

	/**
	 * 根据中间表表名，读取数据库中存储的数据 map<String,FieldConfig>
	 * 
	 * @param tableName
	 * @return Map<String, FieldConfig>
	 */
	public Map<String, FieldConfig> getXmlFieldConfigFromSMDB(String tableName) {
		List<FieldConfig> result = dao.query("select fc from FieldConfig fc where fc.tableName = '" + tableName + "'");
		if (result != null && result.size() > 0) {
			Map<String, FieldConfig> xmlFieldMap = new HashMap<String, FieldConfig>();
			for (FieldConfig fieldConfig : result) {
				xmlFieldMap.put(fieldConfig.getXmlField(), fieldConfig);
			}
			return xmlFieldMap;
		}
		return null;
	}

	/**
	 * 
	 * @param sourceName 数据来源，社科网等（SINOSS）
	 * @param typeName 数据类型（申请、中检、变更、结项）（application、midInspection、variation、endInspection）
	 * @param tableName 中间表表名 （T_SINOSS_PROJECT_APPLICATION、T_SINOSS_MEMBERS、T_SINOSS_CHECKLOGS）
	 * @param xmlFieldMap xml与数据库字段映射关系
	 */
	public void saveFieldConfigInfo(String sourceName, String typeName, String tableName, Map<String, String> xmlFieldMap) {
		Map<String, FieldConfig> smdbXmlFieldMap = getXmlFieldConfigFromSMDB(tableName);//获取数据库中已经存储的xml字段与数据库字段的对应关系
		if (xmlFieldMap != null && xmlFieldMap.size() > 0) {
			if (smdbXmlFieldMap == null || smdbXmlFieldMap.size() == 0) {
				Set<String> keysSet = xmlFieldMap.keySet();
				Iterator<String> iterator = keysSet.iterator();
				while (iterator.hasNext()) {
					String xmlFieldString = iterator.next();
					FieldConfig fieldConfig = new FieldConfig();
					fieldConfig.setXmlField(xmlFieldString);
					fieldConfig.setField(xmlFieldMap.get(xmlFieldString));
					fieldConfig.setSource(sourceName);
					fieldConfig.setType(typeName);
					fieldConfig.setTableName(tableName);
					dao.add(fieldConfig);
				}
			} else {
				Set<String> smdbXmlFieldKeysSet = smdbXmlFieldMap.keySet();
				Set<String> keysSet = xmlFieldMap.keySet();
				Iterator<String> iterator = keysSet.iterator();
				while (iterator.hasNext()) {
					String xmlFieldString = iterator.next();
					FieldConfig fieldConfig;
					if (smdbXmlFieldKeysSet.contains(xmlFieldString)) {// 如果数据库中有该记录，则修改；没有则添加
						fieldConfig = smdbXmlFieldMap.get(xmlFieldString);
					} else {
						fieldConfig = new FieldConfig();
					}
					fieldConfig.setXmlField(xmlFieldString);
					fieldConfig.setField(xmlFieldMap.get(xmlFieldString));
					fieldConfig.setSource(sourceName);
					fieldConfig.setType(typeName);
					fieldConfig.setTableName(tableName);
					dao.addOrModify(fieldConfig);
				}
			}
		}
	}
	
	/**
	 * JsonObject对象转换为Map<String,String>对象
	 * @param jsonObject
	 * @return
	 * @throws JSONException
	 */
	public Map jsonObjectToMap(JSONObject jsonObject) throws JSONException {
        Map result = new HashMap();
        Iterator iterator = jsonObject.keys();
        String key = null;
        String value = null;
        while (iterator.hasNext()) {
            key = (String) iterator.next();
            value = jsonObject.getString(key);
            result.put(key, value);
        }
        return result;
    }
		
	/**
	 * 获取待排除的xml中的节点值
	 * @param resultElementTreeSet xml中，包含子节点的节点树中的节点名称
	 * @param trueElementTreeSet 需要显示的，有中间表对应的xml节点树中的节点名称集合
	 * @return
	 */
	public List<String> gainToExcludeElementList(Set<String> resultElementTreeSet, Set<String> trueElementTreeSet){
		List<String> toExcludeElementList = new ArrayList<String>();
		Iterator<String> resultElementTreeIterator = resultElementTreeSet.iterator();
		while(resultElementTreeIterator.hasNext()){
			String toExcludeElementName = resultElementTreeIterator.next();
			if (!trueElementTreeSet.contains(toExcludeElementName)) {
				toExcludeElementList.add(toExcludeElementName);
			}
		}
		return toExcludeElementList;
	}
	
	/**
	 * 获取需要展示在前台界面的，有数据的节点树
	 * @param sourceName 数据源：SINOSS等
	 * @param typeName 数据类型：application、midinspection、endinspection、variation等
	 * @param resultElementTreeMap xml中，包含子节点的节点树
	 * @param trueElementTreeSet 需要显示的，有中间表对应的xml节点树中的节点名称集合
	 * @param xmlFieldNameToPathMap xml中，节点名称与该节点在xml中的路径的对应关系(result:records/result) 便于通过selectNodes的方式，直接找到xml文件中，所有的该节点
	 * @return
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	public Map<String, Map> gainTrueElementTreeMap(String sourceName, String typeName, Map<String, Map> resultElementTreeMap, Set<String> trueElementTreeSet, Map<String, String> xmlFieldNameToPathMap) throws FileNotFoundException, DocumentException{
		Map<String, Map> trueElementTreeMap = new HashMap<String, Map>();
		Set<String> keySet = resultElementTreeMap.keySet();
		Map<String, Map> tempMap = null;
		Iterator<String> keyIterator = keySet.iterator();
		while(keyIterator.hasNext()){
			String tempKeyString = keyIterator.next();
			if (containsIdSubNode(sourceName, typeName, tempKeyString, xmlFieldNameToPathMap)) {
				if (tempMap == null) {
					tempMap = new HashMap<String, Map>();
				}
				trueElementTreeMap.put(tempKeyString, tempMap);
				trueElementTreeSet.add(tempKeyString);
				gainSubTrueElementTreeMap(sourceName,typeName, resultElementTreeMap.get(tempKeyString),tempMap, trueElementTreeSet, xmlFieldNameToPathMap);
			}else {
				gainSubTrueElementTreeMap(sourceName, typeName, resultElementTreeMap.get(tempKeyString), trueElementTreeMap, trueElementTreeSet, xmlFieldNameToPathMap);
			}
		}
		return trueElementTreeMap;
	}
	
	public void gainSubTrueElementTreeMap(String sourceName, String typeName, Map<String, Map> subResultElementTreeMap, Map<String, Map> resultTreeMap, Set<String> trueElementTreeSet, Map<String, String> xmlFieldNameToPathMap) throws FileNotFoundException, DocumentException{
		Set<String> keySet = subResultElementTreeMap.keySet();
		Map<String, Map> tempMap = null;
		Iterator<String> keyIterator = keySet.iterator();
		while(keyIterator.hasNext()){
			String tempKeyString = keyIterator.next();
			if (containsIdSubNode(sourceName, typeName, tempKeyString, xmlFieldNameToPathMap)) {
				if (tempMap == null) {
					tempMap = new HashMap<String, Map>();
				}
				resultTreeMap.put(tempKeyString, tempMap);
				trueElementTreeSet.add(tempKeyString);
				gainSubTrueElementTreeMap(sourceName, typeName, subResultElementTreeMap.get(tempKeyString), tempMap, trueElementTreeSet, xmlFieldNameToPathMap);
			}else {
				gainSubTrueElementTreeMap(sourceName,typeName, subResultElementTreeMap.get(tempKeyString), resultTreeMap, trueElementTreeSet, xmlFieldNameToPathMap);
			}
		}
	}
	
	/**
	 * 按照前端D3展示需求，转换为前端所需格式
	 * 返回值格式为：[{name=result, children=[{name=ProjectApplyMember, children=[]}, {name=CheckLog, children=[]}]}]
	 * @param trueElementTreeMap
	 * @return
	 */
	public List<Map> gainResultMapList(Map<String, Map> trueElementTreeMap){
		List<Map> resultMapList = new ArrayList<Map>();
		Iterator<String> keyIterator = trueElementTreeMap.keySet().iterator();
		while(keyIterator.hasNext()){
			Map tempMap = new HashMap();
			String trueElementNameString = keyIterator.next();
			tempMap.put("name", trueElementNameString);
			List<Map> tempMapList = new ArrayList<Map>();
			tempMap.put("children", tempMapList);
			resultMapList.add(tempMap);
			gainSubResultMapList(trueElementTreeMap.get(trueElementNameString), tempMapList);
		}
		return resultMapList;
	}
	
	public void gainSubResultMapList(Map<String, Map> trueElementTreeMap,List<Map> trueListMap){
		Iterator<String> keyIterator = trueElementTreeMap.keySet().iterator();
		while(keyIterator.hasNext()){
			Map tempMap = new HashMap();
			String trueElementNameString = keyIterator.next();
			tempMap.put("name", trueElementNameString);
			List<Map> tempMapList = new ArrayList<Map>();
			tempMap.put("children", tempMapList);
			trueListMap.add(tempMap);
			gainSubResultMapList(trueElementTreeMap.get(trueElementNameString), tempMapList);
		}
	}
	
	/**
	 * 判断该节点的子节点是否包含id元素。
	 * 通过该方法，确认该节点是否需要显示到前台的节点树中
	 * @param sourceName 数据源：SINOSS等
	 * @param typeName 数据类型：application、midinspection、endinspection、variation等
	 * @param nodeName xml中的节点名称，例如：result,SinossApplyMember,CheckLog等
	 * @param xmlFieldNameToPathMap xml中，节点名称与该节点在xml中的路径的对应关系(result:records/result) 便于通过selectNodes的方式，直接找到xml文件中，所有的该节点
	 * @return
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	public boolean containsIdSubNode(String sourceName, String typeName, String nodeName, Map<String, String> xmlFieldNameToPathMap) throws FileNotFoundException, DocumentException{
		boolean result = false;
		String directoryPath = ApplicationContainer.sc.getRealPath("/system/interfaces/sinossResource/");
		directoryPath = directoryPath.replace('\\', '/');
		File fileDirectory = new File(directoryPath);
		File[] tempList = fileDirectory.listFiles();
		for (File file : tempList) {
			if (file.getName().contains(sourceName + "_" + typeName)) {
				SAXReader reader = new SAXReader();
				InputStream in = new FileInputStream(file);
				Document doc = reader.read(in);
				Element rootElement = doc.getRootElement();
				List<Element> noteElemntList = rootElement.selectNodes(xmlFieldNameToPathMap.get(nodeName));
				for (Element element : noteElemntList) {
					List<Element> subElements = element.elements();
					for (Element element2 : subElements) {
						if (element2.getName().equals("id")) {
							return true;
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * 获取xml文档中，节点与路径的关系
	 * 例如：
	 * records:records
	 * result:records/result
	 * members:records/result/members
	 * 便于通过selectNodes的方式，直接找到xml文件中，所有的该节点
	 * @param resultElementTreeMap
	 * @return
	 */
	public Map<String, String> gainXmlFieldPath(Map<String, Map> resultElementTreeMap){
		Map<String, String> xmlFieldNameToPathMap = new HashMap<String, String>();
		Set<String> keySet = resultElementTreeMap.keySet();
		Iterator<String> keyIterator = keySet.iterator();
		while(keyIterator.hasNext()){
			String tempPathString = keyIterator.next();
			xmlFieldNameToPathMap.put(tempPathString, tempPathString);
			gainSubXmlFieldPath(resultElementTreeMap.get(tempPathString),tempPathString, xmlFieldNameToPathMap);
		}
		return xmlFieldNameToPathMap;
	}
	
	public void gainSubXmlFieldPath(Map<String,Map> tempElementTreeMap, String preFix , Map<String, String> xmlFieldNameToPathMap){
		Set<String> keySet = tempElementTreeMap.keySet();
		Iterator<String> keyIterator = keySet.iterator();
		while(keyIterator.hasNext()){
			String tempPathString = keyIterator.next();
			xmlFieldNameToPathMap.put(tempPathString, preFix + "/" + tempPathString);
			gainSubXmlFieldPath(tempElementTreeMap.get(tempPathString),preFix + "/" + tempPathString, xmlFieldNameToPathMap);
		}
	}
	
	/**
	 * 创建写入数据库的语句，并写入数据到数据库中
	 * @param sourceName 数据源：SINOSS等
	 * @param typeName 数据类型：application、midinspection、endinspection、variation等
	 * @param xmlFieldToPathName： "records/result/checkLogs/CheckLog" 该节点在xml中的路径的对应关系(result:records/result) 便于通过selectNodes的方式，直接找到xml文件中，所有的该节点
	 * @param tableName："T_SINOSS_CHECKLOGS"  表名
	 * @param FKName：C_PROJECT_APPLICATION_ID 外键关系
	 * @param isMainTable：false 是否主表
	 * @param toExcludeElementList 待排除的xml中节点值。例如：members,checkLogs等
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void importXmlInfoToDB(String sourceName, String typeName, String xmlFieldToPathName, String tableName, String FKName,boolean isMainTable, List<String> toExcludeElementList) throws FileNotFoundException, DocumentException, SQLException, ParseException{
		Map<String, String> xmlFieldMap = gainXMLFieldFromXMLName(sourceName, typeName, xmlFieldToPathName, toExcludeElementList, tableName);
		Connection conn = jdbcDao.getJdbcTemplate().getDataSource().getConnection();
		String directoryPath = ApplicationContainer.sc.getRealPath("/system/interfaces/sinossResource/");
		directoryPath = directoryPath.replace('\\', '/');
		File fileDirectory = new File(directoryPath);
		File[] tempList = fileDirectory.listFiles();
		for (File file : tempList) {
			if (file.getName().contains(sourceName + "_" + typeName)) {
				SAXReader reader = new SAXReader();
				InputStream in = new FileInputStream(file);
				Document doc = reader.read(in);
				Element rootElement = doc.getRootElement();
				List<Element> resultElements = rootElement.selectNodes(xmlFieldToPathName);	
				StringBuffer insertSQL;
				StringBuffer cloumnSQL;
				StringBuffer valueSQL;
				Statement stm = conn.createStatement();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				boolean autoCommitBool = conn.getAutoCommit();
				conn.setAutoCommit(false);
				for (Element element : resultElements) {
					List<Element> subXmlArray = element.elements();
					insertSQL = new StringBuffer();
					cloumnSQL = new StringBuffer();
					valueSQL = new StringBuffer();
					insertSQL.append("insert into " + tableName + " (");
					for (Element subXmls : subXmlArray) {
						if (toExcludeElementList != null && toExcludeElementList.contains(subXmls.getName())) {
							continue;
						}
						cloumnSQL.append(xmlFieldMap.get(subXmls.getName()) + ",");
						if (subXmls.getName().contains("date") || subXmls.getName().contains("Date") || subXmls.getName().contains("birthday") || subXmls.getName().contains("Birthday")) {
							valueSQL.append("TO_DATE('" + new java.sql.Date(sdf.parse(subXmls.getText().substring(0, 10)).getTime()) + "','yyyy-mm-dd'),");
						} else if (subXmls.getName().contains("checkStatus")) {
							valueSQL.append(Integer.parseInt(subXmls.getText()) + ",");
						} else {
							valueSQL.append("'" + subXmls.getText() + "',");
						}
					}
					if (!isMainTable) {
						// 填入子表与主表的外键关系
						cloumnSQL.append(FKName + ",");
						valueSQL.append("'" + element.getParent().getParent().element("id").getText() + "',");
					}

					insertSQL.append(cloumnSQL.toString().substring(0, cloumnSQL.toString().length() - 1) + ") VALUES (" + valueSQL.toString().substring(0, valueSQL.toString().length() - 1) + ")");
					String insertString = insertSQL.toString();
					System.out.println(insertString);
					stm.addBatch(insertSQL.toString()); 
				}
				
				try {
					stm.executeBatch(); 
					conn.commit();
				} catch (Exception e) {
	                if (!conn.isClosed()) {  
	                	conn.rollback();  
	                    conn.setAutoCommit(autoCommitBool);  
	                }  
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 获取xml与数据库对应字段关系
	 * @param sourceName 数据源：SINOSS等
	 * @param typeName 数据类型：application、midinspection、endinspection、variation等
	 * @param xmlFieldToPathName "records/result/checkLogs/CheckLog" 该节点在xml中的路径的对应关系(result:records/result) 便于通过selectNodes的方式，直接找到xml文件中，所有的该节点
	 * @param toExcludeElementList 待排除的节点名。例如：members,checkLogs
	 * @param tableName 表名
	 * @return
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 * @throws SQLException 
	 */
	public Map<String, String> gainXMLFieldFromXMLName(String sourceName, String typeName, String xmlFieldToPathName, List<String> toExcludeElementList, String tableName) throws FileNotFoundException, DocumentException, SQLException{
		//首先从数据库中获取，如果数据库中不存在，则从xml文件读取
		Map<String, String> resultMap = getXmlFieldFromSMDB(tableName);
		if (resultMap != null && resultMap.size() > 0) {
			return resultMap;
		}
		resultMap = new HashMap<String, String>();
		String directoryPath = ApplicationContainer.sc.getRealPath("/system/interfaces/sinossResource/");
		directoryPath = directoryPath.replace('\\', '/');
		File fileDirectory = new File(directoryPath);
		File[] tempList = fileDirectory.listFiles();
		List<String> dbFieldList = gainDBFieldByTableName(tableName);
		for (File file : tempList) {
			if (file.getName().contains(sourceName + "_" + typeName)) {
				SAXReader reader = new SAXReader();
				InputStream in = new FileInputStream(file);
				Document doc = reader.read(in);
				Element rootElement = doc.getRootElement();
				List<Element> resultElements = rootElement.selectNodes(xmlFieldToPathName);	
				for (Element element : resultElements) {
					List<Element> tempElements = element.elements();
					for (Element element2 : tempElements) {
						if (toExcludeElementList.contains(element2.getName())) {
							continue;
						}
						if (!resultMap.containsKey(element2.getName())) {
							resultMap.put(element2.getName(), gainDBFieldByXmlField(element2.getName(), dbFieldList));
						}
					}
				}
			}
		}
		return resultMap;
	}
	
	/**
	 * 根据xml中的字段名，寻找数据库中的字段名，判断是否有相似的
	 * @param xmlField
	 * @param dbFieldList
	 * @return
	 */
	public String gainDBFieldByXmlField(String xmlField, List<String> dbFieldList){
		for (String string : dbFieldList) {
			String tempString = string.replaceAll("_", "");
			if (tempString.contains("C" + xmlField.toUpperCase())) {
				return string;
			}
		}
		return "";
	}
	
	/**
	 * 生成xml节点树
	 * @param sourceName 数据源：SINOSS等
	 * @param typeName 数据类型：application、midinspection、endinspection、variation等
	 * @param resultElementTreeSet:
	 * @return
	 * 例如：{records={result={checkLogs={CheckLog={}}, members={ProjectApplyMember={thirdChildrens={thirdChildren={}}}}}}}
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	public Map<String, Map> gainElementTree(String sourceName, String typeName, Set<String> resultElementTreeSet) throws FileNotFoundException, DocumentException{
		String directoryPath = ApplicationContainer.sc.getRealPath("/system/interfaces/sinossResource/");
		directoryPath = directoryPath.replace('\\', '/');
		File fileDirectory = new File(directoryPath);
		File[] tempList = fileDirectory.listFiles();
		Map<String, Set<String>> elementTreeMap = new HashMap<String, Set<String>>();
		for (File file : tempList) {
			if (file.getName().contains(sourceName + "_" + typeName)) {
				SAXReader reader = new SAXReader();
				InputStream in = new FileInputStream(file);
				Document doc = reader.read(in);
				Element rootElement = doc.getRootElement();
				List<Element> subRootElements = rootElement.elements();
				for (Element element : subRootElements) {
					if (containSubElements(element)) {
						Set<String> stringSet = elementTreeMap.get("firstElement");
						if (stringSet == null) {
							stringSet = new HashSet<String>();
						}
						stringSet.add(element.getName());
						elementTreeMap.put("firstElement", stringSet);
						addAllSubElements(elementTreeMap, element);
					}
				}
			}
		}
		return parseElementTreeMap(elementTreeMap, resultElementTreeSet);
	}
	
	//加入所有包含子节点的节点值
	public void addAllSubElements(Map<String, Set<String>> elementTreeMap, Element element){
		List<Element> subElements = element.elements();
		Set<String> stringSet = elementTreeMap.get(element.getName());
		if (stringSet == null) {
			stringSet = new HashSet<String>();
		}
		for (Element subElement : subElements) {
			if (containSubElements(subElement)) {
				addAllSubElements(elementTreeMap, subElement);
				stringSet.add(subElement.getName());
			}
		}
		elementTreeMap.put(element.getName(), stringSet);
	}

	/**
	 * 解析节点树的map，生成可视的节点树
	 * @param elementTreeMap
	 * @param resultElementTreeSet
	 * @return
	 */
	public Map<String, Map> parseElementTreeMap(Map<String, Set<String>> elementTreeMap, Set<String> resultElementTreeSet){
		Map<String, Map> resultElementTreeMap = new HashMap<String, Map>();
		if (elementTreeMap != null && elementTreeMap.size() > 0) {
			Set<String> firstElementSet = elementTreeMap.get("firstElement");
			Iterator<String> iterator = firstElementSet.iterator();
			while(iterator.hasNext()){
				String aString = iterator.next();
				Map<String, Map> tempMap = new HashMap<String, Map>();
				resultElementTreeMap.put(aString, tempMap);
				resultElementTreeSet.add(aString);
				parseSubElementTreeMap(elementTreeMap, aString, tempMap, resultElementTreeSet);
			}
		}
		return resultElementTreeMap;
	}
	
	//递归调用，加入节点map
	public void parseSubElementTreeMap(Map<String, Set<String>> elementTreeMap, String tempString, Map<String, Map> tempMap, Set<String> resultElementTreeSet){
		Set<String> subElementSet = elementTreeMap.get(tempString);
		if (subElementSet != null && subElementSet.size() > 0) {
			Iterator<String> iterator = subElementSet.iterator();
			while(iterator.hasNext()){
				String bString = iterator.next();
				Map<String, Map> tempMap1 = new HashMap<String, Map>();
				tempMap.put(bString, tempMap1);
				resultElementTreeSet.add(bString);
				parseSubElementTreeMap(elementTreeMap, bString, tempMap1, resultElementTreeSet);
			}
		}
	}
	
	/**
	 * 判断该节点是否有子节点
	 * @param element
	 * @return
	 */
	public boolean containSubElements(Element element){
		boolean result = false;
		List<Element> subElements = element.elements();
		if (subElements != null && subElements.size() > 0) {
			return true;
		}
		return result;
	}
	
	/**
	 * 根据表名，获取该表的所有字段名
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
    public List<String> gainDBFieldByTableName(String tableName) throws SQLException{
		String jndiname = getPropertiesValue(getClass(), "/init.properties", "jndiname");
		jndiname = jndiname.substring(jndiname.lastIndexOf("/") + 1);
    	String Owner = jndiname.toUpperCase();       
        List<String> tableColumnInfoList = new ArrayList<String>();
        try{
            Statement stmt = jdbcDao.getJdbcTemplate().getDataSource().getConnection().createStatement();
            String sql=
             "select "+
             "         comments as \"Name\","+
             "         a.column_name \"Code\","+
             "         a.DATA_TYPE as \"DataType\","+
             "         b.comments as \"Comment\","+
             "         decode(c.column_name,null,'FALSE','TRUE') as \"Primary\","+
             "         decode(a.NULLABLE,'N','TRUE','Y','FALSE','') as \"Mandatory\","+
             "         '' \"sequence\""+
             "   from "+
             "       all_tab_columns a, "+
             "       all_col_comments b,"+
             "       ("+
             "        select a.constraint_name, a.column_name"+
             "          from user_cons_columns a, user_constraints b"+
             "         where a.constraint_name = b.constraint_name"+
             "               and b.constraint_type = 'P'"+
             "               and a.table_name = '"+tableName+"'"+
             "       ) c"+
             "   where "+
             "     a.Table_Name=b.table_Name "+
             "     and a.column_name=b.column_name"+
             "     and a.Table_Name='"+tableName+"'"+
             "     and a.owner=b.owner "+
             "     and a.owner='"+Owner+"'"+
             "     and a.COLUMN_NAME = c.column_name(+)" +
             "  order by a.COLUMN_ID";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                tableColumnInfoList.add(rs.getString("Code"));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return tableColumnInfoList;
    }
	
    /**
     * 获取指定数据源的所有表名
     * @param sourceName 数据源：SINOSS等
     * @return
     * @throws SQLException
     */
    public List<String> getTableName(String sourceName) throws SQLException{
    	List<String> tableNameList = new ArrayList<String>();
    	try {
    		DatabaseMetaData meta = jdbcDao.getJdbcTemplate().getDataSource().getConnection().getMetaData(); 
    		ResultSet rs = meta.getTables(null, null, null, new String[] {"TABLE"});
    		String jndiname = getPropertiesValue(getClass(), "/init.properties", "jndiname");
    		jndiname = jndiname.substring(jndiname.lastIndexOf("/") + 1);
    		while(rs.next()){//根据表所属用户名，以及表的前缀名，获取数据库中，指定数据源的所有表名
    			if (rs.getString(2).equals(jndiname.toUpperCase()) && rs.getString(3).contains(sourceName)) {
					tableNameList.add(rs.getString(3));
				}
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return tableNameList;
    }
    
    /**
     * 获取数据源名和数据格式名
     * 格式为：{“SINOSS”:["application","midinspection","variation","endinspection"]}
     * @return
     */
    public Map<String, List<String>> gainSourceNameAndTypeName(){
    	List<SystemOption> sourceNameOptions = dao.query("select so from SystemOption so where so.systemOption.name = '数据源'");
    	Map<String, List<String>> sourceNameAndTypeNameMap = new HashMap<String, List<String>>();
    	for (SystemOption systemOption : sourceNameOptions) {
			List<SystemOption> typeNameOptions = dao.query("select so from SystemOption so where so.systemOption.name = '" + systemOption.getName() + "'");
			List<String> typeNameList = new ArrayList<String>();
			for (SystemOption systemOption2 : typeNameOptions) {
				typeNameList.add(systemOption2.getName());
			}
			sourceNameAndTypeNameMap.put(systemOption.getName(), typeNameList);
		}
    	return sourceNameAndTypeNameMap;
    }
   
}

