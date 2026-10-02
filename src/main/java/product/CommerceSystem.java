package product;

import product.dto.ProductResponseDTO;

import java.util.*;
import static exception.ExceptionMessage.NUM_NOT_EXIST;
import static exception.GlobalExceptionHandler.handleException;

/* 사용자 인터페이스, 입출력 및 로직 분기, 입력 검증, 메시지 상수 집합 등 -> 클래스 분리 필요 */
public class CommerceSystem {
    private final ProductManager productManager;
    private boolean isRun;

    public CommerceSystem(ProductManager productManager) {
        this.productManager = productManager;
        this.isRun = true;
    }

    public void start() {
        Scanner sc = new Scanner(System.in);

        while (isRun) {
            handleException(() -> {
                // 카테고리 리스트 출력
                System.out.printf(CATEGORIES_FORMAT, categoriesToString());

                int categoryId = validateInputNum(sc.nextInt(), Category.values().length);
                if (categoryId == -1) { stopRun(); return; }   // 외부 변수 캡쳐 및 effectively final 트러블 슈팅 작성

                // 특정 카테고리 상품 리스트 출력
                Category category = Category.by(categoryId);
                List<ProductResponseDTO> productsList = productManager.getAllByCategory(category);
                System.out.printf(PRODUCTS_FORMAT, category.getName(), productsToString(productsList));

                int productIdx = validateInputNum(sc.nextInt(), productsList.size());
                if (productIdx == -1) { return; }

                // 특정 상품 toString 출력
                System.out.printf(SELECTED_PRODUCT_FORMAT, productsList.get(productIdx));
                System.out.println();
            });
        }
        System.out.print(EXIT);
    }

    private void stopRun() {
        this.isRun = false;
    }


    /* 입력 검증, 출력 관련 메서드: 이후 클래스 분리를 위해 미리 static화하여 결합도 낮춤 */


    private static int validateInputNum(int input, int maxSize) {
        if (input == 0) { return -1; }
        if (input < 1 || input > maxSize) { throw new IllegalArgumentException(NUM_NOT_EXIST);}

        return input-1;
    }

    // 상품 리스트 출력문 생성
    private static String productsToString(List<ProductResponseDTO> list) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            ProductResponseDTO product = list.get(i);
            sb.append(i+1).append('.')
                    .append(String.format(PRODUCT_FORMAT, product.getName(), product.getPrice(), product.getDescription()))
                    .append('\n');
        }
        return sb.toString();
    }

    // 카테고리 리스트 출력문 생성
    private static String categoriesToString() {
        StringBuilder sb = new StringBuilder();
        Category[] categories = Category.values();

        for (int i = 0; i < categories.length; i++) {
            sb.append(String.format("%d. %s\n", categories[i].getId()+1, categories[i]));
        }
        return sb.toString();
    }


    // 출력 메시지 상수 집합
    private static final String CATEGORIES_FORMAT = """
            [ 실시간 커머스 플랫폼 메인 ]
            %s0. 종료
            """;

    private static final String PRODUCTS_FORMAT = """
            [ %s 카테고리 ]
            %s0. 뒤로가기
            """;

    private static final String PRODUCT_FORMAT = " %-14s | %,10d원 | %-14s";
    private static final String SELECTED_PRODUCT_FORMAT = "선택한 상품: %s\n";
    private static final String EXIT = "커머스 플랫폼을 종료합니다.";
}