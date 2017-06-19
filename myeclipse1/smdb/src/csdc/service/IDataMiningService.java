package csdc.service;

import java.io.ByteArrayInputStream;
import java.util.List;


/**
 * 数据挖掘：数据挖掘的父接口
 * @author fengcl
 */
public interface IDataMiningService extends IBaseService{
	
	/**
	 * 统计结果Excel导出
	 * @param dataList	数据源
	 * @param header	表头名称
	 * @return	输入流
	 */
	public ByteArrayInputStream commonExportExcel(List<List> dataList, String header);
	
}
