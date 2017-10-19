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
        // ��, ��, ��, ��
        int[] dx = {-1,0,1,0};
        int[] dy = {0,1,0,-1};
        
        while (true) {
            if (a[x][y] == 0) {
                a[x][y] = 2;
            }
            
            if (a[x+1][y] != 0 && a[x-1][y] != 0 && a[x][y-1] != 0 && a[x][y+1] != 0) { // ������ ��� ���̰ų�, �̹� û�Ҹ� �� ������ ��
                if (a[x-dx[dir]][y-dy[dir]] == 1) { // ���� �� ������ ���̶��
                    break; // while���� �����Ѵ�.
                } else { // �װ� �ƴ϶�� 1ĭ ����
                    x -= dx[dir];
                    y -= dy[dir];
                }
            } else { // ������ ��� ���̰ų�, �̹� û�Ҹ� �� ��찡 �ƴ϶��
                dir = (dir + 3) % 4; // �������� ȸ��
                if (a[x+dx[dir]][y+dy[dir]] == 0) { // ȸ���� �� ���� �������� ������ �ٶ���� ��, û�Ҹ� ���� �ʾҴٸ�
                    x += dx[dir]; // �ش� ��ġ�� �̵�
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