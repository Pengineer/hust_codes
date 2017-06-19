package csdc.tool;

import java.util.Scanner;

/**
 * 专家评审建议通过率 计算器
 * 
 * @see ReviewProbabilityCalculator#solve(int, int, double)
 * @author xuhan
 */
public class ReviewProbabilityCalculator {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.print("Input n, k, x:");
			int n = scanner.nextInt();
			int k = scanner.nextInt();
			double x = scanner.nextDouble();
			System.out.println("p = " + new ReviewProbabilityCalculator().solve(n, k, x));
		}
	}
	
	/**
	 * 每个项目由n个专家评审；<br>
	 * 每个项目的分数为同意其立项的专家的个数；<br>
	 * 每个专家同意一个项目立项的概率为p，每个专家的意见相互独立；<br>
	 * 若项目的分数不小于k，则让该项目立项；<br>
	 * 若希望项目整体立项率为x，求p<br>
	 * <br>
	 * 注意，该算法假设每个项目水平相同，即每个专家对每个项目评审的通过率均为p<br>
	 * （显然这不符合实际情况）<br>
	 * <br>
	 * 算法:由于x和p正相关，故使用二分法
	 * 
	 * @param n
	 * @param k
	 * @param x
	 * @return p
	 */
	private double solve(int n, int k, double x) {
		double lowP = 0.0;
		double highP = 1.0;
		int tryTimes = 100;
		while (tryTimes-- > 0) {
			double midP = (lowP + highP) / 2.0;
			double accumulateProbability = 0.0;
			for (int i = k; i <= n; i++) {
				accumulateProbability += choose(n, i) * Math.pow(midP, i) * Math.pow(1 - midP, n - i);
			}
			if (accumulateProbability > x) {
				highP = midP;
			} else {
				lowP = midP;
			}
			System.out.println(lowP);
		}
		return lowP;
	}

	/**
	 * 组合数，从a个数里选b个的方案数
	 * @param a
	 * @param b
	 * @return C(a, b)
	 */
	private long choose(int a, int b) {
		return b == 0 ? 1 : choose(a - 1, b - 1) * a / b;
	}

}
