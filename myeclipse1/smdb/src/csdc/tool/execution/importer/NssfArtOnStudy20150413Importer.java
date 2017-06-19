package csdc.tool.execution.importer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Nssf;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * Excel：《国家社科基金艺术学在研名单（截至2015年3月31日）_修正导入.xls》
 * @author pengliang
 * @status 
 * 备注：数据在库中存在的进行更新，不存在的在数据库中新建。
 */
public class NssfArtOnStudy20150413Importer extends Importer {
	
	private ExcelReader excelReader;
	
	@Autowired
	private Tool tool;
	
	private Map<String, Nssf> initNssfs;
	
	public NssfArtOnStudy20150413Importer() {}
	
	public NssfArtOnStudy20150413Importer(String filePath) {
		excelReader = new ExcelReader(filePath);
	}
	

	@Override
	public void work() throws Exception {
		initNssf();
		importData();
	}

	/**
	 * 正式导入数据
	 * @throws Exception 
	 */
	private void importData() throws Exception {
		excelReader.readSheet(0);
		Nssf nssf;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		
		while (next(excelReader)) {
			if (A == null || A.length() == 0) {
				break;
		    }
			
			if (G != null) {
				if (G.matches("[0-9]{4}")){  //四位：2010
					G = G + "-12-31";
				} else if (G.matches("[0-9]{4}[\\.-][0-9]{1}")) {//六位：2010.1 或则 2010-1
					G = G.replaceAll("\\.", "-0");
					G = getDays(G.substring(0,4), G.substring(5));
				} else if (G.matches("[0-9]{4}[\\.-][0-9]{2}")) {//七位：2010.10 或则2010-10
					G = G.replaceAll("\\.", "-");
					G = getDays(G.substring(0,4), G.substring(5));
				} else if (G.matches("[0-9]{4}[\\.][0-9]{2}[\\.][0-9]{2}")){
					G = G.replaceAll("\\.", "-");
				}
			}
			
			System.out.println(excelReader.getCurrentRowIndex() + "/"+excelReader.getRowNumber() + ":" + C);
			nssf = initNssfs.get(C + D + H + E);
			if (nssf == null) {
				Nssf newNssf = new Nssf();
				newNssf.setYear(B);
				newNssf.setName(C);
				newNssf.setApplicant(D);
				newNssf.setUnit(E);
				newNssf.setProvince(F);
				if (G != null && !G.isEmpty()){
					newNssf.setPlanEndDate(sdf.parse(G));
				}
				
				newNssf.setImportDate(new Date());
				newNssf.setIsDupCheckGeneral(1);
				newNssf.setSingleSubject("艺术学");
				newNssf.setStatus(1);
				dao.add(newNssf);
			} else {
				nssf.setYear(B);
				nssf.setName(C);
				nssf.setApplicant(D);
				nssf.setUnit(E);
				if (G != null && !G.isEmpty()){
					nssf.setPlanEndDate(sdf.parse(G));
				}
				nssf.setImportDate(new Date());
				nssf.setProvince(F);
				nssf.setIsDupCheckGeneral(1);
				nssf.setStatus(1);
				dao.modify(nssf);
			}

		}
		System.out.println("over");
		
	}
	
	public String getDays(String year, String month) {
		int days = getDayNumber(Integer.parseInt(year), Integer.parseInt(month));
		return year + "-" + month + "-" + days;
	}

	/** 
	 * 根据传入的年份和月份获得该月份的天数 
	 *  
	 * @param year 
	 *            年份-正整数 
	 * @param month 
	 *            月份-正整数 
	 * @return 返回天数 
	 */  
	public int getDayNumber(int year, int month) {  
	    int days[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };  
	    if (2 == month && 0 == (year % 4) && (0 != (year % 100) || 0 == (year % 400))) {  
	        days[1] = 29;  
	    }  
	    return (days[month - 1]);  
	} 
	
	public void initNssf() {
		Date begin = new Date();
		if(initNssfs == null) {
			initNssfs = new HashMap<String, Nssf>();
		}
		List<Nssf> nssfList = dao.query("select nssf from Nssf nssf");
		for(Nssf nssf : nssfList) {
			String name =nssf.getName();
			String applicant = nssf.getApplicant();
			String type = nssf.getType();
			String unit = nssf.getUnit();
			initNssfs.put(name + applicant + type + unit, nssf);
		}
		
		System.out.println("init Nssf complete! use time " + (new Date().getTime() - begin.getTime()) + "ms");
	}
}
