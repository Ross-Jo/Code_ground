package SE_lunchHour;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

/*
 * �� ������ ���� �ܼ��� �ùķ��̼� ������� �����ϰ� �����ϰ� �����ߴµ�, �������� ��û �����ɷȰ�, �ڵ嵵 ���İ�Ƽ �ڵ� �����̴�. �ڹ� �ڵ� �ۼ��� �����Ͽ� ������� ���� ���� ��������.
 * �ùķ��̼� ������� �����Ǵ� �͵��� ���� ��� �ؾߵ��� ������ ������ �Ǵ°� �׷��� �Ӹ��� ������ �ڵ�� ������ �Ű����� �ʴ� ��찡 ���� �� ����.
 * �� ������ ��� �ڵ带 �� �� �����ϰ� ��Ȯ�ϰ� © �� �ִ� ����� �ִ��� �ٽ��ѹ� �����غ��� �ٽ�Ǯ��.
 * 
 * - ������ ������ ��� ������ �ű� �� ������ �����غ���
 *   : �ڹ� �� �� �ͼ�������
 *   : ���� ������ ����� ������ �׻� ����� ��
 *   
 * - ��Ŭ������ ����� ����� ���� ���������� �̿�����(����뿡�� �ð��� ���� �ɸ��ٸ� ����� �ð��� ��� ���� �� ������ �ɵ��ְ� ����غ���)
 *   : ���������� : https://spoqa.github.io/2012/03/05/eclipse-debugger.html
 *   : Debug - Expressions���
 *   : Debug - Display��� [�巡���ؼ� �����Կ� ����, �� ���ƿ��� ����� �� �ִ� ��ɾ �����ϰų� ������ ���� �����ϴ� ���� �����ϱ⿡ ������ ȯ���� ����]
 *   : �극��ũ ����Ʈ�� �������� [Breakpoint Properties: Hit count(hit count ���� ��ġ �������� ���ٰ� ������ hit count�� �����ϴ� ������ �����Ѵ�), Conditional ����� Ȱ��]
 *     CF) �극��ũ ����Ʈ ���� �����ϸ鼭 �˰� �� ��. 
 *         �迭�� ���빰 �񱳹�� 2���� : Arrays.equals(), Arrays.deepEquals() * ���� : http://www.geeksforgeeks.org/compare-two-arrays-java/
 *         deepEquals�� ��� �迭 ���ο� �迭�� �ְų�, �迭 ���� ��Ұ� �ٸ� ��ü�� ����Ű���� �� ���빰�� ������ ��� ��������� �迭 ��ҵ��� ���ϸ鼭 ���� ���ϼ��� ������. 
 * 
 * - �ڹ��� Collections�鿡 ���Ͽ� ��Ȯ�� ��������
 *   : Vector�� �����Ͽ� Vector�� sorting�ÿ��� ������ ���� �Ѵ�. Ex) Collections.sort(people_by_stairs.elementAt(i), new Comp());
 *     ������ 1. Collections.sort�� ����Ѵٴ� ��
 *     ������ 2. Collections.sort�� 2��° ���ڷ� Comparator�� ������ ��ü�� �Ѱܾ� �Ѵٴ� ��.
 *     class Comp implements Comparator<Integer>�� ���� ���ο�  Ŭ������ �����ϰ�, compare�޼ҵ带 �������̵� �Ѵ�.
 *     import java.util.Comparator; �� ����� ���� ��������
 *     
 *   : Array�� ArrayList���� �������� ��Ȯ�� ����(Array vs ArrayList)
 *     ������ >> �ʱ�ȭ�� ���� / �ʱ�ȭ�� ����� ǥ������ ����. ������
 *     �ӵ� >> �ʱ�ȭ�� �޸𸮿� �Ҵ�Ǿ� �ӵ��� ������ / �߰��� �޸𸮸� ���Ҵ��Ͽ� �ӵ��� ������
 *     ���� >> ������ ���� �Ұ� / �߰� ���� ����
 *     ������ >> ���� / �Ұ���
 *
 */

class Comp implements Comparator<Integer>{
	@Override
	public int compare(Integer o1, Integer o2){
		return o1 > o2 ? 1 : (o1 == o2) ? 0 : -1; // ���ʼ��� �� ũ�� ���, ������ 0, ������ ���� �� ũ�� ������ ��ȯ�Ѵ�. 
	}
}

public class Main {
	public static int N;
	public static int[][] map;
	
