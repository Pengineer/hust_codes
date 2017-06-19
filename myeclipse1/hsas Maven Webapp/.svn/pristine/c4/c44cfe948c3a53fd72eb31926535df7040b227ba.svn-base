package csdc.tool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取properties配置文件
 * @author fengcl
 *
 */
public class PropertiesTool {
	private Properties prop = null;
	
	/**
	 * 构造器
	 * @param filePath	配置文件的路径
	 */
	public PropertiesTool(String filePath) {
        prop = new Properties();   
        InputStream in = PropertiesTool.class.getClassLoader().getResourceAsStream(filePath);  
        try {   
            prop.load(in);   
        } catch (IOException e) {   
            e.printStackTrace();   
        }finally{
        	try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }   
	
	/**
	 * 根据key获取value
	 * @param key	
	 * @return	value
	 */
	public String getValue(String key){
		if (key == null || key.equals("") || key.equals("null")) {
            return "";
        }
        String value = "";
        try {
        	value = prop.getProperty(key).trim();   
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
    
    public static void main(String[] args) {
    	PropertiesTool test = new PropertiesTool("dbconfig.properties");
    	System.out.println(test.getValue("database.type"));
	}
}
