package SE_microorganism;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {
	public static int N, M, K;
	public static int[][] info;
	
	public static int finalNum(){
		
		for(int i=1; i<=M; i++){
			locChange();
			readCellCheck();
			stateInspection();
		}
		
		int ret = 0;
		for(int i=0; i<K; i++){
			if(info[i][0]==1){
				ret += info[i][3];
			}
		}
		
		return ret;
	}

	public static void locChange(){
		for(int i=0; i<K; i++){
			if(info[i][0]==1){
				switch(info[i][4]){
				case 1:
					info[i][1] -= 1;
					break;
				case 2:
					info[i][1] += 1;
					break;
				case 3:
					info[i][2] -= 1;
					break;
				case 4:
					info[i][2] += 1;
					break;
				}
			}
		}
	}
	
	public static void readCellCheck(){
		for(int i=0; i<K; i++){
			if(info[i][0]==1){
				if(info[i][1]==0 || info[i][2]==0 || info[i][1]==N-1 || info[i][2]==N-1){
					info[i][3] /= 2;
					if(info[i][4]==1 || info[i][4]==3) info[i][4] += 1;
					else info[i][4] -= 1;
					if(info[i][3]==0) info[i][0]=0;
				}
			}
		}
	}
	
	public static void stateInspection(){
		for(int i=0; i<K-1; i++){
			int acc_num = info[i][3];
			int max_mic = info[i][3];
			int max_index = i;
			int target_row = -1;
			int target_col = -1;
			
			for(int j=i+1; j<K; j++){
				if((info[i][0]==1 && info[j][0]==1)&&(info[i][1]==info[j][1])&&(info[i][2]==info[j][2])){
					acc_num += info[j][3];
					target_row = info[i][1];
					target_col = info[i][2];
					
					if(max_mic < info[j][3]){
						max_mic = info[j][3];
						max_index = j;
					}
				}
			}
			
			killExcept(target_row, target_col, max_index);
			info[max_index][3] = acc_num;
		}
	}
	
	public static void killExcept(int target_row, int target_col, int max_index){
		for(int i=0; i<K; i++){
			if(info[i][0]==1 && info[i][1] == target_row && info[i][2] == target_col && i!=max_index){
				info[i][0] = 0;
			}
		}
	}
	
	public static void main(String[] args) {
		
		try{
			BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\350U2A\\Desktop\\±êÇãºê\\code_ground\\Samsung_Code_Ground\\src\\SE_microorganism\\sample_input.txt"));
			int T = Integer.parseInt(br.readLine());
			
			for(int t=0; t<T; t++){
				StringTokenizer st = new StringTokenizer(br.readLine());
				N = Integer.parseInt(st.nextToken());
				M = Integer.parseInt(st.nextToken());
				K = Integer.parseInt(st.nextToken());
				
				info = new int[K][5];
				
				for(int k=0; k<K; k++){
					st = new StringTokenizer(br.readLine());
					info[k][0] = 1;
					info[k][1] = Integer.parseInt(st.nextToken());
					info[k][2] = Integer.parseInt(st.nextToken());
					info[k][3] = Integer.parseInt(st.nextToken());
					info[k][4] = Integer.parseInt(st.nextToken());
				}
				
				System.out.println("#"+(t+1)+" "+finalNum());
			}
			br.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

}