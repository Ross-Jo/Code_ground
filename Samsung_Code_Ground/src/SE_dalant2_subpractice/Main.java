package SE_dalant2_subpractice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * �ܼ� ����Ž���� ������δ� �ð� �ʰ� ������ �����Ѵ�.(���α׷��� �ƿ� ������ ���� ����)
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
		
		for(int i=1; i<=N-pointer; i++){ // ���� �ܰ迡�� ������ ������ŭ ������ �� �ִ� �ִ� ���ڰ� �پ��
			divider[level] = i; // �� level���� ��� �����ߴ°�
			findMaxAmount(level+1, i+pointer);
			/* ++level�� level+1�� �������� �����Ѵ�!
			 * ++level ���� ���� level ���� ������ ��, level�� ��ü�� �����Ű�⿡ (���ÿ� �ִ� �������� �����Ű�� ������ ����)ȣ���� ������ �ٽ� ���󺹱� ������� ������
			 * level+1�� ���� ȣ�� ���� ���� ���ÿ� �ִ� ������� ���� level���� ��������μ� �׷� �ʿ䰡 ��������. 
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
