package baekjoon_resignation;

import java.util.*;

public class Solution1_dp {
	static int[] d = new int[20];
	static int[] t = new int[20];
	static int[] p = new int[20];
	static int n;
	static int ans = 0;

	static void go(int x, int sum) {
		if (x == n) {
			if (ans < sum)
				ans = sum;
			return;
		}
		if (x > n) {
			return;
		}
		go(x + 1, sum);
		go(x + t[x], sum + p[x]);
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		for (int i = 0; i < n; i++) {
			t[i] = sc.nextInt();
			p[i] = sc.nextInt();
		}
		go(0, 0);
		System.out.println(ans);
		sc.close();
	}
}
