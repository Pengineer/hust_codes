package csdc.tool;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

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
}
