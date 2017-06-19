package csdc.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * XML工具包
 * @author 雷达
 */
public class XMLTool {
	
	/**
	 * 获取一个xml字串根节点下的第一层节点值
	 * @param XMLString xml字串
	 * @param nodeName 根节点下的第一层节点名
	 * @return 节点值
	 */
	public static String getElementText(String XMLString, String nodeName) {
		try {
			Document document = DocumentHelper.parseText(XMLString);
			Element rootElm = document.getRootElement();
			return rootElm.elementText(nodeName);
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 生成本地xml文件
	 * @param document
	 */
	public static void generateXmlFile(Document document) {
		XMLWriter out = null;
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;
		try {
			File xmlFile = new File("F:\\clearblack\\exchangeData.xml");//输出xml的路径
			if (!xmlFile.exists()){
				xmlFile.createNewFile();
			}
			fos = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(fos, "UTF-8");//指定编码，防止写中文乱码
			bw = new BufferedWriter(osw);
			OutputFormat format = OutputFormat.createPrettyPrint();//对xml输出格式化
			format.setEncoding("UTF-8");
			out = new XMLWriter(bw, format);
			out.write(document);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (bw != null) {
					bw.close();
				}
				if (osw != null) {
					osw.close();
				}
				if (fos != null) {
					fos.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}	
}
