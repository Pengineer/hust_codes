package org.csdc.bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.csdc.dao.IBaseDao;
import org.csdc.tool.FileTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 应用程序类
 * 功能买描述：
 *   程序运行时的全局信息
 * @author jintf
 * @date 2014-6-15
 */
@Service
public class Application {
	@Autowired
	private IBaseDao dao;
	
	private Properties prop;
	
	public Application() throws Exception{
		loadAppProperties();
		initFileSystem();				
	}
	
	public void loadAppProperties(){
		try {
		prop = new Properties();//属性集合对象 
		
	    InputStream path =Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");//获取路径并转换成流
	    	prop.load(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 获取配置文件的信息
	 * @param key 配置项的键
	 * @return 配置项的值
	 */
	public String getParameter(String key){
		return prop.getProperty(key);
	}
	
	
	
	public void setParameter(String key,String value){
		prop.setProperty(key, value);
		try {
			prop.store(new FileOutputStream("application.properties"), "dmss");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	/**
	 * 返回文档库地址
	 * @return
	 */
	public String getReposPath(){
		return getParameter("ROOT")+File.separator+"repos";
	}
	
	public String getRootPath(){
		return getParameter("ROOT");
	}
	
	
	public String getFetchPath(){
		return getParameter("ROOT")+"/hdfs/fetch";
	}
	/**
	 * 返回临时上传位置
	 * @return
	 */
	public String getTempPath(){
		return getParameter("ROOT")+File.separator+"temp";
	}
	

	public boolean isHadoopOn(){
		return getParameter("HADOOP.ON").equals("1");
	}
	
	/**
	 * 获取预览文档库路径
	 * @return
	 */
	public String getPreviewPath(){
		return getParameter("ROOT")+File.separator+"preview";
	}
	
	/**
	 * 获取SOLR服务器地址
	 * @return
	 */
	public String getSolrServerUrl(){
		return getParameter("SOLR_SERVER");
	}
	
	/**
	 * 应用启动时初始化文件系统目录
	 */
	private void initFileSystem(){
		String rootPathString = getParameter("ROOT");
		//清空临时文件
		FileTool.rm_fr(rootPathString+"/temp");
		//创建文件夹
		FileTool.mkdir(rootPathString);
		FileTool.mkdir(rootPathString+"/temp");
		FileTool.mkdir(rootPathString+"/preview");
		FileTool.mkdir(rootPathString+"/hdfs");
		FileTool.mkdir(rootPathString+"/hdfs/fetch");
	}
	
	
}

