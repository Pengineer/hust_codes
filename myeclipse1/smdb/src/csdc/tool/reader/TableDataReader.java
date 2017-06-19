package csdc.tool.reader;

/**
 * 表格类数据读取器
 * @author xuhan
 *
 */
public interface TableDataReader {
	
	/**
	 * 是否还有可读数据
	 * @return
	 * @throws Exception 
	 */
	public boolean hasNext() throws Exception;
	
	/**
	 * 获取下一行数据
	 * @return
	 * @throws Exception 
	 */
	public String[] next() throws Exception;
	
	/**
	 * 获取总列数
	 * @return
	 */
	public int getColumnNumber();
	
	/**
	 * 获取总行数
	 * @return
	 */
	public int getRowNumber();
	
	/**
	 * 获取已读取了多少行正式数据
	 * @return
	 */
	public int getCurrentRowIndex();

}
