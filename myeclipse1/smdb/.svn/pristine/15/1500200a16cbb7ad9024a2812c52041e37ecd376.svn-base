package csdc.tool.dataMining.association;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.mahout.common.Pair;
import org.apache.mahout.fpm.pfpgrowth.convertors.ContextStatusUpdater;
import org.apache.mahout.fpm.pfpgrowth.convertors.SequenceFileOutputCollector;
import org.apache.mahout.fpm.pfpgrowth.convertors.string.StringOutputConverter;
import org.apache.mahout.fpm.pfpgrowth.convertors.string.TopKStringPatterns;
import org.apache.mahout.fpm.pfpgrowth.fpgrowth.FPGrowth;

import csdc.tool.ApplicationContainer;
import csdc.tool.FileTool;

/**
 * 关联规则挖掘阶段一：
 * 频繁模式分析器（基于FPGrowth算法）<br>
 * 从数据集合中找出所有的高频项目组(Frequent Itemsets)，即频繁模式。
 * @author fengcl
 *
 */
@SuppressWarnings("deprecation")
public class FPGrowthAnalyzer {
	
	private List<Object[]> dataItems;		// 待分析的数据项
	private long minSupport;				// minimum support of the transactions
	private int k;							// Number of top frequent patterns to keep，top-k
	private Collection returnableFeatures;	// 指定的待挖掘的频繁模式，默认为空即可
	
	/**
	 * 关联规则挖掘阶段一：
	 * 频繁模式分析器（基于FPGrowth算法）<br>
	 * FPGrowthAnalyzer 构造器
	 * @param dataItems	待分析的数据项
	 * @param minSupport	最小支持度
	 * @param k			每个属性的top K个频繁模式
	 * @param returnableFeatures	指定的要返回的频繁模式（可选）
	 */
	public FPGrowthAnalyzer(List<Object[]> dataItems, long minSupport, int k, Collection returnableFeatures){
		this.dataItems = dataItems;
		this.minSupport = minSupport;
		this.k = k;
		this.returnableFeatures = returnableFeatures;
	}
	
	/**
	 * 开始关联规则分析，并返回分析后的频繁模式
	 * @throws IOException
	 */
	public List<Pair<String, TopKStringPatterns>> work() throws IOException {
		
		if (dataItems == null) {
			return null;
		}
		//设置最小支持度默认值
		if (minSupport == 0) {
			minSupport = 5;
		}
		//设置待保留的top K默认值
		if (k == 0) {
			k = 10;
		}
		
		//采用FP-bonsai pruning而实现更快的频 繁模式增长（Frequent Pattern Growth）算法
		FPGrowth<String> fp = new FPGrowth<String>();
		// 所有事务集合
		Collection<Pair<List<String>, Long>> transactions = new ArrayList<Pair<List<String>, Long>>();
		// 构建transactions：pair事务集
		for (Object[] dataItem : dataItems) {
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < dataItem.length; i++) {
				if (dataItem[i] != null) {
					list.add(String.valueOf(dataItem[i]));
				}
			}
			transactions.add(new Pair<List<String>, Long>(list, 1L));
		}

		// 设置输出文件路径
		String tmpFilePath = ApplicationContainer.sc.getRealPath("/dataMining/resources/association/fpg_out.dat");
		File tmpFile = new File(tmpFilePath);
		String tmpDirPath = null;	//临时文件路径
		if (!tmpFile.exists()) {
			tmpDirPath = tmpFilePath.substring(0, tmpFilePath.indexOf("fpg_out.dat"));//从文件路径中截取文件夹得路径
			tmpFile = File.createTempFile("fpg_out", ".dat", new File(tmpDirPath));//在上一步的文件夹中创建新文件fpg_out.dat文件
		}
		Path path = new Path(tmpFile.getAbsolutePath());
		
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);

		//构造序列化文件写入器
		SequenceFile.Writer writer = new SequenceFile.Writer(fs, conf, path, Text.class, TopKStringPatterns.class);
		
		// 在给定的事务流和最小支持度下，为每个属性生成前K频繁模
		fp.generateTopKFrequentPatterns(
				transactions.iterator(), 	//待挖掘的事务迭代器
				fp.generateFList(transactions.iterator(), (int) minSupport), //
				minSupport, 	//最小支持度
				k, 				//各属性显示前K条(Number of top frequent patterns to keep)
				returnableFeatures, 
				new StringOutputConverter(new SequenceFileOutputCollector<Text, TopKStringPatterns>(writer)), 
				new ContextStatusUpdater(null));
		writer.close();

		//调用mahout读取接口方法，从频繁模式库中读取频繁模式集
		List<Pair<String, TopKStringPatterns>> frequentPatterns = FPGrowth.readFrequentPattern(conf, path);
		
		//如果在tmpDirPath目录新增了文件，删除tmpDirPath目录下所有文件
		if (tmpDirPath != null) {
			FileTool.delAllFile(tmpDirPath);
		}
		
		return frequentPatterns;
	}
	
	/**
	 * 获取所有频繁集
	 * @return
	 */
	public Map<Object, Long> readFrequency(){
		Map<Object, Long> frequency = new HashMap<Object, Long>();
		for (Object[] objs : dataItems) {
			for (Object item : objs) {
				Long value = frequency.get(item);
				frequency.put(item, (value == null) ? (long) 1 : value + 1);
			}
		}
		return frequency;
	}
}
