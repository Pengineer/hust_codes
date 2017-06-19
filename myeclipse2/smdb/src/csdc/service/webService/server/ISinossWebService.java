package csdc.service.webService.server;

import java.text.ParseException;

import org.dom4j.DocumentException;

import csdc.bean.Account;

public interface ISinossWebService extends IBaseWebService{
	/**
	 * 获取项目申报审核结果
	 * @param account
	 * @param requestAccountBelong
	 * @return
	 * @throws IOException 
	 */
	public String requestProjectApplicationResult(String projectType, int length, Account account, String requestAccountBelong);
	/**
	 * 获取需要进行中检的数据
	 * @param account
	 * @param requestAccountBelong
	 * @return
	 * @throws IOException 
	 */
	public String requestProjectMidinspectionRequired(String projectType, int length, Account account, String requestAccountBelong);
	/**
	 * 获取项目中检审核结果
	 * @param account
	 * @param requestAccountBelong
	 * @return
	 * @throws ParseException
	 * @throws IOException 
	 */
	public String requestProjectMidinspectionResult(String projectType, int length, Account account, String requestAccountBelong);
	/**
	 * 获取项目变更审核结果
	 * @param account
	 * @param requestAccountBelong
	 * @return
	 * @throws ParseException
	 * @throws IOException 
	 */
	public String requestProjectVariationResult(String projectType, int length, Account account, String requestAccountBelong);
	/**
	 * 获取项目结项审核结果
	 * @param account
	 * @param requestAccountBelong
	 * @return
	 * @throws IOException 
	 */
	public String requestProjectEndinspectionResult(String projectType, int length, Account account, String requestAccountBelong);
}
