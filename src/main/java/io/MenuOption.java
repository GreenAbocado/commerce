package io;

import product.Category;
import java.util.*;

import static exception.ExceptionMessage.NONE_CATEGORY_INPUT;

/* UI 메뉴 번호 매핑 및 규칙 정의 (번호 변경할 경우 이 곳에서 관리) - Input, Output, CommerceSystem 모두 사용 */
/* 메뉴 번호는 UI에 속하므로 Category 도메인이 아닌 I/O쪽에 분리 */
public class MenuOption {
    public static final int EXIT = 0;
    public static final int ADMIN = 6;
    private static final Map<Integer, Category> CATEGORIES = getMenuCategoryRule();

    public static Category getCategoryBy(int num) {
        if (!CATEGORIES.containsKey(num)) {
            throw new IllegalArgumentException(NONE_CATEGORY_INPUT);
        }
        return CATEGORIES.get(num);
    }

    /* 원본 데이터 오염 방지를 위해 새로운 맵 생성 */
    public static Map<Integer, Category> getCategoriesMenuNumMapping() {
        return new LinkedHashMap<>(CATEGORIES);
    }

    /* 메뉴 번호와 카테고리 매핑 방식 정의 (클래스 로딩시 필드 초기화를 위해 한 번만 실행) */
    /* 현재 방식 : 1부터 이넘 정의된 순서대로, ADMIN 번호 띄어넘기 */
    private static Map<Integer, Category> getMenuCategoryRule() {
        Map<Integer, Category> map = new LinkedHashMap<>();
        int num = 1;

        for (Category category : Category.values()) {
            if (num == ADMIN) num++;
            map.put(num++, category);
        }
        return map;
    }

    private MenuOption() {}
}
