package csdc.action.mobile.basis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import csdc.action.mobile.MobileAction;
import csdc.bean.AwardGranted;
import csdc.bean.Book;
import csdc.bean.Consultation;
import csdc.bean.Electronic;
import csdc.bean.OtherProduct;
import csdc.bean.Paper;
import csdc.bean.Patent;
import csdc.bean.Product;
import csdc.bean.SystemOption;
import csdc.service.IProductService;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProductInfo;

/**
 * mobile成果模块
 * @author fengcl
 */
public class MobileProductAction extends MobileAction{

	private static final long serialVersionUID = 1L;
	private static final String PAGENAME = "mobileProductPage";

	private static String HQL4PAPER = "select p.chineseName, p.authorName,uni.name, p.id from Paper p left join p.university uni left join p.department dep " +
			"left join p.institute ins left join p.type s left join p.author aut where 1 = 1";
	private static String HQL4BOOK = "select p.chineseName, p.authorName,uni.name, p.id from Book p left join p.university uni left join p.department dep " +
			"left join p.institute ins left join p.type s left join p.author aut where 1 = 1";
	private static String HQL4CONSULTATION = "select p.chineseName, p.authorName,uni.name, p.id from Consultation p left join p.university uni left join p.department dep " +
			"left join p.institute ins left join p.author aut where 1 = 1";
	private static String HQL4ELECTRONIC = "select p.chineseName, p.authorName,uni.name, p.id from Electronic p left join p.university uni left join p.department dep " +
			"left join p.institute ins left join p.type s left join p.author aut where 1 = 1";
	private static String HQL4PATENT = "select p.chineseName, p.authorName,uni.name, p.id from Patent p left join p.university uni left join p.department dep " +
			"left join p.institute ins left join p.author aut where 1 = 1";
	private static String HQL4OTHERPRODUCT = "select p.chineseName, p.authorName,uni.name, p.id from OtherProduct p left join p.university uni left join p.department dep " +
			"left join p.institute ins left join p.author aut where 1 = 1";
	
	private String productType;//1:论文;2:著作;3:研究咨询报告;4:电子出版物;5:专利;6:其他成果;
	private IProductService productService;
	private Paper paper;//论文对象
	private Book book;//著作对象
	private Consultation consultation;//研究咨询报告成果对象
	private Electronic electronic;//电子出版物对象
	private Patent patent;//专利对象
	private OtherProduct otherProduct;//其他成果对象
	
	private String prodName;//高级检索成果名称（论文、著作、报告）
	private String authorName;//高级检索第一作者（论文、著作、报告）
	private String agencyName;//高级检索所属单位（论文、著作、报告）
	private String divisionName;//高级检索所属部门（论文、著作、报告）
	private String publication ;//高级检索发表刊物/出版单位/采纳单位（论文、著作、报告）
	private String prodType;//高级检索成果类型（论文类型、书籍类型）
	
	//隐藏类初始化法
	//MOESOCIALITEMS：当前成果列表
	private static final ArrayList PRODUCTITEMS = new ArrayList();
	static{
		PRODUCTITEMS.add("论文#1");
		PRODUCTITEMS.add("著作#2");
		PRODUCTITEMS.add("研究咨询报告#3");
		PRODUCTITEMS.add("电子出版物#4");
		PRODUCTITEMS.add("专利#5");
		PRODUCTITEMS.add("其他成果#6");
	}
	
