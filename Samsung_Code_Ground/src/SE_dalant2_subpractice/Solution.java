package SE_dalant2_subpractice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

//비고 : SEexpert 타인 코드 참고
public class Solution {
	public static int N; 
	public static int P; 
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(".\\src\\SE_dalant2_subpractice\\input.txt"));
		int T = Integer.parseInt(br.readLine());
		StringTokenizer st;
		
		for(int t=1; t<=T; t++){
			st = new StringTokenizer(br.readLine());
			long result = 1;
			N = Integer.parseInt(st.nextToken());
			P = Integer.parseInt(st.nextToken());
			
			for(int i=P; i>0; --i){ // 특정 숫자를 몇개의 부분으로 나눴을 때, 가장 비슷한 크기로 나누는 것이 나눈 부분의 곱이 최대가 된다.
				result *= N/i;
				N -= N/i;
			}
			// 참고 : https://math.stackexchange.com/questions/125065/partitioning-a-natural-number-n-in-order-to-get-the-maximum-product-sequence-o
			
			System.out.println("#"+t+" "+result);
		}
		br.close();
	}
}