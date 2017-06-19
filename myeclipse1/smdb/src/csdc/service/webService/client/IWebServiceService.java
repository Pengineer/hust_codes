package csdc.service.webService.client;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import csdc.service.IBaseService;

/**
 * webservice除去接口配置之外的业务处理
 * @author suwb
 *
 */
public interface IWebServiceService extends IBaseService {

	/**
	 * 附件下载：多线程
	 * @param projectType :项目类型
	 * @param methodName ：数据类型
	 */
	public void download(String projectType, String methodName);
	
	/**
	 * 数据解析入库
	 * @param methodName
	 * @param projectType
	 * @param year
	 * @param i
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParseException 
	 * @throws DOMException 
	 * @throws XPathExpressionException 
	 * @throws ParserConfigurationException 
	 */
	public void importer(String methodName, String projectType, int year, int i) throws SAXException, IOException, DOMException, ParseException, XPathExpressionException, ParserConfigurationException;
	
	/**
	 * 数据获取并本地备份
	 * @param projectType
	 * @param year
	 * @param count
	 * @param methodName
	 * @throws IOException
	 * @throws ParserConfigurationException 
	 * @throws SAXException 
	 */
	public void obtain(String projectType, int year, int count, String methodName) throws IOException, ParserConfigurationException, SAXException;
	
	/**
	 * 入库前的xml检验
	 * @param methodName
	 * @param projectType
	 * @param year
	 * @param i
	 * @return 所有数据中存在的节点集合
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public Map xmlValidate(String methodName, String projectType, int year, Integer i) throws ParserConfigurationException, SAXException, IOException;

	/**
	 * 获取当前正在进行的任务数（如：入库条数、数据同步文件数等）
	 * @return
	 */
	public Map getCurrentTask();

	/**
	 * 判断文件是否存在，存在则返回文件个数
	 * @param projectType
	 * @param methodName
	 * @param year
	 * @return
	 */
	public int isFileExist(String projectType, String methodName, int year);
}
