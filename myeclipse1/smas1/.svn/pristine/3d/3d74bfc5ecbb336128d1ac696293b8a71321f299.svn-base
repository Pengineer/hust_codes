package csdc.tool.reader;

import java.util.List;

import csdc.dao.JdbcDao;

/**
 * jdbc读取器，使用spring封装的jdbc数据源
 * @author xuhan
 *
 */
public class JdbcTemplateReader implements TableDataReader {
	
	private JdbcDao dao;

	/**
	 * 已读取了多少行正式数据
	 */
	private int currentRowIndex;
	
	private List<String[]> content;
	
	
	public JdbcTemplateReader(JdbcDao dao) {
		this.dao = dao;
	}
	
	public void query(String sql) {
		content = dao.query(sql);
		currentRowIndex = 0;
	}

	public boolean hasNext() {
		return currentRowIndex < content.size();
	}

	public String[] next() {
		return content.get(currentRowIndex++);
	}

	public int getCurrentRowIndex() {
		return currentRowIndex;
	}

	public int getColumnNumber() {
		return content.get(0).length;
	}

	public int getRowNumber() {
		return content.size();
	}
}
