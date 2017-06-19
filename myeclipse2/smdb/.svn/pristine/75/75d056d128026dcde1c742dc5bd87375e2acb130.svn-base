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
import org.csdc.domain.fm.ThirdCheckInForm;
import org.csdc.domain.fm.ThirdUploadForm;
import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Institute;
import csdc.bean.OtherProduct;
import csdc.bean.Paper;
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
 * 论文成果管理
 */
public class PaperAction extends ProductBaseAction {

	private static final long serialVersionUID = 1436875363136212588L;
	private static Integer PRODUCT_TYPE = 1;//成果形式（1：论文；2:著作；3:研究咨询报告； 4：电子出版物；5专利； 6其他成果）
	private static String HQL = "select p.id, p.chineseName, p.authorName, p.agencyName, p.publication, " +
		"p.file, aut.id, uni.id, p.divisionName, dep.id, ins.id, so1.name, p.submitStatus, p.auditResult, " +
		"p.auditStatus, p.auditDate from Paper p left join p.university uni left join p.department dep " +
		"left join p.institute ins left join p.type so1 left join p.author aut where ";
	private static final String PAGE_NAME = "paperPage";
	private Paper paper;//论文对象
	private static final String[] COLUMN = new String[] {
		"p.chineseName",
		"so1.name",
		"p.authorName",
		"p.divisionName",
		"p.agencyName",
		"p.publication",
		"p.submitStatus",
		"p.auditResult",
		"p.auditDate desc"
	};// 排序列
	public String[] column() {
		return PaperAction.COLUMN;
	}
	public String pageName() {
		return PaperAction.PAGE_NAME;
	}
	public Integer productType() {
		return PaperAction.PRODUCT_TYPE;
	}
	
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
		if(searchType == 1) {
			hql.append("LOWER(p.chineseName) like :keyword");
		} else if(searchType == 2){
			hql.append("LOWER(so1.name) like :keyword");
		} else if (searchType == 3) {
			hql.append("LOWER(p.authorName) like :keyword");
		} else if(searchType == 4){
			hql.append("LOWER(p.agencyName) like :keyword");
		} else if (searchType == 5) {
			hql.append("LOWER(p.publication) like :keyword");
		} else if (searchType == 6) {
			hql.append("LOWER(p.divisionName) like :keyword");
		} else {
			hql.append("(LOWER(p.chineseName) like :keyword or LOWER(so1.name) like :keyword or LOWER(p.authorName) like :keyword ")
				.append("or LOWER(p.agencyName) like :keyword or LOWER(p.publication) like :keyword or LOWER(p.divisionName) like :keyword) ");
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
		if(null != paper.getChineseName() && !paper.getChineseName().isEmpty()){
			parMap.put("chineseName", "%" + paper.getChineseName() + "%");
			hql.append(" and LOWER(p.chineseName) like :chineseName ");
		}
		if(!paper.getType().getId().equals("-1")){
			parMap.put("ptype", paper.getType().getId());
			hql.append(" and  so1.id like :ptype ");
		}
		if(null != paper.getDisciplineType() && !paper.getDisciplineType().isEmpty()){
			String[] dtypes = paper.getDisciplineType().split("; ");
			int len = dtypes.length;
			if(len > 0){
				hql.append(" and (");
				for(int i = 0; i < len; i++){
					parMap.put("dtype" + i, "%" + dtypes[i].toLowerCase() + "%");
					hql.append("LOWER(p.disciplineType) like :dtype" + i);
					if (i != len - 1) {hql.append(" or ");}
				}
				hql.append(")");
			}
		} 
		if(null != paper.getAuthorName() && !paper.getAuthorName().isEmpty()){
			parMap.put("authorName", "%" + paper.getAuthorName() + "%");
			hql.append(" and LOWER(p.authorName) like :authorName");
		}
		if(null != paper.getAgencyName() && !paper.getAgencyName().isEmpty()){
			parMap.put("univ", "%" + paper.getAgencyName() + "%");
			hql.append(" and  LOWER(p.agencyName) like :univ");
		}
		if(null != paper.getDivisionName() && !paper.getDivisionName().isEmpty()){
			parMap.put("unit", "%" + paper.getDivisionName() + "%");
			hql.append(" and  LOWER(p.divisionName) like :unit");
		}
		if(null != paper.getPublication() && !paper.getPublication().isEmpty()){
			parMap.put("publication", "%" + paper.getPublication() + "%");
			hql.append(" and  nvl(LOWER(p.publication),' ') like :publication");
		}
		if(null != paper.getProvinceName() && !paper.getProvinceName().isEmpty()){
			parMap.put("provinceName", "%" + paper.getProvinceName() + "%");
			hql.append(" and  LOWER(p.provinceName) like :provinceName");
		}		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (null != pubDate1) {
			parMap.put("pubDate1", df.format(pubDate1));
			hql.append(" and p.publicationDate is not null and to_char(p.publicationDate,'yyyy-MM-dd') >= :pubDate1");
		}
		if (null != pubDate2) {
			parMap.put("pubDate2", df.format(pubDate2));
			if(null == pubDate1 ){
				hql.append(" and p.publicationDate is not null ");
			}
			hql.append(" and to_char(p.publicationDate, 'yyyy-MM-dd') <= :pubDate2");
		}
		//研究人员
		if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员
			if(paper.getSubmitStatus() != -1){
				parMap.put("submitStatus", paper.getSubmitStatus());
				hql.append(" and p.submitStatus = :submitStatus");
			}
		} else {//管理人员
			if(paper.getAuditResult() != -1){
				parMap.put("auditResult", paper.getAuditResult());
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
		if(null != paper.getChineseName() && !paper.getChineseName().isEmpty()){
			searchQuery.put("chineseName", paper.getChineseName());
		}
		if(!paper.getType().getId().equals("-1")){
			searchQuery.put("ptype", paper.getType().getId());
		}
		if(null != paper.getDisciplineType() && !paper.getDisciplineType().isEmpty()){
			searchQuery.put("disciplineType", paper.getDisciplineType());
		} 
		if(null != paper.getAuthorName() && !paper.getAuthorName().isEmpty()){
			searchQuery.put("authorName", paper.getAuthorName());
		}
		if(null != paper.getAgencyName() && !paper.getAgencyName().isEmpty()){
			searchQuery.put("agencyName", paper.getAgencyName());
		}
		if(null != paper.getDivisionName() && !paper.getDivisionName().isEmpty()){
			searchQuery.put("divisionName", paper.getDivisionName());
		}
		if(null != paper.getPublication() && !paper.getPublication().isEmpty()){
			searchQuery.put("publication", paper.getPublication());
		}		
		if(null != paper.getProvinceName() && !paper.getProvinceName().isEmpty()){
			searchQuery.put("provinceName", paper.getProvinceName());
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
			if(paper.getSubmitStatus() != -1){
				searchQuery.put("submitStatus", paper.getSubmitStatus());
			}
		} else {//管理人员
			if(paper.getAuditResult()!= -1){
				searchQuery.put("auditResult", paper.getAuditResult());
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
		paper = (Paper)dao.query(Paper.class, entityId);
		jsonMap.put("productType", paper.getProductType());
		jsonMap.put("accountType", loginer.getCurrentType());
		jsonMap.put("paper", paper);
		jsonMap.put("paperType", (null != paper.getType()) ? paper.getType().getName() : null);//论文类型
		jsonMap.put("form", (null != paper.getForm()) ? paper.getForm().getName() : null);//论文形态
		jsonMap.put("publicationScope", (null != paper.getPublicationScope()) ? paper.getPublicationScope().getName() : null);//发表范围
		jsonMap.put("universityid", (null != paper.getUniversity()) ? paper.getUniversity().getId() : null);//高校id
		jsonMap.put("authorid", (null != paper.getAuthor()) ? paper.getAuthor().getId() : null);//作者id
		//团队信息
//		if(null != paper.getOrganization() && !paper.getOrganization().getId().isEmpty()) {
//			organization = (Organization)dao.query(Organization.class, paper.getOrganization().getId());
//		}
//		jsonMap.put("organization", organization);
		//项目相关信息
		try{
			
			jsonMap.put("relProjectInfos", productService.getRelProjectInfos(entityId));
		}catch(Exception e) {
			System.out.println(e);
		}
		//奖励相关信息
		jsonMap.put("relAwardInfos", productService.getRelAwardInfos(entityId));
		//成果能否被修改
		boolean canBeModify = productService.canModifyProduct(entityId);
		if (paper.getFile() != null) {
			//获取文件大小
			List<String> attachmentSizeList = new ArrayList<String>();
			String[] attachPath = paper.getFile().split("; ");
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
		session.put("paperViewJson", jsonMap);
		return SUCCESS;
	}
	
	/**
	 * 保存成果
	 */
	@SuppressWarnings({ "unchecked", "rawtypes"})
	@Transactional
	public String add() throws Exception {
		//成果文件信息
		String groupId = "file_add";
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
				File curFile = fileRecord.getOriginal();
				String newFileName = productService.getFileName(curFile, savePath, PRODUCT_TYPE);
				paper.setFile(newFileName);
				fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(paper.getFile())));//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}

		//成果作者、单位信息
		if(applyType == 1) {//1.以团队名义申报成果
			paper = (Paper)productService.fillAuthorAndAgencyInfos(loginer.getCurrentType(), paper, organizationAuthorId, organizationAuthorTypeId, organizationAuthorType);
			Person person = (Person)dao.query(Person.class, organizationAuthorId);
			paper.setOrgPerson(person);
		} else {//2.以个人名义申报成果
			paper = (Paper)productService.fillAuthorAndAgencyInfos(loginer.getCurrentType(), paper, authorId, authorTypeId, 1);
		}
		
		//成果审核信息[管理人员添加, 默认审核通过]
		paper = (loginer.getCurrentType().compareTo(AccountType.EXPERT)<0) ? (Paper)productService.fillAuditInfo(loginer.getAccount(), paper, 2) : paper;
		
		//将提交时间设为系统时间
		paper.setSubmitDate(new Date());
		entityId = dao.add(paper);
		
		//项目相关信息
		if(isProRel == 1) {
			ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, projectGrantedId) ;
			projectProduct.setProjectGranted(projectGranted);
			projectProduct.setProduct(paper);
			dao.add(projectProduct);
		}
		
		//成果绑定团队
//		if(null != session.get("awardOrganizationId")) {
//			String organizationId = (String)session.get("awardOrganizationId");
//			organization = (Organization)dao.query(Organization.class, organizationId);
//			paper.setOrganization(organization);
//			dao.modify(paper);
//			session.remove("awardOrganizationId");
//		}
		
		//外部弹出层添加项目成果
		if(null != viewType && viewType != 0){
			paper.setSubmitStatus(3);
			//项目添加成果
			if(viewType == 1 || viewType == 2 || viewType == 3 || viewType == 4) {
				productService.addNewProductToProjectInspection(projectProduct, entityId, projectId, viewType, inspectionId, isFinalProduct);
				//设置结项最终成果
		 		if(viewType == 3) {
		 			productService.setEndInspectionFinalProduct(entityId, inspectionId, isFinalProduct);
		 		}
			} else if(viewType == 5) {//奖励添加成果
				jsonMap.put("productId", entityId);
				jsonMap.put("productType", paper.getProductType());
			}
			return "finish";
		}
		//将文件组(groupId)中文件的更新操作（删除、移动）在磁盘中实施
		uploadService.flush(groupId);
		String dfsId = productService.uploadToDmss(paper);
		paper.setDfs(dfsId);
		return SUCCESS;
	}
	
	/**
	 * 校验添加成果
	 */
	public void validateAdd(){
//		validateMust(); validateEdit();//校验
		inputView = (null != exflag && exflag == 1) ? "/product/extIf/popAdd.jsp" : "/product/paper/add.jsp";
	}
	
	/**
	 * 转到更新成果
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String toModify(){
		if(!productService.checkIfUnderControl(loginer, entityId.trim(), 15, true)){
			addActionError("您所选择的成果不在您的管辖范围内！");
			return ERROR;}
		paper = (Paper)dao.query(Paper.class, entityId);
		if(null != paper.getChineseName()){
			paper.setChineseName(paper.getChineseName().trim());
		}
		//判断是否为项目成果
		if(productService.isRelProduct(entityId)) {
			Map projectInfos = productService.getRelProjectInfos(entityId).get(0);
			session.put("projectInfos", projectInfos);
			isProRel = 1;
		}
		//获取团队信息
		if(null != paper.getOrgName()) {
			organizationAuthorId = (null != paper.getAuthor()) ? paper.getAuthor().getId() : null;
			organizationAuthorTypeId = productService.getAuthorIdOfAuthorType(entityId);
			organizationAuthorType = paper.getAuthorType();
			applyType = 1;
		} else {//获取个人信息
			authorId = (null != paper.getAuthor()) ? paper.getAuthor().getId() : null;
			authorTypeId = productService.getAuthorIdOfAuthorType(entityId);
			authorType = paper.getAuthorType();
			if(null != authorId){
				author = (Person)dao.query(Person.class, paper.getAuthor().getId());
				fetchAuthorInfo();//获取作者信息
			}
		}
		if(null != viewType) {//修改项目成果
			//获取项目立项成果对象
			projectProduct = productService.getProjectProductByProductId(paper.getId());
			if(viewType == 3) {//结项成果
				ProjectEndinspectionProduct ProjectEndinspectionProduct = productService.getProjectEndinspectionProduct(entityId, inspectionId);
				isFinalProduct = (null != ProjectEndinspectionProduct) ? ProjectEndinspectionProduct.getIsFinalProduct() : 0;
			}
		}
		//将已有附件加入文件组，在编辑页面显示
		String groupId = "file_" + paper.getId();
		uploadService.resetGroup(groupId);
		if (paper.getFile() != null) {
			String bookFilepath = ApplicationContainer.sc.getRealPath(paper.getFile());
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
		Paper prePaper = (Paper)dao.query(Paper.class, entityId);
		String orignFile = prePaper.getFile();
		//基本信息
		prePaper.setSubmitStatus(paper.getSubmitStatus());
		prePaper.setChineseName(paper.getChineseName().trim());
		prePaper.setEnglishName((null != paper.getEnglishName()) ? paper.getEnglishName().trim() : null);
		prePaper.setOtherAuthorName((null != paper.getOtherAuthorName()) ? productService.MutipleToFormat(personExtService.regularNames(paper.getOtherAuthorName())) : null);
		prePaper.setType(paper.getType());
		prePaper.setForm(paper.getForm());
		prePaper.setIsTranslation(paper.getIsTranslation());
		prePaper.setIsForeignCooperation(paper.getIsForeignCooperation());
		prePaper.setWordNumber(paper.getWordNumber());
		prePaper.setDisciplineType(paper.getDisciplineType().trim());
		prePaper.setDiscipline(paper.getDiscipline().trim());
		prePaper.setKeywords(this.productService.MutipleToFormat(paper.getKeywords().trim()));
		prePaper.setIntroduction((null != paper.getIntroduction()) ? ("A" + paper.getIntroduction()).trim().substring(1) : null);
		prePaper.setPublication(paper.getPublication().trim());
		prePaper.setPublicationDate(paper.getPublicationDate());
		prePaper.setPublicationScope(paper.getPublicationScope());
		prePaper.setIndex((null != paper.getIndex()) ? paper.getIndex().trim() : null);
		prePaper.setVolume(paper.getVolume());
		prePaper.setNumber(paper.getNumber());
		prePaper.setPage((null != paper.getPage()) ? paper.getPage().trim() : null);
		prePaper.setIssn((null != paper.getIssn()) ? paper.getIssn().trim() : null);
		prePaper.setCn((null != paper.getCn()) ? paper.getCn().trim() : null);
		
		//处理附件
		String groupId = "file_" + prePaper.getId();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
				File curFile = fileRecord.getOriginal();
				String newFileName = productService.getFileName(curFile, savePath, PRODUCT_TYPE);
				prePaper.setFile(newFileName);
				fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(prePaper.getFile())));//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
	 	
	 	//非外部修改成果
	 	if(null == viewType) {
	 		applyType = (Integer)session.get("applyType");
		 	if(applyType == 1) {//1.以团队名义
		 		//填充成果作者、单位信息
				prePaper = (Paper)productService.fillAuthorAndAgencyInfos(loginer.getCurrentType(), prePaper, organizationAuthorId, organizationAuthorTypeId, organizationAuthorType);
				prePaper.setOrgName(paper.getOrgName());
				prePaper.setOrgDiscipline(paper.getOrgDiscipline());
				prePaper.setOrgEmail(paper.getOrgEmail());
				prePaper.setOrgMember(paper.getOrgMember());
				prePaper.setOrgMobilePhone(paper.getOrgMobilePhone());
				prePaper.setOrgOfficeAddress(paper.getOrgOfficeAddress());
				prePaper.setOrgOfficePhone(paper.getOrgOfficePhone());
				prePaper.setOrgPerson(paper.getOrgPerson());
				prePaper.setOrgOfficePostcode(paper.getOrgOfficePostcode());
		 	} else {
		 		//填充成果作者、单位信息
				prePaper = (Paper)productService.fillAuthorAndAgencyInfos(loginer.getCurrentType(), prePaper, authorId, authorTypeId, authorType);
		 	}
		 	
			//如果是项目成果, 保存关联信息
			if(isProRel == 1) {
				projectProduct.setProduct(prePaper);
				dao.add(projectProduct);
			}
	 	} else {//外部项目修改成果
	 		ProjectProduct oldProjectProduct = productService.getProjectProductByProductId(prePaper.getId());
	 		oldProjectProduct.setIsMarkMoeSupport(projectProduct.getIsMarkMoeSupport());
	 		dao.modify(oldProjectProduct);
	 		//设置结项最终成果
	 		if(viewType == 3) {//根据成果id、结项id重新设置结项最终成果（只有一个）
	 			productService.setEndInspectionFinalProduct(entityId, inspectionId, isFinalProduct);
	 		}
	 	}
	 	dao.modify(prePaper);
	 	//将文件组(groupId)中文件的更新操作（删除、移动）在磁盘中实施
		uploadService.flush(groupId);
		//DMSS同步 
		if(orignFile != prePaper.getFile() && dmssService.getStatus()){ //文件已修改
			if(null != orignFile){//原来有文件
				if(null != prePaper.getFile()){ //现在有文件
					productService.checkInToDmss(prePaper);
				}else{ //现在没文件
					dmssService.deleteFile(prePaper.getDfs());
					prePaper.setDfs(null);
					dao.modify(prePaper);
				}
			}else{ //原来没有文件
				if(prePaper.getFile()!=null){ //现在有文件
					String dfsId = productService.uploadToDmss(prePaper);
					prePaper.setDfs(dfsId);
					dao.modify(prePaper);
				}
			}
		}
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
		Paper prePaper = (Paper)dao.query(Paper.class, entityId);
		String groupId = "file_" + prePaper.getId();
		if (uploadService.getGroupFiles(groupId).isEmpty() || uploadService.getGroupFiles(groupId).size() == 0) {
			this.addFieldError("file", ProductInfo.ERROR_FILE_NULL);
		} else if (!uploadService.getGroupFiles(groupId).isEmpty() && uploadService.getGroupFiles(groupId).size() > 1) {
			this.addFieldError("file", ProductInfo.ERROR_FILE_OUT);
		}
//		validateMust(); validateEdit();//校验
//		if(fileIds != null && fileIds.length > 1){
//			this.addFieldError("file", ProductInfo.ERROR_FILE_OUT);
//		}
	}
	
	//校验添加后不能修改的字段
	public void validateMust(){
		if(null == paper.getChineseName() || "".equals(paper.getChineseName())){
			addFieldError("paper.chineseName", ProductInfo.ERROR_CHINESE_NAME_NULL);
		} else if(paper.getChineseName().trim().length() > 50){
			addFieldError("paper.chineseName", ProductInfo.ERROR_CHINESE_NAMR_OUT);
		}
		if(null == authorId || "".equals(authorId)){
			addFieldError("paper.authorName", ProductInfo.ERROR_FIRST_AUTHOR_NULL);
		}
		if(null != paper.getOtherAuthorName() && paper.getOtherAuthorName().trim().length() > 200){
			addFieldError("paper.otherAuthorName", ProductInfo.ERROR_OTHER_AUTHOR_NAME_OUT);
		}
		if(null == paper.getDisciplineType() || "".equals(paper.getDisciplineType())){
			addFieldError("paper.disciplineType", ProductInfo.ERROR_DTYPE_NULL);
		} else if(paper.getDisciplineType().trim().length() > 100){
			addFieldError("paper.disciplineType", ProductInfo.ERROR_DTYPE_OUT);
		}
		if(null == paper.getDiscipline() || "".equals(paper.getDiscipline())){
			addFieldError("paper.discipline", ProductInfo.ERROR_DISCIPLINE_NULL);
		} else if(paper.getDiscipline().trim().length() > 100){
			addFieldError("paper.discipline", ProductInfo.ERROR_DISCIPLINE_OUT);
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
		if(null != paper.getEnglishName() && paper.getEnglishName().trim().length( )> 200){
			addFieldError("paper.chineseName", ProductInfo.ERROR_ENGLIST_NAME_OUT);
		}
		if(null == paper.getType() || "-1".equals(paper.getType().getId())){
			addFieldError("paper.type.id", ProductInfo.ERROR_PTYPE_NULL);
		}
		if(null == paper.getForm() || "-1".equals(paper.getForm().getId())){
			addFieldError("paper.form.id", ProductInfo.ERROR_FORM_NULL);
		}
		if(paper.getKeywords().trim().length() > 100){
			addFieldError("paper.keywords", ProductInfo.ERROR_KEYWORD_OUT);
		}
		if(null != paper.getIntroduction() && paper.getIntroduction().trim().length() > 20000){
			addFieldError("paper.introduction", ProductInfo.ERROR_INTRODUCTION_OUT);
		}
		if(null == paper.getPublication() || paper.getPublication().trim().length() == 0){
			addFieldError("paper.publication", ProductInfo.ERROR_PUBLICATION_NULL);
		}else if( paper.getPublication().trim().length() > 50){
			addFieldError("paper.publication", ProductInfo.ERROR_PUBLICATION_OUT);
		}
		if(paper.getPublicationDate() == null){
			addFieldError("paper.publicationDate", ProductInfo.ERROR_PUBLICATION_DATE_NULL);
		}
		if(null == paper.getPublicationScope() || "-1".equals(paper.getPublicationScope().getId())){
			addFieldError("paper.publicationScope", ProductInfo.ERROR_PUBLICATION_SCOPE_NULL);
		}
		if(null != paper.getIndex() && paper.getIndex().trim().length() > 100){
			addFieldError("paper.index", ProductInfo.ERROR_INDEX_OUT);
		}
		if(null != paper.getVolume() && (paper.getVolume().compareTo("0") < 0 || paper.getVolume().compareTo("999") > 0)){
			addFieldError("paper.volume", ProductInfo.ERROR_VOLUME_OUT);
		}
		if(null != paper.getPage() && paper.getPage().trim().length() > 40){
			addFieldError("paper.page", ProductInfo.ERROR_PAGE_OUT);
		}
		if(null != paper.getIssn() && paper.getIssn().trim().length() > 40){
			addFieldError("paper.issn", ProductInfo.ERROR_ISSN_OUT);
		}
		if(null != paper.getCn() && paper.getCn().trim().length() > 40){
			addFieldError("paper.cn", ProductInfo.ERROR_CN_OUT);
		}
	}
	
	/**
	 * 加载系统选项
	 */
	@SuppressWarnings("unchecked")
	public void initOptions() {
		fetchAuthorInfo();//获取作者信息
		session.put("projectType", ProjectGranted.typeMap);//获取项目类型
	}
	
	public Paper getPaper() {
		return paper;
	}
	public void setPaper(Paper paper) {
		this.paper = paper;
	}
}