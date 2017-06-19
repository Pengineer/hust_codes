package csdc.tool.tableKit.imp;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import csdc.bean.Agency;
import csdc.bean.AwardGranted;
import csdc.bean.AwardApplication;
import csdc.bean.Book;
import csdc.bean.Consultation;
import csdc.bean.Electronic;
import csdc.bean.Paper;
import csdc.bean.SystemOption;
import csdc.service.IBaseService;
import csdc.tool.tableKit.ITableKit;
import csdc.tool.validator.IValidator;
import csdc.tool.validator.imp.DummyValidator;


import jxl.Sheet;
import jxl.Workbook;

/**
 * 奖励导入_工具包
 * 20110115_中国高校人文社会科学优秀成果奖一览表（第1-5届）_修正导入.xls
 * @author 徐涵
 *
 */
@SuppressWarnings("unchecked")
public class AwardKit extends HibernateDaoSupport implements ITableKit{
	/**
	 * 用于在页面显示list
	 */
	private List<List<String> > list;

	/**
	 * 用于导入到数据库
	 */
	private ArrayList<Object> productList = new ArrayList();
	private ArrayList<AwardGranted> awardList = new ArrayList<AwardGranted>();
	private ArrayList<AwardApplication> awardApplicationList = new ArrayList<AwardApplication>();
	static private IBaseService baseService;

	static private List<String> stdTitle = new ArrayList<String>();
	static {
		stdTitle.add("序号");
		stdTitle.add("高校名称");
		stdTitle.add("申请人");
		stdTitle.add("成果名称");
		stdTitle.add("成果形式");
		stdTitle.add("学科名称");
		stdTitle.add("获奖等级");
		stdTitle.add("出版情况");
		stdTitle.add("出版时间");
		stdTitle.add("获奖届次");
		stdTitle.add("得分");
	}

	static private List<IValidator> vlist = new ArrayList<IValidator>();
	static {
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
		vlist.add(new DummyValidator());
	}

////////////////////////////////

///////////////////////////////////////

	private HashMap<String, Agency> universityMap;
	private HashMap<String, SystemOption> gradeMap;
	private HashMap<String, SystemOption> productTypeMap;
	private HashMap<String, Object> awardApplicationProductMap;

	/**
	 * 人文社科奖
	 */
	private SystemOption rwskj;

	/**
	 * 计算机或音像软件
	 */
	private SystemOption jsjhyxrj;

	private void init() {
		universityMap = new HashMap<String, Agency>();
		List<Agency> list1 = baseService.query("select a from Agency a");
		for (Agency agency : list1) {
			universityMap.put(agency.getName(), agency);
		}

		gradeMap = new HashMap<String, SystemOption>();
		List<SystemOption> list2 = baseService.query("select so from SystemOption so where so.code like '%hjdj%' ");
		for (SystemOption so : list2) {
			gradeMap.put(so.getName(), so);
		}

		productTypeMap = new HashMap<String, SystemOption>();
		List<SystemOption> list3 = baseService.query("select so from SystemOption so where so.code like '%cglx%' ");
		for (SystemOption so : list3) {
			productTypeMap.put(so.getName(), so);
		}

		list3 = baseService.query("select so from SystemOption so where so.code is 'jllx01' ");
		rwskj = list3.get(0);

		list3 = baseService.query("select so from SystemOption so where so.code is 'dzcbwlb01' ");
		jsjhyxrj = list3.get(0);

		awardApplicationProductMap = new HashMap<String, Object>();
	}


