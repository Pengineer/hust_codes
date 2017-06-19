package csdc.action.award;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.action.project.ApplicationAction;
import csdc.bean.Expert;
import csdc.bean.ProjectApplication;
import csdc.bean.ProjectApplicationReview;
import csdc.bean.SystemOption;
import csdc.bean.common.ExpertLink;
import csdc.service.IExpertService;
import csdc.service.IInstpService;
import csdc.tool.execution.finder.InstpFinder;
import csdc.tool.execution.importer.Application2014Importer;
import csdc.tool.info.GlobalInfo;

/**
 */
@SuppressWarnings("unchecked")
public class MoeSocialAction extends BaseAction {
	
	public String testList() {
		return SUCCESS;
	}

	@Override
	public String pageName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] column() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String dateFormat() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] simpleSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] advSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String pageBufferId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}

