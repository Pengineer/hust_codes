package csdc.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import csdc.bean.Expert;

public interface IExpertService extends IBaseService{
	/**
	 * 添加专家
	 * @param expert待添加的专家对象
	 */
	public String addExpert(Expert expert);
	/**
	 * 删除专家
	 * @param expertids
	 */
	public void deleteExperts(List<String> expertids);
	/**
	 * 组装学科信息
	 * @param discipline 学科代码
	 * @return	学科代码加名称
	 */
	public String getDiscipline(String discipline);
	/**
	 * 设置参评和退评
	 * @param expertids	专家id
	 * @param label	参评状态0退评，1参评
	 * @param notReviewerReason	退评原因
	 */
	public void setReview(List<String> expertids, int label, String notReviewerReason ,int isNotReviewAll);
	
	/**
	 * 获取评价等级，下拉框
	 * @return
	 */
	public Map fetchTalentLevel();
	
	/**
	 * 根据高校代码生成专家清单
	 * @param universityCode 高校代码
	 * @return 压缩包路径
	 */
	public String createExpertZip12(String universityCode);
	
	/**
	 * 按高校导出专家库
	 * @return 压缩包路径
	 */
	public String createExpertZip(String universityCode,String id); 
	
	/**
	 * 根据模版导出专家库
	 * @param universityCode 按高校导出 
	 *  如果等于null表示导入所有高校的专家
	 * @return
	 */
	public InputStream exportExcel(String universityCode);
	
	public InputStream exportExcel();
	
}