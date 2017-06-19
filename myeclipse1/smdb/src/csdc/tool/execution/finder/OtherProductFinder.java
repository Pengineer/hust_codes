package csdc.tool.execution.finder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.OtherProduct;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.StringTool;

/**
 * OtherProduct查找辅助类
 * @author xuhan
 *
 */
@Component
public class OtherProductFinder {
	
	/**
	 * [成果名+作者名+出版日期]到[OtherProductID]的映射
	 */
	private Map<String, String> map;
	
	@Autowired
	private IHibernateBaseDao dao;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	
	/**
	 * 根据[成果名][作者名][出版日期]找到OtherProduct实体
	 * @return
	 */
	public OtherProduct getOtherProduct(String otherProductName, String authorName, Date pressDate, boolean addIfNotFound) {
		if (map == null) {
			initMap();
		}
		OtherProduct otherProduct = null;
		String key = StringTool.fix(otherProductName + authorName + (pressDate != null ? sdf.format(pressDate) : ""));
		String otherProductId = map.get(key);
		if (otherProductId != null) {
			otherProduct = (OtherProduct) dao.query(OtherProduct.class, otherProductId);
		}
		if (otherProduct == null && addIfNotFound) {
			otherProduct = new OtherProduct();
			otherProduct.setChineseName(otherProductName);
			otherProduct.setAuthorName(authorName);
			otherProduct.setPressDate(pressDate);
			map.put(key, dao.add(otherProduct));
		}
		return otherProduct;
	}
	
	/**
	 * 初始化[成果名+作者名+出版日期]到[OtherProductID]的映射
	 * @return
	 */
	private void initMap() {
		map = new HashMap<String, String>();
		List<Object[]> list = dao.query("select otherProduct.chineseName, otherProduct.authorName, otherProduct.pressDate, otherProduct.id from OtherProduct otherProduct");
		for (Object[] row : list) {
			String otherProductName = (String) row[0];
			String authorName = (String) row[1];
			String pressDateString = row[2] != null ? sdf.format((Date) row[2]) : "";
			if (!otherProductName.isEmpty()) {
				map.put(StringTool.fix(otherProductName + authorName + pressDateString), (String) row[3]);
			}
		}
	}
 

}
