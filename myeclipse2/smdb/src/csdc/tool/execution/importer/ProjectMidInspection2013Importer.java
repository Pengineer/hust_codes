package csdc.tool.execution.importer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.GeneralFunding;
import csdc.bean.GeneralGranted;
import csdc.bean.GeneralMidinspection;
import csdc.bean.GeneralVariation;
import csdc.bean.InstpGranted;
import csdc.bean.Officer;
import csdc.bean.SinossBook;
import csdc.bean.SinossChecklogs;
import csdc.bean.SinossConsultation;
import csdc.bean.SinossElectronic;
import csdc.bean.SinossPaper;
import csdc.bean.SinossPatent;
import csdc.bean.SinossProjectMidinspection;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 《2013年一般项目中检申报信息（多条记录审核版）.xls》、《2013年基地项目中检申报信息（多条记录审核版）.xls》
 * @author 
 * @status 
 * 备注：入库与社科网对接的中间表。
 */
public class ProjectMidInspection2013Importer extends Importer {
	
	/**
	 * 《2013年一般项目中检信息.xls》
	 */
	private ExcelReader excelReader1;
	
	/**
	 * 《2013年基地项目中检信息.xls》
	 */
	private ExcelReader excelReader2;
	
	@Autowired
	private Tool tool;

	@Autowired
	private GeneralProjectFinder generalProjectFinder;
	
	@Autowired
	private InstpProjectFinder instpProjectFinder;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	
	public ProjectMidInspection2013Importer() {}
	
	public ProjectMidInspection2013Importer(String file1, String file2) {
		excelReader1 = new ExcelReader(file1);
		excelReader2 = new ExcelReader(file2);
	}
	

	@Override
	public void work() throws Exception {
//		checkProjectExistence();
		importData();
	}

