package SE_honey; // 수정필요

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {
	// 1명의 농부 순회시 max_income 반환
	// 시간이 오래 걸렸던 부분 : 구현 
	/*
	 * a. visited에서의 이전 기록에 대한 삭제 및 신규 기록
	 * b. DFS의 원리를 이용한 부분집합 설정 및 검색. 특정 바스킷 안에서 어떻게  =<C 이면서 제곱합이 최대인 조합을 찾을 것인가?
	 * c. 그리드 합 - 2중 for문으로 일일히 더하는 수밖에 없는가?
	 *    CF) 1차원 int array에 대한 합 : IntStream.of(array).sum();
	 * 유의사항 : 구현을 위한 문제 정의를 좀 더 엄밀하게하고 명확하게 이해해야 함, DFS, BFS, 트리 등 재귀 구조를 이용하는 부분에 대한 복습필요 
	 * 
	 * */
	public static int[][] tmp_visited;
	public static int max_income;
	public static int tmp_max;
	
	public static int findMaxIncome(int[][] map, int N, int M, int C, int[][] visited){
		max_income = 0;
		int tmp = 0;
		
		for(int i=0; i<N; i++){
			for(int j=0; j<=N-M; j++){
				if((!isVisited(visited[i], j, j+M-1) && partialSum(map[i], j, j+M-1)<=C)){
					if(max_income < (tmp = calcSqSum(map[i], j, j+M-1))){ // 갱신이 될 때에만 기록할 것
						max_income = tmp;
						tmp_visited = checkVisit(N, i, j, j+M-1);
					}
				}
				else if(!isVisited(visited[i], j, j+M-1)){
					int[] checker = new int[N];
					int sum = 0;
					tmp_max = 0;
					max_income = Math.max(max_income, findMax(map[i], 0, M, C, j, j+M-1, checker, sum, i));
				}
			}
		}
		
		for(int i=0; i<N; i++){
			for(int j=0; j<N; j++){
				visited[i][j] += tmp_visited[i][j];
			}
		}
		
		return max_income;
	}
	
	public static int findMax(int[] row, int cnt, int depth, int C, 
			                  int startIndex, int endIndex, int[] checker, int sum, int rowIndex){
		
		int tmp = 0;
		
		if(cnt==depth && sum <=C){
			for(int i=startIndex; i<startIndex+cnt; i++){
				if(checker[i]==1){
					tmp += Math.pow(row[i], 2);
				}
			}
			if(tmp > max_income) {
				tmp_visited = checkVisit(row.length, rowIndex, startIndex, endIndex);
			}
			tmp_max = Math.max(tmp_max, tmp);
			return tmp_max;
		}
		else if(cnt==depth){
			return tmp_max;
		}
		
		checker[startIndex+cnt] = 1;
		sum += row[startIndex+cnt];
		findMax(row, cnt+1, depth, C, startIndex, endIndex, checker, sum, rowIndex);
		
		checker[startIndex+cnt] = 0;
		sum -= row[startIndex+cnt];
		findMax(row, cnt+1, depth, C, startIndex, endIndex, checker, sum, rowIndex);
		
		return tmp_max;
	}
	
	public static int partialSum(int[] row, int startIndex, int endIndex){
		int ret = 0;
		for (int i = startIndex; i <= endIndex; i++) {
			ret += row[i];
		}
		return ret;
	}
	
	public static int calcSqSum(int[] row, int startIndex, int endIndex){
		int ret = 0;
		for (int i = startIndex; i <= endIndex; i++) {
			ret += Math.pow(row[i], 2);
		}
		return ret;
	}
	
	public static boolean isVisited(int[] row, int startIndex, int endIndex){
		boolean flag = false;
		for (int i = startIndex; i <= endIndex; i++) {
			if(row[i]==1) flag = true;
		}
		return flag;
	}
	
	public static int[][] checkVisit(int N, int rowIndex , int startIndex, int endIndex){
		int[][] tmp_visited = new int[N][N];
		
		for(int i=startIndex; i<=endIndex; i++){
			tmp_visited[rowIndex][i] = 1;
		}
		
		return tmp_visited;
	}
	
	public static void main(String[] args) {
		try{
			BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\350U2A\\Desktop\\깃허브\\code_ground\\Samsung_Code_Ground\\src\\SE_honey\\sample_input.txt"));
			int T = Integer.parseInt(br.readLine());
			for(int t=0; t<T; t++){
				StringTokenizer st = new StringTokenizer(br.readLine());
				int N = Integer.parseInt(st.nextToken());
				int M = Integer.parseInt(st.nextToken());
				int C = Integer.parseInt(st.nextToken());
				
				int[][] map = new int[N][N];
				
				for(int i=0; i<N; i++){
					st = new StringTokenizer(br.readLine());
					for(int j=0; j<N; j++){
						map[i][j] = Integer.parseInt(st.nextToken());
					}
				}
				
				int[][] visited = new int[N][N];
				int ret = findMaxIncome(map, N, M, C, visited); 
				ret += findMaxIncome(map, N, M, C, visited);
				
				System.out.println("#"+(t+1)+" "+ret);
			}
			
			br.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}