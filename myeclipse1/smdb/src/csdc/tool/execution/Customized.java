package csdc.tool.execution;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.Person;
import csdc.bean.Product;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.dao.IHibernateBaseDao;
import csdc.dao.JdbcDao;
import csdc.dao.SystemOptionDao;
import csdc.tool.ApplicationContainer;
import csdc.tool.SpringBean;
import csdc.tool.beanutil.AllBeans;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.fix.FixProductType;
import csdc.tool.execution.importer.Data2010oldMDBImporter;
import csdc.tool.execution.importer.GeneralProject1993_2010MidLateImporter;
import csdc.tool.execution.importer.GeneralProject2005_2011EndImporter;
import csdc.tool.execution.importer.GeneralProjectApplication2005Importer;
import csdc.tool.execution.importer.GeneralProjectApplication2006Importer;
import csdc.tool.execution.importer.GeneralProjectApplication2007Importer;
import csdc.tool.execution.importer.GeneralProjectApplication2008Importer;
import csdc.tool.execution.importer.GeneralProjectApplication2009Importer;
import csdc.tool.execution.importer.GeneralProjectApplication2011Importer;
import csdc.tool.execution.importer.GeneralProjectEndInspection2011SecondSeasonImporter;
import csdc.tool.execution.importer.GeneralProjectGranted2005Importer;
import csdc.tool.execution.importer.GeneralProjectGranted2005ImporterV2;
import csdc.tool.execution.importer.GeneralProjectGranted2006Importer;
import csdc.tool.execution.importer.GeneralProjectGranted2007Importer;
import csdc.tool.execution.importer.GeneralProjectGranted2008Importer;
import csdc.tool.execution.importer.GeneralProjectGranted2009Importer;
import csdc.tool.execution.importer.GeneralProjectGranted2010Importer;
import csdc.tool.execution.importer.GeneralProjectGranted2010ImporterV2;
import csdc.tool.execution.importer.GeneralProjectGranted2011Importer;
import csdc.tool.execution.importer.MinistryInstituteImporter;
import csdc.tool.execution.importer.UniversityContactInfoImporter;
import csdc.tool.merger.AgencyMerger;
import csdc.tool.tableKit.imp.AdminDivisionKit;
import csdc.tool.tableKit.imp.AwardKit;
import csdc.tool.tableKit.imp.CountryRegionKit;
import csdc.tool.tableKit.imp.EthnicKit;
import csdc.tool.tableKit.imp.GeneralProjectKit;
import csdc.tool.tableKit.imp.MinistryUniversityContactKit;
import csdc.tool.tableKit.imp.ProvinceAgencyContactKit;
import csdc.tool.tableKit.imp.ReviewingExpertKit;
import csdc.tool.tableKit.imp.UniversityKit;

/**
 * 需要独立执行的方法的容器，专用于不从struts、action之流触发的操作
 * @author xuhan
 *
 */
public class Customized extends Execution {
	
	public static long tmp;
	
	@Autowired
	private IHibernateBaseDao dao;
	
	@Autowired
	private JdbcDao cMIPSDao;
	
	@Autowired
	private SystemOptionDao systemOptionDao;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private AgencyMerger agencyMerger;
	
	
	
	public static void main(String[] args) {
		System.out.println(11);
		for (Class clazz : AllBeans.findAllBeans()) {
			System.out.println(clazz.getCanonicalName() + " - " + clazz.getName());
		}
	}
	
	@Override
	protected void work() throws Throwable {
		int totalRows = (int) dao.count("select app.id, app.name, app.applicantId, app.applicantName, uni.id, app.agencyName, so.name, app.disciplineType, app.year, app.finalAuditResult, app.status, app.file, app.finalAuditStatus , app.ministryAuditStatus, app.ministryAuditResult, app.reviewStatus, app.reviewResult, app.finalAuditDate from GeneralApplication app left join app.university uni left join app.subtype so left join app.topic top, GeneralMember mem where mem.application.id=app.id and 1=1 group by app.id, app.name, app.applicantId, app.applicantName, uni.id, app.agencyName, so.name, app.disciplineType, app.year, app.finalAuditResult, app.status, app.file, app.finalAuditStatus , app.ministryAuditStatus, app.ministryAuditResult, app.reviewStatus, app.reviewResult, app.finalAuditDate order by app.finalAuditDate desc, app.id");
		System.out.println(totalRows);
	}

	//////////////////////////////////////////////////////////////////////
	
