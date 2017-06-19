package csdc.tool;

import java.util.UUID;

import org.hibernate.id.UUIDGenerator;

/**
 * 随机目录生成工具
 * @author Administrator
 *
 */
public class RandTool {
	
	/**
	 * 根据因子生成4位随机字符串（纯数字）
	 * @param factor
	 * @return
	 */
	public static String rand(Integer factor){
		int i=(int) (Math.random()*factor);
		return getStandardRandString(i);
	}
	
	/**
	 * 比如输入1，返回0001
	 * @param i
	 */
	public static String getStandardRandString(int i){
		if(i <10){
			return "000"+i;
		}else if(i<100){
			return "00"+i;
		}else if(i<1000){
			return "0"+i;
		}else{
			return String.valueOf(i);
		}
	}	
}
