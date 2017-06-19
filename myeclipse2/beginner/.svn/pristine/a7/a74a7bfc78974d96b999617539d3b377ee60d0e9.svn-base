package csdc.tool;

import java.util.Vector;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.util.Region;

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
		wb.setSheetName(0,obj[0],HSSFWorkbook.ENCODING_UTF_16);
		sheet1.setDefaultRowHeightInPoints(13);
		sheet1.setDefaultColumnWidth((short)18);
        //设置页脚
        HSSFFooter footer = sheet1.getFooter();
        footer.setRight("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());
        //设置样式 表头
		HSSFCellStyle style1=wb.createCellStyle(); 
		style1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		HSSFFont font1=wb.createFont();
//		font1.setFontHeightInPoints((short)13);
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style1.setFont(font1);
		 //设置样式 表头
		HSSFCellStyle style2=wb.createCellStyle(); 
		style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style2.setWrapText(false);
		//合并
//		sheet1.addMergedRegion(new Region(0,(short) 0,(short)0,(short)(obj.length-2)));
	   //设置样式 表头
//		HSSFCellStyle style3=wb.createCellStyle(); 
//		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		HSSFFont font3=wb.createFont();
//		font3.setFontHeightInPoints((short)20);
//		font3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//		style3.setFont(font3);
//		HSSFRow row0=sheet1.createRow(0);
//		row0.setHeightInPoints(35);
//		//第一行
//		HSSFCell cell0=row0.createCell((short)0);
//		cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
//		cell0.setCellValue(obj[0]);
//		cell0.setCellStyle(style3);
		//设置表头
		HSSFRow row1=sheet1.createRow(0);
		row1.setHeightInPoints(13);
		for(int i=1;i<obj.length;i++){
			HSSFCell cell1=row1.createCell((short)(i-1));
			cell1.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell1.setCellValue(obj[i]);
			cell1.setCellStyle(style1);
		}
		//填充数据
		for(int j=0;j<v.size();j++){
			HSSFRow row2=sheet1.createRow((short)(j+1)); // 第三行开始填充数据 
			Object[] o=(Object[])v.get(j);
			for(int k=0;k<o.length;k++){
				HSSFCell cell=row2.createCell((short)k);
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(o[k].toString());
				cell.setCellStyle(style2);
			}
		}
		
		wb.write(response.getOutputStream());
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
}
