package csdc.action.project;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csdc.tool.info.GlobalInfo;
import csdc.tool.widget.NumberHandle;

public abstract class GrantedAction extends ProjectAction{
	
	/**
	 * 筛选立项通知打印范围
	 * @return
	 */
	public String toPrint(){
		return SUCCESS;
	}
	
	/**
	 * 查询数据，填充立项通知，打印
	 * @return
	 */
	public String printNotice(){
		DecimalFormat myFormatter = new DecimalFormat("#.##");//格式化
		int noticeType = Integer.parseInt(request.getParameter("noticeType"));//立项通知类型(0:给单位;1:西部项目;2:西藏项目;3:新疆项目;4:一般项目)
		int grantYear = Integer.parseInt(request.getParameter("grantYear"));//立项年份
		//获取当前时间
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		String currentYear = NumberHandle.num2Chinese(cal.get(Calendar.YEAR), true);
		String currentMonth = NumberHandle.num2Chinese(cal.get(Calendar.MONTH)+1, false);
		String currentDay = NumberHandle.num2Chinese(cal.get(Calendar.DAY_OF_MONTH), false);
		List dataList = new ArrayList();
		List list = new ArrayList();
		list.add(currentYear);
		list.add(currentMonth);
		list.add(currentDay);
		request.setAttribute("noticeType", noticeType);
		request.setAttribute("list", list);
		
		if(noticeType == 0){//给单位
			dataList.add(list);
			request.setAttribute("dataList", dataList);
			request.setAttribute("grantYear", grantYear);
			return SUCCESS;
		}
		
		Map parMap = new HashMap();
		parMap.put("xbAreas", GlobalInfo.XB_AREAS);
		parMap.put("specialAreas", GlobalInfo.SPECIAL_AREAS);
		parMap.put("xz", "西藏自治区");
		parMap.put("xj", "新疆维吾尔自治区");
		parMap.put("grantYear", grantYear);
		
		String hql4Notice = "select p.id, p.year, p.universityName, p.director, p.projectName, p.approveFee, p.otherFee, TO_CHAR(p.planEndDate, 'yyyy'), u.provinceName, p.approvalNumber, p.projectType, 'midTime' from ProjectApplication p, University u where p.type = 'general' and p.universityCode = u.code and p.isGranted = 1 and p.year = :grantYear";
		StringBuffer sb = new StringBuffer();
		sb.append(hql4Notice);
		switch(noticeType){
			case 1://西部项目
				sb.append(" and u.provinceName in (:xbAreas)");
				break;
			case 2://西藏项目
				sb.append(" and u.provinceName = :xz ");
				break;
			case 3://新疆项目
				sb.append(" and u.provinceName = :xj ");
				break;
			case 4://一般项目
				sb.append(" and u.provinceName not in (:specialAreas)");
				break;
		}
		sb.append(" order by p.universityName, p.director");
		dataList = baseService.query(sb.toString(), parMap);
		for(int i = 0; i < dataList.size(); i++){
			Object[] obj = (Object[]) dataList.get(i);
			double approveFee = Double.parseDouble(obj[5].toString());//批准经费
			obj[6] = myFormatter.format(approveFee * 0.6);//第一批经费为批准经费的60%
			obj[7] = Integer.parseInt(obj[1].toString()) + 2;//项目完成时间为项目年度加2年,即3年
			obj[11] = Integer.parseInt(obj[1].toString()) + 1;//中检时间为项目年度加1年
			dataList.set(i, obj);
		}
		request.setAttribute("dataList", dataList);
		request.setAttribute("grantYear", grantYear);
		return SUCCESS;
	}
	
