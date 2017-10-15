package SE_maze_subpractice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
	public static int num;
	public static int[][] map;
	public static int[] start;
	public static int[] end;
	public static int[][] visited;
	public static int[][] dir = {{0,-1},{-1,0},{0,1},{1,0}};
	public static int ret;
	
	public static int findPath(){
		ret = 0;
		visited = new int[100][100];
		DFS(start[0],start[1]);
		return ret;
	}
	
	public static void DFS(int x, int y){
		if(map[x][y]!=1 && visited[x][y]!=1) {
			visited[x][y]=1;
			if(x==end[0] && y==end[1]) ret = 1;
		}
		
		for(int i=0; i<dir.length; i++){
			if(0<=x+dir[i][0] && x+dir[i][0] <100 &&
			   0<=y+dir[i][1] && y+dir[i][1] <100 &&	
			   map[x+dir[i][0]][y+dir[i][1]]!=1 && visited[x+dir[i][0]][y+dir[i][1]]==0){
			   DFS(x+dir[i][0], y+dir[i][1]);
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(".\\src\\SE_maze_subpractice\\input.txt"));
		
		for(int t=0; t<10; t++){
			
			num = Integer.parseInt(br.readLine());
			map = new int[100][100];
			
			char[] ch;
			for(int i=0; i<100; i++){
				ch = br.readLine().toCharArray(); // 공백이 없이 1자릿수의 숫자들이 붙어있을때는 이와 같이 char 배열로 바꿔준 다음, 
				for(int j=0; j<100; j++){
					map[i][j] = Character.getNumericValue(ch[j]); // 이와 같이 해당 char가 나타내는 숫자를 integer로 바꿔주는 방법도 있다. 
					if(map[i][j]==2) {
						start = new int[2];
						start[0] = i;
						start[1] = j;
					}
					else if(map[i][j]==3){
						end = new int[2];
						end[0] = i;
						end[1] = j;
					}
				}
			}
			System.out.println("#"+(t+1)+" "+findPath());
		}
		br.close();
	}

}
