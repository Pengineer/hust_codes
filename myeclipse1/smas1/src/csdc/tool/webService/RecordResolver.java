package csdc.tool.webService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.dom4j.Element;

import csdc.dao.IHibernateBaseDao;


public interface RecordResolver {
	public void parse(Element item, SimpleDateFormat sdf, IHibernateBaseDao dao, String projectType, List<String> badIDList) throws ParseException ;
}
