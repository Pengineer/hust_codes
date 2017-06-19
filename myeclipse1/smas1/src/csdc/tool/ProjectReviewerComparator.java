package csdc.tool;

import java.util.Comparator;

import csdc.bean.GeneralReviewer;

public class ProjectReviewerComparator implements Comparator<GeneralReviewer>{
	public int compare(GeneralReviewer o1, GeneralReviewer o2) {
//		return o1.getPriority().compareTo(o2.getPriority());
		return 1;
	}
}
