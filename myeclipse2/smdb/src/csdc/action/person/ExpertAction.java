package csdc.action.person;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oracle.net.aso.a;
import oracle.net.aso.r;

import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Abroad;
import csdc.bean.Academic;
import csdc.bean.Account;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Education;
import csdc.bean.Expert;
import csdc.bean.Institute;
import csdc.bean.Expert;
import csdc.bean.Officer;
import csdc.bean.Passport;
import csdc.bean.Person;
import csdc.bean.SystemOption;
import csdc.bean.Work;
import csdc.service.IProductService;
import csdc.service.ext.IProjectExtService;
import csdc.tool.ApplicationContainer;
import csdc.tool.FileTool;
import csdc.tool.SpringBean;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.FileRecord;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.PersonInfo;
import csdc.tool.info.RightInfo;
import csdc.tool.merger.ExpertMerger;

@Transactional
public class ExpertAction extends PersonAction {

	private static final long serialVersionUID = 2267030733918211072L;

	private final static String HQL = "select p.name, p.gender, e.agencyName, e.divisionName, e.id, e.position, p.id, p.isKey from Expert e join e.person p ";
	private final static String[] COLUMN = new String[]{
			"p.name",
			"p.gender, p.name",
			"e.agencyName, p.name",
			"e.divisionName, p.name",
			"e.position, p.name",
			"p.isKey desc, p.name"
	};// 排序列
	private final static String PAGE_NAME = "expertPage";
	private final static String[] SEARCH_CONDITIONS = new String[]{
			"LOWER(p.name) like :keyword",
			"LOWER(e.agencyName) like :keyword",
			"LOWER(e.divisionName) like :keyword",
			"LOWER(e.position) like :keyword"
	};
	private static final String PAGE_BUFFER_ID = "e.id";//缓存id

	private Integer idType = 11;//专家人员id类型
	
	private String specialityTitleId;
	private Expert expert;
	private IProductService productService;
	private IProjectExtService projectExtService;

	private List<Expert> incomeExperts;//用于人员合并
	
	public void validateAdd(){
		validateBasicInfo(person, true);
		validateExpert(expert);	
		validateContactInfo(person, true);
	}
	
