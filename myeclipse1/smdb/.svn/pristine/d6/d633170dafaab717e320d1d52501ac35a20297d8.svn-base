package csdc.tool.execution.importer;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Nssf;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 《全国教育科学规划立项课题2001-2012_修正导入.xls》
 * @author wangyi
 * @status 
 * 备注：1、教育规划课题年度课题课题类别含”国家“关键字的查重标记C_IS_DUP_CHECK_GENERAL设为1。
 */
public class NssfPost2013Importer extends Importer {
	
	private ExcelReader excelReader;
		
	@Autowired
	private Tool tool;
	
	public NssfPost2013Importer() {}
	
	public NssfPost2013Importer(String filePath) {
		excelReader = new ExcelReader(filePath);
	}
	

	@Override
	public void work() throws Exception {
		importData();
	}

	/**
	 * 导入数据
	 * @throws Exception 
	 */
	private void importData() throws Exception {
		
		excelReader.readSheet(0);		
		
		while (next(excelReader)) {
			
			Nssf nssf = new Nssf();
			nssf.setName(A);
			nssf.setApplicant(B);
			nssf.setNumber(C);
			if (D.length() > 0) {
				nssf.setType(D);
			}
			if (E.length() > 0) {
				nssf.setDisciplineType(E);
			}
			if (F.length() > 0) {
				nssf.setTopic(F);
			}
			nssf.setUnit(G);
			nssf.setStartDate(tool.getDate(H));
			nssf.setSubject(I);
			if (J.length() > 0) {
				nssf.setDescription(J);
			}
			if (K.length() > 0) {
				nssf.setReport(K);
			} else {
				nssf.setReport("无");
			}
			nssf.setSingleSubject("教育学");
			if(D.contains("国家")) {
				nssf.setIsDupCheckGeneral(1);
			}
			nssf.setImportDate(new Date());
			dao.add(nssf);
		}
	}
}

