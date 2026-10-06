package io;

import order.dto.CartResponseDTO;
import order.dto.OrderResultDTO;

import java.util.List;

/* 주문 관련 출력 포맷팅 및 출력 */
public class OrderOutput {

    /* 출력 */
    private static void print(String s) {
        System.out.print(s);
    }

    public static void printQuestion() {
        print("""
                
                위 상품을 장바구니에 추가하시겠습니까?
                1. 확인           2. 취소
                """);
    }

    public static void printAdded(String name) {
        print(String.format("%s가 장바구니에 추가되었습니다.\n", name));
    }

    public static void printOrderQuestion(List<CartResponseDTO> list, int totalPrice) {
        print(String.format("""
                아래와 같이 주문 하시겠습니까?
                
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
