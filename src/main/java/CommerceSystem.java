import admin.AdminService;
import io.*;
import lombok.RequiredArgsConstructor;
import order.dto.CartResponseDTO;
import order.*;
import order.dto.*;
import product.*;
import product.dto.ProductResponseDTO;
import java.util.*;
import static exception.GlobalExceptionHandler.handleException;
import static io.ProductOutput.categoriesToStr;
import static product.Category.categories;

/* 상품 조회 로직 분기 */
@RequiredArgsConstructor
public class CommerceSystem {

    private final ProductService productService;
    private final CartService cartService;
    private final OrderService orderService;
    private final AdminService adminService;
    private boolean isRun = true;
    private final CommonInput input;

    public void start() {
        while (isRun) {
            handleException(this::selectMenu);
        }
        System.out.println("커머스 플랫폼을 종료합니다.");
    }

    private void selectMenu() {
        while (true) {
            printMenu();

            int menuNum = input.readNum();
            if (menuNum == 0) { stopRun(); return; }

            if (1 <= menuNum && menuNum <= categories.length) {
                selectProduct(categories[menuNum-1]);
            } else if (categories.length < menuNum && menuNum <= categories.length + 2) {
                orderProduct(menuNum);
            } else if (menuNum == categories.length + 3) {
                authenticateAdmin();
            } else {
                System.out.println("[다시 입력하세요]");
            }
        }
    }

    private void selectProduct(Category category) {
        List<ProductResponseDTO> list = productService.getAllByCategory(category);

        while (true) {
            ProductOutput.printProducts(category, list);

            int productNum = input.readNum();

            if (productNum == 0) { return; }
            if ((productNum < 1 || productNum > list.size())) { System.out.println("[다시 입력하세요]"); continue; }

            ProductResponseDTO product = list.get(productNum-1);
            ProductOutput.printProduct(product);
            selectAddCart(product.id(), product.name());
            return;
        }
    }

    private void selectAddCart(Long id, String name) {
        while (true) {
            OrderOutput.printQuestion();
            int num = input.readNum();

            if (num != 1 && num != 2) { System.out.println("[다시 입력하세요]"); continue; }
            if (num == 2) { return; }
            cartService.addItem(id);
            OrderOutput.printAdded(name);
            return;
        }
    }

    private void orderProduct(int menuNum) {
        if (menuNum == categories.length + 1) {
            List<CartResponseDTO> list = cartService.getAll();
            int totalPrice = cartService.getTotalPrice();
            OrderOutput.printOrderQuestion(list, totalPrice);

            while (true) {
                int num = input.readNum();
                if (num == 1) {
                    List<OrderResultDTO> resultList = orderService.order();
                    OrderOutput.printSuccessOrder(totalPrice, resultList);
                    break;
                } else if (num == 2) {
                    cartService.clear();
                    break;
                } else {
                    System.out.println("[다시 입력하세요]");
                }
            }
        }
    }

    private void authenticateAdmin() {
        for (int i = 0; i < 3; i++) {
            System.out.println("관리자 비밀번호를 입력해주세요:");
            String inputStr = input.readString();
            if (adminService.authenticate(inputStr)) {
                enterAdminMode();
                return;
            }
        }
        throw new IllegalArgumentException();
    }

    private void enterAdminMode() {
        while (true) {
            printAdminMode();
            int num = input.readNum();
            switch(num) {
                case 0: return;
                case 1:
                case 2:
                case 3:
                case 4:
            }
        }
    }

    private void printAdminMode() {
        System.out.println("""
                [ 관리자 모드 ]
                1. 상품 추가
                2. 상품 수정
                3. 상품 삭제
                4. 전체 상품 현황
                0. 메인으로 돌아가기
                """);
    }


    private void printMenu() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("""
              
              [ 실시간 커머스 플랫폼 메인 ]
              %s
              0. 종료
              """, categoriesToStr()));

        if (!cartService.getAll().isEmpty()) {
            sb.append(String.format("""
                    
                    [주문 관리]
                    %d. 장바구니 확인
                    %d. 주문 취소
                    
                    """, categories.length+1, categories.length+2));
        }
        sb.append(String.format("%d. 관리자 모드", categories.length+3));
        System.out.print(sb);
    }

    private void stopRun() {
        this.isRun = false;
    }
}
