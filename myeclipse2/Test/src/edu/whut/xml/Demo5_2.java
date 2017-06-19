package edu.whut.xml;

import java.io.File;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * xpath的一种应用：如果数据是以XML文档的形式存储的，可进行数据库的账号密码匹配
 */

public class Demo5_2 {

	public static void main(String[] args) throws DocumentException {
		
		String username = "zhangsan";
		String password = "123";
		
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File("src/edu/whut/xml/user.xml"));
		//注意单引号
		Node node = document.selectSingleNode("//user[@username='"+username+"' and @password='"+password+"']");
		
		if(node == null){
			System.out.println("用户名或密码错误");
		}else{
			System.out.println("登录成功");
		}
	}
}
