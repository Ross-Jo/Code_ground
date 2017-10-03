package SE_police;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int[][] direction = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}}; // ��, �� , ��, �Ʒ�
	static int[][] dFit = {{1,3,4,5}, {1,2,5,6}, {1,3,6,7}, {1,2,4,7}};
	
	public static int findPoints(int[][] map, int N, int M, int R, int C, int L){
		int[][] visited = new int[N][M];
		int[][] orinum = new int[N][M];
		Queue<int[]> q = new LinkedList<int[]>();
		int counter = 1;
		
		visited[R][C] = 1;
		int[] root = new int[2];
		root[0] = R;
		root[1] = C;
		orinum[R][C] = 1;
		q.add(root);
		
		while(!q.isEmpty()){ // BFS�� ���̾� �� ��ȯ �н��� �� - ���ڿ� ���̺� ���̾� ������ �Է��� ��� ��尡 � ���̾� ������ �ľ��� ��. 
			int[] t = q.poll();
			
			if(orinum[t[0]][t[1]]>L-1) break;
				
			ArrayList<int[]> tmp = findNeighbors(map, N, M, t[0], t[1]);	
			for(int i=0; i<tmp.size(); i++){
				int row = tmp.get(i)[0];
				int col = tmp.get(i)[1];
					
				if(visited[row][col]!=1){
					visited[row][col]=1;
					counter++;
					int[] n = new int[2];
					n[0] = row;
					n[1] = col;
					orinum[n[0]][n[1]] = orinum[t[0]][t[1]]+1;
					q.add(n);
				}
					
			}
		}
		return counter;
	}
	
	public static ArrayList<int[]> findNeighbors(int[][] map, int N, int M, int i, int j){

		ArrayList<int[]> neighbors = new ArrayList<int[]>(); // ArrayList ����� �����ϱ�, �׸����� �׷��� ǥ�� �����ϱ�, BFS �����ϱ�
		
		switch(map[i][j]){
		case 1: 
			checkNeighbors(map, N, M, i, j, neighbors, 0);
			checkNeighbors(map, N, M, i, j, neighbors, 1);
			checkNeighbors(map, N, M, i, j, neighbors, 2);
			checkNeighbors(map, N, M, i, j, neighbors, 3);
			break;
		case 2: 
			checkNeighbors(map, N, M, i, j, neighbors, 1);
			checkNeighbors(map, N, M, i, j, neighbors, 3);
			break;
		case 3: 
			checkNeighbors(map, N, M, i, j, neighbors, 0);
			checkNeighbors(map, N, M, i, j, neighbors, 2);
			break;
		case 4: 
			checkNeighbors(map, N, M, i, j, neighbors, 1);
			checkNeighbors(map, N, M, i, j, neighbors, 2);
			break;
		case 5: 
			checkNeighbors(map, N, M, i, j, neighbors, 2);
			checkNeighbors(map, N, M, i, j, neighbors, 3);
			break;
		case 6: 
			checkNeighbors(map, N, M, i, j, neighbors, 0);
			checkNeighbors(map, N, M, i, j, neighbors, 3);
			break;
		case 7: 
			checkNeighbors(map, N, M, i, j, neighbors, 0);
			checkNeighbors(map, N, M, i, j, neighbors, 1);
			break;
		default:
			break;
		}
		
		return neighbors;
	}
	
	public static void checkNeighbors(int[][] map, int N, int M, int i, int j, ArrayList<int[]> neighbors, int d){
		for(int e=0; e<dFit[d].length; e++){
			if((0<=(i+direction[d][0])) && ((i+direction[d][0])<N) &&
			   (0<=(j+direction[d][1])) && ((j+direction[d][1])<M) && 
			    map[i+direction[d][0]][j+direction[d][1]]==dFit[d][e]){
				int[] n = new int[2];
				n[0] = i+direction[d][0];
				n[1] = j+direction[d][1];
				neighbors.add(n);
			}
		}
	}

	public static void main(String[] args) {
		try{
			BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\350U2A\\Desktop\\�����\\code_ground\\Samsung_Code_Ground\\src\\SE_police\\sample_input.txt"));
			
			int T = Integer.parseInt(br.readLine());
			
			for(int t=0; t<T; t++){
				StringTokenizer st = new StringTokenizer(br.readLine());
				int N = Integer.parseInt(st.nextToken()); // ������ ����
				int M = Integer.parseInt(st.nextToken()); // ������ ����
				
				int R = Integer.parseInt(st.nextToken()); // �Ѳ��� ������ġ
				int C = Integer.parseInt(st.nextToken()); // �Ѳ��� ����
				
				int L = Integer.parseInt(st.nextToken()); // Ż�� �� �ҿ�� �ð�
				
				int[][] map = new int[N][M];
				
				for(int i=0; i<N; ++i){
					st = new StringTokenizer(br.readLine());
					for(int j=0; j<M; ++j){
						map[i][j] = Integer.parseInt(st.nextToken());
					}
				}
				System.out.println("#"+(t+1)+" "+ findPoints(map, N, M, R, C, L));
			}
			
			br.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}

}
