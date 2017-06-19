package csdc.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import csdc.bean.Account;
import csdc.tool.bean.LoginInfo;
import csdc.tool.bean.Pager;

public interface IMessageAuxiliaryService extends IBaseService{
	
	/**
	 * 根据人员ID提供同领域的研究人员信息
	 * @param personId	研究人员id
	 * @return 
	 */
	public List getSameSearchData(String personId);
	
	/**
	 * 根据指定账号的日志记录找到用户查看数据的记录
	 * @param account 账号
	 * @param startDate开始时间
	 * @param endDate结束时间
	 * @param num  查询条数
	 * @return List<Map> name：用户查看的数据名称；sum：用户查看某一条记录的次数
	 */
	public List getLogHistory(Account account, Date startDate, Date endDate, int num);
	
	/**
	 *  @param loginer	当前登陆者
	 *  @return List： 菜单的名称和菜单的入口地址，
	 *  例如{"description":"一般项目立项数据","url":"project/general/application/granted/toList.action?update=1"}
	 * 
	 */
	public List getHistoryMenu(LoginInfo loginer);
	
	/**
	 * 项目模块列表的辅助信息显示
	 * @param pager
	 * @param column 排序的列
	 * @param xTitle X轴标题
	 * @param yTitle Y轴标题
	 * @return map
	 */
	public Map projectListAssist(Pager pager, String column, String xTitle, String yTitle, String key);
	
}