	/**
	 * 正式导入数据
	 */
	private void importData() throws Exception {
		/**
		 * 一般项目
		 */
		excelReader1.readSheet(0);//13年一般项目中检信息
		while (next(excelReader1)) {
			if(A.length() == 0) {
				break;
			}
			System.out.println(excelReader1.getCurrentRowIndex());
			SinossProjectMidinspection spMidinspection = new SinossProjectMidinspection();
			spMidinspection.setProjectType("general");
			spMidinspection.setProjectId(A);
			spMidinspection.setCode(B);
			spMidinspection.setName(C);
			spMidinspection.setAgencyName(D);
			spMidinspection.setAgencyCode(E);
			spMidinspection.setPersonName(F);
			spMidinspection.setYear(G);
			spMidinspection.setStatus(H);
			spMidinspection.setCheckStatus(I);
			spMidinspection.setDeferReason(J);
			if (K.length() > 0) {
				spMidinspection.setDeferDate(tool.getDate(K));
			}
			spMidinspection.setMidReportName(L);
			if (M.length() > 0) {
				spMidinspection.setApplyDate(sdf.parse(M));
			}
			spMidinspection.setReadDate(new Date());
			spMidinspection.setReadPerson("wangyi");
			spMidinspection.setFrom("sinoss");
			GeneralGranted granted = generalProjectFinder.findGranted(B);
			spMidinspection.setSmdbProjectId(granted.getId());
			dao.add(spMidinspection);
		}
		
		/*excelReader1.readSheet(1);//审核记录
		while (next(excelReader1)) {
			if(A.length() == 0) {
				break;
			}
			SinossChecklogs midSC = new SinossChecklogs();
			midSC.setType(2);
			midSC.setProjectId(A);
			midSC.setMidId(B);
			if (C.contains("社科司")) {
				midSC.setCheckStatus(7);
			} else if (C.contains("主管部门审核不通过")) {
				midSC.setCheckStatus(5);
			} else if (C.contains("主管部门审核通过")) {
				midSC.setCheckStatus(4);
			} else if (C.contains("学校审核不通过")) {
				midSC.setCheckStatus(3);
			} else if (C.contains("学校审核通过")) {
				midSC.setCheckStatus(2);
			}
			midSC.setCheckDate(sdf.parse(D));
			midSC.setChecker(E);
			midSC.setCheckInfo(F);
			SinossProjectMidinspection spMidinspection = (SinossProjectMidinspection) dao.queryUnique("from SinossProjectMidinspection spm where spm.projectId =?", A);
			midSC.setProjectMidinspection(spMidinspection);
			dao.add(midSC);
		}*/
		
		/*excelReader1.readSheet(2);//论文成果信息
		while (next(excelReader1)) {
			System.out.println(excelReader1.getCurrentRowIndex());
			if(A.length() == 0) {
				break;
			}
			SinossPaper sPaper = new SinossPaper();
			sPaper.setProjectId(A);
			sPaper.setProjectName(B);
			sPaper.setName(C);
			sPaper.setFirstAuthor(D);
			sPaper.setOtherAuthor(E);
			sPaper.setSubject(F);
			sPaper.setSubjectType(G);
			sPaper.setIsMark(H);
			sPaper.setPaperType(I);
			sPaper.setPaperBook(J);
			sPaper.setPaperBookType(K);
			sPaper.setPublishScope(L);
			sPaper.setJuanhao(M);
			sPaper.setQihao(N);
			sPaper.setPagenumScope(O);
			sPaper.setWordNumber(P);
			sPaper.setIsTranslate(Q);
			sPaper.setIssn(R);
			sPaper.setCn(S);
			if (T.length() > 0) {
				sPaper.setPublicDate(tool.getDate(T));
			}
			sPaper.setNote(U);
			SinossProjectMidinspection spMidinspection = (SinossProjectMidinspection) dao.queryUnique("from SinossProjectMidinspection spm where spm.projectId =?", A);
			sPaper.setProjectMidinspection(spMidinspection);
			dao.add(sPaper);
		}*/
		
		/*excelReader1.readSheet(3);//著作成果信息
		while (next(excelReader1)) {
			if(A.length() == 0) {
				break;
			}
			SinossBook sBook = new SinossBook();
			sBook.setProjectId(A);
			sBook.setProjectName(B);
			sBook.setName(C);
			sBook.setFirstAuthor(D);
			sBook.setOtherAuthor(E);
			sBook.setSubject(F);
			sBook.setDisciplineType(G);
			sBook.setIsMark(H);
			sBook.setPress(I);
			sBook.setPressTime(tool.getDate(J));
			sBook.setPressAddress(K);
			sBook.setBookType(L);
			sBook.setWordNumber(M);
			sBook.setIsEnglish(N);
			sBook.setIsbn(O);
			sBook.setCip(P);
			sBook.setNote(Q);
			SinossProjectMidinspection spMidinspection = (SinossProjectMidinspection) dao.queryUnique("from SinossProjectMidinspection spm where spm.projectId =?", A);
			sBook.setProjectMidinspection(spMidinspection);
			dao.add(sBook);
		}*/
		
		/*excelReader1.readSheet(4);//专利成果信息
		while (next(excelReader1)) {
			if(A.length() == 0) {
				break;
			}
			SinossPatent sPatent = new SinossPatent();
			sPatent.setProjectId(A);
			sPatent.setProjectName(B);
			sPatent.setName(C);
			sPatent.setFirstAuthor(D);
			sPatent.setOtherName(E);
			sPatent.setIsMark(H);
			sPatent.setPatentType(I);
			sPatent.setPatentScope(J);
			sPatent.setPatentNumber(K);
			sPatent.setAuthorizeDate(tool.getDate(L));
			sPatent.setPatentPerson(M);
			sPatent.setNote(N);
			SinossProjectMidinspection spMidinspection = (SinossProjectMidinspection) dao.queryUnique("from SinossProjectMidinspection spm where spm.projectId =?", A);
			sPatent.setProjectMidinspection(spMidinspection);
			dao.add(sPatent);
		}
		
		excelReader1.readSheet(5);//研究报告成果信息
		while (next(excelReader1)) {
			if(A.length() == 0) {
				break;
			}
			SinossConsultation sConsultation = new SinossConsultation();
			sConsultation.setProjectId(A);
			sConsultation.setProjectName(B);
			sConsultation.setName(C);
			sConsultation.setFirstAuthor(D);
			sConsultation.setOtherAuthor(E);
			sConsultation.setIsMark(H);
			sConsultation.setCommitUnit(I);
			sConsultation.setCommitDate(tool.getDate(J));
			sConsultation.setIsAccept(K);
			sConsultation.setAcceptObj(L);
			sConsultation.setNote(M);
			SinossProjectMidinspection spMidinspection = (SinossProjectMidinspection) dao.queryUnique("from SinossProjectMidinspection spm where spm.projectId =?", A);
			sConsultation.setProjectMidinspection(spMidinspection);
			dao.add(sConsultation);
		}
		
		excelReader1.readSheet(6);//电子出版物成果信息
		while (next(excelReader1)) {
			if(A.length() == 0) {
				break;
			}
			SinossElectronic sElectronic = new SinossElectronic();
			sElectronic.setProjectId(A);
			sElectronic.setProjectName(B);
			sElectronic.setName(C);
			sElectronic.setFirstAuthor(D);
			sElectronic.setOtherAuthor(E);
			sElectronic.setSubject(F);
			sElectronic.setDisciplineType(G);
			sElectronic.setIsMark(H);
			sElectronic.setPress(I);
			sElectronic.setPressDate(tool.getDate(J));
			sElectronic.setPressAddress(K);
			sElectronic.setIsbn(L);
			sElectronic.setNote(M);
			SinossProjectMidinspection spMidinspection = (SinossProjectMidinspection) dao.queryUnique("from SinossProjectMidinspection spm where spm.projectId =?", A);
			sElectronic.setProjectMidinspection(spMidinspection);
			dao.add(sElectronic);
		}*/
		
		/**
		 * 基地项目
		 */
		excelReader2.readSheet(0);//13年基地项目中检信息
		while (next(excelReader2)) {
			if(A.length() == 0) {
				break;
			}
			SinossProjectMidinspection spMidinspection = new SinossProjectMidinspection();
			spMidinspection.setProjectType("instp");
			spMidinspection.setProjectId(A);
			spMidinspection.setCode(B);
			spMidinspection.setName(C);
			spMidinspection.setAgencyName(D);
			spMidinspection.setAgencyCode(E);
			spMidinspection.setPersonName(F);
			spMidinspection.setYear(G);
			spMidinspection.setStatus(H);
			spMidinspection.setCheckStatus(I);
			spMidinspection.setDeferReason(J);
			if (K.length() > 0) {
				spMidinspection.setDeferDate(tool.getDate(K));
			}
			spMidinspection.setMidReportName(L);
			if (M.length() > 0) {
				spMidinspection.setApplyDate(sdf.parse(M));
			}
			spMidinspection.setReadDate(new Date());
			spMidinspection.setReadPerson("wangyi");
			spMidinspection.setFrom("sinoss");
			InstpGranted granted = instpProjectFinder.findGranted(B);
			spMidinspection.setSmdbProjectId(granted.getId());
			dao.add(spMidinspection);
		}
		
		/*excelReader2.readSheet(1);//审核记录
		while (next(excelReader2)) {
			if(A.length() == 0) {
				break;
			}
			SinossChecklogs midSC = new SinossChecklogs();
			midSC.setType(2);
			midSC.setProjectId(A);
			midSC.setMidId(B);
			if (C.contains("主管部门审核不通过")) {
				midSC.setCheckStatus(5);
			} else if (C.contains("主管部门审核通过")) {
				midSC.setCheckStatus(4);
			} else if (C.contains("学校审核不通过")) {
				midSC.setCheckStatus(3);
			} else if (C.contains("学校审核通过")) {
				midSC.setCheckStatus(2);
			}
			midSC.setCheckDate(sdf.parse(D));
			midSC.setChecker(E);
			midSC.setCheckInfo(F);
			SinossProjectMidinspection spMidinspection = (SinossProjectMidinspection) dao.queryUnique("from SinossProjectMidinspection spm where spm.projectId =?", A);
			midSC.setProjectMidinspection(spMidinspection);
			dao.add(midSC);
		}*/
		
		/*excelReader2.readSheet(2);//论文成果信息
		while (next(excelReader2)) {
			if(A.length() == 0) {
				break;
			}
			SinossPaper sPaper = new SinossPaper();
			sPaper.setProjectId(A);
			sPaper.setProjectName(B);
			sPaper.setName(C);
			sPaper.setFirstAuthor(D);
			sPaper.setOtherAuthor(E);
			sPaper.setSubject(F);
			sPaper.setSubjectType(G);
			sPaper.setIsMark(H);
			sPaper.setPaperType(I);
			sPaper.setPaperBook(J);
			sPaper.setPaperBookType(K);
			sPaper.setPublishScope(L);
			sPaper.setJuanhao(M);
			sPaper.setQihao(N);
			sPaper.setPagenumScope(O);
			sPaper.setWordNumber(P);
			sPaper.setIsTranslate(Q);
			sPaper.setIssn(R);
			sPaper.setCn(S);
			sPaper.setPublicDate(tool.getDate(T));
			sPaper.setNote(U);
			SinossProjectMidinspection spMidinspection = (SinossProjectMidinspection) dao.queryUnique("from SinossProjectMidinspection spm where spm.projectId =?", A);
			sPaper.setProjectMidinspection(spMidinspection);
			dao.add(sPaper);
		}
		
		excelReader2.readSheet(3);//著作成果信息
		while (next(excelReader2)) {
			if(A.length() == 0) {
				break;
			}
			SinossBook sBook = new SinossBook();
			sBook.setProjectId(A);
			sBook.setProjectName(B);
			sBook.setName(C);
			sBook.setFirstAuthor(D);
			sBook.setOtherAuthor(E);
			sBook.setSubject(F);
			sBook.setDisciplineType(G);
			sBook.setIsMark(H);
			sBook.setPress(I);
			sBook.setPressTime(tool.getDate(J));
			sBook.setPressAddress(K);
			sBook.setBookType(L);
			sBook.setWordNumber(M);
			sBook.setIsEnglish(N);
			sBook.setIsbn(O);
			sBook.setCip(P);
			sBook.setNote(Q);
			SinossProjectMidinspection spMidinspection = (SinossProjectMidinspection) dao.queryUnique("from SinossProjectMidinspection spm where spm.projectId =?", A);
			sBook.setProjectMidinspection(spMidinspection);
			dao.add(sBook);
		}
		
		excelReader2.readSheet(4);//专利成果信息
		while (next(excelReader2)) {
			
			if(A.length() == 0) {
				break;
			}
		}
		
		excelReader2.readSheet(5);//研究报告成果信息
		while (next(excelReader2)) {
			if(A.length() == 0) {
				break;
			}
			SinossConsultation sConsultation = new SinossConsultation();
			sConsultation.setProjectId(A);
			sConsultation.setProjectName(B);
			sConsultation.setName(C);
			sConsultation.setFirstAuthor(D);
			sConsultation.setOtherAuthor(E);
			sConsultation.setIsMark(H);
			sConsultation.setCommitUnit(I);
			sConsultation.setCommitDate(tool.getDate(J));
			sConsultation.setIsAccept(K);
			sConsultation.setAcceptObj(L);
			sConsultation.setNote(M);
			SinossProjectMidinspection spMidinspection = (SinossProjectMidinspection) dao.queryUnique("from SinossProjectMidinspection spm where spm.projectId =?", A);
			sConsultation.setProjectMidinspection(spMidinspection);
			dao.add(sConsultation);
		}
		
		excelReader2.readSheet(6);//电子出版物成果信息
		while (next(excelReader2)) {
			if(A.length() == 0) {
				break;
			}
			SinossElectronic sElectronic = new SinossElectronic();
			sElectronic.setProjectId(A);
			sElectronic.setProjectName(B);
			sElectronic.setName(C);
			sElectronic.setFirstAuthor(D);
			sElectronic.setOtherAuthor(E);
			sElectronic.setSubject(F);
			sElectronic.setDisciplineType(G);
			sElectronic.setIsMark(H);
			sElectronic.setPress(I);
			sElectronic.setPressDate(tool.getDate(J));
			sElectronic.setPressAddress(K);
			sElectronic.setIsbn(L);
			sElectronic.setNote(M);
			SinossProjectMidinspection spMidinspection = (SinossProjectMidinspection) dao.queryUnique("from SinossProjectMidinspection spm where spm.projectId =?", A);
			sElectronic.setProjectMidinspection(spMidinspection);
			dao.add(sElectronic);
		}*/
		
	}


