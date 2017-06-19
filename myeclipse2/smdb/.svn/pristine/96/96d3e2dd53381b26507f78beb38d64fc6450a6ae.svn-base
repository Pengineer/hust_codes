package csdc.tool.execution.importer;

import java.util.HashMap;
import java.util.Map;

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
	
	public ProductTypeNormalizingImporter(String filePath) {
		excelReader = new ExcelReader(filePath);
		productTrans = new HashMap<String, String>();
	}
	
	@Override
	public void work() throws Exception {
		excelReader.readSheet(0);
		while (next(excelReader)) {
			productTrans.put(A, B);
		}
	}
	

	public Map<String, String> getProductTrans() {
		return productTrans;
	}
}
