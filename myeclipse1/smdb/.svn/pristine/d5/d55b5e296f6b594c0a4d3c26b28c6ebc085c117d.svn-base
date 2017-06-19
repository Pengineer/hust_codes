package csdc.tool.execution.importer;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Book;
import csdc.bean.Consultation;
import csdc.bean.Electronic;
import csdc.bean.GeneralGranted;
import csdc.bean.GeneralMidinspection;
import csdc.bean.GeneralVariation;
import csdc.bean.InstpGranted;
import csdc.bean.InstpMidinspection;
import csdc.bean.InstpVariation;
import csdc.bean.Officer;
import csdc.bean.Paper;
import csdc.bean.Patent;
import csdc.bean.ProjectGranted;
import csdc.bean.ProjectMidinspection;
import csdc.bean.ProjectMidinspectionProduct;
import csdc.bean.ProjectProduct;
import csdc.bean.SinossBook;
import csdc.bean.SinossChecklogs;
import csdc.bean.SinossConsultation;
import csdc.bean.SinossElectronic;
import csdc.bean.SinossPaper;
import csdc.bean.SinossPatent;
import csdc.bean.SinossProjectMidinspection;
import csdc.bean.SystemOption;
import csdc.dao.HibernateBaseDao;
import csdc.tool.StringTool;
import csdc.tool.execution.Execution;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.SinossTableTool;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 将与社科网对接的中检中间表数据入库正式表
 * @author 
 */
@Component
public class ProjectMidInspection2014Importer extends Execution {
	
	@Autowired
	private HibernateBaseDao dao;
	
	@Autowired
	private SinossTableTool sTool;
	
	@Autowired
	private Tool tool;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	private int count=0;
	
	/**
	 * 初始化审核数据到内存
	 */
	private List<SinossChecklogs> sinossChecklogs;
	
	/**
	 * 初始化立项数据到内存
	 */
	public static List<ProjectGranted> pGranted;
	
