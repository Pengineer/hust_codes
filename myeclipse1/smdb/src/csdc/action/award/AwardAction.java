package csdc.action.award;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.action.award.moesocial.ApplicationAwardedAction;
import csdc.bean.Person;
import csdc.bean.Product;
import csdc.service.IAwardService;
import csdc.service.ext.IPersonExtService;
import csdc.service.IProductService;
import csdc.tool.info.GlobalInfo;


/**
 * 奖励数据管理
 * @author 余潜玉
 */
public class AwardAction extends BaseAction {

	private static final long serialVersionUID = -1422968885988292511L;
	private static final String HQL =" select aa.id, aa.productName, aa.applicantName, aa.agencyName, aa.disciplineType, pr.productType, gr.name, aa.session, aa.applicant.id, un.id, aa.year, awt.name, '1' "+
		"from AwardGranted aw, AwardApplication aa left outer join aa.university un left join aa.subType awt, SystemOption gr, Product pr where aw.application.id=aa.id and gr.id=aw.grade.id and pr.id = aa.product.id";
	private static final String PAGE_NAME="myAward";//公示及奖励列表页面名称
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	protected IAwardService awardService;
	@Autowired
	protected IProductService productService;
	@Autowired
	protected IPersonExtService personExtService;
	
	protected Person person;//人员对象
	protected String personid;//人员id
	protected int listflag;//列表类型 1:申请列表	2:公示列表  3:获奖列表  4:评审列表	5:我的奖励列表
	protected String selectedTab;//跳到标签指定位置;
	private int subType;//奖励类别 1:高等学校科学研究优秀成果奖（人文社会科学）
	private String returnView;//奖励查看参数
	
	public String dateFormat() {
		return AwardAction.DATE_FORMAT;
	}
	public String pageBufferId() {
		return null;
	}
	public String[] column(){
		return null;
	}
	public String pageName() {
		return AwardAction.PAGE_NAME;
	}
	public Object[] simpleSearchCondition(){
		return null;
	}
	public Object[] advSearchCondition(){
		return null;
	}

	
	/**
	 * 准备进入我的奖励列表
	 * @return
	 */
	public String toSearchMyAward(){
		request.setAttribute("url", "searchMyAward");
		Map ptypes = Product.typeMap;
	    session.put("ptypes", ptypes);
		return SUCCESS;
	}
	/**
	 * 我的奖励列表
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String searchMyAward(){
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL).append(" and aa.applicant.id =:personId ");
		map.put("personId", awardService.getBelongIdByLoginer(loginer));
		this.pageList = dao.query(hql.toString(), map);// 页面大小由客户端传过来
		
		for (Object[] obj : pageList) {
			obj[5] = Product.findTypeName((String)obj[5]);
		}
		
		this.pageListDealWith();
		jsonMap.put("laData", laData);
		return SUCCESS;
	}
	
	/**
	 * 进入查看我的奖励查看
	 */
	public String toViewMyAward(){
		if(this.subType == 1){//高等学校科学研究优秀成果奖（人文社会科学）
			returnView = "/award/moesocial/application/view.jsp";
		}
		return SUCCESS;
	}
	
	/**
	 * 查看我的奖励
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String viewMyAward(){
		String result = "";
		Map session = ActionContext.getContext().getSession();
		if(this.subType == 1){
			ApplicationAwardedAction aaa = new ApplicationAwardedAction();
			aaa.setLoginer(loginer);
			aaa.setEntityId(this.entityId);
			aaa.setAwardService(awardService);
			aaa.setPageNumber(this.pageNumber);
			aaa.setListflag(this.listflag);
			aaa.setDao(dao);
			result = aaa.view();
			this.jsonMap = (Map) session.get("awardViewJson");
		}else{
			jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			result = INPUT;
		}
		return result;
	}

	public IAwardService getAwardService() {
		return awardService;
	}
	public void setAwardService(IAwardService awardService) {
		this.awardService = awardService;
	}
	public int getListflag() {
		return listflag;
	}
	public void setListflag(int listflag) {
		this.listflag = listflag;
	}
	public String getSelectedTab() {
		return selectedTab;
	}
	public void setSelectedTab(String selectedTab) {
		this.selectedTab = selectedTab;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public int getSubType() {
		return subType;
	}
	public void setSubType(int subType) {
		this.subType = subType;
	}
	public String getPersonid() {
		return personid;
	}
	public void setPersonid(String personid) {
		this.personid = personid;
	}
	public String getReturnView() {
		return returnView;
	}
	public void setReturnView(String returnView) {
		this.returnView = returnView;
	}
}