	/**
	 * 导入《20111108_基地一览表（总表）_修正导入.xls》
	 */
	public void importInstitute() throws Exception {
		File xls = new File("D:\\csdc\\基地一览表\\20111108_基地一览表（总表）_修正导入.xls");
		new MinistryInstituteImporter(xls).excute();
	}	
	
	/**
	 * 导入《2011年一般项目立项数据（含立项编号）_修正导入.xls》
	 */
	public void importGeneralGranted2011() throws Exception {
		File xls = new File("D:\\csdc\\一般项目\\修正导入\\2011\\2011年一般项目立项数据（含立项编号）_修正导入.xls");
		new GeneralProjectGranted2011Importer(xls).excute();
	}
	
	/**
	 * 导入《2011年一般项目申请一览表_修正导入.xls》
	 */
	public void importGeneralApplication2011() throws Exception {
		File xls = new File("D:\\csdc\\一般项目\\修正导入\\2011\\2011年一般项目申报一览表_修正导入.xls");
		new GeneralProjectApplication2011Importer(xls).excute();
	}	

	
	/**
	 * 还原smdb一般项目成员表项目负责人的机构信息为其对应Teacher表里的信息
	 */
	public void resetGeneralProjectMajorMemberAgency() throws Exception {
		GeneralProjectMajorMemberAgencyResetter worker = new GeneralProjectMajorMemberAgencyResetter();
		worker.work();
	}
	
//	/**
//	 * 修正一般项目立项表中错误的结项信息
//	 */
//	public void reviseEndinpectionStatus() {
//		Tools tools = new Tools();
//		Session session = tools.session;
//		List<GeneralGranted> ggs = session.createQuery("select gg from GeneralGranted gg left join fetch gg.generalEndinspection").list();
//		for (GeneralGranted gg : ggs) {
//			boolean existApproved = false;	//存在提交且同意的结项条目
//			boolean modified = false;
//			for (Iterator iterator = gg.getGeneralEndinspection().iterator(); iterator.hasNext();) {
//				GeneralEndinspection ge = (GeneralEndinspection) iterator.next();
//				if (ge.getFinalAuditStatus() == 3 && ge.getFinalAuditResultEnd() == 2) {
//					existApproved = true;
//					if (gg.getStatus() != 2 && gg.getStatus() != 4) {
//						System.out.println("1 " + gg.getName() + " " + ge.getFinalAuditDate() + " " + ge.getFinalAuditorName());
//						gg.setStatus(2);
//						gg.setEndStopWithdrawDate(ge.getFinalAuditDate());
//						gg.setEndStopWithdrawOpinion(ge.getFinalAuditOpinion());
//						gg.setEndStopWithdrawPerson(ge.getFinalAuditorName());
//						modified = true;
//					}
//					if (gg.getEndStopWithdrawDate() == null) {
//						gg.setEndStopWithdrawDate(ge.getFinalAuditDate());
//						modified = true;
//					}
//					if (gg.getEndStopWithdrawOpinion() == null) {
//						gg.setEndStopWithdrawOpinion(ge.getFinalAuditOpinion());
//						modified = true;
//					}
//					if (gg.getEndStopWithdrawPerson() == null) {
//						gg.setEndStopWithdrawPerson(ge.getFinalAuditorName());
//						modified = true;
//					}
//					if (modified) {
//						session.update(gg);
//					}
//					break;
//				}
//			}
//			if (existApproved == false) {
//				if (gg.getStatus() != 1 && gg.getStatus() != 3 && gg.getStatus() != 4)
//				System.out.println("2 " + gg.getName());
//			}
//		}
//		tools.finish();
//	}
	
	/**
	 * 纠正一般项目申请和一般项目申请的成果形式
	 */
	public void normalizeProductType() throws Exception {
		FixProductType importer = new FixProductType();
		importer.work();
	}

	/**
	 * 导入《2005年一般项目立项一览表_修正导入.xls》
	 */
	public void importGeneralGranted2005V2() throws Exception {
		File xls = new File("D:\\csdc\\一般项目\\修正导入\\2005年一般项目立项一览表_修正导入.xls");
		GeneralProjectGranted2005ImporterV2 importer = new GeneralProjectGranted2005ImporterV2(xls);
		importer.work();
	}