	@SuppressWarnings("unchecked")
	@Override
	public void work() throws Throwable {
		initCheckInfo();
		initGranded();
		//一般项目(有三条一般项目数据已经提前导入，这三条数据需要手动合并处理)
		count=0;
		Officer 刘杰 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '刘杰'"); 
		List<SinossProjectMidinspection> spMidinspections = dao.query("from SinossProjectMidinspection spm where spm.projectType = 'gener' and to_char(spm.dumpDate)='26-9月 -14' and spm.projectGranted.id !='4028d88a373492e801373494ca8f1fe7' and spm.projectGranted.id !='4028d88a373492e801373494c9d10df1' and spm.projectGranted.id !='4028d88a373492e801373494c9b20b0b' and spm.projectGranted.id !='4028d88a373492e801373494ca3d1691'");		
		for (SinossProjectMidinspection spm: spMidinspections) {
			//GeneralGranted granted = (GeneralGranted) dao.queryUnique("from GeneralGranted g where g.id = ?", spm.getSmdbProjectId());
			//GeneralGranted granted = (GeneralGranted) dao.queryUnique("from GeneralGranted g where g.id = ?", spm.getProjectGranted().getId());//GeneralGranted的主键值全部来自ProjectGranted的主键值，做主键的同时又是外键（如何添加一条数据？）
			GeneralGranted granted = (GeneralGranted) this.getProjectGranted(spm.getProjectGranted().getId());
			System.out.println(spm.getName() + "----gener:" + (++count) + "/" + spMidinspections.size());
			if (spm.getDeferReason() != null) {//录入变更
				GeneralVariation gVariation = new GeneralVariation();
				granted.addVariation(gVariation);
				gVariation.setImportedDate(new Date());
				gVariation.setOther(1);
				gVariation.setApplicantSubmitStatus(3);
				gVariation.setDeptInstAuditStatus(3);
				gVariation.setDeptInstAuditResult(2);
				gVariation.setDeptInstAuditResultDetail("00000000000000000001");  //中检延期属于“其他”类型的变更，不属于“延期”类型，“延期”是指项目延期
				gVariation.setOtherInfo("申请项目中检延期。");
				if(spm.getDeferDate() != null){
					gVariation.setApplicantSubmitDate(spm.getDeferDate());
				}else{
					gVariation.setApplicantSubmitDate(spm.getApplyDate());
				}
				gVariation.setVariationReason(spm.getDeferReason());
				if (AuditStatus(spm.getCheckStatus()).contains("学校")) {
					gVariation.setUniversityAuditResultDetail("00000000000000000001");
					gVariation.setUniversityAuditStatus(3);
					if (AuditStatus(spm.getCheckStatus()).contains("不")) {
						gVariation.setUniversityAuditResult(1);
						gVariation.setStatus(3);
					} else {
						gVariation.setUniversityAuditResult(2);
						gVariation.setStatus(4);
					}
				} else if (AuditStatus(spm.getCheckStatus()).contains("主管")) {
					gVariation.setProvinceAuditResultDetail("00000000000000000001");
					gVariation.setProvinceAuditStatus(3);
					if (AuditStatus(spm.getCheckStatus()).contains("不")) {
						gVariation.setProvinceAuditResult(1);
						gVariation.setStatus(4);
					} else {
						gVariation.setProvinceAuditResult(2);
						gVariation.setStatus(5);
					}					
					gVariation.setUniversityAuditResult(2);
					gVariation.setUniversityAuditResultDetail("00000000000000000001");
					gVariation.setUniversityAuditStatus(3);
				}
				if(spm.getName().equals("俗神叙事的演化逻辑：以陈靖姑传说等为例") || spm.getName().equals("春秋金文及其地域特征研究") || spm.getName().equals("“虚拟”的真实影响：个体在现实与网络空间社会认知的差异及影响机制研究")){   //2014年的特殊数据
					gVariation.setStatus(5);
					gVariation.setFinalAuditStatus(3);
					gVariation.setFinalAuditResult(1);
					gVariation.setFinalAuditResultDetail("00000000000000000001");
					gVariation.setFinalAuditor(刘杰);
					gVariation.setFinalAuditorName(刘杰.getPerson().getName());
					gVariation.setFinalAuditorAgency(刘杰.getAgency());
					gVariation.setFinalAuditorInst(刘杰.getInstitute());
					gVariation.setFinalAuditDate(tool.getDate(2014, 9, 30));
				}				
			} else {//录入中检
			//	if (spm.getStatus().contains("进行中") && (spm.getApplyDate() != null)) {
				if (spm.getApplyDate() != null) {
					GeneralMidinspection gmi = new GeneralMidinspection();
					gmi.setImportedDate(new Date());
					granted.addMidinspection(gmi);
					gmi.setApplicantSubmitDate(spm.getApplyDate());
					gmi.setApplicantSubmitStatus(3);
			
					checkInfo(spm,gmi);
				}
			}			
		}
		
		//基地项目
		count=0;
		List<SinossProjectMidinspection> spMidinspections2 = dao.query("from SinossProjectMidinspection spm where spm.projectType = 'base' and to_char(spm.dumpDate)='26-9月 -14'");		
		for (SinossProjectMidinspection spm: spMidinspections2) {
			InstpGranted granted = (InstpGranted) this.getProjectGranted(spm.getProjectGranted().getId());
			System.out.println(spm.getName() + "----base:" + (++count) + "/" + spMidinspections2.size());
			if (spm.getDeferReason() != null) {//录入变更				
				InstpVariation iVariation = new InstpVariation();
				granted.addVariation(iVariation);
				iVariation.setImportedDate(new Date());
				iVariation.setApplicantSubmitStatus(3);
				iVariation.setDeptInstAuditStatus(3);
				iVariation.setDeptInstAuditResult(2);
				iVariation.setDeptInstAuditResultDetail("00000000000000000001");
				iVariation.setOther(1);
				iVariation.setOtherInfo("申请项目中检延期。");
				if(spm.getDeferDate() != null){
					iVariation.setApplicantSubmitDate(spm.getDeferDate());
				}else{
					iVariation.setApplicantSubmitDate(spm.getApplyDate());
				}
				iVariation.setStatus(5);					
				iVariation.setVariationReason(spm.getDeferReason());
				if (AuditStatus(spm.getCheckStatus()).contains("学校")) {
					iVariation.setUniversityAuditResultDetail("00000000000000000001");
					iVariation.setUniversityAuditStatus(3);
					iVariation.setUniversityAuditResult(2);
				} else if (AuditStatus(spm.getCheckStatus()).contains("主管")) {
					iVariation.setProvinceAuditResultDetail("00000000000000000001");
					iVariation.setProvinceAuditStatus(3);					
					if (AuditStatus(spm.getCheckStatus()).contains("不")) {
						iVariation.setProvinceAuditResult(1);
					} else {
						iVariation.setProvinceAuditResult(2);
					}					
					iVariation.setUniversityAuditResult(2);
					iVariation.setUniversityAuditResultDetail("00000000000000000001");
					iVariation.setUniversityAuditStatus(3);
				}
			} else if (spm.getApplyDate() != null) {//录入中检
				InstpMidinspection imi = new InstpMidinspection();
				imi.setImportedDate(new Date());
				granted.addMidinspection(imi);
				imi.setApplicantSubmitDate(spm.getApplyDate());
				imi.setApplicantSubmitStatus(3);
				
				checkInfo(spm,imi);
			}
		}		
		
		//论文成果
		count=0;
		List<Object[]> objpapers = dao.query("select sp, spm.projectGranted.id from SinossPaper sp left join sp.projectMidinspection spm");
		SystemOption 论文集 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'productType' and so.name = '论文集'");
		SystemOption 期刊论文 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'productType' and so.name = '编著'");
		SystemOption 编著 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'productType' and so.name = '期刊论文'");
		SystemOption 工具书 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'productType' and so.name = '工具书'");
		SystemOption 教材 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'productType' and so.name = '教材'");
		SystemOption 译著 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'productType' and so.name = '译著'");
		SystemOption 专著 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'productType' and so.name = '专著'");
		SystemOption 国内 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'pulicationScope' and so.name = '国内'");
		SystemOption 国外 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'pulicationScope' and so.name = '国外'");
		SystemOption 港澳台 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'pulicationScope' and so.name = '港澳台'");
		
		for (Object[] obj : objpapers) {
			SinossPaper sPaper = (SinossPaper) obj[0];
			ProjectGranted granted = (ProjectGranted) this.getProjectGranted((String) obj[1]);
			Paper paper = new Paper();
			if (sPaper.getName() != null) {
				paper.setChineseName(sPaper.getName());//论文名称
				System.out.println(sPaper.getName() + "----paper:" + (++count) + "/" + objpapers.size());
			} else {
				paper.setChineseName("论文名称不存在");
			}			
			paper.setDiscipline(sPaper.getSubject());//学科代码
			paper.setDisciplineType(sPaper.getSubjectType());//学科名称
			paper.setAuthorName(sPaper.getFirstAuthor());//第一作者
			if (sPaper.getOtherAuthor() != null) {
				paper.setOtherAuthorName(sPaper.getOtherAuthor().replaceAll("[,，、\\s]+", "; "));//其他作者
			}
			
			//论文类型
			if ("论文集".equals(sPaper.getPaperType())) {
				paper.setType(论文集);
			} else if ("期刊论文".equals(sPaper.getPaperType())) {
				paper.setType(期刊论文);
			}		
			//发表范围
			if (sPaper.getPublishScope() != null) {
				if (sPaper.getPublishScope().contains("境内")) {
					paper.setPublicationScope(国内);
				} else if (sPaper.getPublishScope().contains("境外")) {
					paper.setPublicationScope(国外);
				} else if (sPaper.getPublishScope().contains("港澳台")) {
					paper.setPublicationScope(港澳台);
				}				
			}														
			//被引情况（刊物类型）
			if (sPaper.getPaperBookType() != null) {
				String index = sPaper.getPaperBookType().replaceAll(",", "; ");
				paper.setIndex(index);
			}			
			//字数
			if(sPaper.getWordNumber() != null){
				if(!sPaper.getWordNumber().replaceAll("\\s+", "").equals("")){
					paper.setWordNumber(regulateWordsNum(sPaper.getWordNumber()));
				}
			}
			//是否为译文
			if (sPaper.getIsTranslate() != null) {
				if (sPaper.getIsTranslate().contains("是")) {
					paper.setIsTranslation(1);
				} else if (sPaper.getIsTranslate().contains("否")) {
					paper.setIsTranslation(0);				
				}				
			}			
			paper.setNote(sPaper.getNote());//备注
			paper.setPublication(sPaper.getPaperBook());//发表刊物
			paper.setNumber(sPaper.getQihao());//期号
			paper.setVolume(sPaper.getJuanhao());//卷号
			paper.setPage(StringTool.toDBC(sPaper.getPagenumScope()));//页码范围
			paper.setIssn(sPaper.getIssn());//ISSN号
			paper.setCn(sPaper.getCn());//CN号
			paper.setPublicationDate(sPaper.getPublicDate());//发表时间
			paper.setSubmitStatus(3);
			dao.add(paper);
				
			//添加项目成果
			ProjectProduct projectProduct = new ProjectProduct();
			projectProduct.setProduct(paper);
			projectProduct.setProjectGranted(granted);
			//是否标注
			if ("是".equals(sPaper.getIsMark())) {
				projectProduct.setIsMarkMoeSupport(1);
			} else {
				projectProduct.setIsMarkMoeSupport(0);
			}
			dao.add(projectProduct);
			
			//添加项目中检成果
			Set<ProjectMidinspection> pMidinspections = (Set<ProjectMidinspection>) granted.getMidinspection();
			ProjectMidinspectionProduct pmProduct = new ProjectMidinspectionProduct();
			pmProduct.setProduct(paper);
			for (ProjectMidinspection pMidinspection : pMidinspections) {
				if (pMidinspection.getImportedDate().after(tool.getDate("2014-09-20"))) {         
					pmProduct.setProjectMidinspection(pMidinspection);
					pmProduct.setFirstAuditStatus(3);
					pmProduct.setFirstAuditResult(pMidinspection.getUniversityAuditResult());
				}
			}
			dao.add(pmProduct);						
		}
		
		//著作成果
		count=0;
		List<Object[]> objbooks = dao.query("select sb, spm.projectGranted.id from SinossBook sb left join sb.projectMidinspection spm");
		for (Object[] objbook: objbooks) {
			SinossBook sBook = (SinossBook)objbook[0];
			ProjectGranted grantedBook = (ProjectGranted) this.getProjectGranted((String) objbook[1]);
			//ProjectGranted grantedBook = (ProjectGranted) dao.queryUnique("from ProjectGranted g where g.id = ?", sBook.getProjectMidinspection().getProjectGranted().getId());
			Book book = new Book();
			if (sBook.getName() == null) {
				book.setChineseName("著作名称不存在");
			} else {
				book.setChineseName(sBook.getName());
				System.out.println(sBook.getName() + "----book:" + (++count) + "/" + objbooks.size());
			}	
			book.setAuditorName(sBook.getFirstAuthor());
			book.setOtherAuthorName(sBook.getOtherAuthor());
			book.setDiscipline(sBook.getSubject());//学科代码
			book.setDisciplineType(sBook.getDisciplineType());//学科名称
            book.setPublishUnit(sBook.getPress());
            book.setPublishDate(sBook.getPressTime());
            //发表范围
	//		if (sBook.getPressAddress() != null) {
	//			if (sBook.getPressAddress().contains("国内")) {
	//				book.setPublicationScope(国内);
	//			} else if (sBook.getPressAddress().contains("国外")) {
	//				book.setPublicationScope(国外);
	//			}		
	//		}
            book.setPublishRegion(sBook.getPressAddress());
			//论文类型
			if ("编著".equals(sBook.getBookType())) {
				book.setType(编著);
			} else if ("工具书".equals(sBook.getBookType())) {
				book.setType(工具书);
			} else if ("教材".equals(sBook.getBookType())) {
				book.setType(教材);
			} else if ("译著".equals(sBook.getBookType())) {
				book.setType(译著);
			} else if ("专著".equals(sBook.getBookType())) {
				book.setType(专著);
			}
			//字数regulateWordsNum
			if (sBook.getWordNumber() != null) {
				if(!sBook.getWordNumber().replaceAll("\\s+", "").equals("")){
					book.setWordNumber(regulateWordsNum(sBook.getWordNumber()));
				}
			}
			//是否译成英文
			if (sBook.getIsEnglish() != null) {
				if (sBook.getIsEnglish().contains("是")) {
					book.setIsEnglish(1);
				} else if (sBook.getIsEnglish().contains("否")) {
					book.setIsEnglish(0);				
				}				
			}
			book.setIsbn(sBook.getIsbn());
			book.setCip(sBook.getCip());
            book.setNote(sBook.getNote());
            book.setSubmitStatus(3);
            dao.add(book);
            
          //dao.flush();  //此句特别拖慢速度
            
			ProjectProduct projectProduct = new ProjectProduct();
			projectProduct.setProduct(book);
			projectProduct.setProjectGranted(grantedBook);
			//是否标注
			if ("是".equals(sBook.getIsMark())) {
				projectProduct.setIsMarkMoeSupport(1);
			} else {
				projectProduct.setIsMarkMoeSupport(0);
			}
			dao.add(projectProduct);
			
			Set<ProjectMidinspection> pMidinspections = (Set<ProjectMidinspection>) grantedBook.getMidinspection();
			ProjectMidinspectionProduct pmProduct = new ProjectMidinspectionProduct();
			pmProduct.setProduct(book);
			for (ProjectMidinspection pMidinspection: pMidinspections) {
				if (pMidinspection.getImportedDate().after(tool.getDate("2014-09-20"))) {
					pmProduct.setProjectMidinspection(pMidinspection);
					pmProduct.setFirstAuditStatus(3);
					pmProduct.setFirstAuditResult(pMidinspection.getUniversityAuditResult());
				}
			}
			dao.add(pmProduct);	
		}
 

		//专利成果
		count=0;
		List<Object[]> objpatents = dao.query("select sp, spm.projectGranted.id from SinossPatent sp left join sp.projectMidinspection spm");
		for (Object[] objpatent: objpatents) {
			SinossPatent sPatent = (SinossPatent)objpatent[0];
			ProjectGranted grantedPatent = (ProjectGranted) this.getProjectGranted((String) objpatent[1]);
			Patent patent = new Patent();
			//ProjectGranted grantedBook = (ProjectGranted) dao.queryUnique("from ProjectGranted g where g.id = ?", sBook.getProjectMidinspection().getProjectGranted().getId());
			if (sPatent.getName() != null) {
				patent.setChineseName(sPatent.getName());
				System.out.println(sPatent.getName() + "----patent:" + (++count) + "/" + objpatents.size());
			} else {
				patent.setChineseName("专利名称不存在");
			}
			
			if (sPatent.getOtherName() != null) {
				patent.setOtherAuthorName(sPatent.getOtherName().replaceAll("[，、\\s]+", "; "));
			}				
            patent.setAuthorName(sPatent.getFirstAuthor());
            patent.setPublicDate(sPatent.getAuthorizeDate());
            patent.setNote(sPatent.getNote());
            patent.setInventorName(sPatent.getPatentPerson());
            patent.setCategotyType(sPatent.getPatentType());
            patent.setPublicNumber(sPatent.getPatentNumber());
            patent.setDiscipline(sPatent.getSubject());//学科代码
            patent.setDisciplineType(sPatent.getDisciplineType());//学科名称
            //发表范围
	//		if (sPatent.getPatentScope() != null) {
	//			if (sPatent.getPatentScope().contains("国内")) {
	//				patent.setScope(国内);
	//			} else if (sPatent.getPatentScope().contains("国外")) {
	//				patent.setScope(国外);
	//			}		
	//		}
            patent.setPatentScope(sPatent.getPatentScope());
            patent.setSubmitStatus(3);
            dao.add(patent);			
            
			ProjectProduct projectProductPatent = new ProjectProduct();
			projectProductPatent.setProduct(patent);
			projectProductPatent.setProjectGranted(grantedPatent);
			//是否标注
			if ("是".equals(sPatent.getIsMark())) {
				projectProductPatent.setIsMarkMoeSupport(1);
			} else {
				projectProductPatent.setIsMarkMoeSupport(0);
			}
			dao.add(projectProductPatent);
			
			Set<ProjectMidinspection> pms = (Set<ProjectMidinspection>) grantedPatent.getMidinspection();
			ProjectMidinspectionProduct pm = new ProjectMidinspectionProduct();
			pm.setProduct(patent);
			for (ProjectMidinspection pMidinspection: pms) {
				if (pMidinspection.getImportedDate().after(tool.getDate("2014-09-20"))) {   //同上
					pm.setProjectMidinspection(pMidinspection);
					pm.setFirstAuditStatus(3);
					pm.setFirstAuditResult(pMidinspection.getUniversityAuditResult());
				}
			}
			dao.add(pm);
		}

		
		//电子出版物
		count=0;
		List<Object[]> objelectronics = dao.query("select se, spm.projectGranted.id from SinossElectronic se left join se.projectMidinspection spm");
		for (Object[] objelectronic: objelectronics) {
			SinossElectronic sElectronic = (SinossElectronic)objelectronic[0];
			ProjectGranted grantedEle = (ProjectGranted) this.getProjectGranted((String) objelectronic[1]);
			Electronic electronic = new Electronic();
			//ProjectGranted grantedBook = (ProjectGranted) dao.queryUnique("from ProjectGranted g where g.id = ?", sBook.getProjectMidinspection().getProjectGranted().getId());
			if (sElectronic.getName() != null) {
				electronic.setChineseName(sElectronic.getName());
				System.out.println(sElectronic.getName() + "----electronic:" + (++count) + "/" + objelectronics.size());
			} else {
				electronic.setChineseName("电子出版物名字不存在");
			}
			
			electronic.setAuditorName(sElectronic.getFirstAuthor());
			if (sElectronic.getOtherAuthor() != null) {
				electronic.setOtherAuthorName(sElectronic.getOtherAuthor().replaceAll("，", "; "));
			}
			electronic.setDiscipline(sElectronic.getSubject());//学科代码
			electronic.setDisciplineType(sElectronic.getDisciplineType());//学科名称
            electronic.setPublishUnit(sElectronic.getPress());
            electronic.setPublishDate(sElectronic.getPressDate());
            electronic.setPublishRegion(sElectronic.getPressAddress());
            electronic.setNote(sElectronic.getNote());
            electronic.setIsbn(sElectronic.getIsbn());
            electronic.setSubmitStatus(3);
            dao.add(electronic);
            
			ProjectProduct projectElectronic = new ProjectProduct();
			projectElectronic.setProduct(electronic);
			projectElectronic.setProjectGranted(grantedEle);
			//是否标注
			if ("是".equals(sElectronic.getIsMark())) {
				projectElectronic.setIsMarkMoeSupport(1);
			} else {
				projectElectronic.setIsMarkMoeSupport(0);
			}
			dao.add(projectElectronic);
			
			Set<ProjectMidinspection> pmes = (Set<ProjectMidinspection>) grantedEle.getMidinspection();
			ProjectMidinspectionProduct pmc = new ProjectMidinspectionProduct();
			pmc.setProduct(electronic);
			for (ProjectMidinspection pme: pmes) {
				if (pme.getImportedDate().after(tool.getDate("2014-09-20"))) {
					pmc.setProjectMidinspection(pme);
					pmc.setFirstAuditStatus(3);
					pmc.setFirstAuditResult(pme.getUniversityAuditResult());
				}
			}
			dao.add(pmc);	
		}
		
		//研究报告
		count=0;
		List<Object[]> objcons = dao.query("select sc, spm.projectGranted.id from SinossConsultation sc left join sc.projectMidinspection spm");
		for (Object[] objconsultation: objcons) {
			SinossConsultation sConsultation = (SinossConsultation)objconsultation[0];
			ProjectGranted grantedcon = (ProjectGranted) this.getProjectGranted((String) objconsultation[1]);
			Consultation consultation = new Consultation();
			//ProjectGranted grantedBook = (ProjectGranted) dao.queryUnique("from ProjectGranted g where g.id = ?", sBook.getProjectMidinspection().getProjectGranted().getId());
			if (sConsultation.getName() != null) {
				consultation.setChineseName(sConsultation.getName());
				System.out.println(sConsultation.getName() + "----consultation:" + (++count) + "/" + objcons.size());
			} else {
				consultation.setChineseName("该研究报告名字不存在");
			}				
			consultation.setAuditorName(sConsultation.getFirstAuthor());
			if (sConsultation.getOtherAuthor() != null) {
				consultation.setOtherAuthorName(sConsultation.getOtherAuthor().replaceAll("[,，、\\s]+", "; "));
			}
			consultation.setDiscipline(sConsultation.getSubject());//学科代码
			consultation.setDisciplineType(sConsultation.getDisciplineType());//学科名称		           
			consultation.setNote(sConsultation.getNote());
			consultation.setPublicationDate(sConsultation.getCommitDate());
			consultation.setAdoptType(sConsultation.getAcceptObj());
			//consultation.setPublicationUnit(sConsultation.getCommitUnit());  14年数据无此信息 
			String isAccepted = sConsultation.getIsAccept();
			if(isAccepted != null){                   
				if (isAccepted.contains("是") || isAccepted.contains("1")) {
					consultation.setIsAccepted(1);
				} else if (isAccepted.contains("否") || isAccepted.contains("0")) {
					consultation.setIsAccepted(0); 
				}	
			}
			consultation.setSubmitStatus(3);
			dao.add(consultation);
			
            
			ProjectProduct projectConsultation = new ProjectProduct();
			projectConsultation.setProduct(consultation);
			projectConsultation.setProjectGranted(grantedcon);
			//是否标注
			if ("是".equals(sConsultation.getIsMark())) {
				projectConsultation.setIsMarkMoeSupport(1);
			} else {
				projectConsultation.setIsMarkMoeSupport(0);
			}
			dao.add(projectConsultation);
			
			Set<ProjectMidinspection> pmcs = (Set<ProjectMidinspection>) grantedcon.getMidinspection();
			ProjectMidinspectionProduct pmcc = new ProjectMidinspectionProduct();
			pmcc.setProduct(consultation);
			for (ProjectMidinspection pmca: pmcs) {
				if (pmca.getImportedDate().after(tool.getDate("2014-09-20"))) {
					pmcc.setProjectMidinspection(pmca);
					pmcc.setFirstAuditStatus(3);
					pmcc.setFirstAuditResult(pmca.getUniversityAuditResult());
				}
			}
			dao.add(pmcc);	
		}	
	}
	
/*	//从中间表中录入审核信息
	public void checkInfo(SinossProjectMidinspection spm, ProjectMidinspection gmi){		
		//查找中间表中中检项目对应的审核信息
		//List<SinossChecklogs> sChecklogs = dao.query("from SinossChecklogs o where o.projectMidinspection.id = ?",spm.getId());
		List<SinossChecklogs> sChecklogs = this.getSinossChecklogs(spm.getId());
		SinossChecklogs[] lastSChecklogs = {null, null};//[0]：校级审核记录；[1]:主管部门审核记录
		for (SinossChecklogs sChecklog : sChecklogs) {
			int checkStatus = sChecklog.getCheckStatus();
			if (checkStatus == 2 || checkStatus ==3 ) {//校级审核			
				lastSChecklogs[0] = (lastSChecklogs[0] != null && sChecklog.getCheckDate().before(lastSChecklogs[0].getCheckDate())) ? lastSChecklogs[0]: sChecklog;
			}else if (checkStatus == 4 || checkStatus ==5) {//主管部门审核
				lastSChecklogs[1] = (lastSChecklogs[1] != null && sChecklog.getCheckDate().before(lastSChecklogs[1].getCheckDate())) ? lastSChecklogs[1]: sChecklog;
			}				
		}	
		gmi.setDeptInstAuditStatus(3);
		gmi.setDeptInstAuditResult(2);
		gmi.setDeptInstAuditDate(spm.getApplyDate());
		gmi.setStatus(5);
		if (AuditStatus(spm.getCheckStatus()).contains("学校")) {
			gmi.setUniversityAuditStatus(3);
			if (AuditStatus(spm.getCheckStatus()).contains("不")) {
				gmi.setUniversityAuditResult(1);
			} else {
				gmi.setUniversityAuditResult(2);
			}
		} else if (AuditStatus(spm.getCheckStatus()).contains("主管")) {
			gmi.setUniversityAuditResult(2);
			gmi.setUniversityAuditStatus(3);
			if (AuditStatus(spm.getCheckStatus()).contains("不")) {
				gmi.setProvinceAuditResult(1);
			} else {
				gmi.setProvinceAuditResult(2);
			}
			gmi.setProvinceAuditStatus(3);
		}
		//将中间表中的校级审核记录写到中检表的对应字段
		if (lastSChecklogs[0] != null) {	
			gmi.setUniversityAuditorName(lastSChecklogs[0].getChecker());
			gmi.setUniversityAuditDate(lastSChecklogs[0].getCheckDate());
			gmi.setUniversityAuditOpinion(lastSChecklogs[0].getCheckInfo());
			Agency university = universityFinder.getUnivByName(lastSChecklogs[0].getChecker());
			gmi.setUniversityAuditorAgency(university);	
		}
		//将中间表中的主管部门审核记录写到中检表的对应字段
		if (lastSChecklogs[1] != null) {
			gmi.setProvinceAuditorName(lastSChecklogs[1].getChecker());
			gmi.setProvinceAuditDate(lastSChecklogs[1].getCheckDate());
			gmi.setProvinceAuditOpinion(lastSChecklogs[1].getCheckInfo());
			Agency province = universityFinder.getProByName(lastSChecklogs[1].getChecker());
			gmi.setProvinceAuditorAgency(province);
		}
	}*/
	
