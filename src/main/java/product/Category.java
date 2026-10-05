package product;

import lombok.Getter;

/* 카테고리 도메인 모델 */
@Getter
public enum Category {
    ELECTRONIC("전자제품"), CLOTH("의류"), FOOD("음식");

    private final String name;

    Category(String name) {
        this.name = name;
    }
}