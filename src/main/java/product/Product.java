package product;

import lombok.Getter;

/* 상품 도메인 객체 <필드 규칙 및 상태 변경 관리 (유효성, 필드 변경 로직 등)> */
@Getter
public class Product {

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

    @Override
    public String toString() {
        return String.format(" %s | %,d원 | %s | 재고: %d개", name, price, description, stock);
    }


    /* 상태 변경 로직
       - price/description 선택 변경
       - 재고 증감 로직 */

    public void update(Integer price, String description) { // null 여부로 변경 필드 선택하기 위해 래퍼 적용
        if (price != null) { validatePrice(price); }
        if (description != null) { validateDescription(description); }
        // 변경 필드에 대한 매개 변수가 검증에 실패할 경우, 모든 필드 변경 X

        if (price != null) { this.price = price; }
        if (description != null) { this.description = description; }
    }

    public void increaseStock(int amount) {    // 재고 증가 (1 이상 가능)
        validateAmount(amount);
        this.stock += amount;
    }

    public void decreaseStock(int amount) {    // 재고 감소 (1 이상 가능)
        validateAmount(amount);

        if (this.stock < amount) {
            throw new IllegalStateException("재고가 부족합니다.");
        }
        this.stock -= amount;
    }


    /* 필드 변경 검증 로직
        이름 : null, 공백, 20자 초과 입력
        설명 : null, 100자 초과 입력
        가격, 재고 : 음수 입력
        재고 증감 로직 : 0개 이하 인자
     */

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("유효하지 않은 상품명입니다.");
        }
        if (name.length() > 20) {
            throw new IllegalArgumentException("상품명은 20자를 초과할 수 없습니다.");
        }
    }

    private void validatePrice(int num) {
        if (num < 0) {
            throw new IllegalArgumentException("가격에 음수 입력은 유효하지 않습니다.");
        }
    }

    private void validateStock(int num) {
        if (num < 0) {
            throw new IllegalArgumentException("재고에 음수 입력은 유효하지 않습니다.");
        }
    }

    private void validateDescription(String description) {
        if (description == null) {  // 설명 공백 가능
            throw new IllegalArgumentException("유효하지 않은 설명입니다.");
        }

        if (description.length() > 100) {
            throw new IllegalArgumentException("설명은 100자를 초과할 수 없습니다.");
        }
    }

    private void validateAmount(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("재고 증감량은 1개 이상 입력해야 합니다.");
        }
    }
}