	/**
	 * 导入《2010年一般项目立项带结项形式.xls》
	 * @throws Throwable 
	 */
	public void importGeneralGranted2010V2() throws Throwable {
		File xls = new File("D:\\csdc\\一般项目\\修正导入\\2010年一般项目立项带结项形式.xls");
		GeneralProjectGranted2010ImporterV2 importer = new GeneralProjectGranted2010ImporterV2(xls);
		importer.work();
	}

	/**
	 * 导入《2010年一般项目立项一览表（正式立项通知）_修正导入.xls》
	 * @throws Throwable 
	 */
	public void importGeneralGranted2010() throws Throwable {
		File xls = new File("D:\\csdc\\一般项目\\修正导入\\2010年一般项目立项一览表（正式立项通知）_修正导入.xls");
		GeneralProjectGranted2010Importer importer = new GeneralProjectGranted2010Importer(xls);
		importer.work();
	}

	
//	public void importProductType() throws Exception {
//		String path = "D:\\csdc\\乱七八糟的成果形式.xls";
//		File xls = new File(path);
//		new TestImporter(xls).work();
//	}
	
	/**
	 * 导入《2011年第二季度一般项目结项数据（第六批）.xls》
	 */
	public void importGeneralProjectEndInspection2011SecondSeason() throws Exception {
		File xls = new File("D:\\csdc\\2011年第二季度一般项目结项数据（第六批）.xls");
		GeneralProjectEndInspection2011SecondSeasonImporter importer = new GeneralProjectEndInspection2011SecondSeasonImporter(xls);
		importer.work();
	}
	
//	/**
//	 * 为所有没有“其他院系”的高校创建一个其他院系
//	 */
//	public void createOtherDepartments() {
//		Tools tools = new Tools();
//		List<Agency> univList = tools.session.createQuery("select u from Agency u where u.type = 3 or u.type = 4").list();
//		for (Agency university : univList) {
//			tools.getOtherDepartment(university);
//		}
//		tools.finish();
//	}

	/**
	 * 导入《20110607_全国高校教授数据库.rar》 -> 《data2010old.mdb》 -> 单位数据、人员数据
	 */
	public void importData2010oldMDB() throws Exception {
		File mdb = new File("D:\\csdc\\data2010old.mdb");
		Data2010oldMDBImporter importer = new Data2010oldMDBImporter(mdb);
		importer.work();
	}
	
	/**
	 * 导入《20110504_学校联系方式.xls》
	 */
	public void importUniversityContactInfo() throws Exception {
		File xls = new File("D:\\csdc\\20110504_学校联系方式.xls");
		UniversityContactInfoImporter importer = new UniversityContactInfoImporter(xls);
		importer.work();
	}

	/**
	 * 导入《2005-2011年一般项目结项数据v3_修正导入.xls》
	 */
	public void importGeneralProject2005_2011End() throws Exception {
		File xls = new File("D:\\csdc\\一般项目\\修正导入\\2005-2011年一般项目结项数据v3_修正导入.xls");
		GeneralProject2005_2011EndImporter importer = new GeneralProject2005_2011EndImporter(xls);
		importer.work();
	}

	/**
	 * 导入《1993-2010年一般项目中检、结项及变更数据库_修正导入.xls》
	 */
	public void importGeneralProject1993_2010MidLate() throws Exception {
		File xls = new File("D:\\csdc\\一般项目\\修正导入\\1993-2010年一般项目中检、结项及变更数据库v2_修正导入.xls");
		GeneralProject1993_2010MidLateImporter importer = new GeneralProject1993_2010MidLateImporter(xls);
		importer.work();
	}

	/**
	 * 导入《2009年一般项目立项一览表_修正导入.xls》
	 */
	public void importGeneralGranted2009() throws Exception {
		File xls = new File("D:\\csdc\\一般项目\\修正导入\\2009年一般项目立项一览表_修正导入.xls");
		GeneralProjectGranted2009Importer importer = new GeneralProjectGranted2009Importer(xls);
		importer.work();
	}

	/**
	 * 导入《2009年一般项目申请数据v2-13646项_修正导入.xls》
	 */
	public void importGeneralApplication2009() throws Exception {
		File xls = new File("D:\\csdc\\一般项目\\修正导入\\2009年一般项目申报数据v2-13646项_修正导入.xls");
		GeneralProjectApplication2009Importer importer = new GeneralProjectApplication2009Importer(xls);
		importer.work();
	}
	
