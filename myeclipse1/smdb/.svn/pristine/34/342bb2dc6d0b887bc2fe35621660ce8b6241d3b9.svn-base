package csdc.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * XML工具类，可以自动生成学校、职称、学科代码等标准信息的XML文件，并可以随意配置组合。
 * @author leida
 */
public class StandardXMLDataInput {
	private static Connection conn = null; //数据库连接类
	
	private static Map<String, String> projectApplicationTemplateMap;//申请项目的节点名称与数据库字段名称对应关系模板
	private static Map<String, String> projectApplyMemberTemplateMap;//成员的节点名称与数据库字段名称对应关系模板
	private static Map<String, String> checkLogTemplateMap;//审核信息的节点名称与数据库字段名称对应关系模板
	private static int containsMembers;//该项目是否包含项目成员信息
	private static int containsCheckLogs;//该项目是否包含审核信息
	private static String tableName = "T_SINOSS_PROJECT_APPLICATION";//当前导入的表名
	
	public static void initProjectApplicationTemplate(){
		if (projectApplicationTemplateMap == null) {
			projectApplicationTemplateMap = new HashMap<String, String>();
		}
		projectApplicationTemplateMap.put("id", "C_ID");
		projectApplicationTemplateMap.put("projectName", "C_PROJECT_NAME");
		projectApplicationTemplateMap.put("typeCode", "C_TYPE_CODE");
		projectApplicationTemplateMap.put("projectType", "C_PROJECT_TYPE");
		projectApplicationTemplateMap.put("applyDate", "C_APPLY_DATE");
		projectApplicationTemplateMap.put("planFinishDate", "C_PLANFINISH_DATE");
		projectApplicationTemplateMap.put("subject", "C_SUBJECT");
		projectApplicationTemplateMap.put("subject1_1", "C_SUBJECT1_1");
		projectApplicationTemplateMap.put("subject1_2", "C_SUBJECT1_2");
		projectApplicationTemplateMap.put("researchDirection", "C_RESEARCH_DIRECTION");
		projectApplicationTemplateMap.put("researchType", "C_RESEARCH_TYPE");
		projectApplicationTemplateMap.put("lastProductMode", "C_LAST_PRODUCT_MODE");
		projectApplicationTemplateMap.put("applyTotalFee", "C_APPLY_TOTAL_FEE");
		projectApplicationTemplateMap.put("otherFeeSource", "C_OTHER_FEE_SOURCE");
		projectApplicationTemplateMap.put("applyer", "C_APPLYER");
		projectApplicationTemplateMap.put("gender", "C_GENDER");
		projectApplicationTemplateMap.put("birthday", "C_BIRTHDAY");
		projectApplicationTemplateMap.put("IDCardNo", "C_IDCARDNO");
		projectApplicationTemplateMap.put("title", "C_TITLE");
		projectApplicationTemplateMap.put("division", "C_DIVISION");
		projectApplicationTemplateMap.put("duty", "C_DUTY");
		projectApplicationTemplateMap.put("words", "C_WORDS");
		projectApplicationTemplateMap.put("eduLevel", "C_EDULEVEL");
		projectApplicationTemplateMap.put("eduDegree", "C_EDUDEGREE");
		projectApplicationTemplateMap.put("language", "C_LANGUAGE");
		projectApplicationTemplateMap.put("email", "C_EMAIL");
		projectApplicationTemplateMap.put("address", "C_ADDRESS");
		projectApplicationTemplateMap.put("postalCode", "C_POSTAL_CODE");
		projectApplicationTemplateMap.put("telOffice", "C_TEL_OFFICE");		
		projectApplicationTemplateMap.put("tel", "C_TEL");
		projectApplicationTemplateMap.put("checkStatus", "C_CHECK_STATUS");
		projectApplicationTemplateMap.put("attachmentName", "C_ATTACHMENT_NAME");
		projectApplicationTemplateMap.put("checkDate", "C_CHECK_DATE");
		projectApplicationTemplateMap.put("checker", "C_CHECKER");
		projectApplicationTemplateMap.put("applyDocName", "C_APPLY_DOCNAME");
		projectApplicationTemplateMap.put("unitCode", "C_UNITCODE");
		projectApplicationTemplateMap.put("unitName", "C_UNITNAME");
		projectApplicationTemplateMap.put("batchId", "C_BATCH_ID");
		projectApplicationTemplateMap.put("year", "C_YEAR");
		projectApplicationTemplateMap.put("batchName", "C_BATCH_NAME");
		projectApplicationTemplateMap.put("beginDate", "C_BEGIN_DATE");
		projectApplicationTemplateMap.put("endDate", "C_END_DATE");
	}
	
