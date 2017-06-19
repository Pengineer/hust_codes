package csdc.tool.execution;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Expert;
import csdc.bean.Mail;
import csdc.bean.ProjectApplication;
import csdc.bean.ProjectApplicationReview;
import csdc.bean.ProjectApplicationReviewUpdate;
import csdc.dao.IHibernateBaseDao;
import csdc.dao.JdbcDao;
import csdc.service.imp.ExpertService;
import csdc.tool.beanutil.mergeStrategy.Append;
import csdc.tool.execution.importer.Tools;
import csdc.tool.mail.MailController;

public class Test extends Execution {
	
	@Autowired
	private IHibernateBaseDao dao;
	
	@Autowired 
	private ExpertService expertService;
	
	@Autowired
	private MailController mailController;
	
	private JdbcDao smdbDao;

	@Autowired
	private TransactionTemplate transactionTemplate;

	//专家检索工具
	Tools tools = null;
	private Set<String> existingExperts;//本年度专家库中专家申报项目的专家统计，记录 "申请者姓名+申请者id"


	public Test() {
		
	}
	
	public Test(IHibernateBaseDao dao ) {

		this.dao = dao;
	}
	
	@Override
	public void work() throws Throwable {

		//往年修复一般申报年份C_GENERAL_APPLY_YEARS
		work_fix_generalApplyYear();
	}
	
	//统计贺敏项目符合条件的两个专家来自同一所高校信息
	private void test2() {
		//根据匹配
		String hqleffectPAR = "select par from ProjectApplicationReview par where par.matchTime > to_date('2015-05-20','yyyy-mm-dd')  ";
		List<ProjectApplicationReview>	parList = dao.query(hqleffectPAR);
		
		for (ProjectApplicationReview projectApplicationReview : parList) {
			dao.delete(projectApplicationReview);
		}
		
		String hqleffectPARU = "select paru from ProjectApplicationReviewUpdate paru where paru.matchTime > to_date('2015-05-20','yyyy-mm-dd')  ";
		List<ProjectApplicationReviewUpdate> paruList = dao.query(hqleffectPARU);
		
		for (ProjectApplicationReviewUpdate projectApplicationReviewUpdate : paruList) {
			//只删除 ”不是删除的更新“
			if (projectApplicationReviewUpdate.getIsAdd() != 0) {
				dao.delete(projectApplicationReviewUpdate);
			}
			
		}
		
		//可是是否删除影响
		//从新执行影响修改程序
		
		
	}
	//测试专家匹配信息
	@Transactional
	private void test() {
		List<ProjectApplication> enProjectApplications = new ArrayList<ProjectApplication>();
		Date begin = new Date();
		String hql4ExistingMatch = "select pa from ProjectApplication pa where pa.type = 'general' and pa.year = 2015 and pa.isReviewable = 1 ";
		enProjectApplications = dao.query(hql4ExistingMatch);
		List<String> testCommonUResultList = new LinkedList<String>();
		for (int i = 0; i < enProjectApplications.size(); i++) {
			ProjectApplication paProjectApplication = enProjectApplications.get(i);
			Set<ProjectApplicationReview> prSet = paProjectApplication.getApplicationReview();
			int prSize = prSet.size();
			Set<String> reviewsUniversityCodeSet = new HashSet<String>();
			for (ProjectApplicationReview paReviewOne : prSet) {
				String universityCode = paReviewOne.getReviewer().getUniversityCode();
				reviewsUniversityCodeSet.add(universityCode);
			}
			if (prSize != reviewsUniversityCodeSet.size()) {
				testCommonUResultList.add(paProjectApplication.getId() +"("+ paProjectApplication.getProjectName() +")" );
			}
		}
		System.out.println("查询[已有匹配]用时: " + (new Date().getTime() - begin.getTime()) + "ms");
		System.out.println("查出[已有匹配]数目: " + enProjectApplications.size() + "\n");
		
		System.out.println("查出[项目评审专家高校相同]数目: " + testCommonUResultList.size() + "\n");
		System.out.println("查出[项目评审专家高校相同]内容如下: ");
		
		for (int i = 0; i < testCommonUResultList.size(); i++) {
			System.out.println(testCommonUResultList.get(i));
		}
		
	}
	
