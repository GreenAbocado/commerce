package exception;

/* 전역 예외 메시지 상수화 */
public class ExceptionMessage {

    /* Product : 입력 값 관련 */
    public static final String NONE_NAME_INPUT =  "상품명이 존재하지 않습니다.\n";
    public static final String INVALID_NAME_INPUT =  "상품명이 올바르지 않습니다.\n";
    public static final String NONE_CATEGORY_INPUT = "카테고리가 존재하지 않습니다.\n";
    public static final String INVALID_PRICE_INPUT = "유효하지 않은 가격입니다.\n";
    public static final String MINUS_STOCK_INPUT = "재고에 음수 입력은 유효하지 않습니다.\n";
    public static final String NONE_DESCRIPTION_INPUT = "설명이 존재하지 않습니다.\n";
    public static final String OVER_DESCRIPTION_INPUT = "설명은 %d자를 초과할 수 없습니다.\n";
    public static final String INVALID_AMOUNT_INPUT = "재고 증감량 입력이 1개 이상이 아닙니다.\n";

    /* Product : 필드 상태 오류 관련 */
    public static final String OVER_STOCK_STATE = "현재 최대 재고 개수를 초과합니다.\n";
    public static final String UNDER_STOCK_STATE = "현재 재고가 부족합니다.\n";

    /* Category : Product 비즈니스 로직 관련 */
    public static final String PRODUCT_ALREADY_EXIST = "상품이 이미 존재합니다.\n";
    public static final String PRODUCT_NOT_EXIST = "해당 이름을 갖는 상품이 없습니다.\n";

    /* I/O 관련 */
    public static final String NUM_NOT_EXIST = "존재하지 않는 번호입니다.\n";
    public static final String NOT_NUM_TYPE = "숫자 형식이 아닙니다.\n";

    private ExceptionMessage() {}   // 외부 객체 생성 방지
}
