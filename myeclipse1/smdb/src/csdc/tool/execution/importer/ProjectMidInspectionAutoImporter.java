package csdc.tool.execution.importer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java_cup.internal_error;
import java_cup.version;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Agency;
import csdc.bean.Book;
import csdc.bean.Consultation;
import csdc.bean.Electronic;
import csdc.bean.GeneralGranted;
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
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.UniversityFinder;

/**
 * 将与社科网对接的中检中间表数据入库正式表
 * @author pengliang
 */
@Component
public class ProjectMidInspectionAutoImporter extends Execution {
	
	@Autowired
	protected HibernateBaseDao dao;
	
	protected int count = 0;
	
	/**
	 * 初始化审核数据到内存
	 */
	protected Map<String, List<SinossChecklogs>> sinossChecklogs;
	
	/**
	 * 初始化立项数据到内存<id,projectGranted>
	 */
	protected Map<String, ProjectGranted> pGranted;
	
	/**
	 * 初始化中检数据到内存<id,projectGranted>
	 */
	protected Map<String, List<ProjectMidinspection>> pMidins;
	
	//总项目数
	protected int totalNum = 0;
	
	// 当前导入项目条数		  
	protected int currentNum = 0;
	
	// 总共导入项目条数		  
	protected int totalImportNum = 0;

	//是否取消导入（1：是；0：否）
	protected int status;
	
	//是否导入完毕 （1：是；0：否）
	protected int isFinished;
	
	//异常数据
	protected Map<String,List<String[]>> illegalException;

	protected Map<String, List<SinossPaper>> sinossPaperMap;
	protected Map<String, List<SinossBook>> sinossBookMap;
	protected Map<String, List<SinossPatent>> sinossPatentMap;
	protected Map<String, List<SinossElectronic>> sinossElectronicMap;
	protected Map<String, List<SinossConsultation>> sinossConsultationMap;
	
	@Override
	protected void work() throws Throwable {
	}
	/**
	 * [立项编号] -> [granted.id]
	 */
	private Map<String, String> pNumberMap;
	
	/**
	 * [立项编号 + 项目名称] -> [granted.id]
	 */
	private Map<String, String> pNameMap;
	
	public Map<String, Agency> agencyMap;
	
	public ProjectGranted findGrantedByName(String number, String name) {
		if (pNumberMap == null) {
			initGranded();
		}
		String key = number + name;
		String grantedId = pNameMap.get(key);
		return (ProjectGranted) (grantedId == null ? null : dao.query(ProjectGranted.class, grantedId));
	}
	
	public Agency getAgencyByName(String agencyName) {
		if (agencyMap == null) {
			initUnivMap();
		}
		return agencyMap.get(agencyName);
	}
	
	private void initUnivMap() {
		long beginTime  = new Date().getTime();

		agencyMap = new HashMap<String, Agency>();
		List<Agency> agencyList = dao.query("select agency from Agency agency");
		for (Agency agency : agencyList) {
			agencyMap.put(agency.getName().trim(), agency);
		}
		
		System.out.println("initUnivMap complete! Used time: " + (new Date().getTime() - beginTime) + "ms");
	}
	
	//初始化论文集
	public void initPaper(){
		Long begin = System.currentTimeMillis();
		List<Object[]> objpapers = dao.query("select sp, spm.projectGranted.id from SinossPaper sp left join sp.projectMidinspection spm where spm.isAdded = 1");
		if (sinossPaperMap == null) {
			sinossPaperMap = new HashMap<String, List<SinossPaper>>();
		}
		List<SinossPaper> tempPapers;
		for (Object[] objects : objpapers) {
			if(objects[1] == null){
				continue;
			}
			String grantedId = objects[1].toString();
			tempPapers = sinossPaperMap.get(grantedId);
			if (tempPapers == null) {
				tempPapers = new ArrayList<SinossPaper>();
			}
			tempPapers.add((SinossPaper) objects[0]);
			sinossPaperMap.put(grantedId, tempPapers);
		}
		System.out.println("InitPaperInfo Complete! Used time:" + (System.currentTimeMillis() - begin) + "ms");
	}
	
