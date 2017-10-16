package SE_swimingPool;

import java.util.*;

public class Solution {
	static int min = 1<<30;
	static int[] costTable = new int[4];
	static int[] plan = new int[13];
	
	static void Search(int month, int cost){
		if(month>=12){
			min = Math.min(cost, min);
			return;
		}
		// 같은 층위에 있는 요소의 다른 선택을 어떻게 완전탐색으로 구현할 것인가에 대한 좋은 예. 잘 알아두자
		Search(month+1, cost+costTable[0]*plan[month]);
		Search(month+1, cost+costTable[1]);
		Search(month+3, cost+costTable[2]);
		Search(month+12, cost+costTable[3]);
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int T = sc.nextInt();
		for(int t=1; t<=T; t++){
			min = 1<<30;
			for(int i=0; i<4; i++) costTable[i] = sc.nextInt();
			for(int i=0; i<12; i++) plan[i] = sc.nextInt();
			Search(0,0);
			System.out.println("#"+t+" "+min);
		}
		sc.close();
	}
}