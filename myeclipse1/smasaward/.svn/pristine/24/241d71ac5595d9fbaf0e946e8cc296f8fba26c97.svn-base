package csdc.tool.matcher.constraint;

import csdc.tool.matcher.DisciplineBasedMatcher;
import csdc.tool.matcher.Reviewer;
import csdc.tool.matcher.Subject;

/**
 * 匹配器限制条件
 * 有如下几个作用:
 * 1. 在给定限制级(constrainLevel)下，判断一个项目和专家之间若建立匹配是否突破约束
 * 2. (可选)判断一个项目当前的匹配情况是否突破了约束
 * 3. (可选)给出突破约束后的警告信息，也即该约束条件内容的描述
 * 
 * @author xuhan
 */
public abstract class Constraint {
	
	protected DisciplineBasedMatcher matcher;
	
	public void setMatcher(DisciplineBasedMatcher matcher) {
		this.matcher = matcher;
	}

	/**
	 * 根据constraintLevel决定该限制条件是否要起作用
	 */
	private Filter filter;
	
	/**
	 * 构造器，传入一个“有效判断过滤器”
	 * @param filter
	 */
	public Constraint(Filter filter) {
		this.filter = filter;
	}

	/**
	 * 无参构造器，表示始终有效
	 */
	public Constraint() {
		this.filter = new Filter() {
			public boolean shouldWork(Integer constraintLevel) {
				return true;
			}
		};
	}

	/**
	 * 在shouldWork返回false(即：限制级不起作用)的情况下，直接通过，返回true <br />
	 * 在shouldWork返回true (即：限制级起作用)的情况下，再判断subject和reviewer是否通过条件
	 * @param subject 评审对象
	 * @param reviewer 评审专家
	 * @param constraintLevel 限制级
	 * @return
	 */
	public boolean pass(Subject subject, Reviewer reviewer, Integer constraintLevel) {
		return !filter.shouldWork(constraintLevel) || pass(subject, reviewer);
	}
	
	/**
	 * 在不考虑限制级的前提下，subject和reviewer是否通过条件
	 * @param subject 评审对象
	 * @param reviewer 评审专家
	 * @return
	 */
	public abstract boolean pass(Subject subject, Reviewer reviewer);
	
	/**
	 * 某评审对象是否突破了该限制，一般用于匹配结束后添加警告信息用
	 * @param subject 评审对象
	 * @return
	 */
	public abstract boolean broken(Subject subject);
	
	/**
	 * 如果突破，返回的警告信息
	 * @return
	 */
	public abstract String breakWarnInfo();

}
