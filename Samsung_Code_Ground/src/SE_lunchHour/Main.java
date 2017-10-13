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
 * 이 문제는 아주 단순한 시뮬레이션 문제라고 생각하고 만만하게 접근했는데, 생각보다 엄청 오래걸렸고, 코드도 스파게티 코드 수준이다. 자바 코드 작성과 관련하여 배울점이 아주 많은 문제였다.
 * 시뮬레이션 문제라고 생각되는 것들은 보통 어떻게 해야될지 구상은 빠르게 되는게 그러한 머릿속 구상이 코드로 빠르게 옮겨지지 않는 경우가 많은 것 같다.
 * 이 문제의 경우 코드를 좀 더 간결하고 명확하게 짤 수 있는 방법이 있는지 다시한번 생각해보고 다시풀자.
 * 
 * - 구상한 내용을 어떻게 빠르게 옮길 수 있을지 생각해보자
 *   : 자바 언어에 더 익숙해질것
 *   : 보다 현명한 방법이 없는지 항상 고민할 것
 *   
 * - 이클립스의 디버깅 기능을 보다 적극적으로 이용하자(디버깅에서 시간이 오래 걸린다면 디버깅 시간을 어떻게 줄일 수 있을지 심도있게 고민해보자)
 *   : 참고페이지 : https://spoqa.github.io/2012/03/05/eclipse-debugger.html
 *   : Debug - Expressions기능
 *   : Debug - Display기능 [드래그해서 실행함에 유의, 현 문맥에서 사용할 수 있는 명령어를 실행하거나 변수의 값을 조작하는 일을 수행하기에 적합한 환경을 제공]
 *   : 브레이크 포인트에 조건지정 [Breakpoint Properties: Hit count(hit count 지정 수치 직전까지 갔다가 지정한 hit count에 도달하는 식으로 동작한다), Conditional 기능의 활용]
 *     CF) 브레이크 포인트 조건 지정하면서 알게 된 점. 
 *         배열의 내용물 비교방법 2가지 : Arrays.equals(), Arrays.deepEquals() * 참고 : http://www.geeksforgeeks.org/compare-two-arrays-java/
 *         deepEquals의 경우 배열 내부에 배열이 있거나, 배열 내부 요소가 다른 객체를 가리키지만 그 내용물이 동일한 경우 재귀적으로 배열 요소들을 비교하면서 내용 동일성을 따진다. 
 * 
 * - 자바의 Collections들에 대하여 명확히 이해하자
 *   : Vector와 관련하여 Vector의 sorting시에는 다음과 같이 한다. Ex) Collections.sort(people_by_stairs.elementAt(i), new Comp());
 *     유의점 1. Collections.sort를 사용한다는 점
 *     유의점 2. Collections.sort의 2번째 인자로 Comparator를 구현한 객체를 넘겨야 한다는 점.
 *     class Comp implements Comparator<Integer>와 같이 새로운  클래스를 선언하고, compare메소드를 오버라이드 한다.
 *     import java.util.Comparator; 를 해줘야 함을 잊지말자
 *     
 *   : Array와 ArrayList간의 차이점을 명확히 알자(Array vs ArrayList)
 *     사이즈 >> 초기화시 고정 / 초기화시 사이즈를 표시하지 않음. 유동적
 *     속도 >> 초기화시 메모리에 할당되어 속도가 빠르다 / 추가시 메모리를 재할당하여 속도가 느리다
 *     변경 >> 사이즈 변경 불가 / 추가 삭제 가능
 *     다차원 >> 가능 / 불가능
 *
 */

