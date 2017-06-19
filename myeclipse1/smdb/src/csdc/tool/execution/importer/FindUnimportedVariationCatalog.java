package csdc.tool.execution.importer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.GeneralGranted;
import csdc.bean.GeneralVariation;
import csdc.bean.InstpGranted;
import csdc.bean.InstpVariation;
import csdc.bean.SinossMembers;
import csdc.bean.SinossModifyContent;
import csdc.bean.SinossProjectVariation;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 查找出未入库的变更数据
 * @author liangjian
 *
 */
@Component
public class FindUnimportedVariationCatalog extends Importer{

	@Autowired
	private InstpProjectFinder instpProjectFinder;
	
	@Autowired
	private GeneralProjectFinder generalProjectFinder;
	
	@Autowired
	private Tool tool;
	
	List<String> errorDatas = new ArrayList<String>();
	
	List<SinossProjectVariation> errorProjects = new ArrayList<SinossProjectVariation>();
	
	@Override
	protected void work() throws Throwable {
		errorDatas.clear();
		findVariation();
		printErrorDatas();
	}
	
    public void findVariation(){
    	List<SinossProjectVariation> sinossProjectVariations =(List<SinossProjectVariation>)dao.query("select sv from SinossProjectVariation sv where to_char(sv.dumpDate)='02-9月 -14'");
    	int totalNumber = sinossProjectVariations.size();
    	int currentNumber =0;
    	for(SinossProjectVariation spv : sinossProjectVariations){
    		System.out.println((++currentNumber) + "/" + totalNumber);
    		String variationId = spv.getId();
    		String projectType = spv.getTypeCode();
    		String codeString = spv.getCode();
    		boolean flag = false;
    		
    		if (projectType.contains("base")) {
    			InstpGranted granted = instpProjectFinder.findGranted(codeString);
    			Set<InstpVariation> instpVariations = granted.getInstpVariation();
    			
	    		List<SinossModifyContent> sinossModifyContents = (List<SinossModifyContent>) dao.query("from SinossModifyContent o where o.projectVariation.id = ? ",variationId);
	    		List<SinossMembers> sinossInstpMembers = (List<SinossMembers>)dao.query("from SinossMembers o where o.projectVariation.id = ? ",variationId);
	    		if (sinossInstpMembers.size() != 0) { //变更项目成员
	    			flag = false;
	    			for(InstpVariation iv : instpVariations){
	    				if(iv.getImportedDate().after(tool.getDate(2014, 10, 1)) && iv.getChangeMember() == 1){
	    					flag = true;
	    				}
	    			}
	    			if(flag == false){
	    				errorDatas.add(variationId + ":" + spv.getName() + "::变更项目成员");
	    			}
	    		}
	    		//变更其他类型
    			for(int j = 0;j < sinossModifyContents.size();j++){ 
    				SinossModifyContent sinossModifyContent = sinossModifyContents.get(j);
					String modifyMean = sinossModifyContent.getModifyFieldMean();
					if (modifyMean.contains("其他")) {
						flag = false;
						for(InstpVariation iv : instpVariations){
		    				if(iv.getImportedDate().after(tool.getDate(2014, 10, 1)) && iv.getOther() == 1){
		    					flag = true;
		    					break;
		    				}
		    			}
						if(flag == false){
		    				errorDatas.add(variationId + ":" + spv.getName() + "::其他");
		    			}
					} else if (modifyMean.contains("延期")) {	
						flag = false;
						for(InstpVariation iv : instpVariations){
		    				if(iv.getImportedDate().after(tool.getDate(2014, 10, 1)) && iv.getPostponement() == 1){
		    					flag = true;
		    					break;
		    				}
		    			}
						if(flag == false){
		    				//errorDatas.add(variationId + ":" + spv.getName() + "::延期");
							errorDatas.add(spv.getProjectGranted().getId() + "::" + spv.getName());
		    			}
					} else if (modifyMean.contains("改变项目名称")) {
						flag = false;
						for(InstpVariation iv : instpVariations){
		    				if(iv.getImportedDate().after(tool.getDate(2014, 10, 1)) && iv.getChangeName() == 1){
		    					flag = true;
		    					break;
		    				}
		    			}
						if(flag == false){
		    				errorDatas.add(variationId + ":" + spv.getName() + "::改变项目名称");
		    			}
					} else if (modifyMean.contains("申请撤项")) {
						flag = false;
						for(InstpVariation iv : instpVariations){
		    				if(iv.getImportedDate().after(tool.getDate(2014, 10, 1)) && iv.getWithdraw() == 1){
		    					flag = true;
		    					break;
		    				}
		    			}
						if(flag == false){
		    				errorDatas.add(variationId + ":" + spv.getName() + "::申请撤项");
		    			}
					} else if (modifyMean.contains("重大研究内容调整")) {
						flag = false;
						for(InstpVariation iv : instpVariations){
		    				if(iv.getImportedDate().after(tool.getDate(2014, 10, 1)) && iv.getChangeContent() == 1){
		    					flag = true;
		    					break;
		    				}
		    			}
						if(flag == false){
		    				errorDatas.add(variationId + ":" + spv.getName() + "::重大研究内容调整");
		    			}
					} else if (modifyMean.contains("改变成果形式")) {
						flag = false;
						for(InstpVariation iv : instpVariations){
		    				if(iv.getImportedDate().after(tool.getDate(2014, 10, 1)) && iv.getChangeProductType() == 1){
		    					flag = true;
		    					break;
		    				}
		    			}
						if(flag == false){
		    				errorDatas.add(variationId + ":" + spv.getName() + "::改变成果形式");
		    			}
					} else if (modifyMean.contains("变更项目管理单位")) {
						flag = false;
						for(InstpVariation iv : instpVariations){
		    				if(iv.getImportedDate().after(tool.getDate(2014, 10, 1)) && iv.getChangeAgency() == 1){
		    					flag = true;
		    					break;
		    				}
		    			}
						if(flag == false){
		    				errorDatas.add(variationId + ":" + spv.getName() + "::变更项目管理单位");
		    			}
					} else if (modifyMean.contains("变更项目责任人")) {
						flag = false;
						for(InstpVariation iv : instpVariations){
		    				if(iv.getImportedDate().after(tool.getDate(2014, 10, 1)) && iv.getChangeMember() == 1){
		    					flag = true;
		    					break;
		    				}
		    			}
						if(flag == false){
		    				errorDatas.add(variationId + ":" + spv.getName() + "::变更项目责任人");
		    			}
					}
    			}
	    		
    		}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////    		
    		if (projectType.contains("gener")) {
    			GeneralGranted granted = generalProjectFinder.findGranted(codeString);
    			Set<GeneralVariation> generVariations = granted.getGeneralVariation();
    			
	    		List<SinossModifyContent> sinossModifyContents = (List<SinossModifyContent>) dao.query("from SinossModifyContent o where o.projectVariation.id = ? ",variationId);
	    		
	    		if (sinossModifyContents.size() == 0) { //变更项目成员
	    			flag = false;
	    			for(GeneralVariation gv : generVariations){
	    				if(gv.getImportedDate().after(tool.getDate(2014, 10, 1)) && gv.getChangeMember() == 1){
	    					flag = true; 
	    				}
	    			}
	    			if(flag == false){
	    				errorDatas.add(variationId + ":" + spv.getName() + "::变更项目成员");
	    			}
	    		} 
	    		
    			for(int j = 0;j < sinossModifyContents.size();j++){ 
    				SinossModifyContent sinossModifyContent = sinossModifyContents.get(j);
					String modifyMean = sinossModifyContent.getModifyFieldMean();
					if (modifyMean.contains("其他")) {
						flag = false;
						for(GeneralVariation gv : generVariations){
		    				if(gv.getImportedDate().after(tool.getDate(2014, 10, 1)) && gv.getOther() == 1){
		    					flag = true;
		    					break;
		    				}
		    			}
						if(flag == false){
		    				errorDatas.add(variationId + ":" + spv.getName() + "::其他");
		    			}
					} else if (modifyMean.contains("延期")) {	
						flag = false;
						for(GeneralVariation gv : generVariations){
		    				if(gv.getImportedDate().after(tool.getDate(2014, 10, 1)) && gv.getPostponement() == 1){
		    					flag = true;
		    					break;
		    				}
		    			}
						if(flag == false){
		    				//errorDatas.add(variationId + ":" + spv.getName() + "::延期");
							errorDatas.add(spv.getProjectGranted().getId() + "::" + spv.getName());
		    			}
					} else if (modifyMean.contains("改变项目名称")) {
						flag = false;
						for(GeneralVariation gv : generVariations){
		    				if(gv.getImportedDate().after(tool.getDate(2014, 10, 1)) && gv.getChangeName() == 1){
		    					flag = true;
		    					break;
		    				}
		    			}
						if(flag == false){
		    				errorDatas.add(variationId + ":" + spv.getName() + "::改变项目名称");
		    			}
					} else if (modifyMean.contains("申请撤项")) {
						flag = false;
						for(GeneralVariation gv : generVariations){
		    				if(gv.getImportedDate().after(tool.getDate(2014, 10, 1)) && gv.getWithdraw() == 1){
		    					flag = true;
		    					break;
		    				}
		    			}
						if(flag == false){
		    				errorDatas.add(variationId + ":" + spv.getName() + "::申请撤项");
		    			}
					} else if (modifyMean.contains("重大研究内容调整")) {
						flag = false;
						for(GeneralVariation gv : generVariations){
		    				if(gv.getImportedDate().after(tool.getDate(2014, 10, 1)) && gv.getChangeContent() == 1){
		    					flag = true;
		    					break;
		    				}
		    			}
						if(flag == false){
		    				errorDatas.add(variationId + ":" + spv.getName() + "::重大研究内容调整");
		    			}
					} else if (modifyMean.contains("改变成果形式")) {
						flag = false;
						for(GeneralVariation gv : generVariations){
		    				if(gv.getImportedDate().after(tool.getDate(2014, 10, 1)) && gv.getChangeProductType() == 1){
		    					flag = true;
		    					break;
		    				}
		    			}
						if(flag == false){
		    				errorDatas.add(variationId + ":" + spv.getName() + "::改变成果形式");
		    			}
					} else if (modifyMean.contains("变更项目管理单位")) {
						flag = false;
						for(GeneralVariation gv : generVariations){
		    				if(gv.getImportedDate().after(tool.getDate(2014, 10, 1)) && gv.getChangeAgency() == 1){
		    					flag = true;
		    					break;
		    				}
		    			}
						if(flag == false){
		    				errorDatas.add(variationId + ":" + spv.getName() + "::变更项目管理单位");
		    			}
					} else if (modifyMean.contains("变更项目责任人")) {
						flag = false;
						for(GeneralVariation gv : generVariations){
		    				if(gv.getImportedDate().after(tool.getDate(2014, 10, 1)) && gv.getChangeMember() == 1){
		    					flag = true;
		    					break;
		    				}
		    			}
						if(flag == false){
		    				errorDatas.add(variationId + ":" + spv.getName() + "::变更项目责任人");
		    			}
					}
    			}
    		}
		}
    	
    }
    
    public void printErrorDatas(){
    	for(String str : errorDatas){
    		str = str.replaceAll("^[0-9a-z]+[:]", "");
    		//str = str.replaceAll(":{2}.+$", "");
    		System.out.println(str);
    	}
    	System.out.println(errorDatas.size());
    }
    
    public static void main(String[] args){
    	String str = "123::有";
    	str = str.replaceAll(":{2}.+$", "");
    	System.out.println(str);
    }
}
