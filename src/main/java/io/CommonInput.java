package io;

import java.io.InputStream;
import java.util.Scanner;

import static exception.ExceptionMessage.NOT_NUM_TYPE;

/* 기본적인 공통 입력 (숫자 입력 전처리) */
public class CommonInput {
    private final Scanner sc;

    public CommonInput(InputStream inputStream) {
        sc = new Scanner(inputStream);
    }

    public String readString() {
        // 개행 관리 및 잔여 버퍼 문제를 방지하고자 모든 입력을 nextLine()으로
        return sc.nextLine();
    }

    /* 숫자의 경우 의미없는 앞뒤 공백 제거 가능 */
    public int readNum() {
        try {
            return Integer.parseInt(readString().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(NOT_NUM_TYPE);
        }
    }
}