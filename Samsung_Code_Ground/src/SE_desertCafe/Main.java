package SE_desertCafe;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

class Valid{
	boolean bool;
	int sLength;
	public Valid(boolean bool, int sLength){
		this.bool = bool;
		this.sLength = sLength;
	}
}

public class Main {
	public static int findRoute(int[][] map, int N){
		int max_length = N-1-1; // 지레 짐작으로 값을 넣는 행위를 하지 말 것. 작은 실수가 아예 잘못된 프로그램을 만들 수 있다. 
		int max_ans = -1;
		
		for(int i=0; i<N; ++i){
			for(int j=0; j<N; ++j){
				int[] top = new int[2];
				top[0] = i; top[1] = j;
				
				int tmp_length_right = 1;
				while(tmp_length_right<=max_length){
					int[] right = new int[2];
					right[0] = top[0]+tmp_length_right; right[1] = top[1]+tmp_length_right;
					if(!isFit(map, right[0], right[1])) break;
					
					int tmp_length_left = 1;
					while(tmp_length_left<=max_length){
						int[] bottom = new int[2];
						bottom[0] = right[0]+tmp_length_left; bottom[1] = right[1]-tmp_length_left;
						if(!isFit(map, bottom[0], bottom[1])) break;
						
						int[] left = new int[2];
						left[0] = top[0]+tmp_length_left; left[1] = top[1]-tmp_length_left;
						if(!isFit(map, left[0], left[1])) break;
						
						Valid v = isValid(map, top, right, bottom, left);
						if(v.bool==true){
							if(max_ans<v.sLength) max_ans = v.sLength;
						}
						tmp_length_left++;
					}
					tmp_length_right++;
				}
			}
		}
		
		return max_ans;
	}
	
	public static Valid isValid(int[][] map, int[] top, int[] right, int[] bottom, int[] left){
		Set<Integer> s = new HashSet<Integer>();
		if((diagonalCheck(map, top, right, 1, s) && diagonalCheck(map, right, bottom, 2, s) &&
		   diagonalCheck(map, bottom, left, 3, s) && diagonalCheck(map, left, top, 4, s))){
			return new Valid(true, s.size());
		}
		return new Valid(false, 0);
	}
	
	public static boolean diagonalCheck(int[][] map, int[] start, int[] end, int direction, Set<Integer> s){
		// direction 1 : 우하향, 2: 좌하향, 3: 좌상향, 4: 우상향
		int s_r = start[0]; int e_r = end[0]; 
		int s_c = start[1]; int e_c = end[1];
		
		while((s_r!=e_r) && (s_c!= e_c)){
			if(!s.contains(map[s_r][s_c])){
				s.add(map[s_r][s_c]);
				if(direction==1){
					s_r = s_r+1;
					s_c = s_c+1;
				}
				else if(direction==2){
					s_r = s_r+1;
					s_c = s_c-1;
				}
				else if(direction==3){
					s_r = s_r-1;
					s_c = s_c-1;
				}
				else{
					s_r = s_r-1;
					s_c = s_c+1;
				}
			}
			else{
				return false;
			}
		}
		return true;
	}
	
	public static boolean isFit(int[][] map, int i, int j){
		if(((0<=i) && (i<map.length)) && 
		   ((0<=j) && (j<map.length)))
		return true;
		else return false;
	}
	
	
	public static void main(String[] args) {
		try{
		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\350U2A\\Desktop\\깃허브\\code_ground\\Samsung_Code_Ground\\src\\SE_desertCafe\\sample_input.txt"));
		int T = Integer.parseInt(br.readLine());
		
		for(int i=0; i<T; i++){
			int N = Integer.parseInt(br.readLine());
			int[][] map = new int[N][N];
			StringTokenizer st;
			
			for(int j=0; j<N; ++j){
				st = new StringTokenizer(br.readLine());
				for(int k=0; k<N; ++k){
					map[j][k] = Integer.parseInt(st.nextToken());
				}
			}
		System.out.println("#"+(i+1)+" "+findRoute(map, N));	
		}
			br.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

}
