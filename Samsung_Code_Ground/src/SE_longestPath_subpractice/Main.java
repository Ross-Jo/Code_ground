package SE_longestPath_subpractice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

/*
 * ������ �� ����. �־��� �׷����� ����Ŭ�� ���ٴ� ������ ����. �׷��⿡ ���� Ʈ������ DFS�� ���� ���� Ž���ϵ��� Ž���ϸ� Ʋ����.
 */

public class Main {
	public static int N, M; // N : ������ ����, M : ������ ���� 
	public static Vector<Vector<Integer>> G;
	public static Vector<Integer> visited;
	public static int max_level;
	
	public static void dfsAll(){ // �׷����� ��� ����Ǿ� �ִٴ� ������ ����. �׷��⿡ �׷����� ��� ����Ǿ� �ִٴ� ������ �־����� ���� ��, �̿� ���� ���ɼ��� ����ؾ� ��.
		max_level = 1; // ����� ���̴� ��λ� �����ϴ� '����'�� ������ �ǹ���. (�������� �־��� ���ǿ� ������ ��. ������� ����� ���̰� ������ ������� �������� ����)
		for(int i=0; i<visited.size(); i++){
			if(visited.get(i)==0){ 
				dfs(i, 1); // ��� ��带 �������� �������, �ش� ��忡�� ���� �����ϴ� ��� ��θ� DFS�� �̿��ؼ� Ž���Ѵ�. �̶� �߿��� ���� �������ε�,
				visited.set(i, 0); // ���⼭ �������� ��Ҵ� ����� �湮 ���θ� �����ؾ� ������ �������� ���� ����� ��λ� ���ع��� ���� �ʴ´�. 
				                   // ����Ǯ�̽�, �� ���� ã�µ� �ſ� ���� �ð��� �ɷȴ�.
			}	
		}
	}
	
	public static void dfs(int here, int level){
		max_level = Math.max(max_level, level); // ���� ��θ� ���������� ����صд�.
		visited.set(here, 1); // ���Ϳ��� ������ ���� �ٲٴ� ���

//		System.out.println(level);
//		indexPrinter(); // DFS�� ������ �����ϱ⿡ ���� �ڵ��̴� ��� �κ��� ���� ���Ҵ�(������ �ε���[����ȣ], �� �Ʒ����� �ش� ����� �湮 ���θ� ��Ÿ����.)
//		printer();
		
		for(int i=0; i<G.get(here).size(); i++){
			int there = G.get(here).get(i);
			if(visited.get(there)==0){
				dfs(there, ++level); // dfs(there)�� ȣ���Ѵٴ� ���� �ſ� ��������. ���� ���� �����ڸ� �̿��Ͽ� ȣ���ؾ� level�� ������ ���·� ���� dfs�� ȣ��ȴٴ� ��ǿ��� �������� 
				--level; // dfs���� �ϴ� layer ����� level�� ������ 1�ܰ� �����Ѵ�.
				visited.set(there, 0); // �湮�ߴ� ����� �湮���θ� �����Ͽ� ���� ��Ʈ Ž���� ������ �ʵ��� �Ѵ�. 
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
				
			/* Vector ���� ���ƴ� ����. Ȯ���� �˾Ƶ���
			 * Vector�� size()�Լ� : ���� ������ ��� ������ ���Ͽ� �˷��ִ� �Լ��̴�. 
			 * Vector�� capacity()�Լ��� �������� ����. capacity �Լ��� ������ �뷮�� ��Ÿ���� �Լ��̴�. 
			 */
			
			// 2�� ���͸� �ʱ�ȭ �ϴ� ���
			G = new Vector<Vector<Integer>>(N);
			visited = new Vector<Integer>(N); 
			for(int i=0; i<N; i++) {
				G.add(new Vector<Integer>(N)); // �� element�� ���ؼ� �뷮�� �����ؼ� ���͸� ��������� ���Ŀ� ����� �����ϴ�.
				visited.add(0); // �̷��� '�湮���� �ʾ���'�� ��Ÿ���� ������ �־���� �Ѵ�. 
			}
			
			int x, y;
			for(int i=0; i<M; i++){
				st = new StringTokenizer(br.readLine());
				x = Integer.parseInt(st.nextToken())-1; // ������ �ȹٷ� ����. node���� name�� 1���� ���������� ��������
				y = Integer.parseInt(st.nextToken())-1;
				G.get(x).add(y);
				G.get(y).add(x); // ������ �׷���
			}
			dfsAll();
			System.out.println("#"+(t+1)+" "+max_level);
		}
		br.close();	
	}
}