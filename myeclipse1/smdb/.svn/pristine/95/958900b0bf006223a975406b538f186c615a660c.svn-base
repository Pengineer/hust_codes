package csdc.tool.execution.importer;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.InstpGranted;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.reader.ExcelReader;

/**
 * 2015年的基地在研查重名单应该已评价中心的为准，当时以我们库里面在研的为准了。现在需要将评价中心提供的在研的基地项目对比我们
 * 库里面的基地项目，没有的项目就新建
 * @author pengliang
 *
 */

public class InstpProjectOnStudy201504Import extends Importer {
	
	private ExcelReader reader;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private InstpProjectFinder instpProjectFinder;

	@Override
	protected void work() throws Throwable {
		CheckExist();
	}
	
	public void CheckExist() throws Exception {
		reader.readSheet(1);
		
		int current =0;
		int total = reader.getRowNumber();
		Set<String> Msg = new HashSet<String>();
		
		while(next(reader)) {
			System.out.println((++current) + "/" + total);
			String number = G.trim();
			String name = C.trim();
			InstpGranted granted1 = instpProjectFinder.findGranted(number, name);
			if(granted1 == null){
				Msg.add("项目名+编号：" + name + ":" + number);
				InstpGranted granted2 = instpProjectFinder.findGranted(number);
				if(granted2 == null){
					Msg.add("项目编号：" + number);
				}
			}
		}
		if(Msg.size() > 0) {
			System.out.println(Msg.toString().replaceAll(",\\s+", "\n"));
		}
	}
	
	public InstpProjectOnStudy201504Import(){}
	
	public InstpProjectOnStudy201504Import(String fileName){
		reader = new ExcelReader(fileName);
	}
	

}