	/**
	 * 导入《2008年一般项目立项一览表拨款明细_修正导入.xls》
	 */
	public void importGeneralGranted2008() throws Exception {
		File xls = new File("D:\\csdc\\一般项目\\修正导入\\2008年一般项目立项一览表拨款明细_修正导入.xls");
		GeneralProjectGranted2008Importer importer = new GeneralProjectGranted2008Importer(xls);
		importer.work();
	}

	/**
	 * 导入《2008年一般项目申请汇总（各项明细）_修正导入.xls》
	 */
	public void importGeneralApplication2008() throws Exception {
		File xls = new File("D:\\csdc\\一般项目\\修正导入\\2008年一般项目申报汇总（各项明细）_修正导入.xls");
		GeneralProjectApplication2008Importer importer = new GeneralProjectApplication2008Importer(xls);
		importer.work();
	}
	
	/**
	 * 导入《2007年一般项目立项一览表（下发）_修正导入.xls》
	 */
	public void importGeneralGranted2007() throws Exception {
		File xls = new File("D:\\csdc\\一般项目\\修正导入\\2007年一般项目立项一览表（下发）_修正导入.xls");
		GeneralProjectGranted2007Importer importer = new GeneralProjectGranted2007Importer(xls);
		importer.work();
	}

	/**
	 * 导入《2007年一般项目申请汇总_修正导入.xls》
	 */
	public void importGeneralApplication2007() throws Exception {
		File xls = new File("D:\\csdc\\一般项目\\修正导入\\2007年一般项目申报汇总_修正导入.xls");
		GeneralProjectApplication2007Importer importer = new GeneralProjectApplication2007Importer(xls);
		importer.work();
	}
	
	/**
	 * 导入《2006年一般项目立项及拨款一览表_修正导入.xls》
	 */
	public void importGeneralGranted2006() throws Exception {
		File xls = new File("D:\\csdc\\一般项目\\修正导入\\2006年一般项目立项及拨款一览表_修正导入.xls");
		GeneralProjectGranted2006Importer importer = new GeneralProjectGranted2006Importer(xls);
		importer.work();
	}

	/**
	 * 导入《2006年一般项目申请汇总v2_修正导入.xls》
	 */
	public void importGeneralApplication2006() throws Exception {
		File xls = new File("D:\\csdc\\一般项目\\修正导入\\2006年一般项目申报汇总v2_修正导入.xls");
		GeneralProjectApplication2006Importer importer = new GeneralProjectApplication2006Importer(xls);
		importer.work();
	}
	
	/**
	 * 导入《2005年一般项目立项汇总_修正导入.xls》
	 */
	public void importGeneralGranted2005() throws Exception {
		File xls = new File("D:\\csdc\\一般项目\\修正导入\\2005年一般项目立项汇总_修正导入.xls");
		GeneralProjectGranted2005Importer importer = new GeneralProjectGranted2005Importer(xls);
		importer.work();
	}

	/**
	 * 导入《2005年一般项目申请汇总_修正导入.xls》
	 */
	public void importGeneralApplication2005() throws Exception {
		File xls = new File("D:\\csdc\\一般项目\\修正导入\\2005年一般项目申报汇总_修正导入.xls");
		GeneralProjectApplication2005Importer importer = new GeneralProjectApplication2005Importer(xls);
		importer.work();
	}

	
	/**
	 * 导入国家地区名称代码
	 */
	public void importCountryRegion() throws Exception {
		CountryRegionKit countryRegionKit = new CountryRegionKit();
		File xls = new File("D:\\csdc\\ISO3166-1国家和地区代码\\ISO3166-1国家和地区代码.xls");
		StringBuffer errMsg = countryRegionKit.validate(xls);
		System.out.println(errMsg);
		countryRegionKit.imprt();
	}


	/**
	 * 导入奖励
	 */
	public void importAward() throws Exception {
		ServletContext sc = ApplicationContainer.sc;
		AwardKit awardKit = (AwardKit) SpringBean.getBean("awardKit", sc);
		File xls = new File("D:\\csdc\\20110115_中国高校人文社会科学优秀成果奖一览表（第1-5届）_修正导入.xls");
		StringBuffer errMsg = awardKit.validate(xls);
		System.out.println(errMsg);
		awardKit.imprt();
	}


	/**
	 * 导入民族
	 */
	public void importEthnic() throws Exception {
		EthnicKit ethnic = new EthnicKit();
		File xls = new File("D:\\csdc\\GB3304-91中国各民族名称的罗马字母拼写法和代码.xls");
		StringBuffer errMsg = ethnic.validate(xls);
		System.out.println(errMsg);
		ethnic.imprt();
	}

