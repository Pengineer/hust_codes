package csdc.tool.bean;

/**
 * 学科退避级别
 * @author jtf
 *
 */
public class Level{
	public int pro;//项目退避值
	public int exp;//退避值
	
	public Level(int pro,int exp){
		this.pro = pro;
		this.exp = exp;
	}
	
	/**
	 * 项目退避值加专家退避值越小，匹配度则越高
	 * @param b
	 * @return
	 */
	public boolean isBetter(Level b){
		if((this.pro+this.exp)<(b.pro+b.exp)){
			return true;
		}else {
			return false;
		}
	}
}