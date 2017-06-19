package csdc.tool.execution.importer;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.vote.ConsensusBased;
import org.springframework.stereotype.Component;

import csdc.bean.Agency;
import csdc.bean.Book;
import csdc.bean.Consultation;
import csdc.bean.Electronic;
import csdc.bean.GeneralGranted;
import csdc.bean.GeneralMidinspection;
import csdc.bean.GeneralVariation;
import csdc.bean.InstpGranted;
import csdc.bean.InstpMidinspection;
import csdc.bean.InstpVariation;
import csdc.bean.Paper;
import csdc.bean.Patent;
import csdc.bean.Product;
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
import csdc.tool.execution.Execution;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 将与社科网对接的中检中间表数据入库正式表
 * @author 
 * @status 
 */
@Component
public class ProjectMidInspectionImporter extends Execution {
	
	@Autowired
	private HibernateBaseDao dao;
	
	@Autowired
	private Tool tool;

	@Autowired
	private GeneralProjectFinder generalProjectFinder;
	
	@Autowired
	private InstpProjectFinder instpProjectFinder;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Override
	public void work() throws Throwable {
		/*//一般项目
		List<SinossProjectMidinspection> spMidinspections = dao.query("from SinossProjectMidinspection spm where spm.projectType = 'general'");		
		for (SinossProjectMidinspection spm: spMidinspections) {
			GeneralGranted granted = (GeneralGranted) dao.queryUnique("from GeneralGranted g where g.id = ?", spm.getSmdbProjectId());
			if (spm.getDeferDate() != null) {//录入变更
				GeneralVariation gVariation = new GeneralVariation();
				granted.addVariation(gVariation);
				gVariation.setImportedDate(new Date());
				gVariation.setApplicantSubmitStatus(3);
				gVariation.setDeptInstAuditStatus(3);
				gVariation.setDeptInstAuditResult(2);
				gVariation.setDeptInstAuditResultDetail("000000001");
				gVariation.setOther(1);
				gVariation.setOtherInfo("申请项目中检延期。");
				gVariation.setApplicantSubmitDate(spm.getDeferDate());
				gVariation.setStatus(5);
				if (spm.getDeferReason() != null) {
					gVariation.setVariationReason(spm.getDeferReason());
				}
				if (spm.getCheckStatus().contains("学校")) {
					gVariation.setUniversityAuditResultDetail("000000001");
					gVariation.setUniversityAuditStatus(3);
					gVariation.setStatus(5);
					if (spm.getCheckStatus().contains("不")) {
						gVariation.setUniversityAuditResult(1);
					} else {
						gVariation.setUniversityAuditResult(2);
					}
				} else if (spm.getCheckStatus().contains("主管")) {
					gVariation.setStatus(5);
					gVariation.setProvinceAuditResultDetail("000000001");
					gVariation.setProvinceAuditResult(2);
					gVariation.setProvinceAuditStatus(3);
					gVariation.setUniversityAuditResult(2);
					gVariation.setUniversityAuditResultDetail("000000001");
					gVariation.setUniversityAuditStatus(3);
				}
			} else {//录入中检
				if (spm.getStatus().contains("进行中") && (spm.getApplyDate() != null)) {
					GeneralMidinspection gmi = new GeneralMidinspection();
					gmi.setImportedDate(new Date());
					granted.addMidinspection(gmi);
					gmi.setApplicantSubmitDate(spm.getApplyDate());
					gmi.setApplicantSubmitStatus(3);
					gmi.setDeptInstAuditStatus(3);
					gmi.setDeptInstAuditResult(2);
					gmi.setDeptInstAuditDate(spm.getApplyDate());
					gmi.setStatus(5);
					if (spm.getCheckStatus().contains("学校")) {
						gmi.setUniversityAuditStatus(3);
						gmi.setStatus(5);
						if (spm.getCheckStatus().contains("不")) {
							gmi.setUniversityAuditResult(1);
						} else {
							gmi.setUniversityAuditResult(2);
						}
					} else if (spm.getCheckStatus().contains("主管")) {
						gmi.setUniversityAuditResult(2);
						gmi.setUniversityAuditStatus(3);
						gmi.setProvinceAuditResult(2);
						gmi.setProvinceAuditStatus(3);
						gmi.setStatus(5);
					}
				}
			}
		}
		
		//基地项目
		List<SinossProjectMidinspection> spMidinspections2 = dao.query("from SinossProjectMidinspection spm where spm.projectType = 'instp'");		
		for (SinossProjectMidinspection spm: spMidinspections2) {
			InstpGranted granted = (InstpGranted) dao.queryUnique("from InstpGranted g where g.id = ?", spm.getSmdbProjectId());
			if (spm.getDeferDate() != null) {//录入变更
				InstpVariation gVariation = new InstpVariation();
				granted.addVariation(gVariation);
				gVariation.setImportedDate(new Date());
				gVariation.setApplicantSubmitStatus(3);
				gVariation.setDeptInstAuditStatus(3);
				gVariation.setDeptInstAuditResult(2);
				gVariation.setDeptInstAuditResultDetail("000000001");
				gVariation.setOther(1);
				gVariation.setOtherInfo("申请项目中检延期。");
				gVariation.setApplicantSubmitDate(spm.getDeferDate());
				gVariation.setStatus(5);
				if (spm.getDeferReason() != null) {
					gVariation.setVariationReason(spm.getDeferReason());
				}
				if (spm.getCheckStatus().contains("学校")) {
					gVariation.setUniversityAuditResultDetail("000000001");
					gVariation.setUniversityAuditStatus(3);
					gVariation.setStatus(5);
					gVariation.setUniversityAuditResult(2);
				} else if (spm.getCheckStatus().contains("主管")) {
					gVariation.setStatus(5);
					gVariation.setProvinceAuditResultDetail("000000001");
					gVariation.setProvinceAuditResult(2);
					gVariation.setProvinceAuditStatus(3);
					gVariation.setUniversityAuditResult(2);
					gVariation.setUniversityAuditResultDetail("000000001");
					gVariation.setUniversityAuditStatus(3);
				}
			} else {//录入中检
				if (spm.getApplyDate() != null) {
					InstpMidinspection gmi = new InstpMidinspection();
					gmi.setImportedDate(new Date());
					granted.addMidinspection(gmi);
					gmi.setApplicantSubmitDate(spm.getApplyDate());
					gmi.setApplicantSubmitStatus(3);
					gmi.setDeptInstAuditStatus(3);
					gmi.setDeptInstAuditResult(2);
					gmi.setDeptInstAuditDate(spm.getApplyDate());
					gmi.setStatus(5);
					if (spm.getCheckStatus().contains("学校")) {
						gmi.setUniversityAuditStatus(3);
						gmi.setStatus(5);
						if (spm.getCheckStatus().contains("不")) {
							gmi.setUniversityAuditResult(1);
						} else {
							gmi.setUniversityAuditResult(2);
						}
					} else if (spm.getCheckStatus().contains("主管")) {
						gmi.setUniversityAuditResult(2);
						gmi.setUniversityAuditStatus(3);
						gmi.setProvinceAuditResult(2);
						gmi.setProvinceAuditStatus(3);
						gmi.setStatus(5);
					}
				}
			}
		}*/
		
		//从中间表中录入审核信息
		String importedDate = "2013-10-20"; 		
		List<ProjectMidinspection> projectMidinspections = dao.query("from ProjectMidinspection so where to_char(so.importedDate, 'yyyy-MM-dd') = ?", importedDate);
		
		//在中间表中根据中检表中的立项id搜索对应数据
		for (ProjectMidinspection projectMidinspection: projectMidinspections) {
			SinossProjectMidinspection sProjectMidinspection = (SinossProjectMidinspection) dao.queryUnique("from SinossProjectMidinspection o where o.projectGranted.id = ?", projectMidinspection.getGrantedId());
			
			//在审核记录表中根据中间表中的立项id搜索对审核记录数据
			List<SinossChecklogs> sChecklogs = dao.query("from SinossChecklogs o where o.projectId = ?", sProjectMidinspection.getProjectId());
			SinossChecklogs[] maxSChecklogs = {null, null};//[0]：校级审核记录；[1]:主管部门审核记录
			for (SinossChecklogs sChecklog : sChecklogs) {
				int checkStatus = sChecklog.getCheckStatus();
				if (checkStatus == 2 || checkStatus ==3 ) {//校级审核
					maxSChecklogs[0] = (maxSChecklogs[0] != null && sChecklog.getCheckDate().before(maxSChecklogs[0].getCheckDate())) ? maxSChecklogs[0]: sChecklog;
				}else if (checkStatus == 4 || checkStatus ==5) {//主管部门审核
					maxSChecklogs[1] = (maxSChecklogs[1] != null && sChecklog.getCheckDate().before(maxSChecklogs[1].getCheckDate())) ? maxSChecklogs[1]: sChecklog;
				}				
			}																																		
			//将中间表中的校级审核记录写到中检表的对应字段
			if (maxSChecklogs[0] != null) {
				projectMidinspection.setUniversityAuditDate(maxSChecklogs[0].getCheckDate());
				projectMidinspection.setUniversityAuditOpinion(maxSChecklogs[0].getCheckInfo());
				Agency university = universityFinder.getUnivByName(maxSChecklogs[0].getChecker());
				projectMidinspection.setUniversityAuditorAgency(university);	
			}
			//将中间表中的主管部门审核记录写到中检表的对应字段
			if (maxSChecklogs[1] != null) {
				projectMidinspection.setProvinceAuditDate(maxSChecklogs[1].getCheckDate());
				projectMidinspection.setProvinceAuditOpinion(maxSChecklogs[1].getCheckInfo());
				Agency province = universityFinder.getProByName(maxSChecklogs[1].getChecker());
				projectMidinspection.setProvinceAuditorAgency(province);
			}
		}
		/*//论文成果
		//List<Object[]> objpapers = dao.query("select sp, spm.projectGranted.id from SinossPaper sp left join sp.projectMidinspection spm");
		SystemOption 论文集 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'productType' and so.name = '论文集'");
		SystemOption 期刊论文 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'productType' and so.name = '编著'");
		SystemOption 编著 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'productType' and so.name = '期刊论文'");
		SystemOption 工具书 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'productType' and so.name = '工具书'");
		SystemOption 教材 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'productType' and so.name = '教材'");
		SystemOption 译著 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'productType' and so.name = '译著'");
		SystemOption 专著 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'productType' and so.name = '专著'");
		SystemOption 国内 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'pulicationScope' and so.name = '国内'");
		SystemOption 国外 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'pulicationScope' and so.name = '国外'");
		SystemOption 港澳台 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'pulicationScope' and so.name = '国外'");
		
		/*for (Object[] obj : objpapers) {
			SinossPaper sPaper = (SinossPaper) obj[0];
//			SinossProjectMidinspection spp= sPaper.getProjectMidinspection();
//			String aaString = sPaper.getProjectMidinspection().getProjectGranted().getName();
			ProjectGranted granted = (ProjectGranted) dao.queryUnique("from ProjectGranted g where g.id = ?",(String) obj[1]);
			Paper paper = new Paper();
			if (sPaper.getName() != null) {
				paper.setChineseName(sPaper.getName());//论文名称
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
			//被引情况
			if (sPaper.getPaperBookType() != null) {
				String index = sPaper.getPaperBookType().replaceAll(",", "; ");
				paper.setIndex(index);
			}			
			//字数
			paper.setWordNumber(sPaper.getFixedWordNumber());
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
			paper.setPage(sPaper.getPagenumScope());//页码范围
			paper.setIssn(sPaper.getIssn());//ISSN号
			paper.setCn(sPaper.getCn());//CN号
			paper.setPublicationDate(sPaper.getPublicDate());//发表时间
			dao.add(paper);
				
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
			
			Set<ProjectMidinspection> pMidinspections = (Set<ProjectMidinspection>) granted.getMidinspection();
			ProjectMidinspectionProduct pmProduct = new ProjectMidinspectionProduct();
			pmProduct.setProduct(paper);
			for (ProjectMidinspection pMidinspection: pMidinspections) {
				if (pMidinspection.getImportedDate().after(tool.getDate("2013-10-20"))) {
					pmProduct.setProjectMidinspection(pMidinspection);
					pmProduct.setFirstAuditStatus(3);
					pmProduct.setFirstAuditResult(pMidinspection.getUniversityAuditResult());
				}
			}
			dao.add(pmProduct);						
	}
		
		//著作成果
		List<Object[]> objbooks = dao.query("select sb, spm.projectGranted.id from SinossBook sb left join sb.projectMidinspection spm");
		for (Object[] objbook: objbooks) {
			SinossBook sBook = (SinossBook)objbook[0];
			ProjectGranted grantedBook = (ProjectGranted) dao.queryUnique("from ProjectGranted g where g.id = ?",(String) objbook[1]);
			Book book = new Book();
//			ProjectGranted grantedBook = (ProjectGranted) dao.queryUnique("from ProjectGranted g where g.id = ?", sBook.getProjectMidinspection().getProjectGranted().getId());
			if (sBook.getName() == null) {
				book.setChineseName("著作名称不存在");
			} else {
				book.setChineseName(sBook.getName());
			}	
			book.setAuditorName(sBook.getFirstAuthor());
			book.setOtherAuthorName(sBook.getOtherAuthor());
			book.setDiscipline(sBook.getSubject());//学科代码
			book.setDisciplineType(sBook.getDisciplineType());//学科名称
            book.setPublishUnit(sBook.getPress());
            book.setPublishDate(sBook.getPressTime());
          //发表范围
			if (sBook.getPressAddress() != null) {
				if (sBook.getPressAddress().contains("国内")) {
					book.setPublicationScope(国内);
				} else if (sBook.getPressAddress().contains("国外")) {
					book.setPublicationScope(国外);
				}		
			}
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
			//字数
			if (sBook.getWordNumber() != null && !sBook.getWordNumber().isEmpty()) {
				book.setWordNumber(Double.valueOf(sBook.getWordNumber()));
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
            dao.add(book);
            
            dao.flush();
            
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
				if (pMidinspection.getImportedDate().after(tool.getDate("2013-10-20"))) {
					pmProduct.setProjectMidinspection(pMidinspection);
					pmProduct.setFirstAuditStatus(3);
					pmProduct.setFirstAuditResult(pMidinspection.getUniversityAuditResult());
				}
			}
			dao.add(pmProduct);	
		}
 

		//专利成果
			List<Object[]> objpatents = dao.query("select sp, spm.projectGranted.id from SinossPatent sp left join sp.projectMidinspection spm");
			for (Object[] objpatent: objpatents) {
				SinossPatent sPatent = (SinossPatent)objpatent[0];
				ProjectGranted grantedPatent = (ProjectGranted) dao.queryUnique("from ProjectGranted g where g.id = ?",(String) objpatent[1]);
				Patent patent = new Patent();
//				ProjectGranted grantedBook = (ProjectGranted) dao.queryUnique("from ProjectGranted g where g.id = ?", sBook.getProjectMidinspection().getProjectGranted().getId());
				if (sPatent.getName() != null) {
					patent.setChineseName(sPatent.getName());
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
	          //发表范围
				if (sPatent.getPatentScope() != null) {
					if (sPatent.getPatentScope().contains("国内")) {
						patent.setScope(国内);
					} else if (sPatent.getPatentScope().contains("国外")) {
						patent.setScope(国外);
					}		
				}
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
					if (pMidinspection.getImportedDate().after(tool.getDate("2013-10-20"))) {
						pm.setProjectMidinspection(pMidinspection);
						pm.setFirstAuditStatus(3);
						pm.setFirstAuditResult(pMidinspection.getUniversityAuditResult());
					}
				}
				dao.add(pm);
			}

		
		//电子出版物
				List<Object[]> objelectronics = dao.query("select se, spm.projectGranted.id from SinossElectronic se left join se.projectMidinspection spm");
				for (Object[] objelectronic: objelectronics) {
					SinossElectronic sElectronic = (SinossElectronic)objelectronic[0];
					ProjectGranted grantedEle = (ProjectGranted) dao.queryUnique("from ProjectGranted g where g.id = ?",(String) objelectronic[1]);
					Electronic electronic = new Electronic();
//					ProjectGranted grantedBook = (ProjectGranted) dao.queryUnique("from ProjectGranted g where g.id = ?", sBook.getProjectMidinspection().getProjectGranted().getId());
					if (sElectronic.getName() != null) {
						electronic.setChineseName(sElectronic.getName());
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
						if (pme.getImportedDate().after(tool.getDate("2013-10-20"))) {
							pmc.setProjectMidinspection(pme);
							pmc.setFirstAuditStatus(3);
							pmc.setFirstAuditResult(pme.getUniversityAuditResult());
						}
					}
					dao.add(pmc);	
				}
	    //研究报告
				List<Object[]> objcons = dao.query("select sc, spm.projectGranted.id from SinossConsultation sc left join sc.projectMidinspection spm");
				for (Object[] objconsultation: objcons) {
					SinossConsultation sConsultation = (SinossConsultation)objconsultation[0];
					ProjectGranted grantedcon = (ProjectGranted) dao.queryUnique("from ProjectGranted g where g.id = ?",(String) objconsultation[1]);
					Consultation consultation = new Consultation();
//					ProjectGranted grantedBook = (ProjectGranted) dao.queryUnique("from ProjectGranted g where g.id = ?", sBook.getProjectMidinspection().getProjectGranted().getId());
					if (sConsultation.getName() != null) {
						consultation.setChineseName(sConsultation.getName());		
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
					consultation.setPublicationUnit(sConsultation.getCommitUnit());
					if (sConsultation.getIsAccept().contains("是")) {
						consultation.setIsAdopted(1);
					} else if (sConsultation.getAcceptObj().contains("否")) {
						consultation.setIsAdopted(0);
					}		            
		            
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
						if (pmca.getImportedDate().after(tool.getDate("2013-10-20"))) {
							pmcc.setProjectMidinspection(pmca);
							pmcc.setFirstAuditStatus(3);
							pmcc.setFirstAuditResult(pmca.getUniversityAuditResult());
						}
					}
					dao.add(pmcc);	
				}*/
	
				
				
		
	}
		


}
