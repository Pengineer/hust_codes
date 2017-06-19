package edu.hust.dao.impl;

import java.text.SimpleDateFormat;
import org.dom4j.Document;
import org.dom4j.Element;

import edu.hust.dao.UserDao;
import edu.hust.domain.User;
import edu.hust.utils.XmlUtils;

public class UserDaoImpl implements UserDao {
		
	@Override
	public void add(User user){
		try {
			Document document = XmlUtils.getDocument();
			Element root = document.getRootElement();
			Element element = root.addElement("user");
			element.addAttribute("id", user.getId());
			element.addAttribute("username", user.getUsername());
			element.addAttribute("password", user.getPassword());
			element.addAttribute("email", user.getEmail());
			element.addAttribute("birthday", user.getBirthday()==null?"":user.getBirthday().toLocaleString());
			XmlUtils.write2XML(document);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
	}
	
	//查找用户，并返回用户的所有属性值
	@Override
	public User find(String username,String password){
		try{
			Document document = XmlUtils.getDocument();
			Element element = (Element) document.selectSingleNode("//user[@username='"+username+"' and @password='"+password+"']");
			if(element==null){
				return null;
			}
			User user = new User();
			String date = element.attributeValue("birthday");
			if(date==null || date.equals("")){
				user.setBirthday(null);
			}else{
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				user.setBirthday(format.parse(date));
			}
			user.setEmail(element.attributeValue("email"));
			user.setId(element.attributeValue("id"));
			return user;
		}catch(Exception e){
			throw new RuntimeException(e);
		}		
	}
	
	//查找注册的用户在数据库中是否存在
	@Override
	public boolean isExist(String username){
		try{
			Document document = XmlUtils.getDocument();
			Element element = (Element) document.selectSingleNode("//user[@username='"+username+"']");
			if(element==null)
				return false;
			return true;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
}
