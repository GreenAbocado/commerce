package product;

import lombok.*;
import java.util.regex.Pattern;
import static exception.ExceptionMessage.*;

/* 상품 도메인 객체 <필드 규칙 및 상태 변경 관리 (유효성, 필드 변경 로직 등)> */
@Getter
public class Product {

    // 문자열 필드 검증 규칙 (정규표현식 간소하게 작성)
    private static final Pattern NAME_PATTERN = Pattern.compile("[0-9A-Za-z가-힣\\s]{1,20}");
    private static final int MAX_DESCRIPTION_LENGTH = 100;

    /* 이름으로 구분, 이후 필요시 id 추가 */
    private Long id;
    private final String name;
    private final Category category;
    private int price;
    private String description;
    private int stock;

    @Builder    /* 빌더로 클라이언트 가독성 향상, price의 경우 빌더 누락으로 인해 값이 없는 상태를 구분하기 위해 래퍼 사용 */
    private Product(String name, Category category, Integer price, String description, int stock) {
        validateAtConstruct(name, category, price, description, stock);
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.stock = stock;
    }

    /* 상태 변경 로직
       - id 초기화
       - price/description/stock 선택 변경
       - 재고 감소 로직 */

    public void initId(Long id) {
        if (this.id != null) { throw new IllegalStateException("id 이미 존재"); }

        if (id == null) { throw new IllegalArgumentException("유효하지 않은 id"); }

        this.id = id;
    }

    /* 부분 변경(PATCH) (null : 변경하지 않음) */
    public void updateInfo (Integer price, String description, Integer stock) {
        /* 변경 필드 비즈니스 규칙 모두 검증 후 변경 */
        if (price != null) { validatePrice(price);}
        if (description != null) { validateDescription(description); }
        if (stock != null) { validateStock(stock); }

        if (price != null) {this.price = price;}
        if (description != null) {this.description = description;}
        if (stock != null) {this.stock = stock;}
    }

    public void decreaseStock(int amount) {    // 검증 : amount 1 미만, 재고 부족
        if (amount <= 0) {
            throw new IllegalArgumentException(INVALID_AMOUNT_INPUT);
        }

        if (this.stock < amount) {
            throw new IllegalStateException(UNDER_STOCK_STATE);
        }
        this.stock -= amount;
    }


    /*  입력 유효성 및 비즈니스 규칙 검증
        price : null, 음수 입력
        stock : 음수 입력
        description : null, 100자 초과 입력

        name : 각 문자가 [0-9A-Za-z가-힣,공백]으로 1~20자가 아닌 입력
        category : null 입력 */

    private static void validatePrice(Integer price) {
        if (price == null || price < 0) {
            throw new IllegalArgumentException(INVALID_PRICE_INPUT);
        }
    }

    private static void validateStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException(MINUS_STOCK_INPUT);
        }
    }

    private static void validateDescription(String description) {
        if (description == null) {  // 설명 공백 가능
            throw new IllegalArgumentException(NONE_DESCRIPTION_INPUT);
        }
        if (description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException(String.format(OVER_DESCRIPTION_INPUT, MAX_DESCRIPTION_LENGTH));
        }
    }

    /* 불변 필드의 경우 메서드 추출 없이 바로 생성자에서만 검증 */
    private static void validateAtConstruct(String name, Category category, Integer price, String description, int stock) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(NONE_NAME_INPUT);
        }
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException(INVALID_NAME_INPUT);
        }
        if (category == null) {
            throw new IllegalArgumentException(NONE_CATEGORY_INPUT);
        }

        validatePrice(price);
        validateDescription(description);
        validateStock(stock);
    }
}