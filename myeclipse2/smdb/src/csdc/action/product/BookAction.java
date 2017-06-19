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
import csdc.bean.Book;
import csdc.bean.Department;
import csdc.bean.Institute;
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
 * 著作成果管理
 */
public class BookAction extends ProductBaseAction {

	private static final long serialVersionUID = 8083232816846568191L;
	private static Integer PRODUCT_TYPE = 2;//成果形式（1：论文；2:著作；3:研究咨询报告； 4：电子出版物；5专利； 6其他成果）
	private static String HQL = "select p.id, p.chineseName, p.authorName, p.agencyName, p.publishUnit, " +
		"p.file, aut.id, uni.id, p.divisionName, dep.id, ins.id, s.name, p.submitStatus, p.auditResult, " +
		"p.auditStatus, p.auditDate from Book p left join p.university uni left join p.department dep " +
		"left join p.institute ins left join p.type s left join p.author aut where ";
	private static final String PAGE_NAME = "bookPage";
	private Book book;//著作对象
	private static final String[] COLUMN = new String[] {
		"p.chineseName",
		"s.name",
		"p.authorName",
		"p.divisionName",
		"p.agencyName",
		"p.publishUnit",
		"p.submitStatus",
		"p.auditResult",
		"p.auditDate desc"
	};// 排序列
	public String[] column() {
		return BookAction.COLUMN;
	}
	public String pageName() {
		return BookAction.PAGE_NAME;
	}
	public Integer productType() {
		return BookAction.PRODUCT_TYPE;
	};
	
