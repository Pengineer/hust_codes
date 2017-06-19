package csdc.tool.execution.importer;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.NssfProjectApplication;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * Excel:《2014年国家社科基金申请数据（在京部属高校）.xls》入库
 * @author wangyi
 * @status 
 */
public class NssfApplicationBJ2014Importer extends Importer {
	/**
	 * 《2014年国家社科基金申请数据（在京部属高校）.xls》
	 */
	private ExcelReader excelReader;
	
	@Autowired
	private Tool tool;
	
	public NssfApplicationBJ2014Importer() {}
	
	public NssfApplicationBJ2014Importer(String filePath) {
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
		
		int i = 0;
		while (next(excelReader)) {
			System.out.println(excelReader.getCurrentRowIndex() + " / " + excelReader.getRowNumber());
			NssfProjectApplication nApplication = (NssfProjectApplication) dao.queryUnique("select na from NssfProjectApplication na where na.name = ? and na.applicant = ?", C, J);
			if (nApplication == null) {
				System.out.println("找不到的项目：" + C);
			} else {
				nApplication.setDjh(A);
				nApplication.setSjsbbm(B);
				nApplication.setZtc(D);
				nApplication.setKtlzty(E);
				nApplication.setXmlb(F);
				nApplication.setXkfl(G);
				nApplication.setYjlx(H);
				nApplication.setSfzh(I);
				nApplication.setXzzw(N);
				nApplication.setZyzw(O);
				nApplication.setYjzc(P);
				nApplication.setXl(Q);
				nApplication.setXw(R);
				nApplication.setDrds(S);
				nApplication.setSsx(T);
				nApplication.setSsxt(V);
				nApplication.setTxdz(X);
				nApplication.setYb(Y);
				nApplication.setJtdh(Z);
				nApplication.setBgdh(AA);
				nApplication.setYqcg(AB);
				nApplication.setJhwcsj(tool.getDate(AC));
				nApplication.setSqjf(AD);
				nApplication.setJyzze(AE);
				nApplication.setPzcze(AF);
				nApplication.setDwyj(AG);
				nApplication.setDfskghbyj(AH);
				nApplication.setQgbyj(AI);
				nApplication.setTxpf(AJ);
				nApplication.setCsyj(AK);
				nApplication.setPsjg(AL);
				nApplication.setWtgyy(AM);
				nApplication.setPzzze(AN);
				nApplication.setYjbs(AO);
				nApplication.setPsbs(AP);
				nApplication.setZishu(AQ);
				dao.addOrModify(nApplication);
				i++;
			}
		}
		System.out.println(i);
	}
	
}
