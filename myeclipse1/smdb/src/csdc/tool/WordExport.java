package csdc.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionContext;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 指定模板导出word文档
 * @author suwb
 *
 */
/**
 * 1、将模板word文档（2003版）另存为xml文件
 * 2、用firstObject打开xml文件，使用freeMarker标记（${标记}）编辑其中待填入数据的地方
 * 3、将该模板置于指定目录下待程序读取
 * 4、确定待填入的数据和输出路径，调用程序执行
 */
public class WordExport {
	
	private Map<String,Object> dataMap;//待填充数据
	private String templateFile;//模板文件名
	private String fileName;//文件名
	
	public WordExport(Map<String,Object> map, String template, String file){
		dataMap = map;
		templateFile = template;
		fileName = file;
	}

	public void createWord() throws IOException{
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		configuration.setClassForTemplateLoading(getClass(), "/csdc/tool/templates");
		Template t = null;
		try {
			t = configuration.getTemplate(templateFile);//载入模版文件（把要导出的word模版另存为word xml就可以了）
			t.setEncoding("utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ActionContext ctx = ActionContext.getContext();
		HttpServletResponse response = (HttpServletResponse) ctx
				.get("com.opensymphony.xwork2.dispatcher.HttpServletResponse");
		response.setContentType("application/msword");
		try {
			response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
			
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			t.process(dataMap, out);
			out.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
}
