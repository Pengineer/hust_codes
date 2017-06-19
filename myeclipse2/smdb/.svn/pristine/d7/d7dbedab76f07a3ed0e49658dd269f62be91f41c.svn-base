package csdc.action.product;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Institute;
import csdc.bean.Patent;
import csdc.bean.Person;
import csdc.bean.ProjectEndinspectionProduct;
import csdc.bean.ProjectGranted;
import csdc.bean.ProjectProduct;
import csdc.tool.ApplicationContainer;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.FileRecord;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProductInfo;

/**
 * 报告成果管理
 */
public class PatentAction extends ProductBaseAction {

	private static final long serialVersionUID = -7812941384300343442L;
	private static Integer PRODUCT_TYPE = 5;//成果形式（1：论文；2:著作；3:研究咨询报告；4：电子出版物；5：专利；6：其他成果）
	private static String HQL = "select p.id, p.chineseName, p.authorName, p.agencyName, p.inventorName, " +
		"p.file, aut.id, uni.id, p.divisionName, dep.id, ins.id, p.submitStatus, p.auditResult, " +
		"p.auditStatus, p.auditDate from Patent p left join p.university uni left join p.department dep " +
		"left join p.institute ins left join p.author aut where ";
	private static final String PAGE_NAME = "patentPage";
	private Patent patent;//专利对象
	private static final String[] COLUMN = new String[] {
		"p.chineseName",
		"p.wordNumber",
		"p.authorName",
		"p.divisionName",
		"p.agencyName",
		"p.inventorName",
		"p.publicDate",
		"p.submitStatus",
		"p.auditResult",
		"p.auditDate desc"
	};// 排序列
	
	public String[] column() {
		return PatentAction.COLUMN;
	}
	public String pageName() {
		return PatentAction.PAGE_NAME;
	}
	public Integer productType() {
		return PatentAction.PRODUCT_TYPE;
	};
	
