package org.csdc.controller.sm.docdata;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.csdc.bean.JsonData;
import org.csdc.controller.BaseController;
import org.csdc.domain.fm.DocUplaodForm;
import org.csdc.model.Account;
import org.csdc.service.imp.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 数据管理控制器
 * @author jintf
 * @date 2014-6-15
 */
@Controller
@RequestMapping ("/sm/docdata/dataprocess")
public class DataProcessController extends BaseController{
	public static String GROUP_ID ="background_import";
	@Autowired 
	private DocumentService documentService;


	/**
	 * 后台导入文档数据
	 * @param id
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/import", produces="application/json")
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_SM_DOCDATA_DATAPROCESS_IMPORT')")
	public Object backgroundImport(HttpServletRequest request,@RequestParam String categoryId,@RequestParam String path,DocUplaodForm docUplaodForm) throws IOException{
		Account account = getSessionAccount(request);
		documentService.backgroundImport(categoryId, path, docUplaodForm,account);
		return new JsonData();
	}
	
}
