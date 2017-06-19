package csdc.service.webService.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Account;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Institute;
import csdc.bean.Officer;
import csdc.bean.Passport;
import csdc.bean.Person;
import csdc.dao.IHibernateBaseDao;
import csdc.service.IProjectService;
import csdc.tool.bean.AccountType;
import csdc.tool.webService.WSServerSecurityTool;


public class BaseWebService implements IBaseWebService{
	@Autowired
	protected IHibernateBaseDao dao;
	@Autowired
	protected IProjectService projectService;

	/**
	 * 修饰请求内容(暂时没有使用)
	 * requestHandsShake, replySyncStatus的arguement
	 * @throws IOException 
	 */
	public Map<String, String> parseMethod(String content) {
		Map<String, String> serviceMap = new HashMap<String, String>();
		try {
			Document doc = DocumentHelper.parseText(content);
			Element serviceElement = (Element) doc.selectNodes("//service").get(0);
			serviceMap.put("service", serviceElement.getText());
			Element interfaceElement = (Element) doc.selectNodes("//interface").get(0);
			serviceMap.put("interface", interfaceElement.getText());
			Iterator argsListiterator = doc.selectNodes("/request/arguments/*").iterator();
			if(argsListiterator != null){//存在参数
				int i = 0;
				while(argsListiterator.hasNext()) {
					Element argElement = (Element) argsListiterator.next();
					String arg = argElement.getText();
					if(null != arg){
						//参数统一编码还原
						byte[] bytesContent = WSServerSecurityTool.hexStr2ByteArray(arg);
						String infoString = null;
						try {
							infoString = new String(bytesContent, "utf-8");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						} 
						serviceMap.put("arg" + i, infoString);
					}
					i++;
				}
			}//不存在参数节点
			
		} catch (Exception e) {
			e.printStackTrace();
			serviceMap.put("errorInfo", "调用服务的服务参数有误");
			System.out.println("解析错误");
		}
		return serviceMap;
	}
	
	/**
	 * content:密钥协商的String，
	 * userMark:当前进行密钥协商的用户标识（passport的MD5值）
	 */
	public String requestHandShake(String content,String usermark){
		String arry[] = content.split(";");
		String str0 = arry[0];
		String str1 = arry[1];
		String str2 = arry[2];
		String str3 = arry[3];
		BigInteger ag0 = new BigInteger(str0);
		BigInteger ag1 = new BigInteger(str1);
		int ag2 = Integer.parseInt(str2);
		String handsEnd = WSServerSecurityTool.doHandsShake(ag0, ag1, ag2,WSServerSecurityTool.hexStr2ByteArray(str3),usermark);
		//封装并返回握手结果
		return responseContent("shakeEnds",handsEnd); 
	}
	/**
	 * 统一响应形式,双方解析方便
	 * @param type 响应内容类型，error:异常类型， notice:通知类型，shakeEnds:握手结果， data:正常的服务请求返回内容
	 * @param content, type对应类型的内容
	 * 注意:返回的全部内容（xml字符串）统一进行utf-8编码，并采用十六进制字符串显示
	 */
	public String responseContent(String type ,String content ){
		Document document = DocumentHelper.createDocument();
		Element response = document.addElement("response");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		response.addElement("time").setText(df.format(new Date()));//提交时间
		response.addElement("type").setText(type);
		response.addElement("content").setText(content);
		String xmlStr = document.asXML();
		//全部内容统一（中文）编码格式，适合后续签名、加密
		String hexcontent = null;
		try {
			hexcontent = WSServerSecurityTool.byteArray2HexStr(xmlStr.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return hexcontent;
	}
	
	/**
	 * 业务过程中直接调用
	 */
	public String responseContent(String type ,Document documentChild){
		Document document = DocumentHelper.createDocument();
		Element response = document.addElement("response");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		response.addElement("time").setText(df.format(new Date()));//提交时间
		response.addElement("type").setText(type);
		response.appendContent(documentChild);
		String xmlStr = document.asXML();
		//统一（中文）编码格式，适合后续签名、加密
		String hexcontent = null;
		try {
			hexcontent = WSServerSecurityTool.byteArray2HexStr(xmlStr.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return hexcontent;
	}
	
	public String getPeerByAccount(Account account) {
		AccountType type = account.getType();// 待查看的账号级别
		int isPrincipal = account.getIsPrincipal();// 待查看张类别
		String belongId = projectService.getBelongIdByAccount(account) ;// 账号所属ID
		String peer = null;// 数据交互对象
		
		Passport passport = (Passport) dao.query(Passport.class, account.getPassport().getId());
		Agency agency;// 部、省、校级机构对象
		Department department;// 院系对象
		Institute institute;// 基地对象
		Person person;// 人员对象
		
		// 查询账号所属信息，并将其存入jsonMap对象
		if (isPrincipal == 1) {// 主账号需进一步判断账号级别
			if (type.equals(AccountType.MINISTRY) || type.equals(AccountType.PROVINCE) || type.equals(AccountType.MINISTRY_UNIVERSITY) || type.equals(AccountType.LOCAL_UNIVERSITY)) {// 部、省、校级主账号，查询agency表
				agency = (Agency) dao.query(Agency.class, belongId);				
				peer = agency.getName();
			} else if (type.equals(AccountType.DEPARTMENT)) {// 院系主账号，查询department表
				department = (Department) dao.query(Department.class, belongId);
				agency = (Agency) dao.query(Agency.class, department.getUniversity().getId());
				peer = agency.getName();
			} else if (type.equals(AccountType.INSTITUTE)) {// 基地主账号，查询institute表
				institute = (Institute) dao.query(Institute.class, belongId);
				agency = (Agency) dao.query(Agency.class, institute.getSubjection().getId());
				peer = agency.getName();
			} else if (type.equals(AccountType.EXPERT) || type.equals(AccountType.TEACHER) || type.equals(AccountType.STUDENT)) {// 外专家、教师、学生查询person表
				person = (Person) dao.query(Person.class, belongId);
				peer = person.getName();
			}
		} else {// 子账号查询officer表
			Officer officer = (Officer) dao.query(Officer.class, belongId);
			person = (Person) dao.query(Person.class, officer.getPerson().getId());			
			// 查询管理人员所属机构信息
			if (type.equals(AccountType.MINISTRY) || type.equals(AccountType.PROVINCE) || type.equals(AccountType.MINISTRY_UNIVERSITY) || type .equals(AccountType.LOCAL_UNIVERSITY)) {// 部、省、校级子账号，查询agency表
				agency = (Agency) dao.query(Agency.class, officer.getAgency().getId());
				peer = agency.getName();
			} else if (type.equals(AccountType.DEPARTMENT)) {// 院系主账号，查询department表
				department = (Department) dao.query(Department.class, officer.getDepartment().getId());
				agency = (Agency) dao.query(Agency.class, department.getUniversity().getId());
				peer = agency.getName();
			} else if (type.equals(AccountType.INSTITUTE)) {// 基地主账号，查询institute表
				institute = (Institute) dao.query(Institute.class, officer.getInstitute().getId());
				agency = (Agency) dao.query(Agency.class, institute.getSubjection().getId());
				peer = agency.getName();
			}
		}
		return peer;
	}	
}
