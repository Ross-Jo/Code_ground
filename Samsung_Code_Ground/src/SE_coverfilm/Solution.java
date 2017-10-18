package SE_coverfilm;

import java.util.Scanner;

public class Solution {
	static Scanner sc = new Scanner(System.in);
	
	// W 너비, D 겹 보호필름
	// D : 보호필름의 두께
	// W : 보호필름의 너비
	// K : 보호필름의 합격기준
	static final int DSIZE = 13;
	static final int WSIZE = 20;
	static int D, W, K;

	// film : 필름을 2차원으로 저장
	// minChemicalCnt : 보호필름 조건에 만족하는 화학처리 횟수의 최소값을 기록
	// chemical = 각 번호의 엷은 막이 어떤 화학처리를 했는지 기록(0 : 약품 A 투입, 1: 약품 B 투입, 2: 약품처리 안함)
	static int[][] film = new int[DSIZE][WSIZE];
	static int minChemicalCnt;
	static int[] chemical = new int[DSIZE];
	
	// solve : 문제를 재귀적으로 풀기 위한 함수다.
	// curD : d번째 필름의 엷은막을 살펴볼 차례
	// chemicalCnt : 약품 처리 횟수
	// prevContinuum : 이전까지 세로 방향으로 연속한 동일 특성 셀의 개수를 저장
	// prevMaxContinuum : 이전까지 세로 방향으로 연속한 동일 특성 셀의 최대 개수를 저장
	static void solve(int curD, int chemicalCnt, int[] prevContinuum, int[] prevMaxContinuum) {
		// 종료조건 1.
		// 사용한 약품 횟수가 현재까지의 약품 최소 처리 횟수 이상이면 탐색하지 않는다. 
		if (chemicalCnt >= minChemicalCnt) return;
		
		// 종료조건 2.
		// 필름을 모두 다 보았을 때
		if (curD == D) {
			// 합격 기준을 모두 만족시키지 못하는지 확인
			boolean isSatisfied = true;
			for (int i = 0; i < W; i++) {
				if (prevMaxContinuum[i] < K) {
					isSatisfied = false;
					break;
				}
			}
			// 합격 기준을 모두 만족하면서, 현재까지의 약품 최소 처리 횟수보다 적은 값이면 갱신
			if (isSatisfied && chemicalCnt < minChemicalCnt)
				minChemicalCnt = chemicalCnt;
			return;
		}

		// (주목)여기서 continuum 및 maxContinuum을 계속 생성해줌
		// cur : 현재 셀의 상태
		// prev : 바로 이전 셀의 상태
		int[] continuum = new int[WSIZE];
		int[] maxContinuum = new int[WSIZE];
		int prev, cur;

		// 약품처리를 하지 않거나, B 약품을 처리하거나, A 약품을 처리하는 것을 차례대로 진행해본다.
		for (int i = 2; i >= 0; i--) {
			// i 번째 약품을 처리할 때 (0: A약품, 1: B약품, 2: 약품처리안함)
			chemical[curD] = i;
			
			// 모든 세로방향을 탐색해본다.
			for (int j = 0; j < W; j++) {
				cur = chemical[curD] == 2 ? film[curD][j] : chemical[curD];
				prev = chemical[curD - 1] == 2 ? film[curD - 1][j] : chemical[curD - 1];
				
				// cur과 prev가 같으면 이전 연속 셀 개수의 + 1을 하고, 아니면 1을 설정
				continuum[j] = cur == prev ? prevContinuum[j] + 1 : 1;
				
				// 가장 많이 연속되는 셀의 개수를 갱신
				maxContinuum[j] = continuum[j] > prevMaxContinuum[j] ? continuum[j] : prevMaxContinuum[j];
			}
			// 다음 번째의 보호필름의 엷은 막을 확인
			solve(curD + 1, chemicalCnt + (i == 2 ? 0 : 1), continuum, maxContinuum);
		}
	}

	public static void main(String[] args) throws Exception {
		int test_case = sc.nextInt();
		for (int tc = 1; tc <= test_case; tc++) {
			D = sc.nextInt();
			W = sc.nextInt();
			K = sc.nextInt();

			for (int i = 0; i < D; i++)
				for (int j = 0; j < W; j++)
					film[i][j] = sc.nextInt();

			minChemicalCnt = K;
			
			// continuum : 지금 현재 필름까지 세로방향으로 같은 특성이 존재하는 셀의 수를 저장
			// maxContinuum : 지금 현재 필름까지 세로방향으로 같은 특성이 가장 많이 연속되는 셀의 개수를 저장
			int[] continuum = new int[WSIZE];
			int[] maxContinuum = new int[WSIZE];
			for (int i = 0; i < W; i++) 
				continuum[i] = maxContinuum[i] = 1; // 왼쪽과 같은 초기화 방식에 주목할 것
			
			// 0번쨰 엷은 필름 막에 화학약품 처리를 하지 않는다.
			chemical[0] = 2;
			solve(1, 0, continuum, maxContinuum);

			// 0번째 엷은 필름 막에 약품 A 처리를 한다.
			chemical[0] = 0;
			solve(1, 1, continuum, maxContinuum);

			// 0번째 엷은 필름 막에 약품 B 처리를 한다.
			chemical[0] = 1;
			solve(1, 1, continuum, maxContinuum);

			System.out.println("#" + tc + " " + minChemicalCnt);
		}
	}
}