	//初始化审核信息到内存
	@SuppressWarnings("unchecked")
	public void initCheckInfo(){
		Long begin = System.currentTimeMillis();
		sinossChecklogs = dao.query("select o from SinossChecklogs o where o.type=2 and to_char(o.dumpDate)='26-9月 -14'");
		//sinossChecklogs = dao.query("select o from SinossChecklogs o where o.type=2");
		System.out.println("InitCheckInfo Complete! Used time:" + (System.currentTimeMillis() - begin) + "ms");
	}
	
	//从sinossChecklogs中获取需要的审核信息
	public List<SinossChecklogs> getSinossChecklogs(String id){
		List<SinossChecklogs> sChecklogs = new ArrayList<SinossChecklogs>();
		Iterator<SinossChecklogs> iterator = sinossChecklogs.iterator();
		while(iterator.hasNext()){
			SinossChecklogs sc = iterator.next();
			if(id.equals(sc.getProjectMidinspection().getId())){
				sChecklogs.add(sc);
			}
		}
		return sChecklogs;
	}	
	
	//审核信息
	public String checkInfo(SinossProjectMidinspection spm,ProjectMidinspection gmi){		
		SinossChecklogs sic = null;
		//部级以下审核不通过(1,3,5)
		int statusNumber = 0;
		int lastCheckStatus = 0;
		//判断是否有审核信息
		boolean flag = false;
		SinossChecklogs[] lastSChecklogs = {null, null};//[0]：校级审核记录；[1]:主管部门(省级)审核记录
		//分别获取校级和省级最终审核记录
		List<SinossChecklogs> spmChecklogs = this.getSinossChecklogs(spm.getId());
		for(SinossChecklogs sicheck : spmChecklogs){
			statusNumber = sicheck.getCheckStatus();
			if (statusNumber == 2 || statusNumber ==3 ){ //校级审核
				lastSChecklogs[0] = (lastSChecklogs[0] != null && sicheck.getCheckDate().before(lastSChecklogs[0].getCheckDate())) ? lastSChecklogs[0]: sicheck;
				flag = true;
			}else if (statusNumber == 4 || statusNumber ==5) { //省级审核
				lastSChecklogs[1] = (lastSChecklogs[1] != null && sicheck.getCheckDate().before(lastSChecklogs[1].getCheckDate())) ? lastSChecklogs[1]: sicheck;
				flag = true;
			}
		}
		//获取最终审核状态
		if(lastSChecklogs[1] != null){ //校级必然通过
			lastCheckStatus = lastSChecklogs[1].getCheckStatus();
		}else if(lastSChecklogs[0] != null){
			lastCheckStatus = lastSChecklogs[0].getCheckStatus();
		}
		
		//院系审核都通过
		gmi.setDeptInstAuditResult(2);
		gmi.setDeptInstAuditStatus(3);
		gmi.setStatus(3);
		gmi.setDeptInstAuditDate(spm.getApplyDate());
		
		if(!flag){
			return "";
		}
		
		//校级和省级审核不通过
		if(lastCheckStatus%2 ==1){   
			if(lastCheckStatus == 5){//省级审核不通过，说明校级通过
				sic = lastSChecklogs[1];
				gmi.setUniversityAuditResult(2);//[0：未审核；1：不同意；2：同意]
				gmi.setUniversityAuditStatus(3);//[0：默认；1：退回；2：暂存；3：提交]
				
				gmi.setUniversityAuditDate(lastSChecklogs[0].getCheckDate());
				gmi.setUniversityAuditorAgency(universityFinder.getUnivByName(lastSChecklogs[0].getChecker()));
				gmi.setUniversityAuditOpinion(lastSChecklogs[0].getCheckInfo());
				
				gmi.setStatus(4);
				gmi.setProvinceAuditResult(1);
				gmi.setProvinceAuditStatus(3);
				gmi.setProvinceAuditDate(lastSChecklogs[1].getCheckDate());
				gmi.setProvinceAuditorAgency(universityFinder.getUnivByName(lastSChecklogs[1].getChecker()));
				gmi.setProvinceAuditOpinion(lastSChecklogs[1].getCheckInfo());	
			}
			if(lastCheckStatus ==3 || lastCheckStatus ==1){//校级审核不通过
				sic = lastSChecklogs[0];
				gmi.setStatus(3);
				gmi.setUniversityAuditResult(1);
				gmi.setUniversityAuditStatus(3);
				gmi.setUniversityAuditDate(lastSChecklogs[0].getCheckDate());
				gmi.setUniversityAuditorAgency(universityFinder.getUnivByName(lastSChecklogs[0].getChecker()));
				gmi.setUniversityAuditOpinion(lastSChecklogs[0].getCheckInfo());
			}
			gmi.setFinalAuditResult(1);
			gmi.setFinalAuditStatus(3);
			gmi.setFinalAuditDate(sic.getCheckDate());
			gmi.setFinalAuditorAgency(universityFinder.getUnivByName(sic.getChecker()));
			gmi.setFinalAuditOpinion(sic.getCheckInfo());
			return "";
		}
		
		//审核通过
		if(lastCheckStatus%2 ==0){
			//校级审核通过
			gmi.setStatus(4);
			gmi.setUniversityAuditResult(2);//[0：未审核；1：不同意；2：同意]
			gmi.setUniversityAuditStatus(3);//[0：默认；1：退回；2：暂存；3：提交]
			if(lastSChecklogs[0] != null){
				gmi.setUniversityAuditDate(lastSChecklogs[0].getCheckDate());
				gmi.setUniversityAuditorAgency(universityFinder.getUnivByName(lastSChecklogs[0].getChecker()));
				gmi.setUniversityAuditOpinion(lastSChecklogs[0].getCheckInfo());
			}
			 //省级审核通过
			if(lastCheckStatus == 4){
				gmi.setStatus(5);
				gmi.setProvinceAuditResult(2);
				gmi.setProvinceAuditStatus(3);
				gmi.setProvinceAuditDate(lastSChecklogs[1].getCheckDate());
				gmi.setProvinceAuditorAgency(universityFinder.getUnivByName(lastSChecklogs[1].getChecker()));
				gmi.setProvinceAuditOpinion(lastSChecklogs[1].getCheckInfo());
			}
		}
		
		//final审核,单独放在一个Java文件里面（以后可能不用），中检申请数据来自社科网（有各级审核信息），final审核数据来自社科服务中心（如果我们能及时将中检申请数据入库，社科服务中心的人就不会单独发一个final审核的Excel信息，他们会直接在smdb网上审核，这样就会省去Final数据的入库）
		
		return "";
	}
	
