package SCPC_1st_round_2;

import java.io.FileInputStream;
import java.util.Scanner;

class StartPoint{
	int x;
	int y;
	public StartPoint(int x, int y){
		this.x = x;
		this.y = y;
	}
}

class Mirror {
	static int Answer;
	static int[] case_1 = {0, 2, 1, 4, 3}; // 1-위쪽으로 탐색, 2-오른쪽으로 탐색, 3-아래쪽으로 탐색, 4-왼쪽으로 탐색
	static int[] case_2 = {0, 4, 3, 2, 1};
	
	public static int directionSwitching(int direction, int[][] matrix, int sp_x, int sp_y){
		switch(matrix[sp_x][sp_y]){
		case 1:
		    direction = case_1[direction];
			break;
		case 2:
		    direction = case_2[direction];
			break;
		case 0:
			break;
		}
		return direction;
	}
	
	public static int findAns(int[][] matrix, int N){
		
		StartPoint sp = new StartPoint(0,-1); // 시작 지점은 (0,-1)이라 가정
		int[][] check = new int[N][N];
		int count = 0;
		int direction = 2;
		
		label: // while문 breaking을 위함
		while(sp.x<N && sp.y<N){
			switch(direction){
			case 1:
				for(int i = sp.x-1; i>=-1; i--){ // starting point는 포함시키지 않음
					if(i==-1) break label;
					
					if(matrix[i][sp.y]!=0){
						
						sp.x = i;
						check[sp.x][sp.y] = 1; // mirror checking
						direction = directionSwitching(direction, matrix, sp.x, sp.y);
						break;
					}
				}
				break;
				
			case 2:
				for(int i = sp.y+1; i<N+1; i++){
					if(i==N) break label;
					
					if(matrix[sp.x][i]!=0){

						sp.y = i;
						check[sp.x][sp.y] = 1;			
						direction = directionSwitching(direction, matrix, sp.x, sp.y);
						break;
					}
				}
				break;
				
			case 3:
				for(int i = sp.x+1; i<N+1; i++){
					if(i==N) break label;
					
					if(matrix[i][sp.y]!=0){

						sp.x = i;
						check[sp.x][sp.y] = 1;
						direction = directionSwitching(direction, matrix, sp.x, sp.y);
						break;
					}
				}
				break;
				
			default:
				for(int i = sp.y-1; i>=-1; i--){
					if(i==-1) break label;
					
					if(matrix[sp.x][i]!=0){

						sp.y = i;
						check[sp.x][sp.y] = 1;
						direction = directionSwitching(direction, matrix, sp.x, sp.y);
						break;
					}
				}
			}
		}
		
		for(int i=0; i<N; i++){
			for(int j=0; j<N; j++){
				count += check[i][j];
			}
		}
		
		return count;
	}
	
	public static void main(String args[]) throws Exception	{

//		Scanner sc = new Scanner(System.in);
		Scanner sc = new Scanner(new FileInputStream("D:\\자바학습(neon)\\Samsung_Code_Ground\\src\\SCPC_1st_round_2\\sample_input.txt"));
		
		int T = Integer.parseInt(sc.nextLine());
		String line = new String();
		for(int test_case = 0; test_case < T; test_case++) {

			line = sc.nextLine();
			int N = Integer.parseInt(line);
			
			int[][] matrix = new int[N][N];
			for(int i=0; i<N; i++){
				line = sc.nextLine();
				for(int j=0; j<N; j++){
					matrix[i][j] = (int)line.charAt(j)-48;
				}
			}
			
			Answer = findAns(matrix, N);

			System.out.println("Case #"+(test_case+1));
			System.out.println(Answer);
		}
		sc.close();
	}
}