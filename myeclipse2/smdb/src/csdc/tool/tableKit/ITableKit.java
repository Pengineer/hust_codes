package csdc.tool.tableKit;

import java.io.File;
import java.util.List;

/**
 * 表工具包接口
 * @author 徐涵
 *
 */
@SuppressWarnings("unchecked")
public interface ITableKit {
	 /**
	  * Excel上传校验
	  * @param xls Excel文件
	  * @return 校验错误信息(为空表示没有错误)
	  * @throws Exception
	  */
	StringBuffer validate(File xls) throws Exception;

	/**
	 * 显示已上传Excel文件的内容
	 * @param xls Excel文件
	 * @return Excel文件中内容
	 * @throws Exception
	 */
	List display(File xls) throws Exception;

	/**
	 * 导入Excel文件中数据
	 * @throws Exception
	 */
	void imprt() throws Exception;
	void exprtTemplate() throws Exception;


	/**
	 * 导出数据库表中已有数据(因是数据库相关，不是Excel相关，废弃)
	 * @throws Exception
	 */
//	void exprt() throws Exception;


	/**
	 * 列出数据库表中已有数据(因是数据库相关，不是Excel相关，废弃)
	 * @return
	 */
//	List list();
}
