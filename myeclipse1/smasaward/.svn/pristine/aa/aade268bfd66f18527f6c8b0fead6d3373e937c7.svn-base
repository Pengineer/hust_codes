// ========================================================================
// 文件名: MainAction.java
//
// 文件说明:
//	 本文件主要实现用户登入后，主页面上action功能的加载。主要分为上下左右四帧
// 各个action与页面的对应关系查看struts.xml文件。
//
// 历史记录:
// 2009-11-28  龚凡						创建文件，完成基本功能.
// ========================================================================

package csdc.action;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.SystemConfig;
import csdc.bean.common.Visitor;
import csdc.dao.SqlBaseDao;
import csdc.tool.RandomNumUtil;
import csdc.tool.SystemConfigLoader;

/**
 * 登入前后主页面
 * @author 龚凡
 * @author fengcl
 * @version 1.0 2010.03.31
 * @version 1.1 2012.02.21
 */
@SuppressWarnings("unchecked")
public class MainAction extends BaseAction{
	private static final long serialVersionUID = 1313613085019785449L;
	private ByteArrayInputStream inputStream;
	private String UserPictureUploadPath;// 上传照片路径
	private String rows;// 列表每页显示条数
	private SystemConfig general1, general2, general3, general4, general5, general6, general16, general17, general18, general19, general20, general32, general33;
	private SystemConfig generalReviewerImportedStartDate,generalReviewerImportedEndDate;//一般项目_评审专家_导入_开始时间、结束时间
	private SystemConfig instpReviewerImportedStartDate,instpReviewerImportedEndDate;	//基地项目_评审专家_导入_开始时间、结束时间
	private SystemConfig generalReviewerBirthdayStartDate,generalReviewerBirthdayEndDate;//一般项目_评审专家_出生日期_开始时间、结束时间
	private SystemConfig instpReviewerBirthdayStartDate,instpReviewerBirthdayEndDate;//基地项目_评审专家_出生日期_开始时间、结束时间
	private String total;// 一次发送邮件的数量
	private String template;// 模板邮件的名称
	private String waitTime;// 一次发送邮件的数量
	//ValueStack valueStack = ActionContext.getContext().getValueStack();  

	/*
	private Integer GeneralFormalReviewerNInBU;// 一般项目正式评审专家数(部属高校)
	private Integer GeneralFormalReviewerNInDU;// 一般项目正式评审专家数(地方高校)
	private Integer GeneralReserveReviewerN;// 一般项目备用评审专家数
	private Integer GeneralFormalPNPerReviewerMin;// 一般项目每位专家正式评审最小数
	private Integer GeneralFromalPNPerReviewerMax;// 一般项目每位专家正式评审最大数
	private Integer GeneralReservePNReviewerMax;// 一般项目每位专家备用评审最大数
	*/
	private String allYears;		//所有合法年份
	private Integer defaultYear;	//默认年份
	private boolean systemStatus;
	
	private String allProjectTypes;		//所有项目类型
	private String defaultProjectType;	//默认项目类型
	
	@Autowired
	private SqlBaseDao sqlDao;

