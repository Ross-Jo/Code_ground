package SE_honey;

import java.util.Scanner;

public class Solution {
	// N�� �ִ�
	static int MAXN = 10;
	// M�� �ִ�
	static int MAXM = 7;
	// ����� �� ����
	static int h[][] = new int[MAXN][MAXN];
	// ������ �ִ� ���� ����
	static int r[][] = new int[MAXN][MAXN];
	// �׽�Ʈ ���̽� ����
	static int tc, T;
	// ���� �׽�Ʈ ���̽��� N, M, C��
	static int N, M, C;
	
	// (y, x)�� ���� ���� ��ǥ�� �δ� ���뿡���� �ִ� ������ ����ϴ� ����Լ�
	static int getPsum(int m, int y, int x, int sum, int psum) {
		if (m >= M) return psum;
		// Ž���� ����� ������ �����ϴ� ����
		int nextSum;
		// �ִ� ������ �����ϴ� ����
		int max = 0;
		
		for (int i = m; i < M; ++i) {
			// C���� ���� �ʴ� ������� (y, x + i) ��ġ�� ��⸦ ä���غ� �� �ִ�.
			if (sum + h[y][x + i] <= C) {
				// (y, x+i) ��ġ�� ��⸦ ä���� �� ������ Ž���غ���.
				nextSum = getPsum(i + 1, y, x, sum + h[y][x + i], psum + h[y][x + i] * h[y][x + i]);
				// �ִ� ���� ���� ������ ��� ����
				if (max < nextSum) max = nextSum;
			}
			// C�� �Ѿ������ �Ǹ� �ش� ĭ�� ������ �� �� ����.
			nextSum = getPsum(i + 1, y, x, sum, psum);
			if (max < nextSum) max = nextSum;
		}
		return max;
	}
	
	/* �ָ��� �� : Ư�� ��ġ���� �����Ͽ� ���� ����� �ִ� ������ �̹� r[][]�� ������ ��������, 2��° ������ �� �����ʹ� '�ִ���Ͱ��'�� ���� �ݺ������� �� �ʿ䰡 ����
	 * �̿� ���� ����� ���� �ð� ������ �����ϴ�.
	 */
	
	// (y, x)�� ���� ���� ��ǥ�� �δ� ����� ���ÿ� ���� ������ ���� �� �ִ� ������ ���� ������ ������ ã�� �Լ�
	static int getMaxPair(int y, int x) {
		int maxR = 0;
		// �ϴ� ���� �࿡�� ���η� ���ÿ� ���ð����� ���� �� �ִ� ������ ã�´�.
		for (int j = x + M; j <= N - M; ++j) {
			if (maxR < r[y][j]) maxR = r[y][j];
		}
		// �ٸ� �࿡ �ִ� ������ ������ ���ÿ� ���ð����ϹǷ� ���Ͱ��� �� Ȯ���غ���.
		for (int i = y + 1; i < N; ++i) {
			for (int j = 0; j <= N - M; ++j) { // ��ȣ �� ����� ���̴� �͵鵵 ������ �߿��ϴ� �׻� ������ ��
				if (maxR < r[i][j]) maxR = r[i][j];
			}
		}
		return maxR;
	}
	
	// �׽�Ʈ ���̽��� Ǭ��
	static int solve() {
		// i: ���� ��ȸ�� ����, j: ���� ��ȸ�� ����
		int i, j;
		// ���ÿ� ���ð����� �� ������ ���� ���� ������ ����
		int sum;
		// �ִ� ���Ͱ�
		int ans = 0;

		// ��� ����, ���� ��ǥ�� ���� �����Ѵ�.
		for (i = 0; i < N; ++i) {
			// ��, (i, j)�� �����Ͽ� ���η� M���� ��Ⱑ �ڵ����õǴ� ���̹Ƿ�, ���� ������ �� �ִ� ����� ���� N-M+1������(M�� �ݵ�� N���Ϸθ� �־�����)
			for (j = 0; j <= N - M; ++j) {
				// (i, j)�� ���� ���� ��ǥ�� �δ� ������ ��ġ���� ���� �ִ� ������ ���Ѵ�.
				r[i][j] = getPsum(0, i, j, 0, 0);
			}
		}

		// ��� ����, ���� ��ǥ�� ���� ���ÿ� ���ð����� �� ������ ���� ���� �ִ��� ��츦 ã�´�.
		for (i = 0; i < N; ++i) {
			for (j = 0; j <= N - M; ++j) {
				// (i, j) ����� ���ÿ� ���ð����� ���� �� �ִ� ������ ���� ������ ������ ã�´�.
				
				/* �ռ� r[i][j]�� ���� Ư�� ��ġ���� ������ �߻� ������ ��쿡 ���Ͽ� ��� �������
				 * ���⿡�� �ش� ĭ������ �ִ� ������ �ٽ� �������ش�.(�ֳ��ϸ� ������ 2�� ������ �� �ֱ� �����̴�)
				 */
				
				sum = r[i][j] + getMaxPair(i, j);
				// �ִ� ������ ���ŵǴ��� Ȯ���Ѵ�.
				if (ans < sum) ans = sum;
			}
		}
		// �ִ� ���Ͱ��� ��ȯ�Ѵ�.
		return ans;
	}
	
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();

		for (int tx = 1; tx <= T; tx++) {
			N = sc.nextInt();
			M = sc.nextInt();
			C = sc.nextInt();

			// �� (y, x)�� ���� ��ǥ�� �ϴ� ������ �ִ� ���� ������ �ʱ�ȭ �Ѵ�.
			for (int i = 0; i < N; ++i) for (int j = 0; j < N; ++j) r[i][j] = 0;
			// �� ��⺰ ���� �Է��� �Է¹޴´�
			for (int i = 0; i < N; ++i) for (int j = 0; j < N; ++j) h[i][j] = sc.nextInt();
			// �׽�Ʈ ���̽��� ���� ����Ѵ�
			System.out.println("#" + tx + " " + solve());
		}
		sc.close();
	}
}