	/**
	 * 将同一person的多个teacher置为只有一个专职
	 */
	public void mergeTeachers() {
		List<Person> pList = dao.query("select p from Person p left join fetch p.teacher");
		List tList = new ArrayList();
		for (Person person : pList) {
			if (person.getTeacher() != null && person.getTeacher().size() > 1){
				Iterator iterator = person.getTeacher().iterator();
				iterator.next();
				while (iterator.hasNext()) {
					Teacher teacher = (Teacher) iterator.next();
					teacher.setType("兼职人员");
					tList.add(teacher);
				}
			}
		}
		dao.addOrModify(tList);
	}

	/**
	 * 更新部属高校和省厅的subjection
	 */
	public void updateSubjection() {
		List tmpList = dao.query("select a from Agency a where a.code = '360' and a.name = '教育部' ");
		Agency moe = (Agency) tmpList.get(0);

		List<Agency> moeUnivList = dao.query("select a from Agency a where a.type = 3 or a.type = 2 ");
		for (Agency agency : moeUnivList) {
			agency.setSubjection(moe);
		}
		dao.addOrModify(moeUnivList);

	}


	/**
	 * 插入教育部
	 */
	public void addMOE() {
		Agency moe = new Agency();
		moe.setName("教育部");
		moe.setSname("社会科学司");
		moe.setType(1);
		moe.setCode("360");

		List tmpList = dao.query("select so from SystemOption so where so.standard = 'GBT2260-2007' and so.name = '北京市' ");
		moe.setProvince((SystemOption) tmpList.get(0));
		dao.add(moe);
	}


	/**
	 * 导入省厅联系方式
	 */
	public void importProvinceAgencyContact() throws Exception {
		ProvinceAgencyContactKit provinceAgencyContactKit = new ProvinceAgencyContactKit();
		File xls = new File("D:\\csdc\\[修改]20080922_部属高校、地方厅局通讯录.xls");
		StringBuffer errMsg = provinceAgencyContactKit.validate(xls);
		System.out.println(errMsg);
		provinceAgencyContactKit.imprt();
	}


	/**
	 * 导入部属高校联系方式
	 */
	public void importMinistryUniversityContact() throws Exception {
		MinistryUniversityContactKit ministryUniversityContactKit = new MinistryUniversityContactKit();
		File xls = new File("D:\\csdc\\[修改]20080922_部属高校、地方厅局通讯录.xls");
		StringBuffer errMsg = ministryUniversityContactKit.validate(xls);
		System.out.println(errMsg);
		ministryUniversityContactKit.imprt();
	}


	/**
	 * 导入专家表(8485条的那个)
	 */
	public void importExpert() throws Exception {
		ReviewingExpertKit reviewingExpertKit = new ReviewingExpertKit();
		File xls = new File("D:\\csdc\\[修改]专家表.xls");
		StringBuffer errMsg = reviewingExpertKit.validate(xls);
		System.out.println(errMsg);
		reviewingExpertKit.imprt();
	}


	/**
	 * 导入项目
	 */
	public void importGeneralProjects() throws Exception {
		GeneralProjectKit generalProjectKit = new GeneralProjectKit();
		File xls = new File("D:\\csdc\\张金钟.xls");
		StringBuffer errMsg = generalProjectKit.validate(xls);
		System.out.println(errMsg);
		generalProjectKit.imprt();
	}


	/**
	 * 导入高校
	 * 普通高校代码（2011年1月20日）.xls
	 * 成人高校代码（2011年1月20日）.xls
	 * 独立学院代码（2011年1月20日）.xls
	 * 分校、大专班代码（2011年1月20日）.xls
	 * 新增高校.xls
	 */
	public void importUniversity() throws Exception {
		UniversityKit universityKit= new UniversityKit();
		File xls = new File("D:\\csdc\\数据代码\\其他数据代码\\高校代码\\新增高校.xls");
		universityKit.validate(xls);
		universityKit.imprt();
	}

	/**
	 * 导入行政区划
	 */
	public void importAdminDivision() throws Exception {
		AdminDivisionKit adminDivisionKit = new AdminDivisionKit();
		File xls = new File("D:\\csdc\\数据代码\\GBT2260中华人民共和国行政区划代码\\GBT2260-2007中华人民共和国行政区划代码.xls");
		adminDivisionKit.validate(xls);
		adminDivisionKit.imprt();
	}

}
