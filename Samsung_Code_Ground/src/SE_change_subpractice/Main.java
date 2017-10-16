package SE_change_subpractice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
	public static int N;
	public static int[] level = {50000, 10000, 5000, 1000, 500, 100, 50, 10};
	
	public static int[] compChange(){ // Greedy method�� �ذᰡ��
		int[] amount = new int[level.length];
		for(int i=0; i<level.length; i++){
			amount[i] = N/level[i];
			N = N%level[i];
		}
		return amount;
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(".\\src\\SE_change_subpractice\\input.txt"));
		
		int T = Integer.parseInt(br.readLine());
		
		for(int t=0; t<T; t++){
			N = Integer.parseInt(br.readLine());
			int[] ret = compChange();
			System.out.println("#"+(t+1));
			
			for(int k : ret) System.out.print(k+" "); // �� ������� �迭�� �� �� ȿ�������� ����ϴ� ����� ������?

			System.out.println();
		}
		br.close();
	}
}