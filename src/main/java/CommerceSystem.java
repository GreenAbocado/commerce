import admin.AdminController;
import io.*;
import order.*;
import product.*;
import java.util.*;

import static exception.GlobalExceptionHandler.handleException;
import static product.Category.categories;

/* 상품 조회 로직 분기 */
public class CommerceSystem {
    /* 핸들러 매퍼 */
    private final Map<Integer, MenuOption> menuSelect;

    /* 각 컨트롤러 */
    private final ProductController productController;
    private final CartController cartController;
    private final OrderController orderController;
    private final AdminController adminController;

    /* 실행 여부 관리 필드 */
    private boolean isRun;

    private final CommonInput input;

    public CommerceSystem(ProductController pc, CartController cc, OrderController oc, AdminController as, CommonInput input) {
        productController = pc;
        cartController = cc;
        orderController = oc;
        adminController = as;
        menuSelect = new HashMap<>();
        this.input = input;
        isRun = true;
        initMenuSelect();
    }


    public void start() {
        while (isRun) {
            handleException(() -> {
                printMenu();
                getMenu().logic().run();
            });
        }
        printExit();
    }

    /* 입력을 받아 매핑되는 메뉴 선택을 반환 */
    private MenuOption getMenu() {
        while (true) {
            int num = input.readNum();

            if (menuSelect.containsKey(num)) {
                return menuSelect.get(num);
            }
            printRetry();
        }
    }

    private void stopRun() {
        this.isRun = false;
    }

    /* 핸들러 매핑, 각 컨트롤러로 요청 위임 */
    /* 카테고리 개수에 따라 동적으로 메뉴 번호가 바뀌므로 enum이 아닌 객체로 동적으로 삽입 */
    /* MenuOption에 카테고리 길이 포함하여 enum 가능 - 수정 필요 */
    private void initMenuSelect() {
        int menuNum = MenuOption.START_NUM_EXIT;

        menuSelect.put(menuNum++, new MenuOption(MenuOption.EXIT, this::stopRun));

        /* 카테고리별로 매개변수만 다르게 하여 보관 */
        /* DTO 반환시 바로 CartController로 위임 (null: 상품 선택 안 함) */
        for (Category category : categories) {
            menuSelect.put(menuNum++, new MenuOption(category.getName(), () -> {
                productController.getProduct(category).ifPresent(cartController::addCartItem);
            }));
        }

        menuSelect.put(menuNum++, new MenuOption(MenuOption.CART_CHECK, orderController::order));
        menuSelect.put(menuNum++, new MenuOption(MenuOption.ORDER_CANCEL, cartController::clear));
        menuSelect.put(menuNum, new MenuOption(MenuOption.ADMIN, () -> {
            if (adminController.authenticate()) {productController.manageProducts(); }
        }));
    }



    /* 출력 관련 */

    private void printRetry() {
        System.out.println("다시 입력하세요");
    }

    private void printExit() {
        System.out.println("커머스 플랫폼을 종료합니다.");
    }

    /* MenuOption 정의를 기반으로 메인 메뉴 출력문 생성 후 출력 */
    private void printMenu() {
        StringBuilder sb = new StringBuilder();
        sb.append("[실시간 커머스 플랫폼 메인]\n");

        /* 카테고리만 포함 */
        /* 스트림 너무 길어짐 -> 불필요하게 category 길이 다시 확인 -> enum으로 다시 수정 필요 */
        menuSelect.entrySet().stream().filter((entry)
                        -> MenuOption.START_NUM_EXIT < entry.getKey() && entry.getKey() <= categories.length)
                .forEach((entry) ->
                        sb.append(String.format("%d. %s\n", entry.getKey(), entry.getValue().name())));

        /* 종료 포함 */
        sb.append(String.format(
                "%d. %s\n", MenuOption.START_NUM_EXIT, menuSelect.get(MenuOption.START_NUM_EXIT).name()));

        /* 장바구니 여부 확인 후 포함 */
        /* 메뉴 번호 정의 달라지면 변경 위험 -> 수정 필요 */
        if (!cartController.getCartItems().isEmpty()) {
            sb.append("\n[주문 관리]\n");
            sb.append(String.format("%d. %s\n", menuSelect.size()-3, MenuOption.CART_CHECK));
            sb.append(String.format("%d. %s\n", menuSelect.size()-2, MenuOption.ORDER_CANCEL));
        }
        /* 관리자 메뉴 포함 */
        sb.append(String.format("\n%d. %s\n", menuSelect.size()-1, MenuOption.ADMIN));
        System.out.print(sb);
    }
}