class Comp implements Comparator<Integer>{
	@Override
	public int compare(Integer o1, Integer o2){
		return o1 > o2 ? 1 : (o1 == o2) ? 0 : -1; // 왼쪽수가 더 크면 양수, 같으면 0, 오른쪽 수가 더 크면 음수를 반환한다. 
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
		calcDistance(); // 각 지점의 사람으로부터 각 2개의 계단입구까지의 거리를 계산한다.
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
			
			longest = 0; // 특정 케이스의 최대 이동시간을 기록한다. 이유 : 추후 break 조건에서 활용하기 위함
			for(int i=0; i<chosen.length; i++){
				longest = Math.max(longest, distance[chosen[i]][i]);
			}
			
			ArrayList<Integer> stair0 = new ArrayList<Integer>();
			ArrayList<Integer> stair1 = new ArrayList<Integer>();
			
			// Iterator<Integer> it0 = stair0.iterator();
			// Iterator<Integer> it1 = stair1.iterator();

			/*
			 * 위와 같이 반복자를 밖에서 선언하고 코드를 진행시키면 다음의 에러가 난다. ConcurrentModificationException.
			 * 이 오류는 iterator를 가져왔는데, 그 iterator가 바라 보고 있는 원본의 데이터가 변경되었기 때문에 발생하는 것이다.(참고 : http://egloos.zum.com/iilii/v/5350490)
			 * 그래서 아래와 같이 for문 내부에서(for문이 돌아갈 때마다 iterator를 불러와줘야) iterator를 이용한 ArrayList 변경시 오류가 나지 않는다.
			 */
			
			Vector<Vector<Integer>> sorted_people = sortPeople(chosen);
			
			while (true) {
				
				// stair0 계단 완료 검사
				if(stair0.size()>0){
					for(Iterator<Integer> it0 = stair0.iterator(); it0.hasNext() ; )
					{
						Integer value = (Integer)it0.next();
						if(value+1+map[stairs[0]][stairs[1]]<=time) // +1은 대기시간을 반영하기 위함이다.
						{
							it0.remove();
						}
					}
				}
				
				// stair0 계단진입 검사 
				if(sorted_people.elementAt(0).size()>0){
					for(ListIterator<Integer> pe0 = sorted_people.elementAt(0).listIterator(); pe0.hasNext();){
						/*
						 * 반복자를 이용해서 Vector의 요소를 변경시키고 싶다면 ListIterator를 사용해야 한다. 
						 * 반복자가 특정 요소를 가리킬 시, ListIterator의 set메소드를 이용한다면 해당 요소의 값을 바꿀 수 있다.
						 * 동적인 사이즈를 가지는 Collections를 조작할 때 유용하다.
						 * import java.util.ListIterator;를 해줘야 함을 잊지말자. 그리고 Iterator != ListIterator이다(비슷하지만, 차이를 유념하자)
						 */
						Integer value = (Integer)pe0.next();
					
						if(time>=value+1 && stair0.size()<3){ // 만약 들어갈 시간이 되었고, 큐에 빈 자리가 있다면
							stair0.add(value); // 해당 원소를 넣어주고
							pe0.remove(); // sorted_people 바구니에서는 계단에 넣어준 사람을 제한다
						}
						else if(value+1<=time){ // 만약 들어갈 시간이 되었는데도 못들어갔다면
							pe0.set(value+1); // 해당 원소가 가지는 값(기본 도착시간)에 기다린 시간 만큼의(여기서는 1번 기다릴 때마다 1분) delaying을 반영한다.
						}
					}
				}
				
				// stair1 계단 완료 검사
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
				
				// stair1 계단 진입 검사
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
				// 정확히 같은 역할을 하는데 2번 이상 반복된 코드는 함수를 이용해서 줄여주자. - 오늘은 2개밖에 없어서 그냥 진행했지만, 다음부터는 유념하자.
				
				// 탈출조건
				if(time > longest+1 && stair0.size()==0 && stair1.size()==0) break; // 조건 수정 필요
				
				// 스톱워치 증가
				time++;
			}
			return time;
		}
		
		int ret = 0; // 임시적인 역할을 하는 변수의 선언 위치에 유념하자. 재귀함수 사용시 함수 내부코드의 순서가 매우 중요하다. 
		
		// 모든 경우에 대한 완전탐색을 실시한다. 각 자리의 인원이 2개의 계단 각각을 선택하는 경우이다. 따라서 2^Number_of_people 경우의 가지수를 가지게 된다. 
		chosen[depth] = 0;
		ret = chooseStairs(chosen, depth+1, min_time); min_time = Math.min(min_time, ret);
		chosen[depth] = 1;
		ret = chooseStairs(chosen, depth+1, min_time); min_time = Math.min(min_time, ret);
		
		return min_time; // 재귀를 타고 들어갈 때마다 가지고 가야하고, 동시에 최종적으로 반환해야 하는 값을 어떻게 넘겼는지 유념해서 볼 것.
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
    // 응용프로그램이나 특별히 복잡한 프로그램을 작성할 때는 try-catch문을 사용해야겠으나, 알고리즘 문제풀이에서의 IOException 정도는 일단 이렇게 한줄로 작성하자.
			BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\350U2A\\Desktop\\깃허브\\code_ground\\Samsung_Code_Ground\\src\\SE_lunchHour\\sample_input.txt"));
			int T = Integer.parseInt(br.readLine());
			
			for(int t=0; t<T; t++){
				
				// 전역변수의 초기화 위치에 대해서는 항상 유의하자
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