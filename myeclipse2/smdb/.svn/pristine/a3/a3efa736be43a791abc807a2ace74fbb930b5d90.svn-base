package csdc.tool.crawler.validator;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import csdc.tool.StringTool;
import csdc.tool.crawler.NsfcCrawler;

/**
 * 国家自然科学基金项目爬虫页面校验器
 * @author Isun
 *
 */
public class NsfcPageValidator extends PageValidator {
	
	private static HttpHost target = new HttpHost("isisn.nsfc.gov.cn", 80, "http");

	private String headRowReg = "<tr  class=head>\\s*" + "<td align=center width ='10%'>项目批准号/<br>\\s*申请代码1 </td>\\s*" + "<td align=center width ='38%'>项目名称</td>\\s*" + "<td align=center width ='10%'>项目负责人</td>\\s*" + "<td align=center width ='20%' >依托单位</td>\\s*" + "<td align=center width ='6%' >批准<br>金额</td>\\s*" + "<td align=center width ='16%' >项目起止年月</td>\\s*" + "</tr>";

	@Override
	protected boolean validInner() throws Exception {
		/*List<NameValuePair> params = new LinkedList<NameValuePair>();
		params.add(new BasicNameValuePair("page", "1"));
		params.add(new BasicNameValuePair("searchOrg", "学"));
		params.add(new BasicNameValuePair("year", "2012"));
		params.add(new BasicNameValuePair("SortBy1", "3"));
		params.add(new BasicNameValuePair("SortBy2", "0"));
		params.add(new BasicNameValuePair("Order", "desc"));
		String paramString = URLEncodedUtils.format(params, "gbk");

		HttpGet request = new HttpGet("/egrantindex/funcindex/prjsearch-list?" + paramString);

		String html = null;
		HttpResponse response = null;
		HttpEntity entity = null;
		try {
			response = httpClient.execute(target, request);
			entity = response.getEntity();
			html = EntityUtils.toString(entity, "gbk");
		} finally {
			EntityUtils.consume(entity);
		}

		// 标题不能发生变化
		String[] headRow = StringTool.regGroup(html, headRowReg);
		if (headRow == null) {
			return false;
		}

		// 数据区能按预期的格式取到数据
		List<String[]> rows = StringTool.regGroupAll(html, NsfcCrawler.contentExtractReg);
		return !rows.isEmpty();*/
		return true;
	}

}