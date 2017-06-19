package csdc.action.unit;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import csdc.action.BaseAction;
import csdc.bean.Account;
import csdc.bean.Institute;
import csdc.bean.Person;
import csdc.bean.SystemOption;
import csdc.service.IGeneralService;
import csdc.service.IProductService;
import csdc.service.IUnitService;
import csdc.tool.FileTool;
import csdc.tool.HSSFExport;
import csdc.tool.InputValidate;
import csdc.tool.SpringBean;
import csdc.tool.bean.AccountType;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.UnitInfo;
import csdc.tool.merger.InstituteMerger;

/**
 * 研究基地类
 * 该类包含了研究基地的增、删、改、查及基本检索和高级检索功能
 * @author 江荣国
 * @version 2011.04.13
 */

@SuppressWarnings("unchecked")
public class InstituteAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	private static final String HQL = "select ins.id,ins.code,ins.name,pe.name,ag.id,ag.name,ins.phone,ins.fax,pe.id from Institute ins left join ins.director pe left join ins.subjection ag left join ag.province so ";
	private static final String PAGE_NAME = "institutePage";// 列表页面名称
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "ins.id"; //缓存id
	private static final String[] COLUMN = {"ins.name","ins.code, ins.name","ag.name, ins.name","pe.name, ins.name","ins.phone, ins.name","ins.fax, ins.name"};// 排序列
	
	private IUnitService unitService; //机构管理接口
	private IProductService productService;
	private IGeneralService generalService;
	private InputValidate inputValidate = new InputValidate();//校验工具类
	private Institute institute; //研究基地对象
	private Account account; //账号对象
	private List institutes;
	private List<Person> persons;
	private String[] delid;	//选中的id
	private String delids;	//选中的id拼成的字符串
	private String rDisciplineId; //依托重点学科ID组成的字符串
	private String discpId; //学科编号
	private String hiddenId;  //已选学科编号序列
	private String hiddenName; //已选学科名称序列
	private String relyDisciplineId; //依赖学科类别序列
	private String iName; //高级检索中研究机构名称
	private String iCode; //高级检索中研究机构代码
	private String iType; //高级检索中研究机构类型
	private String iUniversity; //高级检索中研究机构所属高校
	private String iDirectorName; //高级检索中研究机构负责人姓名
	private String directorId; //负责人
	private String linkmanId; //联系人
	private String directorName; //负责人
	private String linkmanName; //联系人
	private String provName; //高级检索中高校所在省份
	private String subjectionName; //上级管理机构名称
	private String checkedIds, mainId, name, code;//合并高校、院系、基地时的所选id和主id，合并后的名称和代码
	private int listLabel; //是否从左边栏进入的标志 1:是
	private int viewFlag; //查看类型：0，普通查看;1,个人空间中的查看

	private Integer instType;
	private String instTypeName;
	/**
	 * 为添加研究机构做准备
	 * @return success 转到addInstitute页面
	 */
	public String toAdd(){
		return SUCCESS;
	}

	/**
	 * 添加研究机构
	 * @return success 转到viewInstitute页面
	 */
	@Transactional
	public String add(){
		Account current = loginer.getAccount();
		if(unitService.checkInstituteLeadByAccount(institute, current, loginer.getCurrentBelongUnitId())){
			institute.setName(institute.getName().trim());
			institute.setType((SystemOption)dao.query(SystemOption.class, institute.getType().getId()));// 需要持久态，所以要重新查一次
			entityId = dao.add(institute);
			return SUCCESS;
		}else{
			this.addFieldError("institute.name",UnitInfo.ERROR_MANAGEMENT_OUTSIDE);
			return INPUT;
		}
	}

	/**
	 * 进入查看页面
	 * @return
	 */
	public String toView() {
		return SUCCESS;
	}

	/**
	 * 查看研究机构详细信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String view(){
		if(!unitService.checkIfUnderControl(loginer, entityId.trim(), 5, true)){
			this.addFieldError(GlobalInfo.ERROR_INFO, UnitInfo.ERROR_MANAGEMENT_OUTSIDE);
			return ERROR;
		}
		unitService.getViewOfInstitute(entityId, jsonMap);
		return SUCCESS;
	}
	
	public String toAdvSearch(){
		return SUCCESS;
	}

	/**
	 * 初级检索
	 * @return SUCCESS返回列表页面,INPUT信息提示
	 */
	@SuppressWarnings("unchecked")
	public Object[] simpleSearchCondition() {
		keyword = (null == keyword) ? "" : keyword.toLowerCase();// 预处理关键字
		AccountType accountType = loginer.getCurrentType();
		String belongUnitId = loginer.getCurrentBelongUnitId();
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		hql.append(" left join ins.type tt ");
		if(accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)){//系统管理员或部级管理员
			hql.append(" where 1=1");
		}else if(accountType.equals(AccountType.PROVINCE)){//省级管理员(本省地方高校研究基地或本省省及研究基地
			hql.append(" left join ins.type typ where ag.subjection.id=:belongUnitId and (ag.type=4 or typ.code='02' or typ.code='03')");
			map.put("belongUnitId", belongUnitId);
		}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//学校管理员(位于本校的研究机构）
			hql.append(" where ins.subjection.id=:belongUnitId");
			map.put("belongUnitId", belongUnitId);
		}else{
			hql.append(" where 1=0");
		}
		//机构类型
		instType = (Integer) session.get("instType");
		String instTypeString = "0"+instType.toString();
		map.put("instType", instTypeString);
		hql.append(" and tt.standard = 'researchAgencyType' and tt.code = :instType ");
		
		if(!keyword.isEmpty()){
			if(searchType ==1){
				hql.append("and LOWER(ins.code) like:keyword");
				map.put("keyword", '%'+keyword+'%');
			}else if (searchType==2){
				hql.append("and LOWER(ins.name) like:keyword");
				map.put("keyword", '%'+keyword+'%');
			}else if(searchType == 3){
				hql.append("and LOWER(pe.name) like:keyword");
				map.put("keyword", '%'+keyword+'%');
			}else{
				hql.append("and (LOWER(ins.name) like :keyword or LOWER(ins.code) like :keyword or LOWER(pe.name) like :keyword) ");
				map.put("keyword", '%'+keyword+'%');
			}
		}
		return new Object[]{
			hql.toString(),
			map,
			0,
			null
		};
	}

	@SuppressWarnings("unchecked")
	public Object[] advSearchCondition(){
		AccountType accountType = loginer.getCurrentType();
		String belongUnitId = loginer.getCurrentBelongUnitId();
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		hql.append(" left join ins.type tt ");
		if(accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)) {//系统管理员或部级管理员
			hql.append(" where 1=1");
		}else if(accountType.equals(AccountType.PROVINCE)){//省级管理员(研究机构所在学校位于该省，并且研究机构的类型不是部级研究机构）
			hql.append(" left join ins.type typ where ag.subjection.id=:belongUnitId and (ag.type=4 or typ.code='02' or typ.code='03')");
			map.put("belongUnitId", belongUnitId);
		}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//学校管理员(位于本校的研究机构）
			hql.append(" where ins.subjection.id=:belongUnitId");
			map.put("belongUnitId", belongUnitId);
		}else{
			hql.append(" where 1=0");
		}
		
		instType = (Integer) session.get("instType");
		String instTypeString = "0"+instType.toString();
		map.put("instType", instTypeString);
		hql.append(" and tt.standard = 'researchAgencyType' and tt.code = :instType ");
		
		//高级检索条件
		if(iName !=null && iName.trim().length()>0){
			hql.append(" and LOWER(ins.name) like :name");
			map.put("name", '%'+iName+'%');
		}
		if(iCode !=null && iCode.trim().length()>0){
			hql.append(" and LOWER(ins.code) like :code");
			map.put("code", '%'+iCode+'%');
		}
		if(iType !=null && iType.trim().length()>3){ //排除-1的情况
			hql.append(" and LOWER(ins.type.id) =:type");
			map.put("type", iType);
		}
		if(iUniversity != null && iUniversity.trim().length()>0){
			hql.append(" and LOWER(ag.name) like :university");
			map.put("university", '%'+iUniversity+'%');
		}
		if(iDirectorName !=null && iDirectorName.trim().length()>0){
			hql.append(" and LOWER(pe.name) like :directorName");
			map.put("directorName", '%'+iDirectorName+'%');
		}
		if(provName !=null && provName.trim().length()>0){
			hql.append(" and LOWER(so.name) like :provName");
			map.put("provName", '%'+provName+'%');
		}
		return new Object[] {
			hql.toString(),
			map,
			0,
			null
		};
	}

	/**
	 * 对saveAdvSearchQuery方法进行子类重写
	 * @author yangfq
	 */
	public void saveAdvSearchQuery(Map searchQuery) {
		if(null != iName && !iName.isEmpty()){
			searchQuery.put("iName", iName);
		}
		if(null != iCode && !iCode.isEmpty()){
			searchQuery.put("iCode", iCode);
		}
		if(null != iType && !iType.isEmpty()){
			searchQuery.put("iType", iType);
		}
		if (null != iUniversity && !iUniversity.isEmpty()) {
			searchQuery.put("iUniversity", iUniversity);
		}
		if (null != iDirectorName && !iDirectorName.isEmpty()) {
			searchQuery.put("iDirectorName", iDirectorName);
		}
	}
	
	/**
	 * 为编辑准备研究机构对象
	 * @return
	 */
	@Transactional
	public String toModify(){
		institute = (Institute) dao.query(Institute.class, entityId);
		if(institute.getSubjection() != null) {
			subjectionName = institute.getSubjection().getName();
		}
		if(institute.getDirector()!=null){
			directorId = unitService.getOfficerIdByPersonId(institute.getDirector().getId());
			directorName = institute.getDirector().getName();
		}
		if(institute.getLinkman()!=null){
			linkmanId = unitService.getOfficerIdByPersonId(institute.getLinkman().getId());
			linkmanName =institute.getLinkman().getName();
		}
		session.put("instituteId", entityId);
//		session.put("instituteType", unitService.getInstituteType());//基地类型
		return SUCCESS;
	}

	/**
	 * 编辑研究机构信息
	 * @return
	 */
	@Transactional
	public String modify(){
		Account account = loginer.getAccount();
		//判断某个研究机构是否在指定账号的管辖范围
		if(!unitService.checkInstituteLeadByAccount(institute, account, loginer.getCurrentBelongUnitId())){
			this.addFieldError(GlobalInfo.ERROR_INFO, UnitInfo.ERROR_MANAGEMENT_OUTSIDE);
			return INPUT;
		}
		entityId = (String) session.get("instituteId");
		institute.setType((SystemOption)dao.query(SystemOption.class, institute.getType().getId()));// 需要持久态，所以要重新查一次
		institute.setResearchActivityType((SystemOption)dao.query(SystemOption.class, institute.getResearchActivityType().getId()));// 需要持久态，所以要重新查一次
		unitService.getModifyOfInstitute(session, institute, directorId, linkmanId, entityId);
		session.remove("instituteId");
		return SUCCESS;
	}
	
	/**
	 * 根据编号删除对应研究机构
	 * @param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String delete(){
		int type = unitService.deleteInstituteByIds(entityIds);
		if(type == 1){
			jsonMap.put(GlobalInfo.ERROR_INFO,UnitInfo.ERROR_UNIT_DELETE_CANNOT_1);
			return INPUT;
		} else if(type == 2){
			jsonMap.put(GlobalInfo.ERROR_INFO,UnitInfo.ERROR_UNIT_DELETE_CANNOT_2);
			return INPUT;
		} else if(type == 3){
			jsonMap.put(GlobalInfo.ERROR_INFO,UnitInfo.ERROR_UNIT_DELETE_CANNOT_3);
			return INPUT;
		} else if(type == 4){
			jsonMap.put(GlobalInfo.ERROR_INFO,UnitInfo.ERROR_UNIT_DELETE_CANNOT_4);
			return INPUT;
		} else if(type == 5){
			jsonMap.put(GlobalInfo.ERROR_INFO,UnitInfo.ERROR_UNIT_DELETE_CANNOT_5);
			return INPUT;
		} else if(type == 6){
			jsonMap.put(GlobalInfo.ERROR_INFO,UnitInfo.ERROR_UNIT_DELETE_CANNOT_6);
			return INPUT;
		}
		return SUCCESS;
	}
	
	/**
	 * 机构添加后台校验
	 * @return
	 */
	public void validateAdd(){
		if(null == institute.getName() || institute.getName().trim().length()==0){
			this.addFieldError("institute.name",UnitInfo.ERROR_NAME_NULL);
		}else if(institute.getName().trim().length()>40 || institute.getName().trim().length()<1){
			this.addFieldError("institute.name", UnitInfo.ERROR_NAME_ILLEGAL);
		}

		if(null == institute.getEnglishName() || institute.getEnglishName().trim().length()>200 || institute.getEnglishName().trim().length()<0){
			this.addFieldError("institute.englishName", UnitInfo.ERROR_ENGLISHNAME_ILLEGAL);
		}

		if(null !=institute.getCode() && (institute.getCode().trim().length()>40 || institute.getCode().trim().length()<0)){
			this.addFieldError("institute.code",UnitInfo.ERROR_CODE_ILLEGAL);
		}

		if(null !=institute.getAbbr() && (institute.getAbbr().trim().length()>40)){
			this.addFieldError("institute.abbr", UnitInfo.ERROR_ABBR_ILLEGAL);
		}

		if(null == institute.getType() || institute.getType().getId().trim().length()<5 || institute.getType().getId().trim().length()>40){
			this.addFieldError("institute.type.id",UnitInfo.ERROR_INSTITUTE_TYPE_NULL);
		}

		if(null == institute.getSubjection().getId().trim() || "".equals(institute.getSubjection().getId().trim())){
			this.addFieldError("institute.subjection.id",UnitInfo.ERROR_SUBJECTION_NULL);
		}

		if( institute.getIsIndependent()<0 || institute.getIsIndependent()>1){
			this.addFieldError("institute.isIndependent",UnitInfo.ERROR_ISINDEPENDENT_ILLEGAL);
		}

		if(null != institute.getApproveSession() && institute.getApproveSession().trim().length()>40){
			this.addFieldError("institute.approveSession",UnitInfo.ERROR_APPROVESESSION_ILLEGAL);
		}

		//设立日期未作校验
		if(null != institute.getForm() && institute.getForm().trim().length()>40){
			this.addFieldError("institute.form",UnitInfo.ERROR_FORM_ILLEGAL);
		}

		if(null != institute.getTeacher() && institute.getIntroduction().trim().length()>20000){
			this.addFieldError("institute.introduction",UnitInfo.ERROR_INTRODUCTION_ILLEGAL);
		}

		//面积数量未作校验

		if(null != institute.getAddress() && institute.getAddress().trim().length()>100){
			this.addFieldError("institute.address",UnitInfo.ERROR_ADDRESS_ILLEGAL);
		}
		if(null != institute.getPhone() && !inputValidate.checkPhone(institute.getPhone().trim())){
			this.addFieldError("institute.phone",UnitInfo.ERROR_PHONE_ILLEGAL);
		}
		if(null != institute.getEmail()){
			String[] mail = institute.getEmail().split(";");
			for (int i = 0; i < mail.length; i++) {
				String	email = mail[i];
				if(!inputValidate.checkEmail(email.trim())){
					this.addFieldError("institute.email",UnitInfo.ERROR_EMAIL_ILLEGAL);
				}
			}
		}
//		if(null != institute.getEmail() && !inputValidate.checkEmail(institute.getEmail().trim())){
//			this.addFieldError("institute.email",UnitInfo.ERROR_EMAIL_ILLEGAL);
//		}

		if(null != institute.getFax() && !inputValidate.checkFax(institute.getFax().trim())){
			this.addFieldError("institute.fax",UnitInfo.ERROR_FAX_ILLEGAL);
		}

		if(null != institute.getPostcode() && !inputValidate.checkPostcode(institute.getPostcode().trim())){
			this.addFieldError("institute.postcode",UnitInfo.ERROR_POSTCODE_ILLEGAL);
		}

//		if(hasErrors()){
//			return INPUT;
//		}else{
//			return null;
//		}
	}

	/**
	 * 修改前的后台校验
	 * @return
	 */
	public void validateModify(){
		unitService.getValidateModifyOfInstitute(institute, inputValidate, this);
	}
	
	/**
	 * 生成学科树
	 * @return
	 * @throws DocumentException
	 */
	public String createDisciplineTree() throws DocumentException {
		//将Document生成xml文件
		XMLWriter writer = null;
		SAXReader reader = new SAXReader();
		String discpCode = unitService.getDisciplineCodesByIds(discpId);
		//建立学科树文件的存放目录（有则不会重复建）
		String filePath=ServletActionContext.getServletContext().getRealPath("");
		FileTool.mkdir_p(filePath+"//data//discipline//");
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");
		//判断指定学科xml文件是否存在，不存在则建立
		File file = new File(filePath+"//data//discipline//"+discpCode+".xml");
		if(file.exists()){ //xml文件已经存在
		}else{ //临时建立xml
			Document doc = unitService.createDisciplineXML(discpId);
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				writer = new XMLWriter(fos, format);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			try {
				writer.write(doc);
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Document document = reader.read(file);// 读取XML文件
		//直接输出Document
		String content = document.asXML();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml;charset=UTF-8"); // 使用utf-8
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.print(content); // 通过Io流写到页面上去了
		//必须返回空
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public String export() {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			String[] oo = new String[]{
				"教育部人文社会科学重点研究基地一览表",
				"所在高校",
				"基地名称",
				"基地类型",
				"代码",
				"学科门类",
				"学术片",
				"批准时间",
				"批准批次",
				"基地负责人",
				"负责人手机",
				"联系人",
				"联系人手机",
				"通信地址",
				"邮政编码",
				"基地主页"
			};
			
			String hql = "select ag.name, ins.name, sys.code, ins.code, ins.disciplineType, ins.researchArea, " +
				"TO_CHAR(ins.approveDate, 'yyyy'), ins.approveSession, dir.name, dir.mobilePhone, lin.name, lin.mobilePhone, " +
				"ins.address, ins.postcode, ins.homepage from Institute ins left outer join ins.director dir left outer join " +
				"ins.linkman lin left outer join ins.subjection ag left outer join ins.type sys where ";
			
			//导出部属基地和省部共建基地
			hql += "(sys.standard = 'researchAgencyType' and (sys.code = '01' or sys.code = '02')) order by ag.name, ins.name";
			
			List list = dao.query(hql);
			List dispList = new ArrayList();
			for (Object object : list) {
				Object[] o = (Object[]) object;
				String type = ((String)o[2]).trim();
				if(type.equals("01")) {
					o[2] = "部级基地";
				} else if(type.equals("02")){
					o[2] = "省部共建基地";
				}
				dispList.add(o);
			}
			Vector v = new Vector(dispList);
			HSSFExport.commonExportData(oo, v, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String toMerge() {
		List institutes = unitService.getCheckedList(checkedIds, 3);
		String universityId = (String)((Object[]) institutes.get(0))[3];
		for (int i = 1; i < institutes.size(); i++) {
			if(!((String)((Object[]) institutes.get(i))[3]).equals(universityId)){
				this.addFieldError(GlobalInfo.ERROR_INFO, "您选择的研究基地不在一个高校，请重新选择！");
				return INPUT;
			}
		}
		request.setAttribute("list", institutes);
		return SUCCESS;
	}
	
	/**
	 * 合并研究基地
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 * @throws IllegalArgumentException 
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String merge() throws IllegalArgumentException, IntrospectionException, IllegalAccessException, InvocationTargetException {
		InstituteMerger merger = (InstituteMerger) SpringBean.getBean("instituteMerger");

		Serializable targetId = mainId;
		Set<Serializable> incomeIds = new HashSet<Serializable>();
		Matcher matcher = Pattern.compile("\\w+").matcher(checkedIds);
		while (matcher.find()) {
			incomeIds.add(matcher.group());
		}
		
		merger.mergeInstitute(targetId, new ArrayList<Serializable>(incomeIds));
		
		Institute newInstitute = (Institute) dao.query(Institute.class, targetId);
		newInstitute.setName(name);
		newInstitute.setCode(code);
		return SUCCESS;
	}

	public String toList() {
		if(instType == null){//点击返回时session里有listType，直接取
			instType = Integer.parseInt(session.get("instType").toString());
		}else {//点击列表时更新session里的listType
			session.put("instType", instType);
		}
		instTypeName = unitService.getInstituteTypeByCode(instType);
		session.put("instTypeName", instTypeName);
		return super.toList();
	}
	/**
	 * 
	 * @return
	 */
	public String validateMergeInstitute() {
		
		return SUCCESS;
	}
	
	public String[] getColumn() {
		return COLUMN;
	}
	
	public static String getPageName() {
		return PAGE_NAME;
	}

	public String pageName() {
		return InstituteAction.PAGE_NAME;
	}
	public String[] column() {
		return InstituteAction.COLUMN;
	}
	public String dateFormat() {
		return InstituteAction.DATE_FORMAT;
	}
	public String pageBufferId(){
		return InstituteAction.PAGE_BUFFER_ID;
	}

	public IUnitService getUnitService() {
		return unitService;
	}

	public void setUnitService(IUnitService unitService) {
		this.unitService = unitService;
	}

	public Institute getInstitute() {
		return institute;
	}

	public void setInstitute(Institute institute) {
		this.institute = institute;
	}

	@SuppressWarnings("unchecked")
	public List getInstitutes() {
		return institutes;
	}

	@SuppressWarnings("unchecked")
	public void setInstitutes(List institutes) {
		this.institutes = institutes;
	}

	public String[] getDelid() {
		return delid;
	}

	public void setDelid(String[] delid) {
		this.delid = delid;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public int getListLabel() {
		return listLabel;
	}

	public void setListLabel(int listLabel) {
		this.listLabel = listLabel;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getDelids() {
		return delids;
	}

	public void setDelids(String delids) {
		this.delids = delids;
	}

	public int getViewFlag() {
		return viewFlag;
	}

	public void setViewFlag(int viewFlag) {
		this.viewFlag = viewFlag;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getDiscpId() {
		return discpId;
	}

	public void setDiscpId(String discpId) {
		this.discpId = discpId;
	}

	public String getHiddenId() {
		return hiddenId;
	}

	public void setHiddenId(String hiddenId) {
		this.hiddenId = hiddenId;
	}

	public String getHiddenName() {
		return hiddenName;
	}

	public void setHiddenName(String hiddenName) {
		this.hiddenName = hiddenName;
	}

	public String getRDisciplineId() {
		return rDisciplineId;
	}

	public void setRDisciplineId(String disciplineId) {
		rDisciplineId = disciplineId;
	}

	public String getRelyDisciplineId() {
		return relyDisciplineId;
	}

	public void setRelyDisciplineId(String relyDisciplineId) {
		this.relyDisciplineId = relyDisciplineId;
	}

	public String getIName() {
		return iName;
	}

	public void setIName(String name) {
		iName = name;
	}

	public String getICode() {
		return iCode;
	}

	public void setICode(String code) {
		iCode = code;
	}

	public String getIType() {
		return iType;
	}

	public void setIType(String type) {
		iType = type;
	}

	public String getIUniversity() {
		return iUniversity;
	}

	public void setIUniversity(String university) {
		iUniversity = university;
	}

	public String getIDirectorName() {
		return iDirectorName;
	}

	public void setIDirectorName(String iDirectorName) {
		this.iDirectorName = iDirectorName;
	}

	public String getDirectorId() {
		return directorId;
	}

	public void setDirectorId(String directorId) {
		this.directorId = directorId;
	}

	public String getLinkmanId() {
		return linkmanId;
	}

	public void setLinkmanId(String linkmanId) {
		this.linkmanId = linkmanId;
	}

	public String getDirectorName() {
		return directorName;
	}

	public String getLinkmanName() {
		return linkmanName;
	}

	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}

	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}

	public String getSubjectionName() {
		return subjectionName;
	}

	public void setSubjectionName(String subjectionName) {
		this.subjectionName = subjectionName;
	}

	public IProductService getProductService() {
		return productService;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	public IGeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService generalService) {
		this.generalService = generalService;
	}

	public String getCheckedIds() {
		return checkedIds;
	}

	public String getMainId() {
		return mainId;
	}

	public void setCheckedIds(String checkedIds) {
		this.checkedIds = checkedIds;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getProvName() {
		return provName;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public void setCode(String code) {
		this.code = code;
	}
	public InputValidate getInputValidate() {
		return inputValidate;
	}

	public void setInputValidate(InputValidate inputValidate) {
		this.inputValidate = inputValidate;
	}

	public Integer getInstType() {
		return instType;
	}

	public void setInstType(Integer instType) {
		this.instType = instType;
	}

	public String getInstTypeName() {
		return instTypeName;
	}

	public void setInstTypeName(String instTypeName) {
		this.instTypeName = instTypeName;
	}
	
	
}