	public static void initProjectApplyMemberTemplate(){
		if (projectApplyMemberTemplateMap == null) {
			projectApplyMemberTemplateMap = new HashMap<String, String>();
		}
		projectApplyMemberTemplateMap.put("id", "C_ID");
		projectApplyMemberTemplateMap.put("memberCode", "C_CODE");
		projectApplyMemberTemplateMap.put("memberName", "C_NAME");
		projectApplyMemberTemplateMap.put("memberUnit", "C_UNIT_NAME");
		projectApplyMemberTemplateMap.put("memberTitle", "C_TITLE");
		projectApplyMemberTemplateMap.put("memberBirthday", "C_BIRTHDAY");
		projectApplyMemberTemplateMap.put("memberSpecialty", "C_SPECIALTY");
		projectApplyMemberTemplateMap.put("memberDivide", "C_DIVIDE");
		projectApplyMemberTemplateMap.put("orders", "C_ORDERS");
		projectApplyMemberTemplateMap.put("memberEduDegree", "C_EDU_DEGREE");
	}
	
	public static void initCheckLogTemplate(){
		if (checkLogTemplateMap == null) {
			checkLogTemplateMap = new HashMap<String, String>();
		}
		checkLogTemplateMap.put("id", "C_ID");
		checkLogTemplateMap.put("checkStatus", "C_CHECK_STATUS");
		checkLogTemplateMap.put("checkDate", "C_CHECK_DATE");
		checkLogTemplateMap.put("checker", "C_CHECKER");
		checkLogTemplateMap.put("checkInfo", "C_CHECK_INFO");
	}
	
