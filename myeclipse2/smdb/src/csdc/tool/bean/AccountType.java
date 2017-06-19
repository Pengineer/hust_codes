package csdc.tool.bean;


/**
 * 账号类型。
 * 级别由高到低排序。
 * 和原来数字类型的对应关系为：
 * [1到10]对应
 *  1系统管理员, ADMINISTRATOR
 *  2部级, MINISTRY
 *  3省级, PROVINCE
 *  4部属高校, MINISTRY_UNIVERSITY
 *  5地方高校, LOCAL_UNIVERSITY
 *  6高校院系, DEPARTMENT
 *  7研究基地, INSTITUTE
 *  8外部专家, EXPERT
 *  9教师, TEACHER
 *  10学生, STUDENT
 *  11未知类型，UNDEFINED
 * @author zhouzj
 *
 */
public enum AccountType { 

	ADMINISTRATOR, MINISTRY, PROVINCE, MINISTRY_UNIVERSITY, LOCAL_UNIVERSITY, DEPARTMENT, INSTITUTE, EXPERT, TEACHER, STUDENT, UNDEFINED;
	
	/**
	 * 比较当前accountType和目标accountType
	 * @param accountType
	 * @return 等级高于返回1，等于返回0，低于返回-1
	 */
	public Integer compareWith(AccountType accountType) {
		Integer thisIndex = this.ordinal();
		Integer anotherIndex = accountType.ordinal();
		return (thisIndex < anotherIndex ? 1 : ( thisIndex == anotherIndex ? 0 : -1));
	}
	
	/**
	 * 判断当前accountType是否在at1和at2之间（含at1和at2）
	 * @param at1
	 * @param at2
	 * @return 是返回true，否返回false
	 */
	public boolean within(AccountType at1, AccountType at2) {
		Integer thisIndex = this.ordinal();
		Integer index1 = at1.ordinal();
		Integer index2 = at2.ordinal();
		return (index1 > index2 ? ((index1 >= thisIndex && thisIndex >= index2) ? true : false) : ((index2 >= thisIndex && thisIndex >= index1) ? true : false));
	}
	
	public AccountType chageType(int i) {
		AccountType at1 = AccountType.UNDEFINED;
		for (AccountType at : AccountType.values()) {
			if(at.ordinal() == (i-1)) 
			{
				at1 = at;
				}
		}
		return at1;
	}
}
