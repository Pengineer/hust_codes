package csdc.tool.execution.fix;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Expert;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.execution.Execution;

/**
 * 修复专家外语语种
 * @author xuhan
 *
 */
@Component
public class FixExpertLanguage extends Execution {
	
	@Autowired
	private IHibernateBaseDao dao;
	
	private Map<String, String> languageTrans = new HashMap<String, String>();
	
	{
		languageTrans.put("0571", "");
		languageTrans.put("0592", "");
		languageTrans.put("0898", "");
		languageTrans.put("2189259", "");
		languageTrans.put("66892661", "");
		languageTrans.put("88713626", "");
		languageTrans.put("null", "");
		languageTrans.put("一般阅读", "");
		languageTrans.put("不详", "");
		languageTrans.put("中文", "");
		languageTrans.put("乌克兰语", "乌克兰语");
		languageTrans.put("乌尔都语", "乌尔都语");
		languageTrans.put("俄", "俄语");
		languageTrans.put("俄文", "俄语");
		languageTrans.put("俄英", "俄语; 英语");
		languageTrans.put("俄语", "俄语");
		languageTrans.put("俄语英语", "俄语; 英语");
		languageTrans.put("保语", "保加利亚语");
		languageTrans.put("印地语", "印地语");
		languageTrans.put("印尼", "印尼语");
		languageTrans.put("发", "");
		languageTrans.put("古希伯来语", "希伯来语");
		languageTrans.put("古希腊语", "现代希腊语");
		languageTrans.put("古梵文", "梵语");
		languageTrans.put("哈萨克语", "哈萨克语");
		languageTrans.put("四会", "");
		languageTrans.put("土耳其语", "土耳其语");
		languageTrans.put("外语", "");
		languageTrans.put("外语语种", "");
		languageTrans.put("好", "");
		languageTrans.put("希伯来语", "希伯来语");
		languageTrans.put("希腊语", "现代希腊语");
		languageTrans.put("徳语", "德语");
		languageTrans.put("德", "德语");
		languageTrans.put("德国", "德语");
		languageTrans.put("德日", "德语; 日语");
		languageTrans.put("德語", "德语");
		languageTrans.put("德语", "德语");
		languageTrans.put("意", "意大利语");
		languageTrans.put("意大利语", "意大利语");
		languageTrans.put("意大利语英语拉丁文俄语", "意大利语; 英语; 拉丁语; 俄语");
		languageTrans.put("拉丁语", "拉丁语");
		languageTrans.put("政管", "");
		languageTrans.put("无", "");
		languageTrans.put("日", "日语");
		languageTrans.put("日本语", "日语");
		languageTrans.put("日等", "日语");
		languageTrans.put("日英", "日语; 英语");
		languageTrans.put("日語", "日语");
		languageTrans.put("日语", "日语");
		languageTrans.put("日语英语", "日语; 英语");
		languageTrans.put("日语韩语", "日语; 朝鲜语");
		languageTrans.put("朝语", "朝鲜语");
		languageTrans.put("朝鲜语", "朝鲜语");
		languageTrans.put("朝鲜语（韩国语）", "朝鲜语");
		languageTrans.put("梵文", "梵语");
		languageTrans.put("梵語", "梵语");
		languageTrans.put("梵语", "梵语");
		languageTrans.put("法", "法语");
		languageTrans.put("法语", "法语");
		languageTrans.put("法语古希", "法语; 现代希腊语");
		languageTrans.put("波兰语", "波兰语");
		languageTrans.put("波斯语", "波斯语");
		languageTrans.put("泰语", "泰语");
		languageTrans.put("泰语英语", "泰语; 英语");
		languageTrans.put("瑞典语", "瑞典语");
		languageTrans.put("缅甸语", "缅甸语");
		languageTrans.put("罗马尼亚语", "罗马尼亚语");
		languageTrans.put("老挝语", "老挝语");
		languageTrans.put("芬兰语", "芬兰语");
		languageTrans.put("英", "英语");
		languageTrans.put("英俄", "英语; 俄语");
		languageTrans.put("英国", "英语");
		languageTrans.put("英德古埃", "英语; 德语");
		languageTrans.put("英文", "英语");
		languageTrans.put("英日", "英语; 日语");
		languageTrans.put("英日法", "英语; 日语; 法语");
		languageTrans.put("英法德", "英语; 法语; 德语");
		languageTrans.put("英語", "英语");
		languageTrans.put("英语", "英语");
		languageTrans.put("英语国家六级", "英语");
		languageTrans.put("英语德育", "英语; 德语");
		languageTrans.put("英语德语", "英语; 德语");
		languageTrans.put("英语日语", "英语; 日语");
		languageTrans.put("英语法语", "英语; 法语");
		languageTrans.put("英语西班牙语", "英语; 西班牙语");
		languageTrans.put("英语韩语", "英语; 朝鲜语");
		languageTrans.put("葡萄牙语", "葡萄牙语");
		languageTrans.put("蒙古语", "蒙古语");
		languageTrans.put("西班牙", "西班牙语");
		languageTrans.put("西班牙语", "西班牙语");
		languageTrans.put("语", "");
		languageTrans.put("越南语", "越南语");
		languageTrans.put("阿拉伯语", "阿拉伯语");
		languageTrans.put("韩", "朝鲜语");
		languageTrans.put("韩国语", "朝鲜语");
		languageTrans.put("韩国语（朝鲜语）", "朝鲜语");
		languageTrans.put("韩语", "朝鲜语");
		languageTrans.put("韩语日语", "朝鲜语; 日语");
		languageTrans.put("马其顿语", "马其顿语");
		languageTrans.put("马来语", "马来语");
	}
	
	
	@Override
	public void work() throws Throwable {
		List<Expert> experts = dao.query("from Expert");
		for (Expert expert : experts) if (expert.getLanguage() != null) {
			
//			System.out.println(expert.getLanguage() + " -> " + newLanguage);
			expert.setLanguage(fixLanguage(expert.getLanguage()));
		}
		
	}
	
	public String fixLanguage(String origin) {
		String[] languages = origin.split("[\\s、;；,，\\.。\\\\/\\-－与和＼]+|(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

		Set<String> languageSet = new TreeSet<String>();
		for (String language : languages) if (!language.isEmpty()) {
			if (languageTrans.containsKey(language)) {
				languageSet.addAll(Arrays.asList(languageTrans.get(language).split("[;\\s]+")));
			} else {
				languageSet.add(language);
			}
		}
		
		String result = "";
		for (String language : languageSet) if (!language.isEmpty()) {
			if (result.length() > 0) {
				result += "; ";
			}
			result += language;
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(new FixExpertLanguage().fixLanguage("意大利语英语拉丁文俄语"));
	}
	
}
