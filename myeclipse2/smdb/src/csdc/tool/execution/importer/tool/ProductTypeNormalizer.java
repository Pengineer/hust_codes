package csdc.tool.execution.importer.tool;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.tool.execution.importer.ProductTypeNormalizingImporter;

/**
 * 成果形式规范器
 * @author xuhan
 *
 */
@Component
public class ProductTypeNormalizer {

	/**
	 * 成果类型转换<br>
	 * [不规范的成果类型名] -> [规范的成果类型名]
	 */
	private Map<String, String> productTrans;

	@Autowired
	private ProductTypeNormalizingImporter productTypeNormalizingImporter;
	
	/**
	 * 规范一个项目的成果形式
	 * @param productType
	 * @return 0:编内成果形式	1:其他成果形式	(均用"; "隔开)
	 * @throws Exception 
	 */
	public String[] getNormalizedProductType(String productType) throws Exception {
		if (productTrans == null) {
			productTypeNormalizingImporter.excute();
			productTrans = productTypeNormalizingImporter.getProductTrans();
		}

		if (productType == null) {
			return new String[]{null, null};
		}
		productType = productType.replaceAll("论\\s+文", "论文");
		productType = productType.replaceAll("著\\s+作", "著作");
		productType = productType.replaceAll("专\\s+著", "专著");
		productType = productType.replaceAll("报\\s+告", "报告");
		String []tmp = productType.split("([^A-Za-z\\u4e00-\\u9fa5]|[和或与以及])+");
		
		Set<String> pts = new TreeSet<String>();
		for (String pt : tmp) {
			boolean findSome = false;
			if (pt.contains("论文")) {
				pts.add("论文");
				findSome = true;
			}
			if (pt.contains("著")) {
				pts.add("著作");
				findSome = true;
			}
			if (pt.contains("报告") || pt.contains("咨询")) {
				pts.add("研究咨询报告");
				findSome = true;
			}
			if (pt.contains("软件") || pt.contains("系统") || pt.contains("网站") || pt.contains("网络") || pt.contains("数据库") || pt.contains("光盘") || pt.contains("电子") || pt.contains("课件") || pt.contains("媒体") || pt.contains("音像") || pt.contains("平台")) {
				pts.add("电子出版物");
				findSome = true;
			}
			if (!findSome && pt.length() > 1) {
				if (!productTrans.containsKey(pt)) {
					pts.add(pt);
				} else {
					String rcn[] = productTrans.get(pt).split("[^A-Za-z\\u4e00-\\u9fa5]+");
					for (String string : rcn) {
						if (string.equals("其他")) {
							pts.add(pt);
						} else if (string.length() > 0) {
							pts.add(string);
						}
					}
				}
			}
		}
		
		String res0 = "", res1 = "";
		for (Iterator iterator = pts.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			if (string.equals("研究咨询报告") || string.equals("论文") || string.equals("著作") || string.equals("电子出版物") || string.equals("专利")) {
				if (res0.length() > 0) {
					res0 += "; ";
				}
				res0 += string;
			} else {
				if (res1.length() > 0) {
					res1 += "; ";
				}
				res1 += string;
			}
		}
		return new String[]{res0, res1};
	}
 

}
