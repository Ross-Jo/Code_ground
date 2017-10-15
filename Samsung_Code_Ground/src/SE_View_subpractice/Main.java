package SE_View_subpractice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static int L;
	public static int[] buildings;
	public static int[] d = {-2, -1, 1, 2};
	public static int[][] c;
	
	public static int findGoodPlace(){
		c = new int[L][L];
		for(int i=0; i<c.length; i++){
			Arrays.fill(c[i], -987654321); // 음의 무한대 초기화
		}
		
		int ret = 0;
		int tmp = 0;
		for(int i=0; i<L; i++){
			if((tmp=isGood(i))!=-1) ret += tmp;
		}
		return ret;
	}
	
	public static int isGood(int idx){
		int cnt = 0;
		int ret = 987654321;
		for(int i=0; i<d.length; i++){
			if(idx+d[i]>=0 && idx+d[i] <L && c[idx+d[i]][idx]!=-987654321 && c[idx+d[i]][idx]>=1){
				cnt++;
				ret = Math.min(ret, c[idx+d[i]][idx]);
			}
			else if(idx+d[i]>=0 && idx+d[i] <L && c[idx+d[i]][idx]==-987654321){
				
				if(buildings[idx+d[i]]>buildings[idx]) return -1;
				
				c[idx+d[i]][idx] = buildings[idx] - buildings[idx+d[i]];
				c[idx][idx+d[i]] = -c[idx+d[i]][idx];
				
				if(c[idx+d[i]][idx]>=1){
					cnt++;
					ret = Math.min(ret, c[idx+d[i]][idx]);
				}
			}
		}
		if(cnt==4) return ret;
		else return -1;
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(".\\src\\SE_View_subpractice\\input.txt"));
		for(int t=0; t<11; t++){
			L = Integer.parseInt(br.readLine());
			StringTokenizer st = new StringTokenizer(br.readLine());
			buildings = new int[L];
			
			for(int l=0; l<L; l++){
				buildings[l] = Integer.parseInt(st.nextToken());
			}
			
			System.out.println("#"+(t+1)+" "+findGoodPlace());
		}
		br.close();
	}
}
