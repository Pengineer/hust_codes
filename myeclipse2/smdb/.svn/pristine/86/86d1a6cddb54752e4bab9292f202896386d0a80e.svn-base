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
import csdc.bean.Consultation;
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
 * 报告成果管理
 */
public class ConsultationAction extends ProductBaseAction {

	private static final long serialVersionUID = -7812941384300343442L;
	private static Integer PRODUCT_TYPE = 3;//成果形式（1：论文；2:著作；3:研究咨询报告； 4：电子出版物；5专利； 6其他成果）
	private static String HQL = "select p.id, p.chineseName, p.authorName, p.agencyName, p.useUnit, " +
		"p.file, aut.id, uni.id, p.divisionName, dep.id, ins.id, p.submitStatus, p.auditResult, " +
		"p.auditStatus, p.auditDate from Consultation p left join p.university uni left join p.department dep " +
		"left join p.institute ins left join p.author aut where ";
	private static final String PAGE_NAME = "consultationPage";
	private Consultation consultation;//研究报告对象
	private static final String[] COLUMN = new String[] {
		"p.chineseName",
		"p.wordNumber",
		"p.authorName",
		"p.divisionName",
		"p.agencyName",
		"p.useUnit",
		"p.publicationDate",
		"p.submitStatus",
		"p.auditResult",
		"p.auditDate desc"
	};// 排序列
	