	//初始化著作
	public void initBook(){
		Long begin = System.currentTimeMillis();
		List<Object[]> objbooks = dao.query("select sb, spm.projectGranted.id from SinossBook sb left join sb.projectMidinspection spm where spm.isAdded = 1");
		if (sinossBookMap == null) {
			sinossBookMap = new HashMap<String, List<SinossBook>>();
		}
		List<SinossBook> tempBooks;
		for (Object[] objects : objbooks) {
			if(objects[1] == null){
				continue;
			}
			String grantedId = objects[1].toString();
			tempBooks = sinossBookMap.get(grantedId);
			if (tempBooks == null) {
				tempBooks = new ArrayList<SinossBook>();
			}
			tempBooks.add((SinossBook) objects[0]);
			sinossBookMap.put(grantedId, tempBooks);
		}
		System.out.println("InitBookInfo Complete! Used time:" + (System.currentTimeMillis() - begin) + "ms");
	}
	
	//初始化专利
	public void initPatent(){
		Long begin = System.currentTimeMillis();
		List<Object[]> objpatents = dao.query("select sp, spm.projectGranted.id from SinossPatent sp left join sp.projectMidinspection spm where spm.isAdded = 1");
		if (sinossPatentMap == null) {
			sinossPatentMap = new HashMap<String, List<SinossPatent>>();
		}
		List<SinossPatent> tempPatents;
		for (Object[] objects : objpatents) {
			if(objects[1] == null){
				continue;
			}
			String grantedId = objects[1].toString();
			tempPatents = sinossPatentMap.get(grantedId);
			if (tempPatents == null) {
				tempPatents = new ArrayList<SinossPatent>();
			}
			tempPatents.add((SinossPatent) objects[0]);
			sinossPatentMap.put(grantedId, tempPatents);
		}
		System.out.println("InitPatentInfo Complete! Used time:" + (System.currentTimeMillis() - begin) + "ms");
	}
	
	//初始化电子出版物
	public void initElectronic(){
		Long begin = System.currentTimeMillis();
		List<Object[]> objelectronics = dao.query("select se, spm.projectGranted.id from SinossElectronic se left join se.projectMidinspection spm where spm.isAdded = 1");
		if (sinossElectronicMap == null) {
			sinossElectronicMap = new HashMap<String, List<SinossElectronic>>();
		}
		List<SinossElectronic> tempElectronics;
		for (Object[] objects : objelectronics) {
			if(objects[1] == null){
				continue;
			}
			String grantedId = objects[1].toString();
			tempElectronics = sinossElectronicMap.get(grantedId);
			if (tempElectronics == null) {
				tempElectronics = new ArrayList<SinossElectronic>();
			}
			tempElectronics.add((SinossElectronic) objects[0]);
			sinossElectronicMap.put(grantedId, tempElectronics);
		}
		System.out.println("InitElectronicInfo Complete! Used time:" + (System.currentTimeMillis() - begin) + "ms");
	}
	
	//初始化研究咨询报告
	public void initConsulation(){
		Long begin = System.currentTimeMillis();
		List<Object[]> objcons = dao.query("select sc, spm.projectGranted.id from SinossConsultation sc left join sc.projectMidinspection spm where spm.isAdded = 1");
		if (sinossConsultationMap == null) {
			sinossConsultationMap = new HashMap<String, List<SinossConsultation>>();
		}
		List<SinossConsultation> tempConsulations;
		for (Object[] objects : objcons) {
			if(objects[1] == null){
				continue;
			}
			String grantedId = objects[1].toString();
			tempConsulations = sinossConsultationMap.get(grantedId);
			if (tempConsulations == null) {
				tempConsulations = new ArrayList<SinossConsultation>();
			}
			tempConsulations.add((SinossConsultation) objects[0]);
			sinossConsultationMap.put(grantedId, tempConsulations);
		}
		System.out.println("InitConsulationInfo Complete! Used time:" + (System.currentTimeMillis() - begin) + "ms");
	}
	
