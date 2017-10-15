package SE_mode_subpractice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(".\\src\\SE_mode_subpractice\\input.txt"));
		
		for(int i=0; i<10; i++){
			System.out.print("#"+br.readLine()+" ");
			StringTokenizer st = new StringTokenizer(br.readLine());
			int[] count = new int[101];

			for(int j=0; j<1000; j++){
				count[Integer.parseInt(st.nextToken())]++;
			}
			
			int[] ret = new int[2];
			for(int j=0; j<100; j++){
				if(count[j]>ret[1]) {
					ret[0] = j;
					ret[1] = count[j];
				}
				else if(count[j]==ret[1]){
					ret[0] = j;
				}
			}
			System.out.println(ret[0]);
		}
		br.close();
	}
}
