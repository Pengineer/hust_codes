package csdc.tool.crawler;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import csdc.bean.Nssf;
import csdc.tool.StringTool;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.crawler.validator.NssfPageValidator;
import csdc.tool.crawler.validator.PageValidator;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 国家哲学社会科学基金项目爬虫
 * @link http://gp.people.com.cn/yangshuo/skygb/sk/index.php
 * @author xuhan
 *
 */
@Component
@Scope("prototype")
public class NssfCrawler extends Crawler {
	
	private static HttpHost target = new HttpHost("gp.people.com.cn", 80, "http");
	
	@Autowired
	private Tool tool;
	
	public final static String contentExtractReg = 
			"<tr><td width=\"\\d*\"><span title=\"[\\s\\S]*?\">([\\s\\S]*?)</span></td>" +
			"<td width=\"\\d*\"><span title=\"[\\s\\S]*?\">([\\s\\S]*?)</span></td>" +
			"<td width=\"\\d*\"><span title=\"[\\s\\S]*?\">([\\s\\S]*?)</span></td>" +
			"<td width=\"\\d*\"><span title=\"[\\s\\S]*?\">([\\s\\S]*?)</span></td>" +
			"<td width=\"\\d*\"><span title=\"[\\s\\S]*?\">([\\s\\S]*?)</span></td>" +
			"<td width=\"\\d*\"><span title=\"[\\s\\S]*?\">([\\s\\S]*?)</span></td>" +
			"<td width=\"\\d*\"><span title=\"[\\s\\S]*?\">([\\s\\S]*?)</span></td>" +
			"<td width=\"\\d*\"><span title=\"[\\s\\S]*?\">([\\s\\S]*?)</span></td>" +
			"<td width=\"\\d*\"><span title=\"[\\s\\S]*?\">([\\s\\S]*?)</span></td>" +
			"<td width=\"\\d*\"><span title=\"[\\s\\S]*?\">([\\s\\S]*?)</span></td>" +
			"<td width=\"\\d*\"><span title=\"[\\s\\S]*?\">([\\s\\S]*?)</span></td>" +
			"<td width=\"\\d*\" class=\"t03\"><span title=\"[\\s\\S]*?\">([\\s\\S]*?)</span></td>" +
			"<td width=\"\\d*\" class=\"t03\"><span title=\"[\\s\\S]*?\">([\\s\\S]*?)</span></td>" +
			"<td width=\"\\d*\" class=\"t03\"><span title=\"[\\s\\S]*?\">([\\s\\S]*?)</span></td>" +
			"<td width=\"\\d*\" class=\"t03\"><span title=\"[\\s\\S]*?\">([\\s\\S]*?)</span></td>" +
			"<td width=\"\\d*\" class=\"t03\"><span title=\"[\\s\\S]*?\">([\\s\\S]*?)</span></td>" +
			"<td width=\"\\d*\" class=\"t03\"><span title=\"[\\s\\S]*?\">([\\s\\S]*?)</span></td>" +
			"<td width=\"\\d*\" class=\"t03\"><span title=\"[\\s\\S]*?\">([\\s\\S]*?)</span></td>" +
			"<td width=\"\\d*\" class=\"t03\"><span title=\"[\\s\\S]*?\">([\\s\\S]*?)</span></td>" +
			"<td class=\"t03\"><span title=\"[\\s\\S]*?\">([\\s\\S]*?)</span></td></tr>";
	
