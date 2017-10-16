package SE_longestPath_subpractice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

/*
 * 문제를 잘 읽자. 주어진 그래프가 사이클이 없다는 조건이 없다. 그렇기에 흔히 트리에서 DFS로 최장 깊이 탐색하듯이 탐색하면 틀린다.
 */

public class Main {
	public static int N, M; // N : 정점의 갯수, M : 간선의 갯수 
	public static Vector<Vector<Integer>> G;
	public static Vector<Integer> visited;
	public static int max_level;
	
	public static void dfsAll(){ // 그래프가 모두 연결되어 있다는 보장이 없음. 그렇기에 그래프가 모두 연결되어 있다는 조건이 주어지지 않은 한, 이와 같은 가능성도 고려해야 함.
		max_level = 1; // 경로의 길이는 경로상에 등장하는 '정점'의 갯수를 의미함. (문제에서 주어진 조건에 유의할 것. 마음대로 경로의 길이가 간선의 갯수라고 생각하지 말것)
		for(int i=0; i<visited.size(); i++){
			if(visited.get(i)==0){ 
				dfs(i, 1); // 어느 노드를 기점으로 잡았을때, 해당 노드에서 부터 시작하는 모든 경로를 DFS를 이용해서 탐색한다. 이때 중요한 점은 다음줄인데,
				visited.set(i, 0); // 여기서 기점으로 삼았던 노드의 방문 여부를 해제해야 다음에 기점으로 삼을 노드의 경로상 방해물이 되지 않는다. 
				                   // 문제풀이시, 이 점을 찾는데 매우 오랜 시간이 걸렸다.
			}	
		}
	}
	
	public static void dfs(int here, int level){
		max_level = Math.max(max_level, level); // 최장 경로를 지속적으로 기록해둔다.
		visited.set(here, 1); // 벡터에서 원소의 값을 바꾸는 방법

//		System.out.println(level);
//		indexPrinter(); // DFS의 원리를 이해하기에 좋은 코드이니 출력 부분을 남겨 놓았다(왼쪽은 인덱스[노드번호], 그 아래쪽은 해당 노드의 방문 여부를 나타낸다.)
//		printer();
		
		for(int i=0; i<G.get(here).size(); i++){
			int there = G.get(here).get(i);
			if(visited.get(there)==0){
				dfs(there, ++level); // dfs(there)로 호출한다는 점에 매우 유의하자. 또한 전위 연산자를 이용하여 호출해야 level이 증가된 상태로 다음 dfs가 호출된다는 사실에도 유의하자 
				--level; // dfs에서 하단 layer 종료시 level의 수위를 1단계 해제한다.
				visited.set(there, 0); // 방문했던 노드의 방문여부를 해제하여 다음 루트 탐색시 막히지 않도록 한다. 
			}
		}
	}
	
//	public static void indexPrinter(){
//		for(int i=0; i<visited.size(); i++){
//			System.out.print(i+1+" ");
//		}
//		System.out.println();
//	}
	
//	public static void printer(){
//		for(int i=0; i<visited.size(); i++){
//			System.out.print(visited.get(i)+" ");
//		}
//		System.out.println();
//	}
		
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(".\\src\\SE_longestPath_subpractice\\sample_input.txt"));
		int T = Integer.parseInt(br.readLine());
		StringTokenizer st;
		for(int t=0; t<T; t++){
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
				
			/* Vector 사용시 놓쳤던 점들. 확실히 알아두자
			 * Vector의 size()함수 : 벡터 내부의 요소 갯수에 대하여 알려주는 함수이다. 
			 * Vector의 capacity()함수와 착각하지 말자. capacity 함수를 벡터의 용량을 나타내는 함수이다. 
			 */
			
			// 2중 벡터를 초기화 하는 경우
			G = new Vector<Vector<Integer>>(N);
			visited = new Vector<Integer>(N); 
			for(int i=0; i<N; i++) {
				G.add(new Vector<Integer>(N)); // 각 element에 대해서 용량을 지정해서 벡터를 생성해줘야 추후에 사용이 가능하다.
				visited.add(0); // 이렇게 '방문하지 않았음'을 나타내는 정보를 넣어줘야 한다. 
			}
			
			int x, y;
			for(int i=0; i<M; i++){
				st = new StringTokenizer(br.readLine());
				x = Integer.parseInt(st.nextToken())-1; // 문제를 똑바로 읽자. node들의 name은 1부터 시작했음을 인지하자
				y = Integer.parseInt(st.nextToken())-1;
				G.get(x).add(y);
				G.get(y).add(x); // 무방향 그래프
			}
			dfsAll();
			System.out.println("#"+(t+1)+" "+max_level);
		}
		br.close();	
	}
}