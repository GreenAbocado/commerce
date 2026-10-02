package product;

import lombok.Getter;

import java.util.Arrays;

import static exception.ExceptionMessage.NONE_CATEGORY_INPUT;

/* 카테고리 도메인 모델 (Product에 포함) */
/* 런타임 중 CRUD 요구사항 생길 경우 일반 클래스로 전환 */
@Getter
public enum Category {
    ELECTRONIC("전자제품", 0), CLOTH("의류",1), FOOD("음식",2);

    private final String name;
    private final int id;
    /* 메뉴에서 출력하는 숫자가 카테고리 자체의 순서가 아니라 임의로
       정한 값이라면 수정 필요 */

    Category(String name, int id) {
        this.name = name;
        this.id = id;
    }

    /* id를 기반으로 Category 반환 */
    public static Category by (int id) {
        return Arrays.stream(Category.values())
                    .filter((category)-> category.getId() == id)
                    .findFirst().orElseThrow(() -> new IllegalArgumentException(NONE_CATEGORY_INPUT));
    }

    // 이미 name은 값이 확정된 불변 필드이므로 유효성 검증 불필요
}