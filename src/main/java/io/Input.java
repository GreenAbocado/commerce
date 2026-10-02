package io;

import product.Category;

import java.io.InputStream;
import java.util.Scanner;

import static exception.ExceptionMessage.NOT_NUM_TYPE;
import static io.MenuOption.EXIT;

/* 입력, 타입 기본 유효성 검증 및 변환 수행 */
public class Input {
    private final Scanner sc;

    public Input(InputStream inputStream) {
        sc = new Scanner(inputStream);
    }

    private String readString() {
        // 개행 관리 및 예외 시 잔여 버퍼 문제를 방지하고자 모든 입력을 nextLine()
        return sc.nextLine();
    }

    public int readNum() {
        try {
            return Integer.parseInt(readString());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(NOT_NUM_TYPE);
        }
    }

    public Category readMenuNum() {
        int num = readNum();

        if (num == EXIT) {
            return null;
        }

        // 관리자 확인

        return MenuOption.getCategoryBy(num);   // 카테고리 번호가 아닌 입력의 경우 예외
    }
}