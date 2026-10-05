package product;

import lombok.Getter;

/* 카테고리 도메인 모델 */
@Getter
public enum Category {
    ELECTRONIC("전자제품"), CLOTH("의류"), FOOD("음식");

    /* 매번 불필요한 배열 생성 막기 위한 상수화 */
    public static final Category[] categories = Category.values();
    private final String name;

    Category(String name) {
        this.name = name;
    }
}