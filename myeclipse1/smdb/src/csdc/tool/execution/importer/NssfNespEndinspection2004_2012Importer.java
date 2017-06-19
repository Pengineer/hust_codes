package csdc.tool.execution.importer;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Nssf;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * Excel：《2004-2012全国教育规划立项课题鉴定情况一览表_修正导入.xls》
 * @author wangyi
 * @status 
 * 备注：
 * 1、通过批准号找立项数据，共有300条数据找不到对应立项，原因可能是批准号错误。手工通过负责人核查数据后，项目名称一样的批准号以
 * 立项数据为准。剩余无法匹配的结项数据新建记录。
 * 2、经核查立项数据中批准号GFA、ELA后面接7位数字，与其他规则不符，结项数据对应为6位数字，可能有问题，但是为了保持处理规则一致，
 * 先暂以立项数据中批准号为准入库。
 * 3、经核查有部分数据，项目相同，但立项与结项的负责人不一样，先暂以立项为准入库。
 */
public class NssfNespEndinspection2004_2012Importer extends Importer {
	
	private ExcelReader excelReader;
	
	@Autowired
	private Tool tool;
	
	public NssfNespEndinspection2004_2012Importer() {}
	
	public NssfNespEndinspection2004_2012Importer(String filePath) {
		excelReader = new ExcelReader(filePath);
	}
	

	@Override
	public void work() throws Exception {
		importData();
//		checkProjectExistence();
	}

	/**
	 * 正式导入数据
	 * @throws Exception 
	 */
	private void importData() throws Exception {
		
		excelReader.readSheet(0);
		
		while (next(excelReader)) {
			if (C.length() == 0) {
				break;
		    }
			
			Nssf nssf = (Nssf) dao.queryUnique("select nssf from Nssf nssf where nssf.number = ?", C);
			if (nssf == null) {
				Nssf newNssf = new Nssf();
				if (A.length() > 0) {
					newNssf.setDisciplineType(A);
				}
				if (B.length() > 0) {
					newNssf.setType(B);
				}
				if (C.length() > 0) {
					newNssf.setNumber(C);
				}
				if (D.length() > 0) {
					newNssf.setName(D);
				}
				if (E.length() > 0) {
					newNssf.setProductName(E);
				}
				if (F.length() > 0) {
					newNssf.setApplicant(F);
				}
				if (G.length() > 0) {
					newNssf.setUnit(G);
				}
				if (H.length() > 0) {
					newNssf.setCertificate(H);
				}
				if (I.length() > 0) {
					newNssf.setExperts(I);
				}
				if (J.length() > 0) {
					newNssf.setProductLevel(J);
				}
				if (K.length() > 0) {
					newNssf.setEndDate(tool.getDate(K));
				}										
				if (L.length() > 0) {
					newNssf.setNoIdentifyReason(L);
				}
				newNssf.setImportDate(new Date());
				newNssf.setIsDupCheckGeneral(0);
				newNssf.setSingleSubject("教育学");
				dao.add(newNssf);
								
			} else {
				if (E.length() > 0) {
					nssf.setProductName(E);
				}
				if (H.length() > 0) {
					nssf.setCertificate(H);
				}
				if (I.length() > 0) {
					nssf.setExperts(I);
				}
				if (J.length() > 0) {
					nssf.setProductLevel(J);
				}
				if (K.length() > 0) {
					nssf.setEndDate(tool.getDate(K));
				}
				if (L.length() > 0) {
					nssf.setNoIdentifyReason(L);
				}
				nssf.setImportDate(new Date());
				nssf.setIsDupCheckGeneral(0);
				saveOrUpdate(nssf);
				
			}

		}
		
	}
	
	/**
	 * 检查数据是否库内存在
	 * @throws Exception 
	 */
	private void checkProjectExistence() throws Exception {
		excelReader.readSheet(0);
		
		int i = 0;
		int j = 0;
		int k = 0;
		List applicantList = dao.query("select nesp.applicant from Nesp nesp");
		String applicantString = applicantList.toString();
		while (next(excelReader)) {
			if (C.length() == 0) {
				break;
		    }
			
			Nssf nssf = (Nssf) dao.queryUnique("select nssf from Nssf nssf where nssf.number = ?", C);
			if (nssf == null) {
				if (applicantString.contains(F)) {
					System.out.println("存在：" + C + F);
					i++;
				} else {
					System.out.println("找不到的项目：" + C + F);
					j++;
				}								
			} else {
				k++;
			}

		}
		System.out.println(i);
		System.out.println(j);
		System.out.println(k);
	}
}
