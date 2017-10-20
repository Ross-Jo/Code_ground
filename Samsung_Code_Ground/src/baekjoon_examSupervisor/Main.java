package baekjoon_examSupervisor;

import java.util.Scanner;

/*
 * ���� �ſ� �ܼ��� ������ �� �˰� ���̺��ϰ� �����ߴٰ� �������� �̷��� ������ ���������� ����
 * ����1 : �׽�Ʈ ���̽����� �־����� ���� ��Ȳ�� ���ؼ� �׻� �����غ���. (�� ���� �� �������� ������ �� �ִ� �ο� ���� �ش� ������ �ο� ������ ���� ���)
 * ����2 : ������ input ������ �� ���� �ش� input�� ó���� �� �������� �ڷ����� �� ����ߴ��� �׻� ��������. (�� ���� sum�� �ڷ����� int�� �ƴ϶� long�� ��� �Ѵ�.)
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
			sum += (long)Math.ceil((peoples[i]-B > 0 ? peoples[i]-B : 0)/(double)C); // �� �������� �� ����
		}
		
		System.out.println(sum+N);
		
		sc.close();
	}
}