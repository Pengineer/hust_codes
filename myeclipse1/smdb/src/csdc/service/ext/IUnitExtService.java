package csdc.service.ext;

import java.util.Map;

import csdc.bean.Agency;
import csdc.service.IBaseService;

public interface IUnitExtService extends IBaseService {
	
	/**
	 * 获取教育部的id(仅根据机构代码为360)
	 * @return
	 */
	public String getMOEId();
	
	/**
	 * 获取一级学科
	 * @return
	 */
	public Map<String,String> getDisciplineOne();
	
	/**
	 * 根据学科编号序列获取”编号%名称%代码；“组成的序列，”%“，”；“分隔符
	 */
	public String getDispIdCodeNameById(String ids);
	
	/**
	 * 根据学科"代码/名称; "序列获取”编号%名称%代码；“组成的序列，”%“，”；“分隔符
	 * @param ids
	 * @return
	 */
	public String getDispIdCodeNameByCodeName(String nameCode);
	
	/**
	 * 由id字符串获取选项表中的名称字符串
	 * @param ids
	 * @return
	 */
	public String getNamesByIds(String ids);
}