	/**
	 * right帧，系统首页
	 * @return
	 */
	public String right(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		Map application = ActionContext.getContext().getApplication();
		int generalExpertProjectMin = (Integer) application.get("GeneralExpertProjectMin");
		int generalExpertProjectMax = (Integer) application.get("GeneralExpertProjectMax");
		int instpExpertProjectMin = (Integer) application.get("InstpExpertProjectMin");
		int instpExpertProjectMax = (Integer) application.get("InstpExpertProjectMax");
		int generalUniversityExpertMin = (Integer) application.get("GeneralUniversityExpertMin");
		int instpUniversityExpertMin = (Integer) application.get("InstpUniversityExpertMin");
		Map session = ActionContext.getContext().getSession();
		Map paraMap = new HashMap();
		paraMap.put("defaultYear", session.get("defaultYear"));
		Visitor visitor = (Visitor) session.get("visitor");
		
		Long begin = System.currentTimeMillis();
		if (visitor.getUserRight().contains("专家管理") || visitor.getUser().getIssuperuser() == 1){
			//1、专家统计信息
			long enall, enReview, ennReview, enReviewI, enReviewO, ennReviewI, ennReviewO;
			String hqla = "select COUNT(*) from Expert e where e.isReviewer = 1";//参评专家
			String hqlb = "select COUNT(*) from Expert e where e.isReviewer = 0";//退评专家
			String hqlc = " and e.expertType = 1 ";//内部专家
			enReview = baseService.count(hqla);// 参评专家总数
			ennReview = baseService.count(hqlb);// 退评专家总数
			enall = enReview + ennReview;// 专家总数
			enReviewI = baseService.count(hqla + hqlc);// 参评的内部专家总数
			enReviewO = enReview - enReviewI;// 参评的外部专家总数
			ennReviewI = baseService.count(hqlb + hqlc);// 退评的内部专家总数
			ennReviewO = ennReview - ennReviewI;// 退评的外部专家总数
			request.setAttribute("enall", enall);
			request.setAttribute("enReview", enReview);
			request.setAttribute("ennReview", ennReview);
			request.setAttribute("enReviewI", enReviewI);
			request.setAttribute("enReviewO", enReviewO);
			request.setAttribute("ennReviewI", ennReviewI);
			request.setAttribute("ennReviewO", ennReviewO);
		}
		
		//////////////////////////////////////////////////////////////////////////////////////////////////
		
		if (visitor.getUserRight().contains("一般项目管理") || visitor.getUser().getIssuperuser() == 1){
			//1、一般项目统计信息
			long pnall, pnReview, pnnReview;
			String hqld = "select COUNT(*) from ProjectApplication p where p.type = 'general' and p.year = :defaultYear and p.isReviewable = 1";//参评项目
			String hqle = "select COUNT(*) from ProjectApplication p where p.type = 'general' and p.year = :defaultYear and p.isReviewable = 0";//退评项目
			pnReview = baseService.count(hqld, paraMap); // 参加评审的项目总数
			pnnReview = baseService.count(hqle, paraMap);// 退出评审的项目总数
			pnall = pnReview + pnnReview;//项目总数
			request.setAttribute("pnall", pnall);
			request.setAttribute("pnReview", pnReview);
			request.setAttribute("pnnReview", pnnReview);
			
			//2、一般项目专家分配情况
			initConfig();//为各评审参数赋值
	//		paraMap.put("projectExpertNumber", projectExpertNumber);//每个项目所需的评审专家数
			//查询项目匹配上的专家个数（0,1,2,3,4,5,6,7…）、个数对应的项目数量
			List<Object> re = sqlDao.query("select cnt, count(cnt) from (select COUNT(*) cnt from T_Project_Application p, T_Project_Application_Review pr where p.c_type = 'general' and p.c_year = :defaultYear and p.c_id = pr.c_project_id group by p.c_id) group by cnt order by cnt", paraMap);
			int maxCols = 0;//最大列数
			//先计算最大的列数
			if (re != null && !re.isEmpty()) {
				Object[] o;
				int expertNum = 0;
				for (int i = 0, size = re.size(); i < size; i++) {
					o = (Object[]) re.get(i);
					expertNum = Integer.parseInt(o[0].toString());
					if (maxCols < expertNum) {
						maxCols = expertNum;
					}
				}
			}
			//再根据最大的列数初始化数据的数组penum
			long[] penum = new long[maxCols + 1];// 专家数为(0,1,2,3,4,5,6,7……)的项目个数
			penum[0] = pnReview;// 先将参加评审的项目但分配专家数为0的个数置为参加评审的项目个数
			if (re != null && !re.isEmpty()) {
				Object[] o;
				Integer expertNum = 0;
				for (int i = 0, size = re.size(); i < size; i++) {
					o = (Object[]) re.get(i);
					expertNum = Integer.parseInt(o[0].toString());
					penum[expertNum] = Integer.parseInt(o[1].toString());
				}
				for (int i = 1; i < penum.length; i++) {
					penum[0] -= penum[i];//最后计算未分配专家（即分配专家数为0）的项目个数
				}
			}
			request.setAttribute("maxCols", maxCols);// 专家数为(0,1,2,3,4,5,6,7……)的项目个数
			request.setAttribute("penum", penum);// 专家数为(0,1,2,3,4,5,6,7……)的项目个数
			//警告部分
	/*		long wnall = 0; //警告
			String hqli = "select p.warningReviewer, COUNT(*) from General p where p.year = :defaultYear and p.warningReviewer is not null group by p.warningReviewer";
			List<Object> num = baseService.query(hqli, paraMap);
			if (num != null && !num.isEmpty()) {
				Object[] o;
				for (int i = 0; i < num.size(); i++) {
					o = (Object[]) num.get(i);
					if (o[0] != null && o[1] != null) {
						wnall += Integer.parseInt(o[1].toString());
					}
				}
			}
			request.setAttribute("wnall", wnall);//警告总条数
			request.setAttribute("num", num);//警告信息
*/			
			//3、一般项目参评统计信息
			long runum = 0, renum = 0;// 参评高校数，参评专家数
			long[] uecnum = {0,0};//高校参评专家数小于5、大于5
			String hqlj = "select e.c_university_code, count(distinct e.c_id) from T_Expert e join T_Project_Application_Review pr on e.c_id = pr.c_reviewer_id and pr.c_type = 'general' where pr.c_year = :defaultYear group by e.c_university_code";
			runum = sqlDao.count(hqlj, paraMap);// 参评高校数
			List<Object[]> uncnum = sqlDao.query(hqlj, paraMap);
			for(Object[] o : uncnum) {
				int generalUniversityExpert = ((Number) o[1]).intValue();
				if(generalUniversityExpert < generalUniversityExpertMin) {
					uecnum[0]++;
				} else {
					uecnum[1]++;
				}
			}
			request.setAttribute("runum", runum);// 参评高校数
			request.setAttribute("uecnum", uecnum);//高校参评专家数小于5、大于5
			
			//含备评
			String hqlo = "select cnt, count(*) from (select count(*) cnt from T_Expert e, T_Project_Application_Review pr where pr.c_type = 'general' and pr.c_year = :defaultYear and e.c_id = pr.c_reviewer_id group by e.c_id) group by cnt";
			long[] epcnum = {0,0,0};// 参评项目数小于20、20至60、大于60的专家个数
			List<Object[]> excnum = sqlDao.query(hqlo, paraMap);
			for (Object[] o : excnum) {
				int reviewProjectNumber = ((Number) o[0]).intValue();
				int groupSize = ((Number) o[1]).intValue();
				if (reviewProjectNumber < generalExpertProjectMin) {//小于20
					epcnum[0] += groupSize;
				} else if (generalExpertProjectMin <= reviewProjectNumber && reviewProjectNumber <= generalExpertProjectMax) {//等于60
					epcnum[1] += groupSize;
				} else {
					epcnum[2] += groupSize;
				}
				renum += groupSize;//含备评参评专家总数
			}
			request.setAttribute("epcnum", epcnum);// 参评专家数(含备评)
			request.setAttribute("renum", renum);// 参评项目数小于20或大于60的专家个数(含备评)
		}
		//////////////////////////////////////////////////////////////////////////////////////////////////
		
		if (visitor.getUserRight().contains("基地项目管理") || visitor.getUser().getIssuperuser() == 1){
			//1、基地项目统计信息
			long instpPnall, instpPnReview, instpPnnReview;
			String hqld4instp = "select COUNT(*) from ProjectApplication p where p.type = 'instp' and p.year = :defaultYear and p.isReviewable = 1 ";//参评项目
			String hqle4instp = "select COUNT(*) from ProjectApplication p where p.type = 'instp' and p.year = :defaultYear and p.isReviewable = 0 ";//退评项目
			instpPnReview = baseService.count(hqld4instp, paraMap); // 参加评审的项目总数
			instpPnnReview = baseService.count(hqle4instp, paraMap);// 退出评审的项目总数
			instpPnall = instpPnReview + instpPnnReview;//项目总数
			request.setAttribute("instpPnall", instpPnall);
			request.setAttribute("instpPnReview", instpPnReview);
			request.setAttribute("instpPnnReview", instpPnnReview);
			
			//2、基地项目专家分配情况
	//		initConfig();//为各评审参数赋值
			//查询项目匹配上的专家个数（0,1,2,3,4,5,6,7…）、个数对应的项目数量
			List<Object> instpRe = sqlDao.query("select cnt, count(cnt) from (select COUNT(*) cnt from t_Project_Application p, T_Project_Application_Review pr where p.c_type = 'instp' and p.c_year = :defaultYear and p.c_id = pr.c_project_id group by p.c_id) group by cnt order by cnt", paraMap);
			int instpMaxCols = 0;//最大列数
			//先计算最大的列数
			if (instpRe != null && !instpRe.isEmpty()) {
				Object[] o;
				int expertNum = 0;
				for (int i = 0, size = instpRe.size(); i < size; i++) {
					o = (Object[]) instpRe.get(i);
					expertNum = Integer.parseInt(o[0].toString());
					if (instpMaxCols < expertNum) {
						instpMaxCols = expertNum;
					}
				}
			}
			//再根据最大的列数初始化数据的数组penum
			long[] instpPenum = new long[instpMaxCols + 1];// 专家数为(0,1,2,3,4,5,6,7……)的项目个数
			instpPenum[0] = instpPnReview;// 先将参加评审的项目但分配专家数为0的个数置为参加评审的项目个数
			if (instpRe != null && !instpRe.isEmpty()) {
				Object[] o;
				Integer expertNum = 0;
				for (int i = 0, size = instpRe.size(); i < size; i++) {
					o = (Object[]) instpRe.get(i);
					expertNum = Integer.parseInt(o[0].toString());
					instpPenum[expertNum] = Integer.parseInt(o[1].toString());
				}
				for (int i = 1; i < instpPenum.length; i++) {
					instpPenum[0] -= instpPenum[i];//最后计算未分配专家（即分配专家数为0）的项目个数
				}
			}
			request.setAttribute("instpMaxCols", instpMaxCols);// 专家数为(0,1,2,3,4,5,6,7……)的项目个数
			request.setAttribute("instpPenum", instpPenum);// 专家数为(0,1,2,3,4,5,6,7……)的项目个数
			
			
			//3、基地项目参评统计信息
			long instpRunum = 0, instpRenum = 0;// 参评高校数，参评专家数
			long[] instpUecnum = {0,0};//高校参评专家数小于3、大于3
			String instpHqlj = "select e.c_university_code, count(distinct e.c_id) from t_Expert e join T_Project_Application_Review pr on e.c_id = pr.c_reviewer_id and pr.c_type = 'instp' where pr.c_year = :defaultYear group by e.c_university_code";
			List<Object[]> instpuUncnum = sqlDao.query(instpHqlj, paraMap);
			for(Object[] o : instpuUncnum) {
				int instpUniversityExpert = ((Number) o[1]).intValue();
				if(instpUniversityExpert < instpUniversityExpertMin) {
					instpUecnum[0]++;
				} else {
					instpUecnum[1]++;
				}
			}
			instpRunum = sqlDao.count(instpHqlj, paraMap);// 参评高校数
			request.setAttribute("instpRunum", instpRunum);// 参评高校数			
			request.setAttribute("instpUecnum", instpUecnum);//高校参评专家数小于3、大于3
			
			//含备评
			String instpHqlo = "select cnt, count(*) from (select count(*) cnt from t_Expert e, T_Project_Application_Review pr where pr.c_type = 'instp' and pr.c_year = :defaultYear and e.c_id = pr.c_reviewer_id group by e.c_id) group by cnt";
			long[] instpEpcnum = {0,0,0};// 参评项目数小于20、20至60、大于60的专家个数
			List<Object[]> instpExcnum = sqlDao.query(instpHqlo, paraMap);
			for (Object[] o : instpExcnum) {
				int reviewProjectNumber = ((Number) o[0]).intValue();
				int groupSize = ((Number) o[1]).intValue();
				if (reviewProjectNumber < instpExpertProjectMin) {//小于20
					instpEpcnum[0] += groupSize;
				} else if (instpExpertProjectMin <= reviewProjectNumber && reviewProjectNumber <= instpExpertProjectMax) {//等于60
					instpEpcnum[1] += groupSize;
				} else {
					instpEpcnum[2] += groupSize;
				}
				instpRenum += groupSize;//含备评参评专家总数
			}
			request.setAttribute("instpEpcnum", instpEpcnum);// 参评专家数(含备评)
			request.setAttribute("instpRenum", instpRenum);// 参评项目数小于20或大于60的专家个数(含备评)
		}
		Long end = System.currentTimeMillis();
		System.out.println("总耗时 :" + (end - begin));
		
		//5、系统运行状态
		SystemConfig sf = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00015");
		int sysStatus = Integer.parseInt(sf.getValue());
		session.put("SystemStatus", sysStatus);
		
		return SUCCESS;
	}

