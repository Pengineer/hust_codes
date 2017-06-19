package csdc.tool.execution.finder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Book;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.StringTool;

/**
 * Book查找辅助类
 * @author xuhan
 *
 */
@Component
public class BookFinder {
	
	/**
	 * [成果名+作者名+出版日期]到[BookID]的映射
	 */
	private Map<String, String> map;
	
	@Autowired
	private IHibernateBaseDao dao;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	
	/**
	 * 根据[成果名][作者名][出版日期]找到Book实体
	 * @return
	 */
	public Book getBook(String bookName, String authorName, Date pressDate, boolean addIfNotFound) {
		if (map == null) {
			initMap();
		}
		Book book = null;
		String key = StringTool.fix(bookName + authorName + (pressDate != null ? sdf.format(pressDate) : ""));
		String bookId = map.get(key);
		if (bookId != null) {
			book = (Book) dao.query(Book.class, bookId);
		}
		if (book == null && addIfNotFound) {
			book = new Book();
			book.setChineseName(bookName);
			book.setAuthorName(authorName);
			book.setPublishDate(pressDate);
			map.put(key, dao.add(book));
		}
		return book;
	}
	
	/**
	 * 初始化[成果名+作者名+出版日期]到[BookID]的映射
	 * @return
	 */
	private void initMap() {
		map = new HashMap<String, String>();
		List<Object[]> list = dao.query("select book.chineseName, book.authorName, book.publishDate, book.id from Book book");
		for (Object[] row : list) {
			String bookName = (String) row[0];
			String authorName = (String) row[1];
			String pressDateString = row[2] != null ? sdf.format((Date) row[2]) : "";
			if (!bookName.isEmpty()) {
				map.put(StringTool.fix(bookName + authorName + pressDateString), (String) row[3]);
			}
		}
	}
 

}
