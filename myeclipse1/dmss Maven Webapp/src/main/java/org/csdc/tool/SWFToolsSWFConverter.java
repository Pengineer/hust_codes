package org.csdc.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.csdc.bean.GlobalInfo;



/**
 * PDF转换成SWF文件
 * @author lvjia
 *
 */
public class SWFToolsSWFConverter {
	/**SWFTools pdf2swf.exe路径*/
	private static String PDF2SWF_PATH = GlobalInfo.SWFTOOLS_PDF2SWF_PATH;
	
	public void convert2SWF(String inputFile, String swfFile) {
		
		File pdfFile = new File(inputFile);
		File outFile = new File(swfFile);
		if(!inputFile.endsWith(".pdf")){
			System.out.println("文件格式非PDF！");
			return ;
		}
		if(!pdfFile.exists()){
			System.out.println("PDF文件不存在！");
			return ;
		}
		if(outFile.exists()){
			System.out.println("SWF文件已存在！");
			return ;
		}
		String command = PDF2SWF_PATH +" -t"+" \""+inputFile+"\" -o "+swfFile+" -s flashversion=9 -s languagedir=C:/xpdf/xpdf-chinese-simplified";
		System.out.println(command);
		try {
			System.out.println("开始转换pdf->swf: "+inputFile);
			Process pro =Runtime.getRuntime().exec(command);
			BufferedReader bufferedReader = new BufferedReader(  
                    new InputStreamReader(pro.getInputStream()));  
            while (bufferedReader.readLine() != null) {  
  
            }  
            pro.waitFor();		
			System.out.println("已转换成swf: "+inputFile);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("转换文档为swf文件失败！");
		}
		
	}

	public void convert2SWF(String inputFile) {
		String swfFile = FileTool.getFilePrefix(inputFile)+".swf";
		convert2SWF(inputFile,swfFile);
	}
	
	public static void main(String[] args) {
		String path = "E:\\系自主创新\\研究生论文\\实验\\tika测试\\中文文本自动分类算法研究.pdf";
		SWFToolsSWFConverter swfConverter = new SWFToolsSWFConverter();
		swfConverter.convert2SWF(path);
	}

}
