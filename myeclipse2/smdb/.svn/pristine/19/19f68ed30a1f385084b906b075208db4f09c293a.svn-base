package csdc.tool.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import csdc.bean.Nsfc;
import csdc.tool.ApplicationContainer;
import csdc.tool.StringTool;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.captcha.CaptchaRecognizer;
import csdc.tool.crawler.validator.NsfcPageValidator;
import csdc.tool.crawler.validator.PageValidator;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 国家自科基金项目爬虫
 * 
 * @author xuhan
 *
 */
@Component
@Scope("prototype")
public class NsfcCrawler extends Crawler {
	
	private static HttpHost target = new HttpHost("isisn.nsfc.gov.cn", 80, "http");
	
	@Autowired
	private Tool tool;
	
	public final static String contentExtractReg = 
			"<row id=\"\">\\s*" +
			"<cell>.+?</cell>\\s*" +
			"<cell>.+?</cell>\\s*" +
			"<cell>.+?</cell>\\s*" +
			"<cell>.+?</cell>\\s*" +
			"<cell>.+?</cell>\\s*" +
			"<cell>.+?</cell>\\s*" +
			"<cell>.+?至.+?</cell>\\s*" +
			"</row>";
	
	/**
	 * code -> name
	 */
	private static Map<String, String> grantTypeMap = new HashMap<String, String>();
	
