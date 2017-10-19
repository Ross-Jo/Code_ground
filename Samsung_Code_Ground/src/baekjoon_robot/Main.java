package baekjoon_robot;

import java.util.Scanner;

public class Main {
	public static int N, M;
	public static int x, y, dir, counter;
	public static int[][] map, visited;

	public static int[][] d = {{-1,0},{0,1},{1,0},{0,-1}}; // 상, 우, 하, 좌
	public static int[][] left = {{0,-1},{-1,0},{0,1},{1,0}}; // 좌, 상, 우, 하
	public static int[] next_l = {3, 0, 1, 2}; // 좌, 상, 우, 하
	public static int[] back = {2, 3, 0, 1};
	
	public static void moveRobot(){
		master:
		while(true){
			if(x>=0 && x<N && y>=0 && y<M && visited[x][y]==0 && map[x][y]!=1) {
				counter++;
				visited[x][y] = 1;
			}
			
			int cnt = 0;
			while(true){
				cnt++;
				int n_lx = x+left[dir][0]; // 현재 위치 기준 왼쪽의 row
				int n_ly = y+left[dir][1]; // 현재 위치 기준 왼쪽의 col
				
				/* 상태 검사를 먼저 해 줘야 한다. 사방이 이미 청소를 했거나 벽일 경우, 후진을 통해 위치 변화를 하기 때문이다.
				 * 만약 이 코드 조각이 마지막에 있을 경우, continue에 의한 무한 루프에 빠지게 된다.
				 */
				if(cnt>4){ // 원래 상태로 복귀 시
					int back_x = x+d[back[dir]][0]; // 변수명을 알아보기 쉽게 해야 디버깅이 편리하다.
					int back_y = y+d[back[dir]][1];
					if(back_x>=0 && back_x<N &&
					   back_y>=0 && back_y<M &&
					   map[back_x][back_y]!=1){
					   x = back_x; // 후진
					   y = back_y; // 후진
					   cnt = 0;
					   continue;
					}
					else{
						break master;
					}
				}
				
				if(n_lx>=0 && n_lx<N && 
				   n_ly>=0 && n_ly<M && 
				   visited[n_lx][n_ly]==0 && map[n_lx][n_ly]!=1){
				   dir = next_l[dir]; // 방향 전환
				   x = n_lx;
				   y = n_ly;
				   break;
				}
				
				if(n_lx>=0 && n_lx<N && 
				   n_ly>=0 && n_ly<M && 
				   (visited[n_lx][n_ly]==1 || map[n_lx][n_ly]==1)){ // 이미 청소를 했거나, 벽인경우
				   dir = next_l[dir]; // 방향 전환
				   continue;
				}
			}
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		
		x = sc.nextInt();
		y = sc.nextInt();
		dir = sc.nextInt();
		
		map = new int[N][M];
		visited = new int[N][M];
		counter = 0;
		
		for(int i=0; i<N; i++){
			for(int j=0; j<M; j++){
				map[i][j] = sc.nextInt();
			}
		}
		
		moveRobot();
		System.out.println(counter);
		
		sc.close();
	}
}