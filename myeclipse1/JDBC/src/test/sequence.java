package test;

//测试静态变量的执行顺序

public class sequence {
	public static void main(String[] args) {
		
		UserDao userDao = DaoFactory.getInstance().getuserDao();
		
		userDao.method();
	}
}
