package csdc.tool.matcher;

import java.util.HashMap;
import java.util.Map;
import static csdc.tool.matcher.MatcherInfo.*;

/**
 * 定义参评对象（如：专家），在匹配器中使用
 * @author fengcl
 *
 */
public class Reviewer {

	/**
	 * reviewer的固有属性
	 */
	private Map intrinsicProperties;

	/**
	 * reviewer的可变属性，非固有
	 */
	private Map alterableProperties;

	public Reviewer() {
		intrinsicProperties = new HashMap();
		alterableProperties = new HashMap();
	}

	/**
	 * 给reviewer添加固有属性
	 * 
	 * @param key
	 * @param value
	 */
	public void setIntrinsicProperty(String key, Object value) {
		intrinsicProperties.put(key, value);
	}

	/**
	 * 给reviewer添加可变属性
	 * 
	 * @param key
	 * @param value
	 */
	public void setAlterableProperty(String key, Object value) {
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
	 * 
	 * @param key
	 * @return
	 */
	public Object getIntrinsicProperty(String key) {
		return intrinsicProperties.get(key);
	}

	/**
	 * 根据key获取可变属性
	 * 
	 * @param key
	 * @return
	 */
	public Object getAlterableProperty(String key) {
		return alterableProperties.get(key);
	}

	/**
	 * 重写equals方法，判断两个参评对象是否相等
	 */
	@Override
	public boolean equals(Object o) {
		//如果当前参评对象与待比较参评对象引用相同，则二者相等，返回true
		if (this == o) {
			return true;
		}
		//如果待比较参评对象为空或者当前参评对象与待比较参评对象所属的类不同，则二者不等，返回false
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		//否则，获取两个参评对象的ID，进行比较
		String id1 = (String) this.getIntrinsicProperty(ID);
		String id2 = (String) ((Reviewer) o).getIntrinsicProperty(ID);
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
