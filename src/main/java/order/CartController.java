package order;

import io.CommonInput;
import lombok.RequiredArgsConstructor;
import order.dto.CartResponseDTO;
import product.dto.ProductResponseDTO;

import java.util.List;

/* 장바구니 관련 요청 입출력, 서비스에 위임 */
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CommonInput input;

    private static final int YES = 1;
    private static final int NO = 2;

    /* 장바구니 추가 */
    public void addCartItem(ProductResponseDTO dto) {
        CartAndOrderOutput.printCartAddQuestion();

        if (getAnswer()) {
            cartService.addItem(dto.id());
            CartAndOrderOutput.printAdded(dto.name());
        }
    }

    /* 입력 -> boolean 전환 */
    private boolean getAnswer() {
        while(true) {
            int answer = input.readNum();

            /* 취소일 경우 false, 확인일 경우 true */
            if (answer == NO) { return false;}
            if (answer == YES) { return true; }

            /* 재입력 요청 출력 후 반복 */
            CartAndOrderOutput.printRetryAnswer();
        }
    }

    /* 장바구니 목록 반환 */
    public List<CartResponseDTO> getCartItems() {
        return cartService.getAll();
    }

    public void clear() {
        cartService.clear();
        CartAndOrderOutput.printClearCart();
    }
}
