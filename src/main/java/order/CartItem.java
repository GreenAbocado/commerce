package order;

import lombok.Getter;

/* 장바구니 아이템 도메인 모델 */
@Getter
public class CartItem {

    private final Long productId;
    private int quantity;

    public CartItem(Long productId) {
        if (productId == null) { throw new IllegalArgumentException(""); }
        this.productId = productId;
        this.quantity = 1;
    }

    /* 상태 변경 로직 */

    public void increaseQuantity() {
        if (quantity == Integer.MAX_VALUE) {
            throw new IllegalStateException("카트 입력 개수 최대 초과");
        }
        quantity++;
    }
}