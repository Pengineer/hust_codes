package csdc.action.award.moesocial;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import csdc.tool.bean.AccountType;


/**
 * 社科奖励公示数据管理
 * @author 余潜玉 王燕
 */
public class ApplicationPublicityAction extends ApplicationAction {

	private static final long serialVersionUID = 6548521984474957014L;
	private String awardGradeid;
	private static final String HQL1 = "select aa.id, aa.productName, aa.applicantName, aa.agencyName, aa.disciplineType, pr.productType, gr.name, aa.session, aa.applicant.id, un.id, aa.file" +
		" from AwardApplication aa left outer join aa.university un, Product pr, SystemOption gr where aa.reviewGrade.id=gr.id and aa.status=8 and aa.finalAuditStatus != 3 and pr.id = aa.product.id";
	private static final String HQL2 = "select aa.id, aa.productName, aa.applicantName, aa.agencyName, aa.disciplineType, pr.productType, gr.name, aa.session, aa.applicant.id, un.id, aa.file, aa.finalAuditStatus, aa.finalAuditResult, aa.finalAuditDate" +
		" from AwardApplication aa left outer join aa.university un, Product pr, SystemOption gr where aa.reviewGrade.id = gr.id and aa.status = 8 and aa.finalAuditStatus != 3 and pr.id = aa.product.id";
	private static final String PAGE_NAME="publicity";//公示及奖励列表页面名称

	public String pageName() {
		return ApplicationPublicityAction.PAGE_NAME;
	}

	/**
	 * 初始化列表查询的hql、hql参数、默认排序列、错误信息(如有错误信息，加载列表时会显示该信息而非列表数据)
	 * @return 0:hql  1:hql参数   2:默认排序列编号  3:错误信息  
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object[] simpleSearchCondition() {
		Map map = new HashMap();
		AccountType accountType = loginer.getCurrentType();
		StringBuffer hql = (accountType.compareTo(AccountType.PROVINCE)<0) ? new StringBuffer(HQL2) : this.awardService.getHql(HQL1, accountType);
		if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员
			map.put("belongId",baseService.getBelongIdByLoginer(loginer));
		}else if(accountType.compareTo(AccountType.MINISTRY)>0){//部级以下管理人员
			map.put("belongId", loginer.getCurrentBelongUnitId());
		}
		if (keyword1 > 0) {
			hql.append(" and aa.session =:session ");
			map.put("session", keyword1);
		}
		hql.append(" and ");
	    hql = this.awardService.getHql(hql, searchType,2);
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
		AccountType accountType = loginer.getCurrentType();
		StringBuffer hql = (accountType.compareTo(AccountType.PROVINCE)<0) ? new StringBuffer(HQL2) : this.awardService.getHql(HQL1, accountType);
		if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员
			map.put("belongId",baseService.getBelongIdByLoginer(loginer));
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
		if (universityName != null && !universityName.isEmpty()) {
			universityName = universityName.toLowerCase();
			hql.append(" and LOWER(un.name) like :universityName");
			map.put("universityName", "%" + universityName + "%");
		}
		if (provinceName != null && !provinceName.isEmpty()) {
			provinceName = provinceName.toLowerCase();
			hql.append(" and LOWER(aa.name) like :provinceName");
			map.put("provinceName", "%" + provinceName + "%");
		}
		if (awardGradeid != null && !"-1".equals(awardGradeid)) {
			awardGradeid = awardGradeid.toLowerCase();
			hql.append(" and LOWER(gr.id) like :awardGradeid");
			map.put("awardGradeid",  awardGradeid );
		}
		if(session3 > 0){
			hql.append(" and aa.session >=:session3");
			map.put("session3",  session3 );
		}
		if(session2 > 0){
			hql.append(" and aa.session <=:session2");
			map.put("session2",  session2);
		}
		if(accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)){
			int resultStatus,saveStatus;
			if(status != -1){
				saveStatus=status/10;
				resultStatus=status%10;
				map.put("auditStatus",  saveStatus);
				map.put("auditResult", resultStatus);
				hql.append(" and aa.finalAuditStatus =:auditStatus and aa.finalAuditResult =:auditResult");
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			if (startDate != null) {
				map.put("startDate", df.format(startDate));
				hql.append(" and aa.finalAuditDate is not null and to_char(aa.finalAuditDate,'yyyy-MM-dd')>=:startDate");
			}
			if (endDate != null) {
				map.put("endDate", df.format(endDate));
				if(startDate == null){
					hql.append(" and aa.finalAuditDate is not null");
				}
				hql.append(" and to_char(aa.finalAuditDate,'yyyy-MM-dd')<=:endDate");
			}
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
		AccountType accountType = loginer.getCurrentType();
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
		if(session3 > 0){
			searchQuery.put("session1", session1);
		}
		if(session2 > 0){
			searchQuery.put("session2", session2);
		}
		if(accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)){
			if(status != -1){
				searchQuery.put("status", status);
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			if (null != startDate) {
				searchQuery.put("startDate", df.format(startDate));
			}
			if (null != endDate) {
				searchQuery.put("endDate", df.format(endDate));
			}
		}
	}
	
	public String getAwardGradeid() {
		return awardGradeid;
	}

	public void setAwardGradeid(String awardGradeid) {
		this.awardGradeid = awardGradeid;
	}
}