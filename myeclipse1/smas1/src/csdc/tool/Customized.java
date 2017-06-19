package csdc.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import csdc.bean.Expert;
import csdc.bean.ProjectApplication;
import csdc.bean.ProjectApplicationReview;
import csdc.bean.SystemOption;
import csdc.service.IBaseService;
import csdc.service.IGeneralService;
import csdc.tool.execution.importer.Project2011ExpertImporter;
import csdc.tool.execution.importer.Project2012ExpertImporter;
import csdc.tool.execution.importer.GeneralApplication2011Importer;
import csdc.tool.execution.importer.GeneralApplication2012Importer;
import csdc.tool.execution.importer.InstpApplication2011Importer;
import csdc.tool.execution.importer.UniversityImporter;
import csdc.tool.tableKit.imp.DisciplineKit;
import csdc.tool.tableKit.imp.UniversityKit;
import csdc.tool.widget.ExpertMerger;

/**
 * 需要独立执行的方法的容器，专用于不从struts、action之流触发的操作
 * @author xuhan
 *
 */
public class Customized {
	
	static public IBaseService baseService;
	static public IGeneralService generalService;

	public static void doSomething() throws Exception {
		new ExpertMerger().start();
	}
	
	
	/**
	 * 导入2012年一般项目申请数据
	 */
	public static void importGeneralApplication2012() throws Exception {
		//File xls = new File("D:\\csdc\\一般项目\\修正导入\\2012\\20111110_新增数据.xls");
		File xls = new File("D:\\csdc\\一般项目\\修正导入\\2012\\20111026_2012一般申报项目-(高校通过、主管单位通过)共33955条_修正导入.xls");
		new GeneralApplication2012Importer(xls).start();
	}

	/**
	 * 导入2012年专家库数据
	 */
	public static void importExpert2012() throws Exception {
		File xls = new File("D:\\csdc\\一般项目\\修正导入\\2012\\20111111_修复学科.xls");
		//File xls = new File("D:\\csdc\\一般项目\\修正导入\\2012\\20111111_2012年专家库联系方式补充.xls");
		//File xls = new File("D:\\csdc\\一般项目\\修正导入\\2012\\20111103_2012年专家库整理合并_整合修正导入.xls");
		new Project2012ExpertImporter(xls).start();
	}

	/**
	 * 导入2011年基地项目申请数据
	 */
	public static void importInstpApplication2011() throws Exception {
		File xls = new File("D:\\csdc\\20110610_2011年重点研究基地项目分组.xls");
		InstpApplication2011Importer importer = new InstpApplication2011Importer(xls);
		importer.work();
	}

	/**
	 * 导入2011.01.20版本的普通高校和独立院校
	 */
	public static void import20110120University() throws Exception {
		File xls = new File("D:\\csdc\\数据代码\\其他数据代码\\高校代码\\普通高校代码（2011年1月20日）.xls");
		UniversityImporter importer = new UniversityImporter(xls, "univ20110120");
		importer.work();

		xls = new File("D:\\csdc\\数据代码\\其他数据代码\\高校代码\\独立学院代码（2011年1月20日）.xls");
		importer = new UniversityImporter(xls, "univ20110120");
		importer.work();
	}

	
	/**
	 * 导入2011年一般项目申请数据
	 */
	public static void importGeneralApplication2011() throws Exception {
		File xls = new File("D:\\csdc\\2011申报\\[修正导入]20110512_项目审核数据.xls");
		GeneralApplication2011Importer importer = new GeneralApplication2011Importer(xls);
		importer.work();
	}
	
	/**
	 * 导入2011年专家库数据
	 */
	public static void importExpert2011() throws Exception {
		File xls = new File("D:\\csdc\\2011申报\\[修正导入]20110516_专家库修改(标注申报).xls");
		Project2011ExpertImporter importer = new Project2011ExpertImporter(xls);
		importer.work();
	}

	
	/**
	 * 导入学科编号
	 */
	@SuppressWarnings("unchecked")
	public static void importDisc() throws Exception {
		
		int year = DisciplineKit.year;
		
		List<SystemOption> list = baseService.query("select opt from SystemOption opt where opt.standard like '%-"+year+"]%'");
		for (SystemOption opt : list) {
			baseService.delete(opt);
		}

		
		DisciplineKit disciplineKit = new DisciplineKit();
		File xls = new File("D:\\csdc\\学科分类与代码\\GBT13745-"+year+"学科分类与代码表.xls");
		disciplineKit.validate(xls);
		disciplineKit.imprt();
		
		
		SystemOption god = new SystemOption(), parent;
		god.setName("GBT13745-"+year+"学科分类与代码表");
		god.setStandard("[GBT13745-"+year+"]");
		god.setIsAvailable(1);
		baseService.add(god);
		list = baseService.query("select opt from SystemOption opt where opt.standard like '%[GBT13745-"+year+"]%'");
		
		for (SystemOption opt : list) {
			if (opt.getCode() == null || opt.getCode().length() <= 3){
				opt.setParent(god);
			} else {
				List parentList = baseService.query("select opt from SystemOption opt where opt.code = '" + opt.getCode().substring(0, opt.getCode().length()-2)  + "' and opt.standard like '%[GBT13745-"+year+"]%'");
				if (parentList.size() == 0){
					//System.out.println(opt.getName() + "(" + opt.getCode() + ")" + " 无上级学科!");
					opt.setParent(god);
				} else {
					parent = (SystemOption) parentList.get(0);
					opt.setParent(parent);
				}
			}
			baseService.modify(opt);
		}
		god.setParent(null);
		baseService.modify(god);
	}

	
	public static void importPUTONGUniversity() throws Exception{
		UniversityKit universityKit= new UniversityKit();
		File xls = new File("D:\\csdc\\高校代码\\ptgx.xls");
		universityKit.validate(xls);
		universityKit.imprt();
	}
	
