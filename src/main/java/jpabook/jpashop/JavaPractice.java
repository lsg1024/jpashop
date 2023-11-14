package jpabook.jpashop;

import java.util.*;

public class JavaPractice {

    public static void main(String[] args) {

        long beforeTime = System.currentTimeMillis();
        long startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int count = 0;
        for (int i=0; i<=n; i++) {
            for (int j=0; j<60; j++) {
                for (int k=0; k<60; k++) {
                    if (check(i, j, k)) count++;
                }
            }
        }
        System.out.println(count);

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - beforeTime;
        System.out.println("프로그램 실행 시간: " + executionTime + " 밀리초");

        long endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long usedMemory = endMemory - startMemory;
        System.out.println("프로그램이 사용한 메모리: " + formatMemory(usedMemory));

    }

    public static boolean check(int i, int j, int k) {
        if (i%1000 == 3 || j % 10 == 3 || j / 10 == 3 || k / 10 == 3 || k % 10 == 3)
            return true;
        return false;
    }


    // 메모리 크기를 포맷팅하는 메서드
    private static String formatMemory(long bytes) {
        return String.format("%.2f MB", bytes / (1024.0 * 1024.0));
    }
}
