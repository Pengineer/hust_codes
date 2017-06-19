package org.csdc.controller.fs;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.lucene.analysis.charfilter.BaseCharFilter;
import org.csdc.bean.JsonData;
import org.csdc.bean.SearchForm;
import org.csdc.controller.BaseController;
import org.csdc.model.Document;
import org.csdc.service.imp.SearchService;
import org.csdc.tool.FileTool;
import org.csdc.tool.HttpTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
/**
 * 文档检索控制器
 * @author jintf
 * @date 2014-6-15
 */
@Controller("fsDocumentController")
@RequestMapping ("/fs/document")
public class DocumentController extends BaseController {
	
	/**
	 * 全文检索页面
	 * @return
	 */
	public  ModelAndView toSearchPage(){
		return new ModelAndView("fs/search.jsp");
	}
	
	/**
	 * 全文检索
	 * @param form 全文检索表单对象
	 * @return
	 */
	@RequestMapping(value="/fullQuery", produces="application/json")
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_FS_DOCUMENT_FULLQUERY')") 
	public Object fullQuery(SearchForm form){
		Map params = new HashMap();
		params.put("indent", true);
		params.put("wt", "json"); //设置数据格式为json
		params.put("q", form.getText()); //设置查询关键字
		params.put("start", form.getStart()); //设置返回数据起始记录位置
		params.put("rows", form.getPageSize()); //设置返回记录条数
		params.put("hl", true); //打开高亮
		params.put("hl.fl", "title,content"); //指定全文检索字段高亮为标题和文档内容
		params.put("hl.simple.post", "</em>"); //高亮后置标记</em>
		params.put("hl.simple.pre", "<em>"); //高亮前置标记<em>
		params.put("df", "text"); //指定全文检索字段为text
		String url = application.getSolrServerUrl()+"/select"; //调用select接口
		JSONObject jsonObject = HttpTool.getJson(url,params);
		return jsonObject;
	}
	
	
}