	/**
	 * 客户端主列表条目
	 * @return
	 */
	public String fetchMenu(){
//		AccountType accountType = loginer.getCurrentType();
		Map mainItems = new LinkedHashMap();
		List productItems = null;
		productItems = PRODUCTITEMS;
		mainItems.put("Product", productItems);
		jsonMap.put("listItem", mainItems);
		return SUCCESS;
	}
	
	
	public String simpleSearch(){
//		Cookie[] cookies = request.getCookies();
//		for(Cookie c : cookies){
//			if("JSESSIONID".equals(c.getName())){
//				String sessionId = c.getValue();
//				System.out.println(sessionId);
//			}
//		}
		StringBuffer hql = new StringBuffer();
		HashMap parMap = new HashMap();
		if("Product".equals(productType)){
			switch (listType) {
			case 1://论文
				hql.append(HQL4PAPER);
				break;
			case 2://著作
				hql.append(HQL4BOOK);
				break;
			case 3://研究咨询报告
				hql.append(HQL4CONSULTATION);
				break;
			case 4://电子出版物
				hql.append(HQL4ELECTRONIC);
				break;
			case 5://专利
				hql.append(HQL4PATENT);
				break;
			case 6://其他成果
				hql.append(HQL4OTHERPRODUCT);
				break;
			}	
		}
		keyword = (keyword == null) ? "" : keyword.toLowerCase();
		if(!keyword.isEmpty()){
			parMap.put("keyword", "%" + keyword + "%");
			hql.append(" and (LOWER(p.chineseName) like :keyword or LOWER(p.authorName) like :keyword or LOWER(uni.name) like :keyword)");
		}
		//添加范围控制信息
		String scopeHql = productService.getScopeHql(loginer.getAccount(), parMap);
		hql.append(scopeHql);
		hql.append(" order by p.chineseName asc");//默认按照成果名称排序
		search(hql, parMap);
		return SUCCESS;
	}
	
	public String advSearch(){
		StringBuffer hql = new StringBuffer();
		HashMap parMap = new HashMap();
		if("Product".equals(productType)){
			switch(listType){
			case 1://论文
				hql.append(HQL4PAPER);
				if(null != publication && !publication.isEmpty()){
					parMap.put("publication", "%"+ publication +"%");
					hql.append(" and LOWER(p.publication) like :publication");
				}//发表刊物
				break;
			case 2://著作
				hql.append(HQL4BOOK);
				if(null != publication && !publication.isEmpty()){
					parMap.put("publication", "%"+ publication +"%");
					hql.append(" and LOWER(p.publishUnit) like :publication");
				}//出版单位
				break;
			case 3:////研究咨询报告
				hql.append(HQL4CONSULTATION);
				if(null != publication && !publication.isEmpty()){
					parMap.put("publication", "%"+ publication +"%");
					hql.append(" and LOWER(p.useUnit) like :publication");
				}//采纳单位
				break;
			case 4://电子出版物
				hql.append(HQL4ELECTRONIC);
				if(null != publication && !publication.isEmpty()){
					parMap.put("publication", "%" + publication + "%");
					hql.append(" and LOWER(p.publishUnit) like :publication");
				}//出版单位
				break;
			case 5://专利
				hql.append(HQL4PATENT);
				if(null != publication && !publication.isEmpty()){
					parMap.put("inventorName", "%" + publication + "%");
					hql.append(" and LOWER(p.inventorName) like :inventorName");
				}//发明者
				break;
			case 6://其他成果
				hql.append(HQL4OTHERPRODUCT);
				if(null != publication && !publication.isEmpty()){
					parMap.put("publication", "%" + publication + "%");
					hql.append(" and LOWER(p.publishUnit) like :publication");
				}//出版单位
				break;
			}			
		}
		//公共部分条件
		if(null != prodName && !prodName.isEmpty()){
			parMap.put("chineseName", "%"+ prodName +"%");
			hql.append(" and LOWER(p.chineseName) like :chineseName ");
		}//成果名称
		if(null != authorName && !authorName.isEmpty()){
			parMap.put("authorName", "%"+ authorName +"%");
			hql.append(" and LOWER(p.authorName) like :authorName");
		}//第一作者
		if(null != agencyName && !agencyName.isEmpty()){
			parMap.put("agencyName", "%"+ agencyName +"%");
			hql.append(" and LOWER(p.agencyName) like :agencyName");
		}//所属单位
		if(null != divisionName && !divisionName.isEmpty()){
			parMap.put("divisionName", "%"+ divisionName +"%");
			hql.append(" and LOWER(p.divisionName) like :divisionName");
		}//所属部门
		
		//---根据账号和参数map得到判定范围的hql和新的参数map
		String scopeHql = productService.getScopeHql(loginer.getAccount(), parMap);
		hql.append(scopeHql);
		hql.append(" order by p.chineseName asc");//默认按照机构名称排序
		search(hql, parMap);
		return SUCCESS;
	}
	
