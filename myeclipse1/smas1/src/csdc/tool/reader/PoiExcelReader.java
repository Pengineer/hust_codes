package csdc.tool.reader;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Excel格式数据读取器。要求第一行是表头，存储各列标题；后续行为正式数据
 * 可支持加宏Excel的读取
 * @author xuhan
 *
 */
public class PoiExcelReader implements TableDataReader {
	
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
	
	
	public PoiExcelReader(File file) {
		this.file = file;
	}
	
	public PoiExcelReader(String filePath) {
		this.file = new File(filePath);
	}
	
	public void readSheet(int sheetNumber) {
		HSSFSheet sheet = null;
		try {
			sheet = new HSSFWorkbook((new FileInputStream(file))).getSheetAt(sheetNumber);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		//读取正式数据
		int rowNum = sheet.getLastRowNum() + 1;
		int colNum = 0;
		for (int i = 0; i < rowNum; i++) {
			int curColNum = sheet.getRow(i).getLastCellNum();
			colNum = (colNum < curColNum) ? curColNum : colNum;
		}
		content = new String[rowNum][colNum];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < rowNum; i++) {
			for (int j = 0; j < colNum; j++) {
				HSSFCell cell =  sheet.getRow(i).getCell(j);
				if (cell == null) {
					
				} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						//如果是时间格式，则变为yyyy-MM-dd格式
						content[i][j] = sdf.format(cell.getDateCellValue());
					} else {
						content[i][j] = String.valueOf(cell.getNumericCellValue());
					}
                } else {
                	content[i][j] = cell.toString();
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
