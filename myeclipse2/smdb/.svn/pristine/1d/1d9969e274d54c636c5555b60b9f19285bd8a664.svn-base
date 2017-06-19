package csdc.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;

/**
 * word处理相关小工具
 * @author leida
 *
 */
public class WordTool {
	
	public static String getWordIndexData(File file, String index) {
		String[] indexes = index.split("; ");
		return getWordTableData(file, Integer.parseInt(indexes[0]) - 1, Integer.parseInt(indexes[1]) - 1, Integer.parseInt(indexes[2]) - 1);
	}

	/**
	 * 通过word中的表下标+行下标+列下标，获取word中指定表格、指定行列里面的数据，所有下标从0开始
	 * @param file word文件
	 * @param tableIndex 表下标
	 * @param rowIndex 行下标
	 * @param colIndex 列下标
	 * @return 数据字串
	 * @author leida 2012-01-06
	 */
	public static String getWordTableData(File file, int tableIndex, int rowIndex, int colIndex) {
		try {
			String dataXMLStr = null;
			HWPFDocument doc = new HWPFDocument(new FileInputStream(file));
			Range range = doc.getRange();
			TableIterator it = new TableIterator(range);
			int countIndex = 0;
			while(it.hasNext()) {
				if(countIndex == tableIndex) {
					Table tb = (Table) it.next();
					TableRow tr = tb.getRow(rowIndex);
					TableCell td = tr.getCell(colIndex);
					dataXMLStr = td.text();
					if(dataXMLStr != null) {
						return regulateXMLContent(dataXMLStr.trim());
					}
				}
				countIndex++;
				it.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 更新字段信息
	 * @param formerData 原始数据
	 * @param newData 新数据
	 * @param updateRule 更新规则(1, 2, 3) </br>1.覆盖 eg. formerData = "cat" newData = "dog" return "dog"; 
	 * </br>2.取并集用分号空格隔开 eg. formerData = "A; B" newData = "B; C" return "A; B; C"; 
	 * </br>3.增补 eg. formerData = "珞瑜路 1037号" newData = "华中科技大学" return "珞瑜路 1037号; 华中科技大学"
	 * @param restrictLenth 字段限制长度，-1则不限制
	 * @return 更新后的数据
	 * @author leida 2011-09-14
	 */
	public static String updateData(String formerData, String newData, int updateRule, int restrictLenth) {
		if(newData == null || newData.equals("") || (formerData != null && newData != null && formerData.equals(newData))) {
			return formerData;
		}
		if(updateRule == 2) {
			HashSet<String> dataSet = new HashSet<String>();
			String[] formerStr = formerData.split(";");
			String[] newStr = newData.split(";");
			for(int i = 0; i < formerStr.length; i++) {
				if(!formerStr[i].trim().isEmpty()) {
					dataSet.add(formerStr[i].trim());
				}
			}
			for(int i = 0; i < newStr.length; i++) {
				if(!newStr[i].trim().isEmpty()) {
					dataSet.add(newStr[i].trim());
				}
			}
			String result = "";
			for(String tmp : dataSet) {
				result = result + tmp + "; ";
			}
			if(result.length() > 2 && result.endsWith("; ")) {
				newData = result.substring(0, result.length() - 2);
			} else {
				newData = formerData;
			}
		}
		else if(updateRule == 3) {
			if(formerData != null && !formerData.isEmpty()) {
				newData = formerData.contains(newData) ? formerData : (formerData + "; " + newData);
			}
		}
		if(restrictLenth != -1) {
			if(newData.length() <= restrictLenth) {
				return newData;
			} else {
				return formerData;
			}
		} else {
			return newData;
		}
	}
	
	/**
	 * 预处理上报的word中的数据
	 * @param XMLString
	 * @return 处理过后的数据
	 */
	public static String regulateXMLContent(String XMLString) {
		if(XMLString != null) {
			XMLString = XMLString.replaceAll(">请点击选择...</", "></");
		}
		return XMLString;
	}
	
	/**
	 * 判断文件是否为“教育部人文社会科学研究项目中期检查报告书”
	 * @param file word文件
	 * @return
	 */
	public static boolean isMidFile(File file) {
		WordExtractor wordExtractor;
		try {
			wordExtractor = new WordExtractor(new FileInputStream(file));
			String wordContent = wordExtractor.getTextFromPieces();
			if(wordContent.indexOf("中期检查报告书") > 0 && wordContent.indexOf("教育部人文社会科学研究项目") >= 0 && wordContent.indexOf("1—2项代表性成果简介") > 0 && wordContent.indexOf("项目批准号") >= 0) {
				return true;
			}
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return false;
	}
	
	/**
	 * 判断文件是否为“教育部人文社会科学研究项目重要事项变更申请表”
	 * @param file word文件
	 * @return
	 */
	public static boolean isVarFile(File file) {
		WordExtractor wordExtractor;
		try {
			wordExtractor = new WordExtractor(new FileInputStream(file));
			String wordContent = wordExtractor.getTextFromPieces();
			if(wordContent.indexOf("重要事项变更申请表") > 0 && wordContent.indexOf("教育部人文社会科学研究项目") >= 0 && wordContent.indexOf("变更内容") >= 0) {
				return true;
			}
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return false;
	}
	
	/**
	 * 判断文件是否为“教育部人文社会科学研究项目终结报告书”
	 * @param file word文件
	 * @return
	 */
	public static boolean isEndFile(File file) {
		WordExtractor wordExtractor;
		try {
			wordExtractor = new WordExtractor(new FileInputStream(file));
			String wordContent = wordExtractor.getTextFromPieces();
			if(wordContent.indexOf("终结报告书") > 0 && wordContent.indexOf("教育部人文社会科学研究项目") >= 0 && wordContent.indexOf("实际完成时间") >= 0 && wordContent.indexOf("最终成果内容简介") >= 0) {
				return true;
			}
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return false;
	}
	
	/**
	 * 判断文件是否为“教育部人文社会科学研究项目申请评审书”
	 * @param file word文件
	 * @return
	 */
	public static boolean isAppFile(File file) {
		WordExtractor wordExtractor;
		try {
			wordExtractor = new WordExtractor(new FileInputStream(file));
			String wordContent = wordExtractor.getTextFromPieces();
			if(wordContent.indexOf("申请评审书") > 0 && wordContent.indexOf("教育部人文社会科学研究项目") >= 0 && wordContent.indexOf("项目来源类别") >= 0 && wordContent.indexOf("教育部聘请的学科专家通讯评审意见") >= 0) {
				return true;
			}
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return false;
	}
	
	/**
	 * 判断文件是否为“教育部人文社会科学研究项目专家鉴定意见表”
	 * @param file word文件
	 * @return
	 */
	public static boolean isReviewFile(File file) {
		WordExtractor wordExtractor;
		try {
			wordExtractor = new WordExtractor(new FileInputStream(file));
			String wordContent = wordExtractor.getTextFromPieces();
			if(wordContent.indexOf("专家鉴定意见表") > 0 && wordContent.indexOf("教育部人文社会科学研究项目") >= 0 && wordContent.indexOf("个人用") >= 0 && wordContent.indexOf("鉴定专家郑重承诺") >= 0) {
				return true;
			}
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return false;
	}
	
	/**
	 * 判断文件是否为“教育部人文社会科学研究项目专家鉴定意见书”
	 * @param file word文件
	 * @return
	 */
	public static boolean isAllReviewFile(File file) {
		WordExtractor wordExtractor;
		try {
			wordExtractor = new WordExtractor(new FileInputStream(file));
			String wordContent = wordExtractor.getTextFromPieces();
			if(wordContent.indexOf("专家鉴定意见书") > 0 && wordContent.indexOf("教育部人文社会科学研究项目") >= 0 && wordContent.indexOf("汇总用") >= 0 && wordContent.indexOf("鉴定专家分数和鉴定等级") >= 0) {
				return true;
			}
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return false;
	}
	
	/**
	 * 获取word文件最后修改时间，按照yyyyMMddHHmmss格式的字符串返回
	 * @param file
	 * @return
	 */
	public static String getFileLastModifyDate(File file) {
		DateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sf.format(new Date(file.lastModified()));
	}
}
