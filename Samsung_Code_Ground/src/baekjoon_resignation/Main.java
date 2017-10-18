package baekjoon_resignation;

import java.util.Scanner;

// 퇴사 문제를 푸는 완전탐색 방법
public class Main {
	public static int N;
	public static int[][] table;
	public static int max;

	public static void findMaxOutput(int index, int[] choose) {
		if (index >= N) { // 인덱스가 N을 넘어가게 되는 경우, 해당 케이스의 최대 이익을 계산하고 이 값이 현재까지의 최대 이익 기록보다 크다면 해당 기록을 갱신한다.
			int tmp_sum = 0;
			for (int i = 0; i < N; i++) {
				if (choose[i] == 1)
					tmp_sum += table[1][i];
			}
			max = Math.max(max, tmp_sum);
			return;
		}

		if (index >= 0 && index < N) {
			choose[index] = 0;
			findMaxOutput(index + 1, choose);

			if (index + table[0][index] <= N) { // 마지막 날짜에 대하여 소요기간이 1일인 경우, 유의하자 : 그래서 <이 아닌 <=기호를 사용
				                                // 상담일 N에 1일 만큼의 상담 시간이 소요된다면 index는 N+1이기 때문
				choose[index] = 1;
				findMaxOutput(index + table[0][index], choose);
				choose[index] = 0; // 스택을 통해 상태복구를 하고 싶다면 선택에 대한 해제 부분을 반드시 고려해야 한다
			}
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		N = sc.nextInt();
		table = new int[2][N];
		max = 0;

		for (int i = 0; i < N; i++) {
			table[0][i] = sc.nextInt();
			table[1][i] = sc.nextInt();
		}

		int[] choose = new int[N];
		findMaxOutput(0, choose);

		System.out.println(max);

		sc.close();
	}
}