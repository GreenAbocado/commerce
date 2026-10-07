package product.io;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.Arrays;

/* 상품 관리 번호 매핑 */
@Getter
@RequiredArgsConstructor
public enum ProductManageMenu {

    ADD_PRODUCT(1, "상품 추가"), UPDATE_PRODUCT(2, "상품 수정"),
    DELETE_PRODUCT(3, "상품 삭제"), FIND_PRODUCTS(4, "전체 상품 현황"),
    BACK_MAIN(0, "메인으로 돌아가기");

    private final int menuNum;
    private final String menuName;

    /* 정적 팩토리 및 출력 시 불필요한 스트림 생성을 막기 위한 상수화 */
    public static final ProductManageMenu[] MENU_ARR = ProductManageMenu.values();


    public static ProductManageMenu by(int num) {
        return Arrays.stream(MENU_ARR).filter((menu)-> menu.menuNum == num)
                .findFirst().orElseThrow(()-> new IllegalArgumentException("잘못된 입력"));
    }
}