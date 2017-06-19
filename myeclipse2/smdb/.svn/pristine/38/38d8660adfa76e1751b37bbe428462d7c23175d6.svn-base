package csdc.action.award.moesocial;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import csdc.tool.bean.AccountType;


/**
 * 社科奖励获奖数据管理
 * @author 余潜玉 王燕
 */
public class ApplicationAwardedAction extends ApplicationAction {

	private static final long serialVersionUID = -1422968885988292511L;
	private static final String HQL ="select aa.id, aa.productName, aa.applicantName, aa.agencyName, aa.disciplineType, pr.productType, gr.name, aa.session,aa.applicant.id, un.id,aa.year"+
		" from AwardGranted aw, AwardApplication aa left outer join aa.university un, Product pr, SystemOption gr where aw.application.id=aa.id and gr.id = aw.grade.id and pr.id = aa.product.id";
	private static final String PAGE_NAME="awarded";//公示及奖励列表页面名称
	private String awardGradeid;//奖励等级id
	private int year1,year2;//获奖年份
	
	public String pageName() {
		return ApplicationAwardedAction.PAGE_NAME;
	}
	
	/**
	 * 初始化列表查询的hql、hql参数、默认排序列、错误信息(如有错误信息，加载列表时会显示该信息而非列表数据)
	 * @return 0:hql  1:hql参数   2:默认排序列编号  3:错误信息  
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object[] simpleSearchCondition() {
		AccountType  accountType = loginer.getCurrentType();
		Map map = new HashMap();
		StringBuffer hql = this.awardService.getHql(HQL, accountType);
		if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员
			map.put("belongId", baseService.getBelongIdByLoginer(loginer));
		}else if(accountType.compareTo(AccountType.MINISTRY)>0){//部级以下管理人员
			map.put("belongId", loginer.getCurrentBelongUnitId());
		}
		if (keyword1 > 0) {
			hql.append(" and aa.session =:session ");
			map.put("session", keyword1);
		}
		hql.append(" and ");
	    hql = this.awardService.getHql(hql, searchType,3);
		keyword = (keyword == null)? "" : keyword.toLowerCase();
	    map.put("keyword", "%" + keyword + "%");
		return new Object[]{
			hql.toString(),
			map,
			10,
			null
		};
	}

	//高级检索
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object[] advSearchCondition(){
		Map map = new HashMap();
		AccountType  accountType = loginer.getCurrentType();
		StringBuffer hql = this.awardService.getHql(HQL, accountType);
		if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员
			map.put("belongId", baseService.getBelongIdByLoginer(loginer));
		}else if(accountType.compareTo(AccountType.MINISTRY)>0){//部级以下管理人员
			map.put("belongId", loginer.getCurrentBelongUnitId());
		}
		if(productName != null && !productName.isEmpty()) {
			productName = productName.toLowerCase();
			hql.append(" and LOWER(aa.productName) like :productName");
			map.put("productName", "%" + productName + "%");
		}
		if (ptype != null && !"-1".equals(ptype)) {
			String ptypeid = awardService.fetchProductEnglish(ptype);
			hql.append(" and LOWER(pr.productType) like :ptypeid");
			map.put("ptypeid", ptypeid);
		}
		if(dtypeNames != null && !dtypeNames.isEmpty()){
			String[] dtypes = dtypeNames.split("; ");
			int len = dtypes.length;
			if(len > 0){
				hql.append(" and (");
				for(int i = 0; i < len; i++){
					map.put("disciplineType" + i, "%" + dtypes[i].toLowerCase() + "%");
					hql.append("LOWER(aa.disciplineType) like :disciplineType" + i);
					if (i != len-1)
						hql.append(" or ");
				}hql.append(")");
			}
		}
		if(applicantName != null && !applicantName.isEmpty()) {
			applicantName = applicantName.toLowerCase();
			hql.append(" and LOWER(aa.applicantName) like :applicantName");
			map.put("applicantName", "%" + applicantName + "%");
		}
		if(universityName != null && !universityName.isEmpty()) {
			universityName = universityName.toLowerCase();
			hql.append(" and LOWER(un.name) like :universityName");
			map.put("universityName", "%" + universityName + "%");
		}
		if(provinceName != null && !provinceName.isEmpty()) {
			provinceName = provinceName.toLowerCase();
			hql.append(" and LOWER(aa.provinceName) like :provinceName");
			map.put("provinceName", "%" + provinceName + "%");
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(startDate != null) {
			map.put("startDate", df.format(startDate));
			hql.append(" and aa.applicantSubmitDate is not null and to_char(aa.applicantSubmitDate,'yyyy-MM-dd')>=:startDate");
		}
		if(endDate != null) {
			map.put("endDate", df.format(endDate));
			if(startDate == null){
				hql.append(" and aa.applicantSubmitDate is not null ");
			}
			hql.append(" and to_char(aa.applicantSubmitDate,'yyyy-MM-dd')<=:endDate");
		}
		if(awardGradeid != null && !"-1".equals(awardGradeid)) {
			awardGradeid = awardGradeid.toLowerCase();
			hql.append(" and LOWER(gr.id) like :awardGradeid");
			map.put("awardGradeid",  awardGradeid );
		}
		if(year1 > 0) {
			hql.append(" and aa.year >=:year1");
			map.put("year1",  year1 );
		}
		if(year2 > 0) {
			hql.append(" and aa.year <=:year2");
			map.put("year2",  year2 );
		}
		if(session3 > 0){
			hql.append(" and aa.session >=:session3");
			map.put("session3",  session3 );
		}
		if(session2 > 0){
			hql.append(" and aa.session <=:session2");
			map.put("session2",  session2);
		}
		return new Object[]{
			hql.toString(),
			map,
			10,
			null
		};
	}
	
	/**
	 * 对saveAdvSearchQuery方法进行子类重写，以保存高级检索条件
	 * @author wangyan
	 */
	public void saveAdvSearchQuery(Map searchQuery) {
		AccountType  accountType = loginer.getCurrentType();
		if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员
			searchQuery.put("belongId", baseService.getBelongIdByLoginer(loginer));
		}else if(accountType.compareTo(AccountType.MINISTRY)>0){//部级以下管理人员
			searchQuery.put("belongId", loginer.getCurrentBelongUnitId());
		}
		if(productName != null && !productName.isEmpty()) {
			productName = productName.toLowerCase();
			searchQuery.put("productName", productName);
		}
		if (ptype != null && !"-1".equals(ptype)) {
			searchQuery.put("ptype", ptype);
		}
		if(dtypeNames != null && !dtypeNames.isEmpty()){
			searchQuery.put("dtypeNames", dtypeNames);
		}
		if(applicantName != null && !applicantName.isEmpty()) {
			applicantName = applicantName.toLowerCase();
			searchQuery.put("applicantName", applicantName);
		}
		if(universityName != null && !universityName.isEmpty()) {
			universityName = universityName.toLowerCase();
			searchQuery.put("universityName", universityName);
		}
		if(provinceName != null && !provinceName.isEmpty()) {
			provinceName = provinceName.toLowerCase();
			searchQuery.put("provinceName", provinceName);
		}
		if(awardGradeid != null && !"-1".equals(awardGradeid)) {
			awardGradeid = awardGradeid.toLowerCase();
			searchQuery.put("awardGradeid", awardGradeid);
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (null != startDate) {
			searchQuery.put("startDate", df.format(startDate));
		}
		if (null != endDate) {
			searchQuery.put("endDate", df.format(endDate));
		}
		if(session3 > 0){
			searchQuery.put("session3", session3);
		}
		if(session2 > 0){
			searchQuery.put("session2", session2);
		}
		if(year1 > 0) {
			searchQuery.put("year1", year1 );
		}
		if(year2 > 0) {
			searchQuery.put("year2", year2 );
		}
	}

	public String getAwardGradeid() {
		return awardGradeid;
	}
	public void setAwardGradeid(String awardGradeid) {
		this.awardGradeid = awardGradeid;
	}
	public int getYear1() {
		return year1;
	}
	public void setYear1(int year1) {
		this.year1 = year1;
	}
	public int getYear2() {
		return year2;
	}
	public void setYear2(int year2) {
		this.year2 = year2;
	}
}