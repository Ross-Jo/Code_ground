package baekjoon_robot;

import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] a = new int[n][m];
        
        int x = sc.nextInt();
        int y = sc.nextInt();
        int dir = sc.nextInt();
        
        for (int i=0; i<n; i++) {
            for (int j=0; j<m; j++) {
                a[i][j] = sc.nextInt();
            }
        }
        
        int cnt = 0;
        // 상, 우, 하, 좌
        int[] dx = {-1,0,1,0};
        int[] dy = {0,1,0,-1};
        
        while (true) {
            if (a[x][y] == 0) {
                a[x][y] = 2;
            }
            
            if (a[x+1][y] != 0 && a[x-1][y] != 0 && a[x][y-1] != 0 && a[x][y+1] != 0) { // 주위가 모두 벽이거나, 이미 청소를 한 구역일 때
                if (a[x-dx[dir]][y-dy[dir]] == 1) { // 만약 뒷 방향이 벽이라면
                    break; // while문을 종료한다.
                } else { // 그게 아니라면 1칸 후진
                    x -= dx[dir];
                    y -= dy[dir];
                }
            } else { // 주위가 모두 벽이거나, 이미 청소를 한 경우가 아니라면
                dir = (dir + 3) % 4; // 왼쪽으로 회전
                if (a[x+dx[dir]][y+dy[dir]] == 0) { // 회전을 한 방향 기준으로 왼쪽을 바라봤을 때, 청소를 하지 않았다면
                    x += dx[dir]; // 해당 위치로 이동
                    y += dy[dir];
                }
            }
        }
        
        for (int i=0; i<n; i++) {
            for (int j=0; j<m; j++) {
                if (a[i][j] == 2) {
                    cnt += 1;
                }
            }
        }
        
        System.out.println(cnt);
        sc.close();
    }
}