	/**
	 * 从内存的中间表获取指定项目的审核数据
	 * @param checkStatus[null:获取所有的；not null:获取指定审核状态的数据对象]
	 * @return
	 */
/*	public List<SinossChecklogs> getSinossChecklogs(SinossProjectMidinspection spm, String checkStatus){
		if(!sinossChecklogs.isEmpty()){
			sinossChecklogs.clear();
        }
        Iterator<SinossChecklogs> iterator = sTool.sinossChecklogsList.iterator();
		while(iterator.hasNext()){
			SinossChecklogs sc = iterator.next();
			if(sc.getProjectApplication().getId().equals(spm.getProjectApplication().getId())){
				if(checkStatus == null){
					sinossChecklogs.add(sc);
				}else if((sc.getCheckStatus()+"").equals(checkStatus)){
					sinossChecklogs.add(sc);
				}
			}
		}
		return sinossChecklogs;
	}*/
	
	//初始化立项信息到内存
	@SuppressWarnings("unchecked")
	public void initGranded(){
		Long begin = System.currentTimeMillis();
		pGranted = dao.query("select p from ProjectGranted p");
		System.out.println("InitGranded Complete! Used time:" + (System.currentTimeMillis() - begin) + "ms");
	}
	
	//通过ID从内存中获取立项数据
	public ProjectGranted getProjectGranted(String id){
		Iterator<ProjectGranted> iterator = pGranted.iterator();
		while(iterator.hasNext()){
			ProjectGranted pg = iterator.next();
			if(id.equals(pg.getId())){
				return pg;
			}
		}
		return null;
	}
	
