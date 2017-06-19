package csdc.service;

import java.util.List;
import java.util.Map;


/**
 * @author yingao
 *	文档同步服务接口
 */
public interface IDocService  extends IBaseService{
	
	
	/**
	 * 获取path下面所有文件总数
	 * @param path
	 * @return
	 */
	public int getDirFileNum(String path);
	
	
	/**
	 * 获取数据库C_FILE字段不为空记录的总数
	 * @return
	 */
	public Map<String, int[]> getDataFileNum();
	
	public void upload(String[] path);


	public List<Map> getDirTree();
	
}