	public static int num_of_people;
	public static int[] stairs;
	public static int[][] distance;
	public static int longest;
	
	public static int findMinTime(){
		calcDistance(); // �� ������ ������κ��� �� 2���� ����Ա������� �Ÿ��� ����Ѵ�.
		int[] chosen = new int[num_of_people];
		return chooseStairs(chosen, 0, 987654321);
	}
	
	public static void calcDistance(){
		int index = 0;
		distance = new int[2][num_of_people];
		for(int i=0; i<N; i++){
			for(int j=0; j<N; j++){
				if(map[i][j]==1){
					distance[0][index] = Math.abs(i-stairs[0])+Math.abs(j-stairs[1]);
					distance[1][index] = Math.abs(i-stairs[2])+Math.abs(j-stairs[3]);
					index++;
				}
			}
		}
	}
	
	public static int chooseStairs(int[] chosen, int depth, int min_time){
		if(depth==num_of_people){
			int time = 0;
			
			longest = 0; // Ư�� ���̽��� �ִ� �̵��ð��� ����Ѵ�. ���� : ���� break ���ǿ��� Ȱ���ϱ� ����
			for(int i=0; i<chosen.length; i++){
				longest = Math.max(longest, distance[chosen[i]][i]);
			}
			
			ArrayList<Integer> stair0 = new ArrayList<Integer>();
			ArrayList<Integer> stair1 = new ArrayList<Integer>();
			
			// Iterator<Integer> it0 = stair0.iterator();
			// Iterator<Integer> it1 = stair1.iterator();

			/*
			 * ���� ���� �ݺ��ڸ� �ۿ��� �����ϰ� �ڵ带 �����Ű�� ������ ������ ����. ConcurrentModificationException.
			 * �� ������ iterator�� �����Դµ�, �� iterator�� �ٶ� ���� �ִ� ������ �����Ͱ� ����Ǿ��� ������ �߻��ϴ� ���̴�.(���� : http://egloos.zum.com/iilii/v/5350490)
			 * �׷��� �Ʒ��� ���� for�� ���ο���(for���� ���ư� ������ iterator�� �ҷ������) iterator�� �̿��� ArrayList ����� ������ ���� �ʴ´�.
			 */
			
			Vector<Vector<Integer>> sorted_people = sortPeople(chosen);
			
			while (true) {
				
				// stair0 ��� �Ϸ� �˻�
				if(stair0.size()>0){
					for(Iterator<Integer> it0 = stair0.iterator(); it0.hasNext() ; )
					{
						Integer value = (Integer)it0.next();
						if(value+1+map[stairs[0]][stairs[1]]<=time) // +1�� ���ð��� �ݿ��ϱ� �����̴�.
						{
							it0.remove();
						}
					}
				}
				
				// stair0 ������� �˻� 
				if(sorted_people.elementAt(0).size()>0){
					for(ListIterator<Integer> pe0 = sorted_people.elementAt(0).listIterator(); pe0.hasNext();){
						/*
						 * �ݺ��ڸ� �̿��ؼ� Vector�� ��Ҹ� �����Ű�� �ʹٸ� ListIterator�� ����ؾ� �Ѵ�. 
						 * �ݺ��ڰ� Ư�� ��Ҹ� ����ų ��, ListIterator�� set�޼ҵ带 �̿��Ѵٸ� �ش� ����� ���� �ٲ� �� �ִ�.
						 * ������ ����� ������ Collections�� ������ �� �����ϴ�.
						 * import java.util.ListIterator;�� ����� ���� ��������. �׸��� Iterator != ListIterator�̴�(���������, ���̸� ��������)
						 */
						Integer value = (Integer)pe0.next();
					
						if(time>=value+1 && stair0.size()<3){ // ���� �� �ð��� �Ǿ���, ť�� �� �ڸ��� �ִٸ�
							stair0.add(value); // �ش� ���Ҹ� �־��ְ�
							pe0.remove(); // sorted_people �ٱ��Ͽ����� ��ܿ� �־��� ����� ���Ѵ�
						}
						else if(value+1<=time){ // ���� �� �ð��� �Ǿ��µ��� �����ٸ�
							pe0.set(value+1); // �ش� ���Ұ� ������ ��(�⺻ �����ð�)�� ��ٸ� �ð� ��ŭ��(���⼭�� 1�� ��ٸ� ������ 1��) delaying�� �ݿ��Ѵ�.
						}
					}
				}
				
				// stair1 ��� �Ϸ� �˻�
				if(stair1.size()>0){
					for(Iterator<Integer> it1 = stair1.iterator(); it1.hasNext() ; )
					{
						Integer value = (Integer)it1.next();
						if(value+1+map[stairs[2]][stairs[3]]<=time)
						{
							it1.remove();
						}
					}
				}
				
				// stair1 ��� ���� �˻�
				if(sorted_people.elementAt(1).size()>0){
					for(ListIterator<Integer> pe1 = sorted_people.elementAt(1).listIterator(); pe1.hasNext();){
						Integer value = (Integer)pe1.next();
						if(time>=value+1 && stair1.size()<3){
							stair1.add(value);
							pe1.remove();
						}
						else if(value+1<=time){
							pe1.set(value+1);
						}
					}
				}
				// ��Ȯ�� ���� ������ �ϴµ� 2�� �̻� �ݺ��� �ڵ�� �Լ��� �̿��ؼ� �ٿ�����. - ������ 2���ۿ� ��� �׳� ����������, �������ʹ� ��������.
				
				// Ż������
				if(time > longest+1 && stair0.size()==0 && stair1.size()==0) break; // ���� ���� �ʿ�
				
				// �����ġ ����
				time++;
			}
			return time;
		}
		
		int ret = 0; // �ӽ����� ������ �ϴ� ������ ���� ��ġ�� ��������. ����Լ� ���� �Լ� �����ڵ��� ������ �ſ� �߿��ϴ�. 
		
		// ��� ��쿡 ���� ����Ž���� �ǽ��Ѵ�. �� �ڸ��� �ο��� 2���� ��� ������ �����ϴ� ����̴�. ���� 2^Number_of_people ����� �������� ������ �ȴ�. 
		chosen[depth] = 0;
		ret = chooseStairs(chosen, depth+1, min_time); min_time = Math.min(min_time, ret);
		chosen[depth] = 1;
		ret = chooseStairs(chosen, depth+1, min_time); min_time = Math.min(min_time, ret);
		
		return min_time; // ��͸� Ÿ�� �� ������ ������ �����ϰ�, ���ÿ� ���������� ��ȯ�ؾ� �ϴ� ���� ��� �Ѱ���� �����ؼ� �� ��.
	}
	
