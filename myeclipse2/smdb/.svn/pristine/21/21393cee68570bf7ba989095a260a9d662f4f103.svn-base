package csdc.tool.reader;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Excel格式数据读取器。要求第一行是表头，存储各列标题；后续行为正式数据
 * @author xuhan
 *
 */
public class ExcelReader implements TableDataReader {
	
	/**
	 * Excel文件
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
	
	
	public ExcelReader(File file) {
		this.file = file;
	}
	
	public ExcelReader(String filePath) {
		this.file = new File(filePath);
	}
	
	public void readSheet(int sheetNumber) {
		Sheet sheet = null;
		try {
			sheet = Workbook.getWorkbook(new FileInputStream(file)).getSheet(sheetNumber);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		//读取正式数据
		content = new String[sheet.getRows()][sheet.getColumns()];
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //读取excel时间格式精确到时分秒
		TimeZone zone = TimeZone.getTimeZone("GMT+0");
		sdf.setTimeZone(zone);*/
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//读取excel时间格式为年月日
		for (int i = 0; i < sheet.getRows(); i++) {
			for (int j = 0; j < sheet.getColumns(); j++) {
				Cell cell = sheet.getCell(j, i);
				if (cell.getType() == CellType.DATE) {
					//如果是时间格式，则变为yyyy-MM-dd格式
					DateCell dateCell = (DateCell) cell;
					content[i][j] = sdf.format(dateCell.getDate());
				} else {
					content[i][j] = cell.getContents().trim();
				}
			}
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
