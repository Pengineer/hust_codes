package csdc.action.messageAuxiliary;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java_cup.internal_error;

import org.apache.poi.hdf.extractor.PAP;
import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.Teacher;
import csdc.service.IAuxiliaryService;
import csdc.service.IGeneralService;

public class UnitAction extends MessageAuxiliaryBaseAction{
	
	private int startYear;//项目起始年度
	private int endYear;//项目结束年度
	private double perWeight;//人员权重
	private double proWeight;//项目权重
	private double awrWeight;//奖励权重
	private double prdWeight;//成果权重
	private String entityNameValue;

	
	/**
	 * 进入到人员查询页面
	 */
	public String toUnitQuery(){
		perWeight = 0.25;
		proWeight = 0.25;
		awrWeight = 0.25;
		prdWeight = 0.25;
		return SUCCESS;
	}
	
	/**
	 * 查询
	 */
	public String unitQuery(){
		if (entityId == null) {
			jsonMap.put("errorInfo", "查询对象不能为空！");
		}
		doWithData();
		return SUCCESS;
	}

	/**
	 * 为了提高速度在数据返回到前台之前把一部分数据进行预处理
	 * 把数据放到session里面
	 */
	public String getData(){
		Long nowLong = System.currentTimeMillis();
		startYear = 1088;//这个可以根据后续需求在前台选择项目的年度（现在没有这个需求所以只是定义了一个年份）；
		endYear = 2222;
		Map paraMap = new HashMap();
		Agency agency = dao.query(Agency.class, entityId);
		String provinceId = agency.getProvince().getId();
		paraMap.put("provinceId", provinceId);
		List<Long> universityList = dao.query("select count(a.id) from Agency a where a.province.id = :provinceId and( a.type = 3 or a.type = 4 )", paraMap);
		
		//高校数量
		List<Long> moeUniversityList = dao.query("select count(a.id) from Agency a where a.province.id = :provinceId and a.type = 3 ", paraMap);
		List<Long> locUniversityList = dao.query("select count(a.id) from Agency a where a.province.id = :provinceId and a.type = 4 ", paraMap);
		session.put("totlaUniversity", universityList.size() == 0 ? 0 : universityList.get(0));
		session.put("totlaMoeUniversity",moeUniversityList.size() == 0 ? 0 : moeUniversityList.get(0));
		session.put("totlaLocUniversity", locUniversityList.size() == 0 ? 0 : locUniversityList.get(0));
		
		//申报数
		String hqlApp = "select un.id, count(pa.id) from ProjectApplication pa left join pa.university un where un.province.id = :provinceId and pa.year between " + startYear + " and " + endYear + " group by un.id"; 
		List<Object[]> apps = dao.query(hqlApp, paraMap);
		Map appMap = new HashMap();
		for (Object[] objs : apps) {
			appMap.put(objs[0], objs[1]);
		}
		session.put("appMap", appMap);
		
		//立项数
		String hqlGra = "select un.id, count(pg.id) from ProjectGranted pg left join pg.university un, ProjectApplication pa where pg.applicationId = pa.id and un.province.id = :provinceId and pa.year between " + startYear + " and " + endYear + " group by un.id"; 
		List<Object[]> gras = dao.query(hqlGra, paraMap);
		Map graMap = new HashMap();
		for (Object[] objs : gras) {
			graMap.put(objs[0], objs[1]);
		}
		session.put("graMap", graMap);
		
		//结项数
		String hqlEnd = "select un.id, count(pe.id) from ProjectEndinspection pe, ProjectGranted pg left join pg.university un, ProjectApplication pa where pe.grantedId = pg.id and pg.applicationId = pa.id and " +
				"pe.finalAuditResultEnd = 2 and pe.finalAuditStatus = 3 and un.province.id = :provinceId and pa.year between " + startYear + " and " + endYear + " group by un.id"; 
		List<Object[]> ends = dao.query(hqlEnd,paraMap);
		Map endMap = new HashMap();
		for (Object[] objs : ends) {
			endMap.put(objs[0], objs[1]);
		}
		session.put("endMap", endMap);
	
		//奖励情况
		List<Object[]> award = dao.query("select u.id, count(ap.id) from AwardApplication ap left join ap.university u where u.province.id = :provinceId group by u.id ", paraMap);
		Map awardMap = new HashMap();
		for (Object[] objs : award) {
			awardMap.put(objs[0], objs[1]);
		}
		session.put("awardMap", awardMap);
		//成果情况
		List<Object[]> product = dao.query("select u.id, count(p.id) from Product p left join p.university u where u.province.id = :provinceId group by u.id ", paraMap);
		Map productMap = new HashMap();
		for (Object[] objs : product) {
			productMap.put(objs[0], objs[1]);
		}
		session.put("productMap", productMap);
		List<Object[]> personList = dao.query("select ag.id, ag.name, count(ac.id) from Academic ac left join ac.person p, Teacher t left join t.university ag " +
				"where t.person.id = p.id and ag.province.id = :provinceId group by ag.id, ag.name order by count(ac.id) desc ", paraMap);
		session.put("personList", personList);
		System.out.println("一共耗时：");
		System.out.println(System.currentTimeMillis() - nowLong);
		return SUCCESS;
	}
	
	
	/**
	 * 数据的组装和处理
	 * @return jsonMap
	 */
	public void doWithData(){
		Long nowLong = System.currentTimeMillis();
		Map paraMap = new HashMap();
		List lSocTitle = new ArrayList();//总分
		List lScoData = new ArrayList();
		List personlaData = new ArrayList();//人员列表数据
		List<Object[]> projectlaData = new ArrayList();//项目
		List<Object[]> awardlaData = new ArrayList();//奖励列表数据
		List<Object[]> productlaData = new ArrayList();//成果列表数据
		List<Object[]> scoreList = new ArrayList();
		jsonMap.put("totlaUniversity", session.get("totlaUniversity"));
		jsonMap.put("totlaMoeUniversity", session.get("totlaMoeUniversity"));
		jsonMap.put("totlaLocUniversity", session.get("totlaLocUniversity"));
		List<Object[]> personList = (List) session.get("personList");
		for (Object[] obj : personList) {
			Double score = 0.0;//各高校总得分
			paraMap.put("agencyId", obj[0]);
			//人员分布
			List<Object[]> academicList = dao.query("select ac.specialityTitle, count(ac.id) from Academic ac left join ac.person p, Teacher t " +
					"where t.person.id = p.id and  t.university.id = :agencyId group by ac.specialityTitle order by count(ac.id) desc ", paraMap);
			Object[] object = new Object[]{obj[1],(Long) obj[2]};
			score += (Long) obj[2] * perWeight;
			if (object.length != 0) {
				academicList.add(0, object);
			}
			if (academicList.size() != 0) {
				personlaData.add(listDealWith(academicList));
			}
			
			//项目情况
			Object[] datas = new Object[6];
			datas[0] = obj[1];
			Integer app = 0;
			Integer gra = 0;
			Integer end = 0;
			Map appMap = (Map) session.get("appMap");
			if (appMap.containsKey(obj[0])) {
				app = (Integer) ((Long) appMap.get(obj[0])).intValue();
			}
			Map graMap = (Map) session.get("graMap");
			if (graMap.containsKey(obj[0])) {
				gra = (Integer) ((Long) graMap.get(obj[0])).intValue();
			}
			Map endMap = (Map) session.get("endMap");
			if (endMap.containsKey(obj[0])) {
				end = (Integer) ((Long) endMap.get(obj[0])).intValue();
			}
			datas[1] = app;//申报数量
			datas[2] = gra;//立项数量
			if (gra !=0 && app!=0) {
				datas[3] = assistService.calculate(gra, app);//立项率
			} else {
				datas[3] = 0.0;
			}
			datas[4] = end;//结项数
			if (end !=0 && app!=0) {
				datas[5] = assistService.calculate(end, app);//结项率
			} else {
				datas[5] = 0.0;
			}
			
			projectlaData.add(datas);
			score += gra * proWeight;//立项分数
			
			//奖励情况
			Map awardMap = (Map) session.get("awardMap");
			Object[] awardDatas = new Object[2];
			Integer totalAward = (awardMap.containsKey(obj[0])) ? (Integer) ((Long) awardMap.get(obj[0])).intValue() : 0;
			awardDatas[0] = obj[1];
			awardDatas[1] = totalAward;//获奖数
			awardlaData.add(awardDatas);
			score += totalAward * awrWeight;
			
			//成果情况
			Map productMap = (Map) session.get("productMap");
			Object[] productDatas = new Object[2];
			Integer totalPro = (productMap.containsKey(obj[0])) ? (Integer) ((Long) productMap.get(obj[0])).intValue() : 0;
			productDatas[0] = obj[1];
			productDatas[1] = totalPro;//成果总数
			productlaData.add(productDatas);
			score += totalPro * prdWeight;
			Object[] scoreObject = new Object[2];
			scoreObject[0] = obj[1];
			scoreObject[1] = (double)Math.round(score*100)/100;
			scoreList.add(scoreObject);
			
		}
		//按照项目的总数降序排序;按照成果、奖励的总数、总分降序排序
		Comparator<Object[]> countComparator = new Comparator<Object[]>() {
			public int compare(Object[] o1, Object[] o2) {
				Double cnt1 = Double.parseDouble(String.valueOf(o1[1]));
				Double cnt2 = Double.parseDouble(String.valueOf(o2[1]));
				return cnt2.compareTo(cnt1);
			}
		};
		Collections.sort(projectlaData, countComparator);
		Collections.sort(awardlaData, countComparator);
		Collections.sort(productlaData, countComparator);
		Collections.sort(scoreList, countComparator);
		if (showLineNum == 0) {//显示全部
			jsonMap.put("personlaData", personlaData);
			jsonMap.put("projectlaData", projectlaData);
			jsonMap.put("awardlaData", awardlaData);
			jsonMap.put("productlaData", productlaData);
			jsonMap.put("scoreList", scoreList);
			for (int i = 0; i < scoreList.size(); i++) {
				lSocTitle.add(scoreList.get(i)[0]);
				lScoData.add(scoreList.get(i)[1]);
			}
			jsonMap.put("lSocTitle", lSocTitle);
			jsonMap.put("lScoData", lScoData);
		} else {
			jsonMap.put("personlaData", personlaData.subList(0, showLineNum));
			jsonMap.put("projectlaData", projectlaData.subList(0, showLineNum));
			jsonMap.put("awardlaData", awardlaData.subList(0, showLineNum));
			jsonMap.put("productlaData", productlaData.subList(0, showLineNum));
			jsonMap.put("scoreList", scoreList.subList(0, showLineNum));
			for (int i = 0; i < scoreList.subList(0, showLineNum).size(); i++) {
				lSocTitle.add(scoreList.get(i)[0]);
				lScoData.add(scoreList.get(i)[1]);
			}
			jsonMap.put("lSocTitle", lSocTitle);
			jsonMap.put("lScoData", lScoData);
		}
		jsonMap.put("chartType", chartType);
		long now1 = System.currentTimeMillis();
		System.out.println("一共耗时：");
		System.out.println(now1-nowLong);
	}
	