	/**
	 * 修复2015年度一般项目负责人在smas专家库中专家往年申报年份字段信息程序
	 * @throws Throwable
	 */
	public void work_fix_generalApplyYear() throws Throwable {
		tools = new Tools(dao);
		tools.initExpertMap();
		existingExperts = new HashSet<String>();
		//1.置空2015年一般项目负责人对应的专家表中的专家，对应的一般项目申报年份， 并获取对应的受到影响的专家集合
		//2.针对该受影响的往年专家集合，分别遍历2010-2015且参评的一般项目(不含专项任务项目，且只要申报及追加对应字段)
		// 如果当年项目负责人在“受影响”的专家集合中，则添加当年年份字段信息。
		//说明：只核查并修复2015年份一般项目负责人对应专家表中的专家
		// 2015极其以后年份的一般项目不含有“专项任务”项目。
		
		String hql = "select pa from ProjectApplication pa where pa.year = 2015 and pa.type = 'general' ";
		List<ProjectApplication> result = dao.query(hql);
		Set effect2015Experts = new HashSet<Expert>();
		for (int i = 0; i <result.size(); i++) {
			ProjectApplication projectApplication = result.get(i);
			String dirctorName = projectApplication.getDirector();
			String universityCode = projectApplication.getUniversityCode();
			//判断当前项目负责人是不是项目专家，如果是，则视情况修改往年申报字段
			Expert expert = tools.getExpert(dirctorName, universityCode);
			if (expert != null) {
				expert.setGeneralApplyYears("");
				dao.modify(expert);
				effect2015Experts.add(expert);
				
			}
		}
		System.out.println(existingExperts.size());
		existingExperts.clear();
		System.out.println("2015年一般项目申报项目负责人对应项目专家有：" + effect2015Experts.size());
		
		Append append2 = new Append("\\s*;\\s*", "; ", false);//放在后面
		//修复2010年一般项目申报（且非“专项任务项目”）（且不管参评与否）负责人对应的专家，且在2015受影响专家中，的一般项目申报年份追加
		String hqlreview2010 = "select pa from ProjectApplication pa where pa.year = 2010 and pa.type = 'general' and pa.projectType !='专项任务' ";
		List<ProjectApplication> result2010 = dao.query(hqlreview2010);
		for (int i = 0; i < result2010.size(); i++) {
			ProjectApplication projectApplication = result2010.get(i);
			String dirctorName = projectApplication.getDirector();
			String universityCode = projectApplication.getUniversityCode();
			String idCard = projectApplication.getIdcard();
			Expert expert = null;
			if(idCard == null) {
				expert = tools.getExpert(dirctorName, universityCode);
			} else {
				expert = tools.getExpert(dirctorName, universityCode, idCard);
				if (expert == null) {
					expert = tools.getExpert(dirctorName, universityCode);
				}
			}
			if (expert != null && effect2015Experts.contains(expert)) {//当年前专家表有对应专家且在2015年受影响的专家中
				existingExperts.add(dirctorName + "/" + expert.getId());
				System.out.println(dirctorName + "/" + expert.getId());
				//追加当前申请年份
				String v1 = expert.getGeneralApplyYears();
				String v2 = "2010";;
				String end = append2.merge(v1, v2);
				expert.setGeneralApplyYears(end);
				dao.modify(expert);
			}
		}
		System.out.println("有2010当前年份申请一般项目且非专项任务项目，参评项目的专家：" + existingExperts.size());
		existingExperts.clear();
		
		//修复2011年一般项目（且非“专项任务项目”）（且不管参评与否）负责人对应的专家，且在2015受影响专家中，的一般项目申报年份追加
		String hqlreview2011 = "select pa from ProjectApplication pa where pa.year = 2011 and pa.type = 'general' and pa.projectType !='专项任务' ";
		List<ProjectApplication> result2011 = dao.query(hqlreview2011);
		for (int i = 0; i < result2011.size(); i++) {
			ProjectApplication projectApplication = result2011.get(i);
			String dirctorName = projectApplication.getDirector();
			String universityCode = projectApplication.getUniversityCode();
			String idCard = projectApplication.getIdcard();
			Expert expert = null;
			if(idCard == null) {
				expert = tools.getExpert(dirctorName, universityCode);
			} else {
				expert = tools.getExpert(dirctorName, universityCode, idCard);
				if (expert == null) {
					expert = tools.getExpert(dirctorName, universityCode);
				}
			}
			if (expert != null && effect2015Experts.contains(expert)) {//当年前专家表有对应专家且在2015年受影响的专家中
				existingExperts.add(dirctorName + "/" + expert.getId());
				System.out.println(dirctorName + "/" + expert.getId());
				//追加当前申请年份
				String v1 = expert.getGeneralApplyYears();
				String v2 = "2011";
				String end = append2.merge(v1, v2);
				expert.setGeneralApplyYears(end);
				dao.modify(expert);
			}
		}
		System.out.println("有2011当前年份申请一般项目且非专项任务项目，参评项目的专家：" +existingExperts.size());
		existingExperts.clear();
		
		//修复2012年一般项目（且非“专项任务项目”）（且不管参评与否）负责人对应的专家，且在2015受影响专家中，的一般项目申报年份追加
		String hqlreview2012 = "select pa from ProjectApplication pa where pa.year = 2012 and pa.type = 'general' and pa.projectType !='专项任务' ";
		List<ProjectApplication> result2012 = dao.query(hqlreview2012);
		for (int i = 0; i < result2012.size(); i++) {
			ProjectApplication projectApplication = result2012.get(i);
			String dirctorName = projectApplication.getDirector();
			String universityCode = projectApplication.getUniversityCode();
			String idCard = projectApplication.getIdcard();
			Expert expert = null;
			if(idCard == null) {
				expert = tools.getExpert(dirctorName, universityCode);
			} else {
				expert = tools.getExpert(dirctorName, universityCode, idCard);
				if (expert == null) {
					expert = tools.getExpert(dirctorName, universityCode);
				}
			}
			if (expert != null && effect2015Experts.contains(expert)) {//当年前专家表有对应专家且在2015年受影响的专家中
				existingExperts.add(dirctorName + "/" + expert.getId());
				System.out.println(dirctorName + "/" + expert.getId());
				//追加当前申请年份
				String v1 = expert.getGeneralApplyYears();
				String v2 = "2012";
				String end = append2.merge(v1, v2);
				expert.setGeneralApplyYears(end);
				dao.modify(expert);
			}
		}
		System.out.println("有2012当前年份申请一般项目且非专项任务项目，参评项目的专家：" +existingExperts.size());
		existingExperts.clear();
		
		//修复2013年一般项目（且非“专项任务项目”）（且不管参评与否）负责人对应的专家，且在2015受影响专家中，的一般项目申报年份追加
		String hqlreview2013 = "select pa from ProjectApplication pa where pa.year = 2013 and pa.type = 'general' and pa.projectType !='专项任务' ";
		List<ProjectApplication> result2013 = dao.query(hqlreview2013);
		for (int i = 0; i < result2013.size(); i++) {
			ProjectApplication projectApplication = result2013.get(i);
			String dirctorName = projectApplication.getDirector();
			String universityCode = projectApplication.getUniversityCode();
			String idCard = projectApplication.getIdcard();
			Expert expert = null;
			if(idCard == null) {
				expert = tools.getExpert(dirctorName, universityCode);
			} else {
				expert = tools.getExpert(dirctorName, universityCode, idCard);
				if (expert == null) {
					expert = tools.getExpert(dirctorName, universityCode);
				}
			}
			if (expert != null && effect2015Experts.contains(expert)) {//当年前专家表有对应专家且在2015年受影响的专家中
				existingExperts.add(dirctorName + "/" + expert.getId());
				System.out.println(dirctorName + "/" + expert.getId());
				//追加当前申请年份
				String v1 = expert.getGeneralApplyYears();
				String v2 = "2013";
				String end = append2.merge(v1, v2);
				expert.setGeneralApplyYears(end);
				dao.modify(expert);
			}
		}
		System.out.println("有2013当前年份申请一般项目且非专项任务项目，参评项目的专家：" +existingExperts.size());
		existingExperts.clear();
		
		//修复2014年一般项目（且非“专项任务项目”）（且不管参评与否）负责人对应的专家，且在2015受影响专家中，的一般项目申报年份追加
		String hqlreview2014 = "select pa from ProjectApplication pa where pa.year = 2014 and pa.type = 'general' and pa.projectType !='专项任务' ";
		List<ProjectApplication> result2014 = dao.query(hqlreview2014);
		for (int i = 0; i < result2014.size(); i++) {
			ProjectApplication projectApplication = result2014.get(i);
			String dirctorName = projectApplication.getDirector();
			String universityCode = projectApplication.getUniversityCode();
			String idCard = projectApplication.getIdcard();
			Expert expert = null;
			if(idCard == null) {
				expert = tools.getExpert(dirctorName, universityCode);
			} else {
				expert = tools.getExpert(dirctorName, universityCode, idCard);
				if (expert == null) {
					expert = tools.getExpert(dirctorName, universityCode);
				}
			}
			if (expert != null && effect2015Experts.contains(expert)) {//当年前专家表有对应专家且在2015年受影响的专家中
				existingExperts.add(dirctorName + "/" + expert.getId());
				System.out.println(dirctorName + "/" + expert.getId());
				//追加当前申请年份
				String v1 = expert.getGeneralApplyYears();
				String v2 = "2014";
				String end = append2.merge(v1, v2);
				expert.setGeneralApplyYears(end);
				dao.modify(expert);
			}
		}
		System.out.println("有2014当前年份申请一般项目且非专项任务项目的专家：" +existingExperts.size());
		existingExperts.clear();
		
		//修复2015年一般项目（且非“专项任务项目”）（且不管参评与否）负责人对应的专家，且在2015受影响专家中，的一般项目申报年份追加
		String hqlreview2015 = "select pa from ProjectApplication pa where pa.year = 2015 and pa.type = 'general' and pa.projectType !='专项任务' ";
		List<ProjectApplication> result2015 = dao.query(hqlreview2015);
		for (int i = 0; i < result2015.size(); i++) {
			ProjectApplication projectApplication = result2015.get(i);
			String dirctorName = projectApplication.getDirector();
			String universityCode = projectApplication.getUniversityCode();
			String idCard = projectApplication.getIdcard();
			Expert expert = null;
			if(idCard == null) {
				expert = tools.getExpert(dirctorName, universityCode);
			} else {
				expert = tools.getExpert(dirctorName, universityCode,idCard);
				if (expert == null) {
					expert = tools.getExpert(dirctorName, universityCode);
					if (expert != null) {
						int stop = 0; 
						stop = 1;
					}
				}
			}
			
			if (expert != null && effect2015Experts.contains(expert)) {//当年前专家表有对应专家且在2015年受影响的专家中
				existingExperts.add(dirctorName + "/" + expert.getId());
				System.out.println(dirctorName + "/" + expert.getId());
				//追加当前申请年份
				String v1 = expert.getGeneralApplyYears();
				String v2 = "2015";
				String end = append2.merge(v1, v2);
				expert.setGeneralApplyYears(end);
				dao.modify(expert);
			}
		}
		System.out.println("有2015当前年份申请一般项目且非专项任务项目，参评项目的专家：" +existingExperts.size());
		existingExperts.clear();
	}
	
