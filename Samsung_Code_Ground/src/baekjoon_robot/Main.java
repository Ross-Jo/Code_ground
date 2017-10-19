package baekjoon_robot;

import java.util.Scanner;

public class Main {
	public static int N, M;
	public static int x, y, dir, counter;
	public static int[][] map, visited;

	public static int[][] d = {{-1,0},{0,1},{1,0},{0,-1}}; // ��, ��, ��, ��
	public static int[][] left = {{0,-1},{-1,0},{0,1},{1,0}}; // ��, ��, ��, ��
	public static int[] next_l = {3, 0, 1, 2}; // ��, ��, ��, ��
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
				int n_lx = x+left[dir][0]; // ���� ��ġ ���� ������ row
				int n_ly = y+left[dir][1]; // ���� ��ġ ���� ������ col
				
				/* ���� �˻縦 ���� �� ��� �Ѵ�. ����� �̹� û�Ҹ� �߰ų� ���� ���, ������ ���� ��ġ ��ȭ�� �ϱ� �����̴�.
				 * ���� �� �ڵ� ������ �������� ���� ���, continue�� ���� ���� ������ ������ �ȴ�.
				 */
				if(cnt>4){ // ���� ���·� ���� ��
					int back_x = x+d[back[dir]][0]; // �������� �˾ƺ��� ���� �ؾ� ������� ���ϴ�.
					int back_y = y+d[back[dir]][1];
					if(back_x>=0 && back_x<N &&
					   back_y>=0 && back_y<M &&
					   map[back_x][back_y]!=1){
					   x = back_x; // ����
					   y = back_y; // ����
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
				   dir = next_l[dir]; // ���� ��ȯ
				   x = n_lx;
				   y = n_ly;
				   break;
				}
				
				if(n_lx>=0 && n_lx<N && 
				   n_ly>=0 && n_ly<M && 
				   (visited[n_lx][n_ly]==1 || map[n_lx][n_ly]==1)){ // �̹� û�Ҹ� �߰ų�, ���ΰ��
				   dir = next_l[dir]; // ���� ��ȯ
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