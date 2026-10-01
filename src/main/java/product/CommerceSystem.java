package product;

import lombok.RequiredArgsConstructor;
import java.util.*;
import static exception.ExceptionMessage.NUM_NOT_EXIST;
import static exception.GlobalExceptionHandler.handleException;

/* 사용자 인터페이스, 입출력 및 로직 분기, 입력 검증, 메시지 상수 집합, 카테고리 관리 등 -> 클래스 분리 필요 */
public class CommerceSystem {
    private final List<Category> categories;   // 입출력 계층에서 데이터 직접 변경 위험 -> 이후 클래스 분리하여 메서드로 사용
    private boolean isRun;

    public CommerceSystem(List<Category> categories) {
        this.categories = categories;
        isRun = true;
    }

    public void start() {
        Scanner sc = new Scanner(System.in);

        while (isRun) {
            handleException(() -> {
                // 카테고리 리스트 출력
                System.out.printf(CATEGORIES_FORMAT, categoryListToString(categories));

                int categoryNum = validateInputNum(sc.nextInt(), categories.size()-1);
                if (categoryNum == -1) { stopRun(); return; }   // 외부 변수 캡쳐 및 effectively final 트러블 슈팅 작성

                // 특정 카테고리 상품 리스트 출력
                Category ca = categories.get(categoryNum);
                System.out.printf(PRODUCTS_FORMAT, ca.getName(), productListToString(ca));

                int productNum = validateInputNum(sc.nextInt(), ca.getAllProducts().size()-1);
                if (productNum == -1) { return; }

                // 특정 상품 toString 출력
                System.out.printf(SELECTED_PRODUCT_FORMAT, ca.getAllProducts().get(productNum));
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

        input -= 1;
        if (input < 0 || input > maxSize) { throw new IllegalArgumentException(NUM_NOT_EXIST);}

        return input;
    }

    // 상품 리스트 출력문 생성
    private static String productListToString(Category category) {
        StringBuilder sb = new StringBuilder();
        List<Product> productList = category.getAllProducts();

        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            sb.append(i+1).append('.')
                    .append(String.format(PRODUCT_FORMAT, product.getName(), product.getPrice(), product.getDescription()))
                    .append('\n');
        }
        return sb.toString();
    }

    // 카테고리 리스트 출력문 생성
    private static String categoryListToString(List<Category> categories) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < categories.size(); i++) {
            sb.append(i+1).append(". ").append(categories.get(i).getName()).append('\n');
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