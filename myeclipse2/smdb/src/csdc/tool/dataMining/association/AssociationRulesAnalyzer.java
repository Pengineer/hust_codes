package csdc.tool.dataMining.association;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mahout.common.Pair;
import org.apache.mahout.fpm.pfpgrowth.convertors.string.TopKStringPatterns;

/**
 * 关联规则挖掘阶段二：
 * 关联规则分析器<br>
 * 由高频项目组（频繁模式）中产生关联规则(Association Rules)，
 * 即对频繁模式进行分析，挖掘出其中蕴藏的关联规则。
 * @author fengcl
 *
 */
@SuppressWarnings("deprecation")
public class AssociationRulesAnalyzer {

	/**
	 * 产生关联规则
	 * @param frequentPatterns	第一阶段分析出的频繁模式
	 * @param frequency			
	 * @param transactionCount	事务总数
	 * @param minSupport		最小支持度
	 * @param minConfidence		最小置信度
	 */
	public static List<String> generateRules(List<Pair<String, TopKStringPatterns>> frequentPatterns, Map<Object, Long> frequency, int transactionCount, double minSupport, double minConfidence){
		List<String> rules = new ArrayList<String>();
		DecimalFormat df = new DecimalFormat("#.####");
		//对所有频繁模式进行遍历(一个fps的例子：["男":[["41-45岁",10],["副教授",5]]])
		for (Pair<String, TopKStringPatterns> fps : frequentPatterns) {
    		String key = fps.getFirst();//key="男"
    		TopKStringPatterns value = fps.getSecond();//value=[["41-45岁",10],["副教授",5]]
    		System.out.println("key：" + key + " | value：" + value);
    		
    		//获取当前关键词key下所有模式
            List<Pair<List<String>, Long>> patterns = value.getPatterns();
            String firstItemName = null;	//该模式下第一项（首项）名称
            long firstItemOccurrence = -1;	//该模式下第一项（首项）出现次数
            int i = 0;						//循环计数
            //获取当前关键词下所有模式
            for(Pair<List<String>, Long> pair : patterns) {
                List<String> itemNames = pair.getFirst();	//获取当前模式下的item集合
                Long occurrence = pair.getSecond();			//获取当前模式出现次数
                if (i == 0) {								//遍历到当前模式下的第一个item
                	firstItemName = itemNames.get(0);
                    firstItemOccurrence = occurrence;
                } else {
                    double support = (double) occurrence / transactionCount;			//计算[当前项]的支持度
                    double confidence = (double) occurrence / firstItemOccurrence;		//计算 [首项=>当前项]的置信度
                    if (support > minSupport && confidence > minConfidence) {			//利用最小支持度和最小置信度进行过滤
                        List<String> itemsWithoutFirstItem = new ArrayList<String>();	//不包含首项的item集合
                        for(String itemName: itemNames) {
                            if (!itemName.equals(firstItemName)) {
                                itemsWithoutFirstItem.add(itemName);
                            }
                        }
                        String firstItem = firstItemName;
                        itemsWithoutFirstItem.remove(firstItemName);
                        System.out.printf("%s => %s: supp=%.3f, conf=%.3f",
                            firstItem,
                            itemsWithoutFirstItem,
                            support,
                            confidence);
                        String result = firstItem + " => " + itemsWithoutFirstItem + "：supp=" + df.format(support) + ", conf=" + df.format(confidence); 
 
                        if (itemNames.size() == 2) {
                            // we can easily compute the lift and the conviction for set of size 2, so do it
                            String otherItemId = null;
                            for(String itemId: itemNames) {
                                if (!itemId.equals(firstItemName)) {
                                    otherItemId = itemId;
                                    break;
                                }
                            }
                            long otherItemOccurrence = frequency.get(otherItemId);
                            double lift = (double)occurrence / (firstItemOccurrence * otherItemOccurrence);//lift = P(A,B)/(P(A)P(B))， Lift=1时表示A和B独立。这个数越大(>1)，越表明A和B存在于一个购物篮中不是偶然现象,有较强的关联度.
                            double conviction = (1.0 - (double)otherItemOccurrence / transactionCount) / (1.0 - confidence);//P(A)P(!B)/P(A,!B) （!B表示B没有发生） Conviction也是用来衡量A和B的独立性。从它和lift的关系（对B取反，代入Lift公式后求倒数）可以看出，这个值越大, A、B越关联。
                            System.out.printf(", lift=%.3f, conviction=%.3f",lift, conviction);
                            result += ", lift=" + df.format(lift) + ", conviction=" + df.format(conviction);
                        }
                        System.out.printf("\n");
                        rules.add(result);
                    }
                }
                i++;
            }
		}
		return rules;
	}
	
	/**
	 * 对频繁模式进行分析，获取关联规则
	 * @param frequentPatterns	频繁模式
	 * @return	Map ： String -> List<Object[]>
	 */
	public static Map<String, List<Object[]>> getAssociation(List<Pair<String, TopKStringPatterns>> frequentPatterns){
		Map<String, List<Object[]>> assoMap = new HashMap<String, List<Object[]>>();
		//对所有频繁模式进行遍历
		for (Pair<String, TopKStringPatterns> fps : frequentPatterns) {
    		String key = fps.getFirst();	//如：key=华中科技大学
    		TopKStringPatterns value = fps.getSecond();
//    		System.out.println("key：" + key + " | value：" + value);
    		List<Object[]> data = new ArrayList<Object[]>();
    		//获取当前关键词key下所有模式
            List<Pair<List<String>, Long>> patterns = value.getPatterns();
            for(Pair<List<String>, Long> pair: patterns) {
            	List<String> itemNames = pair.getFirst();	// 获取模式的元素名，如：[华中科技大学, 武汉大学, 湖北大学]
            	Long occurrence = pair.getSecond();			// 获取模式的频繁度，如：10或6
	        	for(String itemName: itemNames) {
	                if (!itemName.equals(key)) {//过滤掉key自身
	               	 	data.add(new Object[]{itemName, occurrence});
	                }
	        	}
            }
            if (data.size() > 0) {
            	assoMap.put(key, data);//map格式样例：{华中科技大学, [[武汉大学, 10], [湖北大学, 6]]}
			}
		}
		return assoMap;
	}
	
	/**
	 * 解析 TopKStringPatterns 
	 * @param val	TopKStringPatterns值
	 * @param isContainSelf	是否包含自相关的项，true：是；false：否
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String getRuleStr(TopKStringPatterns val, boolean isContainSelf) {
		String temp = val.toString();
		if (isContainSelf) {
			return temp;
		}else {
			if (val.getPatterns().size() == 1) {
				return "";
			} else {
				int startIndex = temp.indexOf(")");
				return temp.substring(startIndex + 2, temp.length());
			}
		}
	}
	
}
