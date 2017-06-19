package csdc.tool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class HSSFExport {
	/**
	 *
	 * Excel导出函数
	 * @param obj obj[0]为表名和文件名 obj[1]--obj[i-1]表头
	 * @param v   数据源(object[]对象数组)
	 * @param response servlet中的HttpServletResponse
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static void commonExportData(String[] obj, Vector v, HttpServletResponse response) throws Exception {
		response.setContentType("application/vnd.ms-excel");
		String title = obj[0]; // 第一个元素为文件名\表名
		if(null==title || title.equals(""))
			title = "文件导出";
		title = new String(title.getBytes("gb2312"), "iso-8859-1");
		//文件名
		response.setHeader("Content-Disposition", "attachment;filename="+title+".xls");
		response.setHeader("Pragma", "no-cache");
		HSSFWorkbook wb=new HSSFWorkbook();
		HSSFSheet  sheet1=wb.createSheet();
		wb.setSheetName(0,obj[0]);
		sheet1.setDefaultRowHeightInPoints(20);
		sheet1.setDefaultColumnWidth((short)18);
		//设置页脚
		HSSFFooter footer = sheet1.getFooter();
		footer.setRight("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());
		//设置样式 表头
		HSSFCellStyle style1=wb.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font1=wb.createFont();
		font1.setFontHeightInPoints((short)13);
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style1.setFont(font1);
		style1.setBorderTop(CellStyle.BORDER_THIN);
		style1.setBorderRight(CellStyle.BORDER_THIN);
		style1.setBorderBottom(CellStyle.BORDER_THIN);
		style1.setBorderLeft(CellStyle.BORDER_THIN);
		//设置样式 表头
		HSSFCellStyle style2=wb.createCellStyle();
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setWrapText(true);
		style2.setBorderTop(CellStyle.BORDER_THIN);
		style2.setBorderRight(CellStyle.BORDER_THIN);
		style2.setBorderBottom(CellStyle.BORDER_THIN);
		style2.setBorderLeft(CellStyle.BORDER_THIN);
		//合并
		sheet1.addMergedRegion(new Region(0,(short) 0,(short)0,(short)(obj.length-2)));
	    //设置样式 表头
		HSSFCellStyle style3=wb.createCellStyle();
		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font3=wb.createFont();
		font3.setFontHeightInPoints((short)20);
		font3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style3.setFont(font3);
		style3.setBorderTop(CellStyle.BORDER_THIN);
		style3.setBorderRight(CellStyle.BORDER_THIN);
		style3.setBorderBottom(CellStyle.BORDER_THIN);
		style3.setBorderLeft(CellStyle.BORDER_THIN);
		HSSFRow row0=sheet1.createRow(0);
		row0.setHeightInPoints(35);
		//第一行
		HSSFCell cell0=row0.createCell((short)0);
//		cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell0.setCellValue(obj[0]);
		cell0.setCellStyle(style3);
		//设置表头
		HSSFRow row1=sheet1.createRow(1);
		row1.setHeightInPoints(20);
		for(int i=1;i<obj.length;i++){
			HSSFCell cell1=row1.createCell((short)(i-1));
//			cell1.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell1.setCellValue(obj[i]);
			cell1.setCellStyle(style1);
		}
		//填充数据
		for(int j=0;j<v.size();j++){
			HSSFRow row2=sheet1.createRow((short)(j+2)); // 第三行开始填充数据
			Object[] o=(Object[])v.get(j);
			for(int k=0;k<o.length;k++){
				HSSFCell cell=row2.createCell((short)k);
//				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				//如果是数字类型的，则设置单元格类型位数字,可选择求和
				if(o[k] instanceof Integer){
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					Integer intValue = (Integer) o[k];
					cell.setCellValue(intValue);
				} else if(o[k] instanceof Long){
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					long longValue = (Long) o[k];
					cell.setCellValue(longValue);
				} else if(o[k] instanceof Float){
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					float floatValue = (Float) o[k];
					cell.setCellValue(floatValue);
				} else if(o[k] instanceof Double){
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					double doubleValue = (Double) o[k];
					cell.setCellValue(doubleValue);
				} else {
					cell.setCellValue(o[k] == null ? "" : o[k].toString());
				}
				cell.setCellStyle(style2);
			}
		}
		wb.write(response.getOutputStream());
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	
	/**
	 * Excel导出函数
	 * @param dataList	数据源
	 * @param header	第一行表头名称
	 * @param title		第二行标题名称
	 * @return	输入流
	 */
	@SuppressWarnings({ "deprecation" })
	public static ByteArrayInputStream commonExportExcel(List dataList, String header, String[] title){
		HSSFWorkbook wb=new HSSFWorkbook();
		HSSFSheet  sheet1=wb.createSheet();
		header = (null == header) ? "" : header;
		wb.setSheetName(0, ("".equals(header)) ? "Sheet1" : header);
		sheet1.setDefaultRowHeightInPoints(20);
		sheet1.setDefaultColumnWidth((short)22);
		//设置页脚
		HSSFFooter footer = sheet1.getFooter();
		footer.setRight("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());
		//设置样式 表头
		HSSFCellStyle style1=wb.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font1=wb.createFont();
		font1.setFontHeightInPoints((short)20);
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style1.setFont(font1);
		style1.setBorderTop(CellStyle.BORDER_THIN);
		style1.setBorderRight(CellStyle.BORDER_THIN);
		style1.setBorderBottom(CellStyle.BORDER_THIN);
		style1.setBorderLeft(CellStyle.BORDER_THIN);
		//合并标题行
		sheet1.addMergedRegion(new Region(0,(short) 0,(short)0,(short)(title.length - 1)));
		//设置样式 标题
		HSSFCellStyle style2=wb.createCellStyle();
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font2=wb.createFont();
		font2.setFontHeightInPoints((short)13);
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style2.setFont(font2);
		style2.setBorderTop(CellStyle.BORDER_THIN);
		style2.setBorderRight(CellStyle.BORDER_THIN);
		style2.setBorderBottom(CellStyle.BORDER_THIN);
		style2.setBorderLeft(CellStyle.BORDER_THIN);
		//设置样式 正文
		HSSFCellStyle style3=wb.createCellStyle();
		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style3.setWrapText(true);
		style3.setBorderTop(CellStyle.BORDER_THIN);
		style3.setBorderRight(CellStyle.BORDER_THIN);
		style3.setBorderBottom(CellStyle.BORDER_THIN);
		style3.setBorderLeft(CellStyle.BORDER_THIN);
		
		//第一行表头
		HSSFRow row1=sheet1.createRow(0);
		row1.setHeightInPoints(35);
		//第二行标题
		HSSFRow row2=sheet1.createRow(1);
		row2.setHeightInPoints(20);
		
		HSSFCell cell = null;
		//第一行表头
		cell = row1.createCell((short)0);
		cell.setCellValue(header);
		cell.setCellStyle(style1);
		
		//第二行标题
		for(int i = 0, len = title.length; i < len; i++){
			cell = row2.createCell((short)i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(style2);
		}
		
		//第三行正文
		for(int i = 0, size = dataList.size(); i < size; i++){
			Object[] o = (Object[]) dataList.get(i);
			HSSFRow row3 = sheet1.createRow((short)(i+2)); // 第三行开始填充数据
//			row3.setHeight((short)500);
			for(int j = 0, len = o.length; j < len; j++){
				cell = row3.createCell((short)j);
				//如果是数字类型的，则设置单元格类型位数字,可选择求和
				if(o[j] instanceof Integer){
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					Integer intValue = (Integer) o[j];
					cell.setCellValue(intValue);
				} else if(o[j] instanceof Long){
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					long longValue = (Long) o[j];
					cell.setCellValue(longValue);
				} else if(o[j] instanceof Float){
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					float floatValue = (Float) o[j];
					cell.setCellValue(floatValue);
				} else if(o[j] instanceof Double){
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					double doubleValue = (Double) o[j];
					cell.setCellValue(doubleValue);
				} else {
//					cell.setCellValue(o[j] == null ? "" : o[j].toString());
					cell.setCellValue(new HSSFRichTextString(o[j] == null ? "" : o[j].toString()));//强制换行，用于历次拨款（万元）的分行显示
				}
				cell.setCellStyle(style3);
			}
		}
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			wb.write(bos);
			bos.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		byte[] content = bos.toByteArray();
		ByteArrayInputStream bis = null;
		try{
			bis = new ByteArrayInputStream(content);
			bis.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		return bis;
	}
	
	/**
	 * 根据给定模板导出Excel
	 * @param arrayList	数据源
	 * @param modelFilePath	模板文件路径
	 * @param dataStartRowNum	正文数据内容行号（填写Excel中的行号）
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static InputStream exportFromModel(List<List> arrayList, String modelFilePath, int dataStartRowNum){
	    // 模板源文件读入[获取模板的样式]
	    HSSFWorkbook modelWb = null;
		try {
			modelWb = new HSSFWorkbook(new FileInputStream(new File(modelFilePath)));
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
	    //模板中sheet的个数
	    int sheetSize = modelWb.getNumberOfSheets();
	    for(int i = 0; i < sheetSize; i++){
	    	//获取每个sheet
	    	HSSFSheet sheet = modelWb.getSheetAt(i);
	    	//每个sheet的列数
	    	int cellSize = sheet.getRow(dataStartRowNum - 1).getLastCellNum();//dataStartRowNum - 1 ：因为这里是从0开始
	    	//获取每个cell的样式，存入样式数组
	    	HSSFCellStyle[] titleCellStyle = new HSSFCellStyle[cellSize];//标题行
	    	HSSFCellStyle[] dataCellStyle = new HSSFCellStyle[cellSize]; //内容行
	    	for(int j = 0; j < cellSize; j++){
	    		titleCellStyle[j] = sheet.getRow(1).getCell(j).getCellStyle();
	    		if(null != sheet.getRow(dataStartRowNum - 1) && null != sheet.getRow(dataStartRowNum - 1).getCell(j)){
	    			dataCellStyle[j] = sheet.getRow(dataStartRowNum - 1).getCell(j).getCellStyle();
	    		}
	    	}
	    	if (arrayList.size() < i + 2) {
				break;
			}
	    	
	    	// 获取每个sheet的数据
  			List<Object[]> dataList = arrayList.get(0);
  			List variableList = arrayList.get(1);
  			HSSFCell cell = null;
  			//填充可调变量和统计变量
  			int m =0;
			HSSFRow variableRow3 = sheet.getRow(3);//第四行
			cell = variableRow3.getCell(1);
			cell.setCellValue( (Double) variableList.get(m));
			cell = variableRow3.getCell(2);
			cell.setCellValue((Double)variableList.get(m+1));
			cell = variableRow3.getCell(6);
			cell.setCellValue((Double) variableList.get(m+2));
			cell = variableRow3.getCell(9);
			cell.setCellValue((Double) variableList.get(m+3));
			cell = variableRow3.getCell(12);
			cell.setCellValue((Double) variableList.get(m+4));
			cell = variableRow3.getCell(13);
			cell.setCellValue((Double) variableList.get(m+5));
			cell = variableRow3.getCell(22);
			cell.setCellValue((Integer) variableList.get(m+6));
			cell = variableRow3.getCell(23);
			cell.setCellValue((Integer) variableList.get(m+7));
			HSSFRow variableRow4 = sheet.getRow(5);//第六行
			cell = variableRow4.getCell(4);
			cell.setCellValue((Integer) variableList.get(m+8));
			cell = variableRow4.getCell(6);
			cell.setCellValue((Double) variableList.get(m+9));
			cell = variableRow4.getCell(9);
			cell.setCellValue((Double) variableList.get(m+10));
			cell = variableRow4.getCell(12);
			cell.setCellValue((Double) variableList.get(m+11));
			cell = variableRow4.getCell(14);
			cell.setCellValue((Long) variableList.get(m+12));
			cell = variableRow4.getCell(19);
			cell.setCellValue((Integer) variableList.get(m+13));
			cell = variableRow4.getCell(20);
			cell.setCellValue((Integer) variableList.get(m+14));
			cell = variableRow4.getCell(21);
			cell.setCellValue((Integer) variableList.get(m+15));
			cell = variableRow4.getCell(22);
			cell.setCellValue((Integer) variableList.get(m+16));
			cell = variableRow4.getCell(23);
			cell.setCellValue((Integer) variableList.get(m+17));
  			//第三行正文
  			for(int k = 0, len = dataList.size(); k < len; k++){
  				Object[] o = dataList.get(k);
  				HSSFRow dataRow = sheet.createRow((short)(k + dataStartRowNum - 1)); // 第数据行开始填充数据
//  				dataRow.setHeight((short)500);
  				dataRow.setHeightInPoints((float) 13.5);
  				
  				for(int j = 0; j < o.length; j++){
  					cell = dataRow.createCell((short)j);
  					//如果是数字类型的，则设置单元格类型位数字,可选择求和
  					if(o[j] instanceof Integer){
  						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
  						Integer intValue = (Integer) o[j];
  						cell.setCellValue(intValue);
  					} else if(o[j] instanceof Long){
  						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
  						long longValue = (Long) o[j];
  						cell.setCellValue(longValue);
  					} else if(o[j] instanceof Float){
  						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
  						float floatValue = (Float) o[j];
  						cell.setCellValue(floatValue);
  					} else if(o[j] instanceof Double){
  						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
  						double doubleValue = (Double) o[j];
  						cell.setCellValue(doubleValue);
  					} else {
  						cell.setCellValue(new HSSFRichTextString(o[j] == null ? "" : o[j].toString()));
  					}
  					if (null != dataCellStyle[j]) {
  						cell.setCellStyle(dataCellStyle[j]);
					}else {
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);//文本格式
					}
  				}
  			}
	    }
	    
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			modelWb.write(bos);
			bos.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		byte[] content = bos.toByteArray();
		ByteArrayInputStream bis = null;
		try{
			bis = new ByteArrayInputStream(content);
			bis.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		return bis;
	}
	
	/**
	 * 根据给定模板导出Excel
	 * @param arrayList
	 * @param modelFilePath
	 * @param filePath	//导出文件输出路径
	 * @return
	 */
	public static InputStream exportFromModel(List<List> arrayList, String modelFilePath, String filePath){
	    // 模板源文件读入[获取模板的样式]
	    Workbook modelWb = null;
		try {
			modelWb = WorkbookFactory.create(new FileInputStream(new File(modelFilePath)));
		} catch (IOException e2) {
			e2.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		
	    //模板中sheet的个数
	    int sheetSize = modelWb.getNumberOfSheets();
	    for(int i = 0; i < sheetSize; i++){
	    	//获取每个sheet
	    	Sheet sheet = modelWb.getSheetAt(i);
	    	//每个sheet的列数
	    	int cellSize = sheet.getRow(1).getLastCellNum();
	    	//获取每个cell的样式，存入样式数组
	    	CellStyle[] titleCellStyle = new CellStyle[cellSize];//标题行
	    	CellStyle[] dataCellStyle = new CellStyle[cellSize]; //内容行
	    	for(int j = 0; j < cellSize; j++){
	    		titleCellStyle[j] = sheet.getRow(1).getCell(j).getCellStyle();
	    		if(null != sheet.getRow(2)){
	    			dataCellStyle[j] = sheet.getRow(2).getCell(j).getCellStyle();
	    		}
	    	}
	    	// 获取每个sheet的数据
  			List<Object[]> dataList = arrayList.get(i);
  			Cell cell = null;
  			//第三行正文
  			for(int k = 0, len = dataList.size(); k < len; k++){
  				Object[] o = dataList.get(k);
  				Row dataRow = sheet.createRow((short)(k+2)); // 第三行开始填充数据
//  				dataRow.setHeight((short)500);
  				dataRow.setHeightInPoints((float) 13.5);
  				
  				for(int j = 0; j < o.length; j++){
  					cell = dataRow.createCell((short)j);
  					cell.setCellValue(o[j] == null ? "" : String.valueOf(o[j]));
  					cell.setCellType(HSSFCell.CELL_TYPE_STRING);//文本格式
  				}
  			}
	    }
	    
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			modelWb.write(bos);
			bos.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		byte[] content = bos.toByteArray();
		ByteArrayInputStream bis = null;
		try{
			bis = new ByteArrayInputStream(content);
			bis.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		return bis;
	}
	
	/**
	 * 生成Excel到指定路径
	 * @param dataList		数据源
	 * @param header		第一行表头名称
	 * @param title			第二行标题名称
	 * @param outputPath	输出路径
	 * @return	输入流
	 */
	@SuppressWarnings("deprecation")
	public static void createExcel(List dataList, String header, String[] title, String outputPath){
		HSSFWorkbook wb=new HSSFWorkbook();
		HSSFSheet sheet=wb.createSheet();
		header = (null == header) ? "" : header;
		wb.setSheetName(0, ("".equals(header)) ? "Sheet1" : header);
		sheet.setDefaultRowHeightInPoints(20);
		sheet.setDefaultColumnWidth((short)22);
		//设置页脚
		HSSFFooter footer = sheet.getFooter();
		footer.setRight("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());
		//设置样式 表头
		HSSFCellStyle style1=wb.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font1=wb.createFont();
		font1.setFontHeightInPoints((short)20);
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style1.setFont(font1);
		style1.setBorderTop(CellStyle.BORDER_THIN);
		style1.setBorderRight(CellStyle.BORDER_THIN);
		style1.setBorderBottom(CellStyle.BORDER_THIN);
		style1.setBorderLeft(CellStyle.BORDER_THIN);
		//合并标题行
		sheet.addMergedRegion(new Region(0,(short) 0,(short)0,(short)(title.length - 1)));
		//设置样式 标题
		HSSFCellStyle style2=wb.createCellStyle();
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font2=wb.createFont();
		font2.setFontHeightInPoints((short)13);
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style2.setFont(font2);
		style2.setBorderTop(CellStyle.BORDER_THIN);
		style2.setBorderRight(CellStyle.BORDER_THIN);
		style2.setBorderBottom(CellStyle.BORDER_THIN);
		style2.setBorderLeft(CellStyle.BORDER_THIN);
		//设置样式 正文
		HSSFCellStyle style3=wb.createCellStyle();
		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style3.setWrapText(true);
		style3.setBorderTop(CellStyle.BORDER_THIN);
		style3.setBorderRight(CellStyle.BORDER_THIN);
		style3.setBorderBottom(CellStyle.BORDER_THIN);
		style3.setBorderLeft(CellStyle.BORDER_THIN);
		
		//第一行表头
		HSSFRow row1=sheet.createRow(0);
		row1.setHeightInPoints(35);
		//第二行标题
		HSSFRow row2=sheet.createRow(1);
		row2.setHeightInPoints(20);
		
		HSSFCell cell = null;
		//第一行表头
		cell = row1.createCell((short)0);
		cell.setCellValue(header);
		cell.setCellStyle(style1);
		
		//第二行标题
		for(int i = 0, len = title.length; i < len; i++){
			cell = row2.createCell((short)i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(style2);
		}
		
		//第三行正文
		for(int i = 0, size = dataList.size(); i < size; i++){
			Object[] o = (Object[]) dataList.get(i);
			HSSFRow row3 = sheet.createRow((short)(i+2)); // 第三行开始填充数据
//			row3.setHeight((short)500);
			for(int j = 0, len = o.length; j < len; j++){
				cell = row3.createCell((short)j);
				//如果是数字类型的，则设置单元格类型位数字,可选择求和
				if(o[j] instanceof Integer){
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					Integer intValue = (Integer) o[j];
					cell.setCellValue(intValue);
				} else if(o[j] instanceof Long){
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					long longValue = (Long) o[j];
					cell.setCellValue(longValue);
				} else if(o[j] instanceof Float){
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					float floatValue = (Float) o[j];
					cell.setCellValue(floatValue);
				} else if(o[j] instanceof Double){
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					double doubleValue = (Double) o[j];
					cell.setCellValue(doubleValue);
				} else {
//					cell.setCellValue(o[j] == null ? "" : o[j].toString());
					cell.setCellValue(new HSSFRichTextString(o[j] == null ? "" : o[j].toString()));//强制换行，用于历次拨款（万元）的分行显示
				}
				cell.setCellStyle(style3);
			}
		}
		
		try {
	    	FileOutputStream fileOut = new FileOutputStream(outputPath);
			wb.write(fileOut);
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Excel导出函数
	 * @param dataList	数据源
	 * @param header	第一行表头名称
	 * @param title		第二行标题名称
	 * @param tail		末尾行表尾说明
	 * @return	输入流
	 */
	@SuppressWarnings({ "deprecation" })
	public static ByteArrayInputStream commonExportExcel(List dataList, String header, String[] title, String tail, float tailHeight){
		HSSFWorkbook wb=new HSSFWorkbook();
		HSSFSheet  sheet1=wb.createSheet();
		header = (null == header) ? "" : header;
		wb.setSheetName(0, ("".equals(header)) ? "Sheet1" : header);
		sheet1.setDefaultRowHeightInPoints(20);
		sheet1.setDefaultColumnWidth((short)30);
		//设置页脚
		HSSFFooter footer = sheet1.getFooter();
		footer.setRight("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());
		//设置样式 表头
		HSSFCellStyle style1=wb.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font1=wb.createFont();
		font1.setFontHeightInPoints((short)14);
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style1.setFont(font1);
		style1.setBorderTop(CellStyle.BORDER_THIN);
		style1.setBorderRight(CellStyle.BORDER_THIN);
		style1.setBorderBottom(CellStyle.BORDER_THIN);
		style1.setBorderLeft(CellStyle.BORDER_THIN);
		//合并标题行
		sheet1.addMergedRegion(new Region(0,(short) 0,(short)0,(short)(title.length - 1)));
		//设置样式 标题
		HSSFCellStyle style2=wb.createCellStyle();
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font2=wb.createFont();
		font2.setFontHeightInPoints((short)12);
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style2.setFont(font2);
		style2.setBorderTop(CellStyle.BORDER_THIN);
		style2.setBorderRight(CellStyle.BORDER_THIN);
		style2.setBorderBottom(CellStyle.BORDER_THIN);
		style2.setBorderLeft(CellStyle.BORDER_THIN);
		//设置样式 正文
		HSSFCellStyle style3=wb.createCellStyle();
		style3.setAlignment(HSSFCellStyle.ALIGN_GENERAL);
		style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style3.setWrapText(true);
		style3.setBorderTop(CellStyle.BORDER_THIN);
		style3.setBorderRight(CellStyle.BORDER_THIN);
		style3.setBorderBottom(CellStyle.BORDER_THIN);
		style3.setBorderLeft(CellStyle.BORDER_THIN);
		//设置样式 表尾
		HSSFCellStyle style4=wb.createCellStyle();
		style4.setAlignment(HSSFCellStyle.ALIGN_GENERAL);
		style4.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		HSSFFont font4=wb.createFont();
		font4.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style4.setFont(font4);
		style4.setWrapText(true);
		
		//第一行表头
		HSSFRow row1=sheet1.createRow(0);
		row1.setHeightInPoints(35);
		//第二行标题
		HSSFRow row2=sheet1.createRow(1);
		row2.setHeightInPoints(20);
		
		HSSFCell cell = null;
		//第一行表头
		cell = row1.createCell((short)0);
		cell.setCellValue(header);
		cell.setCellStyle(style1);
		
		//第二行标题
		for(int i = 0, len = title.length; i < len; i++){
			cell = row2.createCell((short)i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(style2);
		}
		
		//第三行正文
		for(int i = 0, size = dataList.size(); i < size; i++){
			Object[] o = (Object[]) dataList.get(i);
			HSSFRow row3 = sheet1.createRow((short)(i+2)); // 第三行开始填充数据
//			row3.setHeight((short)500);
			for(int j = 0, len = o.length; j < len; j++){
				cell = row3.createCell((short)j);
				//如果是数字类型的，则设置单元格类型位数字,可选择求和
				if(o[j] instanceof Integer){
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					Integer intValue = (Integer) o[j];
					cell.setCellValue(intValue);
				} else if(o[j] instanceof Long){
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					long longValue = (Long) o[j];
					cell.setCellValue(longValue);
				} else if(o[j] instanceof Float){
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					float floatValue = (Float) o[j];
					cell.setCellValue(floatValue);
				} else if(o[j] instanceof Double){
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					double doubleValue = (Double) o[j];
					cell.setCellValue(doubleValue);
				} else {
					cell.setCellValue(o[j] == null ? "" : o[j].toString());
				}
				cell.setCellStyle(style3);
			}
		}
		
		//最后一行表尾
		int totalRows = dataList.size()+2;
		HSSFRow lastRow=sheet1.createRow(totalRows);
		lastRow.setHeightInPoints(tailHeight);
		cell = lastRow.createCell((short)0);
		cell.setCellValue(new HSSFRichTextString(tail));
		cell.setCellStyle(style4);
		//合并表尾
		sheet1.addMergedRegion(new Region(totalRows,(short) 0,(short) (totalRows),(short)(title.length - 1)));
		//自动调整行宽
		for (int i = 0, len = title.length; i < len; i++) {
			sheet1.autoSizeColumn(i);
		}
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			wb.write(bos);
			bos.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		byte[] content = bos.toByteArray();
		ByteArrayInputStream bis = null;
		try{
			bis = new ByteArrayInputStream(content);
			bis.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		return bis;
	}
}
