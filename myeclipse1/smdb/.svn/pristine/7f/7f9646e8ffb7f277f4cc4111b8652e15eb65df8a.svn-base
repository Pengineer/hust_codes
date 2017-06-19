package csdc.action.award;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.action.award.moesocial.ApplicationAwardedAction;
import csdc.bean.Person;
import csdc.service.IAwardService;
import csdc.service.ext.IPersonExtService;
import csdc.service.IProductService;
import csdc.tool.info.GlobalInfo;


/**
 * 奖励基类
 * @author 王燕
 */
@SuppressWarnings("unused")
public abstract class AwardBaseAction extends BaseAction {

	private static final long serialVersionUID = -1422968885988292511L;
	
	private static final String PAGE_NAME="myAward";//公示及奖励列表页面名称
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	protected IAwardService awardService;//奖励接口
	@Autowired
	protected IProductService productService;//人员接口
	@Autowired
	protected IPersonExtService personExtService;
	protected Person person;//人员对象
	protected int listflag;//列表类型 1:申请列表	2:公示列表  3:获奖列表  4:评审列表	5:我的奖励列表
	protected String selectedTab;//跳到标签指定位置;
	

	public String dateFormat() {
		return DATE_FORMAT;
	}
	public String pageBufferId() {
		return null;
	}
	public String[] column(){
		return null;
	}
	public String pageName() {
		return null;
	}
	public Object[] simpleSearchCondition(){
		return null;
	}
	public Object[] advSearchCondition(){
		return null;
	}
	
	public void setAwardService(IAwardService awardService) {
		this.awardService = awardService;
	}
	public IAwardService getAwardService() {
		return awardService;
	}
	public void setPersonExtService(IPersonExtService personExtService) {
		this.personExtService = personExtService;
	}
	public int getListflag() {
		return listflag;
	}
	public void setListflag(int listflag) {
		this.listflag = listflag;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
}
