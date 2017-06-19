package csdc.tool.matcher;

import static csdc.tool.matcher.MatcherInfo.ID;

import java.util.HashMap;
import java.util.Map;

/**
 * 定义评审对象（如：项目），在匹配器中使用
 * @author fengcl
 *
 */
public class Subject {
	
	/**
	 * subject的固有属性
	 */
	private Map intrinsicProperties;
	
	/**
	 * subject的可变属性，非固有
	 */
	private Map alterableProperties;

	
	public Subject(){
		intrinsicProperties = new HashMap();
		alterableProperties = new HashMap();
	}

	/**
	 * 给reviewer添加固有属性
	 * @param key
	 * @param value
	 */
	public void setIntrinsicProperty(String key, Object value){
		intrinsicProperties.put(key, value);
	}
	
	/**
	 * 给reviewer添加可变属性
	 * @param key
	 * @param value
	 */
	public void setAlterableProperty(String key, Object value){
		alterableProperties.put(key, value);
	}
	
	/**
	 * 清空alterableProperties
	 */
	public void clearAlterableProperties() {
		alterableProperties.clear();
	}
	
	/**
	 * 根据key获取固有属性
	 * @param key
	 * @return
	 */
	public Object getIntrinsicProperty(String key) {
		return intrinsicProperties.get(key);
	}

	/**
	 * 根据key获取可变属性
	 * @param key
	 * @return
	 */
	public Object getAlterableProperty(String key) {
		return alterableProperties.get(key);
	}
	
	/**
	 * 重写equals方法，判断两个评审对象是否相等
	 */
	@Override
	public boolean equals(Object o) {
		//如果当前评审对象与待比较评审对象引用相同，则二者相等，返回true
		if (this == o) {
			return true;
		}
		//如果待比较评审对象为空或者当前评审对象与待比较评审对象所属的类不同，则二者不等，返回false
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		//否则，获取两个评审对象的ID，进行比较
		String id1 = (String) this.getIntrinsicProperty(ID);
		String id2 = (String) ((Subject) o).getIntrinsicProperty(ID);
		return id1.equals(id2);
	}

	/**
	 * ELF hash
	 */
	@Override
	public int hashCode() {
		int res = 0;
		int high4Bits;
		String id = (String) this.getIntrinsicProperty(ID);
		for (int i = 0; i < id.length(); i++) {
			res = (res << 4) + id.charAt(i);
			high4Bits = res & 0xf0000000;
			if (high4Bits != 0) {
				res ^= high4Bits >> 24;
				res &= ~high4Bits;
			}
		}
		return res;
	}
}
