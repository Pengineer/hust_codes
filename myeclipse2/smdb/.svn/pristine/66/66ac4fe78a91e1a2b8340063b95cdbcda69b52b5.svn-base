package csdc.service.webService.server;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import csdc.bean.Account;
import csdc.tool.XMLTool;

public class SmasWebService extends BaseWebService implements ISmasWebService {


	public String addKeyPerson(Account account, String requestAccountBelong) throws IOException {
		StringBuffer hql = new StringBuffer();
		hql.append("select p.name, p.homePhone, p.officePhone, p.officeFax, p.email, p.mobilePhone, p.idcardType, p.idcardNumber, p.birthday, u.name " +
				"from Person p left join p.teacher t left join t.university u " +
				"where p.isKey = '1'");
		List<Object[]> list = dao.query(hql.toString());
		Document document = DocumentHelper.createDocument();
		Element response = document.addElement("response");
		response.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		Element submitTime = response.addElement("submitTime");//提交时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		submitTime.setText(df.format(new Date()));
		Element dataType = response.addElement("dataType");
		dataType.setText("data");
		Element records = response.addElement("records");
		for (Object[] o : list) {
			String[] str = new String[10];
			for (int i = 0; i < o.length; i++) {
				if (o[i] instanceof String){
					str[i] = (String) o[i];
				} else if (o[i] instanceof Integer) {
					int temp = (Integer) o[i];
					str[i] = String.valueOf(temp);
				} else if (o[i] instanceof Double) {
					str[i] = String.valueOf(o[i]);
				} else if (o[i] instanceof Date) {
					str[i] = (new SimpleDateFormat("yyyy-MM-dd")).format(o[i]);
				} else if (o[i] == null) {
					str[i] = "null";//当对象为空时
				}
			}	
			Element result = records.addElement("item");
			Element name = result.addElement("name");//姓名
			name.setText(str[0]);
			Element homePhone =  result.addElement("homePhone");//住宅电话
			homePhone.setText(str[1]);
			Element officePhone =  result.addElement("officePhone");//办公电话
			officePhone.setText(str[2]);
			Element officeFax =  result.addElement("officeFax");//办公传真
			officeFax.setText(str[3]);			
			Element email =  result.addElement("email");//邮箱
			email.setText(str[4]);
			Element mobilePhone =  result.addElement("mobilePhone");//电话
			mobilePhone.setText(str[5]);
			Element idcardType =  result.addElement("idcardType");//证件类型
			idcardType.setText(str[6]);
			Element idcardNumber =  result.addElement("idcardNumber");//证件号
			idcardNumber.setText(str[7]);
			Element birthday =  result.addElement("birthday");//生日
			birthday.setText(str[8]);
			Element agencyName =  result.addElement("agencyName");//机构名称
			agencyName.setText(str[9]);
		}
		String XMLStr = document.asXML();//将xml文档对象转化成xml字符串
		XMLTool.generateXmlFile(document);//根据document对象生成本地文件
		return responseContent("content", XMLStr);				
	}
	
}
