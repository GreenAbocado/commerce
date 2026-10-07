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

    /* 장바구니 추가 */
    public void addCartItem(ProductResponseDTO dto) {
        CartAndOrderOutput.printCartAddQuestion();

        if (input.readYesOrNo()) {
            cartService.addItem(dto.id());
            CartAndOrderOutput.printAdded(dto.name());
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
