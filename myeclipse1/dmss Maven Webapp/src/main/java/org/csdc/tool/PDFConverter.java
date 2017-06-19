package org.csdc.tool;

import java.io.File;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeException;
import org.artofsolving.jodconverter.office.OfficeManager;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.csdc.bean.GlobalInfo;


/**
 * 使open office将office文档转换成pdf格式
 * @author lvjia
 *
 */
public class PDFConverter {

	private static PDFConverter uniqueConverter;
	private PDFConverter() {}	
	public static PDFConverter getInstance() {
		if(uniqueConverter == null) {
			uniqueConverter = new PDFConverter();
		} 
		return uniqueConverter;
	}
	
	private static OfficeManager officeManager;
	/** OpenOffice安装根目录 */
	private static String OFFICE_HOME = GlobalInfo.OFFICE_HOME;

	private static int[] port = { 8100 };

	public void convert2PDF(String inputFile, String pdfFile)  throws OfficeException{
		pdfFile = pdfFile.replaceAll(" ", "").replaceAll("　", "");
		System.out.println("进行文档转换转换:" + inputFile + " --> " + pdfFile);
		OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
		converter.convert(new File(inputFile), new File(pdfFile));
	}

	public void convert2PDF(String inputFile)   {
		inputFile = inputFile.replaceAll(" ", "").replaceAll("　", "");
		String pdfFile = FileTool.getFilePrefix(inputFile) + ".pdf";
		convert2PDF(inputFile, pdfFile);
	}

	public static void startService() throws Exception {
		DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
		try {
			System.out.println("准备启动服务....");
			configuration.setOfficeHome(OFFICE_HOME);// 设置OpenOffice.org安装目录
			configuration.setPortNumbers(port); // 设置转换端口，默认为8100
			configuration.setTaskExecutionTimeout(1000 * 60 * 5L);// 设置任务执行超时为5分钟
			configuration.setTaskQueueTimeout(1000 * 60 * 60 * 24L);// 设置任务队列超时为24小时
			officeManager = configuration.buildOfficeManager();
			officeManager.start(); // 启动服务
			System.out.println("office转换服务启动成功!");
		} catch (Exception ce) {
			System.out.println("office转换服务启动失败!详细信息:" + ce);
			throw ce;
		}
	}

	public static void stopService() {
		System.out.println("关闭office转换服务....");
		if (officeManager != null) {
			officeManager.stop();
		}
		System.out.println("关闭office转换成功!");
	}
	
	public static void main(String[] args) throws Exception {
		String docPath = "E:\\系自主创新\\研究生论文\\数据\\2011年一般项目申请评审书-Z\\曾保根(91c29948-9826-4e00-932e-68fe3a468920).doc";
		String pdfPath = "E:\\系自主创新\\研究生论文\\实验\\tika测试\\基于碎片分词的未登录词识别方法.pdf";
		String htmlPath = "E:\\系自主创新\\研究生论文\\实验\\tika测试\\冰与火之歌第三季_百度百科.htm";
		String picPath = "F:\\dmss_home\\hdfs\\fetch\\16\\44\\8c\\16448cd6ec7af958e948e07267798042uu.png";
		String mp3Path = "E:\\系自主创新\\研究生论文\\实验\\tika测试\\张宇-囚鸟.mp3";
		String excelPath = "E:\\系自主创新\\研究生论文\\实验\\tika测试\\独立学院代码（2010年02月05日）.xls";
		String pptPath = "E:\\系自主创新\\研究生论文\\实验\\tika测试\\金天凡_开题答辩.ppt";
		String txtPath = "F:\\dmss_home\\hdfs\\fetch\\c8\\1e\\93\\c81e933b6a490b8704d918f5b919b14b";
		
		PDFConverter converter = new PDFConverter();
		PDFConverter.startService();
		converter.convert2PDF(pdfPath,pdfPath+"_");
		PDFConverter.stopService();
	}
		
}

