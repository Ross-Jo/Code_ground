package SE_honey; // �����ʿ�

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {
	// 1���� ��� ��ȸ�� max_income ��ȯ
	// �ð��� ���� �ɷȴ� �κ� : ���� 
	/*
	 * a. visited������ ���� ��Ͽ� ���� ���� �� �ű� ���
	 * b. DFS�� ������ �̿��� �κ����� ���� �� �˻�. Ư�� �ٽ�Ŷ �ȿ��� ���  =<C �̸鼭 �������� �ִ��� ������ ã�� ���ΰ�?
	 * c. �׸��� �� - 2�� for������ ������ ���ϴ� ���ۿ� ���°�? : �ϴ��� �� ����ۿ� ���� ���δ�. 
	 *    CF) 1���� Integer array�� ���� �� : IntStream.of(array).sum();
	 *
	 * [�����ؾ� �� ��/�̽�]
	 * ������ ���� ���� ���Ǹ� �� �� �����ϰ��ϰ� ��Ȯ�ϰ� �����ؾ� ��, DFS, BFS, Ʈ�� �� ��� ������ �̿��ϴ� �κп� ���� �����ʿ� 
	 * - ���������� Ȱ�� : �������� �Һ��ϴ� ���� ��� ���������� ���� Ȱ���� �����ϰ� ���� -> �������� ���̴� �Һ����� �������� ���ִ�, �Ѱܾ� �� �Ķ���Ͱ� �ξ� ����������. 
	 * - DFS/BFS�� Ȱ���� �ڵ� �ۼ���, �� ��� �ܰ踶�� stack�� ����Ǵ°�, ��͸� �԰� ���ÿ� ������ �ٳ�� �ϴ� ���� �� �Ǻ��ؾ� ��(�⺻���� ���������� �ٽ� �� �� ��������). ���� : https://books.google.co.kr/books?id=VPzHDgAAQBAJ&pg=PA59&lpg=PA59&dq=%EC%9E%AC%EA%B7%80%ED%95%A8%EC%88%98+stack+%EC%A0%80%EC%9E%A5&source=bl&ots=I_MCleoS1p&sig=MXFu95FM22e06WnNzDeK86dQrFk&hl=ko&sa=X&ved=0ahUKEwj2naDbvN7WAhUJvrwKHQ8pAroQ6AEIWzAI#v=onepage&q=%EC%9E%AC%EA%B7%80%ED%95%A8%EC%88%98%20stack%20%EC%A0%80%EC%9E%A5&f=false
	 * - DFS/BFS���� �������� Ȱ�� ��, �� ������ �ǹ̸� ��Ȯ�� �ϰ�, �̿� ���� �ʱ�ȭ ��ġ�� ��Ȯ�� ��������. Ȥ�� �ǵ�ġ ���� ��Ⱑ �� �̻��� ����� ���°��� ��������. 
	 * - �迭 ��� ��, ArrayIndexOutofBound ������ ���Ͽ� �׻� ���ο� �ΰ� �ش� ������ �߻����� �ʵ��� ��������
	 * - ���� ���ڴ� ���α׷��� �ӵ��� �϶���Ű�°�? : ���ڸ� �ѱ� ��, ���� ���簡 �Ͼ�⿡ ���α׷��� �ӵ��� ������ ��ġ��� ������ �� ������� ���� �̹��ϴ�. ������ ������ �ʹ� �Ű澲�⺸�ٴ� �˱⽱�� ��Ȯ�� �ڵ� �ۼ��� �� �� ��Ŀ������. ���� : https://stackoverflow.com/questions/13768637/java-method-with-many-parameters-performance
	 * - �ٷ� 1step ���� ����� ��� ����� ���ΰ� - �������� �������� ����� ���ȣ��� visit���θ� ������ �ٴϸ鼭 stack�� �̿��� ���� ���� ����� ����ε�, �� ���� ����� �ִ��� �����غ���. 
	 *                                 - �ƴϸ� �ٷ� ���ܰ��� ���� ������ ����ϴ� ���ο� 2���� �迭�� ����, ���¸� �ǵ��� �� ��� ������ �� ����.
	 * - ����Ž���� ��ݱ����� ���Ͽ� �ٽ��ѹ� ��������.
	 * 
	 * */
	public static int N, M, C;
	public static int[][] map;
	
	public static int[][] tmp_visited;
	public static int max_income;
	public static int tmp_max;
	
	public static int findMaxIncome(int[][] visited){
		max_income = 0; // findMaxIncome �Լ��� ��� 1���� map 1�� ������ ������ �� �ִ� �ִ� ������ ��ȯ�ϱ⿡ �� �Լ��� �մܿ��� �ش� ���������� �ʱ�ȭ ���Ѿ� �Ѵ�. 
		int tmp = 0;
		
		for(int i=0; i<N; i++){
			for(int j=0; j<=N-M; j++){
				if((!isVisited(visited[i], j, j+M-1) && partialSum(map[i], j, j+M-1)<=C)){
					if(max_income < (tmp = calcSqSum(map[i], j, j+M-1))){ // ������ �� ������ ����� ��
						max_income = tmp;
						tmp_visited = checkVisit(i, j, j+M-1);
					}
				}
				else if(!isVisited(visited[i], j, j+M-1)){
					int[] checker = new int[N];
					int sum = 0;
					tmp_max = 0; // tmp_max��� ������ map�� ��ȸ��, Ư�� ��Ŷ(M���� ��� ���� >C��)���� ���Ⱑ���� �ִ� ������ �ǹ��ϱ⿡ ���⿡�� �ʱ�ȭ�� ���Ѿ� �Ѵ�.  
					
					// sum��, checker�� ��͸� ������ ���� �������� �� ���̱⿡ ����Լ� �ۿ��� �Ѱ��ش�.
					
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
		
		checker[startIndex+cnt] = 0; // DFS������ �ռ� ���� ������ ��Ī������ �� ��
		sum -= row[startIndex+cnt];
		findMax(row, cnt+1, depth, startIndex, endIndex, checker, sum, rowIndex);
		
		return tmp_max;
	}
	
	// �Ʒ��� helper �Լ���
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
			BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\350U2A\\Desktop\\�����\\code_ground\\Samsung_Code_Ground\\src\\SE_honey\\sample_input.txt"));
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