	public void work() throws Exception {
		Map arguments = JSONObject.fromObject(crawlTask.getArguments());
//		Integer startPage = Integer.parseInt(arguments.get("startPage").toString());
		Integer endPage = Integer.parseInt(arguments.get("endPage").toString());
		
		String pageNumber = crawlTask.getProgress(); //下次待爬取的页码
		
		while (true) {
			HttpGet request = new HttpGet("/yangshuo/skygb/sk/index.php?p=" + pageNumber);
			
			String html = null;
			HttpResponse response = null;
			HttpEntity entity = null;
			try {
				System.out.println("start: " + request.getURI());
				response = httpClient.execute(target, request);
				System.out.println("  end: " + request.getURI());
				entity = response.getEntity();
				html = EntityUtils.toString(entity, Consts.UTF_8);
			} finally {
				EntityUtils.consume(entity);
			}
			
			List<String[]> rows = StringTool.regGroupAll(html, contentExtractReg);
			for (String[] row : rows) {
				Nssf project = null;
				try {
					project = (Nssf) hibernateBaseDao.queryUnique("select project from Nssf project where project.number = ? and project.singleSubject is null and project.name = ?", row[1], row[4]);
				} catch (Exception e) {
					continue;
				}
				if (project == null) {
					project = new Nssf();
				} else {
					if (!project.getApplicant().equals(row[6])) {
						project.setApplicantNew(null);
					}
				}
				beanFieldUtils.setField(project, "number", row[1], BuiltinMergeStrategies.REPLACE, true);
				beanFieldUtils.setField(project, "type", row[2], BuiltinMergeStrategies.REPLACE, true);
				beanFieldUtils.setField(project, "disciplineType", row[3], BuiltinMergeStrategies.REPLACE, true);
				beanFieldUtils.setField(project, "name", row[4], BuiltinMergeStrategies.REPLACE, true);
				beanFieldUtils.setField(project, "startDate", tool.getDate(row[5]), BuiltinMergeStrategies.REPLACE, true);
				beanFieldUtils.setField(project, "applicant", row[6], BuiltinMergeStrategies.REPLACE, true);
				beanFieldUtils.setField(project, "specialityTitle",  row[7], BuiltinMergeStrategies.REPLACE, true);
				beanFieldUtils.setField(project, "unit", row[8], BuiltinMergeStrategies.REPLACE, true);
				beanFieldUtils.setField(project, "unitType", row[9], BuiltinMergeStrategies.REPLACE, true);
				beanFieldUtils.setField(project, "province", row[10], BuiltinMergeStrategies.REPLACE, true);
				beanFieldUtils.setField(project, "belongSystem", row[11], BuiltinMergeStrategies.REPLACE, true);
				beanFieldUtils.setField(project, "productName", row[12], BuiltinMergeStrategies.REPLACE, true);
				beanFieldUtils.setField(project, "productType", row[13], BuiltinMergeStrategies.REPLACE, true);
				beanFieldUtils.setField(project, "productLevel", row[14], BuiltinMergeStrategies.REPLACE, true);
				beanFieldUtils.setField(project, "endDate", tool.getDate(row[15]), BuiltinMergeStrategies.REPLACE, true);
				beanFieldUtils.setField(project, "certificate", row[16], BuiltinMergeStrategies.REPLACE, true);
				beanFieldUtils.setField(project, "press", row[17], BuiltinMergeStrategies.REPLACE, true);
				beanFieldUtils.setField(project, "publishDate", tool.getDate(row[18]), BuiltinMergeStrategies.REPLACE, true);
				beanFieldUtils.setField(project, "author", row[19], BuiltinMergeStrategies.REPLACE, true);
				beanFieldUtils.setField(project, "prizeObtained", row[20], BuiltinMergeStrategies.REPLACE, true);
				beanFieldUtils.setField(project, "importDate", new Date(), BuiltinMergeStrategies.SUPPLY, true);
				if (project.getName() != null && project.getNumber() != null) {
					hibernateBaseDao.addOrModify(project);
				}
			}
			
			Integer curPage = Integer.parseInt(StringTool.regFind(html, "(\\d+)/\\d+ 页"));
			if (curPage >= endPage) {
				crawlTask.setFinishTime(new Date());
				hibernateBaseDao.modify(crawlTask);
				break;
			} else if (rows.size() != 20) {
				crawlTask.addLog("第" + curPage + "页取出" + rows.size() + "条数据，预期取出20条");
				hibernateBaseDao.modify(crawlTask);
			}

			pageNumber = curPage + 1 + "";
			crawlTask.setProgress(pageNumber);
			hibernateBaseDao.modify(crawlTask);
		}
	}

	@Override
	Class<? extends PageValidator> getPageValidatorClass() {
		return NssfPageValidator.class;
	}
	
}
