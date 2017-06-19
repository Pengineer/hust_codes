package csdc.action.dm.dupCheck;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.bean.Devrpt;
import csdc.bean.GeneralSpecial;
import csdc.bean.Nsfc;
import csdc.bean.Nssf;
import csdc.bean.NssfProjectApplication;
import csdc.bean.PopularBook;
import csdc.bean.ProjectGranted;
import csdc.dao.HibernateBaseDao;
import csdc.service.IProjectService;
import csdc.tool.HSSFExport;

/**
 * 查重标记类
 * 该类主要是标记数据库中在研数据和撤项数据。
 * @author wangyi
 */
public class DupCheckAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private HibernateBaseDao dao;
	private String type;//项目类型general instp special
	
	
	private String fileFileName;//导出文件名字
	
	/**
	 * 进入一般项目查重标记页面
	 * @return
	 */
	public String toAddGeneral() {
		return SUCCESS;
	}
	
	/**
	 * 进入基地项目查重标记页面
	 * @return
	 */
	public String toAddInstp() {
		return SUCCESS;
	}
	
	/**
	 * 进入基地项目查重标记页面
	 * @return
	 */
	public String toAddSpecial() {
		return SUCCESS;
	}
	
	/**
	 * 一般项目查重标记清零
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String deleteGeneral() {
		//四类项目
		List<ProjectGranted> granteds = dao.query("from ProjectGranted granted where granted.projectType in ('general', 'instp', 'post', 'key', 'special', 'entrust') and granted.isDupCheckGeneral = 1");
		for (int i = 0; i < granteds.size(); i++) {
			System.out.println(i);
			ProjectGranted granted = granteds.get(i);
			granted.setIsDupCheckGeneral(0);
			dao.modify(granted);
		}
		
		//一般项目专项任务
		List<GeneralSpecial> generalSpecials = dao.query("from GeneralSpecial generalSpecial where generalSpecial.isDupCheckGeneral = 1");
		for (int i = 0; i < generalSpecials.size(); i++) {
			System.out.println(i);
			GeneralSpecial generalSpecial = generalSpecials.get(i);
			generalSpecial.setIsDupCheckGeneral(0);
			dao.modify(generalSpecial);
		}
		
		//发展报告
		List<Devrpt> devrpts = dao.query("from Devrpt devrpt where devrpt.isDupCheckGeneral = 1");
		for (int i = 0; i < devrpts.size(); i++) {
			System.out.println(i);
			Devrpt devrpt = devrpts.get(i);
			devrpt.setIsDupCheckGeneral(0);
			dao.modify(devrpt);
		}
		
		//普及读物
		List<PopularBook> popularBooks = dao.query("from PopularBook popularBook where popularBook.isDupCheckGeneral = 1");
		for (int i=0; i<popularBooks .size(); i++) {
			System.out.println(i);
			PopularBook popularBook = popularBooks.get(i);
			popularBook.setIsDupCheckGeneral(0);
			dao.modify(popularBook);
		}
				
		//国家自科
		List<Nsfc> nsfcs = dao.query("from Nsfc nsfc where nsfc.isDupCheckGeneral = 1");
		for (int i = 0; i < nsfcs.size(); i++) {
			System.out.println(i);
			Nsfc nsfc = nsfcs.get(i);
			nsfc.setIsDupCheckGeneral(0);
			dao.modify(nsfc);
		}
		
		//国家社会科学基金申请数据
		List<NssfProjectApplication> nssfProjectApplications = dao.query("from NssfProjectApplication where isDupCheckGeneral = 1");
		for (int i = 0; i < nssfProjectApplications.size(); i++) {
			System.out.println(i);
			NssfProjectApplication nssfProjectApplication = nssfProjectApplications.get(i);
			nssfProjectApplication.setIsDupCheckGeneral(0);
			dao.addOrModify(nssfProjectApplication);
		}
		
		//国家社科
		List<Nssf> nssfs = dao.query("from Nssf nssf where nssf.isDupCheckGeneral = 1");
		for (int i = 0; i < nssfs.size(); i++) {
			System.out.println(i);
			Nssf nssf = nssfs.get(i);
			nssf.setIsDupCheckGeneral(0);
			dao.modify(nssf);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 设置一般项目查重标记
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String addGeneral() {
		//1、五类项目（一般，基地，重大攻关，后期资助，委托）：在研（截止20141231）和撤项（撤项三年内不得申请）
//		List<ProjectGranted> granteds = dao.query("from ProjectGranted granted where granted.projectType in ('general', 'instp', 'post', 'key', 'entrust') and (granted.status = 1 or (granted.status = 4 and to_char(granted.endStopWithdrawDate,'yyyyMMddHHmmSS') > '20111231240000'))");
		//基地的在研项目以评价中心提供的为准
		List<ProjectGranted> granteds = dao.query("from ProjectGranted granted where granted.projectType in ('general', 'post', 'key', 'entrust') and (granted.status = 1 or (granted.status = 2 and to_char(granted.endStopWithdrawDate,'yyyyMMdd') > '20141231') or (granted.status = 4 and to_char(granted.endStopWithdrawDate,'yyyyMMddHHmmSS') > '20111231240000'))");
		for (int i = 0; i < granteds.size(); i++) {
			System.out.println(i);
			ProjectGranted granted = granteds.get(i);
			granted.setIsDupCheckGeneral(1);
			dao.addOrModify(granted);
		}
		
		//一般项目专项任务在研（暂时将在研的专项任务放在单独的一张表中）
		List<GeneralSpecial> generalSpecials = dao.query("from GeneralSpecial where to_char(importDate, 'yyyy-mm')='2015-04'");
		for (int i = 0; i < generalSpecials.size(); i++) {
			System.out.println(i);
			GeneralSpecial generalSpecial = generalSpecials.get(i);
			generalSpecial.setIsDupCheckGeneral(1);
			dao.addOrModify(generalSpecial);
		}
		
//		//2014年：发展报告项目（暂时单独建一张表存储立项数据）：由评价中心提供在研数据   
		List<Devrpt> devrpts = dao.query("from Devrpt where to_char(createDate, 'yyyy-mm')='2015-04'");
		for (int i = 0; i < devrpts.size(); i++) {
			System.out.println(i);
			Devrpt devrpt = devrpts.get(i);
			devrpt.setIsDupCheckGeneral(1);
			dao.modify(devrpt);
		}
		
		//2015年：普及读物项目（暂时单独建一张表储存在研数据，由评价中心提供在研数据）
		List<PopularBook> popularBooks = dao.query("from PopularBook where to_char(importDate, 'yyyy-mm')='2015-04'");
		for (int i=0; i<popularBooks .size(); i++) {
			System.out.println(i);
			PopularBook popularBook = popularBooks.get(i);
			popularBook.setIsDupCheckGeneral(1);
			dao.modify(popularBook);
		}
				
		//国家自然科学基金（2012年及之后立项视为在研）
		List<Nsfc> nsfcs = dao.query("from Nsfc nsfc where nsfc.year > 2011 and to_char(createDate, 'yyyy-mm')='2015-04'");
		for (int i = 0; i < nsfcs.size(); i++) {
			System.out.println(i);
			Nsfc nsfc = nsfcs.get(i);
			nsfc.setIsDupCheckGeneral(1);
			dao.addOrModify(nsfc);
		}
		
		//国家社会科学基金申请数据
		List<NssfProjectApplication> nssfProjectApplications = dao.query("from NssfProjectApplication where to_char(createDate, 'yyyy-mm')='2015-04'");
		for (int i = 0; i < nssfProjectApplications.size(); i++) {
			System.out.println(i);
			NssfProjectApplication nssfProjectApplication = nssfProjectApplications.get(i);
			nssfProjectApplication.setIsDupCheckGeneral(1);
			dao.addOrModify(nssfProjectApplication);
		}
				
		//1、国家社会科学基金（2007年及之后立项的在研数据）
		//2、国家社会科学基金单列学科教育学年度课题类别包含“国家”的在研数据；单列学科艺术学的在研数据
		List<Nssf> nssfs = dao.query("from Nssf nssf where (c_status=1) and ((nssf.singleSubject is null and to_char(startDate, 'yyyy') > 2006) or (nssf.singleSubject = '教育学' and nssf.subject = '年度课题' and nssf.type like '%国家%') or nssf.singleSubject = '艺术学')");
		for (int i = 0; i < nssfs.size(); i++) {
			System.out.println(i);
			Nssf nssf = nssfs.get(i);
			nssf.setIsDupCheckGeneral(1);
			dao.addOrModify(nssf);
		}				
		
		return SUCCESS;
	}

	
	/**
	 * 基地项目查重标记清零
	 * @return
	 */
	public String deleteInstp() {
		//基地、后期资助、重大攻关
		List<ProjectGranted> granteds = dao.query("from ProjectGranted granted where granted.projectType in ('instp', 'post', 'key') and granted.isDupCheckInstp = 1");
		for (int i = 0; i < granteds.size(); i++) {
			ProjectGranted granted = granteds.get(i);
			granted.setIsDupCheckInstp(0);
			dao.addOrModify(granted);
		}
				
		//国家社科基金重大项目
		List<Nssf> nssfs = dao.query("from Nssf nssf where nssf.isDupCheckInstp = 1");
		for (int i = 0; i < nssfs.size(); i++) {
			Nssf nssf = nssfs.get(i);
			nssf.setIsDupCheckInstp(0);
			dao.addOrModify(nssf);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 设置基地项目查重标记
	 * @return
	 */
	public String addInstp() {
		//基地、后期资助、重大攻关项目在研数据
		List<ProjectGranted> granteds = dao.query("from ProjectGranted granted where granted.projectType in ('instp', 'post', 'key') and granted.status = 1");
		
		for (int i = 0; i < granteds.size(); i++) {
			ProjectGranted granted = granteds.get(i);
			granted.setIsDupCheckInstp(1);
			dao.addOrModify(granted);
		}
		
		//国家社科基金重大项目在研数据
		List<Nssf> nssfs = dao.query("from Nssf nssf where nssf.type = '重大项目' and nssf.status = 1");
		
		for (int i = 0;  i < nssfs.size(); i++) {
			Nssf nssf = nssfs.get(i);
			nssf.setIsDupCheckInstp(1);
			dao.addOrModify(nssf);
		}
		
		return SUCCESS;
	}

	
	/**
	 * 专项任务查重标记清零（2015年的专项的查重和一般一样）
	 * @return
	 */
	public String deleteSpecial() {
		//添加代码
		deleteGeneral();
		return SUCCESS;
	}
	
	/**
	 * 专项任务项目查重标记
	 * @return
	 */
	public String addSpecial() {
		//添加代码
		addGeneral();
		return SUCCESS;
	}
	
	/**
	 * 导出一般项目查重结果信息
	 * @return
	 */
	public String exportCheckResult() {
		return SUCCESS;
	}
	
	/**
	 * 导出项目查重结果excel导出
	 * @return 输入流
	 */
	public InputStream getDownloadFile() throws Exception{
		
		if ("general".equals(type)) {
			fileFileName = "教育部人文社会科学研究一般项目初审结果一览表.xls";
		} else if ("instp".equals(type)) {
			fileFileName = "教育部人文社会科学研究基地项目经项目初审结果一览表.xls";
		} else if ("special".equals(type)) {
			fileFileName = "教育部人文社会科学研究专项任务项目初审结果一览表.xls";
		}
		fileFileName = new String(fileFileName.getBytes(), "ISO8859-1");
//		ServletContext sc = (ServletContext) ActionContext.getContext();
//		int year1 = (Integer) sc.getAttribute("currentYear");
		InputStream is = getDupResultInfo(type);
	
		return is;
	}
	
	private InputStream getDupResultInfo(String type) {
		//系统当前默认年份
		int year = 2015;
		//项目初审结果
		String hql4Reason = null;
		if ("instp".equals(type) || "general".equals(type) || "special".equals(type)) {
			hql4Reason =  "select p.id, p.name, projectType.name, u.name, p.applicantName, p.firstAuditResult from ProjectApplication p left join p.subtype projectType left join p.university u " +
					" where p.type = '" + type +"' and ( p.firstAuditResult is not null and p.firstAuditDate is not null ) and p.year = ? ";
		} 
		
		hql4Reason += " order by u.name asc, projectType asc, p.name asc";
		List dataList = dao.query(hql4Reason, year);
		
		//添加序号
		for (int i=0, size = dataList.size(); i < size; i++) {
			Object[] obj = (Object[]) dataList.get(i);
			obj[0] = i + 1;
		}
		
		//导出配置
		String header = "";
		String[] title = {"序号", "项目名称", "项目类别", "高校名称", "申请人", "初审结果"};
//		String[] title = {"序号", "初审结果", "项目类别", "项目名称", "申请人", "高校名称"};
		String tail = "";
		float tailHeight = 0.0f;
		
		if ("general".equals(type)) {
			header = year + "年度教育部人文社会科学研究一般项目初审结果一览表";
			tail = "初审规则：\n" +
					"（1）职称与年龄审核：规划基金项目申请者，应为具有高级职称（含副高）的在编在岗教师；青年基金项目申请者，应为具有博士学位或中级以上（含中级）职称的在编在岗教师，年龄不超过40周岁（"+ (year - 40) +"年1月1日以后出生）；\n" +
					"（2）在研项目查重：项目申请人应不具有在研的国家自然科学基金、国家社会科学基金（含教育学、艺术学单列）、教育部人文社会科学研究一般项目、重大攻关项目、后期资助项目、基地项目、发展报告项目、专项任务项目、委托项目；\n" +
					"（3）撤项项目审核：项目申请人应不具有三年内撤项的教育部人文社会科学研究一般项目、重大攻关项目、后期资助项目、基地项目、专项任务项目、委托项目；\n" +
//						"（4）青年基金限制：已获得过青年基金项目资助的申请人（不管结项与否）不得再次申请青年基金项目，即申请人只能获得一次青年基金项目资助；\n" + 
					"（4）申请项目限制：申请国家社科基金项目的负责人同年度不能申请教育部一般项目；申请国家社科基金后期资助项目的负责人同年度不能申请教育部一般项目。" ;
		} else if ("instp".equals(type)) {
			header = year + "年度教育部人文社会科学研究基地项目初审结果一览表";
			tail = "初审规则：\n ...";
		} else if ("special".equals(type)) {
			header = year + "年度教育部人文社会科学研究专项任务项目初审结果一览表";
			tail = "初审规则：\n" +
					"（1）职称与年龄审核：规划基金项目申请者，应为具有高级职称（含副高）的在编在岗教师；青年基金项目申请者，应为具有博士学位或中级以上（含中级）职称的在编在岗教师，年龄不超过40周岁（"+ (year - 40) +"年1月1日以后出生）；\n" +
					"（2）在研项目查重：项目申请人应不具有在研的国家自然科学基金、国家社会科学基金（含教育学、艺术学单列）、教育部人文社会科学研究一般项目、重大攻关项目、后期资助项目、基地项目、发展报告项目、专项任务项目、委托项目；\n" +
					"（3）撤项项目审核：项目申请人应不具有三年内撤项的教育部人文社会科学研究一般项目、重大攻关项目、后期资助项目、基地项目、专项任务项目、委托项目；\n" +
//						"（4）青年基金限制：已获得过青年基金项目资助的申请人（不管结项与否）不得再次申请青年基金项目，即申请人只能获得一次青年基金项目资助；\n" + 
					"（4）申请项目限制：申请国家社科基金项目的负责人同年度不能申请教育部专项任务项目；申请国家社科基金后期资助项目的负责人同年度不能申请教育部专项任务项目。";
		}
	
		tailHeight = 90.0f;
		return HSSFExport.commonExportExcel(dataList, header, title, tail, tailHeight);
	}
	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
