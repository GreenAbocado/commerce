package customer;

import lombok.RequiredArgsConstructor;

/* 등급 도메인 모델  */
@RequiredArgsConstructor
public enum Grade {
    BRONZE(0, 0), SILVER(100_000, 5),
            GOLD(1_000_000, 10), PLATINUM(10_000_000, 15);

    /* 매번 불필요한 배열 생성 막기 위한 상수화 */
    public static final Grade[] grades = Grade.values();

    /* 해당 등급을 위해 필요한 최소 누적 가격 */
    private final int requiredPriceForGrade;

    /* 할인율의 경우, 실수로 저장하면 이후 근사값 곱셈 연산으로 인해 오차가 발생
       정수로 변환하여 마지막에 100으로 나눔 (1원 미만의 나머지는 제거) */
    private final int discountPercent;


    /* 등급 위에서부터 순회하며 최소 누적 가격 비교 */
    public Grade upGrade(int totalUsedPrice) {
        if (totalUsedPrice < 0) { throw new IllegalArgumentException("등급 상승을 위한 누적 금액 잘못 입력"); }

        for (int i = grades.length-1; i >= 0; i--) {
            if (totalUsedPrice >= grades[i].requiredPriceForGrade) { return grades[i]; }
        }
        return this;
    }
}