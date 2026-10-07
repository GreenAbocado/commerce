import admin.AdminController;
import io.CommonInput;
import order.*;
import product.*;
import product.io.ProductConsole;
import product.io.ProductOutput;

import java.util.*;

/* 객체 초기화 및 의존 관계 설정 */
public class Main {

    /* AdminController에 규칙 정의 (숫자 6~20자리) */
    private static final String ADMIN_PASSWORD = "000000";

    public static void main(String[] args) {

        /* 공통 입력 객체 */
        CommonInput input = new CommonInput(System.in);

        /* 상품 관련 */
        ProductOutput output = new ProductOutput(System.out);
        ProductConsole console = new ProductConsole(input, output);
        ProductService productService = initProductDependencies();
        ProductController productController = new ProductController(productService, console);

        /* 장바구니 관련 */
        CartService cartService = initCartDependencies(productService);
        CartController cartController = new CartController(cartService, input);

        /* 주문 관련 */
        OrderService orderService = new OrderService(cartService, productService);
        OrderController orderController = new OrderController(orderService, cartService, input);

        AdminController adminController = new AdminController(ADMIN_PASSWORD, input);

        CommerceSystem cs =
                new CommerceSystem(productController, cartController, orderController, adminController, input);

        /* 더미 상품 삽입 (상품 관리 구현 후 Controller로 다시 수정) */
        Arrays.stream(DummyProduct.values())
                .forEach((dummy)-> productService.addProduct(dummy.createDTO()));

        /* 커머스 플랫폼 시작 */
        cs.start();
    }

    /* 상품 관련 클래스 의존성 주입 (서비스까지) */
    private static ProductService initProductDependencies() {
        /* 상품 저장소 : LinkedList + HashMap 사용으로 순차 확인 가능 + 단건 조회 등 O(1) */
        Map<Long, Product> productsMap = new LinkedHashMap<>();

        ProductRepository productRepository = new ProductRepository(productsMap);
        return new ProductService(productRepository);
    }

    /* 장바구니 관련 클래스 의존성 주입 (서비스까지) */
    private static CartService initCartDependencies(ProductService productService) {
        /* 장바구니 저장소 : LinkedList + HashMap 사용으로 순차 확인 가능 + 단건 조회 등 O(1) */
        Map<Long, CartItem> cartItemsMap = new LinkedHashMap<>();

        CartRepository cartRepository = new CartRepository(cartItemsMap);
        return new CartService(cartRepository, productService);
    }
}