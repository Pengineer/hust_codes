package csdc.tool;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据排序，适用于一切有是否默认、序号、更新时间、创建时间字段的对象
 * [起始值/步长]:步长越大说明占比越重
 * 是否默认权重[0/1000]
 * 序号值权重[0/100]
 * 更新时间权重[0/10]
 * 创建时间权重[0/1]
 * @author suwb
 *
 */
public class SortT {
	
	private Map isDefaultMap = new HashMap<Integer, Integer>();
	private Map snMap = new HashMap<Integer, Integer>();
	private Map updateDateMap = new HashMap<Integer, Integer>();
	private Map createDateMap = new HashMap<Integer, Integer>();
	private Map totalMap = new HashMap<Integer, Integer>();

	/**
	 * <p>数据排序
	 * <p>[起始值/步长]:步长越大说明占比越重
	 * <p>是否默认权重[0/1000]
	 * <p>序号值权重[0/100]
	 * <p>更新时间权重[0/10]
	 * <p>创建时间权重[0/1]
	 * <p>返回的Map中，key权值，value为list中的位置
	 *
	 */
	public <T> Map objectSort(List<T> list) throws Exception{
		initIsDefault(list);
		initSn(list);
		initUpdateDate(list);
		initCreateDate(list);
		if(list.size()>10){//默认排序对象不大于10个
			throw new RuntimeException();
		}else {
			for(int i=0; i<list.size(); i++){
				int key = (Integer)isDefaultMap.get(i) + (Integer)snMap.get(i) + (Integer)updateDateMap.get(i) + (Integer)createDateMap.get(i);
				if(totalMap.keySet().contains(key)){
					totalMap.put(key +1 , i);//部分数据可能这几个权重字段全部一致，为了保证不丢失，这里特殊处理一下
				}else totalMap.put(key , i);
			}
		}
		return totalMap;
	}
	
	private <T> void initIsDefault(List<T> list) throws Exception{
		int i = 0;
		for(T t : list){
			int isDefault = (Integer)t.getClass().getMethod("getIsDefault").invoke(t);
			isDefaultMap.put(i, isDefault*1000);
			i++;
		}
	}
	
	private <T> void initSn(List<T> list) throws Exception{
		int i = 0;
		for(T t : list){
			int sn = (Integer)t.getClass().getMethod("getSn").invoke(t);
			snMap.put(i, (10-sn)*100);
			i++;
		}
	}
	
	private <T> void initUpdateDate(List<T> list) throws Exception{
		int i = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(T t : list){
			Date updateDate = (Date)t.getClass().getMethod("getUpdateDate").invoke(t);
			if(updateDate!=null){
				List<String> updateDateList = sortUpdateDate(list);
				int j=0;
				for(String update:updateDateList){
					j++;
					if(sdf.format(updateDate).equals(update)){
						updateDateMap.put(i, j*10);
					}
				}
			}else {
				updateDateMap.put(i, 0);
			}
			i++;
		}
	}
	
	private <T> List sortUpdateDate(List<T> list) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<String> updateDateList = new ArrayList<String>();
		for(T t : list){
			Date updateDate = (Date)t.getClass().getMethod("getUpdateDate").invoke(t);
			if(updateDate!=null){
				updateDateList.add(sdf.format(updateDate));
			}
		}
		Collections.sort(updateDateList);
		return updateDateList;
	}

	private <T> void initCreateDate(List<T> list) throws Exception{
		int i = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(T t : list){
			Date createDate = (Date)t.getClass().getMethod("getCreateDate").invoke(t);
			if(createDate!=null){
				List<String> createDateList = sortCreateDate(list);
				int j=0;
				for(String create:createDateList){
					j++;
					if(sdf.format(createDate).equals(create)){
						createDateMap.put(i, j);
					}
				}
			}else {
				createDateMap.put(i, 0);
			}
			i++;
		}
	}
	
	private <T> List sortCreateDate(List<T> list) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<String> createDateList = new ArrayList<String>();
		for(T t : list){
			Date createDate = (Date)t.getClass().getMethod("getCreateDate").invoke(t);
			if(createDate!=null){
				createDateList.add(sdf.format(createDate));
			}
		}
		Collections.sort(createDateList);
		return createDateList;
	}
}
