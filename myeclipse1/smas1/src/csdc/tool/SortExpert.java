package csdc.tool;
import java.util.Comparator;

/**
 * 专家树中专家项ExpertTreeItem匹配规则
 *
 */
public class SortExpert implements Comparator<ExpertTreeItem>{
	public int compare(ExpertTreeItem o1, ExpertTreeItem o2) {
		
		String disciplineCode1 = o1.discipline;
		String disciplineCode2 = o2.discipline;
		
		String levelOne1 = disciplineCode1.substring(0, 3);
		String levelOne2 = disciplineCode2.substring(0, 3);
		String levelTwo1 = disciplineCode1.substring(3, 5);
		String levelTwo2 = disciplineCode2.substring(3, 5);
		String levelThr1 = disciplineCode1.substring(5, 7);
		String levelThr2 = disciplineCode2.substring(5, 7);
		
		if(levelOne1.equals("000") && (!levelOne2.equals("000"))){
			return 1;//无学科代码的排在最后
		}else if(levelOne2.equals("000") && (!levelOne1.equals("000"))){
			return -1;
		}else if(levelOne1.equals(levelOne2)) {// 一级学科相同
			if( levelTwo1.equals("00") && (!levelTwo2.equals("00"))){
				return 1;
			}else if(levelTwo2.equals("00") && (!levelTwo1.equals("00"))){
				return -1;
			}else if(levelTwo1.equals(levelTwo2)) {// 二级学科相同
				if(levelThr1.equals("00") && (!levelThr2.equals("00"))){
					return 1;
				}else if(levelThr2.equals("00") && (!levelThr1.equals("00"))){
					return -1;
				}else{
					int result = disciplineCode1.compareTo(disciplineCode2);
					if(result == 0) {//如果学科代码相同，则比较高校代码，小者排在前面
						/*//先按高校名称的拼音排序，若高校相同，则按照专家姓名的拼音排序
						if(PinyinCommon.getPinYin(o1.university).compareTo(PinyinCommon.getPinYin(o2.university)) < 0)
							return -1;
						else if(PinyinCommon.getPinYin(o1.university).equals(PinyinCommon.getPinYin(o2.university))) {
							if(PinyinCommon.getPinYin(o1.name).compareTo(PinyinCommon.getPinYin(o2.name)) < 0)
								return -1;
							else
								return 1;
						}
						else */
						return o1.universityAcronym.compareTo(o2.universityAcronym);
					}
					else
						return result;
				}
			}
			else {//二级学科不同
				int result = disciplineCode1.compareTo(disciplineCode2);
				if(result == 0) {//如果学科代码相同，则比较高校代码，小者排在前面
					/*//先按高校名称的拼音排序，若高校相同，则按照专家姓名的拼音排序
					if(PinyinCommon.getPinYin(o1.university).compareTo(PinyinCommon.getPinYin(o2.university)) < 0)
						return -1;
					else if(PinyinCommon.getPinYin(o1.university).equals(PinyinCommon.getPinYin(o2.university))) {
						if(PinyinCommon.getPinYin(o1.name).compareTo(PinyinCommon.getPinYin(o2.name)) < 0)
							return -1;
						else
							return 1;
					}
					else */
					return o1.universityAcronym.compareTo(o2.universityAcronym);
				}
				else
					return result;
			}
		}
		else {//一级学科不同
			int result = disciplineCode1.compareTo(disciplineCode2);
			if(result == 0) {//如果学科代码相同，则比较高校代码，小者排在前面
				/*if(PinyinCommon.getPinYin(o1.university).compareTo(PinyinCommon.getPinYin(o2.university)) < 0)
					return -1;
				else if(PinyinCommon.getPinYin(o1.university).equals(PinyinCommon.getPinYin(o2.university))) {
					if(PinyinCommon.getPinYin(o1.name).compareTo(PinyinCommon.getPinYin(o2.name)) < 0)
						return -1;
					else
						return 1;
				}
				else */
				return o1.universityAcronym.compareTo(o2.universityAcronym);
			}
			else
				return result;
		}
	}
}
