package csdc.service.imp;

import java.util.Date;

import csdc.bean.Memo;
import csdc.service.IMemoService;

public class MemoService extends BaseService implements IMemoService{
	/**
	 * 修改备忘
	 * @param orgMemoId原始备忘id
	 * @param memo待修改的备忘对象
	 */
	public void modifyMemo(String orgMemoId, Memo memo){
		Memo nowMemo = (Memo) dao.query(Memo.class,orgMemoId);//修改包括原来不需修改的部分和要修改的数据，所以再声明一个对象nowMemo
		//设置新的参数
		nowMemo.setTitle(memo.getTitle());
		nowMemo.setContent(memo.getContent());
		nowMemo.setUpdateTime(new Date());
		nowMemo.setIsRemind(memo.getIsRemind());
		if(memo.getIsRemind() == 0){
			nowMemo.setRemindWay(0);
		}else{
			switch(memo.getRemindWay()){//根据提醒方式添加修改
				case 1://按指定日期
					nowMemo.setRemindWay(memo.getRemindWay());
					nowMemo.setRemindTime(memo.getRemindTime());
					break;
				case 2://按天
					nowMemo.setRemindWay(memo.getRemindWay());
					nowMemo.setStartDateDay(memo.getStartDateDay());
					nowMemo.setEndDateDay(memo.getEndDateDay());
					nowMemo.setExcludeDate(memo.getExcludeDate().replace(",", ";"));
					break;
				case 3://按周
					nowMemo.setRemindWay(memo.getRemindWay());
					nowMemo.setStartDateWeek(memo.getStartDateWeek());
					nowMemo.setEndDateWeek(memo.getEndDateWeek());
					nowMemo.setWeek(memo.getWeek().replace(",", ";"));
					break;
				case 4://按月
					nowMemo.setRemindWay(memo.getRemindWay());
					nowMemo.setMonth(memo.getMonth().replace(",", ";"));
					break;
//				case 5://倒计时
//					nowMemo.setRemindWay(memo.getRemindWay());
//					nowMemo.setReverseRemindTime(memo.getReverseRemindTime());
//					break;
//				case 6://阴历
//					nowMemo.setRemindWay(memo.getRemindWay());
//					nowMemo.setRemindDay(memo.getRemindDay().replace(",", ";"));
//					break;
				default:
					break;	
			}
		}
		dao.modify(nowMemo);
	}
}