	/**
	 * 初始化列表查询的hql、hql参数、默认排序列、错误信息(如有错误信息，加载列表时会显示该信息而非列表数据)
	 * @return 0:hql  1:hql参数   2:默认排序列编号  3:错误信息  
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object[] simpleSearchCondition() {
		int cloumnLabel = 0;//排序列位置
		keyword = (null == keyword)? "" : keyword.toLowerCase();
		StringBuffer hql = new StringBuffer(HQL);
		Map parMap = new HashMap();
		parMap.put("keyword", "%" + keyword + "%");
		if (searchType == 1) {//中文名称
			hql.append("LOWER(p.chineseName) like :keyword");
		} else if (searchType == 3) {//作者姓名
			hql.append("LOWER(p.authorName) like :keyword");
		} else if(searchType == 4){//单位名称
			hql.append("LOWER(p.agencyName) like :keyword");
		} else if(searchType == 5) {//发明人
			hql.append("LOWER(p.inventorName) like :keyword");
		} else if(searchType == 6) {//部门名称
			hql.append("LOWER(p.divisionName) like :keyword");
		} else {
			hql.append("(LOWER(p.chineseName) like :keyword or LOWER(p.authorName) like :keyword or LOWER(p.agencyName) like :keyword or LOWER(p.inventorName) like :keyword")
				.append(" or LOWER(p.divisionName) like :keyword) ");
		}
		String scopeHql = productService.getScopeHql(loginer.getAccount(), parMap);
		hql.append(scopeHql);
		return new Object[]{hql.toString(), parMap, cloumnLabel, null};
	}
	
	/**
	 * 高级检索
	 */
	@SuppressWarnings({ "unchecked", "rawtypes"})
	public Object[] advSearchCondition() {
		AccountType accountType = loginer.getCurrentType();
		int cloumnLabel = 0;//排序列位置
		StringBuffer hql = new StringBuffer(HQL);
		Map parMap = new HashMap();
		hql.append(" p.id is not null ");
		if(null != patent.getChineseName() && !patent.getChineseName().isEmpty()){
			parMap.put("chineseName", "%" + patent.getChineseName() + "%");
			hql.append(" and LOWER(p.chineseName) like :chineseName ");
		}
		if(null != patent.getDisciplineType() && !patent.getDisciplineType().isEmpty()){
			String[] dtypes = patent.getDisciplineType().split("; ");
			int len = dtypes.length;
			if(len>0){
				hql.append(" and (");
				for(int i = 0; i < len; i++){
					parMap.put("dtype" + i,"%" + dtypes[i].toLowerCase() + "%");
					hql.append("LOWER(p.disciplineType) like :dtype" + i);
					if (i != len - 1) hql.append(" or ");
				}
				hql.append(")");
			}
		} 
		if(null != patent.getAuthorName() && !patent.getAuthorName().isEmpty()){
			parMap.put("authorName", "%" + patent.getAuthorName() + "%");
			hql.append(" and LOWER(p.authorName) like :authorName");
		}
		if(null != patent.getAgencyName() && !patent.getAgencyName().isEmpty()){
			parMap.put("univ", "%" + patent.getAgencyName() + "%");
			hql.append(" and LOWER(p.agencyName) like :univ");
		}
		if(null != patent.getDivisionName() && !patent.getDivisionName().isEmpty()){
			parMap.put("unit", "%" + patent.getDivisionName() + "%");
			hql.append(" and LOWER(p.divisionName) like :unit");
		}
		if(null != patent.getInventorName() && !patent.getInventorName().isEmpty()){
			parMap.put("inventorName", "%" + patent.getInventorName() + "%");
			hql.append(" and nvl(LOWER(p.inventorName),' ') like :inventorName");
		}
		if(null != patent.getProvinceName() && !patent.getProvinceName().isEmpty()){
			parMap.put("provinceName", "%" + patent.getProvinceName() + "%");
			hql.append(" and  LOWER(p.provinceName) like :provinceName");
		}		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (null != pubDate1) {
			parMap.put("pubDate1", df.format(pubDate1));
			hql.append(" and p.publicDate is not null and  to_char(p.publicDate,'yyyy-MM-dd')>=:pubDate1");
		}
		if (null != pubDate2) {
			parMap.put("pubDate2", df.format(pubDate2));
			if(null == date1){
				hql.append(" and p.publicDate is not null");
			}
			hql.append(" and to_char(p.publicDate,'yyyy-MM-dd') <= :pubDate2");
		}
		//研究人员
		if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员
			if(patent.getSubmitStatus() != -1){
				parMap.put("submitStatus", patent.getSubmitStatus());
				hql.append(" and p.submitStatus = :submitStatus");
			}
		} else {//管理人员
			if(patent.getAuditResult() != -1){
				parMap.put("auditResult", patent.getAuditResult());
				hql.append(" and p.auditResult =:auditResult");
			}
			if(null != audDate1){
				parMap.put("audDate1", df.format(audDate1));
				hql.append(" and p.auditDate is not null and to_char(p.auditDate, 'yyyy-MM-dd') >= :audDate1");
			}
			if(null != audDate2){
				parMap.put("audDate2", df.format(audDate2));
				if(null == audDate1){
					hql.append(" and p.auditDate is not null");
				}
				hql.append(" and to_char(p.auditDate,'yyyy-MM-dd') <= :audDate2");
			}
		} 
		String scopeHql = productService.getScopeHql(loginer.getAccount(), parMap);
		hql.append(scopeHql);
		if(accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)){//系统管理员
			cloumnLabel =  12;
		} else if(accountType.equals(AccountType.DEPARTMENT) ||  accountType.equals(AccountType.INSTITUTE)){//院系或研究基地
			cloumnLabel =  11;
		} else{//其他用户
			cloumnLabel =  0;
		}
		return new Object[]{hql.toString(), parMap, cloumnLabel, null};
	}
	
	/**
	 * 对saveAdvSearchQuery方法进行子类重写，保存高级检索条件
	 * @author wangyan
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveAdvSearchQuery(Map searchQuery) {
		AccountType accountType = loginer.getCurrentType();
		if(null != patent.getChineseName() && !patent.getChineseName().isEmpty()){
			searchQuery.put("chineseName", patent.getChineseName());
		}
		if(null != patent.getDisciplineType() && !patent.getDisciplineType().isEmpty()){
			searchQuery.put("disciplineType", patent.getDisciplineType());
		} 
		if(null != patent.getAuthorName() && !patent.getAuthorName().isEmpty()){
			searchQuery.put("authorName", patent.getAuthorName());
		}
		if(null != patent.getAgencyName() && !patent.getAgencyName().isEmpty()){
			searchQuery.put("agencyName", patent.getAgencyName());
		}
		if(null != patent.getDivisionName() && !patent.getDivisionName().isEmpty()){
			searchQuery.put("divisionName", patent.getDivisionName());
		}
		if(null != patent.getInventorName() && !patent.getInventorName().isEmpty()){
			searchQuery.put("publication", patent.getInventorName());
		}
		if(null != patent.getProvinceName() && !patent.getProvinceName().isEmpty()){
			searchQuery.put("provinceName", patent.getProvinceName());
		}	
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (null != pubDate1) {
			searchQuery.put("pubDate1", df.format(pubDate1));
		}
		if (null != pubDate2) {
			searchQuery.put("pubDate2", df.format(pubDate2));
		}
		//研究人员
		if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员
			if(patent.getSubmitStatus() != -1){
				searchQuery.put("submitStatus", patent.getSubmitStatus());
			}
		} else {//管理人员
			if(patent.getAuditResult()!= -1){
				searchQuery.put("auditResult", patent.getAuditResult());
			}
			if(null != audDate1){
				searchQuery.put("audDate1", df.format(audDate1));
			}
			if(null != audDate2){
				searchQuery.put("audDate2", df.format(audDate2));
			}
		} 
	}
	
	/**
	 * 查看成果
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String view() throws IOException{
		//成果基本信息
		patent = (Patent)dao.query(Patent.class, entityId);
		jsonMap.put("productType", patent.getProductType());
		jsonMap.put("accountType", loginer.getCurrentType());
		jsonMap.put("patent", patent);
		jsonMap.put("form", (null != patent.getForm()) ? patent.getForm().getName() : null);//形态名称
		jsonMap.put("universityid", (null != patent.getUniversity()) ? patent.getUniversity().getId() : null);//高校id
		jsonMap.put("authorid", (null != patent.getAuthor()) ? patent.getAuthor().getId() : null);//作者id
		//团队信息
//		if(null != patent.getOrganization() && !patent.getOrganization().getId().isEmpty()) {
//			organization = (Organization)dao.query(Organization.class, patent.getOrganization().getId());
//		}
//		jsonMap.put("organization", organization);
		//项目相关信息
		jsonMap.put("relProjectInfos", productService.getRelProjectInfos(entityId));
		//奖励相关信息
		jsonMap.put("relAwardInfos", productService.getRelAwardInfos(entityId));
		//成果能否被修改
		boolean canBeModify = productService.canModifyProduct(entityId);
		if (patent.getFile() != null) {//显示下载文件的大小
			//获取文件大小
			List<String> attachmentSizeList = new ArrayList<String>();
			String[] attachPath = patent.getFile().split("; ");
			InputStream is = null;
			for (String path : attachPath) {
				is = ServletActionContext.getServletContext().getResourceAsStream(path);
				if (null != is) {
					attachmentSizeList.add(baseService.accquireFileSize(is.available()));
				} else {// 附件不存在
					attachmentSizeList.add(null);
				}
				jsonMap.put("attachmentSizeList", attachmentSizeList);
			}
		}
		jsonMap.put("canBeModify", canBeModify);
		session.put("canBeModify", canBeModify);
		session.put("patentViewJson", jsonMap);
		return SUCCESS;
	}
	
	/**
	 * 保存成果
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String add() throws Exception {
		//成果附件信息
		String groupId = "file_add_pat";
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
				File curFile = fileRecord.getOriginal();
				String newFileName = productService.getFileName(curFile, savePath, PRODUCT_TYPE);
				patent.setFile(newFileName);
				fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(patent.getFile())));//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		//成果作者、单位信息
		if(applyType == 1) {//1.以团队名义申报成果
			patent = (Patent)productService.fillAuthorAndAgencyInfos(loginer.getCurrentType(), patent, organizationAuthorId, organizationAuthorTypeId, organizationAuthorType);
			//获取人员对象
			Person person = (Person)dao.query(Person.class, organizationAuthorId);
			patent.setOrgPerson(person);
		} else {//2.以个人名义申报成果
			patent = (Patent)productService.fillAuthorAndAgencyInfos(loginer.getCurrentType(), patent, authorId, authorTypeId, authorType);
		}
		
		//成果审核信息[管理人员添加, 默认审核通过]
		patent = (loginer.getCurrentType().compareTo(AccountType.EXPERT)<0) ? (Patent)productService.fillAuditInfo(loginer.getAccount(), patent, 2) : patent;
		
		//将提交时间设为系统时间
		patent.setSubmitDate(new Date());
		entityId = dao.add(patent);
		
		//项目相关信息
		if(isProRel == 1) {
			ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, projectGrantedId) ;
			projectProduct.setProjectGranted(projectGranted);
			projectProduct.setProduct(patent);
			dao.add(projectProduct);
		}
		
		//成果绑定团队
//		if(null != session.get("awardOrganizationId")) {
//			String organizationId = (String)session.get("awardOrganizationId");
//			organization = (Organization)dao.query(Organization.class, organizationId);
//			patent.setOrganization(organization);
//			dao.modify(patent);
//			session.remove("awardOrganizationId");
//		}
		
		//外部弹出层添加项目成果
		if(null != viewType && viewType != 0){
			patent.setSubmitStatus(3);
			//项目添加成果
			if(viewType == 1 || viewType == 2 || viewType == 3 || viewType == 4) {
				productService.addNewProductToProjectInspection(projectProduct, entityId, projectId, viewType, inspectionId, isFinalProduct);
				//设置结项最终成果
		 		if(viewType == 3) {
		 			productService.setEndInspectionFinalProduct(entityId, inspectionId, isFinalProduct);
		 		}
			} else if(viewType == 5) {//奖励添加成果
				jsonMap.put("productId", entityId);
				jsonMap.put("productType", patent.getProductType());
			}
			//将文件组(groupId)中文件的更新操作（删除、移动）在磁盘中实施
			uploadService.flush(groupId);
			return "finish";
		}
		
		return SUCCESS;
	}
	
	/**
	 * 校验添加成果
	 * @param patentService
	 */
	public void validateAdd(){
		validateMust(); validateEdit();//校验
		String groupId = "file_add_pat";
		if (uploadService.getGroupFiles(groupId).isEmpty() || uploadService.getGroupFiles(groupId).size() == 0) {
			this.addFieldError("file", ProductInfo.ERROR_FILE_NULL);
		} else if (!uploadService.getGroupFiles(groupId).isEmpty() && uploadService.getGroupFiles(groupId).size() > 1) {
			this.addFieldError("file", ProductInfo.ERROR_FILE_OUT);
		}
//		if(null == fileIds || fileIds.length == 0){
//			this.addFieldError("file", ProductInfo.ERROR_FILE_NULL);
//		} else if(fileIds.length > 1){
//			this.addFieldError("file", ProductInfo.ERROR_FILE_OUT);
//		}
		inputView = (null != exflag && exflag == 1) ? "/product/extIf/popAdd.jsp" : "/product/patent/add.jsp";
	}
	
	/**
	 * 转到成果更新
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String toModify(){
		//判断是够在权限范围之内
		if(!productService.checkIfUnderControl(loginer, entityId.trim(), 15, true)){
			addActionError("您所选择的成果不在您的管辖范围内！");
			return ERROR;}
		patent = (Patent)dao.query(Patent.class, entityId);
		if(null != patent.getChineseName()){
			patent.setChineseName(patent.getChineseName().trim());
		}
		//判断是否为项目成果
		if(productService.isRelProduct(entityId)) {
			Map projectInfos = productService.getRelProjectInfos(entityId).get(0);
			session.put("projectInfos", projectInfos);
			isProRel = 1;
		}
		//获取团队信息
		if(null != patent.getOrgName()) {
			organizationAuthorId = (null != patent.getAuthor()) ? patent.getAuthor().getId() : null;
			organizationAuthorTypeId = productService.getAuthorIdOfAuthorType(entityId);
			organizationAuthorType = patent.getAuthorType();
			applyType = 1;
		} else {//获取个人信息
			authorId = (null != patent.getAuthor()) ? patent.getAuthor().getId() : null;
			authorTypeId = productService.getAuthorIdOfAuthorType(entityId);
			authorType = patent.getAuthorType();
			if(null != authorId){
				author = (Person)dao.query(Person.class, patent.getAuthor().getId());
				fetchAuthorInfo();//获取作者信息
			}
		}
		if(null != viewType) {
			//获取项目立项成果对象
			projectProduct = productService.getProjectProductByProductId(patent.getId());
			if(viewType == 3) {//结项成果
				ProjectEndinspectionProduct ProjectEndinspectionProduct = productService.getProjectEndinspectionProduct(entityId, inspectionId);
				isFinalProduct = (null != ProjectEndinspectionProduct) ? ProjectEndinspectionProduct.getIsFinalProduct() : 0;
			}
		}
		//将已有附件加入文件组，在编辑页面显示
		String groupId = "file_" + patent.getId();
		uploadService.resetGroup(groupId);
		if (patent.getFile() != null) {
			String bookFilepath = ApplicationContainer.sc.getRealPath(patent.getFile());
			if (bookFilepath != null) {
				uploadService.addFile(groupId, new File(bookFilepath));
			}
		}
		session.put("projectType", ProjectGranted.typeMap);
		session.put("applyType", applyType);
		session.put("entityId", entityId);
		return (null != viewType) ? "finish" : SUCCESS;//项目修改成果
	}

	/**
	 * 更新成果
	 */
	@SuppressWarnings("rawtypes")
	@Transactional
	public String modify()throws Exception{
		Patent prePatent=(Patent)dao.query(Patent.class, entityId);
		
		//基本信息更新
		prePatent.setSubmitStatus(patent.getSubmitStatus());
		prePatent.setChineseName(patent.getChineseName().trim());
		prePatent.setEnglishName((null != patent.getEnglishName()) ? patent.getEnglishName().trim() : null);
		prePatent.setOtherAuthorName((null != patent.getOtherAuthorName()) ? productService.MutipleToFormat(personExtService.regularNames(patent.getOtherAuthorName())) : null);	
		prePatent.setForm(patent.getForm());
		prePatent.setIsForeignCooperation(patent.getIsForeignCooperation());
		prePatent.setDisciplineType(patent.getDisciplineType().trim());
		prePatent.setDiscipline(patent.getDiscipline().trim());
		prePatent.setKeywords(this.productService.MutipleToFormat(patent.getKeywords().trim()));
		prePatent.setIntroduction((null != patent.getIntroduction()) ? ("A" + patent.getIntroduction()).trim().substring(1) : null);	 
		prePatent.setInventorName(patent.getInventorName().trim());
		prePatent.setApplicationNumber(patent.getApplicationNumber().trim());
		prePatent.setApplicationDate(patent.getApplicationDate());
		prePatent.setPublicNumber(patent.getPublicNumber().trim());
		prePatent.setPublicDate(patent.getPublicDate());
		prePatent.setClassNumber(patent.getClassNumber().trim());
		prePatent.setPriorityNumber((null != patent.getPriorityNumber()) ? patent.getPriorityNumber().trim() : null);
		prePatent.setIndependentClaims(patent.getIndependentClaims().trim());
		prePatent.setCategotyType((null != patent.getCategotyType()) ? patent.getCategotyType().trim() : null);
		prePatent.setSummary(patent.getSummary().trim());
		prePatent.setCountriesProvinceCode(patent.getCountriesProvinceCode().trim());
		
		//处理附件
		String groupId = "file_" + prePatent.getId();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
				File curFile = fileRecord.getOriginal();
				String newFileName = productService.getFileName(curFile, savePath, PRODUCT_TYPE);
				prePatent.setFile(newFileName);
				fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(prePatent.getFile())));//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}

		//非外部修改成果
	 	if(null == viewType) {
		 	applyType = (Integer)session.get("applyType");
		 	if(applyType == 1) {//1.以团队名义
		 		//填充成果作者、单位信息
		 		prePatent = (Patent)productService.fillAuthorAndAgencyInfos(loginer.getCurrentType(), prePatent, organizationAuthorId, organizationAuthorTypeId, organizationAuthorType);
		 		prePatent.setOrgName(patent.getOrgName());
		 		prePatent.setOrgDiscipline(patent.getOrgDiscipline());
		 		prePatent.setOrgEmail(patent.getOrgEmail());
		 		prePatent.setOrgMember(patent.getOrgMember());
		 		prePatent.setOrgMobilePhone(patent.getOrgMobilePhone());
		 		prePatent.setOrgOfficeAddress(patent.getOrgOfficeAddress());
		 		prePatent.setOrgOfficePhone(patent.getOrgOfficePhone());
		 		prePatent.setOrgPerson(patent.getOrgPerson());
		 		prePatent.setOrgOfficePostcode(patent.getOrgOfficePostcode());
		 		
		 	} else {
		 		//填充成果作者、单位信息
		 		prePatent = (Patent)productService.fillAuthorAndAgencyInfos(loginer.getCurrentType(), prePatent, authorId, authorTypeId, authorType);
		 	}
		 	
			//如果是项目成果, 保存关联信息
			if(isProRel == 1) {
				projectProduct.setProduct(prePatent);
				dao.add(projectProduct);
			}
	 	} else {//外部项目修改成果
	 		ProjectProduct oldProjectProduct = productService.getProjectProductByProductId(prePatent.getId());
	 		oldProjectProduct.setIsMarkMoeSupport(projectProduct.getIsMarkMoeSupport());
	 		dao.modify(oldProjectProduct);
	 		//设置结项最终成果
	 		if(viewType == 3) {
	 			productService.setEndInspectionFinalProduct(entityId, inspectionId, isFinalProduct);
	 		}
	 	}
		dao.modify(prePatent);
		//将文件组(groupId)中文件的更新操作（删除、移动）在磁盘中实施
		uploadService.flush(groupId);
		return (null != viewType) ? "finish" : SUCCESS;//项目修改成果
	}
	
	/**
	 * 校验更新成果
	 */
	@SuppressWarnings("unchecked")
	public void validateModify(){
		String productId = (String)session.get("entityId");
		if(!productId.equals(entityId)) {
			this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_NO_RIGHT_MODIFY);
			jsonMap.put(GlobalInfo.ERROR_INFO,ProductInfo.ERROR_NO_RIGHT_MODIFY);
		} else if(!productService.checkIfUnderControl(loginer, entityId, 15, true)) {
			this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_NOT_IN_SCOPE);
			jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_NOT_IN_SCOPE);
		}
		validateMust(); validateEdit();//校验
		Patent prePatent=(Patent)dao.query(Patent.class, entityId);
		String groupId = "file_" + prePatent.getId();
		if (uploadService.getGroupFiles(groupId).isEmpty() || uploadService.getGroupFiles(groupId).size() == 0) {
			this.addFieldError("file", ProductInfo.ERROR_FILE_NULL);
		} else if (!uploadService.getGroupFiles(groupId).isEmpty() && uploadService.getGroupFiles(groupId).size() > 1) {
			this.addFieldError("file", ProductInfo.ERROR_FILE_OUT);
		}
