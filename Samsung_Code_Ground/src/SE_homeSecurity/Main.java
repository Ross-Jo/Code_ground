package SE_homeSecurity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {

	public static int counter(int[][] map, int N, int M){
		int ans = -1;
		for(int i=0; i<N; i++){
			for(int j=0; j<N; j++){
				ans = Math.max(ans, scan(map, i, j, N, M));
			}
		}
		return ans;
	}
	
	public static int scan(int[][] map, int row, int col, int N, int M){
		int max_house = -1;
		for(int k=1; k<=(2*N-1); ++k){
			int house = 0;
			int cost = k*k+(k-1)*(k-1);
			for(int i=0; i<N; ++i){
				for(int j=0; j<N; ++j){
					if(((Math.abs(i-row)+Math.abs(j-col))<=(k-1)) && map[i][j]==1){
						house++;
					}
				}
			}
			if((house*M-cost)>=0){
				max_house = Math.max(max_house, house);
			}
		}
		return max_house;
	}
	
	public static void main(String[] args) {
		try{
		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\350U2A\\Desktop\\±Í«„∫Í\\code_ground\\Samsung_Code_Ground\\src\\SE_homeSecurity\\sample_input.txt"));
		
		int T = Integer.parseInt(br.readLine());
		
		for(int t=0; t<T; ++t){
			StringTokenizer st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			
			int[][] map = new int[N][N];
			for(int i=0; i<N; i++){
				st = new StringTokenizer(br.readLine());
				for(int j=0; j<N; j++){
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			System.out.println("#"+(t+1)+" "+counter(map, N, M));
		}
		br.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

}
