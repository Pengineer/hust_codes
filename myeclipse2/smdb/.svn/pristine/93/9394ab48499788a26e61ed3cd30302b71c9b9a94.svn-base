package csdc.tool.execution.finder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Paper;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.StringTool;

/**
 * Paper查找辅助类
 * @author xuhan
 *
 */
@Component
public class PaperFinder {
	
	/**
	 * [成果名+作者名+出版日期]到[PaperID]的映射
	 */
	private Map<String, String> map;
	
	@Autowired
	private IHibernateBaseDao dao;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	
	/**
	 * 根据[成果名][作者名][出版日期]找到Paper实体
	 * @return
	 */
	public Paper getPaper(String paperName, String authorName, Date pressDate, boolean addIfNotFound) {
		if (map == null) {
			initMap();
		}
		Paper paper = null;
		String key = StringTool.fix(paperName + authorName + (pressDate != null ? sdf.format(pressDate) : ""));
		String paperId = map.get(key);
		if (paperId != null) {
			paper = (Paper) dao.query(Paper.class, paperId);
		}
		if (paper == null && addIfNotFound) {
			paper = new Paper();
			paper.setChineseName(paperName);
			paper.setAuthorName(authorName);
			paper.setPublicationDate(pressDate);
			map.put(key, dao.add(paper));
		}
		return paper;
	}
	
	/**
	 * 初始化[成果名+作者名+出版日期]到[PaperID]的映射
	 * @return
	 */
	private void initMap() {
		map = new HashMap<String, String>();
		List<Object[]> list = dao.query("select paper.chineseName, paper.authorName, paper.publicationDate, paper.id from Paper paper");
		for (Object[] row : list) {
			String paperName = (String) row[0];
			String authorName = (String) row[1];
			String pressDateString = row[2] != null ? sdf.format((Date) row[2]) : "";
			if (!paperName.isEmpty()) {
				map.put(StringTool.fix(paperName + authorName + pressDateString), (String) row[3]);
			}
		}
	}
 

}