	/**
	 * 首页
	 * @return 跳转成功
	 */
	public String toIndex() {
		Map session = ActionContext.getContext().getSession();
		String locale = ActionContext.getContext().getLocale().toString();
		session.put("locale", locale);
		return SUCCESS;
	}

	public String toSynData() {
		return SUCCESS;
	}
	/**
	 * 获取验证码
	 * @return 跳转成功
	 */
	public String rand() {
		RandomNumUtil rdnu = RandomNumUtil.Instance();
		// 取得带有随机字符串的图片
		this.setInputStream(rdnu.getImage());
		// 取得随机字符串放入HttpSession
		ActionContext.getContext().getSession().put("random", rdnu.getString());
		return SUCCESS;
	}
	
	/**
	 * 进入系统设置页面
	 * @return 跳转成功
	 */
	public String toConfig() {
		return SUCCESS;
	}

	/**
	 * 进入上传路径配置页面
	 * @return 跳转成功
	 */
	public String toConfigUpload() {
		return SUCCESS;
	}
	
	/**
	 * 配置上传路径
	 * @return 跳转成功
	 */
	public String configUpload() {
		// 更新上传图片路径
		SystemConfig sys = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00007");
		sys.setValue(UserPictureUploadPath);
		baseService.modify(sys);

		// 更新到application对象中
		SystemConfigLoader.load();
		
		return SUCCESS;
	}