	private void 测试smdbDao() {
		List<String[]> data = smdbDao.query("select * from t_system_option where rownum < 100");
		for (String[] strings : data) {
			for (String string : strings) {
				System.out.print(string + " ");
			}
			System.out.println();
		}
	}
		
	private void add() throws InterruptedException {
		
		Map<String, String> univCodeNameMap = (Map<String, String>) ActionContext.getContext().getApplication().get("univCodeNameMap");
		for (Object map : univCodeNameMap.keySet()) {
//			String realpath = expertService.createExpertZip((String) map);
			final Mail mail = new Mail();
			mail.setReplyTo("moesk@bnu.edu.cn");// 认证地址
			mail.setBody("dqwan");
			mail.setTo("812846919@qq.com");
			mail.setIsHtml(1);
			mail.setCreateDate(new Date());
//			mail.setAttachment(realpath);
			mail.setAttachmentName((String) map + univCodeNameMap.get((String) map) + "专家表.zip");
			dao.add(mail);
		}
		
//		final List<Mail> mails = new ArrayList<Mail>();
//		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//			protected void doInTransactionWithoutResult(TransactionStatus status) {
//				try {
//					for (int i = 0; i < 2; i++) {
//						final Mail mail = new Mail();
//						mail.setReplyTo("serv@csdc.info");// 认证地址
//						mail.setBody("dqwan"+i);
//						mail.setTo("812846919@qq.com");
//						mail.setIsHtml(1);
//						mail.setCreateDate(new Date());
//						mail.setAttachment("/upload/mail/201311");
//						mail.setAttachmentName("");
//						dao.add(mail);
//					}
//				} catch (Exception ex) {
//					status.setRollbackOnly();
//				}
//			}
//		});
//		for (Mail mail : mails) {
//			mailController.send(mail.getId());
//			Thread.sleep(5000);
//		}

	}
	
	private void 冯成林() {
		String hql = "select p, (select count(*) from GeneralReviewer pr where pr.project.id = p.id) as ct, u.name from Project p, University u where p.year = :defaultYear and p.universityCode = u.code ";
		long begin = System.currentTimeMillis();
		List list = dao.query(hql, 0, 100);
		System.out.println(list.size());
		System.out.println(System.currentTimeMillis() - begin + "ms");
	}

	
	
	public void setSmdbDao(JdbcDao smdbDao) {
		this.smdbDao = smdbDao;
	}
}
