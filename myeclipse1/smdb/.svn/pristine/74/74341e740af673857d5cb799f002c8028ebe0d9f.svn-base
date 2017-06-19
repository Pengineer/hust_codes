package csdc.service;

import java.util.Map;

public interface ITotalSearchService extends IBaseService {

	/**
	 * 全站检索
	 * @param range：检索范围
	 * @param keyword：关键字
	 * @return 
	 */
	public Map totalSearch(String range, String keyword);
	
	/**
	 * 创建索引 or 更新索引
	 * @param range
	 * @return 成功与否
	 */
	public boolean updateIndex(String range);
	
	/**
	 * 检测索引是否存在
	 * @param range
	 * @return 存在与否
	 */
	public boolean isExistIndexFile(String range);
}