	public static void importDULIUniversity() throws Exception{
		UniversityKit universityKit= new UniversityKit();
		File xls = new File("D:\\csdc\\高校代码\\dlyx.xls");
		universityKit.validate(xls);
		universityKit.imprt();
	}
	
	/**
	 * @throws Exception 
	 * @throws Exception
	 */
	public static void setDisable() throws Exception{
		FileReader fr = new FileReader("C:\\Documents and Settings\\Administrator\\桌面\\专家删除.txt");//创建FileReader对象，用来读取字符流
		BufferedReader br = new BufferedReader(fr);	//缓冲指定文件的输入
		String myreadline;	//定义一个String类型的变量,用来每次读取一行
		while (br.ready()) {
			myreadline = br.readLine();//读取一行
			//System.out.println(myreadline);
//			if (1<2)continue;
			String content[] = myreadline.split("\\s+");
			Expert expert = (Expert) baseService.query("select e from Expert e where e.name = '" + content[2] + "' and e.universityCode = '" + content[0] + "'").get(0);
			expert.setIsReviewer(0);
			baseService.modify(expert);
		}
		br.close();
		fr.close();
	}

	@SuppressWarnings("unchecked")
	public static void updatePriority() {
		List<ProjectApplicationReview> prList = baseService.query("select pr from ProjectApplicationReview pr where pr.type = 'general' order by pr.project.id asc, pr.priority asc, pr.id asc");
		HashSet<String> prjSet = new HashSet<String>();
		for (int i = 0; i < prList.size(); i++){
			ProjectApplicationReview a = prList.get(Math.abs(i - 1)), b = prList.get(i);
			if (i == 0 || !b.getProject().getId().equals(a.getProject().getId())){
				if (b.getPriority() != 1){
					prjSet.add(b.getProject().getId());
				}
				b.setPriority(1);
			} else {
				if (b.getPriority() != a.getPriority() + 1){
					prjSet.add(b.getProject().getId());
				}
				b.setPriority(a.getPriority() + 1);
			}
		}
		baseService.modify(prList);
		for (Iterator iterator = prjSet.iterator(); iterator.hasNext();) {
			String pid = (String) iterator.next();
			//System.out.println("Update: " + pid);
			ProjectApplication p = (ProjectApplication) baseService.query(ProjectApplication.class, pid);
			generalService.updateWarningReviewer(p);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void updateWarningReviewer() {
		List<String> list = baseService.query("select pr.project.id from ProjectApplicationReview pr where pr.type = 'general' and pr.isManual = 1 group by pr.project.id");
		for (String pid : list) {
			ProjectApplication project = (ProjectApplication) baseService.query(ProjectApplication.class, pid);
			generalService.updateWarningReviewer(project);
		}
		list = baseService.query("select project.id from Project project where project.warningReviewer is not null");
		for (String pid : list) {
			General project = (General) baseService.query(General.class, pid);
			generalService.updateWarningReviewer(project);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void addNumber4Expert(){
		List<Expert> list = baseService.query("select e from Expert e order by e.universityCode asc, e.name asc");
		int InnerNum = 10000001;
		int OuterNum = 20000001;
		for (Expert expert : list) {
			if (Integer.parseInt(expert.getUniversityCode()) > 10000){
				expert.setNumber(InnerNum++);
			} else {
				expert.setNumber(OuterNum++);
			}
		}
		baseService.modify(list);
	}
	
	static public void setBaseService(IBaseService baseService2) {
		Customized.baseService = baseService2;
	}
	static public void setGeneralService(IGeneralService generalService) {
		Customized.generalService = generalService;
	}
	
}
