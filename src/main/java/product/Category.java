package product;

import lombok.Getter;

/* 카테고리 도메인 모델 (Product에 포함) */
/* 런타임 중 CRUD 요구사항 생길 경우 일반 클래스로 전환 */
public enum Category {
    ELECTRONIC("전자제품"), CLOTH("의류"), FOOD("음식");

    @Getter
    private final String name;

    Category(String name) {
        this.name = name;
    }

    // 이미 name은 값이 확정된 불변 필드이므로 유효성 검증 불필요
}