	/**
	 * 上传路径校验
	 */
	public void validateConfigUpload() {
		if (UserPictureUploadPath == null || UserPictureUploadPath.isEmpty()) {
			this.addFieldError("knowError", "上传路径不得为空");
		}
	}

	/**
	 * 进入页面大小配置页面
	 * @return 跳转成功
	 */
	public String toConfigPageSize() {
		return SUCCESS;
	}

	/**
	 * 配置页面大小
	 * @return 跳转成功
	 */
	public String configPageSize() {
		// 更新每页显示记录条数
		SystemConfig sys = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00012");
		sys.setValue(rows);
		baseService.modify(sys);

		// 更新到application对象中
		SystemConfigLoader.load();
		return SUCCESS;
	}

	/**
	 * 页面大小设置
	 */
	public void validateConfigPageSize() {
		if (rows == null || rows.isEmpty()) {
			this.addFieldError("knowError", "每页显示记录数不得为空");
		}
	}
	
	
	
	/**
	 * 查看一般项目评审参数
	 * @return
	 */
	public String viewConfigGeneral() {
		general1 = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00001");
		general2 = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00002");
		general3 = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00003");
		general4 = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00004");
		general5 = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00005");
		general32 = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00032");
		generalReviewerImportedStartDate = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00024");
		generalReviewerImportedEndDate = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00025");
		generalReviewerBirthdayStartDate = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00028");
		generalReviewerBirthdayEndDate = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00029");
		return SUCCESS;
	}
	
