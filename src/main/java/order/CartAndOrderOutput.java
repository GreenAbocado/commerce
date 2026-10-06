package order;

import order.dto.CartResponseDTO;
import order.dto.OrderResultDTO;

import java.util.List;

/* 주문 관련 출력 포맷팅 및 출력 */
public class CartAndOrderOutput {

    /* 출력 */
    private static void print(String s) {
        System.out.println(s);
    }


    public static void printCartItems(List<CartResponseDTO> list) {
        print(itemToStr(list));
    }

    public static void printCartAddQuestion() {
        print("""
                
                위 상품을 장바구니에 추가하시겠습니까?
                1. 확인           2. 취소
                """);
    }


    public static void printRetryAnswer() {
        print("정확한 번호를 눌러주세요");
    }


    public static void printAdded(String name) {
        print(String.format("%s가 장바구니에 추가되었습니다.\n", name));
    }

    public static void printOrderConfirmation(List<CartResponseDTO> list, int totalPrice) {
        print(String.format("""
                아래와 같이 주문 하시겠습니까?
                
                [ 장바구니 내역 ]
                %s
                [ 총 주문 금액 ]
                %,d원
                
                1. 주문 확정        2. 메인으로 돌아가기
                """, itemToStr(list), totalPrice));
    }

    public static void printSuccessOrder(int totalPrice, List<OrderResultDTO> list) {
        print(String.format("""
                주문이 완료되었습니다! 총 금액: %,d원
                %s
                """, totalPrice, resultToStr(list)
        ));
    }

    public static void printClearCart() {
        print("장바구니가 비워졌습니다.");
    }

    // 장바구니 내역 출력문 생성
    private static String itemToStr(List<CartResponseDTO> list) {
        StringBuilder sb = new StringBuilder();

        for (CartResponseDTO dto : list) {
            sb.append(String.format("%s | %,d원 | 수량: %d개\n",
                    dto.name(), dto.price(), dto.quantity()));
        }
        return sb.toString();
    }

    private static String resultToStr(List<OrderResultDTO> list) {
        StringBuilder sb = new StringBuilder();

        for (OrderResultDTO dto : list) {
            sb.append(String.format("%s 재고가 %d개 -> %d개로 업데이트 되었습니다.\n",
                    dto.name(), dto.beforeStock(), dto.afterStock()));
        }
        return sb.toString();
    }
}
