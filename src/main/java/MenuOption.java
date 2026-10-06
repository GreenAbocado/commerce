
/* 각 메뉴의 이름 및 수행할 로직 보관 */
public record MenuOption(String name, Runnable logic) {

    /* 메뉴시작 번호, EXIT부터 시작 */
    public static final int START_NUM_EXIT = 0;

    public static final String EXIT = "종료";
    public static final String ADMIN = "관리자 모드";
    public static final String CART_CHECK = "장바구니 확인";
    public static final String ORDER_CANCEL = "주문 취소";
}