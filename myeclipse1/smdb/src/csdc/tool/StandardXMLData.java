package csdc.tool;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * XML工具类，可以自动生成学校、职称、学科代码等标准信息的XML文件，并可以随意配置组合。
 * @author leida
 */
public class StandardXMLData {
	private static Connection conn = null; //数据库连接类
	private static Element rootDoc = null; //xml根元素
	
	public static void generateXMLFile(String config) {
		
	}
	
	public static void main(String[] args) throws SQLException {
		Document root = DocumentHelper.createDocument();
		rootDoc = root.addElement("DocumentData");
		rootDoc.addAttribute("version", "1.0");
		generateDisciplineInfo();
		generateSchoolInfo();
		generateTitleInfo();
		//System.out.println(root.asXML());
		try {
			FileWriter fw = new FileWriter("D:/const.dat");
			fw.write(root.asXML());
			fw.flush();
			fw.close();
			System.out.println("SUCCESS");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 学校信息
	 * @throws SQLException 
	 */
	public static void generateSchoolInfo() throws SQLException {
		ResultSet rs = executeQuery("select ag.c_code, ag.c_name from t_agency ag where (ag.c_type=3 or ag.c_type=4) and (ag.c_style like '11%' or ag.c_style like '12%' or ag.c_style like '13%' or ag.c_style like '14%' or ag.c_style like '22%') order by ag.c_code asc ");
		Element root = rootDoc.addElement("universityCodes");
		Element school = null;
		school = root.addElement("SchoolCode");
		while(rs != null && rs.next()) {
			Element uni = school.addElement("university");
			uni.addAttribute("code", rs.getString(1));
			uni.addAttribute("name", rs.getString(2));
		}
	}
	
	/**
	 * 职称信息
	 * @throws SQLException 
	 */
	public static void generateTitleInfo() throws SQLException {
		ResultSet rs = executeQuery("select so.c_code, so.c_name from t_system_option so where c_standard = 'GBT8561-2001' and so.c_code <> '000' order by so.c_code asc");
		Element root = rootDoc.addElement("specialityTitle");
		Element titleA = null, titleB = null;
		while(rs != null && rs.next()) {
			if(rs.getString(1).endsWith("0")) {
				titleA = root.addElement("titleA");
				titleA.addAttribute("name", rs.getString(1) + "/" + rs.getString(2));
			} else {
				titleB = titleA.addElement("titleB");
				titleB.addAttribute("name", rs.getString(1) + "/" + rs.getString(2));
			}
		}
	}
	
	/**
	 * 学科代码信息
	 * @throws SQLException 
	 */
	public static void generateDisciplineInfo() throws SQLException {
		ResultSet rs = executeQuery("select so.c_code, so.c_name from t_system_option so where c_standard = 'GBT13745-2009' and so.c_code is not null order by so.c_code asc");
		Element root = rootDoc.addElement("subjectData");
		Element subjectA = null, subjectB = null, subjectC = null;
		while(rs != null && rs.next()) {
			if(rs.getString(1).length() == 3) {
				subjectA = root.addElement("SubjectA");
				subjectA.addAttribute("code", rs.getString(1));
				subjectA.addAttribute("name", rs.getString(2));
			} else if(rs.getString(1).length() == 5) {
				subjectB = subjectA.addElement("SubjectB");
				subjectB.addAttribute("code", rs.getString(1));
				subjectB.addAttribute("name", rs.getString(2));
			} else {
				subjectC = subjectB.addElement("SubjectB");
				subjectC.addAttribute("code", rs.getString(1));
				subjectC.addAttribute("name", rs.getString(2));
			}
		}
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
			System.out.println("Please input database smdb password:");
			Scanner in = new Scanner(System.in);
			String password = in.nextLine(); 
			conn = DriverManager.getConnection(sConnStr, "smdb", password);
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
}
