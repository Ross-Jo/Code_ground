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
		
		for(int i=0; i<d.length; i++){ // 좌로 2채, 우로 2채의 건물에 대하여 모두 체크
			if(idx+d[i]>=0 && idx+d[i] <L && c[idx+d[i]][idx]!=-987654321 && c[idx+d[i]][idx]>=1){ // 인덱스 범위 체크, 이전 계산여부 체크
				cnt++;
				ret = Math.min(ret, c[idx+d[i]][idx]);
			}
			else if(idx+d[i]>=0 && idx+d[i] <L && c[idx+d[i]][idx]==-987654321){ // 이전에 계산을 하지 않은 경우
				
				if(buildings[idx+d[i]]>buildings[idx]) return -1; // 만약 (주어진 범위 내에서) 자신보다 더 큰 빌딩을 만났을 경우, 함수식을 종료함.
				
				c[idx+d[i]][idx] = buildings[idx] - buildings[idx+d[i]]; // idx+d[i] 번째 빌딩과, idx 빌딩의 높이 차이. 기준은 idx빌당.
				c[idx][idx+d[i]] = -c[idx+d[i]][idx];
				
				/* 점화식을 사용할 때는 해당 점화식의 의미를 명확히 규정하고 사용하자. 그렇게 하지 않았을 경우 의도치 않은 결과가 나올 가능성이 다분하다. 
				 */
				
				if(c[idx+d[i]][idx]>=1){ // 해당 건물과 비교시 조망이 보이는 (층이 있는)지 체크
					cnt++;
					ret = Math.min(ret, c[idx+d[i]][idx]);
				}
			}
		}
		
		if(cnt==4) return ret; // 좌로 2채, 우로 2채 비교 했는데 다 조망이 보인다면
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