	/**
	 * 查看Instp项目评审参数
	 * @return
	 */
	public String viewConfigInstp() {
		general16 = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00016");
		general17 = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00017");
		general18 = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00018");
		general19 = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00019");
		general20 = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00020");
		general33 = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00033");
		instpReviewerImportedStartDate = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00026");
		instpReviewerImportedEndDate= (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00027");
		instpReviewerBirthdayStartDate = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00030");
		instpReviewerBirthdayEndDate = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00031");
		return SUCCESS;
	}
	
	/**
	 * 进入业务配置
	 * @return SUCCESS跳转成功
	 */
	public String toConfigGeneral() {
		general1 = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00001");
		general2 = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00002");
		general3 = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00003");
		general4 = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00004");
		general5 = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00005");
		general32 = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00032");
		generalReviewerImportedStartDate = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00024");
		generalReviewerImportedEndDate = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00025");
		generalReviewerBirthdayStartDate = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00028");
		generalReviewerBirthdayEndDate = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00029");
//		general6 = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00006");
		return SUCCESS;
	}
	
	/**
	 * 进入Instp业务配置
	 * @return SUCCESS跳转成功
	 */
	public String toConfigInstp() {
		general16 = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00016");
		general17 = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00017");
		general18 = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00018");
		general19 = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00019");
		general20 = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00020");
		general33 = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00033");
		instpReviewerImportedStartDate = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00026");
		instpReviewerImportedEndDate= (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00027");
		instpReviewerBirthdayStartDate = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00030");
		instpReviewerBirthdayEndDate = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00031");
		return SUCCESS;
	}

	/**
	 * 修改一般项目配置
	 * @return
	 */
	public String configGeneral() {
		SystemConfig sc;
		sc = (SystemConfig) baseService.query(SystemConfig.class, general1.getId());
		sc.setValue(general1.getValue());
		baseService.modify(sc);
		sc = (SystemConfig) baseService.query(SystemConfig.class, general2.getId());
		sc.setValue(general2.getValue());
		baseService.modify(sc);
		sc = (SystemConfig) baseService.query(SystemConfig.class, general3.getId());
		sc.setValue(general3.getValue());
		baseService.modify(sc);
		sc = (SystemConfig) baseService.query(SystemConfig.class, general4.getId());
		sc.setValue(general4.getValue());
		baseService.modify(sc);
		sc = (SystemConfig) baseService.query(SystemConfig.class, general5.getId());
		sc.setValue(general5.getValue());
		baseService.modify(sc);
		sc = (SystemConfig) baseService.query(SystemConfig.class, general32.getId());
		sc.setValue(general32.getValue());
		baseService.modify(sc);
		sc = (SystemConfig) baseService.query(SystemConfig.class, generalReviewerImportedStartDate.getId());
		sc.setValue(generalReviewerImportedStartDate.getValue());
		baseService.modify(sc);
		sc = (SystemConfig) baseService.query(SystemConfig.class, generalReviewerImportedEndDate.getId());
		sc.setValue(generalReviewerImportedEndDate.getValue());
		baseService.modify(sc);
		try {
			sc = (SystemConfig) baseService.query(SystemConfig.class, generalReviewerBirthdayStartDate.getId());
			sc.setValue(generalReviewerBirthdayStartDate.getValue());
			baseService.modify(sc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		sc = (SystemConfig) baseService.query(SystemConfig.class, generalReviewerBirthdayEndDate.getId());
		sc.setValue(generalReviewerBirthdayEndDate.getValue());
		baseService.modify(sc);
		// 更新到application对象中
		SystemConfigLoader.load();
		return SUCCESS;
	}
	
	/**
	 * 修改Instp项目配置
	 * @return
	 */
	public String configInstp() {
		SystemConfig sc;
		sc = (SystemConfig) baseService.query(SystemConfig.class, general16.getId());
		sc.setValue(general16.getValue());
		baseService.modify(sc);
		sc = (SystemConfig) baseService.query(SystemConfig.class, general17.getId());
		sc.setValue(general17.getValue());
		baseService.modify(sc);
		sc = (SystemConfig) baseService.query(SystemConfig.class, general18.getId());
		sc.setValue(general18.getValue());
		baseService.modify(sc);
		sc = (SystemConfig) baseService.query(SystemConfig.class, general19.getId());
		sc.setValue(general19.getValue());
		baseService.modify(sc);
		sc = (SystemConfig) baseService.query(SystemConfig.class, general20.getId());
		sc.setValue(general20.getValue());
		baseService.modify(sc);
		sc = (SystemConfig) baseService.query(SystemConfig.class, general33.getId());
		sc.setValue(general33.getValue());
		baseService.modify(sc);
		sc = (SystemConfig) baseService.query(SystemConfig.class, instpReviewerImportedStartDate.getId());
		sc.setValue(instpReviewerImportedStartDate.getValue());
		baseService.modify(sc);
		sc = (SystemConfig) baseService.query(SystemConfig.class, instpReviewerImportedEndDate.getId());
		sc.setValue(instpReviewerImportedEndDate.getValue());
		baseService.modify(sc);
		sc = (SystemConfig) baseService.query(SystemConfig.class, instpReviewerBirthdayStartDate.getId());
		sc.setValue(instpReviewerBirthdayStartDate.getValue());
		baseService.modify(sc);
		sc = (SystemConfig) baseService.query(SystemConfig.class, instpReviewerBirthdayEndDate.getId());
		sc.setValue(instpReviewerBirthdayEndDate.getValue());
		baseService.modify(sc);
		// 更新到application对象中
		SystemConfigLoader.load();
		return SUCCESS;
	}
	
	/**
	 * 进入邮件参数配置
	 * @return SUCCESS跳转成功
	 */
	public String toConfigMail() {
		total = ((SystemConfig) baseService.query(SystemConfig.class, "sysconfig00021")).getValue();
		template = ((SystemConfig) baseService.query(SystemConfig.class, "sysconfig00022")).getValue();
		waitTime = ((SystemConfig) baseService.query(SystemConfig.class, "sysconfig00023")).getValue();
		return SUCCESS;
	}
	
	/**
	 * 配置邮件发送的参数
	 * @return
	 */
	public String configMail() {
		SystemConfig sys1 = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00021");
		sys1.setValue(total);
		SystemConfig sys = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00022");
		sys.setValue(template);
		SystemConfig sys2 = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00023");
		sys2.setValue(waitTime);
		baseService.modify(sys);
		baseService.modify(sys1);
		baseService.modify(sys2);
		// 更新到application对象中
		SystemConfigLoader.load();
		return SUCCESS;
	}
	/**
	 * 进入年份配置
	 * @return SUCCESS跳转成功
	 */
	public String toConfigYear() {
		allYears = ((SystemConfig) baseService.query(SystemConfig.class, "sysconfig00013")).getValue();
		defaultYear = Integer.parseInt(((SystemConfig) baseService.query(SystemConfig.class, "sysconfig00014")).getValue());
		return SUCCESS;
	}
	
	/**
	 * 进入系统状态配置
	 * @return SUCCESS跳转成功
	 */
	public String toConfigSystemStatus() {
		SystemConfig sf = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00015");
		int sysStatus = Integer.parseInt(sf.getValue());
		systemStatus = sysStatus == 1 ? true : false;
		return SUCCESS;
	}
	
	
	/**
	 * 系统状态配置
	 * @return SUCCESS跳转成功
	 */
	public String configSystemStatus() {
//		System.out.println(systemStatus);
		SystemConfig sf = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00015");
		if(systemStatus)
			sf.setValue("1");
		else {
			//TODO 剔除非超级用户session
			sf.setValue("0");
		}
		baseService.modify(sf);

		// 更新到application对象中
		SystemConfigLoader.load();
		return SUCCESS;
	}
	
	/**
	 * 修改年份配置
	 * @return SUCCESS跳转成功
	 */
	public String configYear() {
		SystemConfig sc = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00013");
		sc.setValue(allYears);
		baseService.modify(sc);

		sc = (SystemConfig) baseService.query(SystemConfig.class, "sysconfig00014");
		sc.setValue(defaultYear.toString());
		baseService.modify(sc);

		// 更新到application对象中
		SystemConfigLoader.load();

		return SUCCESS;
	}
	
	/**
	 * 修改默认的年份
	 * @return SUCCESS跳转成功
	 */
	public String changeYear() {
		Map session = ActionContext.getContext().getSession();
		Map application = ActionContext.getContext().getApplication();
		String[] allYears = (String[]) application.get("allYears");
		if (Arrays.asList(allYears).contains(defaultYear.toString())) {
			session.put("defaultYear", defaultYear);
		}
		return null;
	}

	/**
	 * 修改默认的年份
	 * @return SUCCESS跳转成功
	 */
	public String changeProjectType() {
		Map session = ActionContext.getContext().getSession();
		Map application = ActionContext.getContext().getApplication();
		String[] allProjectTypes = (String[]) application.get("allProjectTypes");
		if (Arrays.asList(allProjectTypes).contains(defaultProjectType.toString())) {
			session.put("defaultProjectType", defaultProjectType);
		}
		return null;
	}
	
	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String getUserPictureUploadPath() {
		return UserPictureUploadPath;
	}
	public void setUserPictureUploadPath(String userPictureUploadPath) {
		UserPictureUploadPath = userPictureUploadPath;
	}
	public String getRows() {
		return rows;
	}
	public void setRows(String rows) {
		this.rows = rows;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public SystemConfig getGeneral1() {
		return general1;
	}
	public void setGeneral1(SystemConfig general1) {
		this.general1 = general1;
	}
	public SystemConfig getGeneral2() {
		return general2;
	}
	public void setGeneral2(SystemConfig general2) {
		this.general2 = general2;
	}
	public SystemConfig getGeneral3() {
		return general3;
	}
	public void setGeneral3(SystemConfig general3) {
		this.general3 = general3;
	}
	public SystemConfig getGeneral4() {
		return general4;
	}
	public void setGeneral4(SystemConfig general4) {
		this.general4 = general4;
	}
	public SystemConfig getGeneral5() {
		return general5;
	}
	public void setGeneral5(SystemConfig general5) {
		this.general5 = general5;
	}
	public SystemConfig getGeneral16() {
		return general16;
	}
	public void setGeneral16(SystemConfig general16) {
		this.general16 = general16;
	}
	public SystemConfig getGeneral17() {
		return general17;
	}
	public void setGeneral17(SystemConfig general17) {
		this.general17 = general17;
	}
	public SystemConfig getGeneral18() {
		return general18;
	}
	public void setGeneral18(SystemConfig general18) {
		this.general18 = general18;
	}
	public SystemConfig getGeneral19() {
		return general19;
	}
	public void setGeneral19(SystemConfig general19) {
		this.general19 = general19;
	}
	public SystemConfig getGeneral20() {
		return general20;
	}
	public void setGeneral20(SystemConfig general20) {
		this.general20 = general20;
	}
	public SystemConfig getGeneral6() {
		return general6;
	}
	public void setGeneral6(SystemConfig general6) {
		this.general6 = general6;
	}
	public String getAllYears() {
		return allYears;
	}
	public void setAllYears(String allYears) {
		this.allYears = allYears;
	}
	public Integer getDefaultYear() {
		return defaultYear;
	}
	public void setDefaultYear(Integer defaultYear) {
		this.defaultYear = defaultYear;
	}
	public boolean isSystemStatus() {
		return systemStatus;
	}
	public void setSystemStatus(boolean systemStatus) {
		this.systemStatus = systemStatus;
	}
	public String getAllProjectTypes() {
		return allProjectTypes;
	}
	public void setAllProjectTypes(String allProjectTypes) {
		this.allProjectTypes = allProjectTypes;
	}
	public String getDefaultProjectType() {
		return defaultProjectType;
	}
	public void setDefaultProjectType(String defaultProjectType) {
		this.defaultProjectType = defaultProjectType;
	}

	@Override
	public String[] column() {
		return null;
	}
	@Override
	public String pageName() {
		return null;
	}
	@Override
	public String dateFormat() {
		return null;
	}
	@Override
	public Object[] simpleSearchCondition() {
		return null;
	}
	@Override
	public Object[] advSearchCondition() {
		return null;
	}
	@Override
	public String pageBufferId() {
		return null;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(String waitTime) {
		this.waitTime = waitTime;
	}

	public SystemConfig getGeneralReviewerImportedStartDate() {
		return generalReviewerImportedStartDate;
	}

	public void setGeneralReviewerImportedStartDate(SystemConfig generalReviewerImportedStartDate) {
		this.generalReviewerImportedStartDate = generalReviewerImportedStartDate;
	}

	public SystemConfig getGeneralReviewerImportedEndDate() {
		return generalReviewerImportedEndDate;
	}

	public void setGeneralReviewerImportedEndDate(SystemConfig generalReviewerImportedEndDate) {
		this.generalReviewerImportedEndDate = generalReviewerImportedEndDate;
	}

	public SystemConfig getInstpReviewerImportedStartDate() {
		return instpReviewerImportedStartDate;
	}

	public void setInstpReviewerImportedStartDate(SystemConfig instpReviewerImportedStartDate) {
		this.instpReviewerImportedStartDate = instpReviewerImportedStartDate;
	}

	public SystemConfig getInstpReviewerImportedEndDate() {
		return instpReviewerImportedEndDate;
	}

	public void setInstpReviewerImportedEndDate(SystemConfig instpReviewerImportedEndDate) {
		this.instpReviewerImportedEndDate = instpReviewerImportedEndDate;
	}

	public SystemConfig getGeneralReviewerBirthdayStartDate() {
		return generalReviewerBirthdayStartDate;
	}

	public void setGeneralReviewerBirthdayStartDate(SystemConfig generalReviewerBirthdayStartDate) {
		this.generalReviewerBirthdayStartDate = generalReviewerBirthdayStartDate;
	}

	public SystemConfig getGeneralReviewerBirthdayEndDate() {
		return generalReviewerBirthdayEndDate;
	}

	public void setGeneralReviewerBirthdayEndDate(SystemConfig generalReviewerBirthdayEndDate) {
		this.generalReviewerBirthdayEndDate = generalReviewerBirthdayEndDate;
	}

	public SystemConfig getInstpReviewerBirthdayStartDate() {
		return instpReviewerBirthdayStartDate;
	}

	public void setInstpReviewerBirthdayStartDate(SystemConfig instpReviewerBirthdayStartDate) {
		this.instpReviewerBirthdayStartDate = instpReviewerBirthdayStartDate;
	}

	public SystemConfig getInstpReviewerBirthdayEndDate() {
		return instpReviewerBirthdayEndDate;
	}

	public void setInstpReviewerBirthdayEndDate(SystemConfig instpReviewerBirthdayEndDate) {
		this.instpReviewerBirthdayEndDate = instpReviewerBirthdayEndDate;
	}

	public SystemConfig getGeneral32() {
		return general32;
	}

	public void setGeneral32(SystemConfig general32) {
		this.general32 = general32;
	}

	public SystemConfig getGeneral33() {
		return general33;
	}

	public void setGeneral33(SystemConfig general33) {
		this.general33 = general33;
	}

	public SqlBaseDao getSqlDao() {
		return sqlDao;
	}

	public void setSqlDao(SqlBaseDao sqlDao) {
		this.sqlDao = sqlDao;
	}
}
