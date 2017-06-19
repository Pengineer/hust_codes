package csdc.tool.crawler;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import net.sf.json.JSONObject;

import csdc.tool.ApplicationContainer;
import csdc.tool.crawler.validator.PageValidator;
import csdc.tool.webService.ClientHandler;
import csdc.tool.webService.sinoss.SinossServiceService;

/**
 * 附件下载
 * 利用多线程的思想
 * @author suwb
 *
 */
@Component
@Scope("prototype")
public class AttachmentDownload  extends Crawler{

	@Override
	Class<? extends PageValidator> getPageValidatorClass() {
		// TODO Auto-generated method stub
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

		Map arguments = JSONObject.fromObject(crawlTask.getArguments());
		String batchId = arguments.get("batchId").toString();
		String applyDocName = arguments.get("applyDocName").toString();
		String attachmentType = crawlTask.getTaskType();

		String filename;
		if(attachmentType.equals("base")){
			filename = ApplicationContainer.sc.getRealPath("/system/interfaces/sinossDownload/" + applyDocName + ".doc");
		}else {
			filename = ApplicationContainer.sc.getRealPath("/system/interfaces/sinossDownload/" + applyDocName);
		}
		FileOutputStream output = new FileOutputStream(filename); 
		try {
			byte[] fileByte = service.getSinossServicePort().getApplyAttachment("gener", batchId, applyDocName);
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
