package baekjoon_resignation;

import java.util.Scanner;

// ��� ������ Ǫ�� ����Ž�� ���
public class Main {
	public static int N;
	public static int[][] table;
	public static int max;

	public static void findMaxOutput(int index, int[] choose) {
		if (index >= N) { // �ε����� N�� �Ѿ�� �Ǵ� ���, �ش� ���̽��� �ִ� ������ ����ϰ� �� ���� ��������� �ִ� ���� ��Ϻ��� ũ�ٸ� �ش� ����� �����Ѵ�.
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

			if (index + table[0][index] <= N) { // ������ ��¥�� ���Ͽ� �ҿ�Ⱓ�� 1���� ���, �������� : �׷��� <�� �ƴ� <=��ȣ�� ���
				                                // ����� N�� 1�� ��ŭ�� ��� �ð��� �ҿ�ȴٸ� index�� N+1�̱� ����
				choose[index] = 1;
				findMaxOutput(index + table[0][index], choose);
				choose[index] = 0; // ������ ���� ���º����� �ϰ� �ʹٸ� ���ÿ� ���� ���� �κ��� �ݵ�� ����ؾ� �Ѵ�
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