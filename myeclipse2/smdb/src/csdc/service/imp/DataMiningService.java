package csdc.service.imp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.CellStyle;

import csdc.service.IDataMiningService;

/**
 * 数据挖掘：数据挖掘的父业务逻辑层
 * @author fengcl
 */
public class DataMiningService extends BaseService implements IDataMiningService{
	
	public ByteArrayInputStream commonExportExcel(List<List> dataList, String header){
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
		//合并标题行
		List title = dataList.get(0);
		sheet1.addMergedRegion(new Region(0,(short) 0,(short)0,(short)(title.size() - 1)));
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
		//设置样式 正文
		HSSFCellStyle style4=wb.createCellStyle();
		style4.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style4.setWrapText(false);

		//行号
		int rowNum = 0;
		//表头行
		HSSFRow row1=sheet1.createRow(rowNum++);
		row1.setHeightInPoints(35);
		
		HSSFCell cell = null;
		//第一行表头
		cell = row1.createCell((short)0);
		cell.setCellValue(header);
		cell.setCellStyle(style1);
		
//		//附加信息（统计指标）行
//		if (extras != null && !extras.isEmpty()) {
//			for (int i = 0; i < extras.size(); i++) {
//				sheet1.addMergedRegion(new Region(rowNum,(short) 0,(short)rowNum,(short)(title.size() - 1)));
//				HSSFRow tmpRow = sheet1.createRow(rowNum++);
//				tmpRow.setHeightInPoints(20);
//				cell = tmpRow.createCell((short)0);
//				cell.setCellValue((String)extras.get(i));
//				cell.setCellStyle(style4);
//			}
//		}
		//标题行
		HSSFRow row2=sheet1.createRow(rowNum++);
		row2.setHeightInPoints(20);
		
		//第二行标题
		for(int i = 0, len = title.size(); i < len; i++){
			cell = row2.createCell((short)i);
			cell.setCellValue((String)title.get(i));
			cell.setCellStyle(style2);
		}
		
		//第三行正文
		for(int i = 1, size = dataList.size(); i < size; i++){
			List datas = dataList.get(i);
			if (datas.isEmpty()) {
				continue;
			}
			HSSFRow row3 = sheet1.createRow((short)(rowNum++)); // 第三行开始填充数据
//			row3.setHeight((short)500);
			for(int j = 0, len = datas.size(); j < len; j++){
				cell = row3.createCell((short)j);
				//如果是数字类型的，则设置单元格类型位数字,可选择求和
				if(datas.get(j) instanceof Integer){
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					Integer intValue = (Integer) datas.get(j);
					cell.setCellValue(intValue);
				} else if(datas.get(j) instanceof Long){
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					long longValue = (Long) datas.get(j);
					cell.setCellValue(longValue);
				} else if(datas.get(j) instanceof Float){//instanceof:java中的instanceof 运算符是用来在运行时指出对象是否是特定类的一个实例
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					float floatValue = (Float) datas.get(j);
					cell.setCellValue(floatValue);
				} else if(datas.get(j) instanceof Double){
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					double doubleValue = (Double) datas.get(j);
					cell.setCellValue(doubleValue);
				} else {
					cell.setCellValue(new HSSFRichTextString(datas.get(j) == null ? "" : datas.get(j).toString()));//强制换行，用于历次拨款（万元）的分行显示
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
	
}