	@SuppressWarnings("deprecation")
	private void getList(File xls) throws Exception{
		Sheet rs = Workbook.getWorkbook(new FileInputStream(xls)).getSheet(0);
		list = new ArrayList<List<String> >();
		for (int i = 0; i < rs.getRows(); i++) {
			List row = new ArrayList<String>();
			for (int j = 0; j < stdTitle.size(); j++) {
				row.add(rs.getCell(j, i).getContents().trim());
			}
			list.add(row);
		}

		Book book;
		Paper paper;
		Consultation consultation;
		AwardGranted award;
		Electronic electronic;
		AwardApplication awardApplication;
		Agency agency;
		HashSet<String> nullUnivName = new HashSet<String>();
		Integer years[] = new Integer[]{0, 1995, 1998, 2003, 2006, 2009};

		init();

		for (int i = 1; i < list.size(); i++){
			String type = list.get(i).get(4);
			award = new AwardGranted();
			awardApplication = new AwardApplication();

			awardList.add(award);
			awardApplicationList.add(awardApplication);

			agency = universityMap.get(list.get(i).get(1));
			if (agency == null){
				nullUnivName.add(list.get(i).get(1));
			}

			awardApplication.setUniversity(agency);
			awardApplication.setApplicantName(list.get(i).get(2));
			awardApplication.setProductType(productTypeMap.get(type));
			awardApplication.setAwardType(rwskj);
			awardApplication.setProductName(list.get(i).get(3));
			awardApplication.setAgencyName(list.get(i).get(1));
			awardApplication.setSession(calcSession(list.get(i).get(9)));
			awardApplication.setApplicantSubmitStatus(3);
//			awardApplication.setIsAwarded(2);
			awardApplication.setDeptInstAuditStatus(3);
			awardApplication.setDeptInstAuditResult(2);
			awardApplication.setUniversityAuditStatus(3);
			awardApplication.setUniversityAuditResult(2);
			if (agency != null && agency.getType() == 4){
				awardApplication.setProvinceAuditStatus(3);
				awardApplication.setProvinceAuditResult(2);
			}
			awardApplication.setMinistryAuditStatus(3);
			awardApplication.setMinistryAuditResult(2);
			awardApplication.setDisciplineType(list.get(i).get(5));
			awardApplication.setStatus(9);
			awardApplication.setReviewStatus(3);
			awardApplication.setReviewResult(2);
			awardApplication.setReviewAuditStatus(3);
			awardApplication.setReviewAuditResult(2);
			awardApplication.setFinalAuditStatus(3);
			awardApplication.setFinalAuditResult(2);
			awardApplication.setYear(years[awardApplication.getSession()]);

			award.setSession(calcSession(list.get(i).get(9)));
			award.setYear(years[award.getSession()]);
			award.setGrade(gradeMap.get(list.get(i).get(6)));

			award.setApplication(awardApplication);

			if ("著作".equals(type)){
				book = new Book();
				book.setAgencyName(list.get(i).get(1));
				book.setChineseName(list.get(i).get(3));
				book.setDisciplineType(list.get(i).get(5));
				book.setAuthorName(list.get(i).get(2));
				try {
					book.setPublishDate(new Date(Integer.parseInt(list.get(i).get(8)) - 1900, 0, 1));
				} catch (Exception e) {
					e.printStackTrace();
				}
				book.setPublishUnit(list.get(i).get(7));
				book.setIsApproved(2);
				book.setDeptInstAuditResult(2);
				book.setDeptInstAuditStatus(3);
				book.setUniversity(agency);
				awardApplicationProductMap.put(awardApplication.getProductName(), book);
				productList.add(book);
			} else if ("研究咨询报告".equals(type)){
				consultation = new Consultation();
				consultation.setAgencyName(list.get(i).get(1));
				consultation.setChineseName(list.get(i).get(3));
				consultation.setDisciplineType(list.get(i).get(5));
				consultation.setAuthorName(list.get(i).get(2));
				try {
					consultation.setPublicationDate(new Date(Integer.parseInt(list.get(i).get(8)) - 1900, 0, 1));
				} catch (Exception e) {
					e.printStackTrace();
				}
				consultation.setUseUnit(list.get(i).get(7));
				consultation.setIsApproved(2);
				consultation.setDeptInstAuditResult(2);
				consultation.setDeptInstAuditStatus(3);
				consultation.setUniversity(agency);
				awardApplicationProductMap.put(awardApplication.getProductName(), consultation);
				productList.add(consultation);
			} else if ("论文".equals(type)){
				paper = new Paper();
				paper.setAgencyName(list.get(i).get(1));
				paper.setChineseName(list.get(i).get(3));
				paper.setDisciplineType(list.get(i).get(5));
				paper.setAuthorName(list.get(i).get(2));
				try {
					paper.setPublicationDate(new Date(Integer.parseInt(list.get(i).get(8)) - 1900, 0, 1));
				} catch (Exception e) {
					e.printStackTrace();
				}
				paper.setPublication(list.get(i).get(7));
				paper.setIsApproved(2);
				paper.setDeptInstAuditResult(2);
				paper.setDeptInstAuditStatus(3);
				paper.setUniversity(agency);
				awardApplicationProductMap.put(awardApplication.getProductName(), paper);
				productList.add(paper);
			} else if ("电子出版物".equals(type)){
				electronic = new Electronic();
				electronic.setAgencyName(list.get(i).get(1));
				electronic.setChineseName(list.get(i).get(3));
				electronic.setDtype(list.get(i).get(5));
				electronic.setAuthorName(list.get(i).get(2));
				try {
					electronic.setPublicationDate(new Date(Integer.parseInt(list.get(i).get(8)) - 1900, 0, 1));
				} catch (Exception e) {
					e.printStackTrace();
				}
				electronic.setPublicationUnit(list.get(i).get(7));
				electronic.setIsApproved(2);
				electronic.setDeptInstAuditResult(2);
				electronic.setDeptInstAuditStatus(3);
				electronic.setUniversity(agency);
				electronic.setType(jsjhyxrj);
				awardApplicationProductMap.put(awardApplication.getProductName(), electronic);
				productList.add(electronic);
			}
		}
		System.out.println("nullUnivName:");
		for (Iterator iterator = nullUnivName.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			System.out.println(string);
		}
		if (!nullUnivName.isEmpty()){
//			throw new Exception();
		}
	}