	//导入论文集
	public void importPaper() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String midImportedDate = sdf.format(new Date());
		List<Object[]> objpapers = dao.query("select sp, spm.projectGranted.id from SinossPaper sp left join sp.projectMidinspection spm where spm.isAdded = 1");
		SystemOption 论文集 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'productType' and so.name = '论文集'");
		SystemOption 期刊论文 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'productType' and so.name = '编著'");
		SystemOption 国内 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'pulicationScope' and so.name = '国内'");
		SystemOption 国外 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'pulicationScope' and so.name = '国外'");
		SystemOption 港澳台 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'pulicationScope' and so.name = '港澳台'");
		count = 0;
		for (Object[] obj : objpapers) {
			if((String) obj[1] == null){
				continue;
			}
			SinossPaper sPaper = (SinossPaper) obj[0];
			ProjectGranted granted = (ProjectGranted) getProjectGranted((String) obj[1]);
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
				if (pMidinspection.getCreateDate() != null && sdf.format(pMidinspection.getCreateDate()).equals(midImportedDate)) {         
					pmProduct.setProjectMidinspection(pMidinspection);
					pmProduct.setFirstAuditStatus(3);
					pmProduct.setFirstAuditResult(pMidinspection.getUniversityAuditResult());
				}
			}
			dao.add(pmProduct);						
		}
	}
	
	//导入著作
	public void importBook() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String midImportedDate = sdf.format(new Date());
		SystemOption 编著 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'productType' and so.name = '期刊论文'");
		SystemOption 工具书 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'productType' and so.name = '工具书'");
		SystemOption 教材 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'productType' and so.name = '教材'");
		SystemOption 译著 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'productType' and so.name = '译著'");
		SystemOption 专著 = (SystemOption) dao.queryUnique("from SystemOption so where so.standard = 'productType' and so.name = '专著'");			
		count = 0;
		//著作成果
		List<Object[]> objbooks = dao.query("select sb, spm.projectGranted.id from SinossBook sb left join sb.projectMidinspection spm where spm.isAdded = 1");
		for (Object[] objbook: objbooks) {
			if((String) objbook[1] == null){
				continue;
			}
			SinossBook sBook = (SinossBook)objbook[0];
			ProjectGranted grantedBook = (ProjectGranted) getProjectGranted((String) objbook[1]);
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
				if (pMidinspection.getCreateDate() != null && sdf.format(pMidinspection.getCreateDate()).equals(midImportedDate)) {
					pmProduct.setProjectMidinspection(pMidinspection);
					pmProduct.setFirstAuditStatus(3);
					pmProduct.setFirstAuditResult(pMidinspection.getUniversityAuditResult());
				}
			}
			dao.add(pmProduct);	
		}
	}
	
	//导入专利
	public void importPatent() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String midImportedDate = sdf.format(new Date());
		count = 0;
		//专利成果
		List<Object[]> objpatents = dao.query("select sp, spm.projectGranted.id from SinossPatent sp left join sp.projectMidinspection spm where spm.isAdded = 1");
		for (Object[] objpatent: objpatents) {
			if((String) objpatent[1] == null){
				continue;
			}
			SinossPatent sPatent = (SinossPatent)objpatent[0];
			ProjectGranted grantedPatent = (ProjectGranted) getProjectGranted((String) objpatent[1]);
			Patent patent = new Patent();
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
				if (pMidinspection.getCreateDate() != null && sdf.format(pMidinspection.getCreateDate()).equals(midImportedDate)) {   //同上
					pm.setProjectMidinspection(pMidinspection);
					pm.setFirstAuditStatus(3);
					pm.setFirstAuditResult(pMidinspection.getUniversityAuditResult());
				}
			}
			dao.add(pm);
		}
	}
	
	//导入电子出版物
	public void importElectronic() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String midImportedDate = sdf.format(new Date());
		count = 0;
		//电子出版物
		List<Object[]> objelectronics = dao.query("select se, spm.projectGranted.id from SinossElectronic se left join se.projectMidinspection spm where spm.isAdded = 1");
		for (Object[] objelectronic: objelectronics) {
			if((String) objelectronic[1] == null){
				continue;
			}
			SinossElectronic sElectronic = (SinossElectronic)objelectronic[0];
			ProjectGranted grantedEle = (ProjectGranted) getProjectGranted((String) objelectronic[1]);
			Electronic electronic = new Electronic();
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
	
	//导入研究报告
	public void importConsultation() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String midImportedDate = sdf.format(new Date());
		count = 0;
		//研究报告
		List<Object[]> objcons = dao.query("select sc, spm.projectGranted.id from SinossConsultation sc left join sc.projectMidinspection spm where spm.isAdded = 1");
		for (Object[] objconsultation: objcons) {
			if (count == 12623) {
				System.out.println("error");
			}
			if((String) objconsultation[1] == null){
				continue;
			}
			SinossConsultation sConsultation = (SinossConsultation)objconsultation[0];
			ProjectGranted grantedcon = (ProjectGranted) getProjectGranted((String) objconsultation[1]);
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
	
	//初始化审核信息到内存
	@SuppressWarnings("unchecked")
	public void initCheckInfo(){
		Long begin = System.currentTimeMillis();
		List<SinossChecklogs> tempSinossChecklogs = dao.query("select o from SinossChecklogs o where o.isAdded = 1");
		if (sinossChecklogs == null) {
			sinossChecklogs = new HashMap<String, List<SinossChecklogs>>();
		}
		List<SinossChecklogs> tempList;
		for (SinossChecklogs sc : tempSinossChecklogs) {
			tempList = sinossChecklogs.get(sc.getProjectMidinspection().getId());
			if (tempList == null) {
				tempList = new ArrayList<SinossChecklogs>();
			}
			tempList.add(sc);
			sinossChecklogs.put(sc.getProjectMidinspection().getId(), tempList);
		}
		//sinossChecklogs = dao.query("select o from SinossChecklogs o where o.type=2");
		System.out.println("InitCheckInfo Complete! Used time:" + (System.currentTimeMillis() - begin) + "ms");
	}
	
	//从sinossChecklogs中获取需要的审核信息
	public List<SinossChecklogs> getSinossChecklogs(String id){
		return sinossChecklogs.get(id);
	}	
	
	//审核信息
	public String checkInfo(SinossProjectMidinspection spm,ProjectMidinspection gmi, ProjectGranted pg){
		//部级以下审核不通过(1,3,5)
		int statusNumber = 0;
		//判断是否有审核信息
		boolean flag = false;
		SinossChecklogs[] lastSChecklogs = {null, null};//[0]：校级审核记录；[1]:主管部门(省级)审核记录
		//分别获取校级和省级最终审核记录
		List<SinossChecklogs> spmChecklogs = getSinossChecklogs(spm.getId());
		if(null != spmChecklogs){
			for(SinossChecklogs sicheck : spmChecklogs){
				statusNumber = sicheck.getCheckStatus();
				if (statusNumber == 2 || statusNumber ==3 ){ //校级审核
					lastSChecklogs[0] = (lastSChecklogs[0] != null && sicheck.getCheckDate().before(lastSChecklogs[0].getCheckDate())) ? lastSChecklogs[0]: sicheck;
					
					flag = true;
				}else if (statusNumber == 4 || statusNumber ==5) { //省级审核
					lastSChecklogs[1] = (lastSChecklogs[1] != null && sicheck.getCheckDate().before(lastSChecklogs[1].getCheckDate())) ? lastSChecklogs[1]: sicheck;
					flag = true;
				}
				sicheck.setIsAdded(0);
			}
		}
		/*if(!flag){
			return "";
		}*/
		//
		if(null != lastSChecklogs[0] && null == getAgencyByName(lastSChecklogs[0].getChecker().trim())){
			lastSChecklogs[0].setChecker(pg.getAgencyName());
		}
		if(null != lastSChecklogs[1] && null == getAgencyByName(lastSChecklogs[1].getChecker().trim())){
			lastSChecklogs[1].setChecker(pg.getAgencyName());
		}
		//导入审核信息
		int finalStatus = 0;
		Date checkDate = null;
		String checker = null;
		String auditOpion = null;
		if (spm.getCheckStatus() != null) {
			switch (Integer.parseInt(spm.getCheckStatus())) {
				case 0: finalStatus = 0;
						gmi.setApplicantSubmitStatus(0);
						break;
				case 1: finalStatus = 0;
						gmi.setApplicantSubmitStatus(1);
						break;
				case 2: finalStatus = 5;//校级通过，直接推送到部级
						gmi.setStatus(5);
						gmi.setApplicantSubmitStatus(3);
						gmi.setUniversityAuditResult(2);
						gmi.setUniversityAuditStatus(3);
						
						if(null != lastSChecklogs[0]){
							//处理高校变更情况
							if(null == getAgencyByName(lastSChecklogs[0].getChecker().trim())){
								lastSChecklogs[0].setChecker(pg.getAgencyName());
							}
							checkDate = lastSChecklogs[0].getCheckDate();
							checker = lastSChecklogs[0].getChecker().trim();
							auditOpion = lastSChecklogs[0].getCheckInfo();
						}else{
							checkDate = spm.getCheckDate();
							checker = spm.getChecker().trim();
							auditOpion = spm.getCheckInfo();
						}
						
						gmi.setUniversityAuditDate(checkDate);
						gmi.setUniversityAuditorName(checker);
						gmi.setUniversityAuditorAgency(getAgencyByName(checker));
						gmi.setUniversityAuditOpinion(auditOpion);
						
						break;
				case 3: finalStatus = 3;
						gmi.setStatus(3);
						gmi.setApplicantSubmitStatus(3);
						gmi.setUniversityAuditResult(1);
						gmi.setUniversityAuditStatus(3);
						
						gmi.setFinalAuditResult(1);
						gmi.setFinalAuditStatus(3);
						
						if(null != lastSChecklogs[0]){
							checkDate = lastSChecklogs[0].getCheckDate();
							checker = lastSChecklogs[0].getChecker().trim();
							auditOpion = lastSChecklogs[0].getCheckInfo();
						}else{
							checkDate = spm.getCheckDate();
							checker = spm.getChecker().trim();
							auditOpion = spm.getCheckInfo();
						}
						
						gmi.setUniversityAuditDate(checkDate);
						gmi.setUniversityAuditorName(checker);
						gmi.setUniversityAuditorAgency(getAgencyByName(checker));
						gmi.setUniversityAuditOpinion(auditOpion);
						
						gmi.setFinalAuditDate(checkDate);
						gmi.setFinalAuditorName(checker);
						gmi.setFinalAuditorAgency(getAgencyByName(checker));
						gmi.setFinalAuditOpinion(auditOpion);
						
						break;
				case 4: finalStatus = 5;
						gmi.setStatus(5);
						gmi.setUniversityAuditResult(2);//[0：未审核；1：不同意；2：同意]
						gmi.setUniversityAuditStatus(3);//[0：默认；1：退回；2：暂存；3：提交]
						gmi.setProvinceAuditResult(2);
						gmi.setProvinceAuditStatus(3);
						
						if(null != lastSChecklogs[0]){
							gmi.setUniversityAuditDate(lastSChecklogs[0].getCheckDate());
							gmi.setUniversityAuditorName(lastSChecklogs[0].getChecker().trim());
							gmi.setUniversityAuditorAgency(getAgencyByName(lastSChecklogs[0].getChecker().trim()));
							gmi.setUniversityAuditOpinion(lastSChecklogs[0].getCheckInfo());
						}
						
						if(null != lastSChecklogs[1]){
							checkDate = lastSChecklogs[1].getCheckDate();
							checker = lastSChecklogs[1].getChecker().trim();
							auditOpion = lastSChecklogs[1].getCheckInfo();
						}else{
							checkDate = spm.getCheckDate();
							checker = spm.getChecker().trim();
							auditOpion = spm.getCheckInfo();
						}
						gmi.setProvinceAuditDate(checkDate);
						gmi.setProvinceAuditorName(checker);
						gmi.setProvinceAuditorAgency(getAgencyByName(checker));
						gmi.setProvinceAuditOpinion(auditOpion);
						
						break;
				case 5: finalStatus = 5;
						gmi.setStatus(5);
						gmi.setUniversityAuditResult(2);//[0：未审核；1：不同意；2：同意]
						gmi.setUniversityAuditStatus(3);//[0：默认；1：退回；2：暂存；3：提交]
						gmi.setProvinceAuditResult(1);
						gmi.setProvinceAuditStatus(3);
						gmi.setFinalAuditResult(1);
						gmi.setFinalAuditStatus(3);
						
						if(null != lastSChecklogs[0]){
							gmi.setUniversityAuditDate(lastSChecklogs[0].getCheckDate());
							gmi.setUniversityAuditorName(lastSChecklogs[0].getChecker().trim());
							gmi.setUniversityAuditorAgency(getAgencyByName(lastSChecklogs[0].getChecker().trim()));
							gmi.setUniversityAuditOpinion(lastSChecklogs[0].getCheckInfo());
						}
						
						if(null != lastSChecklogs[1]){
							checkDate = lastSChecklogs[1].getCheckDate();
							checker = lastSChecklogs[1].getChecker().trim();
							auditOpion = lastSChecklogs[1].getCheckInfo();
						}else{
							checkDate = spm.getCheckDate();
							checker = spm.getChecker().trim();
							auditOpion = spm.getCheckInfo();
						}
						gmi.setProvinceAuditDate(checkDate);
						gmi.setProvinceAuditorName(checker);
						gmi.setProvinceAuditorAgency(getAgencyByName(checker));
						gmi.setProvinceAuditOpinion(auditOpion);
						
						gmi.setFinalAuditDate(spm.getCheckDate());
						gmi.setFinalAuditorName(spm.getChecker().trim());
						gmi.setFinalAuditorAgency(getAgencyByName(spm.getChecker().trim()));
						gmi.setFinalAuditOpinion(spm.getCheckInfo());
						
						break;
				case 6: finalStatus = 5;
						gmi.setStatus(5);
						gmi.setApplicantSubmitStatus(3);
						break;
				case 7: finalStatus = 5;
						gmi.setStatus(5);
						gmi.setApplicantSubmitStatus(3);
						break;
				case 8: finalStatus = 3; 
						gmi.setStatus(3);
						gmi.setApplicantSubmitStatus(2);
						gmi.setDeptInstAuditResult(2);//院系审核都通过
						gmi.setDeptInstAuditStatus(3);
						break;
				case 9: finalStatus = 3; 
						gmi.setStatus(3);
						gmi.setApplicantSubmitStatus(3);
						gmi.setDeptInstAuditResult(2);//院系审核都通过
						gmi.setDeptInstAuditStatus(3);
						break;
				default:finalStatus = 3; 
						gmi.setStatus(3);
			}
			if(finalStatus > 1 && finalStatus < 6){
				gmi.setDeptInstAuditResult(2);
				gmi.setDeptInstAuditStatus(3);
				gmi.setDeptInstAuditDate(spm.getApplyDate());
			}
		}
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
		List<ProjectGranted> tempProjectGranteds = dao.query("select p from ProjectGranted p");
		pNumberMap = new HashMap<String, String>();
		pNameMap = new HashMap<String, String>();
		if (pGranted  == null) {
			pGranted = new HashMap<String, ProjectGranted>();
		}
		for (ProjectGranted pg : tempProjectGranteds) {
			pGranted.put(pg.getId(), pg);
			
			String number = pg.getNumber();
			String name = pg.getName();
			String grantedId = pg.getId();
			String key = number + name;
			
			pNumberMap.put(number, grantedId);
			pNameMap.put(key, grantedId);
		}
		System.out.println("InitGranded Complete! Used time:" + (System.currentTimeMillis() - begin) + "ms");
	}
	
	//通过ID从内存中获取立项数据
	public ProjectGranted getProjectGranted(String id){
		return pGranted.get(id);
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
		//处理特殊情况
		if(words.equals("一左右万字")){
			return (double)10.000;
		}
		if(words.equals("作品") || words.contains("幅")){
			return (double)0.000;
		}
		words = StringTool.toDBC(words).replaceAll("[。·]+", ".");
		words = words.replaceAll("[,，、]+", "");
		words = words.replaceAll("（", "(").replaceAll("）", ")").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("约", "").replaceAll("大约", "").replaceAll("左右", "");
		if(words.contains("万")){
			//words = words.replaceAll("[\u4e00-\u9fa5]+", "");
			words = words.replaceAll("[^0-9\\.]", "");
			wordsNum = Double.parseDouble(words) * 10;
		}else if(words.contains("千")){
			//words = words.replaceAll("[\u4e00-\u9fa5]+", "");
			words = words.replaceAll("[^0-9\\.]", "");
			wordsNum = Double.parseDouble(words);
		}else if(words.endsWith("字")){
			//words = words.replaceAll("[\u4e00-\u9fa5]+", "");
			words = words.replaceAll("[^0-9\\.]", "");
			wordsNum = Double.parseDouble(words) /1000;
		} else {
			//words = words.replaceAll("[\u4e00-\u9fa5]+", "");
			/*if(words.endsWith("000")){
				wordsNum = Double.parseDouble(words) /1000;
			}else{
				wordsNum = Double.parseDouble(words);
			}*/
			words = words.replaceAll("[^0-9\\.]", "");
			if(words.contains(".")){
				wordsNum = Double.parseDouble(words);
			}
			else{
				if(words.length() > 3){
					wordsNum = Double.parseDouble(words) /1000;
				}
				else{
					wordsNum = Double.parseDouble(words);
				}
			}
		}
		int num = (int) (wordsNum * 1000);
		wordsNum = (double) (num) / 1000;
		return wordsNum;
	}
	
	public static void main(String[] args){
		String str = "啦啦11，480.dsaw";
		System.out.println(str);
		System.out.println(str.replaceAll("[^0-9\\.]", ""));
		
	}
	
	/**
	 * 在数据自动入库过程中，根据以前的警告信息，以及当前遇到的警告情况，更新警告信息
	 * @param originExchangeNoteInfo
	 * @param exceptionName
	 * @param projectName
	 * @param errorInfo
	 * @return
	 */
	public String exchangeNoteInfo(String originExchangeNoteInfo, String exceptionName ,String projectName , String errorInfo){
		if (originExchangeNoteInfo == null) {
			return exceptionName + "_" + projectName + "_" + errorInfo;
		}else {
			return originExchangeNoteInfo + "; " + exceptionName + "_" + projectName + "_" + errorInfo;
		}
	}
	
	/**
	 * 必须释放初始化时占据的内存，否则审核信息将无法正常初始化
	 * @author 2014-8-30 
	 */
	public void freeMemory(){
		sinossChecklogs = null;
		pGranted = null;
		sinossPaperMap = null;
		sinossBookMap = null;
		sinossPatentMap = null;
		sinossElectronicMap = null;
		sinossConsultationMap = null;
		pNumberMap = null;
		pNameMap = null;
		agencyMap = null;
	}
	
	//初始化中检信息到内存
	public void initMid(){
		Long begin = System.currentTimeMillis();
		pMidins = null;
		List<ProjectMidinspection> objpMids = dao.query("select pm from ProjectMidinspection pm where to_char(pm.createDate,'yyyy')>'2013'");
		if (pMidins == null) {
			pMidins = new HashMap<String, List<ProjectMidinspection>>();
		}
		List<ProjectMidinspection> temppMid;
		for (ProjectMidinspection object : objpMids) {
			if(object == null){
				continue;
			}
			String grantedId = object.getGrantedId();
			temppMid = pMidins.get(grantedId);
			if (temppMid == null) {
				temppMid = new ArrayList<ProjectMidinspection>();
			}
			temppMid.add(object);
			pMidins.put(grantedId, temppMid);
		}
		System.out.println("InitPaperInfo Complete! Used time:" + (System.currentTimeMillis() - begin) + "ms");
	}
	
	public int getTotalNum() {
		return totalNum;
	}
	
	public int getCurrentNum() {
		return currentNum;
	}
	
	public Map getIllegalException() {
		return illegalException;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getIsFinished() {
		return isFinished;
	}
	
	public int getTotalImportNum() {
		return totalImportNum;
	}
}
