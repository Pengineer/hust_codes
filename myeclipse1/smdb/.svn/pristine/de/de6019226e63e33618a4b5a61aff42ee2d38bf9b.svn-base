package csdc.tool.execution.importer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import csdc.bean.Book;
import csdc.bean.Consultation;
import csdc.bean.Electronic;
import csdc.bean.GeneralMidinspection;
import csdc.bean.GeneralVariation;
import csdc.bean.Paper;
import csdc.bean.Patent;
import csdc.bean.ProjectGranted;
import csdc.bean.ProjectMidinspection;
import csdc.bean.ProjectMidinspectionProduct;
import csdc.bean.ProjectProduct;
import csdc.bean.SinossBook;
import csdc.bean.SinossConsultation;
import csdc.bean.SinossElectronic;
import csdc.bean.SinossPaper;
import csdc.bean.SinossPatent;
import csdc.bean.SinossProjectMidinspection;
import csdc.bean.SystemOption;
import csdc.tool.StringTool;

//关于成果导入异常的数据，已经入库，只不过有些字段由于不规范没有入库，因此显示在前台，直接在正式库中手动修改即可

public class GeneralProjectMidInspectionAutoImporter extends ProjectMidInspectionAutoImporter {
	@SuppressWarnings("unchecked")
	@Override
	public void work() throws Throwable {
		if (illegalException != null) {
			illegalException = null;
		}
		isFinished = 0;
		currentNum = 0;
		totalImportNum = 0;
		List<SinossProjectMidinspection> spMidinspections = dao.query("select spm from SinossProjectMidinspection spm where spm.projectType = 'gener' and spm.isAdded = 1");		
		totalNum = spMidinspections == null ? 0 : spMidinspections.size();
		//初始化审核信息
		initCheckInfo();
		//初始化立项信息
		initGranded();
		//初始化论文集
		initPaper();
		//初始化著作
		initBook();
		//初始化专利
		initPatent();
		//初始化电子出版物
		initElectronic();
		//初始化研究咨询报告
		initConsulation();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String midImportedDate = sdf.format(new Date());
		SystemOption 论文集 = (SystemOption) dao.queryUnique("select so from SystemOption so where so.standard = 'productType' and so.name = '论文集'");
		SystemOption 期刊论文 = (SystemOption) dao.queryUnique("select so from SystemOption so where so.standard = 'productType' and so.name = '编著'");
		SystemOption 国内 = (SystemOption) dao.queryUnique("select so from SystemOption so where so.standard = 'publicationScope' and so.name = '国内'");
		SystemOption 国外 = (SystemOption) dao.queryUnique("select so from SystemOption so where so.standard = 'publicationScope' and so.name = '国外'");
		SystemOption 港澳台 = (SystemOption) dao.queryUnique("select so from SystemOption so where so.standard = 'publicationScope' and so.name = '港澳台'");
		SystemOption 编著 = (SystemOption) dao.queryUnique("select so from SystemOption so where so.standard = 'productType' and so.name = '期刊论文'");
		SystemOption 工具书 = (SystemOption) dao.queryUnique("select so from SystemOption so where so.standard = 'productType' and so.name = '工具书'");
		SystemOption 教材 = (SystemOption) dao.queryUnique("select so from SystemOption so where so.standard = 'productType' and so.name = '教材'");
		SystemOption 译著 = (SystemOption) dao.queryUnique("select so from SystemOption so where so.standard = 'productType' and so.name = '译著'");
		SystemOption 专著 = (SystemOption) dao.queryUnique("select so from SystemOption so where so.standard = 'productType' and so.name = '专著'");	
		List<String[]> notFoundGrantedProjectName = null;//该项目不存在立项（导入中检、变更）
		List<String[]> notImportProductName = null;//该项目的成果未成功导入
		for (SinossProjectMidinspection spm: spMidinspections) {
			//取消导入
			if (status != 0) {
				freeMemory();
				throw new RuntimeException();
			} 
			System.out.println(spm.getName() + "----gener:" + (++currentNum) + "/" + spMidinspections.size());
			
			ProjectGranted granted = null;
			String grantedId = null;
			if(spm.getProjectGranted() == null){
				granted = findGrantedByName(spm.getCode().trim(), spm.getName().trim());
				//granted = (ProjectGranted) dao.queryUnique("select pg from ProjectGranted pg where pg.number='" + spm.getCode() + "' and pg.name='" + spm.getName() + "' ");
				if(granted != null){
					grantedId = granted.getId();
				}
			}else{
				grantedId = spm.getProjectGranted().getId();
				granted = getProjectGranted(grantedId);
			}
			
			if (grantedId == null) {
				if (notFoundGrantedProjectName == null) {
					notFoundGrantedProjectName = new ArrayList<String[]>();
				}
				String[] tempNotFoundGrantedProjectName = new String[2];
				tempNotFoundGrantedProjectName[0] = spm.getName();
				notFoundGrantedProjectName.add(tempNotFoundGrantedProjectName);
				spm.setNote(exchangeNoteInfo(spm.getNote(), "该项目不存在立项（导入中检、变更）", spm.getName(), "null"));
			}else {
				//String grantedId = spm.getProjectGranted().getId();
				//ProjectGranted granted = getProjectGranted(grantedId);
				spm.setIsAdded(0);
				//spm.setNote("2015年录入:一般");
				//中检延期理由
				if (spm.getDeferReason() != null) {//录入变更
					GeneralVariation gVariation = new GeneralVariation();
					
					gVariation.setCreateDate(new Date());
					gVariation.setCreateMode(0);
					gVariation.setOther(1);
					gVariation.setApplicantSubmitStatus(3);
					gVariation.setDeptInstAuditStatus(3);
					gVariation.setDeptInstAuditResult(2);
					gVariation.setStatus(3);
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
							gVariation.setStatus(3);//学校审核不同意，留在学校这一级
						} else {
							gVariation.setUniversityAuditResult(2);
							gVariation.setStatus(4);//学校审核同意，向上一级推送
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
					granted.addVariation(gVariation);
				} else {//录入中检
					//if (spm.getApplyDate() != null) {
						GeneralMidinspection gmi = new GeneralMidinspection();
						gmi.setCreateDate(new Date());
						gmi.setCreateMode(0);
						granted.addMidinspection(gmi);
						gmi.setApplicantSubmitDate(spm.getApplyDate());
						gmi.setApplicantSubmitStatus(3);
						
						checkInfo(spm, gmi, granted);
					//}
				}
				//录入论文集
				List<SinossPaper> tempSinossPapers = sinossPaperMap.get(grantedId);
				if (tempSinossPapers != null) {
					for (SinossPaper sPaper : tempSinossPapers) {
						
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
						//被引情况（刊物类型）
						if (sPaper.getPaperBookType() != null) {
							String index = sPaper.getPaperBookType().replaceAll(",", "; ");
							paper.setIndex(index);
						}			
						//字数
						/*if(sPaper.getWordNumber() != null){
							if(!sPaper.getWordNumber().replaceAll("\\s+", "").equals("")){
								paper.setWordNumber(regulateWordsNum(sPaper.getWordNumber()));
							}
						}*/
						String tempWordLength = sPaper.getWordNumber();
						try {
							//字数
							if(tempWordLength != null){
								tempWordLength = tempWordLength.replaceAll("\\s+", "");
								//regulateWordsNum(sPaper.getWordNumber());
								paper.setWordNumber(regulateWordsNum(sPaper.getWordNumber()));
							}
						} catch (Exception e) {
							if (notImportProductName == null) {
								notImportProductName = new ArrayList<String[]>();
							}
							String[] tempNotImportProductName = new String[2];
							tempNotImportProductName[0] = spm.getName();
							tempNotImportProductName[1] = new String("Paper_（" + sPaper.getName() +"）" + "[" + tempWordLength +"]");
							notImportProductName.add(tempNotImportProductName);
							spm.setNote(exchangeNoteInfo(spm.getNote(), "该项目成果的字数信息未成功导入（Paper）", spm.getName(), sPaper.getName() + "[" + tempWordLength +"]"));
							continue;
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
							if (pMidinspection.getCreateDate() != null && sdf.format(pMidinspection.getCreateDate()).equals(midImportedDate)) {         
								pmProduct.setProjectMidinspection(pMidinspection);
								pmProduct.setFirstAuditStatus(3);
								pmProduct.setFirstAuditResult(pMidinspection.getUniversityAuditResult());
							}
						}
						dao.add(pmProduct);
					}
				}

				//录入著作
				List<SinossBook> tempSinossBooks = sinossBookMap.get(grantedId);
				if (tempSinossBooks != null) {
					for (SinossBook sBook : tempSinossBooks) {
						
						
						Book book = new Book();
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
						String tempWordLength = sBook.getWordNumber();
						/*if (sBook.getWordNumber() != null) {
							if(!sBook.getWordNumber().replaceAll("\\s+", "").equals("")){
								book.setWordNumber(regulateWordsNum(sBook.getWordNumber()));
							}
						}*/
						try {
							//字数
							if(tempWordLength != null){
								tempWordLength = tempWordLength.replaceAll("\\s+", "");
								//regulateWordsNum(sBook.getWordNumber());
								book.setWordNumber(regulateWordsNum(sBook.getWordNumber()));
							}
						} catch (Exception e) {
							if (notImportProductName == null) {
								notImportProductName = new ArrayList<String[]>();
							}
							String[] tempNotImportProductName = new String[2];
							tempNotImportProductName[0] = spm.getName();
							tempNotImportProductName[1] = new String("Book_（" + sBook.getName() +"）" + "[" + tempWordLength +"]");
							notImportProductName.add(tempNotImportProductName);
							spm.setNote(exchangeNoteInfo(spm.getNote(), "该项目成果的字数信息未成功导入（Book）", spm.getName(), sBook.getName() + "[" + tempWordLength +"]"));
							continue;
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
						projectProduct.setProjectGranted(granted);
						//是否标注
						if ("是".equals(sBook.getIsMark())) {
							projectProduct.setIsMarkMoeSupport(1);
						} else {
							projectProduct.setIsMarkMoeSupport(0);
						}
						dao.add(projectProduct);
						
						Set<ProjectMidinspection> pMidinspections = (Set<ProjectMidinspection>) granted.getMidinspection();
						ProjectMidinspectionProduct pmProduct = new ProjectMidinspectionProduct();
						pmProduct.setProduct(book);
						for (ProjectMidinspection pMidinspection: pMidinspections) {
							if (pMidinspection.getCreateDate() != null && sdf.format(pMidinspection.getCreateDate()).equals(midImportedDate)) {
								pmProduct.setProjectMidinspection(pMidinspection);
								pmProduct.setFirstAuditStatus(3);
								pmProduct.setFirstAuditResult(pMidinspection.getUniversityAuditResult());
							}
						}
						dao.add(pmProduct);	
					}
				}
				
				//录入专利
				List<SinossPatent> tempSinossPatents = sinossPatentMap.get(grantedId);
				if (tempSinossPatents != null) {
					for (SinossPatent sPatent : tempSinossPatents) {
						Patent patent = new Patent();
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
			            //处理专利号问题
			            String patentNumber = sPatent.getPatentNumber();
			            patentNumber = StringTool.toDBC(patentNumber);
			            patentNumber = patentNumber.replaceAll("[^a-zA-Z0-9\\.]", "");
			            patent.setPublicNumber(patentNumber);
			            
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
						projectProductPatent.setProjectGranted(granted);
						//是否标注
						if ("是".equals(sPatent.getIsMark())) {
							projectProductPatent.setIsMarkMoeSupport(1);
						} else {
							projectProductPatent.setIsMarkMoeSupport(0);
						}
						dao.add(projectProductPatent);
						
						Set<ProjectMidinspection> pms = (Set<ProjectMidinspection>) granted.getMidinspection();
						ProjectMidinspectionProduct pm = new ProjectMidinspectionProduct();
						pm.setProduct(patent);
						for (ProjectMidinspection pMidinspection: pms) {
							if (pMidinspection.getCreateDate() != null && sdf.format(pMidinspection.getCreateDate()).equals(midImportedDate)) {   //同上
								pm.setProjectMidinspection(pMidinspection);
								pm.setFirstAuditStatus(3);
								pm.setFirstAuditResult(pMidinspection.getUniversityAuditResult());
							}
						}
						dao.add(pm);
					}
				}
				
				//录入电子出版物
				List<SinossElectronic> tempSinossElectronics = sinossElectronicMap.get(grantedId);
				if (tempSinossElectronics != null) {
					for (SinossElectronic sElectronic : tempSinossElectronics) {
						Electronic electronic = new Electronic();
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
			            electronic.setSubmitStatus(3);
			            dao.add(electronic);
			            
						ProjectProduct projectElectronic = new ProjectProduct();
						projectElectronic.setProduct(electronic);
						projectElectronic.setProjectGranted(granted);
						//是否标注
						if ("是".equals(sElectronic.getIsMark())) {
							projectElectronic.setIsMarkMoeSupport(1);
						} else {
							projectElectronic.setIsMarkMoeSupport(0);
						}
						dao.add(projectElectronic);
						
						Set<ProjectMidinspection> pmes = (Set<ProjectMidinspection>) granted.getMidinspection();
						ProjectMidinspectionProduct pmc = new ProjectMidinspectionProduct();
						pmc.setProduct(electronic);
						for (ProjectMidinspection pMidinspection: pmes) {
							if(pMidinspection.getCreateDate() != null && sdf.format(pMidinspection.getCreateDate()).equals(midImportedDate)){
								pmc.setProjectMidinspection(pMidinspection);
								pmc.setFirstAuditStatus(3);
								pmc.setFirstAuditResult(pMidinspection.getUniversityAuditResult());
							}
						}
						dao.add(pmc);	
					}
				}
				
				//录入研究咨询报告
				List<SinossConsultation> tempSinossConsultations = sinossConsultationMap.get(grantedId);
				if (tempSinossConsultations != null) {
					for (SinossConsultation sConsultation : tempSinossConsultations) {
						Consultation consultation = new Consultation();
						//ProjectGranted grantedBook = (ProjectGranted) dao.queryUnique("from ProjectGranted g where g.id = ?", sBook.getProjectMidinspection().getProjectGranted().getId());
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
						projectConsultation.setProjectGranted(granted);
						//是否标注
						if ("是".equals(sConsultation.getIsMark())) {
							projectConsultation.setIsMarkMoeSupport(1);
						} else {
							projectConsultation.setIsMarkMoeSupport(0);
						}
						dao.add(projectConsultation);
						
						Set<ProjectMidinspection> pmcs = (Set<ProjectMidinspection>) granted.getMidinspection();
						ProjectMidinspectionProduct pmcc = new ProjectMidinspectionProduct();
						pmcc.setProduct(consultation);
						for (ProjectMidinspection pMidinspection: pmcs) {
							if(pMidinspection.getCreateDate() != null && sdf.format(pMidinspection.getCreateDate()).equals(midImportedDate)){
								pmcc.setProjectMidinspection(pMidinspection);
								pmcc.setFirstAuditStatus(3);
								pmcc.setFirstAuditResult(pMidinspection.getUniversityAuditResult());
							}
						}
						dao.add(pmcc);	
					}
				}
				totalImportNum++;
			}
		}
		if (notFoundGrantedProjectName != null && notFoundGrantedProjectName.size() > 0) {
			if (illegalException == null) {
				illegalException = new HashMap<String, List<String[]>>();
			}
			List<String[]> tempException = new ArrayList<String[]>(notFoundGrantedProjectName);
			illegalException.put("该项目不存在立项（导入中检、变更）", tempException);
		}
		if (notImportProductName != null && notImportProductName.size() > 0) {
			if (illegalException == null) {
				illegalException = new HashMap<String, List<String[]>>();
			}
			List<String[]> tempException = new ArrayList<String[]>(notImportProductName);
			illegalException.put("该项目成果的字数信息未成功导入", tempException);
		}
		freeMemory();
		isFinished = 1;
	}
}