	@Transactional
	public String view(){
		Map dataMap = new HashMap(); 
		if("Product".equalsIgnoreCase(productType)){
			switch(listType){
			case 1://论文
				paper = (Paper) dao.query(Paper.class, entityId);
				if (null != paper) {
					//[基本信息 9]:论文名称,第一作者,其它作者,所属单位,所属部门,论文形态,论文类型,学科门类,学科代码
					dataMap.put("productName", (null != paper.getChineseName()) ? paper.getChineseName() : "");//论文名称
					dataMap.put("authorName", (null != paper.getAuthorName()) ? paper.getAuthorName() : "");//第一作者名称
					dataMap.put("otherAuthorName", (null != paper.getOtherAuthorName()) ? paper.getOtherAuthorName() : "");//其它作者名称
					dataMap.put("agencyName", (null != paper.getAgencyName()) ? paper.getAgencyName() : "");//所属单位，第一作者单位信息
					dataMap.put("divisionName", (null != paper.getDivisionName()) ? paper.getDivisionName() : "");//所属部门，第一作者部门信息
					dataMap.put("isTranslation", (paper.getIsTranslation() != null && paper.getIsTranslation() == 1) ? "是" : "否");//是否译文
					dataMap.put("isForeignCoop", (paper.getIsForeignCooperation() == 1) ? "是" : "否");//是否与国（境）外合作
					dataMap.put("form", (null != paper.getForm()) ? paper.getForm().getName() : "");//论文形态
					dataMap.put("paperType", (null != paper.getType()) ? paper.getType().getName() : "");//论文类型
					dataMap.put("disciplineType", (null != paper.getDisciplineType())? paper.getDisciplineType() : "");//学科门类
					dataMap.put("discipline", (null != paper.getDiscipline()) ? paper.getDiscipline() : "");//学科代码
					//[发表信息  8]:发表刊物,发表时间,卷号,期号,起止页码,CN号,ISSN号
					dataMap.put("publication", (null != paper.getPublication()) ? paper.getPublication() : "");//发表刊物
					dataMap.put("publicationDate", (null != paper.getPublicationDate()) ? new SimpleDateFormat("yyyy-MM-dd").format(paper.getPublicationDate()) : "");//发表时间
					dataMap.put("paperVolume", (null != paper.getVolume()) ? paper.getVolume().toString() : "");//卷号
					dataMap.put("paperNumber", (null != paper.getNumber()) ? paper.getNumber().toString() : "");//期号
					dataMap.put("paperPage", (null != paper.getPage()) ? paper.getPage() : "");//起止页码
					dataMap.put("paperCn", (null != paper.getCn()) ? paper.getCn() : "");//CN号
					dataMap.put("paperIssn", (null != paper.getIssn()) ? paper.getIssn() : "");//ISSN号
				}
				break;
			case 2://著作
				book = (Book) dao.query(Book.class, entityId);
				//[基本信息 9]:书籍名称,第一作者,其它作者,所属单位,所属部门,形态,书籍类型,学科门类,学科代码
				dataMap.put("productName", (null != book.getChineseName()) ? book.getChineseName() : "");//书籍名称
				dataMap.put("authorName", (null != book.getAuthorName()) ? book.getAuthorName() : "");//第一作者名称
				dataMap.put("otherAuthorName", (null != book.getOtherAuthorName()) ? book.getOtherAuthorName() : "");//其它作者名称
				dataMap.put("agencyName", (null != book.getAgencyName()) ? book.getAgencyName() : "");//所属单位，第一作者单位信息
				dataMap.put("divisionName", (null != book.getDivisionName()) ? book.getDivisionName() : "");//所属部门，第一作者部门信息
				dataMap.put("isTranslation", (null != book.getIsTranslation() && book.getIsTranslation() == 1) ? "是" : "否");//是否译文
				dataMap.put("isForeignCoop", (book.getIsForeignCooperation() == 1) ? "是" : "否");//是否与国（境）外合作
				dataMap.put("form", (null !=  book.getForm()) ? book.getForm().getName() : "");//著作形态
				dataMap.put("bookType", (null != book.getType()) ? book.getType().getName() : "");//著作类型
				dataMap.put("disciplineType", (null != book.getDisciplineType()) ? book.getDisciplineType() : "");//学科门类
				dataMap.put("discipline", (null != book.getDiscipline()) ? book.getDiscipline() : "");//学科代码
				//[出版信息 2]:出版单位,出版时间
				dataMap.put("publishUnit", (null != book.getPublishUnit()) ? book.getPublishUnit() : "");//出版单位
				dataMap.put("publishDate", (null != book.getPublishDate()) ? new SimpleDateFormat("yyyy-MM-dd").format(book.getPublishDate()) : "");//出版时间
				break;
			case 3://研究咨询报告
				consultation = (Consultation)dao.query(Consultation.class, entityId);
				//[基本信息 9]:报告名称,第一作者,其它作者,所属单位,所属部门,形态，学科门类,学科代码
				dataMap.put("productName", (null != consultation.getChineseName()) ? consultation.getChineseName() : "");//报告名称
				dataMap.put("authorName", (null != consultation.getAuthorName()) ? consultation.getAuthorName() : "");//第一作者名称
				dataMap.put("otherAuthorName", (null != consultation.getOtherAuthorName()) ? consultation.getOtherAuthorName() : "");//其它作者名称
				dataMap.put("agencyName", (null != consultation.getAgencyName()) ? consultation.getAgencyName() : "");//所属单位，第一作者单位信息
				dataMap.put("divisionName", (null != consultation.getDivisionName()) ? consultation.getDivisionName() : "");//所属部门，第一作者部门信息
				dataMap.put("isTranslation", (null != consultation.getIsTranslation() && consultation.getIsTranslation() == 1) ? "是" : "否");//是否译文
				dataMap.put("isForeignCoop", (consultation.getIsForeignCooperation() == 1)?"是":"否");//是否与国（境）外合作
				dataMap.put("form", (null != consultation.getForm()) ? consultation.getForm().getName() : "");//形态
				dataMap.put("disciplineType", (null != consultation.getDisciplineType()) ? consultation.getDisciplineType() : "");//学科门类
				dataMap.put("discipline", (null != consultation.getDiscipline()) ? consultation.getDiscipline() : "");//学科代码
				//[采纳信息 2]:采纳单位,发表时间
				dataMap.put("useUnit", (null != consultation.getUseUnit()) ? consultation.getUseUnit() : "");//采纳单位
				dataMap.put("publicationDate", (null != consultation.getPublicationDate()) ?  new SimpleDateFormat("yyyy-MM-dd").format(consultation.getPublicationDate()) : "");//发表时间
				break;
			case 4://电子出版物
				//成果基本信息
				electronic = (Electronic)dao.query(Electronic.class, entityId);
				if (null != electronic) {
					//[基本信息 9]:电子出版物名称,第一作者,其它作者,所属单位,所属部门,是否译文,形态，学科门类,学科代码
					dataMap.put("productName", (null != electronic.getChineseName()) ? electronic.getChineseName() : "");
					dataMap.put("authorName", (null != electronic.getAuthorName()) ? electronic.getAuthorName() : "");
					dataMap.put("otherAuthorName", (null != electronic.getOtherAuthorName()) ? electronic.getOtherAuthorName() : "");
					dataMap.put("agencyName", (null != electronic.getAgencyName()) ? electronic.getAgencyName() : "");
					dataMap.put("divisionName", (null != electronic.getDivisionName()) ? electronic.getDivisionName() : "");
					dataMap.put("isTranslation", (null != electronic.getIsTranslation() && electronic.getIsTranslation() == 1) ? "是" : "否");//是否为翻译电子出版物
					dataMap.put("form", (null != electronic.getForm()) ? electronic.getForm().getName() : "");
					dataMap.put("disciplineType", (null != electronic.getDisciplineType()) ? electronic.getDisciplineType() : "");
					dataMap.put("discipline", (null != electronic.getDiscipline()) ? electronic.getDiscipline() : "");
					//[出版信息 2]:出版单位,出版时间
					dataMap.put("publishUnit", (null != electronic.getPublishUnit()) ? electronic.getPublishUnit() : "");//出版单位
					dataMap.put("publishDate", (null != electronic.getPublishDate()) ? new SimpleDateFormat("yyyy-MM-dd").format(electronic.getPublishDate()) : "");//出版时间
				}
				break;
			case 5://专利
				//成果基本信息
				patent = (Patent)dao.query(Patent.class, entityId);
				if(null != patent){
					//[基本信息 9]:专利名称,第一作者,其它作者,所属单位,所属部门,形态，学科门类,学科代码
					dataMap.put("productName", (null != patent.getChineseName()) ? patent.getChineseName() : "");
					dataMap.put("authorName", (null != patent.getAuthorName()) ? patent.getAuthorName() : "");
					dataMap.put("otherAuthorName", (null != patent.getOtherAuthorName()) ? patent.getOtherAuthorName() : "");
					dataMap.put("agencyName", (null != patent.getAgencyName()) ? patent.getAgencyName() : "");
					dataMap.put("divisionName", (null != patent.getDivisionName()) ? patent.getDivisionName() : "");
					dataMap.put("form", (null != patent.getForm()) ? patent.getForm().getName() : "");
					dataMap.put("disciplineType", (null != patent.getDisciplineType()) ? patent.getDisciplineType() : "");
					dataMap.put("discipline", (null != patent.getDiscipline()) ? patent.getDiscipline() : "");
					//[专利信息 2]:专利发明人,申请号,申请日,公开号,公开日
					dataMap.put("inventorName", (null != patent.getInventorName()) ? patent.getInventorName() : "");
					dataMap.put("applicationNumber", (null != patent.getApplicationNumber()) ? patent.getApplicationNumber() : "");//申请号
					dataMap.put("applicationDate", (null != patent.getApplicationDate()) ? new SimpleDateFormat("yyyy-MM-dd").format(patent.getApplicationDate()) : "");//申请日
					dataMap.put("publicNumber", (null != patent.getPublicNumber()) ? patent.getPublicNumber() : "");//公开号
					dataMap.put("publicDate", (null != patent.getPublicDate()) ? new SimpleDateFormat("yyyy-MM-dd").format(patent.getPublicDate()) : "");//公开日
				}
				break;
			case 6://其他成果
				//成果基本信息
				otherProduct = (OtherProduct)dao.query(OtherProduct.class, entityId);
				if (null != otherProduct) {
					//[基本信息 9]:成果名称,第一作者,其它作者,所属单位,所属部门,形态，学科门类,学科代码
					dataMap.put("productName", (null != otherProduct.getChineseName()) ? otherProduct.getChineseName() : "");
					dataMap.put("authorName", (null != otherProduct.getAuthorName()) ? otherProduct.getAuthorName() : "");
					dataMap.put("otherAuthorName", (null != otherProduct.getOtherAuthorName()) ? otherProduct.getOtherAuthorName() : "");
					dataMap.put("agencyName", (null != otherProduct.getAgencyName()) ? otherProduct.getAgencyName() : "");
					dataMap.put("divisionName", (null != otherProduct.getDivisionName()) ? otherProduct.getDivisionName() : "");
					dataMap.put("form", (null != otherProduct.getForm()) ? otherProduct.getForm().getName() : "");
					dataMap.put("disciplineType", (null != otherProduct.getDisciplineType()) ? otherProduct.getDisciplineType() : "");
					dataMap.put("discipline", (null != otherProduct.getDiscipline()) ? otherProduct.getDiscipline() : "");
					//[出版信息 2]:出版单位,发表时间
					dataMap.put("publishUnit", (null != otherProduct.getPublishUnit()) ? otherProduct.getPublishUnit() : "");//出版单位
					dataMap.put("pressDate", (null != otherProduct.getPressDate()) ? new SimpleDateFormat("yyyy-MM-dd").format(otherProduct.getPressDate()) : "");//发表时间
				}
				break;
			}			
		}
		//项目相关信息
		//[资助项目 2]:所属项目,项目类型
		List<Map> relProjectInfos = productService.getRelProjectInfos(entityId);
		if(relProjectInfos.size() > 0){//是项目成果
			Map projectMap = relProjectInfos.get(0);
			String projectName = (String) projectMap.get("projectName");
			String projectTypeName = (String) projectMap.get("projectTypeName");
			dataMap.put("projectName", projectName);//所属项目
			dataMap.put("projectType", projectTypeName );//项目类型
		}else{//非项目成果
			dataMap.put("projectName", "[该成果无资助项目]");//所属项目
			dataMap.put("projectType", "");//项目类型
		}
		//奖励相关信息
		//[奖励信息 6]:证书编号,奖励等级,获奖届次,获奖年度,获奖时间
		AwardGranted award = (AwardGranted) productService.getAward(entityId);//查询获奖信息
		Integer isAwarded = (null == award) ? 1 : 2;//1:未获奖  2:已获奖 
		jsonMap.put("isAwarded", isAwarded);
		if(isAwarded == 2){//已获奖
			String awardNumber = (null != award.getNumber()) ? award.getNumber() : "";
			dataMap.put("awardNumber", awardNumber);//证书编号
			SystemOption awardGrade=(SystemOption)dao.query(SystemOption.class, award.getGrade().getId());
			String awardGradeName = (null != awardGrade.getName()) ? awardGrade.getName() : "";
			dataMap.put("awardGrade", awardGradeName);//奖励等级
			String awardSession = (award.getSession() == 0)?"":String.valueOf(award.getSession());
			dataMap.put("awardSession", awardSession);//获奖届次
			String awardYear = (award.getYear() == 0)?"":String.valueOf(award.getYear());
			dataMap.put("awardYear", awardYear);//获奖年度
			String awardDate = (null != award.getDate()) ? new SimpleDateFormat("yyyy-MM-dd").format(award.getDate()) : "";
			dataMap.put("awardDate", awardDate);//获奖时间
		} else{//未获奖
			dataMap.put("awardNumber", "[尚未获奖]");//证书编号
			dataMap.put("awardGrade", "");//奖励等级
			dataMap.put("awardSession", "");//获奖届次
			dataMap.put("awardYear", "");//获奖年度
			dataMap.put("awardDate", "");//获奖时间
		}
		
		jsonMap.put("laData", dataMap);
		return SUCCESS;
	}
	
	/**
	 * 查看校验
	 */
	public void validateView() {
		if (null == entityId || entityId.trim().isEmpty()) {//判断实体id是否为空
			this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_VIEW_NULL);
			jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_VIEW_NULL);
		} else {
			Product product = (Product) dao.query(Product.class, entityId);
			if(null == product) {// 判断成果是否存在
				this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_PRODUCT_NULL);
				jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_PRODUCT_NULL);
			} else {//判断是否在查看范围内
				if(!this.productService.checkIfUnderControl(loginer, entityId.trim(), 15, true)){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_NOT_IN_SCOPE);
					jsonMap.put(GlobalInfo.ERROR_INFO, ProductInfo.ERROR_NOT_IN_SCOPE);
				}
			}
		}
	}
	
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	public IProductService getProductService() {
		return productService;
	}
	public Paper getPaper() {
		return paper;
	}
	public void setPaper(Paper paper) {
		this.paper = paper;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public Consultation getConsultation() {
		return consultation;
	}
	public void setConsultation(Consultation consultation) {
		this.consultation = consultation;
	}

	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	public String getPublication() {
		return publication;
	}
	public void setPublication(String publication) {
		this.publication = publication;
	}
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	@Override
	public String pageName() {
		return PAGENAME;
	}
}