	/**
	 * 处理list的数据
	 * @param listData 处理之前的LIST
	 * @return laData 处理之后的LIST
	 */
	public List listDealWith(List<Object[]> listData) {
		List laData = new ArrayList();
		StringBuffer stringBuffer = new StringBuffer();
		Object[] pObject = new Object[3];
		pObject[0] = listData.get(0)[0];
		pObject[1] = listData.get(0)[1];
		for (int i = 1; i < listData.size(); i++) {
			Object[] test = (Object[]) listData.get(i);
			for (int j = 0; j < test.length; j++) {
				if (test[j] == null) {// 如果字段值为空，则以位置替换
					test[j] = "未知";
				}
				if (j % 2 == 0) {
					stringBuffer.append(test[j] + ":　");
				} else {
					stringBuffer.append(test[j] + " ；　");
				}
			}
		}
		pObject[2] = stringBuffer.toString().substring(0, stringBuffer.length()-2);
		laData.add(pObject);
		return laData;
	}
	
	
	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	public double getPerWeight() {
		return perWeight;
	}

	public void setPerWeight(double perWeight) {
		this.perWeight = perWeight;
	}

	public double getProWeight() {
		return proWeight;
	}

	public void setProWeight(double proWeight) {
		this.proWeight = proWeight;
	}

	public double getAwrWeight() {
		return awrWeight;
	}

	public void setAwrWeight(double awrWeight) {
		this.awrWeight = awrWeight;
	}

	public double getPrdWeight() {
		return prdWeight;
	}

	public void setPrdWeight(double prdWeight) {
		this.prdWeight = prdWeight;
	}

	public String getEntityNameValue() {
		return entityNameValue;
	}

	public void setEntityNameValue(String entityNameValue) {
		this.entityNameValue = entityNameValue;
	}
	
}