	public static void main(String[] args) throws SQLException, IOException, DocumentException, ParseException {
		File file = new File("E:\\2014gener_27.xml");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		initCheckLogTemplate();
		initProjectApplicationTemplate();
		initProjectApplyMemberTemplate();
		
		SAXReader reader = new SAXReader();
		InputStream in = new FileInputStream(file);
		Document doc = reader.read(in);
		Element rootElement = doc.getRootElement();
		Element records = rootElement.element("records");
		List<Element> resultsElements = records.elements();
		StringBuffer insertSQL;
		StringBuffer cloumnSQL;
		StringBuffer valueSQL;
		
        PreparedStatement ps = null; 
        createConnection();
		for (Element element : resultsElements) {
			containsMembers = 0;
			containsCheckLogs = 0;
			
			insertSQL = new StringBuffer();
			cloumnSQL = new StringBuffer();
			valueSQL = new StringBuffer();
			
			insertSQL.append("insert into " + tableName + " (");
			
			List<Element> resulteElements = element.elements();
			for (Element element2 : resulteElements) {
				if (element2.getName().equals("members")) {
					containsMembers = 1;
					continue;
				}
				if (element2.getName().equals("checkLogs")) {
					containsCheckLogs = 1;
					continue;
				}
//				System.out.println(element2.getName());
//				System.out.println(element2.getText());

				cloumnSQL.append(projectApplicationTemplateMap.get(element2.getName()) + ",");
				valueSQL.append("?,");
			}
			insertSQL.append(cloumnSQL.toString().substring(0, cloumnSQL.toString().length() - 1) + ") VALUES (" + valueSQL.toString().substring(0, valueSQL.toString().length() - 1) + ")");
			String sql = insertSQL.toString();
			
			ps = conn.prepareStatement(sql);
			int index = 1;
			for (Element element2 : resulteElements) {
				if (element2.getName().equals("members")) {
					continue;
				}
				if (element2.getName().equals("checkLogs")) {
					continue;
				}
				if (element2.getName().contains("date") || element2.getName().contains("Date") || element2.getName().contains("birthday") || element2.getName().contains("Birthday")) {
					ps.setDate(index,new java.sql.Date(sdf.parse(element2.getText().substring(0, 10)).getTime()));
				}else if (element2.getName().contains("checkStatus")) {
					 ps.setInt(index, Integer.parseInt(element2.getText()));
				}else {
					ps.setString(index, element2.getText());
				}
				index++;
			}
			try {
				conn.setAutoCommit(false);
				ps.executeUpdate();
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			}finally{
				ps.close();
			}
			
			
			System.out.println("--------------------------------------------------------");
			if (containsMembers != 0) {
				Element members = element.element("members");
				List<Element> membersList = members.elements();
				for (Element projectApplyMember : membersList) {
					
					insertSQL = new StringBuffer();
					cloumnSQL = new StringBuffer();
					valueSQL = new StringBuffer();
					insertSQL.append("insert into T_SINOSS_MEMBERS (");
					
					List<Element> projectApplyerMemberInfo = projectApplyMember.elements();
					for (Element element3 : projectApplyerMemberInfo) {
//						System.out.println(element3.getName());
//						System.out.println(element3.getText());
						
						cloumnSQL.append(projectApplyMemberTemplateMap.get(element3.getName()) + ",");
						valueSQL.append("?,");
					}
					
					if (tableName.equals("T_SINOSS_PROJECT_APPLICATION")) {
						cloumnSQL.append("C_PROJECT_APPLICATION_ID,");
						valueSQL.append("?,");
					}
					
					insertSQL.append(cloumnSQL.toString().substring(0, cloumnSQL.toString().length() - 1) + ") VALUES (" + valueSQL.toString().substring(0, valueSQL.toString().length() - 1) + ")");
					sql = insertSQL.toString();
					
					ps = conn.prepareStatement(sql);
					index = 1;
					for (Element element3 : projectApplyerMemberInfo) {
						if (element3.getName().equals("members")) {
							continue;
						}
						if (element3.getName().equals("checkLogs")) {
							continue;
						}
						if (element3.getName().contains("date") || element3.getName().contains("Date") || element3.getName().contains("birthday") || element3.getName().contains("Birthday")) {
							ps.setDate(index,new java.sql.Date(sdf.parse(element3.getText().substring(0, 10)).getTime()));
						}else if (element3.getName().contains("checkStatus")) {
							 ps.setInt(index, Integer.parseInt(element3.getText()));
						}else {
							ps.setString(index, element3.getText());
						}
						index++;
					}
					ps.setString(index, element.element("id").getText());
					try {
						conn.setAutoCommit(false);
						ps.executeUpdate();
						conn.commit();
						ps.close();
					} catch (Exception e) {
						conn.rollback();
						e.printStackTrace();
					}
				}
			}
			
			System.out.println("--------------------------------------------------------");
			if (containsCheckLogs != 0) {
				Element checkLogs = element.element("checkLogs");
				List<Element> checkLogsList = checkLogs.elements();
				for (Element checkLog : checkLogsList) {
					
					insertSQL = new StringBuffer();
					cloumnSQL = new StringBuffer();
					valueSQL = new StringBuffer();
					insertSQL.append("insert into T_SINOSS_CHECKLOGS (");
					
					List<Element> checkLogInfo = checkLog.elements();
					for (Element element3 : checkLogInfo) {
//						System.out.println(element3.getName());
//						System.out.println(element3.getText());
					
						cloumnSQL.append(checkLogTemplateMap.get(element3.getName()) + ",");
						valueSQL.append("?,");
					}
					if (tableName.equals("T_SINOSS_PROJECT_APPLICATION")) {
						cloumnSQL.append("C_PROJECT_APPLICATION_ID,");
						valueSQL.append("?,");
					}
					insertSQL.append(cloumnSQL.toString().substring(0, cloumnSQL.toString().length() - 1) + ") VALUES (" + valueSQL.toString().substring(0, valueSQL.toString().length() - 1) + ")");
					sql = insertSQL.toString();
					
					ps = conn.prepareStatement(sql);
					index = 1;
					for (Element element3 : checkLogInfo) {
						if (element3.getName().equals("members")) {
							continue;
						}
						if (element3.getName().equals("checkLogs")) {
							continue;
						}
						//checklog消息中，checkstatus不是number型。
						if (element3.getName().contains("date") || element3.getName().contains("Date") || element3.getName().contains("birthday") || element3.getName().contains("Birthday")) {
							ps.setDate(index,new java.sql.Date(sdf.parse(element3.getText().substring(0, 10)).getTime()));
						}else{
							ps.setString(index, element3.getText());
						}
						index++;
					}
					ps.setString(index, element.element("id").getText());
					try {
						conn.setAutoCommit(false);
						ps.executeUpdate();
						conn.commit();
						ps.close();
					} catch (Exception e) {
						conn.rollback();
						e.printStackTrace();
					}
				}
			}
		}
		
//		executeInsertAndUpdate("insert into t_test(c_id,c_name,c_date) values('1254','wangst',to_date('2002-02-28','yyyy-mm-dd'))");
		conn.close();
		System.out.println("ok!");
	}
	

	
	/**
	 * 执行插入
	 * @param sql
	 * @return
	 */
	public static boolean executeInsert(String sql) {
		boolean rel;
		if(conn == null) {
			createConnection();
		}
		try {
			Statement stmt = conn.createStatement();
			rel = stmt.execute(sql);
			conn.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return rel;
	}
	
	/**
	 * 执行插入更新
	 * @param sql
	 * @return
	 */
	public static int executeInsertAndUpdate(String sql) {
		int rel;
		if(conn == null) {
			createConnection();
		}
		try {
			Statement stmt = conn.createStatement();
			rel = stmt.executeUpdate(sql);
			conn.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return rel;
	}
	
	/**
	 * 执行sql查询并返回结果
	 * @param sql 待查询的sql语句
	 * @return 结果的ResultSet对象
	 */
	public static ResultSet executeQuery(String sql) {
		ResultSet rs;
		if(conn == null) {
			createConnection();
		}
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		}
		catch (Exception e) {
			return null;
		}
		return rs;
	}
	
	/**
	 * 建立数据库连接
	 */
	public static void createConnection() {
		final String sDBDriver = "oracle.jdbc.xa.client.OracleXADataSource";
		final String sConnStr = "jdbc:oracle:thin:@192.168.88.220:1521:orcl";
		try {
			Class.forName(sDBDriver).newInstance();
//			System.out.println("Please input database smdbtestfee password:");
//			Scanner in = new Scanner(System.in);
//			String password = in.nextLine(); 
			String password = "p1013smdbtestfee702";
			conn = DriverManager.getConnection(sConnStr, "smdbtestfee", password);
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
}