	/**
	 * 初始化列表查询的hql、hql参数、默认排序列、错误信息(如有错误信息，加载列表时会显示该信息而非列表数据)
	 * @return 0:hql  1:hql参数   2:默认排序列编号  3:错误信息  
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object[] simpleSearchCondition() {
		int cloumnLabel = 0;//排序列位置
		keyword = (null == keyword) ? "" : keyword.toLowerCase();
		StringBuffer hql = new StringBuffer(HQL);
		Map parMap = new HashMap();
		parMap.put("keyword", "%" + keyword + "%");
		if (searchType == 1) {
			hql.append("LOWER(p.chineseName) like :keyword");
		} else if(searchType == 2){
			hql.append("LOWER(s.name) like :keyword");
		} else if (searchType == 3) {
			hql.append("LOWER(p.authorName) like :keyword");
		} else if (searchType == 4) {
			hql.append("LOWER(p.agencyName) like :keyword");
		} else if(searchType == 5){
			hql.append("LOWER(p.publishUnit) like :keyword");
		} else if (searchType == 6) {
			hql.append("LOWER(p.divisionName) like :keyword");
		} else {
			hql.append("(LOWER(p.chineseName) like :keyword or LOWER(s.name) like :keyword or LOWER(p.authorName) like :keyword or LOWER(p.agencyName) like :keyword")
				.append(" or LOWER(p.publishUnit) like :keyword or LOWER(p.divisionName) like :keyword) ");
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
		if(null != book.getChineseName() && !book.getChineseName().isEmpty()){
			parMap.put("chineseName", "%" + book.getChineseName() + "%");
			hql.append(" and LOWER(p.chineseName) like :chineseName ");
		}
		if(!book.getType().getId().equals("-1")){
			parMap.put("type", book.getType().getId());
			hql.append(" and s.id like :type ");
		}
		if(null != book.getDisciplineType() && !book.getDisciplineType().isEmpty()){
			String[] dtypes = book.getDisciplineType().split("; ");
			int len = dtypes.length;
			if(len > 0){
				hql.append("and (");
				for(int i = 0; i < len; i++){
					parMap.put("dtype" + i, "%" + dtypes[i].toLowerCase() + "%");
					hql.append("LOWER(p.disciplineType) like :dtype" + i);
					if (i != len-1) {hql.append(" or ");}
				}
				hql.append(")");
			}
		} 
		if(null != book.getAuthorName() && !book.getAuthorName().isEmpty()){
			parMap.put("authorName", "%" + book.getAuthorName() + "%");
			hql.append(" and LOWER(p.authorName) like :authorName");
		}
		if(null != book.getAgencyName()&& !book.getAgencyName().isEmpty()){
			parMap.put("univ", "%"+book.getAgencyName() + "%");
			hql.append(" and LOWER(p.agencyName) like :univ");
		}
		if(null != book.getDivisionName()&& !book.getDivisionName().isEmpty()){
			parMap.put("unit", "%" + book.getDivisionName() + "%");
			hql.append(" and LOWER(p.divisionName) like :unit");
		}
		if(null != book.getPublishUnit() && !book.getPublishUnit().isEmpty()){
			parMap.put("publicationUnit", "%" + book.getPublishUnit() + "%");
			hql.append(" and LOWER(p.publishUnit) like :publicationUnit");
		}
		if(null != book.getProvinceName() && !book.getProvinceName().isEmpty()){
			parMap.put("provinceName", "%" + book.getProvinceName() + "%");
			hql.append(" and  LOWER(p.provinceName) like :provinceName");
		}		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (null != pubDate1) {
			parMap.put("pubDate1", df.format(pubDate1));
			hql.append(" and p.publishDate is not null and to_char(p.publishDate,'yyyy-MM-dd')>=:pubDate1");
		}
		if (null != pubDate2) {
			parMap.put("pubDate2", df.format(pubDate2));
			if(null == pubDate1){
				hql.append(" and p.publishDate is not null");
			}
			hql.append(" and to_char(p.publishDate,'yyyy-MM-dd') <= :pubDate2");
		}
		//研究人员
		if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员
			if(book.getSubmitStatus() != -1){
				parMap.put("submitStatus", book.getSubmitStatus());
				hql.append(" and p.submitStatus = :submitStatus");
			}
		} else {//管理人员
			if(book.getAuditResult() != -1){
				parMap.put("auditResult", book.getAuditResult());
				hql.append(" and p.auditResult = :auditResult");
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
		//判断是否在查看范围之内
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
	 * 对saveAdvSearchQuery方法进行子类重写，以保存高级检索条件
	 * @author wangyan
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveAdvSearchQuery(Map searchQuery) {
		AccountType accountType = loginer.getCurrentType();
		if(null != book.getChineseName() && !book.getChineseName().isEmpty()){
			searchQuery.put("chineseName", book.getChineseName());
		}
		if(!book.getType().getId().equals("-1")){
			searchQuery.put("type", book.getType().getId());
		}
		if(null != book.getDisciplineType() && !book.getDisciplineType().isEmpty()){
			searchQuery.put("disciplineType", book.getDisciplineType());
		} 
		if(null != book.getAuthorName() && !book.getAuthorName().isEmpty()){
			searchQuery.put("authorName", book.getAuthorName());
		}
		if(null != book.getAgencyName() && !book.getAgencyName().isEmpty()){
			searchQuery.put("agencyName", book.getAgencyName());
		}
		if(null != book.getDivisionName() && !book.getDivisionName().isEmpty()){
			searchQuery.put("divisionName", book.getDivisionName());
		}
		if(null != book.getPublishUnit() && !book.getPublishUnit().isEmpty()){
			searchQuery.put("publishUnit", book.getPublishUnit());
		}
		if(null != book.getProvinceName() && !book.getProvinceName().isEmpty()){
			searchQuery.put("provinceName", book.getProvinceName());
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
			if(book.getSubmitStatus() != -1){
				searchQuery.put("submitStatus", book.getSubmitStatus());
			}
		} else {//管理人员
			if(book.getAuditResult()!= -1){
				searchQuery.put("auditResult", book.getAuditResult());
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
		book = (Book)dao.query(Book.class, entityId);//获取著作对象
		jsonMap.put("productType", book.getProductType());//著作类型
		jsonMap.put("accountType", loginer.getCurrentType());//账号类型
		jsonMap.put("book", book);//著作对象
		jsonMap.put("bookType", (null != book.getType()) ? book.getType().getName() : null);//著作类型名称
		jsonMap.put("bookTypeId", (null != book.getType()) ? book.getType().getId() : null);//著作类型id
		jsonMap.put("form", (null != book.getForm()) ? book.getForm().getName() : null);//形态名称
		jsonMap.put("universityid", (null != book.getUniversity()) ? book.getUniversity().getId() : null);//高校id
		jsonMap.put("authorid", (null != book.getAuthor()) ? book.getAuthor().getId() : null);//作者id
//		//团队信息
//		if(null != book.getOrganization() && !book.getOrganization().getId().isEmpty()) {
//			organization = (Organization)dao.query(Organization.class, book.getOrganization().getId());
//		}
//		jsonMap.put("organization", organization);
		//项目相关信息
		jsonMap.put("relProjectInfos", productService.getRelProjectInfos(entityId));
		//奖励相关信息
		jsonMap.put("relAwardInfos", productService.getRelAwardInfos(entityId));
		//成果能否被修改
		boolean canBeModify = productService.canModifyProduct(entityId);
		if (book.getFile() != null) {
			//获取文件大小
			List<String> attachmentSizeList = new ArrayList<String>();
			String[] attachPath = book.getFile().split("; ");
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
		session.put("bookViewJson", jsonMap);
		return SUCCESS;
	}
	
	/**
	 * 保存成果
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String add()throws Exception{
		//成果文件信息
		String groupId = "file_add_book";
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
				File curFile = fileRecord.getOriginal();
				String newFileName = productService.getFileName(curFile, savePath, PRODUCT_TYPE);
				book.setFile(newFileName);
				fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(book.getFile())));//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		//成果作者、单位信息
		if(applyType == 1) {//1.以团队名义申报成果
			book = (Book)productService.fillAuthorAndAgencyInfos(loginer.getCurrentType(), book, organizationAuthorId, organizationAuthorTypeId, organizationAuthorType);
			Person person = (Person)dao.query(Person.class, organizationAuthorId);
			book.setOrgPerson(person);
		} else {//2.以个人名义申报成果
			//成果作者、单位信息
			book = (Book)productService.fillAuthorAndAgencyInfos(loginer.getCurrentType(), book, authorId, authorTypeId, authorType);
		}
		
		//成果审核信息[管理人员添加, 默认审核通过]
		book = (loginer.getCurrentType().compareTo(AccountType.EXPERT)<0) ? (Book)productService.fillAuditInfo(loginer.getAccount(), book, 2) : book;
		
		//将提交时间设为系统时间 
		book.setSubmitDate(new Date());
		entityId = dao.add(book);
		
		//项目相关信息
		if(isProRel == 1) {
			ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, projectGrantedId) ;
			projectProduct.setProjectGranted(projectGranted);
			projectProduct.setProduct(book);
			dao.add(projectProduct);
		}
		
		//成果绑定团队
//		if(null != session.get("awardOrganizationId")) {
//			String organizationId = (String)session.get("awardOrganizationId");
//			organization = (Organization)dao.query(Organization.class, organizationId);
//			book.setOrganization(organization);
//			dao.modify(book);
//			session.remove("awardOrganizationId");
//		}
		
		//外部弹出层添加项目成果
		if(null != viewType && viewType != 0){
			book.setSubmitStatus(3);
			//项目添加成果
			if(viewType == 1 || viewType == 2 || viewType == 3 || viewType == 4) {
				productService.addNewProductToProjectInspection(projectProduct, entityId, projectId, viewType, inspectionId, isFinalProduct);
				//设置结项最终成果
		 		if(viewType == 3) {
		 			productService.setEndInspectionFinalProduct(entityId, inspectionId, isFinalProduct);
		 		}
			} else if(viewType == 5) {//奖励添加成果
				jsonMap.put("productId", entityId);
				jsonMap.put("productType", book.getProductType());
			}
			return "finish";
		}
		//将文件组(groupId)中文件的更新操作（删除、移动）在磁盘中实施
		uploadService.flush(groupId);
		return SUCCESS;
	}
	
	/**
	 * 校验添加成果
	 */
	public void validateAdd(){
		validateMust(); validateEdit();//校验
		//校验附件
		String groupId = "file_add_book";
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
		inputView = (null != exflag && exflag ==1) ? "/product/extIf/popAdd.jsp" : "/product/book/add.jsp";
	}
	
