package csdc.tool.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

/**
 * 中文分词器
 * @author fengcl
 *
 */
public class HotspotAnalyzer {

	/**
	 * 获取Top K 分词结果
	 * @param text		待分词的文本
	 * @param k			前K条记录
	 * @param useSmart	是否开启智能模式，不开启就按最小词义分
	 * @return
	 */
	public static List<Entry<String, Integer>> getTopKResult(String text, int k, boolean useSmart){
		Map<String, Integer> wordsFreq = null;
		try {
			wordsFreq = getWordsFreq(text, useSmart);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 按照词频进行排序
		List<Entry<String, Integer>> wordsFreqList = new ArrayList<Entry<String, Integer>>(wordsFreq.entrySet());
		Collections.sort(wordsFreqList, new Comparator<Entry<String, Integer>>() {
			public int compare(Entry<String, Integer> entry1, Entry<String, Integer> entry2) {
				return entry2.getValue() - entry1.getValue();
			}
		});
		if (wordsFreqList.size() > k) {
			return wordsFreqList.subList(0, k);
		}else {
			return wordsFreqList;
		}
	}
	
	/**
	 * 对传入的文本进行分词，并统计好每个词的频率
	 * @param text		待分词的文本
	 * @param useSmart	是否开启智能模式，不开启就按最小词义分
	 * @return
	 * @throws IOException
	 */
	public static Map<String, Integer> getWordsFreq(String text, boolean useSmart) throws IOException {
		System.out.println(text);
		// 词频记录，将分词结果和出现次数放到一个map结构中，map的value对应了词的出现次数。
		Map<String, Integer> wordsFreq = new HashMap<String, Integer>();
		// IKSegmenter是分词的主要类
		IKSegmenter ikSegmenter = new IKSegmenter(new StringReader(text), useSmart);
		// Lexeme是分词的结果类，getLexemeText()方法就能取出相关的分词结果
		Lexeme lexeme = null;
		// 统计词频
		while ((lexeme = ikSegmenter.next()) != null) {
			if (lexeme.getLexemeText().length() > 4) {
				if (wordsFreq.containsKey(lexeme.getLexemeText())) {
					wordsFreq.put(lexeme.getLexemeText(), wordsFreq.get(lexeme.getLexemeText()) + 1);
				} else {
					wordsFreq.put(lexeme.getLexemeText(), 1);
				}
			}
		}
		return wordsFreq;
	}

	/**
	 * 根据关键词 keyword 获取对应的项目集合
	 * @param keyword
	 * @param projects
	 * @return
	 */
	public static List<Object[]> getProjectsByKeyWord(String keyword, List<Object[]> projects){
		List<Object[]> results = new ArrayList<Object[]>();
		for (Object[] p : projects) {
			if (p[1] != null && p[1].toString().contains(keyword)) {
				results.add(p);
			}
		}
		return results;
	}
	
	/**
	 * 对分词后的结果按照关键词出现次数进行排序
	 * 这里注意一下，我只记录两个字及两个字以上的分词结果；
	 * @param wordsFreq
	 * @param topWordsCount
	 */
	public static void sortResult(Map<String, Integer> wordsFreq, int topWordsCount) {
		System.out.println("排序前：-----------------------------------");
		for (Entry<String, Integer> entry : wordsFreq.entrySet()) {
			System.out.println("关键词：" + entry.getKey() + " -> 次数：" + entry.getValue());
		}

		// 按照词频进行排序
		List<Entry<String, Integer>> wordsFreqList = new ArrayList<Entry<String, Integer>>(wordsFreq.entrySet());
		Collections.sort(wordsFreqList, new Comparator<Entry<String, Integer>>() {
			public int compare(Entry<String, Integer> entry1, Entry<String, Integer> entry2) {
				return entry2.getValue() - entry1.getValue();
			}
		});
		
		System.out.println("\n排序后：-----------------------------------");
		for (Entry<String, Integer> entry : wordsFreqList) {
			if (entry.getValue() > topWordsCount) {
				System.out.println("关键词：" + entry.getKey() + " -> 次数：" + entry.getValue());
			}
		}
	}
	
	
	public static void main(String[] args) {
		try {
			work();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void work() throws Exception {
		String text = "基于研究基于研究IKAnalyzer是一个开源的，基于java语言开发的轻量级的中文分词工具包。" +
				"从2006年12月推出1.0版开始，IKAnalyzer已经推出 了3个大版本。" +
				"最初，它是以开源项目 Lucene为应用主体的，结合词典分词和文法分析算法的中文分词组件。" +
				"新版本的IKAnalyzer3.0则发展为 面向Java的公用分词组件基于研究基于研究，独立于Lucene项目，" +
				"同时提供了对Lucene的默认优化实现。\n" + "\n" + "IKAnalyzer3.0特性:\n" + "\n" + 
				"采用了特有的“正向迭代最细粒度切分算法“，" +
				"具有60万字/秒的高速处理能力。\n" + "\n" + "采用了多子处理器分析模式，支持：英文字母（IP地址、Email、URL）、" +
				"数字（日期，常用中文数量词，罗马数字，科学计数法）基于研究基于研究，中文词汇（姓名、地名处理）等分词处理。\n" + 
				"\n" + "优化的词典存储，更小的内存占用。支持用户词典扩展定义\n" + "\n" + "针对Lucene全文检索优化的查询分析器IKQueryParser(作者吐血推荐)；" +
				"采用歧义分析算法优化查询关键字的搜索排列组合，能极大的提高Lucene检索的命中率。基于研究基于研究";
		Map<String, Integer> wordsFreq = getWordsFreq(text, true);
		int topWordsCount = 1;
		sortResult(wordsFreq, topWordsCount);
	}
}
