package SE_hikingPath; // http://bumbums.tistory.com/1

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

// DFSȰ��� ������ �濡 ���� üũ ������ �߿��ϴ� - ��� ���� ���� �ǵ��� �ö� �װ��� ��ŷ�� �����ϴ��� �� �ľ��� ��.

public class Main {
	public static int[][] d = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}}; // left, up, right, down
	static int result = 0;
	static int tmp_max = 0;
	
	public static int findPath(int[][] map, int N, int K, int H){
		int[][] STpoint = findSTpoint(map, N, H);				

		int length;
		boolean card;
		int[][] check;

		result = 0;

		for(int i=0; i<N; ++i){		
			for(int j=0; j<N; ++j){
				if(STpoint[i][j]==1){
					
					tmp_max = 0;
					length = 0;
					card = true;
					check = new int[N][N];
					
					DFS(map, check, N, K, i, j, card, length);
					
					if(result<tmp_max) result = tmp_max;
				}
			}
		}
		
		return result;
	}
	
	public static void DFS(int[][] map, int[][] check, int N, int K, int i, int j, boolean card, int length){
		check[i][j] = 1;
		length++;
		if(length > tmp_max) tmp_max = length;
		
		for(int k=0; k<d.length; ++k){
			if((0<=(i+d[k][0]) && (i+d[k][0])<N) && // �����ڸ��� �׻� üũ������. - ���� �ϴ� �������� Ȥ���� ��������
			   (0<=(j+d[k][1]) && (j+d[k][1])<N) &&
			   (check[i+d[k][0]][j+d[k][1]]==0) &&
			   (map[i+d[k][0]][j+d[k][1]]<map[i][j])){
				
				DFS(map, check, N, K, i+d[k][0], j+d[k][1], card, length);
				check[i+d[k][0]][j+d[k][1]] = 0; // ���� DFS���ΰ� ���ؼ� ����. '����! �κп� ���ؼ�' ���� �� ���� ����� �Ѵ�.
			}
			else if((0<=(i+d[k][0]) && (i+d[k][0])<N) &&
					(0<=(j+d[k][1]) && (j+d[k][1])<N) &&
					(check[i+d[k][0]][j+d[k][1]]==0) &&
					(card == true)) {
				
				for (int h = 1; h <= K; ++h) {
					if ((map[i][j] > map[i + d[k][0]][j + d[k][1]] - h)) {

						boolean hit = false;

						for (int re = 0; re < d.length; ++re) {
							if ((0 <= (i + d[k][0] + d[re][0]) && (i + d[k][0] + d[re][0]) < N)
									&& (0 <= (j + d[k][1] + d[re][1]) && (j + d[k][1] + d[re][1]) < N))
								if (((map[i + d[k][0]][j + d[k][1]] - h) > map[i + d[k][0] + d[re][0]][j + d[k][1] + d[re][1]])) {
									
									hit = true;
									
									card = false;
									check[i + d[k][0]][j + d[k][1]] = 1;
									length++;
									
									DFS(map, check, N, K, i + d[k][0] + d[re][0], j + d[k][1] + d[re][1], card, length);
									
									// DFS ������ �湮������ ��ŷ�� �����ϴ� ������ �ٽ� �� �� �ָ��ϰ� �������� ��������
									card = true;
									check[i + d[k][0]][j + d[k][1]] = 0;
									check[i + d[k][0] + d[re][0]][j + d[k][1] + d[re][1]] = 0;
									length--;
								}
						}
						if (card == true && hit == false) { // ������ ������ ù ���� �� �ݿ����� �ʰ� �ڴʰ� �߰��� �κ��ε�, �ʱ⿡ ���� ���ǿ� ��������.
							length++;                       // ���� �� �� �ְ�, ���θ� ������ �ֺ��� ���� �� �������� ���� ������ ��� H��ŭ ���� �Ĵ� ������ �����ϰ� maximum length�� ���θ� ����, ����Ѵ�. 
							if (length > tmp_max) tmp_max = length;
							length--;
						}
					}
				}
			}
		}
	}
	
	public static int[][] findSTpoint(int[][] map, int N, int H){
		int[][] STpoint = new int[N][N];
		for(int i=0; i<N; i++){
			for(int j=0; j<N; j++){
				if(map[i][j]==H)
					STpoint[i][j] = 1;
			}
		}
		return STpoint;
	}
	
	// 3<=N<=8
	// 1<=k<=5
	public static void main(String[] args) {
		try {
			// Scanner sc = new Scanner(System.in); sc.nextLine(); sc.close(); try-catch ����; ���� Ŭ���� �̸��� Solution
			BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\350U2A\\Desktop\\�����\\code_ground\\Samsung_Code_Ground\\src\\SE_hikingPath\\sample_input.txt"));
			int T = Integer.parseInt(br.readLine());
			StringTokenizer st;
			
			for(int i=0; i<T; ++i){
				st = new StringTokenizer(br.readLine());
				int N = Integer.parseInt(st.nextToken());
				int K = Integer.parseInt(st.nextToken());
				
				int[][] map = new int[N][N];
				int H = 0;
				
				for(int j=0; j<N; ++j){
					st = new StringTokenizer(br.readLine());
					for(int k=0; k<N; k++){
						map[j][k] = Integer.parseInt(st.nextToken());
						if(map[j][k]>H) H = map[j][k];
					}
				}
				System.out.println("#"+(i+1)+" "+findPath(map, N, K, H));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}