	/**
	 * 转到成果更新
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String toModify() throws Exception{
		//判断是否在管辖范围之内
		if(!productService.checkIfUnderControl(loginer, entityId.trim(), 15, true)){
			addActionError("您所选择的成果不在您的管辖范围内！");
			return ERROR;}
		book = (Book)dao.query(Book.class, entityId);
		if(null != book.getChineseName()){
			book.setChineseName(book.getChineseName().trim());
		}
		if(null != book.getOtherAuthorName()){
			book.setOtherAuthorName(book.getOtherAuthorName().trim());
		}
		if(book.getWordNumber() == 0.0){
			book.setWordNumber(0);
		}
		//是否为项目成果
		if(productService.isRelProduct(entityId)) {
			Map projectInfos = productService.getRelProjectInfos(entityId).get(0);
			session.put("projectInfos", projectInfos);
			isProRel = 1;
		}
		//获取团队信息
		if(null != book.getOrgName()) {
			organizationAuthorId = (null != book.getAuthor()) ? book.getAuthor().getId() : null;
			organizationAuthorTypeId = productService.getAuthorIdOfAuthorType(entityId);
			organizationAuthorType = book.getAuthorType();
			applyType = 1;
		} else {//获取个人信息
			authorId = (null != book.getAuthor()) ? book.getAuthor().getId() : null;
			authorTypeId = productService.getAuthorIdOfAuthorType(entityId);
			authorType = book.getAuthorType();
			if(null != authorId){
				author = (Person)dao.query(Person.class, book.getAuthor().getId());
				fetchAuthorInfo();//获取作者信息
			}
		}
		if(null != viewType) {
			//获取项目立项成果对象
			projectProduct = productService.getProjectProductByProductId(book.getId());
			if(viewType == 3) {//结项成果
				ProjectEndinspectionProduct ProjectEndinspectionProduct = productService.getProjectEndinspectionProduct(entityId, inspectionId);
				isFinalProduct = (null != ProjectEndinspectionProduct) ? ProjectEndinspectionProduct.getIsFinalProduct() : 0;
			}
		}
		//将已有附件加入文件组，在编辑页面显示
		String groupId = "file_" + book.getId();
		uploadService.resetGroup(groupId);
		if (book.getFile() != null) {
			String bookFilepath = ApplicationContainer.sc.getRealPath(book.getFile());
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
	@SuppressWarnings({"rawtypes"})
	@Transactional
	public String modify() throws Exception{
		Book preBook=(Book)dao.query(Book.class, entityId);
		
		//基本信息
		preBook.setSubmitStatus(book.getSubmitStatus());
		preBook.setChineseName(book.getChineseName().trim());
		preBook.setEnglishName((null != book.getEnglishName()) ? book.getEnglishName().trim() : null);
		preBook.setOtherAuthorName((null != book.getOtherAuthorName()) ? productService.MutipleToFormat(personExtService.regularNames(book.getOtherAuthorName())) : null);
		preBook.setType(book.getType());
		preBook.setForm(book.getForm());
		preBook.setIsTranslation(book.getIsTranslation());
		preBook.setIsForeignCooperation(book.getIsForeignCooperation());
		preBook.setWordNumber(book.getWordNumber());
		preBook.setDisciplineType(book.getDisciplineType().trim());
		preBook.setDiscipline(book.getDiscipline().trim());
		preBook.setKeywords(this.productService.MutipleToFormat(book.getKeywords().trim()));
		preBook.setIntroduction((null != book.getIntroduction()) ? ("A" + book.getIntroduction()).trim().substring(1) : null);
		preBook.setPublishUnit(book.getPublishUnit().trim());
		preBook.setPublishDate(book.getPublishDate());
		preBook.setOriginalLanguage(book.getOriginalLanguage());
		
		//处理附件
		String groupId = "file_" + preBook.getId();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
				File curFile = fileRecord.getOriginal();
				String newFileName = productService.getFileName(curFile, savePath, PRODUCT_TYPE);
				preBook.setFile(newFileName);
				fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(preBook.getFile())));//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
	
		//非外部修改成果
	 	if(null == viewType) {
			applyType = (Integer)session.get("applyType");
		 	if(applyType == 1) {//1.以团队名义
		 		//填充成果作者、单位信息
				preBook = (Book)productService.fillAuthorAndAgencyInfos(loginer.getCurrentType(), preBook, organizationAuthorId, organizationAuthorTypeId, organizationAuthorType);
				preBook.setOrgName(book.getOrgName());
				preBook.setOrgDiscipline(book.getOrgDiscipline());
				preBook.setOrgEmail(book.getOrgEmail());
				preBook.setOrgMember(book.getOrgMember());
				preBook.setOrgMobilePhone(book.getOrgMobilePhone());
				preBook.setOrgOfficeAddress(book.getOrgOfficeAddress());
				preBook.setOrgOfficePhone(book.getOrgOfficePhone());
				preBook.setOrgPerson(book.getOrgPerson());
				preBook.setOrgOfficePostcode(book.getOrgOfficePostcode());
				
		 	} else {
		 		//填充成果作者、单位信息
				preBook = (Book)productService.fillAuthorAndAgencyInfos(loginer.getCurrentType(), preBook, authorId, authorTypeId, authorType);
		 	}
			
			//如果是项目成果, 保存关联信息
			if(isProRel == 1) {
				projectProduct.setProduct(preBook);
				dao.add(projectProduct);
			}
	 	} else {//外部项目修改成果
	 		ProjectProduct oldProjectProduct = productService.getProjectProductByProductId(preBook.getId());
	 		oldProjectProduct.setIsMarkMoeSupport(projectProduct.getIsMarkMoeSupport());
	 		dao.modify(oldProjectProduct);
	 		//设置结项最终成果
	 		if(viewType == 3) {
	 			productService.setEndInspectionFinalProduct(entityId, inspectionId, isFinalProduct);
	 		}
	 	}
		dao.modify(preBook);
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
//		validateMust(); 
		validateEdit();//校验
		Book preBook=(Book)dao.query(Book.class, entityId);
		String groupId = "file_" + preBook.getId();
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
		if(null == book.getChineseName() || "".equals(book.getChineseName())){
			addFieldError("book.chineseName", ProductInfo.ERROR_CHINESE_NAME_NULL);
		} else if(book.getChineseName().trim().length() > 50){
			addFieldError("book.chineseName", ProductInfo.ERROR_CHINESE_NAMR_OUT);
		}
		if(null == authorId || "".equals(authorId)){
			addFieldError("book.authorName", ProductInfo.ERROR_FIRST_AUTHOR_NULL);
		}
		if(null != book.getOtherAuthorName() && book.getOtherAuthorName().trim().length() > 200){
			addFieldError("book.otherAuthorName", ProductInfo.ERROR_OTHER_AUTHOR_NAME_OUT);
		}
		if(null == book.getDisciplineType() || "".equals(book.getDisciplineType())){
			addFieldError("book.disciplineType", ProductInfo.ERROR_DTYPE_NULL);
		} else if(book.getDisciplineType().trim().length() > 100){
			addFieldError("book.disciplineType", ProductInfo.ERROR_DTYPE_OUT);
		}
		if(null == book.getDiscipline() || "".equals(book.getDiscipline())){
			addFieldError("book.discipline", ProductInfo.ERROR_DISCIPLINE_NULL);
		} else if(book.getDiscipline().trim().length() > 100){
			addFieldError("book.discipline", ProductInfo.ERROR_DISCIPLINE_OUT);
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
		if(null != book.getEnglishName() && book.getEnglishName().trim().length() > 200){
			addFieldError("book.englishName", ProductInfo.ERROR_ENGLIST_NAME_OUT);
		}
		if(null == book.getType() || "-1".equals(book.getType().getId())){
			addFieldError("book.ptype.id", ProductInfo.ERROR_PTYPE_NULL);
		}
		if(null == book.getForm() || "-1".equals(book.getForm().getId())){
			addFieldError("book.form.id", ProductInfo.ERROR_FORM_NULL);
		}
		if(book.getWordNumber() < 0 || book.getWordNumber() > 9999){
			addFieldError("book.wordNumber", ProductInfo.ERROR_WORD_NUMBER_OUT);
		}
		if(book.getKeywords().trim().length() > 100){
			addFieldError("book.keywords", ProductInfo.ERROR_KEYWORD_OUT);
		}
		if(null != book.getIntroduction() && book.getIntroduction().trim().length() > 20000){
			addFieldError("book.introduction", ProductInfo.ERROR_INTRODUCTION_OUT);
		}
		if( book.getPublishUnit().trim().length() > 50){
			addFieldError("book.publishUnit", ProductInfo.ERROR_PUBLICATION_UNIT_OUT);
		}
	}
	
	/**
	 * 加载系统选项
	 */
	@SuppressWarnings({ "unchecked" })
	public void initOptions() {
		fetchAuthorInfo();//获取作者信息
		session.put("projectType", ProjectGranted.typeMap);//获取项目类型
	}
	
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
}