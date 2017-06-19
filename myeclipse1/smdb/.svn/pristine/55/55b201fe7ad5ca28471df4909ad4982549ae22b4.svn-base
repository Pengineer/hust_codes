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
import csdc.bean.ProjectFee;
import csdc.dao.IHibernateBaseDao;
import csdc.service.IProjectService;
import csdc.tool.StringTool;
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
	 * 控制逻辑返回内容
	 * @param type 响应内容类型，error:异常类型， notice:通知类型，shakeEnds:握手结果
	 * @param content, type对应类型的内容
	 * 注意:返回的全部内容（xml字符串）统一进行utf-8编码，并采用十六进制字符串显示
	 */
	public String responseContent(String type ,String content ){
		if (!type.equals("error") && !type.equals("notice") && !type.equals("shakeEnds")) {
			throw new RuntimeException("控制逻辑返回内容必须使用type:error,notice,shakeEnds三者中的一种！");
		}
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
	 * 业务内容返回
	 * type:data:正常的服务请求返回内容
	 */
	public String responseContent(String type ,Document documentChild){
		if (!type.equals("data")) {
			throw new RuntimeException("业务内容必须指定type：data！");
		}
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
	
	//形成项目经费对象
	protected void toParseProjectFeeItem(Element itemElement, ProjectFee pojFee) {
		//封装经费对象
		Element projectFeeItem = itemElement.addElement("projectFeeItem");
		//填充内容
		if (null != pojFee) {
			projectFeeItem.addElement("projectFeeID").setText(pojFee.id);
			projectFeeItem.addElement("bookFee").setText(pojFee.getBookFee() == null ? "null" : pojFee.getBookFee().toString());
			projectFeeItem.addElement("bookNote").setText(pojFee.getBookNote() == null ? "null" : pojFee.getBookNote());
			projectFeeItem.addElement("dataFee").setText(pojFee.getDataFee() == null ? "null" : pojFee.getDataFee().toString());
			projectFeeItem.addElement("dataNote").setText(pojFee.getDataNote() == null ? "null" : pojFee.getDataNote());
			projectFeeItem.addElement("travelFee").setText(pojFee.getTravelFee() == null ? "null" : pojFee.getTravelFee().toString());
			projectFeeItem.addElement("travelNote").setText(pojFee.getTravelNote() == null ? "null" : pojFee.getTravelNote());
			projectFeeItem.addElement("deviceFee").setText(pojFee.getDeviceFee() == null ? "null" : pojFee.getDeviceFee().toString());
			projectFeeItem.addElement("deviceNote").setText(pojFee.getDeviceNote() == null ? "null" : pojFee.getDeviceNote());
			projectFeeItem.addElement("conferenceFee").setText(pojFee.getConferenceFee() == null ? "null" : pojFee.getConferenceFee().toString());
			projectFeeItem.addElement("conferenceNote").setText(pojFee.getConferenceNote() == null ? "null" : pojFee.getConferenceNote());
			projectFeeItem.addElement("consultationFee").setText(pojFee.getConsultationFee() == null ? "null" : pojFee.getConsultationFee().toString());
			projectFeeItem.addElement("consultationNote").setText(pojFee.getConsultationNote() == null ? "null" : pojFee.getConsultationNote());
			projectFeeItem.addElement("laborFee").setText(pojFee.getLaborFee() == null ? "null" : pojFee.getLaborFee().toString());
			projectFeeItem.addElement("laborNote").setText(pojFee.getLaborNote() == null ? "null" : pojFee.getLaborNote());
			projectFeeItem.addElement("printFee").setText(pojFee.getPrintFee() == null ? "null" : pojFee.getPrintFee().toString());
			projectFeeItem.addElement("printNote").setText(pojFee.getPrintNote()  == null ? "null" : pojFee.getPrintNote());
			projectFeeItem.addElement("internationalFee").setText(pojFee.getInternationalFee() == null ? "null" : pojFee.getInternationalFee().toString());
			projectFeeItem.addElement("internationalNote").setText(pojFee.getInternationalNote() == null ? "null" : pojFee.getInternationalNote());
			projectFeeItem.addElement("indirectFee").setText(pojFee.getIndirectFee() == null ? "null" : pojFee.getIndirectFee().toString());
			projectFeeItem.addElement("indirectNote").setText(pojFee.getIndirectNote()  == null ? "null" : pojFee.getIndirectNote());
			projectFeeItem.addElement("otherFee").setText(pojFee.getOtherFee() == null ? "null" : pojFee.getOtherFee().toString());
			projectFeeItem.addElement("otherNote").setText(pojFee.getOtherNote()  == null ? "null" : pojFee.getOtherNote());
			projectFeeItem.addElement("totalFee").setText(pojFee.getTotalFee() == null ? "null" : pojFee.getTotalFee().toString());
			projectFeeItem.addElement("type").setText(pojFee.getType() == null ? "null" : pojFee.getType().toString());
			projectFeeItem.addElement("feeNote").setText(pojFee.getFeeNote() == null ? "null" : pojFee.getFeeNote());
			projectFeeItem.addElement("fundedFee").setText(pojFee.getFundedFee() == null ? "null" : pojFee.getFundedFee().toString());
		} else {
			projectFeeItem.setText("null");
		}
	}
	
	
	/**
	 * 根据乱七八糟的日期字符串分析出正确日期
	 * @param rawDate
	 * @return
	 */
	protected Date _date(String rawDate){
		if (rawDate == null) {
			return null;
		}
		rawDate = StringTool.toDBC(rawDate).trim();
		if (rawDate.isEmpty()) {
			return null;
		}
		if (rawDate.matches("(19|20)\\d{4}") || rawDate.matches("20\\d{4}")) {
			Integer year = Integer.parseInt(rawDate) / 100;
			Integer month = Integer.parseInt(rawDate) % 100;
			if (month >= 1 && month <= 12) {
				return new Date(year - 1900, month - 1, 1);
			}
		}
		if (rawDate.matches("-?\\d{5,6}")) {
			//Excel中的日期在常规格式下的值(1900-1-1过去的天数 + 2)
			return new Date(Long.parseLong(rawDate) * 86400000L - 2209161600000L);
		} else if (rawDate.matches("-?\\d{7,}")) {
			//1970-1-1 00:00:00过去的毫秒数
			return new Date(Long.parseLong(rawDate));
		}
		String tmp[] = rawDate.replaceAll("\\D+", " ").trim().split("\\s+");
		if (tmp.length == 0 || tmp[0].isEmpty()) {
			return null;
		}
		Integer mid;
		Integer year = Integer.parseInt(tmp[0]);
		Integer month = tmp.length > 1 ? Integer.parseInt(tmp[1]) : 1;
		Integer date = tmp.length > 2 ? Integer.parseInt(tmp[2]) : 1;
		if (month > 31) {
			mid = month; month = year; year = mid;
		} else if (date > 31) {
			mid = date; date = year; year = mid;
		}
		if (month > 12) {
			mid = date;	date = month; month = mid;
		}
		if (year < 10) {
			year += 2000;
		} else if (year < 100) {
			year += 1900;
		}
		return new Date(year - 1900, month - 1, date);
	}
	
	/**
	 * 	转换smdb的discipline的形式为smas的discipline形式
	 *  smas的discipline类型：63044; 8101020; 81020
	 *	smdb的discipline类型：19065/应用心理学; 52099/计算机科学技术其他学科; 74010/普通语言学
	 * @param originDis
	 * @return
	 */
	protected static String filterDisciplineType(String originDis) {
		String[] datas = originDis.split("; ");
		String endDispic = "";
		for (int i = 0; i < datas.length; i++) {
			String displineName = datas[i];
			int begin = displineName.indexOf("/");
			
			if (begin > 0) {
				endDispic += displineName.substring(0, begin) + "; ";
			} else {
				endDispic += displineName + "; ";
			}
		}
		if (endDispic.length() > 2) {
			endDispic = endDispic.substring(0, endDispic.length() - 2);
		}
		return endDispic;
	}
	
}