	/**
	 * 构造专家的基础HQL
	 */
	@Override
	public void simpleSearchBaseHql(StringBuffer hql,Map map) {
		hql.append(HQL());
		if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员或部级账号
			hql.append(" left join p.academic a where 1=1 ");
		} else {
			hql.append(" left join p.academic a where 1=0 ");
		}
	}

	public boolean addInner(){
		expert.setPerson(person);
		dao.add(expert);
		entityId = expert.getId();
		return true;
	}

	public void viewInner(){
		Map paraMap = new HashMap();
		paraMap.put("expertId", entityId);
		List result = dao.query("select expert, person, academic from Expert expert, Person person left join person.academic academic where expert.id = :expertId and expert.person.id = person.id ", paraMap);
		expert = (Expert) ((Object[]) result.get(0))[0];
		person = (Person) ((Object[]) result.get(0))[1];
		academic = (Academic) ((Object[]) result.get(0))[2];
		
		//专家类型处理		
		if(null == academic || academic.getExpertType() == null){
			jsonMap.put("expertType", "");
		}else{
			SystemOption expertType =(SystemOption)dao.query(SystemOption.class,academic.getExpertType().getId());
			jsonMap.put("expertType",expertType.getName());
		}
		
		if(null == academic || academic.getComputerLevel() == null){
			jsonMap.put("computerLevel", "");
		}else{
			SystemOption computerLevel =(SystemOption) dao.query(SystemOption.class,academic.getComputerLevel().getId());
			jsonMap.put("computerLevel", computerLevel.getName());
		}

		paraMap.clear();
		paraMap.put("personId", person.getId());

		result = dao.query("select eb from Education eb where eb.person.id = :personId", paraMap);
		jsonMap.put("person_education", result);

		result = dao.query("select we from Work we where we.person.id = :personId", paraMap);
		jsonMap.put("person_work", result);

		result = dao.query("select ae from Abroad ae where ae.person.id = :personId", paraMap);
		jsonMap.put("person_abroad", result);

		jsonMap.put("expert", expert);
		jsonMap.put("person", person);
		jsonMap.put("academic", academic);
		String[] postdoctorStatus = new String[]{"否","在站","出站"};
		if(null != academic && null != academic.getPostdoctor() && academic.getPostdoctor()>-1 && academic.getPostdoctor()<3){
			jsonMap.put("postdoctor", postdoctorStatus[academic.getPostdoctor()]);
		}else{
			jsonMap.put("postdoctor", "");
		}
		result  = this.productService.getProductListByEntityId(1, person.getId());
		jsonMap.put("person_product", result);
		result = this.projectExtService.getProjectListByEntityId(1, person.getId());
		jsonMap.put("person_project", result);
	}
	
	public void toModifyInner(){
		Map paraMap = new HashMap();
		Map session = ActionContext.getContext().getSession();		
		session.put("expertId", entityId);
		paraMap.put("expertId", entityId);
		List result = dao.query("select expert, person, academic from Expert expert, Person person left join person.academic academic where expert.id = :expertId and expert.person.id = person.id", paraMap);
		paraMap.clear();
		expert = (Expert) ((Object[]) result.get(0))[0];
		person = (Person) ((Object[]) result.get(0))[1];
		academic = (Academic) ((Object[]) result.get(0))[2];
		if (academic!=null && academic.getSpecialityTitle() != null && academic.getSpecialityTitle().contains("/")) {
			paraMap.put("standard", "GBT8561-2001");
			String code = academic.getSpecialityTitle().substring(0, academic.getSpecialityTitle().lastIndexOf("/"));
			paraMap.put("code", code);
			specialityTitleId = (String) dao.query("select s.systemOption.id from SystemOption s where s.standard = :standard and s.code = :code",paraMap).get(0);
		}
	}
	
	public boolean modifyInner(){
		Map session = ActionContext.getContext().getSession();
		entityId = (String) session.get("expertId");
		Expert originExpert = (Expert) dao.query(Expert.class, entityId);
		originPerson = (Person) dao.query(Person.class, originExpert.getPerson().getId());
		
		originExpert.setAgencyName(expert.getAgencyName());
		originExpert.setDivisionName(expert.getDivisionName());
		originExpert.setPosition(expert.getPosition());
		originExpert.setType(expert.getType());		
		dao.modify(originExpert);
		
		academic.setPerson(originPerson);
		List result = dao.query("select academic.id from Academic academic where academic.person.id = ?", originPerson.getId());
		if (!result.isEmpty()){
			String origin_academicId = (String) result.get(0);
			academic.setId(origin_academicId);
			dao.modify(academic);
		}else {
			dao.add(academic);
		}
		return true;
	}
	
	
	/**
	 * 跳转到合并页面
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 * @throws IllegalArgumentException 
	 */
	public String toMerge() throws IllegalArgumentException, IntrospectionException, IllegalAccessException, InvocationTargetException{		
		Map paraMap = new HashMap();
		List result = new ArrayList();
		ExpertMerger expertMerger =(ExpertMerger) SpringBean.getBean("expertMerger");		
		Serializable targetId = entityId;
		List<Serializable> incomeIds = new ArrayList<Serializable>();
		Matcher matcher = Pattern.compile("\\w+").matcher(checkedIds);
		while (matcher.find()) {
			incomeIds.add(matcher.group());
		}
		expertMerger.mergeExpert(targetId, new ArrayList<Serializable>(incomeIds));
		//合并结果返回至前台
		person = expertMerger.getTmpPerson();
		expert = expertMerger.getTmpExpert();	
		paraMap.clear();
		paraMap.put("personId", person.getId());
		academic = expertMerger.getTmpAcademic();	
		specialityTitleId = personService.getSpecialityTitleId(academic);
		paraMap.clear();
		paraMap.put("incomeExpertIds", incomeIds);
		List<Expert> incomeExperts = new ArrayList<Expert>();
		for (int i = 0; i < incomeIds.size(); i++) {
			incomeExperts.add(dao.query(Expert.class,new ArrayList<Serializable>(incomeIds).get(i)));
		}
		ebs = new ArrayList<Education>();
		wes = new ArrayList<Work>();
		aes = new ArrayList<Abroad>();
		for (int i = 0; i < incomeExperts.size(); i++) {
			ebs.addAll(incomeExperts.get(i).getPerson().getEducation());
			wes.addAll(incomeExperts.get(i).getPerson().getWork());
			aes.addAll(incomeExperts.get(i).getPerson().getAbroad());
		}
		

		//将已有的照片加入文件组，以在编辑页面显示
		String groupId = "photo_" + person.getId();
		uploadService.resetGroup(groupId);
		String photoFileRealPath = ApplicationContainer.sc.getRealPath(person.getPhotoFile());
		if (photoFileRealPath != null) {
			uploadService.addFile(groupId, new File(photoFileRealPath));
		}
		for (int i = 1; i < incomeExperts.size(); i++) {		
			String otherPhotoFileRealPath = ApplicationContainer.sc.getRealPath(incomeExperts.get(i).getPerson().getPhotoFile());
			if (otherPhotoFileRealPath != null) {
				uploadService.addFile(groupId, new File(otherPhotoFileRealPath));
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 执行专家合并
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IntrospectionException
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String merge() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, IntrospectionException{
		Set<Serializable> incomeIds = new HashSet<Serializable>();
		Matcher matcher = Pattern.compile("\\w+").matcher(checkedIds);
		while (matcher.find()) {
			incomeIds.add(matcher.group());
		}
		
		Map paraMap = new HashMap();
		Map session = ActionContext.getContext().getSession();
		
		Expert originExpert = (Expert) dao.query(Expert.class, entityId);
		Person originPerson = originExpert.getPerson();
		Academic orignAcademic =originPerson.getAcademic();
		//若姓名有变化，则维护项目成果奖励中的人员姓名冗余信息
		if (!originPerson.getName().trim().equals(person.getName().trim())){
			personService.updatePersonName(originPerson.getId(), person.getName());
		}
		
		originExpert.setPosition(expert.getPosition());
		originExpert.setType(expert.getType());
		originExpert.setDivisionName(expert.getDivisionName());
		originExpert.setAgencyName(expert.getAgencyName());
		//修改学术信息
		if(orignAcademic!=null){
			academic.setId(orignAcademic.getId());
		}
		academic.setPerson(originPerson);
		dao.merge(academic);
		
		//处理照片
		String groupId = "photo_" + originPerson.getId();
		String savePath = (String) ApplicationContainer.sc.getAttribute("UserPictureUploadPath");
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			String newPhotoPath = FileTool.getAvailableFilename(savePath, fileRecord.getOriginal());
			person.setPhotoFile(newPhotoPath);	//设置用户照片的新路径
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(person.getPhotoFile())));	//将照片文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}

		personService.modifyPerson(person, originPerson);
		dao.modify(originExpert);

		ExpertMerger expertMerger =(ExpertMerger) SpringBean.getBean("expertMerger");	
		incomeIds.remove(entityId);
		expertMerger.doMerge(entityId, new ArrayList<Serializable>(incomeIds),selectedAccountId);
		modifyInformation(originPerson);
		uploadService.flush(groupId);
		return SUCCESS;			
	}
	
	/**
	 * 获取合并记录的原始数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String fetchMergeData() {
		incomeExperts = new ArrayList<Expert>();
		List<Person> incomePerson = new ArrayList<Person>();
		List<Academic> incomeAcademic = new ArrayList<Academic>();	
		List<String> computerLevels = new ArrayList<String>();	
		List<String> expertTypes = new ArrayList<String>();	
		List<String> specialityTitleNames = new ArrayList<String>();	
		List<Serializable> incomeIds = new ArrayList<Serializable>();
		
		Matcher matcher = Pattern.compile("\\w+").matcher(checkedIds);
		while (matcher.find()) {
			incomeIds.add(matcher.group());
		}
		
		Map paraMap = new HashMap();
		List result = new ArrayList();
		
		for (int i = 0; i < incomeIds.size(); i++) {
			incomeExperts.add(dao.query(Expert.class, incomeIds.get(i)));
		}
		for (int i = 0; i < incomeExperts.size(); i++) {
			incomePerson.add(incomeExperts.get(i).getPerson());
			if(incomeExperts.get(i).getPerson().getAcademic() != null){
				incomeAcademic.add(incomeExperts.get(i).getPerson().getAcademic());	
				if(incomeExperts.get(i).getPerson().getAcademic().getComputerLevel() != null){
					computerLevels.add(incomeExperts.get(i).getPerson().getAcademic().getComputerLevel().getName());
				}else{
					computerLevels.add("");
				}
				if(incomeExperts.get(i).getPerson().getAcademic().getExpertType() != null){
					expertTypes.add(incomeExperts.get(i).getPerson().getAcademic().getExpertType().getName());
				}else{
					expertTypes.add("");
				}	
				specialityTitleNames.add(personService.getSpecialityTitleName(incomeExperts.get(i).getPerson().getAcademic()));
			}else {
				incomeAcademic.add(new Academic());
				computerLevels.add("");
				expertTypes.add("");
				specialityTitleNames.add("");
			}					
		}
		jsonMap.put("incomeExperts", incomeExperts);
		jsonMap.put("incomePerson", incomePerson);				
		jsonMap.put("incomeAcademics", incomeAcademic);
		jsonMap.put("computerLevels", computerLevels);
		jsonMap.put("expertTypes", expertTypes);
		jsonMap.put("specialityTitleNames", specialityTitleNames);
		return SUCCESS;
	}

	public void validateModify(){
		validateBasicInfo(person, false);
		validateExpert(expert);
		validateContactInfo(person, false);
		validateAcademicInfo(academic);
		validateBankInfo(person, false);
		validateEWA();
	}

	public void validateMerge(){
		validateModify();
	}
	
	@Override
	public String pageName(){
		return PAGE_NAME;
	}
	@Override
	public String[] column(){
		return COLUMN;
	}
	@Override
	public String HQL() {
		return HQL;
	}
	@Override
	public String[] searchConditions() {
		return SEARCH_CONDITIONS;
	}


	public List getAes() {
		return aes;
	}

	public void setAes(List aes) {
		this.aes = aes;
	}

	public Expert getExpert() {
		return expert;
	}

	public void setExpert(Expert expert) {
		this.expert = expert;
	}
	public IProductService getProductService() {
		return productService;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	
	public IProjectExtService getProjectExtService() {
		return projectExtService;
	}

	public void setProjectExtService(IProjectExtService projectExtService) {
		this.projectExtService = projectExtService;
	}

	public String getSpecialityTitleId() {
		return specialityTitleId;
	}

	public void setSpecialityTitleId(String specialityTitleId) {
		this.specialityTitleId = specialityTitleId;
	}

	@Override
	public String pageBufferId() {
		return ExpertAction.PAGE_BUFFER_ID;
	}
	
	@Override
	public Integer idType() {
		return idType;
	}
}
