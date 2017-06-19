package csdc.tool.crawler;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import net.sf.json.JSONObject;

import csdc.bean.SinossProjectApplication;
import csdc.bean.SinossProjectEndinspection;
import csdc.bean.SinossProjectMidinspection;
import csdc.tool.ApplicationContainer;
import csdc.tool.crawler.validator.PageValidator;
import csdc.tool.webService.ClientHandler;
import csdc.tool.webService.sinoss.SinossServiceService;

/**
 * 附件下载
 * 利用多线程的思想
 * 申请申请书附件命名规则见projectService第5151行uploadAppFile方法
 * 中检申请书附件命名规则见projectService第5387行uploadMidFile方法
 * 结项申请书附件命名规则见projectService第5489行uploadEndFile方法 
 * @author suwb
 *
 */
@Component
@Scope("prototype")
public class AttachmentDownload extends Crawler{

	@Override
	Class<? extends PageValidator> getPageValidatorClass() {
		return null;
	}

	@Override
	protected void work() throws Exception {
    	SinossServiceService service=new SinossServiceService();

        //向SOAP添加表头
    	service.setHandlerResolver(new HandlerResolver(){
    		public List<Handler> getHandlerChain(PortInfo portInfo) {
    			List<Handler> handlerList = new ArrayList<Handler>();
    			//添加认证信息
    			handlerList.add(new ClientHandler());
    			return handlerList;
            }
        });

    	SimpleDateFormat s = new SimpleDateFormat("yyyy");
		String year = s.format(new Date());
		Map arguments = JSONObject.fromObject(crawlTask.getArguments());
		String batchId = arguments.get("batchId").toString();//申请附件id
		String applyDocName = arguments.get("applyDocName").toString();//申请附件后缀
		String midReportName = arguments.get("midReportName").toString();//中检附件id
		String storageFileName = arguments.get("storageFileName").toString();//中检附件后缀
		String finishReportId = arguments.get("finishReportId").toString();//结项附件id
		String attachmentType = crawlTask.getTaskType();

		Map jsonMap = new HashMap();
		jsonMap.put("storageFileName", storageFileName);
		jsonMap.put("applyDocName", applyDocName);
		jsonMap.put("year", year);
		jsonMap.put("finishReportId", finishReportId);
		
		String filename = "";
		byte[] fileByte = null;
		FileOutputStream output = null;
		if(attachmentType.contains("Apply")){
			String projectType = "";
			if(attachmentType.contains("gener")){
				projectType = "gener";
			}else if(attachmentType.contains("special")){
				projectType = "special";
			}else {
				projectType = "base";
			}
			if(attachmentType.contains("gener")){
				fileByte = service.getSinossServicePort().getApplyAttachment("gener", batchId, applyDocName);
			}else if(attachmentType.contains("special")){
				fileByte = service.getSinossServicePort().getApplyAttachment("special", batchId, applyDocName);
			}else {
				fileByte = service.getSinossServicePort().getAttachment(applyDocName);
				applyDocName+=".doc"; 
			}
			String realName = applyDocName;
			String filepath = projectType + "/app/" + year + "/";
			filepath = "upload/sinoss/" + filepath + realName;
			filename = ApplicationContainer.sc.getRealPath(filepath.replace('\\', '/'));
			SinossProjectApplication spa = (SinossProjectApplication)hibernateBaseDao.queryUnique("select spa from" +
					" SinossProjectApplication spa where spa.applyDocName =:applyDocName and spa.year =:year", jsonMap);
			spa.setFile(filepath);
			hibernateBaseDao.modify(spa);
		}else if(attachmentType.contains("Mid")){
			String projectType = "";
			if(attachmentType.contains("base")){
				projectType = "base";
			}else projectType = "gener";
			String realName = storageFileName;
			String filepath = projectType + "/mid/" + year + "/";
			filepath = "upload/sinoss/" + filepath + realName;
			filename = ApplicationContainer.sc.getRealPath(filepath.replace('\\', '/'));
			fileByte = service.getSinossServicePort().getAttachment(midReportName);
			SinossProjectMidinspection spm = (SinossProjectMidinspection)hibernateBaseDao.queryUnique("select spm from " +
					"SinossProjectMidinspection spm where spm.storageFileName =:storageFileName and spm.batchYear =:year", jsonMap);
			spm.setFile(filepath);
			hibernateBaseDao.modify(spm);
		//注：目前为止还不知道结项项目的附件后缀 待完善
		}else if(attachmentType.contains("End")){
			String projectType = "";
			if(attachmentType.contains("base")){
				projectType = "base";
			}else projectType = "gener";
			String realName = finishReportId;
			String filepath = projectType + "/end/" + year + "/";
			filepath = "upload/sinoss/" + filepath + realName;
			filename = ApplicationContainer.sc.getRealPath(filepath.replace('\\', '/'));
			fileByte = service.getSinossServicePort().getAttachment(finishReportId);
			SinossProjectEndinspection spe = (SinossProjectEndinspection)hibernateBaseDao.queryUnique("select spe from" +
					" SinossProjectEndinspection spe where spe.finishReportId =:finishReportId", jsonMap);
			spe.setFile(filepath);
			hibernateBaseDao.modify(spe);
		}
		output = new FileOutputStream(filename);
		try {
			output.write(fileByte);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e.getMessage());
		}finally {
			output.close();				
		}
		crawlTask.setFinishTime(new Date());
		hibernateBaseDao.modify(crawlTask);
	}

}
