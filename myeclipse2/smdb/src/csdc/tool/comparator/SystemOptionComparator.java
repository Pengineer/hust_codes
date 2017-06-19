package csdc.tool.comparator;

import java.util.Comparator;

import csdc.bean.SystemOption;

public class SystemOptionComparator implements Comparator<SystemOption> {
	public int compare(SystemOption o1, SystemOption o2) {
		return o1.getCode().compareTo(o2.getCode());
	}
}
