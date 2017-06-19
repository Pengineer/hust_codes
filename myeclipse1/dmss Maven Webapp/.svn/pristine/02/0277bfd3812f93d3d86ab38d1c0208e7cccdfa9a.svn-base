package org.cdsc.lucene;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.csdc.tool.TikaTool;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class TikaTest {
	public static void main(String[] args) throws IOException {
		String docPath = "E:\\系自主创新\\研究生论文\\数据\\2011年一般项目申请评审书-Z\\曾保根(91c29948-9826-4e00-932e-68fe3a468920).doc";
		String pdfPath = "E:\\系自主创新\\研究生论文\\实验\\tika测试\\基于碎片分词的未登录词识别方法.pdf";
		String htmlPath = "E:\\系自主创新\\研究生论文\\实验\\tika测试\\冰与火之歌第三季_百度百科.htm";
		String picPath = "E:\\系自主创新\\研究生论文\\实验\\tika测试\\示例图片_03.jpg";
		String mp3Path = "E:\\系自主创新\\研究生论文\\实验\\tika测试\\张宇-囚鸟.mp3";
		String excelPath = "E:\\系自主创新\\研究生论文\\实验\\tika测试\\独立学院代码（2010年02月05日）.xls";
		String pptPath = "E:\\系自主创新\\研究生论文\\实验\\tika测试\\金天凡_开题答辩.ppt";
		System.out.println(TikaTool.fileToTxt(new File(pptPath)).length());
	}
	
	
}
