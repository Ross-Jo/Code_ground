package SE_carRepair; // 이 문제는 알고리즘 문제라기보다 객체지향적 프로그램을 할 수 있는지 물어보는 문제 같았다.

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.PriorityQueue; // 우선순위 큐의 사용방법을 확실히 알아두자
import java.util.Queue;
import java.util.StringTokenizer;

class Cus implements Comparable<Cus>{
	/* vector의 sort 사용시 comparator 객체를 만들어서 넘겨준 것과 비교하자. 
	 * priority 큐에서는 comparable 인터페이스를 구현한다.
	 */
	int cusNo = 0;
	int arrivalTime = -1;
	int rec_num = 0;
	int rep_num = 0;
	int rec_wait_start = -1;
	int rep_wait_start = -1;
	int rep_q_wait_start = -1;
	
	public Cus(int cusNo, int arrivalTime){
		this.cusNo = cusNo;
		this.arrivalTime = arrivalTime;
	}
	
	@Override
	public int compareTo(Cus b){ 
		/* Comparator 객체에서는 compare 메소드를 오버라이드 했음을 기억하자
		 * Comparable 인터페이스를 구현하는 경우는 compareTo 메소드를 사용!
		 */
		int a_c = this.rep_q_wait_start;
		int b_c = b.rep_q_wait_start;
		int a_cc = this.rec_num; // 2번째 비교기준
		int b_cc = b.rec_num;
		return a_c > b_c ? 1 : (a_c==b_c ? (a_cc > b_cc ? 1 : -1): -1); // 만약 대기 시작시간이 같을 경우, 2번쨰 비교기준을 이용해 이용한 접수창구 번호를 비교기준으로 사용한다. 
	}
}

public class Main {
	public static int N, M, K, A, B, ret;
	public static int[] reception, repair, time;
	
	public static int simulator(){
		ret = 0; // 전역변수의 초기화 위치를 유의하자
		
		// timer 작동
		int timer = 0;
		
		// 고객 큐 생성
		Queue<Cus> customers = gen_cus();
		
		// 정비 대기 큐 생성
		PriorityQueue<Cus> q = new PriorityQueue<Cus>();
		
		// 접수 데스크 생성
		Cus[] rec = new Cus[N];
		
		// 정비 데스크 생성
		Cus[] rep = new Cus[M];
		
		while(true){
			// 진출 및 진입 검사(접수처리) : 문제의 특성을 잘 보고 진입을 먼저 처리할 지, 진출을 먼저 처리할 지 결정하자. 
			// 그런데 보통 진출을 먼저 처리하고 진입을 처리하는 경우가 많은 듯 보인다(문제들의 특성상).
			for(int i=0; i<N; i++){
				if(rec[i]!=null && rec[i].rec_wait_start+reception[i]==timer){ // 항상 null 관련 오류에 유의하자
					rec[i].rep_q_wait_start = timer;
					q.add(rec[i]);
					rec[i] = null;
				}
				if(rec[i]==null && !customers.isEmpty() && customers.peek().arrivalTime<=timer){ // 큐 혹은 우선순위 큐에서도 null 관련 오류를 유의하자
					rec[i] = customers.poll();
					rec[i].rec_num = i+1; // 접수 창구 번호는 1번부터 시작
					rec[i].rec_wait_start = timer;
				}
			}
			
			// 진출 및 진입 검사(정비처리)
			for(int i=0; i<M; i++){
				if(rep[i]!=null && rep[i].rep_wait_start+repair[i]==timer){
					checker(rep[i]);
					rep[i] = null;
				}
				if(rep[i]==null && !q.isEmpty()){
					rep[i] = q.poll();
					rep[i].rep_num = i+1; // 정비 창구 번호는 1번부터 시작
					rep[i].rep_wait_start = timer;
				}
			}
			
			if(customers.isEmpty() && q.isEmpty() && allNull(rec) && allNull(rep)) break; // 탈출 조건은 모든 시스템 내에 사람이 없는 경우이다. 
			timer++;
		}
		
		if(ret==0) return -1;
		else return ret;
	}
	
	public static Queue<Cus> gen_cus(){ // 최초 고객 생성
		Queue<Cus> q = new LinkedList<Cus>();
		int cusNo = 1; // 고객 번호는 1번부터 시작
		for(int i=0; i<K; i++){
			Cus customer = new Cus(cusNo++, time[i]);
			q.add(customer);
		}
		return q;
	}
	
	public static void checker(Cus target){
		if(target.rec_num==A && target.rep_num==B){
			ret += target.cusNo;
		}
	}
	
	public static boolean allNull(Cus[] array){ // 이와 같은 유틸리티 함수를 좀 더 효율적으로 작성할 수 있는 방안이 있는지 고민하자. 
		boolean flag = true;
		for(int i=0; i<array.length; i++){
			if(array[i]!=null){
				flag = false;
			}
		}
		return flag;
	}
	/* 자바 Array가 특정 값을 포함하고 있는지 알고 싶다면? : https://stackoverflow.com/questions/1128723/how-can-i-test-if-an-array-contains-a-certain-value
	 * Arrays.asList(yourArray).contains(yourValue) : Warning: this doesn't work for arrays of primitives (see the comments).
	 * 
	 * You can now use a Stream to check whether an array of int, double or long contains a value (by respectively using a IntStream, DoubleStream or LongStream)
	 * 
	 * Ex)
	 * int[] a = {1,2,3,4};
	 * boolean contains = IntStream.of(a).anyMatch(x -> x == 4);
	 */
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\350U2A\\Desktop\\깃허브\\code_ground\\Samsung_Code_Ground\\src\\SE_carRepair\\sample_input.txt"));
		int T = Integer.parseInt(br.readLine());
		
		for(int t=0; t<T; t++){
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			A = Integer.parseInt(st.nextToken());
			B = Integer.parseInt(st.nextToken());
			
			reception = new int[N];
			st = new StringTokenizer(br.readLine());
			for(int i=0; i<N; i++){
				reception[i] = Integer.parseInt(st.nextToken());
			}
			
			repair = new int[M];
			st = new StringTokenizer(br.readLine());
			for(int i=0; i<M; i++){
				repair[i] = Integer.parseInt(st.nextToken());
			}
			
			time = new int[K];
			st = new StringTokenizer(br.readLine());
			for(int i=0; i<K; i++){
				time[i] = Integer.parseInt(st.nextToken());
			}
			System.out.println("#"+(t+1)+" "+simulator());
		}
		br.close();
	}
}