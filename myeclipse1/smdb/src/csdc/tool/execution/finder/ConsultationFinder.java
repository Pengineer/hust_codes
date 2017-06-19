package csdc.tool.execution.finder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Consultation;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.StringTool;

/**
 * Consultation查找辅助类
 * @author xuhan
 *
 */
@Component
public class ConsultationFinder {
	
	/**
	 * [成果名+作者名+出版日期]到[ConsultationID]的映射
	 */
	private Map<String, String> map;
	
	@Autowired
	private IHibernateBaseDao dao;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	
	/**
	 * 根据[成果名][作者名][出版日期]找到Consultation实体
	 * @return
	 */
	public Consultation getConsultation(String consultationName, String authorName, Date pressDate, boolean addIfNotFound) {
		if (map == null) {
			initMap();
		}
		Consultation consultation = null;
		String key = StringTool.fix(consultationName + authorName + (pressDate != null ? sdf.format(pressDate) : ""));
		String consultationId = map.get(key);
		if (consultationId != null) {
			consultation = (Consultation) dao.query(Consultation.class, consultationId);
		}
		if (consultation == null && addIfNotFound) {
			consultation = new Consultation();
			consultation.setChineseName(consultationName);
			consultation.setAuthorName(authorName);
			consultation.setPublicationDate(pressDate);
			map.put(key, dao.add(consultation));
		}
		return consultation;
	}
	
	/**
	 * 初始化[成果名+作者名+出版日期]到[ConsultationID]的映射
	 * @return
	 */
	private void initMap() {
		map = new HashMap<String, String>();
		List<Object[]> list = dao.query("select consultation.chineseName, consultation.authorName, consultation.publicationDate, consultation.id from Consultation consultation");
		for (Object[] row : list) {
			String consultationName = (String) row[0];
			String authorName = (String) row[1];
			String pressDateString = row[2] != null ? sdf.format((Date) row[2]) : "";
			if (!consultationName.isEmpty()) {
				map.put(StringTool.fix(consultationName + authorName + pressDateString), (String) row[3]);
			}
		}
	}
 

}