	public static Vector<Vector<Integer>> sortPeople(int[] chosen){
		Vector<Vector<Integer>> people_by_stairs = new Vector<Vector<Integer>>();

		for(int i=0; i<2; i++) people_by_stairs.add(new Vector<Integer>());
		
		for(int i=0; i<chosen.length; i++){
			if(chosen[i]==0) people_by_stairs.elementAt(0).add(distance[0][i]);
			else people_by_stairs.elementAt(1).add(distance[1][i]);
		}
		
		for(int i=0; i<2; i++) Collections.sort(people_by_stairs.elementAt(i), new Comp());
		
		return people_by_stairs;
	}
	
	public static void main(String[] args) throws IOException { 
    // �������α׷��̳� Ư���� ������ ���α׷��� �ۼ��� ���� try-catch���� ����ؾ߰�����, �˰��� ����Ǯ�̿����� IOException ������ �ϴ� �̷��� ���ٷ� �ۼ�����.
			BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\350U2A\\Desktop\\�����\\code_ground\\Samsung_Code_Ground\\src\\SE_lunchHour\\sample_input.txt"));
			int T = Integer.parseInt(br.readLine());
			
			for(int t=0; t<T; t++){
				
				// ���������� �ʱ�ȭ ��ġ�� ���ؼ��� �׻� ��������
				N = Integer.parseInt(br.readLine());
				map = new int[N][N];
				num_of_people = 0;
				
				StringTokenizer st;
				stairs = new int[4]; Arrays.fill(stairs, -1);
				
				for(int i=0; i<N; i++){
					st = new StringTokenizer(br.readLine());
					for(int j=0; j<N; j++){
						map[i][j] = Integer.parseInt(st.nextToken());
						if(map[i][j]>1){
							if(stairs[0]==-1)stairs[0] = i;
							else stairs[2] = i;
							if(stairs[1]==-1)stairs[1] = j;
							else stairs[3] = j;
						}
						else if(map[i][j]==1){
							num_of_people++;
						}
					}
				}
				System.out.println("#"+(t+1)+" "+findMinTime());
			}
			br.close();
	}
}