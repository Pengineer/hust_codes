package csdc.tool.execution.importer;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import csdc.tool.ApplicationContainer;
import csdc.tool.reader.ExcelReader;

/**
 * 读取《成果形式规范表.xls》放进productTrans
 * @author xuhan
 *
 */
public class ProductTypeNormalizingImporter extends Importer {

	private ExcelReader excelReader;

	/**
	 * 成果形式转换
	 */
	public Map<String, String> productTrans;
	
	public ProductTypeNormalizingImporter() {}
	
	@Override
	public void work() throws Exception {
		if (excelReader == null) {
//			String realPath = ServletActionContext.getServletContext().getRealPath("file");
			String realPath = ApplicationContainer.sc.getRealPath("file");
			realPath = realPath.replace('\\', '/');
			String destFolder = "/template/import/成果形式规范表.xls";
			String resultPath = realPath + destFolder;
			excelReader = new ExcelReader(resultPath);
		}
		excelReader.readSheet(0);
		while (next(excelReader)) {
			if (productTrans == null) {
				productTrans = new HashMap<String, String>();
			}
			productTrans.put(A, B);
		}
		excelReader = null;
	}
	

	public Map<String, String> getProductTrans() {
		return productTrans;
	}
}