	/**
	 * 填充下拉框
	 * @return
	 */
	public String getSelectBoxData(){
		Map parMap = new HashMap();
		parMap.put("ministryCode", GlobalInfo.MINISTRY_CODE);
		//查询省名称
		String hql4Prov = "select distinct u.provinceName from University u where u.provinceName is not null order by u.provinceName";
		List listProv = baseService.query(hql4Prov);
		jsonMap.put("provinceName", listProv);
		String listTypeStr = request.getParameter("listType");
		int listType = (listTypeStr == null || listTypeStr.equals("")) ? 0 : Integer.parseInt(listTypeStr);//立项清单类型(1:给高校;2:给省厅;3:给其他部委;)
		switch(listType){
			case 1://给高校
				Map univ2Prov = new HashMap();//获取每个省内的高校（包扩部属）
				for(int i = 0; i < listProv.size(); i++){
					parMap.put("provinceName", listProv.get(i));
					String hql4Univ = "select u.name from University u where u.provinceName = :provinceName order by u.name";//包括各部委院校
					List listUniv = baseService.query(hql4Univ, parMap);
					univ2Prov.put(listProv.get(i), listUniv);//省名<->省内所有高校
				}
				jsonMap.put("univ2Prov", univ2Prov);//全部高校
				break;
			case 2://给省厅
				Map puniv2Prov = new HashMap();//获取每个省属院校
				for(int i = 0; i < listProv.size(); i++){
					parMap.put("provinceName", listProv.get(i));
					String hql4Univ = "select u.name from University u where u.provinceName = :provinceName and u.founderCode not in (:ministryCode) order by u.name";//排除各部委院校
					List listUniv = baseService.query(hql4Univ, parMap);
					puniv2Prov.put(listProv.get(i), listUniv);//省名<->省属高校
				}
				jsonMap.put("puniv2Prov", puniv2Prov);//省属高校
				break;
			case 3://给其他部委
				Map univ2Min = new HashMap();//获取每个部委院校
				for(int i = 0; i < listProv.size(); i++){
					String hql360Univ = "select u.name from University u where u.founderCode = '360' order by u.name";//教育部院校
					List list360Univ = baseService.query(hql360Univ, parMap);
					String hql4Univ = "select u.name from University u where u.founderCode in (:ministryCode) and u.founderCode <> '360' order by u.name";//各部委院校
					List listUniv = baseService.query(hql4Univ, parMap);
					univ2Min.put("教育部", list360Univ);//教育部<->部属高校
					univ2Min.put("其他部委", listUniv);//其他部委<->其他部委所属高校
				}
				jsonMap.put("univ2Min", univ2Min);//部属高校
				break;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 打印立项清单
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String printGrantedList() throws UnsupportedEncodingException{
		int listType = Integer.parseInt(request.getParameter("listType"));//立项清单类型(1:给高校;2:给省厅;3:给其他部委;)
		int grantYear = Integer.parseInt(request.getParameter("grantYear"));//立项年份
		String univName = request.getParameter("univName").trim();//高校名称
		univName = URLDecoder.decode(univName, "UTF-8");
		String univProvName = request.getParameter("univProvName");//高校所在省
		univProvName = URLDecoder.decode(univProvName, "UTF-8");
		String provName = request.getParameter("provName");//省厅名称
		provName = URLDecoder.decode(provName, "UTF-8");
		String minName = request.getParameter("minName");//其他部委名称
		minName = URLDecoder.decode(minName, "UTF-8");
		
		String hql4List = "select p.id, u.name, p.projectName, p.director, p.projectType, p.disciplineType, TO_CHAR(p.planEndDate, 'yyyy-MM-dd'), p.applyFee, p.otherFee, p.finalResultType, p.approvalNumber, p.auditStatus from ProjectApplication p, University u where p.type = 'general' and p.universityCode = u.code and p.isGranted = 1 and p.year = :grantYear";
		List dataList = new ArrayList();
		List groupList = new ArrayList();//按照高校分组
		Map parMap = new HashMap();
		parMap.put("univName", univName);
		parMap.put("univProvName", univProvName);
		parMap.put("provName", provName);
		parMap.put("ministryCode", GlobalInfo.MINISTRY_CODE);
		parMap.put("grantYear", grantYear);
		
		StringBuffer sb = new StringBuffer();
		sb.append(hql4List);
		switch(listType){
			case 1://给高校
				if(univProvName.equals("-1")){//所有高校
			        sb.append(" order by u.name, p.projectName");
			        dataList = getGroupData(sb.toString(), parMap);
				}else{//指定高校
					if(univName.equals("-1")){//所有该省省内高校
						sb.append(" and u.provinceName = :univProvName");
						sb.append(" order by u.name, p.projectName");
				        dataList = getGroupData(sb.toString(), parMap);
					}else{//具体某一个高校
						sb.append(" and u.name = :univName");
						sb.append(" order by p.projectName, p.director");
						groupList = baseService.query(sb.toString(), parMap);
						if(groupList != null && !groupList.isEmpty()){
							dataList.add(groupList);
						}
					}
				}
				break;
			case 2://给省厅
				if(provName.equals("-1")){//所有省属高校
					sb.append(" and u.founderCode not in (:ministryCode)");//所有省属高校名称列表
					sb.append(" order by u.name, p.projectName");
					dataList = getGroupData(sb.toString(), parMap);
				}else{//指定省厅
					if(univName.equals("-1")){//所有该省省属高校
						sb.append(" and u.provinceName = :univProvName and u.founderCode not in (:ministryCode)");
						sb.append(" order by u.name, p.projectName");
				        dataList = getGroupData(sb.toString(), parMap);
					}else{//具体某一个高校
						sb.append(" and u.name = :univName");
						sb.append(" order by p.projectName, p.director");
						groupList = baseService.query(sb.toString(), parMap);
						if(groupList != null && !groupList.isEmpty()){
							dataList.add(groupList);
						}
					}
				}
				break;
			case 3://给其他部委
				if(minName.equals("-1")){//所有部委院校
					sb.append(" and u.founderCode in (:ministryCode)");//所有部委高校名称列表
					sb.append(" order by u.name, p.projectName");
					dataList = getGroupData(sb.toString(), parMap);
				}else if(minName.equals("教育部")){//教育部直属院校
					if(univName.equals("-1")){//教育部所有高校
						sb.append(" and u.founderCode = '360'");//教育部所有高校名称列表
						sb.append(" order by u.name, p.projectName");
						dataList = getGroupData(sb.toString(), parMap);
					}else{//具体某一个高校
						sb.append(" and u.name = :univName");
						sb.append(" order by p.projectName, p.director");
						groupList = baseService.query(sb.toString(), parMap);
						if(groupList != null && !groupList.isEmpty()){
							dataList.add(groupList);
						}
					}
				}else{//其他部委院校（除教育部）
					if(univName.equals("-1")){//其他部委所有高校
						sb.append(" and u.founderCode in (:ministryCode) and u.founderCode <> '360'");//教育部所有高校名称列表
						sb.append(" order by u.name, p.projectName");
						dataList = getGroupData(sb.toString(), parMap);
					}else{//具体某一个高校
						sb.append(" and u.name = :univName");
						sb.append(" order by p.projectName, p.director");
						groupList = baseService.query(sb.toString(), parMap);
						if(groupList != null && !groupList.isEmpty()){
							dataList.add(groupList);
						}
					}
				}
				break;
		}
		//返回数据
		request.setAttribute("dataList", dataList);
		request.setAttribute("grantYear", grantYear);
		return SUCCESS;
	}
	
	/**
	 * 获取按高校分组后的总结果
	 * @param hql		查询的语句
	 * @param parMap	参数
	 * @return			返回dataList
	 */
	private List getGroupData(String hql, Map parMap){
		List dataList = new ArrayList();
		List groupList = new ArrayList();
		List allGrantedList = baseService.query(hql, parMap);//查询所有已立项项目
		String lastUnivName = "";
		for(int i = 0; i < allGrantedList.size(); i++){
			Object[] obj = (Object[]) allGrantedList.get(i);
			String currentUniv = (obj[1] == null) ? "" : obj[1].toString();//当前项目所属高校
			if(lastUnivName.equals(currentUniv)){
				groupList.add(allGrantedList.get(i));
			}else{
				if(groupList != null && !groupList.isEmpty()){
					dataList.add(groupList);
					groupList = new ArrayList();
				}//输出上一个高校的项目集合到dataList，并重新构造groupList
				lastUnivName = currentUniv;
				groupList.add(allGrantedList.get(i));
			}
			if(i == allGrantedList.size()-1 && groupList != null && !groupList.isEmpty()){
				dataList.add(groupList);
			}//输出最后一个高校的项目集合到dataList
		}
		return dataList;
	}
	
	/**
	 * 导出立项清单
	 * @return
	 */
	public String exportGrantedList(){
		//TODO 待处理
		return SUCCESS;
	}
}
