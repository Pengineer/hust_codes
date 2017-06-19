package org.csdc.tool;

import org.junit.Test;

public class PDFConverterTest {
	
	public void testPic() throws Exception{
		String jpg = "E:\\系自主创新\\研究生论文\\实验\\文档预览\\文件格式\\Desert.jpg";
		String png = "E:\\系自主创新\\研究生论文\\实验\\文档预览\\文件格式\\20130808185307.png";
		String jpeg = "E:\\系自主创新\\研究生论文\\实验\\文档预览\\文件格式\\oo.jpeg";
		String bmp = "E:\\系自主创新\\研究生论文\\实验\\文档预览\\文件格式\\my.bmp";
		PDFConverter converter = PDFConverter.getInstance();
		PDFConverter.startService();
		converter.convert2PDF(jpg);
		converter.convert2PDF(jpeg);
		converter.convert2PDF(bmp);
		converter.convert2PDF(png);
		PDFConverter.stopService();
	}
	
	@Test
	public void testDoc() throws Exception{
		String docx = "E:\\系自主创新\\研究生论文\\实验\\文档预览\\文件格式\\Lucene技术调研.docx";
		PDFConverter converter = PDFConverter.getInstance();
		PDFConverter.startService();
		converter.convert2PDF(docx);
		PDFConverter.stopService();
	}
	
	@Test
	public void testPdf() throws Exception{
		/*String pdf = "E:\\系自主创新\\研究生论文\\实验\\文档预览\\文件格式\\Lucene技术调研.docx";
		PDFConverter converter = PDFConverter.getInstance();
		PDFConverter.startService();
		converter.convert2PDF(docx);
		PDFConverter.stopService();*/
	}
}