	/**
	 * 检查中检数据是否库内存在
	 * @throws Exception  
	 */
	private void checkProjectExistence() throws Exception {
		HashSet exMsg = new HashSet();
		/*excelReader1.readSheet(0);
		int i = 0;
		int j = 0;
		while (next(excelReader1)) {
			GeneralGranted granted = generalProjectFinder.findGranted(B);
			if (granted == null) {
				System.out.println("找不到的项目：" + B + " - " + C);
				i++;
			} else {
				if (!granted.getName().equals(C)) {
					System.out.println("项目名称错误的项目：" + B + " - " + C);
					exMsg.add("项目名称错误的项目：" + B + " - " + C);
					j++;
				}
			}
			
			if(A.length() == 0) {
				break;
			}
		}
		System.out.println(i);
		System.out.println(j);*/
		excelReader2.readSheet(0);
		int i = 0;
		int j = 0;
		while (next(excelReader2)) {
			InstpGranted granted = instpProjectFinder.findGranted(B);
			if (granted == null) {
				System.out.println("找不到的项目：" + B + " - " + C);
				exMsg.add("项目名称错误的项目：" + B + " - " + C);
				i++;
			} else {
				if (!granted.getName().equals(C)) {
					System.out.println("项目名称错误的项目：" + B + " - " + C);
					exMsg.add("项目名称错误的项目：" + B + " - " + C);
					j++;
				}
			}
			
			if(A.length() == 0) {
				break;
			}
		}
		System.out.println(i);
		System.out.println(j);
		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}


}
