package baekjoon_lab;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Node{
	int x; 
	int y;
	public Node(int x, int y){
		this.x = x; 
		this.y = y;
	}
}

public class Main {
	public static int N;
	public static int M;
	public static int[][] direction = {{0,-1},{-1,0},{0,1},{1,0}}; // ��, ��, ��, ��
	public static int max_area; // ���������� �ִ밪
	
	public static void printer(int[][] map){
		for(int i=0; i<N; i++){
			for(int j=0; j<M; j++){
				System.out.print(map[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	public static int[][] copyArray(int[][] map){
		int[][] copy = new int[map.length][map[0].length];
		for(int i=0; i<copy.length; i++){
			copy[i] = Arrays.copyOf(map[i], map[i].length);
		}
		return copy;
	}
	
	public static void chooseWall(int wallNumber, int[][] map){
		if(wallNumber==3){
			int[][] copy = copyArray(map);
//			int[][] copy = new int[map.length][map[0].length];
//			System.arraycopy(map, 0, copy, 0, map.length);

			/* arraycopy�� �������� ����?	: ��� �ѱ۹������� arraycopy�� ���� ������ ǥ���� �������� �ִµ� �˰��� ���� ���簡 �ƴ϶� ���� ���� ����.
			 * (���� : https://stackoverflow.com/questions/15135104/system-arraycopy-copies-object-or-reference-to-object)
			 * �׷��� clone�ߴٰ� ������ ��ü ���� ��, ���� ��ü�� ���� ����Ǿ��� ����. �������ʹ� �ش� �Լ� ��뿡 ������ ��.
			 */

//			System.out.println(map+" "+copy);
//			System.out.println("����");
//			printer(map); System.out.println();
			virus(copy);
//			System.out.println("���纻");
//			printer(copy); System.out.println();
			return;
		}

//		System.out.println("FOR�� �� ����:"+map);
		for(int i=0; i<N; i++){
			for(int j=0; j<M; j++){
				if(map[i][j]==0){
					map[i][j] = 1;
//					System.out.println(map);
//					printer(map); System.out.println();
					chooseWall(wallNumber+1, map);
					map[i][j] = 0;
				}
			}
		}
	}
	
	public static void virus(int[][] map){
		for(int i=0; i<N; i++){
			for(int j=0; j<M; j++){
				if(map[i][j]==2){	
					BFS(map, new Node(i, j));
				}
			}
		}
//		System.out.println("BFS ����: "+map);
//		printer(map); System.out.println();
		
		max_area = Math.max(max_area, countArea(map));
	}
	
	public static void BFS(int[][] map, Node root){
		Queue<Node> q = new LinkedList<Node>();
		
		if(map[root.x][root.y]==0) map[root.x][root.y] = 2;
		q.add(root);
		
		while(!q.isEmpty()){
			Node r = q.poll();
			for(int i=0; i<4; i++){
				int new_x = r.x+direction[i][0];
				int new_y = r.y+direction[i][1];
				
				if( new_x>=0 && new_x<N && 
				    new_y>=0 && new_y<M && 
				    map[new_x][new_y]==0){
					
					map[new_x][new_y] = 2;
					q.add(new Node(new_x, new_y));
				}
			}
		}
	}
	
	public static int countArea(int[][] map){
		int ret = 0;
		for(int i=0; i<N; i++){
			for(int j=0; j<M; j++){
				if(map[i][j]==0) ret++; 
			}
		}
		return ret;
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		M = sc.nextInt();
		int[][] map = new int[N][M];
		max_area = 0;
		
		for(int i=0; i<N; i++){
			for(int j=0; j<M; j++){
				map[i][j] = sc.nextInt();
			}
		}
		
		chooseWall(0, map);
		System.out.println(max_area);
		
		sc.close();
	}
}