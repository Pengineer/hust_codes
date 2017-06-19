package org.csdc.tool;

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
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 * Tika文本抽取工具类
 * @author jintf
 * @date 2014-6-16
 */
public class TikaTool {
	
	/**
	 * 把文档转换为txt
	 * @param f
	 * @return
	 */
	public static  String fileToTxt(File f) {  
		StringBuffer metaString= new StringBuffer("");
        Parser parser = new AutoDetectParser();//自动检测文档类型，自动创建相应的解析器  
        InputStream is = null;  
        try {  
            Metadata metadata = new Metadata();  
            metadata.set(Metadata.RESOURCE_NAME_KEY, f.getName());  
            is = new FileInputStream(f);  
            ContentHandler handler = new BodyContentHandler();  
            ParseContext context = new ParseContext();  
            context.set(Parser.class,parser);  
            parser.parse(is,handler, metadata,context);  
            for(String name:metadata.names()) {  
                metaString.append(metadata.get(name)+",");
            }  
            return metaString.toString()+handler.toString();  
        } catch (FileNotFoundException e) {
        	System.out.println(f.getAbsolutePath());
            e.printStackTrace();  
        } catch (IOException e) {
        	System.out.println(f.getAbsolutePath());
            e.printStackTrace();  
        } catch (SAXException e) {  
        	System.out.println(f.getAbsolutePath());
            e.printStackTrace();  
        } catch (TikaException e) {  
        	System.out.println(f.getAbsolutePath());
            e.printStackTrace();  
        } finally {  
            try {  
                if(is!=null) is.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return null;  
    }  
}
