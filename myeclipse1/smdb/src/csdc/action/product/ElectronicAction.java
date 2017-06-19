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

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Electronic;
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
 * 电子出版物成果管理
 */
public class ElectronicAction extends ProductBaseAction {
	
	private static final long serialVersionUID = 1305542763942864533L;
	private static Integer PRODUCT_TYPE = 4 ;//成果形式（1：论文；2:著作；3:研究咨询报告；4：电子出版物；5：专利；6：其他成果）
	private static String HQL = "select p.id, p.chineseName, p.authorName, p.agencyName, p.publishUnit, " +
		"p.file, aut.id, uni.id, p.divisionName, dep.id, ins.id, s.name, p.submitStatus, p.auditResult, " +
		"p.auditStatus, p.auditDate from Electronic p left join p.university uni left join p.department dep " +
		"left join p.institute ins left join p.type s left join p.author aut where ";
	private static final String PAGE_NAME = "electronicPage";
	private Electronic electronic;//著作对象
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
		return ElectronicAction.COLUMN;
	}
	public String pageName() {
		return ElectronicAction.PAGE_NAME;
	}
	public Integer productType() {
		return ElectronicAction.PRODUCT_TYPE;
	};
	
	/**
	 * 初始化列表查询的hql、hql参数、默认排序列、错误信息(如有错误信息，加载列表时会显示该信息而非列表数据)
	 * @return 0:hql  1:hql参数   2:默认排序列编号  3:错误信息  
	 */
	public Object[] simpleSearchCondition() {
		int cloumnLabel = 0;//排序列位置
		keyword = (null == keyword)? "" : keyword.toLowerCase();
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
	public Object[] advSearchCondition() {
		AccountType accountType = loginer.getCurrentType();
		int cloumnLabel = 0;//排序列位置
		StringBuffer hql = new StringBuffer(HQL);
		Map parMap = new HashMap();
		hql.append(" p.id is not null ");
		if(null != electronic.getChineseName() && !electronic.getChineseName().isEmpty()){
			parMap.put("chineseName", "%" + electronic.getChineseName() + "%");
			hql.append(" and LOWER(p.chineseName) like :chineseName ");
		}
		if(!electronic.getType().getId().equals("-1")){
			parMap.put("type", electronic.getType().getId());
			hql.append(" and s.id like :type ");
		}
		if(null != electronic.getDisciplineType() && !electronic.getDisciplineType().isEmpty()){
			String[] dtypes = electronic.getDisciplineType().split("; ");
			int len = dtypes.length;
			if(len > 0){
				hql.append("and (");
				for(int i = 0; i < len; i++){
					parMap.put("dtype" + i, "%" + dtypes[i].toLowerCase() + "%");
					hql.append("LOWER(p.disciplineType) like :dtype" + i);
					if (i != len-1)
						hql.append(" or ");
				}hql.append(")");
			}
		} 
		if(null != electronic.getAuthorName() && !electronic.getAuthorName().isEmpty()){
			parMap.put("authorName", "%" + electronic.getAuthorName() + "%");
			hql.append(" and LOWER(p.authorName) like :authorName");
		}
		if(null != electronic.getAgencyName()&& !electronic.getAgencyName().isEmpty()){
			parMap.put("univ", "%"+electronic.getAgencyName() + "%");
			hql.append(" and LOWER(p.agencyName) like :univ");
		}
		if(null != electronic.getDivisionName()&& !electronic.getDivisionName().isEmpty()){
			parMap.put("unit", "%" + electronic.getDivisionName() + "%");
			hql.append(" and LOWER(p.divisionName) like :unit");
		}
		if(null != electronic.getPublishUnit() && !electronic.getPublishUnit().isEmpty()){
			parMap.put("publicationUnit", "%" + electronic.getPublishUnit() + "%");
			hql.append(" and LOWER(p.publishUnit) like :publicationUnit");
		}
		if(null != electronic.getProvinceName() && !electronic.getProvinceName().isEmpty()){
			parMap.put("provinceName", "%" + electronic.getProvinceName() + "%");
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
			if(electronic.getSubmitStatus() != -1){
				parMap.put("submitStatus", electronic.getSubmitStatus());
				hql.append(" and p.submitStatus = :submitStatus");
			}
		} else {//管理人员
			if(electronic.getAuditResult() != -1){
				parMap.put("auditResult", electronic.getAuditResult());
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
	public void saveAdvSearchQuery(Map searchQuery) {
		AccountType accountType = loginer.getCurrentType();
		if(null != electronic.getChineseName() && !electronic.getChineseName().isEmpty()){
			searchQuery.put("chineseName", electronic.getChineseName());
		}
		if(!electronic.getType().getId().equals("-1")){
			searchQuery.put("type", electronic.getType().getId());
		}
		if(null != electronic.getDisciplineType() && !electronic.getDisciplineType().isEmpty()){
			searchQuery.put("disciplineType", electronic.getDisciplineType());
		} 
		if(null != electronic.getAuthorName() && !electronic.getAuthorName().isEmpty()){
			searchQuery.put("authorName", electronic.getAuthorName());
		}
		if(null != electronic.getAgencyName() && !electronic.getAgencyName().isEmpty()){
			searchQuery.put("agencyName", electronic.getAgencyName());
		}
		if(null != electronic.getDivisionName() && !electronic.getDivisionName().isEmpty()){
			searchQuery.put("divisionName", electronic.getDivisionName());
		}
		if(null != electronic.getPublishUnit() && !electronic.getPublishUnit().isEmpty()){
			searchQuery.put("publishUnit", electronic.getPublishUnit());
		}
		if(null != electronic.getProvinceName() && !electronic.getProvinceName().isEmpty()){
			searchQuery.put("provinceName", electronic.getProvinceName());
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
			if(electronic.getSubmitStatus() != -1){
				searchQuery.put("submitStatus", electronic.getSubmitStatus());
			}
		} else {//管理人员
			if(electronic.getAuditResult()!= -1){
				searchQuery.put("auditResult", electronic.getAuditResult());
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
		electronic = (Electronic)dao.query(Electronic.class, entityId);//获取电子出版物
		jsonMap.put("productType", electronic.getProductType());//获取电子出版物类型
		jsonMap.put("accountType", loginer.getCurrentType());//获取当前账号类型
		jsonMap.put("electronic", electronic);//获取电子出版物对象
		jsonMap.put("electronicType", (null != electronic.getType()) ? electronic.getType().getName() : null);//电子出版物类型名称
		jsonMap.put("electronicTypeId", (null != electronic.getType()) ? electronic.getType().getId() : null);//电子出版物类型名称
		jsonMap.put("form", (null != electronic.getForm()) ? electronic.getForm().getName() : null);//形态名称
		jsonMap.put("universityid", (null != electronic.getUniversity()) ? electronic.getUniversity().getId() : null);//高校id
		jsonMap.put("authorid", (null != electronic.getAuthor()) ? electronic.getAuthor().getId() : null);//作者id
		//团队信息
//		if(null != electronic.getOrganization() && !electronic.getOrganization().getId().isEmpty()) {
//			organization = (Organization)dao.query(Organization.class, electronic.getOrganization().getId());
//		}
//		jsonMap.put("organization", organization);
		//项目相关信息
		jsonMap.put("relProjectInfos", productService.getRelProjectInfos(entityId));
		//奖励相关信息
		jsonMap.put("relAwardInfos", productService.getRelAwardInfos(entityId));
		//成果能否被修改
		boolean canBeModify = productService.canModifyProduct(entityId);
		if (electronic.getFile() != null) {
			//获取文件大小
			List<String> attachmentSizeList = new ArrayList<String>();
			String[] attachPath = electronic.getFile().split("; ");
			InputStream is = null;
			for (String path : attachPath) {
				is = ApplicationContainer.sc.getResourceAsStream(path);
				if (null != is) {
					attachmentSizeList.add(baseService.accquireFileSize(is.available()));
				} else if(electronic.getDfs() != null && !electronic.getDfs().isEmpty() && dmssService.getStatus()) {
					long fileSize = dmssService.accquireFileSize(electronic.getDfs());
					attachmentSizeList.add(baseService.accquireFileSize(fileSize));
				} else {// 附件不存在
					attachmentSizeList.add(null);
				}
				jsonMap.put("attachmentSizeList", attachmentSizeList);
				if(is != null) {
					is.close();
				}
			}
		}
		jsonMap.put("canBeModify", canBeModify);
		session.put("canBeModify", canBeModify);
		session.put("electronicViewJson", jsonMap);
		return SUCCESS;
	}
	
	/**
	 * 保存成果
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String add()throws Exception{
		//成果文件信息
		String groupId = "file_add";
		if(null != viewType && viewType != 0) {//外部弹出层添加项目成果
			groupId = "file_add_elec";
		}
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
				File curFile = fileRecord.getOriginal();
				String newFileName = productService.getFileName(curFile, savePath, PRODUCT_TYPE);
				electronic.setFile(newFileName);
				fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(electronic.getFile())));//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}

		//成果作者、单位信息
		if(applyType == 1) {//1.以团队名义申请成果
			electronic = (Electronic)productService.fillAuthorAndAgencyInfos(loginer.getCurrentType(), electronic, organizationAuthorId, organizationAuthorTypeId, organizationAuthorType);
			//获取人员对象
			Person person = (Person)dao.query(Person.class, organizationAuthorId);
			electronic.setOrgPerson(person);
		} else {//2.以个人名义申请成果
			//成果作者、单位信息
			electronic = (Electronic)productService.fillAuthorAndAgencyInfos(loginer.getCurrentType(), electronic, authorId, authorTypeId, authorType);
		}
		
		//成果审核信息[管理人员添加, 默认审核通过]
		electronic = (loginer.getCurrentType().compareTo(AccountType.EXPERT)<0) ? (Electronic)productService.fillAuditInfo(loginer.getAccount(), electronic, 2) : electronic;
		
		//将提交时间设为系统时间
		electronic.setSubmitDate(new Date());
		electronic.setCreateDate(new Date());
		electronic.setCreateMode(1);
		entityId = dao.add(electronic);
		
		//项目相关信息
		if(isProRel == 1) {
			ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, projectGrantedId) ;
			projectProduct.setProjectGranted(projectGranted);
			projectProduct.setProduct(electronic);
			dao.add(projectProduct);
		}
		
		//成果绑定团队
//		if(null != session.get("awardOrganizationId")) {
//			String organizationId = (String)session.get("awardOrganizationId");
//			organization = (Organization)dao.query(Organization.class, organizationId);
//			electronic.setOrganization(organization);
//			dao.modify(electronic);
//			session.remove("awardOrganizationId");
//		}
		
		//外部弹出层添加项目成果
		if(null != viewType && viewType != 0){
			electronic.setSubmitStatus(3);
			//项目添加成果
			if(viewType == 1 || viewType == 2 || viewType == 3||viewType == 4) {
				productService.addNewProductToProjectInspection(projectProduct, entityId, projectId, viewType, inspectionId, isFinalProduct);
				//设置结项最终成果
		 		if(viewType == 3) {
		 			productService.setEndInspectionFinalProduct(entityId, inspectionId, isFinalProduct);
		 		}
			} else if(viewType == 5) {//奖励添加成果
				jsonMap.put("productId", entityId);
				jsonMap.put("productType", electronic.getProductType());
				uploadService.flush(groupId);
				String dfsId = productService.uploadToDmss(electronic);
				electronic.setDfs(dfsId);
			}
			
			return "finish";
		}
		uploadService.flush(groupId);
		String dfsId = productService.uploadToDmss(electronic);
		electronic.setDfs(dfsId);
		return SUCCESS;
	}
	
	/**
	 * 校验添加成果
	 */
	public void validateAdd(){
		validateMust(); validateEdit();//校验
		String groupId = "file_add";
		if(null != viewType && viewType != 0) {//外部弹出层添加项目成果
			groupId = "file_add_elec";
		}
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
		inputView = (null != exflag && exflag ==1) ? "/product/extIf/popAdd.jsp" : "/product/electronic/add.jsp";
	}
	
	/**
	 * 转到成果更新
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String toModify()throws Exception{
		//判断是否在管辖范围之内
		if(!productService.checkIfUnderControl(loginer, entityId.trim(), 15, true)){
			addActionError("您所选择的成果不在您的管辖范围内！");
			return ERROR;}
		electronic = (Electronic)dao.query(Electronic.class, entityId);
		if(null != electronic.getChineseName()){
			electronic.setChineseName(electronic.getChineseName().trim());
		}
		//判断是否为项目成果
		if(productService.isRelProduct(entityId)) {
			Map projectInfos = productService.getRelProjectInfos(entityId).get(0);
			session.put("projectInfos", projectInfos);
			isProRel = 1;
		}
		//获取团队信息
		if(null != electronic.getOrgName()) {
			organizationAuthorId = (null != electronic.getAuthor()) ? electronic.getAuthor().getId() : null;
			organizationAuthorTypeId = productService.getAuthorIdOfAuthorType(entityId);
			organizationAuthorType = electronic.getAuthorType();
			applyType = 1;
		} else {//获取个人信息
			authorId = (null != electronic.getAuthor()) ? electronic.getAuthor().getId() : null;
			authorTypeId = productService.getAuthorIdOfAuthorType(entityId);
			authorType = electronic.getAuthorType();
			if(null != authorId){
				author = (Person)dao.query(Person.class, electronic.getAuthor().getId());
				fetchAuthorInfo();//获取作者信息
			}
		}
		if(null != viewType) {
			//获取项目立项成果对象
			projectProduct = productService.getProjectProductByProductId(electronic.getId());
			if(viewType == 3) {//结项成果
				ProjectEndinspectionProduct ProjectEndinspectionProduct = productService.getProjectEndinspectionProduct(entityId, inspectionId);
				isFinalProduct = (null != ProjectEndinspectionProduct) ? ProjectEndinspectionProduct.getIsFinalProduct() : 0;
			}
		}
		//将已有附件加入文件组，在编辑页面显示
		String groupId = "file_" + electronic.getId();
		uploadService.resetGroup(groupId);
		if (electronic.getFile() != null) {
			String bookFilepath = ApplicationContainer.sc.getRealPath(electronic.getFile());
			if (bookFilepath != null && new File(bookFilepath).exists()) {
				uploadService.addFile(groupId, new File(bookFilepath));
			} else if(electronic.getDfs() != null && !electronic.getDfs().isEmpty() && dmssService.getStatus()) {//如果文件在本地服务器不存在，则从云存储中获取临时文件至服务器
				try {
					InputStream downloadStream = dmssService.download(electronic.getDfs());
					String sessionId = ServletActionContext.getRequest().getSession().getId();
					File dir = new File(ApplicationContainer.sc.getRealPath("temp" + "/" + sessionId.replaceAll("\\W+", "")));
					dir.mkdirs();
					String fileName = electronic.getFile().substring(electronic.getFile().lastIndexOf("/") + 1);
					File downloadFile = new File(dir, fileName);
					FileUtils.copyInputStreamToFile(downloadStream, downloadFile);
					uploadService.addFile(groupId, downloadFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
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
	public String modify() throws Exception{
		Electronic preElectronic=(Electronic)dao.query(Electronic.class, entityId);
		String orignFile = preElectronic.getFile();
		//基本信息
		preElectronic.setSubmitStatus(electronic.getSubmitStatus());
		preElectronic.setChineseName(electronic.getChineseName().trim());
		preElectronic.setEnglishName((null != electronic.getEnglishName()) ? electronic.getEnglishName().trim() : null);
		preElectronic.setOtherAuthorName((null != electronic.getOtherAuthorName()) ? productService.MutipleToFormat(personExtService.regularNames(electronic.getOtherAuthorName())) : null);
		preElectronic.setType(electronic.getType());
		preElectronic.setForm(electronic.getForm());
		preElectronic.setIsTranslation(electronic.getIsTranslation());
		preElectronic.setIsForeignCooperation(electronic.getIsForeignCooperation());
		preElectronic.setDisciplineType(electronic.getDisciplineType().trim());
		preElectronic.setDiscipline(electronic.getDiscipline().trim());
		preElectronic.setKeywords(this.productService.MutipleToFormat(electronic.getKeywords().trim()));
		preElectronic.setIntroduction((null != electronic.getIntroduction()) ? ("A" + electronic.getIntroduction()).trim().substring(1) : null);
		preElectronic.setPublishUnit(electronic.getPublishUnit().trim());
		preElectronic.setPublishDate(electronic.getPublishDate());
		preElectronic.setUseUnit(electronic.getUseUnit().trim());
		
		//处理附件
		String groupId = "file_" + preElectronic.getId();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
				File curFile = fileRecord.getOriginal();
				String newFileName = productService.getFileName(curFile, savePath, PRODUCT_TYPE);
				preElectronic.setFile(newFileName);
				fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(preElectronic.getFile())));//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		
		
		//非外部修改成果
	 	if(null == viewType) {
			applyType = (Integer)session.get("applyType");
		 	if(applyType == 1) {//1.以团队名义
		 		//填充成果作者、单位信息
				preElectronic = (Electronic)productService.fillAuthorAndAgencyInfos(loginer.getCurrentType(), preElectronic, organizationAuthorId, organizationAuthorTypeId, organizationAuthorType);
				preElectronic.setOrgDiscipline(electronic.getOrgDiscipline());
				preElectronic.setOrgEmail(electronic.getOrgEmail());
				preElectronic.setOrgMember(electronic.getOrgMember());
				preElectronic.setOrgMobilePhone(electronic.getOrgMobilePhone());
				preElectronic.setOrgOfficeAddress(electronic.getOrgOfficeAddress());
				preElectronic.setOrgOfficePhone(electronic.getOrgOfficePhone());
				preElectronic.setOrgPerson(electronic.getOrgPerson());
				preElectronic.setOrgOfficePostcode(electronic.getOrgOfficePostcode());
		 	} else {
		 		//填充成果作者、单位信息
				preElectronic = (Electronic)productService.fillAuthorAndAgencyInfos(loginer.getCurrentType(), preElectronic, authorId, authorTypeId, authorType);
		 	}
			
			//如果是项目成果, 保存关联信息
			if(isProRel == 1) {
				projectProduct.setProduct(preElectronic);
				dao.add(projectProduct);
			}
	 	} else {//外部项目修改成果
	 		ProjectProduct oldProjectProduct = productService.getProjectProductByProductId(preElectronic.getId());
	 		oldProjectProduct.setIsMarkMoeSupport(projectProduct.getIsMarkMoeSupport());
	 		dao.modify(oldProjectProduct);
	 		//设置结项最终成果
	 		if(viewType == 3) {
	 			productService.setEndInspectionFinalProduct(entityId, inspectionId, isFinalProduct);
	 		}
	 	}
	 	//更新电子出版物信息
	 	preElectronic.setUpdateDate(new Date());
		dao.modify(preElectronic);
		//将文件组(groupId)中文件的更新操作（删除、移动）在磁盘中实施
		uploadService.flush(groupId);
		//DMSS同步 
		if(orignFile != preElectronic.getFile() && dmssService.getStatus()){ //文件已修改
			if(null != orignFile){//原来有文件
				if(null != preElectronic.getFile()){ //现在有文件
					productService.checkInToDmss(preElectronic);
				}else{ //现在没文件
					dmssService.deleteFile(preElectronic.getDfs());
					preElectronic.setDfs(null);
					dao.modify(preElectronic);
				}
			}else{ //原来没有文件
				if(preElectronic.getFile()!=null){ //现在有文件
					String dfsId = productService.uploadToDmss(preElectronic);
					preElectronic.setDfs(dfsId);
					dao.modify(preElectronic);
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
//		validateMust();
		validateEdit();//校验
		Electronic preElectronic=(Electronic)dao.query(Electronic.class, entityId);
		String groupId = "file_" + preElectronic.getId();
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
		if(null == electronic.getChineseName() || "".equals(electronic.getChineseName())){
			addFieldError("electronic.chineseName", ProductInfo.ERROR_CHINESE_NAME_NULL);
		} else if(electronic.getChineseName().trim().length() > 50){
			addFieldError("electronic.chineseName", ProductInfo.ERROR_CHINESE_NAMR_OUT);
		}
		if(null == authorId || "".equals(authorId)){
			addFieldError("electronic.authorName", ProductInfo.ERROR_FIRST_AUTHOR_NULL);
		}
		if(null != electronic.getOtherAuthorName() && electronic.getOtherAuthorName().trim().length() > 200){
			addFieldError("electronic.otherAuthorName", ProductInfo.ERROR_OTHER_AUTHOR_NAME_OUT);
		}
		if(null == electronic.getDisciplineType() || "".equals(electronic.getDisciplineType())){
			addFieldError("electronic.disciplineType", ProductInfo.ERROR_DTYPE_NULL);
		} else if(electronic.getDisciplineType().trim().length() > 100){
			addFieldError("electronic.disciplineType", ProductInfo.ERROR_DTYPE_OUT);
		}
		if(null == electronic.getDiscipline() || "".equals(electronic.getDiscipline())){
			addFieldError("electronic.discipline", ProductInfo.ERROR_DISCIPLINE_NULL);
		} else if(electronic.getDiscipline().trim().length() > 100){
			addFieldError("electronic.discipline", ProductInfo.ERROR_DISCIPLINE_OUT);
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
		if(null != electronic.getEnglishName() && electronic.getEnglishName().trim().length() > 200){
			addFieldError("electronic.englishName", ProductInfo.ERROR_ENGLIST_NAME_OUT);
		}
		if(null == electronic.getType() || "-1".equals(electronic.getType().getId())){
			addFieldError("electronic.ptype.id", ProductInfo.ERROR_PTYPE_NULL);
		}
		if(null == electronic.getForm() || "-1".equals(electronic.getForm().getId())){
			addFieldError("electronic.form.id", ProductInfo.ERROR_FORM_NULL);
		}
		if(electronic.getKeywords().trim().length() > 100){
			addFieldError("electronic.keywords", ProductInfo.ERROR_KEYWORD_OUT);
		}
		if(null != electronic.getIntroduction() && electronic.getIntroduction().trim().length() > 20000){
			addFieldError("electronic.introduction", ProductInfo.ERROR_INTRODUCTION_OUT);
		}
		if( electronic.getPublishUnit().trim().length() > 50){
			addFieldError("electronic.publishUnit", ProductInfo.ERROR_PUBLICATION_UNIT_OUT);
		}
	}
	
	/**
	 * 加载系统选项
	 */
	@SuppressWarnings("unchecked")
	public void initOptions() {
		fetchAuthorInfo();//获取作者信息
		session.put("electronicType", baseService.getSOByParentName("电子出版物"));//获取电子出版物类型
		session.put("productType", baseService.getSOByParentName("成果形态"));//获取成果形态
		session.put("projectType", ProjectGranted.typeMap);//获取项目类型
	}
	
	public Electronic getElectronic() {
		return electronic;
	}
	public void setElectronic(Electronic electronic) {
		this.electronic = electronic;
	}
}