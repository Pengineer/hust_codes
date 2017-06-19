package csdc.tool.execution.importer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import csdc.tool.JDBC;
import csdc.tool.FileTool;
import csdc.tool.WordTool;

public class GeneralAndInstpMidInspectionFile2013Importer {
	public static void main(String[] args) {
//		try {
//			importData("D://2013年基地项目中检信息.xls", "D://orifile", "instp", "2013", "D:/");
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println("ok1");		
//		try {
//			importData("D://2013年一般项目中检信息.xls", "D://orifile", "general", "2013", "D:/");
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println("ok");
		try {
			importData("D://2013年一般项目中检信息1.xls", "D://15个中检报告", "general", "2013", "D:/");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("ok");
	}
	//1.遍历excel文件，取出其中的中检报告附件名（可以分为一般项目和基地项目）
	//2.遍历目录下的所有文件，取得其文件名，与中检报告附件名匹配
	//3.匹配上以后，取出文件的最后修改时间，并取出excel文件的项目编号，组装成标准文件名和标准路径
	//4.文件重命名，并挪动到相应的目录下
	//5.根据项目编号，从T_PROJECT_Granted匹配C_NUMBER,取出C_ID(即立项ID)，根据立项ID,在T_PROJECT_MIDINSPECTION中，匹配C_GRANTED_ID,放入C_FILE
	/**
	 * @author wang
	 * @param excelAddress excel地址
	 * @param fileAddress file源文件地址
	 * @param projectType 项目类型（general、instp）
	 * @param yearOfMinInspection 中检时间
	 * @param directoryAddress	希望最后保存的目录地址
	 */
	public static void importData (String excelAddress, String fileAddress, String projectType, String yearOfMinInspection, String directoryAddress) throws IOException, Exception{
		FileInputStream inp = new FileInputStream(excelAddress);
		Workbook wb = WorkbookFactory.create(inp);
		Sheet sheet = wb.getSheetAt(0);//从第一个工作簿取值
		Row row;
		Cell nameOfMidInspectionCell;//中检报告附件名所在cell
		Cell projectNumCell;//项目编号所在cell
		String[] fileNameStrings;//文件全名
		JDBC jdbc = new JDBC();
		ResultSet rs;
		int count;
		System.out.printf("%d\n",sheet.getLastRowNum());
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {//1.遍历excel文件，取出其中的中检报告附件名（可以分为一般项目和基地项目）
			row = sheet.getRow(i);
			nameOfMidInspectionCell = row.getCell(11);//中检报告附件名所在cell
			projectNumCell = row.getCell(1);//项目编号所在cell
			String nameOfMidInspectionString = "";//中检报告附件名
			String projectNumberString = "";//项目编号
			String fileLastModifyDateString = "";//最后修改文件时间
			String standardFileNameString = "";//标准文件名
			String standardFileDirectoryString = "";//标准目录名
			String standardFilePathString = "";//标准路径名
			String standardFileUploadString = "";//标准上传路径名
			String grantedIDString = "";//立项ID	
			String fileSuffixstringString = "";//文件后缀名
			if (nameOfMidInspectionCell != null && projectNumCell != null) {
				nameOfMidInspectionString = nameOfMidInspectionCell.getStringCellValue();//中检报告附件名
				projectNumberString = projectNumCell.getStringCellValue();//项目编号
				System.out.println(nameOfMidInspectionString);
				File file = new File(fileAddress);    
				File[] array = file.listFiles(); 
				System.out.printf("%d\n",array.length);
				try {	
					count = 1;
					for(int j = 0;j < array.length; j++){//2.遍历目录下的所有文件，取得其文件名，与中检报告附件名匹配       
						if (array[j].getName().equals(nameOfMidInspectionString)) {
							count++;
							System.out.println(array[j].getName());
							fileNameStrings = array[j].getName().split("\\.");
							fileSuffixstringString = fileNameStrings[1];
							fileLastModifyDateString = WordTool.getFileLastModifyDate(array[j]);//3.匹配上以后，取出文件的最后修改时间，并取出excel文件的项目编号，组装成标准文件名和标准路径
							standardFileNameString = projectType + "_mid_" + yearOfMinInspection + "_" + projectNumberString + "_" + fileLastModifyDateString + "." + fileSuffixstringString;
							standardFileDirectoryString = directoryAddress + "/upload/project/" + projectType + "/mid/" + yearOfMinInspection ;
							standardFilePathString = standardFileDirectoryString + "/" + standardFileNameString;
							standardFileUploadString = "upload/project/" + projectType + "/mid/" + yearOfMinInspection +"/" +standardFileNameString;
							System.out.println(standardFileUploadString);
							//5.根据项目编号，从T_PROJECT_Granted匹配C_NUMBER,取出C_ID(即立项ID)，根据立项ID,在T_PROJECT_MIDINSPECTION中，匹配C_GRANTED_ID,放入C_FILE			
							rs = jdbc.executeQuery("select C_ID from T_PROJECT_GRANTED t where t.C_NUMBER = '" + projectNumberString + "'");
							System.out.println(projectNumCell.getStringCellValue());
							try {
								while (rs != null && rs.next()) {
									grantedIDString = rs.getString(1);
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
							System.out.println("grantedID-----" + grantedIDString);
							if (!grantedIDString.equals("")) {
								jdbc.executeQuery("UPDATE T_PROJECT_MIDINSPECTION t SET t.C_FILE = '" + standardFileUploadString + "' where t.C_GRANTED_ID = '" + grantedIDString + "' and to_char(t.c_imported_date, 'yyyy-MM-dd')='2013-10-20'");	
								System.out.println("UPDATE T_PROJECT_MIDINSPECTION t SET t.C_FILE = '" + standardFileUploadString + "' where t.C_GRANTED_ID = '" + grantedIDString + "' and to_char(t.c_imported_date, 'yyyy-MM-dd')='2013-10-20'");							
								FileTool.mkdir_p(standardFileDirectoryString);//4.文件重命名，并挪动到相应的目录下
								array[j].renameTo(new File(standardFilePathString));								
							}
							break;
						}
			        }
					System.out.println("文件次数-------" + count);
			    } catch (Exception e1) {
					e1.printStackTrace();
				}			
			}
			if (i % 20 == 0) {// 假设n个查询 就用300/n 得到(此列的 n = 30)
				jdbc.closeConn();
				jdbc = new JDBC();
				try {
					Thread.currentThread().sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

