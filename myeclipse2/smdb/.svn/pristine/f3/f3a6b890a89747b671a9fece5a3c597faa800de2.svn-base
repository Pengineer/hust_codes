package csdc.tool.execution.importer;

import java.io.Reader;

import mondrian.olap.fun.vba.Excel;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import oracle.net.aso.e;
import oracle.net.aso.p;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import csdc.tool.FileTool;
import csdc.tool.StringTool;
import csdc.tool.WordTool;

import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.execution.Execution;
import csdc.tool.reader.JdbcTemplateReader;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.AwardGranted;
import csdc.bean.AwardApplication;
import csdc.bean.AwardReview;
import csdc.bean.Department;
import csdc.bean.Electronic;
import csdc.bean.Institute;
import csdc.bean.OtherProduct;
import csdc.bean.Person;
import csdc.bean.Product;
import csdc.bean.Paper;
import csdc.bean.Book;
import csdc.bean.Consultation;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.tool.execution.finder.DepartmentFinder;
import csdc.tool.execution.finder.InstituteFinder;
import csdc.tool.execution.finder.PersonFinder;
import csdc.tool.execution.finder.TeacherFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.dao.SystemOptionDao;

import csdc.tool.execution.Execution;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;


/**
 * 第六届成果奖颁奖名单入库
 * 《第六届成果奖颁奖名单（20130412）.xls》
 * @author maowh
 *
 */
public class AwardSixthImporter extends Importer {
	
	private ExcelReader excelReader;
	
	@Autowired
	private Tool tool;	
	
	@Autowired
	private SystemOptionDao systemOptionDao;
	
	/**
	 * [成果名称]到[奖励申报ID]的映射
	 */
	private Map<String, String> productNameMap;
	
	/**
	 * [成果名称 + 奖励类型]到[奖励申报ID]的映射
	 */
	private Map<String, String> map;
	
	
	@Override
	public void work() throws Exception{
		//validate();
		importData();		
	}

	/*
	 * 导入数据
	 */
	private void importData() throws Exception {
		
		excelReader.readSheet(0);
		
		//获奖等级
		SystemOption 一等奖 = systemOptionDao.query("awardGrade", "02");
		SystemOption 二等奖 = systemOptionDao.query("awardGrade", "03");
		SystemOption 三等奖 = systemOptionDao.query("awardGrade", "04");
		SystemOption 普及奖 = systemOptionDao.query("awardGrade", "05");						
		
		while (next(excelReader)) {
			if(A == null) {
				break;
			}
			
			System.out.println(excelReader.getCurrentRowIndex() + "/" + excelReader.getRowNumber());
			
			AwardGranted award = new AwardGranted();
			
			AwardApplication awardApplication = findAwardApplication(A);
			award.setApplication(awardApplication);
			if (V.equals("一等奖")) {				
				award.setGrade(一等奖);
			} else if (V.equals("二等奖")) {
				award.setGrade(二等奖);
			} else if (V.equals("三等奖")) {
				award.setGrade(三等奖);
			} else if (V.equals("普及奖")) {
				award.setGrade(普及奖);
			}
			award.setIsImported(1);
			award.setSession(6);
			award.setYear(2013);
			dao.add(award);
			
			if (J != null && !J.isEmpty()) {
				AwardReview awardReview = new AwardReview();
				awardReview.setScore(Double.valueOf(J));
				awardReview.setReviewerSn(1);
				awardReview.setApplication(awardApplication);
				dao.add(awardReview);
			}
			if (L != null && !L.isEmpty()) {
				AwardReview awardReview = new AwardReview();
				awardReview.setScore(Double.valueOf(L));
				awardReview.setReviewerSn(2);
				awardReview.setApplication(awardApplication);
				dao.add(awardReview);
			}
			if (N != null && !N.isEmpty()) {
				AwardReview awardReview = new AwardReview();
				awardReview.setScore(Double.valueOf(N));
				awardReview.setReviewerSn(3);
				awardReview.setApplication(awardApplication);
				dao.add(awardReview);
			}
			if (P != null && !P.isEmpty()) {
				AwardReview awardReview = new AwardReview();
				awardReview.setScore(Double.valueOf(P));
				awardReview.setReviewerSn(4);
				awardReview.setApplication(awardApplication);
				dao.add(awardReview);
			}
			if (R != null && !R.isEmpty()) {
				AwardReview awardReview = new AwardReview();
				awardReview.setScore(Double.valueOf(R));
				awardReview.setReviewerSn(5);
				awardReview.setApplication(awardApplication);
				dao.add(awardReview);
			}

		
		}			
	}

	/*
	 * 校验
	 */
	private void validate() throws Exception {
		
		excelReader.readSheet(0);
		
		List 问题数据 = new ArrayList();
		
		while (next(excelReader)) {
			if(A == null) {
				break;
			}
		
			AwardApplication awardApplication = findAwardApplication(A);
		
			if (awardApplication == null) {
//			System.out.println("该成果不存在申报：" + A);
			问题数据.add(A);
			}
		}
		
		for (int j = 0; j < 问题数据.size(); j++) {
			System.out.println(问题数据.get(j));
		}
		
	}
	
	/*
	 * 根据传入的成果名找到奖励申报实体
	 */
	private AwardApplication findAwardApplication (String productName) {
		
		if (productNameMap == null || map == null) {
			initProductNameMap();
			initMap();
		}
		
		Set<String> keySet = productNameMap.keySet();
		Float temp = 0.0f;
		String tempName = null;
		for (String pName : keySet) {
			Float similarity = StringTool.stringSimilarity(productName, pName);
			if (similarity > temp && similarity >= 0.4) {
				temp = similarity;
				tempName = pName;
			}
		}
		String key = tempName + B;
		String awardApplicationId = map.get(key);
		return (AwardApplication) (awardApplicationId == null ? null : dao.query(AwardApplication.class, awardApplicationId));
	}

	private void initMap() {
		
		Date begin = new Date();
		
		map = new HashMap<String, String>();
		
		List<Object[]> list = dao.query("select aa.productName, aa.id, ss.name from AwardApplication aa left join aa.subType ss where ss.name != '教育部高等学校科学研究优秀成果奖（人文社会科学）'");
		for (Object[] o : list) {
			map.put((String)o[0] + (String)o[2], (String) o[1]);
		}
		
		System.out.println("initMap complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");
		
	}

	private void initProductNameMap() {

		Date begin = new Date();
		
		productNameMap = new HashMap<String, String>();

		List<Object[]> list = dao.query("select aa.productName, aa.id from AwardApplication aa where to_char(aa.importedDate,'yyyy-MM-dd' ) = '2014-01-21'");
		for (Object[] o : list) {
//			String productName = ((String) o[0]).replaceAll("[^A-Za-z0-9\u4e00-\u9fa5]+" , "");
			productNameMap.put((String) o[0], (String) o[1]);
		}
		
		System.out.println("initProductNameMap complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");	
		
	}
	
	public AwardSixthImporter(){};
	
	public AwardSixthImporter(String file){
		excelReader = new ExcelReader(file);
	}

}