//		if(fileIds != null && fileIds.length > 1){
//			this.addFieldError("file", ProductInfo.ERROR_FILE_OUT);
//		}
	}
	
	//校验添加后不能修改的字段
	public void validateMust(){
		if(null == patent.getChineseName() || "".equals(patent.getChineseName())){
			addFieldError("patent.chineseName", ProductInfo.ERROR_CHINESE_NAME_NULL);
		} else if(patent.getChineseName().trim().length() > 50){
			addFieldError("patent.chineseName", ProductInfo.ERROR_CHINESE_NAMR_OUT);
		}
		if(null == authorId || "".equals(authorId)){
			addFieldError("patent.authorName", ProductInfo.ERROR_FIRST_AUTHOR_NULL);
		}
		if(null != patent.getOtherAuthorName() && patent.getOtherAuthorName().trim().length() > 200){
			addFieldError("patent.otherAuthorName", ProductInfo.ERROR_OTHER_AUTHOR_NAME_OUT);
		}
		if(null == patent.getDisciplineType() || "".equals(patent.getDisciplineType())){
			addFieldError("patent.disciplineType", ProductInfo.ERROR_DTYPE_NULL);
		} else if(patent.getDisciplineType().trim().length() > 100){
			addFieldError("patent.disciplineType", ProductInfo.ERROR_DTYPE_OUT);
		}
		if(null == patent.getDiscipline() || "".equals(patent.getDiscipline())){
			addFieldError("patent.discipline", ProductInfo.ERROR_DISCIPLINE_NULL);
		} else if(patent.getDiscipline().trim().length() > 100){
			addFieldError("patent.discipline", ProductInfo.ERROR_DISCIPLINE_OUT);
		}
		if(isProRel == 1){
		}
		//校验外部弹出层添加项目成果
		if(null != viewType && viewType != 0){
			if(viewType == 1) {//添加年检成果
				if(!productService.canAddAnnProduct(projectId)){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_CANNOT_ADD_ANN_PRODUCT);
				}
			}else if(viewType == 2) {//添加中检成果
				if(!productService.canAddMidProduct(projectId)){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_CANNOT_ADD_MID_PRODUCT);
				}
			} else if(viewType == 3) {//添加结项成果
				if(!productService.canAddEndProduct(projectId)){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_CANNOT_ADD_END_PRODUCT);
				}
			} else if(viewType == 4) {//添加相关成果
				if(!productService.canAddRelProduct(projectId)){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_CANNOT_ADD_END_PRODUCT);
				}
			}
		}
	}
	
	//添加和修改共用的校验
	public void validateEdit(){
		if(null != patent.getEnglishName() && patent.getEnglishName().trim().length() > 200){
			addFieldError("patent.chineseName", ProductInfo.ERROR_ENGLIST_NAME_OUT);
		}
		if(null == patent.getForm() || "-1".equals(patent.getForm().getId())){
			addFieldError("patent.form.id", ProductInfo.ERROR_FORM_NULL);
		}
		if(patent.getKeywords().trim().length() > 100){
			addFieldError("patent.keywords", ProductInfo.ERROR_KEYWORD_OUT);
		}
		if(null != patent.getIntroduction() && patent.getIntroduction().trim().length() > 20000){
			addFieldError("patent.introduction", ProductInfo.ERROR_INTRODUCTION_OUT);
		}
	}
	
	/**
	 * 加载系统选项
	 */
	@SuppressWarnings("unchecked")
	public void initOptions() {
		fetchAuthorInfo();//获取作者信息
		session.put("productType", baseService.getSOByParentName("成果形态"));//获取成果形态
		session.put("projectType", ProjectGranted.typeMap);//获取项目类型
	}
	
	public Patent getPatent() {
		return patent;
	}
	public void setPatent(Patent patent) {
		this.patent = patent;
	}
}