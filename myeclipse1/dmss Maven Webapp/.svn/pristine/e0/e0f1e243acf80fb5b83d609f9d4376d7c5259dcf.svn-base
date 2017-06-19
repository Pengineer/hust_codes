package org.csdc.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.util.NewBeanInstanceStrategy;

import org.csdc.dao.IBaseDao;
import org.springframework.dao.support.DaoSupport;

/**
 * DataTable.js的后台数据封装类（已废弃不用）
 * @author jintf
 * @date 2014-6-15
 */
public class DataTable {
	private IBaseDao baseDao;
	private Integer sEcho; //表示ajax是否正确返回，一般为1;
	private Integer iColumns; //表格的列数，前台定义
	
	private boolean [] bSearchable; //可搜索的列
	private boolean [] bSortable; //可排序的列
	
	private Integer iSortingCols=1; //排序的列(默认第一列排序)
	private Integer[] iSortCol; //iSortCol_0=0
	private String[] sSortDir; //iSortCol_0 =asc
	/**
	 * 分页显示的开始记录
	 * 比如你每页是十条，但你要翻到第三页时，iDisplayStart =20
	 */
	private Integer iDisplayStart;
	
	/**
	 * 一页显示的记录数，默认为10，当采用分页缓存时，一页显示的记录数=单页记录数*缓存页数
	 */
	private Integer iDisplayLength;
	
	private String sSearch; //简单检索参数
	private String[] sSearchCol; //指定列检索
	
	private StringBuffer hql = new StringBuffer();
	
	private Integer iTotalRecords;
	private Integer iTotalDisplayRecords;  //iTotalDisplayRecords和iTotalRecords值一致
	//private String[][] aaData;
	private List<Map> aaDataList = new ArrayList<Map>();
	private List<Object []> list = new ArrayList<Object[]>();
	private String [] columnNames;
	
	public DataTable(HttpServletRequest request,String hqlString,IBaseDao baseDao){
		hql.append(hqlString+" ");
		this.baseDao =baseDao;
		try{
			iColumns = Integer.valueOf( request.getParameter("iColumns"));
			sSearchCol = new String[iColumns];
			iSortCol = new Integer[iColumns];
			sSortDir = new String[iColumns];
			columnNames = new String[iColumns];
			
			sEcho =Integer.valueOf(request.getParameter("sEcho"));
			iDisplayStart = Integer.valueOf(request.getParameter("iDisplayStart"));
			iDisplayLength =  Integer.valueOf(request.getParameter("iDisplayLength"));
			
			getColumnNames(hqlString);
			//搜索参数初始化
			sSearch =  request.getParameter("sSearch");
			if(sSearch.length()>0){
				hql.append("and");
				for (int i = 0; i < iColumns; i++) {
					hql.append(" "+"LOWER("+columnNames[i]+") like '%"+sSearch+"%'");
					if(i!=iColumns-1)
						hql.append(" or ");
				}				
			}
			for (int i = 0; i < iColumns; i++) {
				sSearchCol[i] =   request.getParameter("sSearch_"+i);
				if(sSearchCol[i].length()>0)
					hql.append("and LOWER("+columnNames[i]+") like '%"+sSearchCol[i]+"%'");
			}
			//排序参数初始化
			iSortingCols = Integer.valueOf( request.getParameter("iSortingCols"));
			for (int i = 0; i < iSortingCols; i++) {
				iSortCol[i] = Integer.valueOf( request.getParameter("iSortCol_"+i));
				sSortDir[i] =  request.getParameter("sSortDir_"+i);
				if(i==0)
					hql.append(" order by ");
				hql.append(" "+columnNames[i]+" "+sSortDir[i]);
				if(i!=iSortingCols-1)
					hql.append(",");
			}
			
			System.out.println(hql.toString());
			
			
		}catch (Exception e) {
			e.printStackTrace();
			System.err.println("missing parameters");
		}
	}
	
	private void getColumnNames(String hqlString){
		hqlString =hqlString.substring(7,hqlString.indexOf("from"));
		String [] s=hqlString.split(",");
		for (int i = 0; i < s.length-1; i++) {
			if(s[i].contains("as col")){
				columnNames[i]=s[i].substring(0,s[i].lastIndexOf("as"));
			}else {
				columnNames[i]=s[i].trim();
			}
		}
	}
	
	
	
	/**
	 * 检索数据库
	 */
	private void search(){
		//list = baseDao.querySql(hql.toString(), new HashMap(), iDisplayStart, iDisplayLength);
		iTotalDisplayRecords =(int) baseDao.count(hql.toString(),new HashMap());
		iTotalRecords = iTotalDisplayRecords;
		
		//aaData = new String[list.size()][iColumns];
		for (int i = 0; i < list.size(); i++) {
			Map e = new HashMap();
			Object[] o = list.get(i);
			for (int j = 0; j < iColumns; j++) {
				if(o[j]!=null){
					e.put(j, o[j]);
				}else {
					e.put(j, "");
				}
			}
			if(o[iColumns]!=null){
				e.put("DT_RowId", o[iColumns]);
			}else {
				e.put("DT_RowId", "");
			}
			aaDataList.add(e);
		}
	}
	/**
	 * 带参数的检索数据库
	 * @param map
	 */
	private void search(Map map){
		//list = baseDao.querySql(hql.toString(), map, iDisplayStart, iDisplayLength);
		iTotalDisplayRecords =(int) baseDao.count(hql.toString(),new HashMap());
		iTotalRecords = iTotalDisplayRecords;
		
		//aaData = new String[list.size()][iColumns];
		for (int i = 0; i < list.size(); i++) {
			Map e = new HashMap();
			Object[] o = list.get(i);
			for (int j = 0; j < iColumns; j++) {
				if(o[j]!=null){
					e.put(j, o[j]);
				}else {
					e.put(j, "");
				}
			}
			if(o[iColumns]!=null){
				e.put("DT_RowId", o[iColumns]);
			}else {
				e.put("DT_RowId", "");
			}
			aaDataList.add(e);
		}
	}

	public Map getResponse(Map map){
		search(map);
		Map jsonMap = new HashMap();
		jsonMap.put("sEcho", sEcho);
		jsonMap.put("iTotalRecords", iTotalRecords);
		jsonMap.put("iTotalDisplayRecords", iTotalRecords);
		jsonMap.put("aaData", aaDataList);
		return jsonMap;
	}
	
	public Map getResponse(){
		search();
		Map jsonMap = new HashMap();
		jsonMap.put("sEcho", sEcho);
		jsonMap.put("iTotalRecords", iTotalRecords);
		jsonMap.put("iTotalDisplayRecords", iTotalRecords);
		jsonMap.put("aaData", aaDataList);
		return jsonMap;
	}

}
