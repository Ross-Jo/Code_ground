package SE_dalant2_subpractice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * 단순 완전탐색의 방법으로는 시간 초과 문제에 직면한다.(프로그램이 아예 끝나질 않을 정도)
 */
public class Main {
	public static int N; 
	public static int P;
	public static int[] divider;
	public static long max;
	
	public static void findMaxAmount(int level, int pointer){
		if(level==P-1){
			int tmp = 1;
			for(int k : divider) tmp *= k;
			tmp *= N-Arrays.stream(divider).sum();

			max = Math.max(max, tmp);
			return;
		}
		
		for(int i=1; i<=N-pointer; i++){ // 이전 단계에서 선택한 갯수만큼 선택할 수 있는 최대 숫자가 줄어듦
			divider[level] = i; // 각 level에서 몇개를 선택했는가
			findMaxAmount(level+1, i+pointer);
			/* ++level과 level+1의 차이점이 존재한다!
			 * ++level 같은 경우는 level 값을 참조한 후, level값 자체를 변경시키기에 (스택에 있는 레벨값도 변경시키는 것으로 보임)호출이 끝난후 다시 원상복귀 시켜줘야 하지만
			 * level+1과 같이 호출 했을 경우는 스택에 있는 변경되지 않은 level값을 사용함으로서 그럴 필요가 없어진다. 
			 */
		}
	}
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(".\\src\\SE_dalant2_subpractice\\input.txt"));
		int T = Integer.parseInt(br.readLine());
		StringTokenizer st;
		
		for(int t=1; t<=T; t++){
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			P = Integer.parseInt(st.nextToken());
			
			max = 0;
			divider = new int[P-1];
			findMaxAmount(0, 0);
			
			System.out.println("#"+t+" "+max);
		}
		br.close();
	}
}
