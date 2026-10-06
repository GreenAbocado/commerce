package order;

import io.CommonInput;
import lombok.RequiredArgsConstructor;
import order.dto.CartResponseDTO;
import order.dto.OrderResultDTO;

import java.util.List;

/* 주문 관련 요청 입출력, 서비스에 위임 */
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;
    private final CommonInput input;

    private static final int YES = 1;
    private static final int NO = 2;

    /* 주문 확인 출력 후 서비스에 요청 위임 */
    public void order() {
        List<CartResponseDTO> cartList = cartService.getAll();
        int totalPrice = cartService.getTotalPrice();
        CartAndOrderOutput.printOrderConfirmation(cartList, totalPrice);

        /* 확인 입력에 따라 주문 수행 */
        if (getAnswer()) {
            List<OrderResultDTO> resultList = orderService.order();
            CartAndOrderOutput.printSuccessOrder(totalPrice, resultList);
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
}
