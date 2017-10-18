package SE_dalant2_subpractice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

//��� : SEexpert Ÿ�� �ڵ� ����
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
			
			for(int i=P; i>0; --i){ // Ư�� ���ڸ� ��� �κ����� ������ ��, ���� ����� ũ��� ������ ���� ���� �κ��� ���� �ִ밡 �ȴ�.
				result *= N/i;
				N -= N/i;
			}
			// ���� : https://math.stackexchange.com/questions/125065/partitioning-a-natural-number-n-in-order-to-get-the-maximum-product-sequence-o
			
			System.out.println("#"+t+" "+result);
		}
		br.close();
	}
}