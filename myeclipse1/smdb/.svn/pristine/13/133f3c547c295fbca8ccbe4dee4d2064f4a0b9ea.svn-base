package csdc.action.mobile;

import java.util.HashMap;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.tool.bean.Pager;

/**
 * mobile基类
 * @author fengcl
 */
public abstract class MobileAction extends BaseAction {
	
	private static final long serialVersionUID = 8058951982402233917L;

	protected Integer listType;//列表类型
	protected int startRow;
	protected int maxRow;
	protected int totalPageNums;//总页数

	/**
	 * 构造方法，初始化
	 */
	public MobileAction(){
		session = ActionContext.getContext().getSession();
	}
	
	/**
	 * 将列表功能的公共成员变量放入jsonMap对象中，
	 * 主要包括：列表数据、总记录数
	 */
	public void jsonListPut() {
		//将列表相关的公共变量存入jsonMap对象
		jsonMap.put("laData", pageList);
		jsonMap.put("totalRows", totalRows + "");
		jsonMap.put("totalPageNums", totalPageNums + "");
	}
	
	/**
	 * 用于初级检索和高级检索。
	 * @param hql 查询语句
	 * @param map 参数
	 */
	public void search(String hql, HashMap map) {
		Pager page = fetchPager();
		page.setHql(hql.toString());
		page.setParaMap(map);
		session.put(pageName(), page);
		fetchData(1);
	}
	
	/**
	 * 获取数据
	 * @param pageNumber 请求的页码
	 */
	public void fetchData(Integer pageNumber){
		Pager page = fetchPager();
		Long before = System.currentTimeMillis();
		try {
			if(pageNumber == null || pageNumber < 1) pageNumber = 1;
			pageSize = 20;
			int startRow = pageSize * (pageNumber - 1);
			int maxRow = pageSize;
			//重要！！dao.count()方法对于传入其中的map中参数的类型要求很严格，如果是int型，那么放入map前就要转为int。
			this.totalRows = (int) dao.count(page.getHql(), page.getParaMap());//总记录数
			int totalNums = (int) (this.totalRows / pageSize);//取模
			int remainder = (int) (this.totalRows % pageSize); //求余
			this.totalPageNums = (remainder == 0) ? totalNums : totalNums + 1;//总页码
			pageList = dao.query(page.getHql(), page.getParaMap(), startRow, maxRow);
			this.jsonListPut();
		} catch (Exception e) {
			jsonMap = null;
		}
		Long after = System.currentTimeMillis();
		System.out.println("数据查询时间为： " + (after - before) + "ms");
	}
	
	/**
	 * pageNumber:滑动的次数
	 */
	public String toPage() {
		fetchData(pageNumber);
		return SUCCESS;
	}
	
	/**
	 * 查看
	 * @param hql 查询语句
	 * @param map 参数
	 */
	public void view(StringBuffer hql, HashMap map){
		try {
			pageList = dao.query(hql.toString(), map);
			jsonMap.put("laData", pageList);
		} catch (Exception e) {
			jsonMap = null;
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取session中现有的pager实例，若没有，实例化一个并放入session
	 * @param update 是否忽略session内的已有pager，强制重新实例化
	 * @return
	 */
	private Pager fetchPager() {
		System.out.println("current pageName is : " + pageName());
		Pager pager = (Pager) session.get(pageName());
		// 判断page类是否存在
		if (pager != null) {
			return pager;
		} else {
			Pager page = new Pager();
			session.put(pageName(), page);
			return (Pager) session.get(pageName());
		}
	}

	@Override
	public Object[] advSearchCondition() {
		return null;
	}
	@Override
	public String[] column() {
		return null;
	}
	@Override
	public String dateFormat() {
		return null;
	}
	@Override
	public String pageBufferId() {
		return null;
	}
	@Override
	public Object[] simpleSearchCondition() {
		return null;
	}
	public Integer getListType() {
		return listType;
	}
	public void setListType(Integer listType) {
		this.listType = listType;
	}	
}
