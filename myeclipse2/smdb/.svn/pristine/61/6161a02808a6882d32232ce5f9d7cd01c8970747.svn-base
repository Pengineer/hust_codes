package csdc.action.dm.dupCheck;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import csdc.bean.DevelopmentReport;
import csdc.bean.GeneralSpecial;
import csdc.bean.Nsfc;
import csdc.bean.Nssf;
import csdc.bean.NssfProjectApplication;
import csdc.bean.ProjectGranted;
import csdc.dao.HibernateBaseDao;

/**
 * 查重标记类
 * 该类主要是标记数据库中在研数据和撤项数据。
 * @author wangyi
 */
public class DupCheckAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private HibernateBaseDao dao;
	
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
	 * 一般项目查重标记清零
	 * @return
	 */
	public String deleteGeneral() {
		//四类项目
		List<ProjectGranted> granteds = dao.query("from ProjectGranted granted where granted.projectType in ('general', 'instp', 'post', 'key') and granted.isDupCheckGeneral = 1");
		for (int i = 0; i < granteds.size(); i++) {
			ProjectGranted granted = granteds.get(i);
			granted.setIsDupCheckGeneral(0);
			dao.addOrModify(granted);
		}
		
		//一般项目专项任务
		List<GeneralSpecial> generalSpecials = dao.query("from GeneralSpecial generalSpecial where generalSpecial.isDupCheckGeneral = 1");
		for (int i = 0; i < generalSpecials.size(); i++) {
			GeneralSpecial generalSpecial = generalSpecials.get(i);
			generalSpecial.setIsDupCheckGeneral(0);
			dao.addOrModify(generalSpecial);
		}
				
		//国家自科
		List<Nsfc> nsfcs = dao.query("from Nsfc nsfc where nsfc.isDupCheckGeneral = 1");
		for (int i = 0; i < nsfcs.size(); i++) {
			Nsfc nsfc = nsfcs.get(i);
			nsfc.setIsDupCheckGeneral(0);
			dao.addOrModify(nsfc);
		}
		
		//国家社科
		List<Nssf> nssfs = dao.query("from Nssf nssf where nssf.isDupCheckGeneral = 1");
		for (int i = 0; i < nssfs.size(); i++) {
			Nssf nssf = nssfs.get(i);
			nssf.setIsDupCheckGeneral(0);
			dao.addOrModify(nssf);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 设置一般项目查重标记
	 * @return
	 */
	public String addGeneral() {
		//1、四类项目（一般，基地，重大攻关，后期资助）：在研和撤项（撤项三年内不得申报）
		List<ProjectGranted> granteds = dao.query("from ProjectGranted granted where granted.projectType in ('general', 'instp', 'post', 'key') and (granted.status = 1 or (granted.status = 4 and to_char(granted.endStopWithdrawDate,'yyyyMMddHHmmSS') > '20110314240000'))");
		for (int i = 0; i < granteds.size(); i++) {
			ProjectGranted granted = granteds.get(i);
			granted.setIsDupCheckGeneral(1);
			dao.addOrModify(granted);
		}
		
//		//一般项目专项任务在研（暂时专项任务未合并到一般项目中）
//		List<GeneralSpecial> generalSpecials = dao.query("from GeneralSpecial");
//		for (int i = 0; i < generalSpecials.size(); i++) {
//			GeneralSpecial generalSpecial = generalSpecials.get(i);
//			generalSpecial.setIsDupCheckGeneral(1);
//			dao.addOrModify(generalSpecial);
//		}
		
		//发展报告项目（暂时单独建一张表存储立项数据）
		List<DevelopmentReport> developmentReports = dao.query("from DevelopmentReport");
		for (int i = 0; i < developmentReports.size(); i++) {
			DevelopmentReport developmentReport = developmentReports.get(i);
			developmentReport.setIsDupCheckGeneral(1);
			dao.addOrModify(developmentReport);
		}
				
		//国家自然科学基金（2011年及之后立项视为在研）
		List<Nsfc> nsfcs = dao.query("from Nsfc nsfc where nsfc.year > 2010");
		for (int i = 0; i < nsfcs.size(); i++) {
			Nsfc nsfc = nsfcs.get(i);
			nsfc.setIsDupCheckGeneral(1);
			dao.addOrModify(nsfc);
		}
		
		//国家社会科学基金申报数据
		List<NssfProjectApplication> nssfProjectApplications = dao.query("from NssfProjectApplication");
		for (int i = 0; i < nssfProjectApplications.size(); i++) {
			NssfProjectApplication nssfProjectApplication = nssfProjectApplications.get(i);
			nssfProjectApplication.setIsDupCheckGeneral(1);
			dao.addOrModify(nssfProjectApplication);
		}
				
		//1、国家社会科学基金（2007年及之后立项的在研数据）
		//2、国家社会科学基金单列学科教育学年度课题类别包含“国家”的在研数据；单列学科艺术学的在研数据
		List<Nssf> nssfs = dao.query("from Nssf nssf where (c_status = '1') and ((nssf.singleSubject is null and to_char(startDate, 'yyyy') > 2006) or (nssf.singleSubject = '教育学' and nssf.subject = '年度课题' and nssf.type like '%国家%') or nssf.singleSubject = '艺术学')");
		for (int i = 0; i < nssfs.size(); i++) {
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
	
}
