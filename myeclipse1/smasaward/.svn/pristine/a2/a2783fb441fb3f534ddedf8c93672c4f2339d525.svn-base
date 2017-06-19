package csdc.tool.execution.importer;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.ProjectApplication;
import csdc.bean.User;
import csdc.tool.execution.finder.GeneralFinder;
import csdc.tool.reader.JxlExcelReader;

/**
 * 入库：《20150508_教育部人文社会科学研究一般项目初审结果终版（社科服务中心审定）_修正导入.xls》
 * 出版项目：《2015年教育部人文社会科学研究一般项目出版课题申报名单.xls》
 * @author liangjian
 *
 *	其他未列项目用SQL设置初审通过相关信息  + 参评
 */
public class GeneralApplicationMinistryReview2015Import extends Importer {

	private JxlExcelReader reader1;
	
	private JxlExcelReader reader2;
	
	@Autowired
	private GeneralFinder generalFinder;
	
	@Autowired
	private Tools tool;
	
	@Override
	protected void work() throws Throwable {
//		ImportDate();
		publishProject();
	}
	
	public void ImportDate() throws Exception {
		User auditor = (User) dao.queryUnique("select user from User user where user.chinesename='范明宇'");
		reader1.readSheet(0);
		Set<String> Msg = new HashSet<String>();
		int total = reader1.getRowNumber();
		int current=0;
		while(next(reader1)){
			System.out.println((++current) + "/" + total + ":" + D);
			ProjectApplication application = generalFinder.findGeneral(D, E, 2015, false);
			if(application == null) {
				Msg.add(D);
				continue;
			}
			application.setMinistryAuditDate(tool.getDate("2015-05-08"));
			application.setMinistryAuditOpinion(B);
			application.setMinistryAuditor(auditor);
			application.setMinistryAuditorName("范明宇");
			application.setMinistryAuditResult(1);
			application.setMinistryAuditStatus(3);
			
			application.setFinalAuditResult(1);
			application.setFinalAuditOpinionFeedback(B);//正式的退评原因需要公示反馈
			application.setFinalAuditDate(tool.getDate("2015-05-08"));
			application.setFinalAuditorName("范明宇");
			application.setFinalAuditor(auditor);
			
			application.setIsReviewable(0);
			application.setReason("初审不通过：" + B);
			
			dao.modify(application);
		}
		if(Msg.size() > 0) {
			System.out.println(Msg.toString().replaceAll(",\\s+", "\n"));
		}
		System.out.println("over");
	}
	
	public void publishProject() throws Exception {
		reader2.readSheet(0);
		Set<String> Msg = new HashSet<String>();
		int total = reader2.getRowNumber();
		int current=0;
		while(next(reader2)){
			System.out.println((++current) + "/" + total + ":" + C);
			ProjectApplication application = generalFinder.findGeneral(C, E, 2015, false);
			if(application == null) {
				Msg.add(C);
				continue;
			}
			application.setIsReviewable(0);
			application.setReason("所有出版项目不参评");
			dao.modify(application);
		}
		if(Msg.size() > 0) {
			System.out.println(Msg.toString().replaceAll(",\\s+", "\n"));
		}
		System.out.println("over");
	}
	
	public GeneralApplicationMinistryReview2015Import() {
	}
	
	public GeneralApplicationMinistryReview2015Import(String fileName1, String fileName2) {
		reader1 = new JxlExcelReader(fileName1);
		reader2 = new JxlExcelReader(fileName2);
	}

}
