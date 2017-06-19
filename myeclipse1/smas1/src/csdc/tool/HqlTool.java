package csdc.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * HQL处理辅助类
 * @author xuhan
 *
 */
public class HqlTool {
	
	public String selectClause;
	public String fromClause;
	public String whereClause;
	public String groupClause;
	public String havingClause;
	public String orderClause;
	
	public String[] orderByConditions;
	public String[] orderByVariables;
	
	/////////////////////////////////////////////////////////////////////

	/**
	 * 构造器，传入一个hql，并初始化各子句
	 */
	public HqlTool(String hql) {
		setHql(hql);
	}
	
	/**
	 * 传入一个hql，并初始化各子句
	 * @param hql
	 */
	public void setHql(String hql) {
		selectClause = StringTool.regFind(hql, "\\bselect\\b([\\s\\S]*?)\\bfrom\\b");
		fromClause = StringTool.regFind(hql, "\\bfrom\\b([\\s\\S]*?)(\\bwhere\\b|\\bgroup\\b|\\bhaving\\b|\\border\\b|$)");
		whereClause = StringTool.regFind(hql, "\\bwhere\\b([\\s\\S]*?)(\\bgroup\\b|\\bhaving\\b|\\border\\b|$)");
		groupClause = StringTool.regFind(hql, "\\bgroup\\s+by\\b([\\s\\S]*?)(\\bhaving\\b|\\border\\b|$)");
		havingClause = StringTool.regFind(hql, "\\bhaving\\b([\\s\\S]*?)(\\border\\b|$)");
		orderClause = StringTool.regFind(hql, "\\border\\s+by\\b([\\s\\S]*)");

		orderByConditions = null;
		orderByVariables = null;
		
		if (orderClause != null) {
			//排序条件
			orderByConditions = orderClause.split("(?<!\\([^\\)]{0,100})\\s*,\\s*(?![^\\(]{0,100}\\))");	//该正则表达式匹配不在括号内的逗号
			orderByVariables = new String[orderByConditions.length];	//排序变量
			for (int i = 0; i < orderByConditions.length; i++) {
				orderByVariables[i] = orderByConditions[i].replaceAll("(?i)asc|desc", "");
			}
		}
	}
	
	/**
	 * 按处理过的各子句，使用所有子句重组hql并返回
	 * @return 重组的hql
	 */
	public String getHql() {
		return getHql("sfwgho");
	}
	
	/**
	 * 按处理过的各子句，重组hql并返回
	 * @param neededClause "sfwgho"表示六个子句都要，"sfwgh"表示除了order by子句都要
	 * @return
	 */
	public String getHql(String neededClause) {
		StringBuffer tmpHql = new StringBuffer();
		if (neededClause.contains("s") && selectClause != null) {
			tmpHql.append("select " + selectClause);
		}
		if (neededClause.contains("f") && fromClause != null) {
			tmpHql.append(" from " + fromClause);
		}
		if (neededClause.contains("w") && whereClause != null) {
			tmpHql.append(" where " + whereClause);
		}
		if (neededClause.contains("g") && groupClause != null) {
			tmpHql.append(" group by " + groupClause);
		}
		if (neededClause.contains("h") && havingClause != null) {
			tmpHql.append(" having " + havingClause);
		}
		if (neededClause.contains("o") && orderClause != null) {
			tmpHql.append(" order by " + orderClause);
		}
		return tmpHql.toString();
	}
	
	/**
	 * 获取order by的反向写法
	 */
	public String reverseOrder() {
		List<String> tmp = new ArrayList<String>();
		for (String cond : orderByConditions) {
			if (Pattern.compile("(?i)\\bdesc\\b").matcher(cond).find()) {
				tmp.add(cond.replaceAll("(?i)\\bdesc\\b", "asc"));
			} else if (Pattern.compile("(?i)\\basc\\b").matcher(cond).find()) { 
				tmp.add(cond.replaceAll("(?i)\\basc\\b", "desc"));
			} else {
				tmp.add(cond + " desc");
			}
		}
		return StringTool.joinString(tmp.toArray(new String[0]), ", ");
	}
	