	private Integer calcSession(String st) throws Exception {
		if ("第一届".equals(st)){
			return 1;
		} else if ("第二届".equals(st)){
			return 2;
		} else if ("第三届".equals(st)){
			return 3;
		} else if ("第四届".equals(st)){
			return 4;
		} else if ("第五届".equals(st)){
			return 5;
		} else {
			throw new Exception();
		}
	}

	public StringBuffer validate(File xls) throws Exception{
		StringBuffer tmpMsg, errorMsg = new StringBuffer();
		try {
			getList(xls);
		} catch (Exception e) {
			e.printStackTrace();
			errorMsg.append("文件格式错误,请确认和模板格式一致!");
			return errorMsg;
		}
		System.out.println("文件格式正确!!");

		for (int i = 0; i < stdTitle.size(); i++){
			if (!list.get(0).get(i).equals(stdTitle.get(i))){
				errorMsg.append("标题第" + (i + 1) + "列应为" + stdTitle.get(i) + "<br />");
			}
		}

		for (int k = 1; k < list.size(); k++) {
			for (int i = 0; i < stdTitle.size(); i++){
				tmpMsg = vlist.get(i).validate(list.get(k).get(i));
				if (tmpMsg != null && tmpMsg.length() > 0){
					errorMsg.append("<tr><td>第" + (k + 1) + "行</td><td>第 " + (i + 1) + "列</td><td>&nbsp;&nbsp;&nbsp;" + tmpMsg + "</td></tr>");
				}
			}
		}
		System.out.println(errorMsg);
		return errorMsg;
	}

	public List display(File xls) throws Exception {
		return list;
	}

	public void imprt() throws Exception {

		Session session = super.getSession();
		Transaction tx = session.beginTransaction();
		tx.begin();
		try {
			for (Object product : productList){
				session.save(product);
			}
			for (AwardApplication awardApplication : awardApplicationList){
				Object product = awardApplicationProductMap.get(awardApplication.getProductName());
				String productId = null;
				if (product instanceof Book){
					productId = ((Book)product).getId();
				} else if (product instanceof Paper){
					productId = ((Paper)product).getId();
				} else if (product instanceof Consultation){
					productId = ((Consultation)product).getId();
				} else if (product instanceof Electronic){
					productId = ((Electronic)product).getId();
				} else {
					throw new Exception();
				}
				awardApplication.setProductId(productId);
			}
			for (AwardApplication awardApplication : awardApplicationList){
				session.save(awardApplication);
			}
			for (AwardGranted award : awardList){
				session.save(award);
			}
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		super.releaseSession(session);
	}

	public void exprtTemplate() throws Exception {
	}


	static public void setBaseService(IBaseService baseService) {
		AwardKit.baseService = baseService;
	}




}
