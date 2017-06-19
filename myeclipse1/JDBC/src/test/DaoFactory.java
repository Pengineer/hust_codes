package test;

public class DaoFactory {
	
	private static UserDao userDao = null;//1
	private static DaoFactory factory = new DaoFactory();//2.0--2.2
	
	private DaoFactory() {//2.1
		System.out.println(11);
		System.out.println(userDao);
		System.out.println(factory);
		userDao = new UserDao();
	}
	
	public static DaoFactory getInstance() {//3
		System.out.println(111);
		System.out.println(userDao);
		System.out.println(factory);
		return factory;
	}
	
	public UserDao getuserDao() {//4
		System.out.println(1111);
		System.out.println(userDao);
		System.out.println(factory);
		return userDao;
	}
}
