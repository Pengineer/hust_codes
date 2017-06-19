package csdc.service;

import java.util.List;
import java.util.Map;


public interface IFinanceService extends IBaseService{
	
	/**
	 * 经费核算初始化
	 */
	@SuppressWarnings("unchecked")
	public Map init(int year, double planFee, double youthFee);
	
	/**
	 * 根据比例宏观核算
	 */
	@SuppressWarnings("unchecked")
	public Map checkByRate(Map initMap, Map setMap);

	/**
	 * 
	 * @param dataMap
	 * @param disType
	 * @param area
	 * @param proType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List initCheckById(Map dataMap, String disType, String area, String proType);
	
	/**
	 * 
	 * @param dataMap
	 * @param idsList1
	 * @param idsList2
	 * @param disType
	 * @param area
	 * @param proType
	 * @param planFee
	 * @param youthFee
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map checkById(Map dataMap, List<String> idsList1, List<String> idsList2, String disType, String area, String proType, Double planFee, Double youthFee);

	/**
	 * 
	 * @param dataMap
	 * @param disType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List initCheckZcById(Map dataMap, String disType);
	
	/**
	 * 
	 * @param dataMap
	 * @param idsList
	 * @param disType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map checkZcById(Map dataMap, List<String> idsList, String disType);
	
	/**
	 * 
	 * @param dataMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Boolean finish(Map dataMap);
}
