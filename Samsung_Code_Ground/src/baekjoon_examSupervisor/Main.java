package baekjoon_examSupervisor;

import java.util.Scanner;

/*
 * 정말 매우 단순한 문제인 줄 알고 나이브하게 접근했다가 실제에서 이렇게 했으면 망했을뻔한 문제
 * 교훈1 : 테스트 케이스에서 주어지지 않은 상황에 대해서 항상 생각해보자. (이 경우는 주 감독관이 감독할 수 있는 인원 수가 해당 교실의 인원 수보다 많은 경우)
 * 교훈2 : 문제의 input 범위를 잘 보고 해당 input을 처리할 수 있을만한 자료형을 잘 사용했는지 항상 유의하자. (이 경우는 sum의 자료형을 int가 아니라 long을 써야 한다.)
 */

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int N = sc.nextInt();
		int[] peoples = new int[N];
		
		for(int i=0; i<N; i++) peoples[i] = sc.nextInt();
		
		int B = sc.nextInt();
		int C = sc.nextInt();
		
		long sum = 0;
		
		for(int i=0; i<N; i++){
			sum += (long)Math.ceil((peoples[i]-B > 0 ? peoples[i]-B : 0)/(double)C); // 부 감독관의 수 산출
		}
		
		System.out.println(sum+N);
		
		sc.close();
	}
}