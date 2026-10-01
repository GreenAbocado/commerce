package product;

import lombok.Getter;

import static exception.ExceptionMessage.*;

/* 상품 도메인 객체 <필드 규칙 및 상태 변경 관리 (유효성, 필드 변경 로직 등)> */
@Getter
public class Product {

    private static final int MAX_NAME_LENGTH = 20;
    private static final int MAX_DESCRIPTION_LENGTH = 100;

    /* 이름으로 구분, 이후 필요시 id 추가 */
    private final String name;
    private int price;
    private String description;
    private int stock;

    public Product(String name, int price, String description, int stock) {
        validateName(name); validateDescription(description);
        validatePrice(price); validateStock(stock);
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
    }

    /* stock 입력 X 가능 */
    public Product(String name, int price, String description) {
        this(name, price, description, 0);
    }


    /* 상태 변경 로직
       - price/description 선택 변경
       - 재고 증감 로직 */

    public void update(Integer price, String description) {
        /* null 여부로 변경 필드 결정
           null이 아닌 인자만 비즈니스 검증 후 필드 변경
           변경 필드에 대한 매개 변수가 검증에 실패할 경우, 모든 필드 변경 X */

        if (price != null) { validatePrice(price); }
        if (description != null) { validateDescription(description); }

        if (price != null) { this.price = price; }
        if (description != null) { this.description = description; }
    }

    public void increaseStock(int amount) {    // 예외 : amount 1 미만, 재고 초과
        validateAmount(amount);

        if (Integer.MAX_VALUE - amount < stock) {
            throw new IllegalStateException(OVER_STOCK_STATE);
        }
        this.stock += amount;
    }

    public void decreaseStock(int amount) {    // 예외 : amount 1 미만, 재고 부족
        validateAmount(amount);

        if (this.stock < amount) {
            throw new IllegalStateException(UNDER_STOCK_STATE);
        }
        this.stock -= amount;
    }


    /*  입력 유효성 및 비즈니스 규칙 검증
        name : null, 공백, 20자 초과 입력
        description : null, 100자 초과 입력
        price, stock : 음수 입력
        stock 증감 로직 : 0개 이하 입력 */

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(NONE_NAME_INPUT);
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(String.format(OVER_NAME_INPUT, MAX_NAME_LENGTH));
        }
    }

    private void validatePrice(int num) {
        if (num < 0) {
            throw new IllegalArgumentException(MINUS_PRICE_INPUT);
        }
    }

    private void validateStock(int num) {
        if (num < 0) {
            throw new IllegalArgumentException(MINUS_STOCK_INPUT);
        }
    }

    private void validateDescription(String description) {
        if (description == null) {  // 설명 공백 가능
            throw new IllegalArgumentException(NONE_DESCRIPTION_INPUT);
        }

        if (description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException(String.format(OVER_DESCRIPTION_INPUT, MAX_DESCRIPTION_LENGTH));
        }
    }

    private void validateAmount(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(INVALID_AMOUNT_INPUT);
        }
    }


    @Override
    public String toString() {
        return String.format(" %s | %,d원 | %s | 재고: %d개", name, price, description, stock);
    }
}