package SE_lunchHour;

import java.util.*;

/*
 * 이 해설은 기본적인 아이디어 차이는 없었다. 구현 방식의 차이에 주목하자
 */

// 방에서의 위치를 나타내는 클래스
class PT{
	int r, c; // r번째 행의 c번째 열
	public PT(int r, int c){
		this.r = r;
		this.c = c;
	}
}

public class Solution {
	// N : 방의 한 변의 길이, M : 사람의 수, S(=2) : 계단의 수
	// map : 입력으로 주어지는 방의 정보
	// match[x] = y : x번째 사람이 y번째 계단을 타도록 결정된 상태
	static int MAXN = 11;
	static int MAXT = MAXN * 2 + MAXN * MAXN;
	
	static int N;
	static int map[][], match[];
	static int M, S;
	
	static int answer;
	static PT man[], stair[];
	
	//man_index인 사람과 stair_index인 계단 사이의 거리
	static int dist(int man_index, int stair_index){
		int dx = Math.abs(man[man_index].r - stair[stair_index].r);
		int dy = Math.abs(man[man_index].c - stair[stair_index].c);
		return dx + dy;
	}
	
	// 각 사람이 어느 계단을 이용할 지 모두 정해졌을 때에 필요한 시간을 계산하는 함수
	static void update(){
		// 모든 사람들이 계단을 내려가는데 필요한 최소 시간
		int total_min_time = 0;
		
		// 두 계단은 서로 독립적이므로 각각에 대해서 계산한다.
		for (int stair_index = 0; stair_index < 2; stair_index++) {
			PT now_stair = stair[stair_index];
			// arrival_time[t] : 시간 t일 때 계단에 도착하는 사람의 수
			int[] arrival_time = new int[MAXN * 2];
			// current_stair[t] : 시간 t일 때 계단을 내려가고 있는 사람의 수
			int[] current_stair = new int[MAXT];

			for (int i = 0; i < MAXT; i++) current_stair[i] = 0;
			for (int i = 0; i < 2 * MAXN; i++) arrival_time[i] = 0;

			for (int man_index = 0; man_index < M; man_index++) {
				if (match[man_index] == stair_index) {
					arrival_time[dist(man_index, stair_index) + 1]++; // 해당 시각이 나타내는 사람수 증가
				}
			}
			
			// stair_index 계단을 내려가는 사람이 모두 작업을 마치기 위해 필요한 최소 시간
			int now_min_time = 0;
			
			// 계단에 도착하는 시간 오름차순으로 각 사람이 계단을 내려가도록 시뮬레이션
			for (int time = 1; time <= 20; time++) {

				// time에 도착한 사람이 있다면
				while (arrival_time[time] > 0) {
					arrival_time[time]--;

					// 해당 계단을 내려가는데 필요한 시간
					int remain_time = map[now_stair.r][now_stair.c];

					// 계단에 도착한 시점부터 사람 3명이 동시에 내려가고 있지 않을 때에 1칸씩 밑으로 내려 보낸다.
					for (int walk_time = time; walk_time < MAXT; walk_time++) {
						if (current_stair[walk_time] < 3) {
							current_stair[walk_time]++;
							remain_time--;

							// 계단을 다 내려갔으면 now_min_time 갱신
							if (remain_time == 0) {
								now_min_time = Math.max(now_min_time, walk_time + 1); // +1은 remain_time이 먼제 --를 하기 때문에 제대로 된 시간을 얻기 위함.
								break;
							}
						}
					}
				}
			}
			// 전체 계단을 내려가는데 필요한 최소 시간
			// = (두 계단을 내려가는데 필요한 최소 시간) 중 최대값
			total_min_time = Math.max(total_min_time, now_min_time);
		}
		// 현재 match 상태일 태의 total_min_time을 정답으로 갱신
		answer = Math.min(answer, total_min_time);
	}
	
	static void dfs(int man_index){
		// 모든 사람에 대해 각 사람이 사용할 계단을 다 정했다면
		if(man_index == M){
			// 해당 상태에서 필요한 시간을 계산하고 정답 갱신
			update();
			return;
		}
		
		// man_index 번째 사람이 stair_index 번째 계단을 선택하고, 재귀 호출
		for(int stair_index = 0; stair_index < 2; stair_index++){
			match[man_index] = stair_index;
			dfs(man_index + 1);
		}
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for(int tc = 1; tc <= T; tc++){
			N = scan.nextInt();
			M = S = 0;
			map = new int[N+2][N+2];
			man = new PT[N*N];
			stair = new PT[2];
			
			for(int i=1; i<=N; i++){
				for(int j=1; j<=N; j++){
					map[i][j] = scan.nextInt();
					// 방의 (i, j)가 사람이면, 해당 위치를 man에 삽입
					if(map[i][j]==1) man[M++] = new PT(i, j);
					// 방의 (i, j)가 계단이면, 해당 위치를 stair에 삽입
					if(map[i][j]>=2) stair[S++] = new PT(i, j);
				}
			}
			// 답을 무한대로 초기화
			answer = 1000000000;
			match = new int[M];
			// 깊이 우선 탐색 시작
			dfs(0);
			System.out.println("#" + tc + " " + answer);
		}
		scan.close();
	}
}