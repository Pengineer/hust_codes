package csdc.tool.crawler.validator;

import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import csdc.tool.StringTool;
import csdc.tool.crawler.NssfCrawler;

/**
 * 国家哲学社会科学基金项目爬虫页面校验器
 * 页面地址：http://gp.people.com.cn/yangshuo/skygb/sk/index.php
 * @author Isun
 *
 */
public class NssfPageValidator extends PageValidator {

	private static HttpHost target = new HttpHost("gp.people.com.cn", 80, "http");
	
	private String headRowReg = "<tr><th>项目批准号</th><th>项目类别 </th><th>学科分类 </th><th>项目名称 </th><th>立项时间 </th><th>项目负责人 </th><th>专业职务 </th><th>工作单位 </th><th>单位类别</th><th>所在省区市</th><th>所属系统</th><th>成果名称</th><th>成果形式</th><th>成果等级</th><th>结项时间</th><th>结项证书号</th><th>出版社</th><th>出版时间</th><th>作者</th><th>获奖情况</th></tr>";

	@Override
	protected boolean validInner() throws Exception {
		HttpGet request = new HttpGet("/yangshuo/skygb/sk/index.php");
		
		String html = null;
		HttpResponse response = null;
		HttpEntity entity = null;
		try {
			response = httpClient.execute(target, request);
			entity = response.getEntity();
			html = EntityUtils.toString(entity, Consts.UTF_8);
		} finally {
			EntityUtils.consume(entity);
		}

		//标题不能发生变化
		String[] headRow = StringTool.regGroup(html, headRowReg);
		if (headRow == null) {
			return false;
		}

		//数据区能按预期的格式取到数据
		List<String[]> rows = StringTool.regGroupAll(html, NssfCrawler.contentExtractReg);
		return !rows.isEmpty();
	}

}
