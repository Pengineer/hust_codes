package junit.test;

import java.util.Date;
import org.junit.Test;

import edu.hust.dao.UserDao;
import edu.hust.dao.impl.UserDaoImpl;
import edu.hust.domain.User;

public class UserDAOTest {
	@Test
	public void testAdd() {
		User user = new User();
		user.setId("3344");
		user.setUsername("lisi");
		user.setPassword("456");
		user.setEmail("ls@qq.com");
		user.setBirthday(new Date());
		
		UserDao userdao = new UserDaoImpl();
		userdao.add(user);
	}
	@Test
	public void testFind(){
		UserDao userdao = new UserDaoImpl();		
		userdao.find("zhangsan", "123");
	}
	@Test
	public void testIsExist(){
		UserDao userdao = new UserDaoImpl();		
		System.out.println(userdao.isExist("llisi"));
	}
}
