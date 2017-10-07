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
	 * c. 그리드 합 - 2중 for문으로 일일히 더하는 수밖에 없는가? : 일단은 이 방법밖에 없어 보인다. 
	 *    CF) 1차원 Integer array에 대한 합 : IntStream.of(array).sum();
	 *
	 * [개선해야 할 점/이슈]
	 * 구현을 위한 문제 정의를 좀 더 엄밀하게하고 명확하게 이해해야 함, DFS, BFS, 트리 등 재귀 구조를 이용하는 부분에 대한 복습필요 
	 * - 전역변수의 활용 : 문제에서 불변하는 값인 경우 전역변수로 빼서 활용이 용이하게 하자 -> 공통으로 쓰이는 불변값을 전역으로 뺴주니, 넘겨야 할 파라미터가 훨씬 간결해졌다. 
	 * - DFS/BFS를 활용해 코드 작성시, 각 재귀 단계마다 stack에 저장되는값, 재귀를 함과 동시에 가지고 다녀야 하는 값을 잘 판별해야 함(기본적인 내용이지만 다시 한 번 숙지하자). 참고 : https://books.google.co.kr/books?id=VPzHDgAAQBAJ&pg=PA59&lpg=PA59&dq=%EC%9E%AC%EA%B7%80%ED%95%A8%EC%88%98+stack+%EC%A0%80%EC%9E%A5&source=bl&ots=I_MCleoS1p&sig=MXFu95FM22e06WnNzDeK86dQrFk&hl=ko&sa=X&ved=0ahUKEwj2naDbvN7WAhUJvrwKHQ8pAroQ6AEIWzAI#v=onepage&q=%EC%9E%AC%EA%B7%80%ED%95%A8%EC%88%98%20stack%20%EC%A0%80%EC%9E%A5&f=false
	 * - DFS/BFS에서 전역변수 활용 시, 각 변수의 의미를 명확히 하고, 이에 따른 초기화 위치를 명확히 설정하자. 혹여 의도치 않은 찌꺼기가 껴 이상한 결과를 내는것을 방지하자. 
	 * - 배열 사용 시, ArrayIndexOutofBound 에러에 대하여 항상 염두에 두고 해당 에러가 발생하지 않도록 유의하자
	 * - 많은 인자는 프로그램의 속도를 하락시키는가? : 인자를 넘길 때, 얕은 복사가 일어나기에 프로그램의 속도에 영향을 미치기는 하지만 그 영향력은 극히 미미하다. 인자의 갯수에 너무 신경쓰기보다는 알기쉽고 명확한 코드 작성에 좀 더 포커싱하자. 참고 : https://stackoverflow.com/questions/13768637/java-method-with-many-parameters-performance
	 * - 바로 1step 전의 결과는 어떻게 취소할 것인가 - 아직까지 떠오르는 방법은 재귀호출시 visit여부를 가지고 다니면서 stack을 이용해 값을 쓰고 지우는 방법인데, 더 나은 방법이 있는지 생각해보자. 
	 *                                 - 아니면 바로 전단계의 수정 사항을 기록하는 새로운 2차원 배열을 만들어서, 상태를 되돌릴 때 사용 가능할 것 같다.
	 * - 완전탐색의 골격구조에 대하여 다시한번 복습하자.
	 * 
	 * */
	public static int N, M, C;
	public static int[][] map;
	
	public static int[][] tmp_visited;
	public static int max_income;
	public static int tmp_max;
	
	public static int findMaxIncome(int[][] visited){
		max_income = 0; // findMaxIncome 함수는 농부 1명의 map 1번 루프당 산출할 수 있는 최대 이익을 반환하기에 이 함수의 앞단에서 해당 전역변수를 초기화 시켜야 한다. 
		int tmp = 0;
		
		for(int i=0; i<N; i++){
			for(int j=0; j<=N-M; j++){
				if((!isVisited(visited[i], j, j+M-1) && partialSum(map[i], j, j+M-1)<=C)){
					if(max_income < (tmp = calcSqSum(map[i], j, j+M-1))){ // 갱신이 될 때에만 기록할 것
						max_income = tmp;
						tmp_visited = checkVisit(i, j, j+M-1);
					}
				}
				else if(!isVisited(visited[i], j, j+M-1)){
					int[] checker = new int[N];
					int sum = 0;
					tmp_max = 0; // tmp_max라는 변수는 map을 순회중, 특정 버킷(M개의 요소 합이 >C인)에서 산출가능한 최대 이익을 의미하기에 여기에서 초기화를 시켜야 한다.  
					
					// sum과, checker는 재귀를 돌리는 도중 가져가야 할 값이기에 재귀함수 밖에서 넘겨준다.
					
					max_income = Math.max(max_income, findMax(map[i], 0, M, j, j+M-1, checker, sum, i));
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
	
	public static int findMax(int[] row, int cnt, int depth, int startIndex, int endIndex, 
			                  int[] checker, int sum, int rowIndex){
		
		int tmp = 0;
		
		if(cnt==depth && sum <=C){
			for(int i=startIndex; i<startIndex+cnt; i++){
				if(checker[i]==1){
					tmp += Math.pow(row[i], 2);
				}
			}
			if(tmp > max_income) {
				tmp_visited = checkVisit(rowIndex, startIndex, endIndex);
			}
			tmp_max = Math.max(tmp_max, tmp);
			return tmp_max;
		}
		else if(cnt==depth){
			return tmp_max;
		}
		
		checker[startIndex+cnt] = 1;
		sum += row[startIndex+cnt];
		findMax(row, cnt+1, depth, startIndex, endIndex, checker, sum, rowIndex);
		
		checker[startIndex+cnt] = 0; // DFS에서의 앞선 선택 해제는 대칭적으로 할 것
		sum -= row[startIndex+cnt];
		findMax(row, cnt+1, depth, startIndex, endIndex, checker, sum, rowIndex);
		
		return tmp_max;
	}
	
	// 아래는 helper 함수들
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
	
	public static int[][] checkVisit(int rowIndex , int startIndex, int endIndex){
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
				N = Integer.parseInt(st.nextToken());
				M = Integer.parseInt(st.nextToken());
				C = Integer.parseInt(st.nextToken());
				
				map = new int[N][N];
				
				for(int i=0; i<N; i++){
					st = new StringTokenizer(br.readLine());
					for(int j=0; j<N; j++){
						map[i][j] = Integer.parseInt(st.nextToken());
					}
				}
				
				int[][] visited = new int[N][N];
				int ret = findMaxIncome(visited); 
				ret += findMaxIncome(visited);
				
				System.out.println("#"+(t+1)+" "+ret);
			}
			
			br.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}