	/**
	 * 审核状态码-->审核状态  
	 */
	public String AuditStatus(String auditStatue){
		if(auditStatue == null){
			return "未知";
		}else if(auditStatue.equals("0")){
			return "未提交";
		}else if (auditStatue.equals("1")){
			return "退回修改";
			
		}else if (auditStatue.equals("2")){
			return "学校审核通过";
		}else if (auditStatue.equals("3")){
			return "学校审核不通过";
			
		}else if (auditStatue.equals("4")){
			return "主管部门审核通过";
		}else if (auditStatue.equals("5")){
			return "主管部门审核不通过";
//----------------------------------------------------------------			
		}else if (auditStatue.equals("6")){
			return "社科司审核通过";
		}else if (auditStatue.equals("7")){
			return "社科司审核不通过";
		}else if (auditStatue.equals("8")){
			return "已修改";
		}else if (auditStatue.equals("9")){
			return "已提交";
		}else return "未知";
	}
	
	//处理字数格式
	public Double regulateWordsNum(String words){
		Double wordsNum;	
		words = StringTool.toDBC(words).replaceAll("[,，、。·]+", ".");
		words = words.replaceAll("（", "(").replaceAll("）", ")").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("约", "").replaceAll("大约", "").replaceAll("左右", "");
		if(words.contains("万")){
			words = words.replaceAll("[\u4e00-\u9fa5]+", "");
			wordsNum = Double.parseDouble(words) * 10;
		}else if(words.contains("千")){
			words = words.replaceAll("[\u4e00-\u9fa5]+", "");
			wordsNum = Double.parseDouble(words);
		}else if(words.endsWith("字")){
			words = words.replaceAll("[\u4e00-\u9fa5]+", "");
			wordsNum = Double.parseDouble(words) /1000;
		} else {
			words = words.replaceAll("[\u4e00-\u9fa5]+", "");
			if(words.endsWith("000")){
				wordsNum = Double.parseDouble(words) /1000;
			}else{
				wordsNum = Double.parseDouble(words);
			}			
		}
		int num = (int) (wordsNum * 1000);
		wordsNum = (double) (num) / 1000;
		return wordsNum;
	}
	
	/*public static void main(String[] args){
		String str = "2千多字";
		System.out.println(str.replaceAll("[\u4e00-\u9fa5]+", ""));
		
		Double words=12.0;
		int num = (int) (words * 1000);
		System.out.println((double)(num)/1000);
	}*/
}
