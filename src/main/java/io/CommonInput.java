package io;

import java.io.InputStream;
import java.util.Scanner;

import static exception.ExceptionMessage.NOT_NUM_TYPE;

/* 기본적인 공통 입력 (숫자 입력 전처리) */
public class CommonInput {
    private final Scanner sc;

    private static final int YES = 1;
    private static final int NO = 2;

    public CommonInput(InputStream inputStream) {
        sc = new Scanner(inputStream);
    }

    public String readString() {
        // 개행 관리 및 잔여 버퍼 문제를 방지하고자 모든 입력을 nextLine()으로
        return sc.nextLine();
    }


    /* 숫자 형식을 받을 때 까지 반복 입력 */
    public int readNum() {
        while (true) {
            try {
                return Integer.parseInt(readString().trim());
            } catch (NumberFormatException e) {
                System.out.println("[숫자 형식이 아닙니다]");
            }
        }
    }


    /* 범위에 맞는 숫자 형식 입력까지 반복 수행 */
    public int readNumValidate(int start, int end) {
        while(true) {
            int input = readNum();
            if (start <= input && input <= end) { return input; }

            System.out.println("[알맞은 범위의 숫자를 입력하세요]");
        }
    }

    public boolean readYesOrNo() {
        /* 1(YES) 아니면 2(NO)로만 가져옴 */
        int answer = readNumValidate(YES, NO);

        /* 취소일 경우 false, 확인일 경우 true */
        return answer == YES;
    }
}