	private static Thread initGrantTypeMapThread = new Thread() {
		public void run() {
			try {
				initGrantTypeMap();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	};
	
	private static void initGrantTypeMap() throws ClientProtocolException, IOException, Throwable {
		HttpGet request = new HttpGet("/egrantindex/funcindex/prjsearch-list");
		
		String html = null;
		HttpResponse response = null;
		HttpEntity entity = null;
		try {
			System.out.println("start: " + request.getURI());
			response = httpClient.execute(target, request);
			System.out.println("  end: " + request.getURI());
			entity = response.getEntity();
			html = EntityUtils.toString(entity, Consts.UTF_8).trim();
		} finally {
			EntityUtils.consume(entity);
		}
		
		List<String> years = new ArrayList<String>();
		
		List<String[]> rows = StringTool.regGroupAll(html, "<option value=\"(\\d{4})\">[0-9]{4}</option>");
		for (String[] row : rows) {
			years.add(row[1]);
		}
		rows = StringTool.regGroupAll(html, "<option value=\"(\\d+)\">[^0-9]+?</option>");
		for (String[] row : rows) {
			grantTypeMap.put(row[1], row[0].replaceAll("<option value=\"(\\d+)\">", "").replaceAll("</option>", ""));
			JSONArray subJsonArray = NsfcTaskUpdater.getSub(row[1], "grant");
			for (int i = 0; i < subJsonArray.size(); i++) {
				String code = subJsonArray.getJSONObject(i).get("subGrantCode").toString();
				grantTypeMap.put(code, subJsonArray.getJSONObject(i).get("subGrantName").toString());
				JSONArray descriptionJsonArray = NsfcTaskUpdater.getSub(code, "sub");
				if (!descriptionJsonArray.isEmpty()) {
					for (int j = 0; j < descriptionJsonArray.size(); j++) {
						String desCode = descriptionJsonArray.getJSONObject(j).get("subGrantCode").toString();
						grantTypeMap.put(desCode, descriptionJsonArray.getJSONObject(j).get("subGrantName").toString());
					}
				}
			}
		}
	}
	
	public void work() throws Exception {
		synchronized (grantTypeMap) {
			if (grantTypeMap.isEmpty()) {
				grantTypeMap.put("#", "#");
				initGrantTypeMapThread.start();
			}
		}
		initGrantTypeMapThread.join();
		Map arguments = JSONObject.fromObject(crawlTask.getArguments());
		String year = arguments.get("year").toString();
		String grantTypeId = arguments.get("grantTypeId").toString();
		String subGrantTypeId = arguments.get("subGrantTypeId").toString();
		String grantDescription = arguments.get("grantDescription").toString();
		
		String html = null;
		while(true) {
			html = catchHtml(year, grantTypeId, subGrantTypeId, grantDescription);
			if (!html.contains("<!DOCTYPE html>")) {
				break;
			}
		}
		
		List<String[]> rows = StringTool.regGroupAll(html, contentExtractReg);
		for (String[] row : rows) {
			List<String[]> cells = StringTool.regGroupAll(row[0], "<cell>.+?</cell>");
			String number = cells.get(0)[0].replace("<cell>", "").replace("</cell>", "");
			Nsfc project = (Nsfc) hibernateBaseDao.queryUnique("select project from Nsfc project where project.number = ? ", number);
			if (project == null) {
				project = new Nsfc();
				project.setNumber(number);
			}
			String[] datesStrings = cells.get(6)[0].replace("<cell>", "").replace("</cell>", "").split("至");
			beanFieldUtils.setField(project, "applyNumber", cells.get(1)[0].replace("<cell>", "").replace("</cell>", ""), BuiltinMergeStrategies.REPLACE, true);
			beanFieldUtils.setField(project, "name", cells.get(2)[0].replace("<cell>", "").replace("</cell>", ""), BuiltinMergeStrategies.REPLACE, true);
			beanFieldUtils.setField(project, "applicant", cells.get(3)[0].replace("<cell>", "").replace("</cell>", ""), BuiltinMergeStrategies.REPLACE, true);
			beanFieldUtils.setField(project, "unit", cells.get(4)[0].replace("<cell>", "").replace("</cell>", ""), BuiltinMergeStrategies.REPLACE, true);
			beanFieldUtils.setField(project, "approvedFee", tool.getFee(cells.get(5)[0].replace("<cell>", "").replace("</cell>", "")), BuiltinMergeStrategies.REPLACE, true);
			beanFieldUtils.setField(project, "startDate",  tool.getDate(datesStrings[0]), BuiltinMergeStrategies.REPLACE, true);
			beanFieldUtils.setField(project, "endDate", tool.getDate(datesStrings[1]), BuiltinMergeStrategies.REPLACE, true);
			beanFieldUtils.setField(project, "grantType", grantTypeMap.get(grantTypeId), BuiltinMergeStrategies.REPLACE, true);
			beanFieldUtils.setField(project, "subGrantType", grantTypeMap.get(subGrantTypeId), BuiltinMergeStrategies.REPLACE, true);
			beanFieldUtils.setField(project, "grantDescription", grantTypeMap.get(grantDescription), BuiltinMergeStrategies.REPLACE, true);
			beanFieldUtils.setField(project, "year", Integer.parseInt(year), BuiltinMergeStrategies.REPLACE, true);
			beanFieldUtils.setField(project, "importDate", new Date(), BuiltinMergeStrategies.SUPPLY, true);
			hibernateBaseDao.addOrModify(project);
		}
		
		crawlTask.setFinishTime(new Date());
		hibernateBaseDao.modify(crawlTask);
	}

	/**
	 * 尝试进行一个nsfc抓取任务
	 * @param year 年份
	 * @param grantTypeId 资助类别
	 * @param subGrantTypeId 亚类说明
	 * @param grantDescription 附注说明
	 */
	public String catchHtml(String year, String grantTypeId, String subGrantTypeId, String grantDescription) throws Exception {
		String cookie = CaptchaRecognizer.checkCode();
		String checkCode = CaptchaRecognizer.recognize(ApplicationContainer.sc.getRealPath("/temp/" + "validatecode.jpg"));
		
		List<NameValuePair> params = new LinkedList<NameValuePair>();
		params.add(new BasicNameValuePair("_search", "false"));
		params.add(new BasicNameValuePair("nd", ""));
		params.add(new BasicNameValuePair("page", "1"));
		params.add(new BasicNameValuePair("rows", "36566"));
		params.add(new BasicNameValuePair("searchString", "resultDate^:prjNo:,ctitle:,psnName:,orgName:学,subjectCode:,f_subjectCode_hideId:,subjectCode_hideName:,keyWords:,checkcode:,grantCode:" 
		+ grantTypeId + ",subGrantCode:" + subGrantTypeId + ",helpGrantCode:" + grantDescription + ",year:" + year));
		params.add(new BasicNameValuePair("sidx", ""));
		params.add(new BasicNameValuePair("sord", "desc"));
		String paramString = URLEncodedUtils.format(params, Consts.UTF_8);

		HttpGet request = new HttpGet("/egrantindex/funcindex/prjsearch-list?flag=grid&checkcode=" + checkCode + "&" + paramString);
		
		String html = null;
		HttpResponse response = null;
		HttpEntity entity = null;
		try {
			request.addHeader("cookie", cookie);
			response = httpClient.execute(target, request);
			entity = response.getEntity();
			html = EntityUtils.toString(entity, Consts.UTF_8).trim();
		} finally {
			EntityUtils.consume(entity);
		}
		return html;
	}
	
	@Override
	Class<? extends PageValidator> getPageValidatorClass() {
		return NsfcPageValidator.class;
	}
}


