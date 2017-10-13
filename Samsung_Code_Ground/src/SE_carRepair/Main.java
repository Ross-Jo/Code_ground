package SE_carRepair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

class Cus implements Comparable<Cus>{ // vector �񱳽� sort ���� comparator ��ü�� ���� �Ѱ��� �Ͱ� ������
	int cusNo = 0;
	int arrivalTime = -1;
	int rec_num = 0;
	int rep_num = 0;
	int rec_wait_start = -1;
	int rep_wait_start = -1;
	int rep_q_wait_start = -1;
	
	public Cus(int cusNo, int arrivalTime, int rec_num, int rep_num, int rec_wait_start, int rep_wait_start){
		this.cusNo = cusNo;
		this.arrivalTime = arrivalTime;
		this.rec_num = rec_num;
		this.rep_num = rep_num;
		this.rec_wait_start = rec_wait_start;
		this.rep_wait_start = rep_wait_start;
	}
	
	@Override
	public int compareTo(Cus b){ // Comparator ��ü������ compare �޼ҵ带 �������̵� ������ �������
		int a_c = this.rep_q_wait_start;
		int b_c = b.rep_q_wait_start;
		int a_cc = this.rec_num; // 2��° �񱳱���
		int b_cc = b.rec_num;
		return a_c > b_c ? 1 : (a_c==b_c ? (a_cc > b_cc ? 1 : -1): -1);
	}
}

public class Main {
	public static int N, M, K, A, B, ret;
	public static int[] reception, repair, time;
	
	public static int simulator(){
		ret = 0;
		
		// timer �۵�
		int timer = 0;
		
		// �� ť ����
		Queue<Cus> customers = gen_cus();
		
		// ���� ��� ť ����
		PriorityQueue<Cus> q = new PriorityQueue<Cus>();
		
		// ���� ����ũ ����
		Cus[] rec = new Cus[N];
		
		// ���� ����ũ ����
		Cus[] rep = new Cus[M];
		
		while(true){
			
			// ���� �� ���� �˻�(����ó��)
			for(int i=0; i<N; i++){
				if(rec[i]!=null && rec[i].rec_wait_start+reception[i]==timer){
					rec[i].rep_q_wait_start = timer;
					q.add(rec[i]);
					rec[i] = null;
				}
				if(rec[i]==null && !customers.isEmpty() && customers.peek().arrivalTime<=timer){
					rec[i] = customers.poll();
					rec[i].rec_num = i+1;
					rec[i].rec_wait_start = timer;
				}
			}
			
			// ���� �� ���� �˻�(����ó��)
			for(int i=0; i<M; i++){
				if(rep[i]!=null && rep[i].rep_wait_start+repair[i]==timer){
					checker(rep[i]);
					rep[i] = null;
				}
				if(rep[i]==null && !q.isEmpty()){
					rep[i] = q.poll();
					rep[i].rep_num = i+1;
					rep[i].rep_wait_start = timer; // ���Ⱑ ����
				}
			}
			
			if(customers.isEmpty() && q.isEmpty() && allNull(rec) && allNull(rep)) break;
			timer++;
		}
		
		if(ret==0) return -1;
		else return ret;
	}
	
	public static Queue<Cus> gen_cus(){
		Queue<Cus> q = new LinkedList<Cus>();
		int cusNo = 1;
		for(int i=0; i<K; i++){
			Cus customer = new Cus(cusNo++, time[i], 0, 0, -1, -1);
			q.add(customer);
		}
		return q;
	}
	
	public static void checker(Cus target){
		if(target.rec_num==A && target.rep_num==B){
			ret += target.cusNo;
		}
	}
	
	public static boolean allNull(Cus[] array){
		boolean flag = true;
		for(int i=0; i<array.length; i++){
			if(array[i]!=null){
				flag = false;
			}
		}
		return flag;
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\350U2A\\Desktop\\�����\\code_ground\\Samsung_Code_Ground\\src\\SE_carRepair\\sample_input.txt"));
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