	/**
	 * 获取第i个排序列的排序方向
	 * @return 0:降序    1:升序
	 */
	public int getOrder(int i) {
		String orderString = orderByConditions[i].replace(orderByVariables[i], "");
		return orderString.toLowerCase().contains("desc") ? 0 : 1;
	}
	
	/**
	 * <pre>用于将order by子句转换成相对某条记录关键字的where条件，例如：
	 * 	orderClause: order by name asc, id desc
	 * 	nextDerection: 1 
	 * 则转换后为:
	 * 	(name > :var0 or name = :var0 and (id < :var1))
	 * var0为当前条的name, var1为当前条的id</pre>
	 * 
	 * @param nextDirection false: 上一条方向    true: 下一条方向
	 * @param parameters
	 * @return
	 */
	public String orderToWhere(boolean isNextDirection, Object[] var) {
		StringBuffer res = new StringBuffer();
		for (int i = 0; i < orderByVariables.length; i++) {
			res.append("(");
			if (isNextDirection ^ orderByConditions[i].contains(" desc")) {
				res.append(var[i] == null ? " 1 = 0 " : orderByVariables[i] + " > :var" + i + " or " + orderByVariables[i] + " is null ");
			} else {
				res.append(var[i] == null ? orderByVariables[i] + " is not null " : orderByVariables[i] + " < :var" + i);
			}
			if (i < orderByVariables.length - 1) {
				res.append(" or " + orderByVariables[i] + (var[i] == null ? " is null" : " = :var" + i) + " and ");
			}
		}
		for (int i = 0; i < orderByVariables.length; i++) {
			res.append(")");
		}
		return res.toString();
	}
	
	/**
	 * 找到memberName在select子句中是第几个成员
	 * @param memberName 待查成员名
	 * @return
	 */
	public int getSelectedMemberIndex(String memberName) {
		int memberIndex = 0;
		int bracketCnt = 0; 	//当前处于多少层未完结括号中
		int endLocation = selectClause.indexOf(memberName);
		for (int i = 0; i < endLocation; i++) {
			if (selectClause.charAt(i) == '(') {
				++bracketCnt;
			} else if (selectClause.charAt(i) == ')') {
				--bracketCnt;
			}
			if (bracketCnt == 0 && selectClause.charAt(i) == ',') {
				++memberIndex;
			}
		}
		return memberIndex;
	}
	
	/////////////////////////////////////////////////////////////////////
	

	
	
	public String getSelectClause() {
		return selectClause;
	}
	public void setSelectClause(String selectClause) {
		this.selectClause = selectClause;
	}
	public String getFromClause() {
		return fromClause;
	}
	public void setFromClause(String fromClause) {
		this.fromClause = fromClause;
	}
	public String getWhereClause() {
		return whereClause;
	}
	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}
	public String getGroupClause() {
		return groupClause;
	}
	public void setGroupClause(String groupClause) {
		this.groupClause = groupClause;
	}
	public String getHavingClause() {
		return havingClause;
	}
	public void setHavingClause(String havingClause) {
		this.havingClause = havingClause;
	}
	public String getOrderClause() {
		return orderClause;
	}
	public void setOrderClause(String orderClause) {
		this.orderClause = orderClause;
		orderByConditions = orderClause.split("(?<!\\([^\\)]{0,100})\\s*,\\s*(?![^\\(]{0,100}\\))");	//该正则表达式匹配不在括号内的逗号
		orderByVariables = new String[orderByConditions.length];	//排序变量
		for (int i = 0; i < orderByConditions.length; i++) {
			orderByVariables[i] = orderByConditions[i].replaceAll("(?i)asc|desc", "");
		}

	}


}
