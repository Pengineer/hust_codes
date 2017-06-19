package csdc.tool.reader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.Ostermiller.util.CSVParser;

/**
 * cvs格式数据读取器。要求第一行是表头，存储各列标题；后续行为正式数据
 * @author xuhan
 *
 */
public class CvsReader implements TableDataReader {
	
	/**
	 * cvs文件
	 */
	private File file;

	/**
	 * 各列标题
	 */
	private String[] titles;
	
	private String[][] content;
	
	/**
	 * 已读取了多少行正式数据
	 */
	private int currentRowIndex;
	
	
	public CvsReader(File file) {
		this.file = file;
	}
	
	public CvsReader(String filePath) {
		this.file = new File(filePath);
	}
	
	public void readData() {
		try {
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			CSVParser shredder = new CSVParser(new InputStreamReader(inputStream));
			content = shredder.getAllValues();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		//读取标题
		titles = content[0];
		
		currentRowIndex = 0;
	}

	public boolean hasNext() {
		return currentRowIndex < content.length - 1;
	}

	public String[] next() {
		return content[++currentRowIndex];
	}

	public int getCurrentRowIndex() {
		return currentRowIndex;
	}

	public String[] getTitles() {
		return titles;
	}

	public int getColumnNumber() {
		return content[0].length;
	}

	public int getRowNumber() {
		return content.length - 1;
	}
}
