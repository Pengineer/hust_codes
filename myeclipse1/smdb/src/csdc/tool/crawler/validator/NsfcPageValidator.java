package csdc.tool.crawler.validator;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import csdc.tool.ApplicationContainer;
import csdc.tool.StringTool;
import csdc.tool.captcha.CaptchaRecognizer;
import csdc.tool.crawler.NsfcCrawler;

/**
 * 国家自然科学基金项目爬虫页面校验器
 * 页面地址：http://isisn.nsfc.gov.cn/egrantindex/funcindex/prjsearch-list
 * @author Isun
 *
 */
public class NsfcPageValidator extends PageValidator {
	
	private static HttpHost target = new HttpHost("isisn.nsfc.gov.cn", 80, "http");

//	private String headRowReg = "<tr  class=head>\\s*" + "<td align=center width ='10%'>项目批准号/<br>\\s*申请代码1 </td>\\s*"
//	+ "<td align=center width ='38%'>项目名称</td>\\s*" + "<td align=center width ='10%'>项目负责人</td>\\s*"
//	+ "<td align=center width ='20%' >依托单位</td>\\s*" + "<td align=center width ='6%' >批准<br>金额</td>\\s*"
//	+ "<td align=center width ='16%' >项目起止年月</td>\\s*" + "</tr>";

	private final String headRegex = "new ColumnAttr\\(\".*?\",";
	
	@Override
	protected boolean validInner() throws Exception {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
//		String year = sdf.format(new Date());
		String year = "2014";
		
		//标题不能发生变化
		String htmlHead;
		while(true) {
			htmlHead = catchHtmlHead(year);
			if (htmlHead.length()>0) {
					break;
			}
		}
		List<String[]> headRows = StringTool.regGroupAll(htmlHead, headRegex);
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<headRows.size(); i++) {
			String[] headRow = headRows.get(i);
			sb.append(headRow[0].split("\"")[1].toString());
		}
		String currentTitle = sb.toString().trim();
		if(!currentTitle.equals("项目批准号申请代码1项目名称项目负责人依托单位批准金额项目起止年月")){
			return false;
		}

		//数据区能按预期的格式取到数据
		String htmlBody;
		while(true) {
			htmlBody = catchHtmlBody(year);
			if (!htmlBody.contains("<!DOCTYPE html>") && htmlBody.length()>0) {
				break;
			}
		}		
		List<String[]> rows = StringTool.regGroupAll(htmlBody, NsfcCrawler.contentExtractReg);
		return !rows.isEmpty();
	}

	/**
	 * 尝试获取数据内容
	 * @param year 年份
	 */
	public String catchHtmlBody(String year) throws Exception {
		String cookie = CaptchaRecognizer.checkCode();
		String checkCode = CaptchaRecognizer.recognize(ApplicationContainer.sc.getRealPath("/temp/" + "validatecode.jpg"));
		
		List<NameValuePair> params = new LinkedList<NameValuePair>();
		params.add(new BasicNameValuePair("_search", "false"));
		params.add(new BasicNameValuePair("nd", ""));
		params.add(new BasicNameValuePair("page", "1"));
		params.add(new BasicNameValuePair("rows", "36566"));
		params.add(new BasicNameValuePair("searchString", "resultDate^:prjNo:,ctitle:,psnName:,orgName:学,subjectCode:," +
				"f_subjectCode_hideId:,subjectCode_hideName:,keyWords:,checkcode:,grantCode:,subGrantCode:,helpGrantCode:,year:" + year));
		params.add(new BasicNameValuePair("sidx", ""));
		params.add(new BasicNameValuePair("sord", "desc"));
		String paramString = URLEncodedUtils.format(params, Consts.UTF_8);

		HttpGet request = new HttpGet("/egrantindex/funcindex/prjsearch-list?flag=grid&checkcode=" + checkCode + "&" + paramString);
		
		String html = null;
		HttpResponse response = null;
		HttpEntity entity = null;
		try {
			request.addHeader("Cookie", cookie);
			response = httpClient.execute(target, request);
			entity = response.getEntity();
			html = EntityUtils.toString(entity, Consts.UTF_8).trim();
		} catch (Exception e) {
			html = "";
		}finally {
			EntityUtils.consume(entity);
		}
		return html;
	}

	/**
	 * 尝试获取数据标题
	 * @param year 年份
	 */
	public String catchHtmlHead(String year) throws Exception {
		String cookie = CaptchaRecognizer.checkCode();
		String checkCode = CaptchaRecognizer.recognize(ApplicationContainer.sc.getRealPath("/temp/" + "validatecode.jpg"));
		List<NameValuePair> params = new LinkedList<NameValuePair>();
		params.add(new BasicNameValuePair("resultDate", "prjNo:,ctitle:,psnName:,orgName:学,subjectCode:,f_subjectCode_hideId:,subjectCode_hideName:,keyWords:," +
				"checkcode:" + checkCode + ",grantCode:,subGrantCode:,helpGrantCode:," + "year:" + year));
		params.add(new BasicNameValuePair("checkCode", checkCode));
		String paramString = URLEncodedUtils.format(params, Consts.UTF_8);

		HttpGet request3 = new HttpGet("/egrantindex/funcindex/prjsearch-list?checkcode=" + checkCode + "&" + paramString);
			
		String html = null;
		HttpResponse response3 = null;
		HttpEntity entity = null;
		try {
			request3.setHeader("Cookie", cookie);
			response3 = httpClient.execute(target, request3);
			entity = response3.getEntity();
			html = EntityUtils.toString(entity, Consts.UTF_8).trim();
		} catch (Exception e) {
			html = "";
		}finally {
			EntityUtils.consume(entity);
		}
		return html;
	}
}