	public String[] column() {
		return ConsultationAction.COLUMN;
	}
	public String pageName() {
		return ConsultationAction.PAGE_NAME;
	}
	public Integer productType() {
		return ConsultationAction.PRODUCT_TYPE;
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
		} else if (searchType == 3) {
			hql.append("LOWER(p.authorName) like :keyword");
		} else if(searchType == 4){
			hql.append("LOWER(p.agencyName) like :keyword");
		} else if(searchType == 5) {
			hql.append("LOWER(p.useUnit) like :keyword");
		} else if(searchType == 6) {
			hql.append("LOWER(p.divisionName) like :keyword");
		} else {
			hql.append("(LOWER(p.chineseName) like :keyword or LOWER(p.authorName) like :keyword or LOWER(p.agencyName) like :keyword or LOWER(p.useUnit) like :keyword")
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
		if(null != consultation.getChineseName() && !consultation.getChineseName().isEmpty()){
			parMap.put("chineseName", "%" + consultation.getChineseName() + "%");
			hql.append(" and LOWER(p.chineseName) like :chineseName ");
		}
		if(null != consultation.getDisciplineType() && !consultation.getDisciplineType().isEmpty()){
			String[] dtypes = consultation.getDisciplineType().split("; ");
			int len = dtypes.length;
			if(len>0){
				hql.append(" and (");
				for(int i = 0; i < len; i++){
					parMap.put("dtype" + i,"%" + dtypes[i].toLowerCase() + "%");
					hql.append("LOWER(p.disciplineType) like :dtype" + i);
					if (i != len - 1) {hql.append(" or ");}
				}
				hql.append(")");
			}
		} 
		if(null != consultation.getAuthorName() && !consultation.getAuthorName().isEmpty()){
			parMap.put("authorName", "%" + consultation.getAuthorName() + "%");
			hql.append(" and LOWER(p.authorName) like :authorName");
		}
		if(null != consultation.getAgencyName() && !consultation.getAgencyName().isEmpty()){
			parMap.put("univ", "%" + consultation.getAgencyName() + "%");
			hql.append(" and LOWER(p.agencyName) like :univ");
		}
		if(null != consultation.getDivisionName() && !consultation.getDivisionName().isEmpty()){
			parMap.put("unit", "%" + consultation.getDivisionName() + "%");
			hql.append(" and LOWER(p.divisionName) like :unit");
		}
		if(null != consultation.getUseUnit() && !consultation.getUseUnit().isEmpty()){
			parMap.put("useUnit", "%" + consultation.getUseUnit() + "%");
			hql.append(" and nvl(LOWER(p.useUnit),' ') like :useUnit");
		}
		if(null != consultation.getProvinceName() && !consultation.getProvinceName().isEmpty()){
			parMap.put("provinceName", "%" + consultation.getProvinceName() + "%");
			hql.append(" and  LOWER(p.provinceName) like :provinceName");
		}		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (null != pubDate1) {
			parMap.put("pubDate1", df.format(pubDate1));
			hql.append(" and p.publicationDate is not null and  to_char(p.publicationDate,'yyyy-MM-dd')>=:pubDate1");
		}
		if (null != pubDate2) {
			parMap.put("pubDate2", df.format(pubDate2));
			if(null == date1){
				hql.append(" and p.publicationDate is not null");
			}
			hql.append(" and to_char(p.publicationDate,'yyyy-MM-dd') <= :pubDate2");
		}
		//研究人员
		if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员
			if(consultation.getSubmitStatus() != -1){
				parMap.put("submitStatus", consultation.getSubmitStatus());
				hql.append(" and p.submitStatus = :submitStatus");
			}
		} else {//管理人员
			if(consultation.getAuditResult() != -1){
				parMap.put("auditResult", consultation.getAuditResult());
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
		//判断是否在管辖范围之内
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
		if(null != consultation.getChineseName() && !consultation.getChineseName().isEmpty()){
			searchQuery.put("chineseName", consultation.getChineseName());
		}
		if(null != consultation.getDisciplineType() && !consultation.getDisciplineType().isEmpty()){
			searchQuery.put("disciplineType", consultation.getDisciplineType());
		} 
		if(null != consultation.getAuthorName() && !consultation.getAuthorName().isEmpty()){
			searchQuery.put("authorName", consultation.getAuthorName());
		}
		if(null != consultation.getAgencyName() && !consultation.getAgencyName().isEmpty()){
			searchQuery.put("agencyName", consultation.getAgencyName());
		}
		if(null != consultation.getDivisionName() && !consultation.getDivisionName().isEmpty()){
			searchQuery.put("divisionName", consultation.getDivisionName());
		}
		if(null != consultation.getUseUnit() && !consultation.getUseUnit().isEmpty()){
			searchQuery.put("useUnit", consultation.getUseUnit());
		}
		if(null != consultation.getProvinceName() && !consultation.getProvinceName().isEmpty()){
			searchQuery.put("provinceName", consultation.getProvinceName());
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
			if(consultation.getSubmitStatus() != -1){
				searchQuery.put("submitStatus", consultation.getSubmitStatus());
			}
		} else {//管理人员
			if(consultation.getAuditResult()!= -1){
				searchQuery.put("auditResult", consultation.getAuditResult());
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
		consultation = (Consultation)dao.query(Consultation.class, entityId);
		jsonMap.put("productType", consultation.getProductType());
		jsonMap.put("accountType", loginer.getCurrentType());
		jsonMap.put("consultation", consultation);
		jsonMap.put("form", (null != consultation.getForm()) ? consultation.getForm().getName() : null);//形态名称
		jsonMap.put("universityid", (null != consultation.getUniversity()) ? consultation.getUniversity().getId() : null);//高校id
		jsonMap.put("authorid", (null != consultation.getAuthor()) ? consultation.getAuthor().getId() : null);//作者id
		//团队信息
//		if(null != consultation.getOrganization() && !consultation.getOrganization().getId().isEmpty()) {
//			organization = (Organization)dao.query(Organization.class, consultation.getOrganization().getId());
//		}
//		jsonMap.put("organization", organization);
		//项目相关信息
		jsonMap.put("relProjectInfos", productService.getRelProjectInfos(entityId));
		//奖励相关信息
		jsonMap.put("relAwardInfos", productService.getRelAwardInfos(entityId));
		//成果能否被修改
		boolean canBeModify = productService.canModifyProduct(entityId);
		if (consultation.getFile() != null) {
			//获取文件大小
			List<String> attachmentSizeList = new ArrayList<String>();
			String[] attachPath = consultation.getFile().split("; ");
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
		session.put("consultationViewJson", jsonMap);
		return SUCCESS;
	}
	
	/**
	 * 保存成果
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String add() throws Exception {
		//成果文件信息
		String groupId = "file_add_con";
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
				File curFile = fileRecord.getOriginal();
				String newFileName = productService.getFileName(curFile, savePath, PRODUCT_TYPE);
				consultation.setFile(newFileName);
				fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(consultation.getFile())));//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		
		//成果作者、单位信息
		if(applyType == 1) {//1.以团队名义申报成果
			consultation = (Consultation)productService.fillAuthorAndAgencyInfos(loginer.getCurrentType(), consultation, organizationAuthorId, organizationAuthorTypeId, organizationAuthorType);
			Person person = (Person)dao.query(Person.class, organizationAuthorId);
			consultation.setOrgPerson(person);
		} else {//2.以个人名义申报成果
			consultation = (Consultation)productService.fillAuthorAndAgencyInfos(loginer.getCurrentType(), consultation, authorId, authorTypeId, authorType);
		}
		
		//成果审核信息[管理人员添加, 默认审核通过]
		consultation = (loginer.getCurrentType().compareTo(AccountType.EXPERT)<0) ? (Consultation)productService.fillAuditInfo(loginer.getAccount(), consultation, 2) : consultation;
		
		//将提交时间设为系统时间
		consultation.setSubmitDate(new Date());
		entityId = dao.add(consultation);
		
		//项目相关信息
		if(isProRel == 1) {
			ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, projectGrantedId) ;
			projectProduct.setProjectGranted(projectGranted);
			projectProduct.setProduct(consultation);
			dao.add(projectProduct);
		}
		
		//成果绑定团队
//		if(null != session.get("awardOrganizationId")) {
//			String organizationId = (String)session.get("awardOrganizationId");
//			organization = (Organization)dao.query(Organization.class, organizationId);
//			consultation.setOrganization(organization);
//			dao.modify(consultation);
//			session.remove("awardOrganizationId");
//		}
		
		//外部弹出层添加项目成果
		if(null != viewType && viewType != 0){
			consultation.setSubmitStatus(3);
			//项目添加成果
			if(viewType == 1 || viewType == 2 || viewType == 3 || viewType == 4) {
				productService.addNewProductToProjectInspection(projectProduct, entityId, projectId, viewType, inspectionId, isFinalProduct);
				//设置结项最终成果
		 		if(viewType == 3) {
		 			productService.setEndInspectionFinalProduct(entityId, inspectionId, isFinalProduct);
		 		}
			} else if(viewType == 5) {//奖励添加成果
				jsonMap.put("productId", entityId);
				jsonMap.put("productType", consultation.getProductType());
			}
			return "finish";
		}
		//将文件组(groupId)中文件的更新操作（删除、移动）在磁盘中实施
		uploadService.flush(groupId);
		return SUCCESS;
	}
	
	/**
	 * 校验添加成果
	 * @param consultationService
	 */
	public void validateAdd(){
		validateMust(); validateEdit();//校验
		String groupId = "file_add_con";
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
		inputView = (null != exflag && exflag == 1) ? "/product/extIf/popAdd.jsp" : "/product/consultation/add.jsp";
	}
	
	/**
	 * 转到成果更新
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String toModify(){
		//判断是否在管辖范围之内
		if(!productService.checkIfUnderControl(loginer, entityId.trim(), 15, true)){
			addActionError("您所选择的成果不在您的管辖范围内！");
			return ERROR;}
		consultation = (Consultation)dao.query(Consultation.class, entityId);
		if(null != consultation.getChineseName()){
			consultation.setChineseName(consultation.getChineseName().trim());
		}
		if(consultation.getWordNumber() == 0.0){
			consultation.setWordNumber(0);
		}
		//是否为项目成果
		if(productService.isRelProduct(entityId)) {
			Map projectInfos = productService.getRelProjectInfos(entityId).get(0);
			session.put("projectInfos", projectInfos);
			isProRel = 1;
		}
		//获取团队信息
		if(null != consultation.getOrgName()) {
			organizationAuthorId = (null != consultation.getAuthor()) ? consultation.getAuthor().getId() : null;
			organizationAuthorTypeId = productService.getAuthorIdOfAuthorType(entityId);
			organizationAuthorType = consultation.getAuthorType();
			applyType = 1;
		} else {//获取个人信息
			authorId = (null != consultation.getAuthor()) ? consultation.getAuthor().getId() : null;
			authorTypeId = productService.getAuthorIdOfAuthorType(entityId);
			authorType = consultation.getAuthorType();
			if(null != authorId){
				author = (Person)dao.query(Person.class, consultation.getAuthor().getId());
				fetchAuthorInfo();//获取作者信息
			}
		}
		if(null != viewType) {
			//获取项目立项成果对象
			projectProduct = productService.getProjectProductByProductId(consultation.getId());
			if(viewType == 3) {//结项成果
				ProjectEndinspectionProduct ProjectEndinspectionProduct = productService.getProjectEndinspectionProduct(entityId, inspectionId);
				isFinalProduct = (null != ProjectEndinspectionProduct) ? ProjectEndinspectionProduct.getIsFinalProduct() : 0;
			}
		}
		//将已有附件加入文件组，在编辑页面显示
		String groupId = "file_" + consultation.getId();
		uploadService.resetGroup(groupId);
		if (consultation.getFile() != null) {
			String bookFilepath = ApplicationContainer.sc.getRealPath(consultation.getFile());
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
	public String modify()throws Exception{
		Consultation preConsultation=(Consultation)dao.query(Consultation.class, entityId);
		
		//基本信息
		preConsultation.setSubmitStatus(consultation.getSubmitStatus());
		preConsultation.setChineseName(consultation.getChineseName().trim());
		preConsultation.setEnglishName((null != consultation.getEnglishName()) ? consultation.getEnglishName().trim() : null);
		preConsultation.setOtherAuthorName((null != consultation.getOtherAuthorName()) ? productService.MutipleToFormat(personExtService.regularNames(consultation.getOtherAuthorName())) : null);	
		preConsultation.setForm(consultation.getForm());
		preConsultation.setIsTranslation(consultation.getIsTranslation());
		preConsultation.setIsForeignCooperation(consultation.getIsForeignCooperation());
		preConsultation.setWordNumber(consultation.getWordNumber());
		preConsultation.setDisciplineType(consultation.getDisciplineType().trim());
		preConsultation.setDiscipline(consultation.getDiscipline().trim());
		preConsultation.setKeywords(this.productService.MutipleToFormat(consultation.getKeywords().trim()));
		preConsultation.setIntroduction((null != consultation.getIntroduction()) ? ("A" + consultation.getIntroduction()).trim().substring(1) : null);	 
		preConsultation.setUseUnit(consultation.getUseUnit().trim());
		preConsultation.setPublicationDate(consultation.getPublicationDate());
		
		//处理附件
		String groupId = "file_" + preConsultation.getId();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
				File curFile = fileRecord.getOriginal();
				String newFileName = productService.getFileName(curFile, savePath, PRODUCT_TYPE);
				preConsultation.setFile(newFileName);
				fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(preConsultation.getFile())));//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}

	 	//非外部修改成果
	 	if(null == viewType) {
		 	applyType = (Integer)session.get("applyType");
		 	if(applyType == 1) {//1.以团队名义
		 		//填充成果作者、单位信息
		 		preConsultation = (Consultation)productService.fillAuthorAndAgencyInfos(loginer.getCurrentType(), preConsultation, organizationAuthorId, organizationAuthorTypeId, organizationAuthorType);
		 		preConsultation.setOrgName(consultation.getOrgName());
		 		preConsultation.setOrgDiscipline(consultation.getOrgDiscipline());
		 		preConsultation.setOrgEmail(consultation.getOrgEmail());
		 		preConsultation.setOrgMember(consultation.getOrgMember());
		 		preConsultation.setOrgMobilePhone(consultation.getOrgMobilePhone());
		 		preConsultation.setOrgOfficeAddress(consultation.getOrgOfficeAddress());
		 		preConsultation.setOrgOfficePhone(consultation.getOrgOfficePhone());
		 		preConsultation.setOrgPerson(consultation.getOrgPerson());
		 		preConsultation.setOrgOfficePostcode(consultation.getOrgOfficePostcode());
		 	} else {
		 		//填充成果作者、单位信息
		 		preConsultation = (Consultation)productService.fillAuthorAndAgencyInfos(loginer.getCurrentType(), preConsultation, authorId, authorTypeId, authorType);
		 	}
		 	
			//如果是项目成果, 保存关联信息
			if(isProRel == 1) {
				projectProduct.setProduct(preConsultation);
				dao.add(projectProduct);
			}
	 	} else {//外部项目修改成果
	 		ProjectProduct oldProjectProduct = productService.getProjectProductByProductId(preConsultation.getId());
	 		oldProjectProduct.setIsMarkMoeSupport(projectProduct.getIsMarkMoeSupport());
	 		dao.modify(oldProjectProduct);
	 		//设置结项最终成果
	 		if(viewType == 3) {
	 			productService.setEndInspectionFinalProduct(entityId, inspectionId, isFinalProduct);
	 		}
	 	}
		dao.modify(preConsultation);
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
		Consultation preConsultation=(Consultation)dao.query(Consultation.class, entityId);
		String groupId = "file_" + preConsultation.getId();
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
		if(null == consultation.getChineseName() || "".equals(consultation.getChineseName())){
			addFieldError("consultation.chineseName", ProductInfo.ERROR_CHINESE_NAME_NULL);
		} else if(consultation.getChineseName().trim().length() > 50){
			addFieldError("consultation.chineseName", ProductInfo.ERROR_CHINESE_NAMR_OUT);
		}
		if(null == authorId || "".equals(authorId)){
			addFieldError("consultation.authorName", ProductInfo.ERROR_FIRST_AUTHOR_NULL);
		}
		if(null != consultation.getOtherAuthorName() && consultation.getOtherAuthorName().trim().length() > 200){
			addFieldError("consultation.otherAuthorName", ProductInfo.ERROR_OTHER_AUTHOR_NAME_OUT);
		}
		if(null == consultation.getDisciplineType() || "".equals(consultation.getDisciplineType())){
			addFieldError("consultation.disciplineType", ProductInfo.ERROR_DTYPE_NULL);
		} else if(consultation.getDisciplineType().trim().length() > 100){
			addFieldError("consultation.disciplineType", ProductInfo.ERROR_DTYPE_OUT);
		}
		if(null == consultation.getDiscipline() || "".equals(consultation.getDiscipline())){
			addFieldError("consultation.discipline", ProductInfo.ERROR_DISCIPLINE_NULL);
		} else if(consultation.getDiscipline().trim().length() > 100){
			addFieldError("consultation.discipline", ProductInfo.ERROR_DISCIPLINE_OUT);
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
		if(null != consultation.getEnglishName() && consultation.getEnglishName().trim().length() > 200){
			addFieldError("consultation.chineseName", ProductInfo.ERROR_ENGLIST_NAME_OUT);
		}
		if(null == consultation.getForm() || "-1".equals(consultation.getForm().getId())){
			addFieldError("consultation.form.id", ProductInfo.ERROR_FORM_NULL);
		}
		if(consultation.getWordNumber() < 0 || consultation.getWordNumber() > 9999){
			addFieldError("consultation.wordNumber", ProductInfo.ERROR_WORD_NUMBER_OUT);
		}
		if(consultation.getKeywords().trim().length() > 100){
			addFieldError("consultation.keywords", ProductInfo.ERROR_KEYWORD_OUT);
		}
		if(null != consultation.getIntroduction() && consultation.getIntroduction().trim().length() > 20000){
			addFieldError("consultation.introduction", ProductInfo.ERROR_INTRODUCTION_OUT);
		}
		if( consultation.getUseUnit().trim().length() > 50){
			addFieldError("consultation.publication", ProductInfo.ERROR_PUBLICATION_UNIT_OUT);
		}
	}
	
	/**
	 * 加载系统选项
	 */
	@SuppressWarnings("unchecked")
	public void initOptions() {
		fetchAuthorInfo();//获取作者信息
		session.put("productType", baseService.getSOByParentName("成果形态"));
		session.put("projectType", ProjectGranted.typeMap);//获取项目类型
	}
	
	public Consultation getConsultation() {
		return consultation;
	}
	public void setConsultation(Consultation consultation) {
		this.consultation = consultation;
	}
}