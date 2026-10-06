import io.CommonInput;
import order.CartItem;
import order.CartRepository;
import order.CartService;
import order.OrderService;
import product.*;
import java.util.*;

/* 객체 초기화 및 의존 관계 설정 */
public class Main {
    public static void main(String[] args) {
        // LinkedList + HashMap 사용으로 순차 확인 가능 + 단건 조회 등 O(1)
        Map<Long, Product> productMap = new LinkedHashMap<>();

        ProductRepository productRepository = new ProductRepository(productMap);
        ProductService productService = new ProductService(productRepository);

        Map<Long, CartItem> cartMap = new LinkedHashMap<>();
        CartRepository cartRepository = new CartRepository(cartMap);
        CartService cartService = new CartService(cartRepository, productService);

        OrderService orderService = new OrderService(cartService, productService);

        CommonInput input = new CommonInput(System.in);
        CommerceSystem cs = new CommerceSystem(productService, cartService, orderService, input);


        // 더미 상품 삽입
        Arrays.stream(DummyProduct.values())
                .forEach((dummy)-> productService.addProduct(dummy.createDTO()));

        cs.start();
    }
}